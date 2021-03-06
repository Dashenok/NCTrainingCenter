package jl12.vector.impl;

import jl11.vector.Vector;
import jl11.vector.Vectors;
import jl11.vector.exceptions.IncompatibleVectorSizesException;
import jl11.vector.exceptions.VectorIndexOutOfBoundsException;

import java.io.Serializable;

public class ArrayVector implements jl12.vector.Vector, Cloneable, Serializable {
    protected int size;
    protected double[] mass;
    public ArrayVector(int size) throws jl12.vector.exceptions.VectorIndexOutOfBoundsException {
        if (size < 0) {
            throw new jl12.vector.exceptions.VectorIndexOutOfBoundsException();
        }
        this.size = size;
        this.mass = new double[size];
    }

    @Override
    public double getElement(int index){
        if (index < 0 || index > size-1){
            throw new jl12.vector.exceptions.VectorIndexOutOfBoundsException();
        }
        return  mass[index];
    }

    @Override
    public void setElement(int index, double element){
        if (index < 0 || index > size-1){
            throw new jl12.vector.exceptions.VectorIndexOutOfBoundsException();
        }
        mass[index] = element;
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
    }

    public void fillFromVector(jl12.vector.Vector newVector){

        int newMassLength = newVector.getSize();
        if (size != newMassLength){
            size = newMassLength;
            mass = new double[size];
        }
        for (int i = 0; i < size; i++) {
            mass[i] = newVector.getElement(i);
        }
    }

    public boolean equal(jl12.vector.Vector newVector){

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
    }

    public void sum(jl12.vector.Vector newVector) throws jl12.vector.exceptions.IncompatibleVectorSizesException {


        if (size != newVector.getSize()){
            throw new jl12.vector.exceptions.IncompatibleVectorSizesException();
        }

        for (int i = 0; i < size; i++) {
            mass[i] = mass[i] + newVector.getElement(i);
        }
    }

    public void sort(boolean incr){
        jl12.vector.Vectors.sort(this, incr);
    }

    public void addElement(double element){
        double[] tempArray = new double[size+1];
        System.arraycopy(mass, 0, tempArray, 0, size);
        tempArray[size] = element;
        fillFromMass(tempArray);
    }

    public void insertElement(int index, double element){
        if (index < 0 || index > size) {
            throw new jl12.vector.exceptions.VectorIndexOutOfBoundsException();
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

    }

    public void deleteElement(int index){
        if (index < 0 || index > size-1) {
            throw new jl12.vector.exceptions.VectorIndexOutOfBoundsException();
        }
        double[] tempArray = new double[size - 1];
        System.arraycopy(mass, 0, tempArray, 0, index);
        if (index != size -1) {
            System.arraycopy(mass, index + 1, tempArray, index, size - index - 1);
        }
        fillFromMass(tempArray);
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
        if (obj instanceof jl12.vector.Vector){
            if (((jl12.vector.Vector) obj).getSize() != size){
                return false;
            }
            for (int i = 0; i < size; i++) {
                return ((jl12.vector.Vector) obj).getElement(i) == getElement(i);
            }
        }
        return  false;
    }

    public Object clone() throws CloneNotSupportedException{
        Object arrayVector = super.clone();
        ((jl12.vector.Vector) arrayVector).fillFromMass(mass);
        return arrayVector;
    }
}