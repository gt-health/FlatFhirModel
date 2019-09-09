package edu.gatech.VRDR.mapper;

import java.util.List;

import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.HumanName.NameUse;

import edu.gatech.IGCompass.mapper.util.CommonUtil;
import edu.gatech.IGCompass.model.IGMapDocument;

public class HumanNameMapper implements IGMapper<HumanName>{

	@Override
	public HumanName map(IGMapDocument document, String resourceRootPath){
		HumanName humanName = new HumanName();
		String use = null;
		String text = null;
		String family = null;
		List<String> given = null;
		List<String> prefix = null;
		List<String> suffix = null;
		use = CommonUtil.findValueFromIGKey(document,resourceRootPath+".use");
		text = CommonUtil.findValueFromIGKey(document,resourceRootPath+".text");
		family = CommonUtil.findValueFromIGKey(document,resourceRootPath+".family");
		given = CommonUtil.findListFromIGKey(document,resourceRootPath+".given");
		prefix = CommonUtil.findListFromIGKey(document,resourceRootPath+".prefix");
		suffix = CommonUtil.findListFromIGKey(document,resourceRootPath+".suffix");
		if(use != null) {
			humanName.setUse(NameUse.valueOf(use));
		}
		if(text != null) {
			humanName.setText(text);
		}
		if(family != null) {
			humanName.setFamily(family);
		}
		if(given != null) {
			for(String stringTemp:given) {
				humanName.addGiven(stringTemp);
			}
		}
		if(prefix != null) {
			for(String stringTemp:prefix) {
				humanName.addPrefix(stringTemp);
			}
		}
		if(suffix != null) {
			for(String stringTemp:suffix) {
				humanName.addSuffix(stringTemp);
			}
		}
		//TODO: HumanNameMapper.period
		return humanName;
	}

}
