package com.js.interpreter.tokens;

import com.js.interpreter.linenumber.LineInfo;

public class OperatorToken extends Token {
	public OperatorTypes type;

	public OperatorToken(LineInfo line, OperatorTypes t) {
		super(line);
		this.type = t;
	}

	public boolean can_be_unary() {
		switch (type) {
		case MINUS:
		case NOT:
		case PLUS:
			return true;
		default:
			return false;
		}
	}

	@Override
	public String toString() {
		return type.toString();
	}
}
