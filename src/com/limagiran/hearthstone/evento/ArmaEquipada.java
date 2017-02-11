package com.limagiran.hearthstone.evento;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.heroi.control.Heroi;

/**
 *
 * @author Vinicius Silva
 */
public class ArmaEquipada {

    private static Heroi hero;

    /**
     * Verifica se há algum evento para arma equipada
     *
     * @param hero herói que equipou a arma
     */
    public static void processar(Heroi hero) {
        ArmaEquipada.hero = hero;
        if (hero.getPartida().isVezHeroi()) {
            for (Card c : hero.getPartida().getAllCardsIncludeSecrets()) {
                if (!c.isSilenciado()) {
                    switch (c.getId()) {
                        //Bucaneiro (Sempre que você equipar uma arma, conceda +1 de Ataque a ela)
                        case "AT_029":
                            at_029(c);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Bucaneiro (Sempre que você equipar uma arma, conceda +1 de Ataque a ela)
     */
    private static void at_029(Card c) {
        c.getAnimacao().disparoDeEvento();
        hero.getArma().addAtaque(1);
    }
}