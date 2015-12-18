package pers.xia.jpython.grammar;

public class Grammar
{
    int ndfas;
    DFA[] dfas;
    
    int nlabels;
    Label[] labels;
    
    DFA start;
    
    public Grammar(int ndfas, DFA[] dfas, int nlabels, Label[] labels, DFA start)
    {
        this.ndfas = ndfas;
        this.dfas = dfas;
        this.nlabels = nlabels;
        this.labels = labels;
        this.start = start;
    }
}
