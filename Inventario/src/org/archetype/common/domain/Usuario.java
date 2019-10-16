package org.archetype.common.domain;

import java.io.Serializable;
import java.util.Set;

public class Usuario extends PersistentObject implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nombre;
	private String login;
	private String password;
	private boolean deshabilitado;
	private Set<Rol> roles;
		
	public Set<Rol> getRoles() {
		return roles;
	}
	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isDeshabilitado() {
		return deshabilitado;
	}
	public void setDeshabilitado(boolean deshabilitado) {
		this.deshabilitado = deshabilitado;
	}
	
		
}
