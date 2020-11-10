package cn.yfdxb.keystoreop.server.tcp;

import cn.yfdxb.keystoreop.common.handler.MyDecoder;
import cn.yfdxb.keystoreop.common.handler.MyEncoder;
import cn.yfdxb.keystoreop.common.handler.NettySocketSSLHandler;
import cn.yfdxb.keystoreop.common.tcp.ContextSSLFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class TcpServer {
    private static SslHandler sslHandler = null ;

    private EventLoopGroup bossGroup = null ;

    private EventLoopGroup workerGroup = null ;
    public void run(){
        bossGroup = new NioEventLoopGroup() ;
        workerGroup = new NioEventLoopGroup() ;

        try{
            ServerBootstrap serverStrap = new ServerBootstrap() ;
            serverStrap.group(bossGroup , workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000 * 5 * 60)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pie = socketChannel.pipeline() ;
                            pie.addLast("decoder" , new MyDecoder());
                            pie.addLast("encoder" , new MyEncoder());
                            pie.addLast("handler" , new NettySocketSSLHandler()) ;
                            SSLEngine engine = ContextSSLFactory.getSslContext().createSSLEngine();
                            engine.setUseClientMode(false);
                            engine.setNeedClientAuth(true);
                            pie.addFirst("ssl", new SslHandler(engine));
                        }
                    });
            serverStrap.bind(62345).sync();
            System.out.println("服务已开启");
        }catch(Exception e){
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
