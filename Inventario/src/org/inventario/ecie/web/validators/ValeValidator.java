package org.inventario.ecie.web.validators;

import org.hibernate.exception.ConstraintViolationException;
import org.inventario.ecie.domain.EstadoVale;
import org.inventario.ecie.domain.TipoVale;
import org.inventario.ecie.domain.Vale;
import org.inventario.ecie.web.utils.CalculoEco;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ValeValidator implements Validator {

	public static void resolverConfirmados(Vale vale) {
		if (vale.getEstadoVale().equals(EstadoVale.Confirmado)) {
			resolvereExcepciones("ValeyaConfirm");
		}	
	}
	
	public static void resolverReclamaciones(Vale vale) {
		if (!vale.getEstadoVale().equals(EstadoVale.Confirmado)) {
			resolvereExcepciones("ValeSinConfirm");
		}
	}
	
	public static void resolverRecepACiegaConfirm(Vale vale) {
		if ((vale.getTipoVale().equals(TipoVale.RecepcionACiegas)) && (vale.getValeProducto().size() <= 0)) {
				resolvereExcepciones("ValeSinProducto");
		}
	}
	
	public static void resolverValeCeros(Vale vale) {
		if (CalculoEco.SiEntrada(vale)) {
			if (!vale.getTipoVale().equals(TipoVale.EntradaPorAjuste)) {
				if ((vale.getTotalMLC() == 0) && ((vale.getTotalMN() == 0))&&(!vale.getTipoVale().equals(TipoVale.RecepcionACiegas))) {
					resolvereExcepciones("ValeEnCero");
				}	
			}
			
		}
	}

	public static void resolvereExcepciones(String value) {
		ConstraintViolationException ex = new ConstraintViolationException("error", null, value);
		DataIntegrityViolationException exception = new DataIntegrityViolationException("error", ex);
		throw exception;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class arg0) {
		return Vale.class.equals(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub

	}

}
