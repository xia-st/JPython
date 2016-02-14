// Autogenerated AST node
package pers.xia.jpython.ast;
import pers.xia.jpython.object.PyObject;
import pers.xia.jpython.parser.PythonNode;
import java.io.DataOutputStream;
import java.io.IOException;

public class argType extends PythonNode {
    public String arg;
    public exprType annotation;

    public argType(String arg, exprType annotation) {
        this.arg = arg;
        this.annotation = annotation;
    }

    public argType(String arg, exprType annotation, PythonNode parent) {
        this(arg, annotation);
        this.beginLine = parent.beginLine;
        this.beginColumn = parent.beginColumn;
    }

    public String toString() {
        return "argType";
    }

    public void pickle(DataOutputStream ostream) throws IOException {
    }

    public Object accept(VisitorIF visitor) throws Exception {
        traverse(visitor);
        return null;
    }

    public void traverse(VisitorIF visitor) throws Exception {
    }

}
