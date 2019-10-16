package org.archetype.common.business.support;

import java.io.Serializable;

import org.archetype.common.business.ICommonService;
import org.archetype.common.dao.ICommonDao;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.inspektr.audit.annotation.Audit;
import org.springframework.transaction.annotation.Transactional;


@SuppressWarnings("unchecked")
@Transactional
public class CommonService implements ICommonService{

	private ICommonDao dao;
	/*
	 * El atributo clazz representa la clase del tipo de nomenclador 
	 * correpondiente y sera especificado mediante configuracion  
	 */
	private Class clazz;
		
	public ICommonDao getDao() {
		return dao;
	}

	public void setDao(ICommonDao dao) {
		this.dao = dao;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	@Audit(action="Actualizar", actionResolverName="DefaultAuditActionResolver", resourceResolverName="ParametersAsStringResourceResolver")
	public void actualizar(Object object) {
		dao.actualizar(object);		
	}
	
	@Audit(action="Adicionar", actionResolverName="DefaultAuditActionResolver", resourceResolverName="ParametersAsStringResourceResolver")
	public void adicionar(Object object) {
		dao.adicionar(object);
	}
	
	@Audit(action="Eliminar", actionResolverName="DefaultAuditActionResolver", resourceResolverName="ParametersAsStringResourceResolver")
	public void eliminar(Serializable id) {
		dao.eliminar(getClazz(),id);
	}
		
	@Transactional(readOnly = true)
	public BaseSearchResult listar(ExtGridRequest egr) {		
		return dao.listar(getClazz(), egr);
	}
		
	public Object obtener(Serializable id) {
		return dao.obtener(getClazz(), id);
	}
	
	@Audit(action="AdicionarActualizar", actionResolverName="DefaultAuditActionResolver", resourceResolverName="ParametersAsStringResourceResolver")
	public void adicionarActualizar(Object object) {
		dao.adicionar_actualizar(object);
	}
}
