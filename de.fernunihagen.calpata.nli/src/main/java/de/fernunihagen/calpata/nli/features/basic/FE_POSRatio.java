package de.fernunihagen.calpata.nli.features.basic;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.lift.api.Feature;
import org.lift.api.FeatureType;
import org.lift.api.LiftFeatureExtrationException;

import de.fernunihagen.calpata.nli.featureSettings.FeatureExtractor_ImplBase;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
/**
 * Counts the occurrence of the frequency bands.
 * 
 * @author Viet Phe
 */
public class FE_POSRatio extends FeatureExtractor_ImplBase{
		
	@Override
	public Set<Feature> extract(JCas jcas) throws LiftFeatureExtrationException {
		Set<Feature> POSFeatures = new HashSet<Feature>();
		Collection<Token> tokens = JCasUtil.select(jcas, Token.class);
		
		int CC = 0;
		int CD = 0;
		int DT = 0;
		int EX = 0;
		int FW = 0;
		int IN = 0;
		int JJ = 0;
		
		int JJR = 0;
		int JJS = 0;
		int LS = 0;
		int MD = 0;
		int NN = 0;
		int NNS = 0;
		int NNP = 0;
		
		int NNPS = 0;
		int PDT = 0;
		int POS = 0;
		int PRP = 0;
		int PRP$ = 0;
		int RB = 0;
		int RBR = 0;
		
		int RBS = 0;
		int RP = 0;
		int SYM = 0;
		int TO = 0;
		int UH = 0;
		int VB = 0;
		int VBD = 0;
		
		int VBG = 0;
		int VBN = 0;
		int VBP = 0;
		int VBZ = 0;
		int WDT = 0;
		int WP = 0;
		int WP$ = 0;		
		int WRB = 0;
		for (Token t : tokens) {
//			System.out.println(t.getCoveredText()+" "+t.getPosValue()+" "+t.getPos().getCoarseValue());
			if(t.getPosValue().equals("CC")) {
				CC++;
			}
			if(t.getPosValue().equals("CD")) {
				CD++;
			}
			if(t.getPosValue().equals("DT")) {
				DT++;
			}
			if(t.getPosValue().equals("EX")) {
				EX++;
			}
			if(t.getPosValue().equals("FW")) {
				FW++;
			}
			if(t.getPosValue().equals("IN")) {
				IN++;
			}
			if(t.getPosValue().equals("JJ")) {
				JJ++;
			}
			if(t.getPosValue().equals("JJR")) {
				JJR++;
			}
			if(t.getPosValue().equals("JJS")) {
				JJS++;
			}
			if(t.getPosValue().equals("LS")) {
				LS++;
			}
			if(t.getPosValue().equals("MD")) {
				MD++;
			}
			if(t.getPosValue().equals("NN")) {
				NN++;
			}
			if(t.getPosValue().equals("NNS")) {
				NNS++;
			}
			if(t.getPosValue().equals("NNP")) {
				NNP++;
			}
			if(t.getPosValue().equals("NNPS")) {
				NNPS++;
			}
			if(t.getPosValue().equals("PDT")) {
				PDT++;
			}
			if(t.getPosValue().equals("POS")) {
				POS++;
			}
			if(t.getPosValue().equals("PRP")) {
				PRP++;
			}
			if(t.getPosValue().equals("PRP$")) {
				PRP$++;
			}
			if(t.getPosValue().equals("RB")) {
				RB++;
			}
			if(t.getPosValue().equals("RBR")) {
				RBR++;
			}
			if(t.getPosValue().equals("RBS")) {
				RBS++;
			}
			if(t.getPosValue().equals("RP")) {
				RP++;
			}
			if(t.getPosValue().equals("SYM")) {
				SYM++;
			}
			if(t.getPosValue().equals("TO")) {
				TO++;
			}
			if(t.getPosValue().equals("UH")) {
				UH++;
			}
			if(t.getPosValue().equals("VB")) {
				VB++;
			}
			if(t.getPosValue().equals("VBD")) {
				VBD++;
			}
			if(t.getPosValue().equals("VBG")) {
				VBG++;
			}
			if(t.getPosValue().equals("VBN")) {
				VBN++;
			}
			if(t.getPosValue().equals("VBP")) {
				VBP++;
			}
			if(t.getPosValue().equals("VBZ")) {
				VBZ++;
			}
			if(t.getPosValue().equals("WDT")) {
				WDT++;
			}
			if(t.getPosValue().equals("WP")) {
				WP++;
			}
			if(t.getPosValue().equals("WP$")) {
				WP$++;
			}
			if(t.getPosValue().equals("WRB")) {
				WRB++;
			}			
			
		}
		int sum = CC +CD +DT+ EX+ FW +IN+ JJ +JJR +JJS+ LS+ MD +NN+NNS +NNP +NNPS+ PDT+ POS +PRP
				+PRP$+RB+RBR+RBS+RP+SYM+TO+UH+VB+VBD+VBG+VBN+VBP+VBZ+WDT+WP+WP$+WRB;
//		System.out.println("Noun: "+sum+" size: "+tokens.size()+"Punct: " +PUNCT );		
		POSFeatures.add( new Feature("POS_CC", (double) CC/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_CD", (double) CD/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_DT", (double) DT/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_EX", (double) EX/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_FW", (double) FW/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_IN", (double) IN/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_JJ", (double) JJ/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_JJR", (double) JJR/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_JJS", (double) JJS/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_LS", (double) LS/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_MD", (double) MD/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_NN", (double) NN/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_NNS", (double) NNS/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_NNP", (double) NNP/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_NNPS", (double) NNPS/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_PDT", (double) PDT/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_POS", (double) POS/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_PRP", (double) PRP/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_PRP$", (double) PRP$/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_RB", (double) RB/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_RBR", (double) RBR/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_RBS", (double) RBS/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_RP", (double) RP/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_SYM", (double) SYM/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_TO", (double) TO/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_UH", (double) UH/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_VB", (double) VB/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_VBD", (double) VBD/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_VBG", (double) VBG/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_VBN", (double) VBN/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_VBP", (double) VBP/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_VBZ", (double) VBZ/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_WDT", (double) WDT/tokens.size(), FeatureType.NUMERIC));		
		POSFeatures.add( new Feature("POS_WP", (double) WP/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_WP$", (double) WP$/tokens.size(), FeatureType.NUMERIC));
		POSFeatures.add( new Feature("POS_WRB", (double) WRB/tokens.size(), FeatureType.NUMERIC));	
		
		return POSFeatures;
	}

	@Override
	public String getPublicName() {
		return "POS_Ratio";
	}

}
