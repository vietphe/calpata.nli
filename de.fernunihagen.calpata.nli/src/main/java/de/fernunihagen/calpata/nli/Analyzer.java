package de.fernunihagen.calpata.nli;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.dkpro.tc.api.exception.TextClassificationException;
import org.dkpro.tc.api.features.Feature;
import org.dkpro.tc.api.type.TextClassificationTarget;
import org.lift.api.FeatureType;
import org.lift.api.LiftFeatureExtrationException;
import org.lift.type.FeatureAnnotationNumeric;
import de.fernunihagen.calpata.nli.io.FeatureSetBuilder;
import de.fernunihagen.calpata.nli.io.NGramFeatureBuilder;
import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
public class Analyzer extends JCasAnnotator_ImplBase {
	private static TextClassificationTarget aTarget;
	static StringBuilder sb;
	static String firstColumn;
	ArrayList<Map<String,String>> featureList;
//	ArrayList<String> arnomalyList;
	Map<String,String> arnomalyMap;
	Map<String,String> arnomalyMap2;
	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		
		super.initialize(context);
		sb = new StringBuilder();
		featureList = new ArrayList<>();
//		arnomalyList = new ArrayList<>();
		arnomalyMap= new HashMap<>();
		arnomalyMap2= new HashMap<>();
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {		
		String id = "no Id";
		String text = "no text";
		if (JCasUtil.exists(aJCas, DocumentMetaData.class)){
			DocumentMetaData meta = JCasUtil.selectSingle(aJCas, DocumentMetaData.class);
			id = meta.getDocumentId();
			text = meta.getDocumentTitle();
		}		
		System.out.println(id);	
		// Annotate the features
//		try {
//			Set<Feature> fes = FeatureSetBuilder.buildFeatureSet(aJCas);
//			//add Annotation type
//			for (Feature f : fes) {
//				String name = f.getName();
//				FeatureType featureType = f.getType();
//				Object value = f.getValue();
////				System.out.println(name+": "+value.toString());
//				FeatureAnnotationNumeric fa = new FeatureAnnotationNumeric(aJCas, 0, 0);
//				fa.setName(name);
//				fa.setValue(Double.valueOf(value.toString()));
//				fa.addToIndexes();
//			}
//		
//		} catch (LiftFeatureExtrationException e) {
//			
//			e.printStackTrace();
//		}
//		write feature values in .csv file
//		try {
//			Set<Feature> fes = FeatureSetBuilder.buildFeatureSet(aJCas);
//			Map<String,String> featureMap = new HashMap<>();
//			featureMap.put("textId", id);
////			featureMap.put("length",String.valueOf(JCasUtil.select(aJCas, Token.class).size()));
//			for (Feature feature : fes) {
////				System.out.println(feature.getName()+feature.getValue().toString());
//				featureMap.put(feature.getName(), feature.getValue().toString()); //TODO: check casting type
//			}
//			featureList.add(featureMap);
//		} catch (LiftFeatureExtrationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			Set<Feature> fes = NGramFeatureBuilder.buildFeatureSet(aJCas, aTarget);		
			Map<String,String> featureMap = new HashMap<>();
			featureMap.put("textId", id);
			for (Feature feature : fes) {
				featureMap.put(feature.getName(), feature.getValue().toString());
			}
			featureList.add(featureMap);
		} catch (TextClassificationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	@Override
	public void destroy() {
		exportMultiHashMapListToCSV("target\\output\\ngram_test.csv", featureList);
	}
		
	
	public static void exportMultiHashMapListToCSV(String filePath, List<Map<String, String>> hashMapList) {
        Set<String> allKeys = getAllKeys(hashMapList);

        try (FileWriter writer = new FileWriter(filePath)) {
            // Write the header with all keys
            for (String key : allKeys) {
                writer.write("," + key);
            }
            writer.write("\n");

            // Write the values
            for (Map<String, String> map : hashMapList) {
                for (String key : allKeys) {
                    String value = map.getOrDefault(key, "0");
                    writer.write("," + value);
                }
                writer.write("\n");
            }

            System.out.println("Data successfully exported to CSV file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error exporting data: " + e.getMessage());
        }
    }
	
	private static Set<String> getAllKeys(List<Map<String, String>> hashMapList) {
        Set<String> allKeys = new HashSet<>();
        for (Map<String, String> map : hashMapList) {
            allKeys.addAll(map.keySet());
        }
        return allKeys;
    }
}
