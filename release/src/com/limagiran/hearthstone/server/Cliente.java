package com.limagiran.hearthstone.server;

import com.limagiran.hearthstone.heroi.control.Heroi;
import com.limagiran.hearthstone.util.Utils;
import java.net.Socket;

/**
 *
 * @author Vinicius Silva
 */
public class Cliente extends GameCliente {

    //private ObjectOutputStream saida;
    //private ObjectInputStream entrada;
    public Cliente(Socket server, Heroi heroi, Runnable callback) {
        super(server, heroi, callback);
    }

    public static void connect(String servidor, int porta, Heroi hero, Runnable callback) {
        try {
            new Thread(new Cliente(new Socket(servidor, porta), hero, callback)).start();
        } catch (Exception e) {
            Utils.error("Erro de Conexão!\n" + e);
            callback.run();
        }
    }

}
