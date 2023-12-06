package de.fernunihagen.calpata.nli.io;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.uima.jcas.JCas;
import org.dkpro.tc.api.exception.TextClassificationException;
import org.dkpro.tc.api.features.Feature;
import org.dkpro.tc.api.type.TextClassificationTarget;
import de.fernunihagen.calpata.nli.features.ngrams.CharNGramsNormalizedFeatureExtractor;

public class NGramFeatureBuilder {
	

	public static Set<Feature> buildFeatureSet(JCas jcas, TextClassificationTarget target) throws TextClassificationException{
		Set<Feature> featureSet = new LinkedHashSet<Feature>();
		featureSet.addAll(getCharNGramsNormalizedFeature(jcas,target));		
		return featureSet;
	}
	
	
	private static Set<Feature> getCharNGramsNormalizedFeature(JCas jcas, TextClassificationTarget target) throws TextClassificationException {
		CharNGramsNormalizedFeatureExtractor extractor = new CharNGramsNormalizedFeatureExtractor();
		return extractor.extract(jcas, target);		
	}	
}
