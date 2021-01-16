package com.fisagrp.trenes;

/***
 * 
 * @author aecarril 
 * Clase que guarda la información de la ruta entre el origen y
 * destino
 *
 */
public class Ruta {

	public Estacion origen;
	public Estacion destino;
	public int peso;
	public Ruta siguiente;

	/***
	 * Constructor
	 * @param origen
	 * @param destino
	 */
	public Ruta(Estacion origen, Estacion destino) {
		this(origen, destino, Integer.MAX_VALUE);
	}

	/***
	 * Constructor
	 * @param origen
	 * @param destino
	 * @param peso
	 */
	public Ruta(Estacion origen, Estacion destino, int peso) {
		this.origen = origen;
		this.destino = destino;
		this.peso = peso;
		this.siguiente = null;
	}

	/***
	 * Siguiente ruta
	 * @param ruta
	 * @return
	 */
	public Ruta siguiente(Ruta ruta) {
		this.siguiente = ruta;
		return this;
	}

	public Estacion getOrigen() {
		return origen;
	}

	public void setOrigen(Estacion origen) {
		this.origen = origen;
	}

	public Estacion getDestino() {
		return destino;
	}

	public void setDestino(Estacion destino) {
		this.destino = destino;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public Ruta getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(Ruta siguiente) {
		this.siguiente = siguiente;
	}

}
