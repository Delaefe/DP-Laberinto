package main;

public class Pared {

	/** Identificador de la sala inicio de la pared */
	public int inicio;
	/** Identificador de la sala final de la pared */
	public int fin;

	/**
	 * Constructor por defecto de la clase Pared
	 */
	public Pared() {
		this.inicio = 0;
		this.fin = 0;
	}

	/**
	 * Metodo que devuelve el inicio de una Pared.
	 * 
	 * @return Devuelve el identificador de inicio.
	 */
	public int getInicio() {
		return inicio;

	}

	/**
	 * Metodo que devuelve el fin.
	 * 
	 * @return Devuelve el identificador de fin.
	 */
	public int getFin() {
		return fin;

	}

	/**
	 * Metodo que pone el inicio a una pared
	 * 
	 * @param inicio
	 *            identificador de la sala inicio de la pared
	 */

	public void ponerInicio(int inicio) {
		this.inicio = inicio;
	}

	/**
	 * Metodo que pone el inicio a una pared
	 * 
	 * @param fin
	 *            identificador de la sala fin de la pared
	 */

	void ponerFin(int fin) {
		this.fin = fin;
	}

}
