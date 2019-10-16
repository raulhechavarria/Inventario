package org.inventario.ecie.domain;

public enum TipoVale {
	//entrada-salida
	TransferenciaEntreAlmacenes, 
	//entradas
	CargaInicial,
	RecepcionACiegas, 
	Recepcion,	
	EntradaPorAjuste,// esto es para ajustar el almacen 
	TransferenciaEntrada,//para cuando es una tranferencia   con un almacen externo de  la empresa
	Devolucion,
	//salidas
	SalidaPorAjuste,// esto es para ajustar el almacen o por mermas
	TransferenciaSalida,//para cuando es una tranferencia   con un almacen externo de  la empresa
	SolicitudEntrega,
	Reclamacion,
	FacturaMN,// esto es para facturar
	FacturaMLC,
	Factura_MN_MLC
	
}
