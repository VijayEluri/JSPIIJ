package com.js.interpreter.pascaltypes;

import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.returnsvalue.ReturnsValue;
import com.js.interpreter.ast.returnsvalue.boxing.ArrayBoxer;
import com.js.interpreter.exceptions.ParsingException;
import com.js.interpreter.linenumber.LineInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VarargsType implements ArgumentType {
    RuntimeType elementType;

    public VarargsType(RuntimeType elementType) {
        this.elementType = elementType;
    }

    @Override
    public ReturnsValue convertArgType(Iterator<ReturnsValue> args,
                                       ExpressionContext f) throws ParsingException {
        List<ReturnsValue> convertedargs = new ArrayList<ReturnsValue>();
        LineInfo line = null;
        while (args.hasNext()) {
            ReturnsValue tmp = elementType.convert(args.next(), f);
            if (tmp == null) {
                return null;
            }
            line = tmp.getLineNumber();
            convertedargs.add(tmp);
        }
        return new ArrayBoxer(
                convertedargs.toArray(new ReturnsValue[convertedargs.size()]),
                elementType, line);
    }

    @Override
    public Class getRuntimeClass() {
        return elementType.getClass();
    }

    @Override
    public ReturnsValue perfectFit(Iterator<ReturnsValue> types,
                                   ExpressionContext e) throws ParsingException {
        LineInfo line = null;
        List<ReturnsValue> converted = new ArrayList<ReturnsValue>();
        while (types.hasNext()) {
            ReturnsValue fit = elementType.perfectFit(types, e);
            if (fit == null) {
                return null;
            }
            if (line == null) {
                line = fit.getLineNumber();
            }
            converted.add(fit);
        }
        ReturnsValue[] convert = converted.toArray(new ReturnsValue[converted
                .size()]);
        return new ArrayBoxer(convert, elementType, line);
    }
}
