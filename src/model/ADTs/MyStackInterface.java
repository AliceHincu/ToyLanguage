package model.ADTs;

import exceptions.AdtException;

public interface MyStackInterface<T> {
    T pop() throws AdtException;
    void push(T val);
    boolean isEmpty();
    int size();
    T top() throws AdtException;
}
