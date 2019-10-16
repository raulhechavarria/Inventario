package org.inventario.ecie.web.controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.converters.LongConverter;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.archetype.common.web.controllers.CommonController;
import org.archetype.common.web.utils.ArcheTypeUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.inventario.ecie.business.IEcieServicio;
import org.inventario.ecie.business.ITarjeteroServicio;
import org.inventario.ecie.business.support.EcieServicio;
import org.inventario.ecie.business.support.ProductoServicio;
import org.inventario.ecie.domain.ClasificadorProducto;
import org.inventario.ecie.domain.Ecie;
import org.inventario.ecie.domain.Producto;
import org.inventario.ecie.domain.Tarjetero;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

public class TarjeteroController extends CommonController {

	private ProductoServicio productoServicio;
	private String listarExistenciaView,listarTarjeteroView;
	private String detallesExistenciaView,detallesTarjeteroView;
	private String rangoTarjeteroView;
	
	
	public ProductoServicio getProductoServicio() {
		return productoServicio;
	}

	public void setProductoServicio(ProductoServicio productoServicio) {
		this.productoServicio = productoServicio;
	}
	public String getDetallesExistenciaView() {
		return detallesExistenciaView;
	}

	public void setDetallesExistenciaView(String detallesExistenciaView) {
		this.detallesExistenciaView = detallesExistenciaView;
	}

	public String getDetallesTarjeteroView() {
		return detallesTarjeteroView;
	}

	public void setDetallesTarjeteroView(String detallesTarjeteroView) {
		this.detallesTarjeteroView = detallesTarjeteroView;
	}

	private IEcieServicio ecieServicio;
	
	public String getListarExistenciaView() {
		return listarExistenciaView;
	}

	public ModelAndView detallesExistencia(HttpServletRequest request, HttpServletResponse response)throws Exception {
		super.setDetallesView(getDetallesExistenciaView());
		return super.detalles(request, response);
	}
	
	public ModelAndView detallesTarjetero(HttpServletRequest request, HttpServletResponse response)throws Exception {
		super.setDetallesView(getDetallesTarjeteroView());
		return super.detalles(request, response);
	}
	public void setListarExistenciaView(String listarExistenciaView) {
		this.listarExistenciaView = listarExistenciaView;
	}
	public ModelAndView mostrarListaTarjetero(HttpServletRequest request, HttpServletResponse response)throws Exception {
		super.setListarView(getListarTarjeteroView());
		return super.mostrarLista(request, response);
	}
	public ModelAndView mostrarListaExistencia(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setListarView(getListarExistenciaView());
		return super.mostrarLista(request, response);
	}

