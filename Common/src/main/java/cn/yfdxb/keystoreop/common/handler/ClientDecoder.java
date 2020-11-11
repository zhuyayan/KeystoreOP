package cn.yfdxb.keystoreop.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ClientDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int dataLength = in.readInt();
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        Object obj = data.toString();
        out.add(obj);
    }
}
