package  com.limagiran.hearthstone.heroi.view;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius
 */
public class Mesa extends JPanel {

    private final Heroi hero;

    public Mesa(Heroi hero) {
        super(new FlowLayout(FlowLayout.CENTER, 20, 0));
        this.hero = hero;
        init();
    }

    private void init() {
        setOpaque(false);
    }

    public void atualizar() {
        removeAll();
        for (Card c : hero.getMesa()) {
            add(c.viewCardMesa);
        }
        revalidate();
    }

    @Override
    public String toString() {
        return Values.TO_STRING_MESA + hero.getHeroi();
    }

}