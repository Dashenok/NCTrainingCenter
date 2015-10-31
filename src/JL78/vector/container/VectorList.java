package JL78.vector.container;

import JL78.vector.Vector;
import JL78.vector.exceptions.VectorIndexOutOfBoundsException;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;


public class VectorList extends VectorCollection implements List {

    public VectorList(Vector[] arr) {
        super(arr);
    }

    @Override
    public boolean addAll(int index, Collection c) {
        for (int i = 0; i < c.size(); i++) {
            Vector[] tempArray = new Vector[arr.length+1];
            System.arraycopy(arr, 0, tempArray, 0, arr.length);
            tempArray[arr.length] = (Vector)get(i);
            fillFromMass(tempArray);
        }
        return true;
    }

    private void fillFromMass(Vector[] tempArray) {
        int newMassLength = tempArray.length;
        if (arr.length != newMassLength){
            arr = new Vector[arr.length];
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = tempArray[i];
        }
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index > arr.length-1){
            throw new VectorIndexOutOfBoundsException();
        }
        return  arr[index];
    }

    @Override
    public Object set(int index, Object element) {
        if (index < 0 || index > arr.length-1){
            throw new VectorIndexOutOfBoundsException();
        }
        Object prevElement = arr[index];
        if (element instanceof Vector){
            arr[index] = (Vector)element;
        }
        return prevElement;
    }

    @Override
    public void add(int index, Object element) {

    }

    @Override
    public Object remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
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
    public List subList(int fromIndex, int toIndex) {
        return null;
    }
}