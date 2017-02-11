package com.limagiran.hearthstoneia.partida.view;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.Utils;
import com.limagiran.hearthstoneia.partida.control.Partida;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinicius Silva
 */
public class Muligar {

    private final List<Card> deck;
    private final boolean[] muligar;

    /**
     * Creates new form Muligar
     *
     * @param deck deck com as cartas embaralhadas
     * @param quantidade quantidade de cartas disponíveis para troca
     */
    private Muligar(Partida partida) {
        this.deck = partida.getHero().getDeck();
        muligar = new boolean[partida.isVezHeroi() ? 3 : 4];
    }

    /**
     * Creates new form Muligar
     *
     * @param partida
     * @return
     */
    public static void main(Partida partida) {
        new Muligar(partida).muligar();
    }

    /**
     * Verifica se algum card foi selecionado para muligar e realiza a troca
     */
    private void muligar() {
        List<Integer> maoAtual = new ArrayList<>();
        List<Integer> maoNova = new ArrayList<>();
        for (int i = 0; i < muligar.length; i++) {
            if (deck.get(i).getCost() > 3) {
                muligar[i] = true;
            }
        }
        for (int index = 0; index < muligar.length; index++) {
            //verifica se o card foi selecionado
            if (muligar[index]) {
                maoAtual.add(index);
                //gera um card aleatório para realizar a muligação entre eles
                int maoNovaInt = Utils.random(deck.size() - muligar.length) + muligar.length - 1;
                //verifica se o card selecionado já foi selecionado anterior para outra muligação
                while (maoNova.contains(maoNovaInt)) {
                    maoNovaInt = Utils.random(deck.size() - muligar.length) + muligar.length - 1;
                }
                maoNova.add(maoNovaInt);
            }
        }
        //verifica se algum card foi selecionado
        if (!maoAtual.isEmpty()) {
            for (int index = 0; index < maoAtual.size(); index++) {
                Card card = deck.get(maoAtual.get(index));
                deck.add(maoNova.get(index), card);
                deck.remove((int) maoAtual.get(index));
                card = deck.get(maoNova.get(index));
                deck.add(maoAtual.get(index), card);
                deck.remove((int) maoNova.get(index) + 1);
            }
        }
    }
}