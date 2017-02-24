package main;

import ED.Cola;
import ED.Stack;

/**
 * Implementacion de los metodos de la clase Robot.
 * 
 * 
 * <b> Vicente Gonzalez Ortega / Dimas de la Fuente Hernadez </b><br>
 * Nombre del Grupo: TeamRocket Asignatura Desarrollo de Programas<br/>
 * Curso 14/15
 */

public abstract class Robot implements Comparable<Robot> {
	/** Nombre del robot */
	private String nombre;
	/** Identificador del Robot */
	private String id;
	/** Turno en el que actua cada Robot */
	protected int turnoRobot;
	/** Sala en la que aparece cada Robot */
	private int salaInicio;
	/** Cola con las direcciones que tiene que seguir cada Robot */
	protected Cola<Dir> direccionesRobot;
	/** Cola en el que se encuentran las llaves de cada Robot */
	protected Stack<Llave> Llavero;
	/** Cola auxiliar para las llaves de cada Robot */
	public Stack<Llave> LlaveAux;

	/**
	 * Constructor sin parametros de la Clase robot.
	 */
	public Robot() {
		nombre = null;
		Llavero = new Stack<Llave>();
		direccionesRobot = new Cola<Dir>();
		LlaveAux = new Stack<Llave>();

	}

	/**
	 * Constructor parametrizado de la clase robot
	 * 
	 * @param nombre
	 *            .Nombre que recibe el robot.
	 * @param id
	 *            .Identificador que recibe el robot.
	 * @param turno
	 *            .Turno en el que actua cada Robot.
	 * @param salaInicio
	 *            .Sala en la que aparece cada Robot.
	 */
	public Robot(String nombre, String id, int turno, int salainicio) {
		this.nombre = nombre;
		this.id = id;
		this.turnoRobot = turno;
		this.salaInicio = salainicio;
		Llavero = new Stack<Llave>();
		direccionesRobot = new Cola<Dir>();
	}

	/**
	 * Metodo que devuelve el nombre del robot.
	 * 
	 * @return Devuelve el nombre del robot.
	 */

	public String obtenerNombre() {
		return nombre;
	}

	/** Metodo que pone el nombre a un robot */

	public void ponerNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo que devuelve el identificador del robot.
	 * 
	 * @return Devuelve el identificador del robot.
	 */
	public String obtenerId() {
		return id;
	}

	/**
	 * Metodo que pone el id a un robot
	 * 
	 * @param id
	 *            identificador del robot
	 * */

	public void ponerId(String id) {
		this.id = id;
	}

	/**
	 * Metodo que pone la sala un robot
	 * 
	 * @param salaInicio
	 *            identificador de la sala de inicio del robot
	 * */

	public void ponerSala(int salaInicio) {
		this.salaInicio = salaInicio;
	}

	/**
	 * Metodo que devuelve la sala del robot.
	 * 
	 * @return Devuelve la sala del robot.
	 */
	public int obtenerSala() {

		return salaInicio;

	}

	/**
	 * Metodo que pone el turno a un robot
	 * 
	 * @param turno
	 *            turno de inicio del robot
	 * */
	public void ponerTurno(int turno) {
		this.turnoRobot = turno;
	}

	/**
	 * Metodo que devuelve el turno del robot.
	 * 
	 * @return Devuelve el turno del robot.
	 */
	public int obtenerTurno() {
		return turnoRobot;
	}

	/**
	 * Módulo que gestiona los movimientos de cada robot
	 * 
	 * @param lab
	 *            Laberinto sobre el que se moverá el robot
	 * @param sala
	 *            sala actual en la que se encuentra el robot
	 * @return salaDestino
	 */

	protected Sala movimientoRobot(Laberinto lab, Sala sala) {
		int salaactual;
		int idSala = 0;
		Sala salaDestino = new Sala();
		salaDestino = sala;

		if (!direccionesRobot.estaVacia()) {
			if (direccionesRobot.primero() == Dir.E) {

				idSala = sala.obtenerId() + 1;

			}
			if (direccionesRobot.primero() == Dir.O) {
				idSala = sala.obtenerId() - 1;
			}

			if (direccionesRobot.primero() == Dir.N) {
				idSala = sala.obtenerId() - lab.dimX;

			}
			if (direccionesRobot.primero() == Dir.S) {

				idSala = sala.obtenerId() + lab.dimX;

			}
			for (int i = 0; i < lab.LaberintoMatriz.length; i++) {
				for (int j = 0; j < lab.LaberintoMatriz[i].length; j++) {

					salaactual = lab.LaberintoMatriz[i][j].obtenerId();
					if (idSala == salaactual) {
						lab.LaberintoMatriz[i][j].frecuencia = lab.LaberintoMatriz[i][j].frecuencia + 1;
						lab.LaberintoMatriz[i][j].encolarRobot(this);
						salaDestino = lab.LaberintoMatriz[i][j];

					}
				}
			}
			direccionesRobot.desencolar();
		}

		return salaDestino;
	}

