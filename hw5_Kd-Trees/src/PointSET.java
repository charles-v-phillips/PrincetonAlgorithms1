import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;

public class PointSET {

    private int N;
    private SET<Point2D> points;

    public PointSET(){
        N = 0;
        points = new SET<Point2D>();
    }

    public boolean isEmpty(){return N == 0;}

    public int size(){return N;}

    public void insert(Point2D p){
        if(!points.contains(p)){points.add(p); N++;}
         }

    public boolean contains(Point2D p)  {return points.contains(p);}

    public void draw(){
        for(Point2D p : points) {
            p.draw();
            StdDraw.text(p.x(),p.y(),String.valueOf(p.x()) + " " + String.valueOf(p.y()));
        }}

    public Iterable<Point2D> range(RectHV rect){
        LinkedList<Point2D> pointsinRect = new LinkedList<>();
        for(Point2D p : points){
            if(p.x() >=rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin() && p.y() <= rect.ymax()){pointsinRect.add(p);}
        }
        return pointsinRect;}

    public Point2D nearest(Point2D p){
        double dist = Double.POSITIVE_INFINITY;
        Point2D nearest = null;
        for(Point2D q : points){
            if(squareDistance(p,q)< dist){
                dist = squareDistance(p,q);
                nearest = q;}
        }
        return nearest;

        }


    private double squareDistance(Point2D p, Point2D q){
        return ((p.x() - q.x())*(p.x() - q.x()) + (p.y() - q.y())*(p.y() - q.y()));
    }
    public static void main(String[] args){

    }
}
