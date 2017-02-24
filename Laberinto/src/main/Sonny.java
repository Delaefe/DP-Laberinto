package main;

import java.util.LinkedList;

/**
 * Implementacion de los metodos de la clase Sonny, que hereda de la clase
 * Robot.
 * 
 * 
 * <b> Vicente Gonzalez Ortega / Dimas de la Fuente Hernadez </b><br>
 * Nombre del Grupo: TeamRocket Asignatura Desarrollo de Programas<br/>
 * Curso 14/15
 */
public class Sonny extends Robot {

	public Sonny() {
		super.ponerId("S");
	}

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
	public Sonny(String nombre, String id, int turno, int salaInicio) {
		super(nombre, id, turno, salaInicio);

	}

	/**
	 * Metodo auxiliar para calcular la primera direccion en la ruta del Robot
	 * 
	 * @param inicio
	 *            identificador de la sala de inicio del robot
	 * @param dirRobot
	 *            lista de direcciones del robot que utilizaremos de manera
	 *            auxiliar
	 * @param lab
	 *            Laberinto sobre el que trazaremos la ruta
	 * @return inicio
	 */
	private int AuxRuta(Integer inicio, LinkedList<Dir> dirRobot, Laberinto lab) {
		if (SalaOeste(inicio, lab)) {
			inicio = inicio - 1;
			direccionesRobot.encolar(Dir.O);
			dirRobot.addLast(Dir.O);

		} else {
			if (SalaSur(inicio, lab)) {
				inicio = inicio + lab.dimX;
				direccionesRobot.encolar(Dir.S);
				dirRobot.addLast(Dir.S);
			} else {
				if (SalaEste(inicio, lab)) {
					inicio = inicio + 1;
					direccionesRobot.encolar(Dir.E);
					dirRobot.addLast(Dir.E);

				} else {

					if (SalaNorte(inicio, lab)) {
						inicio = inicio - lab.dimX;
						direccionesRobot.encolar(Dir.N);
						dirRobot.addLast(Dir.N);

					}
				}
			}

		}
		return inicio;

	}

	/**
	 * Metodo que traza la ruta segun el algoritmo de la Mano Derecha de los
	 * robots tipo Bender. Añade las direcciones a seguir por el robot en su
	 * estructura
	 * 
	 * @param inicio
	 *            Identificador de la sala de comienzo del robot
	 * 
	 * @param lab
	 *            Laberinto sobre el que se trazará la ruta
	 */
	public void rutaRobot(Integer inicio, Laberinto lab) {
		// Lista auxiliar para trabajar con la ultima direccion insertada
		LinkedList<Dir> dirRobot = new LinkedList<Dir>();

		inicio = AuxRuta(inicio, dirRobot, lab);

		while (inicio != lab.dimX * lab.dimY - 1) {

			switch (dirRobot.getLast()) {
			case N:
				if (SalaEste(inicio, lab)) {
					inicio = inicio + 1;
					direccionesRobot.encolar(Dir.E);
					dirRobot.addLast(Dir.E);

				} else {
					if (SalaNorte(inicio, lab)) {
						inicio = inicio - lab.dimX;
						direccionesRobot.encolar(Dir.N);
						dirRobot.addLast(Dir.N);

					} else {
						if (SalaOeste(inicio, lab)) {
							inicio = inicio + -1;
							direccionesRobot.encolar(Dir.O);
							dirRobot.addLast(Dir.O);

						} else {

							if (SalaSur(inicio, lab)) {
								inicio = inicio + lab.dimX;
								direccionesRobot.encolar(Dir.S);
								dirRobot.addLast(Dir.S);

							}
						}
					}

				}

				break;

			case O:
				if (SalaNorte(inicio, lab)) {
					inicio = inicio - lab.dimX;
					direccionesRobot.encolar(Dir.N);
					dirRobot.addLast(Dir.N);

				} else {
					if (SalaOeste(inicio, lab)) {
						inicio = inicio - 1;
						direccionesRobot.encolar(Dir.O);
						dirRobot.addLast(Dir.O);

					} else {
						if (SalaSur(inicio, lab)) {
							inicio = inicio + lab.dimX;
							direccionesRobot.encolar(Dir.S);
							dirRobot.addLast(Dir.S);

						} else {

							if (SalaEste(inicio, lab)) {
								inicio = inicio + 1;
								direccionesRobot.encolar(Dir.E);
								dirRobot.addLast(Dir.E);

							}
						}
					}

				}

				break;
			case E:
				if (SalaSur(inicio, lab)) {
					inicio = inicio + lab.dimX;
					direccionesRobot.encolar(Dir.S);
					dirRobot.addLast(Dir.S);

				} else {
					if (SalaEste(inicio, lab)) {
						inicio = inicio + 1;
						direccionesRobot.encolar(Dir.E);
						dirRobot.addLast(Dir.E);

					} else {
						if (SalaNorte(inicio, lab)) {
							inicio = inicio - lab.dimX;
							direccionesRobot.encolar(Dir.N);
							dirRobot.addLast(Dir.N);

						} else {

							if (SalaOeste(inicio, lab)) {
								inicio = inicio - 1;
								direccionesRobot.encolar(Dir.O);
								dirRobot.addLast(Dir.O);

							}
						}
					}

				}
				break;
			case S:
				if (SalaOeste(inicio, lab)) {
					inicio = inicio - 1;
					direccionesRobot.encolar(Dir.O);
					dirRobot.addLast(Dir.O);

				} else {
					if (SalaSur(inicio, lab)) {
						inicio = inicio + lab.dimX;
						direccionesRobot.encolar(Dir.S);
						dirRobot.addLast(Dir.S);

					} else {
						if (SalaEste(inicio, lab)) {
							inicio = inicio + 1;
							direccionesRobot.encolar(Dir.E);
							dirRobot.addLast(Dir.E);

						} else {

							if (SalaNorte(inicio, lab)) {
								inicio = inicio - lab.dimX;
								direccionesRobot.encolar(Dir.N);
								dirRobot.addLast(Dir.N);

							}
						}
					}

				}
				break;

			}

		}
	}

}
