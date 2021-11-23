package repository;

import exceptions.InterpreterException;
import model.ProgramState;

public interface RepositoryInterface {
    ProgramState getCurrentProgram();
    void addProgram(ProgramState state);
    void logProgramStateExecution(ProgramState state) throws InterpreterException;
}
