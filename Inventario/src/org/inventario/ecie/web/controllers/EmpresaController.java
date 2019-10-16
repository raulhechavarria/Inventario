package org.inventario.ecie.web.controllers;

import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.archetype.common.web.controllers.CommonController;
import org.archetype.common.web.utils.ArcheTypeUtils;
import org.inventario.ecie.business.IEmpresaServicio;
import org.inventario.ecie.domain.Empresa;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

public class EmpresaController extends CommonController {

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
		BaseSearchResult bsr = getServicio().listar(egr);		
		ModelAndView mv = new ModelAndView("EmpresasReport");
		
	//	mv.addObject("Logo",getLogo().getInputStream());
		mv.addObject("Logo",getLogo().getFile());
		mv.addObject("dataSource",bsr.getResults());
		response.setHeader("Content-Disposition", "attachment; filename=Listado_de_Proveedores.xls;");
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

		Empresa empresa = ((IEmpresaServicio)getServicio()).obtenerEmpresa(id);
		Set<Empresa> list = new HashSet<Empresa>();
		list.add(empresa);
		ModelAndView mv = new ModelAndView("DetallesEmpresaReport");
		
		mv.addObject("Logo",getLogo().getInputStream());
		mv.addObject("dataSource",list);
		response.setHeader("Content-Disposition", "attachment; filename=Detalles_Proveedor.xls;");
		return mv;
	}	

}
