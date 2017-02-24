package ED;

/**
 * Implementacion de los metodos de la clase Lista.
 * 
 * 
 * <b> Vicente Gonzalez Ortega / Dimas de la Fuente Hernadez </b><br>
 * Nombre del Grupo: TeamRocket Asignatura Desarrollo de Programas<br/>
 * Curso 14/15
 */
public class List<T extends Comparable<T>> {
	/** Puntero al primer elemento de la Lista */
	private Node<T> first;

	/** Puntero al último elemento de la Lista */
	private Node<T> last;

	/** Tamaño de la Lista */
	Integer size = 0;

	public class Node<T> {
		/** Data almacenado en cada nodo */
		private T Data;
		/** Enlace al siguiente elemento */
		private Node<T> next;
		/** Enlace al elemento anterior */
		private Node<T> prev;

		public Node(Node<T> prev, T Data, Node<T> next) {
			this.Data = Data;
			this.next = next;
			this.prev = prev;
		}

		/**
		 * Metodo que devuelve el siguiente nodo de la Lista (para recorridos
		 * con la lista)
		 * 
		 * @return el elemento next
		 */
		public Node<T> next() {
			return next;
		}

		public void setPrev(Node<T> node) {
			prev = node;
		}

		public void setNext(Node<T> node) {
			next = node;
		}

		/**
		 * Metodo que devuelve el anterior nodo de la Lista (para recorridos con
		 * la lista)
		 * 
		 * @return el elemento prev
		 */
		public Node<T> prev() {
			return prev;
		}

		/**
		 * Metodo para consultar un Data
		 * 
		 * @return el Data contenido en el nodo actual
		 */
		public T get() {
			return Data;
		}
	}// class Nodo

	/**
	 * Metodo constructor por defecto de la clase Lista
	 */
	public List() {
		first = last = null;
		size = 0;
	}

	/**
	 * Metodo constructor parametrizado de la clase Lista
	 * 
	 * @param valor
	 *            es el nuevo elemento en la lista
	 */
	public List(T Data) {
		addLast(Data);
	}

	/**
	 * Metodo que devuelve el elemento del inicio de la Lista
	 * 
	 * @return el primer elemento
	 */
	public T getFirst() {
		return first.Data;
	}

	/**
	 * Metodo que devuelve el último elemento de la Lista
	 * 
	 * @return el último elemento
	 */
	public T getLast() {

		return last.Data;
	}

	/**
	 * Metodo que devuelve el inicio de la Lista
	 * 
	 * @return first
	 */
	public Node<T> first() {
		return first;
	}

	/**
	 * Metodo que devuelve el final de la Lista
	 * 
	 * @return last
	 */
	public Node<T> last() {
		return last;
	}

	/**
	 * Metodo para comprobar si la lista esta vacia o no
	 * 
	 * @return true si esta vacia o false en caso contrario
	 */
	public boolean estaVacia() {
		return (size == 0);
	}

	/**
	 * Metodo para comprobar el tamaño de la lista
	 * 
	 * @return size tamaño de la lista
	 */
	public Integer size() {
		return size;
	}

	/**
	 * Metodo para consultar un Data almacenado en una posicion
	 * 
	 * @return el Data contenido en el nodo que está en la posición pos de la
	 *         lista
	 */
	public T get(int pos) {
		T d = null;
		Node<T> iter = first;
		int i = 0;
		boolean encontrado = false;
		while (i <= pos && !encontrado && iter != null) {
			if (i == pos) {
				encontrado = true;
				d = iter.Data;
			}
			i++;
			iter = iter.next;
		}
		return d;
	}

	/**
	 * Permite insertar al final de la lista
	 * 
	 * @param Data
	 *            valor que se va a insertar
	 */
	public void addLast(T Data) {
		Node<T> l = last;
		Node<T> nodo = new Node<T>(l, Data, null);
		last = nodo;
		if (l == null)
			first = nodo;
		else
			l.next = nodo;
		size++;
	}

	/**
	 * Permite eliminar el último elemento de la Lista
	 * 
	 */
	public void removeLast() {
		// implementación del método
		if (last != null) {
			last = last.prev();
		}
		// no hay elementos
		if (last == null)
			first = null;
		else
			last.next = null;
		size--;
	}

	// **********************************************************************************
	// ***** Ejercicios adicionales para alumnos
	// ****************************************
	// **********************************************************************************
	/**
	 * Permite eliminar el primer elemento de la Lista
	 * 
	 */
	public void removeFirst() {
		// TODO: Implementar el método

		if (first != null) {
			first = first.next();

		}

		size--;
	}

