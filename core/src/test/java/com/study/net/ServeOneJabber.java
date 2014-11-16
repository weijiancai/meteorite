package com.study.net;

import java.io.*;
import java.net.Socket;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class ServeOneJabber extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ServeOneJabber(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // 自动刷新
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String str = in.readLine();
                if (str.equals("END")) {
                    break;
                }
                System.out.println("Echoing: " + str);
                out.println(str);
            }
            System.out.println("closing...");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Socket not closed");
            }
        }
    }
}
