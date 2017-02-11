package com.limagiran.hearthstone.util;

import static com.limagiran.hearthstone.util.Utils.reflection;
import com.limagiran.hearthstone.deck.CardView;
import java.util.Comparator;
import static com.limagiran.hearthstone.util.Utils.reflection;

/**
 *
 * @author Vinicius
 */
public class Sort {

    /**
     * Ordenar a lista de cards por custo
     *
     * @return objeto Comparator para ordenação
     */
    public static Comparator<Object> custo() {
        return (Object ob1, Object ob2) -> {
            if (reflection(Integer.class, ob1, "getCost") < reflection(Integer.class, ob2, "getCost")) {
                return -1;
            } else if (reflection(Integer.class, ob1, "getCost") > reflection(Integer.class, ob2, "getCost")) {
                return 1;
            } else {
                return reflection(String.class, ob1, "getName")
                        .compareTo(reflection(String.class, ob2, "getName"));
            }
        };
    }

    /**
     * Ordenar a lista de cards pelo id
     *
     * @return objeto Comparator para ordenação
     */
    public static Comparator<Object> id() {
        return (Object ob1, Object ob2) -> reflection(String.class, ob1, "getId")
                .compareTo(reflection(String.class, ob2, "getId"));
    }

    /**
     * Ordenar a lista de cards pelo time
     *
     * @return objeto Comparator para ordenação
     */
    public static Comparator<Object> time() {
        return (Object ob1, Object ob2) -> {
            if (ob1 == null || ob2 == null) {
                return 1;
            }
            return (int) (reflection(Long.class, ob1, "getTime") - reflection(Long.class, ob2, "getTime"));
        };
    }

    /**
     * Ordenar a lista de cards pelo custo
     *
     * @return objeto Comparator para ordenação
     */
    public static Comparator<CardView> custoCardLabel() {
        return (CardView card1, CardView card2) -> {
            if (card1.getCost() < card2.getCost()) {
                return -1;
            } else if (card1.getCost() > card2.getCost()) {
                return 1;
            } else {
                return card1.getNome().compareTo(card2.getNome());
            }
        };
    }
}