package de.fernunihagen.calpata.nli.structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.lift.api.StructureExtractor;
import org.lift.type.Structure;

import de.tudarmstadt.ukp.dkpro.core.api.anomaly.type.GrammarAnomaly;

public class SE_LanguageTool2 extends StructureExtractor{
	
	public static final String noError1 = "^.*is a common British expression. Consider using expressions more common to American English\\.";
	public static final Pattern noErrorP1 = Pattern.compile(noError1);
	public static final String noError2 = "^An apostrophe may be missing.";
	public static final Pattern noErrorP2 = Pattern.compile(noError2);
	public static final String noError3 = "^Possible spelling mistake.*is British English";
	public static final Pattern noErrorP3 = Pattern.compile(noError3);
	public static final String noError4 = "^Three successive sentences begin with the same word. Consider rewording the sentence or use a thesaurus to find a synonym.";
	public static final Pattern noErrorP4 = Pattern.compile(noError4);
	public static final String noError5 = "^Often this adverbial phrase is redundant. Consider using <suggestion>first</suggestion>.";
	public static final Pattern noErrorP5 = Pattern.compile(noError5);
	public static final String noError6 = "^Use a smart opening quote here.";
	public static final Pattern noErrorP6 = Pattern.compile(noError6);
	public static final String noError7 = "^Use a smart closing quote here.";
	public static final Pattern noErrorP7 = Pattern.compile(noError7);
	
	public static final String spellingMistake1 = "^Possible spelling mistake. Did you mean.*the (plural|past tense|comparative) form of the (noun|verb|adjective) '.*'?";
	public static final Pattern spellingMistakeP1 = Pattern.compile(spellingMistake1);
	public static final String spellingMistake2 = "^Possible spelling mistake found";
	public static final Pattern spellingMistakeP2 = Pattern.compile(spellingMistake2);
	
