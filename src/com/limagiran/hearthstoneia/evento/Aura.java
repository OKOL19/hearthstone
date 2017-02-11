package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.Utils;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import com.limagiran.hearthstoneia.partida.control.Partida;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class Aura {

    private static Partida partida;

    /**
     * Atualiza todas as auras da partida
     *
     * @param partida partida verificada
     */
    public static void refresh(Partida partida) {
        Aura.partida = partida;
        partida.getHero().getAura().salvarELimpar();
        partida.getOponente().getAura().salvarELimpar();
        partida.getHero().getPoderHeroico().setCustoAura(0);
        partida.getOponente().getPoderHeroico().setCustoAura(0);
        List<Card> list = new ArrayList<>();
        list.addAll(partida.getAllCards());
        list.addAll(partida.getHero().getMao());
        list.addAll(partida.getOponente().getMao());
        for (Card card : list) {
            if (card != null) {
                card.getAura().salvarELimpar();
            }
        }
        partida.getHero().getPoderHeroico().setCusto(2);
        partida.getOponente().getPoderHeroico().setCusto(2);
        UtilizarPoderHeroico.setHunter_gvg_087(false);
        for (Card card : list) {
            if (!card.isSilenciado()) {
                switch (card.getId()) {
                    //Ferreira Rancorosa (Enfurecer: Sua arma tem +2 de ataque)
                    case "CS2_221":
                        cs2_221(card);
                        break;
                    //Aviana (Seus lacaios custam 1)
                    case "AT_045":
                        at_045(card);
                        break;
                    //Capitão Celeste Kragg (Investida Custa 1 a menos para cada Pirata aliado)
                    case "AT_070":
                        at_070(card);
                        break;
                    //Treinador de Cavalos (Seus Recrutas do Punho de Prata têm +1 de Ataque)
                    case "AT_075":
                        at_075(card);
                        break;
                    //Dama do Lago (Seu Poder Heroico custa 1)
                    case "AT_085":
                        at_085(card);
                        break;
                    //Gigante de Gelo (Custa 1 a menos para cada vez que você usou seu Poder Heroico neste jogo)
                    case "AT_120":
                        at_120(card);
                        break;
                    //Vigília Solene (Compre 2 cards. Custa 1 a menos para cada lacaio morto neste turno)
                    case "BRM_001":
                        brm_001(card);
                        break;
                    //Sopro do Dragão (Causa 4 de dano. Custa 1 a menos para cada lacaio morto neste turno)
                    case "BRM_003":
                        brm_003(card);
                        break;
                    //Vagaroso Vulcânico (Provocar. Custa 1 a menos para cada lacaio morto neste turno)
                    case "BRM_009":
                        brm_009(card);
                        break;
                    //Draco Vulcânico (Custa 1 a menos para cada lacaio morto neste turno)
                    case "BRM_025":
                        brm_025(card);
                        break;
                    //Líder do Raide (Seus outros lacaios têm +1 de Ataque)
                    case "CS2_122":
                        cs2_122(card);
                        break;
                    //Marujo dos Mares do Sul (Tem Investida enquanto você tiver uma arma equipada)
                    case "CS2_146":
                        cs2_146(card);
                        break;
                    //Campeão de Ventobravo (Seus outros lacaios têm +1/+1)
                    case "CS2_222":
                        cs2_222(card);
                        break;
                    //Mercenário da Em. S. A. (Seus lacaios custam 3 a mais)
                    case "CS2_227":
                        cs2_227(card);
                        break;
                    //Lobo Silvestre (Suas Feras têm +1 de Ataque)
                    case "DS1_175":
                        ds1_175(card);
                        break;
                    //Rinoceronte da Tundra (Suas Feras têm Investida)
                    case "DS1_178":
                        ds1_178(card);
                        break;
                    //Velho Olho-turvo (Investida: Ganha +1 de Ataque para cada um dos demais Murlocs no campo de batalha)
                    case "EX1_062":
                        ex1_062(card);
                        break;
                    //Capitã do Brado Guerreiro (Seus lacaios com Investida têm +1 de Ataque)
                    case "EX1_084":
                        ex1_084(card);
                        break;
                    //Gigante da Montanha (Custa 1 a menos para cada um dos outros cards na sua mão)
                    case "EX1_105":
                        ex1_105(card);
                        break;
                    //Lobo Hediondo Alfa (Lacaios adjacentes têm +1 de Ataque)
                    case "EX1_162":
                        ex1_162(card);
                        break;
                    //Portal de Evocação (Seus lacaios custam 2 a menos, mas nunca menos de 1)
                    case "EX1_315":
                        ex1_315(card);
                        break;
                    //Líder Guerreiro Murloc (TODOS os outros Murlocs têm +2/+1)
                    case "EX1_507":
                        ex1_507(card);
                        break;
                    //Oráculo Escamatroz (TODOS os outros Murlocs têm +1 de Ataque)
                    case "EX1_508":
                        ex1_508(card);
                        break;
                    //Totem de Labaredas (Lacaios adjacentes têm +2 de Ataque)
                    case "EX1_565":
                        ex1_565(card);
                        break;
                    //Gigante do Mar (Custa 1 a menos para cada um dos outros lacaios no campo de batalha)
                    case "EX1_586":
                        ex1_586(card);
                        break;
                    //Aprendiz de Feiticeiro (Seus feitiços custam 1 a menos)
                    case "EX1_608":
                        ex1_608(card);
                        break;
                    //Aparição de Mana (TODOS os lacaios custam 1 a mais)
                    case "EX1_616":
                        ex1_616(card);
                        break;
                    //Gigante Derretido (Custa 1 a menos para cada ponto de dano recebido pelo seu herói)
                    case "EX1_620":
                        ex1_620(card);
                        break;
                    //Senhor da Teia Nerub'ar (Lacaios com Grito de Guerra custam 2 a mais)
                    case "FP1_017":
                        fp1_017(card);
                        break;
                    //Mecano-translador (Seus Mecanoides custam 1 a menos)
                    case "GVG_006":
                        gvg_006(card);
                        break;
                    //Mestre da Engrenagem (Ganha +2 de Ataque enquanto você tiver um Mecanoide)                        
                    case "GVG_013":
                        gvg_013(card);
                        break;
                    //Mal'Ganis (Seus outros Demônios ganham +2/+2. Seu herói fica Imune)
                    case "GVG_021":
                        gvg_021(card);
                        break;
                    //Chave do Mestre da Engrenagem (Ganha +2 de Ataque enquanto você tiver um Mecanoide)
                    case "GVG_024":
                        gvg_024(card);
                        break;
                    //Esmagar (Destrua um lacaio. Se você tiver um lacaio ferido, este feitiço custa 4 a menos)
                    case "GVG_052":
                        gvg_052(card);
                        break;
                    //Atirador Bondebico (Seu Poder Heroico pode alvejar lacaios)
                    case "GVG_087":
                        gvg_087(card);
                        break;
                    //Sapatador Goblin (Tem +4 de Ataque enquanto seu oponente tiver 6 ou mais card na mão)
                    case "GVG_095":
                        gvg_095(card);
                        break;
                    //Gigante de Corda (Custa 1 a menos para cada card na mão do seu oponente)
                    case "GVG_121":
                        gvg_121(card);
                        break;
                    //Interrompe-feitiços Nanico (Lacaios adjacentes não podem ser alvo de feitiços nem de Poderes Heroicos)
                    case "GVG_122":
                        gvg_122(card);
                        break;
                    //Bruxa do Mar Naga (Seus cards custam 5)
                    case "LOE_038":
                        loe_038(card);
                        break;
                    //Retoque de Murloc (Conceda aos seus lacaios +2/+2. Custa 1 a menos por cada Murloc sob seu controle)
                    case "LOE_113":
                        loe_113(card);
                        break;
                    //Leokk (Outros lacaios aliados têm +1 de Ataque)
                    case "NEW1_033":
                        new1_033(card);
                        break;
                    //Corsário Terrível (Provocar. Custa 1 a menos por Ataque da sua arma)
                    case "NEW1_022":
                        new1_022(card);
                        break;
                    //Capitão dos Mares do Sul (Seus outros Piratas têm +1/+1)
                    case "NEW1_027":
                        new1_027(card);
                        break;

                }
            }
        }
        for (Card card : list) {
            if (!card.isSilenciado()) {
                switch (card.getId()) {
                    //Cria da Luz (O Ataque deste lacaio é sempre igual à Vida dele)
                    case "EX1_335":
                        ex1_335(card);
                        break;

                }
            }
        }
        for (Card card : list) {
            if (card != null) {
                if (card.getAura().getVida() > card.getAura().getVidaMaxima()) {
                    card.getAura().setVida(card.getAura().getVidaMaxima());
                }
            }
        }
        partida.getHero().getAura().enviarValoresAlterados();
        partida.getOponente().getAura().enviarValoresAlterados();
        for (Card card : list) {
            card.getAura().enviarValoresAlterados();
        }
        refreshPoderHeroico();
        partida.getAuraEstatica().refresh();
    }

    /**
     * Atualiza o poder heroico. Verifica se pode ser utilizado novamente
     */
    private static void refreshPoderHeroico() {
        if (partida.isVezHeroi()) {
            Heroi hero = partida.getHero();
            hero.setPoderHeroicoUtilizado(hero.getPoderHeroicoUtilizadoTurno() >= 1);
            for (Card card : partida.getHero().getMesa()) {
                switch (card.getId()) {
                    //Draco de Gelarra (Você pode usar o seu Poder Heroico quantas vezes quiser)
                    case "AT_008":
                        hero.setPoderHeroicoUtilizado(false);
                        break;
                    //Comandante da Guarnição (Você pode usar o Poder Heroico duas vezes por turno)
                    case "AT_080":
                        if (hero.getPoderHeroicoUtilizadoTurno() < 2) {
                            hero.setPoderHeroicoUtilizado(false);
                        }
                        break;
                }
            }
        }
    }

    /**
     * Ferreira Rancorosa (sua arma tem +2 de ataque)
     *
     * @param card Ferreira Rancorosa
     */
    private static void cs2_221(Card card) {
        if (card.isMesa()) {
            if (card.getVida() < card.getVidaMaxima() && card.getDono().getArma() != null) {
                card.getDono().getArma().getAura().addAtaque(2);
            }
        }
    }

    /**
     * Aviana (Seus lacaios custam 1)
     */
    private static void at_045(Card card) {
        if (card.isMesa()) {
            for (Card c : card.getDono().getMao()) {
                c.getAura().delCusto(c.getCusto() - 1);
            }
        }
    }

    /**
     * Capitão Celeste Kragg (Investida Custa 1 a menos para cada Pirata aliado)
     *
     * @param card Card
     */
    private static void at_070(Card card) {
        if (card.isMao()) {
            int quantidade = 0;
            for (Card c : card.getDono().getMesa()) {
                if (c.isPirata()) {
                    quantidade++;
                }
            }
            if (quantidade > 0) {
                card.getAura().delCusto(quantidade);
            }
        }
    }

    /**
     * Treinador de Cavalos (Seus Recrutas do Punho de Prata têm +1 de Ataque)
     *
     * @param card Card
     */
    private static void at_075(Card card) {
        if (card.isMesa()) {
            for (Card lacaio : card.getDono().getMesa()) {
                if (lacaio.getId().equals(Values.RECRUTA_PUNHO_DE_PRATA)) {
                    lacaio.getAura().addAtaque(1);
                }
            }
        }
    }

    /**
     * Dama do Lago (Seu Poder Heroico custa 1)
     *
     * @param card Card
     */
    private static void at_085(Card card) {
        if (card.isMesa()) {
            card.getDono().getPoderHeroico().setCusto(1);
        }
    }

    /**
     * Gigante de Gelo (Custa 1 a menos para cada vez que você usou seu Poder
     * Heroico neste jogo)
     *
     * @param card Card
     */
    private static void at_120(Card card) {
        if (card.isMao()) {
            card.setCusto(card.getCost() - card.getDono().getPoderHeroicoUtilizadoPartida());
        }
    }

    /**
     * Vigília Solene (Compre 2 cards. Custa 1 a menos para cada lacaio morto
     * neste turno)
     *
     * @param card Card
     */
    private static void brm_001(Card card) {
        if (card.isMao()) {
            int quantidade = card.getDono().getLacaiosMortosNaRodada().size()
                    + card.getOponente().getLacaiosMortosNaRodada().size();
            if (quantidade > 0) {
                card.getAura().delCusto(quantidade);
            }
        }
    }

    /**
     * Sopro do Dragão (Causa 4 de dano. Custa 1 a menos para cada lacaio morto
     * neste turno)
     *
     * @param card Card
     */
    private static void brm_003(Card card) {
        if (card.isMao()) {
            int quantidade = card.getDono().getLacaiosMortosNaRodada().size()
                    + card.getOponente().getLacaiosMortosNaRodada().size();
            if (quantidade > 0) {
                card.getAura().delCusto(quantidade);
            }
        }
    }

    /**
     * Vagaroso Vulcânico (Provocar. Custa 1 a menos para cada lacaio morto
     * neste turno)
     *
     * @param card Card
     */
    private static void brm_009(Card card) {
        if (card.isMao()) {
            int quantidade = card.getDono().getLacaiosMortosNaRodada().size()
                    + card.getOponente().getLacaiosMortosNaRodada().size();
            if (quantidade > 0) {
                card.getAura().delCusto(quantidade);
            }
        }
    }

    /**
     * Draco Vulcânico (Custa 1 a menos para cada lacaio morto neste turno)
     *
     * @param card Card
     */
    private static void brm_025(Card card) {
        if (card.isMao()) {
            int quantidade = card.getDono().getLacaiosMortosNaRodada().size()
                    + card.getOponente().getLacaiosMortosNaRodada().size();
            if (quantidade > 0) {
                card.getAura().delCusto(quantidade);
            }
        }
    }

    /**
     * Líder do Raide (Seus outros lacaios têm +1 de Ataque)
     *
     * @param card Card
     */
    private static void cs2_122(Card card) {
        if (card.isMesa()) {
            for (Card lacaio : card.getDono().getMesa()) {
                if (!lacaio.equals(card)) {
                    lacaio.getAura().addAtaque(1);
                }
            }
        }
    }

    /**
     * Marujo dos Mares do Sul (Tem Investida enquanto você tiver uma arma
     * equipada)
     *
     * @param card Card
     */
    private static void cs2_146(Card card) {
        if (card.isMesa()) {
            if (card.getDono().getArma() != null && card.getTurnosNaMesa() == 0
                    && !card.getMechanics().contains(Values.INVESTIDA)
                    && card.getAtaque() > 0 && !card.isFreeze()
                    && (card.getAtaquesRealizados() == 1
                    || (card.getAtaquesRealizados() == 2 && card.isFuriaDosVentos()))) {
                card.getAura().setInvestida(true);
            }
        }
    }

    /**
     * Campeão de Ventobravo (Seus outros lacaios têm +1/+1)
     *
     * @param card Card
     */
    private static void cs2_222(Card card) {
        if (card.isMesa()) {
            for (Card lacaio : card.getDono().getMesa()) {
                if (!lacaio.equals(card)) {
                    lacaio.getAura().addAtaque(1);
                    lacaio.getAura().addVidaMaxima(1);
                    if (!lacaio.getAura().findByID(card.getTime())) {
                        lacaio.getAura().addVida(1);
                        lacaio.getAura().addID(card.getTime());
                    }
                }
            }
        }
    }

    /**
     * Mercenário da Em. S. A. (Seus lacaios custam 3 a mais)
     *
     * @param card Card
     */
    private static void cs2_227(Card card) {
        if (card.isMesa()) {
            for (Card c : card.getDono().getMao()) {
                if (c.isLacaio()) {
                    c.getAura().addCusto(3);
                }
            }
        }
    }

    /**
     * Lobo Silvestre (Suas outras Feras têm +1 de Ataque)
     *
     * @param card Card
     */
    private static void ds1_175(Card card) {
        if (card.isMesa()) {
            for (Card lacaio : card.getDono().getMesa()) {
                if (lacaio.isFera() && !lacaio.equals(card)) {
                    lacaio.getAura().addAtaque(1);
                }
            }
        }
    }

    /**
     * Rinoceronte da Tundra (Suas Feras têm Investida)
     *
     * @param card Card
     */
    private static void ds1_178(Card card) {
        if (card.isMesa()) {
            for (Card lacaio : card.getDono().getMesa()) {
                if (lacaio.isFera() && lacaio.getTurnosNaMesa() == 0 && !lacaio.getMechanics().contains(Values.INVESTIDA)
                        && lacaio.getAtaque() > 0 && !lacaio.isFreeze()
                        && (lacaio.getAtaquesRealizados() == 2
                        || (lacaio.getAtaquesRealizados() == 3 && lacaio.isFuriaDosVentos()))) {
                    lacaio.getAura().setInvestida(true);
                }
            }
        }
    }

    /**
     * Capitã do Brado Guerreiro (Seus lacaios com Investida têm +1 de Ataque)
     */
    private static void ex1_084(Card card) {
        if (card.isMesa()) {
            for (Card lacaio : card.getDono().getMesa()) {
                if (lacaio.isInvestida()) {
                    lacaio.getAura().addAtaque(1);
                }
            }
        }
    }

    /**
     * Gigante da Montanha (Custa 1 a menos para cada um dos outros cards na sua
     * mão)
     *
     * @param card Card
     */
    private static void ex1_105(Card card) {
        if (card.isMao()) {
            card.getAura().delCusto(card.getDono().getMao().size());
        }
    }

    /**
     * Lobo Hediondo Alfa (Lacaios adjacentes têm +1 de Ataque)
     *
     * @param card Card
     */
    private static void ex1_162(Card card) {
        if (card.isMesa()) {
            List<Card> adjacentes = Utils.getAdjacentes(card);
            for (Card adjacente : adjacentes) {
                adjacente.getAura().addAtaque(1);
            }
        }
    }

    /**
     * Portal de Evocação (Seus lacaios custam 2 a menos, mas nunca menos de 1)
     *
     * @param card Card
     */
    private static void ex1_315(Card card) {
        if (card.isMesa()) {
            for (Card c : card.getDono().getMao()) {
                if (c.isLacaio()) {
                    if (c.getCusto() == 2) {
                        c.getAura().delCusto(1);
                    } else if (c.getCusto() > 2) {
                        c.getAura().delCusto(2);
                    }
                }
            }
        }
    }

    /**
     * Cria da Luz (O Ataque deste lacaio é sempre igual à Vida dele)
     *
     * @param card Card
     */
    private static void ex1_335(Card card) {
        if (card.isMesa()) {
            card.setAtaque(0, false);
            card.getAura().addAtaque(card.getVida());
        }
    }

    /**
     * Líder Guerreiro Murloc (TODOS os outros Murlocs têm +2/+1)
     *
     * @param card Card
     */
    private static void ex1_507(Card card) {
        if (card.isMesa()) {
            for (Card lacaio : partida.getMesa()) {
                if (lacaio.isMurloc() && !lacaio.equals(card)) {
                    lacaio.getAura().addVidaMaxima(1);
                    lacaio.getAura().addAtaque(2);
                    if (!lacaio.getAura().findByID(card.getTime())) {
                        lacaio.getAura().addVida(1);
                        lacaio.getAura().addID(card.getTime());
                    }
                }
            }
        }
    }

    /**
     * Oráculo Escamatroz (TODOS os outros Murlocs têm +1 de Ataque)
     *
     * @param card Card
     */
    private static void ex1_508(Card card) {
        if (card.isMesa()) {
            for (Card lacaio : partida.getMesa()) {
                if (lacaio.isMurloc() && !lacaio.equals(card)) {
                    lacaio.getAura().addAtaque(1);
                }
            }
        }
    }

    /**
     * Totem de Labaredas (Lacaios adjacentes têm +2 de Ataque)
     *
     * @param card Card
     */
    private static void ex1_565(Card card) {
        if (card.isMesa()) {
            for (Card adjacente : Utils.getAdjacentes(card)) {
                adjacente.getAura().addAtaque(2);
            }
        }
    }

    /**
     * Gigante do Mar (Custa 1 a menos para cada um dos outros lacaios no campo
     * de batalha)
     *
     * @param card Card
     */
    private static void ex1_586(Card card) {
        if (card.isMao()) {
            card.getAura().delCusto(partida.getMesa().size());
        }
    }

    /**
     * Aprendiz de Feiticeiro (Seus feitiços custam 1 a menos)
     *
     * @param card
     */
    private static void ex1_608(Card card) {
        if (card.isMesa()) {
            for (Card mao : card.getDono().getMao()) {
                if (mao.isFeitico()) {
                    mao.getAura().delCusto(1);
                }
            }
        }
    }

    /**
     * Aparição de Mana (TODOS os lacaios custam 1 a mais)
     *
     * @param card Card
     */
    private static void ex1_616(Card card) {
        if (card.isMesa()) {
            for (Card mao : partida.getMao()) {
                if (mao.isLacaio()) {
                    mao.getAura().addCusto(1);
                }
            }
        }
    }

    /**
     * Gigante Derretido (Custa 1 a menos para cada ponto de dano recebido pelo
     * seu herói)
     *
     * @param card Card
     */
    private static void ex1_620(Card card) {
        if (card.isMao() && !card.getDono().isIleso()) {
            card.getAura().delCusto(card.getDono().getVidaTotal() - card.getDono().getHealth());
        }
    }

    /**
     * Senhor da Teia Nerub'ar (Lacaios com Grito de Guerra custam 2 a mais)
     *
     * @param card
     */
    private static void fp1_017(Card card) {
        if (card.isMesa()) {
            for (Card mao : partida.getMao()) {
                if (mao.isLacaio() && mao.isGritoDeGuerra()) {
                    mao.getAura().addCusto(2);
                }
            }
        }
    }

    /**
     * Mecano-translador (Seus Mecanoides custam 1 a menos)
     *
     * @param card Card
     */
    private static void gvg_006(Card card) {
        if (card.isMesa()) {
            for (Card mao : card.getDono().getMao()) {
                if (mao.isMecanoide()) {
                    mao.getAura().delCusto(1);
                }
            }
        }
    }

    /**
     * Mestre da Engrenagem (Ganha +2 de Ataque enquanto você tiver um
     * Mecanoide)
     *
     * @param card Card
     */
    private static void gvg_013(Card card) {
        if (card.isMesa() && card.getDono().temMecanoideNaMesa(card)) {
            card.getAura().addAtaque(2);
        }
    }

    /**
     * Mal'Ganis (Seus outros Demônios ganham +2/+2. Seu herói fica Imune)
     *
     * @param card Card
     */
    private static void gvg_021(Card card) {
        if (card.isMesa()) {
            for (Card lacaio : card.getDono().getMesa()) {
                if (lacaio.isDemonio() && !lacaio.equals(card)) {
                    lacaio.getAura().addVidaMaxima(2);
                    lacaio.getAura().addAtaque(2);
                    if (!lacaio.getAura().findByID(card.getTime())) {
                        lacaio.getAura().addVida(2);
                        lacaio.getAura().addID(card.getTime());
                    }
                }
            }
            card.getDono().getAura().setImune(true);
        }
    }

    /**
     * Chave do Mestre da Engrenagem (Ganha +2 de Ataque enquanto você tiver um
     * Mecanoide)
     *
     * @param arma Card
     */
    private static void gvg_024(Card arma) {
        if (arma.isMesa() && arma.getDono().temMecanoideNaMesa()) {
            arma.getAura().addAtaque(2);
        }
    }

    /**
     * Esmagar (Destrua um lacaio. Se você tiver um lacaio ferido, este feitiço
     * custa 4 a menos)
     *
     * @param card Card
     */
    private static void gvg_052(Card card) {
        if (card.isMao() && !Utils.getFeridos(card.getDono().getMesa()).isEmpty()) {
            card.getAura().delCusto(4);
        }
    }

    /**
     * Atirador Bondebico (Seu Poder Heroico pode alvejar lacaios)
     *
     * @param card Card
     */
    private static void gvg_087(Card card) {
        if (card.isMesa()) {
            UtilizarPoderHeroico.setHunter_gvg_087(true);
        }
    }

    /**
     * Sapatador Goblin (Tem +4 de Ataque enquanto seu oponente tiver 6 ou mais
     * card na mão)
     *
     * @param card Card
     */
    private static void gvg_095(Card card) {
        if (card.isMesa() && card.getOponente().getMao().size() >= 6) {
            card.getAura().addAtaque(4);
        }
    }

    /**
     * Gigante de Corda (Custa 1 a menos para cada card na mão do seu oponente)
     *
     * @param card Card
     */
    private static void gvg_121(Card card) {
        if (card.isMao() && card.getOponente().getMao().size() > 0) {
            card.getAura().delCusto(card.getOponente().getMao().size());
        }
    }

    /**
     * Interrompe-feitiços Nanico (Lacaios adjacentes não podem ser alvo de
     * feitiços nem de Poderes Heroicos)
     *
     * @param card Card
     */
    private static void gvg_122(Card card) {
        if (card.isMesa()) {
            List<Card> adjacentes = Utils.getAdjacentes(card);
            for (Card adjacente : adjacentes) {
                adjacente.getAura().setImuneAlvo(true);
            }
        }
    }

    /**
     * Bruxa do Mar Naga (Seus cards custam 5)
     *
     * @param card Card
     */
    private static void loe_038(Card card) {
        if (card.isMesa()) {
            for (Card mao : card.getDono().getMao()) {
                if (mao.getCusto() < 5) {
                    mao.getAura().addCusto(5 - mao.getCusto());
                } else if (mao.getCusto() > 5) {
                    mao.getAura().delCusto(mao.getCusto() - 5);
                }
            }
        }
    }

    /**
     * Retoque de Murloc (Conceda aos seus lacaios +2/+2. Custa 1 a menos por
     * cada Murloc sob seu controle)
     *
     * @param card Card
     */
    private static void loe_113(Card card) {
        if (card.isMao()) {
            int quantidade = 0;
            for (Card lacaio : card.getDono().getMesa()) {
                if (lacaio.isMurloc()) {
                    quantidade++;
                }
            }
            if (quantidade > 0) {
                card.getAura().delCusto(quantidade);
            }
        }
    }

    /**
     * Leokk (Outros lacaios aliados têm +1 de Ataque)
     *
     * @param card Card
     */
    private static void new1_033(Card card) {
        if (card.isMesa()) {
            for (Card aliado : card.getDono().getMesa()) {
                if (!aliado.equals(card)) {
                    aliado.getAura().addAtaque(1);
                }
            }
        }
    }

    /**
     * Corsário Terrível (Provocar. Custa 1 a menos por Ataque da sua arma)
     *
     * @param card Card
     */
    private static void new1_022(Card card) {
        Card arma = card.getDono().getArma();
        if (card.isMao() && arma != null && arma.getAtaque() > 0) {
            card.getAura().delCusto(arma.getAtaque());
        }
    }

    /**
     * Capitão dos Mares do Sul (Seus outros Piratas têm +1/+1)
     *
     * @param card Card
     */
    private static void new1_027(Card card) {
        if (card.isMesa()) {
            for (Card mesa : card.getDono().getMesa()) {
                if (mesa.isPirata() && !mesa.equals(card)) {
                    mesa.getAura().addVidaMaxima(1);
                    mesa.getAura().addAtaque(1);
                    if (!mesa.getAura().findByID(card.getTime())) {
                        mesa.getAura().addVida(1);
                        mesa.getAura().addID(card.getTime());
                    }
                }
            }
        }
    }

    /**
     * Velho Olho-turvo (Investida: Ganha +1 de Ataque para cada um dos demais
     * Murlocs no campo de batalha)
     */
    private static void ex1_062(Card c) {
        if (c.isMesa()) {
            int quantidade = 0;
            for (Card lacaio : c.getDono().getMesa()) {
                if (!lacaio.equals(c) && lacaio.isMurloc()) {
                    quantidade++;
                }
            }
            if (quantidade > 0) {
                c.getAura().addAtaque(quantidade);
            }
        }
    }

}