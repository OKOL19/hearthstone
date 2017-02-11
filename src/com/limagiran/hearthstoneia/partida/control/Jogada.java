package com.limagiran.hearthstoneia.partida.control;

import com.limagiran.hearthstone.partida.control.Efeito;
import com.limagiran.hearthstone.partida.control.Pacote;
import com.limagiran.hearthstone.util.Param;
import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.evento.AtacarLacaiosAdjacentes;
import com.limagiran.hearthstoneia.evento.Aura;
import com.limagiran.hearthstoneia.evento.FazerAoAtacar;
import com.limagiran.hearthstoneia.evento.FimDoTurno;
import com.limagiran.hearthstoneia.evento.LacaioMorreu;
import com.limagiran.hearthstoneia.evento.PersonagemAtacando;
import com.limagiran.hearthstoneia.evento.PersonagemFoiAtacado;
import com.limagiran.hearthstoneia.evento.PersonagemSendoAtacado;
import com.limagiran.hearthstoneia.evento.UtilizarPoderHeroico;
import com.limagiran.hearthstoneia.evento.PoderHeroicoUtilizado;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import com.limagiran.hearthstoneia.server.GameCliente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Vinicius Silva
 */
public class Jogada implements Serializable {

    public static final int JOGAR_CARTA = 0;
    public static final int PODER_HEROICO = 1;
    public static final int HEROI_ATACA_HEROI = 2;
    public static final int HEROI_ATACA_LACAIO = 3;
    public static final int LACAIO_ATACA_HEROI = 4;
    public static final int LACAIO_ATACA_LACAIO = 5;
    public static final int PASSAR_TURNO = 6;
    public Pacote pack = null;
    public List<Card> ultimoSuspiro = new ArrayList<>();
    public List<Integer> ultimoSuspiroIndex = new ArrayList<>();
    public final Partida partida;

    public Jogada(Partida partida) {
        this.partida = partida;
    }

    public void jogar(Pacote pacote) {
        boolean passarTurno = false;
        pack = pacote;
        if (partida != null) {
            switch (pack.getParamInt(Param.TIPO_JOGADA)) {
                case JOGAR_CARTA:
                    jogarCarta();
                    break;
                case PODER_HEROICO:
                    poderHeroico();
                    break;
                case HEROI_ATACA_HEROI:
                    heroiVsHeroi();
                    break;
                case HEROI_ATACA_LACAIO:
                    heroiVsLacaio();
                    break;
                case LACAIO_ATACA_HEROI:
                    lacaioVsHeroi();
                    break;
                case LACAIO_ATACA_LACAIO:
                    lacaioVsLacaio();
                    break;
                case PASSAR_TURNO:
                    passarTurno();
                    passarTurno = true;
                    break;
            }
            if (!passarTurno) {
                ultimoSuspiro();
                Aura.refresh(partida);
            }
            GameCliente.enviar(Param.REFRESH, Param.VALUE);
        }
    }

    private void lacaioVsLacaio() {
        long alvo = pack.getParamLong(Param.ALVO);
        long atacante = pack.getParamLong(Param.ATACANTE);
        FazerAoAtacar.processar(partida.findCardByIDLong(atacante));
        PersonagemAtacando.processar(null, partida.findCardByIDLong(atacante));
        PersonagemSendoAtacado.processar(null, partida.findCardByIDLong(alvo));
        gerarAtaque(atacante, alvo);
    }

    private void lacaioVsHeroi() {
        long alvo = pack.getParamLong(Param.ALVO);
        long atacante = pack.getParamLong(Param.ATACANTE);
        FazerAoAtacar.processar(partida.findCardByIDLong(atacante));
        PersonagemAtacando.processar(null, partida.findCardByIDLong(atacante));
        PersonagemSendoAtacado.processar(alvo == Param.HEROI ? partida.getHero() : partida.getOponente(), null);
        gerarAtaque(atacante, alvo);
    }

    private void heroiVsLacaio() {
        long alvo = pack.getParamLong(Param.ALVO);
        PersonagemAtacando.processar(partida.getHero(), null);
        PersonagemSendoAtacado.processar(null, partida.findCardByIDLong(alvo));
        gerarAtaque(Param.HEROI, alvo);
    }

    private void jogarCarta() {
        GameCliente.setPodeEnviar(false);
        partida.getHero().jogarCarta(
                pack.getParamLong(Param.CARD_ID_LONG),
                pack.getParamInt(Param.POSICAO_CARTA_JOGADA));

    }

    private void heroiVsHeroi() {
        long alvo = pack.getParamLong(Param.ALVO);
        PersonagemAtacando.processar(partida.getHero(), null);
        PersonagemSendoAtacado.processar(partida.getOponente(), null);
        gerarAtaque(Param.HEROI, alvo);
    }

    private void passarTurno() {
        partida.addHistorico(partida.getHero().getNome() + " encerrou o turno.");
        FimDoTurno.processar(partida);
        partida.getEfeitoProgramado().processar(Efeito.FINAL_TURNO);
        ultimoSuspiro();
        partida.passarVez();
    }

    private void poderHeroico() {
        GameCliente.setPodeEnviar(false);
        GameCliente.enviar(Param.ANIMACAO_CARD_JOGADO, partida.getHero().getPoderHeroico().getTipo());
        if (UtilizarPoderHeroico.poderHeroico(partida, pack)) {
            GameCliente.setPodeEnviar(true);
            Heroi hero = partida.getHero();
            hero.addManaUtilizada(hero.getPoderHeroico().getCusto(true));
            hero.poderHeroicoUtilizado();
            PoderHeroicoUtilizado.processar(partida, pack);
        } else {
            GameCliente.clearPacotes();
            GameCliente.setPodeEnviar(true);
        }
    }

