package com.limagiran.hearthstone.card.view;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.card.util.Util;
import com.limagiran.hearthstone.util.AbsolutesConstraints;
import com.limagiran.hearthstone.util.DimensionValues;
import com.limagiran.hearthstone.util.Images;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.*;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius
 */
public class Mao extends JPanel {

    private final Card card;
    private JLabelAtributos ataque;
    private JLabelAtributos vida;
    private JLabelAtributos durabilidade;
    private JLabelAtributos custo;

    public Mao(Card card) {
        super(new AbsoluteLayout());
        this.card = card;
        init();
    }

    private void init() {
        custo = new JLabelAtributos(card, JLabelAtributos.CUSTO);
        loadView();
        setPreferredSize(DimensionValues.CARD_SMALL);
        setSize(DimensionValues.CARD_SMALL);
        setPreferredSize(new Dimension(getPreferredSize().width - 9, getPreferredSize().height));
        setSize(new Dimension(getPreferredSize().width - 9, getPreferredSize().height));
        setBackground(new Color(0, 0, 0, 0));
        add(Util.JLabelMaoDefault(card, card.getImagemSmall()), new AbsoluteConstraints(-5, 0));
    }

    public void atualizar() {
        if (custo != null) {
            custo.repaint();
        }
        if (ataque != null) {
            ataque.repaint();
        }
        if (vida != null) {
            vida.repaint();
        }
        if (durabilidade != null) {
            durabilidade.repaint();
        }
    }

    public void loadView() {
        switch (card.getType()) {
            case Values.LACAIO:
                loadMinion();
                break;
            case Values.ARMA:
                loadWeapon();
                break;
            case Values.FEITICO:
                loadSpell();
                break;
        }
    }

    private void loadWeapon() {
        ataque = new JLabelAtributos(card, JLabelAtributos.ATAQUE);
        durabilidade = new JLabelAtributos(card, JLabelAtributos.DURABILIDADE);
        add(custo, AbsolutesConstraints.MAO_ARMA_LABEL_CUSTO);
        add(ataque, AbsolutesConstraints.MAO_ARMA_LABEL_ATAQUE);
        add(durabilidade, AbsolutesConstraints.MAO_ARMA_LABEL_DURABILIDADE);
        add(Util.JLabelMaoDefault(card, Images.CARD_CUSTO_ATAQUE_DURABILIDADE_P), new AbsoluteConstraints(-5, 0));
    }

    private void loadMinion() {
        ataque = new JLabelAtributos(card, JLabelAtributos.ATAQUE);
        vida = new JLabelAtributos(card, JLabelAtributos.VIDA);
        add(custo, AbsolutesConstraints.MAO_LACAIO_LABEL_CUSTO);
        add(ataque, AbsolutesConstraints.MAO_LACAIO_LABEL_ATAQUE);
        add(vida, AbsolutesConstraints.MAO_LACAIO_LABEL_VIDA);
        add(Util.JLabelMaoDefault(card, Images.CARD_CUSTO_ATAQUE_VIDA_P),
                new AbsoluteConstraints(-5, 0));
    }

    private void loadSpell() {
        add(custo, AbsolutesConstraints.MAO_ARMA_LABEL_CUSTO);
        add(Util.JLabelMaoDefault(card, Images.CARD_CUSTO_P),
                new AbsoluteConstraints(-5, 0));
    }

    @Override
    public String toString() {
        return Values.TO_STRING_MAO + card.id_long;
    }

}