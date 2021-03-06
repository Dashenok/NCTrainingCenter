package jl8.vector.container;

import jl8.vector.Vector;

import java.util.Collection;
import java.util.HashSet;
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
            if (key == null ? keyArr[i] == null: keyArr[i].equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < valueArr.length; i++) {
            if (value == null ? valueArr[i] == null: valueArr[i].equals(value)) {
                return true;
            }
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
        Object[] keyObjArr = m.keySet().toArray();
        for (int i = 0; i < keyObjArr.length; i++) {
            put(keyObjArr[i], get(keyObjArr[i]));
        }

    }

    @Override
    public void clear() {
        keyArr = new Object[0];
        valueArr = new Vector[0];
    }

    @Override
    public Set keySet() {
        Set keySet = new HashSet();
        for (int i = 0; i < size(); i++) {
            keySet.add(keyArr[i]);
        }
        return keySet;
    }

    @Override
    public Collection values() {
        return new VectorCollection(valueArr);
    }

    @Override
    public Set<Entry> entrySet() {
        Set set = new HashSet();
        for (int i = 0; i < size(); i++) {
            Entry entry = new EntrySet(keyArr[i], (Vector)get(keyArr[i]));
            set.add(entry);

        }

        return set;
    }

    final class EntrySet implements Map.Entry<Object, Vector> {
        private Object key;
        private Vector value;
        public EntrySet(Object key, Vector value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public Object getKey() {
            return key;
        }

        @Override
        public Vector getValue() {
            return value;
        }

        @Override
        public Vector setValue(Vector value) {
            Vector oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }
}
