package edu.js.interpreter.preprocessed.instructions.returns_value;

import edu.js.interpreter.pascal_types.pascal_type;
import edu.js.interpreter.preprocessed.abstract_function;
import edu.js.interpreter.preprocessed.dummy_declaration;
import edu.js.interpreter.preprocessed.function_declaration;
import edu.js.interpreter.preprocessed.instructions.executable;
import edu.js.interpreter.preprocessed.interpreting_objects.arraypointer;
import edu.js.interpreter.preprocessed.interpreting_objects.containsvariablespointer;
import edu.js.interpreter.preprocessed.interpreting_objects.function_on_stack;
import edu.js.interpreter.preprocessed.interpreting_objects.variables.contains_variables;
import edu.js.interpreter.preprocessed.interpreting_objects.variables.subvar_identifier;
import edu.js.interpreter.preprocessed.interpreting_objects.variables.variable_identifier;
import edu.js.interpreter.processing.pascal_program;

public class abstract_function_call implements returns_value, executable {
	String name;

	returns_value[] arguments;

	public abstract_function_call(String name, returns_value[] arguments) {
		this.name = name;
		this.arguments = arguments;
	}

	public Object get_value(function_on_stack f) {
		pascal_type[] arg_types = new pascal_type[arguments.length];
		Object[] values = new Object[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			arg_types[i] = arguments[i].get_type(f.program, f.prototype);
			if (arg_types[i] == null || arg_types[i].toclass() == null) {
				System.out.println(arguments[i]);
				arguments[i].get_type(f.program, f.prototype);
			}
		}
		dummy_declaration header = new dummy_declaration(name, arg_types);
		abstract_function called_function = f.program.callable_functions
				.get(header);
		if (called_function == null) {
			System.err.println("Could not find called function");
			System.exit(0);
		}
		for (int i = 0; i < values.length; i++) {
			if (called_function.is_varargs(i)) {
				if (!(arguments[i] instanceof variable_access)) {
					System.err
							.println("Attempted to pass non-variable as variable argument");
					System.exit(0);
				}
				variable_identifier a = (variable_identifier) arguments[i];
				Object var_holder = f.get_variable_holder(a);
				subvar_identifier identifier = a.get(a.size() - 1);
				if (identifier.isstring()) {
					values[i] = new containsvariablespointer(
							(contains_variables) var_holder, identifier
									.string());
				} else {
					values[i] = new arraypointer(var_holder,
							((Number) identifier.returnsvalue().get_value(f))
									.intValue());
				}
			} else {
				values[i] = arguments[i].get_value(f);
			}
		}
		Object result = called_function.call(f.program, values);
		return result;
	}

	public String toString() {
		return "call function [" + name + "] with args [" + arguments + ']';
	}

	public boolean execute(function_on_stack f) {
		get_value(f);
		return false;
	}

	public pascal_type get_type(pascal_program p, function_declaration f) {
		pascal_type[] arg_types = new pascal_type[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			arg_types[i] = arguments[i].get_type(p, f);
		}
		dummy_declaration header = new dummy_declaration(name, arg_types);
		return p.callable_functions.get(header).get_return_type();
	}
}