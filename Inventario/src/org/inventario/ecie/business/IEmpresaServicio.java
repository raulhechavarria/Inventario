package org.inventario.ecie.business;

import java.io.Serializable;

import javax.jws.WebService;
import org.archetype.common.business.ICommonService;
import org.inventario.ecie.domain.Empresa;

@WebService
public interface IEmpresaServicio extends ICommonService{	
	public Empresa obtenerEmpresa(Serializable id);
}
