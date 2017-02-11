package com.limagiran.hearthstone.card.control;

import com.limagiran.hearthstone.heroi.control.Heroi;
import com.limagiran.hearthstone.partida.control.Pacote;
import static com.limagiran.hearthstone.util.Param.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.server.GameCliente;
import com.limagiran.hearthstone.util.Param;

/**
 *
 * @author Vinicius Silva
 */
public class Aura implements Serializable {

    private int ataque = 0;
    private int vidaAtual = 0;
    private int vidaMaxima = 0;
    private int custo = 0;
    private int danoAdicional = 0;
    private boolean investida = false;
    private boolean imune = false;
    private boolean imuneAlvo = false;
    private final List<Long> aurasAdicionadas = new ArrayList<>();
    public final String type;

    private final Card card;
    private final long id_long;

    public final static String HEROI = "heroi";
    public final static String OPONENTE = "oponente";
    public final static String CARD = "card";
    private final String ATAQUE = "ataque";
    private final String VIDA_ATUAL = "vida_atual";
    private final String VIDA_MAXIMA = "vida_maxima";
    private final String CUSTO = "custo";
    private final String DANO_ADICIONAL = "danoAdicional";
    private final String INVESTIDA = "investida";
    private final String IMUNE = "imune";
    private final String IMUNE_ALVO = "imuneAlvo";
    private final String AURAS_ADDS_ADD = "aurasAdicionadasAdd";
    private final String AURAS_CLEAR = "aurasClear";

    private static boolean podeEnviar = true;

    private final Pacote salvo = new Pacote("");

    /**
     * Criar uma aura para um card
     *
     * @param card dono da aura
     */
    public Aura(Card card) {
        this.card = card;
        type = CARD;
        this.id_long = this.card.id_long;
    }

    /**
     * Cria uma aura para um herói
     *
     * @param hero dono da aura
     */
    public Aura(Heroi hero) {
        this.card = null;
        this.type = hero.getHeroi() != Param.HEROI ? HEROI : OPONENTE;
        this.id_long = hero.getHeroi();
    }

    /**
     * Salva o status atual de todos os atributos e volta aos valores padrões
     */
    public void salvarELimpar() {
        podeEnviar = false;
        salvo.set(ATAQUE, ataque);
        salvo.set(VIDA_MAXIMA, vidaMaxima);
        salvo.set(CUSTO, custo);
        salvo.set(DANO_ADICIONAL, danoAdicional);
        salvo.set(INVESTIDA, investida);
        salvo.set(IMUNE, imune);
        salvo.set(IMUNE_ALVO, imuneAlvo);
        clear();
    }

    /**
     * Altera os atributos para os valores padrões
     */
    private void clear() {
        ataque = 0;
        vidaMaxima = 0;
        custo = 0;
        danoAdicional = 0;
        investida = false;
        imune = false;
        imuneAlvo = false;
    }

    /**
     * Adiciona um idTime do lacaio que está na mesa que adiciona Health a um
     * lacaio para o vidaMaxima não ser recalculado, apenas a vidaAtual
     *
     * @param idTime long time do lacaio na mesa
     */
    public void addID(long idTime) {
        aurasAdicionadas.add(idTime);
        enviar(AURAS_ADDS_ADD, idTime);
    }

    /**
     * Verifica se o idTime está na lista de auras adicionadas
     *
     * @param idTime idTime do lacaio
     * @return {@code true} ou {@code false}
     */
    public boolean findByID(Long idTime) {
        return aurasAdicionadas.stream().anyMatch((l) -> (l.equals(idTime)));
    }

    public int getAtaque() {
        return ataque;
    }

    public void addAtaque(int ataque) {
        setAtaque(this.ataque + ataque);
        if (card != null) {
            card.setAtaquesRealizados(card.getAtaquesRealizados());
        }
    }

    public void delAtaque(int ataque) {
        setAtaque(this.ataque - ataque);
    }

    public int getVida() {
        return vidaAtual;
    }

    public void addVida(int vida) {
        if (this.vidaAtual < vidaMaxima) {
            this.vidaAtual += vida;
            this.vidaAtual = this.vidaAtual > vidaMaxima ? vidaMaxima : this.vidaAtual;
            setVida(this.vidaAtual);
        }
    }

    public void delVida(int vida) {
        setVida(this.vidaAtual - vida);
    }

    public int getCusto() {
        return custo;
    }

    public void addCusto(int custo) {
        setCusto(this.custo + custo);
    }

    public void delCusto(int custo) {
        setCusto(this.custo - custo);
    }

    public int getDanoAdicional() {
        return danoAdicional;
    }

    public void addDanoAdicional(int danoAdicional) {
        setDanoAdicional(this.danoAdicional + danoAdicional);
    }

    public void delDanoAdicional(int danoAdicional) {
        setDanoAdicional(this.danoAdicional - danoAdicional);
    }

    public boolean isInvestida() {
        return investida;
    }

    public void setInvestida(boolean investida) {
        this.investida = investida;
        enviar(INVESTIDA, this.investida);
        if (card != null) {
            card.setAtaquesRealizados(card.getAtaquesRealizados());
        }
    }

    public boolean isImune() {
        return imune;
    }

    public void setImune(boolean imune) {
        this.imune = imune;
        enviar(IMUNE, this.imune);
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
        enviar(ATAQUE, this.ataque);
    }

    public void setVida(int vida) {
        this.vidaAtual = vida;
        enviar(VIDA_ATUAL, this.vidaAtual);
    }

