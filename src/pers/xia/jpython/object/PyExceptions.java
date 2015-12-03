package pers.xia.jpython.object;

import java.util.Stack;

public class PyExceptions extends RuntimeException
{
    public Stack<String> map = new Stack<String>();
    public PyExceptions(String msg)
    {
    	super(msg);
    	map.push(msg);
    }
    
    public String toString()
    {
    	String msg = "";
    	while(!map.empty())
    	{
    		msg += map.pop() + "\n";
    	}
    	return msg;
    }
}
