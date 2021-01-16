package com.fisagrp.trenes.controller;

import java.util.ArrayList;
import java.util.Hashtable;

import com.fisagrp.trenes.Ruta;
import com.fisagrp.trenes.Estacion;
import com.fisagrp.trenes.exception.RutaInvalidaException;

/***
 * 
 * @author aecarril Clase que permite gestionar las rutas del tren
 *
 */
public class RutaController {

	private Hashtable<Estacion, Ruta> tablaEnrutamiento;

	/***
	 * Constructor por defecto
	 */
	public RutaController() {
		this.tablaEnrutamiento = new Hashtable<Estacion, Ruta>();
	}

	/**
	 * @param tablaEnrutamiento
	 *            the tablaEnrutamiento to set
	 */
	public void setTablaEnrutamiento(Hashtable<Estacion, Ruta> tablaEnrutamiento) {
		this.tablaEnrutamiento = tablaEnrutamiento;
	}

	/***
	 * Get Tabla Enrutamiento
	 * 
	 * @return
	 */
	public Hashtable<Estacion, Ruta> getTablaEnrutamiento() {
		return tablaEnrutamiento;
	}

	/***
	 * Crea un item de la tabla de enrutamiento
	 * 
	 * @param estacion
	 * @param ruta
	 */
	public void crearItemTablaEnrutamiento(Estacion estacion, Ruta ruta) {
		this.tablaEnrutamiento.put(estacion, ruta);
	}

	/***
	 * Calcula la distancia entre las estaciones
	 * 
	 * @param estaciones
	 *            Listado de estaciones
	 * @return el valor de la distancia entre las estaciones
	 * @throws RutaInvalidaException
	 */
	public int calcularDistanciaEntreEstaciones(ArrayList<Estacion> estaciones) throws RutaInvalidaException {

		int distancia = 0, noEstaciones = 0, indice = 0;

		// Valida que al menos existan dos estaciones para realizar el cálculo
		// de la distancia
		if (estaciones.size() < 2) {
			return 0;
		}

		while (indice < estaciones.size() - 1) {

			if (this.tablaEnrutamiento.containsKey(estaciones.get(indice))) {
				Ruta ruta = this.tablaEnrutamiento.get(estaciones.get(indice));

				while (ruta != null) {
					if (ruta.destino.equals(estaciones.get(indice + 1))) {
						distancia += ruta.peso;
						noEstaciones++;
						break;
					}

					ruta = ruta.siguiente;
				}

			} else {

				throw new RutaInvalidaException("No exite esta ruta.");
			}

			indice++;
		}

		if (noEstaciones != estaciones.size() - 1) {
			throw new RutaInvalidaException("No exite esta ruta.");
		}

		return distancia;
	}

	/***
	 * Obtiene el nùmero de paradas entre estaciones
	 * 
	 * @param origen
	 * @param destino
	 * @param limite
	 * @return
	 */
	public int obtenerNoParadasEntreEstaciones(Estacion origen, Estacion destino, int limite) {
		return encontrarRutas(origen, destino, 0, limite);
	}

	/***
	 * Encuantra las rutas
	 * 
	 * @param origen
	 * @param destino
	 * @param noEstaciones
	 * @param limite
	 * @return
	 */
	private int encontrarRutas(Estacion origen, Estacion destino, int noEstaciones, int limite) {

		int rutas = 0;

		if (this.tablaEnrutamiento.containsKey(origen) && this.tablaEnrutamiento.containsKey(destino)) {

			if (noEstaciones == limite) {
				return 0;
			}

			noEstaciones++;
			origen.setVisitado(true);

			Ruta ruta = this.tablaEnrutamiento.get(origen);

			while (ruta != null) {
				
				if (ruta.getDestino().equals(destino)) {
					rutas++;
					
					ruta = ruta.siguiente;
					
					continue;
				} else if (!ruta.getDestino().isVisitado()) {
					noEstaciones--;
					rutas += encontrarRutas(ruta.getDestino(), destino, noEstaciones, limite);
				}

				ruta = ruta.siguiente;
			}
		} else {
			noExisteEstaRuta();
		}

		origen.setVisitado(false);

		return rutas;
	}

