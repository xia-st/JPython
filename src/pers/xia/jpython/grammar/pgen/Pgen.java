package pers.xia.jpython.grammar.pgen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import pers.xia.jpython.grammar.pgen.*;
import pers.xia.jpython.object.PyExceptions;
import pers.xia.jpython.parser.TokState;

public class Pgen
{
	_Grammar grammar;
	
	private int level;	//括号的等级
	private int cur;	//当前字符下标
	private String buf;	//每一行数据的缓存
	private BufferedReader br;	//文件缓存
	private Boolean newLine;	//是否读取下一行数据

	private Logger log;


	public Pgen()
	{
		this.grammar = new _Grammar();
		this.level = 0;
		this.cur = 0;
		this.buf = null;
		this.newLine = false;
		
		this.log = Logger.getLogger(Pgen.class);
	}
	
	public Pgen(File file)
	{
		this();
		try
		{
			this.br = new BufferedReader(
					new FileReader(file));
		} catch (FileNotFoundException e)
		{
			log.error("文件打开失败");
			throw new PyExceptions("Open file error");
		}
	}

	//读取下一行的grammar文件
	public boolean nextLine()
	{
		try
		{
			for(;;)
			{
				this.buf = this.br.readLine();
				if(this.buf == null) return false;
				this.buf = this.buf.trim();	//去掉首尾的非可打印字符(空格、制表符等)
				if(!this.buf.equals("")) break; //如果字符串中有数据的话跳出循环
			}
			this.cur = 0;
		} catch (IOException e)
		{
			throw new PyExceptions("buf read Error");
		}
		return true;
	}
	
	//获取下一个字符
	public char nextC()
	{
		char c;
		
		//如果newLine标记为true的话读取下一行数据
		if(this.newLine)
		{
			this.newLine = false;
			this.nextLine();
		}
		for(;;)
		{
			if(this.buf != null && this.cur < this.buf.length())
			{
				//fast page
				c = this.buf.charAt(this.cur++);
			}
			else if(this.buf == null)
			{
				//如果是刚开始没有数据的话则读取下一行。
				if(this.nextLine())
				{
					c = this.buf.charAt(this.cur++);
				}
				else 
				{
					return '\0';
				}
			}
			else if(this.level > 0)
			{
				/* 为了后面的方法能够保存这一行的数据，先不读取下
				 * 一行数据，而是设置一个标志，在下次运行本方法的
				 * 时候再读取，本次直接返回一个空格。
				 */
				this.newLine = true;
				return ' ';
			}
			else
			{
				return '\0';
			}
			
			//去掉注释
			if(c == '#')
			{
				this.nextLine();
				continue;
			}
			return c;
		}
	}
	
	//回退字符
	public void backup(char c)
	{
		if(c != '\0' && this.newLine == false)
		{
			if(--this.cur < 0)
			{
				throw new PyExceptions("token.backup: beginning of buffer");
			}
			if(this.buf.charAt(this.cur) != c)
			{
				throw new PyExceptions("token.backup: backup error");
			}
		}
	}
	
	public _NodeType oneChar(char c)
	{
		switch(c)
		{
		case '(': return _NodeType.LPAR;
		case ')': return _NodeType.RPAR;
		case '[': return _NodeType.LSQB;
		case ']': return _NodeType.RSQB;
		case '|': return _NodeType.VBAR;
		case '*': return _NodeType.STAR;
		case '+': return _NodeType.PLUS;
		}
		return null;
	}
	