	public ModelAndView listarExistencia(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {

		Long idclasificador = ArcheTypeUtils.getIdParameter(request, "idClasificador");
		JSONObject json = new JSONObject();
		
		if (!(idclasificador == null)) {
			BaseSearchResult bsr = ((ITarjeteroServicio) getServicio()).listarExistencia(idclasificador,egr);
			Collection<Object> lista = bsr.getResults();

			
			JSONArray data = new JSONArray();

			for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
				Tarjetero item = (Tarjetero) iterator.next();
				JSONObject obj = new JSONObject();

				obj.put("id", item.getId());
				obj.put("fechaCambio", item.getFechaCambio());
				obj.put("producto", item.getProducto().getNombre());
				obj.put("productoId", item.getProducto().getId());
				obj.put("almacen", item.getAlmacen().getNombre());
				obj.put("almacenId", item.getAlmacen().getId());
				obj.put("valeProducto", item.getValeProducto().getId());
				obj.put("cantEntradaVale", item.getCantEntradaVale());			
				obj.put("cantSalidaVale", item.getCantSalidaVale());
				obj.put("cantExist", item.getCantExist());
				obj.put("precioMNExist", item.getPrecioMNExist());
				obj.put("precioMLCExist", item.getPrecioMLCExist());
				obj.put("impMNExist", item.getImpMNExist());
				obj.put("impMLCExist", item.getPrecioMLCExist());
				
				data.put(obj);
			}

			json.put("totalCount", bsr.getTotalCount());
			json.put("data", data);

		}
		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json", json.toString());
		return mv;
	}

	
	public ModelAndView listarTarjetero(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {

		Long idproducto = ArcheTypeUtils.getIdParameter(request, "idProducto");
		JSONObject json = new JSONObject();
		
		if (!(idproducto == null)) {
			BaseSearchResult bsr = ((ITarjeteroServicio) getServicio()).listarTarjetero(idproducto,egr);
			Collection<Object> lista = bsr.getResults();

			
			JSONArray data = new JSONArray();

			for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
				Tarjetero item = (Tarjetero) iterator.next();
				JSONObject obj = new JSONObject();

				obj.put("id", item.getId());
				obj.put("fechaCambio", item.getFechaCambio());
				obj.put("producto", item.getProducto().getNombre());
				obj.put("productoId", item.getProducto().getId());
				obj.put("almacen", item.getAlmacen().getNombre());
				obj.put("almacenId", item.getAlmacen().getId());
				obj.put("valeProducto", item.getValeProducto().getId());
				obj.put("cantEntradaVale", item.getCantEntradaVale());			
				obj.put("cantSalidaVale", item.getCantSalidaVale());
				obj.put("cantExist", item.getCantExist());
				obj.put("precioMNExist", item.getPrecioMNExist());
				obj.put("precioMLCExist", item.getPrecioMLCExist());
				obj.put("impMNExist", item.getImpMNExist());
				obj.put("impMLCExist", item.getPrecioMLCExist());
				obj.put("noControl", item.getValeProducto().getVale().getNoControl());
				//obj.put("impMLCExist", item.getPrecioMLCExist());
				
				data.put(obj);
			}

			json.put("totalCount", bsr.getTotalCount());
			json.put("data", data);

		}
		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json", json.toString());
		return mv;
	}

	public ModelAndView adicionarTarjetero(
			HttpServletRequest request, HttpServletResponse response,
			Tarjetero vnItem) throws Exception {

		JSONObject resp = new JSONObject();
		BindException errors = new BindException(vnItem,"Tarjetero");
		try {
			((ITarjeteroServicio) getServicio()).adicionarTarjetero(vnItem);
		}catch (Exception ee) {
				String codes[]={ee.getMessage()};
				FieldError fieldError = new FieldError("Tarjetero","fecha",null,false,codes,null,ee.getMessage());
				errors.addError(fieldError);
		        ModelAndView mv = new ModelAndView("common/error");
		        mv.addObject("errors",errors.getFieldErrors());
		        return mv;
		}
								
		resp = ArcheTypeUtils.getSuccess();
		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json", resp.toString());
		return mv;
	}

