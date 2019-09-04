package edu.gatech.VRDR.mapper;

import org.hl7.fhir.dstu3.model.CodeableConcept;

import edu.gatech.IGFlatFhir.exception.MissingDataNodeException;
import edu.gatech.IGFlatFhir.exception.MissingKeyException;
import edu.gatech.IGFlatFhir.exception.PathFormatException;
import edu.gatech.IGFlatFhir.exception.WrongTypeException;
import edu.gatech.IGFlatFhir.model.IGMapDocument;
import edu.gatech.VRDR.model.AutopsyPerformedIndicator;

public class AutopsyPerformedIndicatorMapper implements IGMapper<AutopsyPerformedIndicator> {

	@Override
	public AutopsyPerformedIndicator map(IGMapDocument document,String resourceRootPath) {
		CodeableConcept concept = null;
		CodeableConceptMapper ccMapper = new CodeableConceptMapper();
		try {
			concept = ccMapper.map(document,"Autopsy Performed Indicator.valueCodeableConcept");
		} catch (WrongTypeException | MissingDataNodeException | PathFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(concept != null) {
			return new AutopsyPerformedIndicator(concept);
		}
		return null;
	}

}
