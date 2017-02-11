package  com.limagiran.hearthstone.poder.view;

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
import javax.swing.JLabel;
import com.limagiran.hearthstone.poder.control.PoderHeroico;

/**
 *
 * @author Vinicius
 */
public class Custo extends JLabel {

    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color RED = new Color(242, 0, 0);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color BLACK = new Color(0, 0, 0);
    public final Font FONT;
    public final PoderHeroico PODER_HEROICO;
    public int value;
    public Color textColor = new Color(255, 255, 255);

    public Custo(PoderHeroico poder) {
        PODER_HEROICO = poder;
        FONT = Fontes.getBelwe(26);
    }

    @Override
    protected void paintComponent(Graphics g) {
        atualizar();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(FONT);
        FontRenderContext frc = g2.getFontRenderContext();
        TextLayout textLayout = new TextLayout(Integer.toString(value), FONT, frc);
        g2.setPaint(textColor);
        AffineTransform at = AffineTransform.getTranslateInstance(20, 30);
        Shape outline = textLayout.getOutline(at);
        g2.fill(outline);
        g2.setPaint(BLACK);
        g2.draw(outline);
    }

    private void atualizar() {
        textColor = getColor(PODER_HEROICO.getCusto(false), 2, 2);
        value = PODER_HEROICO.getCusto(false);
    }

    private Color getColor(int atual, int maximo, int padrao) {
        if (atual == maximo && atual > padrao) {
            return GREEN;
        } else if (atual == maximo && atual == padrao) {
            return WHITE;
        } else {
            return RED;
        }
    }
}