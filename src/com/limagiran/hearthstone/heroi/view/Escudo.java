package  com.limagiran.hearthstone.heroi.view;

import com.limagiran.hearthstone.util.AbsolutesConstraints;
import com.limagiran.hearthstone.util.Images;
import com.limagiran.hearthstone.heroi.control.Heroi;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Vinicius
 */
public class Escudo extends JPanel {

    private final Heroi heroi;
    
    private JLabelAtributos shield;
    private JLabel background;

    public Escudo(Heroi heroi) {
        super(new AbsoluteLayout());
        this.heroi = heroi;
        init();
    }
    
    private void init(){
        setOpaque(false);
        shield = new JLabelAtributos(heroi, JLabelAtributos.SHIELD);
        background = new JLabel(Images.HEROI_ESCUDO, SwingConstants.CENTER);
        add(shield, AbsolutesConstraints.HEROI_LABEL_ESCUDO);
        add(background, new AbsoluteConstraints(0, 0, 70, 70));
        setVisible(heroi.getShield() > 0);
        
    }
    
    public void atualizar() {
        shield.repaint();
    }
}