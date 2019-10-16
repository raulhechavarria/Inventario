package org.archetype.common.domain;

import java.io.Serializable;
import java.util.Set;

public class Accion extends Nomenclador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Set<Rol> roles;
	private Accion padre;
	private Set<Accion> hijos;
	
	public Set<Accion> getHijos() {
		return hijos;
	}
	public void setHijos(Set<Accion> hijos) {
		this.hijos = hijos;
	}
	public Accion getPadre() {
		return padre;
	}
	public void setPadre(Accion padre) {
		this.padre = padre;
	}
	public Set<Rol> getRoles() {
		return roles;
	}
	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
}
