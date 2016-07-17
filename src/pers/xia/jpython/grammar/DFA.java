package pers.xia.jpython.grammar;

import pers.xia.jpython.object.PyExceptions;

public class DFA
{
    public DFAType name;    //DFA name
    public int initial;    //Initial state,
    public int nstates;    //the number of state
    public State[] states;    //states for DFA
    public int[] acceptLabel;    //保存跳转到当前DFA

    
    public DFA(DFAType name, int initial, int nstates, State[] states, int[] acceptLabel)
    {
        this.name = name;
        this.initial = initial;
        this.nstates = nstates;
        this.states = states;
        this.acceptLabel = acceptLabel;
    }
    
    public State getState(int index)
    {
        if(index >= nstates || index < 0)
        {
            throw new PyExceptions("DFA: Out of Index");
        }
        return this.states[index];
    }
}
