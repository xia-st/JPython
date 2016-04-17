package pers.xia.jpython.compiler;

import java.io.File;

import pers.xia.jpython.ast.*;
import pers.xia.jpython.grammar.GramInit;
import pers.xia.jpython.object.Py;
import pers.xia.jpython.object.PyDict;
import pers.xia.jpython.object.PyExceptions;
import pers.xia.jpython.object.PyExceptions.ErrorType;
import pers.xia.jpython.object.PyList;
import pers.xia.jpython.object.PyObject;
import pers.xia.jpython.object.PySet;
import pers.xia.jpython.object.PyUnicode;
import pers.xia.jpython.object.PyLong;
import pers.xia.jpython.parser.Ast;
import pers.xia.jpython.parser.Node;
import pers.xia.jpython.parser.ParseToken;

public class Symtable
{
    PyObject stFileName; /* name of file being compiled,
                         decoded from the filesystem encoding */
    PySTEntryObject stCur; /* current symbol table entry */
    PySTEntryObject stTop; /* symbol table entry for module */
    PyDict stBlocks; /* dict: map AST node addresses
                       *       to symbol table entries */
    PyList stStack; /* list: stack of namespace info */
    PyDict stGlobal; /* borrowed ref to st_top->ste_symbols */
    int stNBlocks; /* number of blocks used. kept for
                      consistency with the corresponding
                      compiler structure */
    String stPrivate; /* name of current class or NULL */

    //int recursionDepth; /* current recursion depth */
    //int recursionLimit; /* recursion limit */

    
    private static String top, lambda, genexpr, listcomp, 
    		setcomp, dictcomp, __class__;

    private static int DEF_GLOBAL = 1;
    private static int DEF_LOCAL = 2;
    private static int DEF_PARAM = 2 << 1;
    private static int DEF_NONLOCAL = 2 << 2;
    private static int USE = 2 << 3;
    private static int DEF_FREE = 2 << 4;
    private static int DEF_FREE_CLASS = 2 << 5;
    private static int DEF_IMPORT = 2 << 6;
    private static int DEF_BOUND = DEF_LOCAL | DEF_PARAM | DEF_IMPORT;

    //for warnings
    private static String GLOBAL_AFTER_ASSIGN = "name '%.400s' is assigned to before global declaration";
    private static String NONLOCAL_AFTER_ASSIGN = "name '%.400s' is assigned to before nonlocal declaration";
    private static String GLOBAL_AFTER_USE = "name '%.400s' is used prior to global declaration";
    private static String NONLOCAL_AFTER_USE = "name '%.400s' is used prior to nonlocal declaration";
    private static String IMPORT_STAR_WARNING = "import * only allowed at module level";
    private static String DUPLICATE_ARGUMENT = "duplicate argument '%s' in function definition";
    public Symtable()
    {
    	this.stFileName = null;
    	this.stBlocks = null;
    	this.stStack = new PyList(0);
    	this.stBlocks = new PyDict();
    	
    	this.stCur = null;
    	this.stPrivate = null;
    }
    
    private void visitArgannotations(java.util.List<argType> args)
    {
        // TODO
    }
    
    private void visitParams(java.util.List<argType> args)
    {
        // TODO
    }
    
    private void addDef(String name, int flag)
    {
        PyObject o;
        PyDict dict;
        long val;
        PyObject mangled = Compiler._PyMangLe(this.stPrivate, name);
        dict = this.stCur.steSymbols;
        if((o = dict.getItem(mangled)) != null)
        {
            val = ((PyLong)o).toLang();
            if((flag & DEF_PARAM) > 0 & (val & DEF_PARAM) > 0)
            {
                throw new PyExceptions(ErrorType.SYNTAX_ERROR, String.format(DUPLICATE_ARGUMENT, name));
            }
            val |= flag;
        }
        else
        {
            val = flag;
        }
        
        o = Py.newInteger(val);
        
        dict.setItem(mangled, o);
        
        if((flag & DEF_PARAM) > 0)
        {
            this.stCur.steVarnames.append(mangled);
        }
        else if((flag & DEF_GLOBAL) > 0)
        {
            if((o = this.stGlobal.getItem(mangled)) != null)
            {
                val |= ((PyLong)o).toLang();
            }
            o = Py.newInteger(val);
            
            this.stGlobal.setItem(mangled, o);
        }
        return;
    }

