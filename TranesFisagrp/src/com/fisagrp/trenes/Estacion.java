package com.fisagrp.trenes;

/***
 * 
 * @author aecarril
 * Este clase representa la estación del tren
 *
 */
public class Estacion {
	protected String nombre;
	protected boolean visitado;

	/***
	 * Constructor
	 * @param nombre
	 * 	nombre de la ciudad
	 */
	public Estacion(String nombre) {
		this.nombre = nombre;
		this.visitado = false;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isVisitado() {
		return visitado;
	}

	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}
}
