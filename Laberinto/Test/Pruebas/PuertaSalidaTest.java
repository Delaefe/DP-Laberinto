package Pruebas;

import static org.junit.Assert.assertEquals;
import main.Llave;
import main.PuertaSalida;

import org.junit.Before;
import org.junit.Test;

public class PuertaSalidaTest {
	PuertaSalida PSalida;

	@Before
	public void setUp() {
		PSalida = new PuertaSalida();

	}

	@Test
	public void testIdPuerta() {
		int idPuerta = 13;
		PSalida.poneridPuertaSalida(idPuerta);
		assertEquals(idPuerta, PSalida.idPuertaSalida);
	}

	@Test
	public void testAltura() {
		int altura = 4;
		PSalida.ponerAlturaPuerta(altura);
		assertEquals(altura, PSalida.alturaPuerta);
	}

	@Test
	public void testAbrir() {
		System.out
				.println("Probando el método abrir de la Puerta de Salida" + '\n');
		int estado = 0;

		Llave llave2 = new Llave(6);
		Llave llave1 = new Llave(1);
		Llave llave3 = new Llave(12);
		Llave llave4 = new Llave(23);
		Llave llave5 = new Llave(10);
		Llave llave6 = new Llave(3);
		Llave llave7 = new Llave(4);
		Llave llave8 = new Llave(21);
		Llave llave9 = new Llave(15);
		Llave llave10 = new Llave(11);

		PSalida.combinacion.insertar(llave2);
		PSalida.combinacion.insertar(llave4);
		PSalida.combinacion.insertar(llave6);
		PSalida.combinacion.insertar(llave8);
		PSalida.combinacion.insertar(llave10);
		PSalida.alturaPuerta = PSalida.combinacion.profundidad();

		PSalida.abrir(llave1);
		PSalida.abrir(llave2);
		PSalida.abrir(llave3);
		PSalida.abrir(llave4);
		PSalida.abrir(llave5);
		PSalida.abrir(llave6);
		PSalida.abrir(llave7);
		PSalida.abrir(llave8);
		PSalida.abrir(llave9);
		PSalida.abrir(llave10);

		if (PSalida.condicionApertura)
			estado = 1; // Si la condicion de apertura es true, cambia la
						// variable estado a 1,
						// que consideramos que indica que la puerta se ha
						// abierto

		assertEquals(1, estado);

	}

	@Test
	public void testCerrar() {
		System.out.println("PROBANDO EL METODO CERRAR DE LA PUERTA DE SALIDA");
		int expectedPuerta = 0;

		// Creamos varias llaves para insertarlas en el arbol de la combinacion
		Llave llave2 = new Llave(6);
		Llave llave4 = new Llave(23);
		Llave llave6 = new Llave(3);
		Llave llave8 = new Llave(21);
		Llave llave10 = new Llave(11);

		// Insertamos dichas las llaves anteriores en el arbol
		PSalida.combinacion.insertar(llave2);
		PSalida.combinacion.insertar(llave4);
		PSalida.combinacion.insertar(llave6);
		PSalida.combinacion.insertar(llave8);
		PSalida.combinacion.insertar(llave10);

		// Guardamos en el arrayLlaves una copia de las llaves de la combinacion
		PSalida.arrayLlaves.add(llave2);
		PSalida.arrayLlaves.add(llave4);
		PSalida.arrayLlaves.add(llave6);
		PSalida.arrayLlaves.add(llave8);
		PSalida.arrayLlaves.add(llave10);

		// Guardamos en una variable la combinacion actual del arbol, para
		// comprobar si tras probar llaves en la puerta(lo que haria que la
		// combinacion disminuyera)
		// la cerramos, la combinacion vuelve a su estado original, es decir, se
		// reconfigura con las llaves anteriores.
		int Profundidad = PSalida.combinacion.profundidad();
		PSalida.alturaPuerta = PSalida.combinacion.profundidad();

		// Probamos algunas llaves en la cerradura
		PSalida.abrir(llave4);
		PSalida.abrir(llave6);
		PSalida.abrir(llave8);
		PSalida.abrir(llave10);
		// Llamamos al metodo cerrar de la Puerta
		PSalida.cerrar();

		// Si el método nos ha cambiado la condicion de la puerta a false,
		// tomaremos el valor 1 como que ha realizado correctamente su funcion
		if (PSalida.condicionApertura == false)
			expectedPuerta = 1;

		// Comprobamos los resultados de la llamada al metodo
		assertEquals(1, expectedPuerta);
		assertEquals(Profundidad, PSalida.combinacion.profundidad());
	}

	// @Test
	// public void test() {
	// fail("Not yet implemented");
	// }

}