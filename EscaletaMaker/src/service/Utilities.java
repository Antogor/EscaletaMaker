package service;

public class Utilities {

	/*
	 * Metotodo para comprobar si es o no un numero
	 */
	public static boolean isNumeric(String n) {
		try {
			Integer.parseInt(n);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
}
