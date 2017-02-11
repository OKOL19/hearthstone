package  com.limagiran.hearthstone.colecao.util;

import com.limagiran.hearthstone.deck.Deck;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Vinicius
 */
public class DeckListCellRenderer implements ListCellRenderer/*, Serializable */ {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Deck deck = (Deck) value;
        deck.setSelected(isSelected);
        return deck;
    }
}