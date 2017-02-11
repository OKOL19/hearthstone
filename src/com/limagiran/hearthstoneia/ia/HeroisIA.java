package com.limagiran.hearthstoneia.ia;

import com.limagiran.hearthstoneia.Utils;
import com.limagiran.hearthstone.deck.Deck;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import static com.limagiran.hearthstone.HearthStone.CARTAS;
import com.limagiran.hearthstone.deck.util.Util;
import com.limagiran.hearthstone.util.Param;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinicius Silva
 */
public class HeroisIA {

    private static String[] getRandom() throws Exception {
        String[] deckListNome = Util.getRandomDeck();
        List<String> deckListId = new ArrayList<>();
        deckListId.add(deckListNome[0]);
        deckListId.add(deckListNome[1]);
        for (int i = 2; i < deckListNome.length; i++) {
            deckListId.add(CARTAS.findIdByName(deckListNome[i]));
        }
        return deckListId.toArray(new String[0]);
    }

    public static Heroi criarHeroi(String[] args) throws Exception {
        String[] deckList = args != null ? args : getRandom();
        Deck deck = new Deck(deckList[0], deckList[1], 19);
        for (int i = 2; i < deckList.length; i++) {
            deck.addCard(deckList[i]);
        }
        return new Heroi("Seu PC", Param.HEROI, deck.getClasse(), Utils.getDeckCardIA(deck.getDeckEmbaralhadoIA()));
    }
}