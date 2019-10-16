package org.inventario.ecie.domain;

import java.io.Serializable;

import org.archetype.common.domain.Nomenclador;


public class Producto extends Nomenclador implements Serializable{

	private static final long serialVersionUID = 1L;
	private String codigo,nombre,descripcion;
	private UnidadMedida unidad = new UnidadMedida();
	private ClasificadorProducto clasificadorProducto = new ClasificadorProducto();

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
	public UnidadMedida getUnidad() {
		return unidad;
	}
	public void setUnidad(UnidadMedida unidad) {
		this.unidad = unidad;
	}
	public ClasificadorProducto getClasificadorProducto() {
		return clasificadorProducto;
	}
	public void setClasificadorProducto(ClasificadorProducto clasificadorProducto) {
		this.clasificadorProducto = clasificadorProducto;
	}

	
}
