/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    //private final Comparator<Point> bySlope = new slope_Order();
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point


   /** private class slope_Order implements Comparator<Point>{
        @Override
        public int compare(Point p1, Point p2) {
            double slopeThisToP1 = Point.this.slopeTo(p1);//((double)(Point.this.y - p1.y))/(Point.this.x - p1.x);
            double slopeThisToP2 = Point.this.slopeTo(p2);

            if(slopeThisToP1 < slopeThisToP2)return -1;
            if(slopeThisToP1 > slopeThisToP2)return 1;
            return 0;
        }
    }
    **/


    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {

        assertNotNull(that);
        Point largerPoint = this.compareTo(that) <0 ? that : this;
        Point smallerPoint = largerPoint == that ? this : that;


        /* YOUR CODE HERE */
        if(this.compareTo(that) == 0){return Double.NEGATIVE_INFINITY;}
        try{return ((double)(largerPoint.y - smallerPoint.y))/(largerPoint.x - smallerPoint.x);}
        catch(ArithmeticException e){
            return Double.POSITIVE_INFINITY;
        }

    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if(this.y < that.y || (this.y == that.y && this.x < that.x)){ return -1;}
        if(this.x == that.x && this.y == that.y){return 0;}
        else{return 1;}

    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        Comparator<Point> c = new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {

                double slopeThisToP1 = Point.this.slopeTo(p1);
                double slopeThisToP2 = Point.this.slopeTo(p2);

                if(slopeThisToP1 < slopeThisToP2)return -1;
                if(slopeThisToP1 > slopeThisToP2)return 1;
                return 0;

            }
        };
        return c;

    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private void assertNotNull(Point point){if(point == null) throw new NullPointerException("Argument cant be null");}

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point p1 = new Point(1,1);
        Point p2 = new Point(2,2);
        Point p3 = new Point(3,3);
        Comparator<Point> c = p2.slopeOrder();
        System.out.println(c.compare(p1,p3));
        System.out.println(c.compare(p3,p1));




    }
}