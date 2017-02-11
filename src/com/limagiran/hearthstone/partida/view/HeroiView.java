package  com.limagiran.hearthstone.partida.view;

import com.limagiran.hearthstone.heroi.control.Heroi;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import com.limagiran.hearthstone.util.AbsolutesConstraints;
import com.limagiran.hearthstone.util.Images;

/**
 *
 * @author Vinicius Silva
 */
public class HeroiView extends JPanel {

    private final Heroi hero;

    public HeroiView(Heroi hero) {
        super(new AbsoluteLayout());
        this.hero = hero;
        init();
    }

    @Override
    public String toString() {
        return "null";
    }

    private void init() {
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
        JLabel modelo = new JLabel() {
            @Override
            public String toString() {
                return hero.getToString();
            }
        };
        modelo.setBackground(new Color(0, 0, 0, 0));
        modelo.setHorizontalAlignment(SwingConstants.CENTER);
        modelo.setIcon(Images.ARENA_HEROI);
        add(hero.getPanelHeroi(), AbsolutesConstraints.HEROI_PANEL_IMAGEM, 0);
        add(modelo, AbsolutesConstraints.ZERO, 0);
        add(hero.getPanelAtaque(), AbsolutesConstraints.HEROI_PANEL_ATAQUE, 0);
        add(hero.getPanelEscudo(), AbsolutesConstraints.HEROI_PANEL_SHIELD, 0);
        add(hero.getPanelVida(), AbsolutesConstraints.HEROI_PANEL_VIDA, 0);
        add(hero.getPanelPoder(), AbsolutesConstraints.HEROI_PANEL_PODER, 0);
        add(hero.getPanelArma(), AbsolutesConstraints.HEROI_PANEL_ARMA, 0);
        add(hero.getPanelSegredo(), AbsolutesConstraints.HEROI_PANEL_SEGREDO, 0);
        add(hero.getPanelDanoMagico(), AbsolutesConstraints.HEROI_PANEL_DANO_MAGICO, 0);
    }

}