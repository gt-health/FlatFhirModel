package edu.gatech.VRDR.mapper;

import edu.gatech.IGFlatFhir.exception.MissingDataNodeException;
import edu.gatech.IGFlatFhir.exception.MissingInformationException;
import edu.gatech.IGFlatFhir.exception.MissingKeyException;
import edu.gatech.IGFlatFhir.exception.PathFormatException;
import edu.gatech.IGFlatFhir.exception.WrongTypeException;
import edu.gatech.IGFlatFhir.model.IGMapDocument;

public interface IGMapper<T> {
	T map(IGMapDocument document,String resourceRootPath) throws MissingInformationException, WrongTypeException, MissingDataNodeException, PathFormatException, MissingKeyException;
}
