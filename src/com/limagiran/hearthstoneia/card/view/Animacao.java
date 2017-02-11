package com.limagiran.hearthstoneia.card.view;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstone.partida.control.Pacote;
import com.limagiran.hearthstoneia.server.GameCliente;
import com.limagiran.hearthstone.util.Param;
import java.io.Serializable;

/**
 *
 * @author Vinicius Silva
 */
public final class Animacao implements Serializable {
    
    private final Card card;

    public Animacao(Card card) {
        this.card = card;
    }

    /**
     * Animação de cura ou dano
     *
     * @param value valor da cura ou do dano (positivo para cura, negativo para
     * dano)
     * @param time tempo de exibição da animação em milissegundos
     */
    public void vidaAtual(int value, long time) {
        vidaAtual(value, time, true, true);
    }

    /**
     * Animação de cura ou dano
     *
     * @param value valor da cura ou do dano (positivo para cura, negativo para
     * dano)
     * @param time tempo de exibição da animação em milissegundos
     * @param sleep ativar Thread.sleep {@code true} ou {@code false}
     * @param enviar enviar o pacote de animação pela rede {@code true} ou
     * {@code false}
     */
    public void vidaAtual(int value, long time, boolean sleep, boolean enviar) {
        if (enviar) {
            enviarPacote(Param.ANIMACAO_CARD_VIDA_ATUAL, value);
        }
    }

    /**
     * Animação de vidaAtual alterada
     *
     * @param value quantidade de vidaAtual alterada (positivo ou negativo)
     * @param time tempo de exibição da animação em milissegundos
     */
    public void vidaMaxima(int value, long time) {
        vidaMaxima(value, time, true);
    }

    /**
     * Animação de vidaAtual alterada
     *
     * @param value quantidade de vidaAtual alterada (positivo ou negativo)
     * @param time tempo de exibição da animação em milissegundos
     * @param sleep ativar Thread.sleep {@code true} ou {@code false}
     */
    public void vidaMaxima(int value, long time, boolean sleep) {
        if (value != 0) {
            enviarPacote(Param.ANIMACAO_CARD_VIDA_MAXIMA, value);
        }
    }

    /**
     * Animação de ataque alterado
     *
     * @param value quantidade de ataque alterado (positivo ou negativo)
     * @param time tempo de exibição da animação em milissegundos
     */
    public void ataque(int value, long time) {
        ataque(value, time, true);
    }

    /**
     * Animação de ataque alterado
     *
     * @param value quantidade de ataque alterado (positivo ou negativo)
     * @param time tempo de exibição da animação em milissegundos
     * @param sleep ativar Thread.sleep {@code true} ou {@code false}
     */
    public void ataque(int value, long time, boolean sleep) {
        if (value != 0) {
            enviarPacote(Param.ANIMACAO_CARD_ATAQUE, value);
        }
    }

    /**
     * Animação de vidaAtual original alterada
     *
     * @param value novo valor de vidaAtual
     * @param time tempo de exibição da animação em milissegundos
     */
    public void vidaOriginal(int value, long time) {
        if (value > 0) {
            enviarPacote(Param.ANIMACAO_CARD_VIDA_ORIGINAL, value);
        }
    }

    /**
     * Animação realizada quando um lacaio é a origem de um gatilho
     */
    public void disparoDeEvento() {
        if (card.getDono().getMesa().contains(card)) {
            enviarPacote(Param.ANIMACAO_CARD_GATILHO, 0);
        }
    }

    /**
     * Enviar pacote da animação pela rede
     *
     * @param type tipo da animação
     * @param value valor do atributo
     */
    public void enviarPacote(String type, int value) {
        Pacote pack = new Pacote(type);
        pack.set(Param.CARD_ID_LONG, card.id_long);
        pack.set(Param.VALUE, value);
        GameCliente.enviar(pack);
    }
}