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

    public static PyUnicode internFromString(String str)
    {
        return internFromString(str, false);
    }

    public static PyUnicode internFromString(String str, boolean rawmode)
    {
        return new PyUnicode(str, rawmode);
    }

    private PyUnicode(String str, boolean rawmode)
    {
        this.str = str;
        if(!rawmode)
        {
            // FIXME should use faster and more complete method
            this.str.replace("\\n", "\n");
            this.str.replace("\\t", "\t");
            this.str.replace("\\\\", "\\");
        }
    }

    private PyUnicode(PyObject obj)
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
        return internFromString(newStr);

    }

}
