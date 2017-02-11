package  com.limagiran.hearthstone.card.view;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.DimensionValues;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.JLabel;

/**
 *
 * @author Vinicius
 */
public class EscudoDivino extends JLabel implements Serializable {

    private final Card card;

    public EscudoDivino(Card card) {
        this.card = card;
        init();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(255, 201, 14, 100));
        g.fillOval(8, 2, 128, 163);
    }

    @Override
    public String toString() {
        return card.getToString();
    }

    private void init() {
        setPreferredSize(DimensionValues.MESA);
    }
}