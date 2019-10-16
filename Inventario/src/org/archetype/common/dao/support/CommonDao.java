package org.archetype.common.dao.support;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.archetype.common.dao.ICommonDao;
import org.archetype.common.dao.IComponentExcludeAssociation;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.annotations.Type;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

@SuppressWarnings("all")
@Repository
public class CommonDao extends SpringHibernateSessionFactorySupport implements ICommonDao{
	
	public void adicionar(Object object) {
		getCurrentSession().save(object);
	}
	
	public void actualizar(Object object) {
		getCurrentSession().merge(object);		
	}
		
	public void adicionar_actualizar(Object object){
		getCurrentSession().saveOrUpdate(object);
	}
	
	public void eliminar(Class clazz,Serializable id) {		
		Object del = getCurrentSession().load(clazz, id);
		getCurrentSession().delete(del);						
	}
	
	public void eliminar(Object obj) {		
		getCurrentSession().delete(obj);						
	}
		
	public Object obtener(Class clazz, Serializable id){
		return getCurrentSession().load(clazz, id);
	}	
	
	protected Criterion initialize(Object query){
		return null;
	}
		
	public BaseSearchResult listar(Class clazz, ExtGridRequest egr){
		Object query = egr.getQuery();
		
		Criteria criteriaTotal = getCurrentSession().createCriteria(clazz);
		Criteria crit = getCurrentSession().createCriteria(clazz);
		
		Criterion criterion = initialize(query);
		if(criterion != null){
			criteriaTotal.add(criterion);
			crit.add(criterion);
		}
		
		if (query != null){
			Example example = Example.create(query)
			.excludeZeroes() //exclude zero valued properties
			.ignoreCase() //perform case insensitive string comparisons
			.enableLike(MatchMode.ANYWHERE); //use like for string comparisons
			crit.add(example);
			criteriaTotal.add(example);
			addExampleAsociations(crit, query);
			addExampleAsociations(criteriaTotal, query);
		}
		
		criteriaTotal.setProjection(Projections.rowCount());
		Long totalCount = (Long)criteriaTotal.uniqueResult();
		
		crit.setFirstResult(egr.getStart());

		if (!egr.getSort().isEmpty()){
			if (egr.getDir().equalsIgnoreCase("desc")){
				crit.addOrder(Order.desc(egr.getSort()));
			} else {
				crit.addOrder(Order.asc(egr.getSort()));
			}
		}
		
		List list = null;
		
		if(egr.getLimit() > 0){
			crit.setMaxResults(egr.getLimit());
			list = crit.list();
		} else {
			list = new LinkedList();
			crit.setMaxResults(20);
			
			for (int i = 0; i < totalCount; i+=20) {
				crit.setFirstResult(i);
				list.addAll(crit.list());
			}
		}
		
		BaseSearchResult result = new BaseSearchResult();
		result.setResults(list);
		result.setTotalCount(totalCount);
		
		return result;
	}
	
	//obtener un listado de elementos usando un hql	
	protected BaseSearchResult listarHQL(String query, Map properties, ExtGridRequest egr) {
		Query q = getCurrentSession().createQuery(query);
		if(properties != null){
			q.setProperties(properties);
		}
		
		if(egr != null){
			if(egr.getLimit() > 0){
				q.setMaxResults(egr.getLimit());
			}
			q.setFirstResult(egr.getStart());
		}
		List list = q.list();
		
		BaseSearchResult result = new BaseSearchResult();
		result.setResults(list);
		
		if(egr != null){
			int pos = query.toUpperCase().indexOf("FROM");
			String qtotal = "select count(*) "+query.substring(pos);
			if(qtotal.toUpperCase().indexOf("ORDER BY") != -1){
				qtotal = qtotal.substring(0, qtotal.toUpperCase().indexOf("ORDER BY"));
			}
			if(qtotal.toUpperCase().indexOf("GROUP BY") != -1){
				qtotal = qtotal.substring(0, qtotal.toUpperCase().indexOf("GROUP BY"));
			}
			
			Long total = (Long) obtenerHQL(qtotal, properties);
			result.setTotalCount(total);
		} else {
			result.setTotalCount(new Long(list.size()));
		}
		
		return result;
	}
	
	//ejecutar una accion de update o delete usando un hql
	protected Object ejecutarHQL(String q, Map properties) {
		Query query = getCurrentSession().createQuery(q);
		if(properties != null){
			query.setProperties(properties);
		}
		return query.executeUpdate();
	}
	
	//obtener un elemento usando un hql
	protected Object obtenerHQL(String q, Map properties) {
		Query query = getCurrentSession().createQuery(q);
		if(properties != null){
			query.setProperties(properties);
		}
		return query.uniqueResult();
	}
	
	private void allfields(Class c, LinkedList list){
		Field fields[] = c.getDeclaredFields();
		if(fields.length != 0){
			for (int i = 0; i < fields.length; i++) {
				list.add(fields[i]);
			}
		}
		if(c.getSuperclass() != null){
			allfields(c.getSuperclass(), list);
		}
	}
	
	private void addExampleAsociations(Criteria crit, Object obj){
		LinkedList list = new LinkedList();

		allfields(obj.getClass(), list);
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();
			try {
				if(!isStandardProperty(field.getType())){
					field.setAccessible(true);
					Object query = field.get(obj);
					if(query != null){
						Criteria critExample = crit.createCriteria(field.getName());
						Criterion criterion = initialize(query);
						if(criterion != null){
							critExample.add(criterion);
						}
						if(!(field.isAnnotationPresent(Type.class) && 
								field.getAnnotation(Type.class).type().equals("Component"))){
							critExample.add( Example.create(query));
							addExampleAsociations(critExample, query);
						}
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean isStandardProperty(Class clazz) {
    	return clazz.isPrimitive()                  ||
    		clazz.isAssignableFrom(Byte.class)      ||
    		clazz.isAssignableFrom(Short.class)     ||
    		clazz.isAssignableFrom(Integer.class)   ||
    		clazz.isAssignableFrom(Long.class)      ||
    		clazz.isAssignableFrom(Float.class)     ||
    		clazz.isAssignableFrom(Double.class)    ||
    		clazz.isAssignableFrom(Character.class) ||
    		clazz.isAssignableFrom(String.class)    ||
    		clazz.isAssignableFrom(Boolean.class)	||
    		clazz.isAssignableFrom(Date.class)		||
    		clazz.isEnum();
    }
}
