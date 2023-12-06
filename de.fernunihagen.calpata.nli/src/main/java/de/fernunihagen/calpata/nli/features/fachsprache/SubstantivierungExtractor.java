package de.fernunihagen.calpata.nli.features.fachsprache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.dkpro.core.api.frequency.util.FrequencyDistribution;
import org.lift.api.Feature;
import org.lift.api.FeatureType;
import org.lift.api.LiftFeatureExtrationException;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS_NOUN;
import de.fernunihagen.calpata.nli.featureSettings.FeatureExtractor_ImplBase;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

/**
 * Counts the appearance of the German noun-forming suffixes.
 */

@TypeCapability(inputs = { "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token",
		"de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS",
		"de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Lemma" })
public class SubstantivierungExtractor extends FeatureExtractor_ImplBase {

	private List<String> suffixes;
	private List<String> getSuffixes(String suffixesFile)
			throws ResourceInitializationException
	{
		List<String> list = new ArrayList<String>();
		//TODO: UTF-8 for German Umlaut	"tät"
		try {
			for (String suffix : FileUtils.readLines(new File(suffixesFile),"UTF-8")) {
				list.add(suffix);
			}
		} catch (IOException e) {
			throw new ResourceInitializationException(e);
		}
		
		return list;
	}
	@Override
	public Set<Feature> extract(JCas jcas) throws LiftFeatureExtrationException {
		Set<Feature> featureList = new HashSet<Feature>();
		try {
			String suffixesFilePath = "resources/suffixes_de/noun-forming_suffixes_de.txt";
			suffixes = getSuffixes(suffixesFilePath);
		} catch (ResourceInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int countNouns = JCasUtil.select(jcas, POS_NOUN.class).size();
		FrequencyDistribution<String> suffixFD = new FrequencyDistribution<String>();
		for (String s : suffixes) {
//			System.out.println(s);
			for (Token t : JCasUtil.select(jcas, Token.class)) {
				POS pos = t.getPos();
				if (pos instanceof POS_NOUN) {
					if (t.getLemma().getValue().endsWith(s)){
						suffixFD.inc(s);
					}	
				}
			}
			double suffixRatio = (double) suffixFD.getCount(s) / countNouns;
			featureList.add(new Feature("frequencyOf" + s.toUpperCase(), suffixRatio, FeatureType.NUMERIC));
		}
		
		double nominalizations = (double)suffixFD.getN() / countNouns;
		featureList.add(new Feature("frequencyOfAllSuffixes", nominalizations, FeatureType.NUMERIC));
		return featureList;
	}

	@Override
	public String getPublicName() {
		return "SubstantivierungExtractor";
	}
	
}
