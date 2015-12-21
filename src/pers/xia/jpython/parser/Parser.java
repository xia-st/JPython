package pers.xia.jpython.parser;

import java.util.Stack;

import pers.xia.jpython.grammar.DFA;
import pers.xia.jpython.grammar.Grammar;
import pers.xia.jpython.grammar.State;

public class Parser
{
    class StackEntry
    {
        DFA dfa;
        State curState;
    }

    Stack<StackEntry> stack;
    Grammar grammar;
    Node node;
}
