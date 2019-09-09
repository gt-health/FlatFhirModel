package edu.gatech.IGCompass.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import edu.gatech.IGCompass.exception.MissingKeyException;
import edu.gatech.IGCompass.exception.WrongTypeException;

public class ListProfileEntry extends ProfileEntry implements Serializable{

	private List<ProfileEntry> elements;
	public ListProfileEntry() {
		super();
		elements = new ArrayList<ProfileEntry>();
	}

	public List<ProfileEntry> getElements() {
		return elements;
	}

	public void setElements(List<ProfileEntry> elements) {
		this.elements = elements;
	}

	@Override
	public String toString() {
		return "ListProfileEntry [elements=" + elements + "]";
	}
	
	public ProfileEntry findEntryFromPath(String path) {
		if(name.equalsIgnoreCase(path)) {
			return this;
		}
		for(ProfileEntry element:elements) {
			ProfileEntry targetEntry = element.findEntryFromPath(path);
			
			if(targetEntry != null) {
				return targetEntry;
			}
		}
		return null;
	}
	
	public static ListProfileEntry parseFromJson(ArrayNode inputJson,String path) throws MissingKeyException, WrongTypeException{
		ListProfileEntry listProfileEntryObject = new ListProfileEntry();
		listProfileEntryObject.setName(path);

		for(Iterator<JsonNode> it = inputJson.elements();it.hasNext();) {
			JsonNode item = it.next();
			if(item.getNodeType().equals(JsonNodeType.OBJECT)) {
				ProfileEntry profileEntryObject = ProfileEntry.parseFromJson(item, path);
				listProfileEntryObject.getElements().add(profileEntryObject);
			}
			
		}
		return listProfileEntryObject;
	}
}