package weigl.stgr.model.gui;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import weigl.stgr.model.ICommand;
import weigl.stgr.model.StgrModel;

public class StgrModelJTreeAdapter implements TreeModel {
    private StgrModel model;
    private List<TreeModelListener> eventlistener = new LinkedList<TreeModelListener>();

    public StgrModelJTreeAdapter(StgrModel model) {
	this.model = model;
    }

    @Override
    public void addTreeModelListener(TreeModelListener arg0) {
	synchronized (eventlistener) {
	    eventlistener.add(arg0);
	}
    }

    @Override
    public Object getChild(Object parent, int child) {
	return ((NodeWrapper) parent).getChild(child);
    }

    @Override
    public int getChildCount(Object arg0) {
	return ((NodeWrapper) arg0).childCount();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
	return ((NodeWrapper) parent).indexOf(child);
    }

    @Override
    public Object getRoot() {
	return wrap(model.getCode());
    }

    @Override
    public boolean isLeaf(Object parent) {
	return ((NodeWrapper) parent).childCount() == 0;
    }

    @Override
    public void removeTreeModelListener(TreeModelListener arg0) {
	synchronized (eventlistener) {
	    eventlistener.remove(arg0);
	}
    }

    @Override
    public void valueForPathChanged(TreePath arg0, Object arg1) {
	System.err.println("Can not be called");
    }

    public static NodeWrapper wrap(ICommand command) {
	Class<? extends ICommand> c = command.getClass();
	JTreeWrapper wrapper = c.getAnnotation(JTreeWrapper.class);
	try {
	    Constructor<? extends NodeWrapper> con = wrapper.value()
		    .getConstructor(c);

	    return con.newInstance(command);
	} catch (SecurityException e) {
	    e.printStackTrace();
	} catch (NoSuchMethodException e) {
	    e.printStackTrace();
	} catch (IllegalArgumentException e) {
	    e.printStackTrace();
	} catch (InstantiationException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	}
	return new EmptyWrapper();
    }
}
