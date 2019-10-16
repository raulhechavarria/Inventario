package org.inventario.ecie.dao.support;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.archetype.common.dao.support.CommonDao;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.inventario.ecie.dao.IValeDao;
import org.inventario.ecie.domain.Almacen;
import org.inventario.ecie.domain.EstadoVale;
import org.inventario.ecie.domain.Producto;
import org.inventario.ecie.domain.Tarjetero;
import org.inventario.ecie.domain.TipoVale;
import org.inventario.ecie.domain.Vale;
import org.inventario.ecie.domain.ValeProducto;
import org.inventario.ecie.web.utils.MetComunes;


public class ValeDao extends CommonDao implements IValeDao {
	
	public Object obtenerSolicitudDadoNoControl(String nocontrol){
		String query = "from Vale v where v.noControl =:nocontrol and v.tipoVale = 'SolicitudEntrega'";
		Map properties = new HashMap();
		properties.put("nocontrol", nocontrol);
		return obtenerHQL(query, properties);
	}
	
	public Object obtenerReclamacionDadoNoControl(Vale vale){
		String query = "from Vale v where v.noDoc =:nodoc and v.tipoVale = 'Reclamacion'";
		Map properties = new HashMap();
		properties.put("nodoc", vale.getNoDoc());
		return obtenerHQL(query, properties);
	}
	@SuppressWarnings("unchecked")
	public BaseSearchResult listarFecha(String tip){
		String query = "select distinct v.fechaVale from Vale v where v.tipoVale =:tip order by v.fechaVale desc";
		Map properties = MetComunes.LLenarproperties(tip);
		return listarHQL(query, properties, null);
	}

	@SuppressWarnings("all")

	public BaseSearchResult listarValeDadoTipoVale(String tip,Date fecha,ExtGridRequest egr) {
		String query = "from Vale v where v.tipoVale =:tip and v.fechaVale=:fecha order by v.id desc";
		Map properties = MetComunes.LLenarproperties(tip);
		properties.put("fecha", fecha);
		return listarHQL(query, properties, egr);
	}
	
	public BaseSearchResult listarSoliAnno(ExtGridRequest egr,String subCodigo) {
		Criteria crit =  getCurrentSession().createCriteria(Vale.class);
		crit.add(Restrictions.like("noControl", subCodigo, MatchMode.START).ignoreCase());
		//crit.add(Restrictions.like("tipoVale", "SolicitudEntrega", MatchMode.START).ignoreCase());
		if (!egr.getSort().isEmpty()){
			if (egr.getDir().equalsIgnoreCase("desc")){
				crit.addOrder(Order.desc(egr.getSort()));
			} else {
				crit.addOrder(Order.asc(egr.getSort()));
			}
		}
		
		List list = null;
		
		if(egr.getLimit() > 0){
			crit.setMaxResults(egr.getLimit());
			list = crit.list();
		}
		
		BaseSearchResult bsr = new BaseSearchResult();
		bsr.setResults(list);
		bsr.setTotalCount(new Long(crit.list().size()));
		return bsr;
	}
	
	public BaseSearchResult listarValeDadoTipoVale(String tip){
		String query = "from Vale v where v.tipoVale =:tip order by v.id desc";
		Map properties = MetComunes.LLenarproperties(tip);
		return listarHQL(query, properties, null);
	}
	
	public Vale ValeSinConfirmar() {
		String query = "from Vale v where v.estadoVale != 'Cancelado' and v.estadoVale != 'Confirmado'";
		return (Vale)obtenerHQL(query, null);
	}
	
	public BaseSearchResult listarSolicitudesConfirmados(Date fecha) {
		Criteria crit =  getCurrentSession().createCriteria(Vale.class);
		BaseSearchResult bsr = new BaseSearchResult();
		bsr.setResults(crit.list());
		bsr.setTotalCount(new Long(crit.list().size()));
		
		String query = "";
		Map properties = new HashMap();
		properties.put("fecha", fecha);
		return listarHQL(query, properties, null);
	}
	

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
	
	
	public Object ObtUltimoVale(){		
		String query= "from Vale v";
		BaseSearchResult bsr = super.listarHQL(query, null, null);				
		Collection<Object> lista = bsr.getResults();
		long max = 0;
		Vale v = new Vale();
		for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
			Vale item = (Vale) iterator.next();				
			if (max < item.getId()) {
				max = item.getId();
				v = item;
			} 	
		}
		return v;
	}
	
	public Vale ObtenerNoDoc(String noDoc){
		String query = "from Vale v where v.noDoc =:noDoc and v.tipoVale = 'Reclamacion'";
		Map properties = new HashMap();		
		properties.put("noDoc", noDoc);
		return (Vale)obtenerHQL(query, properties);
	}
	
	public BaseSearchResult listarVale(Date fecha) {
		String query = "from Vale v where v.fechaVale =:fecha";
		Map properties = new HashMap();		
		properties.put("fecha", fecha);
		return listarHQL(query, properties, null);
	}
	
	public BaseSearchResult listarValeAlmacen(Date fecha, Serializable idAlmacen) {
		String query = "from Vale v where v.fechaVale =:fecha and (v.";
		Map properties = new HashMap();		
		properties.put("fecha", fecha);
		return listarHQL(query, properties, null);
	}
	
	public BaseSearchResult listarValeMesAnno(Date fecha) {
		String query = "from Vale v where year(v.fechaVale)=:anno and month(v.fechaVale)=:mes";
		Map properties = new HashMap();
		properties.put("anno", fecha.getYear() + 1900);
		properties.put("mes", fecha.getMonth() + 1);
		return listarHQL(query, properties, null);
	}

	@Override
	public BaseSearchResult listarEntreFecha(String tipo, Date dateIni,
			Date dateFin,ExtGridRequest egr) {
		String query = "from Vale v where v.tipoVale =:tip and v.fechaVale between :fechaIni and :fechaFin";
		Map properties = MetComunes.LLenarproperties(tipo);
		properties.put("fechaIni", dateIni);
		properties.put("fechaFin", dateFin);
		BaseSearchResult bsr = listarHQL(query, properties, egr);	
		return bsr;
	}

	

	
}
