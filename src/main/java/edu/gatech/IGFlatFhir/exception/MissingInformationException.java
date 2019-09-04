package edu.gatech.IGFlatFhir.exception;

//TODO: Figure out a good error message here.
public class MissingInformationException extends Exception{
	public MissingInformationException(String message) {
		super(message);
	}
}