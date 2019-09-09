package edu.gatech.VRDR.mapper;

import org.hl7.fhir.dstu3.model.Address;
import org.hl7.fhir.dstu3.model.CodeableConcept;

import edu.gatech.IGCompass.exception.MissingDataNodeException;
import edu.gatech.IGCompass.exception.MissingInformationException;
import edu.gatech.IGCompass.exception.PathFormatException;
import edu.gatech.IGCompass.exception.WrongTypeException;
import edu.gatech.IGCompass.model.IGMapDocument;
import edu.gatech.VRDR.model.InjuryLocation;

public class InjuryLocationMapper implements IGMapper<InjuryLocation> {

	@Override
	public InjuryLocation map(IGMapDocument document,String resourceRootPath) throws MissingInformationException, WrongTypeException, MissingDataNodeException, PathFormatException {
		// TODO Auto-generated method stub
		CodeableConceptMapper ccMapper = new CodeableConceptMapper();
		AddressMapper addressMapper = new AddressMapper();
		CodeableConcept type = null;
		Address address = null;
		CodeableConcept physicalType = null;
		type = ccMapper.map(document,"Injury Location.type");
		address = addressMapper.map(document, "Injury Location.address");
		physicalType = ccMapper.map(document,"Injury Location.physicalType");
		if(type != null && address != null && physicalType != null) {
			InjuryLocation injuryLocation = new InjuryLocation();
			injuryLocation.setType(type);
			injuryLocation.setAddress(address);
			injuryLocation.setPhysicalType(physicalType);
		}
		return null;
	}

}
