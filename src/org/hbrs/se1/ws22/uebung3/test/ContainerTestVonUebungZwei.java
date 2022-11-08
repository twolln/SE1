package org.hbrs.se1.ws22.uebung3.test;

import org.hbrs.se1.ws22.uebung3.ConcreteMember;
import org.hbrs.se1.ws22.uebung3.Container;
import org.hbrs.se1.ws22.uebung3.Member;
import org.hbrs.se1.ws22.uebung3.exception.ContainerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContainerTestVonUebungZwei {

    Container c;
    Member m1 = new ConcreteMember(100);
    Member m1Duplikat = new ConcreteMember(100);
    Member m3 = new ConcreteMember(300);

    @BeforeEach
    void setUp() {
        c = Container.getInstance();
    }

    //========================================================================================================
    //Positiv: Member Objekt hinzufügen was noch nicht vorkam
    //========================================================================================================
    @Test
    void addMember() throws ContainerException {
        c.addMember(m1);
        assertEquals(c.size(),1);
        //c.dump();
    }


    //========================================================================================================
    //Negativ: Null Member Objekt hinzufügen
    //========================================================================================================
    @Test
    void addNullMember() throws ContainerException {
        m1 = null;
        assertThrows(IllegalArgumentException.class, () -> c.addMember(m1));
    }


    //========================================================================================================
    //Negativ: Exception Test: Member Objekt hinzufügen dessen ID bereits vorhanden ist
    //========================================================================================================
    @Test
    void duplikatAddMember() throws ContainerException {
        c.addMember(m1);

        Exception exception = assertThrows(ContainerException.class, () -> c.addMember(m1Duplikat)); //Exception abspeichern

        String expectedMessage = "Das Member-Objekt mit der ID 100 ist bereits vorhanden!" ;
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);

        assertEquals(c.size(),1);
    }


    //========================================================================================================
    //Positiv: Member Objekt löschen
    //========================================================================================================
    @Test
    void deleteMember() throws ContainerException {
        c.addMember(m1);
        c.deleteMember(m1.getID());
        assertEquals(c.size(),0);
    }


    //========================================================================================================
    //Negativ: Fehlermeldung Member Objekt löschen welches nicht existiert
    //========================================================================================================
    @Test
    void deleteNotExistingMember() throws ContainerException {
        assertEquals("Das Member-Objekt mit der ID " + 100 + " ist nicht im Container enthalten!", c.deleteMember(100));
    }





    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void dump() throws ContainerException {
        c.addMember(m1);
        c.addMember(m3);
        //c.dump();
    }

    @Test
    void size() throws ContainerException {
        c.addMember(m1);
        c.addMember(m3);
        assertEquals(2, c.size());
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
}