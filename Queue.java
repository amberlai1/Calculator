public class Queue<E> extends URLinkedList<E>{

    public void enqueue(E element){
        super.add(element); 
    }
    public E dequeue(){
        return super.pollFirst();
    }
    public E peek(){
        return super.peekFirst();
    }

    public int size(){
        return super.size();
    }
    
    public boolean isEmpty(){
        return super.isEmpty();
    }

}
