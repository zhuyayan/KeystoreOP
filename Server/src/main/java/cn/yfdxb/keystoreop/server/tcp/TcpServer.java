package cn.yfdxb.keystoreop.server.tcp;

import cn.yfdxb.keystoreop.common.initializer.ClientModeServerChannelInitializer;
import cn.yfdxb.keystoreop.common.initializer.ServerModeServerChannelInitializer;
import cn.yfdxb.keystoreop.common.listener.ServerChannelListener;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcpServer {
    private static SslHandler sslHandler = null;

    private EventLoopGroup bossGroup = null;

    private EventLoopGroup workerGroup = null;
    public void run(){
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        try
        {
            ServerBootstrap serverStrap = new ServerBootstrap();
            serverStrap.group(bossGroup , workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000 * 5 * 60)
                    .childHandler(new ServerModeServerChannelInitializer());
            ChannelFuture channelFuture = serverStrap.bind(62345).sync();//
            channelFuture.addListener(new ServerChannelListener());
        } catch(Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
