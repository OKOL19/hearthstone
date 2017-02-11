package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstone.partida.util.Random;
import com.limagiran.hearthstoneia.server.GameCliente;
import com.limagiran.hearthstone.util.Param;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.util.Values;


/**
 *
 * @author Vinicius Silva
 */
public class PersonagemRecebeuDano {

    private static Partida partida;
    private static Heroi hero;
    private static Card card;
    private static int dano;

    /**
     * Verifica se há algum evento para personagem recebeu dano. heroi OU lacaio
     *
     * @param hero herói que recebeu dano
     * @param card lacaio que recebeu dano
     * @param dano dano recebido
     * @param list lista de lacaios adicionados na verificação
     */
    public static void processar(Heroi hero, Card card, int dano, List<Card> list) {
        PersonagemRecebeuDano.partida = hero == null ? card.getPartida() : hero.getPartida();
        PersonagemRecebeuDano.hero = hero;
        PersonagemRecebeuDano.card = card;
        PersonagemRecebeuDano.dano = dano;
        if (partida.isVezHeroi()) {
            List<Card> lista = new ArrayList<>();
            lista.addAll(partida.getAllCardsIncludeSecrets());
            if (list != null) {
                for (Card c : list) {
                    if (!lista.contains(c)) {
                        lista.add(c);
                    }
                }
            }
            for (Card c : lista) {
                if (!c.isSilenciado()) {
                    switch (c.getId()) {
                        //Guardião Colérico (Sempre que este lacaio receber dano, cause a mesma quantidade de dano ao seu herói)
                        case "AT_026":
                            at_026(c);
                            break;
                        //Muriácida (Sempre que outro lacaio receber dano, destrua-o)
                        case "AT_063":
                            at_063(c);
                            break;
                        //Chefe da Gangue dos Diabretes (Sempre que este lacaio receber dano, evoque um Diabrete 1/1)
                        case "BRM_006":
                            brm_006(c);
                            break;
                        //Atirador de Machados (Sempre que este lacaio receber dano, cause 2 de dano ao herói inimigo)
                        case "BRM_016":
                            brm_016(c);
                            break;
                        //Freguês Carrancudo (Sempre que este lacaio sobreviver a dano, evoque outro Freguês Carrancudo)
                        case "BRM_019":
                            brm_019(c);
                            break;
                        //Ovo de Dragão (Sempre que este lacaio receber dano, evoque um Dragonete 2/1)
                        case "BRM_022":
                            brm_022(c);
                            break;
                        //Acólito da Dor (Sempre que este lacaio receber dano, compre um card)
                        case "EX1_007":
                            ex1_007(c);
                            break;
                        //Olho por Olho (Segredo: Quando o seu herói receber dano, cause a mesma quantidade de dano ao herói inimigo)
                        case "EX1_132":
                            ex1_132(c);
                            break;
                        //Berserker Gurubashi (Sempre que este lacaio receber dano, ele recebe +3 de Ataque)
                        case "EX1_399":
                            ex1_399(c);
                            break;
                        //Ferreira de Armaduras (Sempre que um lacaio aliado receber dano, receba 1 de Armadura)
                        case "EX1_402":
                            ex1_402(c);
                            break;
                        //Berserker Espumante (Sempre que um lacaio receber dano, receba +1 de Ataque)
                        case "EX1_604":
                            ex1_604(c);
                            break;
                        //Mecano-Urso-Felino (Sempre que este lacaio receber dano, adicione um card Peça Sobressalente à sua mão)
                        case "GVG_034":
                            gvg_034(c);
                            break;
                        //Gahz'rilla (Sempre que este lacaio receber dano, dobre o Ataque dele)
                        case "GVG_049":
                            gvg_049(c);
                            break;
                        //Observador Flutuante (Sempre que seu herói receber dano no seu turno, receba +2/+2)
                        case "GVG_100":
                            gvg_100(c);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Guardião Colérico (Sempre que este lacaio receber dano, cause a mesma
     * quantidade de dano ao seu herói)
     */
    private static void at_026(Card c) {
        if (card != null && card.equals(c)) {
            c.getAnimacao().disparoDeEvento();
            card.getDono().delHealth(dano);
            card.delMechanics(Values.FURTIVIDADE);
        }
    }

    /**
     * Muriácida (Sempre que outro lacaio receber dano, destrua-o)
     *
     * @param c Card
     */
    private static void at_063(Card c) {
        if (card != null && !card.equals(c)) {
            c.getAnimacao().disparoDeEvento();
            card.morreu();
        }
    }

    /**
     * Chefe da Gangue dos Diabretes (Sempre que este lacaio receber dano,
     * evoque um Diabrete 1/1)
     *
     * @param c Card
     */
    private static void brm_006(Card c) {
        if (card != null && card.equals(c)) {
            c.getAnimacao().disparoDeEvento();
            card.getDono().evocar(partida.criarCard(Values.DIABRETE1_1, System.nanoTime()));
        }
    }

    /**
     * Atirador de Machados (Sempre que este lacaio receber dano, cause 2 de
     * dano ao herói inimigo)
     *
     * @param c Card
     */
    private static void brm_016(Card c) {
        if (card != null && card.equals(c)) {
            c.getAnimacao().disparoDeEvento();
            card.getOponente().delHealth(2);
            card.delMechanics(Values.FURTIVIDADE);
        }
    }

    /**
     * Freguês Carrancudo (Sempre que este lacaio sobreviver a dano, evoque
     * outro Freguês Carrancudo)
     *
     * @param c Card
     */
    private static void brm_019(Card c) {
        if (card != null && card.equals(c) && card.getVida() > 0) {
            c.getAnimacao().disparoDeEvento();
            card.getDono().evocar(partida.criarCard(card.getId(), System.nanoTime()));
        }
    }

    /**
     * Ovo de Dragão (Sempre que este lacaio receber dano, evoque um Dragonete
     * 2/1)
     *
     * @param c Card
     */
    private static void brm_022(Card c) {
        if (card != null && card.equals(c)) {
            c.getAnimacao().disparoDeEvento();
            card.getDono().evocar(partida.criarCard(Values.DRAGONETE2_1, System.nanoTime()));
        }
    }

    /**
     * Acólito da Dor (Sempre que este lacaio receber dano, compre um card)
     *
     * @param c Card
     */
    private static void ex1_007(Card c) {
        if (card != null && card.equals(c)) {
            c.getAnimacao().disparoDeEvento();
            card.getDono().comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Olho por Olho (Segredo: Quando o seu herói receber dano, cause a mesma
     * quantidade de dano ao herói inimigo)
     *
     * @param segredo Card
     */
    private static void ex1_132(Card segredo) {
        if (hero != null && partida.getOponente().equals(hero) && hero.isCard(segredo.id_long)) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            segredo.getOponente().delHealth(dano);
            SegredoRevelado.processar(segredo);
        }
    }

    /**
     * Berserker Gurubashi (Sempre que este lacaio receber dano, ele recebe +3
     * de Ataque)
     *
     * @param c Card
     */
    private static void ex1_399(Card c) {
        if (card != null && card.equals(c)) {
            c.getAnimacao().disparoDeEvento();
            card.addAtaque(3);
        }
    }

    /**
     * Ferreira de Armaduras (Sempre que um lacaio aliado receber dano, receba 1
     * de Armadura)
     *
     * @param c Card
     */
    private static void ex1_402(Card c) {
        if (card != null && card.getDono().equals(c.getDono())) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().addShield(1);
        }
    }

    /**
     * Berserker Espumante (Sempre que um lacaio receber dano, receba +1 de
     * Ataque)
     *
     * @param c Card
     */
    private static void ex1_604(Card c) {
        if (card != null) {
            c.getAnimacao().disparoDeEvento();
            c.addAtaque(1);
        }
    }

    /**
     * Mecano-Urso-Felino (Sempre que este lacaio receber dano, adicione um card
     * Peça Sobressalente à sua mão)
     *
     * @param c Card
     */
    private static void gvg_034(Card c) {
        if (card != null && card.equals(c)) {
            c.getAnimacao().disparoDeEvento();
            card.getDono().addMao(partida.criarCard(Random.pecaSobressalente(), System.nanoTime()));
        }
    }

    /**
     * Gahz'rilla (Sempre que este lacaio receber dano, dobre o Ataque dele)
     *
     * @param c Card
     */
    private static void gvg_049(Card c) {
        if (card != null && card.equals(c)) {
            c.getAnimacao().disparoDeEvento();
            c.addAtaque(c.getAtaque());
        }
    }

    /**
     * Observador Flutuante (Sempre que seu herói receber dano no seu turno,
     * receba +2/+2)
     *
     * @param c Card
     */
    private static void gvg_100(Card c) {
        if (hero != null && partida.getHero().equals(hero) && hero.isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            c.addVidaMaxima(2);
            c.addAtaque(2);
        }
    }
}