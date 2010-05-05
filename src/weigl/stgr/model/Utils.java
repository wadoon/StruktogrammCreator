package weigl.stgr.model;

public class Utils {

    public static void addIndent(StringBuilder str, int indent) {
	for (int i = 0; i < indent; i++)
	    str.append('\t');
    }

}
