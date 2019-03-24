package nia.chapter10;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @author cuixin on 2019/3/24
 **/
public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
    public CombinedByteCharCodec(){
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}
