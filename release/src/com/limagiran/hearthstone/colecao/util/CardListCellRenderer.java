package  com.limagiran.hearthstone.colecao.util;

import com.limagiran.hearthstone.deck.CardView;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Vinicius
 */
public class CardListCellRenderer implements ListCellRenderer/*, Serializable */ {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        return (CardView) value;
    }
}