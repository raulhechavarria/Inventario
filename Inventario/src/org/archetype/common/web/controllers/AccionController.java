package org.archetype.common.web.controllers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.archetype.common.business.ICommonService;
import org.archetype.common.domain.Accion;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.archetype.common.domain.Rol;
import org.archetype.common.web.utils.ArcheTypeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class AccionController extends CommonController{
	
	private ICommonService rolServicio; 
	
	public void initialize(Object obj, HttpServletRequest request, BindException errors)
			throws Exception {
		String[] roles = request.getParameter("idRoles").split(",");
		Set<Rol> rolesSet = new HashSet<Rol>();
		
		for (int i = 0; i < roles.length; i++) {
			long rolId = Long.parseLong(roles[i]);
			Rol nuevo = new Rol();
			nuevo.setId(rolId);
			rolesSet.add(nuevo);
		}
		
		((Accion)obj).setRoles(rolesSet);
	}
	
	@Override
	public ModelAndView editar(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ExtGridRequest egr = new ExtGridRequest();
		egr.setLimit(0);
		BaseSearchResult bsr = rolServicio.listar(egr);
		Collection<Object> list = bsr.getResults();		

		JSONArray data = new JSONArray();
		
		for (Iterator<Object> iterator = list.iterator(); iterator.hasNext();) {
			Rol rol = (Rol) iterator.next();				
			JSONObject obj = new JSONObject();	
			
			obj.put("id",rol.getId());
			obj.put("nombre",rol.getNombre());												
			data.put(obj);
		}
		
		ModelAndView model = new ModelAndView(getEditarView());
		model.addObject("nomenclador", getNomenclador());
		model.addObject("modulo", getModulo());
		model.addObject("roles", data.toString());
		
		Long id = ArcheTypeUtils.getIdParameter(request, "id");
		if(id == null){
			return model;
		}
		
		Accion accion = (Accion)getServicio().obtener(id);
		
		StringBuffer roles = new StringBuffer();
		for (Iterator<Rol> iterator = accion.getRoles().iterator(); iterator.hasNext();) {
			Rol rol = iterator.next();			
			roles.append(rol.getId());
			if (iterator.hasNext()){
				roles.append(",");
			}
		}
		
		model.addObject("command", accion);
		model.addObject("rolesData", roles.toString());
		return model;
	}

	public ICommonService getRolServicio() {
		return rolServicio;
	}

	public void setRolServicio(ICommonService rolServicio) {
		this.rolServicio = rolServicio;
	}
}
