package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import ED.Arbol;
import ED.Cola;
import ED.Grafo;
import ED.List;
import ED.Stack;

/**
 *
 * Nombre del Grupo: TeamRocket
 * * <b> Vicente Gonzalez Ortega / Dimas de la Fuente Hernadez </b><br>
 * Programa principal - Entrega EC4.
 * Curso: 14/15
 * Asignatura Desarrollo de Programas<br/>
 *
 * @param args
 *            Argumentos que recibe el programa principal
 *
 * @return Retorna la salida del programa
 */

/**
 * Tipo enumerado que representa las 4 posibles direcciones en las que puede
 * moverse un robot.
 */
enum Dir {
	S, E, N, O
};

public class Laberinto {

	/** Puerta de Salida del Laberinto */
	PuertaSalida PSalida;
	/** Dimensión X del Laberinto */
	public int dimX;
	/** Dimensión Y del Laberinto */
	public int dimY;
	/** Ubicación de la sala en la que se encuentra la puerta de Salida */
	public int salaPuerta;
	/** Grafo del Laberinto */
	Grafo grafoRobots;
	/** Matriz con las salas del Laberinto */
	public Sala[][] LaberintoMatriz;
	/** Turno general del Laberinto */
	int turno;
	/** Maximo de turnos que puede tener el laberinto */
	int MAXTURNOS;
	/** Cola de robots donde se almacenan los robots ganadores */
	protected Cola<Robot> colaRobotsGanadores;
	/** Lista con las paredes del Laberinto */
	public ArrayList<Pared> ListaParedes;

	/**
	 * Método constructor por parametros de la clase Laberinto
	 * 
	 * @param salaP
	 *            Ubicacion de la sala que contiene la Puerta de Salida
	 * @param X
	 *            Valor que toma la dimension X del Laberinto
	 * @param Y
	 *            Valor que toma la dimension Y del Laberinto
	 */

	public Laberinto(int X, int Y, int salaP, int alturaPuerta) {
		this.dimX = X;
		this.dimY = Y;
		this.salaPuerta = salaP;
		this.MAXTURNOS = 50;
		this.LaberintoMatriz = new Sala[X][Y];
		this.grafoRobots = new Grafo();
		this.colaRobotsGanadores = new Cola<Robot>();
		this.ListaParedes = new ArrayList<Pared>();

		iniciarEstucturaProyecto();

		int idSala = 0;
		for (int i = 0; i < LaberintoMatriz.length; i++) {
			for (int j = 0; j < LaberintoMatriz[i].length; j++) {
				LaberintoMatriz[i][j] = new Sala();
				LaberintoMatriz[i][j].ponerId(idSala);
				LaberintoMatriz[i][j].ponerMarca(idSala);

				idSala++;

			}

		}
		PSalida = new PuertaSalida();
	}

	/**
	 * Metodo que crea los nodos del grafo(Inicia la estructura)
	 */
	private void iniciarEstucturaProyecto() {
		for (int i = 0; i < dimY * dimX; i++) {
			grafoRobots.nuevoNodo(i);
			if (!grafoRobots.nuevoNodo(i))
				System.out.println("Error en la insercion del nodo " + i);
		}
	}

	/**
	 * Genera la combinacion de la puerta de salida.
	 * 
	 * @param listaLlaves
	 *            . Array que contiene los identificadores de las llaves
	 * @param inicio
	 *            . Entero que determina el inicio del array
	 * @param fin
	 *            . Entero que determina el fin del array
	 * 
	 * @return Devuelve un array ya ordenado con la combinacion.
	 */

	public void generarCombinacion(int[] listaLlaves, int inicio, int fin) {
		int mitad = 0;
		if (fin != inicio) {
			mitad = ((fin - inicio) - 1) / 2;

			int idLlave2 = listaLlaves[mitad + inicio];
			Llave llave = new Llave();
			llave.ponerId(idLlave2);

			PSalida.combinacion.insertar(llave);

			PSalida.arrayLlaves.add(llave);

			mitad++;

			if (mitad > 0) {
				generarCombinacion(listaLlaves, inicio, fin - mitad);
				generarCombinacion(listaLlaves, mitad + inicio, fin);
			}

		}
		PSalida.condicionApertura = false;
	}

	/**
	 * Método que genera las 45 llaves iniciales que se repartiran por las salas
	 * del laberinto
	 * 
	 * @return .Lista de llaves
	 * */
	private Cola<Llave> generarLlaves() {

		Cola<Llave> listaIdLlaves = new Cola<Llave>();

		for (int i = 0; i < 30; i++) {

			if ((i % 2) != 0) {

				Llave llave = new Llave();
				llave.ponerId(i);
				Llave llave2 = new Llave();
				llave2.ponerId(i);
				listaIdLlaves.encolar(llave);
				listaIdLlaves.encolar(llave2);

			} else {
				Llave llave = new Llave();
				llave.ponerId(i);

				listaIdLlaves.encolar(llave);

			}

		}
		return listaIdLlaves;

	}

