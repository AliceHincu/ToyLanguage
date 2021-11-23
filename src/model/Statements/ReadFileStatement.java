package model.Statements;

import exceptions.FileException;
import exceptions.InterpreterException;
import exceptions.StatementException;
import model.ADTs.MyDictionaryInterface;
import model.Expressions.Expression;
import model.ProgramState;
import model.Types.IntType;
import model.Types.StringType;
import model.Values.IntValue;
import model.Values.StringValue;
import model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements StatementInterface{
    private final Expression expression;
    private final String varName;

    public ReadFileStatement(Expression expression, String varName) {
        this.expression = expression;
        this.varName = varName;
    }

    @Override
    public String toString(){
        return "readFile(" + this.expression.toString() + ", " + this.varName + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyDictionaryInterface<String, Value> symbolTable = state.getSymbolTable();
        MyDictionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();

        if(!symbolTable.isDefined(this.varName))
            throw new FileException("varName " + this.varName + " is not defined in symbol table");
        Value val = symbolTable.lookup(this.varName);
        if (!val.getType().equals(new IntType()))
            throw new StatementException("ReadFile value is not an integer");

        Value valExpr = expression.eval(symbolTable, state.getHeap());
        if (!valExpr.getType().equals(new StringType()))
            throw new StatementException("ReadFile expression's value type is not a string");

        StringValue valString = (StringValue) valExpr;
        if (fileTable.isDefined(valString)) {
            try {
                BufferedReader reader = fileTable.lookup(valString);
                String line = reader.readLine();
                IntValue intVal;
                if (line == null)
                    intVal = new IntValue(0);
                else
                    intVal = new IntValue(Integer.parseInt(line));
                symbolTable.update(this.varName, intVal);
                state.setSymbolTable(symbolTable);
            } catch (IOException exception) {
                throw new FileException("Cannot read from file");
            }
        } else{
            throw new StatementException("ReadFile expression's string value is defined in file table");
        }

        return state;
    }

    @Override
    public StatementInterface deepCopy() {
        return new ReadFileStatement(this.expression, this.varName);
    }
}
