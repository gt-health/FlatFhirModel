package edu.gatech.IGCompass.exception;

//TODO: Figure out a good error message here.
public class MissingInformationException extends Exception{
	public MissingInformationException(String key) {
		super("Profile requires key "+key+" but found nothing instead");
	}
}