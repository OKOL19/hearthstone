package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.Utils;
import com.limagiran.hearthstoneia.partida.control.Partida;
import java.util.List;


/**
 *
 * @author Vinicius Silva
 */
public class AtacarLacaiosAdjacentes {

    private static Partida partida;
    private static Card atacante;
    private static Card alvo;

    /**
     * Verifica se o lacaio atacante também ataca os lacaios adjacentes. Caso
     * ele ataque, gera o ataque ao lacaio alvo e aos adjacentes.
     *
     * @param atacante lacaio atacando
     * @param alvo lacaio sendo atacado
     * @return {@code true} ou {@code false}
     */
    public static boolean processar(Card atacante, Card alvo) {
        AtacarLacaiosAdjacentes.atacante = atacante;
        AtacarLacaiosAdjacentes.alvo = alvo;
        AtacarLacaiosAdjacentes.partida = atacante.getPartida();
        if (!atacante.isSilenciado()) {
            switch (atacante.getId()) {
                //Magnatauro Alfa (Também causa dano aos lacaios adjacentes ao alvo atacado)
                case "AT_067":
                    return at_067();
                //Ceifador de Inimigos 4000 (Também causa dano aos lacaios adjacentes ao alvo atacado)
                case "GVG_113":
                    return gvg_113();
            }
        }
        return false;
    }

    /**
     * Magnatauro Alfa (Também causa dano aos lacaios adjacentes ao alvo
     * atacado)
     */
    private static boolean at_067() {
        List<Card> lacaios = Utils.getAdjacentes(alvo);
        lacaios.add(0, alvo);
        EmArea.dano(partida, lacaios, atacante.getAtaque(), 0);
        return true;
    }

    /**
     * Ceifador de Inimigos 4000 (Também causa dano aos lacaios adjacentes ao
     * alvo atacado)
     */
    private static boolean gvg_113() {
        List<Card> lacaios = Utils.getAdjacentes(alvo);
        lacaios.add(0, alvo);
        EmArea.dano(partida, lacaios, atacante.getAtaque(), 0);
        return true;
    }
}