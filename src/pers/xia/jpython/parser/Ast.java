package pers.xia.jpython.parser;

import java.util.ArrayList;

import pers.xia.jpython.ast.*;
import pers.xia.jpython.grammar.DFAType;
import pers.xia.jpython.object.Py;
import pers.xia.jpython.object.PyBytes;
import pers.xia.jpython.object.PyComplex;
import pers.xia.jpython.object.PyExceptions;
import pers.xia.jpython.object.PyExceptions.ErrorType;
import pers.xia.jpython.object.PyFloat;
import pers.xia.jpython.object.PyLong;
import pers.xia.jpython.object.PyObject;
import pers.xia.jpython.object.PyUnicode;

public class Ast
{
    private static enum COMP
    {
        COMP_GENEXP, COMP_LISTCOMP, COMP_SETCOMP
    }

    /*
     * Test the node's type is or not the assigened type
     */
    private void REQ(Node n, DFAType type)
    {
        try
        {
            myAssert(n.dfaType == type);
        }
        catch (PyExceptions e)
        {
            throw new PyExceptions(ErrorType.AST_ERROR, "AST Error", n);
        }
    }

    /*
     * The default assert method have some problems
     */
    private void myAssert(boolean b)
    {
        if(!b)
        {
            throw new PyExceptions(ErrorType.AST_ERROR, "AST Error");
        }
    }

    /*
     * Count the number of statement in node's child
     */
    private int numStmts(Node n)
    {
        int l; // the sum of statements
        switch (n.dfaType)
        {
        case single_input:
            Node ch = n.getChild(0);
            if(ch.dfaType == DFAType.NEWLINE)
            {
                return 0;
            }
            else
            {
                return numStmts(ch);
            }
        case file_input:
            l = 0;
            for (Node ch1 : n.childs)
            {
                if(ch1.dfaType == DFAType.stmt)
                {
                    l += numStmts(ch1);
                }
            }
            return l;
        case stmt:
            return numStmts(n.getChild(0));
        case compound_stmt:
            return 1;
        case simple_stmt:
            return n.nChild() / 2;
        case suite:
            if(n.nChild() == 1)
            {
                return numStmts(n.getChild(0));
            }
            else
            {
                l = 0;
                for (int i = 2; i < n.nChild() - 1; i++)
                {
                    l += numStmts(n.getChild(i));
                }
                return l;
            }
        default:
            throw new PyExceptions(ErrorType.AST_ERROR,
                    "Non-statement found: " + n.dfaType + " " + n.nChild(), n);
        }
    }

    private String newIdentifier(Node n)
    {
        return n.str;
    }

    private static final String FORBIDDEN[] = {"None", "True", "False",};

    private void forbiddenName(String name, Node n, boolean fullChecks)
    {
        if(name == null)
        {
            throw new PyExceptions(ErrorType.AST_ERROR, "assignment to keyword",
                    n);
        }
        if("__debug__".equals(name))
        {
            throw new PyExceptions(ErrorType.AST_ERROR, "assignment to keyword",
                    n);
        }
        if(fullChecks)
        {
            for (int i = 0; i < FORBIDDEN.length; i++)
            {
                if(FORBIDDEN[i].equals(name))
                {
                    throw new PyExceptions(ErrorType.AST_ERROR,
                            "assignment to keyword", n);
                }
            }
        }
    }

    private void setContext(exprType e, expr_contextType ctx, Node n)
    {
        String exprName = null;
        java.util.List<exprType> s = null;

        myAssert(ctx != expr_contextType.AugStore
                && ctx != expr_contextType.AugLoad);
        if(e instanceof Attribute)
        {
            ((Attribute) e).ctx = ctx;
        }
        else if(e instanceof Subscript)
        {
            ((Subscript) e).ctx = ctx;
        }
        else if(e instanceof Starred)
        {
            ((Starred) e).ctx = ctx;
            setContext(((Starred) e).value, ctx, n);
        }
        else if(e instanceof Name)
        {
            if(ctx == expr_contextType.Store)
            {
                forbiddenName(((Name) e).id, n, false);
            }
            ((Name) e).ctx = ctx;
        }
        else if(e instanceof List)
        {
            ((List) e).ctx = ctx;
            s = ((List) e).elts;
        }
        else if(e instanceof Tuple)
        {
            if(((Tuple) e).elts.size() > 0)
            {
                ((Tuple) e).ctx = ctx;
                s = ((Tuple) e).elts;
            }
            else
            {
                exprName = "()";
            }
        }
        else if(e instanceof Lambda)
        {
            exprName = "lambda";
        }
        else if(e instanceof Call)
        {
            exprName = "function call";
        }
        else if(e instanceof BoolOp || e instanceof BinOp
                || e instanceof UnaryOp)
        {
            exprName = "operator";
        }
        else if(e instanceof GeneratorExp)
        {
            exprName = "generator expression";
        }
        else if(e instanceof Yield || e instanceof YieldFrom)
        {
            exprName = "yield expression";
        }
        else if(e instanceof Await)
        {
            exprName = "await expression";
        }
        else if(e instanceof ListComp)
        {
            exprName = "list comprehension";
        }
        else if(e instanceof SetComp)
        {
            exprName = "set comprehension";
        }
        else if(e instanceof DictComp)
        {
            exprName = "dict comprehension";
        }
        else if(e instanceof Dict || e instanceof Set || e instanceof Num
                || e instanceof Str || e instanceof Bytes)
        {
            exprName = "literal";
        }
        else if(e instanceof NameConstant)
        {
            exprName = "keyword";
        }
        else if(e instanceof Ellipsis)
        {
            exprName = "Ellipsis";
        }
        else if(e instanceof Compare)
        {
            exprName = "comparison";
        }
        else if(e instanceof IfExp)
        {
            exprName = "conditional expression";
        }
        else
        {
            throw new PyExceptions(ErrorType.AST_ERROR,
                    "unexpected expression in assignment "
                            + e.getClass().getName() + " " + e.lineno,
                    n);
        }
        if(exprName != null)
        {
            throw new PyExceptions(ErrorType.AST_ERROR, String.format(
                    "can't %s %s",
                    ctx == expr_contextType.Store ? "assign to" : "delete",
                    exprName), n);
        }
        if(s != null)
        {
            for (int i = 0; i < s.size(); i++)
            {
                setContext(s.get(i), ctx, n);
            }
        }
    }

    private PyObject parseStr(Node n)
    {

        boolean bytesmode = false;
        boolean rawmode = false;
        //boolean needEncoding = false;
        String s = n.str;

        int start = 0;
        int end;
        char quote = s.charAt(0);
        myAssert(s != null);
        if(Character.isLetter(s.charAt(start)))
        {
            while (!bytesmode || !rawmode)
            {
                if(quote == 'b' || quote == 'B')
                {
                    quote = s.charAt(++start);
                    bytesmode = true;
                }
                else if(quote == 'u' || quote == 'U')
                {
                    quote = s.charAt(++start);
                }
                else if(quote == 'r' || quote == 'R')
                {
                    quote = s.charAt(++start);
                    rawmode = true;
                }
                else
                {
                    break;
                }
            }
        }

        if(quote != '\'' && quote != '"')
        {
            throw new PyExceptions(ErrorType.AST_ERROR, "bad internal call", n);
        }

        start++;

        end = s.length();
        if(s.charAt(--end) != quote)
        {
            throw new PyExceptions(ErrorType.AST_ERROR, "bad internal call", n);
        }

        if(end - start >= 4 && s.charAt(start) == quote
                && s.charAt(start + 1) == quote)
        {
            start += 2;
            if(s.charAt(--end) != quote || s.charAt(--end) != quote)
            {
                throw new PyExceptions(ErrorType.AST_ERROR, "bad internal call",
                        n);
            }
        }

        /*
        if(!bytesmode && !rawmode)
        {
            return decodeUnicode(s.substring(start, end), rawmode, Py.encoding);
        }
        */

        if(bytesmode)
        {
            for (int i = start; i < end; i++)
            {
                if(s.charAt(i) >= 0x80)
                {
                    throw new PyExceptions(ErrorType.AST_ERROR,
                            "bytes can only contain ASCII literal characters.",
                            n);
                }
            }
        }
        /*
        needEncoding = (!bytesmode && Py.encoding != null
                && Py.encoding.equals("utf-8"));
         */
        /*
         * 以下代码没有采用cPython的方案，赶脚cPython的方案在Java中
         * 不适用，希望我没有想错；
         */

        if(bytesmode)
        {
            return new PyBytes(s);
        }
        else
        {
            return PyUnicode.internFromString(s, rawmode);
        }
    }

    private Object[] astForDictelement(Node n, int i)
    {
        //  return a list, 0: i, 1: key 2: value
        Object[] result = new Object[3];

        exprType key;
        exprType value;

        exprType expression;

        if(n.getChild(i).dfaType == DFAType.DOUBLESTAR)
        {
            myAssert(n.nChild() - i >= 2);

            expression = astForExpr(n.getChild(i));

            key = null;
            value = expression;
            i += 2;
        }
        else
        {
            myAssert(n.nChild() - i >= 3);

            expression = astForExpr(n.getChild(i));
            key = expression;

            REQ(n.getChild(i + 1), DFAType.COLON);

            expression = astForExpr(n.getChild(i + 2));
            value = expression;

            i += 3;
        }

        result[0] = i;
        result[1] = key;
        result[2] = value;

        return result;
    }

    private int countCompFors(Node n)
    {
        int nFors = 0;

        count_comp_for: while (true)
        {
            nFors++;
            REQ(n, DFAType.comp_for);
            if(n.nChild() == 5)
            {
                n = n.getChild(4);
            }
            else
            {
                return nFors;
            }

            count_comp_iter: while (true)
            {
                REQ(n, DFAType.comp_iter);
                n = n.getChild(0);
                if(n.dfaType == DFAType.comp_for)
                {
                    continue count_comp_for;
                }
                else if(n.dfaType == DFAType.comp_if)
                {
                    if(n.nChild() == 3)
                    {
                        n = n.getChild(2);
                        continue count_comp_iter;
                    }
                    else
                    {
                        return nFors;
                    }
                }
                else
                {
                    break count_comp_for;
                }
            }
        }

        throw new PyExceptions(ErrorType.AST_ERROR,
                "logic error in count_comp_fors", n);
    }

    private int countCompIfs(Node n)
    {
        int nIfs = 0;

        while (true)
        {
            REQ(n, DFAType.comp_iter);
            if(n.getChild(0).dfaType == DFAType.comp_for)
            {
                return nIfs;
            }
            n = n.getChild(0);

            REQ(n, DFAType.comp_if);
            nIfs++;

            if(n.nChild() == 2)
            {
                return nIfs;
            }
            n = n.getChild(2);
        }
    }

