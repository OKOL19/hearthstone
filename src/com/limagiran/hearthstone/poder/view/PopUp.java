package  com.limagiran.hearthstone.poder.view;

import com.limagiran.hearthstone.util.AbsolutesConstraints;
import java.awt.Color;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Vinicius
 */
public final class PopUp extends JFrame {

    private transient final ImageIcon card = new ImageIcon();

    public PopUp() {
        init();
    }

    public void init() {
        setLayout(new AbsoluteLayout());
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        add(new JLabel(card), AbsolutesConstraints.ZERO);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private void location(Point p) {
        if (p == null) {
            setLocationRelativeTo(null);
        } else {
            setLocation(p);
        }
    }

    public void show(ImageIcon image, Point p) {
        card.setImage(image.getImage());
        setSize(image.getIconWidth(), image.getIconHeight());
        location(p);
        setVisible(true);
    }
}