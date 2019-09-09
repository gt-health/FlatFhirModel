package edu.gatech.IGCompass.mapper.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import edu.gatech.IGCompass.exception.MissingDataNodeException;
import edu.gatech.IGCompass.exception.MissingKeyException;
import edu.gatech.IGCompass.exception.PathFormatException;
import edu.gatech.IGCompass.exception.WrongTypeException;
import edu.gatech.IGCompass.model.IGMapDocument;

public class CommonUtil {
	public static String findValueFromIGKey(IGMapDocument document,String resourcePath) {
		String returnString = null;
		try {
			returnString = document.findValueFromIGKey(resourcePath);
		} catch (WrongTypeException | MissingDataNodeException | PathFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(MissingKeyException e) {
			return null;
		}
		return returnString;
	}
	
	public static List<String> findListFromIGKey(IGMapDocument document,String resourcePath) {
		List<String> returnList = null;
		try {
			returnList = document.findListFromIGKey(resourcePath);
		} catch (WrongTypeException | MissingDataNodeException | PathFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(MissingKeyException e) {
			return null;
		}
		return returnList;
	}
	
	public static List<String> unpackJsonArrayNode(ArrayNode arrayNode){
		List<String> returnList = new ArrayList<String>();
		for(JsonNode jsonNode:arrayNode) {
			if(jsonNode.isValueNode()) {
				returnList.add(jsonNode.asText());
			}
		}
		return returnList;
	}
	
}