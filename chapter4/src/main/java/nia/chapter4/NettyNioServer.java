package nia.chapter4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * * @Author: cuixin
 * * @Date: 2019/3/15 11:05
 */
public class NettyNioServer {
    public void server(int port) throws InterruptedException {
        final ByteBuf buf =  Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(
                "Hi!\r\n", Charset.forName("UTF-8")));
        NioEventLoopGroup group = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(group).channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                   ctx.writeAndFlush(buf.duplicate())
                                           .addListener(ChannelFutureListener.CLOSE);
                                }
                            });
                        }
                    });
            ChannelFuture f = b.bind().sync();
            ((ChannelFuture) f).channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }
}
