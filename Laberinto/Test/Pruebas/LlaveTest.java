package Pruebas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import main.Llave;

import org.junit.Before;
import org.junit.Test;

public class LlaveTest {
	private Llave llave;
	private Llave llave2;

	@Before
	public void setUp() {
		llave = new Llave(6);
		llave = new Llave(23);
	}

	@Test
	public void testEquals() {
		System.out.println("ejecutando testEquals");
		assertTrue(!llave.equals(null));
		assertTrue(!llave.equals(llave2));
	}

	@Test
	public void testSetGet() {
		Llave llave3 = new Llave();
		int id1 = 21;
		llave3.ponerId(id1);
		int id2 = llave3.obtenerId();
		assertEquals(id1, id2);
	}

}