    //访问方法的声明
    private void visitAnnotations(stmtType s, argumentsType a, exprType returns)
    {
        if(a.args != null)
        {
            this.visitArgannotations(a.args);
        }
        if(a.vararg != null && a.vararg.annotation != null)
        {
            this.visitExpr(a.vararg.annotation);
        }
        if(a.kwonlyargs != null)
        {
            this.visitArgannotations(a.kwonlyargs);
        }
        if(returns != null)
        {
            this.visitExpr(returns);
        }
    }

    private void visitArguments(argumentsType a)
    {
        if(a.args != null)
        {
            this.visitParams(a.args);
        }
        if(a.kwonlyargs != null)
        {
            this.visitParams(a.kwonlyargs);
        }
        if(a.vararg != null)
        {
            this.addDef(a.vararg.arg, DEF_PARAM);
            this.stCur.steVarargs = true;
        }
        if(a.kwarg != null)
        {
            this.addDef(a.kwarg.arg, DEF_PARAM);
            this.stCur.steVarkeywords = true;
        }
    }

    private void visitKeyword(keywordType k)
    {
        this.visitExpr(k.value);
    }

    // import as 后面的别名
    private void visitAlias(aliasType a)
    {
        String storeName;
        String name = a.asname == null ? a.name : a.asname;
        
        int dot = name.indexOf('.');
        
        if(dot != -1)
        {
            storeName = name.substring(0, dot);
        }
        else
        {
            storeName = name;
        }
        if(!name.equals("*"))
        {
            this.addDef(storeName, DEF_IMPORT);
            return;
        }
        else
        {
            if(this.stCur.steType != BlockType.ModuleBlock)
            {
                int lineno = this.stCur.steLineno;
                int colOffset = this.stCur.steColOffset;
                throw new PyExceptions(ErrorType.SYNTAX_ERROR, IMPORT_STAR_WARNING, lineno, colOffset);
            }
        }
    }

    private long lookup(String name)
    {
        // TODO
        return 0;
    }

    private void warn(String buf, int lineno)
    {
        // TODO
    }

    private void recordDirective(String name, stmtType s)
    {
        // TODO
    }
    
    
    private void visitGenexp(exprType e)
    {
        // TODO
    }
    
    private void visitListcomp(exprType e)
    {
        // TODO
    }
    
    private void visitSetcomp(exprType e)
    {
        // TODO
    }
    
    private void visitDictcomp(exprType e)
    {
        // TODO
    }
    
    private void visitSlice(sliceType s)
    {
        // TODO
    }

