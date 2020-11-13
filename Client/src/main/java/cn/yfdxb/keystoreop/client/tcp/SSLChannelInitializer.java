package cn.yfdxb.keystoreop.client.tcp;

import cn.yfdxb.keystoreop.common.sslctx.ClientModeClientSslContextFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import javax.net.ssl.SSLEngine;

public class SSLChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
//        SSLEngine engine = ContextSSLFactory.getSslContext2().createSSLEngine();
        ClientModeClientSslContextFactory builder = new ClientModeClientSslContextFactory();
        SSLEngine engine = builder.build().createSSLEngine();
        engine.setUseClientMode(true);
        engine.setNeedClientAuth(false);
//        pipeline.addLast(new ClientEncoder());
//        pipeline.addLast(new ClientDecoder());
//        pipeline.addLast("ssl", new SslHandler(engine));
        System.out.println("connect...");
    }
}
