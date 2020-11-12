package cn.yfdxb.keystoreop.common.sslctx;

import javax.net.ssl.SSLContext;

public interface SslContextFactory {
    public SSLContext build();
}
