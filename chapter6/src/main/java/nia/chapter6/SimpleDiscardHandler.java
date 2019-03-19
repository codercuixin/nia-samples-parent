package nia.chapter6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * * @Author: cuixin
 * * @Date: 2019/3/18 8:53
 * SimpleChannelInboundHandler会自动释放资源
 */
public class SimpleDiscardHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //No need to do anything special.
    }
}
