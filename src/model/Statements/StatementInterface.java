package model.Statements;

import exceptions.InterpreterException;
import model.ProgramState;

public interface StatementInterface {
    ProgramState execute(ProgramState state) throws InterpreterException;
    // which is the execution method for a statement
    StatementInterface deepCopy();
}
