<<<<<<< HEAD
package pers.xia.jpython.main;

import java.io.File;

import pers.xia.jpython.grammar.GramInit;
import pers.xia.jpython.object.PyExceptions;
import pers.xia.jpython.parser.Ast;
import pers.xia.jpython.parser.Node;
import pers.xia.jpython.parser.ParseToken;

public class Run
{

    public static void parse(String fileName)
    {
        File file = new File(fileName);
        if(file.isDirectory())
            return;
        try
        {
            Node node = ParseToken.parseFile(file, GramInit.grammar, 1);

            Ast ast = new Ast();
            ast.fromNode(node);
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
    }
}
=======
package pers.xia.jpython.main;

import java.io.File;

import pers.xia.jpython.grammar.GramInit;
import pers.xia.jpython.object.PyExceptions;
import pers.xia.jpython.parser.Ast;
import pers.xia.jpython.parser.Node;
import pers.xia.jpython.parser.ParseToken;
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
        try
        {
            Node node = ParseToken.parseFile(file, GramInit.grammar, 1);

            Ast ast = new Ast();
            ast.fromNode(node);
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
        File file = new File("./test");
        if(file.isDirectory())
        {
            String[] fileList = file.list();
            for (String fileName : fileList)
            {
                if(fileName.charAt(fileName.length() - 1) == 'y')
                    parse("./test/" + fileName);
            }
        }
    }
}
>>>>>>> develop
