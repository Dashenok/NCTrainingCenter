package jl12.vector.container;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;


public class VectorList<T extends jl12.vector.Vector> extends jl12.vector.container.VectorCollection<T> implements List<T> {

    public VectorList(jl12.vector.Vector[] arr) {
        super(arr);
    }


    @Override
    public boolean addAll(int index, Collection c) throws ClassCastException,
                                                          IndexOutOfBoundsException, IllegalArgumentException{

        Object[] newVectorArray = (Object[]) c.toArray();
        for (int i = 0; i < c.size(); i++) {
            if (!(newVectorArray[i] instanceof jl12.vector.Vector) && newVectorArray[i] != null) {
                throw new IllegalArgumentException();
            }
        }
        if (index < 0 || index > arr.length-1){
            throw new IndexOutOfBoundsException();
        }
        jl12.vector.Vector[] tempArr = arr.clone();
        arr = new jl12.vector.Vector[arr.length + newVectorArray.length];
        System.arraycopy(tempArr, 0, arr, 0, tempArr.length);
        for (int i = 0; i < newVectorArray.length; i++){
            arr[i + tempArr.length] = (jl12.vector.Vector)newVectorArray[i];
        }
        return true;

    }

    @Override
    public T get(int index)  throws IndexOutOfBoundsException{
        if (index < 0 || index > arr.length-1){
            throw new IndexOutOfBoundsException();
        }
        return (T) arr[index];
    }


    @Override
    public T set(int index, T element) throws IndexOutOfBoundsException, ClassCastException,
                                                        NullPointerException{
        if (index < 0 || index > arr.length-1){
            throw new IndexOutOfBoundsException();
        }
        if (!(element instanceof jl12.vector.Vector)){
            throw new ClassCastException();
        }
        Object prevElement = arr[index];
        arr[index] = (jl12.vector.Vector)element;
        return (T) prevElement;
    }

    @Override
    public void add(int index, T element) throws ClassCastException, NullPointerException,
                                                      IndexOutOfBoundsException{
        if (element instanceof jl12.vector.Vector) {
            if (index < 0 || index > arr.length - 1) {
                throw new IndexOutOfBoundsException();
            }
            int l = arr.length;
            jl12.vector.Vector[] tempArray = new jl12.vector.Vector[l + 1];
            if (index == l){
                System.arraycopy(arr, 0, tempArray, 0, l);
                tempArray[index] = (jl12.vector.Vector)element;
            } else {
                System.arraycopy(arr, 0, tempArray, 0, index);
                tempArray[index] = (jl12.vector.Vector)element;
                System.arraycopy(arr, index, tempArray, index + 1, l - index);

            }
            arr = tempArray;

        }else {
            throw new ClassCastException();
        }
    }

    @Override
    public T remove(int index) throws IndexOutOfBoundsException{
        if (index < 0 || index > arr.length - 1) {
            throw new IndexOutOfBoundsException();
        }
        T deletedObject = (T) arr[index];
        int l = arr.length;
        jl12.vector.Vector[] tempArray = new jl12.vector.Vector[l - 1];
        System.arraycopy(arr, 0, tempArray, 0, index);
        if (index != l -1) {
            System.arraycopy(arr, index + 1, tempArray, index, l - index - 1);
        }
        arr = tempArray;
        return deletedObject;
    }

    @Override
    public int indexOf(Object o) throws ClassCastException{
        if ((o instanceof jl12.vector.Vector) || (o == null)) {
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
        if ((o instanceof jl12.vector.Vector) || (o == null)){
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
        jl12.vector.Vector[] newVectorArr = new jl12.vector.Vector[toIndex-fromIndex];
        int newArrIndex = 0;
        for (int i = fromIndex; i < toIndex; i++) {
            newVectorArr[newArrIndex++] = arr[i];
        }
        VectorList newVectorList = new VectorList(newVectorArr);
        return newVectorList;
    }
}