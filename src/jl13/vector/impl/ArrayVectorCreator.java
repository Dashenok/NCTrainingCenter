package jl13.vector.impl;

import jl13.vector.Vector;
import jl13.vector.VectorFactory;

public class ArrayVectorCreator implements VectorFactory {
    @Override
    public Vector createVector() {
        return new ArrayVector(0);
    }

    @Override
    public Vector createVector(int size) {
        return new ArrayVector(size);
    }
}
