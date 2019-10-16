package org.archetype.common.web.controllers;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.archetype.common.business.ICommonService;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.archetype.common.domain.Nomenclador;
import org.archetype.common.domain.PersistentObject;
import org.archetype.common.web.utils.ArcheTypeUtils;
import org.hibernate.Hibernate;
import org.hibernate.collection.PersistentCollection;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.proxy.HibernateProxy;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.UrlPathHelper;

@SuppressWarnings("all")
public class CommonController extends MultiActionController{
	
	private ICommonService servicio;
	private String editarView;
	private String listarView;
	private String detallesView;
	private String buscarView;
	private String imprimirView;
	private String nomenclador;
	private String nombreNomenclador;
	private String modulo;
	private Boolean autoEdit = false;
	private Boolean autoDetail = false;
	private Boolean autoList = false;
	private Boolean autoSearch = false;
	private Validator validator;
	private Resource logo;
	
	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd-MM-yyyy"), true));
		super.initBinder(request, binder);
	}
	
	protected void bind(HttpServletRequest request, Object command)
			throws Exception {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		Map<String, String[]> parameters = request.getParameterMap();
		
		for (Iterator iterator = parameters.keySet().iterator(); iterator.hasNext();) {
			String param = (String) iterator.next();
			String[] values = parameters.get(param);
			boolean empty = false;
			for (int i = 0; i < values.length; i++) {
				if(StringUtils.hasText(values[i]) == false){
					empty = true;
				} else{
					empty = false;
				}
			}
			if(empty == false){
				parameterMap.put(param, values);
			}
		}
		
		mockRequest.setParameters(parameterMap);
		super.bind(mockRequest, command);
	}
	
	public ModelAndView urlFileName(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String urlPath = urlPathHelper.getLookupPathForRequest(request);
		
		int start = (urlPath.charAt(0) == '/' ? 1 : 0);
		int lastIndex = urlPath.lastIndexOf(".");
		int end = (lastIndex < 0 ? urlPath.length() : lastIndex);
		String viewName = urlPath.substring(start, end);
		
		ModelAndView model = new ModelAndView(viewName);
		model.addObject("modulo", getModulo());
		return model;
	}
	
	public void initialize(Object obj,HttpServletRequest request, BindException errors) throws Exception{
		//sobreescribir para realizar operaciones sobre el objeto
	}
	
	public void resolveErrors(BindException errors, DataAccessException exception){
		if(exception.getCause() instanceof ConstraintViolationException){
			String constraintName = ((ConstraintViolationException)exception.getCause()).getConstraintName();
			String codes[]={constraintName};
			FieldError fieldError = new FieldError("command",constraintName,null,false,codes,null,null);
			errors.addError(fieldError);
		}	
	}
	
	public ModelAndView guardar(HttpServletRequest request, HttpServletResponse response)throws Exception {
		JSONObject resp;
		
		Class clazz = servicio.getClazz();
		Object obj = clazz.newInstance();		
		this.bind(request, obj);
		
		BindException errors = new BindException(obj,"command");
		if(validator != null && validator.supports(clazz)){
			ValidationUtils.invokeValidator(validator, obj, errors);
		}
		
		if(errors.hasErrors()){
			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors",errors.getFieldErrors());
			return mv;
		}
		
		//devolver el objeto inicializado
		initialize(obj, request, errors);
		
		//poner a null los elementos no inicializados
		ArcheTypeUtils.cleanHibernateTransientObject(obj);
		
		try {
			if( obj instanceof PersistentObject){
				if(((PersistentObject)obj).getId() == null){
					servicio.adicionar(obj);
				} else{
					servicio.actualizar(obj);
				}
			} else{
				servicio.adicionarActualizar(obj);
			}
		} catch (DataAccessException e) {
			resolveErrors(errors, e);
			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors",errors.getFieldErrors());
			return mv;
		}

		resp = ArcheTypeUtils.getSuccess();
		
		if(obj instanceof PersistentObject){
			resp.put("id", ((PersistentObject)obj).getId());
		}
		
		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json",resp.toString());
		return mv;
	}
	
	public ModelAndView eliminar(HttpServletRequest request, HttpServletResponse response, PersistentObject object)throws Exception {
		JSONObject resp;
		BindException errors = new BindException(nomenclador,"command");
		
		try {
			if(object.getId() == null){
				resp = ArcheTypeUtils.getFailure(null);			
			}else{
				servicio.eliminar(object.getId());
				resp = ArcheTypeUtils.getSuccess();				
			}
		} catch (DataAccessException e) {
			resolveErrors(errors, e);
			
			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors",errors.getFieldErrors());
			return mv;
		}
		
		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json",resp.toString());		
		return mv;
	}
	
	public ModelAndView detalles(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		ModelAndView model = new ModelAndView(detallesView);
		model.addObject("nomenclador", getNomenclador());
		model.addObject("modulo", getModulo());
		
		Long id = ArcheTypeUtils.getIdParameter(request, "id");
		if(id == null){
			return model;
		}
		Object obj = servicio.obtener(id);
		
		if(autoDetail == true){
			StringBuffer itemsDer = new StringBuffer();
			StringBuffer itemsIzq = new StringBuffer();
			autoDetail(obj, itemsDer, itemsIzq);
			model.addObject("itemsDer", itemsDer.toString());
			model.addObject("itemsIzq", itemsIzq.toString());
		}
		model.addObject("command", obj);
		return model;
	}
	
	public String getNombreNomenclador() {
		if(nombreNomenclador != null){
			return nombreNomenclador;
		}
		nombreNomenclador = servicio.getClazz().getSimpleName();
		return nombreNomenclador;
	}

	public void setNombreNomenclador(String nombreNomenclador) {
		this.nombreNomenclador = nombreNomenclador;
	}

	private void autoDetail(Object obj, StringBuffer itemsDer, StringBuffer itemsIzq) throws Exception{
		LinkedList list = new LinkedList();
		ArcheTypeUtils.allfields(obj.getClass(), list);
		int length = list.size();
		int cant = (length-1)/2;
		
		if((length - 1) % 2 != 0){
			cant++;
		}
		for (int i = 0; i < length; i++) {
			Field field = (Field) list.get(i);
			if(field.getName().equals("id")){
				list.remove(i);
				i--;
				length--;
			} else{
				
				field.setAccessible(true);
				String prop = field.getName();
				Object value = field.get(obj);
				
				if((value instanceof HibernateProxy || value instanceof PersistentCollection) && Hibernate.isInitialized(value) == false){
					continue;
				} else {
					String fieldLabel = ((String)prop.substring(0, 1)).toUpperCase() + prop.substring(1);
	
					String item = "new Ext.form.DisplayField({";
					if(value != null){
						item+="value:'"+value+"',";
					}
					item+="fieldLabel: '"+fieldLabel+"'})";
					
					if(i < cant){
						if(i+1 < cant){
							item+=",";
						}
						itemsDer.append(item);
					} else{
						if(i < length - 1){
							item+=",";
						}
						itemsIzq.append(item);
					}
				}
			}
		}
	}
	
	//muestra la vista para actualizar
	public ModelAndView editar(HttpServletRequest request, HttpServletResponse response)throws Exception {
		ModelAndView model = new ModelAndView(editarView);
		model.addObject("nomenclador", getNomenclador());
		model.addObject("nombreNomenclador", getNombreNomenclador());
		model.addObject("modulo", getModulo());
		
		Long id = ArcheTypeUtils.getIdParameter(request, "id");
		Object obj;
		
		if(id == null){
			obj = getServicio().getClazz().newInstance();
		} else{
			obj = servicio.obtener(id);
			model.addObject("command", obj);
		}
		
		if(autoEdit){
			StringBuffer stores = new StringBuffer();
			StringBuffer items = new StringBuffer();
			
			autoEdit(obj, items, stores);
			
			model.addObject("stores", stores.toString());
			model.addObject("items", items.toString());
		}
		
		return model;
	}
		
	public ModelAndView mostrarLista(HttpServletRequest request, HttpServletResponse response)throws Exception {
		ModelAndView model = new ModelAndView(listarView);
		
		if(autoList){
			StringBuffer fields = new StringBuffer();
			StringBuffer columns = new StringBuffer();
			
			autoList(servicio.getClazz(), fields, columns);
			
			model.addObject("fields", fields.toString());
			model.addObject("columns", columns.toString());
		}
		
		model.addObject("nomenclador", getNomenclador());
		model.addObject("nombreNomenclador", getNombreNomenclador());
		model.addObject("modulo", getModulo());		
		return model;
	}
	
	public ModelAndView buscar(HttpServletRequest request, HttpServletResponse response)throws Exception {
		ModelAndView model = new ModelAndView(buscarView);
		
		if(autoSearch){
			StringBuffer stores = new StringBuffer();
			StringBuffer items = new StringBuffer();
			
			autoEdit(servicio.getClazz().newInstance(), items, stores);
			
			model.addObject("stores", stores.toString());
			model.addObject("items", items.toString());
		}
		
		model.addObject("nomenclador", getNomenclador());
		model.addObject("modulo", getModulo());
		return model;	
	}	
	
	public ICommonService getServicio() {
		return servicio;
	}

	public ModelAndView listar(HttpServletRequest request, HttpServletResponse response, ExtGridRequest egr)throws Exception {
		
		Object object = getServicio().getClazz().newInstance();
		this.bind(request,object);
		egr.setQuery(object);
		
		BaseSearchResult bsr = getServicio().listar(egr);
		Collection<Object> lista = bsr.getResults();
		
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		
		for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
			Object item = iterator.next();
			JSONObject obj = ArcheTypeUtils.getJSONObject(item, null, true);
			data.put(obj);
		}
		
		json.put("totalCount", bsr.getTotalCount());
		json.put("data", data);
			
		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json",json);		
		return mv;
	}
	
	public ModelAndView imprimir(HttpServletRequest request, HttpServletResponse response, ExtGridRequest egr) throws Exception {
		
		if(egr.getQuery() != null){
			JSONObject query = new JSONObject(egr.getQuery().toString());
			HttpServletRequest mockRequest = ArcheTypeUtils.createHttpServletRequestJSON(query);
			
			Object object = getServicio().getClazz().newInstance();
			this.bind(mockRequest,object);
			
			egr.setQuery(object);
		}
		egr.setLimit(0);
		BaseSearchResult bsr = getServicio().listar(egr);
		ModelAndView mv = new ModelAndView(imprimirView);
		
		mv.addObject("Logo",getLogo().getFile());
		mv.addObject("Nomenclador",getNomenclador());
		mv.addObject("dataSource",bsr.getResults());
		response.setHeader("Content-Disposition", "attachment; filename=Listado_de_"+getNomenclador()+".xls;");
		return mv;
	}
	
	private void autoList(Class clazz, StringBuffer fields, StringBuffer columns) throws Exception{
		LinkedList list = new LinkedList();
		ArcheTypeUtils.allfields(clazz, list);
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();
			if(field.getName().equals("id") == false && (ArcheTypeUtils.isStandardProperty(field.getType()) == true || Nomenclador.class.isAssignableFrom(field.getType()))){
				field.setAccessible(true);
				String prop = field.getName();
				String fieldLabel = ((String)prop.substring(0, 1)).toUpperCase() + prop.substring(1);
				if(Nomenclador.class.isAssignableFrom(field.getType())){
					prop+=".nombre";
				}
				
				fields.append("'"+prop+"'");
				
				JSONObject jsonTemplate = new JSONObject();
				jsonTemplate.put("header", fieldLabel);
				jsonTemplate.put("width", 120);
				jsonTemplate.put("dataIndex", prop);
				jsonTemplate.put("sortable", true);
	
				columns.append(jsonTemplate.toString());
				
				if(iterator.hasNext()){
					fields.append(",");
					columns.append(",");
				}
			}
		}
	}
	
	private void autoEdit(Object obj, StringBuffer items, StringBuffer stores) throws Exception{
		LinkedList list = new LinkedList();
		Class cls= obj.getClass();
		ArcheTypeUtils.allfields(obj.getClass(), list);
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();
			field.setAccessible(true);
			String prop = field.getName();
			Object value = field.get(obj);
			String item = "";
			
			JSONObject itemTemplate = new JSONObject();
			String fieldLabel = ((String)prop.substring(0, 1)).toUpperCase() + prop.substring(1);
			
			if(ArcheTypeUtils.isStandardProperty(field.getType()) == false || field.getType().isEnum()){
				
				JSONObject storeTemplate = new JSONObject();
				JSONArray fields; 
				
				String store = "";				
				
				if(field.getType().isEnum()){
					store = "var "+prop+"Store = new Ext.data.SimpleStore(";
					fields = new JSONArray();
					fields.put("id");
					fields.put(prop);
					storeTemplate.put("fields", fields);
										
					JSONArray data = ArcheTypeUtils.listarEnums(field.getType());
					storeTemplate.put("data", data);
					
					item = "new Ext.form.ComboBox({";
					item+= "store: "+ prop+"Store";
					item+= ",displayField: '"+prop+"'";
					item+= ",typeAhead: false";
					item+= ",forceSelection: true";
					item+= ",triggerAction: 'all'";
					item+= ",mode: 'local'";
					item+= ",name: '" + prop+"'";
					item+= ",fieldLabel: '" + fieldLabel + "'";
					if(value != null){
						item+= ",value: '" + value + "'";
					}
					item+= ",editable: false";
					item+= "})";
					
					store+= storeTemplate.toString()+");";
					stores.append(store);
				} else{
					if(Collection.class.isAssignableFrom(field.getType())){
						
					} else {
					
						store = "var "+prop+"Store = new Ext.data.JsonStore(";
						String url = "../"+getModulo()+"/listar"+field.getType().getSimpleName()+".json";
						storeTemplate.put("url", url);
						storeTemplate.put("remoteSort", "true");
						storeTemplate.put("baseParams", "{limit:20}");
						storeTemplate.put("root", "data");
						storeTemplate.put("totalProperty", "totalCount");
						storeTemplate.put("id", "id");
						fields = ArcheTypeUtils.getJSONArrayNames(field.getType());
						storeTemplate.put("fields", fields);
						
						item = "new Ext.form.ComboBox({";
						item+= "store: "+ prop+"Store";
						item+= ",displayField: 'nombre'";
						item+= ",typeAhead: false";
						item+= ",forceSelection: true";
						item+= ",triggerAction: 'all'";
						item+= ",pageSize: 20";
						item+= ",valueField: 'id'";
						item+= ",hiddenName: '" + prop+".id'";
						item+= ",fieldLabel: '" + fieldLabel + "'";
						
						if(value instanceof Nomenclador){
							if(((Nomenclador)value).getNombre() != null)
							item+= ",value: '" + ((Nomenclador)value).getNombre() + "'";
							if(((Nomenclador)value).getId() != null)
							item+= ",hiddenValue: '" + ((Nomenclador)value).getId() + "'";
						}
						
						item+= "})";
						
						if(storeTemplate.length() != 0){
							store+= storeTemplate.toString()+");";
							stores.append(store);
						}
					}
				}
				
			} else{
				if (Nomenclador.class.isAssignableFrom(obj.getClass())){
					if(prop.equals("nombre")){
						itemTemplate.put("labelStyle", "font-weight:bold");
						itemTemplate.put("allowBlank", "false");
						itemTemplate.put("blankText", "Este campo es requerido");
					}	
				}
				itemTemplate.put("name", prop);
				itemTemplate.put("value", value);
				itemTemplate.put("autoComplete", "off");
				
				if(prop.equals("id")){
					itemTemplate.put("inputType", "hidden");
				} else{
					itemTemplate.put("fieldLabel", fieldLabel);
				}
				if(field.getType().equals(Date.class)){
					itemTemplate.put("format", "d-m-Y");
					item = "new Ext.form.DateField(" + itemTemplate.toString() + ")";
				} else{
					item = "new Ext.form.TextField(" + itemTemplate.toString() + ")";
				}
			}
			if(iterator.hasNext() && Collection.class.isAssignableFrom(field.getType()) == false){
				item+=",";
			}
			items.append(item);
		}
	}

	public void setEditarView(String editarView) {
		this.editarView = editarView;
	}
	public void setListarView(String listarView) {
		this.listarView = listarView;
	}
	public void setDetallesView(String detallesView) {
		this.detallesView = detallesView;
	}
	public void setNomenclador(String nomenclador) {
		this.nomenclador = nomenclador;
	}
	public void setServicio(ICommonService servicio) {
		this.servicio = servicio;
	}
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
	public String getModulo() {
		if(modulo != null){
			return modulo;
		}
		
		Class clazz = servicio.getClazz();
		String mod = clazz.getName();
		
		int endIndex = mod.lastIndexOf(".domain");
		mod = mod.substring(0, endIndex);
		endIndex = mod.lastIndexOf('.', endIndex);
		mod = mod.substring(endIndex + 1, mod.length());
		
		modulo = mod;
		return modulo;
	}
	public String getEditarView() {
		return editarView;
	}
	public String getListarView() {
		return listarView;
	}
	public String getNomenclador() {
		if(nomenclador != null){
			return nomenclador;
		}
		nomenclador = servicio.getClazz().getSimpleName();
		return nomenclador;
	}
	public Validator getValidator() {
		return validator;
	}
	public String getDetallesView() {
		return detallesView;
	}
	public Resource getLogo() {
		return logo;
	}
	public void setLogo(Resource logo) {
		this.logo = logo;
	}

	public void setAutoEdit(Boolean autoEdit) {
		this.autoEdit = autoEdit;
	}

	public Boolean getAutoEdit() {
		return autoEdit;
	}

	public Boolean getAutoDetail() {
		return autoDetail;
	}

	public void setAutoDetail(Boolean autoDetail) {
		this.autoDetail = autoDetail;
	}

	public String getBuscarView() {
		return buscarView;
	}

	public void setBuscarView(String buscarView) {
		this.buscarView = buscarView;
	}

	public Boolean getAutoList() {
		return autoList;
	}

	public void setAutoList(Boolean autoList) {
		this.autoList = autoList;
	}

	public Boolean getAutoSearch() {
		return autoSearch;
	}

	public void setAutoSearch(Boolean autoSearch) {
		this.autoSearch = autoSearch;
	}

	public String getImprimirView() {
		return imprimirView;
	}

	public void setImprimirView(String imprimirView) {
		this.imprimirView = imprimirView;
	}
}