	/**
	 * Introduce la llave en el llavero de los robot.
	 * 
	 * @param llave
	 *            llave que se añadirá al llavero del robot
	 */

	public void a�adirLlave(Llave llave) {
		Llavero.addData(llave);
	}

	/**
	 * Metodo abstracto que traza la ruta especifica para cada Robot
	 * 
	 * @param inicio
	 *            Identificador de la sala de comienzo del robot
	 * 
	 * @param lab
	 *            Laberinto sobre el que se trazará la ruta
	 */
	abstract public void rutaRobot(Integer v, Laberinto lab);

	/**
	 * Metodo que devuelve la llave que se encuentra en la cima del Llavero del
	 * Robot
	 * 
	 * @return llave
	 */
	public Llave sacarLlave() {

		Llave llave = new Llave();
		if (!Llavero.isEmpty()) {
			llave = Llavero.getTop();
			Llavero.removeData();
		}

		return llave;
	}

	/**
	 * Metodo que realiza las acciones correspondientes del Robot(movimiento y
	 * recogida de llaves)
	 * 
	 * @param lab
	 *            Laberinto sobre el cual se realizan las acciones
	 * @param sala
	 *            sala actual que representa la posicion del robot
	 */
	public void accionRobot(Laberinto lab, Sala sala) {
		if (sala.obtenerId() == lab.salaPuerta) {
			if (!Llavero.isEmpty()) {
				lab.PSalida.abrir(sacarLlave());
				if (lab.PSalida.condicionApertura == true) {
					
					if (!sala.colaRobots.estaVacia()) {

						lab.colaRobotsGanadores.encolar(sala.colaRobots
								.primero());

					}
					while (!sala.colaRobotsAUX.estaVacia()) {
						lab.colaRobotsGanadores.encolar(sala.colaRobotsAUX
								.primero());

						sala.colaRobotsAUX.desencolar();
					}
				}

			}
			if (!lab.PSalida.condicionApertura)

				sala.colaRobotsAUX.encolar(this);

		} else {
			if (!direccionesRobot.estaVacia()) {
				Sala salaDestino = new Sala();

				salaDestino = movimientoRobot(lab, sala);

				if (salaDestino.contieneLlaves()) {
					Llave llave = new Llave();
					llave = salaDestino.obtenerLlave();
					Llavero.addData(llave);

				}

			}

		}
		turnoRobot++;
	}

	/**
	 * Comprueba si la sala actual es adyacente con la sala situada al sur
	 * 
	 * @param inicio
	 *            identificador de la sala actual
	 * @param lab
	 *            Laberinto sobre el que se realiza la comprobacion
	 * @return adyacente
	 */
	protected boolean SalaSur(int inicio, Laberinto lab) {
		boolean adyacente = false;
		/** SALA COMPROBAR SUR */
		if (inicio < lab.dimX * lab.dimY - lab.dimX) {
			if (lab.grafoRobots.adyacente(inicio, inicio + lab.dimX)) {
				adyacente = true;
			}
		}
		return adyacente;
	}

	/**
	 * Comprueba si la sala actual es adyacente con la sala situada al oeste
	 * 
	 * @param inicio
	 *            identificador de la sala actual
	 * @param lab
	 *            Laberinto sobre el que se realiza la comprobacion
	 * @return adyacente
	 */
	protected boolean SalaOeste(int inicio, Laberinto lab) {
		boolean entra = false;
		if (inicio % lab.dimX != 0) {
			if (lab.grafoRobots.adyacente(inicio, inicio - 1)) {
				entra = true;

			}
		}
		return entra;
	}

	/**
	 * Comprueba si la sala actual es adyacente con la sala situada al este
	 * 
	 * @param inicio
	 *            identificador de la sala actual
	 * @param lab
	 *            Laberinto sobre el que se realiza la comprobacion
	 * @return adyacente
	 */
	protected boolean SalaEste(int inicio, Laberinto lab) {
		boolean entra = false;
		if (inicio + 1 % lab.dimX != 0) {
			if (lab.grafoRobots.adyacente(inicio, inicio + 1)) {
				entra = true;

			}
		}
		return entra;
	}

	/**
	 * Comprueba si la sala actual es adyacente con la sala situada al norte
	 * 
	 * @param inicio
	 *            identificador de la sala actual
	 * @param lab
	 *            Laberinto sobre el que se realiza la comprobacion
	 * @return adyacente
	 */
	protected boolean SalaNorte(int inicio, Laberinto lab) {
		boolean entra = false;
		if (inicio > lab.dimX - 1) {
			if (lab.grafoRobots.adyacente(inicio, inicio - lab.dimX)) {
				entra = true;

			}
		}
		return entra;
	}

	/**
	 * Comparando dos robots.
	 * 
	 * @param r2
	 *            robot a comparar
	 * @return Devuelve 0 si son iguales, -1 si es mayor y 1 si es menor.
	 */
	public int compareTo(Robot r2) {
		if (this == r2)
			return 0;
		if (this.id == (r2.obtenerId())) {
			return 0;
		} else {
			return 1;
		}
	}

}