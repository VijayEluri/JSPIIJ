package com.js.interpreter.tokens.basic;

import com.js.interpreter.linenumber.LineInfo;

public class OfToken extends BasicToken {

    public OfToken(LineInfo line) {
        super(line);
    }

    @Override
    public String toString() {
        return "of";
    }
}
