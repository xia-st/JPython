package pers.xia.jpython.grammar.pgen;

import pers.xia.jpython.object.PyExceptions;

class _Grammar
{
	static final int DFAMAXSIZE = 256;
	static final int LABELMAXSIZE = 512;
	
	int ndfas;
	_DFA[] dfas;
	
	int nlabels;
	_Label[] labels;
	
	_DFA start;
	
	public _Grammar()
	{
		this.ndfas = 0;
		this.dfas = new _DFA[_Grammar.DFAMAXSIZE];
		this.nlabels = 0;
		this.labels = new _Label[_Grammar.LABELMAXSIZE];
	}
	
	public _Grammar(int ndfas, _DFA[] dfas, int nlabels, _Label[] labels, _DFA start)
	{
		this.ndfas = ndfas;
		this.dfas = dfas;
		this.nlabels = nlabels;
		this.labels = labels;
		this.start = start;
	}
	
	public Boolean addLabel(_Label label)
	{
		if(nlabels >= this.labels.length)
		{
			throw new PyExceptions("Over the max size of label");
		}
		this.labels[this.nlabels++] = label;
		return true;
	}
	
	public _Label setLabel(_Label label)
	{
		int i = 0;
		for(; i < this.nlabels; i++)
		{
			if(this.labels[i].cmp(label))
			{
				break;
			}
		}
		
		if(i >= this.nlabels)
		{
			this.addLabel(label);
			return label;
		}
		else
		{
			return this.labels[i];
		}
	}
	
}
