package cn.yfdxb.keystoreop.common.sslctx;

import javax.net.ssl.SSLContext;

public abstract class AbstractSslContextBuilder implements SslContextFactory{
    protected SSLContext context;
    public abstract SSLContext build();
}
