package jl13.vector.impl;

import jl13.vector.Vector;
import jl13.vector.VectorFactory;


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