	//获取下一个node
	public _Node nextNode()
	{
		_Node node = new _Node();
		char c;
		for(;;)
		{
			//跳过空白
			c = this.nextC();
			if(c == ' ' || c == '\t') continue;

			break;
		}
		
		//读取到一行的末尾
		if(c == '\0')	return null;
			
		//字符串
		if(c == '\'')
		{
			int start = this.cur;
			do
			{
				c = this.nextC();
				if(c == '\0')
				{
					throw new PyExceptions("Can't find end quote");
				}
			}while(c != '\'');
			
			int end = this.cur - 1;
			node.type = _NodeType.STRING;
			node.value = this.buf.substring(start, end);
			
			return node;
		}
		
		//非终结符
		if(c >= 'A' && c <= 'Z')
		{
			int start = this.cur - 1;
			do
			{
				c = this.nextC();
			}while(c >= 'A' && c <= 'Z');
			
			this.backup(c);

			int end = this.cur;
			node.type = _NodeType.NAME;
			node.value = this.buf.substring(start, end);
			
			
			return node;
		}
		
		//DFA name
		if((c >= 'a' && c <= 'z') || c == '_')
		{
			int start = this.cur - 1;
			do
			{
				c = this.nextC();
			}while((c >= 'a' && c <= 'z') || c == '_');
			
			this.backup(c);
	
			int end = this.cur;
			node.type = _NodeType.DFANAME;
						
			node.value = this.buf.substring(start, end);
			
			return node;
		}
		
		if(c == '(' || c == '[')
		{
			this.level++;
		}
		if(c == ')' || c == ']')
		{
			this.level--;
		}
		
		_NodeType type = this.oneChar(c);
		if(type != null)
		{
			node.type = type;
			node.value = Character.toString(c);
			return node;
		}
		throw new PyExceptions("Can't deal the character: " + c);
	}
	
	//获取每一行DFA的name
	public String getDFAName()
	{
		char c;
		for(;;)
		{
			c = this.nextC();
			
			if(c == ' ' || c == '\t')
			{
				continue;
			}
			break;
		}
		
		if(c == '\0')
		{
			throw new PyExceptions("Not a DFA name");
		}
		
		if((c < 'a' || c > 'z') && c != '_')
		{
			log.error(Character.toString(c));
			throw new PyExceptions("Not a DFA name");
		}
		
		int start = this.cur - 1;
		do
		{
			c = this.nextC();
		}while((c >= 'a' && c <= 'z') || c == '_');
		
		int end = this.cur;
		
		while(c == ' ' || c == '\t')
		{
			c = this.nextC();
		}
		if(c != ':')
		{
			throw new PyExceptions("Can't find char \":\"");
		}
		
		return this.buf.substring(start, end);
	}
		
	
	//下面代码开始创建语法树的代码。
	
	//收缩结点
	public _Node shrink(Stack<_Node> nodeS)
	{
		_Node orNode = new _Node();
		orNode.type = _NodeType.OR;
		_Node node = null;

		while(!nodeS.empty())
		{
			node = nodeS.pop();
			orNode.addFirstChild(node);

			if(nodeS.empty())
			{
				break;
			}

			node = nodeS.pop();

			if(node.type != _NodeType.VBAR)
			{
				throw new PyExceptions("nodeS Structure confusion");
			}
		}
		return orNode.childs.size() == 1 ?  orNode.childs.getLast() : orNode;
	}
	
	//以括号为判断标志收缩结点
	public _Node shrink(Stack<_Node> nodeS, _NodeType type)
	{
		_Node orNode = new _Node();
		orNode.type = _NodeType.OR;
		_Node node = null;

		while(!nodeS.empty())
		{
			node = nodeS.pop();
			
			if(node.type == type)
			{
				throw new PyExceptions("Empty node");
			}
			
			orNode.addFirstChild(node);

			if(nodeS.empty())
			{
				throw new PyExceptions("Can't find type: " + type);
			}
			node = nodeS.pop();
			
			if(node.type == type) break;
			
			if(node.type != _NodeType.VBAR)
			{
				throw new PyExceptions("nodeS Structure confusion");
			}
		}
		if(node.type == type)
		{
			return orNode.childs.size() == 1 ?  orNode.childs.getLast() : orNode;
		}
		throw new PyExceptions("Can't find " + type.toString());
	}
	
