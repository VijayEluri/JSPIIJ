package edu.js.interpreter.bcel_test;

import edu.js.interpreter.preprocessed.interpreting_objects.variables.contains_variables;

public class point implements contains_variables {

	double x;

	int y;

	public point(double x2, int y2) {
		this.x = x2;
		this.y = y2;
	}

	public Object get_var(String name) {
		name = name.intern();
		if (name == "x") {
			return x;
		}
		if (name == "y") {
			return y;
		}
		return null;
	}

	public void set_var(String name, Object val) {
		if (name.equals("x")) {
			x = (Double) val;
		}
		if (name.equals("y")) {
			y = (Integer) val;
		}
	}

	@Override
	public contains_variables clone() {
		return new point(x, y);
	}

}