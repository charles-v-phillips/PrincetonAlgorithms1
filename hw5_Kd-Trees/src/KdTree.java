import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;


import java.util.Comparator;
import java.util.LinkedList;

public class KdTree {
    private enum Orientation {
        HORIZONTAL, VERTICAL
    }
    private Node root;
    private int size = 0;
    private static Node champion;
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private Orientation orientation;



        private Node(Point2D p, Orientation orientation, RectHV rect) {
            this.p = p;
            this.orientation = orientation;
            this.rect = rect;

        }

        private Node() {
        }
    }

    private Comparator<Point2D> xCompare = Point2D.X_ORDER;
    private Comparator<Point2D> yCompare = Point2D.Y_ORDER;

    public KdTree() {


    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if(p == null) throw new IllegalArgumentException();
        if (isEmpty()) {
            Node r = new Node(p, Orientation.VERTICAL, new RectHV(0, 0, 1, 1));
            root = r;
            size++;
        }
        if (!contains(p)) {
            int depth = 0;
            root = insert(p, root, root, depth);
            size++;
        }
    }

    public boolean contains(Point2D p) {
        if(p == null) throw new IllegalArgumentException();
        Node traversalNode = root;
        while (traversalNode != null) {
            if (traversalNode.p.equals(p)) {
                return true;
            }
            int cmp = (traversalNode.orientation == Orientation.VERTICAL) ? xCompare.compare(p, traversalNode.p) : yCompare.compare(p, traversalNode.p);

            if (cmp < 0) traversalNode = traversalNode.lb;
            else {
                traversalNode = traversalNode.rt;
            }

        }

        return false;
    }

    public void draw() {
        Node traversalNode = root;
        draw(traversalNode);


    }

    private void draw(Node n) {

        if (n == null) {
            return;
        }
        draw(n.lb);
        if (n.orientation == Orientation.VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());

        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
        }
        draw(n.rt);

    }


    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null) throw new IllegalArgumentException();
        LinkedList<Point2D> pointsinRec = new LinkedList<>();
        pointsinRec = range(root, rect,pointsinRec);
        return pointsinRec;


    }

    private LinkedList<Point2D> range(Node n, RectHV rect,LinkedList<Point2D> list) {
        if (n == null)  return list;//return null;
        if (rect.contains(n.p)) list.add(n.p);
        if(n.lb != null && n.lb.rect.intersects(rect)) range(n.lb, rect,list);
        if (n.rt != null && n.rt.rect.intersects(rect)) range(n.rt,rect,list);
        return list;
    }

    public Point2D nearest(Point2D p) {
        if(p == null) throw new IllegalArgumentException();
        champion = root;

        return nearest(p,root); }

    private Point2D nearest(Point2D p, Node n){
        if(n == null) return null;

        if(n.lb != null && n.lb.p.distanceSquaredTo(p) < champion.p.distanceSquaredTo(p)) champion = n.lb;
        if(n.rt != null &&  n.rt.p.distanceSquaredTo(p) < champion.p.distanceSquaredTo(p)) champion = n.rt;

        nearest(p,n.lb);
        nearest(p,n.rt);
        
        return champion.p;



    }


    private Node insert(Point2D p, Node x, Node xParent, int depth) {
        if (x == null) {
            Orientation o = (depth % 2 == 0) ? Orientation.VERTICAL : Orientation.HORIZONTAL;
            Node returnNode = new Node();
            returnNode.p = p;
            returnNode.orientation = o;

            RectHV r = null;
            //if parents orientation is vertical and its going into the lb subtree
            if (xParent.orientation == Orientation.VERTICAL && xCompare.compare(p, xParent.p) < 0) {
                RectHV parentRect = xParent.rect;
                r = new RectHV(parentRect.xmin(), parentRect.ymin(), xParent.p.x(), parentRect.ymax());

            }
            //if parents orientation is vertical and its going into the rt subtree
            if (xParent.orientation == Orientation.VERTICAL && xCompare.compare(p, xParent.p) >= 0) {
                RectHV parentRect = xParent.rect;
                r = new RectHV(xParent.p.x(), parentRect.ymin(), parentRect.xmax(), parentRect.ymax());

            }
            //if parents orientation is horizontal and its going into the lb subtree
            if (xParent.orientation == Orientation.HORIZONTAL && yCompare.compare(p, xParent.p) < 0) {
                RectHV parentRect = xParent.rect;
                //r = new RectHV(parentRect.xmin(),parentRect.ymin(),parentRect.xmax(),p.y());
                r = new RectHV(parentRect.xmin(), parentRect.ymin(), parentRect.xmax(), xParent.p.y());

            }
            //if parents orientation is horizontal and its going into the rt subtree
            if (xParent.orientation == Orientation.HORIZONTAL && yCompare.compare(p, xParent.p) >= 0) {
                RectHV parentRect = xParent.rect;
                r = new RectHV(parentRect.xmin(), xParent.p.y(), parentRect.xmax(), parentRect.ymax());

            }

            returnNode.rect = r;


            return returnNode;
        } //FIX THIS


        int cmp = (depth % 2 == 0) ? xCompare.compare(p, x.p) : yCompare.compare(p, x.p);
        if (cmp < 0) x.lb = insert(p, x.lb, x, depth + 1);
        else if (cmp >= 0) x.rt = insert(p, x.rt, x, depth + 1);

        return x;


    }


    /**
     * private void insert2(Point2D p, Node x, int depth){
     * if(x == null){x = new Node(p);}
     * int cmp =  (depth%2 ==0) ? xCompare.compare(p,x.p) : yCompare.compare(p,x.p);
     * if(cmp < 0) insert(p,x.lb, depth + 1);
     * if(cmp > 0) insert(p,x.rt,depth + 1);
     * <p>
     * }
     * private void insert3(Point2D p ,int depth){
     * if(this.root == null){this.root = new Node(p); return;}
     * int cmp =  (depth%2 ==0) ? xCompare.compare(p,this.root.p) : yCompare.compare(p,this.root.p);
     * if(cmp < 0) insert(p,this.root.lb, depth + 1);
     * if(cmp > 0) insert(p,this.root.rt,depth + 1);
     * }
     **/
    public static void main(String[] args) {
        KdTree tree = new KdTree();
        PointSET brute = new PointSET();

        tree.insert(new Point2D(.7,.2));
        tree.insert(new Point2D(.5,.4));
        tree.insert(new Point2D(.2,.3));
        tree.insert(new Point2D(.4,.7));
        tree.insert(new Point2D(.9,.6));

        brute.insert(new Point2D(.7,.2));
        brute.insert(new Point2D(.5,.4));
        brute.insert(new Point2D(.2,.3));
        brute.insert(new Point2D(.4,.7));
        brute.insert(new Point2D(.9,.6));



/**
        tree.insert(new Point2D(.372,.497));
        tree.insert(new Point2D(.564,.413));
        tree.insert(new Point2D(.226,.577));
        tree.insert(new Point2D(.144,.179));
        tree.insert(new Point2D(.083,.51));
        tree.insert(new Point2D(.32,.708));
        tree.insert(new Point2D(.417,.362));
        tree.insert(new Point2D(.862,.825));
        tree.insert(new Point2D(.785,.725));
        tree.insert(new Point2D(.499,.208));

        brute.insert(new Point2D(.372,.497));
        brute.insert(new Point2D(.564,.413));
        brute.insert(new Point2D(.226,.577));
        brute.insert(new Point2D(.144,.179));
        brute.insert(new Point2D(.083,.51));
        brute.insert(new Point2D(.32,.708));
        brute.insert(new Point2D(.417,.362));
        brute.insert(new Point2D(.862,.825));
        brute.insert(new Point2D(.785,.725));
        brute.insert(new Point2D(.499,.208));
**/

        StdDraw.clear();
        tree.draw();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();

        StdDraw.setPenColor(StdDraw.GREEN);
        Point2D qp = new Point2D(.53,.81);
        qp.draw();

        StdDraw.show();

        
        Point2D p = tree.nearest(qp);
        //Point2D p = tree.nearest(new Point2D(1,1));

        System.out.print(p);


    }
}
