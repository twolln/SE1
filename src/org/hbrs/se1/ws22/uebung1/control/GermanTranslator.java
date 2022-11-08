package org.hbrs.se1.ws22.uebung1.control;

public class GermanTranslator implements Translator {

	public String date = "Okt/2022"; // Default-Wert

	/**
	 * Methode zur Übersetzung einer Zahl in eine String-Repraesentation
	 */
	public String translateNumber( int number ) {
		// [ihr Source Code aus Übung 1-2]
		String[] sArray = {"eins","zwei","drei","vier","fünf","sechs","sieben","acht","neun","zehn"};
		try { //Versuche im Array einen Eintrag am entsprechenden index-1 zu finden
			return sArray[number-1];
		}
		catch ( ArrayIndexOutOfBoundsException e ) { //Wenn nicht vorhanden... => Exception abfangen und neue IllegalArgumentException werfen
			throw new IllegalArgumentException("Übersetzung der Zahl " + number + " nicht möglich (" + version + ")" );
		}
	}

	/**
	 * Objektmethode der Klasse GermanTranslator zur Ausgabe einer Info.
	 */
	public void printInfo(){
		System.out.println( "GermanTranslator v1.9, erzeugt am " + this.date );
	}

	/**
	 * Setzen des Datums, wann der Uebersetzer erzeugt wurde (Format: Monat/Jahr (Beispiel: Okt/2022))
	 * Das Datum sollte system-intern durch eine Control-Klasse gesetzt werden und nicht von externen View-Klassen
	 */
	public void setDate( String date ) {
		this.date = date;
	}

}
