package JL78.vector.container;


import JL78.vector.Vector;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;


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
        if (arr.length == 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof Vector){
            for (int i = 0; i < arr.length; i++) {
                if (o == arr[i]){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Iterator iterator() throws UnsupportedOperationException {
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
            System.arraycopy(arr, 0, tempArray, 0, arr.length);
            arr[arr.length] = (Vector)o;
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
        for (Object o : c){
            if (!contains(o)){
                add(o);
                countOfContains++;
            }
        }
        if (countOfContains == 0){return false;}
        return true;
    }

    @Override
    public void clear() {
        arr = null;
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
        for (int i = 0; i < arr.length; i++) {
            if (c.contains(arr[i])){
                remove(arr[i]);
                countOfContains++;
            }
        }
        if (countOfContains == 0){return false;}
        return true;
    }

    @Override
    public boolean containsAll(Collection c) {
        int countOfContains = 0;
        for (Object o : c) {
            if (o instanceof Vector){
                for (int i = 0; i < arr.length; i++) {
                    if (o == arr[i]){
                        countOfContains++;
                    }
                }
            } else {return false;}
        }
        if (countOfContains == c.size()){
            return true;
        }

        return false;
    }


}