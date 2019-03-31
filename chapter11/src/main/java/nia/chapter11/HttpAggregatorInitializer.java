package nia.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author cuixin on 2019/3/27
 * Listing 11.3 Automically aggregting Http message fragment
 **/
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {
    private final boolean isClient;
    public HttpAggregatorInitializer(boolean isClient){
        this.isClient = isClient;
    }
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        if(isClient){
            channelPipeline.addLast("codec", new HttpClientCodec());
        }else{
            channelPipeline.addLast("codec", new HttpServerCodec());
        }
        channelPipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
    }
}
