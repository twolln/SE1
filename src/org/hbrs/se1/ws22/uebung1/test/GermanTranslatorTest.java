package org.hbrs.se1.ws22.uebung1.test;

import org.hbrs.se1.ws22.uebung1.control.GermanTranslator;
import org.hbrs.se1.ws22.uebung1.control.Translator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GermanTranslatorTest {

    Translator t;

    @BeforeEach
    void setUp() {
        t = new GermanTranslator();
    }



    @Test
    void translateNumber() {


        //========================================================================================================
        // Positiv: Test mit dem Eingabewert 5 (Repräsentant von pos_ÄK: [1...10])
        //========================================================================================================

        assertEquals("fünf", t.translateNumber(5));
        System.out.println("[TEST 1] Positiv Test mit dem Eingabewert 5 (Repräsentant von pos_ÄK: [1...10])");
        System.out.println("Erwartet: " + "fünf" );
        System.out.println("Das Ergebnis: " + t.translateNumber(5));
        System.out.println();

        //========================================================================================================
        // Negativ: Exception Test mit dem Eingabewert -5 (Repräsentant von neg_ÄK: [< 0])
        //========================================================================================================

        assertThrows(IllegalArgumentException.class, () -> t.translateNumber(-5)); //Überprüft, ob die Exception einer IllegalArgumentException entspricht

        //Um zu überprüfen, ob die Message der Exception mit der Erwarteten übereinstimmt muss die Exception zunächst in Form eines Strings abgespeichert werden
        Exception exception = assertThrows(IllegalArgumentException.class, () -> t.translateNumber(-5)); //Exception abspeichern

        String expectedMessage = "Übersetzung der Zahl -5 nicht möglich (" + t.version + ")" ;
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
        System.out.println("[TEST 2] Negativ Test mit dem Eingabewert -5 (Repräsentant von neg_ÄK: [< 0])");
        System.out.println("Erwartete Message: " + expectedMessage );
        System.out.println("Das Ergebnis: " + actualMessage );
        System.out.println();


        //========================================================================================================
        // Negativ: Exception Test mit dem Eingabewert 11 (Repräsentant von neg_ÄK: [> 10])
        //========================================================================================================

        assertThrows(IllegalArgumentException.class, () -> t.translateNumber(11)); //Überprüft, ob die Exception einer IllegalArgumentException entspricht

        //Um zu überprüfen, ob die Message der Exception mit der Erwarteten übereinstimmt muss die Exception zunächst in Form eines Strings abgespeichert werden
        exception = assertThrows(IllegalArgumentException.class, () -> t.translateNumber(11)); //Exception abspeichern

        expectedMessage = "Übersetzung der Zahl 11 nicht möglich (" + t.version + ")" ;
        actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
        System.out.println("[TEST 3] Negativ Test mit dem Eingabewert 11 (Repräsentant von neg_ÄK: [> 10])");
        System.out.println("Erwartete Message: " + expectedMessage );
        System.out.println("Das Ergebnis: " + actualMessage );
        System.out.println();


        //========================================================================================================
        // Negativ: Exception Test mit dem Eingabewert 0 (separater Wert)
        //========================================================================================================

        assertThrows(IllegalArgumentException.class, () -> t.translateNumber(0)); //Überprüft, ob die Exception einer IllegalArgumentException entspricht

        //Um zu überprüfen, ob die Message der Exception mit der Erwarteten übereinstimmt muss die Exception zunächst in Form eines Strings abgespeichert werden
        exception = assertThrows(IllegalArgumentException.class, () -> t.translateNumber(0)); //Exception abspeichern

        expectedMessage = "Übersetzung der Zahl 0 nicht möglich (" + t.version + ")" ;
        actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
        System.out.println("[TEST 4] Negativ Test mit dem Eingabewert 0 (separater Wert)");
        System.out.println("Erwartete Message: " + expectedMessage );
        System.out.println("Das Ergebnis: " + actualMessage );
        System.out.println();


    }


}
