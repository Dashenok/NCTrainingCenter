package jl15.vector.container;

import jl15.vector.Vector;

import java.util.*;

public class VectorMap<K extends  Object, V extends  Vector> implements Map<K, V>, Iterable {
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
    public V get(Object key) {
       if (containsKey(key)){
           for (int i = 0; i < keyArr.length; i++) {
               if (keyArr[i].equals(key)){
                   return (V)valueArr[i];
               }
           }
       }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if ((containsKey(key))) {
            for (int i = 0; i < keyArr.length; i++)
                if (keyArr[i].equals(key)) {
                    Vector prevVector = valueArr[i];
                    valueArr[i] = value;
                    return (V)prevVector;
                }
        } else {
            Object[] tempKeyArr = keyArr.clone();
            Vector[] tempValueArr = valueArr.clone();
            keyArr = new Object[keyArr.length + 1];
            valueArr = new Vector[valueArr.length + 1];
            System.arraycopy(tempKeyArr, 0, keyArr, 0, tempKeyArr.length);
            System.arraycopy(tempValueArr, 0, valueArr, 0, tempValueArr.length);
            keyArr[keyArr.length-1] = key;
            valueArr[valueArr.length - 1] = value;
        }
        return null;
    }

    @Override
    public V remove(Object key) {
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

                    return (V)prevVector;
                }
        }
        return null;
    }

    @Override
    public void putAll(Map m) {
        Object[] keyObjArr = m.keySet().toArray();
        for (int i = 0; i < keyObjArr.length; i++) {
            put((K)keyObjArr[i], get(keyObjArr[i]));
        }

    }

    @Override
    public void clear() {
        keyArr = new Object[0];
        valueArr = new Vector[0];
    }

    @Override
    public Set keySet() {
        Set<Object> keySet = new HashSet<Object>();
        for (int i = 0; i < size(); i++) {
            keySet.add(keyArr[i]);
        }
        return keySet;
    }

    @Override
    public Collection values() {
        return new VectorCollection<Vector>(valueArr);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<Entry<K, V>>();
        for (int i = 0; i < size(); i++) {
            Entry<Object, Vector> entry = new EntrySet(keyArr[i], get(keyArr[i]));
            set.add((Entry<K, V>) entry);

        }

        return set;
    }

    @Override
    public Iterator iterator() {
        return new VectorMapIterator();
    }

    final class EntrySet implements Entry<Object, Vector> {
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

    private class VectorMapIterator implements Iterator {
        int cursor = 0;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        @Override
        public boolean hasNext() {
            return cursor != VectorMap.this.keyArr.length;
        }

        @Override
        public Object next() {
            int i = cursor;
            Object[] elementData = VectorMap.this.keyArr;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return VectorMap.this.valueArr[i];
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            try {
                VectorMap.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
