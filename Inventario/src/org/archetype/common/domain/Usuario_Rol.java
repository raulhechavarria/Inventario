package org.archetype.common.domain;

import java.io.Serializable;


public class Usuario_Rol implements Serializable{

	private static final long serialVersionUID = 1L;
	private Usuario_Rol_Key key = new Usuario_Rol_Key();
	
	public Usuario_Rol_Key getKey() {
		return key;
	}
	
	public void setKey(Usuario_Rol_Key key) {
		this.key = key;
	}

}
