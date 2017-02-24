package main;

import java.util.List;

/**
 * Clase creada para ser usada en la utilidad cargador contiene el main del
 * cargador. Se crea una instancia de la clase Estacion, una instancia de la
 * clase Cargador y se procesa el fichero de inicio, es decir, se leen todas las
 * líneas y se van creando todas las instancias de la simulaci�n
 * 
 * @version 1.0 - 02/11/2011
 * @author Profesores DP
 */
public class Cargador {
	/**
	 * n�mero de elementos distintos que tendr� la simulaci�n - lab, Bender,
	 * Sonny, Spirit, Asimo
	 */
	static final int NUMELTOSCONF = 5;
	/**
	 * atributo para almacenar el mapeo de los distintos elementos
	 */
	static private DatoMapeo[] mapeo;

	boolean laberintoCreado;
	/**
	 * referencia a la instancia del patr�n Singleton
	 */
	public Laberinto lab;

	/**
	 * constructor parametrizado
	 * 
	 * @param e
	 *            referencia a la instancia del patr�n Singleton
	 */
	Cargador() {
		mapeo = new DatoMapeo[NUMELTOSCONF];
		mapeo[0] = new DatoMapeo("LABERINTO", 5);
		mapeo[1] = new DatoMapeo("BENDER", 4);
		mapeo[2] = new DatoMapeo("SONNY", 4);
		mapeo[3] = new DatoMapeo("SPIRIT", 4);
		mapeo[4] = new DatoMapeo("ASIMO", 4);
		laberintoCreado = false;
	}

	public Laberinto getLaberinto() {
		return lab;
	}

	/**
	 * busca en mapeo el elemento leído del fichero inicio.txt y devuelve la
	 * posici�n en la que est�
	 * 
	 * @param elto
	 *            elemento a buscar en el array
	 * @return res posici�n en mapeo de dicho elemento
	 */
	private int queElemento(String elto) {
		int res = -1;
		boolean enc = false;

		for (int i = 0; (i < NUMELTOSCONF && !enc); i++) {
			if (mapeo[i].getNombre().equals(elto)) {
				res = i;
				enc = true;
			}
		}
		return res;
	}

	/**
	 * método que crea las distintas instancias de la simulaci�n
	 * 
	 * @param elto
	 *            nombre de la instancia que se pretende crear
	 * @param numCampos
	 *            n�mero de atributos que tendr� la instancia
	 * @param vCampos
	 *            array que contiene los valores de cada atributo de la
	 *            instancia
	 */
	public void crear(String elto, int numCampos, List<String> vCampos) {

		int numElto = queElemento(elto);

		if ((numElto != -1) && (mapeo[numElto].getCampos() == numCampos)) {
			switch (queElemento(elto)) {
			case 0:
				crearLab(numCampos, vCampos);
				break;
			case 1:
				crearBender(numCampos, vCampos);
				break;
			case 2:
				crearSonny(numCampos, vCampos);
				break;
			case 3:
				crearSpirit(numCampos, vCampos);
				break;
			case 4:
				crearAsimo(numCampos, vCampos);
				break;
			}
		} else
			System.out
					.println("ERROR Cargador::crear: Datos de configuraci�n incorrectos... "
							+ elto + "," + numCampos + "\n");
	}

	/**
	 * método que crea una instancia de la clase Planta
	 * 
	 * @param numCampos
	 *            n�mero de atributos que tendr� la instancia
	 * @param vCampos
	 *            array que contiene los valores de cada atributo
	 */
	private void crearLab(int numCampos, List<String> vCampos) {

		if (Integer.parseInt(vCampos.get(1)) > 0
				&& Integer.parseInt(vCampos.get(2)) > 0) {

			lab = new Laberinto(Integer.parseInt(vCampos.get(1)),
					Integer.parseInt(vCampos.get(2)), Integer.parseInt(vCampos
							.get(3)), Integer.parseInt(vCampos.get(4)));

			PuertaSalida puerta = new PuertaSalida();
			lab.insertarPuerta(Integer.parseInt(vCampos.get(4)), puerta);
			lab.kruskal();
			lab.crearAtajos();
			lab.grafoRobots.floyd();
			lab.grafoRobots.warshall();
			lab.distribuirLlaves();
			int[] listaIdLlaves = new int[15];
			int j = 0;

			for (int i = 0; i < 30; i++) {
				if ((i % 2) != 0) {

					listaIdLlaves[j] = i;
					j++;
				}
			}
			int inicio = 0;
			int fin = listaIdLlaves.length;

			lab.generarCombinacion(listaIdLlaves, inicio, fin);
			laberintoCreado = true;
		} else {

			System.out
					.println("El laberinto debe tener dimensiones positivas. Revise el archivo inicio.txt y proporcione valores validos");
		}
	}

	/**
	 * método que crea una instancia de la clase Bender
	 * 
	 * @param numCampos
	 *            n�mero de atributos que tendr� la instancia
	 * @param vCampos
	 *            array que contiene los valores de cada atributo
	 */
	private void crearBender(int numCampos, List<String> vCampos) {

		if (laberintoCreado) {

			Bender benderB = new Bender(vCampos.get(1), vCampos.get(2),
					Integer.parseInt(vCampos.get(3)), 0);
			benderB.rutaRobot(0, lab);

			lab.insertarRobot(benderB);
		}

	}

	/**
	 * método que crea una instancia de la clase Sonny
	 * 
	 * @param numCampos
	 *            n�mero de atributos que tendr� la instancia
	 * @param vCampos
	 *            array que contiene los valores de cada atributo
	 */
	private void crearSonny(int numCampos, List<String> vCampos) {
		if (laberintoCreado) {
			Sonny sonnyS = new Sonny(vCampos.get(1), vCampos.get(2),
					Integer.parseInt(vCampos.get(3)), 0);
			sonnyS.rutaRobot(0, lab);
			lab.insertarRobot(sonnyS);
		}
	}

	/**
	 * método que crea una instancia de la clase Spirit
	 * 
	 * @param numCampos
	 *            n�mero de atributos que tendr� la instancia
	 * @param vCampos
	 *            array que contiene los valores de cada atributo
	 */
	private void crearSpirit(int numCampos, List<String> vCampos) {

		if (laberintoCreado) {
			Spirit spiritT = new Spirit(vCampos.get(1), vCampos.get(2),
					Integer.parseInt(vCampos.get(3)), lab.dimX * lab.dimY
							- lab.dimX);
			spiritT.rutaRobot(lab.dimX * lab.dimY - lab.dimX, lab);
			lab.insertarRobot(spiritT);
		}
	}

	/**
	 * método que crea una instancia de la clase Asimo
	 * 
	 * @param numCampos
	 *            n�mero de atributos que tendr� la instancia
	 * @param vCampos
	 *            array que contiene los valores de cada atributo
	 */
	private void crearAsimo(int numCampos, List<String> vCampos) {

		if (laberintoCreado) {
			Asimo asimoA = new Asimo(vCampos.get(1), vCampos.get(2),
					Integer.parseInt(vCampos.get(3)), lab.dimX * lab.dimY - 1);

			int[] listaIdLlaves = new int[15];
			int j = 0;

			for (int i = 0; i < 30; i++) {
				if ((i % 2) != 0) {

					listaIdLlaves[j] = i;
					j++;
				}
			}
			asimoA.rutaRobot(lab.dimX * lab.dimY - 1, lab);
			asimoA.configurarAsimo(listaIdLlaves);
			lab.insertarRobot(asimoA);
		}
	}

	static void main(String args[]) {

	}
}