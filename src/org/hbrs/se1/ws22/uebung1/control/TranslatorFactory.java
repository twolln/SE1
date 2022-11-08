package org.hbrs.se1.ws22.uebung1.control;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TranslatorFactory {

    //Methode erstellt ein neues Objekt der Klasse GermanTranslator
    public static Translator createGermanTranslator() {
        GermanTranslator translator = new GermanTranslator(); //neues Objekt von der Klasse GermanTranslator wird erzeugt

        //Extra: Der Variable date von dem neuen GermanTranslator Objekt wird mit Hilfe der setDate Methode das aktuelle Datum zugewiesen
        //========================================================================================================
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        Date datum = calendar.getTime();

        translator.setDate(dateFormat.format(datum));
        //========================================================================================================

        return translator;
    }

}
