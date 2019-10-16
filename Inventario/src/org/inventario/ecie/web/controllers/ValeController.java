package org.inventario.ecie.web.controllers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.archetype.common.business.ICommonService;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.archetype.common.domain.PersistentObject;
import org.archetype.common.web.controllers.CommonController;
import org.archetype.common.web.utils.ArcheTypeUtils;
import org.hibernate.Hibernate;
import org.hibernate.exception.ConstraintViolationException;
import org.inventario.ecie.business.IEcieServicio;
import org.inventario.ecie.business.ITarjeteroServicio;
import org.inventario.ecie.business.IValeServicio;
import org.inventario.ecie.dao.IValeDao;
import org.inventario.ecie.domain.Ecie;
import org.inventario.ecie.domain.EstadoVale;
import org.inventario.ecie.domain.TipoVale;
import org.inventario.ecie.domain.Vale;
import org.inventario.ecie.domain.ValeProducto;
import org.inventario.ecie.web.utils.MetComunes;
import org.inventario.ecie.web.validators.ValeValidator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.pdf.PRAcroForm;

public class ValeController extends CommonController {

	private ITarjeteroServicio tarjeteroServicio;
	private IEcieServicio ecieServicio;
	
	private String listarReclamacionView, editarReclamacionView,detallesReclamacionView;
	private String listarDevolucionView, editarDevolucionView,detallesDevolucionView;
	private String listarRecepcionView, editarRecepcionView,detallesRecepcionView;
	private String listarCargaInicialView, editarCargaInicialView,detallesCargaInicialView;
	private String listarRecepcionACiegasView, editarRecepcionACiegasView,detallesRecepcionACiegasView;
	private String listarSolicitudEntregaView, editarSolicitudEntregaView,detallesSolicitudEntregaView;
	private String listarEntradaPorAjusteView, editarEntradaPorAjustView,detallesEntradaPorAjustView;
	private String listarSalidaPorAjusteView, editarSalidaPorAjusteView,detallesSalidaPorAjusteView;
	private String listarTransferenciaEntreAlmacenesView,editarTransferenciaEntreAlmacenesView,	detallesTransEntreAlmacenesView;
	private String listarTransferenciaSalidaView,editarTransferenciaSalidaView, detallesTransferenciaSalidaView;
	private String listarTransferenciaEntradaView,editarTransferenciaEntradaView, detallesTransferenciaEntradaView;
	private String listarFacturaMNView, editarFacturaMNView, detallesFacturaMNView;
	private String listarFacturaMLCView, editarFacturaMLCView, detallesFacturaMLCView;
	private String listarFactura_MN_MLCView, editarFactura_MN_MLCView, detallesFactura_MN_MLCView;
	
	public String getDetallesSalidaPorAjusteView() {
		return detallesSalidaPorAjusteView;
	}

	public String getListarFacturaMNView() {
		return listarFacturaMNView;
	}

	public void setListarFacturaMNView(String listarFacturaMNView) {
		this.listarFacturaMNView = listarFacturaMNView;
	}

	public String getEditarFacturaMNView() {
		return editarFacturaMNView;
	}

	public void setEditarFacturaMNView(String editarFacturaMNView) {
		this.editarFacturaMNView = editarFacturaMNView;
	}

	public String getDetallesFacturaMNView() {
		return detallesFacturaMNView;
	}

	public void setDetallesFacturaMNView(String detallesFacturaMNView) {
		this.detallesFacturaMNView = detallesFacturaMNView;
	}

	public String getListarFacturaMLCView() {
		return listarFacturaMLCView;
	}

	public void setListarFacturaMLCView(String listarFacturaMLCView) {
		this.listarFacturaMLCView = listarFacturaMLCView;
	}

	public String getEditarFacturaMLCView() {
		return editarFacturaMLCView;
	}

	public void setEditarFacturaMLCView(String editarFacturaMLCView) {
		this.editarFacturaMLCView = editarFacturaMLCView;
	}

	public String getDetallesFacturaMLCView() {
		return detallesFacturaMLCView;
	}

	public void setDetallesFacturaMLCView(String detallesFacturaMLCView) {
		this.detallesFacturaMLCView = detallesFacturaMLCView;
	}

	public String getListarFactura_MN_MLCView() {
		return listarFactura_MN_MLCView;
	}

	public void setListarFactura_MN_MLCView(String listarFacturaMNMLCView) {
		listarFactura_MN_MLCView = listarFacturaMNMLCView;
	}

	public String getEditarFactura_MN_MLCView() {
		return editarFactura_MN_MLCView;
	}

	public void setEditarFactura_MN_MLCView(String editarFacturaMNMLCView) {
		editarFactura_MN_MLCView = editarFacturaMNMLCView;
	}

	public String getDetallesFactura_MN_MLCView() {
		return detallesFactura_MN_MLCView;
	}

	public void setDetallesFactura_MN_MLCView(String detallesFacturaMNMLCView) {
		detallesFactura_MN_MLCView = detallesFacturaMNMLCView;
	}

	public String getListarReclamacionView() {
		return listarReclamacionView;
	}

	public void setListarReclamacionView(String listarReclamacionView) {
		this.listarReclamacionView = listarReclamacionView;
	}

	public String getEditarReclamacionView() {
		return editarReclamacionView;
	}

	public void setEditarReclamacionView(String editarReclamacionView) {
		this.editarReclamacionView = editarReclamacionView;
	}

	public String getDetallesReclamacionView() {
		return detallesReclamacionView;
	}

	public void setDetallesReclamacionView(String detallesReclamacionView) {
		this.detallesReclamacionView = detallesReclamacionView;
	}

	public String getListarDevolucionView() {
		return listarDevolucionView;
	}

