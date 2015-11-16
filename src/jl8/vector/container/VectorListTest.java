package jl8.vector.container;

import jl8.vector.Vector;
import jl8.vector.impl.ArrayVector;
import org.junit.*;

import java.util.Collection;

import static org.junit.Assert.*;

public class VectorListTest {
    private double[] mass = {1.0, 0.9, -6.4, 8, -0.4};
    private double[] mass2 = {-6.4, 8, -0.4};
    private double[] mass3 = {2.0, 5, -0.6};
    private ArrayVector vector1 = new ArrayVector(5);
    private ArrayVector vector2 = new ArrayVector(3);
    private ArrayVector vector3 = new ArrayVector(3);

    @Before
    public void setUp() throws Exception {
        vector1.fillFromMass(mass);
        vector2.fillFromMass(mass2);
        vector3.fillFromMass(mass3);

    }

    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void testAddAll() throws Exception {
        Collection col = new VectorList(new Vector[0]);
        col.add(vector1);
        col.add(null);
        col.add(vector3);
        setUp();
        ArrayVector[] testVectorArray = {vector1, vector2};
        VectorList vectorList = new VectorList(testVectorArray);
        vectorList.addAll(1, col);
        assertArrayEquals(new Vector[]{vector1, vector2, vector1, null, vector3}, vectorList.arr);
    }


    @Test
    public void testGet() throws Exception {
    }

    @Test
    public void testSet() throws Exception {

    }

    @Test
    public void testAdd() throws Exception {

    }

    @Test
    public void testRemove() throws Exception {
    }

    @Test
    public void testLastIndexOf() throws Exception {



    }

    @Test
    public void testSubList() throws Exception {

    }
}