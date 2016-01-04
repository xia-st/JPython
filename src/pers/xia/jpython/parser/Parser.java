package pers.xia.jpython.parser;

import java.io.File;
import java.util.Stack;

import org.apache.log4j.Logger;

import pers.xia.jpython.grammar.Arc;
import pers.xia.jpython.grammar.DFA;
import pers.xia.jpython.grammar.GramInit;
import pers.xia.jpython.grammar.Grammar;
import pers.xia.jpython.grammar.Label;
import pers.xia.jpython.grammar.State;
import pers.xia.jpython.object.PyExceptions;
import pers.xia.jpython.tokenizer.TokState;
import pers.xia.jpython.tokenizer.Token;
import pers.xia.jpython.tokenizer.Tokenizer;

public class Parser
{
    class StackEntry    //栈中的元素
    {
        DFA dfa; //所属的DFA
        int curState;  //当前的state
        Node parentNode = null; //当前的父结点，用于连接下面的结点
    }

    Stack<StackEntry> stack;    // DFA的状态栈
    Grammar grammar;    //使用的grammar
    Node tree;  //CST树
    
    private Logger log;
    
    public Parser(Grammar grammar)
    {
        this(grammar, -1);
    }
    
    public Parser(Grammar grammar, int start)
    {
        this.log = Logger.getLogger(Parser.class);
        
        StackEntry stackEntry = new StackEntry();
        
        if(start < 0) start = grammar.start;
       
        stackEntry.dfa = grammar.getDFA(start);
        stackEntry.curState = grammar.getDFA(start).initial;
        stackEntry.parentNode = new Node(grammar.getDFA(start).name);
        
        stack.push(stackEntry);
        this.grammar = grammar;
        this.tree = stackEntry.parentNode;
    }
    
    //根据Token确定相应的label
    private int classify(Token token)
    {
        /* 
         * 如果token的state是NAME的话需要考虑为关键字还是普通的NAME，
         * 做法是如果遇到label(NAME, null)的话把他提取出来，没找到目标Label的
         * 情况下就返回这个label
         */
        if(token.state == TokState.NAME)
        {
            int label = -1; // 保存(NAME, null)这个label
            
            for(int i = 0; i < this.grammar.nlabels; i++)
            {
                if(this.grammar.labels[i].tokState == TokState.NAME)
                {
                    if(this.grammar.labels[i].str == null)
                    {
                        label = i;
                        continue;
                    }
                    if(this.grammar.labels[i].str.equals(token.str))
                    {
                        log.info(token.str + "is a key word");
                        return i;
                    }
                }
            }
            if(label == -1) throw new PyExceptions("Illegal token", token);
            log.info(token.str + " is a token we know");
            return label;
        }
        
        /* 
         * 对于普通的label只需要匹配到label的tokState即可
         */
        for(int i = 0; i < this.grammar.nlabels; i++)
        {
            if(this.grammar.labels[i].tokState == token.state)
            {
                log.info(token.str + "is a key word");
                return i;
            }
        }
        throw new PyExceptions("Illegal token", token);
    }
    
    //设置下一个结点
    private void shift(TokState tokState, int nextState, String str, int lineNo, int colOffset)
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
        int _label = this.classify(token);
        Label label = this.grammar.getLabe(_label);
        
        // 马上修改掉！！！！！！！
        for(;;)
        {
            //TODO add token
            throw new PyExceptions("Illigal token: ", token);
        }
    }
    
    public static void main(String[] args)
    {
        File file = new File("test.py");
        Parser parser = new Parser(GramInit.grammar);
        Tokenizer tokenizer = new Tokenizer(file);
        try
        {
            Token tok = tokenizer.nextToken();
            int colOffset = 0;
            while(tok.state != TokState.ENDMARKER)
            {
                parser.AddToken(tok, colOffset);
                if(tok.state == TokState.NEWLINE)
                {
                    colOffset = 0;
                }
                else
                {
                    colOffset++;
                }
                tok = tokenizer.nextToken();
            }
        }
        catch(PyExceptions e)
        {
            e.printStackTrace();
        }
    }
}
