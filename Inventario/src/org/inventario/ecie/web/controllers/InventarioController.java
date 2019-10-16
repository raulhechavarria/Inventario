package org.inventario.ecie.web.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.archetype.common.web.controllers.CommonController;
import org.archetype.common.web.utils.ArcheTypeUtils;
import org.inventario.ecie.business.IEcieServicio;
import org.inventario.ecie.business.IInventarioServicio;
import org.inventario.ecie.business.IProductoServicio;
import org.inventario.ecie.business.IValeServicio;
import org.inventario.ecie.domain.Almacen;
import org.inventario.ecie.domain.CompraVentas;
import org.inventario.ecie.domain.Ecie;
import org.inventario.ecie.domain.Inventario;
import org.inventario.ecie.domain.Producto;
import org.inventario.ecie.domain.TipoVale;
import org.inventario.ecie.domain.Vale;
import org.inventario.ecie.web.utils.CalculoEco;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

import com.sun.corba.se.spi.extension.ZeroPortPolicy;

public class InventarioController extends CommonController {

	private IEcieServicio ecieServicio;
	private IValeServicio valeServicio;

	public ModelAndView listarMovimientoMes() {
		ModelAndView mv = new ModelAndView("common/json");
		JSONObject json = new JSONObject();
		mv.addObject("json", json.toString());
		return mv;
	}

	public ModelAndView entradaSalidaMes(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {
		String mes = request.getParameter("mes");
		JSONObject json = ((IInventarioServicio) getServicio()).informacion(
				"mes", mes);
		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json", json.toString());
		return mv;
	}

	public ModelAndView entradaSalidaDiario(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {
		String dia = request.getParameter("dia");
		JSONObject json = ((IInventarioServicio) getServicio()).informacion(
				"diario", dia);
		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json", json.toString());
		return mv;
	}
	
	public ModelAndView compras(HttpServletRequest request,	HttpServletResponse response, ExtGridRequest egr) throws JSONException {
		String anno = request.getParameter("anno");
		BaseSearchResult bsr = ((IInventarioServicio) getServicio()).ComprasVentas(anno);
		Collection<Object> lista = bsr.getResults();
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		int i = 1;
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			CompraVentas compra = (CompraVentas) iterator.next();
			JSONObject obj = new JSONObject();
			obj.put("mes", compra.getMes());
			obj.put("compras", compra.getCompras());
			obj.put("ventas", compra.getVentas());
			i++;
			data.put(obj);
		}
		json.put("totalCount", bsr.getTotalCount());
		json.put("data", data);

		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json", json.toString());
		return mv;
	}

	public ModelAndView listar(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {
		String mes = request.getParameter("mes");
		BaseSearchResult bsr = ((IInventarioServicio) getServicio())
				.listarInventario(mes);

		Collection<Object> lista = bsr.getResults();
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
			Inventario item = ((Inventario) iterator.next());
			JSONObject obj = new JSONObject();
			Inventario inventarioAnterior = null ;
			for (int i = 1; i < 30; i++) {
				Date fecha = DateUtils.addDays(item.getFecha(), -i);
			 inventarioAnterior = ((IInventarioServicio) getServicio())
					.obtenerDadoFechaAlmacen(fecha, item.getAlmacen().getId());
			 if (inventarioAnterior != null) {
				break;
			}
			}
			obj.put("id", item.getId());
			obj.put("invFinalMN", item.getInvFinalMN());
			obj.put("invFinalMLC", item.getInvFinalMLC());
			
			if (inventarioAnterior == null) {
				Long cero = new Long((long) 0.00);
				obj.put("invIniMN", cero);
				obj.put("invIniMLC", cero);
				obj.put("MovimientoMN", item.getInvFinalMN());
				obj.put("MovimientoMLC", item.getInvFinalMLC());
			} else {
				obj.put("invIniMN", inventarioAnterior.getInvFinalMN());
				obj.put("invIniMLC", inventarioAnterior.getInvFinalMLC());
				obj.put("MovimientoMN", CalculoEco.redondeo2(item.getInvFinalMN() - inventarioAnterior.getInvFinalMN()));
				obj.put("MovimientoMLC",CalculoEco.redondeo2(item.getInvFinalMLC() - inventarioAnterior.getInvFinalMLC()));
			}

			obj.put("almacen", item.getAlmacen().getNombre());
			obj.put("almacenId", item.getAlmacen().getId());
			obj.put("fecha", item.getFecha());
			data.put(obj);
		}

		json.put("totalCount", bsr.getTotalCount());
		json.put("data", data);

		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json", json.toString());
		return mv;
	}

	public ModelAndView imprimirDetalles(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {
//		Registro del inventario
		String queryString = request.getParameter("query");
		Long idInventario = null;

		if (queryString != null) {
			JSONObject query = new JSONObject(queryString);
			idInventario = query.getLong("idInventario");
		}
		Inventario inventario = (Inventario) getServicio().obtener(idInventario);

//		Para obtener los movimientos del día del cuadre diario
		BaseSearchResult bsrVale = getValeServicio().listarVale(inventario.getFecha());

//		Para Datos de la Empresa (Cod.REUP y Nombre)
		Ecie ecie = getEcieServicio().obtenerEcie();
// 		Obtener inventario final del día anterior (Inventario Inicial del día)
		Date fecha = null;
		Inventario inv = null;
	
		for (int i = 1; i < 90; i++) {
			fecha = DateUtils.addDays(inventario.getFecha(), -i);
			inv = ((IInventarioServicio) getServicio())
			.obtenerDadoFechaAlmacen(fecha, inventario.getAlmacen().getId());
			if (inv != null) {
				break;
			}
		}
		Double inventarioInicialMLC = null;
		Double inventarioInicialMN = null;
	
		if (inv == null) {
// Se utiliza para la carga inicial
			inventarioInicialMLC = 0.00;
			inventarioInicialMN = 0.00;
		} else {
			inventarioInicialMLC = inv.getInvFinalMLC();
			inventarioInicialMN = inv.getInvFinalMN();
		} 
		
//		Pasar datos al reporte		
		ModelAndView mv = new ModelAndView("CuadreDiarioReport");
		mv.addObject("fchCierre", inventario.getFecha());
		mv.addObject("idAlmacen", inventario.getAlmacen().getId());
		mv.addObject("datosAlmacen",inventario.getAlmacen().getCodigo()+"-"+
				                    inventario.getAlmacen().getNombre());
		mv.addObject("inventarioInicialMLC", inventarioInicialMLC);
		mv.addObject("inventarioInicialMN", inventarioInicialMN);
		mv.addObject("inventarioFinalMN", inventario.getInvFinalMN());
		mv.addObject("inventarioFinalMLC", inventario.getInvFinalMLC());
		mv.addObject("Ecie", ecie.getReup() + "-" + ecie.getNombre());
		mv.addObject("Logo", getLogo().getFile());
		mv.addObject("dataSource",bsrVale.getResults());
		response.setHeader("Content-Disposition",
				           "attachment; filename=CuadreDiario-" + inventario.getFecha()+ ".xls;");
		return mv;
	}

	public void setEcieServicio(IEcieServicio ecieServicio) {
		this.ecieServicio = ecieServicio;
	}

	public IEcieServicio getEcieServicio() {
		return ecieServicio;
	}

	public void setValeServicio(IValeServicio valeServicio) {
		this.valeServicio = valeServicio;
	}

	public IValeServicio getValeServicio() {
		return valeServicio;
	}
}
