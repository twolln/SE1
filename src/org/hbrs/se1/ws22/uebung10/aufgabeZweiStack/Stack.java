package org.hbrs.se1.ws22.uebung10.aufgabeZweiStack;
import org.hbrs.se1.ws22.uebung10.aufgabeZweiStack.datenstruktur.EVL;

import java.util.Iterator;

public class Stack<T> implements Iterable<T> {

    EVL<T> einfachverketteteliste;

    //Konstruktor
    public Stack() {
        this.einfachverketteteliste = new EVL<>();
    }


    //1. Methode: Elemente werden bei einer Warteschlange an die erste Stelle gepusht
    public void push(T element) {
        einfachverketteteliste.addLast(element);
    }


    //2. Methode: Pop bedeutet beim Lifo Kellespeicher das letzte Element zu entfernen
    public T pop() {
        if (einfachverketteteliste.size() == 0) {
            throw new ArrayIndexOutOfBoundsException("Der Stapel ist leer");
        }
        return einfachverketteteliste.removeLast();
    }


    //3. Methode: Liefert aktuell vorderstes Element in der Liste ohne es zu entfernen
    public T front() {
        return einfachverketteteliste.getFirst();
    }


    //4. Methode: Ist Warteschlange leer?
    public boolean isEmpty() {
        return (einfachverketteteliste.size() == 0);
    }


    //5. Methode: Wie viele Elemente hat die Warteschlange?
    public int size() {
        return einfachverketteteliste.size();
    }


    //6. Methode: Kapazität... Weil eine EVL nicht wie ein Array eine Kapazität hat... höchste Integer zahl
    public int capacity() {
        return Integer.MAX_VALUE;
    }


    @Override
    public String toString() {
        return einfachverketteteliste.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return einfachverketteteliste.iterator();
    }


    public static void main(String[] args) {
        Stack<Integer> lifo = new Stack<>();
        lifo.push(1);
        lifo.push(2);
        lifo.push(3); // (1,2,3)
        lifo.pop(); //also danach nur noch (1,2) enthalten
        System.out.println(lifo);

        System.out.println("[For-Each Schleife]");
        for (Integer element : lifo) {
            System.out.println(element);
        }

        System.out.println();
        System.out.println(lifo.size());
    }

}
