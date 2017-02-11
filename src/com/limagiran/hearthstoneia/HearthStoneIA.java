package com.limagiran.hearthstoneia;

import com.limagiran.hearthstoneia.gameplay.Server;
import com.limagiran.hearthstoneia.ia.HeroisIA;
import com.limagiran.hearthstoneia.server.Cliente;

/**
 *
 * @author Vinicius
 */
public class HearthStoneIA {

    public static void main(String[] deck) {
        new Thread(() -> Server.open(12491)).start();
        Utils.sleep(1500);
        new Thread(() -> {
            try {
                Cliente.connect("localhost", 12491, HeroisIA.criarHeroi(deck));
            } catch (Exception ex) {
                com.limagiran.hearthstone.util.Utils.error("Falha ao iniciar servidor");
            }
        }).start();
    }
}
