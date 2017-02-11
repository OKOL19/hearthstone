package com.limagiran.hearthstoneia.ia;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstone.util.RW;
import com.limagiran.hearthstone.util.RWObj;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstoneia.server.GameCliente;
import static com.limagiran.hearthstone.util.Param.*;
import static com.limagiran.hearthstone.util.Values.*;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinicius
 */
public class InteligenciaArtificial {

    public static int gritoDeGuerra = 0;

    private static long melhorJogada = 0;

    private static String clicado;
    private static String solto;
    private static List status = new ArrayList<>();

    public static void realizarMelhorJogada(Partida partida) {
        while (true) {
            GameCliente.setIntArtProcessando(true);
            try {
                gerarMelhorJogada(RWObj.clone(partida, Partida.class));
            } catch (Exception e) {
                RW.log(e);
            }
            GameCliente.setIntArtProcessando(false);
            partida.gerarJogada(1L, clicado, solto);
            if (partida.getVencedor() != -1) {
                break;
            }
            if (melhorJogada == Long.MIN_VALUE) {
                partida.passarTurno();
                break;
            }
        }
    }

    private static void gerarMelhorJogada(Partida partida) {
        clicado = TO_STRING_HEROI + HEROI;
        solto = TO_STRING_HEROI + HEROI;
        melhorJogada = Long.MIN_VALUE;
        //poder heroico em todos os lacaios na mesa
        for (Card card : partida.getMesa()) {
            calcularJogada(partida, TO_STRING_PODER_HEROICO + HEROI,
                    TO_STRING_LACAIO + card.id_long);
        }
        //poder heroico no herói
        calcularJogada(partida, TO_STRING_PODER_HEROICO + HEROI,
                TO_STRING_HEROI + HEROI);
        //poder heroico no oponente
        calcularJogada(partida, TO_STRING_PODER_HEROICO + HEROI,
                TO_STRING_HEROI + OPONENTE);
        //poder heroico apenas (+2 de escudo, por exemplo)
        calcularJogada(partida, TO_STRING_PODER_HEROICO + HEROI,
                TO_STRING_PODER_HEROICO + HEROI);
        //herói atacar se tiver ataque
        if (partida.getHero().getAttack() > 0) {
            //herói atacar os lacaios da mesa
            for (Card inimigo : partida.getOponente().getMesa()) {
                calcularJogada(partida, TO_STRING_HEROI + HEROI,
                        TO_STRING_LACAIO + inimigo.id_long);
            }
            //herói atacar o oponente
            calcularJogada(partida, TO_STRING_HEROI + HEROI,
                    TO_STRING_HEROI + OPONENTE);
        }
        //jogar card
        for (Card mao : partida.getHero().getMao()) {
            //jogar card em todas as posições da mesa
            for (Card mesa : partida.getMesa()) {
                //se o lacaio não tiver grito de guerra, gera o evento apenas uma vez
                if (!mao.isGritoDeGuerra()) {
                    calcularJogada(partida, TO_STRING_MAO + mao.id_long,
                            TO_STRING_LACAIO + mesa.id_long);
                } else {
                    //se o lacaio tiver grito de guerra, gera o evento selecionando
                    //como alvo do grito de guerra todos os alvos possíveis
                    for (int i = 0; i < partida.getMesa().size() + 2; i++) {
                        gritoDeGuerra = i;
                        calcularJogada(partida, TO_STRING_MAO + mao.id_long,
                                TO_STRING_LACAIO + mesa.id_long);
                    }
                }
            }
            //jogar o card no final da mesa
            calcularJogada(partida, TO_STRING_MAO + mao.id_long,
                    TO_STRING_MESA + HEROI);
        }
        //atacar com lacaio
        for (Card lacaio : partida.getHero().getMesa()) {
            //atacar cada um dos lacaios inimigos
            for (Card inimigo : partida.getOponente().getMesa()) {
                calcularJogada(partida, TO_STRING_LACAIO + lacaio.id_long,
                        TO_STRING_LACAIO + inimigo.id_long);
            }
            //atacar o oponente
            calcularJogada(partida, TO_STRING_LACAIO + lacaio.id_long,
                    TO_STRING_HEROI + OPONENTE);
        }
    }

    private static void calcularJogada(Partida p, String atacante, String alvo) {
        Partida ia = RWObj.clone(p, Partida.class);
        status = getStatus(ia);
        ia.gerarJogada(1L, atacante, alvo);
        long jogadaAtual = getValorPartida(ia);
        if (jogadaAtual > melhorJogada && ia.getHero().getHealth() > 0 && statusAlterado(ia)) {
            melhorJogada = jogadaAtual;
            clicado = atacante;
            solto = alvo;
        }
    }

    public static List getStatus(Partida p) {
        List retorno = new ArrayList<>();
        retorno.add(getValorPartida(p));
        retorno.add(p.getHero().getPoderHeroico().isAtivado());
        retorno.add(p.getHero().getMao().size());
        retorno.add(p.getHero().getMesa().size());
        retorno.add(p.getHero().getMorto().size());
        retorno.add(p.getHero().getDeck().size());
        retorno.add(p.getHero().getSegredo().size());
        retorno.add(p.getOponente().getSegredo().size());
        retorno.add(p.getHero().getHealth());
        retorno.add(p.getOponente().getHealth());
        retorno.add(p.getHero().getShield());
        retorno.add(p.getOponente().getShield());
        return retorno;
    }

    private static boolean statusAlterado(Partida p) {
        List statusAtual = getStatus(p);
        for (int i = 0; i < status.size(); i++) {
            if (i < statusAtual.size() && !status.get(i).equals(statusAtual.get(i))) {
                return true;
            }
        }
        return false;
    }

    private static long getValorPartida(Partida partida) {
        return partida.getHero().getHealth() <= 0
                ? Long.MIN_VALUE
                : partida.getOponente().getHealth() <= 0
                        ? Long.MAX_VALUE
                        : getValorHeroi(partida.getHero()) - getValorHeroi(partida.getOponente());
    }

    private static int getValorHeroi(Heroi h) {
        int retorno = 0;
        List<Card> m = h.getMesa();
        retorno += m.stream().map((i) -> i.getAtaque()).reduce(retorno, Integer::sum) * 40;
        retorno += m.stream().map((i) -> i.getVida()).reduce(retorno, Integer::sum) * 50;
        retorno += m.stream().filter((c) -> c.isProvocar()).count() * 49;
        retorno += m.stream().filter((c) -> c.isFuriaDosVentos()).count() * 49;
        retorno += m.stream().filter((c) -> c.isEscudoDivino()).count() * 49;
        retorno += m.stream().filter((c) -> c.isCongela()).count() * 20;
        retorno += m.stream().filter((c) -> c.isUltimoSuspiro()).count() * 20;
        retorno += m.stream().filter((c) -> c.isVeneno()).count() * 60;
        retorno -= m.stream().filter((c) -> c.isSilenciado()).count() * 50;
        retorno += h.getMao().size() * 101;
        retorno += h.getSegredo().size() * 75;
        retorno += h.getAttack() * 25;
        retorno += h.getHealth() * 50;
        retorno += h.getArma() != null ? retorno += h.getArma().getDurability() * 50 : 0;
        return retorno;
    }
}