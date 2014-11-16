package com.study.net;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class JabberClientThread extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private static int counter = 0;
    private int id = counter++;
    private static int threadCount = 0;

    public static int getThreadCount() {
        return threadCount;
    }

    public JabberClientThread(InetAddress addr) {
        System.out.println("Mading client " + id);
        threadCount++;
        try {
            socket = new Socket(addr, MultiJabberServer.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            start();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 25; i++) {
                out.println("Client " + id + ": " + i);
                String str = in.readLine();
                System.out.println(str);
            }
            out.println("END");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            threadCount--;
        }
    }
}
