package com.limagiran.hearthstoneia.card.view;

import com.google.gson.annotations.Expose;
import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstone.util.Param;
import java.io.Serializable;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class View implements Serializable {

    private Card card;

    @Expose(serialize = false, deserialize = false)
    public Mesa viewCardMesa;

    /**
     * Configura os componentes do View do card
     *
     * @param card card que herdou View
     */
    public final void init(Card card) {
        this.card = card;
        viewCardMesa = new Mesa(this.card);
    }

    /**
     * Exibir o view responsável pela mecânica adicionada
     *
     * @param mechanic mecânica adicionada
     */
    public void addMecanicaLabel(String mechanic) {
        if (!card.isSilenciado()) {
            if (mechanic.equals(Values.INVESTIDA)) {
                card.setAtaquesRealizados(card.getAtaquesRealizados());
            }
        }
    }

    /**
     * Ocultar o view responsável pela mecânica removida
     *
     * @param mechanic mecânica removida
     */
    protected void delMecanicaLabel(String mechanic) {
        if (mechanic.equals(Values.INVESTIDA) && !card.getMechanics().contains(Values.INVESTIDA)) {
            card.setAtaquesRealizados(card.getAtaquesRealizados());
        }
        if (mechanic.equals(Values.ENFURECER) && !card.getMechanics().contains(Values.ENFURECER)) {
            //enfurecer.setVisible(false);
        }
    }

    /**
     * Exibir/Ocultar a view de fúria dos ventos
     *
     * @param flag {@code true} ou {@code false}
     */
    public void setFuriaDosVentosVisible(boolean flag) {
        if (flag || !card.getMechanics().contains(Values.FURIA_DOS_VENTOS)) {
            card.enviar(Param.CARD_SET_FURIA_DOS_VENTOS_VISIBLE, flag);
        }
    }

    /**
     * Exibir/Ocultar a view da investida
     *
     * @param flag {@code true} ou {@code false}
     */
    public void setInvestidaVisible(boolean flag) {
        viewCardMesa.setInvestida(flag);
    }
}