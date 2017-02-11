package com.limagiran.hearthstoneia.heroi.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Vinicius
 */
public class DanoMagico extends JPanel {

    private JLabel dano;

    public DanoMagico() {
        super(new AbsoluteLayout());
        init();
    }

    private void init() {
        setOpaque(false);
        dano = new JLabel("0", SwingConstants.CENTER);
        dano.setToolTipText("Dano Mágico");
        add(dano, new AbsoluteConstraints(0, 0, 40, 40));
    }

    public int getDano() {
        try {
            return Integer.parseInt(dano.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public void setDano(int value) {
        dano.setText(Integer.toString(value));
    }
}