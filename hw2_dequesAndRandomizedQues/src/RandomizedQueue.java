import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int N = 0;

    private class RandomizedQueueIterator<Item> implements Iterator<Item>{
        private int cursor;
        private Item[] a;

        public RandomizedQueueIterator(){
            cursor = 0;
            a = (Item[]) new Object[N];
            for(int i = 0; i<N;i++){
                a[i] = (Item) arr[i];
            }
            StdRandom.shuffle(a);

        }




        @Override
        public boolean hasNext() {
            return cursor < N;
        }

        @Override
        public Item next() {
            if(!hasNext()){throw new NoSuchElementException("There are no more elements in iterator");}
            return a[cursor++];

        }

        @Override
        public void remove() {throw new UnsupportedOperationException("We dont support remove");}



    }



    public RandomizedQueue(){
        arr = ((Item[]) new Object[1]);
    }
    public boolean isEmpty(){return N == 0;}
    public int size(){return N;}
    public void enqueue(Item item){
        if(item == null){throw new IllegalArgumentException("Cant enque null");}
        if(N == arr.length){
            resizeArray(arr.length*2);}
        arr[N++] = item;
    }
    public Item dequeue(){
        if(isEmpty()){throw new NoSuchElementException("No elements to dequeue");}
        int randInt = StdRandom.uniform(0,N); // produce rand int between 0 and N-1 inclusive
        Item returnVal = arr[randInt];
        N--;
        if(N == 0){arr[randInt] = null;}
        if(N != 0){
            for(int i = randInt + 1; i <= N; i++ ){
                arr[i-1] = arr[i];

            }
            arr[N] = null;
        }

        if(N < arr.length/4){
            resizeArray(arr.length/2);
        }
        return returnVal;

    }
    public Item sample(){
        if(isEmpty()){throw new NoSuchElementException("No elements to dequeue");}
        int randomInt = StdRandom.uniform(0,N);
        return arr[randomInt];
    }

    private void resizeArray(int newSize){

        Item[] copy = ((Item[]) new Object[newSize]);
        for(int i = 0; i< N; i++){ copy[i] = arr[i]; }
        arr = copy;


    }



    @Override
    public Iterator<Item> iterator() {return new RandomizedQueueIterator<Item>();}

    public static void main(String[] args){

        //int [] arr = new int[4];
        //System.out.print(arr.length);

        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
        rq.enqueue(7);
        rq.enqueue(8);
        rq.enqueue(9);
        Iterator<Integer> it0 = rq.iterator();
        Iterator<Integer> it1 = rq.iterator();
        Iterator<Integer> it2 = rq.iterator();


        for(int i : rq){System.out.print(it0.next() + " ");}
        System.out.println();
        for(int i : rq){System.out.print(it1.next() + " ");}
        System.out.println();
        for(int i : rq){System.out.print(it2.next() + " ");}
        System.out.println();



    }






}
