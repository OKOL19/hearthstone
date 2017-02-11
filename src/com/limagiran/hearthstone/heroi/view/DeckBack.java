package com.limagiran.hearthstone.heroi.view;

import com.limagiran.hearthstone.util.DimensionValues;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Vinicius
 */
public class DeckBack extends JPanel {

    private final Heroi heroi;
    private ImageIcon cardBack;
    transient private Popup tooltip;

    public DeckBack(Heroi heroi) {
        super(new AbsoluteLayout());
        this.heroi = heroi;
        init();
    }

    private void init() {
        addMouseListener(mouseEvent());
        setOpaque(false);
        cardBack = heroi.getCardback().getImageIcon(DimensionValues.CARD_SMALL, 90);
    }

    public void atualizar() {
        int qtd = heroi.getDeck().size();
        double total = cardBack.getIconHeight() * qtd;
        double diferenca;
        if (total > (150 - cardBack.getIconHeight())) {
            diferenca = (total - (150 - cardBack.getIconHeight())) / qtd;
        } else {
            diferenca = 0;
        }
        double x = cardBack.getIconHeight() - diferenca;
        removeAll();
        for (double index = 0; index < qtd; index++) {
            add(new JLabel(cardBack), new AbsoluteConstraints(0, (int) (x * index)));
        }
        revalidate();
    }

    private MouseListener mouseEvent() {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                super.mouseEntered(evt);
                showToolTip(true);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                super.mouseExited(evt);
                showToolTip(false);
            }
        };
    }

    private void showToolTip(boolean flag) {
        try {
            if (flag) {
                int qtd = heroi.getDeck().size();
                String txt = (qtd == 0 ? "Nenhum card" : qtd == 1
                        ? "1 card" : qtd + " cards");
                Point p = getLocationOnScreen();
                JToolTip tip = createToolTip();
                tip.setTipText(txt);
                PopupFactory popupFactory = PopupFactory.getSharedInstance();
                tooltip = popupFactory.getPopup(this, tip, p.x + 10, p.y + 10);
                tooltip.show();
            } else {
                tooltip.hide();
            }
        } catch (Exception ex) {
            //ignorar
        }
    }
}