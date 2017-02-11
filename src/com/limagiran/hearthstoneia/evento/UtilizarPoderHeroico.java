package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.Utils;
import com.limagiran.hearthstone.partida.control.Pacote;
import static com.limagiran.hearthstone.poder.control.PoderHeroico.*;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstoneia.partida.view.EscolherCard;
import com.limagiran.hearthstone.util.Param;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class UtilizarPoderHeroico {

    private static Pacote pack;
    private static long alvo_long;
    private static Partida partida;
    private static boolean hunter_gvg_087 = false;
    private static int danoExtra = 0;

    /**
     * Gera uma jogada de poder heroico
     *
     * @param partida partida
     * @param pacote pacote configurado
     * @return {@code true} para poder utilizado ou {@code false} para não
     * utilizado
     */
    public static boolean poderHeroico(Partida partida, Pacote pacote) {
        pack = pacote;
        UtilizarPoderHeroico.partida = partida;
        alvo_long = pack.getParamLong(Param.ALVO);
        danoExtra = getDanoExtra();
        switch (partida.getHero().getPoderHeroico().getTipo()) {
            //receba +1 de ataque neste turno e +1 de armadura
            case DRUID_DEFAULT:
                druid_default(1);
                break;
            //cause 2 de dano ao herói inimigo
            case HUNTER_DEFAULT:
                //verificar se pode alvejar lacaios
                if (isHunter_gvg_087()) {
                    if (!hunter_gvg_087(2)) {
                        return false;
                    }
                } else {
                    hunter_default(2);
                }
                break;
            case MAGE_DEFAULT:
                //cause 1 de dano
                if (!mage_default(1)) {
                    return false;
                }
                break;
            case PALADIN_DEFAULT:
                //evoque um recruta do punho de prata
                if (!paladin_default(1)) {
                    return false;
                }
                break;
            case PRIEST_DEFAULT:
                //restaure 2 de vida
                if (!priest_default(2)) {
                    return false;
                }
                break;
            case ROGUE_DEFAULT:
                //equipe uma adaga 2/1
                rogue_default();
                break;
            case SHAMAN_DEFAULT:
                //evoque um totem aleatório
                if (!shaman_default()) {
                    return false;
                }
                break;
            case WARLOCK_DEFAULT:
                //compre um card e receba 2 de dano
                warlock_default();
                break;
            case WARRIOR_DEFAULT:
                //receba +2 de armadura
                warrior_default(2);
                break;
            case DRUID_MELHORADO:
                //receba +2 de ataque neste turno e +2 de armadura
                druid_default(2);
                break;
            //cause 3 de dano ao herói inimigo
            case HUNTER_MELHORADO:
                //verifica se pode alvejar lacaios
                if (isHunter_gvg_087()) {
                    if (!hunter_gvg_087(3)) {
                        return false;
                    }
                } else {
                    hunter_default(3);
                }
                break;
            //cause 2 de dano
            case MAGE_MELHORADO:
                if (!mage_default(2)) {
                    return false;
                }
                break;
            //evoque 2 recrutas do punho de prata
            case PALADIN_MELHORADO:
                if (!paladin_default(2)) {
                    return false;
                }
                break;
            //restaure 4 de vida
            case PRIEST_MELHORADO:
                if (!priest_default(4)) {
                    return false;
                }
                break;
            //equipe uma adaga 2/2
            case ROGUE_MELHORADO:
                rogue_melhorado();
                break;
            //evoque um totem à sua escolha
            case SHAMAN_MELHORADO:
                if (!shaman_melhorado()) {
                    return false;
                }
                break;
            //compre um card
            case WARLOCK_MELHORADO:
                warlock_melhorado();
                break;
            //receba +4 de armadura
            case WARRIOR_MELHORADO:
                warrior_default(4);
                break;
            //cause 2 de dano
            case PRIEST_EX1_625:
                if (!priest_ex1_625(2)) {
                    return false;
                }
                break;
            //cause 3 de dano
            case PRIEST_EX1_625B:
                if (!priest_ex1_625(3)) {
                    return false;
                }
                break;
            //cause 2 de dano
            case SHAMAN_AT_050T:
                if (!shaman_at_050t()) {
                    return false;
                }
                break;
        }
        partida.addHistorico(partida.getHero().getNome() + " utilizou poder heroico.");
        return true;
    }

    /**
     * Receber ataque por um turno e armadura
     *
     * @param value ataque e armadura
     */
    private static void druid_default(int value) {
        partida.getEfeitoProgramado().concederAtaqueAoHeroiUmTurno(value);
        partida.getHero().addShield(value);
    }

    /**
     * Causar dano ao herói inimigo
     *
     * @param value dano
     */
    private static void hunter_default(int value) {
        partida.getOponente().delHealth(partida.getHero().getDobrarDanoECura(value) + danoExtra);
    }

    /**
     * Causar dano
     *
     * @param value dano
     * @return true para alvo válido e false para alvo inválido
     */
    private static boolean mage_default(int value) {
        return causarDano(value);
    }

    /**
     * Evocar um recruta do punho de prata
     *
     * @param value quantidade de recrutas
     * @return true para recrutas evocados ou false para mesa sem espaço
     */
    private static boolean paladin_default(int value) {
        if (partida.getHero().temEspacoNaMesa()) {
            for (int i = 0; i < value; i++) {
                partida.getHero().evocar(partida.criarCard(Values.RECRUTA_PUNHO_DE_PRATA, System.nanoTime()));
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Curar
     *
     * @param value vida restaurada
     * @return true para alvo válido e false para alvo inválido
     */
    private static boolean priest_default(int value) {
        int cura = partida.getHero().getDobrarDanoECura(value) + (partida.getHero().curaIsDano() ? danoExtra : 0);
        if (alvo_long == Param.HEROI) {
            partida.getHero().addHealth(cura);
        } else if (alvo_long == Param.OPONENTE) {
            partida.getOponente().addHealth(cura);
        } else if (partida.findCardByIDLong(alvo_long) != null) {
            Card alvo = partida.findCardByIDLong(alvo_long);
            alvo.addVida(cura);
        } else {
            return false;
        }
        return true;
    }

    /**
     * Equipar uma adaga 2/1
     */
    private static void rogue_default() {
        //Arma Lâmina Envenenada (poder heróico adiciona 1 de ataque em vez de substituí-la)
        if (partida.getHero().getArma() != null
                && partida.getHero().getArma().getId().equals("AT_034")) {
            partida.getHero().getArma().addAtaque(1);
        } else {
            partida.getHero().setWeapon(partida.criarCard(Values.FACA_PERVERSA, System.nanoTime()));
        }
    }

    /**
     * Evocar um totem
     *
     * @return true para totem evocado ou false para mesa sem espaço
     */
    private static boolean shaman_default() {
        Card criado;
        if (partida.getHero().temEspacoNaMesa()
                && (criado = partida.criarCard(Utils.getTotemXamaPoderHeroico(
                        partida.getHero().getMesa()), System.nanoTime()))
                != null) {
            partida.getHero().evocar(criado);
            return true;
        }
        return false;
    }

    /**
     * Comprar um card e receber 2 de dano
     */
    private static void warlock_default() {
        partida.getHero().comprarCarta(Card.COMPRA_PODER_HEROICO);
        partida.getHero().delHealth(partida.getHero().getDobrarDanoECura(2));
    }

    /**
     * Receber armadura
     *
     * @param value quantidade de armadura
     */
    private static void warrior_default(int value) {
        partida.getHero().addShield(value);
    }

    /**
     * Equipar uma adaga 2/2
     */
    private static void rogue_melhorado() {
        partida.getHero().setWeapon(partida.criarCard(Values.ADAGA_ENVENENADA, System.nanoTime()));
    }

    /**
     * Evocar um totem à sua escolha
     *
     * @return true para totem evocado ou false para mesa sem espaço
     */
    private static boolean shaman_melhorado() {
        if (partida.getHero().temEspacoNaMesa()) {
            List<String> evocar = new ArrayList<>(Arrays.asList(Values.TOTENS_SHAMAN_PODER));
            List<String> escolher = new ArrayList<>(Arrays.asList(Values.TOTENS_SHAMAN_PODER_MELHORADO));
            String escolhido = EscolherCard.main("Evoque um Totem à sua escolha", Values.TOTENS_SHAMAN_PODER_MELHORADO);
            if (escolhido != null) {
                Card criado = partida.criarCard(evocar.get(escolher.indexOf(escolhido)), System.nanoTime());
                partida.getHero().evocar(criado);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Comprar um card
     */
    private static void warlock_melhorado() {
        partida.getHero().comprarCarta(Card.COMPRA_PODER_HEROICO);
    }

    /**
     * Causar dano herói inimigo ou lacaio
     *
     * @param value dano
     * @return true para alvo válido ou false para alvo inválido
     */
    private static boolean hunter_gvg_087(int value) {
        int dano = partida.getHero().getDobrarDanoECura(value) + danoExtra;
        if (alvo_long == Param.OPONENTE) {
            partida.getOponente().delHealth(dano);
        } else if (partida.findCardByIDLong(alvo_long) != null) {
            Card alvo = partida.findCardByIDLong(alvo_long);
            alvo.delVida(dano);
        } else {
            return false;
        }
        return true;
    }

    /**
     * Causar dano
     *
     * @param value dano
     * @return true para alvo válido ou false para alvo inválido
     */
    private static boolean priest_ex1_625(int value) {
        return causarDano(value);
    }

    /**
     * Causar dano
     *
     * @return true para alvo válido e false para alvo inválido
     */
    private static boolean shaman_at_050t() {
        return causarDano(2);
    }

    /**
     * Gera um evento padrão para poder heroico que causa dano a qualquer
     * personagem
     *
     * @param value dano
     * @return true para alvo válido e false para alvo inválido
     */
    public static boolean causarDano(int value) {
        int dano = partida.getHero().getDobrarDanoECura(value) + danoExtra;
        Card alvo;
        if (alvo_long == Param.HEROI) {
            partida.getHero().delHealth(dano);
        } else if (alvo_long == Param.OPONENTE) {
            partida.getOponente().delHealth(dano);
        } else if ((alvo = partida.findCardByIDLong(alvo_long)) != null && !alvo.getId().equals("default")) {
            alvo.delVida(dano);
        } else {
            return false;
        }
        return true;
    }

    /**
     * Verifica se o poder heroico do Caçador pode alvejar lacaios
     *
     * @return true or false
     */
    public static boolean isHunter_gvg_087() {
        return hunter_gvg_087;
    }

    /**
     * Altera o status da variável para saber se o poder heróico do Caçador pode
     * alvejar lacaios
     *
     * @param hunter_gvg_087 novo valor
     */
    public static void setHunter_gvg_087(boolean hunter_gvg_087) {
        UtilizarPoderHeroico.hunter_gvg_087 = hunter_gvg_087;
    }

    /**
     * Verifica a quantidade de dano extra que o poder heroico tem
     *
     * @return quantidade de dano extra
     */
    private static int getDanoExtra() {
        int retorno = 0;
        for (Card c : partida.getAllCardsIncludeSecrets()) {
            if (!c.isSilenciado()) {
                switch (c.getId()) {
                    //Herói Caído - Seu Poder Heroico causa 1 de dano extra
                    case "AT_003":
                        if (partida.getHero().getMesa().contains(c)) {
                            retorno += 1;
                        }
                        break;
                }
            }
        }
        return retorno;
    }
}