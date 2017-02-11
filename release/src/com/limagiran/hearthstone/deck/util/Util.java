package com.limagiran.hearthstone.deck.util;

import com.limagiran.hearthstone.util.RW;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinicius Silva
 */
public class Util {

    private static final String[] DECK_DEFAULT = new String[]{
        "Paladin", "Aggro Paladin", "Bênção do Poder", "Escudeira Argêntea",
        "Gnomo Leproso", "Gnomo Leproso", "Marujo dos Mares do Sul", "Marujo dos Mares do Sul",
        "Sargento Abusivo", "Sargento Abusivo", "Coruja Bico-de-ferro", "Coruja Bico-de-ferro",
        "Malabarista de Facas", "Malabarista de Facas", "Minirrobô blindado", "Minirrobô blindado",
        "Protetor Argênteo", "Favorecimento Divino", "Favorecimento Divino", "Preparação de Batalha",
        "Preparação de Batalha", "Cavalgante Argênteo", "Cavalgante Argênteo", "Golem Arcano",
        "Rei Mukla", "Defensora Veraprateada", "Defensora Veraprateada", "Bênção dos Reis",
        "Bênção dos Reis", "Consagração", "Consagração", "Leeroy Jenkins"};
    private static final int QUANTIDADE_DE_DECKS = 18;
    private static final List<String[]> DECKS = loadDecks();

    public static String[] getRandomDeck() {
        return !DECKS.isEmpty()
                ? DECKS.get(com.limagiran.hearthstone.util.Utils.random(DECKS.size()) - 1)
                : DECK_DEFAULT;
    }

    private static List<String[]> loadDecks() {
        List<String[]> retorno = new ArrayList<>();
        for (int i = 0; i < QUANTIDADE_DE_DECKS; i++) {
            try {
                retorno.add(RW.readJarS("/com/limagiran/hearthstone/deck/list/" + i + ".deck").split("\n"));
            } catch (Exception ex) {
                //ignore
            }
        }
        return retorno;
    }
    
    public static List<String[]> getDecks() {
        return (List<String[]>) new ArrayList<>(DECKS).clone();
    }
}