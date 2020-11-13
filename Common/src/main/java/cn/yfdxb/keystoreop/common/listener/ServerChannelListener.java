package cn.yfdxb.keystoreop.common.listener;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerChannelListener implements ChannelFutureListener {
    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            log.info("服务已开启");
        } else {
            log.info("连接服务器失败");
            future.cause().printStackTrace();
        }
    }
}
