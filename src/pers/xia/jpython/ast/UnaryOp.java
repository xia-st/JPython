// Autogenerated AST node
package pers.xia.jpython.ast;
import pers.xia.jpython.object.PyObject;
import pers.xia.jpython.parser.PythonNode;
import java.io.DataOutputStream;
import java.io.IOException;

public class UnaryOp extends exprType {
    public int op;
    public exprType operand;

    public UnaryOp(int op, exprType operand) {
        this.op = op;
        this.operand = operand;
    }

    public UnaryOp(int op, exprType operand, PythonNode parent) {
        this(op, operand);
        this.beginLine = parent.beginLine;
        this.beginColumn = parent.beginColumn;
    }

    public String toString() {
        return "UnaryOp";
    }

    public void pickle(DataOutputStream ostream) throws IOException {
    }

    public Object accept(VisitorIF visitor) throws Exception {
        return visitor.visitUnaryOp(this);
    }

    public void traverse(VisitorIF visitor) throws Exception {
    }

}
