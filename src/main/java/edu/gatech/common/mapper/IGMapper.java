package edu.gatech.common.mapper;

import edu.gatech.IGCompass.exception.MissingDataNodeException;
import edu.gatech.IGCompass.exception.MissingInformationException;
import edu.gatech.IGCompass.exception.MissingKeyException;
import edu.gatech.IGCompass.exception.PathFormatException;
import edu.gatech.IGCompass.exception.WrongTypeException;
import edu.gatech.IGCompass.model.IGMapDocument;

public interface IGMapper<T> {
	T map(IGMapDocument document,String resourceRootPath) throws MissingInformationException, WrongTypeException, MissingDataNodeException, PathFormatException, MissingKeyException;
}
