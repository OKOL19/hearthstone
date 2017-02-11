package com.limagiran.hearthstone.partida.util;

import static com.limagiran.hearthstone.HearthStone.CARTAS;
import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius
 */
public class Random implements Values {

    public static final int QUALQUER_CUSTO = -1;

    public static List<Card> listOnlyFeitico(int qtd) {
        return getList(qtd, "Spell");
    }

    public static List<Card> deck(int qtd) {
        return getList(qtd, "Minion", "Weapon", "Spell");
    }

    private static List<Card> getList(int qtd, String... typesArray) {
        List<Card> deck = new ArrayList<>();
        List<String> types = new ArrayList<>(Arrays.asList(typesArray));
        while (deck.size() < qtd) {
            Card card = CARTAS.getAllCards().get(Utils.random(CARTAS.getAllCards().size()) - 1);
            while (!types.contains(card.getType()) || deck.contains(card) || !card.isCollectible()) {
                card = CARTAS.getAllCards().get(Utils.random(CARTAS.getAllCards().size()) - 1);
            }
            deck.add(CARTAS.createCard(card.getId()));
        }
        return deck;
    }

    public static Card lacaio() {
        return byType("Minion");
    }

    public static Card arma() {
        return byType("Weapon");
    }

    public static Card feitico() {
        return byType("Spell");
    }

    public static Card getCard(String type, String playerClass, int custo, String race, String rarity, String mechanic) {
        Card card = CARTAS.getAllCards().get(Utils.random(CARTAS.getAllCards().size()) - 1);
        while (!card.isCollectible() || !validarRandom(card, type, playerClass, custo, race, rarity, mechanic)) {
            card = CARTAS.getAllCards().get(Utils.random(CARTAS.getAllCards().size()) - 1);
        }
        return card;
    }

    private static boolean validarRandom(Card card, String type, String playerClass,
            int custo, String race, String rarity, String mechanic) {
        try {
            if (!type.equals(GERAL) && !type.equals(card.getType())) {
                return false;
            } else if (!playerClass.equals(GERAL) && !playerClass.equals(card.getPlayerClass())) {
                return false;
            } else if ((custo != QUALQUER_CUSTO) && (custo != card.getCost())) {
                return false;
            } else if (!race.equals(GERAL) && !race.equals(card.getRace())) {
                return false;
            } else if (!rarity.equals(GERAL) && !rarity.equals(card.getRarity())) {
                return false;
            } else if (!mechanic.equals(GERAL) && ((card.getMechanics() == null) || !card.getMechanics().contains(mechanic))) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Card byType(String type) {
        return getCard(type, GERAL, QUALQUER_CUSTO, GERAL, GERAL, GERAL);
    }

    public static Card byPlayerClass(String playerClass) {
        return getCard(GERAL, playerClass, QUALQUER_CUSTO, GERAL, GERAL, GERAL);
    }

    public static Card byCusto(int custo) {
        return getCard(GERAL, GERAL, custo, GERAL, GERAL, GERAL);
    }

    public static Card byRace(String race) {
        return getCard(GERAL, GERAL, QUALQUER_CUSTO, race, GERAL, GERAL);
    }

    public static Card byRarity(String rarity) {
        return getCard(GERAL, GERAL, QUALQUER_CUSTO, GERAL, rarity, GERAL);
    }

    public static Card byMechanic(String mechanic) {
        return getCard(GERAL, GERAL, QUALQUER_CUSTO, GERAL, GERAL, mechanic);
    }

    public static String pecaSobressalente() {
        return PECA_SOBRESSALENTE[Utils.random(PECA_SOBRESSALENTE.length) - 1];
    }

    public static String companheiroAnimal() {
        return COMPANHEIRO_ANIMAL[Utils.random(COMPANHEIRO_ANIMAL.length) - 1];
    }

    public static String acordePoderoso() {
        return ACORDE_PODEROSO[Utils.random(ACORDE_PODEROSO.length) - 1];
    }

    public static String guerreiroDaHorda() {
        return GUERREIRO_DA_HORDA[Utils.random(GUERREIRO_DA_HORDA.length) - 1];
    }
}