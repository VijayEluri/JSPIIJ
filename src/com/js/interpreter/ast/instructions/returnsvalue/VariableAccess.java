package com.js.interpreter.ast.instructions.returnsvalue;

import com.js.interpreter.ast.CompileTimeContext;
import com.js.interpreter.ast.ExpressionContext;
import com.js.interpreter.ast.instructions.SetValueExecutable;
import com.js.interpreter.ast.instructions.VariableSet;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.exceptions.UnassignableTypeException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.pascaltypes.RuntimeType;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.RuntimePascalException;
import com.js.interpreter.tokens.WordToken;

public class VariableAccess extends DebuggableReturnsValue {
	public String name;
	LineInfo line;

	public VariableAccess(WordToken t) {
		this.name = t.name;
		this.line = t.lineInfo;
	}

	public VariableAccess(String name, LineInfo line) {
		this.name = name;
		this.line = line;
	}

	@Override
	public LineInfo getLineNumber() {
		return line;
	}

	@Override
	public Object getValueImpl(VariableContext f, RuntimeExecutable<?> main)
			throws RuntimePascalException {
		return f.get_var(name);
	}

	@Override
	public String toString() {
		return name.toString();
	}

	@Override
	public RuntimeType get_type(ExpressionContext f) throws ParsingException {
		return new RuntimeType(f.getVariableDefinition(name).type, true);
	}

	@Override
	public Object compileTimeValue(CompileTimeContext context)
			throws ParsingException {
		return null;
	}

	@Override
	public SetValueExecutable createSetValueInstruction(ReturnsValue r)
			throws UnassignableTypeException {
		return new VariableSet(name, r, line);
	}
}
