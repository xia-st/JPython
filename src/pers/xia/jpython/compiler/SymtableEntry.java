package pers.xia.jpython.compiler;

import pers.xia.jpython.object.PyObject;

public class SymtableEntry extends PyObject
{
    PyObject steId; /* int: key in ste_table->st_blocks */
    PyObject steSymbols; /* dict: variable names to flags */
    PyObject steName; /* string: name of current block */
    PyObject steVarname; /* list of function parameters */
    PyObject steChildren; /* list of child blocks */
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
}
