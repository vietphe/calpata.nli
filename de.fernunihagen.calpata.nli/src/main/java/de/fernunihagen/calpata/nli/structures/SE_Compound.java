package de.fernunihagen.calpata.nli.structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.lift.api.StructureExtractor;
import org.lift.type.Structure;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Compound;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

/**
 * Annotation for Germans compounds
 * 
 * @author Viet Phe
 */
public class SE_Compound extends StructureExtractor{

	@Override
	public String getPublicStructureName() {
		return "Compound";
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		Collection<Compound> compounds = JCasUtil.select(aJCas, Compound.class);
		List<Integer> nomens = new ArrayList<>();
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		for (Token token : tokens) {
			if (token.getPos().getCoarseValue() != null && token.getPos().getCoarseValue().equals("NOUN"))
			nomens.add(token.getBegin());
		}
		for (Compound compound : compounds) {
			
			if (nomens.contains(compound.getBegin())) {
				Structure s = new Structure(aJCas, compound.getBegin(), compound.getEnd());
				s.setName(getPublicStructureName());
				s.addToIndexes();
				System.out.println(compound.getCoveredText());
			}
			
		}
	}


}
