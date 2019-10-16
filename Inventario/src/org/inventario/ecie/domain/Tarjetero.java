package org.inventario.ecie.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import org.archetype.common.domain.PersistentObject;
import org.inventario.ecie.web.utils.CalculoEco;


public class Tarjetero extends PersistentObject implements Serializable{

	private static final long serialVersionUID = 1L;
	private Date fechaCambio;
	private Almacen almacen = new Almacen();
	private Producto producto = new Producto();
	private ValeProducto valeProducto = new ValeProducto();
	private Double cantEntradaVale,cantSalidaVale,cantExist,precioMNExist,precioMLCExist ,impMNExist,impMLCExist;
	private Timestamp version;

	public Tarjetero(Date fechaCambio, Almacen almacen, Producto producto,
			ValeProducto valeProducto, Double cantEntradaVale,
			Double cantSalidaVale, Double cantExist, Double precioMNExist,
			Double precioMLCExist, Double impMNExist, Double impMLCExist) {
		super();
		this.fechaCambio = fechaCambio;
		this.almacen = almacen;
		this.producto = producto;
		this.valeProducto = valeProducto;
		this.cantEntradaVale = cantEntradaVale;
		this.cantSalidaVale = cantSalidaVale;
		this.cantExist = cantExist;
		this.precioMNExist = precioMNExist;
		this.precioMLCExist = precioMLCExist;
		this.impMNExist = impMNExist;
		this.impMLCExist = impMLCExist;
	}
	public Tarjetero() {
		// TODO Auto-generated constructor stub
	}
	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}
	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public void setValeProducto(ValeProducto valeProducto) {
		this.valeProducto = valeProducto;
	}
	public void setCantEntradaVale(Double cantEntradaVale) {
		if (cantEntradaVale != null) {
			this.cantEntradaVale = CalculoEco.redondeo3(cantEntradaVale);	
		}		
	}
	public void setCantSalidaVale(Double cantSalidaVale) {
		if (cantSalidaVale != null) {
			this.cantSalidaVale = CalculoEco.redondeo3(cantSalidaVale);
		}
	}
	public void setCantExist(Double cantExist) {
		if (cantExist != null ) {
			this.cantExist = CalculoEco.redondeo3(cantExist);
		}
	}
	public void setPrecioMNExist(Double precioMNExist) {
		if (precioMNExist != null) {
			this.precioMNExist = CalculoEco.redondeo5(precioMNExist);	
		}		
	}
	public void setPrecioMLCExist(Double precioMLCExist) {
		if (precioMLCExist != null) {
			this.precioMLCExist = CalculoEco.redondeo5(precioMLCExist);
		}		
	}
	public void setImpMNExist(Double impMNExist) {
		if (impMNExist != null) {
			this.impMNExist = CalculoEco.redondeo2(impMNExist);
		}		
	}
	public void setImpMLCExist(Double impMLCExist) {
		if (impMLCExist != null) {
			this.impMLCExist = CalculoEco.redondeo2(impMLCExist);
		} 		
	}

	public Date getFechaCambio() {
		return fechaCambio;
	}
	public Almacen getAlmacen() {
		return almacen;
	}
	public Producto getProducto() {
		return producto;
	}
	public ValeProducto getValeProducto() {
		return valeProducto;
	}
	public Double getCantEntradaVale() {
		return cantEntradaVale;
	}
	public Double getCantSalidaVale() {
		return cantSalidaVale;
	}
	public Double getCantExist() {
		return cantExist;
	}
	public Double getPrecioMNExist() {
		return precioMNExist;
	}
	public Double getPrecioMLCExist() {
		return precioMLCExist;
	}
	public Double getImpMNExist() {
		return impMNExist;
	}
	public Double getImpMLCExist() {
		return impMLCExist;
	}
	public Timestamp getVersion() {
		return version;
	}
	public void setVersion(Timestamp version) {
		this.version = version;
	}    
}
