package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.Utils;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstone.util.Filtro;
import com.limagiran.hearthstoneia.server.GameCliente;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.util.Values;


/**
 *
 * @author Vinicius Silva
 */
public class InicioDoTurno {

    private static Partida partida;

    /**
     * Verifica se há algum evento para início do turno
     *
     * @param partida partida verificada
     */
    public static void processar(Partida partida) {
        InicioDoTurno.partida = partida;
        if (partida.isVezHeroi()) {
            for (Card c : partida.getHero().getSegredo()) {
                if (c.getId().equals("AT_073")) {
                    //Espírito Competitivo (Segredo: Quando seu turno começar, conceda +1/+1 aos seus lacaios)
                    at_073(c);
                    break;
                }
            }
            for (Card c : partida.getAllCardsIncludeSecrets()) {
                if (!c.isSilenciado()) {
                    switch (c.getId()) {
                        //Alarmobô (No início do turno, troque este lacaio por um lacaio aleatório da sua mão)
                        case "EX1_006":
                            ex1_006(c);
                            break;
                        //Demolidor (No início do seu turno, cause 2 de dano a um inimigo aleatório)
                        case "EX1_102":
                            ex1_102(c);
                            break;
                        //Galinha Teleguiada (No início do seu turno, destrua este lacaio e compre 3 cards)
                        case "Mekka1":
                            mekka1(c);
                            break;
                        //Galinhaitor (No início do seu turno, transforme um lacaio aleatório em uma Galinha 1/1)
                        case "Mekka4":
                            mekka4(c);
                            break;
                        //Fonte de Luz (No início do seu turno, restaure 3 de Vida de um personagem aliado ferido)
                        case "EX1_341":
                            ex1_341(c);
                            break;
                        //Nat Pagle (No início do seu turno, você tem 50% de chance de comprar um card extra)
                        case "EX1_557":
                            ex1_557(c);
                            break;
                        //Vulto de Naxxramas (Furtividade. No início do seu turno, receba +1/+1)
                        case "FP1_005":
                            fp1_005(c);
                            break;
                        //Gárgula Litopele (No início do seu turno, restaure toda a Vida deste lacaio)
                        case "FP1_027":
                            fp1_027(c);
                            break;
                        //Micromáquina (No início de cada turno, receba +1 de Ataque)
                        case "GVG_103":
                            gvg_103(c);
                            break;
                        //Cabeça de Mimiron (No início do seu turno, se você tiver pelo menos 3 Mecanoides, destrua todos e forme o V-07-TR-0N)
                        case "GVG_111":
                            gvg_111(c);
                            break;
                        //Agoureiro (No início do seu turno, destrua TODOS os lacaios)
                        case "NEW1_021":
                            new1_021(c);
                            break;
                    }
                }

            }
        }
    }