    private void visitStmt(stmtType s)
    {
        if (s instanceof FunctionDef)
        {
            FunctionDef f = (FunctionDef)s;
            this.addDef(f.name, DEF_LOCAL);
            if (f.args.defaults != null)
            {
                for(exprType type : f.args.defaults)
                {
                    this.visitExpr(type);
                }
            }
            if(f.args.kw_defaults != null)
            {
                for (exprType expr : f.args.kw_defaults)
                {
                    if (expr == null)
                        continue;
                    this.visitExpr(expr);
                }
            }
            this.visitAnnotations(s, f.args, f.returns);


            if (f.decorator_list != null)
            {
                for (exprType expr : f.decorator_list)
                {
                    this.visitExpr(expr);
                }
            }
            
            this.enterBlock(f.name, BlockType.FunctionBlock, s, s.lineno, s.col_offset);

            this.visitArguments(f.args);

            for (stmtType stmt : f.body)
            {
                this.visitStmt(stmt);
            }
            this.exitBlock(s);
        }
        else if (s instanceof ClassDef)
        {
            String tmp;
            ClassDef c = (ClassDef)s;
            this.addDef(c.name, DEF_LOCAL);
            
            if(c.bases != null)
            {
                for (exprType e : c.bases)
                {
                    this.visitExpr(e);
                }
            }

            if(c.keywords != null)
            {
                for (keywordType k : c.keywords)
                {
                    this.visitKeyword(k);
                }
            }

            if (c.decorator_list != null)
            {
                for (exprType e : c.decorator_list)
                {
                    this.visitExpr(e);
                }
            }
            
            this.enterBlock(c.name, BlockType.ClassBlock, s, s.lineno, s.col_offset);

            
            tmp = this.stPrivate;

            this.stPrivate = c.name;

            for (stmtType stmt : c.body)
            {
                this.visitStmt(stmt);
            }

            this.stPrivate = tmp;

            this.exitBlock(s);
        }
        else if (s instanceof Return)
        {
            Return r = (Return)s;
            if(r.value != null)
            {
                this.visitExpr(r.value);
            }
        }
        else if (s instanceof Delete)
        {
            for (exprType expr : ((Delete) s).targets)
            {
                this.visitExpr(expr);
            }
        }
        else if (s instanceof Assign)
        {
            Assign assign = (Assign) s;
            for (exprType expr : assign.targets)
            {
                this.visitExpr(expr);
            }
            this.visitExpr(assign.value);
        }
        else if (s instanceof AugAssign)
        {
            AugAssign augAssign = (AugAssign) s;
            this.visitExpr(augAssign.target);
            this.visitExpr(augAssign.value);
        }
        else if (s instanceof For)
        {
            For f = (For) s;
            this.visitExpr(f.target);

            this.visitExpr(f.iter);
            for (stmtType stmt : f.body)
            {
                this.visitStmt(stmt);
            }
            if (f.orelse != null)
            {
                for (stmtType stmt : f.orelse)
                {
                    this.visitStmt(stmt);
                }
            }
        }
        else if (s instanceof While)
        {
            While w = (While) s;
            this.visitExpr(w.test);
            for (stmtType stmt : w.body)
            {
                this.visitStmt(stmt);
            }
            if (w.orelse != null)
            {
                for (stmtType stmt : w.orelse)
                {
                    this.visitStmt(stmt);
                }
            }
        }
        else if (s instanceof If)
        {
            If i = (If) s;
            this.visitExpr(i.test);

            for (stmtType stmt : i.body)
            {
                this.visitStmt(stmt);
            }
            if (i.orelse != null)
            {
                for (stmtType stmt : i.orelse)
                {
                    this.visitStmt(stmt);
                }
            }
        }
        else if (s instanceof Raise)
        {
            Raise raise = (Raise) s;
            if (raise.exc != null)
            {
                this.visitExpr(raise.exc);

                if (raise.cause != null)
                {
                    this.visitExpr(((Raise) s).cause);
                }
            }
        }
        else if (s instanceof Assert)
        {
            Assert a = (Assert) s;
            this.visitExpr(a.test);

            if (a.msg != null)
            {
                this.visitExpr(a.msg);
            }
        }
        else if (s instanceof Import)
        {
            for (aliasType alias : ((Import) s).names)
            {
                this.visitAlias(alias);
            }
        }
        else if (s instanceof ImportFrom)
        {
            for (aliasType alias : ((ImportFrom) s).names)
            {
                this.visitAlias(alias);
            }
        }
        else if (s instanceof Global)
        {
            java.util.List<String> seq = ((Global) s).names;
            for (String name : seq)
            {
                long cur = this.lookup(name);
                if(cur < 0)
                {
                    throw new PyExceptions(ErrorType.SYMTABLE_ERROR, "cur less than zero");
                    
                }
                if ((cur & (DEF_LOCAL | USE)) > 0)
                {
                    String buf;
                    if((cur & DEF_LOCAL) > 0)
                    {
                        buf = String.format(GLOBAL_AFTER_ASSIGN, name);
                    }
                    else
                    {
                        buf = String.format(GLOBAL_AFTER_USE, name);
                    }
                    this.warn(buf, s.lineno);
                }
                this.addDef(name, DEF_GLOBAL);
                this.recordDirective(name, s);
            }
        }
        else if (s instanceof Nonlocal)
        {
            java.util.List<String> seq = ((Nonlocal) s).names;
            for (String name : seq)
            {
                long cur = this.lookup(name);
                if (cur < 0)
                {
                    throw new PyExceptions(ErrorType.SYMTABLE_ERROR, "cur less than zero");
                }
                if ((cur & (DEF_LOCAL | USE)) > 0)
                {
                    String buf;

                    if ((cur & DEF_LOCAL) > 0)
                    {
                        buf = String.format(NONLOCAL_AFTER_ASSIGN, name);
                    }
                    else
                    {
                        buf = String.format(NONLOCAL_AFTER_USE, name);
                    }
                    this.warn(buf, s.lineno);
                }
                this.addDef(name,  DEF_NONLOCAL);

                this.recordDirective(name, s);

            }
        }
        else if (s instanceof Expr)
        {
            this.visitExpr(((Expr) s).value);
        }
        else if (s instanceof Pass || 
                s instanceof Break || 
                s instanceof Continue)
        {
            return; // jump to exit if-else statement 
        }
        else if(s instanceof AsyncFunctionDef)
        {
            AsyncFunctionDef afd = (AsyncFunctionDef)s;
            this.addDef(afd.name, DEF_LOCAL);
            if(afd.args.defaults != null)
            {
                for(exprType e : afd.args.defaults)
                {
                    this.visitExpr(e);
                }
            }
            if(afd.args.kw_defaults != null)
            {
                for(exprType e : afd.args.kw_defaults)
                {
                    if(e == null) continue;
                    this.visitExpr(e);

                }
            }
            this.visitAnnotations(s, afd.args, afd.returns);
            if(afd.decorator_list != null)
            {
                for(exprType e : afd.decorator_list)
                {
                    this.visitExpr(e);
                }
            }
            this.enterBlock(afd.name, BlockType.FunctionBlock, 
                    s, s.lineno, s.col_offset);

            this.visitArguments(afd.args);
            
            for(stmtType stmt : afd.body)
            {
                this.visitStmt(stmt);
            }
            this.exitBlock(s);
        }
        if(s instanceof AsyncFor)
        {
            AsyncFor af = (AsyncFor)s;
            this.visitExpr(af.target);

            this.visitExpr(af.iter);
            
            for(stmtType stmt : af.body)
            {
                this.visitStmt(stmt);
            }
            
            if(af.orelse != null)
            {
                for(stmtType stmt : af.orelse)
                {
                    this.visitStmt(stmt);
                }
            }
        }
    }
    
