package Pruebas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import main.Asimo;
import main.Llave;

import org.junit.Before;
import org.junit.Test;

public class AsimoTest {
	Asimo asimo;

	@Before
	public void setUp() {
		asimo = new Asimo("Asimov", "A", 2,35);

	}

	@Test
	public void testConfigurarAsimo() {
		int[] Llaves = { 3, 4, 6, 8, 9, 10, 11, 12, 13 };
		asimo.configurarAsimo(Llaves);

		Llave llave = new Llave(13);

		assertEquals(llave.obtenerId(), asimo.sacarLlave().obtenerId());
		assertNotSame(llave.obtenerId(), asimo.sacarLlave().obtenerId());
	}

}