	/**
	 * Metodo que inserta la puerta en el laberinto.
	 * 
	 * @param pue
	 *            .Puerta del laberinto.
	 * 
	 * @param AlturaPuerta
	 * 
	 *            .Altura de la puerta del laberinto.
	 */
	public void insertarPuerta(int AlturaPuerta, PuertaSalida puerta) {
		if (this.PSalida != null) {
			this.PSalida = puerta;
			int id;
			id = dimX * dimY - 1;
			PSalida.poneridPuertaSalida(id);
			PSalida.ponerAlturaPuerta(AlturaPuerta);
		}
	}

	/**
	 * Módulo que inserta el robot en una de la salas del laberinto
	 * 
	 * @param robot
	 *            .Robot que se va a insertar en la sala
	 * 
	 */
	public void insertarRobot(Robot robot) {

		int salaInsertar = robot.obtenerSala();
		int idSala = 0;
		for (int i = 0; i < LaberintoMatriz.length; i++) {
			for (int j = 0; j < LaberintoMatriz[i].length; j++) {
				idSala = LaberintoMatriz[i][j].obtenerId();
				if (idSala == salaInsertar) {
					LaberintoMatriz[i][j].encolarRobot(robot);

				}
			}
		}

	}

	/**
	 * Método que devuelve la sala que tiene como identificador el identificador
	 * entrado como parametro.
	 * 
	 * @param idSala
	 *            .Identificador de la sala que vamos a buscar
	 * 
	 * @return .Sala con ese identificador
	 */
	private Sala buscarSala(int idSala) {
		Sala sala = new Sala();
		sala = null;

		for (int i = 0; i < LaberintoMatriz.length; i++) {
			for (int j = 0; j < LaberintoMatriz[i].length; j++) {
				if (LaberintoMatriz[i][j].obtenerId() == idSala) {
					sala = LaberintoMatriz[i][j];
					return sala;
				}
			}
		}

		return sala;
	}

	/**
	 * Método que añade a una lista todas las posibles paredes del Laberinto.
	 * 
	 */
	private void obtenerParedes() {

		int k = 0;

		for (int i = 0; i < LaberintoMatriz.length; i++) {
			for (int j = 0; j < LaberintoMatriz[i].length; j++) {
				if (LaberintoMatriz[i][j].SalaNorte(this) != -1) {
					if (LaberintoMatriz[i][j].marca != LaberintoMatriz[i][j]
							.SalaNorte(this)) {
						Pared pared = new Pared();
						pared.ponerInicio(LaberintoMatriz[i][j].obtenerId());
						pared.ponerFin(LaberintoMatriz[i][j].SalaNorte(this));
						ListaParedes.add(k, pared);

						k++;
					}

				}
				if (LaberintoMatriz[i][j].SalaEste(this) != -1) {
					if (LaberintoMatriz[i][j].marca != LaberintoMatriz[i][j]
							.SalaEste(this)) {
						Pared pared = new Pared();
						pared.ponerInicio(LaberintoMatriz[i][j].obtenerId());
						pared.ponerFin(LaberintoMatriz[i][j].SalaEste(this));
						ListaParedes.add(k, pared);

						k++;
					}
				}

				if (LaberintoMatriz[i][j].SalaSur(this) != -1) {
					if (LaberintoMatriz[i][j].marca != LaberintoMatriz[i][j]
							.SalaSur(this)) {
						Pared pared = new Pared();
						pared.ponerInicio(LaberintoMatriz[i][j].obtenerId());
						pared.ponerFin(LaberintoMatriz[i][j].SalaSur(this));
						ListaParedes.add(k, pared);

						k++;
					}

				}

				if (LaberintoMatriz[i][j].SalaOeste(this) != -1) {
					if (LaberintoMatriz[i][j].marca != LaberintoMatriz[i][j]
							.SalaOeste(this)) {
						Pared pared = new Pared();
						pared.ponerInicio(LaberintoMatriz[i][j].obtenerId());
						pared.ponerFin(LaberintoMatriz[i][j].SalaOeste(this));
						ListaParedes.add(k, pared);

						k++;
					}

				}

			}
		}

	}

	/**
	 * Metodo que rompe las paredes de las salas aleatorianmente
	 */
	public void kruskal() {
		int inicio;
		int fin;
		int marca;
		int marcafin;
		obtenerParedes();
		while (!ListaParedes.isEmpty()) {
			int aleatorio = GenAleatorios.generarNumero(ListaParedes.size());
			Pared paredDerribar;
			paredDerribar = ListaParedes.get(aleatorio);
			ListaParedes.remove(aleatorio);

			inicio = paredDerribar.getInicio();
			fin = paredDerribar.getFin();

			if (buscarSala(inicio).obtenerMarca() != buscarSala(fin)
					.obtenerMarca()) {
				grafoRobots.nuevoArco(inicio, fin, 1);
				grafoRobots.nuevoArco(fin, inicio, 1);
				marca = buscarSala(inicio).obtenerMarca();
				marcafin = buscarSala(fin).obtenerMarca();
				buscarSala(inicio).ponerMarca(marcafin);

				propagarMarca(marca, marcafin);
			}

		}

	}

