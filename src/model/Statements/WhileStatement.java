package model.Statements;

import exceptions.InterpreterException;
import exceptions.StatementException;
import model.ADTs.MyStackInterface;
import model.Expressions.Expression;
import model.ProgramState;
import model.Types.BoolType;
import model.Values.BoolValue;
import model.Values.Value;

public class WhileStatement implements StatementInterface{
    private final Expression expression;
    private final StatementInterface statement;

    public WhileStatement(Expression expression, StatementInterface statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyStackInterface<StatementInterface> stack = state.getExeStack();

        Value valExpr = this.expression.eval(state.getSymbolTable(), state.getHeap());
        if (!valExpr.getType().equals(new BoolType()))
            throw new StatementException("Expression not of type bool");

        if (valExpr.equals(new BoolValue(true))) {
            // daca e true dai push la while mai intai, si dupa la statement ca el sa fie primul ca sa il executi.
            stack.push(new WhileStatement(this.expression, this.statement));
            stack.push(this.statement);
        }

        state.setExeStack(stack);
        return state;
    }

    @Override
    public String toString() {
        return "while(" + this.expression.toString() + ") { " + this.statement.toString() + " }";
    }

    @Override
    public StatementInterface deepCopy() {
        return new WhileStatement(this.expression, this.statement);
    }
}
