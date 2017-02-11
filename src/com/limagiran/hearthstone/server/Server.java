package com.limagiran.hearthstone.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Vinicius Silva
 */
public class Server {

    private static ServerSocket server = null;
    private static boolean open = false;

    public static void open(int porta) {
        open = true;
        try {
            server = new ServerSocket(porta);
            while (open) {
                Socket cliente1 = server.accept();
                Socket cliente2 = server.accept();
                new Thread(new GameServer(cliente1, cliente2)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            open = false;
            server.close();
        } catch (IOException ex) {
        } finally {
            server = null;
        }
    }

    public static boolean isOpen() {
        return open;
    }

    public static String getIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "invalid";
        }
    }
}
