package pers.xia.jpython.compiler;

import java.util.List;

import pers.xia.jpython.ast.Expression;
import pers.xia.jpython.ast.Interactive;
import pers.xia.jpython.ast.Module;
import pers.xia.jpython.ast.Suite;
import pers.xia.jpython.ast.exprType;
import pers.xia.jpython.ast.modType;
import pers.xia.jpython.ast.stmtType;
import pers.xia.jpython.object.Py;
import pers.xia.jpython.object.PyDict;
import pers.xia.jpython.object.PyExceptions;
import pers.xia.jpython.object.PyList;
import pers.xia.jpython.object.PyObject;
import pers.xia.jpython.object.PySet;

public class Symtable
{
    PyObject stFileName; /* name of file being compiled,
                         decoded from the filesystem encoding */
    PySTEntryObject stCur; /* current symbol table entry */
    PySTEntryObject stTop; /* symbol table entry for module */
    PyDict stBlocks; /* dict: map AST node addresses
                       *       to symbol table entries */
    PyList stStack; /* list: stack of namespace info */
    PyObject stGlobal; /* borrowed ref to st_top->ste_symbols */
    int stNBlocks; /* number of blocks used. kept for
                      consistency with the corresponding
                      compiler structure */
    PyObject stPrivate; /* name of current class or NULL */

    int recursionDepth; /* current recursion depth */
    int recursionLimit; /* recursion limit */

    
    private static String top, lambda, genexpr, listcomp, 
    		setcomp, dictcomp, __ckass__;
    
    public Symtable()
    {
    	this.stFileName = null;
    	this.stBlocks = null;
    	this.stStack = new PyList(0);
    	this.stBlocks = new PyDict();
    	
    	this.stCur = null;
    	this.stPrivate = null;
    }
    
    private boolean visitStmt(stmtType s)
    {
    	// TODO
    	return true;
    }
    
    private boolean visitExpr(exprType body)
    {
    	// TODO
    	return true;
    }
    
    private boolean enterBlock(String name,
    		BlockType block, Object ast, int lineno, int colOffet) // add parameter
    {
    	PySTEntryObject prev = null, ste;
    	ste = new PySTEntryObject(this, name, block, ast, lineno, colOffet);
    	
    	this.stStack.append(ste);
    	
    	prev = this.stCur;
    	this.stCur = ste;
    	
    	if(block == BlockType.ModuleBlock)
    	{
    		this.stGlobal = this.stCur.steSymbols;
    	}
    	if(prev != null)
    	{
    		prev.steChildren.append(ste);
    	}
    	return true;
    }
    
    private boolean exitBlock(Object ast)
    {
    	long size; // TODO set the py list max size to long's max size
    	this.stCur = null;
    	size = this.stStack.getSize();
    	if(size > 0)
    	{
    		this.stStack.setSlice(size - 1, size , null);
    		if(--size > 0)
    		{
    			this.stCur = (PySTEntryObject)this.stStack.getItem(size - 1);
    		}
    	}
    	return true;
    }
    
    private boolean analyzeBlock(PySTEntryObject ste, PySet bound, 
    		PySet free, PySet global)
    {
    	PyObject name, v;
    	PyDict scopes = null;
		PySet local = null;
		
		PySet newGlobal = null;
		PySet newFree = null;
		PySet allFree = null;
		PySet newBound = null;
		
    	PyObject temp;
    	
    	int i, success = 0;
    	long pos = 0;
    	
    	local = new PySet(null);
    	scopes = new PyDict();
    	
    	newGlobal = new PySet(null);
    	newFree = new PySet(null);
    	newBound = new PySet(null);
    	
    	if(ste.steType == BlockType.ClassBlock)
    	{
    		/* Pass down known globals */
    		temp = Py.PyNumber_InPlaceOr(newGlobal, global);
    		if(temp != null)
    		{
    			//TODO
    		}
    	}
    	
    	return true;
    }
    
    private boolean analyze()
    {
        PySet free, global;
        free = new PySet(null);
    	global = new PySet(null);
    	return analyzeBlock(this.stTop, null, free, global);
    }
    
    public static Symtable buildObject(modType mod, PyObject fileName)
    {
    	Symtable st = new Symtable();
    	List<stmtType> seq;
    	
    	st.stFileName = fileName;
    	
    	if(top == null)
    	{
    		top = "top";
    	}
    	st.enterBlock(top, BlockType.ModuleBlock, mod, 0, 0);
    	
    	
    	st.stTop = st.stCur;
    	while(true) // Just for add back function
    	{
	    	if(mod instanceof Module)
	    	{
	    		seq = ((Module)mod).body;
	    		for(int i = 0; i < seq.size(); i++)
	    		{
	    			if(!st.visitStmt(seq.get(i)))
	    			{
	    				break;
	    			}
	    		}
	    	}
	    	else if(mod instanceof Expression)
	    	{
	    		if(!st.visitExpr(((Expression)mod).body))
	    		{
	    			break;
	    		}
	    	}
	    	else if(mod instanceof Interactive)
	    	{
	    		seq = ((Interactive)mod).body;
	    		for(int i = 0; i < seq.size(); i++)
	    		{
	    			if(!st.visitStmt(seq.get(i)))
	    			{
	    				break;
	    			}
	    		}
	    	}
	    	else if(mod instanceof Suite)
	    	{
	    		throw new PyExceptions("this compiler does not handle Suites");
	    	}
	    	
	    	st.exitBlock(mod);
	    	
	    	/* Make the second symbol analysis pass */
	    	if(st.analyze())
	    	{
	    		return st;
	    	}
    	}
    	throw new PyExceptions("symtable visit error");
    }
}
