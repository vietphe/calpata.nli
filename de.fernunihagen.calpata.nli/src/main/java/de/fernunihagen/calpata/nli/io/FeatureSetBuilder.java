package de.fernunihagen.calpata.nli.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.uima.jcas.JCas;
import org.lift.api.Configuration.Language;
import org.lift.api.Feature;
import org.lift.api.LiftFeatureExtrationException;

import de.fernunihagen.calpata.nli.features.basic.FE_AvgNrOfCharsPerSentence;
import de.fernunihagen.calpata.nli.features.basic.FE_AvgNrOfCharsPerToken;
import de.fernunihagen.calpata.nli.features.basic.FE_AvgNrOfCompoundPerToken;
import de.fernunihagen.calpata.nli.features.basic.FE_AvgNrOfFiniteVerb;
import de.fernunihagen.calpata.nli.features.basic.FE_AvgNrOfNounPhrasesPerSentence;
import de.fernunihagen.calpata.nli.features.basic.FE_AvgNrOfTokensPerSentence;
import de.fernunihagen.calpata.nli.features.basic.FE_CoarseGrainedPOSTagRatio;
import de.fernunihagen.calpata.nli.features.basic.FE_Errors;
import de.fernunihagen.calpata.nli.features.basic.FE_Errors2;
import de.fernunihagen.calpata.nli.features.basic.FE_FrequencyBandDeReWo;
import de.fernunihagen.calpata.nli.features.basic.FE_FrequencyEVPLevel;
import de.fernunihagen.calpata.nli.features.basic.FE_LexicalDensity;
import de.fernunihagen.calpata.nli.features.basic.FE_LexicalVariation;
import de.fernunihagen.calpata.nli.features.basic.FE_NrDiscourseReferent;
import de.fernunihagen.calpata.nli.features.basic.FE_NrOfChars;
import de.fernunihagen.calpata.nli.features.basic.FE_NrOfConnectives;
import de.fernunihagen.calpata.nli.features.basic.FE_NrOfParagraphs;
import de.fernunihagen.calpata.nli.features.basic.FE_POSRatio;
import de.fernunihagen.calpata.nli.features.basic.FE_SyntacticVariability;
import de.fernunihagen.calpata.nli.features.basic.FE_SyntaxTreeDepth;
import de.fernunihagen.calpata.nli.features.basic.FE_TraditionalReadabilityMeasures;
import de.fernunihagen.calpata.nli.features.basic.FE_TrigramCounter;
import de.fernunihagen.calpata.nli.features.basic.FE_TypeTokenRatio;
import de.fernunihagen.calpata.nli.features.basic.ReadabilityConfiguration;
import de.fernunihagen.calpata.nli.features.fachsprache.PassiveSentenceExtractor;
import de.fernunihagen.calpata.nli.features.fachsprache.SubstantivierungExtractor;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.chunk.NC;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.chunk.PC;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.chunk.VC;
import edu.stanford.nlp.patterns.surface.Token;

public class FeatureSetBuilder {

	public static Set<Feature> buildFeatureSet(JCas jcas) throws LiftFeatureExtrationException {
		Set<Feature> featureSet = new LinkedHashSet<Feature>();
//		featureSet.addAll(getAvgNrOfCharsPerSentence(jcas));
//		featureSet.addAll(getAvgNrOfCharsPerToken(jcas));
//		featureSet.addAll(getNrOfChars(jcas));
//		featureSet.addAll(getLexicalDensity(jcas));
//		featureSet.addAll(getLexicalVariation(jcas));
//		featureSet.addAll(getTypeTokenRatio(jcas));
//		featureSet.addAll(getTraditionalReadabilityMeasures(jcas));
//		featureSet.addAll(getSyntaxTreeDepth(jcas));
//		featureSet.addAll(getAvgNrOfNounPhrasesPerSentence(jcas));
//		featureSet.addAll(getAvgNrOfTokensPerSentence(jcas));
//		featureSet.addAll(getSyntacticVariability(jcas));
//		featureSet.addAll(getNrOfConnective(jcas));
//		featureSet.addAll(getFiniteVerbRatio(jcas));
//		featureSet.addAll(getNrOfDiscourseReferent(jcas));
//		featureSet.addAll(getPOSRatio(jcas));
//		featureSet.addAll(getFrequencyRatio(jcas));
//		featureSet.addAll(getNrOfParagraphs(jcas));
//		featureSet.addAll(getErrors(jcas));
//		featureSet.addAll(getErrors2(jcas));
//		featureSet.addAll(getCoarseGrainedPOSTagRatio(jcas));
		
		return featureSet;
	}
	
	private static Set<Feature> getAvgNrOfCharsPerSentence(JCas jcas) throws LiftFeatureExtrationException {
		FE_AvgNrOfCharsPerSentence extractor = new FE_AvgNrOfCharsPerSentence();
		return extractor.extract(jcas);
	}
	
