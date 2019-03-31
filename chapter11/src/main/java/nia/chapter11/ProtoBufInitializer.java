package nia.chapter11;

import com.google.protobuf.MessageLite;
import io.netty.channel.*;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * @author cuixin on 2019/3/30
 **/
public class ProtoBufInitializer extends ChannelInitializer<Channel> {
    private MessageLite messageLite;

    public ProtoBufInitializer(MessageLite messageLite) {
        this.messageLite = messageLite;
    }

    @Override
    protected void initChannel(Channel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder(), new ProtobufEncoder(), new ProtobufDecoder(messageLite), new ObjectHandler());
    }

    public static final class ObjectHandler extends SimpleChannelInboundHandler<Object>{
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
            //do something with msg
        }
    }
}
