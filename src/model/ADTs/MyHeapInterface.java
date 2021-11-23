package model.ADTs;

import java.util.Map;

public interface MyHeapInterface<T> {
    public int allocate(T value);
    public T deallocate(int address);

    public T get(int address);
    public void put(int address, T value);
    public boolean exists(int address);

    public Map<Integer, T> getHeap();
    public void setHeap(Map<Integer, T> map);
}
