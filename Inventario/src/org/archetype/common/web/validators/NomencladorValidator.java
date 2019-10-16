package org.archetype.common.web.validators;


import org.archetype.common.domain.Nomenclador;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class NomencladorValidator implements Validator{

	@SuppressWarnings("unchecked")
	public boolean supports(Class cl) {
		return cl.getSuperclass().equals(Nomenclador.class);
	}
	
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "nombre", "nombre_requerido");				
	}
}
