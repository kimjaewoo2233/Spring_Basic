package controller;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ChangePwdCommandValidator implements Validator {

		@Override
		public boolean supports(Class<?> clazz) {
			return ChangePwdCommand.class.isAssignableFrom(clazz);
		}
		
		@Override
		public void validate(Object target,Errors error) {
				ValidationUtils.rejectIfEmptyOrWhitespace(error, "currentPassword","required");
				ValidationUtils.rejectIfEmpty(error, "newPassword", "required");
		}
		
}
