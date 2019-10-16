package org.inventario.ecie.domain;

import java.io.Serializable;

import org.archetype.common.domain.Nomenclador;

public class Organismo extends Nomenclador implements Serializable {


	private static final long serialVersionUID = 1L;
	private String descripcion,nombre;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}
