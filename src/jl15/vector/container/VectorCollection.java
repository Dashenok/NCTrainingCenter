package jl15.vector.container;


import jl15.vector.Vector;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;


public class VectorCollection<T extends Vector> implements Collection<T> {

    protected Vector[] arr;

    public VectorCollection(Vector[] arr) {
        this.arr = arr;
    }

    @Override
    public int size() {
        return arr.length;
    }

    @Override
    public boolean isEmpty() {
        return arr.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < arr.length; i++) {
            if(o == null ? arr[i] == null : arr[i].equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return new VectorCollectionIterator();
    }

    @Override
    public Object[] toArray() {
        Vector[] newArr =  new Vector[arr.length];
        System.arraycopy(arr, 0, newArr, 0,  arr.length);
        return newArr;
    }


    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length >= size()){
            System.arraycopy(arr,0,a,0,arr.length);
            int diff = a.length - size();
            for (int i = 0; i < diff; i++) {
                a[arr.length+i] = null;
            }
        } else {
            a = (T[]) new jl11.vector.Vector[arr.length];
            System.arraycopy(arr, 0, a, 0,  arr.length);
        }
        return (T[])a;
    }

    @Override
    public boolean add(T t){
        if (!(t instanceof Vector || t == null)){
            throw new ClassCastException();
        }
            Vector[] tempArray = new Vector[arr.length];
            System.arraycopy(arr, 0, tempArray, 0, arr.length);
            arr = new Vector[arr.length + 1];
            System.arraycopy(tempArray, 0, arr, 0, arr.length-1);
            arr[arr.length-1] = t;
            return true;

    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof jl11.vector.Vector || o == null)){
            throw new ClassCastException();
        }
        for (int i = 0; i < arr.length; i++) {
            if (o == null ? arr[i] == null : arr[i].equals(o)) {
                jl11.vector.Vector[] tempArray = new jl11.vector.Vector[arr.length - 1];
                System.arraycopy(arr, 0, tempArray, 0, i);
                if (i != arr.length - 1) {
                    System.arraycopy(arr, i + 1, tempArray, i, arr.length - 1 - i);
                }
                arr = new Vector[arr.length - 1];
                System.arraycopy(tempArray, 0, arr, 0 , arr.length);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        Vector[] newVectorArray = (Vector[])c.toArray();
        Vector[] tempArr = arr.clone();
        arr = new Vector[arr.length + newVectorArray.length];
        System.arraycopy(tempArr, 0, arr, 0, tempArr.length);
        System.arraycopy(newVectorArray, 0, arr, tempArr.length, newVectorArray.length);
        return true;
    }

    @Override
    public void clear() {
        arr = new Vector[0];
    }

    @Override
    public boolean retainAll(Collection c) {
        int countOfContains = 0;
        for (int i = 0; i < arr.length; ) {
            if (!c.contains(arr[i])){
                remove(arr[i]);
                countOfContains++;
            } else {
                i++;
            }
        }
        return countOfContains != 0;

    }

    @Override
    public boolean removeAll(Collection c) {
        int countOfContains = 0;
        for (int i = 0; i < arr.length;) {
            if (c.contains(arr[i])){
                remove(arr[i]);
                countOfContains++;
            }else{
                i++;//Ask
            }
        }
        if (countOfContains == 0){return false;}
        return true;
    }

    @Override
    public boolean containsAll(Collection c) {
        Vector[] newVectorArray = (Vector[]) c.toArray();
        for (int j = 0; j < newVectorArray.length; j++) {
                for (int i = 0; i < arr.length; i++) {
                    if (!newVectorArray[j].equals(arr[i])){
                        return false;
                    }
                }
        }


        return true;
    }

    private class VectorCollectionIterator implements Iterator{

        int cursor = 0;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        @Override
        public boolean hasNext() {
            return cursor != VectorCollection.this.arr.length;
        }

        @Override
        public Object next() {
            int i = cursor;
            Vector[] elementData = VectorCollection.this.arr;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return elementData[lastRet = i];
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            try {
                VectorCollection.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}