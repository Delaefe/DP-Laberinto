package main;

import ED.Cola;
import ED.List;

/**
 * Implementacion de los metodos de la clase Sala.
 * 
 * 
 * <b> Vicente Gonzalez Ortega / Dimas de la Fuente Hernadez </b><br>
 * Nombre del Grupo: TeamRocket Asignatura Desarrollo de Programas<br/>
 * Curso 14/15 Entrega EC4
 */

public class Sala implements Comparable<Sala> {

	/** Identificador de la sala */
	private int idSala;
	/** Entero que indica la frecuencia de paso de la sala */
	protected int frecuencia;
	/** Marca de la sala */
	protected int marca;
	/** Estructura de datos Cola en la que se almacenan los robots de cada Sala */
	protected Cola<Robot> colaRobots;
	/**
	 * Estructura de datos Cola auxiliar para el almacenamiento de los robots de
	 * cada Sala
	 */
	protected Cola<Robot> colaRobotsAUX;
	/** Lista de llaves que tiene cada sala */
	public List<Llave> llavesSala;

	/** Constructor sin parametros de Sala. */
	public Sala() {
		colaRobots = new Cola<Robot>();
		colaRobotsAUX = new Cola<Robot>();
		llavesSala = new List<Llave>();

	}

	/**
	 * Metodo que pone la marca a la Sala
	 * 
	 * @param marca
	 *            Entero que designa la marca
	 */

	public void ponerMarca(int marca) {
		this.marca = marca;
	}

	/**
	 * Obtiene la marca de la sala.
	 * 
	 * @return Devuele la marca que tiene la sala.
	 */
	public int obtenerMarca() {
		return this.marca;

	}

	/**
	 * Metodo que devuelve el identificador de una sala
	 * 
	 * @return identificador de la sala.
	 */

	public int obtenerId() {
		return idSala;
	}

	/**
	 * Metodo que pone el identificador a una sala
	 * 
	 * @param id
	 *            Entero que designa el identificador de la sala
	 */

	public void ponerId(int id) {
		this.idSala = id;
	}

	/**
	 * Comprueba si en la sala hay robots.
	 * 
	 * @return True, si la sala esta ocupada por robots, false si no
	 */
	public boolean salaOcupada() {
		boolean ocupada = false;

		if (!colaRobots.estaVacia())
			ocupada = true;

		return ocupada;

	}

	/**
	 * Comrpueba si la sala tiene llaves
	 * 
	 * @return True, si la sala contiene llaves, False si no
	 */
	public boolean contieneLlaves() {

		boolean hayLlaves = false;

		if (!llavesSala.estaVacia())
			hayLlaves = true;

		return hayLlaves;

	}

	/**
	 * Inserta una llave en la lista de llaves
	 * 
	 * @param llave
	 *            .Llave que inserta en la sala.
	 */
	public void insertarLlave(Llave llave) {

		llavesSala.sortedAdd(llave);

	}

	/**
	 * Introduce el robot en la cola de Robots de la sala.
	 * 
	 * @param robot
	 *            .Robot que entra en la sala.
	 */

	public void encolarRobot(Robot robot) {
		colaRobots.encolar(robot);
	}

	/**
	 * Quita el robot de la cola de Robots de la sala.
	 */

	public void desencolarRobot() {
		colaRobots.desencolar();
	}

	/**
	 * Obtiene una llave de la sala.
	 * 
	 * @return .La primera llave de la sala.
	 */
	public Llave obtenerLlave() {

		Llave llave = new Llave();
		if (!llavesSala.estaVacia()) {
			llave = llavesSala.getFirst();
			llavesSala.removeFirst();
		}

		return llave;

	}

	/**
	 * Obtiene el primer robot de la sala.
	 * 
	 * @return .Devuele el primer robot que esta en la sala.
	 */

	public Robot obtenerRobot() {
		return colaRobots.primero();
	}

	/**
	 * Metodo que devuelve, si existe, la sala situada al norte de la actual.
	 * 
	 * @param lab
	 *            laberinto sobre el que se realiza la comprobacion
	 * @return idSalaNorte .-1 si no existe. Si existe devuelve el identificador
	 *         de la sala.
	 */
	public int SalaNorte(Laberinto lab) {
		int idSalaNorte = -1;
		if (this.idSala > lab.dimX - 1)
			idSalaNorte = this.idSala - lab.dimX;
		return idSalaNorte;

	}
	/**
	 * Metodo que devuelve, si existe, la sala situada al sur de la actual.
	 * 
	 * @param lab
	 *            laberinto sobre el que se realiza la comprobacion
	 * @return idSalaSur .-1 si no existe. Si existe devuelve el identificador
	 *         de la sala.
	 */
	public int SalaSur(Laberinto lab) {
		int idSalaSur = -1;
		if (this.idSala < (lab.dimX * lab.dimY) - lab.dimX)
			idSalaSur = this.idSala + lab.dimX;
		return idSalaSur;

	}
	/**
	 * Metodo que devuelve, si existe, la sala situada al este de la actual.
	 * 
	 * @param lab
	 *            laberinto sobre el que se realiza la comprobacion
	 * @return idSalaEste .-1 si no existe. Si existe devuelve el identificador
	 *         de la sala.
	 */
	public int SalaEste(Laberinto lab) {
		int idSalaEste = -1;
		if ((this.idSala + 1) % lab.dimX != 0)
			idSalaEste = this.idSala + 1;
		return idSalaEste;

	}
	/**
	 * Metodo que devuelve, si existe, la sala situada al oeste de la actual.
	 * 
	 * @param lab
	 *            laberinto sobre el que se realiza la comprobacion
	 * @return idSalaOeste .-1 si no existe. Si existe devuelve el identificador
	 *         de la sala.
	 */
	public int SalaOeste(Laberinto lab) {
		int idSalaOeste = -1;
		if (this.idSala % lab.dimX != 0)
			idSalaOeste = this.idSala - 1;
		return idSalaOeste;

	}

	/**
	 * Metodo que realiza la simulacion de cada Sala. Recorre la cola de Robots y realiza sus acciones.
	 * 
	 * @param lab
	 *            . Laberinto en el que transcurren las acciones de los robots.
	 * 
	 */
	public void simular(Laberinto lab) {

		while (!colaRobots.estaVacia()) {
			if (colaRobots.primero().turnoRobot == lab.turno) {
				obtenerRobot().accionRobot(lab, this);
			} else
				colaRobotsAUX.encolar(colaRobots.primero());

			if (!colaRobots.estaVacia())
				colaRobots.desencolar();

		}
		while (!colaRobotsAUX.estaVacia()) {
			colaRobots.encolar(colaRobotsAUX.primero());

			colaRobotsAUX.desencolar();
		}

	}
	/**
	 * Comparando dos salas.
	 * 
	 * @param sala
	 *            La sala con la que comparar
	 * @return Devuelve 0 si son iguales, -1 si es mayor y 1 si es menor.
	 */
	public int compareTo(Sala sala) {
		int resultado = 0;
		if (this.idSala < sala.idSala)
			resultado = -1;
		else {
			if (this.idSala > sala.idSala)
				resultado = 1;
		}
		return resultado;
	}

	@Override
	public String toString() {
		return idSala + ", ";
	}

}