	public static final String punc1 = "^.*is missing a hyphen.";
	public static final Pattern puncP1 = Pattern.compile(punc1);
	public static final String punc2 = "^A comma should precede the tag question (i.e., the yes/no-question at the end).";
	public static final Pattern puncP2 = Pattern.compile(punc2);
	public static final String punc3 = "^A possessive apostrophe is missing.";
	public static final Pattern puncP3 = Pattern.compile(punc3);
	public static final String punc4 = "^Add a space between sentences";
	public static final Pattern puncP4 = Pattern.compile(punc4);
	public static final String punc5 = "^Did you forget a comma after a conjunctive/linking adverb?";
	public static final Pattern puncP5 = Pattern.compile(punc5);
	public static final String punc6 = "^Don't put a space after the opening parenthesis.";
	public static final Pattern puncP6 = Pattern.compile(punc6);
	public static final String punc7 = "^Don't put a space before the closing parenthesis.";
	public static final Pattern puncP7 = Pattern.compile(punc7);
	public static final String punc8 = "^Don't put a space before the full stop.";
	public static final Pattern puncP8 = Pattern.compile(punc8);
	public static final String punc9 = "^Don't put a space on both sides of a quote symbol.";
	public static final Pattern puncP9 = Pattern.compile(punc9);
	public static final String punc10 = "^If a new sentence starts here, add a space and start with an uppercase letter.";
	public static final Pattern puncP10 = Pattern.compile(punc10);
	public static final String punc11 = "^It appears that a comma is missing.";
	public static final Pattern puncP11 = Pattern.compile(punc11);
	public static final String punc12 = "^It appears that a hyphen is missing.*";
	public static final Pattern puncP12 = Pattern.compile(punc12);
	public static final String punc13 = "^It seems that a possessive apostrophe is missing.";
	public static final Pattern puncP13 = Pattern.compile(punc13);
	public static final String punc14 = "^It seems that hyphens are missing.";
	public static final Pattern puncP14 = Pattern.compile(punc14);
	public static final String punc15 = "^It seems that one apostrophe is unnecessary.";
	public static final Pattern puncP15 = Pattern.compile(punc15);
	public static final String punc16 = "^Please add a punctuation mark at the end of paragraph.";
	public static final Pattern puncP16 = Pattern.compile(punc16);
	public static final String punc17 = "^It seems that one apostrophe is unnecessary.";
	public static final Pattern puncP17 = Pattern.compile(punc17);
	public static final String punc18 = "^This word is normally spelled with a hyphen.";
	public static final Pattern puncP18 = Pattern.compile(punc18);
	public static final String punc19 = "^Unpaired symbol: '(\\(|\\))' seems to be missing";
	public static final Pattern puncP19 = Pattern.compile(punc19);
	public static final String punc20 = "^Put a space after the comma.*";
	public static final Pattern puncP20 = Pattern.compile(punc20);
	public static final String punc21 = "^Two consecutive (commas|dots)";	
	public static final Pattern puncP21 = Pattern.compile(punc21);
	public static final String punc22 = "^Use a comma before.*if it connects two independent clauses \\(unless they are closely connected and short\\).";
	public static final Pattern puncP22 = Pattern.compile(punc22);
	public static final String punc23 = "^When a number forms part of an adjectival compound, use a hyphen.*";
	public static final Pattern puncP23 = Pattern.compile(punc23);
	public static final String punc24 = "^The currency mark is usually put at the beginning of the number.*";
	public static final Pattern puncP24 = Pattern.compile(punc24);
	public static final String punc25 = "^.*at the beginning of a sentence usually requires a 2nd clause. Maybe a comma, question or exclamation mark is missing, or the sentence is incomplete and should be joined with the following sentence.";
	public static final Pattern puncP25 = Pattern.compile(punc25);
	public static final String punc26 = "^Consider adding a comma here.*";
	public static final Pattern puncP26 = Pattern.compile(punc26);
	public static final String punc27 = "^Possible typo: you repeated a whitespace"; //new after feedback of Stefan
	public static final Pattern puncP27 = Pattern.compile(punc27);
	
	
	public static final String typo = "^Possible typo. Did you mean.*";
	public static final Pattern typoP = Pattern.compile(typo);
	
	public static final String collocation = ".*collocation.*";
	public static final Pattern collocationP = Pattern.compile(collocation);
	
	
	public static final String grammarArticle = "^('These'|'This'|'Those') does not seem to match the (singular|plural) noun.*";
	public static final Pattern grammarArticleP = Pattern.compile(grammarArticle);
	
	public static final String grammarPreposition = "^(?!.*This looks like a collocation error).*preposition.*";
	public static final Pattern grammarPrepositionP = Pattern.compile(grammarPreposition);
	
