package jl14.vector.container;


import java.util.Collection;
import java.util.Iterator;


public class VectorCollection<T extends jl11.vector.Vector> implements Collection<T> {

    protected jl11.vector.Vector[] arr;

    public VectorCollection(jl11.vector.Vector[] arr) {
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
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        jl11.vector.Vector[] newArr =  new jl11.vector.Vector[arr.length];
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
        if (!(t instanceof jl11.vector.Vector || t == null)){
            throw new ClassCastException();
        }
            jl11.vector.Vector[] tempArray = new jl11.vector.Vector[arr.length];
            System.arraycopy(arr, 0, tempArray, 0, arr.length);
            arr = new jl11.vector.Vector[arr.length + 1];
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
                arr = new jl11.vector.Vector[arr.length - 1];
                System.arraycopy(tempArray, 0, arr, 0 , arr.length);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        jl11.vector.Vector[] newVectorArray = (jl11.vector.Vector[])c.toArray();
        jl11.vector.Vector[] tempArr = arr.clone();
        arr = new jl11.vector.Vector[arr.length + newVectorArray.length];
        System.arraycopy(tempArr, 0, arr, 0, tempArr.length);
        System.arraycopy(newVectorArray, 0, arr, tempArr.length, newVectorArray.length);
        return true;
    }

    @Override
    public void clear() {
        arr = new jl11.vector.Vector[0];
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
        jl11.vector.Vector[] newVectorArray = (jl11.vector.Vector[]) c.toArray();
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