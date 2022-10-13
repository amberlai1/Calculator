public class Stack<E> extends URLinkedList<E> {
    
    // public boolean isFull() {//  whether stack is full or not
    //     if(size == capacity){
    //         return true;
    //     }
    //     return false; 
    // }

    public boolean isEmpty(){ //  check whether stack is empty or not
        return super.isEmpty(); 
    }

    public void push(E x){ // push x into the stack
        super.add(x);
    }

    public E pop() {// return and remove one element from top of the stack
        return super.pollLast();
    }

    public E peek(){ //  get the top most element of the stack
        return super.peekLast();
    }

    public int size(){ // get size of stack  

        return super.size(); 
    }
}
