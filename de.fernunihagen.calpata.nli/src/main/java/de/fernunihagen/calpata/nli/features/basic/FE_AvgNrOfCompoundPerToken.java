package de.fernunihagen.calpata.nli.features.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.lift.api.Feature;
import org.lift.api.FeatureType;
import org.lift.api.LiftFeatureExtrationException;
import org.lift.type.Structure;

import de.fernunihagen.calpata.nli.featureSettings.FeatureExtractor_ImplBase;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Compound;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class FE_AvgNrOfCompoundPerToken extends FeatureExtractor_ImplBase{
	public static String NR_OF_COMPOUND_PER_NR_OF_NOUN = "NrOfCompoundPerNrOfNoun";
	public static String NR_OF_TYPE_BASED_COMPOUND_PER_NR_OF_NOUN = "NrOfTypeBasedCompoundPerNrOfNoun";
	
	@Override
	public Set<Feature> extract(JCas jcas) throws LiftFeatureExtrationException {
		Set<Feature> features = new HashSet<>();
		List<Integer> nomens = new ArrayList<>();
		Collection<Token> tokens = JCasUtil.select(jcas, Token.class);
		for (Token token : tokens) {
			if (token.getPos().getCoarseValue() != null && token.getPos().getCoarseValue().equals("NOUN"))
			nomens.add(token.getBegin());
		}
		int nrOfCompound = 0;
		for (Structure s : JCasUtil.select(jcas, Structure.class)) {
    		if (s.getName().equals("Compound")) {
    			nrOfCompound++;
    		}
        }
		
		double ratio = (double) nrOfCompound/nomens.size();		
		features.add(new Feature(NR_OF_COMPOUND_PER_NR_OF_NOUN, ratio, FeatureType.NUMERIC));
		
		//here for type-based		
		
		Collection<Compound> compounds = JCasUtil.select(jcas, Compound.class);
		Map<String,Integer> map = new HashMap<>();
		for (Compound c : compounds) {
			map.put(c.getCoveredText(), c.getBegin());
		}
		
		ArrayList <Compound> typeBasedAllCompounds = new ArrayList<>();
		List<Integer> mapValues = new ArrayList<>();
		ArrayList <Compound> typeBasedNomenCompounds = new ArrayList<>();
		
		Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();		
		while (it.hasNext()) {
			Map.Entry<String, Integer> c = it.next();
		    mapValues.add(c.getValue());
		}
		for (Compound compound : compounds) {
			
			if (mapValues.contains(compound.getBegin())) {
				typeBasedAllCompounds.add(compound);
			}
			
		}
		for (Compound compound : typeBasedAllCompounds) {
			if (nomens.contains(compound.getBegin())) {
				typeBasedNomenCompounds.add(compound);
				System.out.println("Compound: "+compound.getCoveredText());
			}
		}
		System.out.println(" C: "+nrOfCompound);
		System.out.println("TB C: "+typeBasedNomenCompounds.size());
		System.out.println("Nomens: "+nomens.size());
		double ratio2 = (double) typeBasedNomenCompounds.size()/nomens.size();
		features.add(new Feature(NR_OF_TYPE_BASED_COMPOUND_PER_NR_OF_NOUN, ratio2, FeatureType.NUMERIC));
		return features;
	}
	@Override
	public String getPublicName() {
		return "CompoundRatio";
	}
	
}
