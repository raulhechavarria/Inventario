package org.inventario.ecie.dao.support;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.archetype.common.dao.support.CommonDao;
import org.archetype.common.domain.BaseSearchResult;
import org.hibernate.Criteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.inventario.ecie.dao.IEcieDao;
import org.inventario.ecie.domain.EstadoVale;
import org.inventario.ecie.domain.Vale;

public class EcieDao extends CommonDao implements IEcieDao {
	
	@SuppressWarnings("unchecked")
	public BaseSearchResult listarValeDadoFecha(Date date){
		String query = "from Vale v where v.fechaVale=:fecha order by v.noControl, v.id ";
		Map properties = new HashMap();		
		properties.put("fecha", date);
		return listarHQL(query, properties, null);	
	}
	
	public BaseSearchResult listarAlmacen(){
		String query = "from Almacen v";	
		return listarHQL(query, null, null);	
	}
	
	public BaseSearchResult listarValeSinConfirmar(Date fechaEcie){
	//	Property Estado = Property.forName("estadoVale");
		Criteria crit =  getCurrentSession().createCriteria(Vale.class);
		crit.add( Property.forName("estadoVale").in( new EstadoVale[] { EstadoVale.Revision, EstadoVale.Confeccion } ) );
		crit.add(Restrictions.eq("fechaVale", fechaEcie));
		BaseSearchResult bsr = new BaseSearchResult();
		bsr.setResults(crit.list());
		bsr.setTotalCount(new Long(crit.list().size()));
		//	Object obj = crit.uniqueResult();
		//String query = "select v.fechaVale, v.tipoVale from Vale v " +
		return bsr;
	}
	
	@SuppressWarnings("unchecked")
	public Object ObtenerInventario(Date fecha, Serializable idAlmacen) {

		String query = "from Inventario i where i.almacen.id = :idAlmacen and i.fecha = :fecha";
		Map properties = new HashMap();
		properties.put("idAlmacen", idAlmacen);
		properties.put("fecha", fecha);
		return obtenerHQL(query, properties);
	}
}
