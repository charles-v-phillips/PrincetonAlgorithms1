import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;



import java.util.Arrays;

public class FastCollinearPoints {
    private int numSegments;
    private LineSegment [] lineSegments;


    public FastCollinearPoints(Point[] points)  {
        if(points == null) throw new IllegalArgumentException("Cant pass null to constructor");
        for(Point p : points)if(p == null) throw new IllegalArgumentException("A Point is null");
        Point[] pointsCopy = Arrays.copyOf(points,points.length);
        Arrays.sort(pointsCopy); //BE WARY YOUR SORTING HERE!!!!
        for(int i = 0; i< pointsCopy.length - 1; i++)
            if(pointsCopy[i].compareTo(pointsCopy[i+1]) == 0)
                throw new IllegalArgumentException("Duplicte Points not allowed");




        lineSegments = new LineSegment[points.length];
        numSegments = 0;

        Point[] arr = Arrays.copyOf(points,points.length);

        Arrays.sort(arr);

        for(int i = 0; i < arr.length; i++){

            Point origin = arr[i];
            Arrays.sort(arr,i,points.length,origin.slopeOrder());

            for(int j = i; j < points.length-3; j++){
                if(areCollinear(origin, arr[j+1],arr[j+2],arr[j+3])){
                        Point[] d = {origin, arr[j + 1], arr[j + 2], arr[j + 3]};
                        Arrays.sort(d);
                        LineSegment ls = new LineSegment(d[0], d[3]);
                        lineSegments[numSegments++] = ls;
                        j = j + 1;
                        //break;
                }
            }
            Arrays.sort(arr);


        }






    }
    public int numberOfSegments() {return numSegments;}

    public LineSegment[] segments() { return Arrays.copyOf(lineSegments,numSegments); }

    private boolean areCollinear(Point origin, Point p2,Point p3,Point p4){
        double slope1 = origin.slopeTo(p2);
        double slope2 = origin.slopeTo(p3);
        double slope3 = origin.slopeTo(p4);

        if((slope1 == slope2) && (slope2 == slope3)){return true;}
        return false;
    }



    public static void main(String[] args){
        StdDraw.setPenRadius(.02);
        In in = new In("/Users/charlesphillips/Downloads/collinear/input9.txt");
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }



}
