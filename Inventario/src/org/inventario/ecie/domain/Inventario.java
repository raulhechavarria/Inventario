package org.inventario.ecie.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import org.archetype.common.domain.PersistentObject;
import org.inventario.ecie.web.utils.CalculoEco;


public class Inventario extends PersistentObject implements Serializable{

	private static final long serialVersionUID = 1L;
	private Double invFinalMN,invFinalMLC;
	private Date fecha;
    private Calendar fechaCompra; 
	private Almacen almacen = new Almacen();

	
	
	public Calendar getFechaCompra() {
		return fechaCompra;
	}
	public void setFechaCompra(Calendar fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
	public Double getInvFinalMN() {
		return invFinalMN;
	}
	public void setInvFinalMN(Double invFinalMN) {
		this.invFinalMN = CalculoEco.redondeo2(invFinalMN) ;
	}
	public Double getInvFinalMLC() {
		return invFinalMLC;
	}
	public void setInvFinalMLC(Double invFinalMLC) {
		this.invFinalMLC = CalculoEco.redondeo2(invFinalMLC) ;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Almacen getAlmacen() {
		return almacen;
	}
	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}
}
