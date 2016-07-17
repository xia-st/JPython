package pers.xia.jpython.grammar.pgen;

import pers.xia.jpython.object.PyExceptions;

class _Grammar
{
    static final int DFAMAXSIZE = 256;
    static final int LABELMAXSIZE = 512;
    
    int ndfas;
    _DFA[] dfas;
    
    int nlabels;
    _Label[] labels;
    
    _DFA start;
    
    public _Grammar()
    {
        this.ndfas = 0;
        this.dfas = new _DFA[_Grammar.DFAMAXSIZE];
        this.nlabels = 0;
        this.labels = new _Label[_Grammar.LABELMAXSIZE];
    }
    
    public _Grammar(int ndfas, _DFA[] dfas, int nlabels, _Label[] labels, _DFA start)
    {
        this.ndfas = ndfas;
        this.dfas = dfas;
        this.nlabels = nlabels;
        this.labels = labels;
        this.start = start;
    }
    
    private boolean addLabel(_Label label)
    {
        if(nlabels >= this.labels.length)
        {
            throw new PyExceptions("Over the max size of label");
        }
        this.labels[this.nlabels++] = label;
        return true;
    }
    
    public _Label setLabel(_Label label)
    {
        int i = 0;
        for(; i < this.nlabels; i++)
        {
            if(this.labels[i].cmp(label))
            {
                break;
            }
        }
        
        if(i >= this.nlabels)
        {
            this.addLabel(label);
            return label;
        }
        else
        {
            return this.labels[i];
        }
    }
    
    private boolean addDFA(_DFA dfa)
    {
        if(this.ndfas >= this.dfas.length)
        {
            throw new PyExceptions("Over the max size of dfa");
        }
        this.dfas[this.ndfas++] = dfa;
        return true;
    }
    
    public _DFA setDFA(_DFA dfa)
    {
        int i = 0;
        for(; i < this.ndfas; i++)
        {
            if(this.dfas[i].cmp(dfa))
            {
                break;
            }
        }
        
        if(i >= this.ndfas)
        {
            this.addDFA(dfa);
            return dfa;
        }
        else
        {
            return this.dfas[i];
        }
    }
    
    public _DFA getDFA(String DFAName)
    {
        for(int i = 0; i < this.ndfas; i++)
        {
            if(this.dfas[i].name.equals(DFAName))
            {
                return this.dfas[i];
            }
        }
        return null;
    }
    
}
