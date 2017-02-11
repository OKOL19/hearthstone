package com.limagiran.hearthstoneia.server;

import com.limagiran.hearthstoneia.heroi.control.Heroi;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Vinicius Silva
 */
public class Cliente {

    private static Socket server = null;
    public static ObjectOutputStream saida;
    public static ObjectInputStream entrada;

    public static void connect(String servidor, int porta, Heroi hero) throws Exception {
        server = new Socket(servidor, porta);
        new Thread(new GameCliente(server, hero)).start();
    }
}