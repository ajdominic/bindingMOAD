/*Utilizing a stack to store MOAD data*/

import java.util.ArrayList;

public class Stack 
{
	private ArrayList<Data> stack;
	
	// Constructor
	public Stack(){stack = new ArrayList<Data>();}
	
	// set methods
	public void push(Data value) {stack.add(value);}
	
	// get methods
	public Data pop()
	{
		Data retVal = null;
		if (!isEmpty())
		{
			retVal = stack.get(stack.size()-1);
			stack.remove(stack.size()-1);
		}
		
		return retVal;
	} // end pop
	
	// utility methods
	public boolean isEmpty(){return (stack.size() == 0);}
	
	// Not really necessary, but it is part of the traditional
	// stack that we discussed in class
	public Data peek()
	{
		Data retVal = null;
		if (!isEmpty())
		{
			retVal = pop();
			push(retVal);
		}
		
		return retVal;
	}
}