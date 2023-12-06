package de.fernunihagen.calpata.nli.features.basic;

import java.util.HashSet;
import java.util.Set;

import org.apache.uima.fit.descriptor.LanguageCapability;
import org.apache.uima.fit.descriptor.TypeCapability;

import org.apache.uima.jcas.JCas;
import org.lift.api.Feature;
import org.lift.api.FeatureType;
import org.lift.api.LiftFeatureExtrationException;

import de.fernunihagen.calpata.nli.featureSettings.FeatureExtractor_ImplBase;

/**
 * Counts the number of paragraphs
 */
@TypeCapability(inputs = { "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token"})
@LanguageCapability({ "de","en" })
public class FE_NrOfParagraphs 
	extends FeatureExtractor_ImplBase
{
	@Override
	public Set<Feature> extract(JCas jcas) 
			throws LiftFeatureExtrationException
	{	
		Set<Feature> features = new HashSet<>();
		int numOfParagraph = 0;
		//TODO: They are "\n" (Linux and MacOS X), "\r" (MacOS 9 and older) and "\r\n" (Windows).
		String[] lines = jcas.getDocumentText().split("\r\n|\n|\r");
		for (String line : lines) {
		   if(!line.equals("")) {
			   numOfParagraph++;
		   }
		}		
//		System.out.println("Num of Paragraph:" +numOfParagraph);
		features.add(new Feature("NrOfParagraphs", (double) numOfParagraph, FeatureType.NUMERIC));
		return features;
	}
	

	@Override
	public String getPublicName() {
		return "NrOfParagraphs";
	}
}

