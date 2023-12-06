package de.fernunihagen.calpata.nli.features.basic;

import java.util.Set;

import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.lift.api.Feature;
import org.lift.api.LiftFeatureExtrationException;

import de.fernunihagen.calpata.nli.featureSettings.FEL_GenericStructureCounter;
import de.fernunihagen.calpata.nli.featureSettings.FeatureExtractor_ImplBase;

/**
 * Extracts the total number of characters.
 */
@TypeCapability(inputs = { "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token"})
public class FE_NrDiscourseReferent 
	extends FeatureExtractor_ImplBase
{

private FEL_GenericStructureCounter counter;
	
	public FE_NrDiscourseReferent() {
		counter = new FEL_GenericStructureCounter("DiscourseReferent");
	}
	@Override
	public Set<Feature> extract(JCas jcas) throws LiftFeatureExtrationException {
		
		return counter.extract(jcas);
		
	}

	@Override
	public String getPublicName() {
		return counter.getPublicName();
	}
	
	@Override
	public String getInternalName() {
		return counter.getInternalName();
	}
	
}