	/***
	 * Imprime el mensaje "No existe esta ruta"
	 */
	public void noExisteEstaRuta() {
		System.out.println("No exite esta ruta.");

	}

	/***
	 * Obtiene la ruta más corta entre dos estaciones
	 * 
	 * @param origen
	 * @param destino
	 * @return
	 */
	public int obtenerRutaMasCortaEntreEstaciones(Estacion origen, Estacion destino) {
		return encontrarRutaMasCorta(origen, destino, 0, 0);
	}

	/***
	 * Encuentra la ruta más corta
	 * 
	 * @param origen
	 * @param destino
	 * @param distancia
	 * @param caminoMasCorto
	 * @return
	 */
	private int encontrarRutaMasCorta(Estacion origen, Estacion destino, int distancia, int caminoMasCorto) {
		if (this.tablaEnrutamiento.containsKey(origen) && this.tablaEnrutamiento.containsKey(destino)) {
			origen.setVisitado(true);
			
			Ruta ruta = this.tablaEnrutamiento.get(origen);
			
			while (ruta != null) {
				if (ruta.getDestino() == destino || !ruta.getDestino().isVisitado()) {
					distancia += ruta.getPeso();
				}

				if (ruta.getDestino().equals(destino)) {
					if (caminoMasCorto == 0 || distancia < caminoMasCorto)
						caminoMasCorto = distancia;
					
					origen.setVisitado(false);
					
					return caminoMasCorto;
				}
				/* Some backtracking and recursion */
				else if (!ruta.getDestino().isVisitado()) {
					caminoMasCorto = encontrarRutaMasCorta(ruta.getDestino(), destino, distancia, caminoMasCorto);
					distancia -= ruta.getPeso();
				}
				ruta = ruta.siguiente;
			}
		} else {
			noExisteEstaRuta();
		}

		origen.setVisitado(false);
		
		return caminoMasCorto;
	}
	
	/***
	 * Obtiene el número de rutas internas
	 * @param origen
	 * @param destino
	 * @param distanciaMaxima
	 * @return
	 */
	public int obtenerNumeroRutasInternas(Estacion origen, Estacion destino, int distanciaMaxima) {
        return encontrarTodasLasRutasEntreEstaciones(origen, destino, 0, distanciaMaxima);
    }

	/***
	 * Encuentra todas las rutas entre estaciones
	 * @param origen
	 * @param destino
	 * @param peso
	 * @param distanciaMaxima
	 * @return
	 */
    private int encontrarTodasLasRutasEntreEstaciones(Estacion origen, Estacion destino, int peso, int distanciaMaxima){
        int rutas = 0;
        
        if(this.tablaEnrutamiento.containsKey(origen) && this.tablaEnrutamiento.containsKey(destino)) {

            Ruta ruta = this.tablaEnrutamiento.get(origen);
            
            while(ruta != null) {
                peso += ruta.getPeso();
                
                if(peso <= distanciaMaxima) {
                    if(ruta.getDestino().equals(destino)) {
                        rutas++;
                        
                        rutas += encontrarTodasLasRutasEntreEstaciones(ruta.getDestino(), destino, peso, distanciaMaxima);
                        ruta = ruta.siguiente;
                        continue;
                    }
                    else {
                        rutas += encontrarTodasLasRutasEntreEstaciones(ruta.getDestino(), destino, peso, distanciaMaxima);
                        peso -= ruta.getPeso();
                    }
                }
                else
                	peso -= ruta.getPeso();

                ruta = ruta.siguiente;
            }
        }
        else{
        	noExisteEstaRuta();

          }
        return rutas;

    }
}
