package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import com.limagiran.hearthstoneia.partida.control.Partida;


/**
 *
 * @author Vinicius
 */
public class FazerAoAtacar {

    public static final String CURAR = "curar";
    public static final String DANO = "dano";
    public static final String COMPRAR = "comprar";

    /**
     * Verifica se há algum evento para fazer algo ao atacar
     *
     * @param card lacaio que está atacando
     */
    public static void processar(Card card) {
        for (String[] values : card.getFazerAoAtacar()) {
            try {
                Heroi hero = Integer.parseInt(values[0]) == card.getPartida().getPlayer()
                        ? card.getPartida().getHero()
                        : card.getPartida().getOponente();
                switch (values[1]) {
                    case FazerAoAtacar.CURAR:
                        hero.addHealth(Integer.parseInt(values[2]));
                        break;
                    case FazerAoAtacar.DANO:
                        hero.delHealth(Integer.parseInt(values[2]));
                        break;
                    case FazerAoAtacar.COMPRAR:
                        hero.comprarCarta(Card.COMPRA_FEITICO);
                        break;
                    default:
                        break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Cria um array de configuração padrão para curar o herói sempre que um
     * lacaio atacar
     *
     * @param value quantidade de vida restaurada
     * @return String array com a configuração padrão
     */
    public static String[] curarHeroi(Partida partida, int value) {
        return new String[]{Integer.toString(partida.getPlayer()),
            FazerAoAtacar.CURAR, Integer.toString(value)};
    }

    /**
     * Cria um array de configuração padrão para comprar uum card um lacaio
     * atacar
     *
     * @return String array com a configuração padrão
     */
    public static String[] comprarCardHeroi(Partida partida) {
        return new String[]{Integer.toString(partida.getPlayer()), FazerAoAtacar.COMPRAR, ""};
    }

}