package pers.xia.jpython.main;

import java.io.File;


import pers.xia.jpython.grammar.GramInit;
import pers.xia.jpython.object.PyExceptions;
import pers.xia.jpython.parser.Ast;
import pers.xia.jpython.parser.Node;
import pers.xia.jpython.parser.Parser;
import pers.xia.jpython.parser.Parser.ReturnCode;
import pers.xia.jpython.tokenizer.TokState;
import pers.xia.jpython.tokenizer.Token;
import pers.xia.jpython.tokenizer.Tokenizer;

public class Run
{
    public static void main(String[] args)
    {
        File file = new File("test.py");
        Parser parser = new Parser(GramInit.grammar, 1);

        try
        {
            Tokenizer tokenizer = new Tokenizer(file);
            Token tok = tokenizer.nextToken();
            int colOffset = 0;
            while(parser.addToken(tok, colOffset) != ReturnCode.ACCEPT)
            {
                if(tok.state == TokState.NEWLINE)
                {
                    colOffset = 0;
                }
                else
                {
                    colOffset++;
                }
                tok = tokenizer.nextToken();
            }
            // parser.show();
        }
        catch(PyExceptions e)
        {
            e.printStackTrace();
            throw e;
        }
        
        Node tree = parser.tree;
        
        Ast ast = new Ast();
        ast.fromNode(tree);
    }
}
