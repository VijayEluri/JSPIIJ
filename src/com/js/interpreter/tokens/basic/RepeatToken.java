package com.js.interpreter.tokens.basic;

import com.js.interpreter.linenumber.LineInfo;

public class RepeatToken extends BasicToken {

    public RepeatToken(LineInfo line) {
        super(line);
    }

    @Override
    public String toString() {
        return "repeat";
    }
}
