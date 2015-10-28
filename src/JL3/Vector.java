package JL3;

import JL3.exceptions.*;

public interface Vector {

    double getElement(int index);

    void setElement(int index, double element);

    int getSize();

    void fillFromMass(double[] mass);

    void fillFromVector(Vector v);

    void mult(double a);

    void sum(Vector v) throws IncompatibleVectorSizesException;

    boolean equal(Vector v);
}