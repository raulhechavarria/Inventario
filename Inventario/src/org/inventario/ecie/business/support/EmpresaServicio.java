package org.inventario.ecie.business.support;

import java.io.Serializable;

import org.archetype.common.business.support.CommonService;
import org.inventario.ecie.business.IEmpresaServicio;
import org.inventario.ecie.domain.Empresa;


public class EmpresaServicio extends CommonService implements IEmpresaServicio {
	
	public Empresa obtenerEmpresa(Serializable id) {
		return (Empresa) getDao().obtener(Empresa.class, id);
	}
}
