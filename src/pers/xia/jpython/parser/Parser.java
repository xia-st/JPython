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
    class StackEntry    //栈中的元素
    {
        DFA dfa = null; //所属的DFA
        State curState = null;  //当前的state
        Node parentNode = null; //当前的父结点，用于连接下面的结点
    }

    Stack<StackEntry> stack;    // DFA的状态栈
    Grammar grammar;    //使用的grammar
    Node tree;  //CST树
    
    private Logger log;
    
    public Parser(Grammar grammar)
    {
        this(grammar, null);
    }
    
    public Parser(Grammar grammar, DFA start)
    {
        this.log = Logger.getLogger(Parser.class);
        
        StackEntry stackEntry = new StackEntry();
        
        if(start == null) start = grammar.start;
       
        stackEntry.dfa = start;
        stackEntry.curState = start.initial;
        stackEntry.parentNode = new Node(start.name);
        
        stack.push(stackEntry);
        this.grammar = grammar;
        this.tree = stackEntry.parentNode;
    }
    
    //根据Token确定相应的label
    private Label classify(Token token)
    {
        /* 
         * 如果token的state是NAME的话需要考虑为关键字还是普通的NAME，
         * 做法是如果遇到label(NAME, null)的话把他提取出来，没找到目标Label的
         * 情况下就返回这个label
         */
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
                        log.debug(token.str + "is a key word");
                        return this.grammar.labels[i];
                    }
                }
            }
            if(label == null) throw new PyExceptions("Illegal token", token);
            log.debug("It is a token we know");
            return label;
        }
        
        /* 
         * 对于普通的label只需要匹配到label的tokState即可
         */
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
    
    //设置下一个结点
    private void shift(TokState tokState, State nextState, String str, int lineNo, int colOffset)
    {
        StackEntry stackEntry = this.stack.peek();
        stackEntry.parentNode.addChild(tokState, str, lineNo, colOffset);
        stackEntry.curState = nextState;
    }
    
    //添加一个新的stackEntry
    private void push(DFA nextDFA, int lineNo, int colOffset)
    {
        StackEntry stackEntry = this.stack.peek();
        
        stackEntry.parentNode.addChild(nextDFA.name, lineNo, colOffset);
        
        Node node = stackEntry.parentNode.getChild(-1);
        
        StackEntry newSE = new StackEntry();
        newSE.dfa = nextDFA;
        newSE.curState = nextDFA.initial;
        newSE.parentNode = node;
        
        this.stack.push(newSE);
    }
    
    public void AddToken(Token token, int colOffset)
    {
        Label label = this.classify(token);
        
        
        for(;;)
        {
            StackEntry stackEntry = this.stack.peek();
        
            int narcs = stackEntry.curState.narcs;

            boolean finalState = false;
            
            //遍历每一条边
            for(int i = 0; i < narcs; i++)
            {
                Arc arc = stackEntry.curState.arcs[i];
                /*
                 * 如果是终结符的话将其放入到当前父结点，否则就创建新的
                 * stackEntry，连接新的子树
                 */
                if(arc.label.isTerminal)
                {
                    if(arc.label == label)
                    {
                        this.shift(label.tokState, arc.nextState, token.str, token.lineNo, colOffset);
                        return;
                    }
                }
                else
                {
                    if(arc.label.nextDfa == null)
                    {
                        finalState = true; 
                        continue;
                    }
                    DFA nextDFA = arc.label.nextDfa;

                    //一层层遍历下去，每搜索一层DFA就添加一次结点，直到找到最后的结点。
                    DFA nnextDFA = nextDFA.getNextDFA(label);

                    if(nnextDFA == null) continue;

                    while(nextDFA != nnextDFA)
                    {
                        this.push(nextDFA, token.lineNo, colOffset);
                        nextDFA = nnextDFA;
                    }
                    this.push(nextDFA, token.lineNo, colOffset);

                    StackEntry stackEntry2 = this.stack.peek();

                    int narcs2 = stackEntry2.curState.narcs;
                    for(int j = 0; j < narcs2; j++)
                    {
                        Arc arc2 = stackEntry.curState.arcs[i];
                        if(arc2.label == label)
                        {
                            this.shift(label.tokState, arc2.nextState, token.str, token.lineNo, colOffset);
                            return;
                        }
                    }
                    throw new PyExceptions("grammar ERROR");
                }
            }
            
            //如果每找到对应的label且以及到达终结符的话就pop出结点，从前一个结点继续查找
            if(finalState)
            {
                this.stack.pop();
                if(this.stack.empty())
                {
                    log.debug("accept");
                    return;
                }
                continue;
            }
            throw new PyExceptions("Illigal token: ", token);
        }
    }
}
