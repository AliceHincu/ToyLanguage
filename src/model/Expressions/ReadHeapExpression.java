package model.Expressions;

import exceptions.InterpreterException;
import exceptions.StatementException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.Values.RefValue;
import model.Values.Value;

public class ReadHeapExpression implements Expression{
    private final Expression expression;

    public ReadHeapExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(MyDictionaryInterface<String, Value> SymbolTable, MyHeapInterface<Value> Heap) throws InterpreterException {
        Value val = expression.eval(SymbolTable, Heap);
        if (!(val instanceof RefValue))
            throw new StatementException("Expression not of reference type!");

        RefValue refVal = (RefValue) val;
        int address = refVal.getAddress();
        if (!Heap.exists(address))
            throw new StatementException("Not allocated on heap");

        return Heap.get(address);
    }

    @Override
    public String toString() {
        return "readHeap(" + expression.toString() + ")";
    }

    @Override
    public Expression deepCopy() {
        return new ReadHeapExpression(this.expression);
    }
}
