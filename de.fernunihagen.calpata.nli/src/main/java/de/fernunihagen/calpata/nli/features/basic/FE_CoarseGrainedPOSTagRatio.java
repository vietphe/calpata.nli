package de.fernunihagen.calpata.nli.features.basic;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.lift.api.Feature;
import org.lift.api.FeatureType;
import org.lift.api.LiftFeatureExtrationException;

import de.fernunihagen.calpata.nli.featureSettings.FeatureExtractor_ImplBase;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
/**
 * Counts the occurrence of the frequency bands.
 * 
 * @author Viet Phe
 */
public class FE_CoarseGrainedPOSTagRatio extends FeatureExtractor_ImplBase{
		
	@Override
	public Set<Feature> extract(JCas jcas) throws LiftFeatureExtrationException {
		Set<Feature> CoarseGrainedPOSTagRatio = new HashSet<Feature>();
		Collection<Token> tokens = JCasUtil.select(jcas, Token.class);
		Collection<POS> poses = JCasUtil.select(jcas, POS.class);
		Map<String,Integer> posFrequencyMap = new HashMap<>();
			for (POS p: poses) {
				if(p.getCoarseValue()!=null) {
					posFrequencyMap.put(p.getCoarseValue(), posFrequencyMap.getOrDefault(p.getCoarseValue(), 0) + 1);
				}			
			}
		for (Map.Entry<String, Integer> entry : posFrequencyMap.entrySet()) {
			String key = entry.getKey();
			Integer val = entry.getValue();
			CoarseGrainedPOSTagRatio.add( new Feature(key, (double) val/tokens.size(), FeatureType.NUMERIC));
		}		
		return CoarseGrainedPOSTagRatio;
	}

	@Override
	public String getPublicName() {
		return "CoarseGrainedPOSTagRatio";
	}

}
