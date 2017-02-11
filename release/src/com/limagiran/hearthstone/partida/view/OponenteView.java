package  com.limagiran.hearthstone.partida.view;

import com.limagiran.hearthstone.heroi.control.Heroi;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import com.limagiran.hearthstone.util.AbsolutesConstraints;
import com.limagiran.hearthstone.util.Images;
import com.limagiran.hearthstone.util.Param;

/**
 *
 * @author Vinicius
 */
public class OponenteView extends JPanel {

    private final Heroi oponente;

    public OponenteView(Heroi hero) {
        super(new AbsoluteLayout());
        this.oponente = hero;
        init();
    }

    private void init() {
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);        
        JLabel modelo = new JLabel() {
            @Override
            public String toString() {
                return oponente.getToString();
            }
        };
        modelo.setBackground(new Color(0, 0, 0, 0));        
        modelo.setHorizontalAlignment(SwingConstants.CENTER);
        modelo.setIcon(Images.ARENA_OPONENTE);        
        add(oponente.getPanelHeroi(), AbsolutesConstraints.OPONENTE_PANEL_IMAGEM, 0);
        add(modelo, AbsolutesConstraints.ZERO, 0);
        add(oponente.getPanelAtaque(), AbsolutesConstraints.OPONENTE_PANEL_ATAQUE, 0);
        add(oponente.getPanelEscudo(), AbsolutesConstraints.OPONENTE_PANEL_SHIELD, 0);
        add(oponente.getPanelVida(), AbsolutesConstraints.OPONENTE_PANEL_VIDA, 0);
        add(oponente.getPanelPoder(), AbsolutesConstraints.OPONENTE_PANEL_PODER, 0);
        add(oponente.getPanelArma(), AbsolutesConstraints.OPONENTE_PANEL_ARMA, 0);
        add(oponente.getPanelSegredo(), AbsolutesConstraints.OPONENTE_PANEL_SEGREDO, 0);
    }

    @Override
    public String toString() {
        return "null";
    }
}