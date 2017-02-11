package com.limagiran.hearthstone.evento;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Utils;
import com.limagiran.hearthstone.heroi.control.JogouCardException;
import com.limagiran.hearthstone.partida.control.Partida;
import java.util.List;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius
 */
public class LacaioEnfeiticado {

    private static Partida partida;
    private static Card lacaio;
    private static Card feitico;

    /**
     * Verifica se há algum evento para lacaio enfeitiçado
     *
     * @param lacaio lacaio enfeitiçado
     * @param feitico feitiço lançado
     */
    public static void processar(Card lacaio, Card feitico) throws JogouCardException {
        LacaioEnfeiticado.lacaio = lacaio;
        LacaioEnfeiticado.feitico = feitico;
        LacaioEnfeiticado.partida = lacaio.getPartida();
        if (partida.isVezHeroi()) {
            List<Card> list = partida.getAllCards();
            list.addAll(partida.getMao());
            for (Card c : list) {
                if (!c.isSilenciado()) {
                    switch (c.getId()) {
                        //Fiola Ruinaluz (Sempre que você alvejar este lacaio com um feitiço, ganhe Escudo Divino)
                        case "AT_129":
                            at_129(c);
                            break;
                        //Eydis Ruinatreva (Sempre que você alvejar este lacaio com um feitiço, cause 3 de dano a um inimigo aleatório)
                        case "AT_131":
                            at_131(c);
                            break;
                        //Feiticeiro Draconiano (Sempre que você alvejar este lacaio com um feitiço, receba +1/+1)
                        case "BRM_020":
                            brm_020(c);
                            break;
                        //Djinnis dos Zéfiros (Sempre que lançar um feitiço em um lacaio aliado, lance uma cópia do feitiço neste card)
                        case "LOE_053":
                            loe_053(c);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Fiola Ruinaluz (Sempre que você alvejar este lacaio com um feitiço, ganhe
     * Escudo Divino)
     *
     * @param c Card
     */
    private static void at_129(Card c) {
        if (lacaio.equals(c) && partida.getHero().isCard(lacaio.id_long)
                && !c.getMechanics().contains(Values.ESCUDO_DIVINO)) {
            c.addMechanics(Values.ESCUDO_DIVINO);
        }
    }

    /**
     * Eydis Ruinatreva (Sempre que você alvejar este lacaio com um feitiço,
     * cause 3 de dano a um inimigo aleatório)
     *
     * @param c Card
     */
    private static void at_131(Card c) {
        if (lacaio.equals(c) && partida.getHero().isCard(lacaio.id_long)) {
            Utils.dano(partida, Utils.getAleatorioOponente(partida), 3);
            lacaio.delMechanics(Values.FURTIVIDADE);
        }

    }

    /**
     * Feiticeiro Draconiano (Sempre que você alvejar este lacaio com um
     * feitiço, receba +1/+1)
     *
     * @param c Card
     */
    private static void brm_020(Card c) {
        if (lacaio.equals(c) && partida.getHero().isCard(lacaio.id_long)) {
            lacaio.addVidaMaxima(1);
            lacaio.addAtaque(1);
        }
    }

    /**
     * Djinnis dos Zéfiros (Sempre que lançar um feitiço em um lacaio aliado,
     * lance uma cópia do feitiço neste card)
     *
     * @param c Card
     */
    private static void loe_053(Card c) throws JogouCardException {
        if (lacaio.getDono().equals(c.getDono()) && !lacaio.equals(c)
                && partida.getHero().isCard(lacaio.id_long)) {
            UtilizarFeitico.switchFeitico(partida, feitico.getId(), c.id_long);
        }
    }
}