package edu.gatech.common.mapper;

import org.hl7.fhir.dstu3.model.Quantity;

import edu.gatech.IGCompass.exception.MissingDataNodeException;
import edu.gatech.IGCompass.exception.MissingInformationException;
import edu.gatech.IGCompass.exception.MissingKeyException;
import edu.gatech.IGCompass.exception.PathFormatException;
import edu.gatech.IGCompass.exception.WrongTypeException;
import edu.gatech.IGCompass.model.IGMapDocument;

public class QuantityMapper implements IGMapper<Quantity>{

	@Override
	public Quantity map(IGMapDocument document, String resourceRootPath) throws MissingInformationException,
			WrongTypeException, MissingDataNodeException, PathFormatException, MissingKeyException {
		Quantity quantity = new Quantity();
		return quantity;
	}

}
