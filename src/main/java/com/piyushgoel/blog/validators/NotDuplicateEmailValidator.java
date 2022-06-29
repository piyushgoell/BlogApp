package com.piyushgoel.blog.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotDuplicateEmailValidator implements ConstraintValidator<NotDuplicateEmail, String> {
	
	/*
	 * @Autowired private UserRepository userRepository;
	 */
	
	public void initialize(NotDuplicateEmail notExistingEmail) {}
	
	public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContex) {
		// System.out.println(this.userRepository.findByEmail(email));
		return true;
	}
	

}