	/**
	 * Método que propaga la marca de las salas
	 * 
	 * @param marca
	 *            .Marca de la salas que tiene que propagar
	 * @param marcafin
	 *            .Marca que les pone a las salas con la marca anterior
	 */
	private void propagarMarca(int marca, int marcafin) {

		for (int i = 0; i < LaberintoMatriz.length; i++) {
			for (int j = 0; j < LaberintoMatriz[i].length; j++) {
				if (marca == LaberintoMatriz[i][j].obtenerMarca()) {
					LaberintoMatriz[i][j].ponerMarca(marcafin);

				}
			}

		}

	}

	/**
	 * Método que crea atajos entre las paredes del laberinto
	 */
	public void crearAtajos() {
		int NumAtajos = (dimX * dimY) * 5 / 100;// Numeros de paredes a derribar
		int i = 0;
		int SS;
		while (i < NumAtajos) {
			SS = GenAleatorios.generarNumero(dimX * dimY);
			Sala sala1;
			sala1 = buscarSala(SS);
			int idsala;
			if (sala1.SalaNorte(this) != -1 && i < NumAtajos) {
				idsala = sala1.SalaNorte(this);
				if (!grafoRobots.adyacente(idsala, SS)) {
					if ((!grafoRobots.adyacente(SS - 1, idsala - 1)
							|| !grafoRobots.adyacente(SS - 1, SS) || !grafoRobots
								.adyacente(idsala - 1, idsala))
							&& (!grafoRobots.adyacente(SS + 1, idsala + 1)
									|| !grafoRobots.adyacente(SS, SS + 1) || !grafoRobots
										.adyacente(idsala, idsala + 1))) {

						grafoRobots.nuevoArco(idsala, SS, 1);
						grafoRobots.nuevoArco(SS, idsala, 1);

						i++;
					}
				}

			}
			if (sala1.SalaSur(this) != -1 && i < NumAtajos) {
				idsala = sala1.SalaSur(this);
				if (!grafoRobots.adyacente(idsala, SS)) {
					if ((!grafoRobots.adyacente(SS - 1, idsala - 1)
							|| !grafoRobots.adyacente(SS - 1, SS) || !grafoRobots
							.adyacente(idsala - 1, idsala)
							&& (!grafoRobots.adyacente(SS + 1, idsala + 1)
									|| !grafoRobots.adyacente(SS, SS + 1) || !grafoRobots
										.adyacente(idsala, idsala + 1)))) {
						grafoRobots.nuevoArco(idsala, SS, 1);
						grafoRobots.nuevoArco(SS, idsala, 1);

						i++;
					}
				}
			}
			if (sala1.SalaOeste(this) != -1 && i < NumAtajos) {
				idsala = sala1.SalaOeste(this);

				if (!grafoRobots.adyacente(idsala, SS)) {
					if ((!grafoRobots.adyacente(SS - dimX, SS)
							|| !grafoRobots.adyacente(idsala, idsala - dimX) || !grafoRobots
								.adyacente(SS - dimX, idsala - dimX))
							&& (!grafoRobots
									.adyacente(SS + dimX, idsala + dimX)
									|| !grafoRobots.adyacente(SS, SS + dimX) || !grafoRobots
										.adyacente(idsala, idsala + dimX))) {
						grafoRobots.nuevoArco(idsala, SS, 1);
						grafoRobots.nuevoArco(SS, idsala, 1);

						i++;
					}

				}
			}
			if (sala1.SalaEste(this) != -1 && i < NumAtajos) {
				idsala = sala1.SalaEste(this);

				if (!grafoRobots.adyacente(idsala, SS)) {
					if ((!grafoRobots.adyacente(SS - dimX, SS)
							|| !grafoRobots.adyacente(idsala, idsala - dimX) || !grafoRobots
								.adyacente(SS - dimX, idsala - dimX))
							&& (!grafoRobots
									.adyacente(SS + dimX, idsala + dimX)
									|| !grafoRobots.adyacente(SS, SS + dimX) || !grafoRobots
										.adyacente(idsala, idsala + dimX))) {
						grafoRobots.nuevoArco(idsala, SS, 1);
						grafoRobots.nuevoArco(SS, idsala, 1);

						i++;
					}

				}
			}
		}

	}

	/**
	 * Metodo que distribulle las llaves por las salas.
	 */
	public void distribuirLlaves() {
		Prof(0, grafoRobots);
		List<Sala> salasReparto = ordenarPorFrecuencia();
		Cola<Llave> listaLlaves = generarLlaves();
		while (!salasReparto.estaVacia() && !listaLlaves.estaVacia()) {
			insertarLlaves(salasReparto.getFirst(), listaLlaves);
			salasReparto.removeFirst();
		}
	}

	/**
	 * Método que ordena las Salas segun la frecuencia de paso.
	 * 
	 * @return .Lista de salas ordenada por frecuencia
	 */
	private List<Sala> ordenarPorFrecuencia() {

		Sala salaMayorFrec;
		List<Sala> listaSalas = new List<Sala>();
		for (int i = 0; i < LaberintoMatriz.length; i++) {
			for (int j = 0; j < LaberintoMatriz[i].length; j++) {
				salaMayorFrec = LaberintoMatriz[i][j];
				if (salaMayorFrec.frecuencia > 0) {
					listaSalas.addLast(salaMayorFrec);
				}
			}
		}
		listaSalas = ordenar(listaSalas);
		return listaSalas;
	}

