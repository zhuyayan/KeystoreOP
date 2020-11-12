package cn.yfdxb.keystoreop.client.tcp;

import cn.yfdxb.keystoreop.common.sslctx.ClientSslContextFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;
import javax.net.ssl.SSLEngine;

public class SSLChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
//        SSLEngine engine = ContextSSLFactory.getSslContext2().createSSLEngine();
        ClientSslContextFactory builder = new ClientSslContextFactory();
        SSLEngine engine = builder.build().createSSLEngine();
        engine.setUseClientMode(true);
        engine.setNeedClientAuth(false);
//        pipeline.addLast(new ClientEncoder());
//        pipeline.addLast(new ClientDecoder());
//        pipeline.addLast("ssl", new SslHandler(engine));
        System.out.println("connect...");
    }
}
