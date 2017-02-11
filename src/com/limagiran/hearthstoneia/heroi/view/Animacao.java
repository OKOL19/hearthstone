package com.limagiran.hearthstoneia.heroi.view;

import com.limagiran.hearthstoneia.heroi.control.Heroi;
import com.limagiran.hearthstoneia.server.GameCliente;
import com.limagiran.hearthstone.util.Param;
import java.io.Serializable;


/**
 *
 * @author Vinicius Silva
 */
public final class Animacao implements Serializable {

    private static final int VIDA = 0;
    private static final int ESCUDO = 1;
    private static final int ATAQUE = 2;

    private final Heroi heroi;

    public Animacao(Heroi heroi) {
        this.heroi = heroi;
    }

    @Override
    public String toString() {
        return heroi.getToString();
    }

    /**
     * Animação de cura ou dano
     *
     * @param value valor da cura ou do dano (positivo para cura, negativo para
     * dano)
     * @param time tempo de exibição da animação em milissegundos
     */
    public void vida(int value, long time) {
        vida(value, time, true);
    }

    /**
     * Animação de cura ou dano
     *
     * @param value valor da cura ou do dano (positivo para cura, negativo para
     * dano)
     * @param time tempo de exibição da animação em milissegundos
     * @param sleep ativar Thread.sleep {@code true} ou {@code false}
     */
    public void vida(int value, long time, boolean sleep) {
        enviarPacote(VIDA, value);
    }

    /**
     * Animação de escudo alterado
     *
     * @param value valor alterado do escudo (positivo ou negativo)
     * @param time tempo de exibição da animação em milissegundos
     */
    public void escudo(int value, long time) {
        escudo(value, time, true);
    }

    /**
     * Animação de escudo alterado
     *
     * @param value valor alterado do escudo (positivo ou negativo)
     * @param time tempo de exibição da animação em milissegundos
     * @param sleep ativar Thread.sleep {@code true} ou {@code false}
     */
    public void escudo(int value, long time, boolean sleep) {
        if (value > 0) {
            enviarPacote(ESCUDO, value);
        }
    }

    /**
     * Animação de ataque alterado
     *
     * @param value valor alterado do ataque (positivo ou negativo)
     * @param time tempo de exibição da animação em milissegundos
     */
    public void ataque(int value, long time) {
        ataque(value, time, true);
    }

    /**
     * Animação de ataque alterado
     *
     * @param value valor alterado do ataque (positivo ou negativo)
     * @param time tempo de exibição da animação em milissegundos
     * @param sleep ativar Thread.sleep {@code true} ou {@code false}
     */
    public void ataque(int value, long time, boolean sleep) {
        if (value != 0) {
            enviarPacote(ATAQUE, value);
        }
    }

    /**
     * Enviar pacote da animação pela rede
     *
     * @param type tipo da animação
     * @param value valor do atributo
     */
    public void enviarPacote(int type, int value) {
        switch (type) {
            case VIDA:
                GameCliente.enviar(heroi.getHeroi() != Param.HEROI
                        ? Param.ANIMACAO_HEROI_VIDA
                        : Param.ANIMACAO_OPONENTE_VIDA, value);
                break;
            case ESCUDO:
                GameCliente.enviar(heroi.getHeroi() != Param.HEROI
                        ? Param.ANIMACAO_HEROI_ESCUDO
                        : Param.ANIMACAO_OPONENTE_ESCUDO, value);
                break;
            case ATAQUE:
                GameCliente.enviar(heroi.getHeroi() != Param.HEROI
                        ? Param.ANIMACAO_HEROI_ATAQUE
                        : Param.ANIMACAO_OPONENTE_ATAQUE, value);
                break;
        }
    }

}