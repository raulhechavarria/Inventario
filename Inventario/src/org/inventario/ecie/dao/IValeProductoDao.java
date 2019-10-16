package org.inventario.ecie.dao;

import java.io.Serializable;

import org.archetype.common.dao.ICommonDao;
import org.archetype.common.domain.BaseSearchResult;
import org.inventario.ecie.domain.Almacen;
import org.inventario.ecie.domain.ValeProducto;

public interface IValeProductoDao extends ICommonDao {
	public Object ObtUltimoRegTarjeta(ValeProducto vp);
	public BaseSearchResult listarValeProducto(Serializable idvale, String tip);
	public Object ObtUltimoRegTarjeta(ValeProducto vp, Almacen almacen);
	public BaseSearchResult listarValeProducto(Serializable idvale);
	
}
