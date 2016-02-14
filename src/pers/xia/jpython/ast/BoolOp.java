// Autogenerated AST node
package pers.xia.jpython.ast;
import pers.xia.jpython.object.PyObject;
import pers.xia.jpython.parser.PythonNode;
import java.io.DataOutputStream;
import java.io.IOException;

public class BoolOp extends exprType {
    public int op;
    public exprType[] values;

    public BoolOp(int op, exprType[] values) {
        this.op = op;
        this.values = values;
    }

    public BoolOp(int op, exprType[] values, PythonNode parent) {
        this(op, values);
        this.beginLine = parent.beginLine;
        this.beginColumn = parent.beginColumn;
    }

    public String toString() {
        return "BoolOp";
    }

    public void pickle(DataOutputStream ostream) throws IOException {
    }

    public Object accept(VisitorIF visitor) throws Exception {
        return visitor.visitBoolOp(this);
    }

    public void traverse(VisitorIF visitor) throws Exception {
    }

}