	private static Set<Feature> getAvgNrOfCharsPerToken(JCas jcas) throws LiftFeatureExtrationException {
		FE_AvgNrOfCharsPerToken extractor = new FE_AvgNrOfCharsPerToken();
		return extractor.extract(jcas);
	}
	private static Set<Feature> getAvgNrOfNounPhrasesPerSentence(JCas jcas) throws LiftFeatureExtrationException {
		FE_AvgNrOfNounPhrasesPerSentence extractor = new FE_AvgNrOfNounPhrasesPerSentence();
		return extractor.extract(jcas);		
	}
	private static Set<Feature> getAvgNrOfTokensPerSentence(JCas jcas) throws LiftFeatureExtrationException {
		FE_AvgNrOfTokensPerSentence extractor = new FE_AvgNrOfTokensPerSentence();
		return extractor.extract(jcas);		
	}	
	private static Set<Feature> getLexicalDensity(JCas jcas) throws LiftFeatureExtrationException {
		FE_LexicalDensity extractor = new FE_LexicalDensity();
		return extractor.extract(jcas);
	}	
	private static Set<Feature> getLexicalVariation(JCas jcas) throws LiftFeatureExtrationException {
		FE_LexicalVariation extractor = new FE_LexicalVariation();
		return extractor.extract(jcas);
	}
	private static Set<Feature> getNrOfChars(JCas jcas) throws LiftFeatureExtrationException {
		FE_NrOfChars extractor = new FE_NrOfChars();
		return extractor.extract(jcas);
	}
	
	private static Set<Feature> getSyntacticVariability(JCas jcas) throws LiftFeatureExtrationException {
		FE_SyntacticVariability extractor = new FE_SyntacticVariability();
		return extractor.extract(jcas);		
	}
	private static Set<Feature> getSyntaxTreeDepth(JCas jcas) throws LiftFeatureExtrationException {
		FE_SyntaxTreeDepth extractor = new FE_SyntaxTreeDepth(Language.German);
		return extractor.extract(jcas);		
	}
	
	static List<ReadabilityConfiguration> readabilityConfigurations = new ArrayList<ReadabilityConfiguration>(Arrays.asList(ReadabilityConfiguration.values()));
	
	private static Set<Feature> getTraditionalReadabilityMeasures(JCas jcas) throws LiftFeatureExtrationException {
		FE_TraditionalReadabilityMeasures extractor = new FE_TraditionalReadabilityMeasures(readabilityConfigurations);
		return extractor.extract(jcas);		
	}
	
	private static Set<Feature> getTypeTokenRatio(JCas jcas) throws LiftFeatureExtrationException {
		FE_TypeTokenRatio extractor = new FE_TypeTokenRatio();
		return extractor.extract(jcas);		
	}
	
	private static Set<Feature> getPOSRatio(JCas jcas) throws LiftFeatureExtrationException {
		FE_POSRatio extractor = new FE_POSRatio();
		return extractor.extract(jcas);		
	}
	private static Set<Feature> getNrOfConnective(JCas jcas) throws LiftFeatureExtrationException {
		FE_NrOfConnectives extractor = new FE_NrOfConnectives();
		return extractor.extract(jcas);		
	}
	private static Set<Feature> getFiniteVerbRatio(JCas jcas) throws LiftFeatureExtrationException {
		FE_AvgNrOfFiniteVerb extractor = new FE_AvgNrOfFiniteVerb();
		return extractor.extract(jcas);		
	}
	private static Set<Feature> getNrOfDiscourseReferent(JCas jcas) throws LiftFeatureExtrationException {
		FE_NrDiscourseReferent extractor = new FE_NrDiscourseReferent();
		return extractor.extract(jcas);		
	}	
	private static Set<Feature> getFrequencyRatio(JCas jcas) throws LiftFeatureExtrationException {
		FE_FrequencyEVPLevel extractor = new FE_FrequencyEVPLevel();
		return extractor.extract(jcas);		
	}	
	private static Set<Feature> getNrOfParagraphs(JCas jcas) throws LiftFeatureExtrationException {
		FE_NrOfParagraphs extractor = new FE_NrOfParagraphs();
		return extractor.extract(jcas);		
	}
	private static Set<Feature> getErrors(JCas jcas) throws LiftFeatureExtrationException {
		FE_Errors extractor = new FE_Errors();
		return extractor.extract(jcas);		
	}	
	private static Set<Feature> getErrors2(JCas jcas) throws LiftFeatureExtrationException {
		FE_Errors2 extractor = new FE_Errors2();
		return extractor.extract(jcas);		
	}	
	private static Set<Feature> getCoarseGrainedPOSTagRatio(JCas jcas) throws LiftFeatureExtrationException {
		FE_CoarseGrainedPOSTagRatio extractor = new FE_CoarseGrainedPOSTagRatio();
		return extractor.extract(jcas);		
	}	
}
