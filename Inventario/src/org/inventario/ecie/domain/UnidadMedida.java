package org.inventario.ecie.domain;

import java.io.Serializable;

import org.archetype.common.domain.Nomenclador;


public class UnidadMedida extends Nomenclador implements Serializable{

	private static final long serialVersionUID = 1L;
	private String codigo,nombre,descripcion;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
