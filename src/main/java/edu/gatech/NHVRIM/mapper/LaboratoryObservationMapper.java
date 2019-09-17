package edu.gatech.NHVRIM.mapper;

import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Quantity;

import edu.gatech.IGCompass.exception.MissingDataNodeException;
import edu.gatech.IGCompass.exception.MissingInformationException;
import edu.gatech.IGCompass.exception.MissingKeyException;
import edu.gatech.IGCompass.exception.PathFormatException;
import edu.gatech.IGCompass.exception.WrongTypeException;
import edu.gatech.IGCompass.model.IGMapDocument;
import edu.gatech.common.mapper.CodeableConceptMapper;
import edu.gatech.common.mapper.IGMapper;

public class LaboratoryObservationMapper implements IGMapper<Observation>{

	@Override
	public Observation map(IGMapDocument document, String resourceRootPath) throws MissingInformationException,
			WrongTypeException, MissingDataNodeException, PathFormatException, MissingKeyException {
		Observation resource = new Observation();
		CodeableConceptMapper ccMapper = new CodeableConceptMapper();
		
		CodeableConcept code = null;
		String methodText = null;
		CodeableConcept methodCode = null;
		String valueText  = null;
		Quantity valueQuantity = null;
		String comment = null;
		// TODO Auto-generated method stub
		return null;
	}
	
}