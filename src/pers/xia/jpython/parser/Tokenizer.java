package pers.xia.jpython.parser;

import java.io.File;

import pers.xia.jpython.object.PyExceptions;

public class Tokenizer
{
	private TokState tok;
	public Tokenizer(File file, String ps1, String ps2)
	{
		tok = new TokState();
		tok.file = file;
		tok.prompt = ps1;
		tok.nextPrompt = ps2;
	}
	
	public Tokenizer()
	{
		tok = new TokState();
	}
	
	private char nextC()
	{
		for(;;)
		{
			if(tok.buf == null)
			{
                throw new PyExceptions("Tokenizer Error: No interactive mode");
            }
            
            if(tok.cur < tok.buf.length())
            {
                return tok.buf.charAt(tok.cur++);
            }
            if(tok.file == null)
            {
                throw new PyExceptions("Tokenizer Error: No interactive mode");
            }
            
        }
    }
    public static void main(String[] args)
    {
        try
        {
                Tokenizer tk = new Tokenizer();
                tk.nextC();    
        } catch (PyExceptions e)
        {
            System.out.println(e);
        }
        System.out.println("Hello");
    }
}
