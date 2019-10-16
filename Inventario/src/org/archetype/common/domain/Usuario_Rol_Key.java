package org.archetype.common.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;


public class Usuario_Rol_Key implements Serializable{

	private static final long serialVersionUID = 1L;
	private Usuario usuario = new Usuario();
	private Rol rol = new Rol();
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Usuario_Rol_Key))
			return false;
		Usuario_Rol_Key ot = (Usuario_Rol_Key)o;
		return ot.usuario == usuario && ot.rol == rol;
	}
	
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder(17,31);
		builder.append(usuario.getId());
		builder.append(rol.getId());
		
		return builder.toHashCode();
	}
}
