package edu.gatech.IGFlatFhir.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import edu.gatech.IGFlatFhir.exception.MissingKeyException;
import edu.gatech.IGFlatFhir.exception.WrongTypeException;

public class Info implements Serializable{
	private String local_dataset;
	private String fhir_version;
	private String url;
	private String version;
	
	public Info() {
		local_dataset = "";
		fhir_version = "";
		url = "";
		version = "";
	}
	
	public String getLocal_dataset() {
		return local_dataset;
	}
	public void setLocal_dataset(String local_dataset) {
		this.local_dataset = local_dataset;
	}
	public String getFhir_version() {
		return fhir_version;
	}
	public void setFhir_version(String fhir_version) {
		this.fhir_version = fhir_version;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		return "Info [local_dataset=" + local_dataset + ", fhir_version=" + fhir_version + ", url=" + url + ", version="
				+ version + "]";
	}

	public static Info parseFromJson(JsonNode inputJson) throws MissingKeyException, WrongTypeException {
		if(inputJson.get("fhir_version") == null) {
			throw new MissingKeyException("info.fhir_version");
		}
		if(inputJson.get("local_dataset") == null) {
			throw new MissingKeyException("info.local_dataset");
		}
		if(inputJson.get("url") == null) {
			throw new MissingKeyException("info.url");
		}
		Info infoObject = new Info();
		for(Iterator<Map.Entry<String, JsonNode> > it = inputJson.fields();it.hasNext();) {
			Map.Entry<String, JsonNode> field = it.next();
			String key = field.getKey();
			JsonNode value = field.getValue();
			switch(key) {
				case "local_dataset":
					if(!value.isTextual()) {
						throw new WrongTypeException("info.local_dataset",value.getNodeType().toString(),"String");
					}
					infoObject.setLocal_dataset(value.textValue());
					break;
				case "fhir_version":
					if(!value.isTextual()) {
						throw new WrongTypeException("info.fhir_version",value.getNodeType().toString(),"String");
					}
					infoObject.setFhir_version(value.textValue());
					break;
				case "url":
					if(!value.isTextual()) {
						throw new WrongTypeException("info.url",value.getNodeType().toString(),"String");
					}
					infoObject.setUrl(value.textValue());
					break;
				case "version":
					if(!value.isTextual()) {
						throw new WrongTypeException("info.version",value.getNodeType().toString(),"String");
					}
					infoObject.setVersion(value.textValue());
					break;
			}
		}
		return infoObject;
	}
}
