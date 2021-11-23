package model.Statements;

import exceptions.FileException;
import exceptions.InterpreterException;
import exceptions.StatementException;
import model.ADTs.MyDictionaryInterface;
import model.Expressions.Expression;
import model.ProgramState;
import model.Types.StringType;
import model.Values.StringValue;
import model.Values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CloseReadFileStatement implements StatementInterface{
    private final Expression expression;

    public CloseReadFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString(){
        return "closeReadFile(" + this.expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyDictionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();
        Value val = expression.eval(symbolTable, state.getHeap());
        if (val.getType().equals(new StringType())){
            StringValue stringVal = (StringValue) val; // down casting
            if(!fileTable.isDefined(stringVal))
                throw new StatementException("File does not exist");
            else {
                try {
                    BufferedReader reader = fileTable.lookup(stringVal);
                    reader.close();
                    fileTable.remove(stringVal);
                } catch (IOException exception) {
                    throw new FileException("File cannot be close " + exception.getMessage());
                }
            }
        } else
            throw new StatementException("CloseReadFile expression is not a string");

        state.setFileTable(fileTable);
        return state;
    }

    @Override
    public StatementInterface deepCopy() {
        return new CloseReadFileStatement(this.expression);
    }
}
