package  com.limagiran.hearthstone.heroi.view;

import com.limagiran.hearthstone.util.AbsolutesConstraints;
import com.limagiran.hearthstone.util.Images;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Vinicius
 */
public class Ataque extends JPanel {

    private JLabelAtributos ataque;
    private JLabel background;
    private JLabel investida;
    private final Heroi heroi;

    public Ataque(Heroi heroi) {
        super(new AbsoluteLayout());
        this.heroi = heroi;
        init();
    }

    private void init() {
        setOpaque(false);
        setVisible(false);
        ataque = new JLabelAtributos(heroi, JLabelAtributos.ATAQUE);
        background = new JLabel(Images.HEROI_ATAQUE, SwingConstants.CENTER);
        Images.HEROI_INVESTIDA.setImageObserver(investida);
        investida = new JLabel(Images.HEROI_INVESTIDA) {
            @Override
            public boolean imageUpdate(Image img, int flags, int x, int y, int w, int h) {
                return true;
            }
        };
        add(ataque, AbsolutesConstraints.HEROI_LABEL_ATAQUE);
        add(background, new AbsoluteConstraints(0, 8, 70, 70));
        add(investida, AbsolutesConstraints.ZERO);
    }

    public void atualizar() {
        ataque.repaint();
        investida.repaint();
    }

    public void setInvestida(boolean flag) {
        investida.setVisible(flag);
    }

    public boolean isInvestida() {
        return investida.isVisible();
    }
}