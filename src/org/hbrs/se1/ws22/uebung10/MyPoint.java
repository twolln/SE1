package org.hbrs.se1.ws22.uebung10;

import java.awt.*;

public class MyPoint implements Comparable<MyPoint>{

    double x,y;


    //Konstruktor
    public MyPoint(double x, double y) {
        this.x = x;
        this.y =y;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if((object instanceof MyPoint test)) {
            return getX() == test.getX() & getY() == test.getY();
        }
        else {

            return false;
        }
    }

    @Override
    public int compareTo(MyPoint myPoint) {
        if(getX() == myPoint.getX() & getY() == myPoint.getY()){
            return 0;
        }
        else if(getX() <= myPoint.getX() & getY() <= myPoint.getY()){
            return -1;

        }
        else {
            return 1;
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Getter
    public double getX() {
        return x;

    }

    public double getY() {
        return y;
    }
}
