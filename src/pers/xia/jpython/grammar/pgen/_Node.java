package pers.xia.jpython.grammar.pgen;

import java.util.LinkedList;

/*
 * for Pgen
 */
class _Node
{
    public _NodeType type;
    public String value;
    public LinkedList<_Node> childs;
    public _Node()
    {
        this.type = null;
        this.value = null;
        this.childs = new LinkedList<_Node>();
    }

    public boolean addChild(_Node node)
    {
        this.childs.add(node);
        return true;
    }

    public boolean addFirstChild(_Node node)
    {
        this.childs.addFirst(node);
        return true;
    }

    public String toString()
    {
        return type.toString() + ": " + this.value;
    }

}