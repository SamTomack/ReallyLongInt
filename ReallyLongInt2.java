//This describes non-negative integers as linked lists and provides basic arithmetic operations for them. 
//All operations are done recursively.

public class ReallyLongInt2 	extends LinkedListPlus2<Integer> 
implements Comparable<ReallyLongInt2>
{
	//Default constructor, creates an empty list
	private ReallyLongInt2()
	{
		super();
	}

	//Creates a list read from a String
	public ReallyLongInt2(String s)
	{
		super();
		char c = 'a'; //Dummy character
		int digit = -1;
		int count = 0;
		RLICall(this, s, count, c, digit);
	}

	//Recursive call for constructor from String
	private ReallyLongInt2 RLICall(ReallyLongInt2 rli, String s, int count, char c, int digit) {
		if(count < s.length()) {
			c = s.charAt(count);
			if (('0' <= c) && (c <= '9'))
			{
				digit = c - '0';
				if (!(digit == 0 && this.getLength() == 0)) {
					this.add(1, Integer.valueOf(digit));
				}
			}
			else throw new NumberFormatException("Illegal digit " + c);
			count++;
			return RLICall(rli, s, count, c, digit);	
		}
		if (digit == 0 && this.getLength() == 0) 
			this.add(1, Integer.valueOf(digit));
		return this;
	}


	//Copy constructor 
	public ReallyLongInt2(ReallyLongInt2 rightOp)
	{
		super(rightOp);
	}

	/**
	 * A constructor with a long argument. Parses a long value to a linked list 
	 * @param X, a long value to be transformed to a linked list. 
	 */
	public ReallyLongInt2(long X)
	{
		if(X == 0) {
			this.add(0);
			return;
		}
		ReallyLongInt2Call(this, X);
	}

	//Recursive call for constructor from long
	private ReallyLongInt2 ReallyLongInt2Call(ReallyLongInt2 rli, long X) {
		if(X == 0)
			return rli;
		else {
			long temp = X%10;
			rli.add((int) temp);
			X = (X - temp)/10;
			return ReallyLongInt2Call(rli, X);
		}	
	}

	/**
	 * Returns the value of a ReallyLongInt as a String
	 * @return A String of digits in the linked list, displayed backwards to put the most significant digit first 
	 */
	@Override
	public String toString()
	{
		String str = "";
		if(this.getLength() == 0)
			return str;
		Node OGNode = this.firstNode.getPrevNode();
		Node curr = this.firstNode.getPrevNode();
		if(this.getLength() == 1)
			return str + curr.data;
		str = str + curr.data;
		curr = curr.getPrevNode();
		str = toStringCall(curr, str, OGNode);
		return str;
	}

	//Recursive call for toString method
	private String toStringCall(Node curr, String str, Node OGNode) {
		if(curr == OGNode)
			return str;
		else {
			str = str + curr.data;
			curr = curr.getPrevNode();
			return toStringCall(curr, str, OGNode);
		}
	}

	/**
	 * Returns the sum of two ReallyLongInts
	 * @param rightOp, a ReallyLongInt to be added to the current argument
	 * @return new ReallyLongInt which is the sum of the current list and the argument
	 */
	public ReallyLongInt2 add(ReallyLongInt2 rightOp)
	{
		if(rightOp.getLength() == 0)
			return this;
		if(this.getLength() == 0)
			return rightOp;
		ReallyLongInt2 sumList = new ReallyLongInt2();
		Node shorter = this.lesserList(rightOp).firstNode;
		Node longer = this.greaterList(rightOp).firstNode;
		Node longOG = this.greaterList(rightOp).firstNode;
		Node shortOG = this.lesserList(rightOp).firstNode;
		boolean needToAdd = false;
		if(shorter.data+longer.data >= 10) {
			sumList.add(shorter.data+longer.data - 10);
			needToAdd = true;
			if(this.greaterList(rightOp).getLength() == 1) {
				sumList.add(1);
				return sumList;
			}
		}
		else {
			sumList.add(shorter.data+longer.data);
		}
		shorter = shorter.next;
		longer = longer.next;
		sumList = addCall(longer, shorter, sumList, needToAdd, longOG, shortOG); 
		return sumList;
	}

	//Recursive call for add method
	private ReallyLongInt2 addCall(Node longer, Node shorter, ReallyLongInt2 sumList, boolean needToAdd, Node longOG, Node shortOG) {
		if(shorter != shortOG) {
			if(needToAdd) {		
				if(shorter.data+longer.data+1 >= 10) {
					sumList.add(shorter.data+longer.data+1 - 10);
					needToAdd = true;
				}
				else {
					sumList.add(shorter.data+longer.data+1);
					needToAdd = false;
				}
			}
			else if(shorter.data+longer.data >= 10) {
				sumList.add(shorter.data+longer.data - 10);
				needToAdd = true;
			}
			else {
				sumList.add(shorter.data+longer.data);
			}
			shorter = shorter.next;
			longer = longer.next;
			return addCall(longer, shorter, sumList, needToAdd, longOG, shortOG);
		}
		if(longer == longOG && needToAdd) {
			sumList.add(1);
			return sumList;
		}
		if(longer != longOG) {
			if(needToAdd) {
				if(longer.data + 1 >= 10) {
					sumList.add(longer.data + 1 -10);
					needToAdd = true;
				}
				else {
					sumList.add(longer.data + 1);
					needToAdd = false;
				}
			}
			else if(longer.data >= 10) {
				sumList.add(longer.data-10);
				needToAdd = true;
			}
			else
				sumList.add(longer.data);
			longer = longer.next;
			return addCall(longer, shorter, sumList, needToAdd, longOG, shortOG);
		}
		if(needToAdd) 
			sumList.add(1);
		return sumList;

	}

	/**
	 * Return new ReallyLongInt which is difference of current and argument
	 * @param rightOp, a ReallyLongInt to be subtracted from the current argument 
	 * @return A ReallyLongInt that is the difference of the current argument and rightOp
	 */
	public ReallyLongInt2 subtract(ReallyLongInt2 rightOp)
	{
		if(this.compareTo(rightOp) == -1)
			throw new ArithmeticException();
		ReallyLongInt2 differenceList = new ReallyLongInt2();
		if(this.compareTo(rightOp) == 0) { 
			differenceList.add(0);
			return differenceList;
		}
		if(rightOp.getLength() == 0) { 
			return this;
		}
		else { 
			Node rhsCurr = rightOp.firstNode;
			Node curr = this.firstNode;
			Node OGCurr = this.firstNode;
			Node OGRhs = rightOp.firstNode;
			boolean subtract = false;
			if(curr.data - rhsCurr.data < 0) { 
				differenceList.add(curr.data + 10 - rhsCurr.data);
				subtract = true;
			}
			else {
				differenceList.add(curr.data - rhsCurr.data);
			}
			if(this.getLength() == 1 && subtract) {
				differenceList.firstNode.data = (-1)*differenceList.firstNode.data;
				return differenceList;
			}
			curr = curr.next;
			rhsCurr = rhsCurr.next;
			differenceList = subtractCall(differenceList, curr, rhsCurr, OGRhs, OGCurr, subtract);
			return differenceList;
		}
	}

	//Recursive call for subtract method
	private ReallyLongInt2 subtractCall(ReallyLongInt2 differenceList, Node curr, Node rhsCurr, Node OGRhs, Node OGCurr, boolean subtract) {
		if(rhsCurr != OGRhs) {
			if(subtract) {
				if(curr.data-rhsCurr.data-1 < 0) {
					differenceList.add(curr.data -1 + 10 - rhsCurr.data);
					curr = curr.next;
					rhsCurr = rhsCurr.next;
					subtract = true;
				}
				else {
					differenceList.add(curr.data-rhsCurr.data-1);
					curr = curr.next;
					rhsCurr = rhsCurr.next;
					subtract = false;
				}
			}
			else if(curr.data - rhsCurr.data < 0) {  
				differenceList.add(curr.data + 10 - rhsCurr.data);
				curr = curr.next;
				rhsCurr = rhsCurr.next;
				subtract = true;
			}
			else {
				differenceList.add(curr.data - rhsCurr.data);
				curr = curr.next;
				rhsCurr = rhsCurr.next;
			}
			return subtractCall(differenceList, curr, rhsCurr, OGRhs, OGCurr, subtract);
		}
		if(curr != OGCurr) {
			if(subtract) {
				if(curr.data-1 < 0) {
					differenceList.add(curr.data-1+10);
					curr = curr.next;
					subtract = true;
				}
				else {
					differenceList.add(curr.data-1);
					curr = curr.next;
					subtract = false;
				}
			}
			else{
				differenceList.add(curr.data);
				curr = curr.next;
			}
			return subtractCall(differenceList, curr, rhsCurr, OGRhs, OGCurr, subtract);
		}
		if(subtract) {
			differenceList.firstNode.getPrevNode().data = (-1)*differenceList.firstNode.getPrevNode().data;
		}
		if(differenceList.firstNode.getPrevNode().data == 0)
			differenceList = clearLeadingZeroes(differenceList);
		return differenceList;
	}

	/**
	 * Returns a new ReallyLongInt which is the product of the current and argument values
	 * @param rightOp, a ReallyLongInt to be multiplied with the current list
	 * @return A ReallyLongInt that is the product of the current value and the argument
	 */
	public ReallyLongInt2 multiply(ReallyLongInt2 rightOp)
	{
		if(rightOp.getLength() == 0) {
			return this;
		}
		ReallyLongInt2 multList = new ReallyLongInt2();
		if(this.firstNode.getPrevNode().data == 0 || rightOp.firstNode.getPrevNode().data == 0) {
			multList.add(0);
			return multList;
		}
		Node curr = this.firstNode;
		Node rhsCurr = rightOp.firstNode;
		int tensCount = 0;
		int temp = 0;
		int n = this.getLength();
		int m = rightOp.getLength();
		multList = multCall(multList, curr, rhsCurr, m, n, temp, tensCount);
		return multList;
	}

	//Recursive call for multiply method
	private ReallyLongInt2 multCall(ReallyLongInt2 multList, Node curr, Node rhsCurr, int m, int n, int temp, int tensCount) {
		if(m>0) {
			if(n>0) {
				temp = tensCount;
				ReallyLongInt2 subProd = new ReallyLongInt2(curr.data*rhsCurr.data);
				if(temp>0) {
					subProdCall(subProd, temp);
				}
				multList = multList.add(subProd);
				curr = curr.next;
				tensCount++;
				n--;
				return multCall(multList, curr, rhsCurr, m, n, temp, tensCount);
			}
			n = this.getLength();
			tensCount = tensCount-n;
			tensCount++;
			rhsCurr = rhsCurr.next;
			m--;
			return multCall(multList, curr, rhsCurr, m, n, temp, tensCount);
		}
		return multList; 
	}

	//Recursive call for multiply method, generates a subproduct
	private ReallyLongInt2 subProdCall(ReallyLongInt2 subProd, int temp) {
		if(temp>0) {
			subProd.add(1,0);
			temp--;
			return subProdCall(subProd, temp);
		}
		else return subProd;
	}

	/**
	 * Compares ReallyLongInts and determines which is larger
	 * @param rOp, a ReallyLongInt to be compared to 
	 * @return An int. -1 if rOp is greater than current, 0 if they are equal, 1 is current is greater than rOp.
	 */
	public int compareTo(ReallyLongInt2 rOp)
	{
		if(this.getLength() != rOp.getLength()) {
			if(this.getLength() > rOp.getLength())
				return 1;
			if(this.getLength() < rOp.getLength())
				return -1;
		}
		Node OGNode = this.firstNode.getPrevNode();
		Node curr = this.firstNode.getPrevNode();
		Node rhsCurr = rOp.firstNode.getPrevNode();
		if(curr.data > rhsCurr.data)
			return 1;
		else if(curr.data < rhsCurr.data)
			return -1;
		else {
			curr = curr.getPrevNode();
			rhsCurr = rhsCurr.getPrevNode();
			int output = compareToCall(curr, rhsCurr, OGNode);
			return output;
		}
	}

	//Recursive call for compareTo method
	private int compareToCall(Node curr, Node rhsCurr, Node OGNode) {
		if(curr == OGNode)
			return 0;
		else {
			if(curr.data > rhsCurr.data)
				return 1;
			else if(curr.data < rhsCurr.data)
				return -1;
			else{
				curr = curr.getPrevNode();
				rhsCurr = rhsCurr.getPrevNode();
				return compareToCall(curr, rhsCurr, OGNode);
			}
		}
	}

	/**
	 * Describes whether two ReallyLongInts are equal
	 * @param rightOp, a ReallyLongInt to compare to. 
	 * It is designated as an Object here to override the .equals() method in the Object class
	 * @return A boolean describing whether two ReallyLongInts are equal
	 */
	@Override 
	public boolean equals(Object rightOp)
	{
		if(this.compareTo((ReallyLongInt2) rightOp) == 0){
			return true;
		}
		else 
			return false;
	}

	/**
	 * Compares ReallyLongInts and returns the greater ReallyLongInt
	 * @param rhs, a ReallyLongInt to be compared to 
	 * @return The greater ReallyLongInt between the argument and current value
	 */
	public ReallyLongInt2 greaterList (ReallyLongInt2 rhs) {
		ReallyLongInt2 dummyList = new ReallyLongInt2();
		dummyList.add(0);
		if(this.compareTo(rhs) == 0)
			return this;
		if (this.compareTo(rhs) == 1)
			return this;
		if (this.compareTo(rhs) == -1)
			return rhs;
		return dummyList;
	}

	/**
	 * Compares ReallyLongInts and returns the lesser ReallyLongInt
	 * @param rhs, a ReallyLongInt to be compared to 
	 * @return The lesser ReallyLongInt between the argument and current value
	 */
	public ReallyLongInt2 lesserList (ReallyLongInt2 rhs) {
		ReallyLongInt2 dummyList = new ReallyLongInt2();
		dummyList.add(0);
		if(this.compareTo(rhs) == 0)
			return rhs;
		if (this.compareTo(rhs) == 1)
			return rhs;
		if (this.compareTo(rhs) == -1)
			return this;
		return dummyList;
	}

	//Clears leading zeroes from a ReallyLongInt
	public ReallyLongInt2 clearLeadingZeroes(ReallyLongInt2 rli) {
		Node curr = rli.firstNode.getPrevNode();
		rli = zeroCall(curr, rli);
		return rli;
	}

	//Recursive call for clearLeadingZeroes method
	private ReallyLongInt2 zeroCall(Node curr, ReallyLongInt2 rli) {
		if(curr.data != 0) {
			return rli;
		}
		else {
			curr = curr.getPrevNode();
			rli.remove(rli.getLength());
			return zeroCall(curr, rli);
		}
	}
}
