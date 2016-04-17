package pers.xia.jpython.object;

import java.util.ArrayList;
import java.util.List;

public class PyList extends PySequence
{
    private ArrayList<PyObject> list;
    
    public PyList(int n)
    {
        this.list = new ArrayList<PyObject>(n);
    }
    
    public PyList(ArrayList<PyObject> list)
    {
        this.list = list;
    }

	public boolean append(PyObject newitem) 
	{
		// TODO Auto-generated method stub
	    list.add(newitem);
		return true;
	}
	
	public boolean extend(PyObject newitems)
	{
	    PyList items = (PyList)newitems;
	    for(int i = 0; i < items.getSize(); i++)
	    {
	        this.append(items.getItem(i));
	    }
	    return true;
	}

	public int getSize() 
	{
		// TODO Auto-generated method stub
		return list.size();
	}

	public void setSlice(int ilow, int ihigh, PyObject v) 
	{
		// F complete follow
	    if(v == null)
		{
		    ArrayList<PyObject> l = new ArrayList<PyObject>(this.list.subList(0, ilow));
		    ArrayList<PyObject> l2 = new ArrayList<PyObject>(this.list.subList(ihigh, this.getSize()));
		    this.list = l;
		    this.list.addAll(l2);
		}
	    else
	    {
    		PyList l = (PyList)v;
    		PyList l1 = new PyList(new ArrayList<PyObject>(this.list.subList(ihigh, this.getSize())));
    		this.list = (ArrayList<PyObject>) this.list.subList(0, ilow);
    		this.extend(l);
    		this.extend(l1);;
	    }
	}
	
	public PyList getSlice(int ilow, int ihigh)
	{
	    ArrayList<PyObject> newList = new ArrayList<PyObject>(this.list.subList(ilow, ihigh));
	    return new PyList(newList);
	}

	public PyObject getItem(int l) 
	{
		// TODO Auto-generated method stub
		return list.get(l);
	}
}
