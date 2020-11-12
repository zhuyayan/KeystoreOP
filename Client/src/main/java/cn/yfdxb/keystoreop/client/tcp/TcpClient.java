package cn.yfdxb.keystoreop.client.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TcpClient {
    private String host;
    private int port;
    private Channel channel;
    public TcpClient(){
        this.host = "172.16.1.142";
        this.port = 62345;
    }

    public void start(){
        try {
            final EventLoopGroup group = new NioEventLoopGroup();

            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .handler(new SSLChannelInitializer());

            //发起异步连接请求，绑定连接端口和host信息
            final ChannelFuture future = b.connect(host, port).sync();

            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture arg0) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("连接服务器成功");
                    } else {
                        System.out.println("连接服务器失败");
                        future.cause().printStackTrace();
                        group.shutdownGracefully();
                    }
                }
            });
            this.channel = future.channel();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void sendMessage(){
        ChannelFuture future = this.channel.writeAndFlush("Hello World!\n");
        try {
            future.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        this.channel.close().awaitUninterruptibly();
    }
}
