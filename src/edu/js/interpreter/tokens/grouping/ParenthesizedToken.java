package edu.js.interpreter.tokens.grouping;

import edu.js.interpreter.tokens.Token;

public class ParenthesizedToken extends GrouperToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3945938644412769985L;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("(");
		if (next != null) {
			builder.append(next).append(',');
		}
		for (Token t : this.queue) { 
			builder.append(t).append(' ');
		}
		builder.append(')');
		return builder.toString();
	}
}