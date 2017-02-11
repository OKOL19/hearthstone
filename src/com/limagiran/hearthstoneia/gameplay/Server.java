package com.limagiran.hearthstoneia.gameplay;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Vinicius Silva
 */
public class Server {

    private static ServerSocket server = null;

    public static void open(int porta) {
        try {
            close();
            server = new ServerSocket(porta);
            Socket cliente1 = server.accept();
            Socket cliente2 = server.accept();
            new Thread(new GameServer(cliente1, cliente2)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            server.close();
            server = null;
        } catch (Exception e) {
        }
    }
}