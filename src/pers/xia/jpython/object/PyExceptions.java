package pers.xia.jpython.object;

import java.util.Stack;

import pers.xia.jpython.parser.Token;

public class PyExceptions extends RuntimeException
{
    public Stack<String> map = new Stack<String>();
    public Token tok;
    
    public PyExceptions()
    {
    	super();
    }
    
    public PyExceptions(String msg)
    {
    	super(msg);
    	map.push(msg);
    }
      public PyExceptions(String message, Throwable cause) {
        super(message, cause);
    }
      
    public PyExceptions(Token tok)
    {
    	super();
    	this.tok = tok;
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
    
    public void printStackTrace()
    {
    	String msg =  "";
    	while(!map.empty())
    	{
    		msg += map.pop() + "\n";
    	}
    	if(msg.length() > 0)
    	{
    		System.err.println(msg);
    	}
    	else 
    	{
    		super.printStackTrace();
		}
    }
}
