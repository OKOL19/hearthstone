package com.limagiran.hearthstone.evento;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.*;
import com.limagiran.hearthstone.partida.util.Random;
import java.util.*;

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
        List<String> list = new ArrayList<>();
        while (list.size() < 3) {
            Card random = Random.byCusto(custo);
            if (!list.contains(random.getId())) {
                list.add(random.getId());
            }
        }
        String id = Utils.descobrir("Descubra um card com " + custo + " de custo", list.toArray(new String[0]));
        card.getDono().addMao(card.getPartida().criarCard(id, System.nanoTime()));
    }

    /**
     * Descobrir um lacaio pela Raça
     *
     * @param card Card que gerou o evento
     * @param race raça do lacaio
     */
    public static void raca(Card card, String race) {
        List<String> list = new ArrayList<>();
        while (list.size() < 3) {
            Card random = Random.byRace(race);
            if (!list.contains(random.getId())) {
                list.add(random.getId());
            }
        }
        String id = Utils.descobrir("Descubra um " + Utils.traduzirRaca(race), list.toArray(new String[0]));
        card.getDono().addMao(card.getPartida().criarCard(id, System.nanoTime()));
    }

    /**
     * Descobrir um lacaio pela Mecânica
     *
     * @param card Card que gerou o evento
     * @param mecanica mecânica do lacaio
     */
    public static void mecanica(Card card, String mecanica) {
        List<String> list = new ArrayList<>();
        while (list.size() < 3) {
            Card random = Random.byMechanic(mecanica);
            if (!list.contains(random.getId())) {
                list.add(random.getId());
            }
        }
        String id = Utils.descobrir("Descubra um " + Utils.traduzirRaca(mecanica), list.toArray(new String[0]));
        card.getDono().addMao(card.getPartida().criarCard(id, System.nanoTime()));
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
            if(!list.contains(random) && !random.equals(card.getDono().getPoderHeroico().getTipo())) {
                list.add(random);
            }
        }
        card.getDono().setPoderHeroico(Utils.descobrir("Descubra um Poder Heroico novo", list.toArray(new String[0])));
    }
}
