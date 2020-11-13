package cn.yfdxb.keystoreop.common.initializer;

import cn.yfdxb.keystoreop.common.sslctx.ServerModeClientSslContextFactory;
import cn.yfdxb.keystoreop.common.sslctx.SslContextFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class ServerModeClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        SslContextFactory factory = new ServerModeClientSslContextFactory();
        SSLEngine engine = factory.build().createSSLEngine();
        engine.setUseClientMode(false);
        engine.setNeedClientAuth(false);
        pipeline.addFirst("server_mode_client_ssl", new SslHandler(engine));
    }
}
