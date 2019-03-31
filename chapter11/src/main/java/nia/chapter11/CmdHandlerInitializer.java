package nia.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @author cuixin on 2019/3/30
 **/
public class CmdHandlerInitializer extends ChannelInitializer<Channel> {
    private static final byte SPACE = (byte) ' ';

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new CmdDecoder( 64 * 1024));
        pipeline.addLast(new CmdHandler());
    }

    private static final class Cmd{
        private final ByteBuf name;
        private final ByteBuf args;
        public Cmd(ByteBuf name, ByteBuf args) {
            this.name = name;
            this.args = args;
        }

        public ByteBuf getName() {
            return name;
        }

        public ByteBuf getArgs() {
            return args;
        }

        @Override
        public String toString() {
            return "Cmd{" +
                    "name=" + name +
                    ", args=" + args +
                    '}';
        }
    }

    public static final class CmdDecoder extends LineBasedFrameDecoder{
         public CmdDecoder(int maxLength){
             super(maxLength);
         }

        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
            ByteBuf frame = (ByteBuf) super.decode(ctx, buffer);
            if(frame == null){
                return null;
            }
            int index = frame.indexOf(frame.readerIndex(), frame.writerIndex(), SPACE);
            return new Cmd(frame.slice(frame.readerIndex(), index), frame.slice(index+1, frame.writerIndex()));
        }
    }

    public static final class CmdHandler extends SimpleChannelInboundHandler<Cmd>{
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Cmd msg) throws Exception {
            //do something with cmd
            System.out.println(msg);
        }
    }
}
