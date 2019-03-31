package nia.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @author cuixin on 2019/3/27
 **/
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {
    private final boolean client;
    public HttpPipelineInitializer(boolean client){
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if(client){
            //client decode the response sent by server
            pipeline.addLast("decoder", new HttpResponseDecoder());
            //client encode the request and send to server
            pipeline.addLast("encoder", new HttpRequestEncoder());
        }else{
            //server decode the request sent by client
            pipeline.addLast("decoder", new HttpRequestDecoder());
            //server encode the response and send to client
            pipeline.addLast("encoder", new HttpResponseEncoder());
        }
    }
}
