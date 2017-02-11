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
 * @author Vinicius
 */
public class PersonagemSendoAtacado {

    private static Partida partida;
    private static Heroi alvoHero;
    private static Card alvoCard;

    /**
     * Verifica se há algum evento para personagem sendo atacado. heroi OU
     * lacaio.
     *
     * @param hero herói sendo atacado
     * @param card lacaio sendo atacado
     */
    public static void processar(Heroi hero, Card card) {
        PersonagemSendoAtacado.partida = hero == null ? card.getPartida() : hero.getPartida();
        alvoHero = hero;
        alvoCard = card;
        if (partida.isVezHeroi()) {
            for (Card c : partida.getAllCardsIncludeSecrets()) {
                if (!c.isSilenciado()) {
                    switch (c.getId()) {
                        //Barreira de Gelo (Segredo: Quando seu herói for atacado, receba 8 de Armadura)
                        case "EX1_289":
                            ex1_289(c);
                            break;
                        //Redirecionar (Segredo: Quando um personagem tentar atacar o seu herói, ele atacará outro personagem aleatório)
                        case "EX1_533":
                            ex1_533(c);
                            break;
                        //Armadilha de Cobra (Segredo: Quando um de seus lacaios for atacado, evoque três Cobras 1/1)
                        case "EX1_554":
                            ex1_554(c);
                            break;
                        //Vaporizar (Segredo: Quando um lacaio atacar seu herói, destrua-o)
                        case "EX1_594":
                            ex1_594(c);
                            break;
                        //Armadilha Explosiva (Segredo: Quando seu herói for atacado, cause 2 de dano a todos os inimigos)
                        case "EX1_610":
                            ex1_610(c);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Barreira de Gelo (Segredo: Quando seu herói for atacado, receba 8 de
     * Armadura)
     */
    private static void ex1_289(Card segredo) {
        if (alvoHero != null && alvoHero.isCard(segredo.id_long)) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            alvoHero.addShield(8);
            SegredoRevelado.processar(segredo);
        }
    }

    /**
     * Redirecionar (Segredo: Quando um personagem tentar atacar o seu herói,
     * ele atacará outro personagem aleatório)
     *
     * @param segredo Card
     */
    private static void ex1_533(Card segredo) {
        Card atacante = partida.findCardByIDLong(partida.getJogada().pack.getParamLong(Param.ATACANTE));
        if (!partida.getMesa().isEmpty()
                && (atacante == null || !atacante.isMorto())
                && alvoHero != null
                && alvoHero.isCard(segredo.id_long)) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            long novoAlvo = Utils.getPersonagemAleatorio(partida, Param.OPONENTE);
            partida.addHistorico("Novo alvo: " + (novoAlvo == Param.HEROI
                    ? partida.getHero().getNome()
                    : partida.findCardByIDLong(novoAlvo).getName()));
            partida.getJogada().pack.set(Param.ALVO, novoAlvo);
            SegredoRevelado.processar(segredo);
        }
    }

    /**
     * Armadilha de Cobra (Segredo: Quando um de seus lacaios for atacado,
     * evoque três Cobras 1/1)
     *
     * @param segredo Card
     */
    private static void ex1_554(Card segredo) {
        if (alvoCard != null && alvoCard.getDono().equals(segredo.getDono()) && segredo.getDono().temEspacoNaMesa()) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            for (int i = 0; i < 3; i++) {
                segredo.getDono().evocar(partida.criarCard(Values.COBRA1_1, System.nanoTime()));
            }
            SegredoRevelado.processar(segredo);
        }
    }

    /**
     * Vaporizar (Segredo: Quando um lacaio atacar seu herói, destrua-o)
     *
     * @param segredo Card
     */
    private static void ex1_594(Card segredo) {
        if (alvoHero != null && alvoHero.isCard(segredo.id_long)) {
            Card atacante = partida.findCardByIDLong(partida.getJogada().pack.getParamLong(Param.ATACANTE));
            if (atacante != null && !atacante.isMorto()) {
                GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
                atacante.morreu();
                partida.getJogada().pack.set(Param.ALVO, Param.ALVO_CANCEL);
                SegredoRevelado.processar(segredo);
            }
        }
    }

    /**
     * Armadilha Explosiva (Segredo: Quando seu herói for atacado, cause 2 de
     * dano a todos os inimigos)
     *
     * @param segredo Card
     */
    private static void ex1_610(Card segredo) {
        if (alvoHero != null && alvoHero.isCard(segredo.id_long)) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            segredo.getOponente().delHealth(Utils.getDanoFeitico(2, segredo.getDono()));
            EmArea.dano(partida, segredo.getOponente().getMesa(), Utils.getDanoFeitico(2, segredo.getDono()), 0);
            Card atacante = partida.findCardByIDLong(partida.getJogada().pack.getParamLong(Param.ATACANTE));
            if (atacante != null && atacante.getDono().getMorto().contains(atacante)) {
                partida.getJogada().pack.set(Param.ALVO, Param.ALVO_CANCEL);
            }
            SegredoRevelado.processar(segredo);
        }
    }

}