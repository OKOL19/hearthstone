package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.Utils;
import com.limagiran.hearthstone.partida.control.Pacote;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstoneia.server.GameCliente;
import com.limagiran.hearthstone.util.Param;
import com.limagiran.hearthstone.util.Sort;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Vinicius Silva
 */
public class EmArea {

    /**
     * Gera um evento de dano em área
     *
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
            Pacote pacote = new Pacote(Param.ANIMACAO_CARD_VIDA_EM_AREA);
            pacote.set(Param.ANIMACAO_CARD_VIDA_EM_AREA_IDS, packIds);
            pacote.set(Param.ANIMACAO_CARD_VIDA_EM_AREA_VALOR, packValor);
            GameCliente.enviar(pacote);
        }
        for (Card lacaio : lacaios) {
            if (lacaio.getVida() <= 0) {
                GameCliente.enviar(Param.ANIMACAO_CARD_MORREU, lacaio.id_long);
                lacaio.getDono().addMorto(lacaio);
                lacaio.getDono().delMesa(lacaio);
            }
        }
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
        for (Card lacaio : lacaios) {
            if (lacaio.getVida() <= 0) {
                lacaio.morreu(false);
            }
        }
    }

    /**
     * Gera um evento de cura em área
     *
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
            Pacote pacote = new Pacote(Param.ANIMACAO_CARD_VIDA_EM_AREA);
            pacote.set(packIds, Param.ANIMACAO_CARD_VIDA_EM_AREA_IDS);
            pacote.set(packValor, Param.ANIMACAO_CARD_VIDA_EM_AREA_VALOR);
            GameCliente.enviar(pacote);
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
                ex.printStackTrace();
            }
        }
        for (Card lacaio : lacaios) {
            if (lacaio.getVida() <= 0) {
                lacaio.morreu();
            }
        }
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