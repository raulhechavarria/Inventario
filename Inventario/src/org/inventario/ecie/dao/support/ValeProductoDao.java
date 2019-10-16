package org.inventario.ecie.dao.support;

import org.archetype.common.dao.support.CommonDao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.inventario.ecie.dao.ITarjeteroDao;
import org.inventario.ecie.dao.IValeProductoDao;
import org.inventario.ecie.domain.Almacen;
import org.inventario.ecie.domain.Tarjetero;
import org.inventario.ecie.domain.TipoVale;
import org.inventario.ecie.domain.ValeProducto;
import org.inventario.ecie.web.utils.MetComunes;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import com.sun.org.apache.bcel.internal.generic.NEW;

@SuppressWarnings("all")
@Repository
public class ValeProductoDao extends CommonDao implements IValeProductoDao {
	
	@SuppressWarnings("unchecked")
	public Object ObtUltimoRegTarjeta(ValeProducto vp, Almacen almacen){
		
		String query= "from Tarjetero t where t.producto.id =:idprod and t.almacen.id =:idalm";
		Map properties = new HashMap();
		properties.put("idprod", vp.getProducto().getId());
		properties.put("idalm", almacen.getId());
		BaseSearchResult bsr = super.listarHQL(query, properties, null);
				
		Collection<Object> lista = bsr.getResults();
		long max = 0;
		Tarjetero trg = new Tarjetero();
		for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
				Tarjetero item = (Tarjetero) iterator.next();				
			if (max < item.getId()) {
				max = item.getId();
				trg = item;
			} 
			
		}
		return trg;
	}
	public Object ObtUltimoRegTarjeta(ValeProducto vp){
		
		String query= "from Tarjetero t where t.almacen.id =:idal and t.producto.id =:idprod";
		Map properties = new HashMap();
		properties.put("idprod", vp.getProducto().getId());
		BaseSearchResult bsr = super.listarHQL(query, properties, null);
				
		Collection<Object> lista = bsr.getResults();
		long max = 0;
		Tarjetero trg = new Tarjetero();
		for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
				Tarjetero item = (Tarjetero) iterator.next();				
			if (max < item.getId()) {
				max = item.getId();
				trg = item;
			} 
			
		}
		return trg;
	}
	public BaseSearchResult listarValeProducto(Serializable idvale, String tip) {
		String query = "select vp from ValeProducto vp where vp.vale.id =:idvale and vp.vale.tipoVale =:tip";
		Map properties = MetComunes.LLenarproperties(tip);	
		properties.put("idvale", idvale);
		return listarHQL(query, properties, null);
	}
	
	public BaseSearchResult listarValeProducto(Serializable idvale) {
		String query = "select vp from ValeProducto vp where vp.vale.id =:idvale";
		Map properties = new HashMap();		
		properties.put("idvale", idvale);
		return listarHQL(query, properties, null);
	}

}

