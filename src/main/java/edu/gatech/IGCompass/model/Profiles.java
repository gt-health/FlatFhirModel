package edu.gatech.IGCompass.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import edu.gatech.IGCompass.exception.MissingKeyException;
import edu.gatech.IGCompass.exception.WrongTypeException;

public class Profiles implements Serializable{
	Map<String,ProfileEntry> entries;
	
	public Profiles() {
		entries = new HashMap<String,ProfileEntry>();
	}

	public Map<String, ProfileEntry> getEntries() {
		return entries;
	}

	public void setEntries(Map<String, ProfileEntry> entries) {
		this.entries = entries;
	}
	
	@Override
	public String toString() {
		return "Profiles [entries=" + entries + "]";
	}
	
	public ProfileEntry findEntryFromPath(String path) {
		for(ProfileEntry entry:entries.values()) {
			ProfileEntry targetEntry = entry.findEntryFromPath(path); 
			if(targetEntry != null) {
				return targetEntry;
			}
		}
		return null;
	}

	public static Profiles parseFromJson(JsonNode inputJson) throws WrongTypeException, MissingKeyException{
		Profiles profilesObject = new Profiles();
		for(Iterator<Map.Entry<String, JsonNode> > it = inputJson.fields();it.hasNext();) {
			Map.Entry<String, JsonNode> field = it.next();
			String key = field.getKey();
			JsonNode value = field.getValue();
			if(value.getNodeType().equals(JsonNodeType.OBJECT)) {
				ProfileEntry profileEntry = ProfileEntry.parseFromJson(value,key);
				profilesObject.getEntries().put(key, profileEntry);
			}
			else if(value.getNodeType().equals(JsonNodeType.ARRAY)) {
				ListProfileEntry profileEntry = ListProfileEntry.parseFromJson((ArrayNode)value, key);
				profilesObject.getEntries().put(key, profileEntry);
			}
			else {
				throw new WrongTypeException("profiles",value.getNodeType().toString(),"ObjectNode","ArrayNode");
			}
		}
		return profilesObject;
	}
}