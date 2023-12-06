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
public class FE_FrequencyBandDeReWo extends FeatureExtractor_ImplBase{
		
	@Override
	public Set<Feature> extract(JCas jcas) throws LiftFeatureExtrationException {
		Set<Feature> frequencyFeatures = new HashSet<Feature>();
		Collection<Frequency> frequencies = JCasUtil.select(jcas, Frequency.class);
		int countBand0 = 0;
		int countBand1 = 0;
		int countBand2 = 0;
		int countBand3 = 0;
		int countBand4 = 0;
		int countBand5 = 0;
		int countBand6 = 0;
		int countBand7 = 0;
		int countBand8 = 0;
		int countBand9 = 0;
		int countBand10 = 0;
		int countBand11 = 0;
		int countBand12 = 0;
		int countBand13 = 0;
		int countBand14 = 0;
		int countBand15 = 0;
		int countBand16 = 0;
		int countBand17 = 0;
		int countBand18 = 0;
		int countBand19 = 0;
		int countBand20 = 0;
		int countBand21 = 0;
		int countBand22 = 0;
		int countBand23 = 0;
		int countBand24 = 0;
		int countBand25 = 0;
		int countBand26 = 0;
		int countBand27 = 0;
		int countBand28 = 0;
		int countBand29 = 0;
		
		for (Frequency f : frequencies) {
			if(f.getFrequencyBand().equals("Band 0.0")) {
				countBand0++;
			}
			if(f.getFrequencyBand().equals("Band 1.0")) {
				countBand1++;
			}
			if(f.getFrequencyBand().equals("Band 2.0")) {
				countBand2++;
			}
			if(f.getFrequencyBand().equals("Band 3.0")) {
				countBand3++;
			}
			if(f.getFrequencyBand().equals("Band 4.0")) {
				countBand4++;
			}
			if(f.getFrequencyBand().equals("Band 5.0")) {
				countBand5++;
			}
			if(f.getFrequencyBand().equals("Band 6.0")) {
				countBand6++;
			}
			if(f.getFrequencyBand().equals("Band 7.0")) {
				countBand7++;
			}
			if(f.getFrequencyBand().equals("Band 8.0")) {
				countBand8++;
			}
			if(f.getFrequencyBand().equals("Band 9.0")) {
				countBand9++;
			}
			if(f.getFrequencyBand().equals("Band 10.0")) {
				countBand10++;
			}
			if(f.getFrequencyBand().equals("Band 11.0")) {
				countBand11++;
			}
			if(f.getFrequencyBand().equals("Band 12.0")) {
				countBand12++;
			}
			if(f.getFrequencyBand().equals("Band 13.0")) {
				countBand13++;
			}
			if(f.getFrequencyBand().equals("Band 14.0")) {
				countBand14++;
			}
			if(f.getFrequencyBand().equals("Band 15.0")) {
				countBand15++;
			}
			if(f.getFrequencyBand().equals("Band 16.0")) {
				countBand16++;
			}
			if(f.getFrequencyBand().equals("Band 17.0")) {
				countBand17++;
			}
			if(f.getFrequencyBand().equals("Band 18.0")) {
				countBand18++;
			}
			if(f.getFrequencyBand().equals("Band 19.0")) {
				countBand19++;
			}
			if(f.getFrequencyBand().equals("Band 20.0")) {
				countBand20++;
			}
			if(f.getFrequencyBand().equals("Band 21.0")) {
				countBand21++;
			}
			if(f.getFrequencyBand().equals("Band 22.0")) {
				countBand22++;
			}
			if(f.getFrequencyBand().equals("Band 23.0")) {
				countBand23++;
			}
			if(f.getFrequencyBand().equals("Band 24.0")) {
				countBand24++;
			}
			if(f.getFrequencyBand().equals("Band 25.0")) {
				countBand25++;
			}
			if(f.getFrequencyBand().equals("Band 26.0")) {
				countBand26++;
			}
			if(f.getFrequencyBand().equals("Band 27.0")) {
				countBand27++;
			}
			if(f.getFrequencyBand().equals("Band 28.0")) {
				countBand28++;
			}
			if(f.getFrequencyBand().equals("Band 29.0")) {
				countBand29++;
			}
		}
		frequencyFeatures.add( new Feature("Band0Ratio", (double)countBand0/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band1Ratio", (double)countBand1/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band2Ratio", (double)countBand2/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band3Ratio", (double)countBand3/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band4Ratio", (double)countBand4/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band5Ratio", (double)countBand5/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band6Ratio", (double)countBand6/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band7Ratio", (double)countBand7/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band8Ratio", (double)countBand8/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band9Ratio", (double)countBand9/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band10Ratio", (double)countBand10/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band11Ratio", (double)countBand11/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band12Ratio", (double)countBand12/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band13Ratio", (double)countBand13/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band14Ratio", (double)countBand14/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band15Ratio", (double)countBand15/frequencies.size(), FeatureType.NUMERIC));
		
		frequencyFeatures.add( new Feature("Band16Ratio", (double)countBand16/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band17Ratio", (double)countBand17/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band18Ratio", (double)countBand18/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band19Ratio", (double)countBand19/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band20Ratio", (double)countBand20/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band21Ratio", (double)countBand21/frequencies.size(), FeatureType.NUMERIC));
		
		frequencyFeatures.add( new Feature("Band22Ratio", (double)countBand22/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band23Ratio", (double)countBand23/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band24Ratio", (double)countBand24/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band25Ratio", (double)countBand25/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band26Ratio", (double)countBand26/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band27Ratio", (double)countBand27/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band28Ratio", (double)countBand28/frequencies.size(), FeatureType.NUMERIC));
		frequencyFeatures.add( new Feature("Band29Ratio", (double)countBand29/frequencies.size(), FeatureType.NUMERIC));
		
		return frequencyFeatures;
	}

	@Override
	public String getPublicName() {
		return "FrequencyBandRatio";
	}

}
