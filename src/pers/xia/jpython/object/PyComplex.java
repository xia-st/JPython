package pers.xia.jpython.object;

public class PyComplex extends PyObject
{
    double real;
    double imag;

    public PyComplex(double real, double imag)
    {
        this.real = real;
        this.imag = imag;
    }
}
