package edu.gatech.VRDR.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hl7.fhir.dstu3.model.Address;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.dstu3.model.HumanName;

import edu.gatech.IGCompass.exception.MissingDataNodeException;
import edu.gatech.IGCompass.exception.MissingInformationException;
import edu.gatech.IGCompass.exception.PathFormatException;
import edu.gatech.IGCompass.exception.WrongTypeException;
import edu.gatech.IGCompass.mapper.util.CommonUtil;
import edu.gatech.IGCompass.model.IGMapDocument;
import edu.gatech.VRDR.model.Decedent;

public class DecedentMapper implements IGMapper<Decedent> {

	@Override
	public Decedent map(IGMapDocument document,String resourceRootPath) throws MissingInformationException, WrongTypeException, MissingDataNodeException, PathFormatException{
		Decedent resource = new Decedent();
		HumanNameMapper humanNameMapper = new HumanNameMapper();
		AddressMapper addressMapper = new AddressMapper();
		CodeableConceptMapper ccMapper = new CodeableConceptMapper();
		HumanName humanName = null;
		Address address = null;
		String gender = null;
		String birthDate = null;
		CodeableConcept maritalStatus = null;
		CodeableConcept race = null;
		CodeableConcept ethnicity = null;
		CodeableConcept birthSex = null;
		Address birthPlace = null;
		humanName = humanNameMapper.map(document, "Decedent.name");
		address = addressMapper.map(document, "Decedent.address");
		gender = CommonUtil.findValueFromIGKey(document,"Decedent.gender");
		birthDate = CommonUtil.findValueFromIGKey(document,"Decedent.birthDate");
		maritalStatus = ccMapper.map(document, "Decedent.maritalStatus");
		race = ccMapper.map(document, "Decedent.extension.race");
		ethnicity = ccMapper.map(document, "Decedent.extension.ethnicity");
		birthSex = ccMapper.map(document, "Decedent.extension.birthSex");
		birthPlace = addressMapper.map(document, "Decedent.extension.birthPlace");
		if(humanName != null) {
			resource.addName(humanName); //TODO: Make Decedent.name a multiplex
		}
		if(address != null) {
			resource.addAddress(address); //TODO: Make Decedent.address a multiplex
		}
		if(gender != null) {
			resource.setGender(AdministrativeGender.valueOf(gender));
		}
		if(birthDate != null) {
			try {
				resource.setBirthDate(new SimpleDateFormat("yyy-mm-dd").parse(birthDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(maritalStatus != null) {
			resource.setMaritalStatus(maritalStatus);
		}
		if(race != null) {
			resource.setRace(race.getCodingFirstRep().getCode(), "", "");
		}	
		if(ethnicity != null) {
			resource.setEthnicity(ethnicity.getCodingFirstRep().getCode(), "", "");
		}
		if(birthSex != null) {
			resource.setBirthSex(birthSex.getCodingFirstRep().getCode(), birthSex.getCodingFirstRep().getDisplay());
		}
		if(birthPlace != null) {
			resource.setBirthPlace(birthPlace);
		}
		return resource;
	}
}