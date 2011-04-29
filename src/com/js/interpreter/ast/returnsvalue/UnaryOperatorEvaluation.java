package com.js.interpreter.ast.returnsvalue;

import javax.naming.OperationNotSupportedException;

import com.js.interpreter.ast.CompileTimeContext;
import com.js.interpreter.ast.ExpressionContext;
import com.js.interpreter.ast.instructions.SetValueExecutable;
import com.js.interpreter.exceptions.ConstantCalculationException;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.exceptions.UnassignableTypeException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.pascaltypes.RuntimeType;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.PascalArithmeticException;
import com.js.interpreter.runtime.exception.RuntimePascalException;
import com.js.interpreter.tokens.OperatorTypes;

public class UnaryOperatorEvaluation extends DebuggableReturnsValue {
	public OperatorTypes type;

	public ReturnsValue operon;
	LineInfo line;

	public UnaryOperatorEvaluation(ReturnsValue operon, OperatorTypes operator,
			LineInfo line) {
		this.type = operator;
		this.line = line;
		this.operon = operon;
	}

	@Override
	public LineInfo getLineNumber() {
		return line;
	}

	@Override
	public Object getValueImpl(VariableContext f, RuntimeExecutable<?> main)
			throws RuntimePascalException {
		Object value = operon.getValue(f, main);
		return operate(value);
	}

	public Object operate(Object value) throws PascalArithmeticException {
		try {
			return type.operate(value);
		} catch (OperationNotSupportedException e) {
			throw new RuntimeException(e);
		} catch (ArithmeticException e) {
			throw new PascalArithmeticException(line, e);
		}
	}

	@Override
	public String toString() {
		return "operator [" + type + "] on [" + operon + ']';
	}

	@Override
	public RuntimeType get_type(ExpressionContext f) throws ParsingException {
		return operon.get_type(f);
	}

	@Override
	public Object compileTimeValue(CompileTimeContext context)
			throws ParsingException {
		Object value = operon.compileTimeValue(context);
		if (value == null) {
			return null;
		}
		try {
			return operate(value);
		} catch (PascalArithmeticException e) {
			throw new ConstantCalculationException(e);
		}
	}

	@Override
	public SetValueExecutable createSetValueInstruction(ReturnsValue r)
			throws UnassignableTypeException {
		throw new UnassignableTypeException(this);
	}

	@Override
	public ReturnsValue compileTimeExpressionFold(CompileTimeContext context)
			throws ParsingException {
		Object val = this.compileTimeValue(context);
		if (val != null) {
			return new ConstantAccess(val, line);
		} else {
			return new UnaryOperatorEvaluation(
					operon.compileTimeExpressionFold(context), type, line);
		}
	}
}
