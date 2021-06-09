package gmbh.gil;

import java.util.regex.Pattern;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class NumbersAndSpecialCharactersValidator implements IValidator<String> {
	private static final long serialVersionUID = 1L;
	
	private static final Pattern NUMBER = Pattern.compile("[0-9]");
	private static final Pattern specialCharacters = Pattern.compile("[>_<)(=*/'#~&%$§|!?]");
	@Override
	public void validate(IValidatable<String> validatable) {
		 final String numbersOrSpecialCharactersThere = validatable.getValue();
		 if (NUMBER.matcher(numbersOrSpecialCharactersThere).find()) {
			 error(validatable, "numbers are not allowed in the City and Street fields");
		 }
		 if (specialCharacters.matcher(numbersOrSpecialCharactersThere).find()) {
			 error(validatable, "special characters are not allowed");
		 }
	};
	private void error(IValidatable<String> validatable, String errorKey) {
		 ValidationError error = new ValidationError();
		 error.setMessage(errorKey);
		 validatable.error(error);
	}
}

