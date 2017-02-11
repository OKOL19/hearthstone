package  com.limagiran.hearthstone.heroi.view;

import static com.limagiran.hearthstone.card.view.JLabelAtributos.FONT;
import com.limagiran.hearthstone.util.Images;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius
 */
public class Segredo extends JPanel {

    private final Heroi heroi;
    private Quantidade quantidade;
    private JLabel background;

    public Segredo(Heroi heroi) {
        super(new AbsoluteLayout());
        this.heroi = heroi;
        init();
    }

    @Override
    public String toString() {
        return Values.TO_STRING_SEGREDO + heroi.getHeroi();
    }

    private void init() {
        setOpaque(false);
        quantidade = new Quantidade(heroi);
        background = new JLabel(Images.HEROI_SEGREDO) {
            @Override
            public String toString() {
                return Values.TO_STRING_SEGREDO + heroi.getHeroi();
            }
        };
        add(quantidade, new AbsoluteConstraints(0, -7, 60, 60));
        add(background, new AbsoluteConstraints(0, 0, 60, 60));
        setVisible(false);

    }

    public void atualizar() {
        quantidade.setText(Integer.toString(heroi.getSegredo().size()));
        setVisible(heroi.getSegredo().size() > 0);
    }
}

class Quantidade extends JLabel {

    private final Heroi heroi;
    public Quantidade(Heroi heroi) {
        super("", JLabel.CENTER);
        this.heroi = heroi;
        init();
    }

    private void init() {
        setFont(FONT);
        setForeground(Color.WHITE);
    }

    @Override
    public String toString() {
        return Values.TO_STRING_SEGREDO + heroi.getHeroi();
    }

}