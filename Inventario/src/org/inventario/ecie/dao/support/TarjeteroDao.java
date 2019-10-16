package org.inventario.ecie.dao.support;

import org.archetype.common.dao.support.CommonDao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.inventario.ecie.dao.ITarjeteroDao;
import org.inventario.ecie.domain.Almacen;
import org.inventario.ecie.domain.EstadoVale;
import org.inventario.ecie.domain.Producto;
import org.inventario.ecie.domain.Tarjetero;
import org.inventario.ecie.domain.Vale;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import com.sun.org.apache.bcel.internal.generic.NEW;

@SuppressWarnings("all")
@Repository
public class TarjeteroDao extends CommonDao implements ITarjeteroDao {

	public BaseSearchResult listarTarjeteroExistenciaImprimir() {
		String query = "from Tarjetero t order by t.almacen.nombre, t.producto.clasificadorProducto.nombre, t.producto.codigo, t.fechaCambio, t.id";
	
		BaseSearchResult bsr = super.listarHQL(query, null, null);
		Collection<Object> lista = bsr.getResults();
		Tarjetero tarjetero = (Tarjetero) lista.toArray()[0];
		Producto producto = ((Tarjetero) lista.toArray()[0]).getProducto();
		Almacen almacen = ((Tarjetero) lista.toArray()[0]).getAlmacen();
		BaseSearchResult bsrResult = new BaseSearchResult();
		Collection<Tarjetero> tarjeterolist = new ArrayList<Tarjetero>();

		for (Object item : lista) {
			Hibernate.initialize(((Tarjetero) item).getProducto());
			Hibernate.initialize(((Tarjetero) item).getAlmacen());
		}
		Tarjetero lastTarjeta = new Tarjetero();
		for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
			Tarjetero item = (Tarjetero) iterator.next();

			if ((almacen.getCodigo().contentEquals(item.getAlmacen()
					.getCodigo()))
					&& (producto.getCodigo().contentEquals(item.getProducto()
							.getCodigo()))) {
				tarjetero = item;
			} else {
				if (tarjetero.getCantExist() != 0) {
					tarjeterolist.add(tarjetero);
				}
				producto = item.getProducto();
				almacen = item.getAlmacen();
				tarjetero = item;
			}
		}
		bsrResult.setResults(tarjeterolist);

