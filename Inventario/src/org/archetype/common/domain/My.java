package org.archetype.common.domain;

import java.io.Serializable;
import java.util.Date;
import org.archetype.common.domain.Nomenclador;

public class My extends Nomenclador implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nombre;
	private String direccion;
	private Integer reup;
	private Integer nit;
	private Integer agenciaMN;
	private String resolDirectora;
	private Integer cuentaCuc;
	private String tituloCtaCuc;
	private Integer cuentaCup;
	private String tituloCtaCup;
	private Integer agenciaMlc;
	private Integer telefono;

	private String fax;
	private String email;
	private Date fechaOperacion;
	private Long noValeOperacional,noValeRecepcionACiega,noValeReclamacion;

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Integer getReup() {
		return reup;
	}
	public void setReup(Integer reup) {
		this.reup = reup;
	}
	public Integer getNit() {
		return nit;
	}
	public void setNit(Integer nit) {
		this.nit = nit;
	}
	public Integer getAgenciaMN() {
		return agenciaMN;
	}
	public void setAgenciaMN(Integer agenciaMN) {
		this.agenciaMN = agenciaMN;
	}
	public String getResolDirectora() {
		return resolDirectora;
	}
	public void setResolDirectora(String resolDirectora) {
		this.resolDirectora = resolDirectora;
	}
	public Integer getCuentaCuc() {
		return cuentaCuc;
	}
	public void setCuentaCuc(Integer cuentaCuc) {
		this.cuentaCuc = cuentaCuc;
	}
	public String getTituloCtaCuc() {
		return tituloCtaCuc;
	}
	public void setTituloCtaCuc(String tituloCtaCuc) {
		this.tituloCtaCuc = tituloCtaCuc;
	}
	public Integer getCuentaCup() {
		return cuentaCup;
	}
	public void setCuentaCup(Integer cuentaCup) {
		this.cuentaCup = cuentaCup;
	}
	public String getTituloCtaCup() {
		return tituloCtaCup;
	}
	public void setTituloCtaCup(String tituloCtaCup) {
		this.tituloCtaCup = tituloCtaCup;
	}
	public Integer getAgenciaMlc() {
		return agenciaMlc;
	}
	public void setAgenciaMlc(Integer agenciaMlc) {
		this.agenciaMlc = agenciaMlc;
	}
	public Integer getTelefono() {
		return telefono;
	}
	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getFechaOperacion() {
		return fechaOperacion;
	}
	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}
	public Long getNoValeOperacional() {
		return noValeOperacional;
	}
	public void setNoValeOperacional(Long noValeOperacional) {
		this.noValeOperacional = noValeOperacional;
	}
	public Long getNoValeRecepcionACiega() {
		return noValeRecepcionACiega;
	}
	public void setNoValeRecepcionACiega(Long noValeRecepcionACiega) {
		this.noValeRecepcionACiega = noValeRecepcionACiega;
	}
	public Long getNoValeReclamacion() {
		return noValeReclamacion;
	}
	public void setNoValeReclamacion(Long noValeReclamacion) {
		this.noValeReclamacion = noValeReclamacion;
	}
	
}
