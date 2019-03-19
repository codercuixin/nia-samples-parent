package nia.chapter6;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DummyChannelPipeline;

/**
 * * @Author: cuixin
 * * @Date: 2019/3/18 16:11
 */
public class ModifyChannelPipeline {
    private static final ChannelPipeline CHANNEL_PIPELINE_FORM_SOMEWHERE = DummyChannelPipeline.DUMMY_INSTANCE;

    /**
     * Listing 6.5 Modify the ChannelPipeline
     */
    public static void modifyPipeline(){
        ChannelPipeline pipeline = CHANNEL_PIPELINE_FORM_SOMEWHERE;
        FirstHandler firstHandler = new FirstHandler();
        pipeline.addLast("handler1", firstHandler);
        pipeline.addFirst("handler2", new SecondHandler());
        pipeline.addLast("handler3", new SecondHandler());
        //...
        pipeline.remove("handler3");
        pipeline.remove(firstHandler);
        pipeline.replace("handler2", "handler4", new FourthHandler());
    }

    private static final class FirstHandler extends ChannelHandlerAdapter {}
    private static final class SecondHandler extends ChannelHandlerAdapter {}
    private static final class ThirdHandler extends ChannelHandlerAdapter {}
    private static final class FourthHandler extends ChannelHandlerAdapter {}


}
