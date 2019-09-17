package edu.gatech.common.mapper;

import org.hl7.fhir.dstu3.model.CodeableConcept;

import edu.gatech.IGCompass.exception.MissingDataNodeException;
import edu.gatech.IGCompass.exception.PathFormatException;
import edu.gatech.IGCompass.exception.WrongTypeException;
import edu.gatech.IGCompass.mapper.util.CommonUtil;
import edu.gatech.IGCompass.model.IGMapDocument;

public class CodeableConceptMapper implements IGMapper<CodeableConcept>{
	public CodeableConcept map(IGMapDocument document,String resourceRootPath) throws WrongTypeException, MissingDataNodeException, PathFormatException{
		String codingRootPath = resourceRootPath+".coding";
		String valueCodingRootPath = resourceRootPath+".valueCoding";
		String textRootPath = resourceRootPath+".text";
		String codingLocalValue = null;
		String valueCodingLocalValue = null;
		String codingText = null;
		CodeableConcept rootCodeValue = null;
		codingLocalValue = CommonUtil.findValueFromIGKey(document,codingRootPath);
		valueCodingLocalValue = CommonUtil.findValueFromIGKey(document,valueCodingRootPath);
		codingText = CommonUtil.findValueFromIGKey(document,textRootPath);
		if(codingLocalValue != null) {
			rootCodeValue = document.getTranslatedLocalDef(codingRootPath, codingLocalValue);
		}
		else if(valueCodingLocalValue != null) {
			rootCodeValue = document.getTranslatedLocalDef(valueCodingRootPath, valueCodingLocalValue);
		}
		if(rootCodeValue != null) {
			return rootCodeValue;
		}
		else if(codingText != null) {
			CodeableConcept returnConcept = new CodeableConcept().setText(codingText);
			return returnConcept;
		}
		return null;
	}
}
