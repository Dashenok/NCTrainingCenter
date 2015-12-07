package jl15.vector.patterns;

import jl15.vector.Vector;
import jl15.vector.VectorFactory;
import jl15.vector.impl.ArrayVector;

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
