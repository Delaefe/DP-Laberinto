package ED;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Implementacion de los metodos de la clase Arbol binario de búsqueda.
 * 
 * 
 * <b> Vicente Gonzalez Ortega / Dimas de la Fuente Hernadez </b><br>
 * Nombre del Grupo: TeamRocket Asignatura Desarrollo de Programas<br/>
 * Curso 14/15
 */
public class Arbol<T extends Comparable<T>> {

	/** Dato almacenado en cada nodo del árbol. */
	private T datoRaiz;

	/** Atributo que indica si el árbol está vacío. */
	boolean esVacio;

	/** Hijo izquierdo del nodo actual */
	private Arbol<T> hIzq;

	/** Hijo derecho del nodo actual */
	private Arbol<T> hDer;

	/**
	 * Constructor por defecto de la clase. Inicializa un árbol vacío.
	 */
	public Arbol() {
		this.esVacio = true;
		this.hIzq = null;
		this.hDer = null;
	}

	/**
	 * Crea un nuevo árbol a partir de los datos pasados por parámetro.
	 * 
	 * @param hIzq
	 *            El hijo izquierdo del árbol que se está creando
	 * @param datoRaiz
	 *            Raíz del árbol que se está creando
	 * @param hDer
	 *            El hijo derecho del árbol que se está creando
	 */
	public Arbol(Arbol<T> hIzq, T datoRaiz, Arbol<T> hDer) {
		this.esVacio = false;
		this.datoRaiz = datoRaiz;
		this.hIzq = hIzq;
		this.hDer = hDer;
	}

	/**
	 * Devuelve el hijo izquierdo del árbol
	 * 
	 * @return El hijo izquierdo
	 */
	public Arbol<T> getHijoIzq() {
		return hIzq;
	}

	/**
	 * Devuelve el hijo derecho del árbol
	 * 
	 * @return Hijo derecho del árbol
	 */
	public Arbol<T> getHijoDer() {
		return hDer;
	}

	/**
	 * Devuelve la raíz del árbol
	 * 
	 * @return La raíz del árbol
	 */
	public T getRaiz() {
		return datoRaiz;
	}

	/**
	 * Comprueba si el árbol está vacío.
	 * 
	 * @return verdadero si el árbol está vacío, falso en caso contrario
	 */
	public boolean vacio() {
		return esVacio;
	}

	/**
	 * Inserta un nuevo dato en el árbol.
	 * 
	 * @param dato
	 *            El dato a insertar
	 * @return verdadero si el dato se ha insertado correctamente, falso en caso
	 *         contrario
	 */
	public boolean insertar(T dato) {
		boolean resultado = true;
		if (vacio()) {
			datoRaiz = dato;
			esVacio = false;
		} else {
			if (!this.datoRaiz.equals(dato)) {
				Arbol<T> aux;
				if (dato.compareTo(this.datoRaiz) < 0) { // dato < datoRaiz
					if ((aux = getHijoIzq()) == null)
						hIzq = aux = new Arbol<T>();
				} else { // dato > datoRaiz
					if ((aux = getHijoDer()) == null)
						hDer = aux = new Arbol<T>();
				}
				resultado = aux.insertar(dato);
			} else
				resultado = false;
		}
		return resultado;
	}

	/**
	 * Comprueba si un dato se encuentra almacenado en el árbol
	 * 
	 * @param dato
	 *            El dato a buscar
	 * @return verdadero si el dato se encuentra en el árbol, falso en caso
	 *         contrario
	 */
	public boolean pertenece(T dato) {
		Arbol<T> aux = null;
		boolean encontrado = false;
		if (!vacio()) {
			if (dato.compareTo(this.datoRaiz) == 0)
				encontrado = true;
			else {
				if (dato.compareTo(this.datoRaiz) < 0) // dato < datoRaiz
					aux = getHijoIzq();
				else
					// dato > datoRaiz
					aux = getHijoDer();
				if (aux != null)
					encontrado = aux.pertenece(dato);
			}
		}
		return encontrado;
	}

	/**
	 * Borrar un dato del árbol.
	 * 
	 * @param dato
	 *            El dato que se quiere borrar
	 */
	public void borrar(T dato) {
		if (!vacio()) {
			if (dato.compareTo(this.datoRaiz) < 0) { // dato<datoRaiz
				hIzq = hIzq.borrarOrden(dato);
			} else if (dato.compareTo(this.datoRaiz) > 0) { // dato>datoRaiz
				hDer = hDer.borrarOrden(dato);
			} else // En este caso el dato es datoRaiz
			{
				if (hIzq == null && hDer == null) {
					esVacio = true;
				} else
					borrarOrden(dato);
			}
		}
	}

