package JL78.vector;

import JL78.vector.exceptions.IncompatibleVectorSizesException;

public interface Vector {

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
}