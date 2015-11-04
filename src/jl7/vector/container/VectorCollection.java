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
        if (o instanceof Vector){
            for (int i = 0; i < arr.length; i++) {
                if (o == arr[i]){
                    return true;
                }
            }
        }else {
            throw new ClassCastException();
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
        return new Object[0];
    }


    @Override
    public boolean add(Object o){
        if (o instanceof Vector) {
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
        if (o instanceof Vector) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals(o)) {
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
        int countOfContains = 0;
        Vector[] newVectorArray = (Vector[])c.toArray();
        for (int i = 0; i < newVectorArray.length; i++){
                add(newVectorArray[i]);
                countOfContains++;
        }
        if (countOfContains == 0){return false;}
        return true;
    }

    @Override
    public void clear() {
        arr = new Vector[0];
    }

    @Override
    public boolean retainAll(Collection c) {
        int countOfContains = 0;
        for (int i = 0; i < arr.length; i++) {
            if (!c.contains(arr[i])){
                remove(arr[i]);
                countOfContains++;
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
                i++;
            };
        }
        if (countOfContains == 0){return false;}
        return true;
    }

    @Override
    public boolean containsAll(Collection c) {
        int countOfContains = 0;
        Vector[] newVectorArray = (Vector[]) c.toArray();
        for (int j = 0; j < newVectorArray.length; j++) {
                for (int i = 0; i < arr.length; i++) {
                    if (newVectorArray[j] == arr[i]){
                        countOfContains++;
                        break;
                    }
                }
        }
        if (countOfContains == c.size()){
            return true;
        }

        return false;
    }


}