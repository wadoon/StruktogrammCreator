package weigl.stgr.controller;

public enum CommandType {
	IF, REPEAT, WHILE, DO, ELSE, END, NOTHING, CASE, SWITCH;

	public static CommandType getType(String line) {
		if (line == null)
			return NOTHING;
		String l = line.trim();

		int i = l.indexOf(' ');
		if (i > -1)
			l = l.substring(0, l.indexOf(' '));

		if (l.equalsIgnoreCase("if")) {
			return IF;
		} else if (l.equalsIgnoreCase("else")) {
			return ELSE;
		} else if (l.equalsIgnoreCase("while")) {
			return WHILE;
		} else if (l.equalsIgnoreCase("repeat")) {
			return REPEAT;
		} else if (l.equalsIgnoreCase("end") || l.equalsIgnoreCase("fi")) {
			return END;
		} else if (l.equalsIgnoreCase("switch")) {
			return SWITCH;
		} else if (l.equalsIgnoreCase("case")) {
			return CASE;
		} else if (l.equalsIgnoreCase("do")) {
			return DO;
		} else if (l.equals("")) {
			return NOTHING;
		} else {
			return DO;
		}
	}
}
