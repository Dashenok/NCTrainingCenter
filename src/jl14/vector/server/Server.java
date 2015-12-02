package jl14.vector.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by user on 23.11.2015.
 */
public class Server {
    ServerSocket servers = null;

    public static void runServer() {

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(4444);
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Accept failed: 4444");
                System.exit(-1);
            }
        } catch (IOException e) {
            System.out.println(
                    "Could not listen on port: 4444");
            System.exit(-1);
        }

    }
}