    private void gerarAtaque(long atacante, long alvo) {
        try {
            alvo = pack.getParamLong(Param.ALVO);
            if (alvo != Param.ALVO_CANCEL) {
                while (alvo != pack.getParamLong(Param.ALVO)) {
                    alvo = pack.getParamLong(Param.ALVO);
                }
                Pacote pacote = new Pacote(Param.ANIMACAO_EVENTO_GERADO);
                pacote.set(Param.ATACANTE, atacante);
                pacote.set(Param.ALVO, alvo);
                GameCliente.enviar(pacote);
                if (atacante == Param.HEROI) {
                    heroiAtaca(partida.getHero(), alvo);
                } else {
                    LacaioAtaca(atacante, alvo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gera um evento de ataque do heroi
     *
     * @param attack Heroi atacando
     * @param alvo alvo do ataque
     */
    private void heroiAtaca(Heroi attack, long alvo) {
        //DS1_188 - Arco Longo do Gladiador (Seu herói fica Imune enquanto ataca)
        if (attack.getArma() != null && attack.getArma().getId().equals("DS1_188")) {
            attack.getAura().setImune(true);
        }
        attack.addAtaquesRealizados(1);
        if (alvo == Param.OPONENTE) {
            partida.addHistorico(attack.getNome() + " atacou " + partida.getOponente().getNome() + ".");
            partida.getOponente().delHealth(attack.getAttack());
            PersonagemFoiAtacado.processar(partida.getOponente());
        } else {
            Card damage = partida.findCardByIDLong(alvo);
            int attackDamage = damage.getAtaque();
            partida.addHistorico(attack.getNome() + " atacou " + damage.getName() + ".");
            damage.delVida(attack.getAttack());
            attack.delHealth(attackDamage);
            if (damage.getMechanics().contains("Freeze")) {
                attack.setFreeze(true);
            }
        }
        attack.atacou(alvo);
    }

    /**
     * Gera um evento de ataque de um lacaio
     *
     * @param atacante código time do lacaio que vai atacar
     * @param alvo alvo do ataque
     */
    private void LacaioAtaca(long atacante, long alvo) {
        Card attack = partida.findCardByIDLong(atacante);
        if (attack.getVida() > 0) {
            boolean freeze = attack.isCongela();
            boolean veneno = attack.isVeneno();
            attack.atacou();
            if (alvo == Param.OPONENTE) {
                partida.addHistorico(attack.getName() + " atacou " + partida.getOponente().getNome() + ".");
                int vidaAnterior = partida.getOponente().getHealth();
                partida.getOponente().delHealth(attack.getAtaque());
                //Senhora da Dor (Sempre que este lacaio causar dano, restaure a mesma quantidade de Vida do seu herói)
                gvg_018(attack, vidaAnterior - partida.getOponente().getHealth());
                if (freeze) {
                    partida.getOponente().setFreeze(true);
                }
                PersonagemFoiAtacado.processar(partida.getOponente());
            } else if (alvo == Param.HEROI) {
                partida.addHistorico(attack.getName() + " atacou " + partida.getHero().getNome() + ".");
                int vidaAnterior = partida.getHero().getHealth();
                partida.getHero().delHealth(attack.getAtaque());
                //Senhora da Dor (Sempre que este lacaio causar dano, restaure a mesma quantidade de Vida do seu herói)
                gvg_018(attack, vidaAnterior - partida.getHero().getHealth());
                if (freeze) {
                    partida.getOponente().setFreeze(true);
                }
                PersonagemFoiAtacado.processar(partida.getHero());
            } else {
                Card damage = partida.findCardByIDLong(alvo);
                int attackDamage = damage.getAtaque();
                partida.addHistorico(attack.getName() + " atacou " + damage.getName() + ".");
                int vidaAnterior = damage.getVida();
                if (!AtacarLacaiosAdjacentes.processar(attack, damage)) {
                    damage.delVida(attack.getAtaque());
                }
                //Senhora da Dor (Sempre que este lacaio causar dano, restaure a mesma quantidade de Vida do seu herói)
                gvg_018(attack, vidaAnterior - damage.getVida());
                attack.delVida(attackDamage);
                if (damage.getVida() > 0) {
                    if (veneno) {
                        damage.envenenado();
                    } else if (freeze) {
                        damage.setFreeze(true);
                    }
                }
                if (attack.getVida() > 0) {
                    if (damage.isVeneno()) {
                        attack.envenenado();
                    } else if (damage.isCongela()) {
                        attack.setFreeze(true);
                    }
                }
            }
        }
    }

    /**
     * Senhora da Dor (Sempre que este lacaio causar dano, restaure a mesma
     * quantidade de Vida do seu herói)
     *
     * @param dano
     */
    private static void gvg_018(Card attack, int dano) {
        if (attack.getId().equals("GVG_018") && !attack.isSilenciado() && dano > 0) {
            attack.getDono().addHealth(dano);
        }
    }

    public void ultimoSuspiro() {
        for (int i = 0; i < ultimoSuspiro.size(); i++) {
            try {
                int index = ultimoSuspiroIndex.size() > i ? ultimoSuspiroIndex.get(i) : 0;
                index = index >= 0 ? index : 0;
                ultimoSuspiro.get(i).ultimoSuspiro(index);
                LacaioMorreu.processar(ultimoSuspiro.get(i), ultimoSuspiro);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ultimoSuspiro = new ArrayList<>();
        ultimoSuspiroIndex = new ArrayList<>();
    }

    public void ultimoSuspiro(int index, Card card) {
        if (partida != null && partida.isVezHeroi()) {
            ultimoSuspiro.add(card);
            ultimoSuspiroIndex.add(index);
        }
    }
}