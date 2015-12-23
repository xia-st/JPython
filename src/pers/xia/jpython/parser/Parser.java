package pers.xia.jpython.parser;

import java.util.Stack;

import org.apache.log4j.Logger;

import pers.xia.jpython.grammar.Arc;
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
        DFA dfa = null;
        State curState = null;
        Node parentNode = null;
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
            Label label = null; // 保存(NAME, null)这个label
            for(int i = 0; i < this.grammar.nlabels; i++)
            {
                if(this.grammar.labels[i].tokState == TokState.NAME)
                {
                    if(this.grammar.labels[i].str == null)
                    {
                        label = this.grammar.labels[i];
                        continue;
                    }
                    if(this.grammar.labels[i].str.equals(token.str))
                    {
                        log.debug(token.str + "is a ke  word");
                        return this.grammar.labels[i];
                    }
                }
            }
            if(label == null) throw new PyExceptions("Illegal token", token);
            log.debug("It is a token we know");
            return label;
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
    
    public void AddToken(Token token, int colOffset)
    {
        Label label = this.classify(token);
        
        StackEntry stackEntry = this.stack.peek(); //获取栈顶元素而不移除
        for(int i = 0; i < stackEntry.curState.narcs; i++)
        {
            Arc arc = stackEntry.curState.arcs[i];
            if(arc.label.isTerminal)
            {
                if(arc.label == label)
                {
                    stackEntry.curState = arc.nextState;
                    
                    Node node = new Node(token.state);
                    node.lineNo = token.lineNo;
                    node.colOffset = colOffset;
                    node.str = token.str;
                    stackEntry.parentNode.addChild(node);
                    return;
                }
            }
            else
            {
                DFA nextDFA = stackEntry.dfa.getNextDFA(arc, label);
                if(nextDFA != null)
                {
                    stackEntry.curState = arc.nextState;
                    
                    Node node = new Node(nextDFA.name);
                    //TODO
                    
                    StackEntry newSE = new StackEntry();
                    newSE.dfa = nextDFA;
                    newSE.curState = nextDFA.initial;
                    newSE.parentNode = node;
                }
            }
        }
    }
}
