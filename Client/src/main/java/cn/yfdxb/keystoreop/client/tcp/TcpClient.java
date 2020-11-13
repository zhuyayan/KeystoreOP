package cn.yfdxb.keystoreop.client.tcp;

import cn.yfdxb.keystoreop.common.initializer.ClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcpClient {
    private String host;
    private int port;
    private Channel clientChannel;
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private Bootstrap bootstrap = new Bootstrap();
    public boolean clientStat = false;
    public TcpClient(){
        this.host = "172.16.1.142";
        this.port = 62345;
    }

    public void start() {
        try {
            bootstrap.group(bossGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
//                    .handler(new SSLChannelInitializer());
            .handler(new ClientChannelInitializer());
            //发起异步连接请求，绑定连接端口和host信息
            ChannelFuture futureChannel = bootstrap.connect(host, port).sync();
            futureChannel.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture arg0) throws Exception {
                    if (futureChannel.isSuccess()) {
                        System.out.println("连接服务器成功");
                        clientStat = true;
                        clientChannel = futureChannel.channel();
                    } else {
                        System.out.println("连接服务器失败");
                        futureChannel.cause().printStackTrace();
                        bossGroup.shutdownGracefully();
                    }
                }
            });

            //String reqStr = "我是客户端请求1$_";
            //futureChannel.channel().writeAndFlush(Unpooled.copiedBuffer(reqStr.getBytes()));




            futureChannel.channel().flush();



//            futureChannel.channel().closeFuture().addListener(cf -> {
//                System.out.println("closed: " + cf);
//            }).sync();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void close(){
        if(clientChannel != null)
            clientChannel.close();
    }
    public void sendMessage(){
        ChannelFuture future = clientChannel.writeAndFlush(
                Unpooled.copiedBuffer("Hello World!".getBytes())
        );
    }

    public void stop(){
        this.clientChannel.close().awaitUninterruptibly();
    }
}
