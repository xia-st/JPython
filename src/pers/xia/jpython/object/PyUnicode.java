package pers.xia.jpython.object;

import java.io.UnsupportedEncodingException;

import pers.xia.jpython.object.PyExceptions.ErrorType;

public class PyUnicode extends PyObject
{
    String str;

    public PyUnicode(byte[] b, String encode)
    {
        try
        {
            this.str = new String(b, encode);
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new PyExceptions(ErrorType.SYSTEM_ERROR,
                    "Can't convert bytes to str implicitly");
        }
    }

    public PyUnicode(String str)
    {
        this.str = str;
    }

    public PyUnicode(PyObject obj)
    {
        // TODO
    }

    public static PyUnicode concat(PyObject left, PyObject right)
    {
        if(!(left instanceof PyUnicode))
        {
            throw new PyExceptions(ErrorType.SYSTEM_ERROR, "transform error",
                    left);
        }

        return ((PyUnicode) left).concat(right);
    }

    public PyUnicode concat(PyObject obj)
    {
        if(!(obj instanceof PyUnicode))
        {
            throw new PyExceptions(ErrorType.SYSTEM_ERROR, "transform error",
                    obj);
        }

        String newStr = this.str + ((PyUnicode) obj).str;
        return new PyUnicode(newStr);

    }
}
