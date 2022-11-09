package org.hbrs.se1.ws22.uebung4;

public interface Employee {

    /**
     * ID ist über einen Konstruktor einer abgeleiteten Klasse
     * explizit außerhalb der Container-Klasse zu belegen
     *  -> Primärschlüssel zur Unterscheidung aller Member-Objekte
     */
    
    Integer getPid();

    public String toString();

    public void setPid(Integer pid);
}
