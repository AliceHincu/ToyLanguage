package model.Statements;

import exceptions.InterpreterException;
import exceptions.StatementException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.Expressions.Expression;
import model.ProgramState;
import model.Types.RefType;
import model.Values.RefValue;
import model.Values.Value;

public class HeapAllocationStatement implements StatementInterface{
    String varName;
    Expression expression;

    public HeapAllocationStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "new(" + this.varName + ',' + this.expression.toString() + ')';
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyHeapInterface<Value> heap = state.getHeap();

        if (!symbolTable.isDefined(this.varName))
            throw new StatementException("Variable " + this.varName + " is not defined in symbolTable!");
        Value tableVal = symbolTable.lookup(this.varName);
        if (!(tableVal.getType() instanceof RefType))
            throw new StatementException("The value from SymbolTable doesn't have the type RefType!");

        Value newValue = this.expression.eval(symbolTable, heap);
        // daca tableVal este RefValue (0, int) => tableVal.getType() va fi un type => casting => RefType(locationType=int) => get.inner = int
        if(!(newValue.getType().equals(((RefType) tableVal.getType()).getInner())))
            throw new StatementException("New expression's value type is not the same as the variable");

        int addr = heap.allocate(newValue);
        symbolTable.update(this.varName, new RefValue(addr, newValue.getType()));

        state.setSymbolTable(symbolTable);
        state.setHeap(heap);
        return state;
    }

    @Override
    public StatementInterface deepCopy() {
        return new HeapAllocationStatement(this.varName, this.expression);
    }
}
