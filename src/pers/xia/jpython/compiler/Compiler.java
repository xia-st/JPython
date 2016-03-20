package pers.xia.jpython.compiler;

import pers.xia.jpython.ast.modType;
import pers.xia.jpython.object.PyCodeObject;
import pers.xia.jpython.object.PyList;
import pers.xia.jpython.object.PyObject;
import pers.xia.jpython.object.PyUnicode;

public class Compiler
{
    public PyObject fileName;
    public Symtable st;

    public int optimize; // optimization level
    public boolean interactive; // is or not interactive mode
    public int nestLevel;

    public CompilerUnit u;
    public PyList stack;

    private static PyObject __doc__;

    public Compiler()
    {
        this.stack = new PyList(0);
    }

    public Compiler(PyObject fileName, int optimize, Symtable st)
    {
        this.fileName = fileName;
        this.optimize = optimize;
        this.nestLevel = 0;
        this.st = st;
    }

    public PyCodeObject mod(modType mod)
    {
        return null;
    }

    public static PyCodeObject compile(modType mod, String fileName)
    {
        PyObject fileNameObj = PyUnicode.internFromString(fileName);
        return compileObject(mod, fileNameObj, -1);
    }

    public static PyCodeObject compileObject(modType mod, PyObject fileName,
            int optmize)
    {
        if(__doc__ == null)
        {
            __doc__ = PyUnicode.internFromString("__doc__");
        }

        Symtable st = Symtable.buildObject(mod, fileName);
        Compiler c = new Compiler(fileName, optmize, st);
        PyCodeObject co = c.mod(mod);
        return co;
    }
}