		bsrResult.setTotalCount(new java.lang.Long(new Integer(tarjeterolist
				.size()).toString()));
		return bsrResult;
	}

	public BaseSearchResult listarExistencia(Serializable idclasificadorproducto, ExtGridRequest egr) {
	String query = "from Tarjetero t where t.producto.clasificadorProducto.id =:IdCalsificador and t.fechaCambio = (select max(tr.fechaCambio) from Tarjetero tr where tr.producto = t.producto) and t.cantExist > 0 and t.version = (select max(tv.version) from Tarjetero tv where tv.producto = t.producto) order by t.almacen.nombre, t.producto.clasificadorProducto.nombre, t.producto.codigo, t.fechaCambio, t.id";
		Map properties = new HashMap();
		properties.put("IdCalsificador", idclasificadorproducto);
		BaseSearchResult bsr = listarHQL(query, properties, egr);
		String totalcoun = "select count(t.id) from Tarjetero t where t.producto.clasificadorProducto.id =:IdCalsificador and t.fechaCambio = (select max(tr.fechaCambio) from Tarjetero tr where tr.producto = t.producto) and t.cantExist > 0 and t.version = (select max(tv.version) from Tarjetero tv where tv.producto = t.producto)";
		Long totalCount = (Long) obtenerHQL(totalcoun, properties);
		bsr.setTotalCount(totalCount);
		return bsr;
	}

	public BaseSearchResult listarOciosos(Serializable idclasificadorproducto) {
		String query = "from Tarjetero t where t.producto.clasificadorProducto.id =:IdCalsificador and (t.fechaCambio = (select max(tr.fechaCambio) from Tarjetero tr where tr.producto = t.producto) and "+
                       "t.fechaCambio <= current_date - 365) and t.cantExist > 0 and t.version = (select max(tv.version) from Tarjetero tv where tv.producto = t.producto) order by t.almacen.nombre, t.producto.clasificadorProducto.nombre, t.producto.codigo, t.fechaCambio, t.version";
			Map properties = new HashMap();
			properties.put("IdCalsificador", idclasificadorproducto);
			BaseSearchResult bsr = listarHQL(query, properties, null);
/*			String totalcoun = "select count(t.id) from Tarjetero t where t.producto.clasificadorProducto.id =:IdCalsificador and t.fechaCambio = (select max(tr.fechaCambio) from Tarjetero tr where tr.producto = t.producto) and t.cantExist > 0 and t.version = (select max(tv.version) from Tarjetero tv where tv.producto = t.producto)";
			Long totalCount = (Long) obtenerHQL(totalcoun, properties);
			bsr.setTotalCount(totalCount);
*/			return bsr;
		}	
	
	public Object ObtenerTarjetaAnterior(Tarjetero tarjetero) {

		String query = "from Tarjetero t where t.almacen.id =:idal  and t.producto.id =:idprod";
		Map properties = new HashMap();
		properties.put("idal", tarjetero.getAlmacen().getId());
		properties.put("idprod", tarjetero.getProducto().getId());

		BaseSearchResult bsr = super.listarHQL(query, properties, null);
		Collection<Object> lista = bsr.getResults();

		long last = 0;
		Tarjetero trg = new Tarjetero();
		for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
			Tarjetero item = (Tarjetero) iterator.next();
			if (last < item.getId()) {
				last = item.getId();
				trg = item;
			}
		}
		return trg;
	}

	@SuppressWarnings("unchecked")
	public BaseSearchResult listarValeProducto(Serializable idvale) {
		String query = "select vp from ValeProducto vp where vp.vale.id =:idvale";
		Map properties = new HashMap();
		properties.put("idvale", idvale);
		return listarHQL(query, properties, null);
	}

	public BaseSearchResult listarTarjetero(Serializable idproducto) {
		String query = "select t from Tarjetero t where t.producto.id =:idproducto order by t.almacen.id, t.fechaCambio, t.version";
		Map properties = new HashMap();
		properties.put("idproducto", idproducto);
		return listarHQL(query, properties, null);
	}

	public BaseSearchResult listarTarjeteroFecha(Serializable idproducto,
			                                     Date fechaIni, Date fechaFin)  {
		String query = "select t from Tarjetero t where t.producto.id = :idproducto and t.fechaCambio >= :fechaIni and t.fechaCambio <= :fechaFin order by t.almacen.id, t.fechaCambio, t.version";
		Map properties = new HashMap();
		properties.put("idproducto", idproducto);
		properties.put("fechaIni", fechaIni);
		properties.put("fechaFin", fechaFin);
		return listarHQL(query, properties, null);
	}
	
	public BaseSearchResult listarTarjetero(Date fecha) {

		String query = "select t from Tarjetero t where t.fechaCambio =:fecha order by t.producto.id, t.id desc";
		Map properties = new HashMap();
		properties.put("fecha", fecha);
		return listarHQL(query, properties, null);
	}

	public BaseSearchResult listarTarjetero(Serializable idproducto,
			ExtGridRequest egr) {
		String query = "select t from Tarjetero t where t.producto.id =:idproducto order by t.version desc";
		Map properties = new HashMap();
		properties.put("idproducto", idproducto);
		BaseSearchResult bsr = listarHQL(query, properties, egr);
		String totalcoun = "select count(t.id) from Tarjetero t where t.producto.id =:idproducto";
		Long totalCount = (Long) obtenerHQL(totalcoun, properties);
		bsr.setTotalCount(totalCount);
		return bsr;
	}

	public Object ObtenerTarjeta(Serializable idpro) {
		String query = "from Tarjetero t where t.producto.id =:idprod";
		Map properties = new HashMap();
		properties.put("idprod", idpro);
		return obtenerHQL(query, properties);
	}

	public BaseSearchResult listarTarjeteroImprimir() {
		String query = "from Tarjetero t order by t.almacen.nombre, t.producto.clasificadorProducto.nombre, t.producto.codigo, t.fechaCambio, t.id";
		Map properties = new HashMap();
		// properties.put("idvale", idvale);
		return listarHQL(query, properties, null);
	}

}
