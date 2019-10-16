
package org.archetype.common.web.validators;


import org.archetype.common.domain.Usuario;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UsuarioValidator implements Validator{
	
	@SuppressWarnings("unchecked")
	public boolean supports(Class cl) {
		return Usuario.class.equals(cl);
	}
	
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, 
				"nombre", "nombre_requerido");
		ValidationUtils.rejectIfEmpty(errors, 
				"login", "login_requerido");
		ValidationUtils.rejectIfEmpty(errors, 
				"password", "password_requerido");		
	}
}
