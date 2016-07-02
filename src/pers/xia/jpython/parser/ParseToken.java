package pers.xia.jpython.parser;

import java.io.File;

import pers.xia.jpython.grammar.GramInit;
import pers.xia.jpython.grammar.Grammar;
import pers.xia.jpython.object.PyExceptions;
import pers.xia.jpython.parser.Parser.ReturnCode;
import pers.xia.jpython.tokenizer.TokState;
import pers.xia.jpython.tokenizer.Token;
import pers.xia.jpython.tokenizer.Tokenizer;

public class ParseToken
{
    public static Node parseFile(File file, Grammar grammar, int start)
    {

        try
        {
            Parser parser = new Parser(grammar, start);
            Tokenizer tokenizer = new Tokenizer(file);
            Token tok = tokenizer.nextToken();
            int colOffset = 1;
            int lineNo = 1;
            while (parser.addToken(tok, colOffset) != ReturnCode.ACCEPT)
            {

                tok = tokenizer.nextToken();
                if(tok.state != TokState.NEWLINE && tok.state != TokState.INDENT
                        && tok.state != TokState.DEDENT)
                {
                    if(tok.lineNo != lineNo)
                    {
                        colOffset = 1;
                        lineNo = tok.lineNo;
                    }
                    else
                    {
                        colOffset++;
                    }
                }
            }
            return parser.tree;
        }
        catch (PyExceptions e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args)
    {
        File file = new File("test/translator.py");
        Node n = ParseToken.parseFile(file, GramInit.grammar, 1);
        n.show();
    }
}
