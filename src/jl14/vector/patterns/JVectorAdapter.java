package jl14.vector.patterns;

import jl14.vector.Vector;
import jl14.vector.exceptions.*;

import java.util.Arrays;

public class JVectorAdapter implements  jl14.vector.Vector {

    private java.util.Vector adaptee;


    public JVectorAdapter(java.util.Vector adaptee) {
        this.adaptee = adaptee;
    }


    @Override
    public double getElement(int index) {
        Object returnedObject = adaptee.elementAt(index);
        if (returnedObject instanceof Double) {
            return (Double)returnedObject;
        }else {
            return 0;
        }
    }

    @Override
    public void setElement(int index, double element) {
        adaptee.setElementAt(element, index);
    }

    @Override
    public int getSize() {
        return adaptee.capacity();
    }

    @Override
    public void fillFromMass(double[] mass) {
        adaptee.clear();
        adaptee.addAll(Arrays.asList(mass));
    }

    @Override
    public void fillFromVector(Vector v) {
        adaptee.clear();
        double[] arr = new double[v.getSize()];
        for (int i = 0; i < v.getSize(); i++) {
            arr[i] = v.getElement(i);
        }
        adaptee.addAll(Arrays.asList(arr));
    }

    @Override
    public void mult(double a) {

        for (int i = 0; i < adaptee.size(); i++) {
            setElement(i, getElement(i)*a);
        }

    }

    @Override
    public void sum(Vector v) throws IncompatibleVectorSizesException {
        if (v.getSize() != adaptee.size()){
            throw new IncompatibleVectorSizesException();
        }
        for (int i = 0; i < adaptee.size(); i++) {
            setElement(i, getElement(i)*v.getElement(i));
        }

    }

    @Override
    public void addElement(double element) {
        adaptee.addElement(element);
    }

    @Override
    public void insertElement(int index, double element) {
        adaptee.insertElementAt(element, index);
    }

    @Override
    public void deleteElement(int index) {
        adaptee.removeElementAt(index);

    }

    public Object clone() throws CloneNotSupportedException {
        JVectorAdapter vectorAdapter = (JVectorAdapter) super.clone();
        java.util.Vector clonedAdaptee = (java.util.Vector)adaptee.clone();
        vectorAdapter.adaptee = clonedAdaptee;
        return vectorAdapter;
    }

    public boolean equals(Object obj){
        if (obj instanceof Vector){
            if (((Vector) obj).getSize() != adaptee.size()){
                return false;
            }
            for (int i = 0; i < adaptee.size(); i++) {
                if (!(((Vector) obj).getElement(i) == getElement(i)))
                return false;
            }
        }
        return  true;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < adaptee.size(); i++) {
            stringBuilder.append(getElement(i));
            stringBuilder.append(" ");
        }
        return stringBuilder.toString().trim();
    }
}
