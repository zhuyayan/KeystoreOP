package cn.yfdxb.keystoreop.common.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TCPServerHandler extends ChannelInboundHandlerAdapter {
    public TCPServerHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object source) throws Exception {
        // 拿到传过来的msg数据，开始处理
        // 转化为ByteBuf
        ByteBuf recvmg = (ByteBuf) source;
        // 收到及发送，这里如果没有writeAndFlush，上面声明的ByteBuf需要ReferenceCountUtil.release主动释放
        //ctx.writeAndFlush(source);
        byte[] bytes = new byte[recvmg.readableBytes()];
    }
}