package ED;

/**
 * Implementacion de los metodos de la clase Cola.
 * 
 * 
 * <b> Vicente Gonzalez Ortega / Dimas de la Fuente Hernadez </b><br>
 * Nombre del Grupo: TeamRocket Asignatura Desarrollo de Programas<br/>
 * Curso 14/15
 */
public class Cola<TipoDato extends Comparable<TipoDato>> {

	/** Puntero al frente de la cola. */
	private Nodo<TipoDato> frente;

	/** Puntero al fin de la cola. */
	private Nodo<TipoDato> fin;

	private class Nodo<TipoDato extends Comparable<TipoDato>> {
		/** Dato almacenado en cada nodo */
		private TipoDato dato;
		/** Enlace al siguiente elemento */
		private Nodo<TipoDato> siguiente;

		Nodo(TipoDato dato) { // Constructor de la clase Nodo.
			this.dato = dato;
			this.siguiente = null;
		}
	} // Clase Nodo.

	/*********************************************************************************************************************/

	/**
	 * Constructor por defecto de la clase Cola.
	 */
	public Cola() {
		frente = null;
		fin = null;
	}

	/**
	 * Método que permite quitar un elemento de la cola.
	 */
	public void desencolar() {

		frente = frente.siguiente;

		if (frente == null) {
			fin = null;
		}

	}

	public int size() {
		Cola<TipoDato> colaAux = new Cola<TipoDato>();
		int size = 0;
		TipoDato dato;

		while (!estaVacia()) {
			dato = primero();
			size++;
			colaAux.encolar(dato);
			desencolar();
		}
		while (!colaAux.estaVacia()) {
			dato = colaAux.primero();
			colaAux.desencolar();
			encolar(dato);
		}
		return size;
	}

	/**
	 * Método que permite introducir un dato en la cola.
	 * 
	 * @param dato
	 *            dato que se desea insertar.
	 */
	public void encolar(TipoDato dato) {
		Nodo<TipoDato> aux = new Nodo<TipoDato>(dato);
		if (frente == null) {
			frente = aux;
			aux.siguiente = null;
		} else {
			fin.siguiente = aux;
			aux.siguiente = null;
		}
		fin = aux;
	}

	/**
	 * Método que indica si la cola está vacía o no.
	 * 
	 * @return true si está vacía; false en caso contrario.
	 */
	public boolean estaVacia() {
		if (frente == null) {
			return true;
		}
		return false;
	}

	/**
	 * Metodo que muestra los elementos de la cola
	 * 
	 * 
	 */
	public void mostrar() {
		Cola<TipoDato> colaAux = new Cola<TipoDato>();
		TipoDato dato;
		while (!estaVacia()) {
			dato = primero();
			System.out.print(dato + " ");
			colaAux.encolar(dato);
			desencolar();
		}
		while (!colaAux.estaVacia()) {
			dato = colaAux.primero();
			colaAux.desencolar();
			encolar(dato);
		}
	}

	/**
	 * Método que devuelve el primer elemento de la cola.
	 * 
	 * @return el primer elemento.
	 */
	public TipoDato primero() {
		return frente.dato;
	}

	// public static void main(String[] args) {
	//
	// Cola<Integer> aux = new Cola<Integer>();
	//
	// aux.encolar(1);
	// aux.encolar(2);
	// // aux.encolar(3);
	// // aux.encolar(4);
	// // aux.encolar(5);
	// // aux.encolar(6);
	// // aux.encolar(7);
	//
	// aux.mostrar();
	// System.out.println("_______________________________________");
	// aux.desencolar();
	// if (aux.estaVacia())
	// System.out.println("VACIA");
	// aux.desencolar();
	// if (aux.estaVacia())
	// System.out.println("VACIA2");
	// aux.encolar(3);
	// aux.mostrar();
	// aux.desencolar();
	// aux.desencolar();
	// if (aux.estaVacia())
	// System.out.println("VACIA3");
	// // while (!aux.estaVacia()) {
	// //
	// // aux.desencolar();
	// // aux.mostrar();
	// // }
	//
	// }

}