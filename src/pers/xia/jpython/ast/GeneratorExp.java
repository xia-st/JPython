// Autogenerated AST node
package pers.xia.jpython.ast;
import pers.xia.jpython.object.PyObject;
import pers.xia.jpython.parser.PythonNode;
import java.io.DataOutputStream;
import java.io.IOException;

public class GeneratorExp extends exprType {
    public exprType elt;
    public comprehensionType[] generators;

    public GeneratorExp(exprType elt, comprehensionType[] generators) {
        this.elt = elt;
        this.generators = generators;
    }

    public GeneratorExp(exprType elt, comprehensionType[] generators,
    PythonNode parent) {
        this(elt, generators);
        this.beginLine = parent.beginLine;
        this.beginColumn = parent.beginColumn;
    }

    public String toString() {
        return "GeneratorExp";
    }

    public void pickle(DataOutputStream ostream) throws IOException {
    }

    public Object accept(VisitorIF visitor) throws Exception {
        return visitor.visitGeneratorExp(this);
    }

    public void traverse(VisitorIF visitor) throws Exception {
    }

}
