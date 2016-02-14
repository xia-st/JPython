// Autogenerated AST node
package pers.xia.jpython.ast;
import pers.xia.jpython.object.PyObject;
import pers.xia.jpython.parser.PythonNode;
import java.io.DataOutputStream;
import java.io.IOException;

public class AsyncFunctionDef extends stmtType {
    public String name;
    public argumentsType args;
    public stmtType[] body;
    public exprType[] decorator_list;
    public exprType returns;

    public AsyncFunctionDef(String name, argumentsType args, stmtType[]
    body, exprType[] decorator_list, exprType returns) {
        this.name = name;
        this.args = args;
        this.body = body;
        this.decorator_list = decorator_list;
        this.returns = returns;
    }

    public AsyncFunctionDef(String name, argumentsType args, stmtType[]
    body, exprType[] decorator_list, exprType returns, PythonNode parent) {
        this(name, args, body, decorator_list, returns);
        this.beginLine = parent.beginLine;
        this.beginColumn = parent.beginColumn;
    }

    public String toString() {
        return "AsyncFunctionDef";
    }

    public void pickle(DataOutputStream ostream) throws IOException {
    }

    public Object accept(VisitorIF visitor) throws Exception {
        return visitor.visitAsyncFunctionDef(this);
    }

    public void traverse(VisitorIF visitor) throws Exception {
    }

}
