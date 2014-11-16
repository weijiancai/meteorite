package com.study.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MultiJabberServer {
    public static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Server Started");
        try {
            while (true) {
                Socket socket = s.accept();
                try {
                    new ServeOneJabber(socket);
                } catch (IOException e) {
                    socket.close();
                }
            }
        } finally {
            s.close();
        }
    }
}
