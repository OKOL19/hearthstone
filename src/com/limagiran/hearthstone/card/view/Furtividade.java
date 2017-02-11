package  com.limagiran.hearthstone.card.view;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Img;
import com.limagiran.hearthstone.util.DimensionValues;
import com.limagiran.hearthstone.util.Images;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.JLabel;

/**
 *
 * @author Vinicius
 */
public class Furtividade extends JLabel implements Serializable {

    private final Card card;

    public Furtividade(Card card) {
        this.card = card;
        init();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0, 0, 0, 120));
        g.fillOval(16, 10, 105, 140);
        g.drawImage(Img.getCirculo(Images.CARD_NEVOA, 0, 0, 110, 145).getImage(),
                15, 7, null);
    }

    @Override
    public String toString() {
        return card.getToString();
    }

    private void init() {
        setPreferredSize(DimensionValues.MESA);
    }
}