package org.hbrs.se1.ws22.uebung4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeConcrete implements Serializable, Employee {

    private String vorname;
    private String name;
    private Integer pid;
    private String abteilung;

    private List<String> expertisen = new ArrayList<>();

    private String rolle;


    //Konstruktor
    public EmployeeConcrete(int ID) {
        this.pid = ID;
    }


    public String getAbteilung() {
        return abteilung;
    }

    public void setAbteilung(String abteilung) {
        this.abteilung = abteilung;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public String getExpertisen() {
        String ret = "Keine Expertisen vorhanden";
        if (expertisen.size() != 0) {
            ret = "";
            for(String s: expertisen) {
                if (Objects.equals(s, expertisen.get(0))) {
                    ret = ret + s;
                }
                else {
                    ret = ret + ", " + s;
                }
            }
        }
        return ret;
    }

    public void addExpertise (String expertise, int level) {
        this.expertisen.add(expertise + " [" + level +"]");
    }

    //TO STRING METHODE
    @Override
    public String toString() {
        return "Employee (ID = "+ this.getPid() +", Vorname = "+this.getVorname()+", Nachname = "+this.getName()+", Rolle im Unternehmen = "+this.getRolle()+", Abteilung = " + this.getAbteilung()+", Expertisen = " + this.getExpertisen()+")";
    }


}
