package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import com.limagiran.hearthstone.partida.control.Efeito;
import com.limagiran.hearthstone.partida.control.Pacote;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstone.util.Param;
import java.io.Serializable;
import java.util.List;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class EfeitoProgramado implements Serializable {

    public static final String CONCEDER_ATAQUE_HEROI_UM_TURNO = "cah1t";
    public static final String CONCEDER_ATAQUE_LACAIO_UM_TURNO = "cal1t";
    public static final String CONCEDER_MANA_HEROI_UM_TURNO = "cmh1t";
    public static final String CONCEDER_IMUNIDADE_LACAIO_UM_TURNO = "cil1t";
    public static final String CONCEDER_MECANICA_LACAIO_UM_TURNO = "cml1t";
    public static final String LACAIO_MORRER_INICIO_TURNO = "lmit";
    public static final String LACAIO_MORRER_FINAL_TURNO = "lmft";
    public static final String CONCEDER_FURTIV_LACAIO_PROX_TURNO = "cflpt";
    public static final String ADD_MAO_HEROI_FINAL_TURNO = "amhft";
    public static final String CONCEDER_IMUNIDADE_HEROI_UM_TURNO = "cih1t";
    public static final String ASSUMIR_CONTROLE_ATE_FINAL_TURNO = "acaft";
    public static final String CLONAR_LACAIO_FIM_TURNO = "clft";
    private final Partida partida;

    public EfeitoProgramado(Partida partida) {
        this.partida = partida;
    }

    /**
     * Ativa um efeito programado no jogo
     *
     * @param efeito objeto Efeito com ps parâmetros do efeito a ser ativado
     */
    public void ativar(Efeito efeito) {
        if (partida.isVezHeroi()) {
            if (partida != null) {
                switch (efeito.getId()) {
                    case CONCEDER_ATAQUE_HEROI_UM_TURNO:
                        partida.getHero().addAttack(efeito.getParamInt(Param.VALUE));
                        break;
                    case CONCEDER_ATAQUE_LACAIO_UM_TURNO:
                        partida.findCardByIDLong(efeito.getParamLong(Param.CARD_ID_LONG))
                                .addAtaque(efeito.getParamInt(Param.VALUE));
                        break;
                    case CONCEDER_MANA_HEROI_UM_TURNO:
                        if (partida.getHero().getMana() == 10 && partida.getHero().getManaDisponivel() < 10) {
                            int dif = 10 - partida.getHero().getManaDisponivel();
                            partida.getHero().delManaUtilizada(efeito.getParamInt(Param.VALUE));
                            efeito.getPacote().set(Param.VALUE, dif);
                            efeito.getPacote().set(Param.ADD, false);
                        } else {
                            int dif = 10 - partida.getHero().getMana();
                            partida.getHero().addMana(efeito.getParamInt(Param.VALUE));
                            efeito.getPacote().set(Param.VALUE,
                                    efeito.getParamInt(Param.VALUE) > dif ? dif : efeito.getParamInt(Param.VALUE));
                            efeito.getPacote().set(Param.ADD, true);
                        }
                        break;
                    case CONCEDER_IMUNIDADE_LACAIO_UM_TURNO:
                        partida.findCardByIDLong(efeito.getParamLong(Param.CARD_ID_LONG)).setImune(true);
                        break;
                    case CONCEDER_MECANICA_LACAIO_UM_TURNO:
                        partida.findCardByIDLong(efeito.getParamLong(Param.CARD_ID_LONG))
                                .addMechanics(efeito.getParamString(Param.VALUE));
                        break;
                    case CONCEDER_FURTIV_LACAIO_PROX_TURNO:
                        partida.findCardByIDLong(efeito.getParamLong(Param.CARD_ID_LONG))
                                .addMechanics(Values.FURTIVIDADE);
                        break;
                    case CONCEDER_IMUNIDADE_HEROI_UM_TURNO:
                        int heroInt = efeito.getParamInt(Param.VALUE);
                        (heroInt == partida.getPlayer() ? partida.getHero() : partida.getOponente()).setImune(true);
                        break;
                    case ASSUMIR_CONTROLE_ATE_FINAL_TURNO:
                        partida.roubar(efeito.getParamLong(Param.CARD_ID_LONG));
                        break;
                }
            }
        }
    }

    /**
     * Verifica se é hora do efeito ser ativado/desativado e excluído
     *
     * @param momento Efeito.INICIO_TURNO ou Efeito.FINAL_TURNO
     */
    public void processar(int momento) {
        if (partida != null) {
            List<Efeito> efeitos = partida.getEfeitos();
            for (int i = 0; i < efeitos.size(); i++) {
                Efeito efeito = efeitos.get(i);
                if (partida.isVezHeroi()) {
                    switch (efeito.getId()) {
                        case CONCEDER_ATAQUE_HEROI_UM_TURNO:
                            if (efeito.getMomento() == Efeito.FINAL_TURNO && efeito.getSleep() == 0) {
                                partida.getHero().delAttack(efeito.getParamInt(Param.VALUE));
                            }
                            break;
                        case CONCEDER_ATAQUE_LACAIO_UM_TURNO:
                            if (efeito.getMomento() == Efeito.FINAL_TURNO && efeito.getSleep() == 0) {
                                Card alvo = partida.findCardByIDLong(efeito.getParamLong(Param.CARD_ID_LONG));
                                if (alvo.getDono().getMesa().contains(alvo)
                                        && efeito.getParamLong(Param.TIME) == alvo.getTime()) {
                                    alvo.delAtaque(efeito.getParamInt(Param.VALUE));
                                }
                            }
                            break;
                        case CONCEDER_MANA_HEROI_UM_TURNO:
                            if (efeito.getPacote().getParamBoolean(Param.ADD) && efeito.getMomento() == Efeito.FINAL_TURNO && efeito.getSleep() == 0) {
                                partida.getHero().delMana(efeito.getParamInt(Param.VALUE));
                            }
                            break;
                        case CONCEDER_IMUNIDADE_LACAIO_UM_TURNO:
                            if (efeito.getMomento() == Efeito.FINAL_TURNO && efeito.getSleep() == 0) {
                                Card alvo = partida.findCardByIDLong(efeito.getParamLong(Param.CARD_ID_LONG));
                                if (alvo.getDono().getMesa().contains(alvo)
                                        && efeito.getParamLong(Param.TIME) == alvo.getTime()) {
                                    alvo.setImune(false);
                                }
                            }
                            break;
                        case CONCEDER_MECANICA_LACAIO_UM_TURNO:
                            if (efeito.getMomento() == Efeito.FINAL_TURNO && efeito.getSleep() == 0) {
                                Card alvo = partida.findCardByIDLong(efeito.getParamLong(Param.CARD_ID_LONG));
                                if (alvo.getDono().getMesa().contains(alvo)
                                        && efeito.getParamLong(Param.TIME) == alvo.getTime()) {
                                    alvo.delMechanics(efeito.getParamString(Param.VALUE));
                                }
                            }
                            break;
                        case LACAIO_MORRER_INICIO_TURNO:
                            if (efeito.getMomento() == Efeito.INICIO_TURNO && efeito.getSleep() == 0) {
                                Card alvo = partida.findCardByIDLong(efeito.getParamLong(Param.CARD_ID_LONG));
                                if (alvo.getDono().getMesa().contains(alvo)
                                        && efeito.getParamLong(Param.TIME) == alvo.getTime()) {
                                    alvo.morreu();
                                }
                            }
                            break;
                        case CONCEDER_FURTIV_LACAIO_PROX_TURNO:
                            if (efeito.getMomento() == Efeito.INICIO_TURNO && efeito.getSleep() == 0) {
                                Card alvo = partida.findCardByIDLong(efeito.getParamLong(Param.CARD_ID_LONG));
                                if (alvo.getDono().getMesa().contains(alvo)
                                        && efeito.getParamLong(Param.TIME) == alvo.getTime()) {
                                    alvo.delMechanics(Values.FURTIVIDADE);
                                }
                            }
                            break;
                        case ADD_MAO_HEROI_FINAL_TURNO:
                            if (efeito.getMomento() == Efeito.FINAL_TURNO && efeito.getSleep() == 0) {
                                partida.getHero().addMao(partida.criarCard(efeito.getParamString(Param.VALUE), System.nanoTime()));
                            }
                            break;
                        case CONCEDER_IMUNIDADE_HEROI_UM_TURNO:
                            if (efeito.getMomento() == Efeito.FINAL_TURNO && efeito.getSleep() == 0) {
                                long heroLong = efeito.getParamLong(Param.VALUE);
                                (heroLong == partida.getHero().getHeroi() ? partida.getHero() : partida.getOponente()).setImune(false);
                            }
                            break;
                        case LACAIO_MORRER_FINAL_TURNO:
                            if (efeito.getMomento() == Efeito.FINAL_TURNO && efeito.getSleep() == 0) {
                                Card alvo = partida.findCardByIDLong(efeito.getParamLong(Param.CARD_ID_LONG));
                                if (alvo.getDono().getMesa().contains(alvo)
                                        && efeito.getParamLong(Param.TIME) == alvo.getTime()) {
                                    alvo.morreu();
                                }
                            }
                            break;
                        case ASSUMIR_CONTROLE_ATE_FINAL_TURNO:
                            if (efeito.getMomento() == Efeito.FINAL_TURNO && efeito.getSleep() == 0) {
                                Card alvo = partida.findCardByIDLong(efeito.getParamLong(Param.CARD_ID_LONG));
                                if (alvo.getDono().getMesa().contains(alvo)
                                        && efeito.getParamLong(Param.TIME) == alvo.getTime()) {
                                    Heroi origem = alvo.getDono();
                                    Heroi destino = alvo.getOponente();
                                    destino.addMesa(0, alvo);
                                    origem.delMesa(alvo);
                                }
                            }
                            break;
                        case CLONAR_LACAIO_FIM_TURNO:
                            if (efeito.getMomento() == Efeito.FINAL_TURNO && efeito.getSleep() == 0) {
                                Card alvo = partida.findCardByIDLong(efeito.getParamLong(Param.CARD_ID_LONG));
                                if (alvo.getDono().getMesa().contains(alvo)
                                        && efeito.getParamLong(Param.TIME) == alvo.getTime()) {
                                    alvo.getDono().evocar(alvo.getDono().getPosicaoNaMesa(alvo.id_long),
                                            partida.clonar(alvo.id_long, System.nanoTime()));
                                }
                            }
                            break;
                    }
                    if (efeito.getMomento() == momento) {
                        efeito.decrement();
                    }
                }
            }
            if (partida.isVezHeroi()) {
                partida.removeEfeitos();
            }
        }
    }

    /**
     * Concede ataque ao herói até o final do turno
     *
     * @param ataque valor do ataquue
     */
    public void concederAtaqueAoHeroiUmTurno(int ataque) {
        Pacote pack = new Pacote("");
        pack.set(Param.VALUE, ataque);
        partida.addEfeito(new Efeito(CONCEDER_ATAQUE_HEROI_UM_TURNO, Efeito.FINAL_TURNO, 0, pack));
    }

    /**
     * Adiciona um card à mão do heroi no final do turno
     *
     * @param id id do card
     */
    public void adicionarCardMaoHeroiFinalDoTurno(String id) {
        Pacote pack = new Pacote("");
        pack.set(Param.VALUE, id);
        partida.addEfeito(new Efeito(ADD_MAO_HEROI_FINAL_TURNO, Efeito.FINAL_TURNO, 0, pack));
    }

    /**
     * Concede mana ao herói até o final do turno
     *
     * @param mana quantidade de mana
     */
    public void concederManaAoHeroiUmTurno(int mana) {
        Pacote pack = new Pacote("");
        pack.set(Param.VALUE, mana);
        partida.addEfeito(new Efeito(CONCEDER_MANA_HEROI_UM_TURNO, Efeito.FINAL_TURNO, 0, pack));
    }

    /**
     * Concede imunidade a um lacaio até o final do turno
     *
     * @param id_long id_long do lacaio
     */
    public void concederImunidadeAUmLacaioUmTurno(long id_long) {
        Pacote pack = new Pacote("");
        pack.set(Param.CARD_ID_LONG, id_long);
        pack.set(Param.TIME, partida.findCardByIDLong(id_long).getTime());
        partida.addEfeito(new Efeito(CONCEDER_IMUNIDADE_LACAIO_UM_TURNO, Efeito.FINAL_TURNO, 0, pack));
    }

    /**
     * Concede imunidade ao herói até o final do turno
     *
     * @param heroi código do herói (zero ou um)
     */
    public void concederImunidadeHeroiUmTurno(int heroi) {
        Pacote pack = new Pacote("");
        pack.set(Param.VALUE, heroi);
        partida.addEfeito(new Efeito(CONCEDER_IMUNIDADE_HEROI_UM_TURNO, Efeito.FINAL_TURNO, 0, pack));
    }

    /**
     * Concede uma mecânica a um lacaio até o final do turno
     *
     * @param mecanica mecânica a ser concedida
     * @param id_long id_long do card
     */
    public void concederMecanicaLacaioUmTurno(String mecanica, long id_long) {
        Pacote pack = new Pacote("");
        pack.set(Param.VALUE, mecanica);
        pack.set(Param.CARD_ID_LONG, id_long);
        pack.set(Param.TIME, partida.findCardByIDLong(id_long).getTime());
        partida.addEfeito(new Efeito(CONCEDER_MECANICA_LACAIO_UM_TURNO, Efeito.FINAL_TURNO, 0, pack));
    }

    /**
     * Concede ataque a um lacaio até o final do turno
     *
     * @param id_long id_long do lacaio
     * @param ataque ataque concedido
     */
    public void concederAtaqueAUmLacaioUmTurno(long id_long, int ataque) {
        Pacote pack = new Pacote("");
        pack.set(Param.VALUE, ataque);
        pack.set(Param.CARD_ID_LONG, id_long);
        pack.set(Param.TIME, partida.findCardByIDLong(id_long).getTime());
        partida.addEfeito(new Efeito(CONCEDER_ATAQUE_LACAIO_UM_TURNO, Efeito.FINAL_TURNO, 0, pack));
    }

    /**
     * Destrói um lacaio no início de um turno
     *
     * @param id_long id_long do lacaio a ser destruído
     * @param sleep quuantidade de inícios de turno a esperar a ativação
     */
    public void lacaioMorrerInicioTurno(long id_long, int sleep) {
        Pacote pack = new Pacote("");
        pack.set(Param.CARD_ID_LONG, id_long);
        pack.set(Param.TIME, partida.findCardByIDLong(id_long).getTime());
        partida.addEfeito(new Efeito(LACAIO_MORRER_INICIO_TURNO, Efeito.INICIO_TURNO, sleep, pack));
    }

    /**
     * Concede furtividade a um lacaio até o próximo turno
     *
     * @param id_long id_long do lacaio
     */
    public void concederFurtividadeAUmLacaioAteProximoTurno(long id_long) {
        Pacote pack = new Pacote("");
        pack.set(Param.CARD_ID_LONG, id_long);
        pack.set(Param.TIME, partida.findCardByIDLong(id_long).getTime());
        partida.addEfeito(new Efeito(CONCEDER_FURTIV_LACAIO_PROX_TURNO, Efeito.INICIO_TURNO, 1, pack));
    }

    /**
     * Destrói um lacaio no final do turno
     *
     * @param id_long id_long do lacaio
     * @param sleep quantidade de finais de turno a esperar a ativação
     */
    public void lacaioMorrerFimTurno(long id_long, int sleep) {
        Pacote pack = new Pacote("");
        pack.set(Param.CARD_ID_LONG, id_long);
        pack.set(Param.TIME, partida.findCardByIDLong(id_long).getTime());
        partida.addEfeito(new Efeito(LACAIO_MORRER_FINAL_TURNO, Efeito.FINAL_TURNO, sleep, pack));
    }

    /**
     * Clona um lacaio no final do turno
     *
     * @param id_long id_long do lacaio a ser clonado
     */
    public void clonarLacaioFimTurno(long id_long) {
        Pacote pack = new Pacote("");
        pack.set(Param.CARD_ID_LONG, id_long);
        pack.set(Param.TIME, partida.findCardByIDLong(id_long).getTime());
        partida.addEfeito(new Efeito(CLONAR_LACAIO_FIM_TURNO, Efeito.FINAL_TURNO, 0, pack));
    }

    /**
     * Assume o controle de um lacaio até o final do turno
     *
     * @param id_long id_long do lacaio
     */
    public void assumirControleAteFimDoTurno(long id_long) {
        Pacote pack = new Pacote("");
        pack.set(Param.CARD_ID_LONG, id_long);
        pack.set(Param.TIME, partida.findCardByIDLong(id_long).getTime());
        partida.addEfeito(new Efeito(ASSUMIR_CONTROLE_ATE_FINAL_TURNO, Efeito.FINAL_TURNO, 0, pack));
    }
}