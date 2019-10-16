package org.archetype.common.domain;

import java.io.Serializable;

public class Rol extends Nomenclador implements Serializable{

	private static final long serialVersionUID = 1L;

	public Rol(String nombre) {
		super(nombre);
	}
	
	public Rol() {}
}
