package org.hbrs.se1.ws22.uebung10.aufgabeZweiStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StackTest {

    private Stack<Integer> stapel;


    @BeforeEach
    void setUp() {
        stapel = new Stack<>();
    }
    //========================================================================================================
    //Test1: push() Methode testen (toString Methode bereits implementiert)
    //========================================================================================================
    @Test
    void pushTest() {
        stapel.push(1);
        stapel.push(2);
        stapel.push(3);
        stapel.push(4);

        assertEquals("(1, 2, 3, 4)", stapel.toString());


    }


    //========================================================================================================
    //Test2: pop() Methode testen (toString Methode bereits implementiert)
    //========================================================================================================
    @Test
    void popTest() {
        //pop() Negativ Test bei empty stack
        assertThrows(IndexOutOfBoundsException.class, () -> stapel.pop());

        //pop() Test bei filled stack
        stapel.push(1000);
        stapel.push(2000);
        stapel.push(3000);
        stapel.push(4000);
        stapel.pop();
        assertEquals("(1000, 2000, 3000)", stapel.toString());



    }

    //========================================================================================================
    //Test3: size() Methode testen
    //========================================================================================================
    @Test
    void sizeTest() {
        //size() Test bei empty stack
        assertEquals(0, stapel.size());

        //size() Test bei filled stack
        stapel.push(100);
        stapel.push(200);
        stapel.push(300);
        stapel.push(400);
        assertEquals(4, stapel.size());


        //size() Test bei filled stack nachdem oberstes Element entfernt wurde
        stapel.pop();
        assertEquals(3, stapel.size());
    }

}
