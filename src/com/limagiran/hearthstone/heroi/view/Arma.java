package com.limagiran.hearthstone.heroi.view;

import com.limagiran.hearthstone.card.control.Card;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import com.limagiran.hearthstone.util.AbsolutesConstraints;

/**
 *
 * @author Vinicius
 */
public class Arma extends JPanel {

    public Arma() {
        super(new AbsoluteLayout());
        init();
    }

    private void init() {
        setOpaque(false);
    }

    @Override
    public String toString() {
        return "armaNull";
    }

    public static JLabel JLabelArmaDefault(Card arma) {
        return new JLabel() {
            @Override
            public String toString() {
                return arma.getToString();
            }
        };
    }

    /**
     * Edita o painel com a arma equipada
     *
     * @param arma arma equipada
     */
    public void setArma(Card arma) {
        add(Arma.JLabelArmaDefault(arma), AbsolutesConstraints.POP_UP_LABEL_ARMA);
        add(arma.viewCardMesa, new AbsoluteConstraints(0, 0, arma.viewCardMesa.getWidth(), arma.viewCardMesa.getHeight()));
        revalidate();
    }
}