    private void visitExpr(exprType e)
    {
    	if(e instanceof BoolOp)
    	{
    	    for(exprType expr : ((BoolOp)e).values)
    	    {
    	        this.visitExpr(expr);
    	    }
    	}
    	else if(e instanceof BinOp)
    	{
    	    BinOp b = (BinOp)e;
    	    this.visitExpr(b.left);
    	    this.visitExpr(b.right);
    	}
    	else if(e instanceof UnaryOp)
    	{
    	    this.visitExpr(((UnaryOp)e).operand);
    	}
    	else if(e instanceof Lambda)
    	{
    	    lambda = lambda == null ? lambda : "lambda";
    	    Lambda l = (Lambda)e;
    	    if(l.args.defaults != null)
    	    {
    	        for(exprType expr : l.args.defaults)
    	        {
    	            this.visitExpr(expr);
    	        }
    	    }
    	    if(l.args.kw_defaults != null)
    	    {
    	        for(exprType expr : l.args.kw_defaults)
    	        {
    	            if(expr == null) continue;
    	            this.visitExpr(expr);
    	        }
    	    }
    	    this.enterBlock(lambda, BlockType.FunctionBlock, e,
    	            e.lineno, e.col_offset);
    	    this.visitArguments(l.args);
    	    this.visitExpr(l.body);
    	    this.exitBlock(e);
    	}
    	else if(e instanceof IfExp)
    	{
    	    IfExp ifExp = (IfExp)e;
    	    this.visitExpr(ifExp.test);
    	    this.visitExpr(ifExp.body);
    	    this.visitExpr(ifExp.orelse);
    	}
    	else if(e instanceof Dict)
    	{
    	    Dict d = (Dict)e;
    	    if(d.keys != null)
    	    {
        	    for(exprType expr : d.keys)
        	    {
        	        if(expr == null) continue;
        	        this.visitExpr(expr);
        	    }
    	    }
    	    if(d.values != null)
    	    {
        	    for(exprType expr : d.values)
        	    {
        	        this.visitExpr(expr);
        	    }
    	    }
    	}
    	else if(e instanceof Set)
    	{
    	    for(exprType expr : ((Set)e).elts)
    	    {
    	        this.visitExpr(expr);
    	    }
    	}
    	else if(e instanceof GeneratorExp)
    	{
    	    this.visitGenexp(e);
    	}
    	else if(e instanceof ListComp)
    	{
    	    this.visitListcomp(e);
    	}
    	else if(e instanceof SetComp)
    	{
    	    this.visitSetcomp(e);
    	}
    	else if(e instanceof DictComp)
    	{
    	    this.visitDictcomp(e);
    	}
    	else if(e instanceof Yield)
    	{
    	    Yield y = (Yield)e;
    	    if(y.value != null)
    	    {
    	        this.visitExpr(y.value);
    	    }
    	    this.stCur.steGenerator = true;
    	}
    	else if(e instanceof YieldFrom)
    	{
    	    this.visitExpr(((YieldFrom)e).value);
    	    this.stCur.steGenerator = true;
    	}
    	else if(e instanceof Await)
    	{
    	    this.visitExpr(((Await)e).value);
    	    this.stCur.steGenerator = true;
    	}
    	else if(e instanceof Compare)
    	{
    	    Compare c = (Compare)e;
    	    this.visitExpr(c.left);
    	    for(exprType expr : c.comparators)
    	    {
    	        this.visitExpr(expr);
    	    }
    	}
    	else if(e instanceof Call)
    	{
    	    Call c = (Call)e;
    	    this.visitExpr(c.func);
    	    if(c.args != null) 
    	    {
    	        for(exprType expr : c.args)
        	    {
        	        this.visitExpr(expr);
        	    }
    	    }
    	    if(c.keywords != null)
    	    {
    	        for(keywordType k: c.keywords)
        	    {
        	        if(k == null) continue;
        	        this.visitKeyword(k);
        	    }
    	    }
    	}
    	else if(e instanceof Num ||
    	        e instanceof Str ||
    	        e instanceof Bytes ||
    	        e instanceof Ellipsis ||
    	        e instanceof NameConstant)
    	{
    	    return;
    	}
    	else if(e instanceof Attribute)
    	{
    	    this.visitExpr(((Attribute)e).value);
    	}
    	else if(e instanceof Subscript)
    	{
    	    Subscript subs = (Subscript)e;
    	    this.visitExpr(subs.value);
    	    this.visitSlice(subs.slice);
    	}
    	else if(e instanceof Starred)
    	{
    	    this.visitExpr(((Starred)e).value);
    	}
    	else if(e instanceof Name)
    	{
    	    Name n = (Name)e;
    	    this.addDef(n.id, n.ctx == expr_contextType.Load ? USE : DEF_LOCAL);
    	    if(n.ctx == expr_contextType.Load &&
    	            n.id.equals("super"))
    	    {
    	        __class__ = __class__ != null ? __class__ : "__class__";
    	        this.addDef(__class__, USE);
    	    }
    	}
    	else if(e instanceof List)
    	{
    	    List l = (List)e;
    	    if(l.elts != null)
    	    {
        	    for(exprType expr : ((List)e).elts)
        	    {
        	        this.visitExpr(expr);
        	    }
    	    }
    	}
    	else if(e instanceof Tuple)
    	{
    	    Tuple t = (Tuple)e;
    	    if(t.elts != null)
    	    {
        	    for(exprType expr : ((Tuple)e).elts)
        	    {
        	        this.visitExpr(expr);
        	    }
    	    }
    	}
    }
    
