package com.study.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class WhoAmI {
    public static void main(String[] args) throws UnknownHostException {
        if (args.length != 1) {
            System.err.println("Usage: WhoAmI MachineName");
            System.exit(1);
        }
        InetAddress a = InetAddress.getByName(args[0]);
        System.out.println(a);
    }
}
