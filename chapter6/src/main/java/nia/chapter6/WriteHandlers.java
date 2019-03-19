package nia.chapter6;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

/**
 * * @Author: cuixin
 * * @Date: 2019/3/18 17:32
 */
public class WriteHandlers {
    private static final ChannelHandlerContext CHANNEL_HANDLER_FORM_SOMEWHERE = DummyChannelHandlerContext.DUMMY_INSTANCE;
    private static final ChannelPipeline CHANNEL_PIPELINE_FORM_SOMEWHERE = DummyChannelPipeline.DUMMY_INSTANCE;

    /**
     * Listing 6.6 Accessing the Channel from a ChannelHandlerContext
     */
    public static void writeViaChannel(){
        ChannelHandlerContext ctx = CHANNEL_HANDLER_FORM_SOMEWHERE;
        Channel channel = ctx.channel();
        channel.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8));
    }

    /**
     * Listing 6.7 Accessing the ChannelPipeline form a ChannelHandlerContext
     */
    public static void  writeViaChannelPipeline(){
        ChannelHandlerContext ctx = CHANNEL_HANDLER_FORM_SOMEWHERE;
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8));
    }

}
