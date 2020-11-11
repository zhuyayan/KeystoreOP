package cn.yfdxb.keystoreop.client;

import cn.yfdxb.keystoreop.client.tcp.TcpClient;

public class Client {
    public static void main(String[] args) {
        TcpClient client = new TcpClient();
        client.start();
        try {
            Thread.sleep(5000);
            client.sendMessage();
            Thread.sleep(5000);
            client.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
