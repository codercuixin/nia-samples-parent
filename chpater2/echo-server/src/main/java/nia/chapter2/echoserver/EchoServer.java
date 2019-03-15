package nia.chapter2.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * * @Author: cuixin
 * * @Date: 2019/3/14 18:34
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        //由于使用NIO传输，所以置顶NioEventLoopGroup来接收和处理新的连接。
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            //异步地绑定服务器，调用sync()方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync();
            System.out.println(EchoServer.class.getName() + " started and listening for connection on " + f.channel().localAddress());
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        if(args.length!=1){
            System.err.println("Usage: "+EchoServer.class.getSimpleName() +" <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

}
