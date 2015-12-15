package pers.xia.jpython.grammar;

public class DFA
{
	DFAName name;	//DFA name
	int initial;	//Initial state,
	int nstates;	//the number of state
	State[] states;	//states for DFA
	
	public DFA(DFAName name, int initial, int nstates, State[] states)
	{
		this.name = name;
		this.initial = initial;
		this.nstates = nstates;
		this.states = states;
	}
}
