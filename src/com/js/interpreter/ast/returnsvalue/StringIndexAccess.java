package com.js.interpreter.ast.returnsvalue;

import com.js.interpreter.ast.CompileTimeContext;
import com.js.interpreter.ast.ExpressionContext;
import com.js.interpreter.ast.instructions.SetCharAt;
import com.js.interpreter.ast.instructions.SetValueExecutable;
import com.js.interpreter.ast.returnsvalue.boxing.StringBoxer;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.exceptions.UnassignableTypeException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.pascaltypes.JavaClassBasedType;
import com.js.interpreter.pascaltypes.RuntimeType;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.RuntimePascalException;

public class StringIndexAccess extends DebuggableReturnsValue {
	private ReturnsValue container;
	private ReturnsValue index;

	public StringIndexAccess(ReturnsValue container, ReturnsValue index) {
		this.container = container;
		this.index = index;
	}

	@Override
	public RuntimeType get_type(ExpressionContext f) throws ParsingException {
		return new RuntimeType(JavaClassBasedType.Character,
				container.get_type(f).writable);
	}

	@Override
	public LineInfo getLineNumber() {
		return container.getLineNumber();
	}

	@Override
	public Object compileTimeValue(CompileTimeContext context)
			throws ParsingException {
		StringBuilder c = (StringBuilder) container.compileTimeValue(context);
		Integer i = (Integer) index.compileTimeValue(context);
		if (c != null && i != null) {
			return c.charAt(i - 1);
		} else {
			return null;
		}
	}

	@Override
	public SetValueExecutable createSetValueInstruction(ReturnsValue r)
			throws UnassignableTypeException {
		return new SetCharAt(container, index, r);
	}

	@Override
	public Object getValueImpl(VariableContext f, RuntimeExecutable<?> main)
			throws RuntimePascalException {
		StringBuilder c = (StringBuilder) container.getValue(f, main);
		Integer i = (Integer) index.getValue(f, main);
		return c.charAt(i - 1);
	}

	@Override
	public ReturnsValue compileTimeExpressionFold(CompileTimeContext context)
			throws ParsingException {
		Object val = this.compileTimeValue(context);
		if (val != null) {
			return new ConstantAccess(val, container.getLineNumber());
		} else {
			return new StringIndexAccess(
					container.compileTimeExpressionFold(context),
					index.compileTimeExpressionFold(context));
		}
	}

}
