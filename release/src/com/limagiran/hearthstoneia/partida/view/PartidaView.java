package com.limagiran.hearthstoneia.partida.view;

/**
 * ****************************************************************************
 *
 * COPYRIGHT BLIZZARD ENTERTAINMENT, INC ALL RIGHTS RESERVED.
 *
 * This software cannot be copied, stored, distributed without Vinicius L. da
 * Silva prior authorization.
 *
 * This file was made available on
 * https://sites.google.com/site/viniciusgiran/HearthStone.jar and it is free to
 * be restributed or used under Creative Commons license 2.5 br:
 * http://creativecommons.org/licenses/by-sa/2.5/br/
 *
 *******************************************************************************
 * Este software nao pode ser copiado, armazenado ou distribuido sem autorizacao
 * a priori de Vinicius L. da Silva
 *
 * Este arquivo foi disponibilizado no site
 * https://sites.google.com/site/viniciusgiran/HearthStone.jar e esta livre para
 * distribuicao seguindo a licenca Creative Commons 2.5 br:
 * http://creativecommons.org/licenses/by-sa/2.5/br/
 *
 *****************************************************************************
 */
import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.Utils;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import static com.limagiran.hearthstone.HearthStone.CARTAS;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstoneia.server.GameCliente;
import java.awt.Color;
import java.io.Serializable;
import com.limagiran.hearthstone.util.Values;

public final class PartidaView implements Serializable {

    public static final Color TRANSPARENTE = new Color(0, 0, 0, 0);
    public static final int PLAYER_ONE = 0;
    public static final int PLAYER_TWO = 1;
    public boolean ESPERANDO_ALVO = false;

    public boolean pintarLinhaEvento = false;

    public final Partida PARTIDA;
    private final Heroi hero;
    private final boolean inicioTurno;
    private boolean muligado = false;
    public boolean bloqueado = false;
    public boolean partidaEncerrada = false;
    private long alvoSelecionado;

    private static boolean partidaTeste = false;

    private PartidaView(Partida partida, boolean inicioTurno) {
        PARTIDA = partida;
        this.inicioTurno = inicioTurno;
        hero = PARTIDA.getHero();
        PARTIDA.setPartidaView(getInstance());
    }

    public static PartidaView main(Partida partida, boolean inicioTurno) {
        partidaTeste = false;
        return new PartidaView(partida, inicioTurno);
    }

    public PartidaView getInstance() {
        return this;
    }

    /**
     * Passar o turno Desativa o botão Passar Turno
     */
    public void passarTurno() {
        if (PARTIDA.isVezHeroi()) {
            PARTIDA.passarTurno();
        }
    }

    /**
     * Abre a janela para muligar as cartas iniciais
     */
    private void muligar() {
        if (!partidaTeste) {
            Muligar.main(PARTIDA);
        }
        if (!PARTIDA.isVezHeroi()) {
            Card moeda = CARTAS.createCardIA(Values.MOEDA);
            hero.getDeck().add(4, moeda);
        }
        PARTIDA.addAllCards(hero.getDeck());
        GameCliente.enviarDeck(hero.getDeck());
    }

    public void start() {
        muligar();
        new Thread(() -> {
            if (!partidaTeste) {
                while (!muligado) {
                    Utils.sleep(50);
                }
            }
            Utils.sleep(100);
            if (inicioTurno) {
                PARTIDA.iniciarPartida();
            }
        }).start();
    }

    /**
     * Variável muligado, verifica se a muligação já foi feita
     *
     * @return true or false
     */
    public boolean isMuligado() {
        return muligado;
    }

    /**
     * Variável muligado, verifica se a muligação já foi feita
     *
     * @param muligado true or false
     */
    public void setMuligado(boolean muligado) {
        this.muligado = muligado;
    }

    /**
     * Jogo encerrado
     */
    public void jogoEncerrado() {
        setPartidaEncerrada(true);
        Utils.sleep(3000);
        GameCliente.close();
    }

    /**
     * Oponente desistiu
     */
    public void oponenteDesistiu() {
        GameCliente.close();
    }

    /**
     * Verifica se tem alguma animação ocorrendo atualmente
     *
     * @return true or false
     */
    public boolean isBloqueado() {
        return bloqueado;
    }

    /**
     * Altera o valor da variável animação que verifica se tem alguma animação
     * acontecendo atualmente
     *
     * @param bloqueado
     */
    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public long getAlvoSelecionado() {
        return alvoSelecionado;
    }

    public void setAlvoSelecionado(long alvoSelecionado) {
        this.alvoSelecionado = alvoSelecionado;
    }

    /**
     * Verifica se a partida foi encerrada
     *
     * @return true or false
     */
    public boolean isPartidaEncerrada() {
        return partidaEncerrada;
    }

    /**
     * Verifica se a partida foi encerrada
     *
     * @param partidaEncerrada
     */
    public void setPartidaEncerrada(boolean partidaEncerrada) {
        this.partidaEncerrada = partidaEncerrada;
    }

    public boolean getPartidaEncerrada() {
        return partidaEncerrada;
    }

}