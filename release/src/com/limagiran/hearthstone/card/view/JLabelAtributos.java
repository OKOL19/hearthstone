package  com.limagiran.hearthstone.card.view;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Fontes;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import javax.swing.JLabel;

/**
 *
 * @author Vinicius Silva
 */
public class JLabelAtributos extends JLabel implements Serializable {

    public static final int CUSTO = 0;
    public static final int ATAQUE = 1;
    public static final int VIDA = 2;
    public static final int DURABILIDADE = 3;
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color RED = new Color(242, 0, 0);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Font FONT = Fontes.getBelwe(32);
    public final Card CARD;
    public int value;
    private final int type;
    public Color textColor = new Color(255, 255, 255);

    public JLabelAtributos(Card card, int type) {
        CARD = card;
        this.type = type;
    }

    @Override
    protected void paintComponent(Graphics g) {
        atualizar();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(FONT);
        FontRenderContext frc = g2.getFontRenderContext();
        TextLayout textLayout = new TextLayout(Integer.toString(value), FONT, frc);
        g2.setPaint(textColor);
        Shape outline = textLayout.getOutline(AffineTransform.getTranslateInstance(value < 10 ? 20 : 10, 30));
        g2.fill(outline);
        g2.setPaint(BLACK);
        g2.draw(outline);
    }

    private void atualizar() {
        switch (type) {
            case CUSTO:
                Color color = getColor(CARD.getCustoAlterado(false), CARD.getCost());
                textColor = color.equals(RED) ? GREEN : color.equals(GREEN) ? RED : WHITE;
                value = CARD.getCustoAlterado(false);
                break;
            case ATAQUE:
                textColor = getColor(CARD.getAtaque(), CARD.getAttack());
                value = CARD.getAtaque();
                break;
            case VIDA:
                textColor = getColor(CARD.getVida(), CARD.getVidaMaxima(), CARD.getVidaOriginal());
                value = CARD.getVida();
                break;
            case DURABILIDADE:
                textColor = getColor(CARD.getDurability(), CARD.DURABILITY_DEFAULT);
                value = CARD.getDurability();
                break;
        }
    }

    private Color getColor(int atual, int maximo, int padrao) {
        return atual != maximo ? RED : atual > padrao ? GREEN : WHITE;
    }

    private Color getColor(int atual, int padrao) {
        return atual < padrao ? RED : atual == padrao ? WHITE : GREEN;
    }
}