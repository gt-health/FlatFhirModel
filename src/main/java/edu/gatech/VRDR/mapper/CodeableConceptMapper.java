package edu.gatech.VRDR.mapper;

import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Coding;

import edu.gatech.IGFlatFhir.exception.MissingDataNodeException;
import edu.gatech.IGFlatFhir.exception.MissingKeyException;
import edu.gatech.IGFlatFhir.exception.PathFormatException;
import edu.gatech.IGFlatFhir.exception.WrongTypeException;
import edu.gatech.IGFlatFhir.mapper.util.CommonUtil;
import edu.gatech.IGFlatFhir.model.IGMapDocument;
import edu.gatech.VRDR.model.AutopsyPerformedIndicator;

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
			rootCodeValue = document.getCodeableConcept(codingRootPath, codingLocalValue);
		}
		else if(valueCodingLocalValue != null) {
			rootCodeValue = document.getCodeableConcept(valueCodingRootPath, valueCodingLocalValue);
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
