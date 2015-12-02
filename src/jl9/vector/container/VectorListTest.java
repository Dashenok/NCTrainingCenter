package jl9.vector.container;

import jl9.vector.impl.ArrayVector;
import junit.framework.TestCase;

import java.util.List;


public class VectorListTest extends TestCase {

    private double[] mass = {1.0, 0.9, -6.4, 8, -0.4};
    private double[] mass2 = {-6.4, 8, -0.4};
    private double[] mass3 = {2.0, 5, -0.6};
    private double[] mass4 = {5.0, 4, 0.5, -10};
    private ArrayVector vector1 = new ArrayVector(5);
    private ArrayVector vector2 = new ArrayVector(3);
    private ArrayVector vector3 = new ArrayVector(3);
    private ArrayVector vector4 = new ArrayVector(4);

    private void fillArrays(){
        vector1.fillFromMass(mass);
        vector2.fillFromMass(mass2);
        vector3.fillFromMass(mass3);
        vector4.fillFromMass(mass4);
    }


    public void testAddAll() throws Exception {
        fillArrays();
        ArrayVector[] testVectorArray = {vector1, vector2};
        ArrayVector[] inputVectorArray = {vector3, vector4};
        ArrayVector[] expectedVectorArray = {vector1, vector3, vector4, vector1};
        VectorCollection vectorCollection = new VectorCollection(inputVectorArray);
        VectorList vectorList = new VectorList(testVectorArray);
        assertTrue(vectorList.addAll(1, vectorCollection));

        Object[] result = vectorList.toArray();
        for (int i = 0; i < result.length; i++) {
            assertEquals(result[i], expectedVectorArray[i]);
        }
    }

    public void testGet() throws Exception {
        fillArrays();
        ArrayVector[] testVectorArray = {vector1, vector2, vector3};
        VectorList vectorList = new VectorList(testVectorArray);
        assertEquals(vectorList.get(1), vector2);
    }

    public void testGetOutOfIndex() throws Exception {
        fillArrays();
        ArrayVector[] testVectorArray = {vector1, vector2, vector3};
        VectorList vectorList = new VectorList(testVectorArray);
        vectorList.get(3);
    }

    public void testSet() throws Exception {
        fillArrays();
        ArrayVector[] testVectorArray = {vector1, vector2, vector3};
        VectorList vectorList = new VectorList(testVectorArray);
        assertEquals(vectorList.set(1, vector4), vector2);
        Object[] result = vectorList.toArray();
        assertEquals(result[1], vector4);
    }

    public void testAdd() throws Exception {
        fillArrays();
        ArrayVector[] testVectorArray = {vector1, vector2, vector3};
        VectorList vectorList = new VectorList(testVectorArray);
        vectorList.add(1, vector4);
        Object[] result = vectorList.toArray();
        assertEquals(result.length, testVectorArray.length + 1);
        assertEquals(result[1], vector4);
    }

    public void testRemove() throws Exception {
        fillArrays();
        ArrayVector[] testVectorArray = {vector1, vector2, vector3, vector4};
        VectorList vectorList = new VectorList(testVectorArray);
        vectorList.remove(1);
        Object[] result = vectorList.toArray();
        assertEquals(result.length, testVectorArray.length - 1);
        assertEquals(result[1], vector3);
    }

    public void testIndexOf() throws Exception {
        fillArrays();
        ArrayVector[] testVectorArray = {vector3, vector2, vector3, vector4};
        VectorList vectorList = new VectorList(testVectorArray);
        assertEquals(vectorList.indexOf(vector3), 0);
    }

    public void testIndexOfNonExistingElement() throws Exception {
        fillArrays();
        ArrayVector[] testVectorArray = {vector1, vector2, vector4};
        VectorList vectorList = new VectorList(testVectorArray);
        assertEquals(vectorList.indexOf(vector3), -1);
    }

    public void testLastIndexOf() throws Exception {
        fillArrays();
        ArrayVector[] testVectorArray = {vector3, vector2, vector3, vector4};
        VectorList vectorList = new VectorList(testVectorArray);
        assertEquals(vectorList.lastIndexOf(vector3), 2);
    }

    public void testLastIndexOfNonExistingElement() throws Exception {
        fillArrays();
        ArrayVector[] testVectorArray = {vector1, vector2, vector4};
        VectorList vectorList = new VectorList(testVectorArray);
        assertEquals(vectorList.lastIndexOf(vector3), -1);
    }

    public void testSubList() throws Exception {
        fillArrays();
        ArrayVector[] testVectorArray = {vector1, vector2, vector3, vector4};
        VectorList vectorList = new VectorList(testVectorArray);
        ArrayVector[] expectedVectorArray = {vector2, vector3};
        List subList = vectorList.subList(1, 3);
        Object[] result = subList.toArray();
        for (int i = 0; i < expectedVectorArray.length; i++){
            assertEquals(expectedVectorArray[i], result[i]);
        }
    }
}