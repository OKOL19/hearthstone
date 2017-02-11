package com.limagiran.hearthstone.evento;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Utils;
import com.limagiran.hearthstone.partida.control.Partida;
import com.limagiran.hearthstone.partida.util.Random;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.server.GameCliente;
import com.limagiran.hearthstone.util.GamePlay;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class LacaioMorreu {

    private static Partida partida;
    private static Card card;

    /**
     * Verifica se há algum evento para lacaio morreu
     *
     * @param card lacaio morto
     * @param list lista de cards adicionados ao partida.getAll, que já estão
     * mortos mas podem ativar o efeito
     */
    public static void processar(Card card, List<Card> list) {
        LacaioMorreu.partida = card.getPartida();
        LacaioMorreu.card = card;
        if (partida.isVezHeroi()) {
            for (Card mao : partida.getMao()) {
                switch (mao.getId()) {
                    //Bolvar Fordragon (Sempre que um lacaio aliado morrer e este card estiver na sua mão, ganhe +1 de Ataque)
                    case "GVG_063":
                        gvg_063(mao);
                        break;
                }
            }
            List<Card> lista = new ArrayList<>();
            if (list != null) {
                for (Card c : list) {
                    if (!lista.contains(c)) {
                        lista.add(c);
                    }
                }
                lista.addAll(partida.getAllCardsIncludeSecrets());
            } else {
                lista = partida.getAllCardsIncludeSecrets();
            }
            for (Card c : lista) {
                if (!c.isSilenciado()) {
                    switch (c.getId()) {
                        //Efígie (Segredo: Quando um lacaio aliado morrer, evoque outro lacaio aleatório com o mesmo Custo)
                        case "AT_002":
                            at_002(c);
                            break;
                        //Redenção (Segredo: Quando um de seus lacaios morrer, reviva-o com 1 de Vida)
                        case "EX1_136":
                            ex1_136(c);
                            break;
                        //Hiena Carniceira (Sempre que uma Fera aliada morrer, receba +2/+1)
                        case "EX1_531":
                            ex1_531(c);
                            break;
                        //Mestra do Culto (Quando um de seus outros lacaios morrer, compre um card)
                        case "EX1_595":
                            ex1_595(c);
                            break;
                        //Duplicar (Segredo: Quando um lacaio aliado morrer, coloque 2 cópias dele na sua mão)
                        case "FP1_018":
                            fp1_018(c);
                            break;
                        //Vingar (Segredo: Quando um de seus lacaios morrer, conceda +3/+2 a um lacaio aliado aleatório)
                        case "FP1_020":
                            fp1_020(c);
                            break;
                        //Andarilho Espiritual Pinarena (Sempre que um Murloc aliado morrer, compre um card. Sobrecarga: 1)
                        case "GVG_040":
                            gvg_040(c);
                            break;
                        //Sucatatron (Sempre que um Mecanoide aliado morrer, receba +2/+2)
                        case "GVG_106":
                            gvg_106(c);
                            break;
                        //Mecangenheiro Termaplugue (Sempre que um lacaio inimigo morrer, evoque um Gnomo Leproso)
                        case "GVG_116":
                            gvg_116(c);
                            break;
                        //Carniçal Devora-carne (Sempre que um lacaio morrer, receba +1 de Ataque)
                        case "tt_004":
                            tt_004(c);
                            break;

                    }
                }
            }
        }
    }

    /**
     * Efígie - Segredo: Quando um lacaio aliado morrer, evoque outro lacaio aleatório
     * com o mesmo Custo.
     */
    private static void at_002(Card segredo) {
        if (partida.getOponente().isCard(segredo.id_long)
                && partida.getOponente().isCard(card.id_long)) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            Card random = Random.getCard(Values.LACAIO, Values.GERAL, card.getCost(), Values.GERAL, Values.GERAL, Values.GERAL);
            random = partida.criarCard(random.getId(), System.nanoTime());
            partida.getOponente().evocar(random);
            SegredoRevelado.processar(segredo);
        }
    }

    /**
     * Redenção (Segredo: Quando um de seus lacaios morrer, reviva-o com 1 de
     * Vida)
     *
     * @param segredo Card
     */
    private static void ex1_136(Card segredo) {
        if (partida.getOponente().isCard(segredo.id_long)
                && partida.getOponente().isCard(card.id_long)) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            Card criar = partida.criarCard(card.getId(), System.nanoTime());
            criar.delVida(criar.getVida() - 1);
            partida.getOponente().evocar(criar);
            SegredoRevelado.processar(segredo);
        }
    }

    /**
     * Hiena Carniceira (Sempre que uma Fera aliada morrer, receba +2/+1)
     *
     * @param c Card
     */
    private static void ex1_531(Card c) {
        if (card.isFera() && card.getDono().equals(c.getDono()) && !card.equals(c)) {
            c.getAnimacao().disparoDeEvento();
            c.addVidaMaxima(1);
            c.addAtaque(2);
        }
    }

    /**
     * Mestra do Culto (Quando um de seus outros lacaios morrer, compre um card)
     *
     * @param c Card
     */
    private static void ex1_595(Card c) {
        if (!card.equals(c) && card.getDono().equals(c.getDono())) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().comprarCarta(Card.COMPRA_EVENTO);
        }
    }

    /**
     * Duplicar (Segredo: Quando um lacaio aliado morrer, coloque 2 cópias dele
     * na sua mão)
     *
     * @param segredo Card
     */
    private static void fp1_018(Card segredo) {
        if (partida.getOponente().isCard(segredo.id_long)
                && partida.getOponente().isCard(card.id_long)
                && partida.getOponente().getMao().size() < GamePlay.INSTANCE.getCartasNaMao()) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            card.getDono().addMao(partida.criarCard(card.getId(), System.nanoTime()));
            card.getDono().addMao(partida.criarCard(card.getId(), System.nanoTime()));
            SegredoRevelado.processar(segredo);
        }
    }

    /**
     * Vingar (Segredo: Quando um de seus lacaios morrer, conceda +3/+2 a um
     * lacaio aliado aleatório)
     *
     * @param segredo Card
     */
    private static void fp1_020(Card segredo) {
        if (partida.getOponente().isCard(segredo.id_long)
                && partida.getOponente().isCard(card.id_long)
                && !partida.getOponente().getMesa().isEmpty()) {
            Card aleatorio = Utils.getLacaioAleatorio(partida.getOponente().getMesa());
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            aleatorio.addVidaMaxima(2);
            aleatorio.addAtaque(3);
            SegredoRevelado.processar(segredo);
        }
    }

    /**
     * Andarilho Espiritual Pinarena (Sempre que um Murloc aliado morrer, compre
     * um card. Sobrecarga: 1)
     *
     * @param c Card
     */
    private static void gvg_040(Card c) {
        if (card.getDono().equals(c.getDono()) && card.isMurloc()) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().comprarCarta(Card.COMPRA_EVENTO);
        }
    }

    /**
     * Bolvar Fordragon (Sempre que um lacaio aliado morrer e este card estiver
     * na sua mão, ganhe +1 de Ataque)
     *
     * @param mao
     */
    private static void gvg_063(Card mao) {
        if (card.getDono().equals(mao.getDono())) {
            mao.addAtaque(1);
        }
    }

    /**
     * Sucatatron (Sempre que um Mecanoide aliado morrer, receba +2/+2)
     *
     * @param c
     */
    private static void gvg_106(Card c) {
        if (card.getDono().equals(c.getDono()) && card.isMecanoide()) {
            c.getAnimacao().disparoDeEvento();
            c.addVidaMaxima(2);
            c.addAtaque(2);
        }
    }

    /**
     * Mecangenheiro Termaplugue (Sempre que um lacaio inimigo morrer, evoque um
     * Gnomo Leproso)
     *
     * @param c Card
     */
    private static void gvg_116(Card c) {
        if (c.getOponente().equals(card.getDono())) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().evocar(partida.criarCard(Values.GNOMO_LEPROSO, System.nanoTime()));
        }
    }

    /**
     * Carniçal Devora-carne (Sempre que um lacaio morrer, receba +1 de Ataque)
     *
     * @param c Card
     */
    private static void tt_004(Card c) {
        c.getAnimacao().disparoDeEvento();
        c.addAtaque(1);
    }
}