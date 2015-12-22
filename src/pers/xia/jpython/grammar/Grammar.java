package pers.xia.jpython.grammar;

public class Grammar
{
    public int ndfas;
    public DFA[] dfas;
    
    public int nlabels;
    public Label[] labels;
    
    public DFA start;
    
    public Grammar(int ndfas, DFA[] dfas, int nlabels, Label[] labels, DFA start)
    {
        this.ndfas = ndfas;
        this.dfas = dfas;
        this.nlabels = nlabels;
        this.labels = labels;
        this.start = start;
    }
}
