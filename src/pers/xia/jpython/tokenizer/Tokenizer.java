package pers.xia.jpython.tokenizer;

import java.io.File;

import pers.xia.jpython.object.PyExceptions;

public class Tokenizer
{
    private Token tok;

    public Tokenizer()
    {
    }

    public Tokenizer(File file)
    {
        this();
        this.tok = new Token(file);
    }

    public Token nextToken()
    {
        this.tok.get();
        this.tok.str = this.tok.buf.substring(this.tok.start, this.tok.end);
        return tok;
    }

    public static void main(String[] args)
    {
        File file = new File("./test/test.py");
        try
        {
            Tokenizer tokenizer = new Tokenizer(file);
            Token tok = tokenizer.nextToken();
            while (tok.state != TokState.ENDMARKER)
            {
                System.out.println(tok);
                tok = tokenizer.nextToken();
            }
        }
        catch (PyExceptions e)
        {
            e.printStackTrace();
        }
    }
}
