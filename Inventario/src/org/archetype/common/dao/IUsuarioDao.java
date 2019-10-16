package org.archetype.common.dao;

import java.util.Set;

import org.archetype.common.domain.Rol;
import org.archetype.common.domain.Usuario;


public interface IUsuarioDao {	
	public Set<Rol> obtenerRoles(long id_Usuario);
	public void eliminarRol(long id_Usuario, long id_Rol);
	public void adicionarRol(long id_Usuario, long id_Rol);
	public Usuario obtenerUsuario(String nombre);
}
