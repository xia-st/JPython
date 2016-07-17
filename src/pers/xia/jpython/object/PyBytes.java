package pers.xia.jpython.object;

public class PyBytes extends PyObject
{
    public byte[] bytes;

    public PyBytes(byte[] bytes)
    {
        this.bytes = bytes;
    }

    public PyBytes(String str)
    {
        this.bytes = str.getBytes();
    }

    public PyBytes concat(PyBytes pyBytes)
    {
        byte[] concatedBytes = pyBytes.bytes;
        byte[] newBytes = new byte[bytes.length + concatedBytes.length];
        System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
        System.arraycopy(concatedBytes, 0, newBytes, bytes.length,
                concatedBytes.length);
        return new PyBytes(newBytes);
    }
}
