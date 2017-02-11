package com.limagiran.hearthstone.evento;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Utils;
import com.limagiran.hearthstone.partida.control.Partida;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class LacaioEvocado {

    private static Partida partida;
    private static Card evocado;

    /**
     * Verifica se há algum evento para lacaio evocado
     *     
     * @param card lacaio evocado
     */
    public static void processar(Card card) {
        LacaioEvocado.partida = card.getPartida();
        evocado = card;
        if (partida.isVezHeroi()) {
            eventoMao();
            for (Card c : partida.getAllCardsIncludeSecrets()) {
                if (!c.isSilenciado() && !c.equals(evocado)) {
                    switch (c.getId()) {
                        //Urubu Faminto (Sempre que você evocar uma Fera, compre um card)
                        case "CS2_237":
                            cs2_237(c);
                            break;
                        //Espada da Justiça (Toda vez que você evocar um lacaio, conceda-lhe +1/+1, e este item perderá 1 de Durabilidade)
                        case "EX1_366":
                            ex1_366(c);
                            break;
                        //Chamaré Murloc (Sempre que um Murloc for evocado, receba +1 de Ataque)
                        case "EX1_509":
                            ex1_509(c);
                            break;
                        //Coveiro (Toda vez que evocar um lacaio com Último Suspiro, receba +1 de ataque)
                        case "FP1_028":
                            fp1_028(c);
                            break;
                        //Caolho Trapaceiro (Sempre que você evocar um Pirata, receba Furtividade)
                        case "GVG_025":
                            gvg_025(c);
                            break;
                        //Guardião de Cobalto (Sempre que você evocar um Mecanoide, ganhe Escudo Divino)
                        case "GVG_062":
                            gvg_062(c);
                            break;
                        //Canhão do Navio (Sempre que você evocar um Pirata, cause 2 de dano a um inimigo aleatório)
                        case "GVG_075":
                            gvg_075(c);
                            break;
                        //Malabarista de Facas (Depois de evocar um lacaio, cause 1 de dano a um inimigo aleatório)
                        case "NEW1_019":
                            new1_019(c);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Verifica os cards que estão na mão e tem aura ativada pela evocação de
     * lacaios
     */
    private static void eventoMao() {
        for (Card c : partida.getMao()) {
            switch (c.getId()) {
                //Cavaleiro da Campina (Sempre que você evocar uma Fera, reduza em (1) o Custo deste card)
                case "AT_041":
                    at_041(c);
                    break;
            }
        }
    }

    /**
     * Cavaleiro da Campina (Sempre que você evocar uma Fera, reduza em (1) o
     * Custo deste card)
     */
    private static void at_041(Card c) {
        if (evocado.getDono().equals(c.getDono()) && evocado.isFera()) {
            c.delCusto(1);
        }
    }

    /**
     * Urubu Faminto (Sempre que você evocar uma Fera, compre um card)
     *
     * @param c Card
     */
    private static void cs2_237(Card c) {
        if (evocado.getDono().equals(c.getDono()) && evocado.isFera()) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Espada da Justiça (Toda vez que você evocar um lacaio, conceda-lhe +1/+1,
     * e este item perderá 1 de Durabilidade)
     *
     * @param arma Card
     */
    private static void ex1_366(Card arma) {
        if (evocado.getDono().equals(arma.getDono())) {
            evocado.addVidaMaxima(1);
            evocado.addAtaque(1);
            arma.delDurabilidade(1);
            if (arma.getDurability() == 0) {
                arma.getDono().destruirArma();
            }
        }
    }

    /**
     * Chamaré Murloc (Sempre que um Murloc for evocado, receba +1 de Ataque)
     *
     * @param c Card
     */
    private static void ex1_509(Card c) {
        if (evocado.isMurloc()) {
            c.getAnimacao().disparoDeEvento();
            c.addAtaque(1);
        }
    }

    /**
     * Coveiro (Toda vez que evocar um lacaio com Último Suspiro, receba +1 de
     * ataque)
     */
    private static void fp1_028(Card c) {
        if (evocado.isUltimoSuspiro()) {
            c.getAnimacao().disparoDeEvento();
            c.addAtaque(1);
        }
    }

    /**
     * Caolho Trapaceiro (Sempre que você evocar um Pirata, receba Furtividade)
     *
     * @param c Card
     */
    private static void gvg_025(Card c) {
        if (evocado.getDono().equals(c.getDono()) && evocado.isPirata() && !c.isFurtivo()) {
            c.getAnimacao().disparoDeEvento();
            c.addMechanics(Values.FURTIVIDADE);
        }
    }

    /**
     * Guardião de Cobalto (Sempre que você evocar um Mecanoide, ganhe Escudo
     * Divino)
     *
     * @param c Card
     */
    private static void gvg_062(Card c) {
        if (evocado.getDono().equals(c.getDono()) && evocado.isMecanoide() && !c.isEscudoDivino()) {
            c.getAnimacao().disparoDeEvento();
            c.addMechanics(Values.ESCUDO_DIVINO);
        }
    }

    /**
     * Canhão do Navio (Sempre que você evocar um Pirata, cause 2 de dano a um
     * inimigo aleatório)
     *
     * @param c Card
     */
    private static void gvg_075(Card c) {
        if (evocado.getDono().equals(c.getDono()) && evocado.isPirata()) {
            c.getAnimacao().disparoDeEvento();
            Utils.dano(partida, Utils.getAlvoAleatorio(partida, c.getOponente()), 2);
            c.delMechanics(Values.FURTIVIDADE);
        }
    }

    /**
     * Malabarista de Facas (Depois de evocar um lacaio, cause 1 de dano a um
     * inimigo aleatório)
     *
     * @param c Card
     */
    private static void new1_019(Card c) {
        if (evocado.getDono().equals(c.getDono())) {
            c.getAnimacao().disparoDeEvento();
            Utils.dano(partida, Utils.getAlvoAleatorio(partida, c.getOponente()), 1);
            c.delMechanics(Values.FURTIVIDADE);
        }
    }

}