    private void enterBlock(String name,
    		BlockType block, Object ast, int lineno, int colOffet) // add parameter
    {
        System.out.println("enter: " + block);
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
    }
    
    private void exitBlock(Object ast)
    {
        System.out.println("exit");
    	int size; // TODO set the py list max size to int max size
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
    }
    
    private boolean analyzeBlock(PySTEntryObject ste, PySet bound, 
    		PySet free, PySet global)
    {
        // TODO
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
    	java.util.List<stmtType> seq;
    	
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
	    			st.visitStmt(seq.get(i));
	    		}
	    	}
	    	else if(mod instanceof Expression)
	    	{
	    		st.visitExpr(((Expression)mod).body);
	    	}
	    	else if(mod instanceof Interactive)
	    	{
	    		seq = ((Interactive)mod).body;
	    		for(int i = 0; i < seq.size(); i++)
	    		{
	    			st.visitStmt(seq.get(i));
	    		}
	    	}
	    	else if(mod instanceof Suite)
	    	{
	    		throw new PyExceptions("this compiler does not handle Suites");
	    	}
	    	
	    	st.exitBlock(mod);
	    	
	    	/* Make the second symbol analysis pass */
            /*if(st.analyze())
            {
            	return st;
            }*/
	    	return st;
    	}
    }
    
    public static void main(String args[])
    {        
        /*
        File file1 = new File("test/");
        if(file1.isDirectory())
        {
            
            String[] filelist = file1.list();
            for (String fileName : filelist)
            {
                System.out.println(fileName);
                File file = new File("test/" + fileName);
                if(file.isDirectory()) continue;
                try
                {
                    Node node = ParseToken.parseFile(file, GramInit.grammar, 1);
        
                    Ast ast = new Ast();
                    modType mod = ast.fromNode(node);
                    Symtable.buildObject(mod, null);
                }
                catch (PyExceptions e)
                {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        */
        File file = new File("test/test_doctest2.py");
        try
        {
            Node node = ParseToken.parseFile(file, GramInit.grammar, 1);
            //node.show();
            Ast ast = new Ast();
            modType mod = ast.fromNode(node);
            Symtable.buildObject(mod, null);
        }
        catch (PyExceptions e)
        {
            e.printStackTrace();
            throw e;
        }
    }
}
