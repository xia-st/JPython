package pers.xia.jpython.parser;

import java.util.List;

import pers.xia.jpython.grammar.DFAName;
import pers.xia.jpython.object.PyExceptions;
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
   
   public void addChild(DFAName dfaName, int lineNo, int colOffset)
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
           throw new PyExceptions("Out of child list");
       }
       return this.childs.get(n);
   }
}
