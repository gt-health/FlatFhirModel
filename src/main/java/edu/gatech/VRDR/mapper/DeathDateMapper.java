package edu.gatech.VRDR.mapper;

import org.hl7.fhir.dstu3.model.DateTimeType;

import edu.gatech.IGCompass.exception.MissingInformationException;
import edu.gatech.IGCompass.mapper.util.CommonUtil;
import edu.gatech.IGCompass.model.IGMapDocument;
import edu.gatech.VRDR.model.DeathDate;
import edu.gatech.VRDR.model.DecedentAge;
import edu.gatech.common.mapper.IGMapper;

public class DeathDateMapper implements IGMapper<DeathDate>{
	public DeathDate map(IGMapDocument document,String resourceRootPath) throws MissingInformationException {
		DeathDate resource = new DeathDate();
		String effectiveDateTime = null;
		String timePronouncedDead = null;
		effectiveDateTime = CommonUtil.findValueFromIGKey(document,"Death Date.effectiveDateTime");
		timePronouncedDead = CommonUtil.findValueFromIGKey(document,"Death Date.component.effectiveDateTime");
		if(effectiveDateTime != null) {
			resource.setEffective(new DateTimeType(effectiveDateTime));
		}
		if(timePronouncedDead != null) {
			resource.addDatePronouncedDead(new DateTimeType(timePronouncedDead));
		}
		return resource;
	}
}