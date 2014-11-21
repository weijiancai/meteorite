package com.meteorite.core.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Socket Mail
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class SocketMail {
    private String server;
    private String userName;
    private String password;
    private int port;

    public void receive() {
        Socket client = null;

        try {
            client = new Socket(server, port);
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
