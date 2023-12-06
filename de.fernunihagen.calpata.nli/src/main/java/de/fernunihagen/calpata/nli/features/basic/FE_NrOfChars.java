package de.fernunihagen.calpata.nli.features.basic;

import java.util.Set;

import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.lift.api.Feature;
import org.lift.api.FeatureType;
import org.lift.api.LiftFeatureExtrationException;

import de.fernunihagen.calpata.nli.featureSettings.FeatureExtractor_ImplBase;

/**
 * Extracts the total number of characters.
 */
@TypeCapability(inputs = { "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token"})
public class FE_NrOfChars 
	extends FeatureExtractor_ImplBase
{

	public static String NR_OF_CHARS = "NrOfChars";

	@Override
	public Set<Feature> extract(JCas jcas) throws LiftFeatureExtrationException {
		
		String text = jcas.getDocumentText();
		double nrOfChars = text.length();
		
		return new Feature(NR_OF_CHARS, nrOfChars, FeatureType.NUMERIC).asSet();
	}

	@Override
	public String getPublicName() {
		return "NrOfChars";
	}
	
}
