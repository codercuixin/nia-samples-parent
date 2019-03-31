package nia.chapter11;

import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author cuixin on 2019/3/30
 * Listing 11.11 Transferring file contents directly with DefaultFileRegion
 **/
public class FileRegionWriteHandler extends ChannelInboundHandlerAdapter {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
    private static final File FILE_FROM_SOMEWHERE = new File("");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final File file = FILE_FROM_SOMEWHERE;
        Channel channel = CHANNEL_FROM_SOMEWHERE;

        FileInputStream in = new FileInputStream(file);
        FileRegion region = new DefaultFileRegion(file, 0, file.length());
        channel.writeAndFlush(region).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(!future.isSuccess()){
                    Throwable cause = future.cause();
                    System.err.print(cause);
                }
            }
        });
    }
}
