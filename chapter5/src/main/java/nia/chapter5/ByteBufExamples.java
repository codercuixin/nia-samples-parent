package nia.chapter5;

import io.netty.buffer.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DummyChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ByteProcessor;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Random;

/**
 * * @Author: cuixin
 * * @Date: 2019/3/15 15:26
 */
public class ByteBufExamples {
    private static final ByteBuf BYTE_BUF_FROM_SOMEWHERE = Unpooled.buffer(1024);
    private static final Random random = new Random();
    private static final Channel CHANNEL_FORM_SOMEWHERE = new NioSocketChannel();
    private static final ChannelHandlerContext CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE = DummyChannelHandlerContext.DUMMY_INSTANCE;
    static {
        BYTE_BUF_FROM_SOMEWHERE.writeBytes("Hello World! \n\r Hello again".getBytes());
    }

    public static void main(String args[]){
//        heapBuffer();
//        directBuffer();
//        byteBufComposite();
//        byteBufCompositeArray();
//        byteBufRelativeAccess();
//        readAllData();
//        write();
//        byteProcessor();
//        byteBufSlice();
//        byteBufCopy();
//        byteBufSetGet();
//        byteBufWriteRead();
        referenceCounting();
    }

    /**
     * Listing 5.1 Backing array
     */
    public static void heapBuffer() {
        ByteBuf heapBuf = BYTE_BUF_FROM_SOMEWHERE;
        if (heapBuf.hasArray()) {
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            handleArray(array, offset, length);
        }
    }

    /**
     * Listing 5.2 Direct buffer data access
     */
    public static void directBuffer(){
        //todo 改为本地调用
        ByteBuf directBuf = BYTE_BUF_FROM_SOMEWHERE;
        if(!directBuf.hasArray()){
            int length = directBuf.readableBytes();
            byte[] arr = new byte[length];
            directBuf.getBytes(directBuf.readerIndex(), arr);
            handleArray(arr, 0, length);
        }
    }

    /**
    * Listing 5.3 Composite buffer pattern using ByteBuffer
     */
    public static void byteBufferComposite(ByteBuffer header, ByteBuffer body){
        //use an array to hold the message parts
        ByteBuffer[] message = new ByteBuffer[]{header, body};

        //Create a new ByteBuffer and use copy to merge the header and body
        ByteBuffer message2 = ByteBuffer.allocate(header.remaining() + body.remaining());
        message2.put(header);
        message2.put(body);
        message2.flip();
    }

    /**
     * Listing 5.4 Composite buffer pattern using CompositeByteBuf
     */
    public static void byteBufComposite(){
        CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
        ByteBuf headerBuf = BYTE_BUF_FROM_SOMEWHERE;
        ByteBuf bodyBuf = BYTE_BUF_FROM_SOMEWHERE;
        messageBuf.addComponents(headerBuf, bodyBuf);
        messageBuf.removeComponent(0);
        for(ByteBuf buf: messageBuf){
            System.out.println(buf.toString());
        }
    }

    /**
     * Listing 5.5 Accessing the data in a CompositeByteBuf
     */
    public static void byteBufCompositeArray(){
        CompositeByteBuf compBuf = Unpooled.compositeBuffer();
        compBuf.addComponents(BYTE_BUF_FROM_SOMEWHERE);
        int length = compBuf.readableBytes();
        byte[] array = new byte[length];
        compBuf.getBytes(compBuf.readerIndex(), array);
        handleArray(array, 0, array.length);
    }

    /**
     * Listing 5.6 Access Data
     */
    public static void byteBufRelativeAccess(){
        ByteBuf buf = BYTE_BUF_FROM_SOMEWHERE;
        for(int i=0; i<buf.capacity(); i++){
            byte b = buf.getByte(i);
            System.out.println((char)b);
        }
    }

    /**
     * Listing  5.7 Read all data
     */
    public static void readAllData(){
        ByteBuf buf = BYTE_BUF_FROM_SOMEWHERE;
        while(buf.isReadable()){
            System.out.println(buf.readByte());
        }
    }

    /**
     * Listing 5.8 Write data
     */
    public static void write(){
        ByteBuf buf = BYTE_BUF_FROM_SOMEWHERE;
        while(buf.writableBytes() >=4){
            buf.writeInt(random.nextInt());
        }
        byte[] arr =  new byte[buf.readableBytes()];
        buf.getBytes(buf.readerIndex(), arr);
        handleArray(arr, 0, arr.length);
    }

    /**
     * Listing 5.9 Using ByteProcessor to find \r
     */
    public static void byteProcessor(){
        ByteBuf buf = BYTE_BUF_FROM_SOMEWHERE;
        int index = buf.forEachByte(ByteProcessor.FIND_CR);
        System.out.println(index);
    }

    private static void handleArray(byte[] array, int offset, int len) {
        String string = new String(array, offset, len);
        System.out.println(string);
    }

    /**
     * Listing 5.10 Slice a ByteBuf
     */
    public static void byteBufSlice(){
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in action rocks!", utf8);
        ByteBuf sliced = buf.slice(0, 10);
        System.out.println(sliced.toString(utf8));
        buf.setByte(0, (byte)'J');
        assert buf.getByte(0) == sliced.getByte(0);
    }

    /**
     * Listing 5.11 Copying a ByteBuf
     */
    public static void byteBufCopy(){
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in action rocks!", utf8);
        ByteBuf copy = buf.copy(0, 10);
        System.out.println(copy.toString(utf8));
        buf.setByte(0, (byte)'J');
        assert buf.getByte(0) != copy.getByte(0);
    }

    /**
     * Listing 5.12 get() and set() usage
     */
    public static void byteBufSetGet(){
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("netty in action rocks", utf8);
        System.out.println((char)buf.getByte(0));
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        buf.setByte(0, (byte)'b');
        System.out.println((char)buf.getByte(0));
        assert readerIndex == buf.readerIndex();
        assert writerIndex == buf.writerIndex();
    }

    /**
     * Listing 5.13 read() and write() usage
     */
    public static void byteBufWriteRead(){
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("netty in action rocks", utf8);
        System.out.println((char)buf.readByte());
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        buf.writeByte((byte)'?');
        System.out.println((char)buf.getByte(0));
        assert readerIndex == buf.readerIndex();
        assert writerIndex != buf.writerIndex();
    }

    /**
     * Listing 5.14 Obtaining a ByteBufAllocator reference
     */
    public static void obtainingByteBufAllocatorReference(){
        Channel channel = CHANNEL_FORM_SOMEWHERE;
        ByteBufAllocator allocator = channel.alloc();

        //...
        ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
        ByteBufAllocator allocator1 = ctx.alloc();

    }

    /**
     * Listing 5.15 Reference Counting
     */
    public static void referenceCounting(){
        Channel channel = CHANNEL_FORM_SOMEWHERE;
        ByteBufAllocator allocator = channel.alloc();

        //....
        ByteBuf buf = allocator.directBuffer();
        assert buf.refCnt() == 1;

        boolean released = buf.release();
        assert  released;

    }
}
