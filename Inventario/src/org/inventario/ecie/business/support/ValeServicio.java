package org.inventario.ecie.business.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.archetype.common.business.support.CommonService;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.hibernate.Hibernate;
import org.hibernate.exception.ConstraintViolationException;
import org.inventario.ecie.business.IEcieServicio;
import org.inventario.ecie.business.IValeProductoServicio;
import org.inventario.ecie.business.IValeServicio;
import org.inventario.ecie.dao.IValeDao;
import org.inventario.ecie.domain.ClasificadorProducto;
import org.inventario.ecie.domain.Ecie;
import org.inventario.ecie.domain.EstadoVale;
import org.inventario.ecie.domain.Tarjetero;
import org.inventario.ecie.domain.TipoVale;
import org.inventario.ecie.domain.ValeProducto;
import org.inventario.ecie.domain.Vale;
import org.inventario.ecie.web.utils.CalculoEco;
import org.springframework.dao.DataIntegrityViolationException;

public class ValeServicio extends CommonService implements IValeServicio {

	private IEcieServicio ecieServicio;
	private IValeProductoServicio valeProductoServicio;

	public BaseSearchResult listarVale(Date fecha) {
		return ((IValeDao) getDao()).listarVale(fecha);
	}

	public BaseSearchResult listarValeMesAnno(Date fecha) {
		return ((IValeDao) getDao()).listarValeMesAnno(fecha);
	}

	public BaseSearchResult listarValeProducto(Serializable idvale, String tip) {
		return getValeProductoServicio().listarValeProducto(idvale);
	}

	public Object obtener(Serializable id) {
		Vale vale = (Vale) super.obtener(id);
		Hibernate.initialize(vale.getAlmacenDestino());
		Hibernate.initialize(vale.getAlmacenOrigen());
		Hibernate.initialize(vale.getCentroCosto());
		Hibernate.initialize(vale.getEmpresa());
		Hibernate.initialize(vale.getResponsAutoriza());
		Hibernate.initialize(vale.getRecibido());
		Hibernate.initialize(vale.getSolicElab());
		Hibernate.initialize(vale.getValeProducto());
		return vale;
	}

	public BaseSearchResult listarFecha(String tip) {
		return ((IValeDao) getDao()).listarFecha(tip);
	}

	public BaseSearchResult listarSolicitudesConfirmados() {
		Ecie ecie = getEcieServicio().obtenerEcie();
		return ((IValeDao) getDao()).listarSolicitudesConfirmados(ecie
				.getFechaOperacion());
	}
	
	public Vale ValeSinConfirmar(){
		return ((IValeDao) getDao()).ValeSinConfirmar();
	}