	public _Node createTree()
	{
		Stack<_Node> nodeS = new Stack<_Node>();
		_Node node = null;
		_Node nodeAhead = null;
		Boolean hasAhead = false;
		
		for(;;)
		{
			node = hasAhead ? nodeAhead : this.nextNode();
			hasAhead = false;
			
			if(node == null) break;

			if(node.type == _NodeType.VBAR ||
					node.type == _NodeType.LPAR ||
					node.type == _NodeType.LSQB)
			{
				nodeS.push(node);
				continue;
			}
			else if(node.type == _NodeType.RPAR)
			{
				node = this.shrink(nodeS, _NodeType.LPAR);
			}
			else if(node.type == _NodeType.RSQB)
			{
				//把LSQB作为”选择“逻辑的标志
				_Node resultNode = this.shrink(nodeS, _NodeType.LSQB);
				node.addChild(resultNode);
			}
			else if(node.type != _NodeType.NAME &&
					node.type != _NodeType.STRING &&
					node.type != _NodeType.DFANAME)
			{
				//只有符合右边元素的才能进行接下来的操作。
				throw new PyExceptions("Unknow Node: " + node.toString());
			}
			
			//预读下一个Node
			nodeAhead = this.nextNode();
			if(nodeAhead != null)
			{
				if(nodeAhead.type == _NodeType.PLUS ||
						nodeAhead.type == _NodeType.STAR)
				{
					nodeAhead.addChild(node);
					node = nodeAhead;
				}
				else
				{
					hasAhead = true;
				}
			}
			
			if(nodeS.empty())
			{
				nodeS.push(node);
				continue;
			}		

			_Node preNode = nodeS.pop();

			if(preNode.type == _NodeType.AND)
			{
				/* 
				 * 前一个Node的Type为AND的话把该结点放入到前一个结点中，
				 */
				preNode.addChild(node);
				nodeS.push(preNode);
				continue;
			}
			
			if(preNode.type != _NodeType.VBAR &&
					preNode.type != _NodeType.LPAR &&
					preNode.type != _NodeType.LSQB)
			{
				/*
				 * 前一个Node的Type为这几者之一的话就用AND Type的结点将他们
				 * 连起来
				 */
				
				_Node optNode = new _Node();
				optNode.type = _NodeType.AND;
				optNode.addChild(preNode);
				optNode.addChild(node);
				nodeS.push(optNode);
				continue;
			}
			nodeS.push(preNode);
			nodeS.push(node);
		}
		node = this.shrink(nodeS);

		return node;
	}
	
	public void showTree(_Node tree)
	{
		System.out.println(tree);
		List<_Node> childs = tree.childs;
		List<_Node> childs2;
		
		while(!childs.isEmpty())
		{
			childs2 = new LinkedList<_Node>();
			for(_Node node : childs)
			{
				System.out.print(node + " ");
				childs2.addAll(node.childs);
			}
			System.out.println();
			childs = childs2;
		}
	}
	
	
	//下面代码开始创建FA
	
	
	public DoubleS connChild(_Node node)
	{
		if(node.childs.isEmpty())
		{
			_Label label = null;
			if(node.type == _NodeType.NAME)
			{
				label = new _Label(TokState.valueOf(node.value), null);
			}
			else if(node.type == _NodeType.STRING)
			{
				label = new _Label(TokState.NAME, node.value);
			}
			else if(node.type == _NodeType.DFANAME)
			{
				label = new _Label(node.value);
			}
			this.grammar.setLabel(label);
			return new DoubleS(label);
		}
		
		if(node.type == _NodeType.AND)
		{
			_State start = new _State();
			_State end = null;
			DoubleS ds = null;
			for(_Node cNode : node.childs)
			{
				ds = this.connChild(cNode);
			}
		}
		return null;
	}
	
	public DoubleS createFA(_Node tree, String DFAName)
	{
		return null;
	}
	
	public Boolean createGrammar()
	{
		do
		{
			String DFAName = this.getDFAName();
			System.out.println(DFAName);
	
			_Node tree = this.createTree();
			
			DoubleS ds = this.createFA(tree, DFAName);
			
			System.out.println();
		}while(this.nextLine());
		
		return true;
	}
	
	public static void main(String[] args)
	{
		File file = new File("grammar/Grammar");
		Pgen pgen = new Pgen(file);
		pgen.createGrammar();
	}
}
