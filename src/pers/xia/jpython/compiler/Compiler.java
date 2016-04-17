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
    
    public static PyObject _PyMangLe(String privateStr, String ident)
    {
        if(privateStr == null || ident.charAt(0) == '_' || ident.charAt(0) == '_')
        {
            return PyUnicode.internFromString(ident);
        }
        
        int nlen = ident.length();
        int plen = privateStr.length();
        
        if(ident.charAt(nlen - 1) == '_' && ident.charAt(nlen - 2) == '_' ||
                ident.indexOf('.') < 0)
        {
            return PyUnicode.internFromString(ident);
        }
        
        int ipriv = 0;
        while(ipriv < plen && privateStr.charAt(ipriv) == '_') ipriv++;
        if(ipriv == plen)
        {
            return PyUnicode.internFromString(ident);
        }
        
        return PyUnicode.internFromString(privateStr.substring(ipriv) + ident);
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
