package main;

/**
 * Clase creada para ser usada en la utilidad cargador
 * estudiada previamente en sesi�n práctica "Excepciones"
 * 
 * @version 1.0 -  02/11/2011 
 * @author Profesores DP
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FicheroCarga {
	/**
	 * atributo de la clase que indica el numero máximo de campos que se pueden
	 * leer
	 */
	public static final int MAXCAMPOS = 10;

	/**
	 * buffer para almacenar el flujo de entrada
	 */
	private static BufferedReader bufferIn;

	/**
	 * Metodo para procesar el fichero. Sin excepciones
	 * 
	 * @return codigoError con el error que se ha producido
	 * @throws Exception. Puede
	 *             lanzar una excepci�n en la apertura del buffer de lectura
	 */
	public static void procesarFichero(String nombreFichero, Cargador cargador)
			throws FileNotFoundException, IOException {
		// **String vCampos[]=new String[MAXCAMPOS];
		List<String> vCampos = new ArrayList<String>();
		String S = new String();
		int numCampos = 0;
		bufferIn = new BufferedReader(new FileReader(nombreFichero));// CREACION
																		// DEL
																		// FILTRO
																		// ASOCIADO
																		// AL
																		// FLUJO
																		// DE
																		// DATOS.
		while ((S = bufferIn.readLine()) != null) {

			if (!S.startsWith("--")) {
				vCampos.clear();
				numCampos = trocearLinea(S, vCampos);
				cargador.crear(vCampos.get(0), numCampos, vCampos);
			}
		}
		bufferIn.close(); // cerramos el filtro
	}

	/**
	 * Metodo para trocear cada l�nea y separarla por campos
	 * 
	 * @param S
	 *            cadena con la l�nea completa le�da
	 * @param vCampos
	 *            . Array de String que contiene en cada posici�n un campo
	 *            distinto
	 * @return numCampos. N�mero campos encontrados
	 */
	private static int trocearLinea(String S, List<String> vCampos) {
		boolean eol = false;
		int pos = 0, posini = 0, numCampos = 0;

		while (!eol) {
			pos = S.indexOf("#");
			if (pos != -1) {
				vCampos.add(new String(S.substring(posini, pos)));
				// **vCampos[numCampos] = new String(S.substring(posini,pos));
				S = S.substring(pos + 1, S.length());
				numCampos++;
			} else
				eol = true;
		}
		return numCampos;
	}

}
