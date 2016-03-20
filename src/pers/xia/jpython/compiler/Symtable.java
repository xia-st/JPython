package pers.xia.jpython.compiler;

import pers.xia.jpython.ast.modType;
import pers.xia.jpython.object.PyDict;
import pers.xia.jpython.object.PyObject;

public class Symtable
{
    PyObject fileName; /* name of file being compiled,
                         decoded from the filesystem encoding */
    SymtableEntry stCur; /* current symbol table entry */
    SymtableEntry stTop; /* symbol table entry for module */
    PyDict stBlocks; /* dict: map AST node addresses
                        *       to symbol table entries */
    PyDict stStack; /* dict: map AST node addresses
                       *       to symbol table entries */
    PyObject stGlobal; /* borrowed ref to st_top->ste_symbols */
    int stNBlocks; /* number of blocks used. kept for
                      consistency with the corresponding
                      compiler structure */
    PyObject stPrivate; /* name of current class or NULL */

    int recursionDepth; /* current recursion depth */
    int recursionLimit; /* recursion limit */

    public static Symtable buildObject(modType mod, PyObject fileName)
    {
        return null;
    }
}
