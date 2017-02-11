package  com.limagiran.hearthstone.heroi.view;

import com.limagiran.hearthstone.util.Images;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Vinicius
 */
public class Mana extends JPanel {

    private final Heroi heroi;
    private Qtdd quantidade;
    private final ImageIcon mana_on = Images.MANA_ON;
    private final ImageIcon mana_off = Images.MANA_OFF;

    public Mana(Heroi heroi) {
        this.heroi = heroi;
        init();
    }

    private void init() {
        setOpaque(false);
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 1, 0);
        layout.setAlignOnBaseline(true);
        setLayout(layout);
        quantidade = new Qtdd();
    }

    public void atualizar() {
        int total = 1;
        removeAll();
        while (total <= heroi.getMana()) {
            add(new JLabel(total <= heroi.getManaDisponivel() ? mana_on : mana_off));
            total++;
        }
    }

    public JLabel getQuantidade() {
        return quantidade;
    }
}

final class Qtdd extends JLabel {

    public Qtdd() {
        super("", SwingConstants.CENTER);
        init();
    }

    public void init() {
        setFont(new Font("Tahoma", Font.BOLD, 18));
        setForeground(Color.WHITE);
    }
}