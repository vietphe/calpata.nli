package de.fernunihagen.calpata.nli;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ResourceInitializationException;
import org.dkpro.core.io.xmi.XmiWriter;
import org.lift.api.Configuration.Language;
import de.fernunihagen.calpata.nli.io.TextReader;
import de.fernunihagen.calpata.nli.structures.SE_Connectives;
import de.fernunihagen.calpata.nli.structures.SE_DiscourseReferent;
import de.fernunihagen.calpata.nli.structures.SE_FiniteVerb;
import de.fernunihagen.calpata.nli.structures.SE_FrequencyEVP;

public class BaseExperiment {

	public static void main(String[] args) 
			throws Exception
	{
		String inputPath = "resources\\inputText";
		runTextExample(inputPath, Language.English);
	}
		
	private static void runTextExample(String inputPath, Language language) throws ResourceInitializationException, UIMAException, IOException {
		PreprocessingConfiguration config = new PreprocessingConfiguration(language);
		CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
				TextReader.class, TextReader.PARAM_INPUT_FILE,inputPath, TextReader.PARAM_LANGUAGE,"en" );
		AnalysisEngineDescription prepro = config.getUimaEngineDescription();
		AnalysisEngineDescription frequencyEVP = createEngineDescription(SE_FrequencyEVP.class);
		AnalysisEngineDescription connectivies = createEngineDescription(SE_Connectives.class, SE_Connectives.PARAM_LANGUAGE, "en",
				SE_Connectives.PARAM_USE_LEMMAS, false);
		AnalysisEngineDescription discourseReferents = createEngineDescription(SE_DiscourseReferent.class, SE_DiscourseReferent.PARAM_LANGUAGE, "en");
		AnalysisEngineDescription finiteVerbs = createEngineDescription(SE_FiniteVerb.class, SE_FiniteVerb.PARAM_LANGUAGE, "en");
		AnalysisEngineDescription analyzer = createEngineDescription(Analyzer.class);
		AnalysisEngineDescription xmiWriter = createEngineDescription(
				XmiWriter.class, 
				XmiWriter.PARAM_OVERWRITE, true,
				XmiWriter.PARAM_TARGET_LOCATION, "target/cas"
		);
		
		SimplePipeline.runPipeline(reader, 
				prepro,
//				errors,
//				frequencyEVP,
//				connectivies,
//				spellingMistake,
//				discourseReferents,
//				finiteVerbs,
				analyzer
//				xmiWriter
		);
	}
}