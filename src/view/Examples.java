package view;

import model.Expressions.*;
import model.Statements.*;
import model.Types.BoolType;
import model.Types.IntType;
import model.Types.RefType;
import model.Types.StringType;
import model.Values.BoolValue;
import model.Values.IntValue;
import model.Values.StringValue;

public class Examples {

    StatementInterface example1 = new CompoundStatement(
            new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(
                    new AssignStatement("v", new ValueExpression(new IntValue(2))),
                    new PrintStatement(new VariableExpression("v"))
            )
    );
    StatementInterface example2 = new CompoundStatement(
            new VariableDeclarationStatement("a", new IntType()),
            new CompoundStatement(
                    new VariableDeclarationStatement("b", new IntType()),
                    new CompoundStatement(
                            new AssignStatement(
                                    "a",
                                    new ArithmeticExpression(
                                            '+',
                                            new ValueExpression(new IntValue(2)),
                                            new ArithmeticExpression('*',
                                                    new ValueExpression(new IntValue(3)),
                                                    new ValueExpression(new IntValue(5))
                                            )
                                    )
                            ),
                            new CompoundStatement(
                                    new AssignStatement(
                                            "b",
                                            new ArithmeticExpression('+',
                                                    new VariableExpression("a"),
                                                    new ValueExpression(new IntValue(1))
                                            )
                                    ),
                                    new PrintStatement(new VariableExpression("b"))
                            )
                    )
            )
    );
    StatementInterface example3 = new CompoundStatement(
            new VariableDeclarationStatement("a", new BoolType()),
            new CompoundStatement(
                    new VariableDeclarationStatement("v", new IntType()),
                    new CompoundStatement(
                            new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                            new CompoundStatement(
                                    new IfStatement(
                                            new VariableExpression("a"),
                                            new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                            new AssignStatement("v", new ValueExpression(new IntValue(3)))
                                    ),
                                    new PrintStatement(new VariableExpression("v"))
                            )
                    )
            )
    );

    StatementInterface example4 = new CompoundStatement(
            new VariableDeclarationStatement("varf", new StringType()),
            new CompoundStatement(
                    new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                    new CompoundStatement(
                            new OpenReadFileStatement(new VariableExpression("varf")),
                            new CompoundStatement(
                                    new VariableDeclarationStatement("varc", new IntType()),
                                    new CompoundStatement(
                                            new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                            new CompoundStatement(
                                                    new PrintStatement(new VariableExpression("varc")),
                                                    new CompoundStatement(
                                                           new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                           new CompoundStatement(
                                                                   new PrintStatement(new VariableExpression("varc")),
                                                                   new CloseReadFileStatement(new VariableExpression("varf"))
                                                           )
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );

    StatementInterface example5 = new CompoundStatement(
            new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(
                    new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                            new CompoundStatement(
                                    new HeapAllocationStatement("a",new VariableExpression("v")),
                                    new CompoundStatement(
                                            new PrintStatement(new VariableExpression("v")),
                                            new PrintStatement(new VariableExpression("a"))
                                    )
                            )
                    )
            )
    );

    StatementInterface example6 = new CompoundStatement(
            new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(
                    new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                            new CompoundStatement(
                                    new HeapAllocationStatement("a",new VariableExpression("v")),
                                    new CompoundStatement(
                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                            new PrintStatement(
                                                    new ArithmeticExpression(
                                                            '+',
                                                                new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                                                                new ValueExpression(new IntValue(5))
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );

    StatementInterface example7 = new CompoundStatement(
            new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(
                    new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                            new CompoundStatement(
                                    new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                                    new PrintStatement(new ArithmeticExpression(
                                            '+',
                                                new ReadHeapExpression(new VariableExpression("v")),
                                                new ValueExpression(new IntValue(5))
                                    ))
                            )
                    )
            )
    );

    StatementInterface example8 = new CompoundStatement(
            new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(
                    new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                            new CompoundStatement(
                                    new HeapAllocationStatement("a",new VariableExpression("v")),
                                    new CompoundStatement(
                                            new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                            new PrintStatement(
                                                    new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))
                                            )
                                    )
                            )
                    )
            )
    );

    StatementInterface example9 = new CompoundStatement(
            new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(
                    new AssignStatement("v", new ValueExpression(new IntValue(4))),
                    new CompoundStatement(
                            new WhileStatement(
                                    new RelationalExpression(
                                            ">",
                                            new VariableExpression("v"),
                                            new ValueExpression(new IntValue(0))),
                                    new CompoundStatement(
                                            new PrintStatement(new VariableExpression("v")),
                                            new AssignStatement(
                                                    "v",
                                                    new ArithmeticExpression(
                                                            '-',
                                                            new VariableExpression("v"),
                                                            new ValueExpression(new IntValue(1)))
                                            )
                                    )
                            ),
                            new PrintStatement(new VariableExpression("v"))
                    )
            )
    );

    public StatementInterface[] exampleList(){
        return new StatementInterface[]{example1, example2, example3, example4, example5, example6, example7, example8,
        example9};
    }
}
