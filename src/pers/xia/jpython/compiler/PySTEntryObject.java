package pers.xia.jpython.compiler;

import pers.xia.jpython.object.Py;
import pers.xia.jpython.object.PyDict;
import pers.xia.jpython.object.PyList;
import pers.xia.jpython.object.PyObject;

class PySTEntryObject extends PyObject
{
    PyObject steId;
    PyObject steSymbols;
    String steName;
    PyObject steVarnames;
    PyObject steChildren;
    PyObject steDirecitives;
    BlockType steType;
    boolean steNested;
    boolean steFree;
    boolean steChildFree;
    boolean steGenerator;
    boolean steVarargs;
    boolean steVarkeywords;
    boolean steReturnsValue;
    boolean steNeedsClassClosure;

    int steLineno;
    int steColOffset;
    int steOptLineno;
    int steOptColOffset;
    int steTmpname;
    Symtable steTable;

    // TODO coding
    public PySTEntryObject(Symtable st, String name, BlockType block,
            Object key, int lineno, int colOffet)
    {
        PySTEntryObject ste = null;
        PyObject k = null;
        k = Py.newInteger(key.hashCode());
        this.steTable = st;
        this.steId = k;
        this.steName = name;

        this.steSymbols = null;
        this.steVarnames = null;
        this.steChildren = null;

        this.steDirecitives = null;

        this.steType = block;
        this.steNested = false;
        this.steFree = false;
        this.steVarargs = false;
        this.steVarkeywords = false;
        this.steOptLineno = 0;
        this.steOptColOffset = 0;
        this.steTmpname = 0;
        this.steLineno = lineno;
        this.steColOffset = colOffet;

        if(st.stCur != null && (st.stCur.steNested
                || st.stCur.steType == BlockType.FunctionBlock))
        {
            this.steNested = true;
        }
        this.steGenerator = false;
        this.steGenerator = false;
        this.steReturnsValue = false;
        this.steNeedsClassClosure = false;

        this.steSymbols = new PyDict();
        this.steVarnames = new PyList(0);
        this.steChildren = new PyList(0);

        st.stBlocks.setItem(this.steId, this);
    }
}
