package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstone.util.Values;


/**
 *
 * @author Vinicius Silva
 */
public class Enfurecer {

    /**
     * Gera o evento de enfurecer de um lacaio
     *
     * @param card lacaio enfurecido
     * @param flag {@code true} para enfurecer ou {@code false} para remover o
     * enfurecer (vida completa)
     */
    public static void processar(Card card, boolean flag) {
        if (card.getPartida().isVezHeroi()) {
            if (!card.isSilenciado()) {
                switch (card.getId()) {
                    //Grommash +6 de ataque
                    case "EX1_414":
                        card.setAtaqueEnfurecer(flag ? 6 : 0);
                        break;
                    //Berserker Amani
                    case "EX1_393":
                        card.setAtaqueEnfurecer(flag ? 3 : 0);
                        break;
                    //Frango Nervoso
                    case "EX1_009":
                        card.setAtaqueEnfurecer(flag ? 5 : 0);
                        break;
                    //Guerreiro Tauren
                    case "EX1_390":
                        card.setAtaqueEnfurecer(flag ? 3 : 0);
                        break;
                    //Guerrobô
                    case "GVG_051":
                        card.setAtaqueEnfurecer(flag ? 1 : 0);
                        break;
                    //Worgen Enfurecido
                    case "EX1_412":
                        card.setAtaqueEnfurecer(flag ? 1 : 0);
                        if (flag) {
                            card.addMechanics(Values.FURIA_DOS_VENTOS);
                        } else {
                            card.delMechanics(Values.FURIA_DOS_VENTOS);
                        }
                        break;
                }
            }
        }
    }
}