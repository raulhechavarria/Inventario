package org.archetype.common.dao;

import java.io.Serializable;

import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;

@SuppressWarnings("unchecked")
public interface ICommonDao {

	public void adicionar(Object object);
	public void eliminar(Class clazz,Serializable id);
	public void eliminar(Object obj);
	public void actualizar(Object object);
	public void adicionar_actualizar(Object object);
	public Object obtener(Class clazz,Serializable id);
	public BaseSearchResult listar(Class clazz, ExtGridRequest egr);
}
