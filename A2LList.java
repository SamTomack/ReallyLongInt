public class A2LList<T> 
{
	protected Node firstNode;     
	protected int numberOfEntries; 

	public A2LList() {
		clear();
	} 
	
	public final void clear() {
		firstNode = null;
		numberOfEntries = 0;
	} 
	
	public int getLength() {
		return numberOfEntries;
	}
	
	public void add(T newEntry) {
		Node newNode = new Node(newEntry);
		if (isEmpty())
		{
   		firstNode = newNode;
   		firstNode.setPrevNode(firstNode);
   		firstNode.setNextNode(firstNode);
   	}
		else {
   		Node lastNode = firstNode.getPrevNode();
   		lastNode.setNextNode(newNode); 
   		newNode.setPrevNode(lastNode);
   		newNode.setNextNode(firstNode);
   		firstNode.setPrevNode(newNode);
		} 
		numberOfEntries++;
	}  

	public boolean add(int newPosition, T newEntry) {
		boolean isSuccessful = true;
		if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {	
   		Node newNode = new Node(newEntry);
			if (isEmpty())
			{
   			firstNode = newNode;
   			firstNode.setPrevNode(firstNode);
   			firstNode.setNextNode(firstNode);
   		}
   		else if (newPosition == 1) {
   			Node lastNode = firstNode.getPrevNode();
      		newNode.setNextNode(firstNode);
      		firstNode.setPrevNode(newNode);
      		newNode.setPrevNode(lastNode);
      		lastNode.setNextNode(newNode);				
      		firstNode = newNode;
   		}
   		else {                                
      		Node nodeBefore = getNodeAt(newPosition - 1);
      		Node nodeAfter = nodeBefore.getNextNode();
      		newNode.setNextNode(nodeAfter);
      		nodeBefore.setNextNode(newNode);
      		nodeAfter.setPrevNode(newNode);
      		newNode.setPrevNode(nodeBefore);
   		} 	
   		numberOfEntries++;
		}
		else
   		isSuccessful = false;
   
		return isSuccessful;
	} 
	
	public boolean isEmpty() {
		boolean result;
   
		if (numberOfEntries == 0) {
   		result = true;
		}
		else {
   		result = false;
		} 
		return result;
	} 

	public T remove(int givenPosition) {
		T result = null;                        
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			if (numberOfEntries == 1) {							
				result = firstNode.getData();
				firstNode = null;
			}	
   		else if (givenPosition == 1) {
      		result = firstNode.getData();       
      		Node lastNode = firstNode.getPrevNode();
      		firstNode = firstNode.getNextNode(); 
      		lastNode.setNextNode(firstNode);	
      		firstNode.setPrevNode(lastNode);
   		}
   		else {  
      		Node nodeBefore = getNodeAt(givenPosition - 1);
      		Node nodeToRemove = nodeBefore.getNextNode();
      		Node nodeAfter = nodeToRemove.getNextNode();
      		nodeBefore.setNextNode(nodeAfter);
      		nodeAfter.setPrevNode(nodeBefore);  
      		result = nodeToRemove.getData();    
   		} 
   		numberOfEntries--;
		} 
		return result;                                                          
	} 

	public boolean replace(int givenPosition, T newEntry) {
		boolean isSuccessful = true;
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {   
   		assert !isEmpty();
   		Node desiredNode = getNodeAt(givenPosition);
   		desiredNode.setData(newEntry);
		}    
		else
   		isSuccessful = false;
		return isSuccessful;
	} 


	public T getEntry(int givenPosition) {
		T result = null;  

		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
   		result = getNodeAt(givenPosition).getData();
		} 
		return result;
	} 
	
	public boolean contains(T anEntry) {
		boolean found = false;
		Node currentNode = firstNode;
		int count = 0;
		while (!found && (count < this.getLength())) {
   		if (anEntry.equals(currentNode.getData()))
      		found = true;
   		else {
      		currentNode = currentNode.getNextNode();
      		count++;
      	}
		} 
		return found;
	} 

	private Node getNodeAt(int givenPosition) {
		assert !isEmpty() && (1 <= givenPosition) && (givenPosition <= numberOfEntries);
		Node currentNode = firstNode;
		for (int counter = 1; counter < givenPosition; counter++)
			currentNode = currentNode.getNextNode();		
		assert currentNode != null;
		return currentNode;
	} 
	
protected class Node {
   protected T data; 	
   protected Node next; 	
   protected Node prev;  
   
   protected Node(T dataPortion) {
      this(dataPortion, null, null);
   } 
   
   protected Node(T dataPortion, Node nextNode, Node prevNode) {
      data = dataPortion;
      next = nextNode;
      prev = prevNode;
   } 
   
   protected T getData() {
      return data;
   } 
   
   protected void setData(T newData) {
      data = newData;
   } 
   
   protected Node getNextNode() {
      return next;
   } 
   
   protected void setNextNode(Node nextNode) {
      next = nextNode;
   } 
   
   protected Node getPrevNode()
   {
   	return prev;
   }
   
   protected void setPrevNode(Node prevNode)
   {
   	prev = prevNode;
   }
} 
} 
