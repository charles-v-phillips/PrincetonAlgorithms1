import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.LinkedList;


public class Solver {
    private Node searchNode;

    private class Node implements Comparable<Node> {

        Node previous;
        Board board;
        int numMoves;
        int manhattan;


        Node(Node previous, Board board, int numMoves){
            this.previous = previous;
            this.board = board;
            this.numMoves = numMoves;
            this.manhattan = board.manhattan();



        }

        @Override
        public int compareTo(Node o) {
            return (this.manhattan + this.numMoves) - (o.manhattan + o.numMoves);
        }
    }


    public Solver(Board initial) {
        if(initial == null) throw new IllegalArgumentException();

         MinPQ<Node>   pq1 = new MinPQ<Node>();
         MinPQ<Node>   pq2 = new MinPQ<Node>();
         Node twinSearchNode;

        Node start = new Node(null, initial, 0);
        Node twinStart = new Node(null, initial.twin(), 0);
        searchNode = start;
        twinSearchNode = twinStart;

        while (!searchNode.board.isGoal() && !twinSearchNode.board.isGoal()) {
            for (Board b : searchNode.board.neighbors()) {
                if (searchNode.previous == null || !b.equals(searchNode.previous.board)) {
                    pq1.insert(new Node(searchNode, b, searchNode.numMoves + 1));
                }}

            for (Board b : twinSearchNode.board.neighbors()) {
                if (twinSearchNode.previous == null || !b.equals(twinSearchNode.previous.board)) {
                    pq2.insert(new Node(twinSearchNode, b, twinSearchNode.numMoves + 1));
                }}

            searchNode = pq1.delMin();
            twinSearchNode = pq2.delMin();



        }
    }



    public boolean isSolvable() { return searchNode.board.isGoal(); }
    public int moves() { return (isSolvable() ? searchNode.numMoves : -1); }

    public Iterable<Board> solution() {
        if(!isSolvable()){return null;}
        LinkedList<Board> solution = new LinkedList<>();
        for(Node s = searchNode; s != null; s = s.previous) solution.push(s.board);
        return solution; }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In("/Users/charlesphillips/Downloads/8puzzle/puzzle05.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }




    }
}

