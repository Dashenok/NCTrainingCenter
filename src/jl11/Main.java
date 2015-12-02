package jl11;

import jl11.vector.client.Client;
import jl5.vector.Vectors;
import jl5.vector.impl.ArrayVector;
import jl5.vector.impl.LinkedVector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
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




        /*// test bin date
        ObjectOutputStream outBin = new ObjectOutputStream(new FileOutputStream("out.bin"));
        Vectors.outputVector(arrayVectorInstance, outBin);
        Vectors.outputVector(arrayVectorInstance1, outBin);
        outBin.close();

        ObjectInputStream inBin = new ObjectInputStream(new FileInputStream("out.bin"));
        Vector vector = Vectors.inputVector(inBin);
        System.out.println(vector);
        Vector vector1 = Vectors.inputVector(inBin);
        System.out.println(vector1);
        inBin.close();*/

        // test txt date
        BufferedWriter outText = new BufferedWriter(new FileWriter("out.txt"));
        Vectors.writeVector(arrayVectorInstance, outText);
        Vectors.writeVector(arrayVectorInstance1, outText);
        Vectors.writeVector(arrayVectorInstance1, outText);
        Vectors.writeVector(arrayVectorInstance1, outText);
        outText.close();
        //Server.runServer();
        Client.startClient("out.txt", "vectorSum.txt");
       /* Vector vectorText = Vectors.readVector(inText);
		System.out.println(vectorText);
        Vector vectorText1 = Vectors.readVector(inText);
        System.out.println(vectorText1);
        Vector vectorText2 = Vectors.readVector(inText);
        System.out.println(vectorText2);
        inText.close();*/

    }

}