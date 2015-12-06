package jl14.vector.patterns;

import jl14.vector.Vector;
import jl14.vector.VectorFactory;
import jl14.vector.impl.ArrayVector;

public class ArrayVectorFactory implements VectorFactory {
    @Override
    public Vector createVector() {
        return new ArrayVector(0);
    }

    @Override
    public Vector createVector(int size) {
        return new ArrayVector(size);
    }
}
