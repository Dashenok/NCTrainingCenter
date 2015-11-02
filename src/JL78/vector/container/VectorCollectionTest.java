package JL78.vector.container;

import JL78.vector.impl.ArrayVector;
import junit.framework.TestCase;


public class VectorCollectionTest extends TestCase{

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
        ArrayVector[] testVectorArray = {vector1, vector2};
        VectorCollection vectorCollection = new VectorCollection(testVectorArray);
        assertEquals(testVectorArray.length, vectorCollection.size());

    }

    public void testIsEmpty() throws Exception {
        fillArrays();
        ArrayVector[] emptyVectorArray = new ArrayVector[0];
        VectorCollection emptyVectorCollection = new VectorCollection(emptyVectorArray);
        assertTrue(emptyVectorCollection.isEmpty());

        ArrayVector[] testVectorArray = {vector1};
        VectorCollection vectorCollection = new VectorCollection(testVectorArray);
        assertFalse(vectorCollection.isEmpty());
    }

    public void testContains() throws Exception {
        fillArrays();
        vector3.fillFromMass(mass3);
        ArrayVector[] testVectorArray = {vector1, vector2};
        VectorCollection vectorCollection = new VectorCollection(testVectorArray);
        assertTrue(vectorCollection.contains(vector1));
        assertFalse(vectorCollection.contains(vector3));
    }

    public void testIterator() throws Exception {
//        fillArrays();
    }

    public void testToArray() throws Exception {
        fillArrays();
        ArrayVector[] testVectorArray = {vector1, vector2, vector3};
        VectorCollection vectorCollection = new VectorCollection(testVectorArray);
        Object[] arrayFromCollection = vectorCollection.toArray();
        assertEquals(testVectorArray.length, arrayFromCollection.length);
        for (int i = 0; i < arrayFromCollection.length; i++){
            assertEquals(testVectorArray[i], arrayFromCollection[i]);
        }

    }

    public void testToArrayWithObject() throws Exception {

    }

    public void testAdd() throws Exception {
        fillArrays();
        ArrayVector[] testVectorArray = {vector1, vector2};
        VectorCollection vectorCollection = new VectorCollection(testVectorArray);
        vectorCollection.add(vector3);
        Object[] arrayFromCollection = vectorCollection.toArray();
        assertEquals(vector3, arrayFromCollection[arrayFromCollection.length-1]);
    }

    public void testAddWithException() {
        fillArrays();
        ArrayVector[] testVectorArray = {vector1, vector2};
        VectorCollection vectorCollection = new VectorCollection(testVectorArray);
        int fakeVector = 10;
        try {
            vectorCollection.add(fakeVector);
        } catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    public void testRemove() throws Exception {
        fillArrays();
        ArrayVector[] testVectorArray = {vector1, vector2, vector3};
        VectorCollection vectorCollection = new VectorCollection(testVectorArray);
        int collectionLength = vectorCollection.size();
        vectorCollection.remove(vector2);

        assertFalse(vectorCollection.contains(vector2));
        assertEquals(collectionLength - 1, vectorCollection.size());
    }

    public void testRemoveWithException() {
        fillArrays();
        ArrayVector[] testVectorArray = {vector1, vector2};
        VectorCollection vectorCollection = new VectorCollection(testVectorArray);
        int fakeVector = 10;
        try {
            vectorCollection.remove(fakeVector);
        } catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    public void testAddAll() throws Exception {
        fillArrays();
        ArrayVector[] inputVectorArray = {vector1, vector2};
        ArrayVector[] testVectorArray = {vector3};
        VectorCollection inputVectorCollection = new VectorCollection(inputVectorArray);
        VectorCollection testVectorCollection = new VectorCollection(testVectorArray);
        assertTrue(testVectorCollection.addAll(inputVectorCollection));
    }

    public void testAddAllEmpty() {
        fillArrays();
        ArrayVector[] emptyVectorArray = new ArrayVector[0];
        VectorCollection emptyVectorCollection = new VectorCollection(emptyVectorArray);

        ArrayVector[] testVectorArray = {vector1};
        VectorCollection testVectorCollection = new VectorCollection(testVectorArray);
        assertFalse(testVectorCollection.addAll(emptyVectorCollection));
    }

    public void testClear() throws Exception {
        fillArrays();
        ArrayVector[] testVectorArray = {vector1, vector2};
        VectorCollection testVectorCollection = new VectorCollection(testVectorArray);
        testVectorCollection.clear();
        assertEquals(0, testVectorCollection.size());
    }

    public void testRetainAll() throws Exception {
        fillArrays();
        ArrayVector[] inputVectorArray = {vector1, vector2};
        ArrayVector[] testVectorArray = {vector2, vector3};
        VectorCollection inputVectorCollection = new VectorCollection(inputVectorArray);
        VectorCollection testVectorCollection = new VectorCollection(testVectorArray);
        assertTrue(testVectorCollection.retainAll(inputVectorCollection));
        assertEquals(1, testVectorCollection.size());
        assertTrue(testVectorCollection.contains(vector2));
    }

    public void testRetainAllEquals() {
        fillArrays();
        ArrayVector[] inputVectorArray = {vector1, vector2};
        ArrayVector[] testVectorArray = {vector1, vector2};
        VectorCollection inputVectorCollection = new VectorCollection(inputVectorArray);
        VectorCollection testVectorCollection = new VectorCollection(testVectorArray);
        int testCollectionLength = testVectorCollection.size();
        assertFalse(testVectorCollection.retainAll(inputVectorCollection));
        assertEquals(testCollectionLength, testVectorCollection.size());
    }

    public void testRemoveAll() throws Exception {
        fillArrays();
        ArrayVector[] inputVectorArray = {vector1, vector2};
        ArrayVector[] testVectorArray = {vector2, vector3};
        VectorCollection inputVectorCollection = new VectorCollection(inputVectorArray);
        VectorCollection testVectorCollection = new VectorCollection(testVectorArray);
        assertTrue(testVectorCollection.removeAll(inputVectorCollection));
        assertEquals(1, testVectorCollection.size());
        assertTrue(testVectorCollection.contains(vector3));

    }

    public void testRemoveAllEquals() {
        fillArrays();
        ArrayVector[] inputVectorArray = {vector1, vector2};
        ArrayVector[] testVectorArray = {vector1, vector2};
        VectorCollection inputVectorCollection = new VectorCollection(inputVectorArray);
        VectorCollection testVectorCollection = new VectorCollection(testVectorArray);
        assertTrue(testVectorCollection.removeAll(inputVectorCollection));
        assertTrue(testVectorCollection.isEmpty());
    }

    public void testRemoveAllDifferent() {
        fillArrays();
        ArrayVector[] inputVectorArray = {vector1};
        ArrayVector[] testVectorArray = {vector2};
        VectorCollection inputVectorCollection = new VectorCollection(inputVectorArray);
        VectorCollection testVectorCollection = new VectorCollection(testVectorArray);
        int testCollectionLength = testVectorCollection.size();
        assertFalse(testVectorCollection.removeAll(inputVectorCollection));
        assertEquals(testCollectionLength, testVectorCollection.size());
    }

    public void testContainsAll() throws Exception {
        fillArrays();
        ArrayVector[] inputVectorArray = {vector1, vector2};
        ArrayVector[] testVectorArray = {vector1, vector2, vector3};
        VectorCollection inputVectorCollection = new VectorCollection(inputVectorArray);
        VectorCollection testVectorCollection = new VectorCollection(testVectorArray);
        assertTrue(testVectorCollection.containsAll(inputVectorCollection));
    }

    public void testNotContainsAll() throws Exception {
        fillArrays();
        ArrayVector[] inputVectorArray = {vector1, vector3};
        ArrayVector[] testVectorArray = {vector1, vector2};
        VectorCollection inputVectorCollection = new VectorCollection(inputVectorArray);
        VectorCollection testVectorCollection = new VectorCollection(testVectorArray);
        assertFalse(testVectorCollection.containsAll(inputVectorCollection));
    }

    public void testContainsAllEmpty() throws Exception {
        fillArrays();
        ArrayVector[] inputVectorArray = new ArrayVector[0];
        ArrayVector[] testVectorArray = {vector1, vector2, vector3};
        VectorCollection inputVectorCollection = new VectorCollection(inputVectorArray);
        VectorCollection testVectorCollection = new VectorCollection(testVectorArray);
        assertTrue(testVectorCollection.containsAll(inputVectorCollection));
    }
}
