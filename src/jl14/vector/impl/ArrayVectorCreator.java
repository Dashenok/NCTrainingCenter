package jl14.vector.impl;

import jl14.vector.Vector;
import jl14.vector.VectorFactory;

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
