package pers.xia.jpython.grammar;

public class Arc
{
    public int label;
    public int nextState;
    
    public Arc(int label, int nextState)
    {
        this.label = label;
        this.nextState = nextState;
    }
}
