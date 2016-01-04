package pers.xia.jpython.grammar;

import pers.xia.jpython.object.PyExceptions;

public class Grammar
{
    public int ndfas;   // dfa总数
    public DFA[] dfas;  // 保存dfa的数组
    
    public int nlabels; // label总数
    public Label[] labels;  // 保存label的数组
    
    public int start;   //起始dfa
    
    public boolean accel; // 加速器 set if accelerators present
        
    public Grammar(int ndfas, DFA[] dfas, int nlabels, Label[] labels, int start)
    {
        this.ndfas = ndfas;
        this.dfas = dfas;
        this.nlabels = nlabels;
        this.labels = labels;
        this.start = start;
    }
    
    private void fixState(State state)
    {
        int accel[] = new int[this.nlabels];
        for(int i = 0; i < this.nlabels; i++)
        {
            accel[i] = -1;
        }
        
        Arc a[] = state.arcs;
        
        for(int i = 0; i < this.nlabels; i++)
        {
            int lbl = a[i].label;
            Label l = this.getLabe(lbl);
            if(l.isTerminal)
            {
                accel[lbl] = i;
            }
            else if(l.nextDfa == -1)
            {
                state.accept = true;
            }
            else
            {
                DFA dfa = this.getDFA(l.nextDfa);
                if(dfa.getNextDFA(lbl) != -1)
                {
                    accel[lbl] = l.nextDfa + State.MAXNARCS;
                }
            }
        }
        
    }
    
    private void fixDFA(DFA dfa)
    {
        for(int i = 0; i < dfa.nstates; i++)
        {
            fixState(dfa.states[i]);
        }
    }
    
    public void addAccelerators()
    {
        for(int i = 0; i < this.ndfas; i++)
        {
            fixDFA(this.dfas[i]);
        }
        this.accel = true;
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
