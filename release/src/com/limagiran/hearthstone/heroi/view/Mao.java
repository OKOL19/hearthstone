package  com.limagiran.hearthstone.heroi.view;

import com.limagiran.hearthstone.heroi.control.Heroi;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Vinicius
 */
public class Mao extends JPanel {

    private final Heroi heroi;

    public Mao(Heroi heroi) {
        super(new AbsoluteLayout());
        this.heroi = heroi;
        init();
    }

    private void init() {
        setName("maoNull");
        setOpaque(false);
    }

    public void atualizar() {
        removeAll();
        for (int index = heroi.getMao().size() - 1; index >= 0; index--) {
            int total = 123 * heroi.getMao().size();
            int diferenca;
            if (total > (500 - 123)) {
                diferenca = (total - (500 - 123)) / heroi.getMao().size();
            } else {
                diferenca = 0;
            }
            int x = 123 - diferenca;
            add(heroi.getMao().get(index).viewCardMao, new AbsoluteConstraints(x * index, 0));
        }
        revalidate();
    }

    @Override
    public String toString() {
        return getName();
    }
}