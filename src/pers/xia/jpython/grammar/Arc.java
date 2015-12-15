package pers.xia.jpython.grammar;

public class Arc
{
	Label label;
	State nextState;
	
	public Arc(Label label, State nextState)
	{
		this.label = label;
		this.nextState = nextState;
	}
}
