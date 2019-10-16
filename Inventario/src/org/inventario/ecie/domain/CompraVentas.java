package org.inventario.ecie.domain;

public class CompraVentas {
	private String mes;
	private Double compras,ventas;
	
	
	public CompraVentas(String mes, Double compras,Double ventas) {
		super();
		this.mes = mes;
		this.compras = compras;
		this.ventas = ventas;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public Double getCompras() {
		return compras;
	}
	public void setCompras(Double compras) {
		this.compras = compras;
	}
	public Double getVentas() {
		return ventas;
	}
	public void setVentas(Double ventas) {
		this.ventas = ventas;
	}
	
	
}
