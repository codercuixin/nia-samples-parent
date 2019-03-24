package nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author cuixin on 2019/3/24
 **/
public class FixedLengthFrameDecoderTest {
    @Test
    public void testFrameDecoded(){
        ByteBuf buf = Unpooled.buffer();
        for(int i=0; i<9; i++){
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

        //write bytes
        assertTrue(embeddedChannel.writeInbound(input.retain()));
        assertTrue(embeddedChannel.finish());


        //read bytes
        ByteBuf read = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

    }


    @Test
    public void testFramesDecode2(){
        ByteBuf buf = Unpooled.buffer();
        for(int i=0; i< 9;i++){
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assertFalse(channel.writeInbound(input.readBytes(2)));
        assertTrue(channel.writeInbound(input.readBytes(7)));
        assertTrue(channel.finish());

        ByteBuf read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();


        read = channel.readInbound();
        assertNull(read);



    }
}