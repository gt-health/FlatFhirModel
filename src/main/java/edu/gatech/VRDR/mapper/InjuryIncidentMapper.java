package edu.gatech.VRDR.mapper;

import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.DateTimeType;
import org.hl7.fhir.dstu3.model.StringType;

import edu.gatech.IGCompass.exception.MissingDataNodeException;
import edu.gatech.IGCompass.exception.MissingInformationException;
import edu.gatech.IGCompass.exception.PathFormatException;
import edu.gatech.IGCompass.exception.WrongTypeException;
import edu.gatech.IGCompass.mapper.util.CommonUtil;
import edu.gatech.IGCompass.model.IGMapDocument;
import edu.gatech.VRDR.model.InjuryIncident;

public class InjuryIncidentMapper implements IGMapper<InjuryIncident> {

	@Override
	public InjuryIncident map(IGMapDocument document,String resourceRootPath) throws MissingInformationException, WrongTypeException, MissingDataNodeException, PathFormatException {
		InjuryIncident resource = new InjuryIncident();
		String value = null;
		String effectiveDateTime = null;
		CodeableConceptMapper ccMapper = new CodeableConceptMapper();
		String component1 = null;
		CodeableConcept component2CC = null;
		CodeableConcept component3CC = null;
		value = CommonUtil.findValueFromIGKey(document,"Injury Incident.valueString");
		effectiveDateTime = CommonUtil.findValueFromIGKey(document,"Injury Incident.effectiveDateTime");
		component1 = CommonUtil.findValueFromIGKey(document, "Injury Incident.component1.valueString");
		component2CC = ccMapper.map(document, "Injury Incident.component2.valueCodeableConcept");
		component3CC = ccMapper.map(document, "Injury Incident.component3.valueCodeableConcept");
		if(value == null) {
			throw new MissingInformationException("Injury Incident.valueString");
		}
		if(effectiveDateTime != null) {
			resource.setEffective(new DateTimeType(effectiveDateTime));
		}
		resource.setValue(new StringType(value));
		if(component1 == null) {
			component1 = "unknown";
		}
		resource.addPlaceOfInjuryComponent(component1);
		if(component2CC == null) {
			component2CC = edu.gatech.VRDR.model.util.CommonUtil.unknownCode;
		}
		resource.addInjuredAtWorkBooleanComponent(component2CC);
		
		if(component3CC == null) {
			component3CC = edu.gatech.VRDR.model.util.CommonUtil.unknownCode;
		}
		resource.addtransportationRelationshipComponent(component3CC);
		
		return resource;
	}

}