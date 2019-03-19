package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * * @Author: cuixin
 * * @Date: 2019/3/19 13:38
 * Listing 8.1 Bootstrapping a client
 */
public class BootstrapClient {
    public static void main(String args[]){
        BootstrapClient client = new BootstrapClient();
        client.bootstrap();
    }
    public void bootstrap(){
        final EventLoopGroup group = new NioEventLoopGroup();
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Client Received data: " + msg);
                    }

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        ctx.writeAndFlush("Hello Server");
                    }
                });
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", 8080));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("Connection established");
                }else{
                    System.err.println("Connection attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }
}
