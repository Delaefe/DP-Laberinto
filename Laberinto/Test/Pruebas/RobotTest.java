package Pruebas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import main.Laberinto;
import main.Llave;
import main.Robot;
import main.Sala;

import org.junit.Before;
import org.junit.Test;

enum Dir {
	S, E, N, O
};

public class RobotTest {
	private Robot robot = null;
	private Robot robot2 = null;
	private Robot robot3 = null;

	@Before
	public void setUp() {
		robot = new Robot("R2D2", "R", 2, 0) {

			@Override
			public void accionRobot(Laberinto lab, Sala sala) {
				// TODO Auto-generated method stub

			}

			@Override
			public void rutaRobot(Integer v, Laberinto lab) {
				// TODO Auto-generated method stub
				
			}
		};
		robot2 = new Robot("Croc", "W", 2, 35) {

			@Override
			public void accionRobot(Laberinto lab, Sala sala) {
				// TODO Auto-generated method stub

			}

			@Override
			public void rutaRobot(Integer v, Laberinto lab) {
				// TODO Auto-generated method stub
				
			}
		};

		robot3 = new Robot() {

			@Override
			public void accionRobot(Laberinto lab, Sala sala) {
				// TODO Auto-generated method stub

			}

			@Override
			public void rutaRobot(Integer v, Laberinto lab) {
				// TODO Auto-generated method stub
				
			}
		};

	}

	@Test
	public void testEquals() {
		System.out.println("ejecutando testEquals");
		assertTrue(!robot.equals(null));
		assertTrue(!robot2.equals(null));
		assertTrue(!robot.equals(robot2));
	}

	@Test
	public void testNombre() {
		String nombre1 = "Paco";
		robot.ponerNombre(nombre1);
		String nombre2 = robot.obtenerNombre();
		assertEquals(nombre1, nombre2);

	}

	@Test
	public void testId() {
		String id1 = "P";
		robot.ponerId(id1);
		String id2 = robot.obtenerId();
		assertEquals(id1, id2);
	}

	@Test
	public void testSala() {
		int sala1 = 4;
		robot.ponerSala(sala1);
		int sala2 = robot.obtenerSala();
		assertEquals(sala1, sala2);
	}

	@Test
	public void testTurno() {
		int turno = 23;
		robot.ponerTurno(turno);
		int turno2 = robot.obtenerTurno();
		assertEquals(turno, turno2);
	}

	@Test
	public void testLlave() {
		Llave llave = new Llave(2);
		Llave llave1 = new Llave(4);
		Llave llave2 = new Llave(23);
		Llave llave3 = new Llave(9);

		robot3.añadirLlave(llave);
		robot3.añadirLlave(llave1);
		robot3.añadirLlave(llave2);
		robot3.añadirLlave(llave3);

		assertTrue(llave3.equals(robot3.sacarLlave()));
		assertTrue(llave2.equals(robot3.sacarLlave()));
		assertTrue(llave1.equals(robot3.sacarLlave()));
		assertTrue(llave.equals(robot3.sacarLlave()));

	}

	// @Test
	// public void testDirecciones() {
	//
	// Dir[] direccionesA = { Dir.N, Dir.N, Dir.O, Dir.N, Dir.N, Dir.O, Dir.S,
	// Dir.O, Dir.O, Dir.N, Dir.N, Dir.O, Dir.S, Dir.S, Dir.S, Dir.S,
	// Dir.S,
	//
	// Dir.E, Dir.E, Dir.E, Dir.E, Dir.N };
	// robot3.asignarRuta(direccionesA);
	//
	// }
}
// @Test
// public void test() {
// fail("Not yet implemented");
// }