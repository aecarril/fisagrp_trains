package com.fisagrp.trenes;

import java.util.ArrayList;

import com.fisagrp.trenes.controller.RutaController;
import com.fisagrp.trenes.exception.RutaInvalidaException;

public class Main {

	public static void main(String[] args) throws RutaInvalidaException {
		RutaController rutaController = new RutaController();

		// Crea las 5 estaciones del tren
		Estacion a = new Estacion("A");
		Estacion b = new Estacion("B");
		Estacion c = new Estacion("C");
		Estacion d = new Estacion("D");
		Estacion e = new Estacion("E");

		//Se crea la tabla de rutas
		rutaController.crearItemTablaEnrutamiento(a,
				new Ruta(a, b, 5).siguiente(new Ruta(a, d, 5).siguiente(new Ruta(a, e, 7))));
		rutaController.crearItemTablaEnrutamiento(b, new Ruta(b, c, 4));
		rutaController.crearItemTablaEnrutamiento(c, new Ruta(c, d, 8).siguiente(new Ruta(c, e, 2)));
		rutaController.crearItemTablaEnrutamiento(d, new Ruta(d, c, 8).siguiente(new Ruta(d, e, 6)));
		rutaController.crearItemTablaEnrutamiento(e, new Ruta(e, b, 3));

		// 1. Distancia A-B-C
		ArrayList<Estacion> rutas1 = new ArrayList<Estacion>();
		rutas1.add(a);
		rutas1.add(b);
		rutas1.add(c);

		// 2. Distancia A-D
		ArrayList<Estacion> rutas2 = new ArrayList<Estacion>();
		rutas2.add(a);
		rutas2.add(d);
		System.out.println("Output#2  " + rutaController.calcularDistanciaEntreEstaciones(rutas2));

		// 3. Distancia A-D-C
		ArrayList<Estacion> rutas3 = new ArrayList<Estacion>();
		rutas3.add(a);
		rutas3.add(d);
		rutas3.add(c);
		System.out.println("Output#3  " + rutaController.calcularDistanciaEntreEstaciones(rutas3));

		// 4. Distancia A-E-B-C-D
		ArrayList<Estacion> rutas4 = new ArrayList<Estacion>();
		rutas4.add(a);
		rutas4.add(e);
		rutas4.add(b);
		rutas4.add(c);
		rutas4.add(d);

		System.out.println("Output#4  " + rutaController.calcularDistanciaEntreEstaciones(rutas4));

		// 5. Distancia A-E-D
		try {

			ArrayList<Estacion> rutas5 = new ArrayList<Estacion>();
			rutas5.add(a);
			rutas5.add(e);
			rutas5.add(d);
			System.out.println("Output#5  " + rutaController.calcularDistanciaEntreEstaciones(rutas5));

		} catch (RutaInvalidaException riEx) {
			System.out.println("Output#5  " + riEx.getMessage());
		}

		// 6. Numero de viajes comenzando en C
		System.out.println("Output#6  " + rutaController.obtenerNoParadasEntreEstaciones(c, c, 3));

		// 7. Numero de viajes comenzando en A
		System.out.println("Output#7  " + rutaController.obtenerNoParadasEntreEstaciones(a, c, 4));

		// 8. Longitud de la ruta más corta de A a C.
		System.out.println("Output#8  " + rutaController.obtenerRutaMasCortaEntreEstaciones(a, c));

		// 9. Longitud de la ruta más corta de B a B.

		System.out.println("Output#9  " + rutaController.obtenerRutaMasCortaEntreEstaciones(b, b));
		
		//10. Número de diferentes rutas de C a C con una distancia menor a 30
        System.out.println("Output#10  " + rutaController.obtenerNumeroRutasInternas(c,c,30));
	}

	
}
