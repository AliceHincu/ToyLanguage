package model.Expressions;

import exceptions.ExpressionException;
import exceptions.InterpreterException;
import model.ADTs.MyDictionaryInterface;
import model.ADTs.MyHeapInterface;
import model.Types.BoolType;
import model.Values.BoolValue;
import model.Values.Value;

public class LogicExpression implements Expression{
    private final Expression expression1;
    private final Expression expression2;
    private final int op; // 1->&, 2->|

    public LogicExpression(Expression expression1, Expression expression2, int op) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.op = op;
    }

    @Override
    public String toString() {
        return switch (op) {
            case 1 -> expression1.toString() + "&" + expression2.toString();
            case 2 -> expression1.toString() + "|" + expression2.toString();
            default -> "";
        };
    }

    @Override
    public Value eval(MyDictionaryInterface<String, Value> SymbolTable, MyHeapInterface<Value> Heap) throws InterpreterException {
        Value v1, v2;
        v1 = expression1.eval(SymbolTable, Heap);

        if (v1.getType().equals(new BoolType())) {
            v2 = expression2.eval(SymbolTable, Heap);
            if (v2.getType().equals(new BoolType())) {
                BoolValue booleanValue1 = (BoolValue) v1; //casting -> dintr-un tip mai mare intr-un tip mai mic
                BoolValue booleanValue2 = (BoolValue) v2; //casting -> dintr-un tip mai mare intr-un tip mai mic
                boolean b1, b2;
                b1 = booleanValue1.getValue();
                b2 = booleanValue2.getValue();

                switch (op) {
                    case 1 -> {return new BoolValue(b1 & b2);}
                    case 2 -> {return new BoolValue(b1 | b2);}
                    default -> throw new ExpressionException("Operation invalid!");
                }
            } else
                throw new ExpressionException("The second operand is not a boolean");
        } else
            throw new ExpressionException("The first operand is not a boolean");

    }

    @Override
    public Expression deepCopy() {
        return new LogicExpression(this.expression1, this.expression2, this.op);
    }
}
