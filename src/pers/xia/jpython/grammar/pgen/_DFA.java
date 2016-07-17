package pers.xia.jpython.grammar.pgen;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import pers.xia.jpython.object.PyExceptions;

class _DFA
{
    
    String name;        //DFA name
    _State initial;     //Initial state,
    int nstates;        //the number of state
    // 这里其实用List操作起来会方便很多。。。一开始没设计好后面也不好修改，还好这里对最终代码没影响
    _State[] states;        //states for DFA 
    Map<_Label, _DFA> jumpedDFAs;    //当跳转到本DFA时根据lebel判断实际需要跳转的DFA
    
    static final int MAXSIZE = 256;
    
    public _DFA(String name)
    {
        if(name == null)
        {
            throw new PyExceptions("_DFA must have name");
        }
        this.name = name;
        this.initial = null;
        this.nstates = 0;
        this.states = new _State[_DFA.MAXSIZE];
        this.jumpedDFAs = null;
    }
    
    public boolean addState(_State state)
    {
        if(this.nstates >= this.states.length)
        {
            throw new PyExceptions("Over the max size of DFA");
        }
        this.states[this.nstates++] = state;
        return true;
    }
    
    public _DFA(String name, _State initial, int nstates, _State[] states)
    {
        this.name = name;
        this.initial = initial;
        this.nstates = nstates;
        this.states = states;
    }
    
    public boolean cmp(_DFA dfa)
    {
        if(this.name.equals(dfa.name))
        {
            return true;
        }
        return false;
    }
    
    public List<_Label> getAllStartLabel()
    {
        List<_Label> labels = new LinkedList<_Label>();
        for(int i = 0; i < this.initial.narcs; i++)
        {
            labels.add(this.initial.arcs[i].label);
        }
        return labels;
    }
    
    public void setStates()
    {
        Set<_State> states = new HashSet<_State>();
        Stack<_State> stateStack = new Stack<_State>();
        
        stateStack.push(this.initial);
        _State state = null;
        while(!stateStack.empty())
        {
            state = stateStack.pop();
            if(states.contains(state))
            {
                continue;
            }
            states.add(state);
            
            for(int i = 0; i < state.narcs; i++)
            {
                if(state.arcs[i].nextState != null)
                    stateStack.push(state.arcs[i].nextState);
            }
        }
        
        int i = 0;
        for(_State s : states)
        {
            this.states[i++] = s;
        }
        this.nstates = i;
        
        //将起始结点放到0号位
        for(i = 0; i < this.nstates; i++)
        {
            if(this.states[i] == this.initial)
            {
                _State s = this.states[0];
                this.states[0] = this.states[i];
                this.states[i] = s;
            }
        }
        
    }
}
