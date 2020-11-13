package cn.yfdxb.keystoreop.common.sslctx;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

public class ServerSslContextFactory extends AbstractSslContextFactory {
    private static SSLContext context = null;
    private KeyManager[] getServerKeyManagers(){
        FileInputStream is = null;
        KeyStore ks = null;
        KeyManagerFactory keyFac = null;
        KeyManager[] kms = null;
        try {
            // 获得KeyManagerFactory对象. 初始化位默认算法
            keyFac = KeyManagerFactory.getInstance("SunX509");
            File file = new File("C:\\Users\\zhu\\.keystore");
            is =new FileInputStream(file);
            ks = KeyStore.getInstance("PKCS12");
            String keyStorePass = "Efxx12*";
            ks.load(is , keyStorePass.toCharArray());
            keyFac.init(ks, keyStorePass.toCharArray());
            kms = keyFac.getKeyManagers();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if( is != null ){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return kms;
    }

    private TrustManager[] getServerTrustManager(){
        FileInputStream is = null;
        KeyStore ks = null;
        TrustManagerFactory keyFac = null;
        TrustManager[] kms = null;
        try {
            // 获得KeyManagerFactory对象. 初始化位默认算法
            keyFac = TrustManagerFactory.getInstance("SunX509");
            File file = new File("C:\\Users\\zhu\\.keystore");
            is =new FileInputStream(file);
            ks = KeyStore.getInstance("PKCS12") ;
            String keyStorePass = "Efxx12*";
            ks.load(is , keyStorePass.toCharArray());
            keyFac.init(ks);
            kms = keyFac.getTrustManagers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(is != null ){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return kms;
    }

    @Override
    public SSLContext build() {
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
}
