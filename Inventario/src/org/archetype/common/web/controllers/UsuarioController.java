package org.archetype.common.web.controllers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.archetype.common.business.ICommonService;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.archetype.common.domain.Rol;
import org.archetype.common.domain.Usuario;
import org.archetype.common.web.utils.ArcheTypeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.providers.encoding.PlaintextPasswordEncoder;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class UsuarioController extends CommonController{
	
	private PasswordEncoder encoder = new PlaintextPasswordEncoder();
	private ICommonService rolServicio; 
	
	public void setEncoder(PasswordEncoder encoder) {
		this.encoder = encoder;
	}

	public void initialize(Object obj, HttpServletRequest request,
			BindException errors) throws Exception {
		Usuario user = (Usuario)obj; 
		String[] roles = request.getParameter("idRoles").split(",");
		Set<Rol> rolesSet = new HashSet<Rol>();
		
		for (int i = 0; i < roles.length; i++) {
			long rolId = Long.parseLong(roles[i]);
			Rol nuevo = new Rol();
			nuevo.setId(rolId);
			rolesSet.add(nuevo);
		}
		
		if("*******".equals(user.getPassword()) || "".equals(user.getPassword()) || user.getPassword() == null){
			user.setPassword(null);
		}else{
			String pass = encoder.encodePassword(user.getPassword(),null);
			user.setPassword(pass);			
		}
		
		user.setRoles(rolesSet);
	}
	
	public ModelAndView editar(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Long id = ArcheTypeUtils.getIdParameter(request, "id");
		
		ExtGridRequest egr = new ExtGridRequest();
		egr.setLimit(0);
		BaseSearchResult bsr = rolServicio.listar(egr);
		Collection<Object> roles = bsr.getResults();		

		JSONArray data = new JSONArray();
		
		for (Iterator<Object> iterator = roles.iterator(); iterator.hasNext();) {
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
		
		if(id == null){
			return model;
		}
		
		Usuario usuario = (Usuario)getServicio().obtener(id);
		
		StringBuffer rolesUsuario = new StringBuffer();
		for (Iterator<Rol> iterator = usuario.getRoles().iterator(); iterator.hasNext();) {
			Rol rol = iterator.next();			
			rolesUsuario.append(rol.getId());
			if (iterator.hasNext()){
				rolesUsuario.append(",");
			}
		}

		model.addObject("command",usuario);
		model.addObject("rolesUsuario", rolesUsuario.toString());
		return model;
	}
	
	public ModelAndView listar(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {
		
		BaseSearchResult bsr = getServicio().listar(egr);
		Collection<Object> lista = bsr.getResults();
	
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		
		for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
			Usuario usuario = (Usuario) iterator.next();				
			JSONObject obj = new JSONObject();	
			
			obj.put("id",usuario.getId());
			obj.put("nombre",usuario.getNombre());
			obj.put("login",usuario.getLogin());
			obj.put("deshabilitado",usuario.isDeshabilitado());
			data.put(obj);
		}
		
		json.put("totalCount", bsr.getTotalCount());
		json.put("data", data);
			
		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json",json.toString());		
		return mv;
	}

	public ICommonService getRolServicio() {
		return rolServicio;
	}
	public void setRolServicio(ICommonService rolServicio) {
		this.rolServicio = rolServicio;
	}
	public PasswordEncoder getEncoder() {
		return encoder;
	}
}
