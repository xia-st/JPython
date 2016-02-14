package pers.xia.jpython.parser;

import pers.xia.jpython.ast.*;
import pers.xia.jpython.object.PyExceptions;
public class Ast
{
    public modType fromNodeObject(Node n)
    {
        if(!n.isDFAType)
        {
            throw new PyExceptions("cst's top node is not nonterminal node");
        }
        
        modType m = null;
        
        switch(n.dfaType)
        {
        case file_input:
        case eval_input:
        case single_input:
        default:
            break;
        }
        
        return null;
        
    }
}
