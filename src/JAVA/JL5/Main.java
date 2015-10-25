package JAVA.JL5;

import JAVA.JL5.Vector.*;
import JAVA.JL5.Vector.impl.*;

import java.io.*;

public class Main{
    public static void main(String[] args)  throws IOException{
        double[] mass = {1.0, 0.9, -6.4, 8, -0.4};
        ArrayVector arrayVectorInstance = new ArrayVector(5);
        LinkedVector linkedVectorInstance = new LinkedVector();
        arrayVectorInstance.fillFromMass(mass);
        linkedVectorInstance.fillFromMass(mass);


        ObjectOutputStream outBin = new ObjectOutputStream(new FileOutputStream("out.bin"));
        Vectors.outputVector(arrayVectorInstance, outBin);
        outBin.close();

        ObjectInputStream inBin = new ObjectInputStream(new FileInputStream("out.bin"));
        Vector vector = Vectors.inputVector(inBin);
        System.out.println(vector);
        inBin.close();

        BufferedWriter outText = new BufferedWriter(new FileWriter("out.txt"));
        Vectors.writeVector(arrayVectorInstance, outText);
        outText.close();

        BufferedReader inText = new BufferedReader(new FileReader("out.txt"));
        Vector vectorText = Vectors.readVector(inText);
		System.out.println(vectorText);
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
        Object linkedVectorDeserialisable = null;
        try {
            linkedVectorDeserialisable = inLinked.readObject();
            System.out.println(linkedVectorDeserialisable);
            inLinked.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}