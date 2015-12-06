package jl12.vector.container;

import jl9.vector.Vector;
import jl9.vector.container.VectorCollection;

import java.util.Set;

public class VectorSet<T extends jl12.vector.Vector> extends jl12.vector.container.VectorCollection<T> implements Set<T> {


    public VectorSet(jl12.vector.Vector[] inArr) {
        super(inArr);
    }

    @Override
    public boolean add(T o) {
        if (contains(o)){
            return false;
        }
        super.add(o);
        return true;
    }

}
