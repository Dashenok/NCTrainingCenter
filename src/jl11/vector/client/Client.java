package jl11.vector.client;

import jl11.vector.Vector;
import jl11.vector.Vectors;
import jl11.vector.impl.ArrayVector;
import jl11.vector.impl.LinkedVector;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void startClient(String fileWithVectors, String outputFile) throws IOException {

        int serverPort = 8888; // здесь обязательно нужно указать порт к которому привязывается сервер.
        String host = "localhost"; // это IP-адрес компьютера, где исполняется наша серверная программа.

        //try {
            System.out.println("Any of you heard of a socket with host " + host + " and port " + serverPort + "?");
            Socket socket = new Socket(host, serverPort); // создаем сокет используя host и порт сервера.
            System.out.println("Yes! I just got hold of the program.");

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();


            BufferedReader inText = new BufferedReader(new FileReader(fileWithVectors));
            while (true) {
               try {
                    Vector vectorText1 = Vectors.readVector(inText);
                    Vector vectorText2 = Vectors.readVector(inText);
                   if ((vectorText1.toString().equals("")) || (vectorText2.toString().equals(""))) {
                       break;
                   }
                    Vectors.outputVector(vectorText1, sout);
                    Vectors.outputVector(vectorText2, sout);

                    Vector summedVector = Vectors.inputVector(sin);
                    BufferedWriter outText = new BufferedWriter(new FileWriter(outputFile));
                    Vectors.writeVector(summedVector, outText);

                    //outText.close();
                } catch (Exception e){
                    sout.flush();
                    break;
                }

          }

            //}
        //} catch (Exception x) {
          //  x.printStackTrace();
        //}
    }
    public static void main(String[] args) throws IOException{

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


        BufferedWriter outText = new BufferedWriter(new FileWriter("out.txt"));
        Vectors.writeVector(arrayVectorInstance, outText);
        Vectors.writeVector(arrayVectorInstance1, outText);
        Vectors.writeVector(arrayVectorInstance1, outText);
        Vectors.writeVector(arrayVectorInstance1, outText);
        outText.close();

        Client.startClient("out.txt", "vectorSum.txt");
    }

}
