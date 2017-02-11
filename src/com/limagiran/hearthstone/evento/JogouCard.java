package com.limagiran.hearthstone.evento;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.*;
import com.limagiran.hearthstone.partida.control.Partida;
import com.limagiran.hearthstone.partida.util.Random;
import com.limagiran.hearthstone.server.GameCliente;

/**
 *
 * @author Vinicius Silva
 */
public class JogouCard {

    private static Card card;
    private static Partida partida;

    /**
     * Verifica se há algum evento para card jogado
     *
     * @param card card jogado
     */
    public static void processar(Card card) {
        Sobrecarga.processar(card);
        UtilizarFeitico.jogouCardProcessado = true;
        JogouCard.partida = card.getPartida();
        JogouCard.card = card;
        if (card.isFeitico()) {
            feiticoLancado();
        }
        if (partida.isVezHeroi()) {
            JogouCard.card = card;
            for (Card c : partida.getAllCardsIncludeSecrets()) {
                if (!c.isSilenciado()) {
                    switch (c.getId()) {
                        //Queridinho do Público (Sempre que você jogar um card com Grito de Guerra, receba +1/+1)
                        case "AT_121":
                            at_121(c);
                            break;
                        //Ardilante (Depois de lançar um feitiço, cause 2 de dano dividido aleatoriamente entre todos os inimigos)
                        case "BRM_002":
                            brm_002(c);
                            break;
                        //Aventureiro em Missão (Sempre que você jogar um card, receba +1/+1)
                        case "EX1_044":
                            ex1_044(c);
                            break;
                        //Viciada em Mana (Sempre que você lançar um feitiço, receba +2 de Ataque no mesmo turno)
                        case "EX1_055":
                            ex1_055(c);
                            break;
                        //Guardiã de Segredos (Sempre que um Segredo for jogado, receba +1/+1)
                        case "EX1_080":
                            ex1_080(c);
                            break;
                        //Leiloeiro de Gerintontzan (Ao lançar um feitiço, compre um card)
                        case "EX1_095":
                            ex1_095(c);
                            break;
                        //Andarilho das Lendas Cho (Quando um jogador lançar um feitiço, coloque uma cópia dele na mão do outro jogador)
                        case "EX1_100":
                            ex1_100(c);
                            break;
                        //Elemental Desvinculado (Sempre que você jogar um card com Sobrecarga, receba +1/+1)
                        case "EX1_258":
                            ex1_258(c);
                            break;
                        //Entidade do Espelho (Segredo: Quando seu oponente jogar um lacaio, evoque uma cópia dele)
                        case "EX1_294":
                            ex1_294(c);
                            break;
                        //Contrição (Segredo: Quando seu oponente jogar um lacaio, reduza a Vida dele para 1)
                        case "EX1_379":
                            ex1_379(c);
                            break;
                        //Arquimago Antônidas (Sempre que você lançar um feitiço, adicione um feitiço "Bola de Fogo" à sua mão)
                        case "EX1_559":
                            ex1_559(c);
                            break;
                        //Tiro de Tocaia (Segredo: Quando seu oponente jogar um lacaio, cause 4 de dano ao lacaio)
                        case "EX1_609":
                            ex1_609(c);
                            break;
                        //Illidan Tempesfúria (Sempre que você jogar um card, evoque uma Labareda de Azzinoth 2/1)
                        case "EX1_614":
                            ex1_614(c);
                            break;
                        //Aníquilus (Sempre que seu oponente jogar um card, remova os três cards do topo do seu deck)
                        case "GVG_016":
                            gvg_016(c);
                            break;
                        //Príncipe Mercador Gallywix (Sempre que seu oponente lança um feitiço, ganhe uma cópia do feitiço e conceda uma Moeda a ele)
                        case "GVG_028":
                            gvg_028(c);
                            break;
                        //Trogg Lascapedra (Sempre que seu oponente lançar um feitiço, receba +1 de Ataque)
                        case "GVG_067":
                            gvg_067(c);
                            break;
                        //Trogg Pedraqueixo Parrudo (Sempre que seu oponente lançar um feitiço, receba +2 de Ataque)
                        case "GVG_068":
                            gvg_068(c);
                            break;
                        //Hobgolin (Sempre que você jogar um lacaio com 1 de Ataque, conceda-lhe +2/+2)
                        case "GVG_104":
                            gvg_104(c);
                            break;
                        //Gasganete (Sempre que você lançar um feitiço com custo 1 de mana, adicione um Mecanoide aleatório à sua mão)
                        case "GVG_117":
                            gvg_117(c);
                            break;
                        //Troggzor, o Aterrador (Quando seu oponente lançar um feitiço, evoque um Trogg Pedraqueixo Parrudo)
                        case "GVG_118":
                            gvg_118(c);
                            break;
                        //Elemental Tonitruante (Depois de jogar um lacaio com Grito de Guerra, cause 2 de dano a um inimigo aleatório)
                        case "LOE_016":
                            loe_016(c);
                            break;
                        //Provação Divina (Segredo: Quando seu oponente tiver 3 lacaios ou mais e jogar outro, destrua-o)
                        case "LOE_027":
                            loe_027(c);
                            break;
                        //Pedra Evocadora (Sempre que lançar um feitiço, evoque um lacaio aleatório com o mesmo custo)
                        case "LOE_086":
                            loe_086(c);
                            break;
                        //Moreia de Mana (Sempre que você lançar um feitiço, receba +1 de Ataque)
                        case "NEW1_012":
                            new1_012(c);
                            break;
                        //Piromante Selvagem (Depois de lançar um feitiço, cause 1 de dano a TODOS os lacaios)
                        case "NEW1_020":
                            new1_020(c);
                            break;
                        //Professora Violeta (Sempre que lançar um feitiço, evoque um Aprendiz Violeta 1/1)
                        case "NEW1_026":
                            new1_026(c);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Verifica se há algum evento específico para feitiço lançado
     */
    private static void feiticoLancado() {
        //Largar o Dedo (Cada vez que você lançar um feitiço neste turno, adicione um card de Caçador aleatório à sua mão)
        if (partida.getFeiticosAtivos().isAT_061() && partida.getFeiticosAtivos().getAT_061Long() != card.id_long) {
            card.getDono().addMao(partida.criarCard(Random.byPlayerClass(Values.CACADOR).getId(), System.nanoTime()));
        }
    }

    /**
     * Queridinho do Público (Sempre que você jogar um card com Grito de Guerra,
     * receba +1/+1)
     *
     * @param c Card
     */
    private static void at_121(Card c) {
        if (partida.getHero().isCard(c.id_long) && card.isGritoDeGuerra()) {
            c.getAnimacao().disparoDeEvento();
            c.addVidaMaxima(1);
            c.addAtaque(1);
        }
    }

    /**
     * Ardilante (Depois de lançar um feitiço, cause 2 de dano dividido
     * aleatoriamente entre todos os inimigos)
     *
     * @param c Card
     */
    private static void brm_002(Card c) {
        if (partida.getHero().isCard(c.id_long) && card.isFeitico()) {
            c.getAnimacao().disparoDeEvento();
            for (int i = 0; i < 2; i++) {
                Utils.dano(partida, Utils.getAleatorioOponente(partida), 1);
                c.delMechanics(Values.FURTIVIDADE);
            }
        }
    }

    /**
     * Aventureiro em Missão (Sempre que você jogar um card, receba +1/+1)
     *
     * @param c Card
     */
    private static void ex1_044(Card c) {
        if (partida.getHero().isCard(c.id_long) && !card.equals(c)) {
            c.getAnimacao().disparoDeEvento();
            c.addVidaMaxima(1);
            c.addAtaque(1);
        }
    }

    /**
     * Viciada em Mana (Sempre que você lançar um feitiço, receba +2 de Ataque
     * no mesmo turno)
     *
     * @param c Card
     */
    private static void ex1_055(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            partida.getEfeitoProgramado().concederAtaqueAUmLacaioUmTurno(c.id_long, 2);
        }
    }

    /**
     * Guardiã de Segredos (Sempre que um Segredo for jogado, receba +1/+1)
     */
    private static void ex1_080(Card c) {
        if (card.isSegredo()) {
            c.addVidaMaxima(1);
            c.addAtaque(1);
        }
    }

    /**
     * Leiloeiro de Gerintontzan (Ao lançar um feitiço, compre um card)
     *
     * @param c Card
     */
    private static void ex1_095(Card c) {
        if (partida.getHero().isCard(c.id_long) && card.isFeitico()) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().comprarCarta(Card.COMPRA_EVENTO);
        }
    }

    /**
     * Andarilho das Lendas Cho (Quando um jogador lançar um feitiço, coloque
     * uma cópia dele na mão do outro jogador)
     *
     * @param c Card
     */
    private static void ex1_100(Card c) {
        if (card.isFeitico()) {
            c.getAnimacao().disparoDeEvento();
            card.getOponente().addMao(partida.criarCard(card.getId(), System.nanoTime()));
        }
    }

    /**
     * Elemental Desvinculado (Sempre que você jogar um card com Sobrecarga,
     * receba +1/+1)
     *
     * @param c Card
     */
    private static void ex1_258(Card c) {
        if (partida.getHero().isCard(c.id_long) && card.isSobrecarga()) {
            c.getAnimacao().disparoDeEvento();
            c.addVidaMaxima(1);
            c.addAtaque(1);
        }
    }

    /**
     * Entidade do Espelho (Segredo: Quando seu oponente jogar um lacaio, evoque
     * uma cópia dele)
     *
     * @param segredo Card
     */
    private static void ex1_294(Card segredo) {
        if (card.isLacaio() && card.getOponente().isCard(segredo.id_long) && segredo.getDono().temEspacoNaMesa()) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            card.getOponente().evocar(partida.criarCard(card.getId(), System.nanoTime()));
            SegredoRevelado.processar(segredo);
        }
    }

    /**
     * Contrição (Segredo: Quando seu oponente jogar um lacaio, reduza a Vida
     * dele para 1)
     *
     * @param segredo Card
     */
    private static void ex1_379(Card segredo) {
        if (card.isLacaio() && card.getOponente().isCard(segredo.id_long)) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            card.setVidaOriginal(1, true);
            SegredoRevelado.processar(segredo);
        }
    }

    /**
     * Arquimago Antônidas (Sempre que você lançar um feitiço, adicione um
     * feitiço "Bola de Fogo" à sua mão)
     *
     * @param c Card
     */
    private static void ex1_559(Card c) {
        if (card.isFeitico() && partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().addMao(partida.criarCard(Values.BOLA_DE_FOGO, System.nanoTime()));
        }
    }

    /**
     * Tiro de Tocaia (Segredo: Quando seu oponente jogar um lacaio, cause 4 de
     * dano ao lacaio)
     *
     * @param segredo
     */
    private static void ex1_609(Card segredo) {
        if (!card.isMorto() && card.isLacaio() && card.getOponente().isCard(segredo.id_long)) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            card.delVida(Utils.getDanoFeitico(4, segredo.getDono()));
            SegredoRevelado.processar(segredo);
        }
    }

