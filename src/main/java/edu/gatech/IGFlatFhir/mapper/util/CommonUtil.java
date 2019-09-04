package edu.gatech.IGFlatFhir.mapper.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import edu.gatech.IGFlatFhir.exception.MissingDataNodeException;
import edu.gatech.IGFlatFhir.exception.MissingKeyException;
import edu.gatech.IGFlatFhir.exception.PathFormatException;
import edu.gatech.IGFlatFhir.exception.WrongTypeException;
import edu.gatech.IGFlatFhir.model.IGMapDocument;

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