	public void setListarDevolucionView(String listarDevolucionView) {
		this.listarDevolucionView = listarDevolucionView;
	}

	public String getEditarDevolucionView() {
		return editarDevolucionView;
	}

	public void setEditarDevolucionView(String editarDevolucionView) {
		this.editarDevolucionView = editarDevolucionView;
	}

	public String getDetallesDevolucionView() {
		return detallesDevolucionView;
	}

	public void setDetallesDevolucionView(String detallesDevolucionView) {
		this.detallesDevolucionView = detallesDevolucionView;
	}

	public void setDetallesSalidaPorAjusteView(
			String detallesSalidaPorAjusteView) {
		this.detallesSalidaPorAjusteView = detallesSalidaPorAjusteView;
	}

	public String getDetallesTransEntreAlmacenesView() {
		return detallesTransEntreAlmacenesView;
	}

	public void setDetallesTransEntreAlmacenesView(
			String detallesTransEntreAlmacenesView) {
		this.detallesTransEntreAlmacenesView = detallesTransEntreAlmacenesView;
	}

	public String getDetallesSolicitudEntregaView() {
		return detallesSolicitudEntregaView;
	}

	public String getDetallesEntradaPorAjustView() {
		return detallesEntradaPorAjustView;
	}

	public void setDetallesEntradaPorAjustView(
			String detallesEntradaPorAjustView) {
		this.detallesEntradaPorAjustView = detallesEntradaPorAjustView;
	}

	public void setDetallesSolicitudEntregaView(
			String detallesSolicitudEntregaView) {
		this.detallesSolicitudEntregaView = detallesSolicitudEntregaView;
	}

	public String getListarRecepcionACiegasView() {
		return listarRecepcionACiegasView;
	}

	public String getDetallesRecepcionView() {
		return detallesRecepcionView;
	}

	public void setDetallesRecepcionView(String detallesRecepcionView) {
		this.detallesRecepcionView = detallesRecepcionView;
	}

	public void setListarRecepcionACiegasView(String listarRecepcionACiegasView) {
		this.listarRecepcionACiegasView = listarRecepcionACiegasView;
	}

	public String getEditarRecepcionACiegasView() {
		return editarRecepcionACiegasView;
	}

	public void setEditarRecepcionACiegasView(String editarRecepcionACiegasView) {
		this.editarRecepcionACiegasView = editarRecepcionACiegasView;
	}

	public String getListarSolicitudEntregaView() {
		return listarSolicitudEntregaView;
	}

	public void setListarSolicitudEntregaView(String listarSolicitudEntregaView) {
		this.listarSolicitudEntregaView = listarSolicitudEntregaView;
	}

	public String getEditarSolicitudEntregaView() {
		return editarSolicitudEntregaView;
	}

	public void setEditarSolicitudEntregaView(String editarSolicitudEntregaView) {
		this.editarSolicitudEntregaView = editarSolicitudEntregaView;
	}

	public String getListarEntradaPorAjusteView() {
		return listarEntradaPorAjusteView;
	}

	public void setListarEntradaPorAjusteView(String listarEntradaPorAjusteView) {
		this.listarEntradaPorAjusteView = listarEntradaPorAjusteView;
	}

	public String getEditarEntradaPorAjustView() {
		return editarEntradaPorAjustView;
	}

	public void setEditarEntradaPorAjustView(String editarEntradaPorAjustView) {
		this.editarEntradaPorAjustView = editarEntradaPorAjustView;
	}

	public String getListarSalidaPorAjusteView() {
		return listarSalidaPorAjusteView;
	}

	public void setListarSalidaPorAjusteView(String listarSalidaPorAjusteView) {
		this.listarSalidaPorAjusteView = listarSalidaPorAjusteView;
	}

	public String getEditarSalidaPorAjusteView() {
		return editarSalidaPorAjusteView;
	}

	public void setEditarSalidaPorAjusteView(String editarSalidaPorAjusteView) {
		this.editarSalidaPorAjusteView = editarSalidaPorAjusteView;
	}

	public String getListarTransferenciaEntreAlmacenesView() {
		return listarTransferenciaEntreAlmacenesView;
	}

	public void setListarTransferenciaEntreAlmacenesView(
			String listarTransferenciaEntreAlmacenesView) {
		this.listarTransferenciaEntreAlmacenesView = listarTransferenciaEntreAlmacenesView;
	}

	public String getEditarTransferenciaEntreAlmacenesView() {
		return editarTransferenciaEntreAlmacenesView;
	}

	public void setEditarTransferenciaEntreAlmacenesView(
			String editarTransferenciaEntreAlmacenesView) {
		this.editarTransferenciaEntreAlmacenesView = editarTransferenciaEntreAlmacenesView;
	}

	public String getEditarRecepcionView() {
		return editarRecepcionView;
	}

	public void setEditarRecepcionView(String editarRecepcionView) {
		this.editarRecepcionView = editarRecepcionView;
	}

	public String getListarRecepcionView() {
		return listarRecepcionView;
	}

	public void setListarRecepcionView(String listarRecepcionView) {
		this.listarRecepcionView = listarRecepcionView;
	}

	public void setEditarCargaInicialView(String editarCargaInicialView) {
		this.editarCargaInicialView = editarCargaInicialView;
	}

	public String getEditarCargaInicialView() {
		return editarCargaInicialView;
	}

	public void setListarCargaInicialView(String listarCargaInicialView) {
		this.listarCargaInicialView = listarCargaInicialView;
	}

	public String getListarCargaInicialView() {
		return listarCargaInicialView;
	}

	public void setDetallesCargaInicialView(String detallesCargaInicialView) {
		this.detallesCargaInicialView = detallesCargaInicialView;
	}

	public String getDetallesCargaInicialView() {
		return detallesCargaInicialView;
	}

