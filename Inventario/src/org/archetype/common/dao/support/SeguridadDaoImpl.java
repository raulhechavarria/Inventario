package org.archetype.common.dao.support;

import org.archetype.common.dao.ISeguridadDao;
import org.archetype.common.domain.Usuario;
import org.hibernate.Query;


public class SeguridadDaoImpl extends SpringHibernateSessionFactorySupport implements ISeguridadDao{
	
	public String abrirSesion(String nombreUsuario){
		return null;
	}
	
	public void cerrarSesion(String idSession){
	
	}

	public Usuario obtenerUsuario(String usuario) {
		
		String hql = "from Usuario u where u.login = :usuario";
		Query query = getCurrentSession().createQuery(hql);
		query.setString("usuario", usuario);
		Usuario user = (Usuario)query.uniqueResult();
		
		return user;
	}
}
