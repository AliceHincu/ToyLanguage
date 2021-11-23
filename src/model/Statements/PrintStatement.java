package model.Statements;

import exceptions.InterpreterException;
import model.ADTs.MyListInterface;
import model.Expressions.Expression;
import model.ProgramState;
import model.Values.Value;

public class PrintStatement implements StatementInterface{
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString(){
        return "print(" + this.expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyListInterface<Value> out1 = state.getOut();
        out1.add(expression.eval(state.getSymbolTable(), state.getHeap()));
        state.setOut(out1);
        return state;
    }

    @Override
    public StatementInterface deepCopy() {
        return new PrintStatement(this.expression);
    }
}
