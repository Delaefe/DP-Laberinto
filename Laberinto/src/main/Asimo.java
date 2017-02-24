package main;

/**
 * Implementacion de los metodos de la clase Asimo, que hereda de la clase
 * Robot.
 * 
 * 
 * <b> Vicente Gonzalez Ortega / Dimas de la Fuente Hernadez </b><br>
 * Nombre del Grupo: TeamRocket Asignatura: Desarrollo de Programas<br/>
 * Curso 14/15 Entrega EC4
 */
public class Asimo extends Robot {

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
	public Asimo(String nombre, String id, int turno, int salaInicio) {
		super(nombre, id, turno, salaInicio);

	}

	/**
	 * Configura las llaves apilándolas en el llavero.
	 * 
	 * @param arrayLlaves
	 *            . Array que contiene las llaves.
	 */
	public void configurarAsimo(int[] arrayLlaves) {
		int i;

		for (i = 0; i < arrayLlaves.length; i++) {
			Llave llave = new Llave(arrayLlaves[i]);
			super.a�adirLlave(llave);
		}

	}

	/**
	 * Cierra la puerta del laberinto
	 * 
	 * @param lab
	 *            .Laberinto de la simulacion
	 * 
	 */

	private void cerrarPuerta(Laberinto lab) {

		lab.PSalida.cerrar();

	}

	/**
	 * Suelta la llave en la sala indicada.
	 * 
	 * @param sala
	 *            Sala indicada en la que suelta llave
	 */

	private void soltarLlave(Sala sala) {

		Llave llave = new Llave();
		llave = super.sacarLlave();
		sala.insertarLlave(llave);
	}

	/**
	 * Metodo que realiza las acciones correspondientes del tipo robot Asimo
	 * 
	 * @param lab
	 *            Laberinto sobre el cual se realizan las acciones
	 * @param sala
	 *            sala actual que representa la posicion del robot
	 */

	public void accionRobot(Laberinto lab, Sala sala) {
		if (sala.obtenerId() == lab.salaPuerta) {
			if (super.turnoRobot != 0)
				cerrarPuerta(lab);

		}

		if (!super.direccionesRobot.estaVacia()) {
			Sala salaDestino = new Sala();
			salaDestino = super.movimientoRobot(lab, sala);
			if (!super.Llavero.isEmpty()) {
				if (salaDestino.obtenerId() % 2 == 0)
					soltarLlave(salaDestino);
			}
		} else {
			sala.colaRobotsAUX.encolar(this);

		}
		super.turnoRobot++;
	}

	/**
	 * Metodo que traza la ruta cíclica de los robots tipo Asimo
	 * 
	 * @param inicio
	 *            Identificador de la sala de comienzo del robot
	 * 
	 * @param lab
	 *            Laberinto sobre el que se trazará la ruta
	 */

	public void rutaRobot(Integer inicio, Laberinto lab) {
		int destino;

		if (inicio == lab.dimX * lab.dimY - 1) {

			destino = lab.dimX - 1;
			inicio = metodoRuta(inicio, destino, lab);
		}
		if (inicio == lab.dimX - 1) {

			destino = 0;
			inicio = metodoRuta(inicio, destino, lab);
		}
		if (inicio == 0) {
			destino = lab.dimX * lab.dimY - lab.dimX;
			inicio = metodoRuta(inicio, destino, lab);
		}
		if (inicio == lab.dimX * lab.dimY - lab.dimX) {

			destino = lab.dimX * lab.dimY - 1;

			inicio = metodoRuta(inicio, destino, lab);
		}

	}

	/**
	 * Metodo que realiza el camino minimo entre un origen y un destino. Encola
	 * la siguiente direccion a seguir por el robot en su estructura de
	 * direcciones.
	 * 
	 * @param inicio
	 *            identificador de la sala actual
	 * @param destino
	 *            identificador de la sala a la que queremos ir
	 * @param lab
	 *            Laberinto que contiene el grafo para calcular el nodo
	 *            siguiente
	 * @return devuelve siguiente, que será el identificador del proximo inicio
	 *         de la ruta
	 * 
	 */

	private int metodoRuta(int inicio, int destino, Laberinto lab) {
		int siguiente = 0;
		while (inicio != destino) {
			siguiente = lab.grafoRobots.siguiente(inicio, destino);
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
			inicio = siguiente;
		}
		return siguiente;
	}
}
