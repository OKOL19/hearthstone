package com.limagiran.hearthstoneia.poder.view;

import com.limagiran.hearthstoneia.poder.control.PoderHeroico;
import java.io.Serializable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Vinicius
 */
public class View implements Serializable {

    private final JPanel panel = new JPanel(new AbsoluteLayout());
    private PoderHeroico poder;
    private JLabel custo;

    public void loadView(PoderHeroico poder) {
        this.poder = poder;
        init();
    }

    private void init() {
        panel.setOpaque(false);
        custo = new JLabel();
        panel.add(custo, new AbsoluteConstraints(42, 61, 36, 40));
        setAtivado(false);
    }

    public JPanel getPanelPoderHeroicoView() {
        return panel;
    }

    public void setAtivado(boolean flag) {
        custo.setVisible(flag);
    }

    public boolean isAtivado() {
        return custo.isVisible();
    }

    public PoderHeroico getPoder() {
        return poder;
    }

    @Override
    public String toString() {
        return "null";
    }
}