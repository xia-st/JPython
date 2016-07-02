// Autogenerated AST node
package pers.xia.jpython.ast;
import pers.xia.jpython.object.PyObject;

public interface VisitorIF {
    public Object visitModule(Module node) throws Exception;
    public Object visitInteractive(Interactive node) throws Exception;
    public Object visitExpression(Expression node) throws Exception;
    public Object visitSuite(Suite node) throws Exception;
    public Object visitFunctionDef(FunctionDef node) throws Exception;
    public Object visitAsyncFunctionDef(AsyncFunctionDef node) throws
    Exception;
    public Object visitClassDef(ClassDef node) throws Exception;
    public Object visitReturn(Return node) throws Exception;
    public Object visitDelete(Delete node) throws Exception;
    public Object visitAssign(Assign node) throws Exception;
    public Object visitAugAssign(AugAssign node) throws Exception;
    public Object visitFor(For node) throws Exception;
    public Object visitAsyncFor(AsyncFor node) throws Exception;
    public Object visitWhile(While node) throws Exception;
    public Object visitIf(If node) throws Exception;
    public Object visitWith(With node) throws Exception;
    public Object visitAsyncWith(AsyncWith node) throws Exception;
    public Object visitRaise(Raise node) throws Exception;
    public Object visitTry(Try node) throws Exception;
    public Object visitAssert(Assert node) throws Exception;
    public Object visitImport(Import node) throws Exception;
    public Object visitImportFrom(ImportFrom node) throws Exception;
    public Object visitGlobal(Global node) throws Exception;
    public Object visitNonlocal(Nonlocal node) throws Exception;
    public Object visitExpr(Expr node) throws Exception;
    public Object visitPass(Pass node) throws Exception;
    public Object visitBreak(Break node) throws Exception;
    public Object visitContinue(Continue node) throws Exception;
    public Object visitBoolOp(BoolOp node) throws Exception;
    public Object visitBinOp(BinOp node) throws Exception;
    public Object visitUnaryOp(UnaryOp node) throws Exception;
    public Object visitLambda(Lambda node) throws Exception;
    public Object visitIfExp(IfExp node) throws Exception;
    public Object visitDict(Dict node) throws Exception;
    public Object visitSet(Set node) throws Exception;
    public Object visitListComp(ListComp node) throws Exception;
    public Object visitSetComp(SetComp node) throws Exception;
    public Object visitDictComp(DictComp node) throws Exception;
    public Object visitGeneratorExp(GeneratorExp node) throws Exception;
    public Object visitAwait(Await node) throws Exception;
    public Object visitYield(Yield node) throws Exception;
    public Object visitYieldFrom(YieldFrom node) throws Exception;
    public Object visitCompare(Compare node) throws Exception;
    public Object visitCall(Call node) throws Exception;
    public Object visitNum(Num node) throws Exception;
    public Object visitStr(Str node) throws Exception;
    public Object visitBytes(Bytes node) throws Exception;
    public Object visitNameConstant(NameConstant node) throws Exception;
    public Object visitEllipsis(Ellipsis node) throws Exception;
    public Object visitAttribute(Attribute node) throws Exception;
    public Object visitSubscript(Subscript node) throws Exception;
    public Object visitStarred(Starred node) throws Exception;
    public Object visitName(Name node) throws Exception;
    public Object visitList(List node) throws Exception;
    public Object visitTuple(Tuple node) throws Exception;
    public Object visitSlice(Slice node) throws Exception;
    public Object visitExtSlice(ExtSlice node) throws Exception;
    public Object visitIndex(Index node) throws Exception;
    public Object visitExceptHandler(ExceptHandler node) throws Exception;
}
