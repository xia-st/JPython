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

    public static final PyObject newInteger(int i)
    {
        if(i < Config.NSMALLPOSINTS && i > Config.NSMALLNEGINTS)
        {
            return integerCache[i + Config.NSMALLNEGINTS];
        }
        return new PyLong(i);
    }
}
