package pers.xia.jpython.object;

public class PyLong extends PyObject
{
    private long num;

    public PyLong(long num)
    {

    }

    public static PyObject newLong(String str)
    {
        return new PyLong(Long.parseLong(str));
    }
}
