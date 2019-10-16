package org.inventario.ecie.web.controllers;

import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.archetype.common.domain.PersistentObject;
import org.archetype.common.web.controllers.CommonController;
import org.archetype.common.web.utils.ArcheTypeUtils;
import org.inventario.ecie.business.IValeProductoServicio;
import org.inventario.ecie.domain.EstadoVale;
import org.inventario.ecie.domain.TipoVale;
import org.inventario.ecie.domain.Vale;
import org.inventario.ecie.domain.ValeProducto;
import org.inventario.ecie.web.utils.CalculoEco;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

public class ValeProductoController extends CommonController {

	private String editarEntradaValeProducto, editarSalidaValeProducto,
			editarDevolValeProducto;

	public String getEditarEntradaValeProducto() {
		return editarEntradaValeProducto;
	}

	public void setEditarEntradaValeProducto(String editarEntradaValeProducto) {
		this.editarEntradaValeProducto = editarEntradaValeProducto;
	}

	public String getEditarSalidaValeProducto() {
		return editarSalidaValeProducto;
	}

	public void setEditarSalidaValeProducto(String editarSalidaValeProducto) {
		this.editarSalidaValeProducto = editarSalidaValeProducto;
	}

	// Metodo Listar el vale Producto
	public ModelAndView listarValeProducto(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {

		Long idVale = ArcheTypeUtils.getIdParameter(request, "idVale");
		String tipo = request.getParameter("tipo");
		BaseSearchResult bsr = ((IValeProductoServicio) getServicio())
				.listarValeProducto(idVale, tipo);

		Collection<Object> lista = bsr.getResults();

		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
			ValeProducto vp = (ValeProducto) iterator.next();
			JSONObject obj = new JSONObject();

			obj.put("id", vp.getId());
			obj.put("producto", vp.getProducto().getNombre());
			obj.put("cantProdVale", vp.getCantProdVale());
			if (vp.getExistenciaOrigen() == null) {
				obj.put("existenciaOrigen", 0.00);
			} else {
				obj.put("existenciaOrigen", vp.getExistenciaOrigen());
			};
			if (vp.getExistenciaDestino() == null) {
				obj.put("existenciaDestino", 0.00);
			} else {
				obj.put("existenciaDestino", vp.getExistenciaDestino());
			};
			obj.put("precioMNVale", vp.getPrecioMNVale());
			obj.put("importeMNVale", vp.getImporteMNVale());
			obj.put("precioMLCVale", vp.getPrecioMLCVale());
			obj.put("importeMLCVale", vp.getImporteMLCVale());
			TipoVale tipoP = null;
			switch (tipoP.valueOf(tipo)) {
			case FacturaMLC:
				Double precioVentaMLC = CalculoEco.redondeo5(vp.getPrecioMLCVale() + vp.getPrecioMNVale());
				obj.put("precioVentaMLC", precioVentaMLC);
				obj.put("recargoDescuentoMLC", vp.getRecargoDescuentoMLC());
				obj.put("totalMLC",CalculoEco.redondeo2((vp.getCantProdVale()*precioVentaMLC)+ vp.getRecargoDescuentoMLC()));
				break;
			case FacturaMN:
				Double precioVentaMN = CalculoEco.redondeo5(vp.getPrecioMLCVale() + vp.getPrecioMNVale());
				obj.put("precioVentaMN", precioVentaMN);
				obj.put("recargoDescuentoMN", vp.getRecargoDescuentoMN());
				obj.put("totalMN",CalculoEco.redondeo2((vp.getCantProdVale()*precioVentaMN) + vp.getRecargoDescuentoMN()));
				break;
			case Factura_MN_MLC:
				obj.put("recargoDescuentoMN", vp.getRecargoDescuentoMN());
				obj.put("recargoDescuentoMLC", vp.getRecargoDescuentoMLC());
				obj.put("totalMN", CalculoEco.redondeo2((vp.getCantProdVale()*vp.getPrecioMNVale()) + vp.getRecargoDescuentoMN()));
				obj.put("totalMLC", CalculoEco.redondeo2((vp.getCantProdVale()*vp.getPrecioMLCVale()) + vp.getRecargoDescuentoMLC()));
				break;
			case RecepcionACiegas:
				obj.put("codigo", vp.getProducto().getCodigo());
				obj.put("unidadMedida", vp.getProducto().getUnidad()
						.getNombre());
				break;
			default:
				obj.put("recargoDescuentoMN", vp.getRecargoDescuentoMN());
				obj.put("recargoDescuentoMLC", vp.getRecargoDescuentoMLC());
				obj.put("totalMN", vp.getTotalMN());
				obj.put("totalMLC", vp.getTotalMLC());
				break;				
			}
			
			data.put(obj);
		}

		json.put("totalCount", bsr.getTotalCount());
		json.put("data", data);

		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json", json.toString());
		return mv;
	}

