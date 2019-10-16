package org.inventario.ecie.web.controllers;

import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.archetype.common.web.controllers.CommonController;
import org.inventario.ecie.business.IProductoServicio;
import org.inventario.ecie.domain.Producto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

public class ProductoController extends CommonController {

	public ModelAndView listarProducto(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {
		String subCodigo = request.getParameter("query");
		BaseSearchResult bsr = ((IProductoServicio)getServicio()).listarProducto(subCodigo, egr);

		Collection<Object> lista = bsr.getResults();

		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();

		for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
			Producto item = ((Producto) iterator.next());
			JSONObject obj = new JSONObject();

			obj.put("id", item.getId());
			obj.put("nombre", item.getNombre());
			obj.put("codigo", item.getCodigo());
			data.put(obj);
		}

		json.put("totalCount", bsr.getTotalCount());
		json.put("data", data);

		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json", json.toString());
		return mv;
	}
}
