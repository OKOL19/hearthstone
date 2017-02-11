package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.heroi.control.Heroi;


/**
 *
 * @author Vinicius Silva
 */
public class ReceberDanoNoLugarDoHeroi {

    private static int damage;

    /**
     * Verifica se há algum evento para receber dano no lugar do herói
     *
     * @param hero herói recebendo dano
     * @param damage dano sendo recebido
     * @return {@code true} se algum lacaio recebeu dano pelo herói. {@code false}
     * o contrário.
     */
    public static boolean processar(Heroi hero, int damage) {
        ReceberDanoNoLugarDoHeroi.damage = damage;
        for (Card lacaio : hero.getMesa()) {
            if (!lacaio.isSilenciado()) {
                switch (lacaio.getId()) {
                    //Bolf Marrabroquel (Sempre que seu herói receber dano, este lacaio recebe no lugar dele)
                    case "AT_124":
                        return at_124(lacaio);

                }
            }
        }
        return false;
    }

    /**
     * Bolf Marrabroquel (Sempre que seu herói receber dano, este lacaio recebe
     * no lugar dele)
     *
     * @param lacaio
     */
    private static boolean at_124(Card lacaio) {
        lacaio.getAnimacao().disparoDeEvento();
        lacaio.delVida(damage);
        return true;
    }
}