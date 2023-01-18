package org.hbrs.se1.ws22.uebung10;

public class BoundingBoxFactory {
    public static MyPrettyRectangle createBoundingBox(MyPrettyRectangle[] rectangleArray) {
        if (rectangleArray != null) {
            if (rectangleArray.length > 0) {

                MyPoint highRight = rectangleArray[0].getTopRight();

                MyPoint lowLeft = rectangleArray[0].getBottomLeft();


                //========================================================================================================
                for (MyPrettyRectangle myPrettyRectangle : rectangleArray) {
                    if (myPrettyRectangle.getTopRight().compareTo(highRight) > 0) {
                        highRight = myPrettyRectangle.getTopRight();
                    }
                    if (myPrettyRectangle.getBottomLeft().compareTo(lowLeft) < 0) {
                        lowLeft = myPrettyRectangle.getBottomLeft();
                    }
                }
                //========================================================================================================


                return new MyPrettyRectangle(lowLeft, highRight);
            }

            else {
                return new MyPrettyRectangle(0, 0, 0, 0);
            }

        }
        throw new IllegalArgumentException();
    }
}

