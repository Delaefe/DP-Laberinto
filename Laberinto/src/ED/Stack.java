package ED;
/**
 * Implementacion de los metodos de la clase Pila.
 * 
 * 
 * <b> Vicente Gonzalez Ortega / Dimas de la Fuente Hernadez </b><br>
 * Nombre del Grupo: TeamRocket Asignatura Desarrollo de Programas<br/>
 * Curso 14/15
 */

public class Stack<T extends Comparable<T>> {

	/** Puntero a la cima de la pila */
	private Node<T> top;

	private class Node<T extends Comparable<T>> {
		/** Dato almacenado en cada nodo */
		private T data;
		/** Enlace al siguiente elemento */
		private Node<T> next;

		Node(T data) {
			this.data = data;
			this.next = null;
		}
	}// class Nodo

	/**
	 * Metodo constructor por defecto de la clase Pila
	 */
	public Stack() {
		top = null;
	}

	/**
	 * Metodo constructor parametrizado de la clase Stack
	 * 
	 * @param data
	 *            es el nuevo elemento en la pila
	 */
	public Stack(T data) {
		Node<T> node = new Node<T>(data);
		node.next = top;
		top = node;
	}

	/**
	 * Metodo que devuelve el elemento en la cima de la pila
	 * 
	 * @return la cima de la pila
	 */
	public T getTop() {
		return top.data;
	}

	/**
	 * Metodo para comprobar si la pila est� vacia o no
	 * 
	 * @return true si est� vacia o false en caso contrario
	 */
	public boolean isEmpty() {
		return (top == null);
	}

	/**
	 * Metodo que permite insertar un dato
	 * 
	 * @param dato
	 *            valor que se va a insertar
	 */
	public void addData(T data) {
		Node<T> node = new Node<T>(data);
		node.next = top;
		top = node;
	}
	/**
	 * 
	 */


	public void mostrar() {
		Stack<T> aux = new Stack<T>();
		T dato;
		while (!isEmpty()) {
			dato = getTop();
			removeData();
			System.out.print(dato + "|");
			aux.addData(dato);
		}
		this.vaciar(aux);

	}

	public void vaciar(Stack<T> stack) {
		T dato;
		while (!stack.isEmpty()) {
			dato = stack.getTop();
			this.addData(dato);
			stack.removeData();

		}
	}

	/**
	 * Elimina un dato de la pila. Se elimina el dato que est� en la top
	 */
	public void removeData() {
		if (!isEmpty()) {
			top = top.next;
		}
	}

	// /**
	// * Metodo principal. Hace pruebas de inserciones y borrados en la pila y
	// muestra el resultado obtenido
	// *
	// * @param args Argumentos que recibe el programa principal
	// */
	// public static void main (String args[]) {
	// Integer[] dataSet = {new Integer(2),new Integer(8),new Integer(3),
	// new Integer(4),new Integer(1),new Integer(5)};
	//
	// //Pruebas de inserciones en la pila
	// Stack stack = new Stack();
	// for (int i = 0; i < dataSet.length; i++) {
	// stack.addData(dataSet[i]);
	// }
	//
	// //Mostrando la pila
	// while(!stack.isEmpty()){
	// System.out.print(stack.getTop() + " : ");
	// stack.removeData();
	// }
	//
	// System.out.println();
	// }
}
