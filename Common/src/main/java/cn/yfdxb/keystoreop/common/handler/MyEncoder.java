package cn.yfdxb.keystoreop.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteBuffer;

public class MyEncoder extends MessageToByteEncoder<ByteBuffer> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuffer msg, ByteBuf out) throws Exception {
        if(msg == null)
            return;
        if(msg.hasArray()){
            byte[] message = msg.array();
            if(message == null || message.length <= 0)
                return;
            out.writeBytes(message);
        }
    }
}
