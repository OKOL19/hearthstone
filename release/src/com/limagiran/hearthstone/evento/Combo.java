package com.limagiran.hearthstone.evento;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.*;
import com.limagiran.hearthstone.heroi.control.JogouCardException;
import com.limagiran.hearthstone.partida.control.Partida;
import com.limagiran.hearthstone.partida.util.EscolherMesa;

/**
 *
 * @author Vinicius Silva
 */
public class Combo {

    private static Partida partida;
    private static Card card;

    /**
     * Verifica se há algum evento para Combo
     *
     * @param card card que gerou o evento
     * @throws com.limagiran.hearthstone.heroi.control.JogouCardException
     */
    public static void processar(Card card) throws JogouCardException {
        Combo.card = card;
        Combo.partida = card.getPartida();
        if (partida.isVezHeroi() && Utils.isCombo(Combo.card)) {
            switch (card.getId()) {
                //Cavalgante Shado-Pan (Combo: Receba +3 de Ataque)
                case "AT_028":
                    at_028();
                    break;
                //Valente da Cidade Baixa (Combo: Cause 1 de dano)
                case "AT_030":
                    at_030();
                    break;
                //Líder Défias (Combo: Evoque um Bandido Défias 2/1)
                case "EX1_131":
                    ex1_131();
                    break;
                //Agente da AVIN (Combo: Cause 2 de dano)
                case "EX1_134":
                    ex1_134();
                    break;
                //Edwin VanCleef (Combo: Receba +2/+2 para cada um dos cards jogados antes neste turno)
                case "EX1_613":
                    ex1_613();
                    break;
                //Sequestrador (Combo: Devolva um lacaio à mão do dono)
                case "NEW1_005":
                    new1_005();
                    break;
            }
        }
    }

    /**
     * Gera o evento padrão para causar dano em um personagem
     *
     * @param dano valor do dano
     */
    private static void causarDano(int dano) throws JogouCardException {
        long escolhido = EscolherMesa.main(1L,card, "Cause " + dano + " de dano", partida, false);
        if (escolhido != EscolherMesa.CANCEL) {
            Utils.dano(partida, escolhido, dano);
        } else {
            throw new JogouCardException("Combo cancelado.");
        }
    }

    /**
     * Cavalgante Shado-Pan (Combo: Receba +3 de Ataque)
     */
    private static void at_028() {
        card.addAtaque(3);
    }

    /**
     * Valente da Cidade Baixa (Combo: Cause 1 de dano)
     */
    private static void at_030() throws JogouCardException {
        causarDano(1);
    }

    /**
     * Líder Défias (Combo: Evoque um Bandido Défias 2/1)
     */
    private static void ex1_131() {
        card.getDono().evocar(card.getDono().getPosicaoNaMesa(card.id_long) + 1,
                partida.criarCard(Values.BANDIDO_DEFIAS2_1, System.nanoTime()));
    }

    /**
     * Agente da AVIN (Combo: Cause 2 de dano)
     */
    private static void ex1_134() throws JogouCardException {
        causarDano(2);
    }

    /**
     * Edwin VanCleef (Combo: Receba +2/+2 para cada um dos cards jogados antes
     * neste turno)
     */
    private static void ex1_613() {
        card.addVidaMaxima(card.getDono().getCardsJogadosNaRodada().size() * 2);
        card.addAtaque(card.getDono().getCardsJogadosNaRodada().size() * 2);
    }

    /**
     * Sequestrador (Combo: Devolva um lacaio à mão do dono)
     */
    private static void new1_005() throws JogouCardException {
        if (partida.getMesa().size() > 1) {
            long escolhido = EscolherMesa.main(1L, partida.getPartidaView(), card, "Devolva um lacaio à mão do dono",
                    null, null, card.getDono().getMesa(), card.getOponente().getMesa(), false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.voltarPraMaoDoDono(escolhido);
            } else {
                throw new JogouCardException("Combo cancelado.");
            }
        }
    }
}