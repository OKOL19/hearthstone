package com.limagiran.hearthstone.evento;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Utils;
import com.limagiran.hearthstone.util.Param;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.server.GameCliente;

/**
 *
 * @author Vinicius Silva
 */
public class Justas {

    public static Card justaHeroi;
    public static Card justaOponente;

    /**
     * Gera um evento Justas
     *
     * @param hero herói
     * @param oponente oponente
     * @return retorna o vencedor das justas Param.HEROI ou Param.OPONENTE
     */
    public static long processar(Heroi hero, Heroi oponente) {
        justaHeroi = null;
        justaOponente = null;
        List<Card> deckHeroi = new ArrayList<>();
        List<Card> deckOponente = new ArrayList<>();
        for (Card c : hero.getDeck()) {
            if (c.getType().equals("Minion")) {
                deckHeroi.add(c);
            }
        }
        for (Card c : oponente.getDeck()) {
            if (c.getType().equals("Minion")) {
                deckOponente.add(c);
            }
        }
        if (deckHeroi.isEmpty() && deckOponente.isEmpty()) {
            GameCliente.addHistorico(oponente.getNome() + " venceu as justas.\n"
                    + getDescricao(hero.getNome(), null) + getDescricao(oponente.getNome(), null), true);
            return Param.OPONENTE;
        } else if (deckHeroi.isEmpty()) {
            justaOponente = deckOponente.get(Utils.random(deckOponente.size()) - 1);
            GameCliente.addHistorico(oponente.getNome() + " venceu as justas.\n"
                    + getDescricao(hero.getNome(), null) + getDescricao(oponente.getNome(), justaOponente), true);
            animacao();
            return Param.OPONENTE;
        } else if (deckOponente.isEmpty()) {
            justaHeroi = deckHeroi.get(Utils.random(deckHeroi.size()) - 1);
            GameCliente.addHistorico(hero.getNome() + " venceu as justas.\n"
                    + getDescricao(hero.getNome(), justaHeroi) + "\n" + getDescricao(oponente.getNome(), null), true);
            animacao();
            return Param.HEROI;
        } else {
            justaHeroi = deckHeroi.get(Utils.random(deckHeroi.size()) - 1);
            justaOponente = deckOponente.get(Utils.random(deckOponente.size()) - 1);
            if (justaHeroi.getCost() > justaOponente.getCost()) {
                GameCliente.addHistorico(hero.getNome() + " venceu as justas.\n"
                        + getDescricao(hero.getNome(), justaHeroi) + "\n"
                        + getDescricao(oponente.getNome(), justaOponente), true);
                animacao();
                return Param.HEROI;
            } else {
                GameCliente.addHistorico(oponente.getNome() + " venceu as justas.\n"
                        + getDescricao(hero.getNome(), justaHeroi) + "\n"
                        + getDescricao(oponente.getNome(), justaOponente), true);
                animacao();
                return Param.OPONENTE;
            }
        }
    }

    /**
     * Retorna a descrição do card
     *
     * @param nome nome do herói
     * @param card Card
     * @return "#nomeHeroi = #nomeCard (#custo/#ataque/#vida) #textoDoCard
     */
    private static String getDescricao(String nome, Card card) {
        return card != null ? Utils.getDescricao(nome, card) : nome + " não tem lacaio no deck.";
    }

    /**
     * Exibe os cards escolhidos para as justas
     */
    private static void animacao() {
        GameCliente.exibirJustas(justaHeroi == null ? "null" : justaHeroi.getId(), justaOponente == null ? "null" : justaOponente.getId());
    }
}