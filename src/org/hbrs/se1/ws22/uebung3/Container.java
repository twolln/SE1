package org.hbrs.se1.ws22.uebung3;

import org.hbrs.se1.ws22.uebung3.exception.ContainerException;
import org.hbrs.se1.ws22.uebung3.exception.PersistenceException;
import org.hbrs.se1.ws22.uebung3.persistence.PersistenceStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Container {

    //ArrayList als Datenstruktur
    private ArrayList<Member> arrayList = new ArrayList<>();


    //Objekt von der Klasse PersistenceStrategy erstellen
    private PersistenceStrategy<Member> persistenceStrategy;


    //========================================================================================================
    //Privater Konstruktor zur Realisierung des Singleton Entwurfsmusters
    private Container() {
    }


    //Privates statisches Objekt der Klasse Container erzeugen zur Realisierung des Singleton Entwurfsmusters
    private static final Container container = new Container();


    //Private statische getInstance Methode zur Realisierung des Singleton Entwurfsmusters
    public static Container getInstance() {
        return container;
    }
    //========================================================================================================


    //Methode zum Hinzufügen eines Member Objektes
    public void addMember(Member member) throws ContainerException {

        if (member == null) {
            throw new IllegalArgumentException();
        }

        for (Member iMember : arrayList) {
            if (iMember.toString().equals(member.toString())) {
                throw new ContainerException("Das Member-Objekt mit der ID " + member.getID() + " ist bereits vorhanden!");
            }
        }
        arrayList.add(member);
    }


    //Methode zum Entfernen eines Member Objektes
    public String deleteMember(Integer id) {
        for (int i = 0; i < arrayList.size(); i++) {
            //System.out.println(arrayList.get(i).toString());
            if (Objects.equals(arrayList.get(i).getID(), id)){
                arrayList.remove(i);
                return "Das Member-Objekt mit der ID " + id + " wurde gelöscht!";
            }
        }
        return "Das Member-Objekt mit der ID " + id + " ist nicht im Container enthalten!";
    }


    //Methode gibt Anzahl der gespeicherten Member Objekte zurück
    public int size() {

        return arrayList.size();
    }


    //========================================================================================================

    //Methode speichert die aktuell in einem Container-Objekt hinzugefügten Member-Objekte PERSISTENT auf einem Datenspeicher
    public void store() throws PersistenceException {

        //Wenn keine Persistenz-Strategie festgelegt ist => PersistenceException
        if(getPersistenceStrategy() == null) {
            throw new PersistenceException(PersistenceException.ExceptionType.NoStrategyIsSet, "Keine Persistenz-Strategie ist festgelegt");
        }

        persistenceStrategy.save(arrayList);
    }


    //Methode ermöglicht das Laden von Member-Objekten z.B. nach einem Neustart
    public void load() throws PersistenceException {
        arrayList = (ArrayList<Member>) persistenceStrategy.load();
    }


    public void setPersistenceStrategy(PersistenceStrategy<Member> persistenceStrategy) {
        this.persistenceStrategy = persistenceStrategy;
    }


    public PersistenceStrategy<Member> getPersistenceStrategy() {
        return persistenceStrategy;
    }


    //Methode gibt die aktuelle Liste von Member-Objekten zurück
    public List<Member> getCurrentList(){
        return arrayList;
    }

    //========================================================================================================



}
