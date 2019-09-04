package edu.gatech.VRDR.mapper;

import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.DateTimeType;

import edu.gatech.IGFlatFhir.exception.MissingDataNodeException;
import edu.gatech.IGFlatFhir.exception.MissingInformationException;
import edu.gatech.IGFlatFhir.exception.MissingKeyException;
import edu.gatech.IGFlatFhir.exception.PathFormatException;
import edu.gatech.IGFlatFhir.exception.WrongTypeException;
import edu.gatech.IGFlatFhir.model.IGMapDocument;
import edu.gatech.VRDR.model.CauseOfDeathCondition;

public class CauseOfDeathMapper implements IGMapper<CauseOfDeathCondition> {

	@Override
	public CauseOfDeathCondition map(IGMapDocument document,String resourceRootPath) throws MissingInformationException {
		// TODO Auto-generated method stub
		CodeableConceptMapper ccMapper = new CodeableConceptMapper(); 
		CodeableConcept concept = null;
		String onset = null;
		try {
			concept = ccMapper.map(document, "Cause Of Death Condition.code");
			onset = document.findValueFromIGKey("Cause Of Death Condition.onset");
		} catch (WrongTypeException | MissingDataNodeException | PathFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MissingKeyException e) {
		}
		if(concept != null && onset != null) {
			CauseOfDeathCondition causeOfDeathCondition = new CauseOfDeathCondition();
			causeOfDeathCondition.setCode(concept);
			causeOfDeathCondition.setOnset(new DateTimeType(onset));
			return causeOfDeathCondition;
		}
		return null;
	}
}