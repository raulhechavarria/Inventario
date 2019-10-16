package org.archetype.common.dao;

import org.archetype.common.domain.Usuario;

public interface ISeguridadDao {

	public String abrirSesion(String nombreUsuario);	
	public void cerrarSesion(String idSession);
	public Usuario obtenerUsuario(String usuario);
}
