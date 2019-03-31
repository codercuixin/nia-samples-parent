package nia.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author cuixin on 2019/3/30
 **/
public class LengthBaseInitializer extends ChannelInitializer<Channel> {


    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(64*1024,  0, 8));
        pipeline.addLast(new FrameHandler());
    }


    public static class FrameHandler extends SimpleChannelInboundHandler<ByteBuf>{
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            //do something with msg
        }
    }
}
