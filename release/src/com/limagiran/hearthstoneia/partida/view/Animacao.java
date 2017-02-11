package com.limagiran.hearthstoneia.partida.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Vinicius Silva
 */
public class Animacao extends JPanel {

    private final Point origemEvento;
    private final Point destinoEvento;

    public Animacao(Point origemEvento, Point destinoEvento) {
        super(new AbsoluteLayout());
        this.origemEvento = origemEvento;
        this.destinoEvento = destinoEvento;
        setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        SwingUtilities.invokeLater(() -> {
            Graphics2D g2 = (Graphics2D) getGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(5, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND));
            g2.setColor(Color.red);
            g2.drawLine(origemEvento.x, origemEvento.y, destinoEvento.x, destinoEvento.y);
            g2.setStroke(new BasicStroke(5, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND));
            g2.drawOval(destinoEvento.x - 25, destinoEvento.y - 25, 50, 50);
            g2.fillOval(destinoEvento.x - 10, destinoEvento.y - 10, 20, 20);
            g2.dispose();
        });
    }

}