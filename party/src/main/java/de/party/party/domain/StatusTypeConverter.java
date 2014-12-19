package de.party.party.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;



@Converter(autoApply = true)
public class StatusTypeConverter implements AttributeConverter<StatusType, String> {

	@Override
	public String convertToDatabaseColumn(StatusType arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusType convertToEntityAttribute(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
