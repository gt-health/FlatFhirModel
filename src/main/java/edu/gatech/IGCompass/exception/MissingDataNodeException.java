package edu.gatech.IGCompass.exception;

public class MissingDataNodeException extends Exception{
	public MissingDataNodeException(String path) {
		super("Expected to find a data value at \""+path+"\" but found nothing instead");
	}
}
