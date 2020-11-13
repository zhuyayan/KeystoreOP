package cn.yfdxb.keystoreop.client;

import cn.yfdxb.keystoreop.client.tcp.TcpClient;

public class Client {
    public static void main(String[] args) {
        TcpClient client = new TcpClient();
        client.start();

        while(client.clientStat == false);
        System.out.println("sending ...");
        for(int index = 0; index < 500; index++) {
            client.sendMessage();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.stop();
    }
}
