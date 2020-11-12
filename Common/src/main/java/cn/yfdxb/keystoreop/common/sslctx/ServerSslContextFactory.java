package cn.yfdxb.keystoreop.common.sslctx;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class ServerSslContextBuilder extends AbstractSslContextBuilder {
    private SSLContext context = null;
    public SSLContext getContext(){

        if(context != null)
            return context;
        try {
            context = SSLContext.getInstance("TLSv1.2");
            context.init(getServerKeyManagers(), getServerTrustManager(), null);
            return context;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    private KeyManager[] getServerKeyManagers(){
        return null;
    }

    private TrustManager[] getServerTrustManager(){
        return null;
    }

    @Override
    public SSLContext build() {
        return null;
    }
}
