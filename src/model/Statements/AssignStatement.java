package model.Statements;

import exceptions.InterpreterException;
import exceptions.StatementException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyStackInterface;
import model.Expressions.Expression;
import model.ProgramState;
import model.Types.Type;
import model.Values.Value;

public class AssignStatement implements StatementInterface{
    private final String id;
    private final Expression expression;

    public AssignStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();

        if (symbolTable.isDefined(id)) {
            Value val = expression.eval(symbolTable, state.getHeap());
            Type typeID = (symbolTable.lookup(id).getType());
            if (val.getType().equals(typeID)) {
                symbolTable.update(id, val);
            } else {
                throw new StatementException("declared type of variable" + id +
                        "and type of the assigned expression do not match");
            }
        }
        else {
            throw new StatementException("the used variable " + id + " was not declared before");
        }
        state.setSymbolTable(symbolTable);
        return state;
    }

    @Override
    public StatementInterface deepCopy() {
        return new AssignStatement(this.id, this.expression);
    }

    @Override
    public String toString(){
        return id + "=" + expression.toString();
    }
}