	public static final String grammarVerb1 = "^A verb seems to be missing. Did you mean.*";
	public static final Pattern grammarVerbP1 = Pattern.compile(grammarVerb1);
	public static final String grammarVerb2 = "^After ('a'|'A'|'the'|'The'), do not use a verb.*";
	public static final Pattern grammarVerbP2 = Pattern.compile(grammarVerb2);
	public static final String grammarVerb3 = "^After ('it'|'It'), use the third-person verb form.";
	public static final Pattern grammarVerbP3 = Pattern.compile(grammarVerb3);
	public static final String grammarVerb4 = "^After 'must', the verb is used without 'to'.*";
	public static final Pattern grammarVerbP4 = Pattern.compile(grammarVerb4);
	public static final String grammarVerb5 = "^Consider using third-person verb forms for singular and mass nouns.*";
	public static final Pattern grammarVerbP5 = Pattern.compile(grammarVerb5);
	public static final String grammarVerb6 = "^Did you mean <suggestion>have</suggestion>? As 'do' is already inflected, the verb cannot also be inflected.";
	public static final Pattern grammarVerbP6 = Pattern.compile(grammarVerb6);
	public static final String grammarVerb7 = "^Did you mean the verb.*instead of the noun.*";
	public static final Pattern grammarVerbP7 = Pattern.compile(grammarVerb7);
	public static final String grammarVerb8 = "^(?!.*instead of the noun)Did you mean the verb.*";
	public static final Pattern grammarVerbP8 = Pattern.compile(grammarVerb8);
	public static final String grammarVerb9 = "^Do not use 's with a verb.*";
	public static final Pattern grammarVerbP9 = Pattern.compile(grammarVerb9);
	public static final String grammarVerb10 = "^If '(learn|want)' is used as a verb, it usually requires the infinitive.*";
	public static final Pattern grammarVerbP10 = Pattern.compile(grammarVerb10);
	public static final String grammarVerb11 = "^If 'people' is plural here, don't use the third-person singular verb.";
	public static final Pattern grammarVerbP11 = Pattern.compile(grammarVerb11);
	public static final String grammarVerb12 = "^^In the grammatical structure 'pronoun \\+ be/get \\+ used \\+ to \\+ verb', the verb 'used' is used with the gerund.*";
	public static final Pattern grammarVerbP12 = Pattern.compile(grammarVerb12);
	public static final String grammarVerb13 = "^It seems that a verb is missing.*";
	public static final Pattern grammarVerbP13 = Pattern.compile(grammarVerb13);
	public static final String grammarVerb14 = "^It seems that the correct verb form here is.*";
	public static final Pattern grammarVerbP14 = Pattern.compile(grammarVerb14);
	public static final String grammarVerb15 = "^Possible agreement error - use third-person verb forms for singular and mass nouns.*";
	public static final Pattern grammarVerbP15 = Pattern.compile(grammarVerb15);
	public static final String grammarVerb16 = "^The auxiliary verb 'do' requires the base form of the verb.*";
	public static final Pattern grammarVerbP16 = Pattern.compile(grammarVerb16);
	public static final String grammarVerb17 = "^The modal verb ('could'|'may'|'might'|'must'|'should'|'will'|'won'|'would') requires the verb's base form.";
	public static final Pattern grammarVerbP17 = Pattern.compile(grammarVerb17);
	public static final String grammarVerb18 = "^The past tense and past participle of the verb \"to split\" is <suggestion>split</suggestion>.*";
	public static final Pattern grammarVerbP18 = Pattern.compile(grammarVerb18);
	public static final String grammarVerb19 = "^The preposition 'to' is required in front of the verb 'see'.";
	public static final Pattern grammarVerbP19 = Pattern.compile(grammarVerb19);
	public static final String grammarVerb20 = "^The pronoun '.*' (is usually used with|muss be used with|requires) a third-person or a past tense verb";
	public static final Pattern grammarVerbP20 = Pattern.compile(grammarVerb20);
	public static final String grammarVerb21 = "^The pronoun '.*' must be used with a non-third-person form of a verb";
	public static final Pattern grammarVerbP21 = Pattern.compile(grammarVerb21);
	public static final String grammarVerb22 = "^The verb '' is used with an infinitive: <suggestion>to do</suggestion> or <suggestion>do</suggestion>.";
	public static final Pattern grammarVerbP22 = Pattern.compile(grammarVerb22);
	public static final String grammarVerb23 = "^The verb '.*' is (plural|singular). Did you mean.*";
	public static final Pattern grammarVerbP23 = Pattern.compile(grammarVerb23);
	public static final String grammarVerb24 = "^The verb 'found' is both the base form of a verb and the past tense of a different verb. Make sure that 'found' is correct.";
	public static final Pattern grammarVerbP24 = Pattern.compile(grammarVerb24);
	public static final String grammarVerb25 = "^The verb '.*' is used with the gerund form.*";
	public static final Pattern grammarVerbP25 = Pattern.compile(grammarVerb25);
	public static final String grammarVerb26 = "^The verb '.*' can be stative.*";
	public static final Pattern grammarVerbP26 = Pattern.compile(grammarVerb26);
	public static final String grammarVerb27 = "^The verb '.*' is used with an infinitive.*";
	public static final Pattern grammarVerbP27 = Pattern.compile(grammarVerb27);
	public static final String grammarVerb28 = "^The verb 'to depend' requires the preposition '(up)on'.*";
	public static final Pattern grammarVerbP28 = Pattern.compile(grammarVerb28);
	public static final String grammarVerb29 = "^The verb '.*' requires a preposition.*";
	public static final Pattern grammarVerbP29 = Pattern.compile(grammarVerb29);
	public static final String grammarVerb30 = "^The verb \".*\" needs to be in the to-infinitive form.";
	public static final Pattern grammarVerbP30 = Pattern.compile(grammarVerb30);
	public static final String grammarVerb31 = "^The verb after \"to\" should be in the base form.*";
	public static final Pattern grammarVerbP31 = Pattern.compile(grammarVerb31);
	public static final String grammarVerb32 = "^The verb form seems incorrect.*";
	public static final Pattern grammarVerbP32 = Pattern.compile(grammarVerb32);
	public static final String grammarVerb33 = "^Two modal verbs connected. Did you mean.*";
	public static final Pattern grammarVerbP33 = Pattern.compile(grammarVerb33);
	public static final String grammarVerb34 = "^You used a negation after a negated contracted verb. Did you mean simply.*";
	public static final Pattern grammarVerbP34 = Pattern.compile(grammarVerb34);
	public static final String grammarVerb35 = "You used a past participle without using any required verb ('be' or 'have').";
	public static final Pattern grammarVerbP35 = Pattern.compile(grammarVerb35);
	public static final String grammarVerb36 = "^Consider using either the past participle.*or the present participle.*here.";
	public static final Pattern grammarVerbP36 = Pattern.compile(grammarVerb36);	
	public static final String grammarVerb37 = "^Consider using the past participle.*";
	public static final Pattern grammarVerbP37 = Pattern.compile(grammarVerb37);
	public static final String grammarVerb38 = "^Use past participle here.*";
	public static final Pattern grammarVerbP38 = Pattern.compile(grammarVerb38);
	
