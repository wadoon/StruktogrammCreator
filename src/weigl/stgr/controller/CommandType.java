package weigl.stgr.controller;

public enum CommandType {
    IF, REPEAT, WHILE, DO, ELSE, END, NOTHING, CASE, SWITCH, ESAC, NON, CALL, MODEL;

    public static CommandType getType(String line) {
	if (line == null)
	    return NOTHING;
	String l = line.trim();

	int i = l.indexOf(' ');
	if (i > -1)
	    l = l.substring(0, l.indexOf(' '));

	CommandType[] array = values();
	for (int j = 0; j < array.length; j++) {
	    if (array[j].toString().equalsIgnoreCase(l))
		return array[j];
	}
	return NON;
//	return null;
    }
}
