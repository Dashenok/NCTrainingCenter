package jl8.vector.container;

import jl8.vector.Vector;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
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
                    valueArr[i] = (Vector)value;
                    return prevVector;
                }
        } else {
            Object[] tempKeyArr = keyArr.clone();
            Vector[] tempValueArr = valueArr.clone();
            keyArr = new Object[keyArr.length + 1];
            valueArr = new Vector[valueArr.length + 1];
            System.arraycopy(tempKeyArr, 0, keyArr, 0, tempKeyArr.length);
            System.arraycopy(tempValueArr, 0, valueArr, 0, tempValueArr.length);
            keyArr[keyArr.length-1] = key;
            valueArr[valueArr.length - 1] = (Vector)value;
        }
        return null;
    }

    @Override
    public Object remove(Object key) {
        if ((containsKey(key))) {
            for (int i = 0; i < keyArr.length; i++)
                if (keyArr[i].equals(key)) {
                    Vector prevVector = valueArr[i];
                    Object[] tempKeyArr = keyArr.clone();
                    Vector[] tempValueArr = valueArr.clone();
                    keyArr = new Object[keyArr.length - 1];
                    valueArr = new Vector[valueArr.length - 1];

                    System.arraycopy(keyArr, 0, tempKeyArr, 0, i);
                    if (i != keyArr.length - 1) {
                        System.arraycopy(keyArr, i + 1, tempKeyArr, i, keyArr.length - 1 - i);
                    }
                    keyArr = new Vector[keyArr.length - 1];
                    System.arraycopy(tempValueArr, 0, keyArr, 0, keyArr.length);

                    System.arraycopy(valueArr, 0, tempValueArr, 0, i);
                    if (i != valueArr.length - 1) {
                        System.arraycopy(valueArr, i + 1, tempValueArr, i, valueArr.length - 1 - i);
                    }
                    valueArr = new Vector[valueArr.length - 1];
                    System.arraycopy(tempValueArr, 0, valueArr, 0, valueArr.length);

                    return prevVector;
                }
        }
        return null;
    }

    @Override
    public void putAll(Map m) {
        for (int i = 0; i < m.size(); i++) {
            //put()
        }
    }

    @Override
    public void clear() {
        keyArr = new Object[0];
        valueArr = new Vector[0];
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