    /**
     * Espírito Competitivo (Segredo: Quando seu turno começar, conceda +1/+1
     * aos seus lacaios)
     *
     * @param segredo Segredo
     */
    private static void at_073(Card segredo) {
        if (partida.getHero().isCard(segredo.id_long) && !partida.getHero().getMesa().isEmpty()) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            for (Card lacaio : segredo.getDono().getMesa()) {
                lacaio.addVidaMaxima(1);
                lacaio.addAtaque(1);
            }
            SegredoRevelado.processar(segredo);
        }
    }

    /**
     * Alarmobô (No início do turno, troque este lacaio por um lacaio aleatório
     * da sua mão)
     *
     * @param c Card
     */
    private static void ex1_006(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            List<Card> lacaios = new ArrayList<>();
            for (Card card : partida.getHero().getMao()) {
                if (card.isLacaio()) {
                    lacaios.add(card);
                }
            }
            if (!lacaios.isEmpty()) {
                c.getAnimacao().disparoDeEvento();
                Card aleatorio = lacaios.get(Utils.random(lacaios.size()) - 1);
                int index = partida.getHero().getPosicaoNaMesa(c.id_long);
                partida.getHero().addMao(c);
                partida.getHero().delMesa(c);
                partida.getHero().addMesa(index, aleatorio);
                partida.getHero().delMao(aleatorio);
                partida.addHistorico(c.getName() + " trocou de lugar com " + aleatorio.getName());
            }
        }
    }

    /**
     * Demolidor (No início do seu turno, cause 2 de dano a um inimigo
     * aleatório)
     *
     * @param c Card
     */
    private static void ex1_102(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            Utils.dano(partida, Utils.getAleatorioOponente(partida), 2);
            c.delMechanics(Values.FURTIVIDADE);
        }
    }

    /**
     * Galinha Teleguiada (No início do seu turno, destrua este lacaio e compre
     * 3 cards)
     *
     * @param c Card
     */
    private static void mekka1(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            c.morreu();
            c.getDono().comprarCarta(Card.COMPRA_EVENTO);
            c.getDono().comprarCarta(Card.COMPRA_EVENTO);
            c.getDono().comprarCarta(Card.COMPRA_EVENTO);
        }
    }

    /**
     * Galinhaitor (No início do seu turno, transforme um lacaio aleatório em
     * uma Galinha 1/1)
     *
     * @param c Card
     */
    private static void mekka4(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            Card aleatorio = partida.getMesa().get(Utils.random(partida.getMesa().size()) - 1);
            partida.polimorfia(aleatorio.id_long, Values.GALINHA1_1, true);
        }
    }

    /**
     * Fonte de Luz (No início do seu turno, restaure 3 de Vida de um personagem
     * aliado ferido)
     *
     * @param c Card
     */
    private static void ex1_341(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            long personagem = Utils.getPersonagemAleatorioFerido(partida);
            if (personagem != Utils.ALVO_INVALIDO) {
                c.getAnimacao().disparoDeEvento();
                Utils.cura(partida, personagem, 3);
            }
        }
    }

    /**
     * Nat Pagle (No início do seu turno, você tem 50% de chance de comprar um
     * card extra)
     *
     * @param c Card
     */
    private static void ex1_557(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            if (Utils.random(10) % 2 == 0) {
                c.getAnimacao().disparoDeEvento();
                c.getDono().comprarCarta(Card.COMPRA_EVENTO);
                partida.addHistorico(c.getName() + " comprou um card extra.");
            }
        }
    }

    /**
     * Vulto de Naxxramas (Furtividade. No início do seu turno, receba +1/+1)
     *
     * @param c Card
     */
    private static void fp1_005(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            c.addVidaMaxima(1);
            c.addAtaque(1);
        }
    }

    /**
     * Gárgula Litopele (No início do seu turno, restaure toda a Vida deste
     * lacaio)
     *
     * @param c Card
     */
    private static void fp1_027(Card c) {
        if (partida.getHero().isCard(c.id_long) && !c.isIleso()) {
            c.getAnimacao().disparoDeEvento();
            c.restaurarVida();
        }
    }

    /**
     * Micromáquina (No início de cada turno, receba +1 de Ataque)
     *
     * @param c Card
     */
    private static void gvg_103(Card c) {
        c.getAnimacao().disparoDeEvento();
        c.addAtaque(1);
    }

    /**
     * Cabeça de Mimiron (No início do seu turno, se você tiver pelo menos 3
     * Mecanoides, destrua todos e forme o V-07-TR-0N)
     *
     * @param c Card
     */
    private static void gvg_111(Card c) {
        if (partida.getHero().isCard(c.id_long) && Filtro.raca(c.getDono().getMesa(), Values.MECANOIDE).size() >= 3) {
            c.getAnimacao().disparoDeEvento();
            EmArea.destruir(Filtro.raca(c.getDono().getMesa(), Values.MECANOIDE));
            c.getDono().evocar(partida.criarCard(Values.V07TR0N, System.nanoTime()));
        }
    }

    /**
     * Agoureiro (No início do seu turno, destrua TODOS os lacaios)
     *
     * @param c Card
     */
    private static void new1_021(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            EmArea.destruir(partida.getMesa());
        }
    }

}