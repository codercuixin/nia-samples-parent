package nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author cuixin on 2019/3/24
 **/
public class AbsIntegerEncoderTest {
    @Test
    public  void testAbsEncoded(){
        ByteBuf byteBuf = Unpooled.buffer();
        for(int i=1; i< 10; i++){
            byteBuf.writeInt( i * -1);
        }
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new AbsIntegerEncoder());
        embeddedChannel.writeOutbound(byteBuf);

        for(int i=1;i<10; i++){
           assertEquals(i, embeddedChannel.readOutbound());
        }
        assertNull(embeddedChannel.readOutbound());
    }
}