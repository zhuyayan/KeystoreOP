package cn.yfdxb.keystoreop.server;

import cn.yfdxb.keystoreop.server.tcp.TcpServer;

public class Server {
    public static void main(String[] args) {
        TcpServer server = new TcpServer();
        server.run();
    }
}
