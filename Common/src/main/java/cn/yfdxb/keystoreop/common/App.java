package cn.yfdxb.keystoreop.common;

import cn.yfdxb.keystoreop.common.tcp.TcpClient;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import sun.security.provider.X509Factory;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class App {
    public static final String KEY_ALGORITHM = "RSA";
    public static void main(String[] args) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, KeyStoreException, NoSuchProviderException {
        TcpClient client = new TcpClient();
        client.start();
        //        exportCertificate();
//        addCertificateToKeystore("001", "001", "123456", "75723958305834905829283490538495340");
    }

    public static void addCertificateToKeystore(String alias, String sn,String password, String uid)
            throws CertificateException, NoSuchProviderException, NoSuchAlgorithmException, IOException, KeyStoreException, UnrecoverableKeyException{

        char[] keystorePassword = "Efxx12*".toCharArray();
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(new FileInputStream("C:\\Users\\zhu\\.keystore"), keystorePassword);

        try {
            //获得对象 KeyPairGenerator 参数 RSA 1024个字节
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(4096);
            //通过对象 KeyPairGenerator 获取对象KeyPair
            KeyPair keyPair = keyPairGen.generateKeyPair();

            //X.509 Distinct Name
            String subjectDN = String.format("SN=%s,UID=%s,CN=eflytech,O=eflytech,L=shanghai,ST=shanghai,C=cn", sn, uid);
            Certificate cert = selfSign(keyPair, subjectDN);
            Certificate[] chain = new Certificate[]{cert};
            keystore.setKeyEntry(alias, keyPair.getPrivate(), password.toCharArray(), chain);
            try (FileOutputStream fos = new FileOutputStream("C:\\Users\\zhu\\.keystore")) {
                keystore.store(fos, keystorePassword);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private static Certificate selfSign(KeyPair keyPair, String subjectDN) throws OperatorCreationException, CertificateException, IOException
    {
        Provider bcProvider = new BouncyCastleProvider();
        Security.addProvider(bcProvider);
        long now = System.currentTimeMillis();
        Date startDate = new Date(now);
        X500Name dnName = new X500Name(subjectDN);
        BigInteger certSerialNumber = new BigInteger(Long.toString(now)); // <-- Using the current timestamp as the certificate serial number
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.YEAR, 10); // <-- 1 Yr validity
        Date endDate = calendar.getTime();
        String signatureAlgorithm = "SHA256WithRSA"; // <-- Use appropriate signature algorithm based on your keyPair algorithm.
        ContentSigner contentSigner = new JcaContentSignerBuilder(signatureAlgorithm).build(keyPair.getPrivate());
        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(dnName, certSerialNumber, startDate, endDate, dnName, keyPair.getPublic());
        // Extensions --------------------------

        // Basic Constraints
        BasicConstraints basicConstraints = new BasicConstraints(true); // <-- true for CA, false for EndEntity
        certBuilder.addExtension(new ASN1ObjectIdentifier("2.5.29.19"), true, basicConstraints); // Basic Constraints is usually marked as critical.
        // -------------------------------------
        return new JcaX509CertificateConverter().setProvider(bcProvider).getCertificate(certBuilder.build(contentSigner));
    }

    public static void exportCertificate(){
        try {
            FileInputStream inputStream = new FileInputStream("C:\\Users\\zhu\\.keystore");
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(inputStream, "Efxx12*".toCharArray());
            inputStream.close();
            Certificate certificate = keyStore.getCertificate("001");
            PublicKey publicKey = certificate.getPublicKey();
            Base64.Encoder encoder = Base64.getEncoder();
            String base64Cert = new String(encoder.encode(publicKey.toString().getBytes("UTF-8")), "UTF-8");
            String base64Cert1 = new String(encoder.encode(certificate.getEncoded()), "UTF-8");
            System.out.println("提取的公钥为___:\n" + base64Cert1);

            FileWriter fw = new FileWriter("C:\\Users\\zhu\\001.pem");
            fw.write(X509Factory.BEGIN_CERT);    //非必须
            fw.write(base64Cert1);
            fw.write(X509Factory.END_CERT);  //非必须
            fw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
