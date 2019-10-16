package org.inventario.ecie.dao.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.archetype.common.dao.support.CommonDao;
import org.archetype.common.domain.BaseSearchResult;
import org.inventario.ecie.dao.IInventarioDao;
import org.inventario.ecie.domain.Inventario;
import org.inventario.ecie.domain.Producto;
import org.inventario.ecie.domain.Tarjetero;

public class InventarioDao extends CommonDao implements IInventarioDao {

	@SuppressWarnings( { "deprecation", "unchecked" })
	public BaseSearchResult listarInventario(Date fecha) {
		String query = "from Inventario i where year(i.fecha)=:anno and month(i.fecha)=:mes order by i.fecha";
		Map properties = new HashMap();
		properties.put("anno", fecha.getYear() + 1900);
		properties.put("mes", fecha.getMonth() + 1);
		return listarHQL(query, properties, null);
	}
	
	public BaseSearchResult ValesMes(Date fecha) {
		String query = "from Vale v where year(v.fechaVale)=:anno and month(v.fechaVale)=:mes";
		Map properties = new HashMap();
		properties.put("anno", fecha.getYear() + 1900);
		properties.put("mes", fecha.getMonth() + 1);
		return listarHQL(query, properties, null);
	}

	@SuppressWarnings("unchecked")
	public Inventario obtenerDadoFechaAlmacen(Date fecha, Serializable idalmacen) {
		String query = "from Inventario i where i.fecha=:fecha and i.almacen.id=:idalmacen";
		Map properties = new HashMap();
		properties.put("fecha", fecha);
		properties.put("idalmacen", idalmacen);
		return (Inventario) obtenerHQL(query, properties);
	}
	
	@SuppressWarnings("all")
	public BaseSearchResult listarMovimientoMes(){
		String query106 = "from Producto p where p.clasificadorProducto =: clasifipro";
		Map properties106 = new HashMap();
		properties106.put("clasifipro", 106);
		BaseSearchResult bsrproducto = listarHQL(query106, properties106, null);
		Collection<Object> listapro = bsrproducto.getResults();	
		Collection<Object> listaCollection = new ArrayList<Object>();;
		for (Iterator iterator = listapro.iterator(); iterator.hasNext();) {
			Producto producto = (Producto) iterator.next();
		String queryTar = "select count(t.id) as cant, t.producto from Tarjetero t where year(t.fechaCambio)=:anno and month(t.fechaCambio)=:mes and t.producto.id =:idProducto";	
		Map propertiesTar = new HashMap();
		propertiesTar.put("idProducto", producto.getId());	
		propertiesTar.put("anno",2011);
		propertiesTar.put("mes", "04");
		Object e = obtenerHQL(queryTar, propertiesTar);
		listaCollection.add(e);	
		}
		BaseSearchResult baseSearchResult = new BaseSearchResult();
		baseSearchResult.setResults(listaCollection);
		baseSearchResult.setTotalCount((long)listaCollection.size());
		return baseSearchResult;
	}
}
