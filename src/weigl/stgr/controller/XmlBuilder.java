package weigl.stgr.controller;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import weigl.stgr.exception.BuildException;
import weigl.stgr.model.StgrCall;
import weigl.stgr.model.StgrCase;
import weigl.stgr.model.StgrCommand;
import weigl.stgr.model.StgrCommandBlock;
import weigl.stgr.model.StgrIfElse;
import weigl.stgr.model.StgrModel;
import weigl.stgr.model.StgrRepeat;
import weigl.stgr.model.StgrSwitch;
import weigl.stgr.model.StgrWhile;

public final class XmlBuilder extends BuilderImpl {
    public static Builder INSTANCE = new XmlBuilder();

    static {
	BuilderUtil.registerBuilder(new XmlBuilder());
    }

    public StgrModel parse(final String content) throws BuildException {
	try {
	    final SAXBuilder sax = new SAXBuilder();
	    final Document m_doc = sax.build(new StringReader(content));
	    final Element root = m_doc.getRootElement();
	    StgrModel product = new StgrModel();
	    product.setCreateDate(new Date());
	    product.setAuthor(root.getChildTextTrim("author"));

	    // Parse commandblock
	    final StgrCommandBlock cmdbloc = new StgrCommandBlock();
	    parseCmd(cmdbloc, root.getChild("bloc"));
	    product.setRootBloc(cmdbloc);
	    return product;
	} catch (final JDOMException e) {
	    final BuildException ex = new BuildException(0, e.getMessage());
	    throw ex;
	} catch (final IOException e) {
	    throw new BuildException(0, e.getLocalizedMessage());
	}
    }

    @SuppressWarnings("unchecked")
    private void parseCmd(final StgrCommandBlock parent, final Element e) {
	final Iterator<Element> iter = e.getChildren().iterator();

	while (iter.hasNext()) {
	    final Element current = iter.next();
	    if (current.getName().equalsIgnoreCase("do")) {
		parent.append(new StgrCommand(current
			.getAttributeValue("label")));
	    } else if (current.getName().equalsIgnoreCase("if")) {
		final StgrIfElse cond = new StgrIfElse(current
			.getAttributeValue("label"));
		parseCmd(cond.getCommands(), current);
		if (current.getChild("else") != null) {
		    cond.setCurrentBloc(CommandType.ELSE);
		    parseCmd(cond.getCommands(), current.getChild("else"));
		}
		parent.append(cond);
	    } else if (current.getName().equalsIgnoreCase("repeat")) {
		final StgrRepeat r = new StgrRepeat(current
			.getAttributeValue("label"));
		parseCmd(r.getCommands(), current);
		parent.append(r);
	    } else if (current.getName().equalsIgnoreCase("while")) {
		final StgrWhile w = new StgrWhile(current
			.getAttributeValue("label"));
		parseCmd(w.getCommands(), current);
		parent.append(w);
	    } else if (current.getName().equalsIgnoreCase("switch")) {
		final StgrSwitch w = new StgrSwitch(current
			.getAttributeValue("label"));
		parseCmd(w.getCommands(), current);
		parent.append(w);
	    } else if (current.getName().equalsIgnoreCase("while")) {
		final StgrCase w = new StgrCase(current
			.getAttributeValue("label"));
		parseCmd(w.getCommands(), current);
		parent.append(w);
	    } else if (current.getName().equalsIgnoreCase("call")) {
		final StgrCall call = new StgrCall(current
			.getAttributeValue("label"));
		parent.append(call);
	    }
	}
    }

    public boolean isSuitableFor(final File file) {
	return file.getName().toLowerCase().endsWith(".xml");
    }
}
