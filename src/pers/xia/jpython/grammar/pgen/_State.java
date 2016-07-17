package pers.xia.jpython.grammar.pgen;

import pers.xia.jpython.object.PyExceptions;

/*
 * 用于Pgen生成数据
 */
class _State
{
    static final int MAXSIZE = 100;
    int narcs;
    _Arc[] arcs;

    public _State()
    {
        this.narcs = 0;
        this.arcs = new _Arc[_State.MAXSIZE];
    }

    public boolean addArc(_Arc arc)
    {
        if(this.narcs >= this.arcs.length)
        {
            throw new PyExceptions("Over the max size of arcs");
        }
        this.arcs[this.narcs++] = arc;
        return true;
    }
    
    public int findArc(_Label label)
    {
        for(int i = 0; i < this.narcs; i++)
        {
            if(this.arcs[i].label == label)
            {
                return i;
            }
        }
        return -1;
    }
    
    public int findArc(_State nextState)
    {
        for(int i = 0; i < this.narcs; i++)
        {
            if(this.arcs[i].nextState == nextState)
            {
                return i;
            }
        }
        return -1;
    }
    
    public void removeAllArc(_Label label)
    {
        for(int i = 0; i < this.narcs; i++)
        {
            if(this.arcs[i].label == label)
            {
                this.arcs[i] = this.arcs[this.narcs - 1];
                this.narcs--;
                i--;
            }
        }
    }

    public boolean removeArc(_Arc arc)
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
