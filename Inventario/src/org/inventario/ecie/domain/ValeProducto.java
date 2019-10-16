package org.inventario.ecie.domain;

import java.io.Serializable;

import org.archetype.common.domain.PersistentObject;
import org.inventario.ecie.web.utils.CalculoEco;



public class ValeProducto extends PersistentObject implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private Producto producto = new Producto();
	private Vale vale = new Vale();
	private Double cantProdVale;
	private Double precioMNVale;
	private Double importeMNVale;
	private Double precioMLCVale;
	private Double importeMLCVale;
	private Double recargoDescuentoMN;
	private Double recargoDescuentoMLC;
	private Double totalMN;
	private Double totalMLC;
	private Double existenciaOrigen;
	private Double existenciaDestino;
	
	
	public void CalculoPrecios() throws Exception{
		this.setPrecioMNVale(CalculoEco.calculoPrecio(importeMNVale,cantProdVale));
		this.setPrecioMLCVale(CalculoEco.calculoPrecio(importeMLCVale, cantProdVale));
	}
	
	public void CalculoTotalesConRecargo(){
		this.setTotalMN(this.getImporteMNVale() + this.getRecargoDescuentoMN());
		this.setTotalMLC(this.getImporteMLCVale() + this.getRecargoDescuentoMLC());
	}
	
	public Double calculoRecargoDescuentoMN() throws Exception{
		/*en caso de  que la moneda  no tenga  importe  se recargara   por la otra moneda */
		Double porrateo = new Double(0.00);
		Double reca = new Double(0.00);
		if (this.vale.getImporteMN() == 0) {
			
			porrateo = CalculoEco.porrateo(this.vale.getRecargoDescuentoMN(), this.vale.getImporteMLC());
			reca = (porrateo)*this.getImporteMLCVale();
		} else {
			porrateo = CalculoEco.porrateo(this.vale.getRecargoDescuentoMN(), this.vale.getImporteMN());
			reca = (porrateo)*this.getImporteMNVale();
		}
		this.setRecargoDescuentoMN(reca);
		return reca;
	}
	
	public Double calculoRecargoDescuentoMLC() throws Exception{
		/*en caso de  que la moneda  no tenga  importe  se recargara   por la otra moneda */
		Double porrateo = new Double(0.00);
		Double reca = new Double(0.00);
		if (this.vale.getImporteMLC() == 0) {
			porrateo = CalculoEco.porrateo(this.vale.getRecargoDescuentoMLC(), this.vale.getImporteMN());
			reca = (porrateo)*this.getImporteMNVale();
		} else {
			porrateo = CalculoEco.porrateo(this.vale.getRecargoDescuentoMLC(), this.vale.getImporteMLC());
			reca = (porrateo)*this.getImporteMLCVale();
		}
		this.setRecargoDescuentoMLC(reca);
		return reca;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public void setVale(Vale vale) {
		this.vale = vale;
	}

	public void setCantProdVale(Double cantProdVale) {
		if (cantProdVale != null) {
			this.cantProdVale = CalculoEco.redondeo3(cantProdVale);	
		}
	}

	public void setPrecioMNVale(Double precioMNVale) {
		if (precioMNVale != null) {
			this.precioMNVale = CalculoEco.redondeo5(precioMNVale);	
		}		
	}

	public void setImporteMNVale(Double importeMNVale) {
		if (importeMNVale != null) {
			this.importeMNVale = CalculoEco.redondeo2(importeMNVale);	
		}		
	}

	public void setPrecioMLCVale(Double precioMLCVale) {
		if (precioMLCVale != null) {
			this.precioMLCVale = CalculoEco.redondeo5(precioMLCVale);
		}	
	}

	public void setImporteMLCVale(Double importeMLCVale) {
		if (importeMLCVale != null) {
			this.importeMLCVale = CalculoEco.redondeo2(importeMLCVale);
		}	
	}

	public void setRecargoDescuentoMN(Double recargoDescuentoMN) {
		if (recargoDescuentoMN != null) {
			this.recargoDescuentoMN = CalculoEco.redondeo2(recargoDescuentoMN);	
		}		
	}

	public void setRecargoDescuentoMLC(Double recargoDescuentoMLC) {
		if (recargoDescuentoMLC != null) {
			this.recargoDescuentoMLC = CalculoEco.redondeo2(recargoDescuentoMLC);	
		}	
	}

	public void setTotalMN(Double totalMN) {
		if (totalMN != null) {
			this.totalMN = CalculoEco.redondeo2(totalMN);
		}	
	}

	public void setTotalMLC(Double totalMLC) {
		if (totalMLC != null ) {
			this.totalMLC = CalculoEco.redondeo2(totalMLC);	
		}	
	}

	public void setExistenciaOrigen(Double existenciaOrigen) {
		if (existenciaOrigen != null) {
			this.existenciaOrigen = CalculoEco.redondeo3(existenciaOrigen);	
		}
	}

	public void setExistenciaDestino(Double existenciaDestino) {
		if (existenciaDestino != null) {
			this.existenciaDestino = CalculoEco.redondeo3(existenciaDestino);
		}			
	}

	public Producto getProducto() {
		return producto;
	}

	public Vale getVale() {
		return vale;
	}

	public Double getCantProdVale() {
		return cantProdVale;
	}

	public Double getPrecioMNVale() {
		return precioMNVale;
	}

	public Double getImporteMNVale() {
		return importeMNVale;
	}

	public Double getPrecioMLCVale() {
		return precioMLCVale;
	}

	public Double getImporteMLCVale() {
		return importeMLCVale;
	}

	public Double getRecargoDescuentoMN() {
		return recargoDescuentoMN;
	}

	public Double getRecargoDescuentoMLC() {
		return recargoDescuentoMLC;
	}

	public Double getTotalMN() {
		return totalMN;
	}

	public Double getTotalMLC() {
		return totalMLC;
	}

	public Double getExistenciaOrigen() {
		return existenciaOrigen;
	}

	public Double getExistenciaDestino() {
		return existenciaDestino;
	}

}