	public ModelAndView guardarVale(HttpServletRequest request,
			HttpServletResponse response, Vale vnItem) throws Exception {
		JSONObject resp;
		Vale vale = new Vale();
		BindException errorsbd = new BindException(vnItem, "command");
		BindException errors = new BindException(vale, "id");
		if (getValidator() != null && getValidator().supports(Vale.class)) {
			ValidationUtils.invokeValidator(getValidator(), vnItem, errors);
		}
		if (errorsbd.hasErrors()) {
			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors", errorsbd.getFieldErrors());
			return mv;
		}
		// devolver el objeto inicializado
		initialize(vnItem, request, errorsbd);

		// poner a null los elementos no inicializados
		ArcheTypeUtils.cleanHibernateTransientObject(vnItem);
		try {
			if (vnItem.getId() != null) {
				vale = (Vale) getServicio().obtener(vnItem.getId());
				ValeValidator.resolverConfirmados(vale);
			}
			if (((PersistentObject) vnItem).getId() == null) {

				((IValeServicio) getServicio()).adicionar(vnItem);

			} else {
				((IValeServicio) getServicio()).actualizar(vnItem);
			}

		} catch (DataAccessException e) {
			resolveErrors(errorsbd, e);
			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors", errorsbd.getFieldErrors());
			return mv;
		} catch (Exception ee) {
			String codes[] = { ee.getMessage() };
			FieldError fieldError = new FieldError("idvale", "estado", null,
					false, codes, null, null);
			errors.addError(fieldError);

			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors", errors.getFieldErrors());
			return mv;
		}
		resp = ArcheTypeUtils.getSuccess();

		if (vnItem instanceof PersistentObject) {
			resp.put("id", ((PersistentObject) vnItem).getId());
		}

		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json", resp.toString());
		return mv;
	}

	/*public ModelAndView listarFecha(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {
		String tipo = request.getParameter("tipo");
		BaseSearchResult bsr = ((IValeServicio) getServicio())
				.listarFecha(tipo);
		JSONObject json = new JSONObject();
		Collection<Object> lista = bsr.getResults();
		JSONArray data = new JSONArray();
		int cont = 1;
		for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
			Date item = (Date) iterator.next();
			JSONObject obj = ArcheTypeUtils.getJSONObject(item, null, true);
			obj.put("fecha", item);
			obj.put("id", cont++);
			data.put(obj);
		}
		json.put("totalCount", bsr.getTotalCount());
		json.put("data", data);

		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json", json.toString());
		return mv;
	}*/

	// Metodo listar los vale segun el tipo de vale
	
