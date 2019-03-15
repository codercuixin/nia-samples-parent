package nia.chapter2.echoclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * * @Author: cuixin
 * * @Date: 2019/3/14 18:02
 */
public class EchoClient {
    private final String host;
    private final int port;
    public EchoClient(String host, int port){
        this.host = host;
        this.port = port;
    }
    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    //设置服务器的地址
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            //连接到远程节点，阻塞等待直到连接完成
            ChannelFuture f = b.connect().sync();
            //阻塞，直到Channel关闭
            f.channel().closeFuture().sync();
        }
        finally {
            //关闭线程并且释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String args[]) throws InterruptedException {
        if(args.length !=2){
            System.err.println("Usage: "+EchoClient.class.getSimpleName() +" <host> <port>");
            return;
        }
        final String host = args[0];
        final int port = Integer.parseInt(args[1]);
        new EchoClient(host, port).start();

    }
}
