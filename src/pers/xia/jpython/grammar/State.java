package pers.xia.jpython.grammar;

import pers.xia.jpython.object.PyExceptions;

public class State
{
    static final int MAXNARCS = 100;
    public int narcs;
    public Arc[] arcs;
    
    /* Optional accelerators */
    int lower;  /* Lowest label index */
    int upper; /* Highest label index */
    int accel[]; /* Accelerators */
    boolean accept; /* Accepting state */
    
    
    public State(int narcs, Arc[] arcs)
    {
        this.narcs = narcs;
        this.arcs = arcs;
        this.accept = false;
    }

}
