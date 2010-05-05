package weigl.stgr.controller;

import java.io.File;

import java.util.EmptyStackException;
import java.util.Stack;

import weigl.stgr.exception.BuildException;
import weigl.stgr.exception.BuildExceptions;
import weigl.stgr.model.ICommand;
import weigl.stgr.model.StgrCall;
import weigl.stgr.model.StgrCase;
import weigl.stgr.model.StgrCommand;
import weigl.stgr.model.StgrCommandBlock;
import weigl.stgr.model.StgrIfElse;
import weigl.stgr.model.StgrModel;
import weigl.stgr.model.StgrRepeat;
import weigl.stgr.model.StgrSwitch;
import weigl.stgr.model.StgrWhile;

public final class StgrBuilder extends BuilderImpl {
    public static final Builder INSTANCE = new StgrBuilder();

    static {
	BuilderUtil.registerBuilder(new StgrBuilder());
    }

    private int line_num;

    public boolean isSuitableFor(File file) {
	return file.getName().toLowerCase().endsWith(".stgr");
    }

    public StgrModel parse(final String content) throws BuildException,
	    BuildExceptions {
	line_num = 0;
	try {
	    return parse0(content);
	} catch (EmptyStackException e) {
	    throw new BuildException(line_num,
		    "Too many end or fi for openning commands");
	}
    }

    private StgrModel parse0(final String content) throws BuildExceptions {
	StgrModel product = new StgrModel();
	StringIterator iter = new StringIterator(content);

	Stack<ICommand> stack = new Stack<ICommand>();
	StgrCommandBlock root = new StgrCommandBlock();
	stack.push(root);

	BuildExceptions bx = new BuildExceptions();

	line_num++;

	while (iter.hasNext()) {
	    String line = iter.next();
	    if (line == null)
		continue;
	    Options o = getOptionsFromLine(line);
	    switch (CommandType.getType(line)) {
	    case MODEL:
		product.setLabel(getLabelFromLine(line));
		product.setOptions(o);
		break;
	    case NON:
		// bx.add(line_num, "Line does not contain a known command");
		line = "do " + line;
	    case DO:
		StgrCommand c;
		c = new StgrCommand(getLabelFromLine(line));
		c.setOptions(o);
		stack.peek().append(c);
		break;
	    case CALL:
		StgrCall call = new StgrCall(getLabelFromLine(line));
		call.setOptions(o);
		stack.peek().append(call);
		break;
	    case IF:
		StgrIfElse c1 = new StgrIfElse(getLabelFromLine(line));
		c1.setOptions(o);
		stack.peek().append(c1);
		stack.push(c1);
		break;
	    case ELSE:
		boolean b = false;
		ICommand ico;
		do {
		    ico = stack.pop();
		    b = ico.getClass().isAssignableFrom(StgrIfElse.class);

		} while (!b && stack.size() > 0);

		try {
		    StgrIfElse c3 = (StgrIfElse) ico;
		    c3.setCurrentBloc(CommandType.ELSE);
		    stack.push(c3);
		} catch (Exception e) {
		    bx.add(line_num, "ELSE has no preceeding IF");
		}
		break;
	    case REPEAT:
		StgrRepeat r = new StgrRepeat(getLabelFromLine(line));
		stack.peek().append(r);
		stack.push(r);
		r.setOptions(o);
		break;
	    case WHILE:
		StgrWhile w = new StgrWhile(getLabelFromLine(line));
		w.setOptions(o);
		stack.peek().append(w);
		stack.push(w);
		break;
	    case END:
		stack.pop();
		break;
	    case SWITCH:
		StgrSwitch swtch = new StgrSwitch(getLabelFromLine(line));
		stack.peek().append(swtch);
		stack.push(swtch);
		swtch.setOptions(o);
		break;
	    case CASE:
		StgrCase ca = new StgrCase(getLabelFromLine(line));
		ca.setOptions(o);
		try {
		    ICommand a = stack.peek();
		    if (a instanceof StgrCase) {
			stack.pop();
		    }
		    swtch = (StgrSwitch) stack.peek();
		    swtch.append(ca);
		    stack.push(ca);
		} catch (ClassCastException e) {
		    bx.add(line_num, "CASE  are not used correctly");
		}
		break;
	    case ESAC:
		if (stack.peek() instanceof StgrCase)
		    stack.pop();
		if (stack.peek() instanceof StgrSwitch)
		    stack.pop();
		break;
	    }
	}

	if (bx.fire())
	    throw bx;

	product.setRootBloc(root);
	return product;
    }

    private static String getLabelFromLine(String line) {
	try {
	    int first = line.indexOf("{");
	    first = first < 0 ? line.length() : first;
	    return line.substring(line.indexOf(' '), first);
	} catch (StringIndexOutOfBoundsException e) {
	    return "";
	}
    }

    private static Options getOptionsFromLine(String line) {
	int first = line.indexOf("{");
	int last = line.indexOf("}");
	if (first >= 0 && last >= 0 && first < last) {
	    Options o = new Options();
	    for (String field : line.substring(first + 1, last).split(","))
		o.set(field);
	    return o;
	}
	return null;
    }
}
