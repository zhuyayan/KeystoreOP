package cn.yfdxb.keystoreop.common.tcp;

import cn.yfdxb.keystoreop.common.sslctx.ClientSslContextFactory;
import cn.yfdxb.keystoreop.common.sslctx.SslContextFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;
import javax.net.ssl.SSLEngine;

public class SSLChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        SslContextFactory factory = new ClientSslContextFactory();
        SSLEngine engine = factory.build().createSSLEngine();
        engine.setUseClientMode(true);
        pipeline.addFirst("ssl", new SslHandler(engine));
        System.out.println("connect...");
    }
}
