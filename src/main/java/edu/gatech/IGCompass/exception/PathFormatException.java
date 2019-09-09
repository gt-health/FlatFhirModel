package edu.gatech.IGCompass.exception;

public class PathFormatException extends Exception{
	public PathFormatException(String badPath) {
		super("Expected a period delineated path but found: \""+badPath+"\"");
	}
}
