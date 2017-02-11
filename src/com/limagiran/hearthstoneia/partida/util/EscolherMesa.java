package com.limagiran.hearthstoneia.partida.util;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import com.limagiran.hearthstoneia.ia.InteligenciaArtificial;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstoneia.partida.view.PartidaView;
import com.limagiran.hearthstone.util.Param;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinicius Silva
 */
public class EscolherMesa {

    private static long selecionado = -1;
    public static final long CANCEL = -1;
    public static final long AGUARDANDO_ALVO = -2;
    private static int alvos = 0;
    private static boolean feitico;
    private static Heroi heroi;
    private static Heroi oponente;
    private static List<Card> lacaiosHeroi;
    private static List<Card> lacaiosOponente;
    private static Card excluir;
    private static String titulo;
    private static final List<Long> ALVOS_VALIDOS = new ArrayList<>();

    /**
     * Altera o ícone do mouse para aguardar o jogador selecionar um personagem
     * na janela principal e verifica se o personagem selecionado é válido.
     * Alvos inválidos podem ser declarados como null.
     *
     * @param partidaView
     * @param excluir excluir o lacaio que originou o grito de guerra
     * @param titulo mensagem exibida
     * @param heroi herói
     * @param oponente oponente
     * @param lacaiosHeroi lacaios válidos do herói
     * @param lacaiosOponente lacaios válidos do oponente
     * @param feitico true para excluir da lista de lacaios válidos os que são
     * imunes a feitiços. false adiciona todos.
     * @return código do alvo selecionado. id_long do card ou Param.HEROI ou
     * Param.OPONENTE ou EscolherMesa.CANCEL para alvo não selecionado.
     */
    public static long main(long escolhido, PartidaView partidaView, Card excluir, String titulo, Heroi heroi, Heroi oponente, List<Card> lacaiosHeroi, List<Card> lacaiosOponente, boolean feitico) {
        if (escolhido != 1L) {
            return escolhido;
        }
        alvos = 0;
        ALVOS_VALIDOS.clear();
        EscolherMesa.excluir = excluir;
        EscolherMesa.titulo = titulo;
        EscolherMesa.heroi = heroi;
        EscolherMesa.oponente = oponente;
        EscolherMesa.lacaiosHeroi = lacaiosHeroi;
        EscolherMesa.lacaiosOponente = lacaiosOponente;
        EscolherMesa.feitico = feitico;
        if (partidaView != null) {
            partidaView.ESPERANDO_ALVO = true;
            partidaView.setAlvoSelecionado(AGUARDANDO_ALVO);
        }
        selecionado = CANCEL;
        getAlvosValidos();
        escolher(partidaView);
        return selecionado;
    }

    /**
     * Altera o ícone do mouse para aguardar o jogador selecionar um personagem
     * na janela principal e verifica se o personagem selecionado é válido.
     *
     * @param excluir excluir o lacaio que originou o grito de guerra
     * @param titulo mensagem exibida
     * @param partida objeto com os heróis e lacaios da partida atual
     * @param feitico true para excluir da lista de lacaios válidos os que são
     * imunes a feitiços. false adiciona todos.
     * @return código do alvo selecionado. id_long do card ou Param.HEROI ou
     * Param.OPONENTE ou EscolherMesa.CANCEL para alvo não selecionado.
     */
    public static long main(long escolhido, Card excluir, String titulo, Partida partida, boolean feitico) {
        return EscolherMesa.main(escolhido, partida.getPartidaView(), excluir, titulo, partida.getHero(), partida.getOponente(), partida.getHero().getMesa(), partida.getOponente().getMesa(), feitico);
    }

    private static void adicionarAlvosValidos(List<Card> lacaios) {
        if (lacaios != null) {
            lacaios.stream().filter((card) -> (excluir == null || !card.equals(excluir)))
                    .filter((card) -> (!card.isFurtivo() && (!feitico || !card.isImuneAlvo())))
                    .forEach((card) -> {
                        alvos++;
                        ALVOS_VALIDOS.add(card.id_long);
                    });
        }
    }

    public static long getSelecionado() {
        return selecionado;
    }

    private static void getAlvosValidos() {
        if (heroi != null) {
            alvos++;
            ALVOS_VALIDOS.add(Param.HEROI);
        }
        if (oponente != null) {
            alvos++;
            ALVOS_VALIDOS.add(Param.OPONENTE);
        }
        adicionarAlvosValidos(lacaiosHeroi);
        adicionarAlvosValidos(lacaiosOponente);
    }

    private static void escolher(PartidaView partidaView) {
        if (alvos == 0 || InteligenciaArtificial.gritoDeGuerra >= ALVOS_VALIDOS.size()) {
            partidaView.ESPERANDO_ALVO = false;
            selecionado = CANCEL;
        } else {
            selecionado = ALVOS_VALIDOS.get(InteligenciaArtificial.gritoDeGuerra);
            partidaView.ESPERANDO_ALVO = false;
        }
    }
}