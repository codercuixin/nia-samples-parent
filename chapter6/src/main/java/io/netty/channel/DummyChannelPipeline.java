package io.netty.channel;

/**
 * * @Author: cuixin
 * * @Date: 2019/3/18 16:12
 */
public class DummyChannelPipeline extends DefaultChannelPipeline {
    public static final ChannelPipeline DUMMY_INSTANCE = new DummyChannelPipeline(null);
    public DummyChannelPipeline(Channel channel) {
        super(channel);
    }
}
