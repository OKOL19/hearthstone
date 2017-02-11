package  com.limagiran.hearthstone.heroi.view;

import com.limagiran.hearthstone.heroi.control.Heroi;
import com.limagiran.hearthstone.util.AbsolutesConstraints;
import com.limagiran.hearthstone.util.Images;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Vinicius
 */
public class PanelHeroi extends JPanel {

    private Animacao animacao;
    private Congelado congelado;
    private JLabel heroi;
    private final ImageIcon imagemHeroi;
    private final Heroi hero;

    public PanelHeroi(Heroi hero, ImageIcon image) {
        super(new AbsoluteLayout());
        this.hero = hero;
        imagemHeroi = image;
        init();
    }

    private void init() {
        setOpaque(false);
        congelado = new Congelado();
        heroi = new JLabel(imagemHeroi, JLabel.CENTER);
        animacao = new Animacao(hero);
        add(animacao, new AbsoluteConstraints(0, 0, imagemHeroi.getIconWidth(), imagemHeroi.getIconHeight()));
        add(congelado, AbsolutesConstraints.ZERO);
        add(heroi, AbsolutesConstraints.ZERO);
    }

    public void atualizar() {
        congelado.repaint();
        heroi.repaint();
    }

    public void setFreeze(boolean flag) {
        congelado.setVisible(flag);
    }

    public Animacao getAnimacao() {
        return animacao;
    }

    @Override
    public String toString() {
        return hero.getToString();
    }
}

class Congelado extends JLabel {

    public Congelado() {
        super(Images.HEROI_CONGELADO, JLabel.CENTER);
        init();
    }

    private void init() {
        setOpaque(false);
        setVisible(false);
    }
}