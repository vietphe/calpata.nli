package de.fernunihagen.calpata.nli.structures;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.lift.api.StructureExtractor;
import org.lift.type.Structure;

import de.tudarmstadt.ukp.dkpro.core.api.anomaly.type.GrammarAnomaly;
import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;

public class SE_SpellingMistake extends StructureExtractor {

	public static final String spellingMistake1 = "^Possible spelling mistake. Did you mean.*the (plural|past tense|comparative) form of the (noun|verb|adjective) '.*'?";
	public static final Pattern spellingMistakeP1 = Pattern.compile(spellingMistake1);
	public static final String spellingMistake2 = "^Possible spelling mistake found";
	public static final Pattern spellingMistakeP2 = Pattern.compile(spellingMistake2);
	public static final String spellingMistake3 = "^Possible typo. Did you mean.*";
	public static final Pattern spellingMistakeP3 = Pattern.compile(spellingMistake3);
	public static final String spellingMistake4 = "^Did you mean <suggestion>.*</suggestion>.*";
	public static final Pattern spellingMistakeP4 = Pattern.compile(spellingMistake4);
	
	public static boolean corpusIsMEWS = true;
	// manual for MEWS
	public static final String manual1 = "^Did you mean the preposition <suggestion>through</suggestion>.*";
	public static final Pattern manualP1 = Pattern.compile(manual1);
	public static final String manual2 = "^Did you mean <suggestion>am I</suggestion> or <suggestion>I am</suggestion>?";
	public static final Pattern manualP2 = Pattern.compile(manual2);
	public static final String manual3 = "^Did you mean <suggestion>doesn't</suggestion> (= verb)?";
	public static final Pattern manualP3 = Pattern.compile(manual3);
	public static final String manual4 = "^Did you mean the adjective <suggestion>great</suggestion>?";
	public static final Pattern manualP4 = Pattern.compile(manual4);
	
	//Levenshtein distance = 1, but is grammatical error, not sm
	public static final String noSm1 = "^Did you mean <suggestion>affect</suggestion> (have an effect upon)?";
	public static final Pattern noSmP1 = Pattern.compile(noSm1);	
	public static final String noSm2 = "^Did you mean <suggestion>allow</suggestion>? Alternatively, check whether the singular of 'mothers' should be used.";
	public static final Pattern noSmP2 = Pattern.compile(noSm2);
	public static final String noSm3 = "^Did you mean <suggestion>come</suggestion>? Alternatively, check whether the singular of 'parents' should be used.";
	public static final Pattern noSmP3 = Pattern.compile(noSm3);
	public static final String noSm4 = "^Did you mean <suggestion>defend</suggestion>?";
	public static final Pattern noSmP4 = Pattern.compile(noSm4);
	public static final String noSm5 = "^Did you mean <suggestion>happen</suggestion>?";
	public static final Pattern noSmP5 = Pattern.compile(noSm5);
	public static final String noSm6 = "^Did you mean <suggestion>many times</suggestion> or <suggestion>much time</suggestion>?";
	public static final Pattern noSmP6 = Pattern.compile(noSm6);
	public static final String noSm7 = "^Did you mean <suggestion>now</suggestion> (=at this moment) instead of 'no' (negation)?";
	public static final Pattern noSmP7 = Pattern.compile(noSm7);
	public static final String noSm8 = "^Did you mean <suggestion>seem</suggestion>? Alternatively, check whether the singular of 'children' should be used.";
	public static final Pattern noSmP8 = Pattern.compile(noSm8);
	public static final String noSm9 = "^Did you mean <suggestion>think</suggestion>?";
	public static final Pattern noSmP9 = Pattern.compile(noSm9);
	public static final String noSm10 = "^Did you mean <suggestion>want</suggestion>? Alternatively, check whether the singular of 'children' should be used.";
	public static final Pattern noSmP10 = Pattern.compile(noSm10);
	public static final String noSm11 = "^Did you mean <suggestion>whom</suggestion>?";
	public static final Pattern noSmP11 = Pattern.compile(noSm11);
	public static final String noSm12 = "^Did you mean <suggestion>work</suggestion>? Alternatively, check whether the singular of 'people' should be used.";
	public static final Pattern noSmP12 = Pattern.compile(noSm12);
	public static final String noSm13 = "^Did you mean <suggestion>think</suggestion>?";
	public static final Pattern noSmP13 = Pattern.compile(noSm13);
	
