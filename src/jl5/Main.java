package jl5;

import jl5.vector.*;
import jl5.vector.impl.*;

import java.io.*;

public class Main{
    public static void main(String[] args)  throws IOException{
        double[] mass = {1.0, 0.9, -6.4, 8, -0.4};
        ArrayVector arrayVectorInstance = new ArrayVector(5);
        LinkedVector linkedVectorInstance = new LinkedVector();
        arrayVectorInstance.fillFromMass(mass);
        linkedVectorInstance.fillFromMass(mass);

        double[] mass1 = {6.0, 1.9, 100.4, 7, -5.4};
        ArrayVector arrayVectorInstance1 = new ArrayVector(5);
        LinkedVector linkedVectorInstance1 = new LinkedVector();
        arrayVectorInstance1.fillFromMass(mass1);
        linkedVectorInstance1.fillFromMass(mass1);




        // test bin date
        ObjectOutputStream outBin = new ObjectOutputStream(new FileOutputStream("out.bin"));
        Vectors.outputVector(arrayVectorInstance, outBin);
        Vectors.outputVector(arrayVectorInstance1, outBin);
        outBin.close();

        ObjectInputStream inBin = new ObjectInputStream(new FileInputStream("out.bin"));
        Vector vector = Vectors.inputVector(inBin);
        System.out.println(vector);
        Vector vector1 = Vectors.inputVector(inBin);
        System.out.println(vector1);
        inBin.close();

        // test txt date
        BufferedWriter outText = new BufferedWriter(new FileWriter("out.txt"));
        Vectors.writeVector(arrayVectorInstance, outText);
        Vectors.writeVector(arrayVectorInstance1, outText);
        Vectors.writeVector(arrayVectorInstance1, outText);
        outText.close();

        BufferedReader inText = new BufferedReader(new FileReader("out.txt"));
        Vector vectorText = Vectors.readVector(inText);
		System.out.println(vectorText);
        Vector vectorText1 = Vectors.readVector(inText);
        System.out.println(vectorText1);
        Vector vectorText2 = Vectors.readVector(inText);
        System.out.println(vectorText2);
        inText.close();


        // test Serializable
        ObjectOutputStream outSerializableArray = new ObjectOutputStream(new FileOutputStream("outSerializableArray.bin"));
        outSerializableArray.writeObject(arrayVectorInstance);
        outSerializableArray.close();

        ObjectInputStream inArray = new ObjectInputStream(new FileInputStream("outSerializableArray.bin"));
        Object ArrayVectorDeserialisable = null;
        try {
            ArrayVectorDeserialisable = inArray.readObject();
            System.out.println(ArrayVectorDeserialisable);
            inArray.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ObjectOutputStream outSerializableLinked = new ObjectOutputStream(new FileOutputStream("outSerializableLinked.bin"));
        outSerializableLinked.writeObject(linkedVectorInstance);
        outSerializableLinked.close();

        ObjectInputStream inLinked = new ObjectInputStream(new FileInputStream("outSerializableLinked.bin"));
        Object linkedVectorDeserializable = null;
        try {
            linkedVectorDeserializable = inLinked.readObject();
            System.out.println(linkedVectorDeserializable);
            inLinked.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}