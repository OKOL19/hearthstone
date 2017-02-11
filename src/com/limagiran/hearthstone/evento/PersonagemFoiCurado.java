package com.limagiran.hearthstone.evento;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Utils;
import com.limagiran.hearthstone.heroi.control.Heroi;
import com.limagiran.hearthstone.partida.control.Partida;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class PersonagemFoiCurado {

    private static Partida partida;
    private static Card card;
    private static Heroi hero;

    /**
     * Verifica se há algum evento para personagem foi curado
     *
     * @param card lacaio curado
     * @param list lista de cards que foram curados
     */
    public static void processar(Heroi hero, Card card, List<Card> list) {
        PersonagemFoiCurado.card = card;
        PersonagemFoiCurado.hero = hero;
        PersonagemFoiCurado.partida = hero == null ? card.getPartida() : hero.getPartida();
        if (partida.isVezHeroi()) {
            List<Card> lista = new ArrayList<>();
            lista.addAll(partida.getAllCardsIncludeSecrets());
            if (list != null) {
                list.stream().filter((c) -> (!lista.contains(c))).forEach((c) -> {
                    lista.add(c);
                });
            }
            for (Card c : lista) {
                if (!c.isSilenciado()) {
                    switch (c.getId()) {
                        //Campeã Sagrada (Sempre que um personagem for curado, receba +2 de Ataque)
                        case "AT_011":
                            at_011(c);
                            break;
                        //Clériga da Vila Norte (Sempre que um lacaio for curado, compre um card)
                        case "CS2_235":
                            cs2_235(c);
                            break;
                        //Guardiã da Luz (Sempre que um personagem for curado, receba +2 de Ataque)
                        case "EX1_001":
                            ex_001(c);
                            break;
                        //Boxeador da Sombra (Sempre que um personagem for curado, cause 1 de dano a um inimigo aleatório)
                        case "GVG_072":
                            gvg_072(c);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Campeã Sagrada (Sempre que um personagem for curado, receba +2 de Ataque.
     *
     * @param c Card
     */
    private static void at_011(Card c) {
        c.getAnimacao().disparoDeEvento();
        c.addAtaque(2);
    }

    /**
     * Clériga da Vila Norte (Sempre que um lacaio for curado, compre um card)
     *
     * @param c Card
     */
    private static void cs2_235(Card c) {
        if (card != null) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Guardiã da Luz (Sempre que um personagem for curado, receba +2 de Ataque)
     *
     * @param c Card
     */
    private static void ex_001(Card c) {
        c.getAnimacao().disparoDeEvento();
        c.addAtaque(2);
    }

    /**
     * Boxeador da Sombra (Sempre que um personagem for curado, cause 1 de dano
     * a um inimigo aleatório)
     *
     * @param c Card
     */
    private static void gvg_072(Card c) {
        c.getAnimacao().disparoDeEvento();
        Utils.dano(partida, Utils.getAlvoAleatorio(partida, c.getOponente()), 1);
        c.delMechanics(Values.FURTIVIDADE);
    }
}