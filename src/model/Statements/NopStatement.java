package model.Statements;

import model.ProgramState;

public class NopStatement implements StatementInterface{
    public NopStatement() {}

    @Override
    public ProgramState execute(ProgramState state) {
        return state;
    }

    @Override
    public StatementInterface deepCopy() {
        return new NopStatement();
    }

    @Override
    public String toString() {
        return "\n";
    }
}
