package de.fernunihagen.calpata.nli.structures;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.lift.type.Frequency;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;


/**
 * annotates for each token its frequency according to a provided dictionary
 */
@TypeCapability(inputs = {"de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token"})
public class SE_FrequenciesDeReWo
extends JCasAnnotator_ImplBase
{

	public static final String PARAM_LIST_FILE_PATH = "mapFilePath";
	@ConfigurationParameter(name = PARAM_LIST_FILE_PATH, mandatory = true)
	protected String mapFilePath;

	public static final String PARAM_USE_LEMMAS = "useLemmas";
	@ConfigurationParameter(name = PARAM_USE_LEMMAS, mandatory = true, defaultValue = "false")
	private boolean useLemmas;
	
	//TODO: Variable, ob alles gelowercased werden muss

	private final String NAME = "Frequencies";

	private final String sep = "\t";
	
	protected Map<Vocabulary, Double> dict;
	protected Map<String, Double> dictWithoutPos;
	
	public void initialize(UimaContext context)
			throws ResourceInitializationException
	{
		super.initialize(context);
		
		try {
			// if list file is not set, try to load default for language
			if (mapFilePath == "" || mapFilePath == null) {
				Path path = Paths.get(mapFilePath);
				if (Files.notExists(path)) {
					throw new ResourceInitializationException(new Throwable("Cannot load frequency dictionary"));
				}
				mapFilePath = path.toString();
			}

			dict = readMapping(mapFilePath);
			dictWithoutPos = readMappingWithoutPos(mapFilePath);

		} catch (IOException e) {
			throw new ResourceInitializationException(e);
		}
	}

	protected Map<Vocabulary, Double> readMapping(String listFilePath) throws IOException {
		//UTF-8 for German
		Charset inputCharset = Charset.forName("UTF-8");
		Map<Vocabulary, Double> map = new HashMap<Vocabulary, Double>();
		for (String line : FileUtils.readLines(new File(listFilePath), inputCharset)) {
			String[] parts = line.split(" ");
			String str = parts[0].toLowerCase();
			double band = Double.parseDouble(parts[1]);
			String pos = "";
			if(Character.isUpperCase(parts[0].charAt(0))) {
				pos = "NN";
			}
			if(parts.length == 3) {
				pos = parts[2];
			}
			if (str.contains("(")) {
				int parenthesisBegin =str.indexOf("(") ;
				int parenthesisEnd =str.indexOf(")") ;
				String ending = str.substring(parenthesisBegin+1,parenthesisEnd);
				String [] endings = null;
				if (ending.contains(",")) {
					endings = ending.split(",");
				}			
				String str1 = str.substring(0, parenthesisBegin);
//				System.out.println(str1);
				String str2 = null;
				String str3 = null;
				String str4 = null;
				if (endings != null) {
					
					str2 = str1+endings[0];
					
					if (endings.length >= 2) {
						str3 = str1+endings[1];
					}
					if (endings.length >= 3) {
						str4 = str1+endings[2];
					}
				} else {
					str2 = str1+ ending;
				}			
//				System.out.println(str2);
//				System.out.println(str3);
//				System.out.println(str4);
				map.put((new Vocabulary(str1, pos)),band);
				map.put((new Vocabulary(str2, pos)), band);
				if (str3 != null) {
					map.put((new Vocabulary(str3, pos)), band);					
				}
				if (str4 != null) {
					map.put((new Vocabulary(str4, pos)), band);					
				}
				
			} else {
				map.put((new Vocabulary(str, pos)),band);
			}
		}
		return map;
	}
	
	protected Map<String, Double> readMappingWithoutPos(String listFilePath) throws IOException {
		//UTF-8 for German
		Charset inputCharset = Charset.forName("UTF-8");
		Map<String, Double> map = new HashMap<String, Double>();
		for (String line : FileUtils.readLines(new File(listFilePath), inputCharset)) {
			String[] parts = line.split(" ");
			String str = parts[0].toLowerCase();
			double band = Double.parseDouble(parts[1]);
			if (str.contains("(")) {
				int parenthesisBegin =str.indexOf("(") ;
				int parenthesisEnd =str.indexOf(")") ;
				String ending = str.substring(parenthesisBegin+1,parenthesisEnd);
				String [] endings = null;
				if (ending.contains(",")) {
					endings = ending.split(",");
				}			
				String str1 = str.substring(0, parenthesisBegin);
//				System.out.println(str1);
				String str2 = null;
				String str3 = null;
				String str4 = null;
				if (endings != null) {
					
					str2 = str1+endings[0];
					
					if (endings.length >= 2) {
						str3 = str1+endings[1];
					}
					if (endings.length >= 3) {
						str4 = str1+endings[2];
					}
				} else {
					str2 = str1+ ending;
				}			
//				System.out.println(str2);
//				System.out.println(str3);
//				System.out.println(str4);
				if (!map.containsKey(str1)) {
					map.put(str1,band);
				}
				if (!map.containsKey(str2)) {
					map.put(str2,band);
				}				
				
				if (str3 != null) {
					if (!map.containsKey(str3)) {
						map.put(str3,band);
					}						
				}
				if (str4 != null) {
					if (!map.containsKey(str4)) {
						map.put(str4,band);
					}				
				}
				
			} else {
				if (!map.containsKey(str)) {
					map.put(str,band);
				}	
			}
		}
		return map;
	}


	@Override
	public void process(JCas jcas) 
			throws AnalysisEngineProcessException
	{
//		for (Map.Entry<Vocabulary, Double> entry : dict.entrySet()) {
//			Vocabulary key = entry.getKey();
//			Double val = entry.getValue();
//			System.out.println(key.getVocab()+"    "+key.getPos()+"   "+val);
//		}
//		for (Map.Entry<String, Double> entry : dictWithoutPos.entrySet()) {
//			String key = entry.getKey();
//			Double val = entry.getValue();
//			System.out.println(key+"-"+val);
//		}
		Collection<Token> tokens = JCasUtil.select(jcas, Token.class);
		HashSet<Integer> annotated = new HashSet<>();
		String vocab1;
		for (Token t : tokens) {
//			System.out.println(t.getLemmaValue()+" "+t.getPosValue()+ " "+ t.getPos().getCoarseValue());
			int begin = t.getBegin();
			if (t.getLemmaValue().toLowerCase().equals("vieler")) { //fix pos->lemma error
				vocab1 = "viel";
			}else if (t.getLemmaValue().toLowerCase().equals("anderer")) {
				vocab1 = "andere";
			}else {
				vocab1 = t.getLemmaValue().toLowerCase();
			}			
			String pos = t.getPos().getCoarseValue();
			if (pos != null) {
				if (pos.equals("VERB")) {
					pos = "V";
				}else if (pos.equals("DET")) {
					pos = "DET";
				}else if (pos.equals("ADP")) {
					pos = "PREP";
				}else if (pos.equals("CONJ")) {
					pos = "CONJ";
				}else if (pos.equals("PRON")) {
					pos = "PRON";
				}else if (pos.equals("NUM")) {
					pos = "NUM";
				}else if (pos.equals("ADV")) {
					pos = "ADV";
				}else if (pos.equals("NOUN")) {
					pos = "NN";
				}else if (pos.equals("ADJ")) {
					pos = "ADJ";
				}else {
					pos = "others";
				}	
			}else {
				pos = "others";
			}			
			Vocabulary v = new Vocabulary(vocab1, pos);
			
			for (Vocabulary vo : dict.keySet()) {
				if(vo.equals(v)) {
					Frequency f = new Frequency(jcas, t.getBegin(), t.getEnd());
					f.setValue(dict.get(vo));
					f.setFrequencyBand("Band "+dict.get(vo));
					f.addToIndexes();
//					System.out.println("Lemma-Match:"+v.getVocab()+" "+v.getPos());
					annotated.add(begin);
					break;
				}
			}					
		}
		String vocab2;
		for (Token t : tokens) {			
			int begin = t.getBegin();
			if (t.getLemmaValue().toLowerCase().equals("vieler")) { //fix pos->lemma error
				vocab2 = "viel";
			}else if (t.getLemmaValue().toLowerCase().equals("anderer")) {
				vocab2 = "andere";
			}else {
				vocab2 = t.getLemmaValue().toLowerCase();
			}			
//			// ignore annotated tokens with pos
			if (annotated.contains(begin)) {
				continue;
			}
			if(dictWithoutPos.containsKey(vocab2)) {
					Frequency f = new Frequency(jcas, t.getBegin(), t.getEnd());
					f.setValue(dictWithoutPos.get(vocab2));
					f.setFrequencyBand("Band "+dictWithoutPos.get(vocab2));
					f.addToIndexes();
//					System.out.println("Lemma-Match:"+vocab);
			} else {
//				System.out.println(t.getCoveredText()+"  "+t.getLemmaValue()+" "+t.getPosValue());
			}
		}
	}	
	class Vocabulary {
		protected String vocab;
		protected String pos ;
		public Vocabulary(String vocab, String pos) {
			super();
			this.vocab = vocab;
			this.pos = pos;
		}
		public String getVocab() {
			return vocab;
		}
		public void setVocab(String name) {
			this.vocab = name;
		}
		public String getPos() {
			return pos;
		}
		public void setPos(String pOS) {
			this.pos = pOS;
		}
		
		@Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }
	        Vocabulary vocabulary = (Vocabulary) o;
	        return vocab.equals(vocabulary.vocab) &&
	               pos.equals(vocabulary.pos);
	    }
		
	}
	
}