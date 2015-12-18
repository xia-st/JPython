package pers.xia.jpython.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import pers.xia.jpython.config.Config;
import pers.xia.jpython.object.PyExceptions;

public class Tokenizer
{
    private InputStream in;
    private String buf;
    private Logger log;
    private Token tok;
    
    public Tokenizer()
    {
        log = Logger.getLogger(Tokenizer.class);
    }
    
    public Tokenizer(File file)
    {
        this();
        this.tok = new Token(file);
    }

    public Token nextToken()
    {
        this.tok.get();
        return tok;
    }
    
    public static void main(String[] args)
    {
       File file = new File("test.py");
       try
       {
           Tokenizer tokenizer = new Tokenizer(file);
           Token tok = tokenizer.nextToken();
           while(tok.state != TokState.ENDMARKER)
           {
               System.out.println(tok);
               tok = tokenizer.nextToken();
           }
       }
       catch(PyExceptions e)
       {
           e.printStackTrace();
       }
    }
}