	@Override
	public String getPublicStructureName() {
		return "Spelling mistake";
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		String id = "no id";
		if (JCasUtil.exists(aJCas, DocumentMetaData.class)){
			DocumentMetaData meta = JCasUtil.selectSingle(aJCas, DocumentMetaData.class);
			id = meta.getDocumentId();
		}
		System.out.println(id);		
		Set<Integer> offsets = new HashSet<>();
		Collection<GrammarAnomaly> gas = JCasUtil.select(aJCas, GrammarAnomaly.class);
		loop:
		for (GrammarAnomaly g : gas) {
//			System.out.println(g);
//			System.out.println("----------------------------------------");
			int begin = g.getBegin();
			String description = g.getDescription();
			String coveredText = g.getCoveredText();
			// löst die doppelte Annotation
			if(offsets.contains(begin)) {
				continue loop;
			}
			offsets.add(begin);
			
			Matcher spellingMistakeM1 = spellingMistakeP1.matcher(description);
			Matcher spellingMistakeM2 = spellingMistakeP2.matcher(description);
			Matcher spellingMistakeM3 = spellingMistakeP3.matcher(description);
			
			Matcher spellingMistakeM4 = spellingMistakeP4.matcher(description);	
			
			Matcher manualM1 = manualP1.matcher(description);
			Matcher manualM2 = manualP2.matcher(description);
			Matcher manualM3 = manualP3.matcher(description);
			Matcher manualM4 = manualP4.matcher(description);
			
			Matcher noSmM1 = noSmP1.matcher(description);
			Matcher noSmM2 = noSmP2.matcher(description);
			Matcher noSmM3 = noSmP3.matcher(description);
			Matcher noSmM4 = noSmP4.matcher(description);
			Matcher noSmM5 = noSmP5.matcher(description);
			Matcher noSmM6 = noSmP6.matcher(description);
			Matcher noSmM7 = noSmP7.matcher(description);
			Matcher noSmM8 = noSmP8.matcher(description);
			Matcher noSmM9 = noSmP9.matcher(description);
			Matcher noSmM10 = noSmP10.matcher(description);
			Matcher noSmM11 = noSmP11.matcher(description);
			Matcher noSmM12 = noSmP12.matcher(description);
			Matcher noSmM13 = noSmP13.matcher(description);
			
			if (noSmM1.find()||noSmM2.find()||noSmM3.find()|| noSmM4.find()||noSmM5.find()||noSmM6.find()||noSmM7.find()
					|| noSmM8.find()||noSmM9.find()||noSmM10.find()||noSmM11.find()
					|| noSmM12.find()||noSmM13.find()) {
				
			}else {
				if (spellingMistakeM1.find()||spellingMistakeM2.find()||spellingMistakeM3.find()
						|| manualM1.find()||manualM2.find()||manualM3.find()||manualM4.find()	) {
						Structure s = new Structure(aJCas);
						s.setBegin(begin);
						s.setEnd(g.getEnd());
						s.setName("spelling mistake");
						s.addToIndexes();
				}
					// with Levenshtein distance
				if (spellingMistakeM4.find()) {
					int beginOffsetOfSuggestionWord = description.indexOf("<suggestion>") + 12; // because <suggestion> has length 12
					int endOffsetOfSuggestionWord = description.indexOf("</suggestion>");
					String suggestionWord = description.substring(beginOffsetOfSuggestionWord, endOffsetOfSuggestionWord);
					int levenshteinDistance = LevenshteinDistance.getDefaultInstance().apply(suggestionWord, coveredText);
					System.out.println(levenshteinDistance);
					if(levenshteinDistance <= 2) {
						Structure s = new Structure(aJCas);
						s.setBegin(begin);
						s.setEnd(g.getEnd());
						s.setName("spelling mistake");
						s.addToIndexes();
					}
					
				}
			}
		}				
	}	
}
