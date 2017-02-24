package Pruebas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import main.Laberinto;
import main.Llave;
import main.Robot;
import main.Sala;

import org.junit.Before;
import org.junit.Test;

public class SalaTest {
	Sala sala;

	@Before
	public void setUp() {
		sala = new Sala();

	}

	@Test
	public void testIdSala() {
		int idSala = 21;
		sala.ponerId(idSala);
		assertEquals(idSala, sala.obtenerId());
	}

	public void testMarcaSala() {
		int marca = 21;
		sala.ponerMarca(marca);
		assertEquals(marca, sala.obtenerId());
	}

	@Test
	public void testColaRobots() {

		int estado = 0;
		// Creamos dos Robots para introducir en la cola de la Sala
		Robot robot = new Robot() {

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
		// Introducimos los robots anteriores en la Sala
		sala.encolarRobot(robot);
		sala.encolarRobot(robot2);

		// Comprobamos si la sala esta ocupada. Si lo esta, el valor estado sera
		// igual a 1
		if (sala.salaOcupada())
			estado = 1;
		// Realizamos las comprobaciones
		assertEquals(1, estado);
		assertEquals(robot, sala.obtenerRobot());
		sala.desencolarRobot();
		assertEquals(robot2, sala.obtenerRobot());
		sala.desencolarRobot();

		if (!sala.salaOcupada())
			estado = 2;
		assertEquals(2, estado);
	}

	@Test
	public void testLlavesSala() {
		int estado = 0;
		// Creamos varias llaves para insertar en la Lista de llaves de la Sala
		Llave llave1 = new Llave(1);
		Llave llave2 = new Llave(6);
		Llave llave3 = new Llave(12);
		// Insertamos las llaves en la Lista de la Sala
		sala.insertarLlave(llave3);
		sala.insertarLlave(llave2);
		sala.insertarLlave(llave1);
		// Comprobamos si la Lista de llaves no esta vacia. En caso afirmativo,
		// cambiaremos la variable estado a 1, indicando que la sala contiene
		// llaves
		if (sala.contieneLlaves())
			estado = 1;
		// Realizamos la comprobacion del estado de la Lista
		assertEquals(1, estado);

		// Realizamos comprobaciones con las llaves de la Lista
		assertTrue(llave1.equals(sala.obtenerLlave()));
		assertTrue(!llave1.equals(sala.obtenerLlave()));
		assertTrue(llave3.equals(sala.obtenerLlave()));

		// Una vez obtenidas todas las llaves, la Lista deberia estar vacia. En
		// caso afirmativo, cambiaremos la variable estado a 2, indicando que la
		// sala no contiene llaves
		if (!sala.contieneLlaves())
			estado = 2;
		// Realizamos la comprobacion del estado de la Lista
		assertEquals(2, estado);

	}
	// @Test
	// public void test() {
	// fail("Not yet implemented");
	// }

}