    private PyObject parseStrPlus(Node n)
    {
        PyObject v;
        REQ(n.getChild(0), DFAType.STRING);

        v = parseStr(n.getChild(0));

        boolean bytesmode = (v instanceof PyBytes);

        for (int i = 1; i < n.nChild(); i++)
        {
            PyObject s;
            boolean subbm = false;
            s = parseStr(n.getChild(i));

            subbm = (s instanceof PyBytes);
            if(bytesmode != subbm)
            {
                throw new PyExceptions(ErrorType.AST_ERROR,
                        "cannot mix bytes and nonbytes literal", n);
            }

            // XXX 源代码使用了PyType来检查v和s是否为PyBytes
            if(v instanceof PyBytes && s instanceof PyBytes)
            {
                v = ((PyBytes) v).concat((PyBytes) s);
            }
            else
            {
                v = PyUnicode.concat(v, s);
            }
        }
        return v;
    }

    private PyObject parseNumber(Node n)
    {
        int end;
        long x;
        double dx;
        boolean imflag;

        String s = n.str;
        myAssert(s != null);

        end = s.length() - 1;

        imflag = (s.charAt(end) == 'j' || s.charAt(end) == 'J');
        if(imflag)
        {
            double imag = Double.parseDouble(s.substring(0, s.length() - 1));
            return new PyComplex(0., imag);
        }
        else
        {
            if(s.indexOf('.') > 0 || s.indexOf('E') > 0 || s.indexOf('e') > 0)
            {
                return new PyFloat(s);
            }
            else
            {
                return PyLong.newLong(s);
            }
        }
    }

    private exprType astForListcomp(Node n)
    {
        myAssert(n.dfaType == DFAType.testlist_comp);
        return astForItercomp(n, COMP.COMP_LISTCOMP);
    }

    private exprType astForSetdisplay(Node n)
    {
        java.util.List<exprType> elts;

        myAssert(n.dfaType == DFAType.dictorsetmaker);

        elts = new ArrayList<exprType>((n.nChild() + 1) / 2);

        for (int i = 0; i < n.nChild(); i += 2)
        {
            exprType expression;
            expression = astForExpr(n.getChild(i));
            elts.add(expression);
        }
        return new Set(elts, n.lineNo, n.colOffset);
    }

    private exprType astForSetcomp(Node n)
    {
        myAssert(n.dfaType == DFAType.dictorsetmaker);
        return astForItercomp(n, COMP.COMP_SETCOMP);
    }

    private exprType astForDictcomp(Node n)
    {
        exprType key = null;
        exprType value = null;
        java.util.List<comprehensionType> comps;
        int i = 0;

        Object[] result = astForDictelement(n, i);
        i = (int) result[0];
        key = (exprType) result[1];
        value = (exprType) result[2];

        myAssert(key != null);
        myAssert(n.nChild() - i >= 1);

        comps = astForComprehension(n.getChild(i));

        return new DictComp(key, value, comps, n.lineNo, n.colOffset);
    }

    private exprType astForDictdisplay(Node n)
    {
        java.util.List<exprType> keys;
        java.util.List<exprType> values;

        int size = (n.nChild() + 1) / 3;

        keys = new ArrayList<exprType>(size);
        values = new ArrayList<exprType>(size);

        for (int i = 0; i < n.nChild(); i++)
        {
            exprType key;
            exprType value;

            Object[] result = astForDictelement(n, i);
            i = (int) result[0];
            key = (exprType) result[1];
            value = (exprType) result[2];

            keys.add(key);
            values.add(value);
        }
        return new Dict(keys, values, n.lineNo, n.colOffset);
    }

    private sliceType astForSlice(Node n)
    {
        Node ch;
        exprType lower = null;
        exprType upper = null;
        exprType step = null;

        REQ(n, DFAType.subscript);

        /*
            subscript: test | [test] ':' [test] [sliceop
            sliceop: ':' [test]
         */
        ch = n.getChild(0);
        if(n.nChild() == 1 && ch.dfaType == DFAType.test)
        {
            /* 'step' variable hold no significance in terms of being used over
            other vars */
            step = astForExpr(ch);

            return new Index(step);
        }

        if(ch.dfaType == DFAType.COLON)
        {
            if(n.nChild() > 1)
            {
                Node n2 = n.getChild(1);
                if(n2.dfaType == DFAType.test)
                {
                    upper = astForExpr(n2);
                }
            }
        }
        else if(n.nChild() > 2)
        {
            Node n2 = n.getChild(2);

            if(n2.dfaType == DFAType.test)
            {
                upper = astForExpr(n2);
            }
        }

        ch = n.getChild(-1);

        if(ch.dfaType == DFAType.sliceop)
        {
            if(ch.nChild() != 1)
            {
                ch = ch.getChild(1);
                if(ch.dfaType == DFAType.test)
                {
                    step = astForExpr(ch);
                }
            }
        }
        return new Slice(lower, upper, step);
    }

    /*
     * returns -1 if failed to handle keyword only arguments returns new
     * position to keep processing if successful (',' tfpdef ['=' test])* ^^^
     * start pointing here
     */
    private int handleKeywordonlyArgs(Node n, int start,
            java.util.List<argType> kwonlyargs,
            java.util.List<exprType> kwdefaults)
    {
        String argName;
        Node ch;
        exprType expression;
        exprType annotation;
        argType arg;
        int i = start;

        if(kwonlyargs == null)
        {
            throw new PyExceptions(ErrorType.AST_ERROR,
                    "named arguments must follow bare *", n);
        }

        myAssert(kwdefaults != null);
        while (i < n.nChild())
        {
            ch = n.getChild(i);
            switch (ch.dfaType)
            {
            case vfpdef:
            case tfpdef:
                if(i + 1 < n.nChild()
                        && n.getChild(i + 1).dfaType == DFAType.EQUAL)
                {
                    expression = astForExpr(n.getChild(i + 2));
                    kwdefaults.add(expression);
                    i += 2;
                }
                else
                {
                    kwdefaults.add(null);
                }

                if(ch.nChild() == 3)
                {
                    /* ch is NAME ':' test */
                    annotation = astForExpr(ch.getChild(2));
                }
                else
                {
                    annotation = null;
                }
                ch = ch.getChild(0);
                argName = newIdentifier(ch);
                forbiddenName(argName, ch, false);
                arg = new argType(argName, annotation, ch.lineNo, ch.colOffset);
                kwonlyargs.add(arg);
                i += 2;
                break;
            case DOUBLESTAR:
                return i;
            default:
                throw new PyExceptions(ErrorType.AST_ERROR, "unexpected node",
                        ch);
            }
        }
        return i;
    }

    private java.util.List<comprehensionType> astForComprehension(Node n)
    {
        int nFors;
        java.util.List<comprehensionType> comps;

        nFors = countCompFors(n);

        comps = new ArrayList<comprehensionType>(nFors);

        for (int i = 0; i < nFors; i++)
        {
            comprehensionType comp;
            java.util.List<exprType> t;
            exprType expression;
            exprType first;
            Node forCh;

            REQ(n, DFAType.comp_for);

            forCh = n.getChild(1);
            t = astForExprlist(forCh, expr_contextType.Store);
            expression = astForExpr(n.getChild(3));

            /*
             * Check the # of children rather than the length of t, since (x for
             * x, in ...) has 1 element in t, but still requires a Tuple.
             */
            first = t.get(0);

            if(forCh.nChild() == 1)
            {
                comp = new comprehensionType(first, expression, null);
            }
            else
            {
                comp = new comprehensionType(new Tuple(t,
                        expr_contextType.Store, first.lineno, first.col_offset),
                        expression, null);
            }

            if(n.nChild() == 5)
            {
                int nIfs;
                java.util.List<exprType> ifs;

                n = n.getChild(4);
                nIfs = countCompIfs(n);

                ifs = new ArrayList<exprType>(nIfs);

                for (int j = 0; j < nIfs; j++)
                {
                    REQ(n, DFAType.comp_iter);
                    n = n.getChild(0);
                    REQ(n, DFAType.comp_if);

                    expression = astForExpr(n.getChild(1));
                    ifs.add(expression);

                    if(n.nChild() == 3)
                    {
                        n = n.getChild(2);
                    }
                }
                /* on exit, must guarantee that n is a comp_for */
                if(n.dfaType == DFAType.comp_iter)
                {
                    n = n.getChild(0);
                }
                comp.ifs = ifs;
            }
            comps.add(comp);
        }
        return comps;
    }

    private argType astForArg(Node n)
    {
        String name;
        exprType annotation = null;
        Node ch;

        myAssert(n.dfaType == DFAType.tfpdef || n.dfaType == DFAType.vfpdef);

        ch = n.getChild(0);
        name = newIdentifier(ch);
        forbiddenName(name, ch, false);

        if(n.nChild() == 3 && n.getChild(1).dfaType == DFAType.COLON)
        {
            annotation = astForExpr(n.getChild(2));
        }

        return new argType(name, annotation, n.lineNo, n.colOffset);
    }

    private operatorType getOperator(Node n)
    {
        switch (n.dfaType)
        {
        case VBAR:
            return operatorType.BitOr;
        case CIRCUMFLEX:
            return operatorType.BitXor;
        case AMPER:
            return operatorType.BitAnd;
        case LEFTSHIFT:
            return operatorType.LShift;
        case RIGHTSHIFT:
            return operatorType.RShift;
        case PLUS:
            return operatorType.Add;
        case MINUS:
            return operatorType.Sub;
        case STAR:
            return operatorType.Mult;
        case AT:
            return operatorType.MatMult;
        case SLASH:
            return operatorType.Div;
        case DOUBLESLASH:
            return operatorType.FloorDiv;
        case PERCENT:
            return operatorType.Mod;
        default:
            throw new PyExceptions(ErrorType.AST_ERROR, "invlid operator", n);
        }
    }

    private exprType astForDottedName(Node n)
    {
        exprType e;
        String id;

        REQ(n, DFAType.dotted_name);

        id = newIdentifier(n.getChild(0));

        e = new Name(id, expr_contextType.Load, n.lineNo, n.colOffset);

        for (int i = 2; i < n.nChild(); i += 2)
        {
            id = newIdentifier(n.getChild(i));

            e = new Attribute(e, id, expr_contextType.Load, n.lineNo,
                    n.colOffset);
        }
        return e;
    }

