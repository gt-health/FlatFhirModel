package edu.gatech.VRDR.mapper;

import org.hl7.fhir.dstu3.model.DateTimeType;
import org.hl7.fhir.dstu3.model.Quantity;

import edu.gatech.IGCompass.exception.MissingInformationException;
import edu.gatech.IGCompass.mapper.util.CommonUtil;
import edu.gatech.IGCompass.model.IGMapDocument;
import edu.gatech.VRDR.model.DecedentAge;

public class DecedentAgeMapper implements IGMapper<DecedentAge>{
	
	public DecedentAge map(IGMapDocument document,String resourceRootPath) throws MissingInformationException {
		DecedentAge resource = new DecedentAge();
		String value = null;
		String unit = null;
		String effectiveDateTime = null;
		value = CommonUtil.findValueFromIGKey(document,"Decedent Age.valueQuantity.value");
		unit = CommonUtil.findValueFromIGKey(document,"Decedent Age.valueQuantity.unit");
		effectiveDateTime = CommonUtil.findValueFromIGKey(document,"Decedent Age.valueQuantity");
		if((value == null || unit == null) && effectiveDateTime == null) {
			throw new MissingInformationException("Some error message here"); //TODO; get this error message right
		}
		if(value != null && unit != null) {
			resource.setValue(new Quantity().setValue(Double.valueOf(value)).setUnit(unit));
		}
		if(effectiveDateTime != null) {
			resource.setEffective(new DateTimeType(effectiveDateTime));
		}
		return resource;
	}
}