	public ModelAndView imprimir(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {
		if(egr.getQuery() != null){
			JSONObject query = new JSONObject(egr.getQuery().toString());
			HttpServletRequest mockRequest = ArcheTypeUtils.createHttpServletRequestJSON(query);
			
			Object object = getServicio().getClazz().newInstance();
			this.bind(mockRequest,object);
			
			egr.setQuery(object);
		}
		egr.setLimit(0);	
		BaseSearchResult bsr = ((ITarjeteroServicio)getServicio()).listarTarjeteroImprimir();		
		ModelAndView mv = new ModelAndView("InventarioReport");
		
		mv.addObject("Logo", getLogo().getFile());
		mv.addObject("dataSource",bsr.getResults());
		response.setHeader("Content-Disposition", "attachment; filename=Tarjetero.xls;");
		return mv;
	}

	public ModelAndView imprimirTarjetero(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {
		String queryString = request.getParameter("query");
		Long idproducto = null;
		
		if(queryString != null){
			JSONObject query = new JSONObject(queryString);
		/*	if(query.get("idproducto") == null) {
				throw new Exception("no hay ningún producto seleccionado");
			}*/
			idproducto = query.getLong("idproducto");
		}
		if(egr.getQuery() != null){
			JSONObject query = new JSONObject(egr.getQuery().toString());
			HttpServletRequest mockRequest = ArcheTypeUtils.createHttpServletRequestJSON(query);
			
			Object object = getServicio().getClazz().newInstance();
			this.bind(mockRequest,object);
			
			egr.setQuery(object);
		}

		egr.setLimit(0);	
	
		BaseSearchResult bsr = ((ITarjeteroServicio)getServicio()).listarTarjeteroAsc(idproducto);
		ModelAndView mv = new ModelAndView("InventarioReport");
		
		mv.addObject("Logo", getLogo().getFile());
		mv.addObject("dataSource",bsr.getResults());
		response.setHeader("Content-Disposition", "attachment; filename=Tarjetero.xls;");
		return mv;
		
	}

	public ModelAndView imprimirExistenciaFamilia(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {
		String queryString = request.getParameter("query");
		Long idClasificadorProducto = null;
		
		if(queryString != null){
			JSONObject query = new JSONObject(queryString);
		/*	if(query.get("idproducto") == null) {
				throw new Exception("no hay ningún producto seleccionado");
			}*/
			idClasificadorProducto = query.getLong("idClasificadorProducto");
		}
		if(egr.getQuery() != null){
			JSONObject query = new JSONObject(egr.getQuery().toString());
			HttpServletRequest mockRequest = ArcheTypeUtils.createHttpServletRequestJSON(query);
			
			Object object = getServicio().getClazz().newInstance();
			this.bind(mockRequest,object);
			
			egr.setQuery(object);
		}

		egr.setLimit(0);	
		BaseSearchResult bsr = ((ITarjeteroServicio)getServicio()).listarExistencia(idClasificadorProducto,null);
		Ecie ecie = getEcieServicio().obtenerEcie();
		ClasificadorProducto familia = ((ITarjeteroServicio)getServicio()).obtenerFamilia(idClasificadorProducto);
		
		ModelAndView mv = new ModelAndView("ExistenciaFamiliaReport");
		mv.addObject("Ecie",ecie.getReup()+"-"+ecie.getNombre());
		mv.addObject("Logo", getLogo().getFile());
		mv.addObject("dataSource",bsr.getResults());
		response.setHeader("Content-Disposition", "attachment; filename=ExistenciaFamilia"+familia.getNombre()+".xls;");
		return mv;
		
	}

	public ModelAndView imprimirOciosos(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {
		String queryString = request.getParameter("query");
		Long idClasificadorProducto = null;
		
		if(queryString != null){
			JSONObject query = new JSONObject(queryString);
		/*	if(query.get("idproducto") == null) {
				throw new Exception("no hay ningún producto seleccionado");
			}*/
			idClasificadorProducto = query.getLong("idClasificadorProducto");
		}
		if(egr.getQuery() != null){
			JSONObject query = new JSONObject(egr.getQuery().toString());
			HttpServletRequest mockRequest = ArcheTypeUtils.createHttpServletRequestJSON(query);
			
			Object object = getServicio().getClazz().newInstance();
			this.bind(mockRequest,object);
			
			egr.setQuery(object);
		}

		egr.setLimit(0);	
		BaseSearchResult bsr = ((ITarjeteroServicio)getServicio()).listarOciosos(idClasificadorProducto,null);
		Ecie ecie = getEcieServicio().obtenerEcie();
		ClasificadorProducto familia = ((ITarjeteroServicio)getServicio()).obtenerFamilia(idClasificadorProducto);
		
		ModelAndView mv = new ModelAndView("OciososFamiliaReport");
		mv.addObject("Ecie",ecie.getReup()+"-"+ecie.getNombre());
		mv.addObject("Logo", getLogo().getFile());
		mv.addObject("dataSource",bsr.getResults());
		response.setHeader("Content-Disposition", "attachment; filename=OciososFamilia"+familia.getNombre()+".xls;");
		return mv;
		
	}	
	
	public ModelAndView imprimirExistencia(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {
	
//		BaseSearchResult bsr = ((ITarjeteroServicio)getServicio()).listarExistenciaImprimir();		
		BaseSearchResult bsr = ((ITarjeteroServicio)getServicio()).listarExistenciaImprimir();

		
		ModelAndView mv = new ModelAndView("ExistenciaReport");
		
		mv.addObject("Logo", getLogo().getInputStream());
		mv.addObject("dataSource",bsr.getResults());
		response.setHeader("Content-Disposition", "attachment; filename=Existencia.xls;");
		
		return mv;
	}
	
	public ModelAndView imprimirDetalles(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String queryString = request.getParameter("query");
		Long id = null;

		if(queryString != null){
			JSONObject query = new JSONObject(queryString);
			id = query.getLong("id");
		}

		Tarjetero tarjetero = ((ITarjeteroServicio)getServicio()).obtenerProductoTarjetero(id);
		Set<Tarjetero> list = new HashSet<Tarjetero>();
		list.add(tarjetero);
		ModelAndView mv = new ModelAndView("DetallesTarjeteroReport");
		
		mv.addObject("Logo",getLogo().getInputStream());
		mv.addObject("dataSource",list);
		response.setHeader("Content-Disposition", "attachment; filename=Detalles_Producto_en_Tarjetero.xls;");
		return mv;
	}	
    public ModelAndView rangoTarjetero(HttpServletRequest request, HttpServletResponse response)throws Exception {
    	String id = request.getParameter("idproducto");
    	if (id.length() == 0 || id == null ) {
			BindException errors = new BindException(id, "idproducto");
	    	ConstraintViolationException ex = new ConstraintViolationException("idproducto", null, "idproducto");
	    	DataAccessException exception = new DataIntegrityViolationException("idproducto", ex);
	    	resolveErrors(errors, exception);
	    	ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors",errors.getFieldErrors());
			return mv;
		}
		
    	Long idproducto = null;
    	idproducto = Long.valueOf(id);

    	Producto producto = ((ITarjeteroServicio)getServicio()).obtenerProducto(idproducto); 
    	
		ModelAndView mv = super.detalles(request, response);
				
		mv.setViewName(rangoTarjeteroView);
		mv.addObject("modulo", getModulo());
		mv.addObject("producto", producto);
			
		return mv;
	}
    
	public ModelAndView rangoReporteTarjetero(HttpServletRequest request, HttpServletResponse response, ExtGridRequest egr) throws JSONException, IOException{

		BaseSearchResult bsr = new BaseSearchResult();
		
		String query = request.getParameter("query");
		JSONObject jsonObject = new JSONObject(query);
		
		Long idproducto= jsonObject.getLong("idproducto");
		
		String fecha1=jsonObject.getString("fechaI");
		String fecha2=jsonObject.getString("fechaF");
		
		Date fechaIni= null;
		Date fechaFin= null;
									
		if (fecha1 != null && "".equals(fecha1) == false && fecha2!= null && "".equals(fecha2) == false) {
			fechaIni = Date.valueOf(fecha1);
			fechaFin = Date.valueOf(fecha2);
			bsr = ((ITarjeteroServicio)getServicio()).listarTarjeteroFecha(idproducto, fechaIni, fechaFin);    			
		} 
		ModelAndView mv = new ModelAndView("InventarioXFechasReport");		
		mv.addObject("Logo",getLogo().getFile());
		mv.addObject("fechaIni",fechaIni);
		mv.addObject("fechaFin",fechaFin);
		mv.addObject("dataSource", bsr.getResults());
		response.setHeader("Content-Disposition", "attachment; filename=Reporte_Tarjetero_Rango_Fecha.xls;");    		
		return mv;
	}
    
	public void setListarTarjeteroView(String listarTarjeteroView) {
		this.listarTarjeteroView = listarTarjeteroView;
	}

	public String getListarTarjeteroView() {
		return listarTarjeteroView;
	}

	public void setEcieServicio(IEcieServicio ecieServicio) {
		this.ecieServicio = ecieServicio;
	}

	public IEcieServicio getEcieServicio() {
		return ecieServicio;
	}

	public String getRangoTarjeteroView() {
		return rangoTarjeteroView;
	}

	public void setRangoTarjeteroView(String rangoTarjeteroView) {
		this.rangoTarjeteroView = rangoTarjeteroView;
	}
}
