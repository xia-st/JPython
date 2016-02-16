package pers.xia.jpython.parser;

import pers.xia.jpython.ast.*;
import pers.xia.jpython.grammar.DFAType;
import pers.xia.jpython.object.PyExceptions;
import pers.xia.jpython.object.PyExceptions.ErrorType;
import pers.xia.jpython.object.PyObject;

import java.util.ArrayList;
import java.util.List;


public class Ast
{
    /* 
      *Test the node's type is or not the assigened type
     */
    private void REQ(Node n, DFAType type)
    {
        Assert(n.dfaType == type);
    }
    
    /*
     * The default assert method have some problems
     */
    private void Assert(boolean b)
    {
        if(!b)
        {
            throw new PyExceptions(ErrorType.AST_ERROR, "AST Error");
        }
    }
    
    /*
     * Count the number of statement in node's childkkk
     */
    private int numStmts(Node n)
    {
        int l;  // the sum of statements
        switch(n.dfaType)
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
            for(Node ch1 : n.childs)
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
            return n.childs.size() / 2;
        case suite:
            if(n.childs.size() == 1)
            {
                return numStmts(n.getChild(0));
            }
            else
            {
                l = 0;
                for(int i = 1; i < n.childs.size() - 1; i++)
                {
                    l += numStmts(n.getChild(i));
                }
                return l;
            }
        default:
            throw new PyExceptions(ErrorType.AST_ERROR, "Non-statement found: " + 
                    n.dfaType + " " + n.childs.size()); 
        }
    }
    
    private String newIdentifier(Node n)
    {
        return n.str;
    }
    
    private static final String FORBIDDEN[] = {
            "None",
            "True",
            "False",
    };
    
    private void forbiddenName(String name, Node n, boolean fullChecks)
    {
        if(name == null)
        {
            throw new PyExceptions(ErrorType.AST_ERROR, "assignment to keyword");
        }
        if("__debug__".equals(name))
        {
            throw new PyExceptions(ErrorType.AST_ERROR, "assignment to keyword");
        }
        if(fullChecks)
        {
            for(int i = 0; i < FORBIDDEN.length; i++)
            {
                if(FORBIDDEN[i].equals(name))
                {
                    throw new PyExceptions(ErrorType.AST_ERROR, "assignment to keyword");
                }
            }
        }
    }
    
    private void setContext(exprType expr, expr_contextType ctx, Node n)
    {
        //TODO
    }
    
    private exprType astForGenexp(Node n)
    {
        // TODO
        return null;
    }
    
    private operatorType astForAugassign(Node n)
    {
        // TODO 
        return null;
    }
    
    private List<exprType> astForExprlist(Node n, expr_contextType context)
    {
        // TODO
        return null;
    }
    
    private aliasType aliasForImportName(Node n, boolean store)
    {
        // TODO
        return null;
    }
    
    private List<stmtType> astForSuite(Node n)
    {
        // TODO
        return null;
    }
    
    private excepthandlerType astForExceptClause(Node exc, Node body)
    {
        // TODO
        return null;
    }
    
    private withitemType astForWithItem(Node n)
    {
        // TODO
        return null;
    }
    
    private stmtType astForFuncdefImpl(Node n, List<exprType> decoratorSeq, boolean isAsync)
    {
        // TODO
        return null;
    }
    
    private exprType astForCall(Node n, exprType func)
    {
        /*
            arglist: argument (',' argument)*  [',']
            argument: ( test [comp_for] | '*' test | test '=' test | '**' test )
         */
        REQ(n, DFAType.arglist);
        
        int nargs = 0; 
        int nkeywords = 0; 
        int ngens = 0;
        int ndoublestars = 0;
        
        List<exprType> args = null;
        List<keywordType> keywords = null;
        for(int i = 0; i < n.childs.size(); i++)
        {
            Node ch = n.getChild(i);
            if(ch.dfaType == DFAType.argument)
            {
                if(ch.childs.size() == 1)
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
                    "Generator expression must be parenthesized if not sole argument");
        }
        
        if(nargs + nkeywords + ngens > 255)
        {
            throw new PyExceptions(ErrorType.AST_ERROR, 
                    "more than 255 arguments");
        }
        
        args = new ArrayList<exprType>(nargs + ngens);
        keywords = new ArrayList<keywordType>(nkeywords);
        
        nargs = 0; /* positional arguments + iterable argument unpackings */
        nkeywords = 0; /* keyword arguments + keyword argument unpackings */
        ndoublestars = 0; /* just keyword argument unpackings */
        
        for(int i = 0; i < n.childs.size(); i++)
        {
            Node ch = n.getChild(i);
            if(ch.dfaType == DFAType.argument)
            {
                exprType e = null;
                Node chch = ch.getChild(0);
                if(ch.childs.size() == 1)
                {
                    if(nkeywords > 0)
                    {
                        if(ndoublestars > 0)
                        {
                            throw new PyExceptions(ErrorType.AST_ERROR,
                                    "positional argument follows keyword argument unpacking");
                        }
                        else
                        {
                            throw new PyExceptions(ErrorType.AST_ERROR,
                                    "positional argument follows keyword argument");
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
                                "iterable argument unpacking follows keyword argument unpacking");
                    }
                    e = astForExpr(ch.getChild(1));
                    starred = new Starred(e, expr_contextType.Load, chch.lineNo, chch.colOffset);
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
                                "lambda cannot contain assignment");
                    }
                    else if(!(e instanceof Name))
                    {
                        throw new PyExceptions(ErrorType.AST_ERROR,
                                "keyword can't be an expression");
                    }
                    forbiddenName(((Name)e).id, ch, true);
                    
                    key = ((Name)e).id;
                    for(int k = 0; k < nkeywords; k++)
                    {
                        tmp = (keywords.get(k)).arg;
                        if(tmp != null && tmp.equals(key))
                        {
                            throw new PyExceptions(ErrorType.AST_ERROR, 
                                    "keyword argument repeated");
                        }
                    }
                    e = astForExpr(ch.getChild(2));
                    kw = new keywordType(key, e);
                    keywords.add(kw);
                }
            }
        }
        
        return new Call(func,args, keywords, func.lineno, func.col_offset);
    }

    private List<exprType> astForDecorators(Node n)
    {
        // TODO
        return null;
    }
    
    private stmtType astForAsyncFuncdef(Node n, List<exprType> decoratorSeq)
    {
        // TODO
        return null;
    }
    
    private exprType astForLambdef(Node n)
    {
        // TODO
        return null;
    }
    
    private exprType astForIfexpr(Node n)
    {
        // TODO
        return null;
    }
    
    private cmpopType astForCompOp(Node n)
    {
        // TODO
        return null;
    }
    
    private exprType astForStarred(Node n)
    {
        // TODO
        return null;
    }
    
    private exprType astForFactor(Node n)
    {
        // TODO
        return null;
    }
    
    private exprType astForPower(Node n)
    {
        // TODO
        return null;
    }
    
    private stmtType astForExprStmt(Node n)
    {
        
        REQ(n, DFAType.expr_stmt);
        /* expr_stmt: testlist_star_expr (augassign (yield_expr|testlist)
        | ('=' (yield_expr|testlist))*)
        testlist_star_expr: (test|star_expr) (',' test|star_expr)* [',']
        augassign: '+=' | '-=' | '*=' | '@=' | '/=' | '%=' | '&=' | '|=' | '^='
                | '<<=' | '>>=' | '**=' | '//='
        test: ... here starts the operator precendence dance
         */

        if(n.childs.size() == 1)
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
            
            if(!(expr1 instanceof Name ||
                    expr1 instanceof Attribute ||
                    expr1 instanceof Subscript))
            {
                throw new PyExceptions(ErrorType.AST_ERROR, "illegal expression for augmented assignment");
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
            return new AugAssign(expr1, newOperator, expr2, n.lineNo, n.colOffset);
        }
        else
        {
            List<exprType> targets = null;
            Node value;
            exprType expression;
            
            REQ(n.getChild(1), DFAType.EQEQUAL);
            
            targets = new ArrayList<exprType>(n.childs.size() / 2);
            
            for(int i = 0; i < n.childs.size() - 2; i += 2)
            {
                Node ch = n.getChild(i);
                if(ch.dfaType == DFAType.yield_expr)
                {
                    throw new PyExceptions(ErrorType.AST_ERROR, 
                            "assignment to yield expression not possible");
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
        List<exprType> exprList = astForExprlist(n.getChild(1), expr_contextType.Del);
        return new Delete(exprList, n.lineNo, n.colOffset);
    }
    
    private stmtType astForFlowStmt(Node n)
    {
        /*
            flow_stmt: break_stmt | continue_stmt | return_stmt | raise_stmt
                       | yield_stmt
            break_stmt: 'break'
            continue_stmt: 'continue'
            return_stmt: 'return' [testlist]
            yield_stmt: yield_expr
            yield_expr: 'yield' testlist | 'yield' 'from' test
            raise_stmt: 'raise' [test [',' test [',' test]]]
        */
        REQ(n, DFAType.flow_stmt);
        
        Node ch = n.getChild(0);
        switch(ch.dfaType)
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
            if(ch.childs.size() == 1)
            {
                return new Return(null, n.lineNo, n.colOffset);
            }
            else
            {
                exprType exp = astForTestlist(ch.getChild(1));
                return new Return(exp, n.lineNo, n.colOffset);
            }
        case raise_stmt:
            if(ch.childs.size() == 1)
            {
                return new Raise(null, null, n.lineNo, n.colOffset);
            }
            else if(ch.childs.size() >= 2)
            {
                exprType cause = null;
                exprType exp = astForExpr(ch.getChild(1));
                if(ch.childs.size() == 4)
                {
                    cause = astForExpr(ch.getChild(3));
                }
                return new Raise(exp, cause, n.lineNo, n.colOffset);
            }
        default: 
            throw new PyExceptions(ErrorType.AST_ERROR, "unexpected flow_stmt: " + ch.dfaType);
        }
    }
    
    private stmtType astForImportStmt(Node n)
    {
        /*
            import_stmt: import_name | import_from
            import_name: 'import' dotted_as_names
            import_from: 'from' (('.' | '...')* dotted_name | ('.' | '...')+)
                         'import' ('*' | '(' import_as_names ')' | import_as_names)
        */
        List<aliasType> aliases = null;
        
        REQ(n, DFAType.import_stmt);
        n = n.getChild(0);
        if(n.dfaType == DFAType.import_name)
        {
            n = n.getChild(1);
            REQ(n, DFAType.dotted_as_names);
            aliases = new ArrayList<aliasType>((n.childs.size() + 1) / 2);
            for(int i = 0; i < n.childs.size(); i += 2)
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
            
            for(idx = 1; idx < n.childs.size(); idx++)
            {
                if(n.getChild(idx).dfaType == DFAType.dotted_as_name)
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
            idx++;  // skip over the 'import' keyword
            switch(n.getChild(idx).dfaType)
            {
            case STAR:
                /* from ... import * */
                n = n.getChild(idx);
                nChildren = 1;
                break;
            case LPAR:
                /* from ... import (x, y, z) */
                n = n.getChild(idx + 1);
                nChildren = n.childs.size();
            case import_as_names:
                /* from ... import x, y, z */
                n = n.getChild(idx);
                nChildren = n.childs.size();
                if(nChildren % 2 == 0)
                {
                    throw new PyExceptions(ErrorType.AST_ERROR, 
                            "trailing comma not allowed without surrounding parentheses");
                }
            default:
                throw new PyExceptions(ErrorType.AST_ERROR, "Unexpected node-type in from-import");
            }
            
            aliases = new ArrayList<aliasType>(nChildren);
            
            if(n.dfaType == DFAType.STAR)
            {
                aliasType importAlias = aliasForImportName(n, true);
                aliases.add(importAlias);
            }
            else
            {
                for(int i = 0; i < n.childs.size(); i += 2)
                {
                    aliasType importAlias = aliasForImportName(n.getChild(i), true);
                    aliases.add(importAlias);
                }
            }
            if(mod != null)
            {
                modname = mod.name;
            }
            return new ImportFrom(modname, aliases, ndots, n.lineNo, n.colOffset);
        }
        throw new PyExceptions(ErrorType.AST_ERROR, "unknown import statement: starts with command " + n.getChild(0).str);
    }
    
    private stmtType astForGlobalStmt(Node n)
    {
        /* global_stmt: 'global' NAME (',' NAME)* */
        String name;
        List<String> s = new ArrayList<String>(n.childs.size() / 2);
        
        REQ(n, DFAType.global_stmt);
        
        for(int i = 1; i < n.childs.size(); i += 2)
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
        List<String> s = new ArrayList<String>(n.childs.size() / 2);
        
        REQ(n, DFAType.nonlocal_stmt);
        
        for(int i = 1; i < n.childs.size(); i += 2)
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
        
        if(n.childs.size() == 2)
        {
            exprType expression = astForExpr(n.getChild(1));
            return new Assert(expression, null, n.lineNo, n.colOffset);
        }
        else if(n.childs.size() == 4)
        {
            exprType expr1 = astForExpr(n.getChild(1));
            exprType expr2 = astForExpr(n.getChild(3));
            return new Assert(expr1, expr2, n.lineNo, n.colOffset);
        }
        return null;
    }
    
    private stmtType astForIfStmt(Node n)
    {
        /* if_stmt: 'if' test ':' suite ('elif' test ':' suite)*
            ['else' ':' suite]
         */
        REQ(n, DFAType.if_stmt);
        
        if(n.childs.size() == 4)
        {
            exprType expression = astForExpr(n.getChild(1));
            List<stmtType> suiteSeq = astForSuite(n.getChild(3));
            return new If(expression, suiteSeq, null, n.lineNo, n.colOffset);
        }
        
        String s = n.getChild(4).str;
        
        if(s.equals("else"))
        {
            exprType expression = astForExpr(n.getChild(1));
            List<stmtType> seq1 = astForSuite(n.getChild(3));
            List<stmtType> seq2 = astForSuite(n.getChild(6));
            return new If(expression, seq1, seq2, n.lineNo, n.colOffset);
        }
        else if(s.equals("elif"))
        {
            exprType expression = null;
            List<stmtType> suiteSeq = null;
            List<stmtType> orelse = null;
            int nElif = n.childs.size() - 4;
            boolean hasElse = false;
            
            /* must reference the child n_elif+1 since 'else' token is third,
                not fourth, child from the end. */
            if(n.getChild(nElif + 1).dfaType == DFAType.NAME &&
                    n.getChild(nElif + 1).str.equals("else"))
            {
                hasElse = true;
                nElif -= 3;
            }
            nElif /= 4;
            
            if(hasElse)
            {
                List<stmtType> suiteSeq2;
                
                orelse = new ArrayList<stmtType>(1);
                expression = astForExpr(n.getChild(-6));
                suiteSeq = astForSuite(n.getChild(-4));
                suiteSeq2 = astForSuite(n.getChild(-1));
                
                orelse.add(
                        new If(expression, suiteSeq, suiteSeq2, 
                                n.getChild(-6).lineNo, 
                                n.getChild(-6).colOffset));
            }
            
            for(int i = 0; i < nElif; i++)
            {
                int off = 5 + (nElif - i - 1) * 4;
                List<stmtType> newObj = new ArrayList<stmtType>(1);
                expression = astForExpr(n.getChild(off));
                suiteSeq = astForSuite(n.getChild(off + 2));
                newObj.add(
                        new If(expression, suiteSeq, orelse,
                                n.getChild(off).lineNo, 
                                n.getChild(off).colOffset));
                orelse = newObj;
            }
            expression = astForExpr(n.getChild(1));
            suiteSeq = astForSuite(n.getChild(3));
            return new If(expression, suiteSeq, orelse, n.lineNo, n.colOffset);
        }
        throw new PyExceptions(ErrorType.AST_ERROR, 
                "unexpected token in 'if' statement: " + s);
    }
    
    private stmtType astForWhileStmt(Node n)
    {
        /* while_stmt: 'while' test ':' suite ['else' ':' suite] */
        REQ(n, DFAType.while_stmt);
        
        if(n.childs.size() == 4)
        {
            exprType expression = astForExpr(n.getChild(1));
            List<stmtType> suiteSeq = astForSuite(n.getChild(3));
            return new While(expression, suiteSeq, null, n.lineNo, n.colOffset);
        }
        else if(n.childs.size() == 7)
        {
            exprType expression = astForExpr(n.getChild(1));
            List<stmtType> seq1 = astForSuite(n.getChild(3));
            List<stmtType> seq2 = astForSuite(n.getChild(6));
            return new While(expression, seq1, seq2, n.lineNo, n.colOffset);
        }
        throw new PyExceptions(ErrorType.AST_ERROR, 
                "wrong number of tokens for 'while' statement: " + n.childs.size());
    }
    
    private stmtType astForForStmt(Node n, boolean isAsync)
    {
        /* for_stmt: 'for' exprlist 'in' testlist ':' suite ['else' ':' suite] */
        REQ(n, DFAType.for_stmt);
        
        exprType expression;
        exprType target, first;
        List<stmtType> suiteSeq;
        List<stmtType> seq = null;
        Node ch = null;
        
        if(n.childs.size() == 9)
        {
            seq = astForSuite(n.getChild(8));
        }
        
        ch = n.getChild(1);
        List<exprType> _target = astForExprlist(ch, expr_contextType.Store);
        
        /* Check the # of children rather than the length of _target, since
        for x, in ... has 1 element in _target, but still requires a Tuple. */
        first = _target.get(0);
        if(ch.childs.size() == 1)
        {
            target = first;
        }
        else
        {
            target = new Tuple(_target, expr_contextType.Store, first.lineno, first.col_offset);
        }
        expression = astForTestlist(n.getChild(3));
        suiteSeq = astForSuite(n.getChild(5));
        if(isAsync)
        {
            return new AsyncFor(target, expression, suiteSeq, seq, n.lineNo, n.colOffset);
        }
        else
        {
            return new For(target, expression, suiteSeq, seq, n.lineNo, n.colOffset);
        }
    }
    
    private stmtType astForTryStmt(Node n)
    {
        /*
           try_stmt: ('try' ':' suite
               ((except_clause ':' suite)+
                ['else' ':' suite]
                ['finally' ':' suite] |
               'finally' ':' suite))

         */
        REQ(n ,DFAType.try_stmt);
        
        int nExcept = (n.childs.size() - 3) / 3;
        List<excepthandlerType> handlers = null;
        List<stmtType> orelse = null;
        List<stmtType> finalbody = null;
        List<stmtType> body = astForSuite(n.getChild(2));
        
        if(n.getChild(-3).dfaType == DFAType.NAME)
        {
            if("finally".equals(n.getChild(-3).str))
            {
                if(n.childs.size() >= 9 &&
                        n.getChild(-6).dfaType == DFAType.NAME)
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
            throw new PyExceptions(ErrorType.AST_ERROR, "malformed 'try' statement");
        }
        if(nExcept > 0)
        {
            handlers = new ArrayList<excepthandlerType>(nExcept);
            for(int i = 0; i < nExcept; i++)
            {
                excepthandlerType e = astForExceptClause(n.getChild(3 + i * 3),
                        n.getChild(5 + i * 3));
                handlers.add(e);
            }
        }
        
        Assert(finalbody != null || handlers.size() > 0);
        return new Try(body, handlers, orelse, finalbody, n.lineNo, n.colOffset);
    }
    
    private stmtType astForWithStmt(Node n, boolean isAsync)
    {
        /* with_stmt: 'with' with_item (',' with_item)*  ':' suite */
        REQ(n, DFAType.with_stmt);
        List<withitemType> items = new ArrayList<withitemType>((n.childs.size() - 2) / 2);
        List<stmtType> body = null;
        
        for(int i = 1; i < n.childs.size() - 2; i += 2)
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
    
    private stmtType astForFuncdef(Node n, List<exprType> decoratorSeq)
    {
        /* funcdef: 'def' NAME parameters ['->' test] ':' suite */
        return astForFuncdefImpl(n, decoratorSeq, false);
    }
    
    private stmtType astForClassdef(Node n, List<exprType> decoratorSeq)
    {
        /* classdef: 'class' NAME ['(' arglist ')'] ':' suite */
        
        REQ(n, DFAType.classdef);
        
        String className = null;
        List<stmtType> s = null;
        exprType call = null;
        
        if(n.childs.size() == 4)  /* class NAME ':' suite */
        {
            s = astForSuite(n.getChild(3));
            className = newIdentifier(n.getChild(1));
            forbiddenName(className, n.getChild(3), false);
            return new ClassDef(className, null, null, s, decoratorSeq, n.lineNo, n.colOffset);
        }
        
        if(n.getChild(3).dfaType == DFAType.RPAR)  /* class NAME '(' ')' ':' suite */
        {
            s = astForSuite(n.getChild(5));
            className = newIdentifier(n.getChild(1));
            forbiddenName(className, n.getChild(3), false);
            return new ClassDef(className, null, null, s, decoratorSeq, n.lineNo, n.colOffset);
        }
        /* class NAME '(' arglist ')' ':' suite */
        /* build up a fake Call node so we can extract its pieces */
        {
            String dummyName = newIdentifier(n.getChild(1));
            exprType dummy = new Name(dummyName, expr_contextType.Load, n.lineNo, n.colOffset);
            call = astForCall(n.getChild(3), dummy);
        }
        s = astForSuite(n.getChild(6));
        className = newIdentifier(n.getChild(1));
        forbiddenName(className, n.getChild(1), false);
        
        return new ClassDef(className, ((Call)call).args, ((Call)call).keywords, s,
                decoratorSeq, n.lineNo, n.colOffset);
    }
    
    private stmtType astForDecorated(Node n)
    {
        /* decorated: decorators (classdef | funcdef | async_funcdef) */
        REQ(n, DFAType.decorated);
        
        stmtType thing = null;
        List<exprType> decoratorSeq = null;
        
        decoratorSeq = astForDecorators(n.getChild(0));
        
        Assert(n.getChild(1).dfaType == DFAType.funcdef ||
                n.getChild(1).dfaType == DFAType.async_funcdef ||
                n.getChild(1).dfaType == DFAType.classdef);
        
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
        
        switch(n.getChild(1).dfaType)
        {
        case funcdef:
            return astForFuncdefImpl(n.getChild(1), null, true);
        case with_stmt:
            return astForWithStmt(n.getChild(1), true);
        case for_stmt:
            return astForForStmt(n.getChild(1), true);
        default:
            throw new PyExceptions(ErrorType.AST_ERROR, "invalid async stament: " + n.getChild(1).str);
        }
    }
    
    private exprType astForExpr(Node n)
    {
        /* handle the full range of simple expressions
            test: or_test ['if' or_test 'else' test] | lambdef
            test_nocond: or_test | lambdef_nocond
            or_test: and_test ('or' and_test)*
            and_test: not_test ('and' not_test)*
            not_test: 'not' not_test | comparison
            comparison: expr (comp_op expr)*
            expr: xor_expr ('|' xor_expr)*
            xor_expr: and_expr ('^' and_expr)*
            and_expr: shift_expr ('&' shift_expr)*
            shift_expr: arith_expr (('<<'|'>>') arith_expr)*
            arith_expr: term (('+'|'-') term)*
            term: factor (('*'|'@'|'/'|'%'|'//') factor)*
            factor: ('+'|'-'|'~') factor | power
            power: atom_expr ['**' factor]
            atom_expr: [AWAIT] atom trailer*
            yield_expr: 'yield' [yield_arg]
         */
        List<exprType> seq = null;
        
        while(true)
        {
            switch(n.dfaType)
            {
            case test:
            case test_nocond:
                if(n.getChild(0).dfaType == DFAType.lambdef ||
                        n.getChild(0).dfaType == DFAType.lambdef_nocond)
                {
                    return astForLambdef(n.getChild(0));
                }
                else if(n.childs.size() > 1)
                {
                    return astForIfexpr(n);
                }
                /* Fallthrough */
            case or_test:
            case and_test:
                if(n.childs.size() == 1)
                {
                    n = n.getChild(0);
                    continue;
                }
                seq = new ArrayList<exprType>((n.childs.size() + 1) / 2);
                for(int i = 0; i < n.childs.size(); i += 2)
                {
                    exprType e = astForExpr(n.getChild(i));
                    seq.add(e);
                }
                if("and".equals(n.getChild(1).str))
                {
                    return new BoolOp(boolopType.And, seq, n.lineNo, n.colOffset);
                }
                Assert("or".equals(n.getChild(1).str));
                return new BoolOp(boolopType.Or, seq, n.lineNo, n.colOffset);
            case not_test:
                if(n.childs.size() == 1)
                {
                    n = n.getChild(0);
                    continue;
                }
                else
                {
                    exprType expression = astForExpr(n.getChild(1));
                    return new UnaryOp(unaryopType.Not, expression, n.lineNo, n.colOffset);
                }
            case comparison:
                if(n.childs.size() == 1)
                {
                    n = n.getChild(0);
                    continue;
                }
                else
                {
                    exprType expression;
                    List<cmpopType> ops = new ArrayList<cmpopType>(n.childs.size() / 2);
                    List<exprType> cmps = new ArrayList<exprType>(n.childs.size() / 2);
                    
                    for(int i = 1; i < n.childs.size(); i += 2)
                    {
                        cmpopType newOperator = astForCompOp(n.getChild(i));
                        expression = astForExpr(n.getChild(i + 1));
                        ops.add(newOperator);
                        cmps.add(expression);
                    }
                    
                    expression = astForExpr(n.getChild(0));
                    return new Compare(expression, ops, cmps, n.lineNo, n.colOffset);
                }
            case star_expr:
                return astForStarred(n);
            case expr:
            case xor_expr:
            case and_expr:
            case shift_expr:
            case arith_expr:
            case term:
                if(n.childs.size() == 1)
                {
                    n = n.getChild(0);
                    continue;
                }
            case yield_expr:
                Node an = null;
                Node en = null;
                boolean isFrom = false;
                exprType exp = null;
                if(n.childs.size() > 1)
                {
                    an = n.getChild(1);
                }
                if(an != null)
                {
                    en = an.getChild(-1);
                    if(an.childs.size() == 2)
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
                if(n.childs.size() == 1)
                {
                    n = n.getChild(0);
                    continue;
                }
                return astForFactor(n);
            case power:
                return astForPower(n);
            default:
                throw new PyExceptions(ErrorType.AST_ERROR, 
                        "unhandled expr: " + n.dfaType);
            }
        }
    }
    
    private List<exprType> seqForTestlist(Node n)
    {
        /* testlist: test (',' test)* [',']
            testlist_star_expr: test|star_expr (',' test|star_expr)* [',']
         */
        Assert(n.dfaType == DFAType.testlist ||
                n.dfaType == DFAType.testlist_star_expr ||
                n.dfaType == DFAType.testlist_comp);
        List<exprType> seq = new ArrayList<exprType>((n.childs.size() + 1) / 2);
        exprType expression = null;
        for(int i = 0; i < n.childs.size(); i += 2)
        {
            Node ch = n.getChild(i);
            Assert(ch.dfaType == DFAType.test ||
                    ch.dfaType == DFAType.test_nocond ||
                    ch.dfaType == DFAType.star_expr);
            expression = astForExpr(ch);
            seq.add(expression);
        }
        return seq;
    }
    
    private stmtType astForStmt(Node n)
    {
        if(n.dfaType == DFAType.stmt)
        {
            Assert(n.childs.size() == 1);
            n = n.getChild(0);
        }
        if(n.dfaType == DFAType.simple_stmt)
        {
            Assert(numStmts(n) == 1);
            n = n.getChild(0);
        }
        if(n.dfaType == DFAType.small_stmt)
        {
            n = n.getChild(0);
            /* small_stmt: expr_stmt | del_stmt | pass_stmt | flow_stmt
                    | import_stmt | global_stmt | nonlocal_stmt | assert_stmt
            */

            switch(n.dfaType)
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
                throw new PyExceptions(ErrorType.AST_ERROR, "unhandled small_stmt: Type=" + 
                        n.dfaType.toString() + "child length=" +
                        n.childs.size());
            }
        }
        else
        {
            /* compound_stmt: if_stmt | while_stmt | for_stmt | try_stmt
                    | funcdef | classdef | decorated | async_stmt
            */
            Node ch = n.getChild(0);
            REQ(n, DFAType.compound_stmt);
            switch(ch.dfaType)
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
                throw new PyExceptions(ErrorType.AST_ERROR, "unhandled small_stmt: Type=" + 
                        n.dfaType.toString() + "child length=" +
                        n.childs.size());
            }
        }
    }
    
    private exprType astForTestlist(Node n)
    {
        /* testlist_comp: test (comp_for | (',' test)* [',']) */
        /* testlist: test (',' test)* [','] */
        Assert(n.childs.size() > 0);
        if(n.dfaType == DFAType.testlist_comp)
        {
            if(n.childs.size() > 1)
            {
                Assert(n.getChild(1).dfaType != DFAType.comp_for);
            }
        }
        else
        {
            Assert(n.dfaType == DFAType.testlist ||
                    n.dfaType == DFAType.testlist_star_expr);
        }
        if(n.childs.size() == 1)
        {
            return astForExpr(n.getChild(0));
        }
        else
        {
            List<exprType> tmp = seqForTestlist(n);
            return new Tuple(tmp, expr_contextType.Load, n.lineNo, n.colOffset);
        }
    }
    
    public modType fromNode(Node n)
    {
        if(!n.isDFAType)
        {
            throw new PyExceptions(ErrorType.AST_ERROR, "cst's top node is not nonterminal node");
        }
        
        modType res = null;
        List<stmtType> stmts = null;
        stmtType s;
        Node ch;
        int num;
        
        switch(n.dfaType)
        {
        case file_input:
            stmts = new ArrayList<stmtType>(numStmts(n));
            for(int i = 0; i < n.childs.size() - 1; i++)
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
                    for(int j = 0; j < num; j++)
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
                    for(int i = 0; i < n.childs.size(); i+=2)
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
            throw new PyExceptions(ErrorType.AST_ERROR, "invalid node " + 
                    n.dfaType.toString() + " for FromNode");
        }
        
        return res;
        
    }
}
