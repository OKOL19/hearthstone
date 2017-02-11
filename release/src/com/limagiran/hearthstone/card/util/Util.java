package com.limagiran.hearthstone.card.util;

import com.limagiran.hearthstone.card.control.Card;
import java.awt.Image;
import com.limagiran.hearthstone.util.DimensionValues;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius
 */
public class Util {

    /**
     * Cria um JLabel padrão para as mecânicas de um lacaio
     *
     * @param card lacaio dono do JLabel
     * @param image imagem do JLabel
     * @return JLabel configurado
     */
    public static JLabel jLabelDefault(Card card, ImageIcon image) {
        JLabel label = new JLabel(image) {
            @Override
            public String toString() {
                return card.getToString();
            }

            @Override
            public boolean imageUpdate(Image img, int flags, int x, int y, int w, int h) {
                return true;
            }
        };
        image.setImageObserver(label);
        label.setPreferredSize(DimensionValues.MESA);
        return label;
    }

    /**
     * Cria um JLabel padrão para a exibição de um card na mão do jogador
     *
     * @param card card dono do JLabel
     * @param image imagem do JLabel
     * @return JLabel configurado
     */
    public static JLabel JLabelMaoDefault(Card card, ImageIcon image) {
        return new JLabel(image, JLabel.CENTER) {
            @Override
            public String toString() {
                return Values.TO_STRING_MAO + card.id_long;
            }
        };
    }

    /**
     * Verifica se existe um id específico numa lista de cards
     *
     * @param id id do card
     * @param cards lista de cards
     * @return {@code true} ou {@code false}
     */
    public static boolean containCard(String id, List<Card> cards) {
        return cards.stream().anyMatch((card) -> (card.getId().equals(id)));
    }
}