	/**
	 * Método que ordena las salas segun la frecuencia de paso.
	 * 
	 * @param listaSalas
	 *            .Lista de salas que tiene que ordenar.
	 * @return .Lista de salas ordenada.
	 */
	private List<Sala> ordenar(List<Sala> listaSalas) {

		for (int i = 1; i < listaSalas.size(); i++) {
			for (int j = 0; j < listaSalas.size() - i; j++) {
				if (listaSalas.get(j).frecuencia < listaSalas.get(j + 1).frecuencia) {
					Sala salaAux = listaSalas.get(j);
					Sala salaAux2 = listaSalas.get(j + 1);
					listaSalas.set(j, salaAux2);
					listaSalas.set(j + 1, salaAux);

				}
			}
		}

		return listaSalas;

	}

	/**
	 * Método que inserta en una sala dada, una serie de llaves.
	 * 
	 * @param sala
	 *            .Sala donde se insertan las llaves.
	 * @param listaLlaves
	 *            .Cola de llaves a insertar.
	 */
	private void insertarLlaves(Sala sala, Cola<Llave> listaLlaves) {
		int contador;
		contador = 0;
		while (contador < 5 && !listaLlaves.estaVacia()) {
			if (sala.obtenerId() != 0) {
				sala.insertarLlave(listaLlaves.primero());
				listaLlaves.desencolar();
				contador++;
			} else
				contador++;
		}

	}

	/**
	 * Módulo encargado de distribuir las llaves por cada sala del laberinto.
	 * 
	 * @param idSalasConLlave
	 *            .Array que contiene las salas en las que se insertan las
	 *            llaves iniciales.
	 */

	private void Prof(Integer v, Grafo G) {

		ArrayList<Integer> Visitados = new ArrayList<Integer>();
		CaminosLaberinto(v, Visitados, G);

	}

	/**
	 * Método que recorre el laberinto en profundidad.
	 * 
	 * @param v
	 *            .Vertice donde se encuentra
	 * @param Visitados
	 *            .ArrayList de nodos visitados
	 * @param G
	 *            .Grafo en el que se realiza el recorrido.
	 * @param ListaVisitados
	 *            .
	 */
	private void CaminosLaberinto(Integer v, ArrayList<Integer> Visitados,
			Grafo G) {

		TreeSet<Integer> ady;
		ady = new TreeSet<Integer>();
		Sala sala;

		Integer w = null;

		Visitados.add(v);
		if (v == salaPuerta) {
			int i = 0;
			while (i < Visitados.size() - 1) {
				sala = buscarSala(Visitados.get(i));
				sala.frecuencia++;

				i++;

			}

		} else {

			G.adyacentes(v, ady);
			while (!ady.isEmpty()) {

				w = ady.first();

				ady.remove(w);

				if (!Visitados.contains(w)) {
					CaminosLaberinto(w, Visitados, G);

				}
			}

		}
		Visitados.remove(Visitados.size() - 1);

	}

	/**
	 * El método procesar se ejecutará turno a turno, recorriendo el laberinto
	 * desde la sala de entrada hasta la de salida.
	 * 
	 * @param FileWriter
	 *            Salida .Fichero en el que se escribe el proceso del juego
	 * @param BufferedWriter
	 *            bw .Buffer de escritura.
	 * */
	public void procesar(FileWriter Salida, BufferedWriter sa) {
		try {
			turno = 0;
			while (turno < MAXTURNOS && PSalida.condicionApertura == false) {
				for (int i = 0; i < LaberintoMatriz.length; i++) {
					for (int j = 0; j < LaberintoMatriz[i].length; j++) {
						if (LaberintoMatriz[i][j].salaOcupada()) {

							LaberintoMatriz[i][j].simular(this);

						}
					}

				}
				PintarTurnos();
				FicheroTurnos(Salida, sa);
				turno++;
			}
			sa.close();

		} catch (IOException valor) {
			System.err.println("Excepcion capturada al procesar fichero: "
					+ valor.getMessage());
		}

	}

	/**************************************** METODOS DE ESCRITURA EN FICHERO Y PANTALLA ***************************/
	//
	/**
	 * Es el encargado de mostrar por pantalla las situaciones de las salas.
	 */

	private void situacionSalas() {
		for (int i = 0; i < LaberintoMatriz.length; i++) {
			for (int j = 0; j < LaberintoMatriz[i].length; j++) {
				if (LaberintoMatriz[i][j].contieneLlaves()) {
					System.out.print('\n' + "(sala:"
							+ LaberintoMatriz[i][j].obtenerId() + ": ");
					for (int x = 0; x < LaberintoMatriz[i][j].llavesSala.size(); x++) {
						System.out.print(LaberintoMatriz[i][j].llavesSala
								.get(x));
					}
					System.out.print(")");
				}
			}
		}
	}

