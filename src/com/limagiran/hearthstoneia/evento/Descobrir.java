package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstone.partida.util.Random;
import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstone.util.Utils;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class Descobrir {

    /**
     * Descobrir um card pelo custo dele
     *
     * @param card Card que gerou o evento
     * @param custo custo do card
     */
    public static void custo(Card card, int custo) {
        List<com.limagiran.hearthstone.card.control.Card> list = new ArrayList<>();
        while (list.size() < 3) {
            com.limagiran.hearthstone.card.control.Card random = Random.byCusto(custo);
            while (list.contains(random)) {
                random = Random.byCusto(custo);
            }
            list.add(random);
        }
        card.getDono().addMao(card.getPartida().criarCard(Utils.descobrirIA("Descubra um card com " + custo + " de custo",
                        com.limagiran.hearthstone.util.Utils.getIds(list)),
                System.nanoTime()));
    }

    /**
     * Descobrir um lacaio pela Raça
     *
     * @param card Card que gerou o evento
     * @param race raça do lacaio
     */
    public static void raca(Card card, String race) {
        List<com.limagiran.hearthstone.card.control.Card> list = new ArrayList<>();
        while (list.size() < 3) {
            com.limagiran.hearthstone.card.control.Card random = Random.byRace(race);
            while (list.contains(random)) {
                random = Random.byRace(race);
            }
            list.add(random);
        }
        card.getDono().addMao(card.getPartida().criarCard(Utils.descobrirIA("Descubra um " + Utils.traduzirRaca(race),
                        com.limagiran.hearthstone.util.Utils.getIds(list)),
                System.nanoTime()));
    }

    /**
     * Descobrir um lacaio pela Mecânica
     *
     * @param card Card que gerou o evento
     * @param mecanica mecânica do lacaio
     */
    public static void mecanica(Card card, String mecanica) {
        List<com.limagiran.hearthstone.card.control.Card> list = new ArrayList<>();
        while (list.size() < 3) {
            com.limagiran.hearthstone.card.control.Card random = Random.byMechanic(mecanica);
            while (list.contains(random)) {
                random = Random.byMechanic(mecanica);
            }
            list.add(random);
        }
        card.getDono().addMao(card.getPartida().criarCard(Utils.descobrirIA("Descubra um " + Utils.traduzirRaca(mecanica),
                        com.limagiran.hearthstone.util.Utils.getIds(list)),
                System.nanoTime()));
    }

    /**
     * Descobrir um poder heróico
     *
     * @param card Card que gerou o evento
     */
    public static void poderHeroico(Card card) {
        List<String> list = new ArrayList<>();
        while (list.size() < 3) {
            String random = Values.PODER_HEROICO_BASICO[Utils.random(Values.PODER_HEROICO_BASICO.length) - 1];
            while (list.contains(random) || random.equals(card.getDono().getPoderHeroico().getTipo())) {
                random = Values.PODER_HEROICO_BASICO[Utils.random(Values.PODER_HEROICO_BASICO.length) - 1];
            }
            list.add(random);
        }
        String[] ids = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ids[i] = list.get(i);
        }
        card.getDono().setPoderHeroico(Utils.descobrirIA("Descubra um Poder Heroico novo", ids));
    }
}