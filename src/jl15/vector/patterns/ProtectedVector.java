package jl15.vector.patterns;

import jl15.vector.Vector;
import jl15.vector.exceptions.IncompatibleVectorSizesException;

import java.util.Iterator;

/**
 * Created by user on 02.12.2015.
 */
public class ProtectedVector implements Vector {

    private Vector protectedVect;

    public ProtectedVector(Vector protectedV) {
        this.protectedVect = protectedV;
    }

    @Override
    public double getElement(int index) {
        return protectedVect.getElement(index);
    }

    @Override
    public void setElement(int index, double element) {

    }

    @Override
    public int getSize() {
        return protectedVect.getSize();
    }

    @Override
    public void fillFromMass(double[] mass) {

    }

    @Override
    public void fillFromVector(Vector v) {

    }

    @Override
    public void mult(double a) {

    }

    @Override
    public void sum(Vector v) throws IncompatibleVectorSizesException {

    }

    @Override
    public void addElement(double element) {

    }

    @Override
    public void insertElement(int index, double element) {

    }

    @Override
    public void deleteElement(int index) {

    }

    public Object clone() throws CloneNotSupportedException {
        ProtectedVector protectedVectorClone = (ProtectedVector) super.clone();
        Vector clonedProtectedV = (Vector)protectedVect.clone();
        protectedVectorClone.protectedVect = clonedProtectedV;
        return protectedVectorClone;
    }

    @Override
    public Iterator iterator() {
        return protectedVect.iterator();
    }

    public String toString(){
        return protectedVect.toString();
    }

    public boolean equals(Object obj){
        return super.equals(obj);
    }
}
