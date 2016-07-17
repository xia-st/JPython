package pers.xia.jpython.grammar;

import pers.xia.jpython.tokenizer.TokState;

public class Label
{
    //DFAName dfaName;  //DFA的名字
    public int nextDfa;        //下一个DFA
    
    public TokState tokState;
    public String str;
    
    public boolean isTerminal;
    
    public Label(int nextDfa)
    {
        //this.dfaName = dfaName;
        this.nextDfa = nextDfa;
        this.isTerminal = false;
    }

    public Label(TokState tokState, String str)
    {
        this.tokState = tokState;
        this.str = str;
        this.isTerminal = true;
    }
}
