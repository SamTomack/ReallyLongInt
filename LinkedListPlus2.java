package Assig3;
//CS 0445 Spring 2022
//Author - Samuel Tomack 
//This provides a few operations for shifting data in linked lists.  
//All operations are done recursively. 

public class LinkedListPlus2<T> extends A2LList<T>
{
	//Default constructor
	public LinkedListPlus2()
	{
		super();
	}

	//Copy constructor
	public LinkedListPlus2(LinkedListPlus2<T> oldList)
	{
		super();
		if (oldList.getLength() > 0)
		{
			Node temp = oldList.firstNode;
			Node newNode = new Node(temp.data);
			firstNode = newNode;
			Node currNode = firstNode;
			temp = temp.next;
			int count = 1;
			RLICall(oldList, currNode, count, temp, newNode);
		}		
	}

	//Recursive call for copy constructor 
	private void RLICall(LinkedListPlus2 oldList, Node currNode, int count, Node temp, Node newNode) {
		if (count < oldList.getLength())
		{
			newNode = new Node(temp.data);
			currNode.next = newNode;
			newNode.prev = currNode;
			temp = temp.next;
			currNode = currNode.next;
			count++;
			RLICall(oldList, currNode, count, temp, newNode);
		}
		else {
			currNode.next = firstNode; 
			firstNode.prev = currNode;
			numberOfEntries = oldList.numberOfEntries;
		}
	}		

	//Displays the linked list as a String
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		Node curr = firstNode;
		int i = 0;
		return toStringCall(b, curr, i);
	}

	//Recursive call for toString method
	private String toStringCall(StringBuilder b, Node curr, int i) {
		if(i < this.getLength())
		{
			b.append(curr.data.toString());
			b.append(" ");
			curr = curr.next;
			i++;
			return toStringCall(b, curr, i);
		}
		return b.toString();
	}

	/**
	 * Removes a number of elements from the left side of the list. 
	 * @param num, an integer representing the number of elements to be removed
	 */
	public void leftShift(int num)
	{
		if(num >= getLength()) {
			clear();
		}
		else if(num == 0) {
			return;
		}
		else {
			remove(num);
			num--;
			leftShift(num);
		}
	}

	/**
	 * Removes a number of elements from the right side of the list. 
	 * @param num, an integer representing the number of elements to be removed
	 */
	public void rightShift(int num)
	{
		if(num >= getLength()) {
			clear();
		}
		else if(num == 0) {
			return;
		}
		else {
			remove(getLength()-num+1);
			num--;
			rightShift(num);
		}
	}

	/**
	 * Rotates the list to the left. One rotation shifts all elements left and 
	 * moves the leftmost element to the back of the list. 
	 * @param num, an integer representing the number of rotations
	 */
	public void leftRotate(int num)
	{
		if(num<0)
			rightRotate(-num);
		else {
			if(num == 0)
				return;
			else {
				int n = num%getLength();
				firstNode = firstNode.next;
				leftRotate(n-1);
			}
		}

	}

	/**
	 * Rotates the list to the right. One rotation shifts all elements right and 
	 * moves the rightmost element to the front of the list. 
	 * @param num, an integer representing the number of rotations
	 */
	public void rightRotate(int num)
	{
		if(num<0)
			leftRotate(-num);
		else {
			if(num == 0)
				return;
			else {
				int n = num%getLength();
				firstNode = firstNode.getPrevNode();
				rightRotate(n-1);
			}
		}

	}	
}