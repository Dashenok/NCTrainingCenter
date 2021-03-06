package jl7.vector.container;


import jl7.vector.Vector;

import java.util.Collection;
import java.util.Iterator;


public class VectorCollection implements Collection {

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
            return o == null ? arr[i] == null : arr[i].equals(o);
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        Vector[] newArr =  new Vector[arr.length];
        System.arraycopy(arr, 0, newArr, 0,  arr.length);
        return newArr;
    }

    @Override
    public Object[] toArray(Object[] a) {
        if (a.length >= size()){
            System.arraycopy(arr,0,a,0,arr.length);
            int diff = a.length - size();
            for (int i = 0; i < diff; i++) {
                a[arr.length+i] = null;
            }
        } else {
            a =  new Vector[arr.length];
            System.arraycopy(arr, 0, a, 0,  arr.length);
        }
        return a;
    }


    @Override
    public boolean add(Object o){
        if ((o instanceof Vector) || o == null){
            Vector[] tempArray = new Vector[arr.length];
            System.arraycopy(arr, 0, tempArray, 0, arr.length);
            arr = new Vector[arr.length + 1];
            System.arraycopy(tempArray, 0, arr, 0, arr.length-1);
            arr[arr.length-1] = (Vector)o;
            return true;
        } else {
            throw new ClassCastException();
        }
    }

    @Override
    public boolean remove(Object o) {
        if ((o instanceof Vector) || o == null){
            for (int i = 0; i < arr.length; i++) {
                if (o == null ? arr[i] == null : arr[i].equals(o)) {
                    Vector[] tempArray = new Vector[arr.length - 1];
                    System.arraycopy(arr, 0, tempArray, 0, i);
                    if (i != arr.length - 1) {
                        System.arraycopy(arr, i + 1, tempArray, i, arr.length - 1 - i);
                    }
                    arr = new Vector[arr.length - 1];
                    System.arraycopy(tempArray, 0, arr, 0 , arr.length);
                    return true;
                }
            }
        } else {
            throw new ClassCastException();
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
        if (countOfContains == 0){return false;}
        return true;

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


}