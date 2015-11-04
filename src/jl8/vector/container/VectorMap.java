package jl8.vector.container;

import jl8.vector.Vector;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class VectorMap implements Map {
    private Object[] keyArr;
    private Vector[] valueArr;

    @Override
    public int size() {
        return keyArr.length;
    }

    @Override
    public boolean isEmpty() {
        return keyArr.length == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        for (int i = 0; i < keyArr.length; i++) {
            return key == null ? keyArr[i] == null: keyArr[i].equals(key);
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < valueArr.length; i++) {
            return value == null ? valueArr[i] == null: valueArr[i].equals(value);
        }
        return false;
    }

    @Override
    public Object get(Object key) {
       if (containsKey(key)){
           for (int i = 0; i < keyArr.length; i++) {
               if (keyArr[i].equals(key)){
                   return valueArr[i];
               }
           }
       }
        return null;
    }

    @Override
    public Object put(Object key, Object value) {
        if ((containsKey(key))) {
            for (int i = 0; i < keyArr.length; i++)
                if (keyArr[i].equals(key)) {
                    Vector prevVector = valueArr[i];
                    //valueArr[i] = value;
                    return prevVector;
                }
        }
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set keySet() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }
}