    /**
     * Illidan Tempesfúria (Sempre que você jogar um card, evoque uma Labareda
     * de Azzinoth 2/1)
     *
     * @param c Card
     */
    private static void ex1_614(Card c) {
        if (partida.getHero().isCard(c.id_long) && !card.equals(c)) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().evocar(partida.criarCard(Values.LABAREDA_DE_AZZINOTH2_1, System.nanoTime()));
        }
    }

    /**
     * Aníquilus (Sempre que seu oponente jogar um card, remova os três cards do
     * topo do seu deck)
     *
     * @param c Card
     */
    private static void gvg_016(Card c) {
        if (partida.getOponente().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            for (int i = 0; i < 3; i++) {
                if (!c.getDono().getDeck().isEmpty()) {
                    Card remover = c.getDono().getDeck().get(0);
                    c.getDono().delDeck(remover);
                    partida.addHistorico(Utils.getDescricao(c.getName() + " removeu ", card));
                }
            }
        }
    }

    /**
     * Príncipe Mercador Gallywix (Sempre que seu oponente lança um feitiço,
     * ganhe uma cópia do feitiço e conceda uma Moeda a ele)
     *
     * @param c Card
     */
    private static void gvg_028(Card c) {
        if (partida.getOponente().isCard(c.id_long) && card.isFeitico() && !card.getId().equals(Values.MOEDA_GALLYWIX)) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().addMao(partida.criarCard(card.getId(), System.nanoTime()));
            card.getDono().addMao(partida.criarCard(Values.MOEDA_GALLYWIX, System.nanoTime()));
        }
    }

    /**
     * Trogg Lascapedra (Sempre que seu oponente lançar um feitiço, receba +1 de
     * Ataque)
     *
     * @param c Card
     */
    private static void gvg_067(Card c) {
        if (partida.getOponente().isCard(c.id_long) && card.isFeitico()) {
            c.getAnimacao().disparoDeEvento();
            c.addAtaque(1);
        }
    }

    /**
     * Trogg Pedraqueixo Parrudo (Sempre que seu oponente lançar um feitiço,
     * receba +2 de Ataque)
     *
     * @param c Card
     */
    private static void gvg_068(Card c) {
        if (partida.getOponente().isCard(c.id_long) && card.isFeitico()) {
            c.getAnimacao().disparoDeEvento();
            c.addAtaque(2);
        }
    }

    /**
     * Hobgolin (Sempre que você jogar um lacaio com 1 de Ataque, conceda-lhe
     * +2/+2)
     *
     * @param c Card
     */
    private static void gvg_104(Card c) {
        if (partida.getHero().isCard(c.id_long) && card.getAtaqueSemAura() == 1) {
            c.getAnimacao().disparoDeEvento();
            card.addVidaMaxima(2);
            card.addAtaque(2);
        }
    }

    /**
     * Gasganete (Sempre que você lançar um feitiço com custo 1 de mana,
     * adicione um Mecanoide aleatório à sua mão)
     *
     * @param c Card
     */
    private static void gvg_117(Card c) {
        if (partida.getHero().isCard(c.id_long) && card.isFeitico() && card.getCost() == 1) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().addMao(partida.criarCard(Random.byRace(Values.MECANOIDE).getId(), System.nanoTime()));
        }
    }

    /**
     * Troggzor, o Aterrador (Quando seu oponente lançar um feitiço, evoque um
     * Trogg Pedraqueixo Parrudo)
     *
     * @param c Card
     */
    private static void gvg_118(Card c) {
        if (partida.getOponente().isCard(c.id_long) && card.isFeitico()) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().evocar(partida.criarCard(Values.TROGG_PEDRAQUEIXO_PARRUDO, System.nanoTime()));
        }
    }

    /**
     * Elemental Tonitruante (Depois de jogar um lacaio com Grito de Guerra,
     * cause 2 de dano a um inimigo aleatório)
     *
     * @param c Card
     */
    private static void loe_016(Card c) {
        if (partida.getHero().isCard(c.id_long) && card.isGritoDeGuerra()) {
            c.getAnimacao().disparoDeEvento();
            Utils.dano(partida, Utils.getAleatorioOponente(partida), 2);
            c.delMechanics(Values.FURTIVIDADE);
        }
    }

    /**
     * Provação Divina (Segredo: Quando seu oponente tiver 3 lacaios ou mais e
     * jogar outro, destrua-o)
     *
     * @param segredo Card
     */
    private static void loe_027(Card segredo) {
        if (!card.isMorto() && card.isLacaio() && card.getDono().equals(segredo.getOponente())
                && card.getDono().getMesa().size() >= 4) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            card.morreu();
            SegredoRevelado.processar(segredo);
        }
    }

    /**
     * Pedra Evocadora (Sempre que lançar um feitiço, evoque um lacaio aleatório
     * com o mesmo custo)
     *
     * @param c Card
     */
    private static void loe_086(Card c) {
        if (partida.getHero().isCard(c.id_long) && card.isFeitico()) {
            c.getAnimacao().disparoDeEvento();
            String random = Random.getCard(Values.LACAIO, Values.GERAL, card.getCustoAlterado(false),
                    Values.GERAL, Values.GERAL, Values.GERAL).getId();
            c.getDono().evocar(partida.criarCard(random, System.nanoTime()));
        }
    }

    /**
     * Moreia de Mana (Sempre que você lançar um feitiço, receba +1 de Ataque)
     *
     * @param c Card
     */
    private static void new1_012(Card c) {
        if (partida.getHero().isCard(c.id_long) && card.isFeitico()) {
            c.getAnimacao().disparoDeEvento();
            c.addAtaque(1);
        }
    }

    /**
     * Piromante Selvagem (Depois de lançar um feitiço, cause 1 de dano a TODOS
     * os lacaios)
     *
     * @param c Card
     */
    private static void new1_020(Card c) {
        if (partida.getHero().isCard(c.id_long) && card.isFeitico()) {
            c.getAnimacao().disparoDeEvento();
            EmArea.dano(partida, partida.getMesa(), 1, 0);
            c.delMechanics(Values.FURTIVIDADE);
        }
    }

    /**
     * Professora Violeta (Sempre que lançar um feitiço, evoque um Aprendiz
     * Violeta 1/1)
     *
     * @param c Card
     */
    private static void new1_026(Card c) {
        if (partida.getHero().isCard(c.id_long) && card.isFeitico()) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().evocar(partida.criarCard(Values.APRENDIZ_VIOLETA, System.nanoTime()));
        }
    }

}