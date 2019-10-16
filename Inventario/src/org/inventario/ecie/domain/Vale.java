package org.inventario.ecie.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.archetype.common.domain.PersistentObject;
import org.inventario.ecie.web.utils.CalculoEco;

public class Vale extends PersistentObject implements Serializable {

	
	private static final long serialVersionUID = 1L;
    private String noVale, noControl,noDoc,noOrden,noSolicitud,noTransporte;
    private String comentario;
    private String nombreDoc,nombreTransportador,cITransportador,receptor ;
    private Double totalMN,totalMLC;
    private Double recargoMN,recargoMLC;
    private Double descuentoMN,descuentoMLC;
    private Double recargoDescuentoMN,recargoDescuentoMLC;
	private Double serviciosMN,serviciosMLC;
    private Double importeNeto;
    private Double importeMN,importeMLC;
	private Date fchResponsAutoriza,fchSolicElab,fchRecibido,fechaVale;
    private TipoVale tipoVale;
    private EstadoVale estadoVale;
    private Empresa empresa = new Empresa();  // comprador para factura 
    private Almacen almacenOrigen = new Almacen(); //de aqui se sacaria el que entrega para factura 
    private Almacen almacenDestino = new Almacen();
    private Responsable responsAutoriza = new Responsable();
    private Responsable solicElab = new Responsable();
    private Responsable recibido = new Responsable();
    private CentroCosto centroCosto = new CentroCosto();
    private Set<ValeProducto> valeProducto;
    private OperacionFactura operacionFactura;
    private ConceptoAjuste conceptoAjuste;

