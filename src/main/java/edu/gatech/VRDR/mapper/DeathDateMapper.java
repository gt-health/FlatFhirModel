package edu.gatech.VRDR.mapper;

import org.hl7.fhir.dstu3.model.DateTimeType;

import edu.gatech.IGCompass.exception.MissingInformationException;
import edu.gatech.IGCompass.mapper.util.CommonUtil;
import edu.gatech.IGCompass.model.IGMapDocument;
import edu.gatech.VRDR.model.DeathDate;
import edu.gatech.VRDR.model.DecedentAge;

public class DeathDateMapper implements IGMapper<DeathDate>{
	public DeathDate map(IGMapDocument document,String resourceRootPath) throws MissingInformationException {
		DeathDate resource = new DeathDate();
		String effectiveDateTime = null;
		effectiveDateTime = CommonUtil.findValueFromIGKey(document,"Death Date.valueQuantity.value");
		if(effectiveDateTime != null) {
			resource.setEffective(new DateTimeType(effectiveDateTime));
		}
		return resource;
	}
}
