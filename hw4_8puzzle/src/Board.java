import edu.princeton.cs.algs4.StdRandom;
import java.util.LinkedList;

public class Board {
    private final int [][] tiles;
    private final int n;

    public Board(int[][] blocks){
        if(blocks == null){throw new IllegalArgumentException();}
        tiles = deepCopy2D(blocks);
        n = blocks.length; }

    public int dimension() {return n;}

    public int hamming(){
        int hammingDist = 0;
        for(int i = 0; i< n; i++)
            for(int j = 0; j <  n; j++)
                if(tiles[i][j] != 0 && tiles[i][j] != convert2Dto1D(i,j)) hammingDist++;
        return hammingDist; }

    public int manhattan(){
        int manhattanDist = 0;
        for(int i = 0 ; i < n; i++){
            for(int j = 0; j < n; j++){
                if((tiles[i][j] != convert2Dto1D(i,j)) && (tiles[i][j] !=0)){
                    int correctXCoord =   (tiles[i][j]-1)/n;
                    int correctYCoord =   (tiles[i][j]-1)%n;
                    manhattanDist += Math.abs(correctXCoord - i) + Math.abs(correctYCoord - j);
                }
            }
        }
        return manhattanDist;
    }

    public boolean isGoal(){
        if(this.tiles[n-1][n-1] != 0){return false;}
        for(int i = 0; i < this.dimension(); i++){
            for(int j = 0; j < this.dimension(); j++){
                if(this.tiles[i][j] != convert2Dto1D(i,j) && this.tiles[i][j] !=0){return false;}

            }
        }
        return true;
    }

    public Board twin(){
        int [][] arr = deepCopy2D(this.tiles);
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n-1 ; j++){
                if(arr[i][j] != 0 && arr[i][j+1] != 0){
                    swap(arr,i,j,i,j+1);
                    return new Board(arr);
                }

            }
        }
        while(true) {
            int r1 = StdRandom.uniform(this.dimension());
            int r2 = StdRandom.uniform(this.dimension());
            int r3 = StdRandom.uniform(this.dimension());
            int r4 = StdRandom.uniform(this.dimension());
            if (arr[r1][r2] != 0 && arr[r3][r4] !=0 && arr[r1][r2] != arr[r3][r4]) {
                swap(arr, r1, r2, r3, r4);
                break;}
            }
        return new Board(arr);
    }

    public boolean equals(Object y){
        if(y == null) return false;
        if(y instanceof String){return false;}
        Board b = (Board) y;
        if(this.dimension() != b.dimension()){return false;}
        for(int i = 0; i < this.dimension(); i++)
            for(int j = 0; j < this.dimension(); j++)
                if(this.tiles[i][j] != b.tiles[i][j])return false;

        return true;
    }

    public Iterable<Board> neighbors(){
        int[][] copy = deepCopy2D(this.tiles);
        int blankXCoord = 0;
        int blankYCoord = 0;
        LinkedList<Board> neighbors = new LinkedList<Board>();

        for(int i = 0; i < this.dimension(); i++ )
            for(int j = 0; j < this.dimension(); j++)
                if(copy[i][j] == 0){ blankXCoord = i; blankYCoord = j;}

        createNeighbor(copy,blankXCoord,blankYCoord,blankXCoord - 1,blankYCoord,neighbors);
        createNeighbor(copy,blankXCoord,blankYCoord, blankXCoord + 1, blankYCoord,neighbors);
        createNeighbor(copy,blankXCoord,blankYCoord, blankXCoord, blankYCoord - 1,neighbors);
        createNeighbor(copy,blankXCoord,blankYCoord, blankXCoord, blankYCoord + 1,neighbors);








        return neighbors;
    }

    public String toString() {StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();}

    /**Helper functions **/
    private int convert2Dto1D(int i, int j){
        return i*n + j+1;
    }
    private boolean isValidIndices(int i, int j){
        if ((i < this.dimension() && i >=0) && (j < this.dimension()&& j >=0)) return true;
        return false;
    }
    private void swap(int[][] a, int i1,  int j1, int i2, int j2){
        int dummy = a[i1][j1];
        a[i1][j1] = a[i2][j2];
        a[i2][j2] = dummy;
    }
    private int[][] deepCopy2D(int [][] arr){
        int [][] returnValue = new int [arr.length][arr.length];
        for(int i = 0; i< arr.length;i++){
            for(int j = 0; j < arr.length; j++){
                returnValue[i][j] = arr[i][j];
            }
        }
        return returnValue;
    }
    private void createNeighbor(int[][] arr, int i, int j, int x, int y,LinkedList<Board> neighbors){
        if(isValidIndices(x,y)){
            swap(arr, i,j,x,y);
            Board b = new Board(deepCopy2D(arr));
            neighbors.add(b);
            swap(arr, i,j,x,y);
        }
    }
}
