package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.Utils;
import java.util.List;


/**
 *
 * @author Vinicius Silva
 */
public class Sobrecarga {

    private static int sobrecarga;

    /**
     * Verifica se há algum evento para sobrecarga
     *
     * @param card card com sobrecarga jogado
     */
    public static void processar(Card card) {
        if (card.isSobrecarga()) {
            sobrecarga = Utils.getSobrecarga(card);
            card.getPartida().getHero().addManaBloqueadaEsteTurno(sobrecarga);
            List<Card> list = card.getPartida().getAllCardsIncludeSecrets();
            for (Card c : list) {
                switch (c.getId()) {
                    //Trogg de Túnel (Sempre que você usar Sobrecarga, receba +1 de Ataque por Cristal de Mana bloqueado)
                    case "LOE_018":
                        loe_018(c);
                        break;
                }
            }
        }
    }

    /**
     * Trogg de Túnel (Sempre que você usar Sobrecarga, receba +1 de Ataque por
     * Cristal de Mana bloqueado)
     *
     * @param card Card
     */
    private static void loe_018(Card card) {
        card.addAtaque(sobrecarga);
    }

}