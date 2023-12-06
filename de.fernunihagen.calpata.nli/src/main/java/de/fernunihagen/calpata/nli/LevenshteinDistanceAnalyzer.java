package de.fernunihagen.calpata.nli;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.pipeline.JCasIterator;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.dkpro.core.api.featurepath.FeaturePathException;
import org.dkpro.core.api.featurepath.FeaturePathFactory;
import org.lift.api.Feature;
import org.lift.api.FeatureType;
import org.lift.api.LiftFeatureExtrationException;
import org.lift.type.FeatureAnnotationNumeric;

import de.fernunihagen.calpata.nli.io.CasFeatureFileWriter;
import de.fernunihagen.calpata.nli.io.FeatureCSVFileWriter;
import de.fernunihagen.calpata.nli.io.FeatureSetBuilder;
import de.tudarmstadt.ukp.dkpro.core.api.anomaly.type.GrammarAnomaly;
import de.tudarmstadt.ukp.dkpro.core.api.anomaly.type.SpellingAnomaly;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.Morpheme;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.morph.MorphologicalFeatures;
import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Compound;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.chunk.NC;

public class LevenshteinDistanceAnalyzer extends JCasAnnotator_ImplBase {
	
	public static final String errorMsg = "^Did you mean <suggestion>.*</suggestion>.*";
	public static final Pattern errorMsgPattern = Pattern.compile(errorMsg);
	
	static ArrayList<Object[]> arrayList;
	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		
		super.initialize(context);
		
		arrayList = new ArrayList<>();
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {		
		String id = "no Id";
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
//		int punct = 0;
//		for (Token t : tokens) {
//			if (t.getPos().getCoarseValue().equals("PUNCT")) {
//				punct++;
//			};
//		}
//		int numberOfWord = tokens.size()-punct;
		
		if (JCasUtil.exists(aJCas, DocumentMetaData.class)){
			DocumentMetaData meta = JCasUtil.selectSingle(aJCas, DocumentMetaData.class);
			id = meta.getDocumentId();
		}
		System.out.println(id);			
		Collection<GrammarAnomaly> gas = JCasUtil.select(aJCas, GrammarAnomaly.class);
		
		loop1:
		for (GrammarAnomaly g : gas) {
			String description = g.getDescription();
			String coveredText = g.getCoveredText();
			
			Matcher errorMsgMatcher = errorMsgPattern.matcher(description);	
			
			if(errorMsgMatcher.find()) {
				int beginOffsetOfSuggestionWord = description.indexOf("<suggestion>") + 12; // because <suggestion> has length 12
				int endOffsetOfSuggestionWord = description.indexOf("</suggestion>");
				String suggestionWord = description.substring(beginOffsetOfSuggestionWord, endOffsetOfSuggestionWord);
				int levenshteinDistance = LevenshteinDistance.getDefaultInstance().apply(suggestionWord, coveredText);
				String max1 = "0";
				String max2 = "0";
				String max3 = "0";
				String max4 = "0";
				if(levenshteinDistance <= 1 ) {
					max1 = "1";
				}
				if(levenshteinDistance <= 2 ) {
					max2 = "1";
				}
				if(levenshteinDistance <= 3 ) {
					max3 = "1";
				}
				if(levenshteinDistance <= 4 ) {
					max4 = "1";
				}
				arrayList.add(new Object[] {description,levenshteinDistance,max1,max2,max3,max4});
				continue loop1;				
			}
			
		}	
	}
	@Override
	public void destroy() {
				exportToTSV(arrayList,"D:\\HiWi\\LiFT\\output\\Levenshtein.tsv");
	}		
	private static void exportToTSV(ArrayList<Object[]> data, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Object[] row : data) {
                for (int i = 0; i < row.length; i++) {
                    writer.write(String.valueOf(row[i]));
                    if (i < row.length - 1) {
                        writer.write("\t"); // Use tab as the delimiter
                    }
                }
                writer.newLine(); // Start a new line
            }
            System.out.println("Data exported successfully to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
