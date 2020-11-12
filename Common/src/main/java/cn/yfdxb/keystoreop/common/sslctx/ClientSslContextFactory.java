package cn.yfdxb.keystoreop.common.sslctx;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class ClientSslContextBuilder extends AbstractSslContextBuilder{
    @Override
    public SSLContext build() {
        if(context != null)
            return context;
        try {
            context = SSLContext.getInstance("TLSv1.2");
            context.init(getClientKeyManagers(), getClientTrustManagers(), null);
            return context;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    private KeyManager[] getClientKeyManagers(){
        return null;
    }

    private TrustManager[] getClientTrustManagers(){
        return null;
    }
}
