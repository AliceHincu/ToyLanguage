package model.Expressions;

import exceptions.InterpreterException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.Values.Value;

public interface Expression {
    Value eval(MyDictionaryInterface<String, Value> SymbolTable, MyHeapInterface<Value> Heap) throws InterpreterException;
    public Expression deepCopy();
}
