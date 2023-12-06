package de.fernunihagen.calpata.nli.io;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
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


public class TextReader extends JCasCollectionReader_ImplBase {

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
	int index;
	
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		items = new LinkedList<QueueItem>();
		index = 0;
		
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
				String text = cleanString(String.join("\n", FileUtils.readLines(f,inputCharset)));
//				System.out.println(String.join(" ", FileUtils.readLines(f,inputCharset)));
//				System.out.println(text);
//				System.out.println( );
				if (text.startsWith("missing data") || text.equals("")) {
					continue;
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
		textForCas = textForCas.replaceAll("´", "'");
		textForCas = textForCas.replaceAll("…", "...");						
		textForCas = textForCas.replaceAll("`", "'");
		textForCas = textForCas.replaceAll("’", "'");		
//		textForCas = textForCas.replaceAll("[^a-zA-Z0-9\\-\\.,:;\\(\\)\\'´’…`@/?! ]", "");
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