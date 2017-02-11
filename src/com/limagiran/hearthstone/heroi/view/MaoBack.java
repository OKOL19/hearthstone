package  com.limagiran.hearthstone.heroi.view;

import com.limagiran.hearthstone.util.DimensionValues;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingConstants;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Vinicius
 */
public class MaoBack extends JPanel {

    private final Heroi heroi;
    transient private Popup tooltip;

    public MaoBack(Heroi heroi) {
        super(new AbsoluteLayout());
        this.heroi = heroi;
        init();
    }

    private void init() {
        addMouseListener(mouseEvent());
        setOpaque(false);
    }

    private MouseAdapter mouseEvent() {
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
                int qtd = heroi.getMao().size();
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

    public void atualizar() {
        int qtd = heroi.getMao().size();
        int total = 123 * qtd;
        int diferenca;
        if (total > (500 - 123)) {
            diferenca = (total - (500 - 123)) / qtd;
        } else {
            diferenca = 0;
        }
        int x = 123 - diferenca;
        removeAll();
        for (int j = 0; j < qtd; j++) {
            JLabel card = new JLabel(heroi.getCardback().getImageIcon(DimensionValues.CARD_SMALL));
            card.setPreferredSize(new Dimension(card.getPreferredSize().width - 9, card.getPreferredSize().height));
            card.setHorizontalAlignment(SwingConstants.CENTER);
            add(card, new AbsoluteConstraints(x * j, 0));
        }
        revalidate();
    }
}