package pers.xia.jpython.parser;

import java.util.List;

import pers.xia.jpython.grammar.DFAName;
import pers.xia.jpython.tokenizer.TokState;

public class Node
{
    boolean isDFAType;
    DFAName dfaType;
    TokState tokType;
    
    String str;
    int lineNo;
    int colOffset;
    
   List<Node> childs;
   
   public Node(DFAName dfaType)
   {
       this.dfaType = dfaType;
       this.isDFAType = true;
   }
   
   public Node(TokState tokType)
   {
       this.tokType = tokType;
       this.isDFAType = false;
   }
   
   //创建一个node的拷贝
   private Node(Node node)
   {
       this.isDFAType = node.isDFAType;
       this.dfaType = node.dfaType;
       this.tokType = node.tokType;
       this.str = node.str;
       this.lineNo = node.lineNo;
       this.colOffset = node.colOffset;
   }
   
   public void addChild(Node child)
   {
       Node _child = new Node(child);
       this.childs.add(_child);
   }
   
}
