package org.inventario.ecie.dao.support;

import java.util.List;

import org.apache.poi.hssf.record.formula.functions.Isnontext;
import org.apache.poi.hssf.record.formula.functions.Isnumber;
import org.archetype.common.dao.support.CommonDao;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.inventario.ecie.dao.IProductoDao;
import org.inventario.ecie.domain.Producto;

@SuppressWarnings("all")
public class ProductoDao extends CommonDao implements IProductoDao {

	public BaseSearchResult listarProducto(String subCodigo,ExtGridRequest egr){
		Criteria critcodigo =  getCurrentSession().createCriteria(Producto.class);
		critcodigo.add(Restrictions.like("codigo", subCodigo, MatchMode.START).ignoreCase());
		Criteria critnombre =  getCurrentSession().createCriteria(Producto.class);
		critnombre .add(Restrictions.like("nombre", subCodigo, MatchMode.START).ignoreCase());
if (critcodigo.list().size() > 0 ) {
	if (!egr.getSort().isEmpty()){
		if (egr.getDir().equalsIgnoreCase("desc")){
			critcodigo.addOrder(Order.desc(egr.getSort()));
		} else {
			critcodigo.addOrder(Order.asc(egr.getSort()));
		}
	}
	
	List list = null;
	
	if(egr.getLimit() > 0){
		critcodigo.setMaxResults(egr.getLimit());
		list = critcodigo.list();
	}
	
	BaseSearchResult bsr = new BaseSearchResult();
	bsr.setResults(list);
	bsr.setTotalCount(new Long(critcodigo.list().size()));
	return bsr;
} else {
	if (!egr.getSort().isEmpty()){
		if (egr.getDir().equalsIgnoreCase("desc")){
			critnombre.addOrder(Order.desc(egr.getSort()));
		} else {
			critnombre.addOrder(Order.asc(egr.getSort()));
		}
	}
	
	List list = null;
	
	if(egr.getLimit() > 0){
		critnombre.setMaxResults(egr.getLimit());
		list = critnombre.list();
	}
	
	BaseSearchResult bsr = new BaseSearchResult();
	bsr.setResults(list);
	bsr.setTotalCount(new Long(critnombre.list().size()));
	return bsr;
}
		
		
	}
}
