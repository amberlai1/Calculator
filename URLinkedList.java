import java.util.Collection;
import java.util.Iterator;

public class URLinkedList<E> implements URList<E> {
    public URNode<E> head = null;
    public URNode<E> tail = null;
    private int size = 0;

    // Inserts the specified element at the beginning of this list. 
    public void addFirst(E e){
        this.add(0, e);
    }
    // Appends the specified element to the end of this list. 
    public void addLast(E e){
        this.add(e);
    }


    // Retrieves, but does not remove, the first element of this list, or returns null if
    //this list is empty.
    public E peekFirst() {
        try {
            return head.element(); // returns the head if list not empty because head = first element
        } catch (Exception e) {
            return null; // returns null if list empty
        }
    }
    
    // Retrieves, but does not remove, the last element of this list, or returns null if this list is empty.
    public E peekLast() {
        try {
            return tail.element(); // returns the tail if list not empty because tail = last element
        } catch (Exception e) {
            
            return null; // returns null if list empty
        }
        
    }
    
    // Retrieves and removes the first element of this list, or returns null if this list is empty.
    public E pollFirst() {
        try {
            return this.remove(0); // returns the element that gets removed...element at index 0
        } catch (Exception e) {
            return null; //if list is empty returns null
        }
        
    }
    
    // Retrieves and removes the last element of this list, or returns null if this list is empty.
    public E pollLast() {
        try {
            return this.remove(this.size - 1); // returns the element that gets removed...element at index size - 1 since index starts at 0
        } catch (Exception e) {;
            return null;  //if list is empty returns null 
   
        }
    
    }

