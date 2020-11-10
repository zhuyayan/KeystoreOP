package cn.yfdxb.keystoreop.client;

import cn.yfdxb.keystoreop.client.tcp.TcpClient;

public class App {
    public static void main(String[] args) {
        TcpClient client = new TcpClient();
        client.start();
    }

}
