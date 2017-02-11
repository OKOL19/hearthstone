package  com.limagiran.hearthstone.heroi.view;

import com.limagiran.hearthstone.util.Fontes;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import javax.swing.JLabel;

/**
 *
 * @author Vinicius Silva
 */
public class JLabelAtributos extends JLabel {

    public static final int SHIELD = 0;
    public static final int ATAQUE = 1;
    public static final int VIDA = 2;
    public static final int PODER = 3;
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color RED = new Color(242, 0, 0);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color BLACK = new Color(0, 0, 0);
    public final Font FONT;
    public final Heroi HEROI;
    public int value;
    private final int type;
    public Color textColor = new Color(255, 255, 255);

    public JLabelAtributos(Heroi heroi, int type) {
        HEROI = heroi;
        this.type = type;
        FONT = Fontes.getBelwe(this.type == SHIELD ? 36 : 26);
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
        Shape outline = textLayout.getOutline(AffineTransform.getTranslateInstance(value < 10 ? 20 : type == SHIELD ? 10 : 14, 30));
        g2.fill(outline);
        g2.setPaint(BLACK);
        g2.draw(outline);
    }

    private void atualizar() {
        switch (type) {
            case SHIELD:
                textColor = WHITE;
                value = HEROI.getShield();
                break;
            case ATAQUE:
                textColor = getColor(HEROI.getAttack(), HEROI.getAttack());
                value = HEROI.getAttack();
                break;
            case VIDA:
                textColor = getColor(HEROI.getHealth(), HEROI.getVidaTotal(), HEROI.getVidaTotal());
                value = HEROI.getHealth();
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