package cn.yfdxb.keystoreop.client.tcp;

import cn.yfdxb.keystoreop.common.tcp.ContextSSLFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;
import javax.net.ssl.SSLEngine;

public class SSLChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        SSLEngine engine = ContextSSLFactory.getSslContext2().createSSLEngine();
        engine.setUseClientMode(true);
        pipeline.addFirst("ssl", new SslHandler(engine));
        System.out.println("connect...");
    }
}
