package pers.xia.jpython.object;

public class PyLong extends PyObject
{
    private long num;

    public PyLong(long num)
    {
        this.num = num;
    }

    public static PyObject newLong(String str)
    {
        // TODO check the str is too much longer
        if (str.length() <= 1)
        {
            return new PyLong(Long.parseLong((str)));
        }
        try
        {
            if (str.charAt(1) == 'x' || str.charAt(1) == 'X')
            {
                return new PyLong(Long.parseLong(str.substring(2), 16));
            }
            else if (str.charAt(1) == 'b' || str.charAt(1) == 'B')
            {
                return new PyLong(Long.parseLong(str.substring(2), 2));
            }
            else if (str.charAt(1) == 'o' || str.charAt(1) == 'O')
            {
                return new PyLong(Long.parseLong(str.substring(2), 8));
            }
            else
            {
                return new PyLong(Long.parseLong((str)));
            }
        }
        catch (NumberFormatException err)
        {
            //System.out.println(str);
            return new PyNumber(str);
        }
    }

    public long asLong()
    {
        return num;
    }
}
