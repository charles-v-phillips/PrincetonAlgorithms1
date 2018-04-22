import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;



public class PercolationStats {
    private double threshold_values[];
    //private Percolation perc;
    private int n;
    private int trials;

    public PercolationStats(int n, int trials){
        if(n <= 0 || trials <= 0) throw new IllegalArgumentException("Invalid n or trials value");
        threshold_values = new double[trials];
        this.n = n;
        this.trials = trials;


        for(int i =  0; i < trials; i++){
            Percolation perc = new Percolation(n);
            while(!perc.percolates()){
                int randInt1 = StdRandom.uniform(1,n+1);
                int randInt2 = StdRandom.uniform(1,n+1);
                perc.open(randInt1,randInt2);

            }
            this.threshold_values[i] = ((double)perc.numberOfOpenSites())/((double)(n*n));
        }



    }
    public double mean(){return StdStats.mean(threshold_values);}

    public double stddev(){ return StdStats.stddev(threshold_values); }

    public double confidenceLo(){
        return mean() - ((1.96*stddev())/Math.sqrt((double) trials));
    }
    public double confidenceHi(){
        return mean() + ((1.96*stddev())/Math.sqrt((double) trials));
    }



    public static void main(String[] args){

        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));



        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + " , " + percolationStats.confidenceHi() + " ]");



    }
}
