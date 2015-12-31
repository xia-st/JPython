package pers.xia.jpython.grammar;

import pers.xia.jpython.object.PyExceptions;

public class Grammar
{
    public int ndfas;
    public DFA[] dfas;
    
    public int nlabels;
    public Label[] labels;
    
    public int start;
    
    public Grammar(int ndfas, DFA[] dfas, int nlabels, Label[] labels, int start)
    {
        this.ndfas = ndfas;
        this.dfas = dfas;
        this.nlabels = nlabels;
        this.labels = labels;
        this.start = start;
    }
    
    public DFA getDFA(int index)
    {
        if(index < 0 || index >= this.ndfas)
        {
            throw new PyExceptions("Grammar: Out of Index while get DFA");
        }
        return this.dfas[index];
    }
    
    public Label getLabe(int index)
    {
        if(index < 0 || index >= this.ndfas)
        {
            throw new PyExceptions("Grammar: Out of Index while get DFA");
        }
        return this.labels[index];
    }
    
}
