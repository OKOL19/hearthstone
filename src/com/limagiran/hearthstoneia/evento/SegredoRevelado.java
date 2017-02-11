package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;


/**
 *
 * @author Vinicius Silva
 */
public class SegredoRevelado {

    private static Card segredo;

    /**
     * Verifica se há algum evento para segredo revelado
     *
     * @param segredo segredo revelado
     */
    public static void processar(Card segredo) {
        segredo.getPartida().addHistorico("O segredo " + segredo.getName() + " foi revelado.\n" + segredo.getDescricao() + ".");
        SegredoRevelado.segredo = segredo;
        if (segredo.getPartida().isVezHeroi()) {
            for (Card c : segredo.getPartida().getAllCards()) {
                if (!c.isSilenciado()) {
                    switch (c.getId()) {
                        //Arco Hastáguia (Sempre que um Segredo aliado for revelado, receba +1 de Durabilidade)
                        case "EX1_536":
                            ex1_536(c);
                            break;
                    }
                }
            }
        }
        segredo.getDono().delSegredo(SegredoRevelado.segredo);
    }

    /**
     * Arco Hastáguia (Sempre que um Segredo aliado for revelado, receba +1 de
     * Durabilidade)
     *
     * @param c Card
     */
    private static void ex1_536(Card c) {
        if (segredo.getDono().equals(c.getDono())) {
            c.addDurabilidade(1);
        }
    }
}