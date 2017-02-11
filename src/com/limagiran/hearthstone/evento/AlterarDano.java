package com.limagiran.hearthstone.evento;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.util.List;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class AlterarDano {

    private static Heroi hero;
    private static int damage;

    /**
     * Verifica se há algum evento para alterar o dano causado ao herói
     *
     * @param hero herói recebendo dano
     * @param damage valor do dano
     * @return valor do dano atualizado após a verificação e processamento dos
     * eventos
     */
    public static int processar(Heroi hero, int damage) {
        AlterarDano.hero = hero;
        AlterarDano.damage = damage;
        List<Card> list = hero.getPartida().getAllCardsIncludeSecrets();
        for (Card c : list) {
            if (!c.isSilenciado()) {
                switch (c.getId()) {
                    //lâmina amaldiçoada (dobra o dano causado ao seu herói)
                    case "LOE_118":
                        loe_118();
                        break;
                    //armadura animada (herói só pode receber 1 de dano por vez)
                    case "LOE_119":
                        loe_119();
                        break;
                }
            }
        }
        return AlterarDano.damage;
    }

    /**
     * Armadura Animada (herói só pode receber 1 de dano por vez)
     */
    private static void loe_119() {
        if (hero != null && hero.temNaMesa(Values.ARMADURA_ANIMADA) && damage > 1) {
            for (Card c : hero.getMesa()) {
                if (c.getId().equals(Values.ARMADURA_ANIMADA)) {
                    c.getAnimacao().disparoDeEvento();
                    break;
                }
            }
            damage = 1;
        }
    }

    /**
     * lâmina amaldiçoada (dobra o dano causado ao seu herói)
     */
    private static void loe_118() {
        if (hero != null && hero.getArma() != null && hero.getArma().getId().equals("LOE_118") && damage > 0) {
            damage *= 2;
        }
    }
}