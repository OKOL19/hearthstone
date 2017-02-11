package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import com.limagiran.hearthstone.util.JsonUtils;
import com.limagiran.hearthstone.partida.control.Pacote;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstoneia.server.GameCliente;
import com.limagiran.hearthstone.util.Param;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.util.Values;


/**
 *
 * @author Vinicius
 */
public class AuraEstatica  implements Serializable {

    public static final String FEITICO_MAO_CUSTAM_X_DAQUI_X_TURNOS = "fmcxdxt";
    public static final String PODER_HEROI_CUSTA_X_DAQUI_X_TURNOS = "phcxdxt";

    private final Partida partida;

    private final List<Pacote> PACKS = new ArrayList<>();

    public AuraEstatica(Partida partida) {
        this.partida = partida;
    }

    /**
     * Adiciona uma aura estática
     *
     * @param pacote pacote com as configurações da aura estática
     */
    public void add(Pacote pacote) {
        PACKS.add(pacote);
        Pacote clone = pacote.clone();
        long alvo = clone.getParamLong(Param.ALVO);
        clone.set(Param.ALVO, alvo == Param.HEROI
                ? Param.OPONENTE
                : alvo == Param.OPONENTE
                        ? Param.HEROI
                        : Param.AMBOS);
        GameCliente.enviar(Param.AURA_ESTATICA_ADD, JsonUtils.toJSon(clone));
    }

    /**
     * Remove uma aura estática
     *
     * @param index índice do pacote removido
     */
    public void remove(int index) {
        PACKS.remove(index);
    }

    /**
     * Altera o tempo de espera de uma aura estática
     *
     * @param index índice do pacote
     * @param value novo valor do sleep
     */
    public void setSleep(int index, int value) {
        PACKS.get(index).set(Param.SLEEP, value);
    }

    /**
     * Atualiza as auras estáticas configuradas
     */
    public void refresh() {
        for (Pacote pack : PACKS) {
            int sleep = pack.getParamInt(Param.SLEEP);
            switch (pack.TIPO) {
                case FEITICO_MAO_CUSTAM_X_DAQUI_X_TURNOS:
                    if (sleep == 0) {
                        feiticosNaMaoCustamXDaquiAXTurnos(pack);
                    }
                    break;
                case PODER_HEROI_CUSTA_X_DAQUI_X_TURNOS:
                    if (sleep == 0) {
                        poderHeroicoCustaXDaquiAXTurnos(pack);
                    }
                    break;
            }
        }
        partida.getPodeAtacar().processar();
    }

    /**
     * Remove 1 sleep (tempo de espera para ativação) de todas as auras
     * estáticas e remove as auras com sleep menores do que 0 (zero).
     */
    public void decrement() {
        for (int i = 0; i < PACKS.size(); i++) {
            int sleep = PACKS.get(i).getParamInt(Param.SLEEP);
            setSleep(i, sleep - 1);
            if (sleep == -1) {
                remove(i);
                i--;
            }
        }
    }

    /**
     * Adiciona uma aura estática para um feitiço que vai ter seu custo alterado
     *
     * @param value valor da alteração
     * @param addOuExato Param.ADD para valor a ser ADICIONADO ao valor
     * existente ou Param.EXATO para alterar para o valor passado por parâmetro
     * @param heroi código long do herói (Param.HEROI ou Param.OPONENTE)
     * @param sleep quantidade de turnos esperados para a ativação da aura
     */
    public void feiticosMaoCustamXDaquiAXTurnos(int value, String addOuExato, long heroi, int sleep) {
        Pacote pack = new Pacote(FEITICO_MAO_CUSTAM_X_DAQUI_X_TURNOS);
        pack.set(Param.VALUE, value);
        pack.set(Param.ADD_OU_EXATO, addOuExato);
        pack.set(Param.SLEEP, sleep);
        pack.set(Param.ALVO, heroi);
        add(pack);
    }

    /**
     * Adiciona uma aura estática para um feitiço que vai ter seu custo alterado
     *
     * @param pack pacote configurado
     */
    private void feiticosNaMaoCustamXDaquiAXTurnos(Pacote pack) {
        long alvo = pack.getParamLong(Param.ALVO);
        List<Card> cards = alvo == Param.HEROI
                ? partida.getHero().getMao()
                : alvo == Param.OPONENTE
                        ? partida.getOponente().getMao()
                        : partida.getMao();
        String type = pack.getParamString(Param.ADD_OU_EXATO);
        int value = pack.getParamInt(Param.VALUE);
        for (Card c : cards) {
            if (c.getType().equals(Values.FEITICO)) {
                if (type.equals(Param.ADD)) {
                    c.getAura().addCusto(value);
                } else {
                    c.getAura().setCusto(value - c.getCustoAlterado(false));
                }
            }
        }
    }

    /**
     * Adiciona uma aura estática para um poder heroico que vai ter seu custo
     * alterado
     *
     * @param value valor da alteração
     * @param addOuExato Param.ADD para valor a ser ADICIONADO ao valor
     * existente ou Param.EXATO para alterar para o valor passado por parâmetro
     * @param heroi código long do herói (Param.HEROI ou Param.OPONENTE)
     * @param sleep quantidade de turnos esperados para a ativação da aura
     */
    public void poderHeroicoCustaXDaquiAXTurnos(int value, String addOuExato, long heroi, int sleep) {
        Pacote pack = new Pacote(PODER_HEROI_CUSTA_X_DAQUI_X_TURNOS);
        pack.set(Param.VALUE, value);
        pack.set(Param.ADD_OU_EXATO, addOuExato);
        pack.set(Param.SLEEP, sleep);
        pack.set(Param.ALVO, heroi);
        add(pack);
    }

    /**
     * Adiciona uma aura estática para um poder heroico que vai ter seu custo
     * alterado
     *
     * @param pack pacote configurado
     */
    private void poderHeroicoCustaXDaquiAXTurnos(Pacote pack) {
        long alvo = pack.getParamLong(Param.ALVO);
        Heroi hero = alvo == Param.HEROI ? partida.getHero() : partida.getOponente();
        String type = pack.getParamString(Param.ADD_OU_EXATO);
        int value = pack.getParamInt(Param.VALUE);
        if (type.equals(Param.ADD)) {
            hero.getPoderHeroico().addCustoAura(value);
        } else {
            hero.getPoderHeroico().setCustoAura(value);
        }
    }
}