package org.archetype.common.business.support;

import java.io.Serializable;

import org.archetype.common.business.IUsuarioService;
import org.archetype.common.dao.IUsuarioDao;
import org.archetype.common.domain.Usuario;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UsuarioService extends CommonService implements IUsuarioService{
	
	public Object obtener(Serializable id) {
		Usuario user = (Usuario)getDao().obtener(getClazz(),id);
		user.getRoles().size();
		return user;
	}

	public Usuario obtenerUsuario(String nombre) {
		Usuario user = ((IUsuarioDao)getDao()).obtenerUsuario(nombre);		
		if(user != null)
			user.getRoles().size();
		
		return user;
	}
}
