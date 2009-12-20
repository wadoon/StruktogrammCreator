package weigl.stgr.controller;

import java.io.File;
import java.util.Stack;

import weigl.stgr.exception.BuildException;
import weigl.stgr.model.*;

public final class StgrBuilder extends BuilderImpl {
	public static final Builder INSTANCE = new StgrBuilder();

	static {
		BuilderUtil.registerBuilder(new StgrBuilder());
	}

	public boolean isSuitableFor(File file) {
		return file.getName().toLowerCase().endsWith(".stgr");
	}

	public StgrModel parse(final String content) throws BuildException {
		int line_num = 0;
		StgrModel product = new StgrModel();
		StringIterator iter = new StringIterator(content);

		Stack<ICommand> stack = new Stack<ICommand>();
		StgrCommandBlock root = new StgrCommandBlock();
		stack.push(root);

		while (iter.hasNext()) {
			String line = iter.next();
			if (line == null)
				continue;
			line = line.trim();
			++line_num;
			switch (CommandType.getType(line)) {
			case DO:
				StgrCommand c;
				if (line.startsWith("DO"))
					c = new StgrCommand(getLabelFromLine(line));
				else
					c = new StgrCommand(line);
				stack.peek().append(c);
				break;
			case IF:
				StgrIfElse c1 = new StgrIfElse(getLabelFromLine(line));
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

				if (ico == null)
					throw new BuildException("Das ELSE in Zeile " + line_num
							+ "hat keinen IF als Vorgänger.");

				StgrIfElse c3 = (StgrIfElse) ico;
				c3.setCurrentBloc(CommandType.ELSE);
				stack.push(c3);
				break;
			case REPEAT:
				StgrRepeat r = new StgrRepeat(getLabelFromLine(line));
				stack.peek().append(r);
				stack.push(r);
				break;
			case WHILE:
				StgrWhile w = new StgrWhile(getLabelFromLine(line));
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
				break;
			case CASE:
				StgrCase ca = new StgrCase(getLabelFromLine(line));
				try {
					swtch = (StgrSwitch) stack.peek();
					swtch.append(ca);
					stack.push(ca);
				} catch (ClassCastException e) {
					BuildException ex = new BuildException(
							"Ein CASE wird nicht korrekt verwendet."
									+ "In Zeile " + line_num + " \n");
					throw ex;
				}
				break;
			}
		}
		product.setRootBloc(root);
		return product;
	}

	private static String getLabelFromLine(String line) {
		return line.substring(line.indexOf(' ')).trim();
	}
}