	/**
	 * Es el encargado de mostrar por pantalla las situaciones de los robots en
	 * el juego.
	 */
	private void situacionRobots() {
		for (int i = 0; i < LaberintoMatriz.length; i++) {
			for (int j = 0; j < LaberintoMatriz[i].length; j++) {
				if (LaberintoMatriz[i][j].salaOcupada()) {
					while (!LaberintoMatriz[i][j].colaRobots.estaVacia()) {
						System.out.print('\n'
								+ "("
								+ LaberintoMatriz[i][j].obtenerRobot()
										.obtenerNombre()
								+ ":"
								+ LaberintoMatriz[i][j].obtenerRobot()
										.obtenerId()
								+ ":"
								+ LaberintoMatriz[i][j].obtenerId()
								+ ":"
								+ LaberintoMatriz[i][j].obtenerRobot()
										.obtenerTurno() + "-Llaves: ");
						LaberintoMatriz[i][j].obtenerRobot().Llavero.mostrar();
						System.out.print(")");
						LaberintoMatriz[i][j].colaRobotsAUX
								.encolar(LaberintoMatriz[i][j].obtenerRobot());
						LaberintoMatriz[i][j].colaRobots.desencolar();
					}
					while (!LaberintoMatriz[i][j].colaRobotsAUX.estaVacia()) {
						LaberintoMatriz[i][j].colaRobots
								.encolar(LaberintoMatriz[i][j].colaRobotsAUX
										.primero());
						LaberintoMatriz[i][j].colaRobotsAUX.desencolar();
					}

				}
			}

		}

	}

	/**
	 * Escribe en un fichero los datos iniciales del Laberinto
	 * 
	 * @param FileWriter
	 *            Salida .Fichero en el que se escribe el proceso del juego
	 * @param BufferedWriter
	 *            bw .Buffer de escritura.
	 * */
	private void FicheroSalida(FileWriter Salida, BufferedWriter bw) {

		try {
			escribirLaberintoSinRobot(bw);
			bw.write("(Distribucion llaves)");
			bw.newLine();
			escribirSalas(Salida, bw);
			escribirRutasRobot(bw);

		} catch (IOException valor) {
			System.err.println("Excepci�n capturada al procesar fichero: "
					+ valor.getMessage());
		}

	}

	/**
	 * Escribe en el fichero las rutas asignadas a cada Robot del Laberinto,
	 * 
	 * @param bw
	 * 
	 *            .Buffer de escritura.
	 * 
	 * @throws IOException
	 */
	private void escribirRutasRobot(BufferedWriter bw) throws IOException {
		Cola<Robot> RobotAux = new Cola<Robot>();

		for (int i = 0; i < LaberintoMatriz.length; i++) {
			for (int j = 0; j < LaberintoMatriz[i].length; j++) {
				while (LaberintoMatriz[i][j].salaOcupada()) {
					bw.write("(Ruta: "
							+ LaberintoMatriz[i][j].obtenerRobot().obtenerId()
									.toString() + ":");
					RobotAux.encolar(LaberintoMatriz[i][j].obtenerRobot());
					Cola<Dir> direccionesRobotAux = new Cola<Dir>();
					while (!LaberintoMatriz[i][j].obtenerRobot().direccionesRobot
							.estaVacia()) {
						bw.write(LaberintoMatriz[i][j].obtenerRobot().direccionesRobot
								.primero().toString() + " ");
						direccionesRobotAux.encolar(LaberintoMatriz[i][j]
								.obtenerRobot().direccionesRobot.primero());
						LaberintoMatriz[i][j].obtenerRobot().direccionesRobot
								.desencolar();
					}
					while (!direccionesRobotAux.estaVacia()) {
						LaberintoMatriz[i][j].obtenerRobot().direccionesRobot
								.encolar(direccionesRobotAux.primero());
						direccionesRobotAux.desencolar();
					}
					bw.write(")");
					LaberintoMatriz[i][j].desencolarRobot();
					bw.newLine();

				}
				while (!RobotAux.estaVacia()) {
					LaberintoMatriz[i][j].encolarRobot(RobotAux.primero());
					RobotAux.desencolar();
				}
			}
		}
	}

	/**
	 * Escribe en el fichero las llaves que tiene cada Robot del Laberinto,
	 * indicando su identificador, su nombre, asi como su turno y sala de donde
	 * empieza.
	 * 
	 * @param bw
	 * 
	 *            .Buffer de escritura.
	 * 
	 * @throws IOException
	 */
	private void escribirLlavesRobot(BufferedWriter bw) throws IOException {
		Cola<Robot> RobotAux = new Cola<Robot>();

		for (int i = 0; i < LaberintoMatriz.length; i++) {
			for (int j = 0; j < LaberintoMatriz[i].length; j++) {
				while (LaberintoMatriz[i][j].salaOcupada()) {
					bw.write("("
							+ LaberintoMatriz[i][j].obtenerRobot()
									.obtenerNombre().toString()
							+ ":"
							+ LaberintoMatriz[i][j].obtenerRobot().obtenerId()
									.toString()
							+ ":"
							+ LaberintoMatriz[i][j].obtenerId()
							+ ":"
							+ LaberintoMatriz[i][j].obtenerRobot()
									.obtenerTurno() + ":");
					RobotAux.encolar(LaberintoMatriz[i][j].obtenerRobot());
					Stack<Llave> LlaveAux = new Stack<Llave>();

					while (!LaberintoMatriz[i][j].obtenerRobot().Llavero
							.isEmpty()) {
						bw.write(LaberintoMatriz[i][j].obtenerRobot().Llavero
								.getTop().toString());
						LlaveAux.addData(LaberintoMatriz[i][j].obtenerRobot().Llavero
								.getTop());
						LaberintoMatriz[i][j].obtenerRobot().Llavero
								.removeData();

					}
					while (!LlaveAux.isEmpty()) {
						LaberintoMatriz[i][j].obtenerRobot().Llavero
								.addData(LlaveAux.getTop());
						LlaveAux.removeData();
					}
					bw.write(")");
					bw.newLine();
					LaberintoMatriz[i][j].desencolarRobot();

				}
				while (!RobotAux.estaVacia()) {
					LaberintoMatriz[i][j].encolarRobot(RobotAux.primero());
					RobotAux.desencolar();
				}
			}
		}
	}

