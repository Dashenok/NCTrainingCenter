package jl15.vector;

import jl15.vector.exceptions.IncompatibleVectorSizesException;

import java.util.Iterator;

public interface Vector extends Iterable<Double>{

    double getElement(int index);

    void setElement(int index, double element);

    int getSize();

    void fillFromMass(double[] mass);

    void fillFromVector(Vector v);

    void mult(double a);

    void sum(Vector v) throws IncompatibleVectorSizesException;

    void addElement(double element);

    void insertElement(int index, double element);

    void deleteElement(int index);

    String toString();

    boolean equals(Object obj);

    Object clone() throws CloneNotSupportedException;

    Iterator iterator();
}