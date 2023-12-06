package de.fernunihagen.calpata.nli.features.fachsprache;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.lift.api.Feature;
import org.lift.api.FeatureType;
import org.lift.api.LiftFeatureExtrationException;

import de.fernunihagen.calpata.nli.featureSettings.FeatureExtractor_ImplBase;
import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import edu.stanford.nlp.parser.nndep.DependencyParser;

/**
 * Counts the occurrence of the passive sentences.
 * 
 * @author Yuning
 */
@TypeCapability(inputs = {"de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token",
		"de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence",
		"de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS",
		"de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Lemma" })
public class PassiveSentenceExtractor  extends FeatureExtractor_ImplBase {
	
	@Override
	public Set<Feature> extract(JCas jcas) throws LiftFeatureExtrationException {
//		DocumentMetaData meta = JCasUtil.selectSingle(jcas, DocumentMetaData.class);
//		System.out.println(meta.getDocumentId());
		Set<Feature> passiveFeatures = new HashSet<Feature>();
		Collection<Sentence> sentences = JCasUtil.select(jcas, Sentence.class);
		//System.out.println("Sentence Sum: "+sentences.size());
		//Counter of different type of passive sentences
		int countTypicalPassive = 0;
		int countPassiveWithImpersonalPronoun  = 0;
		int countPassiveWithSichLassen = 0 ;
		int countPassiveWithAdjective = 0;
		int countPassiveWithZu = 0;
		int countPassiveWithBekommen = 0;
		
		for(Sentence s:sentences){
			Collection<Token> tokenInSentence = JCasUtil.selectCovered(Token.class, s);
			Collection<Dependency> deps = JCasUtil.selectCovered(Dependency.class, s);
//			for (Dependency dep : deps) {
//				System.out.println(dep.getGovernor().getCoveredText() +" "+dep.getDependencyType()+" "+dep.getDependent().getCoveredText());
//			}
//			boolean impersonalPronoun =false;
			boolean werden = false;
			boolean sein = false;
//			boolean partizipII = false;			
//			boolean adjective = false;
//			boolean zu = false;
//			boolean lassen = false; 
//			boolean sich = false;
//			boolean bekommen = false;
			
			ImpersonalPronoun impersonalPronoun = null;
//			Werden werden = null;
			Sein seinObject = null;
			PartizipII partizipII = null;
			Adjective adjective = null;
			Zu zu = null;
			Lassen lassen = null;
			Sich sich = null;
			Bekommen bekommen = null;
			Comma comma = null;
			int offset = 0;
			int offsetComma;
			int distance = 7;
			
			for(Token t :tokenInSentence){				
				String text = t.getCoveredText().toLowerCase();
				String posValue = t.getPos().getPosValue();
				String lemmaValue = t.getLemma().getValue();
//				System.out.println(text+" "+posValue+" "+lemmaValue+ " "+ t.getPos().getCoarseValue());
				//System.out.print(lemmaValue+" ");
				//System.out.print("["+posValue+"] ");
							
				if(text.equals("man")||text.equals("jemand")) //search "man/jemand"
//					impersonalPronoun = true;			
					impersonalPronoun = new ImpersonalPronoun(offset);
				if ((posValue.equals("VAFIN")||posValue.equals("VAINF"))&&lemmaValue.equals("werden")) //search "werden"				
					werden = true;	
				if ((posValue.equals("VAFIN")||posValue.equals("VAINF"))&&lemmaValue.equals("sein")&&!text.equals("sein")) { //search "sein"
					sein = true;
					seinObject = new Sein(offset);
				}	
				if (posValue.equals("VVPP")) //search "Partizip II"
					partizipII = new PartizipII(offset);
//					partizipII = true;
				if((posValue.equals("ADJD")&&t.getCoveredText().endsWith("bar"))|| (posValue.equals("ADJD")&&t.getCoveredText().endsWith("lich")))  //search adjective end with "bar" or "lich"
					adjective = new Adjective(offset);
//					adjective = true;			
				if(posValue.equals("PTKZU")||posValue.equals("VVIZU")) //search "zu"
					zu = new Zu(offset);
//					zu = true;
				if (lemmaValue.equals("lassen")||lemmaValue.equals("lässen")) //search "lassen", "lässen" is only for MateLemmatizer
					lassen = new Lassen(offset);
//					lassen = true;			
				if (lemmaValue.equals("sich")) //search "sich"
					sich = new Sich(offset);
//					sich = true;				
				if (lemmaValue.equals("bekommen")||lemmaValue.equals("kriegen")) //search "bekommen/kriegen"
					bekommen = new Bekommen(offset);
//					bekommen = true
				if (text.equals(","))
					comma = new Comma(offset);
				offset++;
			}
			//System.out.println();
			//System.out.println(s.getCoveredText());
//			if((werden||sein)&&partizipII){
//				countTypicalPassive++; //typical passive form with "werden + Partizip Perfekt"
//				System.out.println("[typical passive]" + s.getCoveredText());
//			}
//			if(lassen&&sich){
//				countPassiveWithSichLassen++; //passive form with "sich lassen"
//				System.out.println("[passive with sich lassen]"+s.getCoveredText());
//			}
//			if(sein&&adjective){
//				countPassiveWithAdjective++; //passive form with adjective suffix "bar"
//				System.out.println("[passive with adjektive]" +s.getCoveredText());
//			}
//			if(sein&&zu){
//				countPassiveWithZu++; //passive form with "sein + zu + Infinitiv"
//				System.out.println("[passive with zu]"+s.getCoveredText());
//			}
//			if(bekommen){
//				countPassiveWithBekommen++; //passive form with "bekommen/kriegen"
//				System.out.println("[passive with bekommen]"+s.getCoveredText());
//			}
//			if(impersonalPronoun){
//				countPassiveWithImpersonalPronoun++; //passive form with "man/jemand"
//				System.out.println("[passive with Impersonal Pronoun]"+s.getCoveredText());
//			}
//			// avoid double count typical passiv when a sentence has both sein and werden
			if((werden||sein)&&(partizipII!=null)){
				countTypicalPassive++; //typical passive form with "werden + Partizip Perfekt"
//				System.out.println("[typical passive]" + s.getCoveredText());
			}
			if((lassen != null&& sich != null)&& (Math.abs(lassen.beginOffset-sich.beginOffset)<=distance)){
				countPassiveWithSichLassen++; //typical passive form with "lassen sich "
//				System.out.println("[passive with sich lassen]" + s.getCoveredText()+" "+Math.abs(lassen.beginOffset-sich.beginOffset));
			}
			if((seinObject != null&& adjective != null)&& (Math.abs(seinObject.beginOffset-adjective.beginOffset)<=distance)){
				countPassiveWithAdjective++; //typical passive form with "sein adj "
//				System.out.println("[passive with adjektive]" + s.getCoveredText()+" "+ Math.abs(seinObject.beginOffset-adjective.beginOffset));
			}
			// to void structure exp.: Es ist schön, spazieren gehen zu können false detected as passiv with zu
			if (comma != null) {
				if((seinObject != null&& zu != null)&& (Math.abs(seinObject.beginOffset-zu.beginOffset)<=distance)&&
						(((comma.beginOffset > seinObject.beginOffset)&&(comma.beginOffset > zu.beginOffset))||((comma.beginOffset < seinObject.beginOffset)&&(comma.beginOffset < zu.beginOffset)))){
					countPassiveWithZu++; //typical passive form with "sein zu "
//					System.out.println("[passive with zu]" + s.getCoveredText()+" "+Math.abs(seinObject.beginOffset-zu.beginOffset));
				}
				
			}else{
				if((seinObject != null&& zu != null)&& (Math.abs(seinObject.beginOffset-zu.beginOffset)<=distance)){
					countPassiveWithZu++; //typical passive form with "sein zu "
//					System.out.println("[passive with zu]" + s.getCoveredText()+" "+Math.abs(seinObject.beginOffset-zu.beginOffset));
				}
			}
			
			if((bekommen != null&&partizipII != null) && (Math.abs(bekommen.beginOffset-partizipII.beginOffset)<=distance)){
				countPassiveWithBekommen++; //typical passive form with "sein zu "
//				System.out.println("[passive with bekommen]" + s.getCoveredText()+" "+ Math.abs(bekommen.beginOffset-partizipII.beginOffset));
			}
			if(impersonalPronoun != null){
				countPassiveWithImpersonalPronoun++; //typical passive form with "man/jemand"
//				System.out.println("[passive with Impersonal Pronoun]" + s.getCoveredText());
			}
		}
		passiveFeatures.add(new Feature("FrequencyOfTypicalPassiveSentence", (double)countTypicalPassive/sentences.size(), FeatureType.NUMERIC));
		passiveFeatures.add(new Feature("FrequencyOfPassiveSentenceWithImpersonalPronoun", (double)countPassiveWithImpersonalPronoun/sentences.size(), FeatureType.NUMERIC));
		passiveFeatures.add(new Feature("FrequencyOfPassiveSentenceWithSichLassen", (double)countPassiveWithSichLassen/sentences.size(), FeatureType.NUMERIC));
		passiveFeatures.add(new Feature("FrequencyOfPassiveSetenceWithAdjective", (double)countPassiveWithAdjective/sentences.size(), FeatureType.NUMERIC));
		passiveFeatures.add(new Feature("FrequencyOfPassiveSentenceWithZu",(double)countPassiveWithZu/sentences.size(), FeatureType.NUMERIC));
		passiveFeatures.add(new Feature("FrequencyOfPassiveSentenceWithBekommen",(double)countPassiveWithBekommen/sentences.size(), FeatureType.NUMERIC));
		int sum = countTypicalPassive + countPassiveWithImpersonalPronoun  + countPassiveWithSichLassen + countPassiveWithAdjective + countPassiveWithZu + countPassiveWithBekommen;
		passiveFeatures.add(new Feature("FrequencyOfPassiveSentences",(double)sum/sentences.size(), FeatureType.NUMERIC));
//		System.out.println("num:"+sum);
		return passiveFeatures;
	}
	@Override
	public String getPublicName() {
		return "PassiveSentenceExtractor";
	}
	class ImpersonalPronoun {
		int beginOffset;
		

