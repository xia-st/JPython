package pers.xia.jpython.compiler;

import pers.xia.jpython.object.Py;
import pers.xia.jpython.object.PyDict;
import pers.xia.jpython.object.PyList;
import pers.xia.jpython.object.PyLong;
import pers.xia.jpython.object.PyObject;
import pers.xia.jpython.object.PySet;

class PySTEntryObject extends PyObject
{
    PyLong steId; /* int: key in ste_table->st_blocks */
    PyDict steSymbols; /* dict: variable names to flags */
    String steName; /* string: name of current block */
    PyList steVarnames; /* list of function parameters */
    PyList steChildren; /* list of child blocks */
    PyObject steDirecitives; /* locations of global and nonlocal statements */
    BlockType steType; /* module, class, or function */
    boolean steNested; /* true if block is nested */
    boolean steFree; /* true if block has free variables */
    boolean steChildFree; /* true if a child block has free vars,
                               including free refs to globals */
    boolean steGenerator; /* true if namespace is a generator */
    boolean steVarargs; /* true if block has varargs */
    boolean steVarkeywords; /* true if block has varkeywords */
    boolean steReturnsValue; /* true if a child block has free vars,
                                   including free refs to globals */

    boolean steNeedsClassClosure;/* for class scopes, true if a
                                    closure over __class__
                                    should be created */
    int steLineno; /* first line of block */
    int steColOffset; /* offset of first line of block */
    int steOptLineno; /* lineno of last exec or import * */
    int steOptColOffset; /* offset of last exec or import * */
    int steTmpname; /* counter for listcomp temp vars */
    Symtable steTable;

    // TODO coding
    public PySTEntryObject(Symtable st, String name, BlockType block,
            Object key, int lineno, int colOffet)
    {
        PyLong k = null;
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
