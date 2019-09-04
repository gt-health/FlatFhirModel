package edu.gatech.IGFlatFhir.exception;

public class WrongTypeException extends Exception{
	public WrongTypeException(String attribute, String foundType, String... expectedTypes) {
		super(attribute+": expected type "+String.join(" or ", expectedTypes)+" but found type"+foundType);
	}
}
