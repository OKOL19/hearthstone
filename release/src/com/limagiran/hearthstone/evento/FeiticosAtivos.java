package com.limagiran.hearthstone.evento;

import java.io.Serializable;

/**
 *
 * @author Vinicius
 */
public class FeiticosAtivos implements Serializable {

    private boolean AT_061 = false;
    private long AT_061Long = 0;
    private boolean EX1_145 = false;
    private boolean EX1_612 = false;
    private boolean NEW1_036 = false;

    public void clear() {
        AT_061 = false;
        EX1_145 = false;
        EX1_612 = false;
        NEW1_036 = false;
        AT_061Long = 0;
    }

    /**
     * Largar o Dedo (Cada vez que você lançar um feitiço neste turno, adicione
     * um card de Caçador aleatório à sua mão)
     *
     * @param id
     */
    public void ativarAT_061(long id) {
        AT_061 = true;
        AT_061Long = id;
    }

    /**
     * Largar o Dedo (Cada vez que você lançar um feitiço neste turno, adicione
     * um card de Caçador aleatório à sua mão)
     *
     */
    public void desativarAT_061() {
        AT_061 = false;
        AT_061Long = 0;
    }

    /**
     * Largar o Dedo (Cada vez que você lançar um feitiço neste turno, adicione
     * um card de Caçador aleatório à sua mão)
     *
     * @return id_long do feitiço responsável pela aura
     */
    public long getAT_061Long() {
        return AT_061Long;
    }

    /**
     * Largar o Dedo (Cada vez que você lançar um feitiço neste turno, adicione
     * um card de Caçador aleatório à sua mão)
     *
     * @return true para feitiço ativo. false para feitiço inativo
     */
    public boolean isAT_061() {
        return AT_061;
    }

    /**
     * Preparação (O próximo feitiço que você lançar neste turno custará 3 a
     * menos)
     *
     */
    public void ativarEX1_145() {
        EX1_145 = true;
    }

    /**
     * Preparação (O próximo feitiço que você lançar neste turno custará 3 a
     * menos)
     *
     */
    public void desativarEX1_145() {
        EX1_145 = false;
    }

    /**
     * Preparação (O próximo feitiço que você lançar neste turno custará 3 a
     * menos)
     *
     * @return true para feitiço ativo. false para feitiço inativo.
     */
    public boolean isEX1_145() {
        return EX1_145;
    }

    /**
     * Maga do Kirin Tor (Grito de Guerra: O próximo Segredo que você lançar
     * neste turno custará 0)
     */
    public void ativarEX1_612() {
        EX1_612 = true;
    }

    /**
     * Maga do Kirin Tor (Grito de Guerra: O próximo Segredo que você lançar
     * neste turno custará 0)
     *
     */
    public void desativarEX1_612() {
        EX1_612 = false;
    }

    /**
     * Maga do Kirin Tor (Grito de Guerra: O próximo Segredo que você lançar
     * neste turno custará 0)
     *
     * @return true para feitiço ativo. false para feitiço inativo.
     */
    public boolean isEX1_612() {
        return EX1_612;
    }

    /**
     * Brado de Comando (A vida de seus lacaios não pode ser reduzida a menos de
     * 1 neste turno. Compre um card)
     */
    public void ativarNEW1_036() {
        NEW1_036 = true;
    }

    /**
     * Brado de Comando (A vida de seus lacaios não pode ser reduzida a menos de
     * 1 neste turno. Compre um card)
     */
    public void desativarNEW1_036() {
        NEW1_036 = false;
    }

    /**
     * Brado de Comando (A vida de seus lacaios não pode ser reduzida a menos de
     * 1 neste turno. Compre um card)
     *
     * @return true para feitiço ativo. false para feitiço inativo.
     */
    public boolean isNEW1_036() {
        return NEW1_036;
    }
}