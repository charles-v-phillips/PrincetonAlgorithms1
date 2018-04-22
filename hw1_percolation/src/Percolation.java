import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF wquf;
    private int grid_dimension;
    private int top_site = 0;
    private int bottom_site;
    private boolean [] open;
    private int numberOfOpenSites = 0;

    public Percolation(int n){
        if(n <=0) throw new IllegalArgumentException(n + " is not a valid dimension");
        wquf = new WeightedQuickUnionUF(n*n + 2);
        grid_dimension = n;
        bottom_site = n*n + 1;


        //This loop connects the top satellite site to the first
        // row of sites and connects the bottom satellite site to the bottom row of sites
        for(int i = 1; i<=n; i++){
            wquf.union(top_site,i);
            wquf.union(bottom_site,n*n - n + i);
        }

        //Keep track of open sites
        open = new boolean[n*n + 2];
        open[top_site] = true;
        open[bottom_site] = true;

        for(int i = 1; i < n*n+1; i++) open[i] = false;




    }
    public void open(int row, int col){
        if(!isValidIndex(row,col)) throw new IllegalArgumentException("Invalid Indices");

        if(!isOpen(row,col)) {
            if (isValidIndex(row, col)) {
                open[xyTo1D(row, col)] = true;
            }
            if (isValidIndex(row - 1, col) && open[xyTo1D(row - 1, col)] == true) {
                wquf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if (isValidIndex(row + 1, col) && open[xyTo1D(row + 1, col)] == true) {
                wquf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
            if (isValidIndex(row, col - 1) && open[xyTo1D(row, col - 1)] == true) {
                wquf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
            if (isValidIndex(row, col + 1) && open[xyTo1D(row, col + 1)] == true) {
                wquf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
            numberOfOpenSites++;
        }


    }
    public boolean isOpen(int row, int col){

        if(!isValidIndex(row,col)) throw new IllegalArgumentException("Invalid Indices");
        return open[xyTo1D(row,col)];}


    public boolean isFull(int row, int col){

        if(!isValidIndex(row,col)) throw new IllegalArgumentException("Invalid Indices");
        return (wquf.connected(top_site,xyTo1D(row,col)) && isOpen(row,col));}

    public int numberOfOpenSites(){return numberOfOpenSites;}

    public boolean percolates(){
        if(numberOfOpenSites == 0) return false; // corner case for input1.txt
        return wquf.connected(top_site,bottom_site);

    }



    private int xyTo1D(int i, int j){
        return (i-1)*grid_dimension + j;
    }

    private boolean isValidIndex(int row, int col){return(row <=grid_dimension && col <=grid_dimension && row > 0 && col > 0); }



    public static void main(String[] args) {




    }  // test client (optional)

}

