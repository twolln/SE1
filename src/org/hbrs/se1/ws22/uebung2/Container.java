package org.hbrs.se1.ws22.uebung2;

import java.util.ArrayList;
import java.util.Objects;

public class Container {

    //ArrayList als Datenstruktur
    private final ArrayList<Member> arrayList;


    //Konstruktor
    public Container() {
        this.arrayList = new ArrayList<>();
    }


    //Methode zum Hinzufügen eines Member Objektes
    public void addMember(Member member) throws ContainerException {

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


    //Methode um alle abgespeicherten Member Objekte zu printen
    public void dump() {
        for (Member iMember : arrayList) {
            System.out.println(iMember.toString());
        }
    }


    //Methode gibt Anzahl der gespeicherten Member Objekte zurück
    public int size() {

        return arrayList.size();
    }

    /*
    ///////////////////////////////////////////////MAIN-METHODE//////////////////////////////////////////////

    public static void main(String[] args) throws ContainerException {
        Member m1 = new ConcreteMember(12112);
        Member m2 = new ConcreteMember(232323);

        Container memberSpeicher = new Container();
        memberSpeicher.addMember(m1);
        memberSpeicher.addMember(m2);


        System.out.println(memberSpeicher.deleteMember(12));
        memberSpeicher.dump();

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    */
}
