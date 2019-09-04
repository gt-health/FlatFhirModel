package edu.gatech.IGFlatFhir.exception;

public class MissingKeyException extends Exception{
	public MissingKeyException(String keyname) {
		super("Looking for json key \""+keyname+"\" but could not find it");
	}
	public MissingKeyException(String path, String keyname) {
		super(path+": looking for json key \""+keyname+"\" but could not find it");
	}
}
