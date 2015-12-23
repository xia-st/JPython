package pers.xia.jpython.grammar;

public class Arc
{
    public Label label;
    public State nextState;
    
    public Arc(Label label, State nextState)
    {
        this.label = label;
        this.nextState = nextState;
    }
}
