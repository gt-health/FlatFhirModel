package edu.gatech.IGCompass.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Coding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.gatech.IGCompass.exception.MissingKeyException;
import edu.gatech.IGCompass.exception.WrongTypeException;

public class ProfileEntry implements Serializable{
	protected String name;
	protected String local_def;
	protected Map<String,String> translatedValues;
	protected Map<String,CodeableConcept> conceptMap;
	protected Map<String,ProfileEntry> childMap;
	
	public ProfileEntry() {
		name = "";
		local_def = "";
		translatedValues = new HashMap<String,String>();
		conceptMap = new HashMap<String,CodeableConcept>();
		childMap = new HashMap<String,ProfileEntry>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocal_def() {
		return local_def;
	}

	public void setLocal_def(String local_def) {
		this.local_def = local_def;
	}

	public Map<String, CodeableConcept> getConceptMap() {
		return conceptMap;
	}

	public void setConceptMap(Map<String, CodeableConcept> conceptMap) {
		this.conceptMap = conceptMap;
	}

	public Map<String, String> getTranslatedValues() {
		return translatedValues;
	}

	public void setTranslatedValues(Map<String, String> translatedValues) {
		this.translatedValues = translatedValues;
	}
	
	public Map<String, ProfileEntry> getChildMap() {
		return childMap;
	}

	public void setChildMap(Map<String, ProfileEntry> childMap) {
		this.childMap = childMap;
	}
	
	@Override
	public String toString() {
		return "ProfileEntry [name=" + name + ", local_def=" + local_def + ", translatedValues=" + translatedValues
				+ ", childMap=" + childMap + "]";
	}

	public ProfileEntry findEntryFromPath(String path) {
		if(name.equalsIgnoreCase(path)) {
			return this;
		}
		/*String[] pathSplit = path.split("\\.");
		String[] nameSplit = name.split("\\.");
		String currentPath = path;
		for(int i=0;i<pathSplit.length;i++) {
			if(nameSplit[i] != null) {
				String trimmedName = nameSplit[i].replace("[^a-zA-A0-9]+", "").trim();
				String trimmedPath = pathSplit[i].replace("[^a-zA-A0-9]+", "").trim();
				if(trimmedName.equalsIgnoreCase(trimmedPath)) {
					currentPath = currentPath.substring(currentPath.indexOf('.'));
				}
			}
			else {
				for
			}
		}*/
		for(ProfileEntry childEntry:childMap.values()) {
			ProfileEntry targetEntry = childEntry.findEntryFromPath(path);
			if(targetEntry != null) {
				return targetEntry;
			}
		}
		return null;
	}

	public static ProfileEntry parseFromJson(JsonNode inputJson,String path) throws MissingKeyException, WrongTypeException{
		ProfileEntry profileEntryObject = new ProfileEntry();
		profileEntryObject.setName(path);

		for(Iterator<Map.Entry<String, JsonNode> > it = inputJson.fields();it.hasNext();) {
			Map.Entry<String, JsonNode> field = it.next();
			String key = field.getKey();
			JsonNode value = field.getValue();
			if(key.equals("local_def")) {
				if(!value.isTextual()) {
					throw new WrongTypeException(path+".local_def",value.getNodeType().toString(),"String");
				}
				profileEntryObject.setLocal_def(value.asText());
			}
			else if(value.isObject()){
				ObjectNode objectNode = (ObjectNode)value;
				//If there's a code,system and display we make a codeableconcept object otherwise make a new child
				if(objectNode.get("code") != null && objectNode.get("system") != null && objectNode.get("display") != null) {
					CodeableConcept codeableConcept = parseCodeableConceptFromJson(path+"."+key,value);
					profileEntryObject.getConceptMap().put(key,codeableConcept);
				}
				else {
					ProfileEntry childProfileEntryObject = ProfileEntry.parseFromJson(objectNode,path+"."+key);
					profileEntryObject.getChildMap().put(key, childProfileEntryObject);
					//TODO: Find out how to pass this back up to the root context 
				}
			}
			else if(value.isArray()) {
				ListProfileEntry childListProfileEntry = ListProfileEntry.parseFromJson((ArrayNode)value, path+"."+key);
				profileEntryObject.getChildMap().put(key, childListProfileEntry);
			}
			else if(value.isTextual()) {
				profileEntryObject.getTranslatedValues().put(key,value.asText());
			}
		}
		if(profileEntryObject.getChildMap().size() == 0 && profileEntryObject.getLocal_def().isEmpty()) {
			throw new MissingKeyException(path,"local_def");
		}
		return profileEntryObject;
	}
	
	private static CodeableConcept parseCodeableConceptFromJson(String path,JsonNode inputJson) throws MissingKeyException, WrongTypeException {
		if(inputJson.get("code") == null) {
			throw new MissingKeyException(path,"code");
		}
		if(inputJson.get("system") == null) {
			throw new MissingKeyException(path,"system");
		}
		if(inputJson.get("display") == null) {
			throw new MissingKeyException(path,"display");
		}
		CodeableConcept codeableConcept = new CodeableConcept();
		Coding coding = new Coding();
		for(Iterator<Map.Entry<String, JsonNode> > it = inputJson.fields();it.hasNext();) {
			Map.Entry<String, JsonNode> field = it.next();
			String key = field.getKey();
			JsonNode value = field.getValue();
			if(key.equals("code")) {
				if(!value.isTextual()) {
					throw new WrongTypeException(path+".code",value.getNodeType().toString(),"String");
				}
				coding.setCode(value.asText());
			}
			if(key.equals("system")) {
				if(!value.isTextual()) {
					throw new WrongTypeException(path+".system",value.getNodeType().toString(),"String");
				}
				coding.setSystem(value.asText());
			}
			if(key.equals("display")) {
				if(!value.isTextual()) {
					throw new WrongTypeException(path+".display",value.getNodeType().toString(),"String");
				}
				coding.setDisplay(value.asText());
			}
		}
		codeableConcept.addCoding(coding);
		return codeableConcept;
	}
}