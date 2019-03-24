package nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author cuixin on 2019/3/24
 **/
public class FrameChunkDecoderTest {
    @Test
    public void testFramesDecodes(){
        ByteBuf buf = Unpooled.buffer();
        for(int i=0; i<9; i++){
            buf.writeByte(i);
        }
        ByteBuf duplicatedBuf = buf.duplicate();

        //write inbound
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FrameChunkDecoder(3));
        assertTrue(embeddedChannel.writeInbound(duplicatedBuf.readBytes(2)));
        try{
            embeddedChannel.writeInbound(duplicatedBuf.readBytes(4));
            Assert.fail();
        }catch (TooLongFrameException e){
            //ignore
        }
        embeddedChannel.writeInbound(duplicatedBuf.readBytes(3));

        //read inboud and compare
        //read  first object
        ByteBuf readByteBuf = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(2), readByteBuf);
        //read second object
        readByteBuf = embeddedChannel.readInbound();
        assertEquals(buf.skipBytes(4).readSlice(3), readByteBuf);

    }

}