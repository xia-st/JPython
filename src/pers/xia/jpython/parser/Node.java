package pers.xia.jpython.parser;

import java.util.ArrayList;
import java.util.List;

import pers.xia.jpython.grammar.DFAType;
import pers.xia.jpython.object.PyExceptions;
import pers.xia.jpython.tokenizer.TokState;

public class Node
{
    boolean isDFAType;
    DFAType dfaType;
    
    String str;
    public int lineNo;
    public int colOffset;
    
    List<Node> childs;

    public Node()
    {
        // empty method
    }

    public Node(DFAType dfaType)
    {
        this.childs = new ArrayList<Node>();
        this.dfaType = dfaType;
        this.isDFAType = true;
    }

    public Node(TokState tokType)
    {
        this.childs = new ArrayList<Node>();
        this.dfaType = DFAType.valueOf(tokType.toString());  //modify TokState to DFAType
        this.isDFAType = false;
    }

    //创建一个node的拷贝
    /*
   private Node(Node node)
   {
       this.isDFAType = node.isDFAType;
       this.dfaType = node.dfaType;
       this.tokType = node.tokType;
       this.str = node.str;
       this.lineNo = node.lineNo;
       this.colOffset = node.colOffset;
   }
     */

    public void addChild(DFAType dfaName, int lineNo, int colOffset)
    {
        Node node = new Node(dfaName);

        node.lineNo = lineNo;
        node.colOffset = colOffset;

        this.childs.add(node);
    }

    public void addChild(TokState tokState, String str, int lineNo, int colOffset)
    {                    
        Node node = new Node(tokState);

        node.lineNo = lineNo;
        node.colOffset = colOffset;
        node.str = str;

        this.childs.add(node);
    }

    public Node getChild(int n)
    {
        if(n < 0)
        {
            n = this.childs.size() + n;
        }
        if(n < 0 || n > this.childs.size())
        {
            throw new PyExceptions("Out of range by Node child's list");
        }
        return this.childs.get(n);
    }
}
