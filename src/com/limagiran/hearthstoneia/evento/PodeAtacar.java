package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.partida.control.Partida;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Vinicius
 */
public class PodeAtacar implements Serializable {

    private final List<Long> podemAtacarNesteTurno = new ArrayList<>();
    private final Partida partida;

    public PodeAtacar(Partida partida) {
        this.partida = partida;
    }

    /**
     * Atualiza a investida dos lacaios que podem ou não atacar atualmente
     */
    public void processar() {
        List<Card> list = partida.getMesa();
        for (Card c : list) {
            switch (c.getId()) {
                //Vigia Argênteo (Não pode atacar. Inspirar: Pode atacar normalmente neste turno)
                case "AT_109":
                    at_109(c);
                    break;
                //Vigia Anciente (Não pode atacar)
                case "EX1_045":
                    ex1_045(c);
                    break;
                //Ragnaros, o Senhor do Fogo (Não pode atacar. No final do seu turno, cause 8 de dano a um inimigo aleatório)
                case "EX1_298":
                    ex1_298(c);
                    break;
                //Estátua Sinistra (Ela só pode atacar se for o único lacaio no campo de batalha)
                case "LOE_107":
                    loe_107(c);
                    break;
            }
        }
    }

    public List<Long> getPodemAtacarNesteTurno() {
        return podemAtacarNesteTurno;
    }

    /**
     * Vigia Argênteo (Não pode atacar. Inspirar: Pode atacar normalmente neste
     * turno)
     *
     * @param c Card
     */
    private void at_109(Card c) {
        if (c.isSilenciado() || podemAtacarNesteTurno.contains(c.id_long)) {
            c.setAtaquesRealizados(c.getAtaquesRealizados());
        } else {
            c.setInvestidaVisible(false);
        }
    }

    /**
     * Vigia Anciente (Não pode atacar)
     *
     * @param c Card
     */
    private static void ex1_045(Card c) {
        if (c.isSilenciado()) {
            c.setAtaquesRealizados(c.getAtaquesRealizados());
        } else {
            c.setInvestidaVisible(false);
        }
    }

    /**
     * Ragnaros, o Senhor do Fogo (Não pode atacar. No final do seu turno, cause
     * 8 de dano a um inimigo aleatório)
     *
     * @param c Card
     */
    private static void ex1_298(Card c) {
        if (c.isSilenciado()) {
            c.setAtaquesRealizados(c.getAtaquesRealizados());
        } else {
            c.setInvestidaVisible(false);
        }
    }

    /**
     * Estátua Sinistra (Ela só pode atacar se for o único lacaio no campo de
     * batalha)
     *
     * @param c Card
     */
    private static void loe_107(Card c) {
        if (c.isSilenciado() || (c.getDono().getMesa().size() == 1 && c.getOponente().getMesa().isEmpty())) {
            c.setAtaquesRealizados(c.getAtaquesRealizados());
        } else {
            c.setInvestidaVisible(false);
        }
    }
}