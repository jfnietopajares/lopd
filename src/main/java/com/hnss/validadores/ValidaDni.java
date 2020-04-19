package com.hnss.validadores;

import com.vaadin.data.validator.RegexpValidator;

@SuppressWarnings("serial")
public class ValidaDni extends RegexpValidator {
//	private static final String PATTERN = "(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])";

	private static final String PATTERN = "((([X-Z])|([LM])){1}([-]?)((\\d){7})([-]?)([A-Z]{1}))|((\\d{8})([-]?)([A-Z]))";

	public ValidaDni(String errorMessage) {

		super(errorMessage, PATTERN, true);
	}
}