		public int getDistance() {
			return beginOffset;
		}


		public void setDistance(int distance) {
			this.beginOffset = distance;
		}


		public ImpersonalPronoun(int distance) {
			super();
			this.beginOffset = distance;
		}
	}
	class Werden {
		int beginOffset;
		

		public int getDistance() {
			return beginOffset;
		}


		public void setDistance(int distance) {
			this.beginOffset = distance;
		}


		public Werden(int distance) {
			super();
			this.beginOffset = distance;
		}
	}
	class Sein {
		int beginOffset;
		

		public int getDistance() {
			return beginOffset;
		}


		public void setDistance(int distance) {
			this.beginOffset = distance;
		}


		public Sein(int distance) {
			super();
			this.beginOffset = distance;
		}
	}
	class PartizipII {
		int beginOffset;
		

		public int getDistance() {
			return beginOffset;
		}


		public void setDistance(int distance) {
			this.beginOffset = distance;
		}


		public PartizipII(int distance) {
			super();
			this.beginOffset = distance;
		}
	}
	class Adjective {
		int beginOffset;
		

		public int getDistance() {
			return beginOffset;
		}


		public void setDistance(int distance) {
			this.beginOffset = distance;
		}


		public Adjective(int distance) {
			super();
			this.beginOffset = distance;
		}
	}
	class Zu {
		int beginOffset;
		

