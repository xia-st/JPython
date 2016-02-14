// Autogenerated AST node
package pers.xia.jpython.ast;
import pers.xia.jpython.object.PyObject;
import pers.xia.jpython.parser.PythonNode;
import java.io.DataOutputStream;
import java.io.IOException;

public class Attribute extends exprType {
    public exprType value;
    public String attr;
    public int ctx;

    public Attribute(exprType value, String attr, int ctx) {
        this.value = value;
        this.attr = attr;
        this.ctx = ctx;
    }

    public Attribute(exprType value, String attr, int ctx, PythonNode
    parent) {
        this(value, attr, ctx);
        this.beginLine = parent.beginLine;
        this.beginColumn = parent.beginColumn;
    }

    public String toString() {
        return "Attribute";
    }

    public void pickle(DataOutputStream ostream) throws IOException {
    }

    public Object accept(VisitorIF visitor) throws Exception {
        return visitor.visitAttribute(this);
    }

    public void traverse(VisitorIF visitor) throws Exception {
    }

}