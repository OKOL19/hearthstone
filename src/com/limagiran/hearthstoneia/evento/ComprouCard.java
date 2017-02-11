package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstoneia.Utils;
import com.limagiran.hearthstone.util.Values;


/**
 *
 * @author Vinicius Silva
 */
public class ComprouCard {

    private static Partida partida;
    private static Heroi hero;
    private static Heroi oponente;
    private static Card comprado;

    /**
     * Verifica se há algum evento para card comprado
     *
     * @param comprado card comprado
     */
    public static void processar(Card comprado) {
        ComprouCard.partida = comprado.getPartida();
        ComprouCard.hero = comprado.getDono();
        ComprouCard.comprado = comprado;
        fazerAoComprar();
        if (partida.isVezHeroi()) {
            for (Card card : partida.getAllCardsIncludeSecrets()) {
                if (!card.isSilenciado()) {
                    switch (card.getId()) {
                        //Demônio das Sombras (Sempre que comprar um card, reduza seu custo em 1)
                        case "AT_014":
                            at_014(card);
                            break;
                        //Cromaggus (Sempre que comprar um card, coloque outra cópia na sua mão)
                        case "BRM_031":
                            brm_031(card);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Verifica se o card comprado faz alguma coisa logo ao ser comprado e
     * executa a ação
     */
    private static void fazerAoComprar() {
        switch (comprado.getId()) {
            //Emboscada! (Ao comprar este card, evoque um Nerubiano 4/4 para o seu oponente. Compre um card)
            case "AT_035t":
                at_035t();
                break;
            //Aniquilador do Mar (Ao comprar este card, cause 1 de dano aos seus lacaios)
            case "AT_130":
                at_130();
                break;
            //Leviatã Flamejante (Ao comprar este card, cause 2 de dano a todos os personagens)
            case "GVG_007":
                gvg_007();
                break;
            //Mina Entocada (Ao comprar este card, ele explode. Recebe 10 de dano e compre um card)
            case "GVG_056t":
                gvg_056t();
                break;
            //Maldição Antiga (Ao comprar este card, receba 7 de dano e compre um card)
            case "LOE_110t":
                loe_110t();
                break;
        }
    }

    /**
     * Demônio das Sombras (Sempre que comprar um card, reduza seu custo em 1)
     */
    private static void at_014(Card c) {
        if (hero.isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            comprado.delCusto(1);
        }
    }

    /**
     * Emboscada! (Ao comprar este card, evoque um Nerubiano 4/4 para o seu
     * oponente. Compre um card)
     */
    private static void at_035t() {
        partida.addHistorico(Utils.getDescricao(hero.getNome(), comprado));
        oponente.evocar(partida.criarCard(Values.NERUBIANO4_4, System.nanoTime()));
    }

    /**
     * Aniquilador do Mar (Ao comprar este card, cause 1 de dano aos seus
     * lacaios)
     */
    private static void at_130() {
        partida.addHistorico(Utils.getDescricao(hero.getNome(), comprado));
        EmArea.dano(partida, comprado.getDono().getMesa(), 1, 0);
    }

    /**
     * Cromaggus (Sempre que comprar um card, coloque outra cópia na sua mão)
     */
    private static void brm_031(Card c) {
        if (c.getDono().equals(comprado.getDono())) {
            c.getAnimacao().disparoDeEvento();
            comprado.getDono().addMao(partida.criarCard(comprado.getId(), System.nanoTime()));
        }
    }

    /**
     * Leviatã Flamejante (Ao comprar este card, cause 2 de dano a todos os
     * personagens)
     */
    private static void gvg_007() {
        partida.addHistorico(Utils.getDescricao(hero.getNome(), comprado));
        hero.delHealth(2);
        oponente.delHealth(2);
        EmArea.dano(partida, partida.getMesa(), 2, 0);
    }

    /**
     * Mina entocada (Ao comprar este card, ele explode. Recebe 10 de dano e
     * compre um card)
     */
    private static void gvg_056t() {
        Heroi h = comprado.getDono();
        partida.addHistorico(Utils.getDescricao(hero.getNome(), comprado));
        h.delHealth(10);
        h.delMao(comprado);
        h.delDeck(comprado);
        h.comprarCarta(Card.COMPRA_EVENTO);
    }

    /**
     * Maldição Antiga (Ao comprar este card, receba 7 de dano e compre um card)
     */
    private static void loe_110t() {
        Heroi h = comprado.getDono();
        partida.addHistorico(Utils.getDescricao(hero.getNome(), comprado));
        h.delHealth(7);
        h.delMao(comprado);
        h.delDeck(comprado);
        h.comprarCarta(Card.COMPRA_EVENTO);
    }

}