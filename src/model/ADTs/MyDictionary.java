package model.ADTs;

import exceptions.AdtException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<T1, T2>  implements MyDictionaryInterface<T1, T2>{
    private Map<T1, T2> map;

    public MyDictionary() {
        this.map = new HashMap<>();
    }

    @Override
    public void add(T1 key, T2 val) throws AdtException {
        try{
            this.map.put(key, val);
        } catch (Exception e){
            throw new AdtException("We cannot add null values to dictionary!");
        }
    }

    @Override
    public T2 remove(T1 key) throws AdtException {
        try{
            return this.map.remove(key);
        } catch (Exception e){
            throw new AdtException("The specified key does not exist!");
        }
    }

    @Override
    public void update(T1 key, T2 val) throws AdtException{
        try{
            this.map.replace(key, val);
        } catch (Exception e){
            throw new AdtException("The specified key does not exist!");
        }
    }

    @Override
    public T2 lookup(T1 key) throws AdtException {
        try{
            return this.map.get(key);
        } catch (Exception e){
            throw new AdtException("The specified key does not exist!");
        }
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean isDefined(T1 key) {
        return map.containsKey(key);
    }

    @Override
    public Collection<T2> values() {
        return this.map.values();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
