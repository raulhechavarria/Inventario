package org.inventario.ecie.web.utils;

import org.inventario.ecie.domain.TipoVale;
import org.inventario.ecie.domain.Vale;
import org.inventario.ecie.domain.ValeProducto;

import sun.tools.jar.resources.jar;

public class CalculoEco {
		
	public static Double redondeo2(Double d){
    	return (double)Math.round((d)*100)/100;
    	}
	public static Double redondeo3(Double d){
    	return (double)Math.round((d)*1000)/1000;
    }
	public static Double redondeo5(Double d){
    	return (double)Math.round((d)*100000)/100000;
    }
	
	public static Double calculoImporte(Double precio,Double cantidad){
			return(precio * cantidad);
	}
	
	public static Double calculoCantidad(Double importe,Double precio)throws Exception{
		if ((precio == 0.00) || (precio == null)) {
			throw new Exception("Calculo de Cantidad invalida");
		} else {
			return(importe/precio);
		}
					
	}
	
	public static Double calculoPrecio(Double importe,Double cantidad)throws Exception{
		if ((cantidad == 0.00) || (cantidad == null)) {
			throw new Exception("Calculo de Cantidad invalida");
		} else {
			return(importe/cantidad);
		}					
	}
	
	public static Double porrateo(Double recargoDescuento,Double importe)throws Exception{
		if ((importe == null)) {
			throw new Exception("Calculo de porrateo invalida");
		} else {
			if (importe == 0.00) {
				return 0.00;
			} else {
				return(recargoDescuento/importe);
			}
		}
	}
	
	public static void llenarValeConCero(Vale vale){
		vale.setImporteMLC(0.00);
		vale.setImporteMN(0.00);
		vale.setImporteNeto(0.00);
		vale.setRecargoDescuentoMLC(0.00);
		vale.setRecargoDescuentoMN(0.00);
		vale.setTotalMLC(0.00);
		vale.setTotalMN(0.00);
	}
	
	public static void llenarValeProductoConCero(ValeProducto vp){
		vp.setImporteMLCVale(0.00);
		vp.setImporteMNVale(0.00);
		vp.setRecargoDescuentoMLC(0.00);
		vp.setRecargoDescuentoMN(0.00);
		vp.setTotalMN(0.00);
		vp.setTotalMLC(0.00);
		vp.setPrecioMLCVale(0.00);
		vp.setPrecioMNVale(0.00);
		vp.setTotalMLC(0.00);
		vp.setTotalMN(0.00);
	}
	
	public static Boolean SiEntrada(Vale vale) {
		if ((vale.getTipoVale() == TipoVale.Recepcion)
				|| (vale.getTipoVale() == TipoVale.RecepcionACiegas)
				|| (vale.getTipoVale() == TipoVale.EntradaPorAjuste)
				|| (vale.getTipoVale() == TipoVale.TransferenciaEntrada)
				|| (vale.getTipoVale() == TipoVale.CargaInicial)
				|| (vale.getTipoVale() == TipoVale.Devolucion)) {
			return true;
		} else {
			return false;
		}
	}
}
