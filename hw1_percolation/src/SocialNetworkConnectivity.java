/**
 * Social network connectivity. Given a social network containing n members and a log file
 * containing m timestamps at which times pairs of members formed friendships, design an
 * algorithm to determine the earliest time at which all members are connected (i.e., every
 * member is a friend of a friend of a friend ... of a friend). Assume that the log file is
 * sorted by timestamp and that friendship is an equivalence relation. The running time of
 * your algorithm should be mlogn or better and use extra space proportional to n
 */


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class SocialNetworkConnectivity {
    private WeightedQuickUnionUF wquf;
    private String file;

    SocialNetworkConnectivity(String file){
        In in = new In(file);
        wquf = new WeightedQuickUnionUF(in.readInt());
        while(!in.isEmpty()){
            int i = in.readInt();
            int j = in.readInt();
            int time = in.readInt();
            this.union(i,j);
            if(this.allConnected()){
                System.out.println("All friends are connected at time " + time);
                break;}

        }
        if(!this.allConnected()){System.out.println("All the friends arent connected");}

    }

    public void union(int i, int j){wquf.union(i,j);}

    public boolean allConnected(){return wquf.count()==1;}

    public static void main(String[] args){

        SocialNetworkConnectivity snc = new SocialNetworkConnectivity("/Users/charlesphillips/Desktop/try.txt");


    }
}
