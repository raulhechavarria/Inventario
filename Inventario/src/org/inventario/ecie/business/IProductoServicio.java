package org.inventario.ecie.business;

import javax.jws.WebService;
import org.archetype.common.business.ICommonService;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;

@WebService
public interface IProductoServicio extends ICommonService{	
	public BaseSearchResult listarProducto(String subCodigo,ExtGridRequest egr);
}
