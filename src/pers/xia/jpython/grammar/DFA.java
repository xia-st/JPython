package pers.xia.jpython.grammar;

import java.util.Map;

public class DFA
{
    DFAName name;    //DFA name
    State initial;    //Initial state,
    int nstates;    //the number of state
    State[] states;    //states for DFA
    Map<Label, DFA> jumpDFA;    //当跳转到本DFA时根据lebel判断实际需要跳转的DFA

    
    public DFA(DFAName name, State initial, int nstates, State[] states, Map<Label, DFA> jumpDFA)
    {
        this.name = name;
        this.initial = initial;
        this.nstates = nstates;
        this.states = states;
        this.jumpDFA = jumpDFA;
    }
}
