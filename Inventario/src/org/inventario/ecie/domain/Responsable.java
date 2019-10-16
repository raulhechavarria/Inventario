package org.inventario.ecie.domain;

import java.io.Serializable;

import org.archetype.common.domain.Nomenclador;

public class Responsable extends Nomenclador implements Serializable {


	private static final long serialVersionUID = 1L;
	private String descripcion,nombre,apellido1,apellido2,carnetIdentidad;
	
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
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getCarnetIdentidad() {
		return carnetIdentidad;
	}
	public void setCarnetIdentidad(String carnetIdentidad) {
		this.carnetIdentidad = carnetIdentidad;
	}
	
	

}
