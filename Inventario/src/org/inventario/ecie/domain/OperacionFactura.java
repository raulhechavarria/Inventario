package org.inventario.ecie.domain;

public enum OperacionFactura {
	VentaMayorista, //Venta mayorista de bienes y servicios.
	VentaInterna, //Venta interna con operación de cobro y pago.
	EntregaProductos, //Entrega de productos en consignación o depósito.
	VentaActF, //Venta activos fijos tangibles.
	Devolucion // Devolución.	
}
