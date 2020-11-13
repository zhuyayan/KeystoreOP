package cn.yfdxb.keystoreop.server.tcp;

import cn.yfdxb.keystoreop.common.sslctx.ServerSslContextFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLEngine;

@Slf4j
public class SSLChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        SSLEngine engine = new ServerSslContextFactory().build().createSSLEngine();
        engine.setUseClientMode(true);
        pipeline.addFirst("ssl", new SslHandler(engine));
        log.info("connect...");
    }
}
