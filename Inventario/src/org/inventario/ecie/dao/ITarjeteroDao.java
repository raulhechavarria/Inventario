package org.inventario.ecie.dao;

import java.io.Serializable;
import java.util.Date;

import org.archetype.common.dao.ICommonDao;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.inventario.ecie.domain.Tarjetero;

public interface ITarjeteroDao extends ICommonDao {
	public Object ObtenerTarjetaAnterior(Tarjetero tarjetero);
	public BaseSearchResult listarValeProducto(Serializable idvale);
	public BaseSearchResult listarTarjeteroImprimir();
	public BaseSearchResult listarTarjetero(Serializable idproducto);
	public BaseSearchResult listarTarjeteroExistenciaImprimir();
	public BaseSearchResult listarExistencia(Serializable idclasificadorproducto,ExtGridRequest egr);
	public BaseSearchResult listarTarjetero(Date fecha);
	public BaseSearchResult listarTarjetero(Serializable idproducto,ExtGridRequest egr);
	public BaseSearchResult listarOciosos(Serializable idclasificadorproducto);
	public BaseSearchResult listarTarjeteroFecha(Serializable idproducto, Date fechaIni, Date fechaFin);
}
