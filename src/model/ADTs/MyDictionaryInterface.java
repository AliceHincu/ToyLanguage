package model.ADTs;

import exceptions.AdtException;

import java.util.Collection;

public interface MyDictionaryInterface<T1, T2> {
    void add(T1 key, T2 val) throws AdtException;
    T2 remove(T1 key) throws AdtException;
    void update(T1 key, T2 val) throws AdtException;
    T2 lookup(T1 key) throws AdtException;
    boolean isEmpty();
    boolean isDefined(T1 key); // if the key is in dict
    Collection<T2> values();
}
