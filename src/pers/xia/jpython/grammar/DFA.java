package pers.xia.jpython.grammar;

import java.util.Map;

public class DFA
{
    public DFAName name;    //DFA name
    public State initial;    //Initial state,
    public int nstates;    //the number of state
    public State[] states;    //states for DFA
    public Map<Label, DFA> jumpDFA;    //当跳转到本DFA时根据lebel判断实际需要跳转的DFA

    
    public DFA(DFAName name, State initial, int nstates, State[] states, Map<Label, DFA> jumpDFA)
    {
        this.name = name;
        this.initial = initial;
        this.nstates = nstates;
        this.states = states;
        this.jumpDFA = jumpDFA;
    }
}
