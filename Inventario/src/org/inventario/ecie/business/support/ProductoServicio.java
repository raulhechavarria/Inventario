package org.inventario.ecie.business.support;

import org.archetype.common.business.support.CommonService;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.inventario.ecie.business.IProductoServicio;
import org.inventario.ecie.dao.IProductoDao;

public class ProductoServicio extends CommonService implements IProductoServicio {
	
	public BaseSearchResult listarProducto(String subCodigo,ExtGridRequest egr) {
		return ((IProductoDao) getDao()).listarProducto(subCodigo, egr);
	}
}
