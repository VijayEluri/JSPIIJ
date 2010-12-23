package com.js.interpreter.runtime.variables;

import java.lang.reflect.Array;

import com.js.interpreter.ast.instructions.returnsvalue.ReturnsValue;
import com.js.interpreter.pascaltypes.DeclaredType;
import com.js.interpreter.runtime.ArrayPointer;
import com.js.interpreter.runtime.VariableBoxer;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.RuntimePascalException;

public class ReturnsValue_SubvarIdentifier implements SubvarIdentifier {
	ReturnsValue value;

	public ReturnsValue_SubvarIdentifier(ReturnsValue next_returns_value) {
		value = next_returns_value;
	}

	@Override
	public String toString() {
		return '[' + value.toString() + ']';
	}

	@Override
	public Object get(Object container, VariableContext f,
			RuntimeExecutable<?> main) throws RuntimePascalException {
		int index = -1;
		Object indexvalue = value.get_value(f, main);
		if(!(indexvalue instanceof Number)) {
			throw new 
		}
		index = ((Number) indexvalue).intValue();

		if (container instanceof StringBuilder) {
			return ((StringBuilder) container).charAt(index);
		} else {
			return Array.get(container, index);
		}

	}

	@Override
	public VariableBoxer create_pointer(Object container,
			VariableContext context, RuntimeExecutable<?> main)
			throws RuntimePascalException {
		return new ArrayPointer(container, ((Number) value.get_value(context,
				main)).intValue());
	}

	@Override
	public DeclaredType getType(DeclaredType containerType) {
		return (containerType.get_type_array()).element_type;
	}

	@Override
	public void set(Object container, VariableContext context,
			RuntimeExecutable<?> main, Object input)
			throws RuntimePascalException {
		int index = ((Number) value.get_value(context, main)).intValue();
		if (container instanceof StringBuilder) {
			((StringBuilder) container).setCharAt(index, (Character) input);
		} else {
			Array.set(container, index, input);
		}
	}

}