    private exprType astForAtom(Node n)
    {
        /*
         * atom: '(' [yield_expr|testlist_comp] ')' | '[' [testlist_comp] ']' |
         * '{' [dictmaker|testlist_comp] '}' | NAME | NUMBER | STRING+ | '...' |
         * 'None' | 'True' | 'False'
         */
        Node ch = n.getChild(0);

        // int bytesmode = 0;
        switch (ch.dfaType)
        {
        case NAME:
        {
            String s = ch.str;
            int len = s.length();
            if(len >= 4 && len <= 5)
            {

                if("None".equals(s))
                {
                    return new NameConstant(Py.None, n.lineNo, n.colOffset);
                }
                if("True".equals(s))
                {
                    return new NameConstant(Py.True, n.lineNo, n.colOffset);
                }
                if("False".equals(s))
                {
                    return new NameConstant(Py.False, n.lineNo, n.colOffset);
                }
            }

            if(newIdentifier(ch) == null)
            {
                throw new PyExceptions(ErrorType.AST_ERROR,
                        "node's str is null", ch);
            }

            return new Name(s, expr_contextType.Load, n.lineNo, n.colOffset);
        }
        case STRING:
        {
            PyObject str = parseStrPlus(n);
            if(str instanceof PyBytes)
            {
                return new Bytes(str, n.lineNo, n.colOffset);
            }
            else
            {
                return new Str(str, n.lineNo, n.colOffset);
            }
        }
        case NUMBER:
        {
            PyObject pyNum = parseNumber(ch);
            return new Num(pyNum, n.lineNo, n.colOffset);
        }
        case ELLIPSIS:
            return new Ellipsis(n.lineNo, n.colOffset);
        case LPAR:
            ch = n.getChild(1);
            if(ch.dfaType == DFAType.RPAR)
            {
                return new Tuple(null, expr_contextType.Load, n.lineNo,
                        n.colOffset);
            }

            if(ch.dfaType == DFAType.yield_expr)
            {
                return astForExpr(ch);
            }

            /* testlist_comp: test ( comp_for | (',' test)* [','] ) */
            if(ch.nChild() > 1 && ch.getChild(1).dfaType == DFAType.comp_for)
            {
                return astForGenexp(ch);
            }

            return astForTestlist(ch);
        case LSQB: /* list (or list comprehension) */
            ch = n.getChild(1);

            if(ch.dfaType == DFAType.RSQB)
            {
                return new List(null, expr_contextType.Load, n.lineNo,
                        n.colOffset);
            }

            REQ(ch, DFAType.testlist_comp);
            if(ch.nChild() == 1 || ch.getChild(1).dfaType == DFAType.COMMA)
            {
                java.util.List<exprType> elts = seqForTestlist(ch);
                return new List(elts, expr_contextType.Load, n.lineNo,
                        n.colOffset);
            }
            else
            {
                return astForListcomp(ch);
            }
        case LBRACE:
        {
            /*
             * dictorsetmaker: ( ((test ':' test | '**' test) (comp_for | (','
             * (test ':' test | '**' test))* [','])) | ((test | '*' test)
             * (comp_for | (',' (test | '*' test))* [','])) )
             */
            ch = n.getChild(1);

            if(ch.dfaType == DFAType.RBRACE)
            {
                return new Dict(null, null, n.lineNo, n.colOffset);
            }
            else
            {
                boolean isDict = (ch.getChild(0).dfaType == DFAType.DOUBLESTAR);
                if(ch.nChild() == 1 || (ch.nChild() > 1
                        && ch.getChild(1).dfaType == DFAType.COMMA))
                {
                    return astForSetdisplay(ch);
                }
                else if(ch.nChild() > 1
                        && ch.getChild(1).dfaType == DFAType.comp_for)
                {
                    return astForSetcomp(ch);
                }
                else if(ch.nChild() > 3 - (isDict ? 1 : 0) && ch.getChild(
                        3 - (isDict ? 1 : 0)).dfaType == DFAType.comp_for)
                {
                    if(isDict)
                    {
                        throw new PyExceptions(ErrorType.AST_ERROR,
                                "dict unpacking cannot be used in dict comprehension",
                                n);
                    }
                    return astForDictcomp(ch);
                }
                else
                {
                    return astForDictdisplay(ch);
                }
            }
        }
        default:
            throw new PyExceptions(ErrorType.AST_ERROR,
                    "unhandled atom " + ch.dfaType, n);
        }
    }

    private exprType astForTrailer(Node n, exprType leftExpr)
    {
        /* trailer: '(' [arglist] ')' | '[' subscriptlist ']' | '.' NAME
            subscriptlist: subscript (',' subscript)* [',']
            subscript: '.' '.' '.' | test | [test] ':' [test] [sliceop]
         */
        REQ(n, DFAType.trailer);

        if(n.getChild(0).dfaType == DFAType.LPAR)
        {
            if(n.nChild() == 2)
            {
                return new Call(leftExpr, null, null, n.lineNo, n.colOffset);
            }
            else
            {
                return astForCall(n.getChild(1), leftExpr);
            }
        }
        else if(n.getChild(0).dfaType == DFAType.DOT)
        {
            String attrId = newIdentifier(n.getChild(1));
            return new Attribute(leftExpr, attrId, expr_contextType.Load,
                    n.lineNo, n.colOffset);
        }
        else
        {
            REQ(n.getChild(0), DFAType.LSQB);
            REQ(n.getChild(2), DFAType.RSQB);
            n = n.getChild(1);

            if(n.nChild() == 1)
            {
                sliceType slc = astForSlice(n.getChild(0));
                return new Subscript(leftExpr, slc, expr_contextType.Load,
                        n.lineNo, n.colOffset);
            }
            else
            {
                /* The grammar is ambiguous here. The ambiguity is resolved
                    by treating the sequence as a tuple literal if there are
                    no slice features.
                 */
                sliceType slc;
                exprType e;

                boolean simple = true;
                java.util.List<sliceType> slices;
                java.util.List<exprType> elts;

                slices = new ArrayList<sliceType>((n.nChild() + 1) / 2);

                for (int j = 0; j < n.nChild(); j += 2)
                {
                    slc = astForSlice(n.getChild(j));

                    if(!(slc instanceof Index))
                    {
                        simple = false;
                    }
                    slices.add(slc);
                }

                if(!simple)
                {
                    return new Subscript(leftExpr, new ExtSlice(slices),
                            expr_contextType.Load, n.lineNo, n.colOffset);
                }
                /* extract Index values and put them in a Tuple */
                elts = new ArrayList<exprType>(slices.size());

                for (int j = 0; j < slices.size(); j++)
                {
                    slc = slices.get(j);
                    myAssert(slc instanceof Index
                            && ((Index) slc).value != null);

                    elts.add(((Index) slc).value);
                }
                e = new Tuple(elts, expr_contextType.Load, n.lineNo,
                        n.colOffset);

                return new Subscript(leftExpr, new Index(e),
                        expr_contextType.Load, n.lineNo, n.colOffset);
            }
        }
    }

    private exprType astForItercomp(Node n, COMP type)
    {
        /*
         * testlist_comp: (test|star_expr) ( comp_for | (',' (test|star_expr))*
         * [','] )
         */
        myAssert(n.nChild() > 1);

        exprType elt;
        java.util.List<comprehensionType> comps;

        Node ch = n.getChild(0);
        elt = astForExpr(ch);

        if(elt instanceof Starred)
        {
            throw new PyExceptions(ErrorType.AST_ERROR,
                    "iterable unpacking cannot be used in comprehension", n);
        }
        comps = astForComprehension(n.getChild(1));

        switch (type)
        {
        case COMP_GENEXP:
            return new GeneratorExp(elt, comps, n.lineNo, n.colOffset);
        case COMP_LISTCOMP:
            return new ListComp(elt, comps, n.lineNo, n.colOffset);
        case COMP_SETCOMP:
            return new SetComp(elt, comps, n.lineNo, n.colOffset);
        default:
            throw new PyExceptions(ErrorType.AST_ERROR, "invalid type: " + type,
                    n);
        }
    }

    private argumentsType astForArguments(Node n)
    {
        /* This function handles both typedargslist (function definition)
            and varargslist (lambda definition).
        
            parameters: '(' [typedargslist] ')'
            typedargslist: ((tfpdef ['=' test] ',')*
                ('*' [tfpdef] (',' tfpdef ['=' test])* [',' '**' tfpdef]
                | '**' tfpdef)
                | tfpdef ['=' test] (',' tfpdef ['=' test])* [','])
            tfpdef: NAME [':' test]
            varargslist: ((vfpdef ['=' test] ',')*
                ('*' [vfpdef] (',' vfpdef ['=' test])*  [',' '**' vfpdef]
                | '**' vfpdef)
                | vfpdef ['=' test] (',' vfpdef ['=' test])* [','])
            vfpdef: NAME
         */

        int nposargs = 0;
        int nkwonlyargs = 0;
        int nposdefaults = 0;
        boolean foundDefault = false;

        java.util.List<argType> posargs;
        java.util.List<exprType> posdefaults;
        java.util.List<argType> kwonlyargs;
        java.util.List<exprType> kwdefaults;

        argType vararg = null;
        argType kwarg = null;
        argType arg;
        Node ch;

        int i;

        if(n.dfaType == DFAType.parameters)
        {
            if(n.nChild() == 2) /* () as argument list */
            {
                return new argumentsType(null, null, null, null, null, null);
            }
            n = n.getChild(1);
        }

        myAssert(n.dfaType == DFAType.typedargslist
                || n.dfaType == DFAType.varargslist);

        /*
         * First count the number of positional args & defaults. The variable i
         * is the loop index for this for loop and the next. The next loop picks
         * up where the first leaves off.
         */
        for (i = 0; i < n.nChild(); i++)
        {
            ch = n.getChild(i);
            if(ch.dfaType == DFAType.STAR)
            {
                /* skip star */
                i++;
                if(i < n.nChild() && /* skip argument following star */
                        (n.getChild(i).dfaType == DFAType.tfpdef
                                || n.getChild(i).dfaType == DFAType.vfpdef))
                {
                    i++;
                }
                break;
            }
            if(ch.dfaType == DFAType.DOUBLESTAR)
            {
                break;
            }
            if(ch.dfaType == DFAType.vfpdef || ch.dfaType == DFAType.tfpdef)
            {
                nposargs++;
            }
            if(ch.dfaType == DFAType.EQUAL)
            {
                nposdefaults++;
            }
        }

        /*
         * count the number of keyword only args & defaults for keyword only
         * args
         */
        for (; i < n.nChild(); i++)
        {
            ch = n.getChild(i);
            if(ch.dfaType == DFAType.DOUBLESTAR)
            {
                break;
            }
            if(ch.dfaType == DFAType.tfpdef || ch.dfaType == DFAType.vfpdef)
            {
                nkwonlyargs++;
            }
        }
        posargs = nposargs > 0 ? new ArrayList<argType>(nposargs) : null;
        kwonlyargs = nkwonlyargs > 0 ? new ArrayList<argType>(nkwonlyargs)
                : null;
        posdefaults = nposdefaults > 0 ? new ArrayList<exprType>(nposdefaults)
                : null;
        kwdefaults = nkwonlyargs > 0 ? new ArrayList<exprType>(nkwonlyargs)
                : null;

        if(nposargs + nkwonlyargs > 255)
        {
            throw new PyExceptions(ErrorType.AST_ERROR,
                    "more than 255 arguments", n);
        }

        /*
         * tfpdef: NAME [':' test] vfpdef: NAME
         */
        i = 0;
        while (i < n.nChild())
        {
            ch = n.getChild(i);
            switch (ch.dfaType)
            {
            case tfpdef:
            case vfpdef:
                if(n.nChild() > i + 1
                        && n.getChild(i + 1).dfaType == DFAType.EQUAL)
                {
                    exprType expression = astForExpr(n.getChild(i + 2));

                    myAssert(posdefaults != null);
                    posdefaults.add(expression);
                    i += 2;
                    foundDefault = true;
                }
                else if(foundDefault)
                {
                    new PyExceptions(ErrorType.AST_ERROR,
                            "non-default argument follows default argument", n);
                }
                arg = astForArg(ch);
                posargs.add(arg);
                i += 2;
                break;
            case STAR:
                if(i + 1 >= n.nChild())
                {
                    throw new PyExceptions(ErrorType.AST_ERROR,
                            "named arguments must follow bare *", n);
                }
                ch = n.getChild(i + 1); /* tfpdef or COMMA */
                if(ch.dfaType == DFAType.COMMA)
                {
                    int res = 0;
                    i += 2;
                    res = handleKeywordonlyArgs(n, i, kwonlyargs, kwdefaults);
                    i = res;
                }
                else
                {
                    vararg = astForArg(ch);

                    i += 3;
                    if(i < n.nChild()
                            && (n.getChild(i).dfaType == DFAType.tfpdef
                                    || n.getChild(i).dfaType == DFAType.vfpdef))
                    {
                        int res = 0;
                        res = handleKeywordonlyArgs(n, i, kwonlyargs,
                                kwdefaults);
                        i = res;
                    }
                }
                break;
            case DOUBLESTAR:
                ch = n.getChild(i + 1);
                myAssert(ch.dfaType == DFAType.tfpdef
                        || ch.dfaType == DFAType.vfpdef);
                kwarg = astForArg(ch);

                i += 3;
                break;
            default:
                throw new PyExceptions(ErrorType.AST_ERROR,
                        String.format("unexpected node in varargslist: %s @ %d",
                                ch.dfaType.toString(), i),
                        n);
            }
        }
        return new argumentsType(posargs, vararg, kwonlyargs, kwdefaults, kwarg,
                posdefaults);
    }

