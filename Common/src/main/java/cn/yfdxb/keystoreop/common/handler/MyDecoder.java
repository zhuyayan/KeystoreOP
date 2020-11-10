package cn.yfdxb.keystoreop.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.ByteBuffer;
import java.util.List;

public class MyDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in != null)
        {
            ByteBuffer msg = null;
            try {
                if(in.readableBytes() > 0 ){
                    msg = ByteBuffer.allocate(in.readableBytes()) ;
                    byte[] bb = new byte[in.readableBytes()] ;
                    in.readBytes(bb) ;
                    msg.put(bb);
                    msg.flip();
                }
            } catch (Exception e) {
                e.printStackTrace();
                msg = null ;
            }
            if (msg != null) {
                out.add(msg);
            }
        }
    }
}
