package pers.xia.jpython.object;

import pers.xia.jpython.config.Config;

public final class Py
{
    public final static PyNone None = new PyNone();
    public final static PyBoolean True = new PyBoolean(true);
    public final static PyBoolean False = new PyBoolean(false);

    public static String encoding = "utf-8";

    private final static PyLong[] integerCache = new PyLong[Config.NSMALLPOSINTS
            - Config.NSMALLNEGINTS];

    static
    {
        for (int i = 0; i < Config.NSMALLPOSINTS - Config.NSMALLNEGINTS; i++)
        {
            integerCache[i] = new PyLong(Config.NSMALLNEGINTS + i);
        }
    }
    
    public static final PyLong newInteger(int i)
    {
        if(i < Config.NSMALLPOSINTS && i > Config.NSMALLNEGINTS)
        {
            return integerCache[(int)i - Config.NSMALLNEGINTS];
        }
        return new PyLong(i);
    }
    
    public static final PyLong newInteger(long i)
    {
        if(i < Integer.MIN_VALUE || i > Integer.MAX_VALUE)
        {
            return newInteger((int)i);
        }
        else
        {
            return new PyLong(i);
        }
    }
    
    public static PyObject PyNumber_InPlaceOr(PyObject v, PyObject w)
    {
    	return null;
    }
}
