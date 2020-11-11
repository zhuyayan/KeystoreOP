package cn.yfdxb.keystoreop.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ClientEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if(msg instanceof String){
            out.writeInt(((String) msg).getBytes().length);
            out.writeBytes(((String) msg).getBytes());
        }
    }
}
