package pers.xia.jpython.object;

import java.util.ArrayList;

public class PyList extends PyObject
{
    ArrayList<PyObject> list;
    public PyList(int n)
    {
        list = new ArrayList<PyObject>(n);
    }

	public boolean append(PyObject newitem) {
		// TODO Auto-generated method stub
	    list.add(newitem);
		return true;
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public void setSlice(long l, long size, PyObject object) {
		// TODO Auto-generated method stub
		
	}

	public PyObject getItem(int l) {
		// TODO Auto-generated method stub
		return list.get(l);
	}
}
