package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;

/**
 * * @Author: cuixin
 * * @Date: 2019/3/15 16:07
 */
public class DummyChannelHandlerContext extends AbstractChannelHandlerContext {
    public static ChannelHandlerContext DUMMY_INSTANCE = new DummyChannelHandlerContext(
            null,
            null,
            "Dummy",
            true,
            true
    );
    public DummyChannelHandlerContext(DefaultChannelPipeline pipeline,
                                      EventExecutor executor,
                                      String name, boolean inbound, boolean outbound) {
        super(pipeline, executor, name, inbound, outbound);
    }

    @Override
    public ChannelHandler handler() {
        return null;
    }

}
