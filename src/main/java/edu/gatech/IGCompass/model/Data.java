package edu.gatech.IGCompass.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.gatech.IGCompass.exception.MissingDataNodeException;
import edu.gatech.IGCompass.exception.PathFormatException;
import edu.gatech.IGCompass.exception.WrongTypeException;

public class Data implements Serializable{
	private ObjectNode data;
	
	public Data() {
		data = JsonNodeFactory.instance.objectNode();
	}

	public ObjectNode getData() {
		return data;
	}

	public void setData(ObjectNode data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "Data [data=" + data + "]";
	}
	
	public JsonNode get(String path) throws MissingDataNodeException, PathFormatException {
		String[] paths = path.split("\\.");
		String currentPath = "";
		if(paths[0] == null) {
			throw new PathFormatException(path);
		}
		JsonNode node = data;
		for(String myPath:paths) {
			currentPath = currentPath.concat(myPath);
			node = node.path(myPath);
			if(node.isMissingNode()) {
				throw new MissingDataNodeException(currentPath);
			}
			currentPath = currentPath.concat(".");
		}
		return node;
	}

	public static Data parseFromJson(JsonNode inputJson) throws WrongTypeException{
		Data dataObject = new Data();
		if(!inputJson.isObject()) {
			throw new WrongTypeException("data",inputJson.getNodeType().toString(),"ObjectNode");
		}
		dataObject.setData((ObjectNode)inputJson);
		return dataObject;
	}
}