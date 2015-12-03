package pers.xia.jpython.parser;

import java.io.File;

import pers.xia.jpython.config.Config;

public class TokState
{
	String buf;
	int cur;
	ErrorCode done;
	File file;
	int tabSize;
	int indent;
	int indstack[];	//Stack of indents
	Boolean atbol;
	int pendin;
	String prompt;	//For interactive prompting
	String nextPrompt;	//For interactive prompting
	int lineNo;
	int level;	//Parentheses nesting level
	String enc;
	String str;
	String input;
	
	public TokState()
	{
		this.buf = null;
		this.cur = 0;
		this.done = ErrorCode.E_OK;
		this.file = null;
		this.tabSize = 0;
		this.indent = 0;
		this.tabSize = Config.TABSIZE;
		this.indstack = new int[Config.MAXINDENT];
		this.atbol = true;
		this.pendin = 0;
		this.prompt = null;
		this.nextPrompt = null;
		this.lineNo = 0;
		this.level = 0;
		this.enc = null;
		this.str = null;
		this.input = null;		
	}
	
	public TokState(String s)
	{
	}
}