	public ModelAndView listarSoliAnno(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {
		String subCodigo = request.getParameter("query");
		BaseSearchResult bsr = ((IValeServicio) getServicio()).listarSoliAnno(egr,subCodigo);
		Collection<Object> lista = bsr.getResults();

		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();

		for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
			Vale item = ((Vale) iterator.next());
			JSONObject obj = ArcheTypeUtils.getJSONObject(item, null, true);

			obj.put("id", item.getId());
			obj.put("fechaVale", item.getFechaVale());
			obj.put("noVale", item.getNoVale());
			obj.put("noControl", item.getNoControl());
			obj.put("tipoVale", item.getTipoVale());
			obj.put("estadoVale", item.getEstadoVale());
			obj.put("comentario", item.getComentario());
			obj.put("almacenOrigen", item.getAlmacenOrigen().getNombre());
			obj.put("almacenOrigenId", item.getAlmacenOrigen().getId());
			obj.put("recibido", item.getRecibido().getNombre());
			obj.put("centroCosto", item.getCentroCosto());
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
		String tipo = request.getParameter("tipo");
		String fechaIni = request.getParameter("fechaI");
		String fechaFin = request.getParameter("fechaF");
		BaseSearchResult bsr = ((IValeServicio) getServicio()).listarValeDadoTipoVale(tipo, fechaIni,fechaFin,egr);

		Collection<Object> lista = bsr.getResults();

		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();

		for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
			Vale item = ((Vale) iterator.next());
			JSONObject obj = ArcheTypeUtils.getJSONObject(item, null, true);

			obj.put("id", item.getId());
			obj.put("fechaVale", item.getFechaVale());
			obj.put("noVale", item.getNoVale());
			obj.put("noControl", item.getNoControl());
			obj.put("importeNeto", item.getImporteNeto());
			obj.put("tipoVale", item.getTipoVale());
			obj.put("estadoVale", item.getEstadoVale());

			TipoVale tipoP = null;
			switch (tipoP.valueOf(tipo)) {
			case Recepcion:
				obj.put("fchRecibido", item.getFchRecibido());
				obj.put("comentario", item.getComentario());
				obj.put("totalMN", item.getTotalMN());
				obj.put("totalMLC", item.getTotalMLC());
				if (item.getRecargoDescuentoMLC() > 0) {
					obj.put("recargoMLC", item.getRecargoDescuentoMLC());	
				} else {
					obj.put("descuentoMLC", item.getRecargoDescuentoMLC());
				}
				if (item.getRecargoDescuentoMN() > 0) {
					obj.put("recargoMN", item.getRecargoDescuentoMN());	
				} else {
					obj.put("descuentoMN", item.getRecargoDescuentoMN());
				}
				obj.put("empresa", item.getEmpresa().getNombre());
				obj.put("almacenDestino", item.getAlmacenDestino().getNombre());
				obj.put("recibido", item.getRecibido().getNombre());
				obj.put("noDoc", item.getNoDoc());
				obj.put("nombreDoc", item.getNombreDoc());
				obj.put("nombreTransportador", item.getNombreTransportador());
				obj.put("cITransportador", item.getcITransportador());
				obj.put("noTransporte", item.getNoTransporte());
				obj.put("importeMLC", item.getImporteMLC());
				obj.put("importeMN", item.getImporteMN());
				break;
			case CargaInicial:
				obj.put("almacenDestino", item.getAlmacenDestino().getNombre());
				break;
			case Reclamacion:
				obj.put("empresa", item.getEmpresa().getNombre());
				break;	
			case Devolucion:
				obj.put("almacenDestino", item.getAlmacenDestino().getNombre());
				obj.put("centroCosto", item.getCentroCosto());
				obj.put("ValeReferencia", item.getNoDoc());

				break;
			case SolicitudEntrega:
				obj.put("fchRecibido", item.getFchRecibido());
				obj.put("fchResponsAutoriza", item.getFchResponsAutoriza());
				obj.put("fchSolicElab", item.getFchSolicElab());
				obj.put("comentario", item.getComentario());
				obj.put("totalMN", item.getTotalMN());
				obj.put("totalMLC", item.getTotalMLC());
				obj.put("almacenOrigen", item.getAlmacenOrigen().getNombre());
				obj.put("almacenOrigenId", item.getAlmacenOrigen().getId());
				obj.put("recibido", item.getRecibido().getNombre());
				obj.put("centroCosto", item.getCentroCosto());
				break;
			case EntradaPorAjuste:
				obj.put("fchRecibido", item.getFchRecibido());
				obj.put("comentario", item.getComentario());
				obj.put("totalMN", item.getTotalMN());
				obj.put("totalMLC", item.getTotalMLC());
				obj.put("almacenDestino", item.getAlmacenDestino().getNombre());
				obj.put("recibido", item.getRecibido().getNombre());
				obj.put("centroCosto", item.getCentroCosto());
				break;
			case SalidaPorAjuste:
				obj.put("fchRecibido", item.getFchRecibido());
				obj.put("fchResponsAutoriza", item.getFchResponsAutoriza());
				obj.put("fchSolicElab", item.getFchSolicElab());
				obj.put("comentario", item.getComentario());
				obj.put("totalMN", item.getTotalMN());
				obj.put("totalMLC", item.getTotalMLC());
				obj.put("almacenOrigen", item.getAlmacenOrigen().getNombre());
				obj.put("responsAutoriza", item.getResponsAutoriza()
						.getNombre());
				obj.put("solicElab", item.getSolicElab().getNombre());
				obj.put("recibido", item.getRecibido().getNombre());
				obj.put("centroCosto", item.getCentroCosto());
				break;
			case RecepcionACiegas:
				obj.put("fchRecibido", item.getFchRecibido());
				obj.put("comentario", item.getComentario());
				obj.put("empresa", item.getEmpresa().getNombre());
				obj.put("almacenDestino", item.getAlmacenDestino().getNombre());
				obj.put("recibido", item.getRecibido().getNombre());
				break;
			case TransferenciaEntrada:
				obj.put("empresa", item.getEmpresa().getNombre());
				obj.put("almacenDestino", item.getAlmacenDestino().getNombre());
				obj.put("importeMLC", item.getImporteMLC());
				obj.put("importeMN", item.getImporteMN());
				obj.put("recibido", item.getRecibido().getNombre());
				obj.put("noDoc", item.getNoDoc());
				obj.put("nombreDoc", item.getNombreDoc());
				obj.put("comentario", item.getComentario());
				obj.put("nombreTransportador", item.getNombreTransportador());
				obj.put("cITransportador", item.getcITransportador());
				obj.put("noTransporte", item.getNoTransporte());
				break;
			case TransferenciaSalida:
				obj.put("almacenOrigen", item.getAlmacenOrigen().getNombre());
				obj.put("empresa", item.getEmpresa().getNombre());
				obj.put("importeMLC", item.getImporteMLC());
				obj.put("importeMN", item.getImporteMN());
				obj.put("noDoc", item.getNoDoc());
				obj.put("nombreDoc", item.getNombreDoc());
				obj.put("comentario", item.getComentario());
				obj.put("nombreTransportador", item.getNombreTransportador());
				obj.put("cITransportador", item.getcITransportador());
				obj.put("noTransporte", item.getNoTransporte());
				obj.put("responsAutoriza", item.getResponsAutoriza()
						.getNombre());
				break;
			case TransferenciaEntreAlmacenes:
				obj.put("fchResponsAutoriza", item.getFchResponsAutoriza());
				obj.put("comentario", item.getComentario());
				obj.put("totalMN", item.getTotalMN());
				obj.put("totalMLC", item.getTotalMLC());
				obj.put("almacenOrigen", item.getAlmacenOrigen().getNombre());
				obj.put("almacenDestino", item.getAlmacenDestino().getNombre());
				obj.put("responsAutoriza", item.getResponsAutoriza()
						.getNombre());
				break;
			default:
				obj.put("fchRecibido", item.getFchRecibido());
				obj.put("comentario", item.getComentario());
				obj.put("totalMN", item.getTotalMN());
				obj.put("totalMLC", item.getTotalMLC());
				obj.put("empresa", item.getEmpresa().getNombre());
				obj.put("almacenOrigen", item.getAlmacenOrigen().getNombre());
				obj.put("recibido", item.getRecibido().getNombre());
				obj.put("noDoc", item.getNoDoc());
				obj.put("nombreDoc", item.getNombreDoc());
				obj.put("nombreTransportador", item.getNombreTransportador());
				obj.put("cITransportador", item.getcITransportador());
				obj.put("noTransporte", item.getNoTransporte());
				obj.put("importeMLC", item.getImporteMLC());
				obj.put("importeMN", item.getImporteMN());
				obj.put("recargoDescuentoMN", item.getRecargoDescuentoMN());
				obj.put("recargoDescuentoMLC", item.getRecargoDescuentoMLC());				
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

	@SuppressWarnings("static-access")
	public ModelAndView listarSolicitudesConfirmadas(
			HttpServletRequest request, HttpServletResponse response,
			ExtGridRequest egr) throws Exception {
		String tipo = request.getParameter("tipo");
		BaseSearchResult bsr = ((IValeServicio) getServicio())
				.listarSolicitudesConfirmados();

		Collection<Object> lista = bsr.getResults();

		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();

		for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
			Vale item = ((Vale) iterator.next());
			JSONObject obj = ArcheTypeUtils.getJSONObject(item, null, true);

			obj.put("id", item.getId());
			obj.put("fechaVale", item.getFechaVale());
			obj.put("noVale", item.getNoVale());
			obj.put("noControl", item.getNoControl());
			obj.put("almacenOrigen", item.getAlmacenOrigen().getNombre());
			data.put(obj);
		}

		json.put("totalCount", bsr.getTotalCount());
		json.put("data", data);

		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json", json.toString());
		return mv;
	}

	// estos metodos son para mostrar el listado de todos los vales...es como el
	// mostar lista de common controller

	public ModelAndView mostrarLista(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String param = request.getParameter("param");
		TipoVale tipo = null;
		switch (tipo.valueOf(param)) {
		case CargaInicial:
			super.setListarView(getListarCargaInicialView());
			break;
		case Recepcion:
			super.setListarView(getListarRecepcionView());
			break;
		case EntradaPorAjuste:
			super.setListarView(getListarEntradaPorAjusteView());
			break;
		case RecepcionACiegas:
			super.setListarView(getListarRecepcionACiegasView());
			break;
		case SalidaPorAjuste:
			super.setListarView(getListarSalidaPorAjusteView());
			break;
		case SolicitudEntrega:
			super.setListarView(getListarSolicitudEntregaView());
			break;
		case TransferenciaEntrada:
			super.setListarView(getListarTransferenciaEntradaView());
			break;
		case TransferenciaSalida:
			super.setListarView(getListarTransferenciaSalidaView());
			break;
		case TransferenciaEntreAlmacenes:
			super.setListarView(getListarTransferenciaEntreAlmacenesView());
			break;
		case Devolucion:
			super.setListarView(getListarDevolucionView());
			break;
		case Reclamacion:
			super.setListarView(getListarReclamacionView());
			break;
		case Factura_MN_MLC:
			super.setListarView(getListarFactura_MN_MLCView());
			break;
		case FacturaMLC:
			super.setListarView(getListarFacturaMLCView());
			break;
		case FacturaMN:
			super.setListarView(getListarFacturaMNView());
			break;
		}
		// String tipoVale = Subtipovale[0];
		return super.mostrarLista(request, response);
	}

	// estos son para mostrar la vista de editar de los vales ...como el metodo
	// editar de common controller
	public ModelAndView editar(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String param = request.getParameter("tipo");
		Long id = null ;
		if (param == null) {
			id = ArcheTypeUtils.getIdParameter(request, "id");
			Vale vale = (Vale)((IValeServicio)getServicio()).obtener(id);
			param = vale.getTipoVale().toString();
		}
		BindException errors = new BindException(param, "command");
		try {
			Vale vale = ((IValeServicio)getServicio()).ValeSinConfirmar();
			if ((vale != null)) {
				if (!vale.getId().equals(id)) {
					ConstraintViolationException ex = new ConstraintViolationException("error", null, "ValeSinConfirm");
					DataIntegrityViolationException exception = new DataIntegrityViolationException("error", ex);
					throw exception;	
				}
					
			}
		} catch (DataAccessException e) {
			resolveErrors(errors, e);
			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors", errors.getFieldErrors());
			return mv;
		}
		TipoVale tipo = null;
		switch (tipo.valueOf(param)) {
		case CargaInicial:
			super.setEditarView(getEditarCargaInicialView());
			break;
		case Devolucion:
			super.setEditarView(getEditarDevolucionView());
			break;
		case EntradaPorAjuste:
			super.setEditarView(getEditarEntradaPorAjustView());
			break;
		case Recepcion:
			super.setEditarView(getEditarRecepcionView());
			break;
		case RecepcionACiegas:
			super.setEditarView(getEditarRecepcionACiegasView());
			break;
		case Reclamacion:
			super.setEditarView(getEditarReclamacionView());
			break;
		case SalidaPorAjuste:
			super.setEditarView(getEditarSalidaPorAjusteView());
			break;
		case SolicitudEntrega:
			super.setEditarView(getEditarSolicitudEntregaView());
			break;
		case TransferenciaEntrada:
			super.setEditarView(getEditarTransferenciaEntradaView());
			break;
		case TransferenciaEntreAlmacenes:
			super.setEditarView(getEditarTransferenciaEntreAlmacenesView());
			break;
		case TransferenciaSalida:
			super.setEditarView(getEditarTransferenciaSalidaView());
			break;			
		case Factura_MN_MLC:
			super.setEditarView(getEditarFactura_MN_MLCView());
			break;			
		case FacturaMLC:
			super.setEditarView(getEditarFacturaMLCView());
			break;			
		case FacturaMN:
			super.setEditarView(getEditarFacturaMNView());
			break;			
				
		}
		return super.editar(request, response);
	}
	
	// estos son para mostrar los detalles de los vales ...como el metodo
	// detalles de common controller
	public ModelAndView detallesFacturaMN(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setDetallesView(getDetallesFacturaMNView());

		return super.detalles(request, response);
	}
	
	public ModelAndView detallesFacturaMLC(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setDetallesView(getDetallesFacturaMLCView());

		return super.detalles(request, response);
	}
	
	public ModelAndView detallesFactura_MN_MLC(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setDetallesView(getDetallesFactura_MN_MLCView());

		return super.detalles(request, response);
	}
	public ModelAndView detallesTransEntreAlmacenes(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setDetallesView(detallesTransEntreAlmacenesView);

		return super.detalles(request, response);
	}
	
	public ModelAndView detallesReclamacion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setDetallesView(getDetallesReclamacionView());

		return super.detalles(request, response);
	}
	
	public ModelAndView detallesRecepcion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setDetallesView(getDetallesRecepcionView());

		return super.detalles(request, response);
	}