	/**
	 * Borrar un dato. Este método es utilizado por el método borrar anterior.
	 * 
	 * @param dato
	 *            El dato a borrar
	 * @return Devuelve el árbol resultante después de haber realizado el
	 *         borrado
	 */
	private Arbol<T> borrarOrden(T dato) {
		T datoaux;
		Arbol<T> retorno = this;
		Arbol<T> aborrar, candidato, antecesor;

		if (!vacio() && pertenece(dato)) {
			if (dato.compareTo(this.datoRaiz) < 0) { // dato<datoRaiz
				hIzq = hIzq.borrarOrden(dato);
			} else if (dato.compareTo(this.datoRaiz) > 0) { // dato>datoRaiz
				hDer = hDer.borrarOrden(dato);
			} else {
				aborrar = this;
				if ((hDer == null) && (hIzq == null)) { /* si es hoja */
					retorno = null;
				} else {
					if (hDer == null) { /* Solo hijo izquierdo */
						aborrar = hIzq;
						datoaux = this.datoRaiz;
						datoRaiz = hIzq.getRaiz();
						hIzq.datoRaiz = datoaux;
						hIzq = hIzq.getHijoIzq();
						hDer = aborrar.getHijoDer();

						retorno = this;
					} else if (hIzq == null) { /* Solo hijo derecho */
						aborrar = hDer;
						datoaux = datoRaiz;
						datoRaiz = hDer.getRaiz();
						hDer.datoRaiz = datoaux;
						hDer = hDer.getHijoDer();
						hIzq = aborrar.getHijoIzq();

						retorno = this;
					} else { /* Tiene dos hijos */
						candidato = this.getHijoIzq();
						antecesor = this;
						while (candidato.getHijoDer() != null) {
							antecesor = candidato;
							candidato = candidato.getHijoDer();
						}

						/* Intercambio de datos de candidato */
						datoaux = datoRaiz;
						datoRaiz = candidato.getRaiz();
						candidato.datoRaiz = datoaux;
						aborrar = candidato;
						if (antecesor == this)
							hIzq = candidato.getHijoIzq();
						else
							antecesor.hDer = candidato.getHijoIzq();
					} // Eliminar solo ese nodo, no todo el subarbol
					aborrar.hIzq = null;
					aborrar.hDer = null;
				}
			}
		}
		return retorno;
	}

	public int maximo(int a, int b) {
		if (a > b)
			return a;
		else
			return b;
	}

	/**
	 * Determinar la profundidad del arbol.
	 * 
	 * @return Devuelve un entero que determina la profundida del arbol.
	 */
	public int profundidad() {
		int res = 0;
		int res1 = 0;
		int res2 = 0;
		Arbol<T> aux = null;
		if (!vacio()) {
			if ((aux = getHijoIzq()) != null) {
				res = 1 + aux.profundidad();
			}
			if ((aux = getHijoDer()) != null) {
				res1 = 1 + aux.profundidad();
			}
			if (res > res1)
				res2 = res;
			else
				res2 = res1;
		} else {
			return 0;
		}
		return res2;
		// int res = 0;
		// int res1 = 0;
		// if (vacio())
		// return 0;
		// else {
		// res = 1 + hIzq.profundidad();
		// res1 = 1 + hDer.profundidad();
		// }
		// if (res > res1)
		// return res;
		//
		// return res1;
	}

	/**
	 * Determinar si un nodo es Hoja o no
	 * 
	 * @return Devuelve un boolean que determina si es Hoja o no.
	 */

	public boolean esHoja(T dato) {
		Arbol<T> arbolAux = null;
		boolean esHoja = false;

		if (!vacio()) {
			if (dato.compareTo(this.datoRaiz) == 0) {
				if (hDer == null && hIzq == null)
					esHoja = true;
			} else {
				if (dato.compareTo(this.datoRaiz) < 0)
					esHoja = arbolAux.getHijoIzq().esHoja(dato);
				else {
					if (dato.compareTo(this.datoRaiz) > 0)
						esHoja = arbolAux.getHijoDer().esHoja(dato);
				}
			}
		}
		return esHoja;

	}

	/**
	 * Cuenta los nodos que son Hoja del arbol.
	 * 
	 * @param arbol
	 *            . El arbol sobre el que se realiza el recuento.
	 * @return Devuelve un entero con el numero de nodos hoja.
	 */
	public int nodosHoja() {

		int resultado = 0;
		if (!vacio()) {

			if (esHoja(this.datoRaiz)) { // si la raiz no tiene hijos, es
											// hoja
				resultado++;
			}

			if (hDer != null) {
				resultado = resultado + hDer.nodosHoja(); // recorrido derecha
			}

			if (hIzq != null) {
				resultado = resultado + hIzq.nodosHoja(); // recorrido izquierda
			}

		}
		return resultado;

	}

	/**
	 * Cuenta los nodos que NO son Hoja del arbol.
	 * 
	 * @param arbol
	 *            . El arbol sobre el que se realiza el recuento.
	 * @return Devuelve un entero con el numero de nodos NO hoja.
	 */

