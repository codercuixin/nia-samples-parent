package nia.chapter12;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

/**
 * @author cuixin on 2019/3/31
 **/
public class SecureChatServer extends ChatServer {
    private final SslContext sslContext;

    public SecureChatServer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        return new SecureChatServerInitializer(group,sslContext);
    }

    public static void main(String args[]) {
        if(args.length !=1){
            System.err.println("please give port as argument");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        try {
            SelfSignedCertificate cert = new SelfSignedCertificate();
            SslContext sslContext = SslContext.newServerContext(cert.certificate(), cert.privateKey());
            final SecureChatServer endpoint = new SecureChatServer(sslContext);
            ChannelFuture future = endpoint.start(new InetSocketAddress(port));
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    endpoint.destory();
                }
            });
            future.channel().closeFuture().syncUninterruptibly();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
