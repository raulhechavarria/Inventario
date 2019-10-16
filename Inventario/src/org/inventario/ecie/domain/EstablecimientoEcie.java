package org.inventario.ecie.domain;

import java.io.Serializable;

import org.archetype.common.domain.Nomenclador;


public class EstablecimientoEcie extends Nomenclador implements Serializable{

	private static final long serialVersionUID = 1L;
	private String codigo;
	private String direccion;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getDireccion() {
		return direccion;
	}
	
}