    @Override
    public boolean add(E e) {   // Appends the specified element to the end of this list 
        URNode<E> newNode;
 
        if (head == null) {
            newNode = new URNode<E>(e,null, null);
            head = newNode; 
            tail = newNode;
        }
        else {
            newNode = new URNode<E>(e,tail, null);
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
        return true; // returns true since a linked list usually implements collection which returns true if collection has changed
    }

    @Override
    // Inserts the specified element at the specified position in this list 
    public void add(int index, E element) {
        URNode<E> newNode = new URNode<E>(element, null, null);
        try {
            if (index < 0 || index > size())
		        throw new Exception(String.format("Error: Index %d out of range.", index));
        } catch(Exception e) {
            System.out.println(e);
        }
	    if (head == null) {
		    head = newNode;
		    tail = newNode;
        }
        else if (index == 0) {	
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        }
        else if (index == this.size() - 1) {
            newNode.setPrev(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        else {			
            URNode<E> nodeRef = this.getNodeAtIndex(index);
            
            newNode.setPrev(nodeRef.prev());
            newNode.setNext(nodeRef);
            nodeRef.setPrev(newNode);
        }

        size++;
    }
    
    @Override
    // Appends all of the elements in the specified collection to the end of this list,
	// in the order that they are returned by the specified collection's iterator 
    public boolean addAll(Collection<? extends E> c) {
        for(E el : c) { 
            this.add(el);
        }
        return true; // returns true since a linked list usually implements collection which returns true if collection has changed
    }

    @Override
    // Inserts all of the elements in the specified collection into this list 
	// at the specified position
    public boolean addAll(int index, Collection<? extends E> c) {
        for(E el : c) { 
            this.add(index++, el);
        }
        return true; // returns true since a linked list usually implements collection which returns true if collection has changed
    }

    @Override
    public void clear() { 
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean contains(Object o) { 
        URNode<E> tmpNode = getNodeAtIndex(0);
        for(int i = 0; i <= size; i ++ ) {
            if(tmpNode.element() == o){
                return true;  // return true if list contains specified element
            } else{
                tmpNode = getNodeAtIndex(i);
            }
        }
        return false; // returns false if list does not contain specified element
    }
    
    @Override
    // Returns true if this list contains all of the elements of the specified collection
    public boolean containsAll(Collection<?> c) {
        int containerCheck = 0;
        for(Object el : c) {
            URNode<E> tmpNode = getNodeAtIndex(0);
            for(int i = 0; i < size; i ++ ) {
                tmpNode = getNodeAtIndex(i);
                if(tmpNode.element() == el){
                    containerCheck++;
                    break;
                } else  continue;

            }
        } if (containerCheck == c.size()) {
            return true; // returns true if each element in container are equal to an element in linked list
        } else {
            return false; // returns false if it is not (e.g. containerCheck < c.size()
        }
    }
    
    @Override
    // Compares the specified object with this list for equality. 
	// Returns true if both contain the same elements. Ignore capacity
	public boolean equals(Object o){
        URNode<E> tmpNode = getNodeAtIndex(0);
        URLinkedList other = (URLinkedList) o;
        URNode<E> onode = other.getNodeAtIndex(0);
        if(other.size() != size){
            return false; // return false if the object != size of linked list
        }
        for(int i = 0; i < size; i++ ){
            if(tmpNode.element() == onode.element() && tmpNode.element() != null){
                tmpNode = getNodeAtIndex(i);
                onode = other.getNodeAtIndex(i);
            }
            else {
                return false; // return false if tmpNode and onode are not the same
            }
        }
          return true; // returns true if it doesn't reach the previous 'return false's
      }

    public URNode<E> getNodeAtIndex(int index) { // method to just get the actual node at the specified index
    	URNode<E> current = head;
    		for(int i = 0; i < index; i++) { 
                current = current.next();
            }
        return current; // returns actual node at index
    }

    @Override 
    public E get(int index) { // Returns the element at the specified position in this list.
    	try {
    		getNodeAtIndex(index);
    	} catch (NullPointerException e) {
    		System.out.println("Invalid Index: Enter index from 0 - " + (size - 1));
    		return null;
    	}
    	return getNodeAtIndex(index).element(); // returns the element of the node at the provided index
    }
    
    @Override
    public int indexOf(Object o) { 
         // Returns the index of the first occurrence of the specified element in this list, 
	    // or -1 if this list does not contain the element.
        URNode<E> tmpNode = getNodeAtIndex(0);
    
        for(int i = 0; i < size; i ++ ) {
            if(tmpNode.element() == o){
                return i; // returns index of element if o = element in linked list
            } else{
                tmpNode = getNodeAtIndex(i+1);
            }
        }
        return -1; // returns -1 if it's not = any of the elements
    }

    @Override
    public boolean isEmpty() { 
       // Returns true if this list contains no elements.
       if(head == null && tail == null) {
        return true; // returns true if the head and tail are null since that means the linked list is empty
       } 
       else {
        return false; // returns false if the above condition is not met
       }
    }
    
    class URIterator<E> implements Iterator<E>{
        URNode<E> current = (URNode<E>) head;
          
        // Checks if the next element exists
        public boolean hasNext() {
            if (current.next() == null) {
                return false; // returns false if the next node is null
            } else {
                return true; // returns true if the next node is not false
            }
        }
          
        // moves the cursor/iterator to next element
        public E next() {
            current = current.next();
            E val = current.element();
            return val; // returns the value of the element at the current node
        }

    }
    
    @Override
    public Iterator<E> iterator() {
        
        // Returns an iterator over the elements in this list in proper sequence
        return new URIterator<E>(); // returns the above iterator for usage
    }

    @Override 
    public E remove(int index)  { // Removes the element at the specified position in this list 
    	try {
    		getNodeAtIndex(index);
    	} catch (NullPointerException e) {
    		System.out.println("Invalid Index: Enter index from 0 - " + (size - 1));
    		return null;
    	}
    	
        URNode<E> nodeAtIndex = getNodeAtIndex(index);

    	URNode<E> tmpNode = nodeAtIndex;
        E removedElement = nodeAtIndex.element();
    	
        	if (head.equals(null)) {
                System.out.println("The array is empty!");
            }
            else if (index == 0 && size == 1) {
                head = null;
                tail = null;
            }
            else if (index == 0) {
                tmpNode.next().setPrev(null);
                head = tmpNode.next();
            }
            else if (index == this.size() - 1) {
                tmpNode.prev().setNext(null);
                tail = tmpNode.prev();
            }
            else {
                tmpNode.prev().setNext(tmpNode.next());
                tmpNode.next().setPrev(tmpNode.prev());
            }
        	
            tmpNode.setElement(null);
            size--;
            return removedElement; // returns element that was removed

    }
    
    @Override
    public boolean remove(Object o) {  
       // Removes the first occurrence of the specified element from this list,
	// if it is present 
         
         for(int i = 0; i <= size; i ++ ) {
            URNode<E> tmpNode = getNodeAtIndex(i);
             if (tmpNode.element() == o) {
                 if (head == null) {
                     System.out.println("The list is empty!");
                 }
                 else if (i == 0 && size == 1) {
                     head = null;
                     tail = null;
                 }
                 else if (i == 0) {
                     tmpNode.next().setPrev(null);
                     head = tmpNode.next();
                 }
                 else if (i == this.size() - 1) {
                     tmpNode.prev().setNext(null);
                     tail = tmpNode.prev();
                 }
                 else {
                     tmpNode.prev().setNext(tmpNode.next());
                     tmpNode.next().setPrev(tmpNode.prev());
                 }
                 tmpNode.setElement(null);
                 size--;
                 break;
             } else {
                 continue;
             }
         }
         return true; // returns true after execution of code
     }
       
    @Override
    public boolean removeAll(Collection<?> c) { 
        // Removes from this list all of its elements that are contained
	//  in the specified collection
        for (Object el : c) {
            this.remove(el);
        }
        return true; // returns true if elements are removed and list changes
    }
    
    @Override 
    public E set(int index, E element) { // Replaces the element at the specified position in this list with the specified element 
    	try {
    		getNodeAtIndex(index);
    	} catch (NullPointerException e) {
    		System.out.println("Invalid Index: Enter index from 0 - " + (size - 1));
    		return null;
    	}
    	URNode<E> oldNode = this.getNodeAtIndex(index);
        oldNode.setElement(element);
        return oldNode.element();
    }

    @Override
    public int size() { 
       return this.size; // returns the size of the linked list
    }

    @Override
    // Returns a view of the portion of this list 
	// between the specified fromIndex, inclusive, and toIndex, exclusive.
    public URList<E> subList(int fromIndex, int toIndex) {
        URLinkedList<E> newList = new URLinkedList<E>();
        URNode<E> tmpNode = getNodeAtIndex(fromIndex);
        for(int i = fromIndex; i < toIndex; i++) {
            newList.add(tmpNode.element());
            System.out.print(tmpNode.element() + " ");
            tmpNode = getNodeAtIndex(i+1);
        
        }
    
        return newList; // returns the list within the specified indexes
    }
    
    @Override
    // Returns an array containing all of the elements in this list
	//  in proper sequence (from first to the last element).
    public Object[] toArray() {
        Object[] arr = new Object[size];
        URNode<E> tmpNode = getNodeAtIndex(0);
        for(int i = 0; i < size; i++) {
            arr[i] = (tmpNode.element());
            tmpNode = getNodeAtIndex(i+1);
        
        } 
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");

        }
        return arr; // returns the array of all the elements now in the array
    }   


}