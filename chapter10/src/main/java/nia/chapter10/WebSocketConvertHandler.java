package nia.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.*;

import java.util.List;

/**
 * @author cuixin on 2019/3/24
 **/
public class WebSocketConvertHandler extends MessageToMessageCodec<WebSocketFrame, WebSocketConvertHandler.MyWebSocketFrame> {


    @Override
    protected void encode(ChannelHandlerContext ctx, MyWebSocketFrame msg, List<Object> out) throws Exception {
        ByteBuf payload = msg.getData().duplicate().retain();

        switch (msg.getType()) {
            case BINARY:
                out.add(new BinaryWebSocketFrame(payload));
                break;
            case TEXT:
                out.add(new TextWebSocketFrame(payload));
                break;
            case CLOSE:
                out.add(new CloseWebSocketFrame(true, 0, payload));
                break;
            case CONTINUATION:
                out.add(new ContinuationWebSocketFrame(payload));
                break;
            case PING:
                out.add(new PingWebSocketFrame(payload));
                break;
            case PONG:
                out.add(new PongWebSocketFrame(payload));
                break;
            default:
                throw new IllegalStateException("Unsupported web socket msg " + msg);

        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        ByteBuf payload = msg.content().duplicate().retain();
        if(msg instanceof  BinaryWebSocketFrame){
            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.BINARY, payload));
        }else if(msg instanceof  TextWebSocketFrame){
            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.TEXT, payload));
        }
        else if(msg instanceof  CloseWebSocketFrame){
            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.CLOSE, payload));
        }
        else if(msg instanceof  ContinuationWebSocketFrame){
            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.CONTINUATION, payload));
        }
        else if(msg instanceof  PingWebSocketFrame){
            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.PING, payload));
        }
        else if(msg instanceof  PongWebSocketFrame){
            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.PONG, payload));
        }else {
            throw new IllegalStateException("Unsupported web socket msg "+msg);
        }
    }

    public static final class MyWebSocketFrame {
        private final FrameType type;
        private final ByteBuf data;
        public MyWebSocketFrame(FrameType type, ByteBuf data) {
            this.type = type;
            this.data = data;
        }

        public ByteBuf getData() {
            return data;
        }

        public FrameType getType() {
            return type;
        }

        public enum FrameType {
            /**
             * 二进制
             */
            BINARY,
            CLOSE,
            PING,
            PONG,
            TEXT,
            CONTINUATION
        }
    }
}