	// Guardar el vale Producto
	public ModelAndView guardarValeProducto(HttpServletRequest request,
			HttpServletResponse response, ValeProducto vnItem) throws Exception {

		JSONObject resp = new JSONObject();
		Long idvale = ArcheTypeUtils.getIdParameter(request, "idVale");
		vnItem.getVale().setId(idvale);

		BindException errors = new BindException(vnItem, "command");

		if (errors.hasErrors()) {
			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors", errors.getFieldErrors());
			return mv;
		}
		try {
			Vale vale = (Vale) ((IValeProductoServicio) getServicio())
					.obtenerV(idvale);
			if (vale.getEstadoVale() == EstadoVale.Confirmado) {
				throw new Exception(
						"Estos producto no puede sufrir modificaciones porque el vale ya esta Confirmado");
			}
			if (vnItem instanceof PersistentObject) {
				if (vnItem.getId() == null) {
					((IValeProductoServicio) getServicio()).adicionar(vnItem);
				} else {
					((IValeProductoServicio) getServicio()).actualizar(vnItem);
				}
			}
		} catch (DataAccessException e) {
			resolveErrors(errors, e);
			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors", errors.getFieldErrors());
			return mv;
		} catch (Exception ee) {
			String codes[] = { ee.getMessage() };
			FieldError fieldError = new FieldError("idvale", "existencia",
					null, false, codes, null, ee.getMessage());
			errors.addError(fieldError);

			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors", errors.getFieldErrors());
			return mv;
		}
		resp = ArcheTypeUtils.getSuccess();
		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json", resp.toString());
		return mv;
	}

	// eliminar vale producto
	public ModelAndView eliminarValeProducto(HttpServletRequest request,
			HttpServletResponse response, PersistentObject object)
			throws Exception {
		JSONObject resp;
		BindException errors = new BindException(getNomenclador(), "command");
		Long idvaleproduc = ArcheTypeUtils.getIdParameter(request, "id");

		try {
			ValeProducto valep = (ValeProducto) getServicio().obtener(
					idvaleproduc);
			if (valep.getVale().getEstadoVale() == EstadoVale.Confirmado) {
				throw new Exception(
						"Estos producto no puede sufrir modificaciones porque el vale ya esta Confirmado");
			}
			if (object.getId() == null) {
				resp = ArcheTypeUtils.getFailure(null);
			} else {
				getServicio().eliminar(object.getId());
				resp = ArcheTypeUtils.getSuccess();
			}
		} catch (DataAccessException e) {
			resolveErrors(errors, e);

			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors", errors.getFieldErrors());
			return mv;
		} catch (Exception ee) {
			String codes[] = { ee.getMessage() };
			FieldError fieldError = new FieldError("idvale", "existencia",
					null, false, codes, null, ee.getMessage());
			errors.addError(fieldError);

			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors", errors.getFieldErrors());
			return mv;
		}

		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json", resp.toString());
		return mv;
	}

	// esto es para los editar de los detalles

	public ModelAndView editarValeProductoDetalles(HttpServletRequest request,
			HttpServletResponse response, String editarvista) throws Exception {

		ModelAndView model = new ModelAndView(editarvista);
		model.addObject("nomenclador", getNomenclador());
		model.addObject("nombreNomenclador", getNombreNomenclador());
		model.addObject("modulo", getModulo());
		Long id = ArcheTypeUtils.getIdParameter(request, "id");
		Long idvale = ArcheTypeUtils.getIdParameter(request, "idVale");
		model.addObject("command2", idvale);
		Object obj;
		if (id == null) {
			obj = getServicio().getClazz().newInstance();
		} else {
			obj = getServicio().obtener(id);
			model.addObject("command", obj);
		}

		return model;
	}

	public ModelAndView editarDevolValeProductos(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return editarValeProductoDetalles(request, response,
				getEditarDevolValeProducto());
	}

	public ModelAndView editarValeProductoSalida(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return editarValeProductoDetalles(request, response,
				getEditarSalidaValeProducto());
	}

	public ModelAndView editarValeProductoEntrada(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return editarValeProductoDetalles(request, response,
				getEditarEntradaValeProducto());
	}

	public void setEditarDevolValeProducto(String editarDevolValeProducto) {
		this.editarDevolValeProducto = editarDevolValeProducto;
	}

	public String getEditarDevolValeProducto() {
		return editarDevolValeProducto;
	}

}
