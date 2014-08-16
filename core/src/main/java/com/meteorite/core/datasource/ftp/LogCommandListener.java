package com.meteorite.core.datasource.ftp;

import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.log4j.Logger;

/**
 * 记录ftp输出信息到日志文件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class LogCommandListener implements ProtocolCommandListener {
    private static Logger log = Logger.getLogger(LogCommandListener.class);

    @Override
    public void protocolCommandSent(ProtocolCommandEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("--> ");
        String cmd = event.getCommand();
        if ("PASS".equalsIgnoreCase(cmd) || "USER".equalsIgnoreCase(cmd)) {
            sb.append(cmd);
            sb.append(" *******"); // Don't bother with EOL marker for this!
        } else {
            final String IMAP_LOGIN = "LOGIN";
            if (IMAP_LOGIN.equalsIgnoreCase(cmd)) { // IMAP
                String msg = event.getMessage();
                msg=msg.substring(0, msg.indexOf(IMAP_LOGIN)+IMAP_LOGIN.length());
                sb.append(msg);
                sb.append(" *******"); // Don't bother with EOL marker for this!
            } else {
                sb.append(event.getMessage());
            }
        }
        log.debug(sb.toString().replace("\r\n", ""));
    }

    @Override
    public void protocolReplyReceived(ProtocolCommandEvent event) {
        log.debug("<-- " + event.getMessage().replace("\r\n", ""));
    }

}