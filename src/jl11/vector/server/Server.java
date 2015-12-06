package jl11.vector.server;

import jl11.vector.Vectors;
import jl11.vector.Vector;

import java.io.*;
import java.net.*;


/**
 * Created by user on 23.11.2015.
 */
public class Server {
    ServerSocket servers = null;

    public static void runServer() {
        int port = 8888;
        try {
            ServerSocket ss = new ServerSocket(port, 0, InetAddress.getByName("localhost")); // создаем сокет сервера и привязываем его к вышеуказанному порту
            System.out.println("Waiting for a client...");

            Socket socket = ss.accept(); // заставляем сервер ждать подключений и выводим сообщение когда кто-то связался с сервером
            System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
            System.out.println();


            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();
            while (true) {
                System.out.println("000");
                Vector vector1 = Vectors.inputVector(sin);
                System.out.println("111");
                Vector vector2 = Vectors.inputVector(sin);
                System.out.println("222");
                vector1.sum(vector2);
                Vectors.outputVector(vector1, sout);
            }

        } catch(Exception x) { x.printStackTrace(); }

    }
    public static void main(String[] args) {
        Server.runServer();
    }
}
