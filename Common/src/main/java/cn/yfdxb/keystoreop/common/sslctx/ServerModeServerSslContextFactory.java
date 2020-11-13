package cn.yfdxb.keystoreop.common.sslctx;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

@Slf4j
public class ServerModeServerSslContextFactory extends AbstractSslContextFactory {
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
        TrustManagerFactory trustManagerFactory = null;
        KeyStore keyStore = null;
        FileInputStream trustStoreFileInputStream = null;
        TrustManager trustManager[] = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustStoreFileInputStream = new FileInputStream("C:\\Users\\zhuyayan\\client_truststore.p12");
            keyStore = KeyStore.getInstance("PKCS12");
            String trustStorePassword = "Efxx12*";
            keyStore.load(trustStoreFileInputStream, trustStorePassword.toCharArray());
            trustManagerFactory.init(keyStore);
            trustManager = trustManagerFactory.getTrustManagers();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        log.info("Trust Manager Ready");
        return trustManager;
    }

    private KeyManager[] getClientKeyManagers() {
        return null;
    }
}
