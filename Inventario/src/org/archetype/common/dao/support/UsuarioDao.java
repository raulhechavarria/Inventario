package org.archetype.common.dao.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.archetype.common.dao.IUsuarioDao;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.archetype.common.domain.Rol;
import org.archetype.common.domain.Usuario;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unchecked")
@Repository
public class UsuarioDao extends CommonDao implements IUsuarioDao{
	
	public void actualizar(Object usuario) {
		if(((Usuario)usuario).getPassword() == null){
			String query = "select user.password from Usuario user where user.id = :id";
			Map properties = new HashMap();
			properties.put("id", ((Usuario)usuario).getId());
			String pass = (String)obtenerHQL(query, properties);
			((Usuario)usuario).setPassword(pass);
		}
				
		getCurrentSession().merge(usuario);		
	}
			
	public BaseSearchResult listar(Class clazz, ExtGridRequest egr) {
		String query = "";
        if (egr.getQuery() != null){
        	query = egr.getQuery().toString();
        }
		Criteria crit = getCurrentSession().createCriteria(Usuario.class);
		
		if (!query.isEmpty()){
			crit.add(Restrictions.or(Restrictions.like("nombre", query, MatchMode.ANYWHERE).ignoreCase(),
					 Restrictions.like("login", query, MatchMode.ANYWHERE).ignoreCase()));
		}
		
		crit.setProjection(Projections.rowCount());
		Long totalCount = (Long) crit.uniqueResult();
		crit.setProjection(null);

		crit.setFirstResult(egr.getStart());

		if (!egr.getSort().isEmpty())
			if (egr.getDir().equalsIgnoreCase("desc")){
				crit.addOrder(Order.desc(egr.getSort()));
			} else {
				crit.addOrder(Order.asc(egr.getSort()));
			}	
		if(egr.getLimit() > 0){
			crit.setMaxResults(egr.getLimit());	
		}
		
		List list = crit.list();

		BaseSearchResult result = new BaseSearchResult();
		result.setResults(list);
		result.setTotalCount(totalCount);
		
		return result;
	}
		
	public Set<Rol> obtenerRoles(long id_Usuario){
		return (Set<Rol>) ((Usuario)obtener(Usuario.class,id_Usuario)).getRoles();
	}
	
	public void eliminarRol(long id_Usuario, long id_Rol){
		obtenerRoles(id_Usuario).remove(obtener(Rol.class, id_Rol));
	}
	public void adicionarRol(long id_Usuario, long id_Rol){
		obtenerRoles(id_Usuario).add((Rol) obtener(Rol.class, id_Rol));
	}
	
	public Usuario obtenerUsuario(String nombre){
		String hql = "from Usuario u where u.login = :usuario";
		Query query = getCurrentSession().createQuery(hql);
		query.setString("usuario", nombre);
		Usuario user = (Usuario)query.uniqueResult();
		
		return user;
	}
}
