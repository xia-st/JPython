// Autogenerated AST node
package pers.xia.jpython.ast;
import pers.xia.jpython.object.PyObject;
import pers.xia.jpython.parser.PythonNode;
import java.io.DataOutputStream;
import java.io.IOException;

public class IfExp extends exprType {
    public exprType test;
    public exprType body;
    public exprType orelse;

    public IfExp(exprType test, exprType body, exprType orelse) {
        this.test = test;
        this.body = body;
        this.orelse = orelse;
    }

    public IfExp(exprType test, exprType body, exprType orelse, PythonNode
    parent) {
        this(test, body, orelse);
        this.beginLine = parent.beginLine;
        this.beginColumn = parent.beginColumn;
    }

    public String toString() {
        return "IfExp";
    }

    public void pickle(DataOutputStream ostream) throws IOException {
    }

    public Object accept(VisitorIF visitor) throws Exception {
        return visitor.visitIfExp(this);
    }

    public void traverse(VisitorIF visitor) throws Exception {
    }

}
