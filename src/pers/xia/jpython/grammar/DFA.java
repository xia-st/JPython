package pers.xia.jpython.grammar;

import java.util.Map;

public class DFA
{
    DFAName name;    //DFA name
    int initial;    //Initial state,
    int nstates;    //the number of state
    State[] states;    //states for DFA
    Map<Label, DFA> jumpedDFAs;    //当跳转到本DFA时根据lebel判断实际需要跳转的DFA

    
    public DFA(DFAName name, int initial, int nstates, State[] states)
    {
        this.name = name;
        this.initial = initial;
        this.nstates = nstates;
        this.states = states;
    }
}
