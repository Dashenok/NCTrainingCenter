package jl9.vector.container;

import jl9.vector.impl.ArrayVector;
import junit.framework.TestCase;

public class VectorMapTest extends TestCase {

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

    public void testSize() throws Exception {
        fillArrays();
        VectorMap vectorMap = new VectorMap();
        vectorMap.clear();
        vectorMap.put(1, vector2);
        vectorMap.put(2, vector3);
        assertEquals(vectorMap.size(), 2);
    }

    public void testIsEmpty() throws Exception {
        fillArrays();
        VectorMap vectorMap = new VectorMap();
        vectorMap.clear();
        assertTrue(vectorMap.isEmpty());
    }

    public void testIsNotEmpty() throws Exception {
        fillArrays();
        VectorMap vectorMap = new VectorMap();
        vectorMap.clear();
        vectorMap.put(1, vector2);
        assertFalse(vectorMap.isEmpty());
    }

    public void testContainsKey() throws Exception {

    }

    public void testContainsValue() throws Exception {

    }

    public void testGet() throws Exception {

    }

    public void testPut() throws Exception {

    }

    public void testRemove() throws Exception {

    }

    public void testPutAll() throws Exception {

    }

    public void testClear() throws Exception {

    }

    public void testKeySet() throws Exception {

    }

    public void testValues() throws Exception {

    }

    public void testEntrySet() throws Exception {

    }
}