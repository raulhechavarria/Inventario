package org.inventario.ecie.dao;

import org.archetype.common.dao.ICommonDao;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;

public interface IProductoDao extends ICommonDao {
	public BaseSearchResult listarProducto(String subCodigo,ExtGridRequest egr);
	
}
