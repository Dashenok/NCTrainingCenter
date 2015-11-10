package jl8.vector.container;

import jl8.vector.Vector;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class VectorSet extends VectorCollection  implements Set {


    public VectorSet(Vector[] inArr) {
      /*  Vector[] newVectorArr = new Vector[0];
        VectorCollection vectorCollection = new VectorCollection(newVectorArr);
        for (int i = 0; i < inArr.length; i++) {
            if (!(vectorCollection.contains(inArr[i]))){
                vectorCollection.add(inArr[i]);
            }
        }*/
        super(inArr);
    }

    @Override
    public boolean add(Object o) {
        if (contains(o)){
            return false;
        }
        if (o instanceof Vector) {
            Vector[] tempArray = new Vector[arr.length];
            System.arraycopy(arr, 0, tempArray, 0, arr.length);
            arr = new Vector[arr.length + 1];
            System.arraycopy(tempArray, 0, arr, 0, arr.length-1);
            arr[arr.length-1] = (Vector)o;
            return true;
        } else {
            throw new ClassCastException();
        }
    }

}
