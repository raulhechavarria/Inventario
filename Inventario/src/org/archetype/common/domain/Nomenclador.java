package org.archetype.common.domain;

import java.io.Serializable;

public class Nomenclador extends PersistentObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String descripcion;
	
	public Nomenclador() {
	
	}
	public Nomenclador(String nombre){
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
