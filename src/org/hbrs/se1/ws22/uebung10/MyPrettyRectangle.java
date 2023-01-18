package org.hbrs.se1.ws22.uebung10;

public class MyPrettyRectangle {

    private MyPoint topRight, bottomLeft;



    //Konstruktor
    public MyPrettyRectangle(double x1, double y1, double x2, double y2) {
        bottomLeft = new MyPoint(x1,y1);

        topRight = new MyPoint(x2,y2);
    }

    public MyPrettyRectangle(MyPoint bottomLeft, MyPoint topRight){
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public MyPoint getCenter() {
        return new MyPoint((topRight.getX() + bottomLeft.getX()) / 2, (topRight.getY() + bottomLeft.getY()) / 2);
    }


    public boolean contains(MyPrettyRectangle middle) {
        return  middle.getBottomLeft().getX() >= bottomLeft.getX() & middle.getBottomLeft().getY() >= bottomLeft.getY() & middle.getTopRight().getX() <= topRight.getX() & middle.getTopRight().getY() <= topRight.getY();

    }


    public double getPerimeter(){
        return ((topRight.getY() - bottomLeft.getY()) * 2+topRight.getX() - bottomLeft.getX()) * 2;
    }


    public double getArea(){
        return (topRight.getX() - bottomLeft.getX()) * (topRight.getY() - bottomLeft.getY());
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Equals Methode
    @Override
    public boolean equals(Object object) {
        if(object instanceof MyPrettyRectangle rectangle){

            return rectangle.topRight.equals(this.topRight) & rectangle.bottomLeft.equals(this.bottomLeft);
        }

        else {
            throw new IllegalArgumentException();

        }
    }

    //Getter & Setter
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public MyPoint getTopRight() {
        return topRight;
    }


    public MyPoint getBottomLeft() {
        return bottomLeft;
    }
}
