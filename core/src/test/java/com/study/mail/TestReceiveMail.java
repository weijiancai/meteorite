package com.study.mail;

import com.sun.mail.imap.IMAPFolder;

import javax.mail.*;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Properties;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class TestReceiveMail {
    private static boolean verbose = true;

    public static void main(String[] args) throws UnsupportedEncodingException {
        try {
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

            // Get a Properties object
            Properties props = System.getProperties();

            props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.pop3.socketFactory.fallback", "false");
            props.setProperty("mail.pop3.port", "995");
            props.setProperty("mail.pop3.socketFactory.port", "995");

            // Get a Session object
            Session session = Session.getInstance(props, null);
            session.setDebug(true);

            // Get a Store object
            URLName urln = new URLName("pop3", "pop.exmail.qq.com", 995, null, "weijc@ectongs.com", "wjcectongs2013#");
            Store store = session.getStore(urln);
            store.connect();

            Folder rf = store.getDefaultFolder();
            dumpFolder(rf, false, "");
        /*if ((rf.getType() & Folder.HOLDS_FOLDERS) != 0) {
            Folder[] f = rf.list(pattern);
            for (int i = 0; i < f.length; i++)
                dumpFolder(f[i], recursive, "    ");
        }*/

            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void dumpFolder(Folder folder, boolean recurse, String tab)
            throws Exception {
        System.out.println(tab + "Name:      " + folder.getName());
        System.out.println(tab + "Full Name: " + folder.getFullName());
        System.out.println(tab + "URL:       " + folder.getURLName());

        if (verbose) {
            if (!folder.isSubscribed())
                System.out.println(tab + "Not Subscribed");

            if ((folder.getType() & Folder.HOLDS_MESSAGES) != 0) {
                if (folder.hasNewMessages()) {
                    System.out.println(tab + "Has New Messages");
                }
                System.out.println(tab + "Total Messages:  " + folder.getMessageCount());
                System.out.println(tab + "New Messages:    " + folder.getNewMessageCount());
                System.out.println(tab + "Unread Messages: " + folder.getUnreadMessageCount());
            }
            if ((folder.getType() & Folder.HOLDS_FOLDERS) != 0) {
                System.out.println(tab + "Is Directory");
            }

	    /*
         * Demonstrate use of IMAP folder attributes
	     * returned by the IMAP LIST response.
	     */
            if (folder instanceof IMAPFolder) {
                IMAPFolder f = (IMAPFolder) folder;
                String[] attrs = f.getAttributes();
                if (attrs != null && attrs.length > 0) {
                    System.out.println(tab + "IMAP Attributes:");
                    for (int i = 0; i < attrs.length; i++) {
                        System.out.println(tab + "    " + attrs[i]);
                    }
                }
            }
        }

        System.out.println();

        if ((folder.getType() & Folder.HOLDS_FOLDERS) != 0) {
            if (recurse) {
                Folder[] f = folder.list();
                for (int i = 0; i < f.length; i++) {
                    dumpFolder(f[i], recurse, tab + "    ");
                }
            }
        }
    }
}
