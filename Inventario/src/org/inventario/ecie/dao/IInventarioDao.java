package org.inventario.ecie.dao;

import org.archetype.common.dao.ICommonDao;
import org.archetype.common.domain.BaseSearchResult;
import org.inventario.ecie.domain.Almacen;
import org.inventario.ecie.domain.Inventario;
import org.inventario.ecie.domain.ValeProducto;

import java.io.Serializable;
import java.util.Date;


public interface IInventarioDao extends ICommonDao {
	public BaseSearchResult listarInventario(Date fecha);
	public Inventario obtenerDadoFechaAlmacen(Date fecha,Serializable idalmacen);
	public BaseSearchResult listarMovimientoMes();
	public BaseSearchResult ValesMes(Date fecha);
}