	public int nodosNoHoja() {
		int resultado = 0;
		if (!vacio()) {

			if (!esHoja(this.datoRaiz)) { // si la raiz no tiene hijos, es hoja
				resultado++;
			}

			if (hDer != null) {
				resultado = resultado + hDer.nodosNoHoja(); // recorrido derecha
			}

			if (hIzq != null) {
				resultado = resultado + hIzq.nodosNoHoja(); // recorrido
															// izquierda
			}

		}
		return resultado;

	}

	/**
	 * Recorrido inOrden del árbol.
	 */
	public void EscrbirinOrden(FileWriter Salida, BufferedWriter bw) {
		try {
			Arbol<T> aux = null;
			if (!vacio()) {
				if ((aux = getHijoIzq()) != null) {
					aux.inOrden();
				}

				bw.write(this.datoRaiz.toString());

				if ((aux = getHijoDer()) != null) {
					aux.inOrden();
				}
			}
		} catch (IOException valor) {
			System.err.println("Excepci�n capturada al procesar fichero: "
					+ valor.getMessage());
		}

	}

	/**
	 * Recorrido inOrden del árbol.
	 */
	public void inOrden() {
		Arbol<T> aux = null;
		if (!vacio()) {
			if ((aux = getHijoIzq()) != null) {
				aux.inOrden();
			}

			System.out.print(this.datoRaiz.toString());

			if ((aux = getHijoDer()) != null) {
				aux.inOrden();
			}
		}

	}

	/**
	 * Recorrido preOrden del árbol.
	 */
	public void preOrden() {
		Arbol<T> aux = null;
		if (!vacio()) {
			System.out.print(this.datoRaiz.toString());
			if ((aux = getHijoIzq()) != null) {
				aux.preOrden();
			}

			if ((aux = getHijoDer()) != null) {
				aux.preOrden();
			}
		}

	}

	public void borrarArbol() {

		if (!vacio()) {
			datoRaiz = null;

		}
	}

	/**
	 * Método que main que realiza las pruebas con el árbol.
	 * 
	 * @param args
	 *            Argumentos del main //
	 */
	public static void main(String[] args) {
		Arbol<Integer> arbol = new Arbol<Integer>();
		System.out.println("Ejemplos sesion árbol binario de búsqueda" + '\n');

		Integer[] datos = { new Integer(20), new Integer(7), new Integer(18),
				new Integer(6), new Integer(5), new Integer(22),
				new Integer(24), new Integer(26), new Integer(28),
				new Integer(29), new Integer(30) };

		for (int i = 0; i < datos.length; i++) {
			arbol.insertar(datos[i]);
		}

		// // Insertando datos repetidos
		// if (arbol.insertar(new Integer(22)) == false)
		// System.out.println("El ABB no admite elementos duplicados");
		//
		// // Pertenencia de un dato
		// if (arbol.pertenece(new Integer(22)))
		// System.out.println("Pertenece");
		// else
		// System.out.println("NO Pertenece");

		// Recorrido en inOrden
		System.out.println("InOrden" + '\n');
		arbol.inOrden();

		// profunidad
		System.out.println("Profundidad: " + arbol.profundidad() + '\n');
		// nodosHoja
		System.out.println("NodosHoja: " + arbol.nodosHoja() + '\n');
		// nodosNoHOja
		System.out.println("NodosnoHoja: " + arbol.nodosHoja() + '\n');

		arbol.borrar(28);
		System.out.println("BORRADO: " + "7" + '\n');

		// profunidad
		System.out.println("Profundidad: " + arbol.profundidad() + '\n');
		// nodosHoja
		System.out.println("NodosHoja: " + arbol.nodosHoja() + '\n');
		// nodosNoHOja
		System.out.println("NodosnoHoja: " + arbol.nodosHoja() + '\n');

		arbol.borrar(1);
		System.out.println("BORRADO: " + "1" + '\n');

		// profunidad
		System.out.println("Profundidad: " + arbol.profundidad() + '\n');
		// nodosHoja
		System.out.println("NodosHoja: " + arbol.nodosHoja() + '\n');
		// nodosNoHOja
		System.out.println("NodosnoHoja: " + arbol.nodosHoja() + '\n');

		arbol.borrar(20);
		System.out.println("BORRADO: " + "20" + '\n');

		// profunidad
		System.out.println("Profundidad: " + arbol.profundidad() + '\n');
		// nodosHoja
		System.out.println("NodosHoja: " + arbol.nodosHoja() + '\n');
		// nodosNoHOja
		System.out.println("NodosnoHoja: " + arbol.nodosHoja() + '\n');

		// Probando el borrado de diferentes datos -- Descomentar estas líneas
		// para ver qué ocurre
		// arbol.borrar(new Integer(20));
		// System.out.println("Borrado " + 20);
		// arbol.borrar(new Integer(15));
		// System.out.println("Borrado " + 15);

		// Borrando datos del árbol
		// for (int i = 0; i < datos.length; i++) {
		// arbol.borrar(datos[i]);
		// System.out.println(" - Borrado " + datos[i] + '\n');
		// arbol.inOrden();
		// }
	}
}
