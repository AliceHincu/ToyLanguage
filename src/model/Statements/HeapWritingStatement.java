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

public class HeapWritingStatement implements StatementInterface{
    private final String varName;
    private final Expression expression;

    public HeapWritingStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
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

        int refAddress = ((RefValue)tableVal).getAddress();
        if (!heap.exists(refAddress))
            throw new StatementException("Value does not exist on heap");

        Value valExpr = this.expression.eval(symbolTable, heap);
        if(!valExpr.getType().equals(heap.get(refAddress).getType()))
            throw new StatementException("Expression not of variable type");

        heap.put(refAddress, valExpr);
        state.setSymbolTable(symbolTable);
        state.setHeap(heap);
        return state;
    }

    @Override
    public String toString() {
        return "writeHeap(" + this.varName + ", " + this.expression.toString() + ")";
    }

    @Override
    public StatementInterface deepCopy() {
        return new HeapWritingStatement(this.varName, this.expression);
    }
}
