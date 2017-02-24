package main;

/**
 * Implementacion de los metodos de la clase Llave.
 * 
 * 
 * <b> Vicente Gonzalez Ortega / Dimas de la Fuente Hernadez </b><br>
 * Nombre del Grupo: TeamRocket Asignatura Desarrollo de Programas<br/>
 * Curso 14/15
 */
public class Llave implements Comparable<Llave> {
	/** Numero entero que designa el identificador de la llave */
	private int id_llave;

	/**
	 * Constructor parametrizado de la clase Llav
	 * 
	 * @param id
	 *            identificador de la llave
	 */
	public Llave(int id) {
		this.id_llave = id;

	}

	/**
	 * Metodo constructor por defecto de la clase Llave.
	 * 
	 * @param id
	 *            Identificador de la llave.
	 */
	public Llave() {
		this.id_llave = 0;

	}

	@Override
	public String toString() {
		return id_llave + " ";
	}

	/**
	 * Metodo que devuelve el identificador de la Llave.
	 * 
	 * @return Devuelve el identificador de la llave.
	 */

	public int obtenerId() {
		return id_llave;

	}

	/**
	 * Metodo que pone el identificador de una llave
	 * 
	 * @param id
	 *            identificador de la llave
	 * */

	public void ponerId(int id) {
		this.id_llave = id;
	}

	/**
	 * Comparando dos objetos.
	 * 
	 * @param llave
	 *            La llave con la que comparar
	 * @return Devuelve 0 si son iguales, -1 si es mayor y 1 si es menor.
	 */

	public int compareTo(Llave llave) {
		int resultado = 0;
		if (this.id_llave < llave.id_llave)
			resultado = -1;
		else {
			if (this.id_llave > llave.id_llave)
				resultado = 1;
		}
		return resultado;
	}

}
