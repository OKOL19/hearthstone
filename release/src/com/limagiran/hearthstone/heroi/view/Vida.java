package  com.limagiran.hearthstone.heroi.view;

import com.limagiran.hearthstone.util.AbsolutesConstraints;
import com.limagiran.hearthstone.heroi.control.Heroi;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Vinicius
 */
public class Vida extends JPanel {

    private JLabelAtributos vida;
    private final Heroi heroi;

    public Vida(Heroi heroi) {
        super(new AbsoluteLayout());
        this.heroi = heroi;
        init();
    }

    private void init() {
        setOpaque(false);
        vida = new JLabelAtributos(heroi, JLabelAtributos.VIDA);
        add(vida, AbsolutesConstraints.HEROI_LABEL_VIDA);
    }

    public void atualizar() {
        vida.repaint();
    }
}