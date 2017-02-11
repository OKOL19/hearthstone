package com.limagiran.hearthstone.partida.control;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Utils;
import com.google.gson.Gson;
import com.limagiran.hearthstone.evento.AlterarCusto;
import com.limagiran.hearthstone.evento.Aura;
import com.limagiran.hearthstone.heroi.control.Heroi;
import com.limagiran.hearthstone.util.JsonUtils;
import com.limagiran.hearthstone.server.GameCliente;
import static com.limagiran.hearthstone.settings.Settings.EnumSettings.TIME_ANIMATION;
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
                        partida.getPartidaView().addHistorico(pacote.getParamString(Param.VALUE));
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
                    case Param.HEROI_SET_ATAQUE_VISIBLE:
                        hero.setAtaqueVisible(pacote.getParamBoolean(Param.VALUE));
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
                    case Param.OPONENTE_SET_ATAQUE_VISIBLE:
                        oponente.setAtaqueVisible(pacote.getParamBoolean(Param.VALUE));
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
                    case Param.ANIMACAO_CARD_ATAQUE:
                        card.getAnimacao().ataque(pacote.getParamInt(Param.VALUE), TIME_ANIMATION.getLong());
                        break;
                    case Param.ANIMACAO_CARD_VIDA_ATUAL:
                        card.getAnimacao().vidaAtual(pacote.getParamInt(Param.VALUE), TIME_ANIMATION.getLong());
                        break;
                    case Param.ANIMACAO_CARD_VIDA_MAXIMA:
                        card.getAnimacao().vidaMaxima(pacote.getParamInt(Param.VALUE), TIME_ANIMATION.getLong());
                        break;
                    case Param.ANIMACAO_CARD_VIDA_ORIGINAL:
                        card.getAnimacao().vidaOriginal(pacote.getParamInt(Param.VALUE), TIME_ANIMATION.getLong());
                        break;
                    case Param.ANIMACAO_CARD_MORREU:
                        partida.findCardByIDLong(pacote.getParamLong(Param.VALUE)).getAnimacao().morreu();
                        break;
                    case Param.ANIMACAO_CARD_EVOCADO:
                        partida.findCardByIDLong(pacote.getParamLong(Param.VALUE)).getAnimacao().evocado();
                        break;
                    case Param.ANIMACAO_HEROI_ATAQUE:
                        hero.getPanelHeroi().getAnimacao().ataque(pacote.getParamInt(Param.VALUE), TIME_ANIMATION.getLong());
                        break;
                    case Param.ANIMACAO_HEROI_ESCUDO:
                        hero.getPanelHeroi().getAnimacao().escudo(pacote.getParamInt(Param.VALUE), TIME_ANIMATION.getLong());
                        break;
                    case Param.ANIMACAO_HEROI_VIDA:
                        hero.getPanelHeroi().getAnimacao().vida(pacote.getParamInt(Param.VALUE), TIME_ANIMATION.getLong());
                        break;
                    case Param.ANIMACAO_OPONENTE_ATAQUE:
                        oponente.getPanelHeroi().getAnimacao().ataque(pacote.getParamInt(Param.VALUE), TIME_ANIMATION.getLong());
                        break;
                    case Param.ANIMACAO_OPONENTE_ESCUDO:
                        oponente.getPanelHeroi().getAnimacao().escudo(pacote.getParamInt(Param.VALUE), TIME_ANIMATION.getLong());
                        break;
                    case Param.ANIMACAO_OPONENTE_VIDA:
                        oponente.getPanelHeroi().getAnimacao().vida(pacote.getParamInt(Param.VALUE), TIME_ANIMATION.getLong());
                        break;
                    case Param.ANIMACAO_CARD_GATILHO:
                        card.getAnimacao().disparoDeEvento();
                        break;
                    case Param.ANIMACAO_CARD_JOGADO:
                        if (partida.getPartidaView() != null) {
                            partida.getPartidaView().exibirCardJogado(pacote.getParamString(Param.VALUE));
                        }
                        break;
                    case Param.ANIMACAO_CARD_JUSTAS:
                        if (partida.getPartidaView() != null) {
                            partida.getPartidaView().exibirJustas(pacote.getParamString(Param.CARD_JUSTAS_HEROI),
                                    pacote.getParamString(Param.CARD_JUSTAS_OPONENTE));
                        }
                        break;
                    case Param.ANIMACAO_CHANCE_DE_ERRAR_ALVO:
                        if (partida.getPartidaView() != null) {
                            partida.getPartidaView().chanceDeAtacarInimigoErradoAnimacao();
                        }
                        break;
                    case Param.ANIMACAO_CARD_QUEIMADO:
                        if (partida.getPartidaView() != null) {
                            partida.getPartidaView().exibirCardQueimado(pacote.getParamString(Param.CARD_ID), pacote.getParamLong(Param.VALUE));
                        }
                        break;
                    case Param.ANIMACAO_CARD_COMPRADO:
                        if (partida.getPartidaView() != null) {
                            partida.getPartidaView().exibirCardComprado(pacote.getParamString(Param.CARD_ID), pacote.getParamLong(Param.VALUE));
                        }
                        break;
                    case Param.ANIMACAO_CARD_SEGREDO_ATIVADO:
                        if (partida.getPartidaView() != null) {
                            partida.getPartidaView().exibirSegredoAtivado();
                        }
                        break;
                    case Param.ANIMACAO_CARD_SEGREDO_REVELADO:
                        if (partida.getPartidaView() != null) {
                            partida.getPartidaView().exibirSegredoRevelado(
                                    pacote.getParamString(Param.CARD_ID), pacote.getParamLong(Param.VALUE));
                        }
                        break;
                    case Param.ANIMACAO_CARD_VIDA_EM_AREA:
                        String[] ids = pacote.getParamString(Param.ANIMACAO_CARD_VIDA_EM_AREA_IDS).split(";");
                        String[] valores = pacote.getParamString(Param.ANIMACAO_CARD_VIDA_EM_AREA_VALOR).split(";");
                        for (int i = 0; i < ids.length; i++) {
                            Card animar = partida.findCardByIDLong(Long.parseLong(ids[i]));
                            animar.getAnimacao().vidaAtual(Integer.parseInt(valores[i]), 3000, false, false);
                        }
                        Utils.sleep(1800);
                        break;
                    case Param.ANIMACAO_EVENTO_GERADO:
                        if (partida.getPartidaView() != null) {
                            partida.getPartidaView().exibirLinhaEvento(pacote.getParamLong(Param.ATACANTE), pacote.getParamLong(Param.ALVO));
                        }
                        break;
                    case Param.ANIMACAO_FADIGA:
                        if (partida.getPartidaView() != null) {
                            partida.getPartidaView().exibirDanoFadiga(pacote.getParamInt(Param.VALUE), pacote.getParamLong(Param.ALVO));
                        }
                        break;
                }
                if (card != null) {
                    try {
                        card.atualizarAtributos();
                        card.atualizarMecanicas();
                    } catch (Exception e) {

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (partida.getPartidaView() != null) {
            partida.getPartidaView().revalidate();
        }
    }

}