package main;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Implementacion de los metodos de la clase Bender, que hereda de la clase
 * Robot.
 * 
 * 
 * <b> Vicente Gonzalez Ortega / Dimas de la Fuente Hernadez </b><br>
 * Nombre del Grupo: TeamRocket Asignatura Desarrollo de Programas<br/>
 * Curso 14/15 Entrega EC4
 */

public class Bender extends Robot {
	/** Booleano que indica si el robot ha calculado ya su ruta */
	private boolean ruta;

	/**
	 * 
	 * Constructor
	 * 
	 * @param nombre
	 *            . Nombre que recibe el robot
	 * @param id
	 *            . Identificador del robot.
	 * @param turno
	 *            . Entero que muestra cuando actua el robot.
	 * @param salaInicio
	 *            . Sala en la que el robot aparece.
	 */
	public Bender(String nombre, String id, int turno, int salaInicio) {
		super(nombre, id, turno, salaInicio);
		ruta = false;

	}

	/**
	 * Metodo que traza la ruta en profundidad de los robots tipo Bender
	 * 
	 * @param v
	 *            Identificador de la sala de comienzo del robot
	 * 
	 * @param lab
	 *            Laberinto sobre el que se trazará la ruta
	 */
	public void rutaRobot(Integer v, Laberinto lab) {

		ArrayList<Integer> Visitados = new ArrayList<Integer>();
		CaminosLaberintoBENDER(v, Visitados, lab);

	}

	/**
	 * Metodo auxiliar recursivo que realiza un recorrido en profundidad del
	 * Laberinto y añade las direcciones a seguir por el robot
	 * 
	 * @param inicio
	 *            Identificador de la sala actual del robot
	 * @param Visitados
	 *            ArrayList de tipo Integer que almacena los nodos del grafo que
	 *            ya se han visitado
	 * 
	 * @param lab
	 *            Laberinto sobre el que se trazará la ruta
	 */
	private void CaminosLaberintoBENDER(Integer v,
			ArrayList<Integer> Visitados, Laberinto lab) {

		TreeSet<Integer> ady;
		ady = new TreeSet<Integer>();
		Integer w = null;
		int i = 0;
		int siguiente = 0;

		Visitados.add(v);
		if (v == lab.salaPuerta && !ruta) {
			ruta = true;

			while (i < Visitados.size() - 1) {
				int inicio = Visitados.get(i);
				siguiente = Visitados.get(i + 1);

				if (siguiente == inicio + 1) {
					direccionesRobot.encolar(Dir.E);
				}
				if (siguiente == inicio - 1) {
					direccionesRobot.encolar(Dir.O);
				}
				if (siguiente == inicio + lab.dimX) {
					direccionesRobot.encolar(Dir.S);
				}
				if (siguiente == inicio - lab.dimX) {
					direccionesRobot.encolar(Dir.N);
				}

				i++;
			}

		} else {

			lab.grafoRobots.adyacentes(v, ady);
			while (!ady.isEmpty()) {
				w = ady.first();

				ady.remove(w);
				if (!Visitados.contains(w) && !ruta) {
					CaminosLaberintoBENDER(w, Visitados, lab);

				}
			}

		}
		Visitados.remove(Visitados.size() - 1);
	}
}
