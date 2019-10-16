package org.inventario.ecie.domain;

public enum OperacionFactura {
	VentaMayorista, //Venta mayorista de bienes y servicios.
	VentaInterna, //Venta interna con operaci�n de cobro y pago.
	EntregaProductos, //Entrega de productos en consignaci�n o dep�sito.
	VentaActF, //Venta activos fijos tangibles.
	Devolucion // Devoluci�n.	
}
