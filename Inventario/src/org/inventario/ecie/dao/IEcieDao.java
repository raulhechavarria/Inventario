package org.inventario.ecie.dao;

import java.io.Serializable;
import java.util.Date;

import org.archetype.common.dao.ICommonDao;
import org.archetype.common.domain.BaseSearchResult;

public interface IEcieDao extends ICommonDao {
	public BaseSearchResult listarValeSinConfirmar(Date fechaEcie);
	public BaseSearchResult listarValeDadoFecha(Date date);
	public Object ObtenerInventario(Date fecha, Serializable idAlmacen);
	public BaseSearchResult listarAlmacen();
}
