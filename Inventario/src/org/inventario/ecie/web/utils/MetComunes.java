package org.inventario.ecie.web.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.exception.ConstraintViolationException;
import org.inventario.ecie.domain.EstadoVale;
import org.inventario.ecie.domain.TipoVale;
import org.inventario.ecie.domain.Vale;
import org.springframework.dao.DataIntegrityViolationException;

public class MetComunes {

	@SuppressWarnings("deprecation")
	public static Date fechaStringToDate(String valor) {
		String[] fechaValues = valor.split("-");
		Date date = new Date();
		date.setDate(java.lang.Integer.valueOf(fechaValues[2]));
		date.setMonth(java.lang.Integer.valueOf(fechaValues[1])-1);
		date.setYear(java.lang.Integer.valueOf(fechaValues[0])-1900);
		return date;
	}
	
	@SuppressWarnings("all")
	public static Map LLenarproperties(String tip) {
		Map properties = new HashMap();
		TipoVale tipo = null;
		switch (tipo.valueOf(tip)) {
		case CargaInicial:
			properties.put("tip", TipoVale.CargaInicial);
			break;
		case Recepcion:
			properties.put("tip", TipoVale.Recepcion);
			break;
		case EntradaPorAjuste:
			properties.put("tip", TipoVale.EntradaPorAjuste);
			break;
		case RecepcionACiegas:
			properties.put("tip", TipoVale.RecepcionACiegas);
			break;
		case SalidaPorAjuste:
			properties.put("tip", TipoVale.SalidaPorAjuste);
			break;
		case SolicitudEntrega:
			properties.put("tip", TipoVale.SolicitudEntrega);
			break;
		case TransferenciaEntrada:
			properties.put("tip", TipoVale.TransferenciaEntrada);
			break;
		case TransferenciaSalida:
			properties.put("tip", TipoVale.TransferenciaSalida);
			break;
		case TransferenciaEntreAlmacenes:
			properties.put("tip", TipoVale.TransferenciaEntreAlmacenes);
			break;
		case Devolucion:
			properties.put("tip", TipoVale.Devolucion);
			break;
		case Reclamacion:
			properties.put("tip", TipoVale.Reclamacion);
			break;
		case Factura_MN_MLC:
			properties.put("tip", TipoVale.Factura_MN_MLC);
			break;
		case FacturaMLC:
			properties.put("tip", TipoVale.FacturaMLC);
			break;
		case FacturaMN:
			properties.put("tip", TipoVale.FacturaMN);
			break;
		}
		return properties;
	}
	
	public static void ResolverConfirmados(Vale vale) {
		if (vale.getEstadoVale() == EstadoVale.Confirmado) {
			ConstraintViolationException ex = new ConstraintViolationException(
					"error", null, "ValeyaConfirm");
			DataIntegrityViolationException exception = new DataIntegrityViolationException(
					"error", ex);
			throw exception;
		}
	}
}
