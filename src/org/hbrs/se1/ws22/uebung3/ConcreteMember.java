package org.hbrs.se1.ws22.uebung3;

import java.io.Serializable;

public class ConcreteMember implements Serializable, Member {

    private final int ID; //ID als int Wert

    //Konstruktor
    public ConcreteMember(int ID) {
        this.ID = ID;
    }

    //Methode gibt ID zurück
    @Override
    public Integer getID() {
        return ID;
    }

    //TO STRING METHODE
    @Override
    public String toString() {
        return "Member (ID = "+ this.ID +")";
    }
}
