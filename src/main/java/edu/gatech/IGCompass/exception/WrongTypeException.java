package edu.gatech.IGCompass.exception;

public class WrongTypeException extends Exception{
	public WrongTypeException(String attribute, String foundType, String... expectedTypes) {
		super("Json path:"+attribute+", expected type "+String.join(" or ", expectedTypes)+" but found type"+foundType);
	}
}
