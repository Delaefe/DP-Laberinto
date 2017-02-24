package Pruebas;

import static org.junit.Assert.assertTrue;
import main.Laberinto;
import main.PuertaSalida;
import main.Robot;
import main.Sala;

import org.junit.Before;
import org.junit.Test;

public class LaberintoTest {

	Laberinto lab;

	@Before
	public void setUp() {
		lab = new Laberinto(6, 6, (6 * 6 - 1), 3);
	}

	@Test
	public void testInsertarRobot() {
		int idSalaRobot = 7;
		int idSala;

		// Creamos dos robots. Uno para insertar en el laberinto y otro para
		// obtener el robot de la sala idSalaRobot
		Robot robot = new Robot("Bender", "B", 3,idSalaRobot) {

			@Override
			public void accionRobot(Laberinto lab, Sala sala) {
				// TODO Auto-generated method stub

			}

			@Override
			public void rutaRobot(Integer v, Laberinto lab) {
				// TODO Auto-generated method stub
				
			}
		};
		Robot robot2 = new Robot() {

			@Override
			public void accionRobot(Laberinto lab, Sala sala) {
				// TODO Auto-generated method stub

			}

			@Override
			public void rutaRobot(Integer v, Laberinto lab) {
				// TODO Auto-generated method stub
				
			}
		};
		// Insertamos el robot en el Laberinto
		lab.insertarRobot(robot);

		// Como el robot tiene asignada una sala de inicio, buscamos en la
		// matriz del laberinto la sala que coincida con el id de la sala
		// idSalaRobot
		for (int i = 0; i < lab.LaberintoMatriz.length; i++) {
			for (int j = 0; j < lab.LaberintoMatriz[i].length; j++) {
				idSala = lab.LaberintoMatriz[i][j].obtenerId();
				if (idSala == idSalaRobot) {
					if (lab.LaberintoMatriz[i][j].salaOcupada())
						// Igualamos robot2 al robot obtenido de la sala
						robot2 = lab.LaberintoMatriz[i][j].obtenerRobot();

				}
			}
		}
		// Comprobamos si el robot insertado es igual al robot obtenido
		assertTrue(robot.equals(robot2));
	}

	@Test
	// TODO
	public void testInsertarPuerta() {
		PuertaSalida PSal = new PuertaSalida();

	}

}