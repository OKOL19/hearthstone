package com.limagiran.hearthstone.evento;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Utils;
import com.limagiran.hearthstone.partida.control.Partida;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.util.List;

/**
 *
 * @author Vinicius Silva
 */
public class CardDescartado {

    private static Partida partida;
    private static Heroi hero;

    /**
     * Verifica se há algum evento para card descartado
     *
     * @param hero herói que gerou o evento
     */
    public static void processar(Heroi hero) {
        CardDescartado.hero = hero;
        CardDescartado.partida = hero.getPartida();
        List<Card> list = partida.getAllCardsIncludeSecrets();
        for (Card c : list) {
            if (!c.isSilenciado()) {
                switch (c.getId()) {
                    //Cavaleirinho do Mal (Sempre que você descartar um card, receba +1/+1)
                    case "AT_021":
                        at_021(c);
                        break;
                    //Punho de Jaraxxus (Ao jogar ou descartar este card, cause 4 de dano a um inimigo aleatório)
                    case "AT_022":
                        at_022(c);
                        break;
                }
            }
        }
    }

    /**
     * Cavaleirinho do Mal (Sempre que você descartar um card, receba +1/+1)
     *
     * @param c Card
     */
    private static void at_021(Card c) {
        c.getAnimacao().disparoDeEvento();
        c.addVidaMaxima(1);
        c.addAtaque(1);
    }

    /**
     * Punho de Jaraxxus (Ao jogar ou descartar este card, cause 4 de dano a um
     * inimigo aleatório)
     */
    private static void at_022(Card c) {
        Heroi oponente = partida.getHero().equals(hero) ? partida.getOponente() : partida.getHero();
        int random = Utils.random(oponente.getMesa().size() + 1) - 1;
        c.getAnimacao().disparoDeEvento();
        if (random == oponente.getMesa().size()) {
            oponente.delHealth(Utils.getDanoFeitico(4, hero));
        } else {
            oponente.getMesa().get(random).delVida(Utils.getDanoFeitico(4, hero));
        }
    }
}