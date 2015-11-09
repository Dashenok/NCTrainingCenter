package jl9.vector.container;

import jl9.vector.Vector;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;


public class VectorList extends VectorCollection implements List {

    public VectorList(Vector[] arr) {
        super(arr);
    }

    @Override
    public boolean addAll(int index, Collection c) throws ClassCastException,
                                                          IndexOutOfBoundsException, IllegalArgumentException{

        Object[] newVectorArray = (Object[]) c.toArray();
        for (int i = 0; i < c.size(); i++) {
            if (!(newVectorArray[i] instanceof Vector) && newVectorArray[i] != null) {
                throw new IllegalArgumentException();
            }
        }
        if (index < 0 || index > arr.length-1){
            throw new IndexOutOfBoundsException();
        }
        Vector[] tempArr = arr.clone();
        arr = new Vector[arr.length + newVectorArray.length];
        System.arraycopy(tempArr, 0, arr, 0, tempArr.length);
        for (int i = 0; i < newVectorArray.length; i++){
            arr[i + tempArr.length] = (Vector)newVectorArray[i];
        }
        return true;

    }

    @Override
    public Object get(int index)  throws IndexOutOfBoundsException{
        if (index < 0 || index > arr.length-1){
            throw new IndexOutOfBoundsException();
        }
        return  arr[index];
    }

    @Override
    public Object set(int index, Object element) throws IndexOutOfBoundsException, ClassCastException,
                                                        NullPointerException{
        if (index < 0 || index > arr.length-1){
            throw new IndexOutOfBoundsException();
        }
        if (!(element instanceof Vector)){
            throw new ClassCastException();
        }
        Object prevElement = arr[index];
        arr[index] = (Vector)element;
        return prevElement;
    }

    @Override
    public void add(int index, Object element) throws ClassCastException, NullPointerException,
                                                      IndexOutOfBoundsException{
        if (element instanceof Vector) {
            if (index < 0 || index > arr.length - 1) {
                throw new IndexOutOfBoundsException();
            }
            int l = arr.length;
            Vector[] tempArray = new Vector[l + 1];
            if (index == l){
                System.arraycopy(arr, 0, tempArray, 0, l);
                tempArray[index] = (Vector)element;
            } else {
                System.arraycopy(arr, 0, tempArray, 0, index);
                tempArray[index] = (Vector)element;
                System.arraycopy(arr, index, tempArray, index + 1, l - index);

            }
            arr = tempArray;

        }else {
            throw new ClassCastException();
        }
    }

    @Override
    public Object remove(int index) throws IndexOutOfBoundsException{
        if (index < 0 || index > arr.length - 1) {
            throw new IndexOutOfBoundsException();
        }
        Object deletedObject = arr[index];
        int l = arr.length;
        Vector[] tempArray = new Vector[l - 1];
        System.arraycopy(arr, 0, tempArray, 0, index);
        if (index != l -1) {
            System.arraycopy(arr, index + 1, tempArray, index, l - index - 1);
        }
        arr = tempArray;
        return deletedObject;
    }

    @Override
    public int indexOf(Object o) throws ClassCastException{
        if ((o instanceof Vector) || (o == null)) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == null){
                    if (o == null) {
                        return i;
                    }
                }else if ((arr[i].equals(o))){
                    return i;
                }
            }
            return -1;
        }else{
            throw new ClassCastException();
        }
    }

    @Override
    public int lastIndexOf(Object o) {
        if ((o instanceof Vector) || (o == null)){
            for (int i = arr.length-1; i >=0 ; i--) {
                if (arr[i] == null){
                    if (o == null) {
                        return i;
                    }
                }else if ((arr[i].equals(o))){
                    return i;
                }
            }
            return -1;
        }else{
            throw new ClassCastException();
        }
    }

    @Override
    public ListIterator listIterator() {
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException{
        if (fromIndex < 0 || toIndex > arr.length - 1 || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        Vector[] newVectorArr = new Vector[toIndex-fromIndex];
        int newArrIndex = 0;
        for (int i = fromIndex; i < toIndex; i++) {
            newVectorArr[newArrIndex++] = arr[i];
        }
        VectorList newVectorList = new VectorList(newVectorArr);
        return newVectorList;
    }
}