    private exprType astForBinop(Node n)
    {
        /*
         * Must account for a sequence of expressions. How should A op B op C by
         * represented? BinOp(BinOp(A, op, B), op, C).
         */
        exprType expr1;
        exprType expr2;
        exprType result;

        operatorType newoperator;

        expr1 = astForExpr(n.getChild(0));
        expr2 = astForExpr(n.getChild(2));
        newoperator = getOperator(n.getChild(1));
        result = new BinOp(expr1, newoperator, expr2, n.lineNo, n.colOffset);

        for (int i = 1; i < (n.nChild() - 1) / 2; i++)
        {
            exprType tmpResult;
            exprType tmp;

            Node nextOper = n.getChild(i * 2 + 1);

            newoperator = getOperator(nextOper);
            tmp = astForExpr(n.getChild(i * 2 + 2));
            tmpResult = new BinOp(result, newoperator, tmp, nextOper.lineNo,
                    nextOper.colOffset);

            result = tmpResult;
        }

        return result;
    }

    private exprType astForDecorator(Node n)
    {
        /* decorator: '@' dotted_name [ '(' [arglist] ')' ] NEWLINE */
        REQ(n, DFAType.decorator);
        REQ(n.getChild(0), DFAType.AT);
        REQ(n.getChild(-1), DFAType.NEWLINE);

        exprType d = null;
        exprType nameExpr;

        nameExpr = astForDottedName(n.getChild(1));

        if(n.nChild() == 3)
        {
            d = nameExpr;
        }
        else if(n.nChild() == 5)
        {
            d = new Call(nameExpr, null, null, n.lineNo, n.colOffset);
        }
        else
        {
            d = astForCall(n.getChild(3), nameExpr);
        }

        return d;
    }

    private exprType astForAtomExpr(Node n)
    {
        /*
         * atom_expr: [AWAIT] atom trailer* atom: ('('
         * [yield_expr|testlist_comp] ')' | '[' [testlist_comp] ']' | '{'
         * [dictorsetmaker] '}' |
         */
        REQ(n, DFAType.atom_expr);

        int start = 0;
        exprType e;
        exprType tmp;

        if(n.getChild(0).dfaType == DFAType.AWAIT)
        {
            start = 1;
            myAssert(n.nChild() > 1);
        }

        e = astForAtom(n.getChild(start));
        if(n.nChild() == 1)
        {
            return e;
        }
        if(start > 0 && n.nChild() == 2)
        {
            return new Await(e, n.lineNo, n.colOffset);
        }

        for (int i = start + 1; i < n.nChild(); i++)
        {
            Node ch = n.getChild(i);
            if(ch.dfaType != DFAType.trailer)
            {
                break;
            }
            tmp = astForTrailer(ch, e);
            tmp.lineno = e.lineno;
            tmp.col_offset = e.col_offset;
            e = tmp;
        }

        if(start > 0)
        {
            return new Await(e, n.lineNo, n.colOffset);
        }
        else
        {
            return e;
        }
    }

    private exprType astForGenexp(Node n)
    {
        myAssert(n.dfaType == DFAType.testlist_comp
                || n.dfaType == DFAType.argument);
        return astForItercomp(n, COMP.COMP_GENEXP);
    }

    private operatorType astForAugassign(Node n)
    {
        REQ(n, DFAType.augassign);
        n = n.getChild(0);
        switch (n.str)
        {
        case "+=":
            return operatorType.Add;
        case "-=":
            return operatorType.Sub;
        case "/=":
            return operatorType.Div;
        case "//=":
            return operatorType.FloorDiv;
        case "%=":
            return operatorType.Mod;
        case "<<=":
            return operatorType.LShift;
        case ">>=":
            return operatorType.RShift;
        case "&=":
            return operatorType.BitAnd;
        case "^=":
            return operatorType.BitXor;
        case "|=":
            return operatorType.BitOr;
        case "*=":
            return operatorType.Mult;
        case "**=":
            return operatorType.Pow;
        case "@=":
            return operatorType.Mult;
        default:
            throw new PyExceptions(ErrorType.AST_ERROR,
                    "invalid augassign: " + n.str, n);
        }
    }

    private java.util.List<exprType> astForExprlist(Node n,
            expr_contextType context)
    {
        REQ(n, DFAType.exprlist);
        java.util.List<exprType> seq = new ArrayList<exprType>(
                (n.nChild() + 1) / 2);
        exprType e;

        for (int i = 0; i < n.nChild(); i += 2)
        {
            e = astForExpr(n.getChild(i));
            seq.add(e);
            if(context != null)
            {
                setContext(e, context, n.getChild(i));
            }
        }
        return seq;
    }

    private aliasType aliasForImportName(Node n, boolean store)
    {
        /*
         * import_as_name: NAME ['as' NAME] dotted_as_name: dotted_name ['as'
         * NAME] dotted_name: NAME ('.' NAME)*
         */
        String name = null;
        String str = null;

        while (true)
        {
            switch (n.dfaType)
            {
            case import_as_name:
            {
                Node nameNode = n.getChild(0);
                name = newIdentifier(nameNode);
                if(n.nChild() == 3)
                {
                    Node strNode = n.getChild(2);
                    str = newIdentifier(strNode);
                    if(store)
                    {
                        forbiddenName(str, strNode, false);
                    }
                }
                else
                {
                    forbiddenName(name, nameNode, false);
                }
                return new aliasType(name, str);
            }
            case dotted_as_name:
                if(n.nChild() == 1)
                {
                    n = n.getChild(0);
                    continue;
                }
                else
                {
                    Node asnameNode = n.getChild(2);
                    aliasType a = aliasForImportName(n.getChild(0), false);
                    myAssert(a.asname == null);
                    a.asname = newIdentifier(asnameNode);
                    forbiddenName(a.asname, asnameNode, false);
                    return a;
                }
            case dotted_name:
                if(n.nChild() == 1)
                {
                    Node nameNode = n.getChild(0);
                    name = newIdentifier(nameNode);
                    if(store)
                    {
                        forbiddenName(name, nameNode, false);
                    }
                    return new aliasType(name, null);
                }
                else
                {
                    /* Create a string of the form "a.b.c" */
                    str = "";
                    java.util.List<String> tmp = new ArrayList<String>(
                            n.nChild());
                    for (int i = 0; i < n.nChild(); i += 2)
                    {
                        tmp.add(n.getChild(i).str);
                    }
                    str = String.join(".", tmp);
                    return new aliasType(str, null);
                }
            case STAR:
                str = "*";
                return new aliasType(str, null);
            default:
                throw new PyExceptions(ErrorType.AST_ERROR,
                        "unexpected import name: " + n.dfaType, n);
            }
        }
    }

    private java.util.List<stmtType> astForSuite(Node n)
    {
        /* suite: simple_stmt | NEWLINE INDENT stmt+ DEDENT */
        REQ(n, DFAType.suite);

        int end;
        java.util.List<stmtType> seq = new ArrayList<stmtType>(numStmts(n));
        Node ch = null;
        stmtType s = null;

        if(n.getChild(0).dfaType == DFAType.simple_stmt)
        {
            n = n.getChild(0);

            /*
             * simple_stmt always ends with a NEWLINE, and may have a trailing
             * SEMI
             */
            end = n.nChild() - 1;
            if(n.getChild(end).dfaType == DFAType.SEMI)
            {
                end--;
            }

            for (int i = 0; i < end; i += 2)
            {
                ch = n.getChild(i);
                s = astForStmt(ch);
                seq.add(s);
            }
        }
        else
        {
            for (int i = 2; i < n.nChild() - 1; i++)
            {
                ch = n.getChild(i);
                REQ(ch, DFAType.stmt);
                if(numStmts(ch) == 1)
                {
                    s = astForStmt(ch);
                    seq.add(s);
                }
                else
                {
                    ch = ch.getChild(0);
                    REQ(ch, DFAType.simple_stmt);
                    for (int j = 0; j < ch.nChild(); j += 2)
                    {
                        if(ch.getChild(j).nChild() == 0)
                        {
                            myAssert((j + 1) == ch.nChild());
                            break;
                        }
                        s = astForStmt(ch.getChild(j));
                        seq.add(s);
                    }
                }
            }
        }
        return seq;
    }

