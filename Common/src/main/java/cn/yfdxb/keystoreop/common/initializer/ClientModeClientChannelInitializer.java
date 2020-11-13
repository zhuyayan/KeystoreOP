package cn.yfdxb.keystoreop.common.initializer;

import cn.yfdxb.keystoreop.common.handler.TCPClientHandler;
import cn.yfdxb.keystoreop.common.sslctx.ClientModeClientSslContextFactory;
import cn.yfdxb.keystoreop.common.sslctx.SslContextFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class ClientModeClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        SslContextFactory factory = new ClientModeClientSslContextFactory();
        SSLEngine engine = factory.build().createSSLEngine();
        engine.setUseClientMode(true);

        pipeline.addFirst("ssl", new SslHandler(engine));
        pipeline.addLast(new TCPClientHandler());
    }
}
