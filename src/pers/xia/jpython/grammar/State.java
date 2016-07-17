package pers.xia.jpython.grammar;

public class State
{
    public int narcs;
    public Arc[] arcs;

    /* Optional accelerators */
    public int lower; /* Lowest label index */
    public int upper; /* Highest label index */
    int accel[]; /* Accelerators */
    public boolean accept; /* Accepting state */

    public State(int narcs, Arc[] arcs)
    {
        this.narcs = narcs;
        this.arcs = arcs;
        this.accept = false;
    }

    public int next(int index)
    {
        return accel[index];
    }

}
