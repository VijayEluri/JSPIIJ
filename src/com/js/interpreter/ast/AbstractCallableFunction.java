package com.js.interpreter.ast;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.returnsvalue.FunctionCall;
import com.js.interpreter.ast.returnsvalue.ReturnsValue;
import com.js.interpreter.ast.returnsvalue.SimpleFunctionCall;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.linenumber.LineInfo;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.RuntimePascalException;

public abstract class AbstractCallableFunction extends AbstractFunction {

	/**
	 * This invokes a function call of any type.
	 * 
	 * @param parentcontext
	 *            The program context.
	 * @param arguments
	 * @return The return value of the called function.
	 * @throws RuntimePascalException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public abstract Object call(VariableContext parentcontext,
			RuntimeExecutable<?> main, Object[] arguments)
			throws RuntimePascalException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException;

	@Override
	public FunctionCall generatePerfectFitCall(LineInfo line,
			List<ReturnsValue> values, ExpressionContext f)
			throws ParsingException {
		ReturnsValue[] args = perfectMatch(values, f);
		if (args == null) {
			return null;
		}
		return new SimpleFunctionCall(this, args, line);
	}

	@Override
	public FunctionCall generateCall(LineInfo line, List<ReturnsValue> values,
			ExpressionContext f) throws ParsingException {
		ReturnsValue[] args = format_args(values, f);
		if (args == null) {
			return null;
		}
		return new SimpleFunctionCall(this, args, line);
	}


}
