package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.oio.OioSocketChannel;

import java.net.InetSocketAddress;

/**
 * * @Author: cuixin
 * * @Date: 2019/3/19 13:48
 * Listing 8.3 Incompatible Channel and EventLoopGroup
 */
public class InvalidBootstrapClient {
    public static void main(String args[]){
        InvalidBootstrapClient client = new InvalidBootstrapClient();
        client.bootstrap();
    }
    public void bootstrap(){
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(OioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.baidu.com", 80));
        future.syncUninterruptibly();
    }
}
