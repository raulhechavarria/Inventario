package org.archetype.common.business;

import java.io.Serializable;

import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;

@SuppressWarnings("unchecked")
public interface ICommonService {
	public void adicionar(Object object);
	public void eliminar(Serializable id);
	public void actualizar(Object object);
	public void adicionarActualizar(Object object);
	public Object obtener(Serializable id);
	public BaseSearchResult listar(ExtGridRequest egr);
	public Class getClazz();
}
