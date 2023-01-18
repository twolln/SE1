package org.hbrs.se1.ws22.uebung10.aufgabeZweiStack.datenstruktur;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EVL<T> implements Iterable<T>{

    ListenElement first = null; //zeiger auf das erste listenelement
    int size = 0; //Anzahl der Listenelemente


    // Innere Klasse ListenElement
    //========================================================================
    class ListenElement {
        T data; //Inhalt
        ListenElement next = null; //Zeiger auf das nächste Element

        //Konstruktor
        public ListenElement(T data) {
            this.data = data;
        }
    }
    //========================================================================


    //Methode 1: Gibt das erste ListenElement zurück
    public T getFirst() {
        return first.data;
    }

    //Methode 2: Gibt das letzte ListenElement zurück
    public T getLast() {
        if(first!=null) { //Falls die liste schon Einträge hat
            ListenElement current = first; //Wird durch die Schleife zum letzten Element
            while (current.next != null) { // Schleife läuft solange der Nachbar von tmp nicht Null ist
                current = current.next; //current wird überschrieben durch seinen eignen Nachbarn
            }
            ListenElement tmp = current; //Inhalt von ELement an der letzten Position abspeichern
            size--; //Die Größe der Liste verkleinert sich um 1
            return tmp.data;
        }
        //Falls die liste keine Einträge hat
        return null;
    }

    //Methode 3: ListenElement hinten einfügen
    public void addLast(T inhalt) {
        ListenElement z = new ListenElement(inhalt);

        if (0 == size) { //Wenn die Liste keine Einträge hat
            first = z; // ist das Element z das erste Element
        }
        else { //Falls die Liste schon Einträge hat
            ListenElement current = first; //Temp den Wert des ersten Listenelements zuordnen
            while (current.next != null) { // Schleife läuft solange der Nachbar von tmp nicht Null ist
                current = current.next; //current wird überschrieben durch seinen eignen Nachbarn
            }
            current.next = z;
        }
        size++; //Durch das einfügen eines neuen Listenelements wird die Größe der Liste logischerweise um eins erhöht
    }

    //Methode 4: ListenElement als erstes Element der Liste einfügen
    public void addFirst(T inhalt) {
        ListenElement z = new ListenElement(inhalt);
        if(first == null) { //Wenn das erste Listenelement gleich null ist
            first = z; //Einfach erstes element gleich z setzen
            size++;
        }
        else { //Wenn schon elemente in der Liste sind
            z.next = first; //der nachbar von z bekommt das erste Element
            first = z; //Das erste Element wird zu Z
            size++;
        }
    }

    //Methode 5: Entfernen des letzten Elements und den Inhalt zurückgeben
    public T removeLast() {
        if(first!=null) { //Falls die liste schon Einträge hat
            ListenElement current = first; //Wird durch die Schleife zum letzten Element
            ListenElement secondLastElement = null; //wird vorletztes Element
            if (size() == 1) {
                T tmp = first.data;
                first = null;
                size--;
                return tmp;
            }
            else {
                while (current.next != null) { // Schleife läuft solange der Nachbar von tmp nicht Null ist
                    secondLastElement = current; //secondlastElement speichert Wert von Current ab
                    current = current.next; //current wird überschrieben durch seinen eignen Nachbarn
                }
                T tmp = current.data; //Inhalt von ELement an der letzten Position abspeichern
                secondLastElement.next = null;
                size--; //Die Größe der Liste verkleinert sich um 1
                return tmp;
            }
        }

        //Falls die liste keine Einträge hat
        return null;
    }

    //Methode 6: Entfernen des ersten Elements und den Inhalt zurückgebens
    public T removeFirst() {
        if(first != null) { //Wenn das erste Element nicht null ist
            T tmp = first.data; //die Daten des ersten Elements abspeichern
            first = first.next; //Das erste Element wird mit dem Nachbarelement ausgetauscht
            size--; //Die Größe der Liste verkleinert sich um 1
            return tmp; //Inhalt des gelöschten Elements zurückgeben
        }
        return null; //Wenn first = null, null zurückgeben
    }

    //Methode 7: Die Methode prüft ob die Liste den übergebenen Referenzwert beinhaltet
    public boolean contains(T e) {
        ListenElement current = first;
        int pos = 0;
        while (current != null) { //solange current ungleich null ist
            if (e.equals(current.data)){
                System.out.println(e + " befindet sich in der Liste an Position: " + pos);
                return true;
            }
            else {
                current = current.next; //current bekommt den wert des Nachbarn
                pos++;
            }
        }
        System.out.println(e + " nicht enthalten");
        return false;
    }

    //Methode 8: Gibt die Anzahl der Listenelemente innerhalb der Liste zurück.
    public int size() {
        return size;
    }

    //Methode 9: Printe die Liste
    public void printList(){
        ListenElement currentElement = first;
        int pos = 0;
        while (currentElement != null) {
            System.out.println("[" + pos + "] " + currentElement.data);
            currentElement = currentElement.next;
            pos++;
        }

    }

    //Methode 10: Ist die Methode leer?
    public boolean isEmpty() {
        return size() == 0;
    }

    //========================================================================================================
    // ERWEITERUNG DER KLASSE: ZUSATZAUFGABEN
    //========================================================================================================
    public void zip(EVL<T> other) {
        ListenElement aElement = first;
        ListenElement bElement = other.first;
        ListenElement aNext;
        ListenElement bNext;

        if (first == null) {
            first = other.first;
        }

        //Schleife läuft solange aElement und bElement ungleich null sind
        while (aElement != null && bElement != null) {
            aNext = aElement.next;
            bNext = bElement.next;

            bElement.next = aNext;
            aElement.next = bElement;

            //append remaining elements of other list
            //Wenn aNext gleich null ist und bNext ungleich null ist
            if (aNext == null && bNext != null) {
                bElement.next = bNext;
            }
            //Wenn bnext gleich null ist und anext ungleich null ist
            if (bNext == null && aNext != null) {
                aElement.next = aNext;
            }

            //setup next iteration
            aElement = aNext;
            bElement = bNext;
        }

        size += other.size;
        other.size = 0;
        other.first = null;
    }

    //Zusatz Methode: Liefert Wert an Pos
    public T get(int pos) {

        if(first!=null) { //Falls die liste schon Einträge hat
            ListenElement current = first;
            for (int i = 0; i < pos; i++) {
                current = current.next; //current wird überschrieben durch seinen eignen Nachbarn
            }
            ListenElement tmp = current;
            return tmp.data;
        }
        return null;
    }

    //Zusatz Methode: Ändert den Wert an Pos zu e
    public T set(int pos, T e) {

        if(first!=null) { //Falls die liste schon Einträge hat
            ListenElement current = first;
            for (int i = 0; i < pos; i++) {
                current = current.next; //current wird überschrieben durch seinen eignen Nachbarn
            }
            current.data = e;
            ListenElement tmp = current;

            return tmp.data;
        }
        return null;
    }

    //Zusatz Methode: e wird an die position pos eingefügt. Das vorherige objekte was an der position ist + alle nachfolgenden werden nach rechts verschoben (Indizes werden jeweils um 1 erhöht)
    public void insert(int pos, T e) {
        if (pos < 0) {
            throw new IllegalArgumentException();
        }
        ListenElement element = new ListenElement(e);
        if (size() < pos) {
            throw new IllegalArgumentException();
        } else if (pos == 0) {
            addFirst(e);
            return;
        } else if (pos == size()) {
            addLast(e);
            return;
        }
        ListenElement tmp = first;
        for (int i = 1; i < pos; i++) {
            tmp = tmp.next;
        }
        element.next = tmp.next;
        tmp.next = element;
    }

    //Zusatz Methode: Löscht wert an Pos
    public T remove(int pos) {

        if(pos == 0) { //Wenn der eingegebe Pos Wert = 0 ist
            removeFirst(); //Erstes Element löschen
        } else if (pos == size()) { //Wenn der eingebene pos Wert == size
            removeLast(); //Letztes Element löschen
        } else {
            ListenElement current = first;
            for (int i = 1; i < pos; i++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        return get(pos);
    }

    //========================================================================================================

    //TO STRING METHODE
    @Override
    public String toString() {
        String s = "(";
        ListenElement tmp = first;
        while (tmp != null) {
            if (tmp != first) {
                s = s + ", ";
            }
            s = s + tmp.data;
            tmp = tmp.next;
        }
        return s + ")";
    }

    @Override
    public Iterator<T> iterator() {
        return new EVLIterator();
    }

    // Innere Klasse: EVLIterator
    //========================================================================================================
    public class EVLIterator implements Iterator<T> { //MyIterator braucht kein T weil es von äußere Klasse bekommt

        int i;

        //Konstruktor: setzt i auf 0
        public EVLIterator() {
            i = 0;
        }


        //Methode liefert einen Wahrheitswert ob es ein nächstes Element überhaupt gibt
        @Override
        public boolean hasNext(){
            return i<size();  //ist der i Wert kleiner als die Länge des Arrays
        }

        private ListenElement tmpFueNextMethode = first;
        //Methode liefert das nächste Element und rückt eins weiter
        @Override
        public T next() {
            if(!hasNext()) { //Wenn es kein nächstes Element mehr gibt Exception werfen
                throw new NoSuchElementException();
            }
            else {
                if (i == 0) {
                    i++;
                    return first.data;
                }
                else {
                    tmpFueNextMethode = tmpFueNextMethode.next;
                    i++;
                    return tmpFueNextMethode.data; //Wenn es ein nächstes Element gibt geben wir inhalt von a an Stelle i zurück + Erhöhen danach den Wert von i um 1
                }
            }
        }

    }
//========================================================================================================

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        EVL<Integer> bspEVL = new EVL<>();
        bspEVL.addFirst(1);
        bspEVL.addLast(2);
        bspEVL.addLast(3);

        System.out.println("[For-Each Schleife]");
        for (Integer element : bspEVL) {
            System.out.println(element);
        }

        System.out.println("[For-Each Schleife]");
        for (Integer element : bspEVL) {
            System.out.println(element);
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

}