	/**
	 * Escribe en el fichero las Salas del laberinto indicando las llaves que
	 * tienen.
	 * 
	 * @param b
	 * 
	 *            .Buffer de escritura.
	 * 
	 * @throws IOException
	 */
	private void escribirSalas(FileWriter Salida, BufferedWriter bw) {
		try {
			for (int i = 0; i < LaberintoMatriz.length; i++) {
				for (int j = 0; j < LaberintoMatriz[i].length; j++) {
					if (LaberintoMatriz[i][j].contieneLlaves()) {
						bw.write("(" + "sala:"
								+ LaberintoMatriz[i][j].obtenerId() + ": ");
						for (int x = 0; x < LaberintoMatriz[i][j].llavesSala
								.size(); x++) {
							bw.write(LaberintoMatriz[i][j].llavesSala.get(x)
									.toString());
						}
						bw.write(")");
						bw.newLine();
					}
				}
			}
		} catch (IOException valor) {
			System.err.println("Excepcion capturada al procesar fichero: "
					+ valor.getMessage());
		}
	}

	/**
	 * Pasa los datos de un arbol a un fichero
	 * 
	 * @param Salida
	 * 
	 *            . Salida .Fichero en el que se escribe las llaves almacenadas
	 *            en el arbol(Combinacion,probadas).
	 * 
	 * @param bw
	 *            .Buffer de escritura.
	 * 
	 * @param arbol
	 * 
	 *            .Arbol en el que estan almacenadas las llaves.
	 * */
	private void ArbolAFichero(Arbol<Llave> arbol, FileWriter Salida,
			BufferedWriter bw) {
		Arbol<Llave> aux = null;
		try {
			if (!arbol.vacio()) {
				if ((aux = arbol.getHijoIzq()) != null) {
					ArbolAFichero(aux, Salida, bw);
				}
				bw.write(arbol.getRaiz().toString());

				if ((aux = arbol.getHijoDer()) != null) {
					ArbolAFichero(aux, Salida, bw);
				}
			}

		} catch (IOException valor) {
			System.err.println("Excepci�n capturada al procesar fichero: "
					+ valor.getMessage());
		}
	}

	/**
	 * Escribe en el fichero las llaves que tienen en su llavero
	 * 
	 * @param FileWriter
	 *            Salida .Fichero en el que se escriben las llaves de cada
	 *            robot.
	 * @param BufferedWriter
	 *            bw .Buffer de escritura.
	 * @param crobot
	 *            .Cola en la que estan almacenados los robots.
	 * */
	private void EscribirLlaves(FileWriter Salida, BufferedWriter bw,
			Cola<Robot> crobot) {
		try {
			Stack<Llave> LlaveAux = new Stack<Llave>();
			while (!crobot.primero().Llavero.isEmpty()) {
				bw.write(crobot.primero().Llavero.getTop().toString());
				LlaveAux.addData(crobot.primero().Llavero.getTop());
				crobot.primero().Llavero.removeData();
			}

			bw.write(")");

			while (!LlaveAux.isEmpty()) {
				crobot.primero().Llavero.addData(LlaveAux.getTop());
				LlaveAux.removeData();

			}

		} catch (IOException valor) {
			System.err.println("Excepci�n capturada al procesar fichero: "
					+ valor.getMessage());
		}
	}