	/**
	 * Permite eliminar un dato almacenado en la lista
	 * 
	 * @param dato
	 *            valor que se eliminará de la lista
	 * @return la posición en la que se almacenaba el dato
	 */
	public int removeDato(T dato) {
		// TODO: Implementar el método

		Node<T> iter = first;
		int i = 0;
		boolean encontrado = false;

		while (i < size && !encontrado && iter != null) {
			if (dato.compareTo(iter.get()) == 0) {
				if (iter.prev != null) {
					if (iter.next != null) {
						iter.prev.next = iter.next;
						iter.next.prev = iter.prev;
					} else {
						iter.prev.next = null;
						iter.prev = null;
					}
				} else {
					first = iter.next;
					iter.next.prev = null;
					iter.next = null;

				}
				encontrado = true;
				size--;
				return i;

			}
			i++;
			iter = iter.next;
		}

		return -1;
	}

	/**
	 * Permite insertar de forma ordenada en la lista
	 * 
	 * @param Data
	 *            valor que se va a insertar
	 */

	public void sortedAdd(T Data) {
		// TODO: Implementar el método
		Node<T> iter = first;
		int i = 0;
		boolean encontrado = false;

		if (!estaVacia()) {
			if (iter.get().compareTo(Data) >= 0) {
				addFirst(Data);
				encontrado = true;
			}
			while (i < size && !encontrado && iter.next() != null) {

				if (iter.get().compareTo(Data) < 0) {
					iter = iter.next;

				} else {
					addBeforeIndex(Data, i);
					encontrado = true;

				}

				i++;
			}
			if (!encontrado && iter != null) {

				addAfterIndex(Data, i);

			}
		} else {
			addFirst(Data);
		}
	}

	public void addAfterIndex(T Data, int index) {
		// TODO: Implementar el método
		Node<T> iter = first;
		Node<T> nodo = new Node<T>(iter, Data, null);
		int i = 0;
		boolean encontrado = false;

		while (i <= size && !encontrado && iter != null) {
			if (i == index) {
				if (iter.next == null) {
					iter.next = nodo;
					nodo.prev = iter;
					nodo.next = null;

				} else {
					iter.next.prev = nodo;
					nodo.next = iter.next;
					nodo.prev = iter;
					iter.next = nodo;
				}

				encontrado = true;
				size++;
			}
			i++;
			iter = iter.next;
		}
	}

	/**
	 * Permite insertar al principio de la lista
	 * 
	 * @param Data
	 *            valor que se va a insertar
	 */
	public void addFirst(T Data) {

		// TODO: Implementar el método

		Node<T> iter = first;
		Node<T> nodo = new Node<T>(iter, Data, null);
		if (first != null) {
			iter.prev = nodo;
			nodo.next = iter;
			nodo.prev = null;
			first = nodo;
		} else {
			first = nodo;
			last = nodo;
		}
		size++;
	}

	/**
	 * comprueba si un valor existe en la lista
	 * 
	 * @param Data
	 *            valor que se va a comprobar
	 * @return true si el Data existe en la lista o false en caso contrario
	 */

	public boolean contains(T Data) {
		// TODO: Implementar el método

		Node<T> iter = first;
		int i = 0;
		boolean encontrado = false;

		while (i <= size && !encontrado && iter != null) {

			if (Data.compareTo(iter.get()) == 0) {
				return encontrado = true;
			}
			i++;
			iter = iter.next;
		}
		return encontrado;
	}

	/**
	 * Permite insertar delante de un valor dado
	 * 
	 * @param Data
	 *            valor que se va a insertar
	 * @param valorbuscar
	 *            valor delante del cual se insertará el nuevo dato
	 */
	public void addBefore(T Data, T valorbuscar) {
		// TODO: Implementar el método

		Node<T> iter = first;
		Node<T> nodo = new Node<T>(iter, Data, null);
		iter.Data = Data;
		int i = 0;
		boolean encontrado = false;

		while (i <= size && !encontrado && iter != null) {
			if (iter.get() == valorbuscar) {
				iter.prev.next = nodo;
				iter.prev = nodo;
				nodo.next = iter;

				encontrado = true;
			}
			i++;
			iter = iter.next;
		}
	}

	/**
	 * Permite insertar detrás de un valor dado
	 * 
	 * @param Data
	 *            valor que se va a insertar
	 * @param valorbuscar
	 *            valor detrás del cual se insertará el nuevo dato
	 */
	public void addAfter(T Data, T valorbuscar) {
		// TODO: Implementar el método

		Node<T> iter = first;
		Node<T> nodo = new Node<T>(iter, Data, null);
		iter.Data = Data;
		int i = 0;
		boolean encontrado = false;

		while (i <= size && !encontrado && iter != null) {
			if (iter.get() == valorbuscar) {
				iter.next.prev = nodo;
				nodo.next = iter.next;
				nodo.prev = iter;
				iter.next = nodo;

				encontrado = true;
			}
			i++;
			iter = iter.next;
		}
	}

