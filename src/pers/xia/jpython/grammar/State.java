package pers.xia.jpython.grammar;

import pers.xia.jpython.object.PyExceptions;

public class State
{
    static int MAXSIZE = 100;
    int narcs;
    Arc[] arcs;
    
    public State()
    {
        this.narcs = 0;
        this.arcs = new Arc[State.MAXSIZE];
    }
    
    public State(int narcs, Arc[] arcs)
    {
        this.narcs = narcs;
        this.arcs = arcs;
    }

    public boolean addArc(Arc arc)
    {
        if(this.narcs >= this.arcs.length)
        {
            throw new PyExceptions("Over the max size of arcs");
        }
        this.arcs[this.narcs++] = arc;
        return true;
    }
    
    public boolean removeArc(Arc arc)
    {
        int i = 0;
        for(; i < this.narcs; i++)
        {
            if(this.arcs[i] == arc)
            {
                break;
            }
        }
        
        if(i >= this.narcs)
        {
            return false;
        }
        
        //把最后一个arc放到i位置上，然后总数减1，这样可以不使用前移数组这个操作
        this.arcs[i] = this.arcs[--this.narcs];    
        
        return true;
    }
}
