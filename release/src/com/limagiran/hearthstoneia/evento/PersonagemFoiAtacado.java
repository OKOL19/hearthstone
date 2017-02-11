package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstoneia.server.GameCliente;
import com.limagiran.hearthstone.util.Param;
import com.limagiran.hearthstone.util.Values;


/**
 *
 * @author Vinicius
 */
public class PersonagemFoiAtacado {

    private static Partida partida;
    private static Heroi hero;

    /**
     * Verifica se há algum evento para personagem foi atacado
     *
     * @param hero herói que foi atacado
     */
    public static void processar(Heroi hero) {
        PersonagemFoiAtacado.partida = hero.getPartida();
        PersonagemFoiAtacado.hero = hero;
        if (partida.isVezHeroi()) {
            for (Card c : partida.getAllCardsIncludeSecrets()) {
                if (!c.isSilenciado()) {
                    switch (c.getId()) {
                        //Armadilha de Urso (Segredo: Depois que o seu herói for atacado, evoque um Urso 3/3 com Provocar)
                        case "AT_060":
                            at_060(c);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Armadilha de Urso (Segredo: Depois que o seu herói for atacado, evoque um
     * Urso 3/3 com Provocar)
     */
    private static void at_060(Card segredo) {
        if (hero != null && partida.getOponente().equals(hero)
                && hero.isCard(segredo.id_long) && hero.temEspacoNaMesa()) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            hero.evocar(partida.criarCard(Values.URSO3_3PROVOCAR, System.nanoTime()));
            SegredoRevelado.processar(segredo);
        }
    }

}