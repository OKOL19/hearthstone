package com.limagiran.hearthstone.evento;

import com.limagiran.hearthstone.util.*;
import com.limagiran.hearthstone.partida.control.Pacote;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.server.GameCliente;

/**
 *
 * @author Vinicius Silva
 */
public class AlterarCusto implements Serializable {

    private final Class classPartida = com.limagiran.hearthstone.partida.control.Partida.class;
    private final Class classPartidaIA = com.limagiran.hearthstoneia.partida.control.Partida.class;
    private Object partida;
    public static final String TYPE = "type";
    public static final String PLAYER = "player";
    public static final String RACE = "race";
    public static final String VALUE = "value";
    private final List<Integer> LIST_PLAYER = new ArrayList<>();
    private final List<String> LIST_TYPE = new ArrayList<>();
    private final List<String> LIST_RACE = new ArrayList<>();
    private final List<Integer> LIST_VALUE = new ArrayList<>();

    public AlterarCusto(Object partida) throws IllegalArgumentException {
        if (!classPartida.isInstance(partida) && !classPartidaIA.isInstance(partida)) {
            throw new IllegalArgumentException();
        }
        this.partida = partida;
    }

    public AlterarCusto clone(Object partida) throws IllegalArgumentException {
        if (!classPartida.isInstance(partida) && !classPartidaIA.isInstance(partida)) {
            throw new IllegalArgumentException();
        }
        AlterarCusto clone = RWObj.clone(this, AlterarCusto.class);
        clone.partida = partida;
        return clone;
    }

    /**
     * Verifica e altera o custo do poder heroico
     *
     * @param custo custo atual do poder heroico
     * @param flag excluir o feitiço ou aura responsável pela alteração do custo
     * @return custo alterado por algum feitico ou aura
     */
    public int processarPoderHeroico(int custo, boolean flag) {
        if (partida != null) {
            for (int i = 0; i < LIST_TYPE.size(); i++) {
                if (LIST_PLAYER.get(i) == Utils.reflection(Integer.class, partida, "getPlayer").intValue()
                        && LIST_TYPE.get(i).equals(Values.PODER_HEROICO)) {
                    custo += LIST_VALUE.get(i);
                    if (flag) {
                        remove(i--);
                    }
                }
            }
        }
        return custo < 0 ? 0 : custo;
    }

    /**
     * Verifica e altera o custo do card
     *
     * @param card Card a ser verificado
     * @param flag excluir o feitiço ou aura responsável pela alteração do custo
     * @return custo alterado por algum feitico ou aura
     */
    public int processarCard(Object card, boolean flag) {
        if (partida != null) {
            FeiticosAtivos fa = Utils.reflection(FeiticosAtivos.class, partida, "getFeiticosAtivos");
            Integer custo = Utils.reflection(Integer.class, card, "getCusto");
            for (int i = 0; i < LIST_TYPE.size(); i++) {
                String type = Utils.reflection(String.class, card, "getType");
                String race = Utils.reflection(String.class, card, "getRace");
                if (LIST_PLAYER.get(i) == Utils.reflection(Integer.class, partida, "getPlayer").intValue()
                        && ((LIST_TYPE.get(i).equals(Values.GERAL) || (type != null && LIST_TYPE.get(i).equals(type)))
                        && (LIST_RACE.get(i).equals(Values.GERAL) || (race != null && LIST_RACE.get(i).equals(race))))) {
                    custo += LIST_VALUE.get(i);
                    if (flag) {
                        remove(i--);
                    }
                }
            }
            //Evocadora Diminuta (O primeiro lacaio que você joga a cada turno custa 1 a menos)
            if (Utils.reflection(Boolean.class, card, "isLacaio")) {
                custo -= ex1_076();
            }
            //Preparação (O próximo feitiço que você lançar neste turno custará 3 a menos)
            if (fa.isEX1_145() && Utils.reflection(Boolean.class, card, "isFeitico")) {
                custo -= 3;
                if (flag) {
                    fa.desativarEX1_145();
                }
            }
            //Maga do Kirin Tor (Grito de Guerra: O próximo Segredo que você lançar neste turno custará 0)
            if (fa.isEX1_612() && Utils.reflection(Boolean.class, card, "isSegredo")) {
                custo = 0;
                if (flag) {
                    fa.desativarEX1_612();
                }
            }

            return custo < 0 ? 0 : custo;
        } else {
            return Utils.reflection(Integer.class, card, "getCusto");
        }
    }

    /**
     * Adiciona uma aura estática para alterar o custo de um card
     *
     * @param player jogador dono da aura
     * @param type tipo do card (feitiço, lacaio, arma)
     * @param race raça do lacaio (caso seja lacaio)
     * @param value valor a ser alterado (pode ser negativo ou positivo)
     */
    public void add(int player, String type, String race, int value) {
        LIST_PLAYER.add(player);
        LIST_TYPE.add(type);
        LIST_RACE.add(race);
        LIST_VALUE.add(value);
        Pacote pack = new Pacote(Param.ALTERAR_CUSTO_ADD);
        pack.set(PLAYER, player);
        pack.set(TYPE, type);
        pack.set(RACE, race);
        pack.set(VALUE, value);
        GameCliente.send(pack);
    }

    /**
     * Remover uma aura existente
     *
     * @param index índice da aura na lista de auras adicionadas
     */
    public void remove(int index) {
        LIST_PLAYER.remove((int) index);
        LIST_TYPE.remove(index);
        LIST_RACE.remove(index);
        LIST_VALUE.remove((int) index);
        GameCliente.send(Param.ALTERAR_CUSTO_DEL, index);
    }

    /**
     * Evocadora Diminuta (O primeiro lacaio que você joga a cada turno custa 1
     * a menos)
     *
     * @return custo a ser subtraído
     */
    private int ex1_076() {
        Object hero = Utils.reflection(partida, "getHero");
        if (Utils.reflection(Long.class, hero, "getLacaiosJogadosNaRodada") == 0) {
            List l = Utils.reflection(List.class, hero, "getMesa");
            return (int) l.stream().filter((mesa)
                    -> (Utils.reflection(String.class, mesa, "getId").equals("EX1_076")
                    && !Utils.reflection(Boolean.class, mesa, "isSilenciado"))).count();
        }
        return 0;
    }
}