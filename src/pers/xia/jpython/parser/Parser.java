package pers.xia.jpython.parser;

import java.util.Stack;

import org.apache.log4j.Logger;

import pers.xia.jpython.grammar.DFA;
import pers.xia.jpython.grammar.Grammar;
import pers.xia.jpython.grammar.Label;
import pers.xia.jpython.grammar.State;
import pers.xia.jpython.object.PyExceptions;
import pers.xia.jpython.tokenizer.TokState;
import pers.xia.jpython.tokenizer.Token;

public class Parser
{
    class StackEntry
    {
        DFA dfa;
        State curState;
        Node parentNode;
        
        public StackEntry()
        {
            dfa = null;
            curState = null;
            parentNode = null;
        }
    }

    Stack<StackEntry> stack;
    Grammar grammar;
    Node tree;
    
    private Logger log;
    
    public Parser(Grammar grammar)
    {
        this(grammar, null);
    }
    
    public Parser(Grammar grammar, DFA start)
    {
        this.log = Logger.getLogger(Parser.class);
        
        StackEntry stackEntry = new StackEntry();
        
        if(start == null)
        {
            stackEntry.dfa = grammar.start;
        }
        else
        {
            stackEntry.dfa = start;
        }
        
        stackEntry.curState =stackEntry.dfa.initial;
        stack.push(stackEntry);
    }
    
    //根据Token确定相应的label
    private Label classify(Token token)
    {
        if(token.state == TokState.NAME)
        {
            for(int i = 0; i < this.grammar.nlabels; i++)
            {
                if(this.grammar.labels[i].tokState == TokState.NAME &&
                        this.grammar.labels[i].str.equals(token.str))
                {
                    log.debug(token.str + "is a token");
                    return this.grammar.labels[i];
                }
            }
        }
        
        for(int i = 0; i < this.grammar.nlabels; i++)
        {
            if(this.grammar.labels[i].tokState == token.state)
            {
                log.debug(token.str + "is a token");
                return this.grammar.labels[i];
            }
        }
        throw new PyExceptions("Illegal token", token);
    }
    
    private void shift()
    {
        
        //TODO
    }
    
    private void push()
    {
        //TODO
    }
    
    public void AddToken(Token token)
    {
        
    }
}
