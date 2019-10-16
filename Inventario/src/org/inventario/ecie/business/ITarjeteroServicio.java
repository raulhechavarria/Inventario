package org.inventario.ecie.business;

import java.io.Serializable;
import java.util.Date;

import javax.jws.WebService;

import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.inventario.ecie.domain.ClasificadorProducto;
import org.inventario.ecie.domain.Ecie;
import org.inventario.ecie.domain.Producto;
import org.inventario.ecie.domain.Tarjetero;

@WebService
public interface ITarjeteroServicio {
	public void adicionarTarjetero(Object object)throws Exception;
	public void adicionarTarjeteroTrans(Tarjetero obj)throws Exception;
	public BaseSearchResult listarTarjetero(Date fecha);
	public BaseSearchResult listarTarjetero(Serializable idproducto,ExtGridRequest egr);
	public void addCargaInicial(Object object);
	public void actualizarAlmacen(Serializable id);
	public void PasarATarjetero(Serializable idVale)throws Exception;
	public BaseSearchResult listarTarjeteroImprimir();
	public BaseSearchResult listarTarjeteroAsc(Serializable idproducto);
	public BaseSearchResult listarExistenciaImprimir() ;
	public BaseSearchResult listarExistencia(Serializable idclasificador, ExtGridRequest egr);
	public ClasificadorProducto obtenerFamilia(Serializable idClasificadorProducto);
	public Tarjetero obtenerProductoTarjetero(Serializable id);
	public BaseSearchResult listarOciosos(Serializable idclasificador,ExtGridRequest egr);
	public BaseSearchResult listarTarjeteroFecha(Serializable idproducto, Date fechaIni, Date fechaFin);
	public Producto obtenerProducto(Serializable id);
}
