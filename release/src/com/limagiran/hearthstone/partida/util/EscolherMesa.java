package com.limagiran.hearthstone.partida.util;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Utils;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.partida.control.Partida;
import com.limagiran.hearthstone.partida.view.PartidaView;
import com.limagiran.hearthstone.util.MouseCursor;
import com.limagiran.hearthstone.util.Param;

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
     * @param escolhido
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
        if(escolhido != 1L){
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
            partidaView.disposeAllPopUps();
        }
        selecionado = CANCEL;
        getAlvosValidos();
        if (partidaView != null) {
            esperarUsuarioClicar(partidaView);
        } else {
            selecionado = CANCEL;
        }

        return selecionado;
    }

    /**
     * Altera o ícone do mouse para aguardar o jogador selecionar um personagem
     * na janela principal e verifica se o personagem selecionado é válido.
     *
     * @param escolhido
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

    private static void esperarUsuarioClicar(PartidaView partidaView) {
        if (alvos == 0) {
            partidaView.ESPERANDO_ALVO = false;
            selecionado = CANCEL;
        } else {
            partidaView.setCursor(MouseCursor.ALVO);
            while (partidaView.ESPERANDO_ALVO) {
                selecionado = partidaView.getAlvoSelecionado();
                if (selecionado != AGUARDANDO_ALVO) {
                    if (selecionado == Param.ALVO_CANCEL || ALVOS_VALIDOS.contains(selecionado)) {
                        break;
                    } else {
                        partidaView.setAlvoSelecionado(AGUARDANDO_ALVO);
                        partidaView.showPopUp("Alvo inválido!");
                    }
                }
                Utils.sleep(50);
            }
            partidaView.ESPERANDO_ALVO = false;
            partidaView.setCursor(MouseCursor.DEFAULT);
            selecionado = selecionado == Param.ALVO_CANCEL ? CANCEL : selecionado;
        }
    }
}