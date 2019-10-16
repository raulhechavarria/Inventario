package org.inventario.ecie.domain;

import java.io.Serializable;

import org.archetype.common.domain.Nomenclador;

public class Empresa extends Nomenclador implements Serializable{

	private static final long serialVersionUID = 1L;
	private String direccion;
	private Organismo organismo = new Organismo();
	private String reup;
	private String codigoNit;
	private Integer agenciaMn;
	private Integer agenciaMlc;
	private String resolDirectora;
	private String cuentaMlc;
	private String tituloMlc;
	private String cuentaMn;
	private String tituloMn;
	private Long telefono;
	private String correo;
	private String fax;
	
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Organismo getOrganismo() {
		return organismo;
	}
	public void setOrganismo(Organismo organismo) {
		this.organismo = organismo;
	}
	public String getReup() {
		return reup;
	}
	public void setReup(String reup) {
		this.reup = reup;
	}

	
	public Integer getAgenciaMn() {
		return agenciaMn;
	}
	public void setAgenciaMn(Integer agenciaMn) {
		this.agenciaMn = agenciaMn;
	}
	public Integer getAgenciaMlc() {
		return agenciaMlc;
	}
	public void setAgenciaMlc(Integer agenciaMlc) {
		this.agenciaMlc = agenciaMlc;
	}
	public String getResolDirectora() {
		return resolDirectora;
	}
	public void setResolDirectora(String resolDirectora) {
		this.resolDirectora = resolDirectora;
	}
	
	public String getTituloMlc() {
		return tituloMlc;
	}
	public void setTituloMlc(String tituloMlc) {
		this.tituloMlc = tituloMlc;
	}
	
	public String getTituloMn() {
		return tituloMn;
	}
	public void setTituloMn(String tituloMn) {
		this.tituloMn = tituloMn;
	}
	
	
	public Long getTelefono() {
		return telefono;
	}
	public void setTelefono(Long telefono) {
		this.telefono = telefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getCodigoNit() {
		return codigoNit;
	}
	public void setCodigoNit(String codigoNit) {
		this.codigoNit = codigoNit;
	}
	public void setCuentaMlc(String cuentaMlc) {
		this.cuentaMlc = cuentaMlc;
	}
	public String getCuentaMlc() {
		return cuentaMlc;
	}
	public void setCuentaMn(String cuentaMn) {
		this.cuentaMn = cuentaMn;
	}
	public String getCuentaMn() {
		return cuentaMn;
	}	
}
