package jl15.vector.impl;

import jl15.vector.Vector;
import jl15.vector.Vectors;
import jl15.vector.exceptions.IncompatibleVectorSizesException;
import jl15.vector.exceptions.VectorIndexOutOfBoundsException;

import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Observable;

public class LinkedVector extends Observable implements Vector, Cloneable, Serializable{

    public class Nod  implements Serializable{
        public double element;
        public Nod next;
        public Nod prev;

        public Nod(double element) {
            this.element = element;
        }
    }

    protected Nod head;
    protected int size;

    protected Nod goToElement(int index) {
        Nod result = head;
        int i=0;
        while (i != index) {
            result = result.next;
            i++;
        }
        return result;
    }

    protected void insertElementBefore(Nod current, Nod newNod) {
        newNod.next = current;
        newNod.prev = current.prev;
        current.prev.next = newNod;
        current.prev = newNod;
        size++;
        notifyObservers();
    }

    protected void delElement(Nod current) {
        if (size == 1) {
            head = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
            if (current == head) {
                head = current.next;
            }
        }
        size--;
        notifyObservers();

    }

    @Override
    public double getElement(int index) {
        if (index < 0 || index > size-1){
            throw new VectorIndexOutOfBoundsException();
        }
        return goToElement(index).element;
    }

    @Override
    public void setElement(int index, double element) {
        if (index < 0 || index > size-1){
            throw new VectorIndexOutOfBoundsException();
        }
        Nod currentElement = goToElement(index);
        currentElement.element = element;
        notifyObservers();
    }

    public int getSize() {
        return size;
    }


    public void fillFromMass(double[] newMass){
        int l = newMass.length;
        size = 0;
        for (int i = 0; i < l; i++) {
            addElement(newMass[i]);
        }
        notifyObservers();
    }

    public void fillFromVector(Vector newVector){

        int l  = newVector.getSize();
        size = 0;
        for (int i = 0; i < l; i++) {
            addElement(newVector.getElement(i));
        }
        notifyObservers();
    }

    public void mult (double factor){
        Nod currentElement = head;
        for (int i = 0; i < size; i++) {
            currentElement.element = currentElement.element * factor;
            currentElement = currentElement.next;
        }
        notifyObservers();
    }

    public void sum(Vector newVector) throws IncompatibleVectorSizesException {


        if (size != newVector.getSize()){
            throw new IncompatibleVectorSizesException();
        }

        Nod currentElement = head;
        for (int i = 0; i < size; i++) {
            currentElement.element = currentElement.element + newVector.getElement(i);
            currentElement = currentElement.next;
        }
        notifyObservers();
    }

    public void sort(boolean incr){

        Vectors.sort(this, incr);
        notifyObservers();
    }

    public void addElement(double element){
        if (size == 0){
            Nod newElement = new Nod(element);
            head = newElement;
            newElement.next = newElement;
            newElement.prev = newElement;
        } else {
            Nod newElement = new Nod(element);
            newElement.next = head;
            newElement.prev = head.prev;
            head.prev.next = newElement;
            head.prev = newElement;

        }
        size++;
        notifyObservers(size-1);
    }

    public void insertElement(int index, double element){
        if (index < 0 || index > size){
            throw new VectorIndexOutOfBoundsException();
        }
        if (index == size){
            addElement(element);
        } else{
            Nod newNod = new Nod(element);
            Nod currentNod = goToElement(index);
            insertElementBefore(currentNod, newNod);
            if (index == 0) {head = newNod;}
        }
        notifyObservers(index);
    }

    public void deleteElement(int index){
        if (index < 0 || index > size){
            throw new VectorIndexOutOfBoundsException();
        }
        Nod currentElement = goToElement(index);
        delElement(currentElement);
        notifyObservers(index);
    }

    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        Nod currentElement = head;
        for (int i = 0; i < size; i++) {

            stringBuffer.append(currentElement.element).append(" ");
            currentElement = currentElement.next;

        }
        return stringBuffer.toString().trim();
    }

    public boolean equals(Object obj){
        if (obj instanceof Vector){
            if (((Vector) obj).getSize() != size){
                return false;
            }
            Nod currentElement = head;
            Nod currentObjectElement = ((LinkedVector) obj).head;
            for (int i = 0; i < size; i++) {

                if (currentElement.element != currentObjectElement.element){
                    return false;
                }
                currentElement = currentObjectElement.next;
                currentObjectElement = currentObjectElement.next;
                return true;
            }
        }
        return  false;}

    public Object clone() throws CloneNotSupportedException{
        Object newVector = super.clone();
        ((LinkedVector) newVector).fillFromVector(this);
        return newVector;
    }

    @Override
    public Iterator iterator() {
        return new LinkedVectorIterator();
    }

    private class LinkedVectorIterator implements Iterator{
        int cursor = 0;
        Nod currentNod = head;
        Nod lastRetNod = null;
        int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public Object next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Nod returnedNod = currentNod;
            cursor = i + 1;
            currentNod = currentNod.next;
            return returnedNod.element;
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            try {
                LinkedVector.this.deleteElement(lastRet);
                cursor = lastRet;
                lastRetNod = currentNod.prev;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}