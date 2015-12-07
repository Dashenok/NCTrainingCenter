package jl14.vector.client;

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


        BufferedReader inTextClient = new BufferedReader(new FileReader(fileWithVectors));
        BufferedWriter outTextClient = new BufferedWriter(new FileWriter(outputFile));
        while (true) {
            try {
                jl12.vector.Vector vectorText1 = jl12.vector.Vectors.readVector(inTextClient);
                jl12.vector.Vector vectorText2 = jl12.vector.Vectors.readVector(inTextClient);
                if ((vectorText1.toString().equals("")) || (vectorText2.toString().equals(""))) {
                    break;
                }
                jl12.vector.Vectors.outputVector(vectorText1, sout);
                jl12.vector.Vectors.outputVector(vectorText2, sout);

                jl12.vector.Vector summedVector = jl12.vector.Vectors.inputVector(sin);

                jl12.vector.Vectors.writeVector(summedVector, outTextClient);

                //outText.close();
            } catch (Exception e){

                sout.flush();
                break;
            }


        }


        outTextClient.close();
    }
    public static void main(String[] args) throws IOException{

        double[] mass = {1.0, 0.9, -6.4, 8, -0.4};
        jl12.vector.impl.ArrayVector arrayVectorInstance = new jl12.vector.impl.ArrayVector(5);
        jl12.vector.impl.LinkedVector linkedVectorInstance = new jl12.vector.impl.LinkedVector();
        arrayVectorInstance.fillFromMass(mass);
        linkedVectorInstance.fillFromMass(mass);

        double[] mass1 = {6.0, 1.9, 100.4, 7, -5.4};
        jl12.vector.impl.ArrayVector arrayVectorInstance1 = new jl12.vector.impl.ArrayVector(5);
        jl12.vector.impl.LinkedVector linkedVectorInstance1 = new jl12.vector.impl.LinkedVector();
        arrayVectorInstance1.fillFromMass(mass1);
        linkedVectorInstance1.fillFromMass(mass1);


        BufferedWriter outText = new BufferedWriter(new FileWriter("out.txt"));
        jl12.vector.Vectors.writeVector(arrayVectorInstance, outText);
        jl12.vector.Vectors.writeVector(arrayVectorInstance1, outText);
        jl12.vector.Vectors.writeVector(arrayVectorInstance1, outText);
        jl12.vector.Vectors.writeVector(arrayVectorInstance1, outText);
        outText.close();

        Client.startClient("out.txt", "vectorSum.txt");
    }

}
