package com.limagiran.hearthstoneia.card.view;

import com.limagiran.hearthstoneia.card.control.Card;
import java.io.Serializable;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius
 */
public class Mesa implements Serializable {

    private final Card card;

    private Animacao animacao;

    private boolean investida;

    public Mesa(Card card) {
        this.card = card;
        init();
    }

    private void init() {
        if (card.getType().equals(Values.LACAIO)) {
            initMinion();
        }
    }

    private void initMinion() {
        animacao = new Animacao(card);
        initMecanicas();
    }

    private void initMecanicas() {
        if (card.isLacaio()) {
            investida = card.getMechanics().contains(Values.INVESTIDA);
        }
    }

    public Animacao getAnimacao() {
        return animacao;
    }

    public void setInvestida(boolean flag) {
        investida = flag;
    }

    public boolean isInvestida() {
        return investida;
    }

    @Override
    public String toString() {
        return card.getToString();
    }
}