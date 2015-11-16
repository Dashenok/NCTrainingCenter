package jl8.vector.container;

import jl8.vector.Vector;
import jl8.vector.impl.ArrayVector;
import org.junit.*;

import java.util.Collection;

import static org.junit.Assert.*;

public class VectorSetTest {
    private double[] mass = {1.0, 0.9, -6.4, 8, -0.4};
    private double[] mass2 = {-6.4, 8, -0.4};
    private double[] mass3 = {2.0, 5, -0.6};
    private ArrayVector vector1 = new ArrayVector(5);
    private ArrayVector vector2 = new ArrayVector(3);
    private ArrayVector vector3 = new ArrayVector(3);

    private void fillArrays(){
        vector1.fillFromMass(mass);
        vector2.fillFromMass(mass2);
        vector3.fillFromMass(mass3);
    }

    @Test
    public void testAdd() throws Exception {
        fillArrays();
        ArrayVector[] testVectorArray = {vector1, vector2};
        VectorSet vectorSet = new VectorSet(testVectorArray);

        assertTrue(vectorSet.add(null));
        assertFalse(vectorSet.add(null));
        Vector a = new ArrayVector(2);
        Vector b = new ArrayVector(1);
        assertTrue(vectorSet.add(a));
        assertFalse(vectorSet.add(a));
        assertFalse(vectorSet.add(null));
        assertTrue(vectorSet.add(b));
        assertArrayEquals(new Vector[]{null, a, b}, vectorSet.arr);
    }

}