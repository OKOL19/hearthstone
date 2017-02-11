package com.limagiran.hearthstone.evento;

import com.limagiran.hearthstone.util.*;
import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.partida.control.*;
import com.limagiran.hearthstone.server.GameCliente;
import java.util.*;

/**
 *
 * @author Vinicius Silva
 */
public class EmArea implements Param {

    /**
     * Gera um evento de dano em área
     *
     * @param partida
     * @param lacaios alvos
     * @param dano valor do dano
     * @param variacao variação no valor do dano
     */
    public static void dano(Partida partida, List<Card> lacaios, int dano, int variacao) {
        String packIds = "";
        String packValor = "";
        variacao = variacao < 0 ? 0 : variacao;
        lacaios.sort(Sort.time());
        List<Integer> danos = new ArrayList<>();
        for (Card lacaio : lacaios) {
            int vidaAnterior = lacaio.getVida();
            lacaio.delVidaEmArea(dano + (Utils.random(100) % (variacao + 1)));
            danos.add(lacaio.getVida() - vidaAnterior);
            lacaio.getAnimacao().vidaAtual(danos.get(danos.size() - 1), 2000, false, false);
            packValor += (!packValor.isEmpty() ? ";" : "") + (danos.get(danos.size() - 1));
            packIds += (!packIds.isEmpty() ? ";" : "") + lacaio.id_long;
        }
        if (!packValor.isEmpty()) {
            Pacote pacote = new Pacote(ANIMACAO_CARD_VIDA_EM_AREA);
            pacote.set(ANIMACAO_CARD_VIDA_EM_AREA_IDS, packIds);
            pacote.set(ANIMACAO_CARD_VIDA_EM_AREA_VALOR, packValor);
            GameCliente.send(pacote);
            Audios.playEfeitos(Audios.PARTIDA_DANO_GERADO);
            Utils.sleep(2000);
        }
        lacaios.forEach(lacaio -> {
            if (lacaio.getVida() <= 0) {
                lacaio.getAnimacao().morreu();
                GameCliente.send(ANIMACAO_CARD_MORREU, lacaio.id_long);
                lacaio.getDono().addMorto(lacaio);
                lacaio.getDono().delMesa(lacaio);
            }
        });
        for (int i = 0; i < danos.size(); i++) {
            try {
                if (danos.get(i) > 0) {
                    GameCliente.addHistorico(lacaios.get(i).getName() + " recebeu " + danos.get(i) + " de dano.", true);
                    PersonagemRecebeuDano.processar(null, lacaios.get(i), danos.get(i), lacaios);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        lacaios.stream().filter(lacaio -> (lacaio.getVida() <= 0)).forEach(lacaio -> lacaio.morreu(false));
    }

    /**
     * Gera um evento de cura em área
     *
     * @param partida
     * @param lacaios alvos
     * @param vida valor da vidaAtual restaurada
     */
    public static void cura(Partida partida, List<Card> lacaios, int vida) {
        String packIds = "";
        String packValor = "";
        lacaios.sort(Sort.time());
        List<Integer> curas = new ArrayList<>();
        for (Card lacaio : lacaios) {
            int vidaAnterior = lacaio.getVida();
            lacaio.addVidaEmArea(vida);
            curas.add(lacaio.getVida() - vidaAnterior);
            lacaio.getAnimacao().vidaAtual(curas.get(curas.size() - 1), 2000, false, false);
            packValor += (!packValor.isEmpty() ? ";" : "") + (curas.get(curas.size() - 1));
            packIds += (!packIds.isEmpty() ? ";" : "") + lacaio.id_long;
        }
        if (!packValor.isEmpty()) {
            Pacote pacote = new Pacote(ANIMACAO_CARD_VIDA_EM_AREA);
            pacote.set(packIds, ANIMACAO_CARD_VIDA_EM_AREA_IDS);
            pacote.set(packValor, ANIMACAO_CARD_VIDA_EM_AREA_VALOR);
            GameCliente.send(pacote);
            Audios.playEfeitos(Audios.PARTIDA_CURAR);
            Utils.sleep(2000);
        }
        for (int i = 0; i < curas.size(); i++) {
            try {
                if (curas.get(i) < 0) {
                    GameCliente.addHistorico(lacaios.get(i).getName() + " recebeu " + curas.get(i) + " de dano.", true);
                    PersonagemRecebeuDano.processar(null, lacaios.get(i), curas.get(i), lacaios);
                } else if (curas.get(i) > 0) {
                    GameCliente.addHistorico(lacaios.get(i).getName() + " teve " + curas.get(i) + " de vida restaurada.", true);
                    PersonagemFoiCurado.processar(null, lacaios.get(i), lacaios);
                }
            } catch (Exception ex) {
                //ignore
            }
        }
        lacaios.stream().filter(lacaio -> (lacaio.getVida() <= 0)).forEach(lacaio -> lacaio.morreu());
    }

    /**
     * Gera um evento de destruir lacaios em área
     *
     * @param lacaios alvos
     */
    public static void destruir(List<Card> lacaios) {
        for (int i = 0; i < lacaios.size(); i++) {
            lacaios.get(i).getDono().addMorto(lacaios.get(i));
            lacaios.get(i).getDono().delMesa(lacaios.get(i));
        }
        for (int i = 0; i < lacaios.size(); i++) {
            lacaios.get(i).morreu();
        }
    }

}
