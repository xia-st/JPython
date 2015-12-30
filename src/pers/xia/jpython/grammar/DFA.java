package pers.xia.jpython.grammar;

import java.util.Map;

public class DFA
{
    public DFAName name;    //DFA name
    public int initial;    //Initial state,
    public int nstates;    //the number of state
    public State[] states;    //states for DFA
    public Map<Integer, Integer> jumpDFA;    //当跳转到本DFA时根据lebel判断实际需要跳转的DFA

    
    public DFA(DFAName name, int initial, int nstates, State[] states, Map<Integer, Integer> jumpDFA)
    {
        this.name = name;
        this.initial = initial;
        this.nstates = nstates;
        this.states = states;
        this.jumpDFA = jumpDFA;
    }
    
    public int getNextDFA(int label)
    {
        return this.jumpDFA.get(label);
    }
}
