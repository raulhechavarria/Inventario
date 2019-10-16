package org.inventario.ecie.domain;

import java.io.Serializable;

import org.archetype.common.domain.Nomenclador;


public class Almacen extends Nomenclador implements Serializable{

	private static final long serialVersionUID = 1L;
	private String codigo,nombre,descripcion;
	private Boolean cargaInicial;
	private Responsable responsable = new Responsable();
	private EstablecimientoEcie establecimientoEcie = new EstablecimientoEcie();
	
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
	public Responsable getResponsable() {
		return responsable;
	}
	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}
	public EstablecimientoEcie getEstablecimientoEcie() {
		return establecimientoEcie;
	}
	public void setEstablecimientoEcie(EstablecimientoEcie establecimientoEcie) {
		this.establecimientoEcie = establecimientoEcie;
	}
	public void setCargaInicial(Boolean cargaInicial) {
		this.cargaInicial = cargaInicial;
	}
	public Boolean getCargaInicial() {
		return cargaInicial;
	}
	
	
}