	/**
	 * Escribe en el fichero lo que ha ocurrido en cada turno
	 * 
	 * @param FileWriter
	 *            Salida .Fichero en el que se escribe el proceso del juego
	 * @param BufferedWriter
	 *            bw .Buffer de escritura.
	 * */
	private void FicheroTurnos(FileWriter Salida, BufferedWriter bw) {

		try {
			boolean ganadores = false;
			bw.write("(turno:" + turno + ")");
			bw.newLine();

			bw.write("(Laberinto:" + PSalida.idPuertaSalida + ")");
			bw.newLine();

			if (PSalida.condicionApertura) {
				bw.write("(puerta:" + "Abierta" + ":" + PSalida.alturaPuerta
						+ ":");
			} else {
				bw.write("(puerta:" + "cerrada" + ":" + ""
						+ PSalida.alturaPuerta + ":");

			}
			/** ESCRIBE AQUI LA COMBINACION */
			ArbolAFichero(PSalida.combinacion, Salida, bw);
			bw.write(":");
			/** ESCRIBE AQUI LAS PROBADAS */
			ArbolAFichero(PSalida.llavesProbadas, Salida, bw);
			bw.write(")");
			/** ESCRIBE EL LABERINTO */
			bw.newLine();
			escribirLaberinto(bw);
			/** ESCRIBE LAS SALAS CON SUS LLAVES */
			escribirSalas(Salida, bw);
			/** ESCRIBE LOS ROBOTS CON SUS RUTAS */
			escribirLlavesRobot(bw);
			/** ESCRIBE LOS GANADORES */
			if (this.PSalida.condicionApertura && !ganadores) {
				ganadores = true;
				bw.write("(Robots Ganadores)");

				while (!colaRobotsGanadores.estaVacia()) {

					bw.write('\n' + "("
							+ colaRobotsGanadores.primero().obtenerNombre()
							+ ":ganador:"
							+ colaRobotsGanadores.primero().obtenerId() + ":"
							+ "1111:"
							+ colaRobotsGanadores.primero().turnoRobot + ":");
					EscribirLlaves(Salida, bw, colaRobotsGanadores);

					colaRobotsGanadores.desencolar();
				}

			}

		} catch (IOException valor) {
			System.err.println("Excepci�n capturada al procesar fichero: "
					+ valor.getMessage());
		}
	}

	/**
	 * Escribe en el fichero el laberinto con los Robots.
	 * 
	 * @param bw
	 * 
	 *            .Buffer de escritura.
	 * 
	 * @throws IOException
	 */
	private void escribirLaberinto(BufferedWriter bw) throws IOException {
		for (int i = 0; i < LaberintoMatriz.length; i++) {
			for (int j = 0; j < LaberintoMatriz[i].length; j++) {
				if (i == 0 && j == 0) {
					int m = 0;
					bw.write(" ");

					while (m < dimX) {
						bw.write("_ ");
						m++;
					}
					bw.write('\n');

				}
				if (LaberintoMatriz[i][j].obtenerId() % dimX == 0) {
					bw.write("|");

				}
				if (LaberintoMatriz[i][j].salaOcupada()) {
					if (LaberintoMatriz[i][j].colaRobots.size() > 1) {
						int numero = LaberintoMatriz[i][j].colaRobots.size();

						String cadena = String.valueOf(numero);
						cadena = Integer.toString(numero);
						bw.write(cadena);
					} else {
						bw.write(LaberintoMatriz[i][j].obtenerRobot()
								.obtenerId());

					}
				} else {
					if (LaberintoMatriz[i][j].obtenerId() > (dimX * dimY - dimX - 1)) {
						bw.write("_");

					}
					if (LaberintoMatriz[i][j].obtenerId() < (dimX * dimY)
							- dimX) {
						if (grafoRobots.adyacente(
								LaberintoMatriz[i][j].obtenerId(),
								LaberintoMatriz[i][j].obtenerId() + dimX))
							bw.write(" ");

						else
							bw.write("_");
					}
				}
				if ((LaberintoMatriz[i][j].obtenerId() + 1) % dimX != 0) {
					if (grafoRobots.adyacente(
							LaberintoMatriz[i][j].obtenerId(),
							LaberintoMatriz[i][j].obtenerId() + 1))
						bw.write(" ");

					else
						bw.write("|");

				}

				if ((LaberintoMatriz[i][j].obtenerId() + 1) % dimX == 0) {
					bw.write("|" + '\n');

				}

			}
		}

	}

	private void escribirLaberintoSinRobot(BufferedWriter bw)
			throws IOException {
		for (int i = 0; i < LaberintoMatriz.length; i++) {
			for (int j = 0; j < LaberintoMatriz[i].length; j++) {
				if (i == 0 && j == 0) {
					int m = 0;
					bw.write(" ");

					while (m < dimX) {
						bw.write("_ ");
						m++;
					}
					bw.write('\n');

				}
				if (LaberintoMatriz[i][j].obtenerId() % dimX == 0) {
					bw.write("|");

				}

				if (LaberintoMatriz[i][j].obtenerId() > (dimX * dimY - dimX - 1)) {
					bw.write("_");

				}
				if (LaberintoMatriz[i][j].obtenerId() < (dimX * dimY) - dimX) {
					if (grafoRobots.adyacente(
							LaberintoMatriz[i][j].obtenerId(),
							LaberintoMatriz[i][j].obtenerId() + dimX))
						bw.write(" ");

					else
						bw.write("_");
				}

				if ((LaberintoMatriz[i][j].obtenerId() + 1) % dimX != 0) {
					if (grafoRobots.adyacente(
							LaberintoMatriz[i][j].obtenerId(),
							LaberintoMatriz[i][j].obtenerId() + 1))
						bw.write(" ");

					else
						bw.write("|");

				}

				if ((LaberintoMatriz[i][j].obtenerId() + 1) % dimX == 0) {
					bw.write("|" + '\n');

				}

			}
		}

	}

