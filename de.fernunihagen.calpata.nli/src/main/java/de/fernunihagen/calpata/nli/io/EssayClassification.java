package de.fernunihagen.calpata.nli.io;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.dkpro.core.api.resources.ResourceUtils;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;


public class EssayClassification extends JCasCollectionReader_ImplBase {

	public static final String PARAM_INPUT_FILE = "InputFile";
	@ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = true)
	protected String inputFileString;
	protected URL inputFileURL;

	public static final String PARAM_SCORE_FILE = "ScoreFile";
	@ConfigurationParameter(name = PARAM_SCORE_FILE, mandatory = false)
	protected String scoreFileString;

	public static final String PARAM_LANGUAGE = "Language";
	@ConfigurationParameter(name = PARAM_LANGUAGE, mandatory = false, defaultValue = "en")
	protected String language;

	public static final String PARAM_ENCODING = "Encoding";
	@ConfigurationParameter(name = PARAM_ENCODING, mandatory = false, defaultValue = "UTF-8")
	private String encoding;

	public static final String PARAM_SEPARATOR = "Separator";
	@ConfigurationParameter(name = PARAM_SEPARATOR, mandatory = false, defaultValue = "\t")
	private String separator;

	protected int currentIndex;

	protected Queue<QueueItem> items;
	//TODO: adjust filepath;
	protected String mapFilePath = "resources/labels.train.csv";
	int index;
	
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		items = new LinkedList<QueueItem>();
		index = 0;
		
		Map<String, String> dict = new HashMap<>();
		try {
			dict = readMapping(mapFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Map.Entry<String, String> entry : dict.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			System.out.println(key+" - "+val);
		}
		
		try {
			inputFileURL = ResourceUtils.resolveLocation(inputFileString, this, aContext);
			File file = new File(inputFileString);
			//UTF-8 for German
			Charset inputCharset = Charset.forName("UTF-8");
			File[] fileArray = file.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.indexOf(".txt") != -1;
				}
			});
			for (File f : fileArray) {
				String id = f.getName();
				String text = String.join("\n", FileUtils.readLines(f,inputCharset));
				System.out.println(text);
				System.out.println(id);
				if (text.startsWith("missing data") || text.equals("")) {
					continue;
				}
				if(dict.get(id).equals("ARA")) {
					writeTextToFile(text, "D:\\HiWi\\NLI\\ARA\\",id);
				}
				if(dict.get(id).equals("CHI")) {
					writeTextToFile(text, "D:\\HiWi\\NLI\\CHI\\",id);
				}
				if(dict.get(id).equals("FRE")) {
					writeTextToFile(text, "D:\\HiWi\\NLI\\FRE\\",id);
				}
				if(dict.get(id).equals("GER")) {				
					writeTextToFile(text, "D:\\HiWi\\NLI\\GER\\",id);
				}
				if(dict.get(id).equals("HIN")) {
					writeTextToFile(text, "D:\\HiWi\\NLI\\HIN\\",id);
				}
				if(dict.get(id).equals("ITA")) {
					writeTextToFile(text, "D:\\HiWi\\NLI\\ITA\\",id);
				}
				if(dict.get(id).equals("JPN")) {
					writeTextToFile(text, "D:\\HiWi\\NLI\\JPN\\",id);
				}
				if(dict.get(id).equals("KOR")) {
					writeTextToFile(text, "D:\\HiWi\\NLI\\KOR\\",id);
				}
				if(dict.get(id).equals("SPA")) {				
					writeTextToFile(text, "D:\\HiWi\\NLI\\SPA\\",id);
				}
				if(dict.get(id).equals("TEL")) {
					writeTextToFile(text, "D:\\HiWi\\NLI\\TEL\\",id);
				}
				if(dict.get(id).equals("TUR")) {
					writeTextToFile(text, "D:\\HiWi\\NLI\\TUR\\",id);
				}
				QueueItem item = new QueueItem(id, text);
				items.add(item);
			}
			
	      	
	    }
	    catch(IOException ioe) {
	      ioe.printStackTrace();
	    }
		
		currentIndex = 0;
	}
	// HOTFIX for Issue 445 in DKPro Core
	private static String cleanString(String textForCas) {
//		textForCas = textForCas.replaceAll("[^a-zA-Z0-9\\-\\.,:;\\(\\)\\'´’…`@/?! ]", "");
		textForCas = textForCas.replaceAll("…", "...");
		textForCas = textForCas.replaceAll("´", "'");				
		textForCas = textForCas.replaceAll("`", "'");
		textForCas = textForCas.replaceAll("’", "'");	
		//to add space after a dot if not
//		textForCas = textForCas.replaceAll("[,.!?;:]", "$0 ").replaceAll("\\s+", " "); 
		return textForCas;
	}
	
	public boolean hasNext() throws IOException, CollectionException {
		return !items.isEmpty();
	}

	public Progress[] getProgress() {
		return new Progress[] { new ProgressImpl(currentIndex, currentIndex, Progress.ENTITIES) };
	}

	@Override
	public void getNext(JCas jcas) throws IOException, CollectionException {
		QueueItem item = items.poll();
//		getLogger().debug(item);
		
		try {
			
			jcas.setDocumentLanguage(language);
			jcas.setDocumentText(item.getText());
			
			DocumentMetaData dmd = DocumentMetaData.create(jcas);
			//TODO: The name of the getters und setters must be meaningful
			dmd.setDocumentId(item.getId());
			dmd.setDocumentTitle(item.getText());	
		}

		catch (Exception e) {
			throw new CollectionException(e);
		}
		currentIndex++;
	}
	protected Map<String, String> readMapping(String listFilePath) throws IOException {

		Map<String, String> map = new HashMap<String, String>();
		for (String line : FileUtils.readLines(new File(listFilePath), "UTF-8")) {
			String[] parts = line.split(",");
			map.put(parts[0]+".txt", parts[3]);
		}
		return map;
	}
	// Function to write a String to a text file
	public static void writeTextToFile(String content, String destinationFolder, String fileName) throws IOException {
        File folder = new File(destinationFolder);

        // Create the destination folder if it doesn't exist
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String filePath = destinationFolder + File.separator + fileName;

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
        }
    }
	
	
	class QueueItem{
		private String id;
		private String text;
		
		public QueueItem(String id, String text) {
			super();
			this.id = id;
			this.text = text;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		
	}
	
}