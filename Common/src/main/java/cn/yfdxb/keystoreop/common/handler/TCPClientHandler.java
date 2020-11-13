package cn.yfdxb.keystoreop.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class TCPClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        try {
            ByteBuf bb = (ByteBuf)msg;
            byte[] respByte = new byte[bb.readableBytes()];
            bb.readBytes(respByte);
        } finally{
            // 必须释放msg数据
            ReferenceCountUtil.release(msg);
        }

    }
}