	/** Pinta el proceso de la simulacion */
	private void PintarTurnos() {

		boolean ganadores = false;
		System.out.println();
		System.out.print('\n' + "(turno:" + turno + ")");

		System.out.print('\n' + "(Laberinto:" + PSalida.idPuertaSalida + ")");

		if (PSalida.condicionApertura) {
			System.out.print('\n' + "(puerta:" + "Abierta" + ":"
					+ PSalida.alturaPuerta + ":");
		} else {
			System.out.print("(puerta:" + "cerrada" + ":" + ""
					+ PSalida.alturaPuerta + ":");

		}
		/** ESCRIBE AQUI LA COMBINACION */
		PSalida.combinacion.inOrden();
		System.out.print(":");
		/** ESCRIBE AQUI LAS PROBADAS */
		PSalida.llavesProbadas.inOrden();
		System.out.print(")");

		/** ESCRIBE EL LABERINTO */
		System.out.print('\n');

		for (int i = 0; i < LaberintoMatriz.length; i++) {
			for (int j = 0; j < LaberintoMatriz[i].length; j++) {
				if (i == 0 && j == 0) {
					int m = 0;
					System.out.print(" ");

					while (m < dimX) {
						System.out.print("_ ");
						m++;
					}
					System.out.print('\n');

				}
				if (LaberintoMatriz[i][j].obtenerId() % dimX == 0) {
					System.out.print("|");

				}
				if (LaberintoMatriz[i][j].salaOcupada()) {
					if (LaberintoMatriz[i][j].colaRobots.size() > 1) {
						int numero = LaberintoMatriz[i][j].colaRobots.size();

						String cadena = String.valueOf(numero);
						cadena = Integer.toString(numero);
						System.out.print(cadena);
					} else {
						System.out.print(LaberintoMatriz[i][j].obtenerRobot()
								.obtenerId());

					}
				} else {
					if (LaberintoMatriz[i][j].obtenerId() > (dimX * dimY - dimX - 1)) {
						System.out.print("_");

					}
					if (LaberintoMatriz[i][j].obtenerId() < (dimX * dimY)
							- dimX) {
						if (grafoRobots.adyacente(
								LaberintoMatriz[i][j].obtenerId(),
								LaberintoMatriz[i][j].obtenerId() + dimX))
							System.out.print(" ");

						else
							System.out.print("_");
					}
				}
				if ((LaberintoMatriz[i][j].obtenerId() + 1) % dimX != 0) {
					if (grafoRobots.adyacente(
							LaberintoMatriz[i][j].obtenerId(),
							LaberintoMatriz[i][j].obtenerId() + 1))
						System.out.print(" ");

					else
						System.out.print("|");

				}

				if ((LaberintoMatriz[i][j].obtenerId() + 1) % dimX == 0) {
					System.out.print("|" + '\n');

				}

			}
		}
		/** ESCRIBE LAS SALAS CON SUS LLAVES */
		situacionSalas();
		/** ESCRIBE LOS ROBOTS CON SUS RUTAS */
		situacionRobots();
		/** ESCRIBE LOS GANADORES */
		if (this.PSalida.condicionApertura && !ganadores) {
			ganadores = true;
			System.out.print('\n' + "(Robots Ganadores)");

			if (!colaRobotsGanadores.estaVacia()) {

				System.out.print('\n' + "("
						+ colaRobotsGanadores.primero().obtenerNombre()
						+ ":ganador:"
						+ colaRobotsGanadores.primero().obtenerId() + ":"
						+ "1111:" + colaRobotsGanadores.primero().turnoRobot
						+ ":");
				Stack<Llave> LlaveAux = new Stack<Llave>();
				while (!colaRobotsGanadores.primero().Llavero.isEmpty()) {
					System.out.print(colaRobotsGanadores.primero().Llavero
							.getTop().toString());
					LlaveAux.addData(colaRobotsGanadores.primero().Llavero
							.getTop());
					colaRobotsGanadores.primero().Llavero.removeData();
				}

				System.out.print(")");

				while (!LlaveAux.isEmpty()) {
					colaRobotsGanadores.primero().Llavero.addData(LlaveAux
							.getTop());
					LlaveAux.removeData();

				}

			}

		}

	}

	public static void main(String args[]) {

		Cargador cargador = new Cargador();
		try {
			/**
			 * Metodo que procesa linea a linea el fichero de entrada inicio.txt
			 */
			FicheroCarga.procesarFichero("inicio.txt", cargador);
			Laberinto lab = cargador.getLaberinto();

			if (cargador.laberintoCreado) {
				FileWriter Salida = new FileWriter("registro.log");
				BufferedWriter sa = new BufferedWriter(Salida);

				lab.FicheroSalida(Salida, sa);
				/**
				 * Ejecución de la simulación El método procesar se ejecutará
				 * turno a turno, recorriendo el laberinto desde la sala de
				 * entrada hasta la de salida y los robots almacenados en cada
				 * sala
				 */
				lab.procesar(Salida, sa);
			}

		} catch (IOException valor) {
			System.err.println("Excepcion capturada al procesar fichero: "
					+ valor.getMessage());
		}

	}
}