	@Override
	public String getPublicStructureName() {
		return "LanguageTool";
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		
		Collection<GrammarAnomaly> gas = JCasUtil.select(aJCas, GrammarAnomaly.class);
		
		for (GrammarAnomaly g : gas) {
			int begin = g.getBegin();
			String description = g.getDescription();
			Matcher noErrorM1 = noErrorP1.matcher(description);
			Matcher noErrorM2 = noErrorP2.matcher(description);
			Matcher noErrorM3 = noErrorP3.matcher(description);
			Matcher noErrorM4 = noErrorP4.matcher(description);
			Matcher noErrorM5 = noErrorP5.matcher(description);
			Matcher noErrorM6 = noErrorP6.matcher(description);
			Matcher noErrorM7 = noErrorP7.matcher(description);
			
			Matcher spellingMistakeM1 = spellingMistakeP1.matcher(description);
			Matcher spellingMistakeM2 = spellingMistakeP2.matcher(description);
			
			Matcher puncM1 = puncP1.matcher(description);
			Matcher puncM2 = puncP2.matcher(description);
			Matcher puncM3 = puncP3.matcher(description);
			Matcher puncM4 = puncP4.matcher(description);
			Matcher puncM5 = puncP5.matcher(description);
			Matcher puncM6 = puncP6.matcher(description);
			Matcher puncM7 = puncP7.matcher(description);
			Matcher puncM8 = puncP8.matcher(description);
			Matcher puncM9 = puncP9.matcher(description);
			Matcher puncM10 = puncP10.matcher(description);
			Matcher puncM11 = puncP11.matcher(description);
			Matcher puncM12 = puncP12.matcher(description);
			Matcher puncM13 = puncP13.matcher(description);
			Matcher puncM14 = puncP14.matcher(description);
			Matcher puncM15 = puncP15.matcher(description);
			Matcher puncM16 = puncP16.matcher(description);
			Matcher puncM17 = puncP17.matcher(description);
			Matcher puncM18 = puncP18.matcher(description);
			Matcher puncM19 = puncP19.matcher(description);
			Matcher puncM20 = puncP20.matcher(description);
			Matcher puncM21 = puncP21.matcher(description);
			Matcher puncM22 = puncP22.matcher(description);
			Matcher puncM23 = puncP23.matcher(description);
			Matcher puncM24 = puncP24.matcher(description);
			Matcher puncM25 = puncP25.matcher(description);
			Matcher puncM26 = puncP26.matcher(description);
			Matcher puncM27 = puncP27.matcher(description);
			
			Matcher typoM = typoP.matcher(description);
			
			Matcher collocationM = collocationP.matcher(description);
			
			Matcher grammarArticleM = grammarArticleP.matcher(description);
			
			Matcher grammarPrepositionM = grammarPrepositionP.matcher(description);
			
			Matcher grammarVerbM1 = grammarVerbP1.matcher(description);
			Matcher grammarVerbM2 = grammarVerbP2.matcher(description);
			Matcher grammarVerbM3 = grammarVerbP3.matcher(description);
			Matcher grammarVerbM4 = grammarVerbP4.matcher(description);
			Matcher grammarVerbM5 = grammarVerbP5.matcher(description);
			Matcher grammarVerbM6 = grammarVerbP6.matcher(description);
			Matcher grammarVerbM7 = grammarVerbP7.matcher(description);
			Matcher grammarVerbM8 = grammarVerbP8.matcher(description);
			Matcher grammarVerbM9 = grammarVerbP9.matcher(description);
			Matcher grammarVerbM10 = grammarVerbP10.matcher(description);
			Matcher grammarVerbM11 = grammarVerbP11.matcher(description);
			Matcher grammarVerbM12 = grammarVerbP12.matcher(description);
			Matcher grammarVerbM13 = grammarVerbP13.matcher(description);
			Matcher grammarVerbM14 = grammarVerbP14.matcher(description);
			Matcher grammarVerbM15 = grammarVerbP15.matcher(description);
			Matcher grammarVerbM16 = grammarVerbP16.matcher(description);
			Matcher grammarVerbM17 = grammarVerbP17.matcher(description);
			Matcher grammarVerbM18 = grammarVerbP18.matcher(description);
			Matcher grammarVerbM19 = grammarVerbP19.matcher(description);
			Matcher grammarVerbM20 = grammarVerbP20.matcher(description);
			Matcher grammarVerbM21 = grammarVerbP21.matcher(description);
			Matcher grammarVerbM22 = grammarVerbP22.matcher(description);
			Matcher grammarVerbM23 = grammarVerbP23.matcher(description);
			Matcher grammarVerbM24 = grammarVerbP24.matcher(description);
			Matcher grammarVerbM25 = grammarVerbP25.matcher(description);
			Matcher grammarVerbM26 = grammarVerbP26.matcher(description);
			Matcher grammarVerbM27 = grammarVerbP27.matcher(description);
			Matcher grammarVerbM28 = grammarVerbP28.matcher(description);
			Matcher grammarVerbM29 = grammarVerbP29.matcher(description);
			Matcher grammarVerbM30 = grammarVerbP30.matcher(description);
			Matcher grammarVerbM31 = grammarVerbP31.matcher(description);
			Matcher grammarVerbM32 = grammarVerbP32.matcher(description);
			Matcher grammarVerbM33 = grammarVerbP33.matcher(description);
			Matcher grammarVerbM34 = grammarVerbP34.matcher(description);
			Matcher grammarVerbM35 = grammarVerbP35.matcher(description);
			Matcher grammarVerbM36 = grammarVerbP36.matcher(description);
			Matcher grammarVerbM37 = grammarVerbP37.matcher(description);
			Matcher grammarVerbM38 = grammarVerbP38.matcher(description);
			
			
			if(noErrorM1.find()||noErrorM2.find()||noErrorM3.find()||noErrorM4.find()||noErrorM5.find()||noErrorM6.find()||noErrorM7.find()) {
//				Structure s = new Structure(aJCas);
//				s.setBegin(begin);
//				s.setEnd(g.getEnd());
//				s.setName("no error");
//				s.addToIndexes();
			}else if (spellingMistakeM1.find()||spellingMistakeM2.find()||typoM.find()){
				Structure s = new Structure(aJCas);
				s.setBegin(begin);
				s.setEnd(g.getEnd());
				s.setName("spelling mistake");
				s.addToIndexes();
			}else if(puncM1.find()||puncM2.find()||puncM3.find()||puncM4.find()||puncM5.find()||puncM6.find()||puncM7.find()||puncM8.find()
					||puncM9.find()||puncM10.find()||puncM11.find()||puncM12.find()||puncM13.find()||puncM14.find()||puncM15.find()
					||puncM16.find()||puncM17.find()||puncM16.find()||puncM19.find()||puncM20.find()||puncM21.find()||puncM22.find()
					||puncM23.find()||puncM24.find()||puncM25.find()||puncM26.find()||puncM27.find()) {
				Structure s = new Structure(aJCas);
				s.setBegin(begin);
				s.setEnd(g.getEnd());
				s.setName("punctuation error");
				s.addToIndexes();
			}else if (collocationM.find()) {
				Structure s = new Structure(aJCas);
				s.setBegin(begin);
				s.setEnd(g.getEnd());
				s.setName("collocation error");
				s.addToIndexes();
			}else if(grammarArticleM.find()) {
					Structure s = new Structure(aJCas);
					s.setBegin(begin);
					s.setEnd(g.getEnd());
					s.setName("grammatical- article");
					s.addToIndexes();
			}else if (grammarPrepositionM.find()) {
					Structure s = new Structure(aJCas);
					s.setBegin(begin);
					s.setEnd(g.getEnd());
					s.setName("grammatical- proposition");
					s.addToIndexes();
			}else if (grammarVerbM1.find()||grammarVerbM2.find()||grammarVerbM3.find()||grammarVerbM4.find()||grammarVerbM5.find()
						||grammarVerbM6.find()||grammarVerbM7.find()||grammarVerbM8.find()||grammarVerbM9.find()||grammarVerbM10.find()
						||grammarVerbM11.find()||grammarVerbM12.find()||grammarVerbM13.find()||grammarVerbM14.find()||grammarVerbM15.find()
						||grammarVerbM16.find()||grammarVerbM17.find()||grammarVerbM18.find()||grammarVerbM19.find()||grammarVerbM20.find()
						||grammarVerbM21.find()||grammarVerbM22.find()||grammarVerbM23.find()||grammarVerbM24.find()||grammarVerbM25.find()
						||grammarVerbM26.find()||grammarVerbM27.find()||grammarVerbM28.find()||grammarVerbM29.find()||grammarVerbM30.find()
						||grammarVerbM31.find()||grammarVerbM32.find()||grammarVerbM33.find()||grammarVerbM34.find()||grammarVerbM35.find()
						||grammarVerbM36.find()||grammarVerbM37.find()||grammarVerbM38.find()) {
					Structure s = new Structure(aJCas);
					s.setBegin(begin);
					s.setEnd(g.getEnd());
					s.setName("grammatical- verb");
					s.addToIndexes();
			}else {
					Structure s = new Structure(aJCas);
					s.setBegin(begin);
					s.setEnd(g.getEnd());
					s.setName("other grammatical error");
					s.addToIndexes();								
			}				
		}
	}
	
}