	public BaseSearchResult listarValeDadoTipoVale(String tipo, String fechaIni, String fechaFin,ExtGridRequest egr) {
		Date dateIni = null;
		Date dateFin = null;
		if (fechaIni == null) {
			Ecie ecie = getEcieServicio().obtenerEcie();
			dateIni = ecie.getFechaOperacion();
		} else {
			dateIni = java.sql.Date.valueOf(fechaIni);
		}
		if ((fechaFin == null)|| (fechaFin.contentEquals(""))) {
			return ((IValeDao) getDao()).listarValeDadoTipoVale(tipo, dateIni,egr);
		} else {
			dateFin = java.sql.Date.valueOf(fechaFin);
			return ((IValeDao) getDao()).listarEntreFecha(tipo, dateIni,dateFin, egr);

		} 
		
	}
	public BaseSearchResult listarSoliAnno(ExtGridRequest egr,String subCodigo) {
		Date date = null;
			Ecie ecie = getEcieServicio().obtenerEcie();
			date = ecie.getFechaOperacion();
			BaseSearchResult resultBD = ((IValeDao) getDao()).listarSoliAnno(egr,subCodigo); 
			Collection<Object> lista = resultBD.getResults();
			Collection<Vale> listResult = new ArrayList<Vale>();
			for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
				Vale vale = (Vale) iterator.next();
				if ((vale.getFechaVale().getYear() == ecie.getFechaOperacion().getYear())&&
					(vale.getTipoVale().equals(TipoVale.SolicitudEntrega))) {
					listResult.add(vale);
				}
			}
			BaseSearchResult bsrResult = new BaseSearchResult();
			bsrResult.setResults(listResult);
			bsrResult.setTotalCount(new java.lang.Long(new Integer(listResult.size()).toString()));
			return bsrResult;
	}
	
	
	@SuppressWarnings("unchecked")
	public void adicionarValeProductoRAC(Vale vale) {
		for (Iterator iterator = vale.getValeProducto().iterator(); iterator.hasNext();) {
			ValeProducto valeProducto = (ValeProducto) iterator.next();
			ValeProducto vp = getValeProductoServicio().asignarValores(valeProducto);
			vp.getVale().setId(vale.getId());
			getDao().adicionar_actualizar(vp);
		}
	}

	public void adicionar_ActualizarVPDevolucion(Vale vale) {
		if (vale.getId() != null) {
			getValeProductoServicio().eliminarVPDadoVale(vale);
		} 
			adicionarValeProductoRAC(vale);
		
	}

	private void llenarDatos(Vale vale) {
		if ((CalculoEco.SiEntrada(vale))) {
			switch (vale.getTipoVale()) {
			case RecepcionACiegas:
				CalculoEco.llenarValeConCero(vale);
				break;
			case Devolucion:
				BaseSearchResult bsr = ((IValeDao) getDao()).listarValeDadoTipoVale("Devolucion");
				Collection<Object> lista = bsr.getResults();
				for (Iterator<Object> iterator = lista.iterator(); iterator
						.hasNext();) {
					Vale item = ((Vale) iterator.next());
					if (item.getNoDoc().contentEquals(vale.getNoDoc())) {
						if (vale.getId() != null) {													
						if (!item.getId().equals(vale.getId())) {
							ConstraintViolationException ex = new ConstraintViolationException(
									"error", null, "ValeDevolucConcatenado");
							DataIntegrityViolationException exception = new DataIntegrityViolationException(
									"error", ex);
							throw exception;
						}}
						
					}
				}
				Vale valereferencia = (Vale) ((IValeDao) getDao())
						.obtenerSolicitudDadoNoControl(vale.getNoDoc());
				if (valereferencia.getEstadoVale() != EstadoVale.Confirmado) {
					ConstraintViolationException ex = new ConstraintViolationException(
							"error", null, "ValeReferenNoConfirm");
					DataIntegrityViolationException exception = new DataIntegrityViolationException(
							"error", ex);
					throw exception;
				}
				vale.setAlmacenDestino(valereferencia.getAlmacenOrigen());
				vale.setImporteMLC(valereferencia.getImporteMLC());
				vale.setImporteMN(valereferencia.getImporteMN());
				vale.setImporteNeto(valereferencia.getImporteNeto());
				vale.setTotalMLC(valereferencia.getTotalMLC());
				vale.setTotalMN(valereferencia.getTotalMN());
				Set<ValeProducto> valprods = new HashSet<ValeProducto>();
				valprods.addAll(valereferencia.getValeProducto());
				vale.setValeProducto(valprods);
				vale.setCentroCosto(valereferencia.getCentroCosto());
				break;
			case Recepcion:
				vale.setImporteMLC(vale.getTotalMLC()- vale.getRecargoDescuentoMLC());
				if (vale.getImporteMLC() < 0) {
					vale.setTotalMLC(vale.getRecargoDescuentoMLC());
					vale.setImporteMLC(new Double(0.00));
				}
				vale.setImporteMN(vale.getTotalMN()- vale.getRecargoDescuentoMN());
				if (vale.getImporteMN() < 0) {
					vale.setTotalMN(vale.getRecargoDescuentoMN());
					vale.setImporteMN(new Double(0.00));
				}
				vale.setImporteNeto(vale.getTotalMLC() + vale.getTotalMN());
				break;
			default:
				vale.setImporteMLC(vale.getTotalMLC());
				vale.setImporteMN(vale.getTotalMN());
				vale.setImporteNeto(vale.getTotalMLC() + vale.getTotalMN());
				break;
			}
		}
	}

	private void llenarDatosECIE(Vale vale) {
		Ecie ecie = getEcieServicio().obtenerEcie();
		if (vale.getId() == null) {
			String fecha = java.lang.String.valueOf(ecie.getFechaOperacion());
			String[] fechaValues = fecha.split("-");
			switch (vale.getTipoVale()) {
			case Reclamacion:
				vale.setNoVale(fechaValues[0].substring(1) + "-"
						+ ecie.getNoValeReclamacion());
				ecie.setNoValeReclamacion(ecie.getNoValeReclamacion() + 1);
				break;
			case RecepcionACiegas:
				vale.setNoVale(fechaValues[0].substring(2) + "-"
						+ ecie.getNoValeRecepcionACiega());
				ecie
						.setNoValeRecepcionACiega(ecie
								.getNoValeRecepcionACiega() + 1);
				break;
			default:
				vale.setNoVale(fechaValues[0] + "-"
						+ ecie.getNoValeOperacional());
				ecie.setNoValeOperacional(ecie.getNoValeOperacional() + 1);
				break;
			}
		}
		vale.setFechaVale(ecie.getFechaOperacion());
		getDao().actualizar(ecie);
	}

	public void adicionar(Vale vale) {
		llenarDatosECIE(vale);
		llenarDatos(vale);
		super.adicionar(vale);
		if ((vale.getValeProducto() != null)&& (vale.getTipoVale().equals(TipoVale.Devolucion))) {
			adicionar_ActualizarVPDevolucion(vale);
		}
		if ((vale.getValeProducto() != null)&& (vale.getTipoVale().equals(TipoVale.Recepcion))) {
			adicionarValeProductoRAC(vale);
		}
		if ((vale.getValeProducto() != null)&& (vale.getTipoVale().equals(TipoVale.Reclamacion))) {
			adicionarValeProductoRAC(vale);
		}
		if (vale.getTipoVale().equals(TipoVale.Reclamacion)) {
			vale.setImporteNeto(vale.getTotalMLC() + vale.getTotalMN());
			vale.setImporteMLC(vale.getTotalMLC());
			vale.setImporteMN(vale.getTotalMN());
		}
		
	}

	public void actualizar(Vale vale) {
		llenarDatosECIE(vale);
		llenarDatos(vale);
		if (vale.getTipoVale().equals(TipoVale.Reclamacion)) {
			vale.setImporteNeto(vale.getTotalMLC() + vale.getTotalMN());
			vale.setImporteMLC(vale.getTotalMLC());
			vale.setImporteMN(vale.getTotalMN());
		}
		vale = ActualizarDatosVista(vale);
		super.actualizar(vale);
	}


	
	
	public void pasarARecepcion(Serializable id) {
		Vale vale = (Vale) getDao().obtener(Vale.class, id);
		if ((vale.getTipoVale() == TipoVale.RecepcionACiegas)
				&& (vale.getEstadoVale() == EstadoVale.Confirmado)) {
			vale.setTipoVale(TipoVale.Recepcion);
			getDao().actualizar(vale);
		}
	}

	public void confirmar(Serializable id) throws Exception {
		Vale vale = (Vale) getDao().obtener(Vale.class, id);
		if (vale.getEstadoVale() == EstadoVale.Confirmado) {
			ConstraintViolationException ex = new ConstraintViolationException(
					"error", null, "ValeyaConfirm");
			DataIntegrityViolationException exception = new DataIntegrityViolationException(
					"error", ex);
			throw exception;
		}
		vale.setEstadoVale(EstadoVale.Confirmado);
		getDao().actualizar(vale);
	}

	@SuppressWarnings("unchecked")
	public Double comprobarImporteNeto(Vale vale) {
		Double Total = new Double(0.00);
		for (Iterator iterator = vale.getValeProducto().iterator(); iterator
				.hasNext();) {
			ValeProducto vp = (ValeProducto) iterator.next();
			Total += vp.getTotalMLC()
					+ vp.getTotalMN();

		}
		return CalculoEco.redondeo2(Total);// confirma aqui si hay que redondear aqui
	}

	public Vale obtenerUltimo() {
		return (Vale) ((IValeDao) getDao()).ObtUltimoVale();
	}
	
	public Vale obtenerNoDoc(String noDoc){
		return (Vale) ((IValeDao) getDao()).ObtenerNoDoc(noDoc);
	}

	public ClasificadorProducto obtenerFamilia(
			Serializable idClasificadorProducto) {
		return (ClasificadorProducto) getDao().obtener(
				ClasificadorProducto.class, idClasificadorProducto);
	}

	public String imprimir(Serializable id) {
		Ecie e = (Ecie) getDao().obtener(Ecie.class, id);
		return e.getReup() + "-" + e.getNombre();
	}

	public void setEcieServicio(IEcieServicio ecieServicio) {
		this.ecieServicio = ecieServicio;
	}

	public IEcieServicio getEcieServicio() {
		return ecieServicio;
	}

	public void setValeProductoServicio(
			IValeProductoServicio valeProductoServicio) {
		this.valeProductoServicio = valeProductoServicio;
	}

	public IValeProductoServicio getValeProductoServicio() {
		return valeProductoServicio;
	}
	
	public Vale ActualizarDatosVista(Vale vale) {
		Vale valeBD = (Vale) getDao().obtener(Vale.class, vale.getId());
		if (vale.getAlmacenDestino() == null) {
			vale.setAlmacenDestino(valeBD.getAlmacenDestino());
		}
		if (vale.getAlmacenOrigen() == null) {
			vale.setAlmacenOrigen(valeBD.getAlmacenOrigen());
		}
		if (vale.getCentroCosto() == null) {
			vale.setCentroCosto(valeBD.getCentroCosto());
		}
		if (vale.getcITransportador() == null) {
			vale.setcITransportador(valeBD.getcITransportador());
		}
		if (vale.getComentario() == null) {
			vale.setComentario(valeBD.getComentario());
		}
		if (vale.getEmpresa() == null) {
			vale.setEmpresa(valeBD.getEmpresa());
		}
		if (vale.getEstadoVale() == null) {
			vale.setEstadoVale(valeBD.getEstadoVale());
		}
		if (vale.getFchRecibido() == null) {
			vale.setFchRecibido(valeBD.getFchRecibido());
		}
		if (vale.getFchResponsAutoriza() == null) {
			vale.setFchResponsAutoriza(valeBD.getFchResponsAutoriza());
		}
		if (vale.getFchSolicElab() == null) {
			vale.setFchSolicElab(valeBD.getFchSolicElab());
		}
		if (vale.getFechaVale() == null) {
			vale.setFechaVale(valeBD.getFechaVale());
		}
		if (vale.getImporteMLC() == null) {
			vale.setImporteMLC(valeBD.getImporteMLC());
		}
		if (vale.getImporteMN() == null) {
			vale.setImporteMN(valeBD.getImporteMN());
		}
		if (vale.getImporteNeto() == null) {
			vale.setImporteNeto(valeBD.getImporteNeto());
		}
		if (vale.getNoControl() == null) {
			vale.setNoControl(valeBD.getNoControl());
		}
		if (vale.getNoDoc() == null) {
			vale.setNoDoc(valeBD.getNoDoc());
		}
		if (vale.getNombreDoc() == null) {
			vale.setNombreDoc(valeBD.getNombreDoc());
		}
		if (vale.getNombreTransportador() == null) {
			vale.setNombreTransportador(valeBD.getNombreTransportador());
		}
		if (vale.getNoOrden() == null) {
			vale.setNoOrden(valeBD.getNoOrden());
		}
		if (vale.getNoSolicitud() == null) {
			vale.setNoSolicitud(valeBD.getNoSolicitud());
		}
		if (vale.getNoTransporte() == null) {
			vale.setNoTransporte(valeBD.getNoTransporte());
		}
		if (vale.getNoVale() == null) {
			vale.setNoVale(valeBD.getNoVale());
		}
		if (vale.getOperacionFactura() == null) {
			vale.setOperacionFactura(valeBD.getOperacionFactura());
		}
		if (vale.getRecargoDescuentoMLC() == null) {
			vale.setRecargoDescuentoMLC(valeBD.getRecargoDescuentoMLC());
		}
		if (vale.getRecargoDescuentoMN() == null) {
			vale.setRecargoDescuentoMN(valeBD.getRecargoDescuentoMN());
		}
		if (vale.getReceptor() == null) {
			vale.setReceptor(valeBD.getReceptor());
		}
		if (vale.getRecibido() == null) {
			vale.setRecibido(valeBD.getRecibido());
		}
		if (vale.getResponsAutoriza() == null) {
			vale.setResponsAutoriza(valeBD.getResponsAutoriza());
		}
		if (vale.getSolicElab() == null) {
			vale.setSolicElab(valeBD.getSolicElab());
		}
		if (vale.getTipoVale() == null) {
			vale.setTipoVale(valeBD.getTipoVale());
		}
		if (vale.getTotalMLC() == null) {
			vale.setTotalMLC(valeBD.getTotalMLC());
		}
		if (vale.getTotalMN() == null) {
			vale.setTotalMN(valeBD.getTotalMN());
		}	
		return vale;
	}
}