	public ModelAndView detallesDevolucion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setDetallesView(getDetallesDevolucionView());

		return super.detalles(request, response);
	}

	public ModelAndView detallesRecepcionACiega(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setDetallesView(getDetallesRecepcionACiegasView());

		return super.detalles(request, response);
	}

	public ModelAndView detallesSolicitudEntrega(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setDetallesView(getDetallesSolicitudEntregaView());

		return super.detalles(request, response);
	}

	public ModelAndView detallesEntradaPorAjuste(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setDetallesView(detallesEntradaPorAjustView);

		return super.detalles(request, response);
	}

	public ModelAndView detallesSalidaPorAjuste(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setDetallesView(detallesSalidaPorAjusteView);

		return super.detalles(request, response);
	}

	public ModelAndView detallesCargaInicial(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setDetallesView(detallesCargaInicialView);

		return super.detalles(request, response);
	}

	public ModelAndView detallesTransferenciaEntrada(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.setDetallesView(detallesTransferenciaEntradaView);

		return super.detalles(request, response);
	}

	public ModelAndView detallesTransferenciaSalida(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setDetallesView(detallesTransferenciaSalidaView);

		return super.detalles(request, response);
	}


	@SuppressWarnings("unchecked")
	public ModelAndView reclamacion(HttpServletRequest request, HttpServletResponse response,Vale vnItem) throws Exception {
		
		Serializable id = ArcheTypeUtils.getIdParameter(request, "id");
		Vale vale = (Vale) ((IValeServicio) getServicio()).obtener(id);
		BindException errors = new BindException(vnItem, "command");
		
		try {
			Vale valesinconfir = ((IValeServicio)getServicio()).ValeSinConfirmar();
			if ((valesinconfir != null)) {
				if (!vale.getId().equals(id)) {
					ConstraintViolationException ex = new ConstraintViolationException("error", null, "ValeSinConfirm");
					DataIntegrityViolationException exception = new DataIntegrityViolationException("error", ex);
					throw exception;	
				}
					
			}
			if (vale.getNoDoc() != null) {
				Vale valeReclamacion = ((IValeServicio)getServicio()).obtenerNoDoc(vale.getNoDoc());
				if (valeReclamacion != null) {
					ConstraintViolationException ex = new ConstraintViolationException("error", null, "ReclamacionDuplicada");
					DataIntegrityViolationException exception = new DataIntegrityViolationException("error", ex);
					throw exception;
				}	
			}			
			ValeValidator.resolverReclamaciones(vale);
			BaseSearchResult bsr = ((IValeServicio) getServicio()).listarValeProducto(id, vale.getTipoVale().toString());
			Collection<Object> lista = bsr.getResults();
			Set<ValeProducto> listVP = new HashSet<ValeProducto>();
			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				ValeProducto object = (ValeProducto) iterator.next();
				listVP.add(object);
			}
			vale.setValeProducto(listVP);
			getServicio().actualizar(vale);
		} catch (DataAccessException e) {
			resolveErrors(errors, e);
			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors", errors.getFieldErrors());
			return mv;
		}
		
		vnItem = vale;
		vnItem.setId(null);
		vnItem.setTipoVale(TipoVale.Reclamacion);
		vnItem.setAlmacenOrigen(vale.getAlmacenDestino());
		vnItem.setAlmacenDestino(vale.getAlmacenDestino());
		vnItem.setEmpresa(vale.getEmpresa());
		vnItem.setEstadoVale(EstadoVale.Confeccion);
		return guardarVale(request, response, vnItem);
	}
	
	public ModelAndView confirmarReclamacion(
			HttpServletRequest request, HttpServletResponse response,
			Vale vnItem) throws Exception {
		JSONObject resp;
		Serializable id = ArcheTypeUtils.getIdParameter(request, "id");
		Vale obj =(Vale)((IValeServicio)getServicio()).obtener(id); 
		BindException errors = new BindException(obj, "command");
		if (getValidator() != null && getValidator().supports(getClass())) {
			ValidationUtils.invokeValidator(getValidator(), obj, errors);
		}
		if (errors.hasErrors()) {
			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors", errors.getFieldErrors());
			return mv;
		}
		try {
			obj.setEstadoVale(EstadoVale.Confirmado);
			getServicio().actualizar(obj);			
		} catch (DataAccessException e) {
			resolveErrors(errors, e);
			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors", errors.getFieldErrors());
			return mv;
		}
		resp = ArcheTypeUtils.getSuccess();
		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json", resp.toString());
		return mv;
	}
	
	
	@SuppressWarnings("unchecked")
	public ModelAndView confirmarRecepcionACiegaVale(
			HttpServletRequest request, HttpServletResponse response,
			Vale vnItem) throws Exception {
		// request.setAttribute("id", null);
		Serializable id = ArcheTypeUtils.getIdParameter(request, "id");
		Vale vale = (Vale) ((IValeServicio) getServicio()).obtener(id);
		BaseSearchResult bsr = ((IValeServicio) getServicio())
				.listarValeProducto(id, vale.getTipoVale().toString());
		Collection<Object> lista = bsr.getResults();
		Set<ValeProducto> listVP = new HashSet<ValeProducto>();
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			ValeProducto object = (ValeProducto) iterator.next();
			listVP.add(object);
		}
		vale.setValeProducto(listVP);
		Class clazz = Vale.class;
		Object obj = clazz.newInstance();
		this.bind(request, obj);

		BindException errors = new BindException(obj, "command");
		if (getValidator() != null && getValidator().supports(clazz)) {
			ValidationUtils.invokeValidator(getValidator(), obj, errors);
		}

		if (errors.hasErrors()) {
			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors", errors.getFieldErrors());
			return mv;
		}
		try {
			ValeValidator.resolverRecepACiegaConfirm(vale);
			((IValeServicio) getServicio()).confirmar(id);

		} catch (DataAccessException e) {
			resolveErrors(errors, e);
			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors", errors.getFieldErrors());
			return mv;
		}
		vnItem = vale;
		vnItem.setId(null);
		vnItem.setTipoVale(TipoVale.Recepcion);
		return guardarVale(request, response, vnItem);
	}

	public ModelAndView confirmarVale(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject resp;

		Serializable idVale = ArcheTypeUtils.getIdParameter(request, "id");
		BindException errors = new BindException(idVale, "idvale");
		try {
			Vale vale = (Vale) ((IValeServicio) getServicio()).obtener(idVale);
			ValeValidator.resolverConfirmados(vale);
			ValeValidator.resolverValeCeros(vale);
			if ((vale.getTipoVale() == TipoVale.Recepcion)
					&& (vale.getImporteNeto() > ((IValeServicio) getServicio())
							.comprobarImporteNeto(vale))) {
					ValeValidator.resolvereExcepciones("ImporteNeto");
			}
			((ITarjeteroServicio) getTarjeteroServicio())
					.PasarATarjetero(idVale);
			((IValeServicio) getServicio()).confirmar(idVale);

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

	public ModelAndView imprimir(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {

		String tipo = null;
		String nombreReporte = null;
		TipoVale tipoP = null;
		String fecha = null;
		String fechaFin = request.getParameter("fechaF");
		if (egr.getQuery() != null) {
			JSONObject query = new JSONObject(egr.getQuery().toString());
			tipo = query.optString("tipo");
			fecha = query.optString("fecha");
			
			if (fecha == null || fecha.length()== 0) {
				Ecie ecie = getEcieServicio().obtenerEcie();
				fecha = new SimpleDateFormat("yyyy-MM-dd").format(ecie.getFechaOperacion());
			} 
			
			switch (tipoP.valueOf(tipo)) {
			case CargaInicial:
				nombreReporte = "ValeCIReport";
				break;
			case Recepcion:
				nombreReporte = "ValeIRReport";
				break;
			case EntradaPorAjuste:
				nombreReporte = "ValeEPAReport";
				break;
			case RecepcionACiegas:
				nombreReporte = "ValeRACReport";
				break;
			case SalidaPorAjuste:
				nombreReporte = "ValeSPAReport";
				break;
			case SolicitudEntrega:
				nombreReporte = "ValeSEReport";
				break;
			case TransferenciaEntrada:
				nombreReporte = "ValeTEReport";
				break;
			case TransferenciaSalida:
				nombreReporte = "ValeTSReport";
				break;
			case TransferenciaEntreAlmacenes:
				nombreReporte = "ValeTEAReport";
				break;
			case Devolucion:
				nombreReporte = "ValeDReport";
				break;
			case Reclamacion:
				nombreReporte = "ValeIRecReport";
				break;	
			default:
				nombreReporte = "ValeFactReport";
				break;
			} 
			query.put("tipoVale", tipo);
			query.put("fechaVale",fecha);
			query.remove("tipo");
			query.remove("fecha");

			
			HttpServletRequest mockRequest = ArcheTypeUtils
					.createHttpServletRequestJSON(query);

			Object object = getServicio().getClazz().newInstance();
			this.bind(mockRequest, object);
			
			Date fe = java.sql.Date.valueOf(fecha);
			((Vale)object).setFechaVale(fe);
			egr.setQuery(object);
		}

		egr.setLimit(0);
		BaseSearchResult bsr = ((IValeServicio) getServicio())
				.listarValeDadoTipoVale(tipo, fecha,fechaFin,egr);
		ModelAndView mv = new ModelAndView(nombreReporte);

		mv.addObject("Logo", getLogo().getFile());
		mv.addObject("dataSource", bsr.getResults());
		response.setHeader("Content-Disposition",
				"attachment; filename=Listado_Vales_" + tipoP.valueOf(tipo)
						+ ".xls;");
		return mv;
	}

	public ModelAndView impDetalles(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {

		String queryString = request.getParameter("query");
		Long idvale = null;
		String tip = null;
		TipoVale tipoP = null;
		String nombreReporte = null;

		if (queryString != null) {
			JSONObject query = new JSONObject(queryString);
			idvale = query.getLong("idVale");
			tip = query.getString("tipo");
		}

		Vale vir = (Vale) getServicio().obtener(idvale);

		BaseSearchResult bsrValeProducto = ((IValeServicio) getServicio())
				.listarValeProducto(idvale, tip);
		List<Vale> valelist = new ArrayList<Vale>();
		valelist.add(vir);
		Ecie ecie = getEcieServicio().obtenerEcie();		

		switch (tipoP.valueOf(tip)) {
		case Recepcion:
			nombreReporte = "DetalleValeIRReport";
			break;
		case EntradaPorAjuste:
			nombreReporte = "DetalleValeEPAReport";
			break;
		case RecepcionACiegas:
			nombreReporte = "DetalleValeRACReport";
			break;
		case SalidaPorAjuste:
			nombreReporte = "DetalleValeSPAReport";
			break;
		case SolicitudEntrega:
			nombreReporte = "DetalleValeSEReport";
			break;
		case TransferenciaEntrada:
			nombreReporte = "DetalleValeTEReport";
			break;
		case TransferenciaSalida:
			nombreReporte = "DetalleValeTSReport";
			break;
		case TransferenciaEntreAlmacenes:
			nombreReporte = "DetalleValeTEAReport";
			break;
		case Devolucion:
			nombreReporte = "DetalleValeDReport";
			break;
		case Reclamacion:
			nombreReporte = "DetalleValeIRecReport";
			break;			
		case FacturaMN:
			nombreReporte = "DetalleValeFactMNReport";
			break;			
		case FacturaMLC:
			nombreReporte = "DetalleValeFactMLCReport";
			break;			
		case Factura_MN_MLC:	
			nombreReporte = "DetalleValeFact_MN_MLCReport";
			break;
		}
		ModelAndView mv = new ModelAndView(nombreReporte);
		mv.addObject("Logo", getLogo().getFile());
		mv.addObject("dataSource", valelist);
		mv.addObject("ValeProductoDataSource", bsrValeProducto.getResults());
		mv.addObject("Ecie", ecie.getReup() + "-" + ecie.getNombre());
		if (tip.contains("Reclamacion")) {
			mv.addObject("DireccionEcie", ecie.getDireccion());
		}
		if (tip.contains("FacturaMN") || tip.contains("FacturaMLC") || 
				tip.contains("Factura_MN_MLC")) {
			mv.addObject("DireccionEcie", ecie.getDireccion());
			mv.addObject("NIT", ecie.getNit());     
			mv.addObject("CBCUC", ecie.getCuentaCuc() );
			mv.addObject("CBCUP", ecie.getCuentaCup() );
			mv.addObject("ABCUP", ecie.getAgenciaMN() );
			mv.addObject("ABCUC", ecie.getCuentaCuc() );
		}        
		response.setHeader("Content-Disposition",
				"attachment; filename=Detalles_Vale_No_" + vir.getNoVale()
						+ ".xls;");
		return mv;

	}

	public void setListarTransferenciaSalidaView(
			String listarTransferenciaSalidaView) {
		this.listarTransferenciaSalidaView = listarTransferenciaSalidaView;
	}

	public String getListarTransferenciaSalidaView() {
		return listarTransferenciaSalidaView;
	}

	public void setEditarTransferenciaSalidaView(
			String editarTransferenciaSalidaView) {
		this.editarTransferenciaSalidaView = editarTransferenciaSalidaView;
	}

	public String getEditarTransferenciaSalidaView() {
		return editarTransferenciaSalidaView;
	}

	public void setDetallesTransferenciaSalidaView(
			String detallesTransferenciaSalidaView) {
		this.detallesTransferenciaSalidaView = detallesTransferenciaSalidaView;
	}

	public String getDetallesTransferenciaSalidaView() {
		return detallesTransferenciaSalidaView;
	}

	public void setListarTransferenciaEntradaView(
			String listarTransferenciaEntradaView) {
		this.listarTransferenciaEntradaView = listarTransferenciaEntradaView;
	}

	public String getListarTransferenciaEntradaView() {
		return listarTransferenciaEntradaView;
	}

	public void setEditarTransferenciaEntradaView(
			String editarTransferenciaEntradaView) {
		this.editarTransferenciaEntradaView = editarTransferenciaEntradaView;
	}

	public String getEditarTransferenciaEntradaView() {
		return editarTransferenciaEntradaView;
	}

	public void setDetallesTransferenciaEntradaView(
			String detallesTransferenciaEntradaView) {
		this.detallesTransferenciaEntradaView = detallesTransferenciaEntradaView;
	}

	public String getDetallesTransferenciaEntradaView() {
		return detallesTransferenciaEntradaView;
	}

	public void setDetallesRecepcionACiegasView(
			String detallesRecepcionACiegasView) {
		this.detallesRecepcionACiegasView = detallesRecepcionACiegasView;
	}

	public String getDetallesRecepcionACiegasView() {
		return detallesRecepcionACiegasView;
	}

	public void setEcieServicio(IEcieServicio ecieServicio) {
		this.ecieServicio = ecieServicio;
	}

	public IEcieServicio getEcieServicio() {
		return ecieServicio;
	}

	public void setTarjeteroServicio(ITarjeteroServicio tarjeteroServicio) {
		this.tarjeteroServicio = tarjeteroServicio;
	}

	public ITarjeteroServicio getTarjeteroServicio() {
		return tarjeteroServicio;
	}

	public ModelAndView eliminar(HttpServletRequest request,
			HttpServletResponse response, PersistentObject object)
			throws Exception {
		
		Vale vale = (Vale) ((IValeServicio) getServicio()).obtener(object
				.getId());
		Ecie ecie = getEcieServicio().obtenerEcie();
		//Long id = ArcheTypeUtils.getIdParameter(request, "id");
		//Vale vale = getServicio().obtener(id);
		int i = -1;
		switch (vale.getTipoVale()) {
		case RecepcionACiegas:
			ecie.setNoValeRecepcionACiega(ecie.getNoValeRecepcionACiega()+i);
			break;
		case Reclamacion:
			ecie.setNoValeReclamacion(ecie.getNoValeReclamacion()+i);
			break;
		default:ecie.setNoValeOperacional(ecie.getNoValeOperacional()+i);
			break;
		}
		
		BindException errors = new BindException(getNomenclador(), "command");
		try {
			getEcieServicio().actualizar(ecie);
			ValeValidator.resolverConfirmados(vale);
		} catch (DataAccessException e) {
			resolveErrors(errors, e);
			ModelAndView mv = new ModelAndView("common/error");
			mv.addObject("errors", errors.getFieldErrors());
			return mv;
		}
		return super.eliminar(request, response, object);
	}
}
