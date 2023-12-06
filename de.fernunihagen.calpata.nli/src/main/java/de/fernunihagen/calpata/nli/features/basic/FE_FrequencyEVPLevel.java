package de.fernunihagen.calpata.nli.features.basic;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.lift.api.Feature;
import org.lift.api.FeatureType;
import org.lift.api.LiftFeatureExtrationException;
import org.lift.type.Frequency;

import de.fernunihagen.calpata.nli.featureSettings.FeatureExtractor_ImplBase;
/**
 * Counts the occurrence of the frequency bands.
 * 
 * @author Viet Phe
 */
public class FE_FrequencyEVPLevel extends FeatureExtractor_ImplBase{
		
	@Override
	public Set<Feature> extract(JCas jcas) throws LiftFeatureExtrationException {
		Set<Feature> frequencyFeatures = new HashSet<Feature>();
		Collection<Frequency> frequencies = JCasUtil.select(jcas, Frequency.class);
		
		int countA1 = 0;
		int countA2 = 0;
		int countB1 = 0;
		int countB2 = 0;
		int countC1 = 0;
		int countC2 = 0;
		
		for (Frequency f : frequencies) {
			if(f.getFrequencyBand().equals("A1")) {
				countA1++;
			}
			if(f.getFrequencyBand().equals("A2")) {
				countA2++;
			}
			if(f.getFrequencyBand().equals("B1")) {
				countB1++;
			}
			if(f.getFrequencyBand().equals("B2")) {
				countB2++;
			}
			if(f.getFrequencyBand().equals("C1")) {
				countC1++;
			}
			if(f.getFrequencyBand().equals("C2")) {
				countC2++;
			}
			
		}
		frequencyFeatures.add( new Feature("A1Ratio", (double)countA1/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("A2Ratio", (double)countA2/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("B1Ratio", (double)countB1/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("B2Ratio", (double)countB2/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("C1Ratio", (double)countC1/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("C2Ratio", (double)countC2/frequencies.size(), FeatureType.NUMERIC));
		return frequencyFeatures;
	}

	@Override
	public String getPublicName() {
		return "FrequencyEVPRatio";
	}

}
