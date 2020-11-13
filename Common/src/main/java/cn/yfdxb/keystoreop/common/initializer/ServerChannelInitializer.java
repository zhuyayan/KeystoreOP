package cn.yfdxb.keystoreop.common.initializer;

import cn.yfdxb.keystoreop.common.coder.MessagePacketDecoder;
import cn.yfdxb.keystoreop.common.coder.MessagePacketEncoder;
import cn.yfdxb.keystoreop.common.handler.TCPServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("idleStateHandler",
                new IdleStateHandler(15, 0, 0, TimeUnit.MINUTES));

        pipeline.addLast(
                new MessagePacketDecoder(),
                new MessagePacketEncoder()
        );

        // 自定义Hadler
        pipeline.addLast("handler",new TCPServerHandler());
        // 自定义Hander,可用于处理耗时操作，不阻塞IO处理线程
        //pipeline.addLast(group,"BussinessHandler",new BussinessHandler());

    }
}
