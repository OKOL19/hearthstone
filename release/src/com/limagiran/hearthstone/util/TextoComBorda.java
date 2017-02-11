package  com.limagiran.hearthstone.util;

import java.awt.BasicStroke;
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
public final class TextoComBorda extends JLabel {

    private final String text;
    private final Font font;
    private final Color color;
    private final Color border = Color.BLACK;
    private final String toString;

    private TextoComBorda(String value, int size, Color color, String toString) {
        this.text = value;
        this.color = color;
        this.font = Fontes.getBelwe(size);
        this.toString = toString;
        init();
    }

    @Override
    public String toString() {
        return toString;
    }

    public static TextoComBorda create(String text, int size, Color foreground, String toString) {
        return new TextoComBorda(text, size, foreground, toString);
    }

    public void init() {
        setSize(getFontMetrics(font).stringWidth(text) + 5, font.getSize() + 5);
        setPreferredSize(getSize());
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(font);
        FontRenderContext frc = g2.getFontRenderContext();
        TextLayout textLayout = new TextLayout(text, font, frc);
        g2.setPaint(color);
        g2.setStroke(new BasicStroke(((float) font.getSize()) * (font.getStyle() == Font.BOLD ? 0.05f : 0.025f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        AffineTransform at = AffineTransform.getTranslateInstance(5, font.getSize());
        Shape outline = textLayout.getOutline(at);
        g2.fill(outline);
        g2.setPaint(border);
        g2.draw(outline);
        g2.dispose();
        setSize(getFontMetrics(font).stringWidth(text) + 10, font.getSize() + 5);
        setPreferredSize(getSize());
    }
}