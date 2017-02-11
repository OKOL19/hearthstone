package com.limagiran.hearthstoneia.heroi.view;

import com.limagiran.hearthstoneia.heroi.control.Heroi;
import java.io.Serializable;
import javax.swing.JLabel;

/**
 *
 * @author Vinicius
 */
public class PanelHeroi implements Serializable {

    private Animacao animacao;
    private JLabel congelado;
    private JLabel heroi;
    private final Heroi hero;

    public PanelHeroi(Heroi hero) {
        this.hero = hero;
        init();
    }

    private void init() {
        congelado = new JLabel();
        heroi = new JLabel("", JLabel.CENTER);
        animacao = new Animacao(hero);        
    }

    public void atualizar() {
        congelado.repaint();
        heroi.repaint();
    }

    public void setFreeze(boolean flag) {
        congelado.setVisible(flag);
    }

    public Animacao getAnimacao() {
        return animacao;
    }

    @Override
    public String toString() {
        return hero.getToString();
    }
}