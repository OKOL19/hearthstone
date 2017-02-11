package com.limagiran.hearthstone.evento;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Utils;
import com.limagiran.hearthstone.util.Param;
import com.limagiran.hearthstone.partida.control.Partida;
import com.limagiran.hearthstone.heroi.control.Heroi;
import com.limagiran.hearthstone.server.GameCliente;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class PersonagemAtacando {

    private static Partida partida;
    private static Card card;
    private static Heroi hero;

    /**
     * Verifica se há algum evento para personagem atacando. Herói OU lacaio
     *
     * @param hero herói atacando
     * @param card lacaio atacando
     */
    public static void processar(Heroi hero, Card card) {
        PersonagemAtacando.partida = hero == null ? card.getPartida() : hero.getPartida();
        PersonagemAtacando.card = card;
        PersonagemAtacando.hero = hero;
        if (partida.isVezHeroi()) {
            for (Card c : partida.getAllCardsIncludeSecrets()) {
                if (!c.isSilenciado()) {
                    switch (c.getId()) {
                        //Pungista (Sempre que este lacaio atacar um herói, coloque a Moeda na sua mão)
                        case "AT_031":
                            at_031(c);
                            break;
                        //Campeão de Mogor (50% de chance de atacar o inimigo errado)
                        case "AT_088":
                            at_088(c);
                            break;
                        //Defensora Veraprateada (Quanto seu herói atacar, restaure 2 de Vida dele)
                        case "CS2_097":
                            cs2_097(c);
                            break;
                        //Sacrifício Nobre (Segredo: Quando um inimigo atacar, evoque um Defensor 2/1 para ser o novo alvo)
                        case "EX1_130":
                            ex1_130(c);
                            break;
                        //Armadilha Congelante (Segredo: Quando um lacaio inimigo atacar, devolva-o à mão do dono custando 2 a mais)
                        case "EX1_611":
                            ex1_611(c);
                            break;
                        //Ogro Guerramalho (50% de chance de atacar o inimigo errado)
                        case "GVG_054":
                            gvg_054(c);
                            break;
                        //Brutamontes Ogro (50% de chance de atacar o inimigo errado)
                        case "GVG_065":
                            gvg_065(c);
                            break;
                        //Xamã Dunamalho (Fúria dos Ventos, Sobrecarga: 1. 50% de chance de atacar o inimigo errado)
                        case "GVG_066":
                            gvg_066(c);
                            break;
                        //Ogro Ninja (Furtividade. 50% de chance de atacar o inimigo errado)
                        case "GVG_088":
                            gvg_088(c);
                            break;
                        //Mogor, o Ogro (Todos os lacaios têm 50% de chance de atacar o inimigo errado)
                        case "GVG_112":
                            gvg_112();
                            break;

                    }
                }
            }
        }
    }

    /**
     * Gera um evento padrão para 50% de chances de atacar o inimigo errado
     *
     * @param lacaio dono do evento 50% de chances...
     */
    private static void chanceDeAtacarInimigoErrado(Card lacaio) {
        if (card != null && card.equals(lacaio) && !lacaio.getOponente().getMesa().isEmpty()) {
            boolean errar = Utils.chanceErrar();
            if (errar) {
                partida.addHistorico("50% chance = Alvo alterado!");
                GameCliente.chanceDeAtacarInimigoErradoAnimacao();
                long random = Utils.getAleatorioOponente(partida, partida.getJogada().pack.getParamLong(Param.ALVO));
                partida.getJogada().pack.set(Param.ALVO, random);
                GameCliente.exibirLinhaEvento(lacaio.id_long, random);
            }
        }
    }

    /**
     * Pungista (Sempre que este lacaio atacar um herói, coloque a Moeda na sua
     * mão)
     *
     * @param c Card
     */
    private static void at_031(Card c) {
        if (card != null && card.equals(c)
                && (partida.getJogada().pack.getParamLong(Param.ALVO) == Param.HEROI
                || partida.getJogada().pack.getParamLong(Param.ALVO) == Param.OPONENTE)) {
            Card moeda = partida.criarCard(Values.MOEDA, System.nanoTime());
            c.getDono().addMao(moeda);
        }
    }

    /**
     * Campeão de Mogor (50% de chance de atacar o inimigo errado)
     *
     * @param c Card
     */
    private static void at_088(Card c) {
        chanceDeAtacarInimigoErrado(c);
    }

    /**
     * Defensora Veraprateada (Quanto seu herói atacar, restaure 2 de Vida dele)
     *
     * @param c Card
     */
    private static void cs2_097(Card c) {
        if (hero != null && hero.isCard(c.id_long)) {
            hero.addHealth(2);
        }
    }

    /**
     * Sacrifício Nobre (Segredo: Quando um inimigo atacar, evoque um Defensor
     * 2/1 para ser o novo alvo)
     *
     * @param segredo Card
     */
    private static void ex1_130(Card segredo) {
        if (partida.getOponente().isCard(segredo.id_long) && partida.getOponente().temEspacoNaMesa()) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            Card defensor = partida.criarCard(Values.DEFENSOR2_1, System.nanoTime());
            partida.getOponente().evocar(defensor);
            partida.getJogada().pack.set(Param.ALVO, defensor.id_long);
            SegredoRevelado.processar(segredo);
        }
    }

    /**
     * Armadilha Congelante (Segredo: Quando um lacaio inimigo atacar, devolva-o
     * à mão do dono custando 2 a mais)
     *
     * @param segredo Card
     */
    private static void ex1_611(Card segredo) {
        if (card != null && card.getOponente().isCard(segredo.id_long) && !card.isMorto()) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            partida.voltarPraMaoDoDono(card.id_long);
            card.addCusto(2);
            partida.getJogada().pack.set(Param.ALVO, Param.ALVO_CANCEL);
            SegredoRevelado.processar(segredo);
        }
    }

    /**
     * Ogro Guerramalho (50% de chance de atacar o inimigo errado)
     *
     * @param arma Card
     */
    private static void gvg_054(Card arma) {
        if (hero != null && hero.getArma() != null && hero.getArma().equals(arma) && !arma.getOponente().getMesa().isEmpty()) {
            boolean errar = Utils.chanceErrar();
            if (errar) {
                partida.addHistorico("50% chance = Alvo alterado!");
                GameCliente.chanceDeAtacarInimigoErradoAnimacao();
                long random = Utils.getAleatorioOponente(partida, partida.getJogada().pack.getParamLong(Param.ALVO));
                partida.getJogada().pack.set(Param.ALVO, random);
                GameCliente.exibirLinhaEvento(Param.OPONENTE, random);
            }
        }
    }

    /**
     * Brutamontes Ogro (50% de chance de atacar o inimigo errado)
     *
     * @param c Card
     */
    private static void gvg_065(Card c) {
        chanceDeAtacarInimigoErrado(c);
    }

    /**
     * Xamã Dunamalho (Fúria dos Ventos, Sobrecarga: 1. 50% de chance de atacar
     * o inimigo errado)
     *
     * @param c Card
     */
    private static void gvg_066(Card c) {
        chanceDeAtacarInimigoErrado(c);
    }

    /**
     * Ogro Ninja (Furtividade. 50% de chance de atacar o inimigo errado)
     *
     * @param c Card
     */
    private static void gvg_088(Card c) {
        chanceDeAtacarInimigoErrado(c);
    }

    /**
     * Mogor, o Ogro (Todos os lacaios têm 50% de chance de atacar o inimigo
     * errado)
     *
     * @param c Card
     */
    private static void gvg_112() {
        if (card != null && !card.getOponente().getMesa().isEmpty()) {
            boolean errar = Utils.chanceErrar();
            if (errar) {
                partida.addHistorico("50% chance = Alvo alterado!");
                GameCliente.chanceDeAtacarInimigoErradoAnimacao();
                long random = Utils.getAleatorioOponente(partida, partida.getJogada().pack.getParamLong(Param.ALVO));
                partida.getJogada().pack.set(Param.ALVO, random);
                GameCliente.exibirLinhaEvento(card.id_long, random);
            }
        }
    }

}