		public int getDistance() {
			return beginOffset;
		}


		public void setDistance(int distance) {
			this.beginOffset = distance;
		}


		public Zu(int distance) {
			super();
			this.beginOffset = distance;
		}
	}
	class Lassen {
		int beginOffset;
		

		public int getDistance() {
			return beginOffset;
		}


		public void setDistance(int distance) {
			this.beginOffset = distance;
		}


		public Lassen(int distance) {
			super();
			this.beginOffset = distance;
		}
	}
	class Sich {
		int beginOffset;
		

		public int getDistance() {
			return beginOffset;
		}


		public void setDistance(int distance) {
			this.beginOffset = distance;
		}


		public Sich(int distance) {
			super();
			this.beginOffset = distance;
		}
	}
	class Bekommen {
		int beginOffset;
		

		public int getDistance() {
			return beginOffset;
		}


		public void setDistance(int distance) {
			this.beginOffset = distance;
		}


		public Bekommen(int distance) {
			super();
			this.beginOffset = distance;
		}
	}
	class Comma {
		int beginOffset;
		

		public int getDistance() {
			return beginOffset;
		}


		public void setDistance(int distance) {
			this.beginOffset = distance;
		}


		public Comma(int distance) {
			super();
			this.beginOffset = distance;
		}
	}
}
