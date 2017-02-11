package  com.limagiran.hearthstone.partida.view;

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
public class DragAndDrop extends JPanel {

    private final Point pressed;
    private final Point released;

    public DragAndDrop(Point pressed, Point released) {
        super(new AbsoluteLayout());
        this.pressed = pressed;
        this.released = released;
        setVisible(false);
        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        SwingUtilities.invokeLater(() -> {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) getGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(5, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND));
            g2.setColor(Color.red);
            g2.drawLine(pressed.x, pressed.y, released.x, released.y);
            g2.setStroke(new BasicStroke(5, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND));
            g2.drawOval(released.x - 25, released.y - 25, 50, 50);
            g2.fillOval(released.x - 10, released.y - 10, 20, 20);
            g2.dispose();
        });
    }

}