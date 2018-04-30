import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node<Item>{
        Item item;
        Node<Item> prev;
        Node<Item> next;
    }
    private class DequeIterator implements Iterator<Item>{
        private Node<Item> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if(!hasNext()){throw new NoSuchElementException();}
            Item returnValue = current.item;
            current = current.next;
            return returnValue;
        }

        @Override
        public void remove() {throw new java.lang.UnsupportedOperationException(); }
    }

    private Node<Item> first;
    private Node<Item> last;
    private int size;


    public Deque(){
        size = 0;
    }
    public boolean isEmpty(){return size == 0;}
    public int size(){return size;}

    public void addFirst(Item item){
        if(item == null){throw new IllegalArgumentException("Item cannot be null");}

        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        //here for corner case when you put in first element
        if(isEmpty()) last = first; //isEmpty will be true at this point when you add your first element since size is decremented at end of method
        //here for once you have elements in your list
        else{ oldFirst.prev = first; }
        size++;
    }
    public void addLast(Item item){
        if(item == null){throw new IllegalArgumentException("Item cannot be null");}

        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.prev = oldLast;
        last.next = null;
        if(isEmpty()){first = last;}
        else{oldLast.next = last;}
        size++;

    }
    public Item removeFirst(){
        if(isEmpty()){ throw new NoSuchElementException("Nothing to remove");}
        Item returnValue = first.item;
        size--;
        if(isEmpty()){first = last; first.item = null;}
        else{first = first.next;
        first.prev.next = null; // takes care of loitering references
        first.prev = null;}
        return returnValue;

    }
    public Item removeLast(){
        if(isEmpty()){ throw new NoSuchElementException("Nothing to remove");}
        Item returnValue = last.item;
        size--;
        if(isEmpty()){last = first; last.item = null;}
        else{last = last.prev;
        last.next.prev = null; // takes care of loitering references
        last.next = null;}
        return returnValue;
    }
    public Iterator<Item> iterator(){return new DequeIterator();}




    public static void main(String[] args){
        Deque<Integer> deque = new Deque<Integer>();
        Iterator<Integer> it1 = deque.iterator();
        deque.addLast(1);
        deque.addFirst(2);
        deque.removeLast();//    ==> 1
        Iterator<Integer> it = deque.iterator();
        for(int i : deque){System.out.print(it.next()+ " ");}

    }


}
