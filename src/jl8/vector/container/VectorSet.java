package jl8.vector.container;

import jl8.vector.Vector;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class VectorSet extends VectorCollection  implements Set {


    public VectorSet(Vector[] inArr) {
        super(inArr);
    }

    @Override
    public boolean add(Object o) {
        if (contains(o)){
            return false;
        }
        super.add(o);
        return true;
    }

}