    private excepthandlerType astForExceptClause(Node exc, Node body)
    {
        /* except_clause: 'except' [test ['as' test]] */
        REQ(exc, DFAType.except_clause);
        REQ(body, DFAType.suite);

        if(exc.nChild() == 1)
        {
            java.util.List<stmtType> suiteSeq = astForSuite(body);
            return new ExceptHandler(null, null, suiteSeq, exc.lineNo,
                    exc.colOffset);
        }
        else if(exc.nChild() == 2)
        {
            exprType expression = astForExpr(exc.getChild(1));
            java.util.List<stmtType> suiteSeq = astForSuite(body);
            return new ExceptHandler(expression, null, suiteSeq, exc.lineNo,
                    exc.colOffset);
        }
        else if(exc.nChild() == 4)
        {
            String e = newIdentifier(exc.getChild(3));
            forbiddenName(e, exc.getChild(3), false);

            exprType expression = astForExpr(exc.getChild(1));
            java.util.List<stmtType> suiteSeq = astForSuite(body);

            return new ExceptHandler(expression, e, suiteSeq, exc.lineNo,
                    exc.colOffset);
        }
        throw new PyExceptions(ErrorType.AST_ERROR,
                "wrong number of children for 'except' clause: " + exc.nChild(),
                exc);
    }

    private withitemType astForWithItem(Node n)
    {
        /* with_item: test ['as' expr] */
        REQ(n, DFAType.with_item);

        exprType contextExpr = astForExpr(n.getChild(0));
        exprType optionalVars = null;

        if(n.nChild() == 3)
        {
            optionalVars = astForExpr(n.getChild(2));
            setContext(optionalVars, expr_contextType.Store, n);
        }
        return new withitemType(contextExpr, optionalVars);
    }

    private stmtType astForFuncdefImpl(Node n,
            java.util.List<exprType> decoratorSeq, boolean isAsync)
    {
        /* funcdef: 'def' NAME parameters ['->' test] ':' suite */
        REQ(n, DFAType.funcdef);

        String name;
        argumentsType args;
        java.util.List<stmtType> body;
        exprType returns = null;
        int nameI = 1;

        name = newIdentifier(n.getChild(nameI));
        forbiddenName(name, n.getChild(nameI), false);

        args = astForArguments(n.getChild(nameI + 1));
        if(n.getChild(nameI + 2).dfaType == DFAType.RARROW)
        {
            returns = astForExpr(n.getChild(nameI + 3));
            nameI += 2;
        }
        body = astForSuite(n.getChild(nameI + 3));

        if(isAsync)
        {
            return new AsyncFunctionDef(name, args, body, decoratorSeq, returns,
                    n.lineNo, n.colOffset);
        }
        else
        {
            return new FunctionDef(name, args, body, decoratorSeq, returns,
                    n.lineNo, n.colOffset);
        }
    }

    private exprType astForCall(Node n, exprType func)
    {
        /*
         * arglist: argument (',' argument)* [','] argument: ( test [comp_for] |
         * '*' test | test '=' test | '**' test )
         */
        REQ(n, DFAType.arglist);

        int nargs = 0;
        int nkeywords = 0;
        int ngens = 0;
        int ndoublestars = 0;

        java.util.List<exprType> args = null;
        java.util.List<keywordType> keywords = null;
        for (int i = 0; i < n.nChild(); i++)
        {
            Node ch = n.getChild(i);
            if(ch.dfaType == DFAType.argument)
            {
                if(ch.nChild() == 1)
                {
                    nargs++;
                }
                else if(ch.getChild(1).dfaType == DFAType.comp_for)
                {
                    ngens++;
                }
                else
                {
                    nkeywords++;
                }
            }
        }

        if(ngens > 1 || (ngens > 0 && (nargs > 0 || nkeywords > 0)))
        {
            throw new PyExceptions(ErrorType.AST_ERROR,
                    "Generator expression must be parenthesized if not sole argument",
                    n);
        }

        if(nargs + nkeywords + ngens > 255)
        {
            throw new PyExceptions(ErrorType.AST_ERROR,
                    "more than 255 arguments", n);
        }

        args = new ArrayList<exprType>(nargs + ngens);
        keywords = new ArrayList<keywordType>(nkeywords);

        nargs = 0; /* positional arguments + iterable argument unpackings */
        nkeywords = 0; /* keyword arguments + keyword argument unpackings */
        ndoublestars = 0; /* just keyword argument unpackings */

        for (int i = 0; i < n.nChild(); i++)
        {
            Node ch = n.getChild(i);
            if(ch.dfaType == DFAType.argument)
            {
                exprType e = null;
                Node chch = ch.getChild(0);
                if(ch.nChild() == 1)
                {
                    if(nkeywords > 0)
                    {
                        if(ndoublestars > 0)
                        {
                            throw new PyExceptions(ErrorType.AST_ERROR,
                                    "positional argument follows keyword argument unpacking",
                                    n);
                        }
                        else
                        {
                            throw new PyExceptions(ErrorType.AST_ERROR,
                                    "positional argument follows keyword argument",
                                    n);
                        }
                    }

                    e = astForExpr(chch);
                    args.add(e);
                }
                else if(chch.dfaType == DFAType.STAR)
                {
                    exprType starred;
                    if(ndoublestars > 0)
                    {
                        throw new PyExceptions(ErrorType.AST_ERROR,
                                "iterable argument unpacking follows keyword argument unpacking",
                                n);
                    }
                    e = astForExpr(ch.getChild(1));
                    starred = new Starred(e, expr_contextType.Load, chch.lineNo,
                            chch.colOffset);
                    args.add(starred);
                }
                else if(chch.dfaType == DFAType.DOUBLESTAR)
                {
                    keywordType kw = null;
                    i++;
                    e = astForExpr(ch.getChild(1));
                    kw = new keywordType(null, e);
                    keywords.add(kw);
                    ndoublestars++;
                }
                else if(ch.getChild(1).dfaType == DFAType.comp_for)
                {
                    e = astForGenexp(ch);
                    args.add(e);
                }
                else
                {
                    keywordType kw = null;
                    String key = null;
                    String tmp = null;

                    e = astForExpr(chch);

                    if(e instanceof Lambda)
                    {
                        throw new PyExceptions(ErrorType.AST_ERROR,
                                "lambda cannot contain assignment", n);
                    }
                    else if(!(e instanceof Name))
                    {
                        throw new PyExceptions(ErrorType.AST_ERROR,
                                "keyword can't be an expression", n);
                    }
                    forbiddenName(((Name) e).id, ch, true);

                    key = ((Name) e).id;
                    for (int k = 0; k < nkeywords; k++)
                    {
                        tmp = (keywords.get(k)).arg;
                        if(tmp != null && tmp.equals(key))
                        {
                            throw new PyExceptions(ErrorType.AST_ERROR,
                                    "keyword argument repeated", n);
                        }
                    }
                    e = astForExpr(ch.getChild(2));
                    kw = new keywordType(key, e);
                    keywords.add(kw);
                }
            }
        }

        return new Call(func, args, keywords, func.lineno, func.col_offset);
    }

    private java.util.List<exprType> astForDecorators(Node n)
    {
        /* decorators: decorator+ */
        REQ(n, DFAType.decorators);

        java.util.List<exprType> decoratorSeq = null;
        exprType d = null;

        decoratorSeq = new ArrayList<exprType>(n.nChild());

        for (int i = 0; i < n.nChild(); i++)
        {
            d = astForDecorator(n.getChild(i));
            decoratorSeq.add(d);
        }
        return decoratorSeq;
    }

    private stmtType astForAsyncFuncdef(Node n,
            java.util.List<exprType> decoratorSeq)
    {
        /* ASYNC funcdef */
        REQ(n, DFAType.async_funcdef);
        REQ(n.getChild(0), DFAType.ASYNC);
        REQ(n.getChild(1), DFAType.funcdef);
        return astForFuncdefImpl(n.getChild(1), decoratorSeq, true);
    }

    private exprType astForLambdef(Node n)
    {
        /*
         * lambdef: 'lambda' [varargslist] ':' test 
         * lambdef_nocond: 'lambda' [varargslist] ':' test_nocond
         */
        argumentsType args;
        exprType expression;

        if(n.nChild() == 3)
        {
            args = new argumentsType(null, null, null, null, null, null);
            expression = astForExpr(n.getChild(2));
        }
        else
        {
            args = astForArguments(n.getChild(1));
            expression = astForExpr(n.getChild(3));
        }
        return new Lambda(args, expression, n.lineNo, n.colOffset);
    }

    private exprType astForIfexpr(Node n)
    {
        /* test: or_test 'if' or_test 'else' test */
        exprType expression;
        exprType body;
        exprType orelse;

        myAssert(n.nChild() == 5);

        body = astForExpr(n.getChild(0));
        expression = astForExpr(n.getChild(2));
        orelse = astForExpr(n.getChild(4));
        return new IfExp(expression, body, orelse, n.lineNo, n.colOffset);
    }

    private cmpopType astForCompOp(Node n)
    {
        /*
         * comp_op: '<'|'>'|'=='|'>='|'<='|'!='|'in'|'not' 'in'|'is' |'is' 'not'
         */
        REQ(n, DFAType.comp_op);

        if(n.nChild() == 1)
        {
            n = n.getChild(0);
            switch (n.dfaType)
            {
            case LESS:
                return cmpopType.Lt;
            case GREATER:
                return cmpopType.Gt;
            case EQEQUAL:
                return cmpopType.Eq;
            case LESSEQUAL:
                return cmpopType.LtE;
            case GREATEREQUAL:
                return cmpopType.GtE;
            case NOTEQUAL:
                return cmpopType.NotEq;
            case NAME:
                if("in".equals(n.str))
                {
                    return cmpopType.In;
                }
                if("is".equals(n.str))
                {
                    return cmpopType.Is;
                }
            default:
                throw new PyExceptions(ErrorType.AST_ERROR,
                        "invalid comp_op: " + n.str, n);
            }
        }
        else if(n.nChild() == 2)
        {
            /* handle "not in" and "is not" */
            switch (n.getChild(0).dfaType)
            {
            case NAME:
                if("in".equals(n.getChild(1).str))
                {
                    return cmpopType.NotIn;
                }
                if("is".equals(n.getChild(0).str))
                {
                    return cmpopType.IsNot;
                }
            default:
                throw new PyExceptions(ErrorType.AST_ERROR,
                        String.format("invalid comp_op: %s %s",
                                n.getChild(0).str, n.getChild(1).str),
                        n);
            }
        }
        throw new PyExceptions(ErrorType.AST_ERROR,
                String.format("invalid comp_op: has %d children", n.nChild()),
                n);
    }

    private exprType astForStarred(Node n)
    {
        exprType tmp;
        REQ(n, DFAType.star_expr);
        tmp = astForExpr(n.getChild(1));

        return new Starred(tmp, expr_contextType.Load, n.lineNo, n.colOffset);
    }

