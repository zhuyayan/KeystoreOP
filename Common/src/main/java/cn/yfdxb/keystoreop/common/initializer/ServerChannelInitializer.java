package cn.yfdxb.keystoreop.common.initializer;

import cn.yfdxb.keystoreop.common.coder.MessagePacketDecoder;
import cn.yfdxb.keystoreop.common.coder.MessagePacketEncoder;
import cn.yfdxb.keystoreop.common.handler.NettySocketSSLHandler;
import cn.yfdxb.keystoreop.common.handler.TCPServerHandler;
import cn.yfdxb.keystoreop.common.sslctx.ServerSslContextFactory;
import cn.yfdxb.keystoreop.common.sslctx.SslContextFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;

import javax.net.ssl.SSLEngine;
import java.util.concurrent.TimeUnit;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("idleStateHandler",
                new IdleStateHandler(15, 0, 0, TimeUnit.MINUTES));

        SslContextFactory factory = new ServerSslContextFactory();
        SSLEngine engine = factory.build().createSSLEngine();
        engine.setUseClientMode(false);

        // 需要客户端认证
        engine.setNeedClientAuth(false);
        pipeline.addFirst("ssl", new SslHandler(engine));

        pipeline.addLast("ssl_handler" , new NettySocketSSLHandler());

        // 自定义Hadler
        pipeline.addLast("tcp_handler",new TCPServerHandler());
        // 自定义Hander,可用于处理耗时操作，不阻塞IO处理线程
        //pipeline.addLast(group,"BussinessHandler",new BussinessHandler());

        pipeline.addLast(
                new MessagePacketDecoder(),
                new MessagePacketEncoder()
        );
    }
}