	public ConceptoAjuste getConceptoAjuste() {
		return conceptoAjuste;
	}
	public void setConceptoAjuste(ConceptoAjuste conceptoAjuste) {
		this.conceptoAjuste = conceptoAjuste;
	}
	public String getReceptor() {
		return receptor;
	}
	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}
	public OperacionFactura getOperacionFactura() {
		return operacionFactura;
	}
	public void setOperacionFactura(OperacionFactura operacionFactura) {
		this.operacionFactura = operacionFactura;
	}
	public void setFechaVale(Date fechaVale) {
		this.fechaVale = fechaVale;
	}
	public void setNoVale(String noVale) {
		this.noVale = noVale;
	}
	public void setNoControl(String noControl) {
		this.noControl = noControl;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public void setNoDoc(String noDoc) {
		this.noDoc = noDoc;
	}
	public void setNombreDoc(String nombreDoc) {
		this.nombreDoc = nombreDoc;
	}
	public void setNombreTransportador(String nombreTransportador) {
		this.nombreTransportador = nombreTransportador;
	}
	public void setcITransportador(String cITransportador) {
		this.cITransportador = cITransportador;
	}
	public void setNoTransporte(String noTransporte) {
		this.noTransporte = noTransporte;
	}
	public void setTotalMN(Double totalMN) {
		if (totalMN != null) {
			this.totalMN = CalculoEco.redondeo2(totalMN);	
		}	
	}
	public void setTotalMLC(Double totalMLC) {
		if (totalMLC != null) {
			this.totalMLC = CalculoEco.redondeo2(totalMLC);	
		}		
	}
	public void setServiciosMN(Double serviciosMN) {
		if (serviciosMN != null) {
			this.serviciosMN = CalculoEco.redondeo2(serviciosMN);	
		}		
	}
	public void setServiciosMLC(Double serviciosMLC) {
		if (serviciosMLC != null) {
			this.serviciosMLC = CalculoEco.redondeo2(serviciosMLC);	
		}		
	}
	public void setImporteNeto(Double importeNeto) {
		if (importeNeto != null) {
			this.importeNeto = CalculoEco.redondeo2(importeNeto);
		}
	}
	public void setImporteMN(Double importeMN) {
		if (importeMN != null) {
			this.importeMN = CalculoEco.redondeo2(importeMN);
		}		
	}
	public void setImporteMLC(Double importeMLC) {
		if (importeMLC != null) {
			this.importeMLC = CalculoEco.redondeo2(importeMLC);
		}		
	}
	public void setFchResponsAutoriza(Date fchResponsAutoriza) {
		this.fchResponsAutoriza = fchResponsAutoriza;
	}
	public void setFchSolicElab(Date fchSolicElab) {
		this.fchSolicElab = fchSolicElab;
	}
	public void setFchRecibido(Date fchRecibido) {
		this.fchRecibido = fchRecibido;
	}
	public void setTipoVale(TipoVale tipoVale) {
		this.tipoVale = tipoVale;
	}
	public void setEstadoVale(EstadoVale estadoVale) {
		this.estadoVale = estadoVale;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public void setAlmacenOrigen(Almacen almacenOrigen) {
		this.almacenOrigen = almacenOrigen;
	}
	public void setAlmacenDestino(Almacen almacenDestino) {
		this.almacenDestino = almacenDestino;
	}
	public void setResponsAutoriza(Responsable responsAutoriza) {
		this.responsAutoriza = responsAutoriza;
	}
	public void setSolicElab(Responsable solicElab) {
		this.solicElab = solicElab;
	}
	public void setRecibido(Responsable recibido) {
		this.recibido = recibido;
	}
	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}
	public void setValeProducto(Set<ValeProducto> valeProducto) {
		this.valeProducto = valeProducto;
	}
	public Date getFechaVale() {
		return fechaVale;
	}
	public String getNoVale() {
		return noVale;
	}
	public String getNoControl() {
		return noControl;
	}
	public String getComentario() {
		return comentario;
	}
	public String getNoDoc() {
		return noDoc;
	}
	public String getNombreDoc() {
		return nombreDoc;
	}
	public String getNombreTransportador() {
		return nombreTransportador;
	}
	public String getcITransportador() {
		return cITransportador;
	}
	public String getNoTransporte() {
		return noTransporte;
	}
	public Double getTotalMN() {
		return totalMN;
	}
	public Double getTotalMLC() {
		return totalMLC;
	}
	public Double getServiciosMN() {
		return serviciosMN;
	}
	public Double getServiciosMLC() {
		return serviciosMLC;
	}
	public Double getImporteNeto() {
		return importeNeto;
	}
	public Double getImporteMN() {
		return importeMN;
	}
	public Double getImporteMLC() {
		return importeMLC;
	}
	public Date getFchResponsAutoriza() {
		return fchResponsAutoriza;
	}
	public Date getFchSolicElab() {
		return fchSolicElab;
	}
	public Date getFchRecibido() {
		return fchRecibido;
	}
	public TipoVale getTipoVale() {
		return tipoVale;
	}
	public EstadoVale getEstadoVale() {
		return estadoVale;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public Almacen getAlmacenOrigen() {
		return almacenOrigen;
	}
	public Almacen getAlmacenDestino() {
		return almacenDestino;
	}
	public Responsable getResponsAutoriza() {
		return responsAutoriza;
	}
	public Responsable getSolicElab() {
		return solicElab;
	}
	public Responsable getRecibido() {
		return recibido;
	}
	public CentroCosto getCentroCosto() {
		return centroCosto;
	}
	public Set<ValeProducto> getValeProducto() {
		return valeProducto;
	}
	public void setRecargoDescuentoMN(Double recargoDescuentoMN) {
		if (recargoDescuentoMN != null) {
			this.recargoDescuentoMN = CalculoEco.redondeo2(recargoDescuentoMN);
		}
	}
	public Double getRecargoDescuentoMN() {
		return recargoDescuentoMN;
	}
	public void setRecargoDescuentoMLC(Double recargoDescuentoMLC) {
		if (recargoDescuentoMLC != null) {
			this.recargoDescuentoMLC = CalculoEco.redondeo2(recargoDescuentoMLC);
		}	
	}
	public Double getRecargoDescuentoMLC() {
		return recargoDescuentoMLC;
	}
	
	public void setRecargoMN(Double recargoMN) {
		this.recargoMN = recargoMN;
		if (this.recargoDescuentoMN != null) {
			this.recargoDescuentoMN += CalculoEco.redondeo2(recargoMN);
		}else{
			this.recargoDescuentoMN = CalculoEco.redondeo2(recargoMN);
		}
	}
	public void setRecargoMLC(Double recargoMLC) {
		this.recargoMLC = recargoMLC;
		if (this.recargoDescuentoMLC != null) {
			this.recargoDescuentoMLC += CalculoEco.redondeo2(recargoMLC);
		}else{
			this.recargoDescuentoMLC = CalculoEco.redondeo2(recargoMLC);
		}
	}
	public void setDescuentoMN(Double descuentoMN) {
		this.descuentoMN = descuentoMN;
		if (this.recargoDescuentoMN != null) {
			this.recargoDescuentoMN -= CalculoEco.redondeo2(descuentoMN);
		}else{
			if (descuentoMN == 0) {
				this.recargoDescuentoMN = 0.00;
			} else {
				this.recargoDescuentoMN = -CalculoEco.redondeo2(descuentoMN);
			}			
		}
	}
	public void setDescuentoMLC(Double descuentoMLC) {
		this.descuentoMLC = descuentoMLC;
		if (this.recargoDescuentoMLC != null) {
			this.recargoDescuentoMLC -= CalculoEco.redondeo2(descuentoMLC);
		}else{if (descuentoMLC == 0) {
			this.recargoDescuentoMLC = 0.00;
		} else {
			this.recargoDescuentoMLC = -CalculoEco.redondeo2(descuentoMLC);
		}
		}
	}
	public Double getRecargoMN() {
		return recargoMN;
	}
	public Double getRecargoMLC() {
		return recargoMLC;
	}
	public Double getDescuentoMN() {
		return descuentoMN;
	}
	public Double getDescuentoMLC() {
		return descuentoMLC;
	}
	public void setNoOrden(String noOrden) {
		this.noOrden = noOrden;
	}
	public String getNoOrden() {
		return noOrden;
	}
	public String getNoSolicitud() {
		return noSolicitud;
	}
	public void setNoSolicitud(String noSolicitud) {
		this.noSolicitud = noSolicitud;
	}
	
}
