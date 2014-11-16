package com.study.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MultiJabberClient {
    public static final int MAX_THREADS = 40;

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        InetAddress addr = InetAddress.getByName(null);
        while (true) {
            if (JabberClientThread.getThreadCount() < MAX_THREADS) {
                new JabberClientThread(addr);
            }
            Thread.currentThread().sleep(100);
        }
    }
}
