package model;

import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.ADTs.MyListInterface;
import model.ADTs.MyStackInterface;
import model.Statements.StatementInterface;
import model.Values.StringValue;
import model.Values.Value;

import java.io.BufferedReader;

public class ProgramState {
    private MyStackInterface<StatementInterface> exeStack;
    private MyDictionaryInterface<String, Value> symbolTable;
    private MyListInterface<Value> out;
    private MyDictionaryInterface<StringValue, BufferedReader> fileTable;
    private MyHeapInterface<Value> heap;
    StatementInterface originalProgram; // optional field, but good to have


    public ProgramState(MyStackInterface<StatementInterface> exeStack, MyDictionaryInterface<String, Value> symbolTable,
                        MyListInterface<Value> out) {
        this.exeStack = exeStack;
        this.symbolTable = symbolTable;
        this.out = out;
    }

    public ProgramState(MyStackInterface<StatementInterface> exeStack, MyDictionaryInterface<String, Value> symbolTable,
                        MyListInterface<Value> out, MyDictionaryInterface<StringValue, BufferedReader> fileTable,
                        MyHeapInterface<Value> heap, StatementInterface originalProgram) {
        this.exeStack = exeStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
        this.heap = heap;
        exeStack.push(this.originalProgram);
    }

    public MyStackInterface<StatementInterface> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyStackInterface<StatementInterface> exeStack) {
        this.exeStack = exeStack;
    }

    public MyDictionaryInterface<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(MyDictionaryInterface<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public MyListInterface<Value> getOut() {
        return out;
    }

    public void setOut(MyListInterface<Value> out) {
        this.out = out;
    }

    public StatementInterface getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(StatementInterface originalProgram) {
        this.originalProgram = originalProgram;
    }

    public MyDictionaryInterface<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyDictionaryInterface<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public MyHeapInterface<Value> getHeap() {
        return this.heap;
    }

    public void setHeap(MyHeapInterface<Value> heap) {
        this.heap = heap;
    }

    @Override
    public String toString() {
        //", originalProgram=" + originalProgram +

        return ">>> ProgramState: " + "\n----------\n" +
                "* exeStack: \n" + exeStack.toString() + "\n----------\n" +
                "* symbolTable: \n" + symbolTable + "\n----------\n" +
                "* out: " + out + "\n----------\n" +
                "* fileTable=" + fileTable.toString() + "\n----------\n" +
                "* heap: " + heap.toString() + "\n----------\n\n";
    }
}