	/**
	 * Permite insertar delante de una posici�n dada
	 * 
	 * @param Data
	 *            valor que se va a insertar
	 * @param index
	 *            posición delante de la cual se insertará el nuevo dato
	 */
	public void addBeforeIndex(T Data, int index) {
		// TODO: Implementar el método

		Node<T> iter = first;
		int i = 0;
		boolean encontrado = false;

		while (i <= size && !encontrado && iter != null) {
			if (i == index) {
				if (iter.prev != null) {
					Node<T> nodo = new Node<T>(iter, Data, null);
					iter.prev.next = nodo;
					nodo.prev = iter.prev;
					nodo.next = iter;
					iter.prev = nodo;

				} else {
					Node<T> nodo = new Node<T>(iter, Data, null);

					iter.prev = nodo;
					nodo.next = iter;
					nodo.prev = null;

				}
				encontrado = true;
				size++;
			}
			i++;
			iter = iter.next;
		}
	}

	/**
	 * Permite insertar detrás de una posición dada
	 * 
	 * @param Data
	 *            valor que se va a insertar
	 * @param index
	 *            posición detrás de la cual se insertará el nuevo dato
	 */

	/**
	 * Permite insertar en una posición dada
	 * 
	 * @param Data
	 *            valor que se va a insertar
	 * @param index
	 *            posición en la cual se insertará el nuevo Data
	 */
	public void addIndex(T Data, int index) {
		// TODO: Implementar el método
		Node<T> iter = first;
		Node<T> nodo = new Node<T>(iter, Data, null);
		iter.Data = Data;
		int i = 0;
		boolean encontrado = false;

		while (i <= size && !encontrado && iter != null) {
			if (i == index) {
				iter.prev.next = nodo;
				nodo.prev = iter.prev;
				iter.next.prev = nodo;
				nodo.next = iter.next;

				encontrado = true;
			}
			i++;
			iter = iter.next;
		}
	}

	/**
	 * Permite eliminar el Data almacenado en una posición dada
	 * 
	 * @param index
	 *            posición del Data que se eliminará
	 * @return el dato que está al inicio de la lista
	 */
	public T removeIndex(int index) {
		// TODO: Implementar el método
		Node<T> iter = first;
		int i = 0;
		boolean encontrado = false;

		while (i <= size && !encontrado && iter != null) {
			if (i == index) {
				iter.prev.next = iter.next;
				iter.next.prev = iter.prev;

				encontrado = true;
			}
			i++;
			iter = iter.next;
		}
		return null;
	}

	/**
	 * Cambia el valor almacenado en una posición por otro
	 * 
	 * @param index
	 *            posición del dato que se cambiará
	 * @param Data
	 *            nuevo valor por el que se sustituirá el valor que hay
	 *            almacenado actualmente
	 */
	public void set(int index, T Data) {
		// TODO: Implementar el método

		Node<T> iter = first;
		int i = 0;
		boolean encontrado = false;

		while (i <= size && !encontrado && iter != null) {
			if (i == index) {
				iter.Data = Data;
				encontrado = true;
			}
			i++;
			iter = iter.next;
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((last == null) ? 0 : last.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		return result;
	}

	/**
	 * Metodo principal. Hace pruebas de inserciones y borrados en la lista y
	 * muestra el resultado obtenido
	 * 
	 * @param args
	 *            Argumentos que recibe el programa principal
	 */
	// public static void main(String args[]) {
	// Integer[] dataSet = { new Integer(2), new Integer(3), new Integer(3),
	// new Integer(4), new Integer(5), new Integer(7), new Integer(8),
	// new Integer(9) };
	//
	// // Pruebas de inserciones al final y al inicio
	// List<Integer> list = new List<Integer>();
	// for (int i = 0; i < dataSet.length; i++) {
	// list.addLast(dataSet[i]);
	// }
	//
	// // list.removeDato(12);
	// // list.addFirst(dataSet[4]);
	// // Integer dato = new Integer(8);
	// // if (list.contains(0))
	// // System.out.println("Contains ok");
	// // else
	// // System.out.println("Not ok");
	//
	// // Mostrando la lista
	//
	// list.sortedAdd(2);
	// list.sortedAdd(32);
	// list.sortedAdd(6);
	// list.sortedAdd(21);
	// list.sortedAdd(33);
	// list.sortedAdd(211);
	//
	// list.sortedAdd(1);
	// // Mostrando la lista
	// for (int i = 0; i < list.size(); i++) {
	// System.out.print(list.get(i) + " : ");
	// }
	//
	// }
}
