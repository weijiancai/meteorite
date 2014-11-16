package com.study.net;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class JabberClient {
    public static void main(String[] args) throws IOException {
        InetAddress addr = InetAddress.getByName(null);
//        InetAddress addr = InetAddress.getByName("127.0.0.1");
//        InetAddress addr = InetAddress.getByName("localhost");
//        InetAddress addr = InetAddress.getLocalHost();
        System.out.println("addr = " + addr);

        Socket socket = new Socket(addr, JabberServer.PORT);
        try {
            System.out.println("socket = " + socket);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            for (int i = 0; i < 10; i++) {
                out.println("howbd " + i);
                String str = in.readLine();
                System.out.println(str);
            }
            out.println("END");
        } finally {
            System.out.println("closing...");
            socket.close();
        }
    }
}
