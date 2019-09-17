package edu.gatech.IGCompass.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hl7.fhir.dstu3.model.CodeableConcept;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import edu.gatech.IGCompass.exception.MissingDataNodeException;
import edu.gatech.IGCompass.exception.MissingKeyException;
import edu.gatech.IGCompass.exception.PathFormatException;
import edu.gatech.IGCompass.exception.WrongTypeException;
import edu.gatech.IGCompass.mapper.util.CommonUtil;

public class IGMapDocument implements Serializable{
	private Info info;
	private Data data;
	private Profiles profiles;
	public IGMapDocument() {
		super();
		info = new Info();
		data = new Data();
		profiles = new Profiles();
	}
	
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info iginfo) {
		this.info = iginfo;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public Profiles getProfiles() {
		return profiles;
	}
	public void setProfiles(Profiles profiles) {
		this.profiles = profiles;
	}
	
	@Override
	public String toString() {
		return "IGMapDocument [info=" + info + ", data=" + data + ", profiles=" + profiles + "]";
	}
	
	public List getResourceMapFromProfile(String resourceName) {
		String cleanResourceName = resourceName.replaceAll("[^a-zA-A0-9]+", "").trim();
		List<ProfileEntry> returnList = new ArrayList<ProfileEntry>();
		for(String key:profiles.entries.keySet()) {
			String cleanKey = key.replace("[^a-zA-A0-9]+", "").trim();
			if(cleanKey.startsWith(cleanResourceName)) {
				returnList.add(profiles.entries.get(key));
			}
		}
		return returnList;
	}
	
	public String findValueFromIGKey(String resourcePath) throws WrongTypeException, MissingDataNodeException, PathFormatException, MissingKeyException {
		ProfileEntry profileEntry = profiles.findEntryFromPath(resourcePath);
		if(profileEntry == null) {
			throw new MissingKeyException(resourcePath);
		}
		String local_def = profileEntry.getLocal_def();
		if(local_def == null) {
			local_def = "";
		}
		JsonNode data = getData(local_def);
		if(!data.isValueNode()) {
			throw new WrongTypeException(local_def,data.getNodeType().toString(),"JacksonValueNode");
		}
		String translatedValue = profileEntry.getTranslatedValues().get(data.asText());
		return translatedValue != null ? translatedValue : data.asText();
	}
	
	public List<String> findListFromIGKey(String resourcePath) throws WrongTypeException, MissingDataNodeException, PathFormatException, MissingKeyException{
		ProfileEntry profileEntry = profiles.findEntryFromPath(resourcePath);
		if(profileEntry == null) {
			throw new MissingKeyException(resourcePath);
		}
		if(!(profileEntry instanceof ListProfileEntry)) {
			throw new WrongTypeException(resourcePath,"ObjectNode","ArrayNode");
		}
		ListProfileEntry listProfileEntry = (ListProfileEntry) profileEntry;
		List<String> returnList = new ArrayList<String>();
		for(ProfileEntry childProfileEntry:listProfileEntry.getElements()) {
			String local_def = childProfileEntry.getLocal_def();
			if(local_def == null) {
				local_def = ""; //TODO: Handle missing local_def better here
			}
			JsonNode data = getData(local_def);
			if(!data.isValueNode() && !data.isArray()) {
				throw new WrongTypeException(local_def,data.getNodeType().toString(),"JacksonValueNode or JacksonArrayNode");
			}
			if(data.isArray()) {
				returnList.addAll(CommonUtil.unpackJsonArrayNode((ArrayNode)data));
			}
			else if(data.isValueNode()) {
				returnList.add(data.asText());
			}
		}
		return returnList;
	}
	
	public CodeableConcept getTranslatedLocalDef(String resourcePath,String localValue) {
		ProfileEntry profileEntry = profiles.findEntryFromPath(resourcePath);
		return profileEntry.getConceptMap().get(localValue);
	}
	
	public JsonNode getData(String path) throws MissingDataNodeException, PathFormatException {
		if(path.startsWith("data.")) {
			path = path.substring(5);
		}
		return data.get(path);
	}

	public static IGMapDocument parseFromJson(JsonNode inputJson) throws WrongTypeException, MissingKeyException {
		if(inputJson.get("profiles") == null) {
			throw new MissingKeyException("profiles");
		}
		if(inputJson.get("data") == null) {
			throw new MissingKeyException("data");
		}
		if(inputJson.get("info") == null) {
			throw new MissingKeyException("info");
		}
		IGMapDocument documentObject = new IGMapDocument();
		for(Iterator<Map.Entry<String, JsonNode> > it = inputJson.fields();it.hasNext();) {
			Map.Entry<String, JsonNode> field = it.next();
			String key = field.getKey();
			switch(key) {
				case "profiles":
					Profiles profiles = Profiles.parseFromJson(field.getValue());
					documentObject.setProfiles(profiles);
					break;
				case "data":
					Data data = Data.parseFromJson(field.getValue());
					documentObject.setData(data);
					break;
				case "info":
					Info info = Info.parseFromJson(field.getValue());
					documentObject.setInfo(info);
					break;
			}
		}
		return documentObject;
	}
}