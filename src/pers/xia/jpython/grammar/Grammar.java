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
        
        for(int i = 0; i < state.narcs; i++)
        {
            int lbl = a[i].label;
            Label l = this.getLabel(lbl);
            
            if(l.isTerminal)
            {
                accel[lbl] = a[i].nextState;
            }
            else if(l.nextDfa == -1)
            {
                state.accept = true;
            }
            else
            {
                DFA dfa = this.getDFA(l.nextDfa);

                for(int j = 0; j < dfa.acceptLabel.length; j++)
                {
                    accel[dfa.acceptLabel[j]] = l.nextDfa << 8 | 1 << 7 |
                            a[i].nextState;
                }
            }
        }
        
        int lower; //标记最小的label下标
        int upper;    //标记最大的label下标
        
        //获取最小坐标和最大坐标
        for(lower = 0; lower < this.nlabels && accel[lower] == -1; lower++);
        for(upper = this.nlabels; upper >= 0 && upper > lower && accel[upper-1] == -1; upper--);

        //不存在有效边
        if(upper == lower)
        {
            return;
        }
        
        state.lower = lower;
        state.upper = upper;
        
        //将生成的数组放到state里面
        state.accel = new int[state.upper - state.lower];
        for(int i = 0; i < upper - lower; i++)
        {
            state.accel[i] = accel[i + lower];
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
    
    public Label getLabel(int index)
    {
        if(index < 0 || index >= this.nlabels)
        {
            throw new PyExceptions("Grammar: Out of Index while get DFA");
        }
        return this.labels[index];
    }
    
}
