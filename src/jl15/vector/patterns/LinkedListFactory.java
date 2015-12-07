package jl15.vector.patterns;

import jl15.vector.Vector;
import jl15.vector.VectorFactory;
import jl15.vector.impl.LinkedVector;


public class LinkedListFactory implements VectorFactory {
    @Override
    public Vector createVector() {
        return new LinkedVector();
    }

    @Override
    public Vector createVector(int size) {
        LinkedVector linkedVector = new LinkedVector();
        for (int i = 0; i < size; i++) {
            linkedVector.addElement(0);
        }
        return linkedVector;
    }
}