    private exprType astForFactor(Node n)
    {
        /* factor: ('+'|'-'|'~') factor | power */
        exprType expression;

        expression = astForExpr(n.getChild(1));

        switch (n.getChild(0).dfaType)
        {
        case PLUS:
            return new UnaryOp(unaryopType.UAdd, expression, n.lineNo,
                    n.colOffset);
        case MINUS:
            return new UnaryOp(unaryopType.USub, expression, n.lineNo,
                    n.colOffset);
        case TILDE:
            return new UnaryOp(unaryopType.Invert, expression, n.lineNo,
                    n.colOffset);
        default:
            throw new PyExceptions(ErrorType.AST_ERROR,
                    "unhandled factor: " + n.getChild(0).dfaType, n);
        }
    }

    private exprType astForPower(Node n)
    {
        /* power: atom trailer* ('**' factor)* */
        exprType e;
        REQ(n, DFAType.power);
        e = astForAtomExpr(n.getChild(0));
        if(n.nChild() == 1)
        {
            return e;
        }
        if(n.getChild(-1).dfaType == DFAType.factor)
        {
            exprType f = astForExpr(n.getChild(-1));
            e = new BinOp(e, operatorType.Pow, f, n.lineNo, n.colOffset);
        }
        return e;
    }

    private stmtType astForExprStmt(Node n)
    {

        REQ(n, DFAType.expr_stmt);
        /*
         * expr_stmt: testlist_star_expr (augassign (yield_expr|testlist) | ('='
         *      (yield_expr|testlist))*) 
         * testlist_star_expr: (test|star_expr) (','
         *      test|star_expr)* [','] 
         * augassign: '+=' | '-=' | '*=' | '@=' | '/=' |
         *      '%=' | '&=' | '|=' | '^=' | '<<=' | '>>=' | '**=' | '//=' 
         * test: ...
         * here starts the operator precendence dance
         */

        if(n.nChild() == 1)
        {
            exprType e = astForTestlist(n.getChild(0));
            return new Expr(e, n.lineNo, n.colOffset);
        }
        else if(n.getChild(1).dfaType == DFAType.augassign)
        {
            exprType expr1, expr2;
            operatorType newOperator;
            Node ch = n.getChild(0);

            expr1 = astForTestlist(ch);
            setContext(expr1, expr_contextType.Store, ch);

            if(!(expr1 instanceof Name || expr1 instanceof Attribute
                    || expr1 instanceof Subscript))
            {
                throw new PyExceptions(ErrorType.AST_ERROR,
                        "illegal expression for augmented assignment", n);
            }

            ch = n.getChild(2);
            if(ch.dfaType == DFAType.testlist)
            {
                expr2 = astForTestlist(ch);
            }
            else
            {
                expr2 = astForExpr(ch);
            }

            newOperator = astForAugassign(n.getChild(1));
            return new AugAssign(expr1, newOperator, expr2, n.lineNo,
                    n.colOffset);
        }
        else
        {
            java.util.List<exprType> targets = null;
            Node value;
            exprType expression;

            REQ(n.getChild(1), DFAType.EQUAL);

            targets = new ArrayList<exprType>(n.nChild() / 2);

            for (int i = 0; i < n.nChild() - 2; i += 2)
            {
                Node ch = n.getChild(i);
                if(ch.dfaType == DFAType.yield_expr)
                {
                    throw new PyExceptions(ErrorType.AST_ERROR,
                            "assignment to yield expression not possible", n);
                }
                exprType e = astForTestlist(ch);
                setContext(e, expr_contextType.Store, n.getChild(i));
                targets.add(e);
            }
            value = n.getChild(-1);
            if(value.dfaType == DFAType.testlist_star_expr)
            {
                expression = astForTestlist(value);
            }
            else
            {
                expression = astForExpr(value);
            }
            return new Assign(targets, expression, n.lineNo, n.colOffset);
        }
    }

    private stmtType astForDelStmt(Node n)
    {
        /* del_stmt: 'del' exprlist */
        REQ(n, DFAType.del_stmt);
        java.util.List<exprType> exprList = astForExprlist(n.getChild(1),
                expr_contextType.Del);
        return new Delete(exprList, n.lineNo, n.colOffset);
    }

    private stmtType astForFlowStmt(Node n)
    {
        /*
         * flow_stmt: break_stmt | continue_stmt | return_stmt | raise_stmt |
         * yield_stmt break_stmt: 'break' continue_stmt: 'continue' return_stmt:
         * 'return' [testlist] yield_stmt: yield_expr yield_expr: 'yield'
         * testlist | 'yield' 'from' test raise_stmt: 'raise' [test [',' test
         * [',' test]]]
         */
        REQ(n, DFAType.flow_stmt);

        Node ch = n.getChild(0);
        switch (ch.dfaType)
        {
        case break_stmt:
            return new Break(n.lineNo, n.colOffset);
        case continue_stmt:
            return new Continue(n.lineNo, n.colOffset);
        case yield_stmt:
        {
            exprType exp = astForExpr(ch.getChild(0));
            return new Expr(exp, n.lineNo, n.colOffset);
        }
        case return_stmt:
            if(ch.nChild() == 1)
            {
                return new Return(null, n.lineNo, n.colOffset);
            }
            else
            {
                exprType exp = astForTestlist(ch.getChild(1));
                return new Return(exp, n.lineNo, n.colOffset);
            }
        case raise_stmt:
            if(ch.nChild() == 1)
            {
                return new Raise(null, null, n.lineNo, n.colOffset);
            }
            else if(ch.nChild() >= 2)
            {
                exprType cause = null;
                exprType exp = astForExpr(ch.getChild(1));
                if(ch.nChild() == 4)
                {
                    cause = astForExpr(ch.getChild(3));
                }
                return new Raise(exp, cause, n.lineNo, n.colOffset);
            }
        default:
            throw new PyExceptions(ErrorType.AST_ERROR,
                    "unexpected flow_stmt: " + ch.dfaType, n);
        }
    }

    private stmtType astForImportStmt(Node n)
    {
        /*
         * import_stmt: import_name | import_from import_name: 'import'
         * dotted_as_names import_from: 'from' (('.' | '...')* dotted_name |
         * ('.' | '...')+) 'import' ('*' | '(' import_as_names ')' |
         * import_as_names)
         */
        java.util.List<aliasType> aliases = null;

        REQ(n, DFAType.import_stmt);
        n = n.getChild(0);
        if(n.dfaType == DFAType.import_name)
        {
            n = n.getChild(1);
            REQ(n, DFAType.dotted_as_names);
            aliases = new ArrayList<aliasType>((n.nChild() + 1) / 2);
            for (int i = 0; i < n.nChild(); i += 2)
            {
                aliasType importAlias = aliasForImportName(n.getChild(i), true);
                aliases.add(importAlias);
            }
            return new Import(aliases, n.lineNo, n.colOffset);
        }
        else if(n.dfaType == DFAType.import_from)
        {
            int idx = 0;
            int ndots = 0;
            aliasType mod = null;
            String modname = null;
            int nChildren;

            for (idx = 1; idx < n.nChild(); idx++)
            {
                if(n.getChild(idx).dfaType == DFAType.dotted_name)
                {
                    mod = aliasForImportName(n.getChild(idx), false);
                    idx++;
                    break;
                }
                else if(n.getChild(idx).dfaType == DFAType.ELLIPSIS)
                {
                    ndots += 3;
                    continue;
                }
                else if(n.getChild(idx).dfaType != DFAType.DOT)
                {
                    break;
                }
                ndots++;
            }
            idx++; // skip over the 'import' keyword
            switch (n.getChild(idx).dfaType)
            {
            case STAR:
                /* from ... import * */
                n = n.getChild(idx);
                nChildren = 1;
                break;
            case LPAR:
                /* from ... import (x, y, z) */
                n = n.getChild(idx + 1);
                nChildren = n.nChild();
                break;
            case import_as_names:
                /* from ... import x, y, z */
                n = n.getChild(idx);
                nChildren = n.nChild();
                if(nChildren % 2 == 0)
                {
                    throw new PyExceptions(ErrorType.AST_ERROR,
                            "trailing comma not allowed without surrounding parentheses",
                            n);
                }
                break;
            default:
                throw new PyExceptions(ErrorType.AST_ERROR,
                        "Unexpected node-type in from-import: "
                                + n.getChild(idx).str,
                        n.getChild(idx));
            }

            aliases = new ArrayList<aliasType>(nChildren);

            if(n.dfaType == DFAType.STAR)
            {
                aliasType importAlias = aliasForImportName(n, true);
                aliases.add(importAlias);
            }
            else
            {
                for (int i = 0; i < n.nChild(); i += 2)
                {
                    aliasType importAlias = aliasForImportName(n.getChild(i),
                            true);
                    aliases.add(importAlias);
                }
            }
            if(mod != null)
            {
                modname = mod.name;
            }
            return new ImportFrom(modname, aliases, ndots, n.lineNo,
                    n.colOffset);
        }
        throw new PyExceptions(ErrorType.AST_ERROR,
                "unknown import statement: starts with command "
                        + n.getChild(0).str,
                n);
    }

    private stmtType astForGlobalStmt(Node n)
    {
        /* global_stmt: 'global' NAME (',' NAME)* */
        String name;
        java.util.List<String> s = new ArrayList<String>(n.nChild() / 2);

        REQ(n, DFAType.global_stmt);

        for (int i = 1; i < n.nChild(); i += 2)
        {
            name = newIdentifier(n.getChild(i));
            s.add(name);
        }
        return new Global(s, n.lineNo, n.colOffset);
    }

    private stmtType astForNonlocalStmt(Node n)
    {
        /* nonlocal_stmt: 'nonlocal' NAME (',' NAME)* */
        String name;
        java.util.List<String> s = new ArrayList<String>(n.nChild() / 2);

        REQ(n, DFAType.nonlocal_stmt);

        for (int i = 1; i < n.nChild(); i += 2)
        {
            name = newIdentifier(n.getChild(i));
            s.add(name);
        }
        return new Nonlocal(s, n.lineNo, n.colOffset);
    }

    private stmtType astForAssertStmt(Node n)
    {
        /* assert_stmt: 'assert' test [',' test] */
        REQ(n, DFAType.assert_stmt);

        if(n.nChild() == 2)
        {
            exprType expression = astForExpr(n.getChild(1));
            return new Assert(expression, null, n.lineNo, n.colOffset);
        }
        else if(n.nChild() == 4)
        {
            exprType expr1 = astForExpr(n.getChild(1));
            exprType expr2 = astForExpr(n.getChild(3));
            return new Assert(expr1, expr2, n.lineNo, n.colOffset);
        }
        return null;
    }

