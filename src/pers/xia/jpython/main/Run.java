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

    public static void parse(String fileName)
    {
        File file = new File(fileName);
        if(file.isDirectory())
            return;
        Parser parser = new Parser(GramInit.grammar, 1);

        try
        {
            Tokenizer tokenizer = new Tokenizer(file);
            Token tok = tokenizer.nextToken();
            int colOffset = 0;
            while (parser.addToken(tok, colOffset) != ReturnCode.ACCEPT)
            {
                if(tok.state == TokState.NEWLINE)
                {
                    colOffset = 1;
                }
                else
                {
                    colOffset++;
                }
                tok = tokenizer.nextToken();
            }

            //parser.show();

            Node tree = parser.tree;

            Ast ast = new Ast();
            ast.fromNode(tree);
        }
        catch (PyExceptions e)
        {
            System.out.println(fileName);
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args)
    {
        //*

        File file = new File("./test");
        if(file.isDirectory())
        {
            String[] filelist = file.list();
            for (String fileName : filelist)
            {
                if(fileName.charAt(fileName.length() - 1) == 'y')
                    parse("./test/" + fileName);
            }
        }
        //*/

        //parse("./test/test.py");
        // System.out.println(Runtime.getRuntime().totalMemory() -
        // Runtime.getRuntime().freeMemory());
    }
}
