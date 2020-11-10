package cn.yfdxb.keystoreop.common.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetAddress;
import java.nio.ByteBuffer;

public class NettySocketSSLHandler extends SimpleChannelInboundHandler<ByteBuffer> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuffer msg) throws Exception {
        // Once session is secured, send a greeting and register the channel to the global channel
        // list so the channel received the messages from others.

        ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
                new GenericFutureListener<Future<Channel>>() {
                    @Override
                    public void operationComplete(Future<Channel> future) throws Exception {
                        if(future.isSuccess()){
                            System.out.println("握手成功");
                            byte[] array = new byte[]{ (byte)7d,  04} ;
                            ByteBuffer bu = ByteBuffer.wrap(array) ;
                            ctx.channel().writeAndFlush(bu) ;
                        }else{
                            System.out.println("握手失败");
                        }
                        ctx.writeAndFlush(
                                "Welcome to " + InetAddress.getLocalHost().getHostName() +
                                        " secure chat service!\n");
                        ctx.writeAndFlush(
                                "Your session is protected by " +
                                        ctx.pipeline().get(SslHandler.class).engine().getSession().getCipherSuite() +
                                        " cipher suite.\n");
                    }
                });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx)
            throws Exception {
        System.out.println("服务端增加");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx){
        System.out.println("移除:"+ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Unexpected exception from downstream.");
        ctx.close();
    }

//    @Override
//    public void messageReceived(ChannelHandlerContext ctx, ByteBuffer msg) throws Exception {
//        System.out.println("服务端receive msg ");
//        byte[] array = new byte[]{00, 01, 00, 00, 00, 06, 05, 03, (byte)7d, 00, 00, 07} ;
//        ByteBuffer bu = ByteBuffer.wrap(array) ;
//        ctx.channel().writeAndFlush(bu) ;
//    }
}