    private stmtType astForIfStmt(Node n)
    {
        /*
         * if_stmt: 'if' test ':' suite ('elif' test ':' suite)* ['else' ':'
         * suite]
         */
        REQ(n, DFAType.if_stmt);

        if(n.nChild() == 4)
        {
            exprType expression = astForExpr(n.getChild(1));
            java.util.List<stmtType> suiteSeq = astForSuite(n.getChild(3));
            return new If(expression, suiteSeq, null, n.lineNo, n.colOffset);
        }

        String s = n.getChild(4).str;

        if(s.equals("else"))
        {
            exprType expression = astForExpr(n.getChild(1));
            java.util.List<stmtType> seq1 = astForSuite(n.getChild(3));
            java.util.List<stmtType> seq2 = astForSuite(n.getChild(6));
            return new If(expression, seq1, seq2, n.lineNo, n.colOffset);
        }
        else if(s.equals("elif"))
        {
            exprType expression = null;
            java.util.List<stmtType> suiteSeq = null;
            java.util.List<stmtType> orelse = null;
            int nElif = n.nChild() - 4;
            boolean hasElse = false;

            /*
             * must reference the child n_elif+1 since 'else' token is third,
             * not fourth, child from the end.
             */
            if(n.getChild(nElif + 1).dfaType == DFAType.NAME
                    && n.getChild(nElif + 1).str.equals("else"))
            {
                hasElse = true;
                nElif -= 3;
            }
            nElif /= 4;

            if(hasElse)
            {
                java.util.List<stmtType> suiteSeq2;

                orelse = new ArrayList<stmtType>(1);
                expression = astForExpr(n.getChild(-6));
                suiteSeq = astForSuite(n.getChild(-4));
                suiteSeq2 = astForSuite(n.getChild(-1));

                orelse.add(new If(expression, suiteSeq, suiteSeq2,
                        n.getChild(-6).lineNo, n.getChild(-6).colOffset));
            }

            for (int i = 0; i < nElif; i++)
            {
                int off = 5 + (nElif - i - 1) * 4;
                java.util.List<stmtType> newObj = new ArrayList<stmtType>(1);
                expression = astForExpr(n.getChild(off));
                suiteSeq = astForSuite(n.getChild(off + 2));
                newObj.add(new If(expression, suiteSeq, orelse,
                        n.getChild(off).lineNo, n.getChild(off).colOffset));
                orelse = newObj;
            }
            expression = astForExpr(n.getChild(1));
            suiteSeq = astForSuite(n.getChild(3));
            return new If(expression, suiteSeq, orelse, n.lineNo, n.colOffset);
        }
        throw new PyExceptions(ErrorType.AST_ERROR,
                "unexpected token in 'if' statement: " + s, n);
    }

    private stmtType astForWhileStmt(Node n)
    {
        /* while_stmt: 'while' test ':' suite ['else' ':' suite] */
        REQ(n, DFAType.while_stmt);

        if(n.nChild() == 4)
        {
            exprType expression = astForExpr(n.getChild(1));
            java.util.List<stmtType> suiteSeq = astForSuite(n.getChild(3));
            return new While(expression, suiteSeq, null, n.lineNo, n.colOffset);
        }
        else if(n.nChild() == 7)
        {
            exprType expression = astForExpr(n.getChild(1));
            java.util.List<stmtType> seq1 = astForSuite(n.getChild(3));
            java.util.List<stmtType> seq2 = astForSuite(n.getChild(6));
            return new While(expression, seq1, seq2, n.lineNo, n.colOffset);
        }
        throw new PyExceptions(ErrorType.AST_ERROR,
                "wrong number of tokens for 'while' statement: " + n.nChild(),
                n);
    }

    private stmtType astForForStmt(Node n, boolean isAsync)
    {
        /*
         * for_stmt: 'for' exprlist 'in' testlist ':' suite ['else' ':' suite]
         */
        REQ(n, DFAType.for_stmt);

        exprType expression;
        exprType target, first;
        java.util.List<stmtType> suiteSeq;
        java.util.List<stmtType> seq = null;
        Node ch = null;

        if(n.nChild() == 9)
        {
            seq = astForSuite(n.getChild(8));
        }

        ch = n.getChild(1);
        java.util.List<exprType> _target = astForExprlist(ch,
                expr_contextType.Store);

        /*
         * Check the # of children rather than the length of _target, since for
         * x, in ... has 1 element in _target, but still requires a Tuple.
         */
        first = _target.get(0);
        if(ch.nChild() == 1)
        {
            target = first;
        }
        else
        {
            target = new Tuple(_target, expr_contextType.Store, first.lineno,
                    first.col_offset);
        }
        expression = astForTestlist(n.getChild(3));
        suiteSeq = astForSuite(n.getChild(5));
        if(isAsync)
        {
            return new AsyncFor(target, expression, suiteSeq, seq, n.lineNo,
                    n.colOffset);
        }
        else
        {
            return new For(target, expression, suiteSeq, seq, n.lineNo,
                    n.colOffset);
        }
    }

    private stmtType astForTryStmt(Node n)
    {
        /*
         * try_stmt: ('try' ':' suite ((except_clause ':' suite)+ ['else' ':'
         * suite] ['finally' ':' suite] | 'finally' ':' suite))
         * 
         */
        REQ(n, DFAType.try_stmt);

        int nExcept = (n.nChild() - 3) / 3;
        java.util.List<excepthandlerType> handlers = null;
        java.util.List<stmtType> orelse = null;
        java.util.List<stmtType> finalbody = null;
        java.util.List<stmtType> body = astForSuite(n.getChild(2));

        if(n.getChild(-3).dfaType == DFAType.NAME)
        {
            if("finally".equals(n.getChild(-3).str))
            {
                if(n.nChild() >= 9 && n.getChild(-6).dfaType == DFAType.NAME)
                {
                    orelse = astForSuite(n.getChild(-4));
                    nExcept--;
                }
                finalbody = astForSuite(n.getChild(-1));
                nExcept--;
            }
            else
            {
                orelse = astForSuite(n.getChild(-1));
                nExcept--;
            }
        }
        else if(n.getChild(-3).dfaType != DFAType.except_clause)
        {
            throw new PyExceptions(ErrorType.AST_ERROR,
                    "malformed 'try' statement", n);
        }
        if(nExcept > 0)
        {
            handlers = new ArrayList<excepthandlerType>(nExcept);
            for (int i = 0; i < nExcept; i++)
            {
                excepthandlerType e = astForExceptClause(n.getChild(3 + i * 3),
                        n.getChild(5 + i * 3));
                handlers.add(e);
            }
        }

        myAssert(finalbody != null || handlers.size() > 0);
        return new Try(body, handlers, orelse, finalbody, n.lineNo,
                n.colOffset);
    }

    private stmtType astForWithStmt(Node n, boolean isAsync)
    {
        /* with_stmt: 'with' with_item (',' with_item)* ':' suite */
        REQ(n, DFAType.with_stmt);
        java.util.List<withitemType> items = new ArrayList<withitemType>(
                (n.nChild() - 2) / 2);
        java.util.List<stmtType> body = null;

        for (int i = 1; i < n.nChild() - 2; i += 2)
        {
            withitemType item = astForWithItem(n.getChild(i));
            items.add(item);
        }

        body = astForSuite(n.getChild(-1));

        if(isAsync)
        {
            return new AsyncWith(items, body, n.lineNo, n.colOffset);
        }
        else
        {
            return new With(items, body, n.lineNo, n.colOffset);
        }
    }

    private stmtType astForFuncdef(Node n,
            java.util.List<exprType> decoratorSeq)
    {
        /* funcdef: 'def' NAME parameters ['->' test] ':' suite */
        return astForFuncdefImpl(n, decoratorSeq, false);
    }

    private stmtType astForClassdef(Node n,
            java.util.List<exprType> decoratorSeq)
    {
        /* classdef: 'class' NAME ['(' arglist ')'] ':' suite */

        REQ(n, DFAType.classdef);

        String className = null;
        java.util.List<stmtType> s = null;
        exprType call = null;

        if(n.nChild() == 4) /* class NAME ':' suite */
        {
            s = astForSuite(n.getChild(3));
            className = newIdentifier(n.getChild(1));
            forbiddenName(className, n.getChild(3), false);
            return new ClassDef(className, null, null, s, decoratorSeq,
                    n.lineNo, n.colOffset);
        }

        if(n.getChild(
                3).dfaType == DFAType.RPAR) /* class NAME '(' ')' ':' suite */
        {
            s = astForSuite(n.getChild(5));
            className = newIdentifier(n.getChild(1));
            forbiddenName(className, n.getChild(3), false);
            return new ClassDef(className, null, null, s, decoratorSeq,
                    n.lineNo, n.colOffset);
        }
        /* class NAME '(' arglist ')' ':' suite */
        /* build up a fake Call node so we can extract its pieces */
        {
            String dummyName = newIdentifier(n.getChild(1));
            exprType dummy = new Name(dummyName, expr_contextType.Load,
                    n.lineNo, n.colOffset);
            call = astForCall(n.getChild(3), dummy);
        }
        s = astForSuite(n.getChild(6));
        className = newIdentifier(n.getChild(1));
        forbiddenName(className, n.getChild(1), false);

        return new ClassDef(className, ((Call) call).args,
                ((Call) call).keywords, s, decoratorSeq, n.lineNo, n.colOffset);
    }

    private stmtType astForDecorated(Node n)
    {
        /* decorated: decorators (classdef | funcdef | async_funcdef) */
        REQ(n, DFAType.decorated);

        stmtType thing = null;
        java.util.List<exprType> decoratorSeq = null;

        decoratorSeq = astForDecorators(n.getChild(0));

        myAssert(n.getChild(1).dfaType == DFAType.funcdef
                || n.getChild(1).dfaType == DFAType.async_funcdef
                || n.getChild(1).dfaType == DFAType.classdef);

        if(n.getChild(1).dfaType == DFAType.funcdef)
        {
            thing = astForFuncdef(n.getChild(1), decoratorSeq);
        }
        else if(n.getChild(1).dfaType == DFAType.classdef)
        {
            thing = astForClassdef(n.getChild(1), decoratorSeq);
        }
        else if(n.getChild(1).dfaType == DFAType.async_funcdef)
        {
            thing = astForAsyncFuncdef(n.getChild(1), decoratorSeq);
        }
        if(thing != null)
        {
            thing.lineno = n.lineNo;
            thing.col_offset = n.colOffset;
        }
        return thing;
    }

    private stmtType astForAsyncStmt(Node n)
    {
        /* async_stmt: ASYNC (funcdef | with_stmt | for_stmt) */
        REQ(n, DFAType.async_stmt);
        REQ(n.getChild(0), DFAType.ASYNC);

        switch (n.getChild(1).dfaType)
        {
        case funcdef:
            return astForFuncdefImpl(n.getChild(1), null, true);
        case with_stmt:
            return astForWithStmt(n.getChild(1), true);
        case for_stmt:
            return astForForStmt(n.getChild(1), true);
        default:
            throw new PyExceptions(ErrorType.AST_ERROR,
                    "invalid async stament: " + n.getChild(1).str, n);
        }
    }

