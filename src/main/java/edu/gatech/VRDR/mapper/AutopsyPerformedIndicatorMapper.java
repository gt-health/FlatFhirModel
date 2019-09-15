package edu.gatech.VRDR.mapper;

import org.hl7.fhir.dstu3.model.CodeableConcept;

import edu.gatech.IGCompass.exception.MissingDataNodeException;
import edu.gatech.IGCompass.exception.PathFormatException;
import edu.gatech.IGCompass.exception.WrongTypeException;
import edu.gatech.IGCompass.model.IGMapDocument;
import edu.gatech.VRDR.model.AutopsyPerformedIndicator;

public class AutopsyPerformedIndicatorMapper implements IGMapper<AutopsyPerformedIndicator> {

	@Override
	public AutopsyPerformedIndicator map(IGMapDocument document,String resourceRootPath) {
		CodeableConcept autopsyPerformedConcept = null;
		CodeableConcept resultsAvailableConcept = null;
		CodeableConceptMapper ccMapper = new CodeableConceptMapper();
		
		try {
			autopsyPerformedConcept = ccMapper.map(document,"Autopsy Performed Indicator.valueCodeableConcept");
			resultsAvailableConcept = ccMapper.map(document,"Autopsy Performed Indicator.component.valueCodeableConcept");
		} catch (WrongTypeException | MissingDataNodeException | PathFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(autopsyPerformedConcept != null && resultsAvailableConcept != null) {
			return new AutopsyPerformedIndicator(autopsyPerformedConcept,resultsAvailableConcept);
		}
		return null;
	}

}
