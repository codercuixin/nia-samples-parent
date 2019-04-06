package nia.chapter13;

import java.net.InetSocketAddress;

/**
 * @author cuixin on 2019/3/31
 **/
public class LogEvent {

    public static final byte SEPARATOR = ':';
    /**
     * 返回发送消息的源头
     */
    private final InetSocketAddress source;
    /**
     * 来自哪个日志文件
     */
    private final String logfile;
    /**
     * 消息内容
     */
    private final String msg;
    /**
     * 接收消息的时间
     */
    private final long received;

    public LogEvent(String logfile, String msg) {
        this(null, -1, logfile, msg);
    }

    public LogEvent(InetSocketAddress source, long received, String logfile, String msg) {
        this.source = source;
        this.logfile = logfile;
        this.msg = msg;
        this.received = received;
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public long getReceived() {
        return received;
    }

    public String getLogfile() {
        return logfile;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "LogEvent{" +
                "source=" + source +
                ", logfile='" + logfile + '\'' +
                ", msg='" + msg + '\'' +
                ", received=" + received +
                '}';
    }
}
