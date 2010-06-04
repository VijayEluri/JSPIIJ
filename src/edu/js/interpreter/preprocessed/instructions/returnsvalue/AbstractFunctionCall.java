package edu.js.interpreter.preprocessed.instructions.returnsvalue;

import edu.js.interpreter.pascaltypes.PascalType;
import edu.js.interpreter.preprocessed.AbstractFunction;
import edu.js.interpreter.preprocessed.FunctionDeclaration;
import edu.js.interpreter.preprocessed.instructions.Executable;
import edu.js.interpreter.preprocessed.interpretingobjects.FunctionOnStack;
import edu.js.interpreter.preprocessed.interpretingobjects.variables.ContainsVariables;

public class AbstractFunctionCall implements ReturnsValue, Executable {
	AbstractFunction function;

	ReturnsValue[] arguments;

	public AbstractFunctionCall(AbstractFunction function,
			ReturnsValue[] arguments) {
		this.function = function;
		if (function == null) {
			System.err.println("Warning: Null function call");
		}
		this.arguments = arguments;
	}

	public Object get_value(FunctionOnStack f) {
		PascalType[] arg_types = new PascalType[arguments.length];
		Object[] values = new Object[arguments.length];
		for (int i = 0; i < values.length; i++) {
			values[i] = arguments[i].get_value(f);
		}
		Object result = function.call(f.program, values);
		return result;
	}

	@Override
	public String toString() {
		return "call function [" + function + "] with args [" + arguments + ']';
	}

	public boolean execute(FunctionOnStack f) {
		get_value(f);
		return false;
	}

	public PascalType get_type(FunctionDeclaration f) {
		PascalType[] arg_types = new PascalType[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			arg_types[i] = arguments[i].get_type(f);
		}
		return function.return_type();
	}
}
