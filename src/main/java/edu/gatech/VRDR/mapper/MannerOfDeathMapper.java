package edu.gatech.VRDR.mapper;

import org.hl7.fhir.dstu3.model.CodeableConcept;

import edu.gatech.IGCompass.exception.MissingDataNodeException;
import edu.gatech.IGCompass.exception.MissingInformationException;
import edu.gatech.IGCompass.exception.PathFormatException;
import edu.gatech.IGCompass.exception.WrongTypeException;
import edu.gatech.IGCompass.model.IGMapDocument;
import edu.gatech.VRDR.model.MannerOfDeath;
import edu.gatech.common.mapper.CodeableConceptMapper;
import edu.gatech.common.mapper.IGMapper;

public class MannerOfDeathMapper implements IGMapper<MannerOfDeath> {

	@Override
	public MannerOfDeath map(IGMapDocument document,String resourceRootPath) throws MissingInformationException {
		CodeableConcept value = null;
		CodeableConceptMapper ccMapper = new CodeableConceptMapper();
		try {
			value = ccMapper.map(document,"Manner of Death.valueCodeableConcept");
		} catch (WrongTypeException | MissingDataNodeException | PathFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(value != null) {
			MannerOfDeath mannerOfDeath = new MannerOfDeath();
			mannerOfDeath.setValue(value);
			return mannerOfDeath;
		}
		return null;
	}
}