    public void setCusto(int custo) {
        this.custo = custo;
        enviar(CUSTO, this.custo);
    }

    public void setDanoAdicional(int danoAdicional) {
        this.danoAdicional = danoAdicional;
        enviar(DANO_ADICIONAL, this.danoAdicional);
    }

    public boolean isImuneAlvo() {
        return imuneAlvo;
    }

    public void setImuneAlvo(boolean imuneAlvo) {
        this.imuneAlvo = imuneAlvo;
        enviar(IMUNE_ALVO, this.imuneAlvo);
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void addVidaMaxima(int v) {
        setVidaMaxima(vidaMaxima + v);
    }

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
        enviar(VIDA_MAXIMA, this.vidaMaxima);
    }

    /**
     * Enviar pela rede o atributo alterado
     *
     * @param tipo tipo do atributo
     * @param value novo valor do atributo
     */
    private void enviar(String tipo, Object value) {
        if (podeEnviar) {
            Pacote pack = new Pacote(getTypePack(tipo));
            if (id_long != 0) {
                pack.set(CARD_ID_LONG, id_long);
            }
            pack.set(VALUE, value.toString());
            GameCliente.send(pack);
        }
    }

    /**
     * Envia os atributos que tiveram alteração na atualização das auras
     */
    public void enviarValoresAlterados() {
        podeEnviar = true;
        if (ataque != salvo.getParamInt(ATAQUE)) {
            setAtaque(ataque);
        }
        if (vidaMaxima != salvo.getParamInt(VIDA_MAXIMA)) {
            setVidaMaxima(vidaMaxima);
        }
        if (custo != salvo.getParamInt(CUSTO)) {
            setCusto(custo);
        }
        if (danoAdicional != salvo.getParamInt(DANO_ADICIONAL)) {
            setDanoAdicional(danoAdicional);
        }
        if (investida != salvo.getParamBoolean(INVESTIDA)) {
            setInvestida(investida);
        }
        if (imune != salvo.getParamBoolean(IMUNE)) {
            setImune(imune);
        }
        if (imuneAlvo != salvo.getParamBoolean(IMUNE_ALVO)) {
            setImuneAlvo(imuneAlvo);
        }
    }

    /**
     * Retorna o nome do TIPO do pacote enviado pela rede (heroi, oponente ou
     * card)
     *
     * @param atributo atributo da aura
     * @return TIPO do pacote
     */
    private String getTypePack(String atributo) {
        switch (atributo) {
            case ATAQUE:
                return type.equals(HEROI)
                        ? AURA_HEROI_SET_ATAQUE
                        : type.equals(OPONENTE)
                        ? AURA_OPONENTE_SET_ATAQUE
                        : AURA_CARD_SET_ATAQUE;
            case CUSTO:
                return type.equals(HEROI)
                        ? AURA_HEROI_SET_CUSTO
                        : type.equals(OPONENTE)
                        ? AURA_OPONENTE_SET_CUSTO
                        : AURA_CARD_SET_CUSTO;
            case DANO_ADICIONAL:
                return type.equals(HEROI)
                        ? AURA_HEROI_SET_DANO_ADICIONAL
                        : type.equals(OPONENTE)
                        ? AURA_OPONENTE_SET_DANO_ADICIONAL
                        : AURA_CARD_SET_DANO_ADICIONAL;
            case VIDA_MAXIMA:
                return type.equals(HEROI)
                        ? AURA_HEROI_SET_VIDA_MAXIMA
                        : type.equals(OPONENTE)
                        ? AURA_OPONENTE_SET_VIDA_MAXIMA
                        : AURA_CARD_SET_VIDA_MAXIMA;
            case IMUNE:
                return type.equals(HEROI)
                        ? AURA_HEROI_SET_IMUNE
                        : type.equals(OPONENTE)
                        ? AURA_OPONENTE_SET_IMUNE
                        : AURA_CARD_SET_IMUNE;
            case IMUNE_ALVO:
                return type.equals(HEROI)
                        ? AURA_HEROI_SET_IMUNE_ALVO
                        : type.equals(OPONENTE)
                        ? AURA_OPONENTE_SET_IMUNE_ALVO
                        : AURA_CARD_SET_IMUNE_ALVO;
            case INVESTIDA:
                return type.equals(HEROI)
                        ? AURA_HEROI_SET_INVESTIDA
                        : type.equals(OPONENTE)
                        ? AURA_OPONENTE_SET_INVESTIDA
                        : AURA_CARD_SET_INVESTIDA;
            case VIDA_ATUAL:
                return type.equals(HEROI)
                        ? AURA_HEROI_SET_VIDA_ATUAL
                        : type.equals(OPONENTE)
                        ? AURA_OPONENTE_SET_VIDA_ATUAL
                        : AURA_CARD_SET_VIDA_ATUAL;
            case AURAS_ADDS_ADD:
                return type.equals(HEROI)
                        ? AURA_HEROI_SET_AURAS_ADDS_ADD
                        : type.equals(OPONENTE)
                        ? AURA_OPONENTE_SET_AURAS_ADDS_ADD
                        : AURA_CARD_SET_AURAS_ADDS_ADD;
            case AURAS_CLEAR:
                return type.equals(HEROI)
                        ? AURA_HEROI_CLEAR
                        : type.equals(OPONENTE)
                        ? AURA_OPONENTE_CLEAR
                        : AURA_CARD_CLEAR;
        }
        return "";
    }

}