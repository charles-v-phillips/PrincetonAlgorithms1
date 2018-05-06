import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    //Point[] orderBySlope;
    private LineSegment[] lineSegments;
    private int numSegments;
    private int shifts = 0;
    public BruteCollinearPoints(Point[] points){
        if(points == null) throw new IllegalArgumentException("Cant pass null to constructor");
        for(Point p : points)if(p == null) throw new IllegalArgumentException("A Point is null");
        Point[] pointsCopy = Arrays.copyOf(points,points.length);
        Arrays.sort(pointsCopy); //BE WARY YOUR SORTING HERE!!!!
        for(int i = 0; i< pointsCopy.length - 1; i++)
            if(pointsCopy[i].compareTo(pointsCopy[i+1]) == 0)
                throw new IllegalArgumentException("Duplicte Points not allowed");

        lineSegments = new LineSegment[points.length];
        numSegments = 0;



        for(int i = 0; i< points.length; i++){
            for(int j = i; j< points.length;j++){
                for(int k = j; k< points.length; k++){
                    for(int l = k; l < points.length; l++){
                        Point p1 = pointsCopy[i];
                        Point p2 = pointsCopy[j];
                        Point p3 = pointsCopy[k];
                        Point p4 = pointsCopy[l];

                        if(areCollinear(p1,p2,p3,p4)) {
                            LineSegment ls = new LineSegment(p1, p4);
                            lineSegments[numSegments++] = ls;



                        }

                    }
                }
            }
        }
    }


    private boolean areCollinear(Point p1, Point p2, Point p3, Point p4) {
        if((p1.compareTo(p2) == 0) || (p1.compareTo(p2) == 0) || (p1.compareTo(p2) == 0)){return false;}

        if ((p1.compareTo(p2) == p2.compareTo(p3)) && (p2.compareTo(p3) == p3.compareTo(p4))) {
            if((p1.slopeTo(p2) == p2.slopeTo(p3)) && (p2.slopeTo(p3) == p3.slopeTo(p4))) {
                return true;
            }
        }
        return false;
    }

    public int numberOfSegments(){return numSegments;}

    public LineSegment[] segments(){
        LineSegment[] ls = new LineSegment[numSegments];
        for(int i = 0; i<numSegments; i++){ls[i] = lineSegments[i];}
        return ls;
    }

    private void assertValidArgument(Point[] points){
        if(points == null) throw new IllegalArgumentException("Cant pass null to constructor");
        for(Point p : points)if(p == null) throw new IllegalArgumentException("A Point is null");
        Point[] pointsCopy = Arrays.copyOf(points,points.length);
        Arrays.sort(pointsCopy); //BE WARY YOUR SORTING HERE!!!!
        for(int i = 0; i< pointsCopy.length - 1; i++)
            if(pointsCopy[i].compareTo(pointsCopy[i+1]) == 0)
                throw new IllegalArgumentException("Duplicte Points not allowed");



    }

    public static void main(String[] args){
        StdDraw.setPenRadius(.02);
        In in = new In("/Users/charlesphillips/Downloads/collinear/equidistant.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
            //StdDraw.circle(5000,5000,2000);
        }
        StdDraw.show();
        StdDraw.setPenRadius(.01);
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }


}
