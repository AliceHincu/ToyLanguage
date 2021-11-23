package controller;


import exceptions.InterpreterException;
import model.ADTs.MyHeap;
import model.ADTs.MyHeapInterface;
import model.ADTs.MyStackInterface;
import model.ProgramState;
import model.Statements.StatementInterface;
import model.Values.RefValue;
import model.Values.Value;
import repository.RepositoryInterface;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private RepositoryInterface repo;
    boolean displayFlag;

    public Controller(RepositoryInterface repo) {
        this.repo = repo;
        this.displayFlag = false;
    }

    public void addProgram(ProgramState newProgram) {
        this.repo.addProgram(newProgram);
    }

    public ProgramState oneStep(ProgramState state) throws InterpreterException {
        MyStackInterface<StatementInterface> stack = state.getExeStack();
        if(stack.isEmpty())
            throw new InterpreterException("prgstate stack is empty");
        StatementInterface currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public String displayState(ProgramState state) {
        return state.toString();
    }

    public void setDisplayAll(Boolean flag) {
        this.displayFlag = flag;
    }

    public void allSteps() throws InterpreterException{
        ProgramState prg = repo.getCurrentProgram(); // repo is the controller field of type MyRepoInterface
        if(this.displayFlag)
            System.out.println(displayState(prg));
        repo.logProgramStateExecution(prg);
        while(!prg.getExeStack().isEmpty()){
            try{
                oneStep(prg);
            }catch(Exception e){
                throw new InterpreterException(e.getMessage());
            }
            if(this.displayFlag)
                System.out.println(displayState(prg));
            repo.logProgramStateExecution(prg);

            // unsafe garbage
            /*MyHeapInterface<Value> auxHeap = new MyHeap<>();
            auxHeap.setHeap(unsafeGarbageCollector(
                    getAddressesFromSymbolTable(prg.getSymbolTable().values()),
                    prg.getHeap().getHeap())
            );
            prg.getHeap().setHeap(auxHeap.getHeap());*/

            // safe
            // Ca sa fie safe, am salvat atat refValues din symbol table, cat si cele din heap. Iau adresele de la cele
            // n refValues. Astfel salvez toate referintele.
            // De ce era unsafe inainte? Pt ca eu daca alocam new(v,20) si dupa new(v, 30), in symbol table din
            // {a=(2, Ref(int)), v=(1, int)} se facea {a=(2, Ref(int)), v=(3, int)}, dar in heap aveam {1=20, 2=(1, int), 3=30}
            // daca luam doar refValues din symbtable, ramaneam cu adresele 2 si 3. Daca iau si din heap refValues, o
            // sa am si adresa 1.
            MyHeapInterface<Value> auxHeap = new MyHeap<>();
            auxHeap.setHeap(unsafeGarbageCollector(
                    getAddressesFromSymbolTable(prg.getSymbolTable().values(), prg.getHeap().getHeap().values()),
                    prg.getHeap().getHeap())
            );
            prg.getHeap().setHeap(auxHeap.getHeap());
        }
    }

    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symbolTableAddresses, Map<Integer, Value> heap) {
        /* transformi dict in set, vezi daca ce e in symbol table se afla si in heap, salvezi intr-un map*/
        return heap.entrySet().stream()
                .filter(e -> symbolTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    //unsafe
    private List<Integer> getAddressesFromSymbolTable(Collection<Value> symbolTableValues) {
        /*dai filter ca sa ai doar RefValues, dupa aceea dai .map ca sa mapezi din Value in adresa, dupa colectezi intr o lista*/
        return symbolTableValues.stream()
                .filter(v ->v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    //safe
    private List<Integer> getAddressesFromSymbolTable(Collection<Value> symbolTableValue, Collection<Value> heap) {
        return Stream.concat(
                heap.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();}),
                symbolTableValue.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
        ).collect(Collectors.toList());
    }


}
