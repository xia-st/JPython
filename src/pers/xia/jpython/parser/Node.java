package pers.xia.jpython.parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import pers.xia.jpython.grammar.DFAType;
import pers.xia.jpython.object.PyExceptions;
import pers.xia.jpython.tokenizer.TokState;

public class Node
{
    public boolean isDFAType;
    public DFAType dfaType;

    public String str;
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
        this.dfaType = DFAType.valueOf(tokType.toString()); // modify TokState
                                                            // to DFAType
        this.isDFAType = false;
    }

    // 创建一个node的拷贝
    /*
     * private Node(Node node) { this.isDFAType = node.isDFAType; this.dfaType =
     * node.dfaType; this.tokType = node.tokType; this.str = node.str;
     * this.lineNo = node.lineNo; this.colOffset = node.colOffset; }
     */

    public void addChild(DFAType dfaName, int lineNo, int colOffset)
    {
        Node node = new Node(dfaName);

        node.lineNo = lineNo;
        node.colOffset = colOffset;

        this.childs.add(node);
    }

    public void addChild(TokState tokState, String str, int lineNo,
            int colOffset)
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
            throw new PyExceptions("Out of range by Node child's list", this);
        }
        return this.childs.get(n);
    }

    public int nChild()
    {
        return this.childs.size();
    }

    public void show()
    {
        //save the node and there index
        class NodeAndIndex
        {
            Node node;
            int index;

            NodeAndIndex(Node node)
            {
                this.node = node;
                this.index = 0;
            }
        }

        Stack<NodeAndIndex> stack = new Stack<NodeAndIndex>();
        LinkedList<String> nodeNames = new LinkedList<String>();
        NodeAndIndex ni = new NodeAndIndex(this);

        nodeNames.add(ni.node.dfaType.toString());
        stack.add(ni);

        while (!stack.empty())
        {
            ni = stack.peek();
            if(ni.index >= ni.node.childs.size())
            {
                stack.pop();
                nodeNames.removeLast();
                continue;
            }

            Node node = ni.node.getChild(ni.index++);
            if(node.isDFAType)
            {
                NodeAndIndex ni2 = new NodeAndIndex(node);
                stack.push(ni2);
                nodeNames.add(ni2.node.dfaType.toString());
                continue;
            }

            for (String s : nodeNames)
            {
                System.out.print(s + " ");
            }
            if(node.dfaType == DFAType.NAME)
            {
                System.out.println(node.dfaType.toString() + " " + node.str);
            }
            else
            {
                System.out.println(node.dfaType.toString());
            }
        }
    }

}
