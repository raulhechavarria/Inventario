package org.archetype.common.business;

import org.archetype.common.domain.Usuario;

public interface IUsuarioService extends ICommonService{
	public Usuario obtenerUsuario(String nombre);
}
