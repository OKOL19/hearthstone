package com.limagiran.hearthstoneia.partida.control;

import com.google.gson.Gson;
import com.limagiran.hearthstone.partida.control.Pacote;
import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstone.evento.AlterarCusto;
import com.limagiran.hearthstoneia.evento.Aura;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import com.limagiran.hearthstone.util.JsonUtils;
import com.limagiran.hearthstone.partida.control.Efeito;
import com.limagiran.hearthstoneia.server.GameCliente;
import com.limagiran.hearthstone.util.Param;

/**
 *
 * @author Vinicius Silva
 */
public class Sincronizar {

    /**
     * Recebe um pacote e atualiza as informações da partida atual
     *
     * @param partida
     * @param pacote Pacote
     */
    public static void sincronizar(Partida partida, Pacote pacote) {
        Heroi hero = partida.getHero();
        Heroi oponente = partida.getOponente();
        try {
            if (!GameCliente.isPartidaEncerrada() && pacote != null) {
                Card card = partida.findCardByIDLong(pacote.getParamLong(Param.CARD_ID_LONG));
                Card temp;
                int index;
                long nanoTime;
                switch (pacote.TIPO) {
                    case Param.OPONENTE_DESISTIU:
                        GameCliente.oponenteDesistiu();
                        break;
                    case Param.REFRESH:
                        Aura.refresh(partida);
                        break;
                    case Param.ALTERAR_CUSTO_ADD:
                        partida.getAlterarCusto().add(
                                pacote.getParamInt(AlterarCusto.PLAYER),
                                pacote.getParamString(AlterarCusto.TYPE),
                                pacote.getParamString(AlterarCusto.RACE),
                                pacote.getParamInt(AlterarCusto.VALUE));
                        break;
                    case Param.ALTERAR_CUSTO_DEL:
                        partida.getAlterarCusto().remove(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.PARTIDA_SET_VEZ_DO_PLAYER:
                        partida.setVezDoPlayer(pacote.getParamInt(Param.VALUE));
                        partida.iniciarTurno();
                        break;
                    case Param.PARTIDA_SET_TURNO:
                        partida.setTurno(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.PARTIDA_SET_NOVO_ALVO:
                        partida.setNovo_alvo(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.PARTIDA_SET_VENCEDOR:
                        partida.encerrar(pacote.getParamLong(Param.VALUE));
                        break;
                    case Param.PARTIDA_SET_HISTORICO_ADD:
                        partida.addHistorico(pacote.getParamString(Param.VALUE));
                        break;
                    case Param.PARTIDA_SET_EFEITO_ADD:
                        Efeito efeito = new Gson().fromJson(pacote.getParamString(Param.VALUE), Efeito.class);
                        partida.getEfeitos().add(efeito);
                        break;
                    case Param.PARTIDA_SET_EFEITO_REMOVE:
                        partida.getEfeitos().remove(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.PARTIDA_CRIAR_CARD:
                        String id = pacote.getParamString(Param.VALUE);
                        long id_long = pacote.getParamLong(Param.CARD_ID_LONG);
                        partida.criarCard(id, id_long);
                        break;
                    case Param.PARTIDA_CLONAR:
                        long clonar = pacote.getParamLong(Param.VALUE);
                        nanoTime = pacote.getParamLong(Param.CARD_ID_LONG);
                        partida.clonar(clonar, nanoTime);
                        break;
                    case Param.PARTIDA_POLIMORFIA:
                        String newCard = pacote.getParamString(Param.VALUE);
                        nanoTime = pacote.getParamLong(Param.CARD_ID_LONG);
                        partida.polimorfia(nanoTime, newCard, false);
                        break;
                    case Param.PARTIDA_CARD_DEFAULT:
                        String cardDefault = pacote.getParamString(Param.VALUE);
                        nanoTime = pacote.getParamLong(Param.CARD_ID_LONG);
                        partida.cardDefault(nanoTime, cardDefault);
                        break;
                    case Param.PARTIDA_ROUBAR:
                        partida.roubar(pacote.getParamLong(Param.VALUE));
                        break;
                    case Param.PARTIDA_COPIA:
                        partida.tornarSeCopia(pacote.getParamLong(Param.CARD_ID_LONG),
                                pacote.getParamLong(Param.VALUE));
                        break;
                    case Param.CARD_SET_VIDA_MAXIMA:
                        card.setVidaMaxima(pacote.getParamInt(Param.VALUE), false);
                        break;
                    case Param.CARD_SET_VIDA_ORIGINAL:
                        card.setVidaOriginal(pacote.getParamInt(Param.VALUE), false);
                        break;
                    case Param.CARD_SET_VIDA_ATUAL:
                        card.setVida(pacote.getParamInt(Param.VALUE), true);
                        break;
                    case Param.CARD_SET_VIDA_EM_AREA:
                        card.setVidaEmArea(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.CARD_SET_ATAQUE:
                        card.setAtaque(pacote.getParamInt(Param.VALUE), false);
                        break;
                    case Param.CARD_SET_CUSTO:
                        card.setCusto(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.CARD_SET_DANO_MAGICO:
                        card.setDanoMagico(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.CARD_SET_ATAQUES_REALIZADOS:
                        card.setAtaquesRealizados(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.CARD_SET_ATAQUE_ENFURECER:
                        card.setAtaqueEnfurecer(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.CARD_SET_MECANICA_ADD:
                        card.addMechanics(pacote.getParamString(Param.VALUE));
                        break;
                    case Param.CARD_SET_MECANICA_REMOVE:
                        card.delMechanics(pacote.getParamString(Param.VALUE));
                        break;
                    case Param.CARD_SET_FREEZE:
                        card.setFreeze(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.CARD_SET_CUMPRIU_FREEZE:
                        card.setCumpriuFreeze(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.CARD_SET_SILENCIADO:
                        card.setSilenciado(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.CARD_SET_SLEEP:
                        card.setSleep(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.CARD_SET_TIME:
                        card.setTime(pacote.getParamLong(Param.VALUE));
                        break;
                    case Param.CARD_SET_DURABILIDADE:
                        card.setDurability(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.CARD_SET_TURNOS_NA_MESA:
                        card.setTurnosNaMesa(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.CARD_SET_FURIA_DOS_VENTOS_VISIBLE:
                        card.setFuriaDosVentosVisible(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.CARD_EVOCAR_ULTIMO_SUSPIRO_ADD:
                        card.addEvocarUltimoSuspiro(pacote.getParamString(Param.VALUE));
                        break;
                    case Param.CARD_FAZER_AO_ATACAR_ADD:
                        card.addFazerAoAtacar(new String[]{
                            pacote.getParamString(Param.CARD_FAZER_AO_ATACAR_HEROI),
                            pacote.getParamString(Param.CARD_FAZER_AO_ATACAR_TIPO),
                            pacote.getParamString(Param.CARD_FAZER_AO_ATACAR_VALUE)});
                        break;
                    case Param.CARD_ADD_DECK_ULTIMO_SUSPIRO_ADD:
                        card.addAddDeckUltimoSuspiro(pacote.getParamString(Param.VALUE));
                        break;
                    case Param.CARD_ADD_MAO_ULTIMO_SUSPIRO_ADD:
                        card.addAddMaoUltimoSuspiro(pacote.getParamString(Param.VALUE));
                        break;
                    case Param.HEROI_SET_TYPE:
                        hero.setType(pacote.getParamString(Param.VALUE));
                        break;
                    case Param.HEROI_SET_HEALTH:
                        hero.setHealth(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.HEROI_SET_VIDA_TOTAL:
                        hero.setVidaTotal(pacote.getParamInt(Param.VALUE), false);
                        break;
                    case Param.HEROI_SET_ATAQUE:
                        hero.setAttack(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.HEROI_SET_SHIELD:
                        hero.setShield(pacote.getParamInt(Param.VALUE), false);
                        break;
                    case Param.HEROI_SET_MANA:
                        hero.setMana(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.HEROI_SET_MANA_UTILIZADA:
                        hero.setManaUtilizada(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.HEROI_SET_MANA_BLOQ_ESTE_TURNO:
                        hero.setManaBloqueadaEsteTurno(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.HEROI_SET_MANA_BLOQ_TURNO_ANT:
                        hero.setManaBloqueadaTurnoAnterior(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.HEROI_SET_ARMA:
                        temp = pacote.getParamString(Param.VALUE).equals("null")
                                ? null
                                : partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        hero.setArma(temp);
                        break;
                    case Param.HEROI_SET_MESA_ADD:
                        index = pacote.getParamInt(Param.INDEX_CARTA);
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        temp.setTime(System.nanoTime());
                        hero.addMesa(index, temp);
                        break;
                    case Param.HEROI_SET_MESA_REMOVE:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        hero.delMesa(temp);
                        break;
                    case Param.HEROI_SET_MAO_ADD:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        hero.addMao(temp);
                        break;
                    case Param.HEROI_SET_MAO_INDEX_ADD:
                        int indice = pacote.getParamInt(Param.INDEX_CARTA);
                        hero.addMao(indice, card);
                        break;
                    case Param.HEROI_SET_MAO_REMOVE:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        hero.delMao(temp);
                        break;
                    case Param.HEROI_SET_DECK_ADD:
                        index = pacote.getParamInt(Param.INDEX_CARTA);
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        hero.addDeck(index, temp);
                        break;
                    case Param.HEROI_SET_DECK_REMOVE:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        hero.delDeck(temp);
                        break;
                    case Param.HEROI_SET_SEGREDO_ADD:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        hero.addSegredo(temp);
                        break;
                    case Param.HEROI_SET_SEGREDO_REMOVE:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        hero.delSegredo(temp);
                        break;
                    case Param.HEROI_SET_MORTO_ADD:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        hero.getMorto().add(temp);
                        break;
                    case Param.HEROI_SET_MORTO_REMOVE:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        hero.getMorto().remove(temp);
                        break;
                    case Param.HEROI_SET_CARDS_JOGADOS_ADD:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        hero.getCardsJogadosNaRodada().add(temp);
                        break;
                    case Param.HEROI_SET_CARDS_JOGADOS_REMOVE:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        hero.getCardsJogadosNaRodada().remove(temp);
                        break;
                    case Param.HEROI_SET_IMUNE:
                        hero.setImune(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.HEROI_SET_FREEZE:
                        hero.setFreeze(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.HEROI_SET_INVESTIDA_VISIBLE:
                        hero.setInvestidaVisible(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.HEROI_SET_ATAQUES_REALIZADOS:
                        hero.setAtaquesRealizados(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.HEROI_SET_CUMPRIU_FREEZE:
                        hero.setCumpriuFreeze(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.HEROI_SET_PODER_UTILIZADO:
                        hero.setPoderHeroicoUtilizado(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.HEROI_SET_TIPO_PODER:
                        hero.setPoderHeroico(pacote.getParamString(Param.VALUE));
                        break;
                    case Param.HEROI_SET_CUSTO_PODER:
                        hero.getPoderHeroico().setCusto(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.HEROI_SET_FADIGA:
                        hero.setFadiga(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_HEROI_SET_CUSTO_PODER:
                        hero.getPoderHeroico().setCustoAura(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.OPONENTE_SET_TYPE:
                        oponente.setType(pacote.getParamString(Param.VALUE));
                        break;
                    case Param.OPONENTE_SET_HEALTH:
                        oponente.setHealth(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.OPONENTE_SET_VIDA_TOTAL:
                        oponente.setVidaTotal(pacote.getParamInt(Param.VALUE), false);
                        break;
                    case Param.OPONENTE_SET_ATAQUE:
                        oponente.setAttack(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.OPONENTE_SET_SHIELD:
                        oponente.setShield(pacote.getParamInt(Param.VALUE), false);
                        break;
                    case Param.OPONENTE_SET_MANA:
                        oponente.setMana(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.OPONENTE_SET_MANA_UTILIZADA:
                        oponente.setManaUtilizada(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.OPONENTE_SET_MANA_BLOQ_ESTE_TURNO:
                        oponente.setManaBloqueadaEsteTurno(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.OPONENTE_SET_MANA_BLOQ_TURNO_ANT:
                        oponente.setManaBloqueadaTurnoAnterior(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.OPONENTE_SET_ARMA:
                        temp = pacote.getParamString(Param.VALUE).equals("null")
                                ? null
                                : partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        oponente.setArma(temp);
                        break;
                    case Param.OPONENTE_SET_MESA_ADD:
                        index = pacote.getParamInt(Param.INDEX_CARTA);
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        temp.setTime(System.nanoTime());
                        oponente.addMesa(index, temp);
                        break;
                    case Param.OPONENTE_SET_MESA_REMOVE:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        oponente.delMesa(temp);
                        break;
                    case Param.OPONENTE_SET_MAO_ADD:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        oponente.addMao(temp);
                        break;
                    case Param.OPONENTE_SET_MAO_REMOVE:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        oponente.delMao(temp);
                        break;
                    case Param.OPONENTE_SET_DECK_ADD:
                        index = pacote.getParamInt(Param.INDEX_CARTA);
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        oponente.addDeck(index, temp);
                        break;
                    case Param.OPONENTE_SET_DECK_REMOVE:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        oponente.delDeck(temp);
                        break;
                    case Param.OPONENTE_SET_SEGREDO_ADD:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        oponente.addSegredo(temp);
                        break;
                    case Param.OPONENTE_SET_SEGREDO_REMOVE:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        oponente.delSegredo(temp);
                        break;
                    case Param.OPONENTE_SET_MORTO_ADD:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        oponente.getMorto().add(temp);
                        break;
                    case Param.OPONENTE_SET_MORTO_REMOVE:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        oponente.getMorto().remove(temp);
                        break;
                    case Param.OPONENTE_SET_CARDS_JOGADOS_ADD:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        oponente.getCardsJogadosNaRodada().add(temp);
                        break;
                    case Param.OPONENTE_SET_CARDS_JOGADOS_REMOVE:
                        temp = partida.findCardByIDLong(pacote.getParamLong(Param.VALUE));
                        oponente.getCardsJogadosNaRodada().remove(temp);
                        break;
                    case Param.OPONENTE_SET_IMUNE:
                        oponente.setImune(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.OPONENTE_SET_FREEZE:
                        oponente.setFreeze(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.OPONENTE_SET_INVESTIDA_VISIBLE:
                        oponente.setInvestidaVisible(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.OPONENTE_SET_ATAQUES_REALIZADOS:
                        oponente.setAtaquesRealizados(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.OPONENTE_SET_CUMPRIU_FREEZE:
                        oponente.setCumpriuFreeze(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.OPONENTE_SET_PODER_UTILIZADO:
                        oponente.setPoderHeroicoUtilizado(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.OPONENTE_SET_TIPO_PODER:
                        oponente.setPoderHeroico(pacote.getParamString(Param.VALUE));
                        break;
                    case Param.OPONENTE_SET_CUSTO_PODER:
                        oponente.getPoderHeroico().setCusto(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.OPONENTE_SET_FADIGA:
                        oponente.setFadiga(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_OPONENTE_SET_CUSTO_PODER:
                        oponente.getPoderHeroico().setCustoAura(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_CARD_SET_ATAQUE:
                        card.getAura().setAtaque(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_CARD_SET_VIDA_ATUAL:
                        card.getAura().setVida(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_CARD_SET_VIDA_MAXIMA:
                        card.getAura().setVidaMaxima(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_CARD_SET_CUSTO:
                        card.getAura().setCusto(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_CARD_SET_DANO_ADICIONAL:
                        card.getAura().setDanoAdicional(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_CARD_SET_INVESTIDA:
                        card.getAura().setInvestida(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.AURA_CARD_SET_IMUNE:
                        card.getAura().setImune(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.AURA_CARD_SET_IMUNE_ALVO:
                        card.getAura().setImuneAlvo(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.AURA_HEROI_SET_ATAQUE:
                        hero.getAura().setAtaque(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_HEROI_SET_VIDA_ATUAL:
                        hero.getAura().setVida(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_HEROI_SET_VIDA_MAXIMA:
                        hero.getAura().setVidaMaxima(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_HEROI_SET_CUSTO:
                        hero.getAura().setCusto(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_HEROI_SET_DANO_ADICIONAL:
                        hero.getAura().setDanoAdicional(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_HEROI_SET_INVESTIDA:
                        hero.getAura().setInvestida(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.AURA_HEROI_SET_IMUNE:
                        hero.getAura().setImune(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.AURA_HEROI_SET_IMUNE_ALVO:
                        hero.getAura().setImuneAlvo(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.AURA_OPONENTE_SET_ATAQUE:
                        oponente.getAura().setAtaque(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_OPONENTE_SET_VIDA_ATUAL:
                        oponente.getAura().setVida(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_OPONENTE_SET_VIDA_MAXIMA:
                        oponente.getAura().setVidaMaxima(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_OPONENTE_SET_CUSTO:
                        oponente.getAura().setCusto(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_OPONENTE_SET_DANO_ADICIONAL:
                        oponente.getAura().setDanoAdicional(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_OPONENTE_SET_INVESTIDA:
                        oponente.getAura().setInvestida(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.AURA_OPONENTE_SET_IMUNE:
                        oponente.getAura().setImune(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.AURA_OPONENTE_SET_IMUNE_ALVO:
                        oponente.getAura().setImuneAlvo(pacote.getParamBoolean(Param.VALUE));
                        break;
                    case Param.AURA_CARD_SET_AURAS_ADDS_ADD:
                        card.getAura().addID(pacote.getParamLong(Param.VALUE));
                        break;
                    case Param.AURA_HEROI_SET_AURAS_ADDS_ADD:
                        card.getAura().addID(pacote.getParamLong(Param.VALUE));
                        break;
                    case Param.AURA_OPONENTE_SET_AURAS_ADDS_ADD:
                        card.getAura().addID(pacote.getParamLong(Param.VALUE));
                        break;
                    case Param.AURA_ESTATICA_ADD:
                        Pacote p = JsonUtils.toObject(pacote.getParamString(Param.VALUE));
                        partida.getAuraEstatica().add(p);
                        break;
                    case Param.AURA_ESTATICA_DEL:
                        partida.getAuraEstatica().remove(pacote.getParamInt(Param.VALUE));
                        break;
                    case Param.AURA_ESTATICA_SET_SLEEP:
                        partida.getAuraEstatica().setSleep(pacote.getParamInt(Param.INDEX), pacote.getParamInt(Param.VALUE));
                        break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}