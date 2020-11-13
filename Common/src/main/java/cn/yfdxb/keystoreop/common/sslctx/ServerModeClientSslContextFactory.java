package cn.yfdxb.keystoreop.common.sslctx;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Slf4j
public class ServerModeClientSslContextFactory extends AbstractSslContextFactory{
    @Override
    public SSLContext build() {
        if(context != null)
            return context;
        try {
            context = SSLContext.getInstance("TLSv1.2");
            context.init(getClientKeyManagers(), getClientTrustManagers(), new SecureRandom());
            return context;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    private TrustManager[] getClientTrustManagers() {
        return null;
    }

    private KeyManager[] getClientKeyManagers() {
        KeyStore keyStore = null;
        KeyManagerFactory keyManagerFactory = null;
        InputStream keyStoreInputStream = null;
        KeyManager keyManager[] = null;
        try {
            keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyStore = KeyStore.getInstance("PKCS12");
            String keyStorePassword = "Efxx12*";
            keyStoreInputStream = new FileInputStream("/Users/zhuyayan/client.keystore");
            keyStore.load(keyStoreInputStream, keyStorePassword.toCharArray());
            keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());
            keyManager = keyManagerFactory.getKeyManagers();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        log.info("Key Manager Ready");
        return keyManager;
    }
}
