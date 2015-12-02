package jl11.vector.client;

import jl11.vector.Vector;
import jl11.vector.Vectors;

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
            //while (true) {
            //    try {
                    Vector vectorText1 = Vectors.readVector(inText);
                    Vector vectorText2 = Vectors.readVector(inText);
                    Vectors.outputVector(vectorText1, sout);
                    Vectors.outputVector(vectorText2, sout);

                    Vector summedVector = Vectors.inputVector(sin);
                    BufferedWriter outText = new BufferedWriter(new FileWriter(outputFile));
                    Vectors.writeVector(summedVector, outText);
          //      } catch (Exception e){
          //          sout.flush();
          //          break;
          //      }
               // if ((vectorText1.equals("")) || (vectorText2.equals(""))){
            //        break;
                //}

            //}
       // } catch (Exception x) {
       //     x.printStackTrace();
       // }
    }


}
