package main;

import java.util.ArrayList;

import ED.Arbol;

/**
 * Implementacion de los metodos de la clase PuertaSalida.
 * 
 * 
 * <b> Vicente Gonzalez Ortega / Dimas de la Fuente Hernadez </b><br>
 * Nombre del Grupo: TeamRocket Asignatura Desarrollo de Programas<br/>
 * Curso 14/15
 */

public class PuertaSalida {
	public int idPuertaSalida;
	/** Estado de la puerta de salida. Abierta o cerrada. */
	public boolean condicionApertura;
	/** Arbol binario de busqueda para las llaves probadas en la cerradura. */
	public Arbol<Llave> llavesProbadas;
	/** Arbol binario de busqueda para la combinacion correcta de la cerradura */
	public Arbol<Llave> combinacion;
	/** Array para mantener una copia de la combinacion correcta de la cerradura */
	public ArrayList<Llave> arrayLlaves;
	/** Entero quue tiene la altura de la puerta */
	public int alturaPuerta;

	/**
	 * Metodo que inserta el id de la Puerta de salida
	 * 
	 * @param id
	 *            .Id donde se encuentra la Sala de la puerta.
	 */
	public void poneridPuertaSalida(int id) {
		this.idPuertaSalida = id;
	}

	/**
	 * Metodo que pone a la puerta una altura determinada
	 * 
	 * @param alturapuerta
	 *            .Entero con la altura de la puerta
	 */
	public void ponerAlturaPuerta(int alturapuerta) {
		this.alturaPuerta = alturapuerta;

	}

	/**
	 * Constructor por defecto de la clase PuertaSalida.
	 */
	public PuertaSalida() {

		this.condicionApertura = false;
		this.llavesProbadas = new Arbol<Llave>();
		this.combinacion = new Arbol<Llave>();
		this.arrayLlaves = new ArrayList<Llave>();

	}

	/**
	 * Comprueba si las condiciones para abrir la puerta se cumplen y, es ese
	 * caso, abre la puerta.
	 * 
	 * @param llave
	 *            . La llave que se va a probar en la puerta.
	 */

	public void abrir(Llave llave) {

		if (!llavesProbadas.pertenece(llave)) {
			if (combinacion.pertenece(llave)) {

				llavesProbadas.insertar(llave);

				combinacion.borrar(llave);

				if ((combinacion.profundidad() < alturaPuerta && combinacion
						.nodosNoHoja() >= combinacion.nodosHoja())) {
					condicionApertura = true;

				}

				llavesProbadas.insertar(llave);
			}
		}
	}

	/**
	 * Método que cierra y vuelve a configurar la puerta del laberinto.
	 */
	public void cerrar() {
		if (condicionApertura) {
			condicionApertura = false;
		}
		llavesProbadas = new Arbol<Llave>();

		combinacion = new Arbol<Llave>();
		for (int i = 0; i < arrayLlaves.size(); i++) {

			combinacion.insertar(arrayLlaves.get(i));

		}

	}

}