    private exprType astForExpr(Node n)
    {
        /*
         * handle the full range of simple expressions test: or_test ['if'
         * or_test 'else' test] | lambdef test_nocond: or_test | lambdef_nocond
         * or_test: and_test ('or' and_test)* and_test: not_test ('and'
         * not_test)* not_test: 'not' not_test | comparison comparison: expr
         * (comp_op expr)* expr: xor_expr ('|' xor_expr)* xor_expr: and_expr
         * ('^' and_expr)* and_expr: shift_expr ('&' shift_expr)* shift_expr:
         * arith_expr (('<<'|'>>') arith_expr)* arith_expr: term (('+'|'-')
         * term)* term: factor (('*'|'@'|'/'|'%'|'//') factor)* factor:
         * ('+'|'-'|'~') factor | power power: atom_expr ['**' factor]
         * atom_expr: [AWAIT] atom trailer* yield_expr: 'yield' [yield_arg]
         */
        java.util.List<exprType> seq = null;

        while (true)
        {
            switch (n.dfaType)
            {
            case test:
            case test_nocond:
                if(n.getChild(0).dfaType == DFAType.lambdef
                        || n.getChild(0).dfaType == DFAType.lambdef_nocond)
                {
                    return astForLambdef(n.getChild(0));
                }
                else if(n.nChild() > 1)
                {
                    return astForIfexpr(n);
                }
                /* Fallthrough */
            case or_test:
            case and_test:
                if(n.nChild() == 1)
                {
                    n = n.getChild(0);
                    continue;
                }
                seq = new ArrayList<exprType>((n.nChild() + 1) / 2);
                for (int i = 0; i < n.nChild(); i += 2)
                {
                    exprType e = astForExpr(n.getChild(i));
                    seq.add(e);
                }
                if("and".equals(n.getChild(1).str))
                {
                    return new BoolOp(boolopType.And, seq, n.lineNo,
                            n.colOffset);
                }
                myAssert("or".equals(n.getChild(1).str));
                return new BoolOp(boolopType.Or, seq, n.lineNo, n.colOffset);
            case not_test:
                if(n.nChild() == 1)
                {
                    n = n.getChild(0);
                    continue;
                }
                else
                {
                    exprType expression = astForExpr(n.getChild(1));
                    return new UnaryOp(unaryopType.Not, expression, n.lineNo,
                            n.colOffset);
                }
            case comparison:
                if(n.nChild() == 1)
                {
                    n = n.getChild(0);
                    continue;
                }
                else
                {
                    exprType expression;
                    java.util.List<cmpopType> ops = new ArrayList<cmpopType>(
                            n.nChild() / 2);
                    java.util.List<exprType> cmps = new ArrayList<exprType>(
                            n.nChild() / 2);

                    for (int i = 1; i < n.nChild(); i += 2)
                    {
                        cmpopType newOperator = astForCompOp(n.getChild(i));
                        expression = astForExpr(n.getChild(i + 1));
                        ops.add(newOperator);
                        cmps.add(expression);
                    }

                    expression = astForExpr(n.getChild(0));
                    return new Compare(expression, ops, cmps, n.lineNo,
                            n.colOffset);
                }
            case star_expr:
                return astForStarred(n);
            case expr:
            case xor_expr:
            case and_expr:
            case shift_expr:
            case arith_expr:
            case term:
                if(n.nChild() == 1)
                {
                    n = n.getChild(0);
                    continue;
                }
                return astForBinop(n);
            case yield_expr:
                Node an = null;
                Node en = null;
                boolean isFrom = false;
                exprType exp = null;
                if(n.nChild() > 1)
                {
                    an = n.getChild(1);
                }
                if(an != null)
                {
                    en = an.getChild(-1);
                    if(an.nChild() == 2)
                    {
                        isFrom = true;
                        exp = astForExpr(en);
                    }
                    else
                    {
                        exp = astForTestlist(en);
                    }
                }

                if(isFrom)
                {
                    return new YieldFrom(exp, n.lineNo, n.colOffset);
                }
                return new Yield(exp, n.lineNo, n.colOffset);
            case factor:
                if(n.nChild() == 1)
                {
                    n = n.getChild(0);
                    continue;
                }
                return astForFactor(n);
            case power:
                return astForPower(n);
            default:
                throw new PyExceptions(ErrorType.AST_ERROR,
                        "unhandled expr: " + n.dfaType, n);
            }
        }
    }

    private java.util.List<exprType> seqForTestlist(Node n)
    {
        /*
         * testlist: test (',' test)* [','] testlist_star_expr: test|star_expr
         * (',' test|star_expr)* [',']
         */
        myAssert(n.dfaType == DFAType.testlist
                || n.dfaType == DFAType.testlist_star_expr
                || n.dfaType == DFAType.testlist_comp);
        java.util.List<exprType> seq = new ArrayList<exprType>(
                (n.nChild() + 1) / 2);
        exprType expression = null;
        for (int i = 0; i < n.nChild(); i += 2)
        {
            Node ch = n.getChild(i);
            myAssert(ch.dfaType == DFAType.test
                    || ch.dfaType == DFAType.test_nocond
                    || ch.dfaType == DFAType.star_expr);
            expression = astForExpr(ch);
            seq.add(expression);
        }
        return seq;
    }

    private stmtType astForStmt(Node n)
    {
        if(n.dfaType == DFAType.stmt)
        {
            myAssert(n.nChild() == 1);
            n = n.getChild(0);
        }
        if(n.dfaType == DFAType.simple_stmt)
        {
            myAssert(numStmts(n) == 1);
            n = n.getChild(0);
        }
        if(n.dfaType == DFAType.small_stmt)
        {
            n = n.getChild(0);
            /*
             * small_stmt: expr_stmt | del_stmt | pass_stmt | flow_stmt |
             * import_stmt | global_stmt | nonlocal_stmt | assert_stmt
             */

            switch (n.dfaType)
            {
            case expr_stmt:
                return astForExprStmt(n);
            case del_stmt:
                return astForDelStmt(n);
            case pass_stmt:
                return new Pass(n.lineNo, n.colOffset);
            case flow_stmt:
                return astForFlowStmt(n);
            case import_stmt:
                return astForImportStmt(n);
            case global_stmt:
                return astForGlobalStmt(n);
            case nonlocal_stmt:
                return astForNonlocalStmt(n);
            case assert_stmt:
                return astForAssertStmt(n);
            default:
                throw new PyExceptions(ErrorType.AST_ERROR,
                        "unhandled small_stmt: Type=" + n.dfaType.toString()
                                + "child length=" + n.nChild(),
                        n);
            }
        }
        else
        {
            /*
             * compound_stmt: if_stmt | while_stmt | for_stmt | try_stmt |
             * funcdef | classdef | decorated | async_stmt
             */
            Node ch = n.getChild(0);
            REQ(n, DFAType.compound_stmt);
            switch (ch.dfaType)
            {
            case if_stmt:
                return astForIfStmt(ch);
            case while_stmt:
                return astForWhileStmt(ch);
            case for_stmt:
                return astForForStmt(ch, false);
            case try_stmt:
                return astForTryStmt(ch);
            case with_stmt:
                return astForWithStmt(ch, false);
            case funcdef:
                return astForFuncdef(ch, null);
            case classdef:
                return astForClassdef(ch, null);
            case decorated:
                return astForDecorated(ch);
            case async_stmt:
                return astForAsyncStmt(ch);
            default:
                throw new PyExceptions(ErrorType.AST_ERROR,
                        "unhandled small_stmt: Type=" + n.dfaType.toString()
                                + "child length=" + n.nChild(),
                        n);
            }
        }
    }

    private exprType astForTestlist(Node n)
    {
        /* testlist_comp: test (comp_for | (',' test)* [',']) */
        /* testlist: test (',' test)* [','] */
        myAssert(n.nChild() > 0);
        if(n.dfaType == DFAType.testlist_comp)
        {
            if(n.nChild() > 1)
            {
                myAssert(n.getChild(1).dfaType != DFAType.comp_for);
            }
        }
        else
        {
            myAssert(n.dfaType == DFAType.testlist
                    || n.dfaType == DFAType.testlist_star_expr);
        }
        if(n.nChild() == 1)
        {
            return astForExpr(n.getChild(0));
        }
        else
        {
            java.util.List<exprType> tmp = seqForTestlist(n);
            return new Tuple(tmp, expr_contextType.Load, n.lineNo, n.colOffset);
        }
    }

    public modType fromNode(Node n)
    {
        if(!n.isDFAType)
        {
            throw new PyExceptions(ErrorType.AST_ERROR,
                    "cst's top node is not nonterminal node", n);
        }

        modType res = null;
        java.util.List<stmtType> stmts = null;
        stmtType s;
        Node ch;
        int num;

        switch (n.dfaType)
        {
        case file_input:
            stmts = new ArrayList<stmtType>(numStmts(n));
            for (int i = 0; i < n.nChild() - 1; i++)
            {
                ch = n.getChild(i);
                if(ch.dfaType == DFAType.NEWLINE)
                {
                    continue;
                }
                REQ(ch, DFAType.stmt);
                num = numStmts(ch);
                if(num == 1)
                {
                    s = astForStmt(ch);
                    stmts.add(s);
                }
                else
                {
                    ch = ch.getChild(0);
                    REQ(ch, DFAType.simple_stmt);
                    for (int j = 0; j < num; j++)
                    {
                        s = astForStmt(ch.getChild(j * 2));
                        stmts.add(s);
                    }
                }
            }
            res = new Module(stmts);
            break;
        case eval_input:
            exprType testlistAst = astForTestlist(n.getChild(0));
            res = new Expression(testlistAst);
            break;
        case single_input:
            if(n.getChild(0).dfaType == DFAType.NEWLINE)
            {
                stmts = new ArrayList<stmtType>(1);
                stmts.add(new Pass(n.lineNo, n.colOffset));
                res = new Interactive(stmts);
            }
            else
            {
                n = n.getChild(0);
                num = numStmts(n);
                stmts = new ArrayList<stmtType>(num);
                if(num == 1)
                {
                    s = astForStmt(n);
                    stmts.add(s);
                }
                else
                {
                    REQ(n, DFAType.simple_stmt);
                    for (int i = 0; i < n.nChild(); i += 2)
                    {
                        if(n.getChild(i).dfaType == DFAType.NEWLINE)
                        {
                            break;
                        }

                        s = astForStmt(n.getChild(i));
                        stmts.add(s);
                    }
                }
                res = new Interactive(stmts);
            }
            break;
        default:
            throw new PyExceptions(ErrorType.AST_ERROR,
                    "invalid node " + n.dfaType.toString() + " for FromNode",
                    n);
        }

        return res;

    }
}
