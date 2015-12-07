package jl15.vector.impl;

import jl15.vector.Vector;
import jl15.vector.Vectors;
import jl15.vector.exceptions.IncompatibleVectorSizesException;
import jl15.vector.exceptions.VectorIndexOutOfBoundsException;

import java.io.Serializable;
import java.util.*;

public class ArrayVector extends Observable implements Vector, Cloneable, Serializable {
    protected int size;
    protected double[] mass;

    public ArrayVector(int size) throws VectorIndexOutOfBoundsException {
        if (size < 0) {
            throw new VectorIndexOutOfBoundsException();
        }
        this.size = size;
        this.mass = new double[size];
    }

    @Override
    public double getElement(int index){
        if (index < 0 || index > size-1){
            throw new VectorIndexOutOfBoundsException();
        }
        return  mass[index];
    }

    @Override
    public void setElement(int index, double element){
        if (index < 0 || index > size-1){
            throw new VectorIndexOutOfBoundsException();
        }
        mass[index] = element;
        notifyObservers(index);
    }

    public int getSize() {
        return size;
    }


    public void fillFromMass(double[] newMass){
        int newMassLength = newMass.length;
        if (size != newMassLength){
            size = newMassLength;
            mass = new double[size];
        }
        for (int i = 0; i < size; i++) {
            mass[i] = newMass[i];
        }
        notifyObservers();
    }

    public void fillFromVector(Vector newVector){

        int newMassLength = newVector.getSize();
        if (size != newMassLength){
            size = newMassLength;
            mass = new double[size];
        }
        for (int i = 0; i < size; i++) {
            mass[i] = newVector.getElement(i);
        }
        notifyObservers();
    }

    public boolean equal(Vector newVector){

        int newMassLength = newVector.getSize();

        if (size != newMassLength){
            System.out.println("Vectors have different length");
            return false;
        }

        for (int i = 0; i < newMassLength; i++) {
            if (mass[i] != newVector.getElement(i)) return false;
        }
        return true;
    }

    public double getMaxValue(){
        double maxValue = mass[0];
        for (int i = 1; i < size; i++) {
            maxValue = getMax(maxValue, mass[i]);

        }
        return maxValue;
    }

    private double getMax(double a, double b){
        return a > b ? a : b;
    }

    public double getMinValue(){
        double minValue = mass[0];
        for (int i = 1; i < size; i++) {
            minValue = getMin(minValue, mass[i]);

        }
        return minValue;
    }

    private double getMin(double a, double b){
        return a < b ? a : b;
    }

    public void mult (double factor){
        for (int i = 0; i < size; i++) {
            mass[i] = mass[i]*factor;
        }
        notifyObservers();
    }

    public void sum(Vector newVector) throws IncompatibleVectorSizesException {


        if (size != newVector.getSize()){
            throw new IncompatibleVectorSizesException();
        }

        for (int i = 0; i < size; i++) {
            mass[i] = mass[i] + newVector.getElement(i);
        }
        notifyObservers();
    }

    public void sort(boolean incr){
        Vectors.sort(this, incr);
        notifyObservers();
    }

    public void addElement(double element){
        double[] tempArray = new double[size+1];
        System.arraycopy(mass, 0, tempArray, 0, size);
        tempArray[size] = element;
        fillFromMass(tempArray);
        notifyObservers(size-1);
    }

    public void insertElement(int index, double element){
        if (index < 0 || index > size) {
            throw new VectorIndexOutOfBoundsException();
        }
        if (index == size){
            addElement(element);
        } else {
            double[] tempArray = new double[size + 1];
            System.arraycopy(mass, 0, tempArray, 0, index);
            tempArray[index] = element;
            System.arraycopy(mass, index, tempArray, index + 1, size - index);
            fillFromMass(tempArray);
        }
        notifyObservers(index);


    }

    public void deleteElement(int index){
        if (index < 0 || index > size-1) {
            throw new VectorIndexOutOfBoundsException();
        }
        double[] tempArray = new double[size - 1];
        System.arraycopy(mass, 0, tempArray, 0, index);
        if (index != size -1) {
            System.arraycopy(mass, index + 1, tempArray, index, size - index - 1);
        }
        fillFromMass(tempArray);
        notifyObservers(index);
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append(getElement(i));
            stringBuilder.append(" ");
        }
        return stringBuilder.toString().trim();
    }

    public boolean equals(Object obj){
        if (obj instanceof Vector){
            if (((Vector) obj).getSize() != size){
                return false;
            }
            for (int i = 0; i < size; i++) {
                return ((Vector) obj).getElement(i) == getElement(i);
            }
        }
        return  false;
    }

    public Object clone() throws CloneNotSupportedException{
        Object arrayVector = super.clone();
        ((Vector) arrayVector).fillFromMass(mass);
        return arrayVector;
    }

    @Override
    public Iterator<Double> iterator() {
        return new ArrayVectorIterator();
    }

    private class ArrayVectorIterator implements Iterator<Double>{
        int cursor = 0;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public Double next() {

            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            double[] elementData = ArrayVector.this.mass;
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
                ArrayVector.this.deleteElement(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}