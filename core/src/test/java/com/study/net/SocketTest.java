package com.study.net;

import java.io.*;
import java.net.Socket;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class SocketTest {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.133.1", 6000);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        pw.write("Test......");
        pw.flush();
        String line = br.readLine();
        System.out.println(line);
        br.close();
        /*InputStream is = socket.getInputStream();
        for(int i = 0; i < 1000; i++) {
            System.out.println("i = " + i);
            System.out.println(is.read());
        }
        is.close();*/
        socket.close();
    }
}
