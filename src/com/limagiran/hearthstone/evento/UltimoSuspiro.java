package com.limagiran.hearthstone.evento;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Utils;
import com.limagiran.hearthstone.util.Filtro;
import com.limagiran.hearthstone.util.Param;
import com.limagiran.hearthstone.partida.control.Partida;
import com.limagiran.hearthstone.partida.util.Random;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class UltimoSuspiro {

    private static Partida partida;
    private static Card card;
    private static int index;

    /**
     * Verifica se há algum evento para último suspiro
     *
     * @param card lacaio que morreu
     * @param index local onde o lacaio morreu
     */
    public static void processar(Card card, int index) {
        UltimoSuspiro.partida = card.getPartida();
        UltimoSuspiro.card = card;
        UltimoSuspiro.index = index;
        if (partida.isVezHeroi()) {
            if (!UltimoSuspiro.card.isSilenciado()) {
                switch (UltimoSuspiro.card.getId()) {
                    //Rhonin (Último Suspiro: Adicione 3 cópias de Mísseis Arcanos à sua mão.
                    case "AT_009":
                        at_009();
                        break;
                    //Corcel Medonho (Último Suspiro: Evoque um Corcel Medonho)
                    case "AT_019":
                        at_019();
                        break;
                    //Anub'arak (Último Suspiro: Devolva este card à sua mão e evoque um Nerubiano 4/4)
                    case "AT_036":
                        at_036();
                        break;
                    //Aspirante de Darnassus (Grito de Guerra: Receba um Cristal de Mana vazio. Último Suspiro: Perca um Cristal de Mana)
                    case "AT_038":
                        at_038();
                        break;
                    //Martelo Carregado (Último Suspiro: Seu Poder Heroico torna-se "Cause 2 de dano")
                    case "AT_050":
                        at_050();
                        break;
                    //Teceteia (Último Suspiro: Adicione um card de Fera aleatório à sua mão)
                    case "FP1_011":
                        fp1_011();
                        break;
                    //Gargagelo (Provocar. Último Suspiro: Se tiver um Dragão na mão, cause 3 de dano a todos os lacaios)
                    case "AT_123":
                        at_123();
                        break;
                    //O Cavaleiro Esqueleto (Último Suspiro: Revele um lacaio em cada deck. Se o seu custar mais, devolva este card à sua mão)
                    case "AT_128":
                        at_128();
                        break;
                    //Mago Sangrento Thalnos (+1 de Dano Mágico. Último Suspiro: Compre um card)
                    case "EX1_012":
                        ex1_012();
                        break;
                    //Sylvana Correventos (Último Suspiro: Assuma o controle de um lacaio inimigo aleatório)
                    case "EX1_016":
                        ex1_016();
                        break;
                    //Gnomo Leproso (Último Suspiro: Cause 2 de dano ao herói inimigo)
                    case "EX1_029":
                        ex1_029();
                        break;
                    //Acumulador de Tesouros (Último Suspiro: Compre um card)
                    case "EX1_096":
                        ex1_096();
                        break;
                    //Abominação (Provocar. Último Suspiro: Cause 2 de dano a TODOS os personagens)
                    case "EX1_097":
                        ex1_097();
                        break;
                    //Caerne Casco Sangrento (Último Suspiro: Evoque um Baine Casco Sangrento 4/5)
                    case "EX1_110":
                        ex1_110();
                        break;
                    //Tirion Fordring (Escudo Divino. Provocar. Último Suspiro: Equipe uma Crematória 5/3)
                    case "EX1_383":
                        ex1_383();
                        break;
                    //Jubalta da Savana (Último Suspiro, Evoque duas Hienas 2/2)
                    case "EX1_534":
                        ex1_534();
                        break;
                    //Golem Colheiteiro (Último Suspiro: Evoque um Golem Danificado 2/1)
                    case "EX1_556":
                        ex1_556();
                        break;
                    //A Fera (Último Suspiro: Evoque um Jonas Tiragosto 3/3 para o seu oponente)
                    case "EX1_577":
                        ex1_577();
                        break;
                    //Petisco Zumbi (Último Suspiro: Restaure 5 de Vida do Herói inimigo)
                    case "FP1_001":
                        fp1_001();
                        break;
                    //Rastejante Assombrado (Último Suspiro: Evoque duas Aranhas Espectrais 1/1)
                    case "FP1_002":
                        fp1_002();
                        break;
                    //Cientista Louco (Último Suspiro: Coloque um Segredo do seu deck no campo de batalha)
                    case "FP1_004":
                        fp1_004();
                        break;
                    //Ovo Nerubiano (Último Suspiro: Evoque um Nerubiano 4/4)
                    case "FP1_007":
                        fp1_007();
                        break;
                    //Necrolorde (Provocar. Último Suspiro: Seu oponente coloca um lacaio do próprio deck no campo de batalha)
                    case "FP1_009":
                        fp1_009();
                        break;
                    //Arrota-Lodo (Provocar. Último Suspiro: Evoque um Visto 1/2 com Provocar)
                    case "FP1_012":
                        fp1_012();
                        break;
                    //Stalagg (Último Suspiro: Se Feugen também morreu nesta partidaView, evoque Thaddius)
                    case "FP1_014":
                        fp1_014();
                        break;
                    //Feugen (Último Suspiro: Se Stalagg também morreu nesta partidaView, evoque Thaddius)
                    case "FP1_015":
                        fp1_015();
                        break;
                    //Mordida da Morte (Último Suspiro: Cause 1 de dano a todos os lacaios)
                    case "FP1_021":
                        fp1_021();
                        break;
                    //Arauto do Caos (Último Suspiro: Coloque um Demônio aleatório da sua mão no campo de batalha)
                    case "FP1_022":
                        fp1_022();
                        break;
                    //Sectário Sombrio (Último Suspiro: Conceda +3 de Vida a um lacaio aliado aleatório)
                    case "FP1_023":
                        fp1_023();
                        break;
                    //Carniçal Instável (Provocar. Último Suspiro: Cause 1 de dano a todos os lacaios)
                    case "FP1_024":
                        fp1_024();
                        break;
                    //Emboscador Anub'ar (Último Suspiro: Devolva um lacaio aliado aleatório à sua mão)
                    case "FP1_026":
                        fp1_026();
                        break;
                    //Espadas Dançantes (Último Suspiro: Seu oponente compra um card)
                    case "FP1_029":
                        fp1_029();
                        break;
                    //Marlone (Último Suspiro: Coloque este lacaio no seu deck)
                    case "GVG_035":
                        gvg_035();
                        break;
                    //Maça do Poder (Último Suspiro: Conceda +2/+2 a um Mecanoide aliado aleatório)
                    case "GVG_036":
                        gvg_036();
                        break;
                    //Ovelha Explosiva (Último Suspiro: Cause 2 de dano a todos os lacaios)
                    case "GVG_076":
                        gvg_076();
                        break;
                    //Yeti Mecânico (Último Suspiro: Concede uma Peça Sobressalente a cada jogador)
                    case "GVG_078":
                        gvg_078();
                        break;
                    //Gnomo de Corda (Último Suspiro: Adicione um card Peça Sobressalente à sua mão)
                    case "GVG_082":
                        gvg_082();
                        break;
                    //Retalhador Guiado (Último Suspiro: Evoque um lacaio aleatório com 2 de Custo)
                    case "GVG_096":
                        gvg_096();
                        break;
                    //Golem Celeste Guiado (Último Suspiro: Evoque um lacaio aleatório com 4 de Custo)
                    case "GVG_105":
                        gvg_105();
                        break;
                    //Robomba (Último Suspiro: Cause 1-4 de dano a um inimigo aleatório)
                    case "GVG_110t":
                        gvg_110t();
                        break;
                    //Velho Retalhador do Sneed (Último Suspiro: Evoque um lacaio lendário aleatório)
                    case "GVG_114":
                        gvg_114();
                        break;
                    //Tocha (Grito de Guerra e Último Suspiro: Adicione um card Peça Sobressalente à sua mão)
                    case "GVG_115":
                        gvg_115();
                        break;
                    //Saparrão (Último Suspiro: Cause 1 de dano a um inimigo aleatório)
                    case "LOE_046":
                        loe_046();
                        break;
                    //Raptor Montado (Último Suspiro: Evoque um lacaio aleatório com 1 de custo)
                    case "LOE_050":
                        loe_050();
                        break;
                    //Sentinela Anubisath (Último Suspiro: Conceda +3/+3 a um lacaio aliado aleatório)
                    case "LOE_061":
                        loe_061();
                        break;
                    //Nanicos Vacilantes (Último Suspiro: Evoque três Nanicos 2/2)
                    case "LOE_089":
                        loe_089();
                        break;
                }
            }
            card.getEvocarUltimoSuspiro().forEach(evocar -> {
                if (evocar.contains(Values.CLONAR_ULTIMO_SUSPIRO)) {
                    try {
                        Card morto = partida.findCardByIDLong(Long.parseLong(evocar.replace(Values.CLONAR_ULTIMO_SUSPIRO, "")));
                        if (morto != null) {
                            UltimoSuspiro.processar(morto, index);
                        }
                    } catch (Exception e) {
                    }
                } else {
                    card.getDono().evocar(index, partida.criarCard(evocar, System.nanoTime()));
                }
            });
            card.getAddMaoUltimoSuspiro().forEach(c -> card.getDono().addMao(partida.criarCard(c, System.nanoTime())));
            card.getAddDeckUltimoSuspiro().forEach(c -> card.getDono().addCardDeckAleatoriamente(partida.criarCard(c, System.nanoTime())));
        }
    }

    private static void at_009() {
        for (int i = 0; i < 3; i++) {
            Card missel = partida.criarCard(Values.MISSEL_ARCANO, System.nanoTime());
            card.getDono().addMao(missel);
        }
    }

    /**
     * Corcel Medonho (Último Suspiro: Evoque um Corcel Medonho)
     */
    private static void at_019() {
        card.getDono().evocar(index, partida.criarCard("AT_019", System.nanoTime()));
    }

    /**
     * Anub'arak (Último Suspiro: Devolva este card à sua mão e evoque um
     * Nerubiano 4/4)
     */
    private static void at_036() {
        card.getDono().addMao(partida.criarCard("AT_036", System.nanoTime()));
        card.getDono().evocar(index, partida.criarCard(Values.NERUBIANO4_4, System.nanoTime()));
    }

    /**
     * Aspirante de Darnassus (Grito de Guerra: Receba um Cristal de Mana vazio.
     * Último Suspiro: Perca um Cristal de Mana)
     */
    private static void at_038() {
        card.getDono().delMana(1);
    }

    /**
     * Martelo Carregado (Último Suspiro: Seu Poder Heroico torna-se "Cause 2 de
     * dano")
     */
    private static void at_050() {
        card.getDono().setPoderHeroico(com.limagiran.hearthstone.poder.control.PoderHeroico.SHAMAN_AT_050T);
    }

    /**
     * Teceteia (Último Suspiro: Adicione um card de Fera aleatório à sua mão)
     */
    private static void fp1_011() {
        card.getDono().addMao(partida.criarCard(Random.byRace(Values.FERA).getId(), System.nanoTime()));
    }

    /**
     * Gargagelo (Provocar. Último Suspiro: Se tiver um Dragão na mão, cause 3
     * de dano a todos os lacaios)
     */
    private static void at_123() {
        for (Card mao : card.getDono().getMao()) {
            if (mao.isDragao() && !mao.equals(card)) {
                List<Card> lacaios = new ArrayList<>();
                lacaios.addAll(card.getDono().getMesa());
                lacaios.addAll(card.getOponente().getMesa());
                EmArea.dano(partida, lacaios, 3, 0);
                break;
            }
        }
    }

    /**
     * O Cavaleiro Esqueleto (Último Suspiro: Revele um lacaio em cada deck. Se
     * o seu custar mais, devolva este card à sua mão)
     */
    private static void at_128() {
        long vencedor = Justas.processar(card.getDono(), card.getOponente());
        if (vencedor == Param.HEROI) {
            card.getDono().addMao(partida.criarCard(card.getId(), System.nanoTime()));
        }
    }

    /**
     * Mago Sangrento Thalnos (+1 de Dano Mágico. Último Suspiro: Compre um
     * card)
     */
    private static void ex1_012() {
        card.getDono().comprarCarta(Card.COMPRA_FEITICO);
    }

    /**
     * Sylvana Correventos (Último Suspiro: Assuma o controle de um lacaio
     * inimigo aleatório)
     */
    private static void ex1_016() {
        List<Card> mesaOponente = card.getOponente().getMesa();
        if (!mesaOponente.isEmpty()) {
            int random = Utils.random(mesaOponente.size()) - 1;
            partida.roubar(mesaOponente.get(random).id_long);
        }
    }

    /**
     * Gnomo Leproso (Último Suspiro: Cause 2 de dano ao herói inimigo)
     */
    private static void ex1_029() {
        card.getOponente().delHealth(2);
    }

    /**
     * Acumulador de Tesouros (Último Suspiro: Compre um card)
     */
    private static void ex1_096() {
        card.getDono().comprarCarta(Card.COMPRA_EVENTO);
    }

    /**
     * Abominação (Provocar. Último Suspiro: Cause 2 de dano a TODOS os
     * personagens)
     */
    private static void ex1_097() {
        card.getOponente().delHealth(2);
        card.getDono().delHealth(2);
        EmArea.dano(partida, partida.getMesa(), 2, 0);
    }

    /**
     * Caerne Casco Sangrento (Último Suspiro: Evoque um Baine Casco Sangrento
     * 4/5)
     */
    private static void ex1_110() {
        card.getDono().evocar(index, partida.criarCard(Values.BAINE_CASCO_SANGRENTO4_5, System.nanoTime()));
    }

    /**
     * Tirion Fordring (Escudo Divino. Provocar. Último Suspiro: Equipe uma
     * Crematória 5/3)
     */
    private static void ex1_383() {
        card.getDono().setWeapon(partida.criarCard(Values.CREMATORIA5_3, System.nanoTime()));
    }

    /**
     * Jubalta da Savana (Último Suspiro, Evoque duas Hienas 2/2)
     */
    private static void ex1_534() {
        card.getDono().evocar(index, partida.criarCard(Values.HIENA2_2, System.nanoTime()));
        card.getDono().evocar(index, partida.criarCard(Values.HIENA2_2, System.nanoTime()));
    }

    /**
     * Golem Colheiteiro (Último Suspiro: Evoque um Golem Danificado 2/1)
     */
    private static void ex1_556() {
        card.getDono().evocar(index, partida.criarCard(Values.GOLEM_DANIFICADO2_1, System.nanoTime()));
    }

    /**
     * A Fera (Último Suspiro: Evoque um Jonas Tiragosto 3/3 para o seu
     * oponente)
     */
    private static void ex1_577() {
        card.getOponente().evocar(partida.criarCard(Values.JONAS_TIRAGOSTO3_3, System.nanoTime()));
    }

    /**
     * Petisco Zumbi (Último Suspiro: Restaure 5 de Vida do Herói inimigo)
     */
    private static void fp1_001() {
        card.getOponente().addHealth(5);
    }

    /**
     * Rastejante Assombrado (Último Suspiro: Evoque duas Aranhas Espectrais
     * 1/1)
     */
    private static void fp1_002() {
        card.getDono().evocar(index, partida.criarCard(Values.ARANHA_ESPECTRAL1_1, System.nanoTime()));
        card.getDono().evocar(index, partida.criarCard(Values.ARANHA_ESPECTRAL1_1, System.nanoTime()));
    }

    /**
     * Cientista Louco (Último Suspiro: Coloque um Segredo do seu deck no campo
     * de batalha)
     */
    private static void fp1_004() {
        List<Card> segredos = Filtro.segredo(card.getDono().getDeck());
        if (!segredos.isEmpty()) {
            Card aleatorio = segredos.get(Utils.random(segredos.size()) - 1);
            while (card.getDono().temSegredoAtivado(aleatorio.getId())) {
                segredos.remove(aleatorio);
                if (segredos.isEmpty()) {
                    aleatorio = null;
                    break;
                }
                aleatorio = segredos.get(Utils.random(segredos.size()) - 1);
            }
            if (aleatorio != null) {
                card.getDono().addSegredo(aleatorio);
                card.getDono().delDeck(aleatorio);
            }
        }
    }

    /**
     * Ovo Nerubiano (Último Suspiro: Evoque um Nerubiano 4/4)
     */
    private static void fp1_007() {
        card.getDono().evocar(partida.criarCard(Values.NERUBIANO4_4T2, System.nanoTime()));
    }

    /**
     * Necrolorde (Provocar. Último Suspiro: Seu oponente coloca um lacaio do
     * próprio deck no campo de batalha)
     */
    private static void fp1_009() {
        Card aleatorio = Utils.getLacaioAleatorio(card.getOponente().getDeck());
        if (aleatorio != null && aleatorio.getDono().temEspacoNaMesa()) {
            aleatorio.getDono().evocar(aleatorio);
            aleatorio.getDono().delDeck(aleatorio);
        }
    }

    /**
     * Arrota-Lodo (Provocar. Último Suspiro: Evoque um Visto 1/2 com Provocar)
     */
    private static void fp1_012() {
        card.getDono().evocar(partida.criarCard(Values.VISGO2_1, System.nanoTime()));
    }

    /**
     * Stalagg (Último Suspiro: Se Feugen também morreu nesta partidaView,
     * evoque Thaddius)
     */
    private static void fp1_014() {
        List<Card> mortos = new ArrayList<>();
        mortos.addAll(partida.getHero().getMorto());
        mortos.addAll(partida.getOponente().getMorto());
        for (Card morto : mortos) {
            if (morto.getId().equals(Values.FEUGEN)) {
                card.getDono().evocar(partida.criarCard(Values.THADDIUS, System.nanoTime()));
                break;
            }
        }
    }

    /**
     * Feugen (Último Suspiro: Se Stalagg também morreu nesta partidaView,
     * evoque Thaddius)
     */
    private static void fp1_015() {
        List<Card> mortos = new ArrayList<>();
        mortos.addAll(partida.getHero().getMorto());
        mortos.addAll(partida.getOponente().getMorto());
        for (Card morto : mortos) {
            if (morto.getId().equals(Values.STALAGG)) {
                card.getDono().evocar(partida.criarCard(Values.THADDIUS, System.nanoTime()));
                break;
            }
        }
    }

    /**
     * Mordida da Morte (Último Suspiro: Cause 1 de dano a todos os lacaios)
     */
    private static void fp1_021() {
        EmArea.dano(partida, partida.getMesa(), 1, 0);
    }

    /**
     * Arauto do Caos (Último Suspiro: Coloque um Demônio aleatório da sua mão
     * no campo de batalha)
     */
    private static void fp1_022() {
        Card aleatorio = Utils.getLacaioAleatorio(Filtro.raca(card.getDono().getMao(), Values.DEMONIO));
        if (aleatorio != null) {
            aleatorio.getDono().evocar(aleatorio);
            aleatorio.getDono().delMao(aleatorio);
        }
    }

    /**
     * Sectário Sombrio (Último Suspiro: Conceda +3 de Vida a um lacaio aliado
     * aleatório)
     */
    private static void fp1_023() {
        if (!card.getDono().getMesa().isEmpty()) {
            Utils.getLacaioAleatorio(card.getDono().getMesa()).addVidaMaxima(3);
        }
    }

    /**
     * Carniçal Instável (Provocar. Último Suspiro: Cause 1 de dano a todos os
     * lacaios)
     */
    private static void fp1_024() {
        EmArea.dano(partida, partida.getMesa(), 1, 0);
    }

    /**
     * Emboscador Anub'ar (Último Suspiro: Devolva um lacaio aliado aleatório à
     * sua mão)
     */
    private static void fp1_026() {
        if (!card.getDono().getMesa().isEmpty()) {
            partida.voltarPraMaoDoDono(Utils.getLacaioAleatorio(card.getDono().getMesa()).id_long);
        }
    }

    /**
     * Espadas Dançantes (Último Suspiro: Seu oponente compra um card)
     */
    private static void fp1_029() {
        card.getOponente().comprarCarta(Card.COMPRA_EVENTO);
    }

    /**
     * Marlone (Último Suspiro: Coloque este lacaio no seu deck)
     */
    private static void gvg_035() {
        card.getDono().addCardDeckAleatoriamente(partida.criarCard(card.getId(), System.nanoTime()));
    }

    /**
     * Maça do Poder (Último Suspiro: Conceda +2/+2 a um Mecanoide aliado
     * aleatório)
     */
    private static void gvg_036() {
        Card aleatorio = Utils.getLacaioAleatorio(Filtro.raca(card.getDono().getMesa(), Values.MECANOIDE));
        if (aleatorio != null) {
            aleatorio.addVidaMaxima(2);
            aleatorio.addAtaque(2);
        }
    }

    /**
     * Ovelha Explosiva (Último Suspiro: Cause 2 de dano a todos os lacaios)
     */
    private static void gvg_076() {
        EmArea.dano(partida, partida.getMesa(), 2, 0);
    }

    /**
     * Yeti Mecânico (Último Suspiro: Concede uma Peça Sobressalente a cada
     * jogador)
     */
    private static void gvg_078() {
        card.getDono().addMao(partida.criarCard(Random.pecaSobressalente(), System.nanoTime()));
        card.getOponente().addMao(partida.criarCard(Random.pecaSobressalente(), System.nanoTime()));
    }

    /**
     * Gnomo de Corda (Último Suspiro: Adicione um card Peça Sobressalente à sua
     * mão)
     */
    private static void gvg_082() {
        card.getDono().addMao(partida.criarCard(Random.pecaSobressalente(), System.nanoTime()));
    }

    /**
     * Retalhador Guiado (Último Suspiro: Evoque um lacaio aleatório com 2 de
     * Custo)
     */
    private static void gvg_096() {
        Card random = Random.getCard(Values.LACAIO, Values.GERAL, 2, Values.GERAL, Values.GERAL, Values.GERAL);
        card.getDono().evocar(partida.criarCard(random.getId(), System.nanoTime()));
    }

    /**
     * Golem Celeste Guiado (Último Suspiro: Evoque um lacaio aleatório com 4 de
     * Custo)
     */
    private static void gvg_105() {
        Card random = Random.getCard(Values.LACAIO, Values.GERAL, 4, Values.GERAL, Values.GERAL, Values.GERAL);
        card.getDono().evocar(partida.criarCard(random.getId(), System.nanoTime()));
    }

    /**
     * Robomba (Último Suspiro: Cause 1-4 de dano a um inimigo aleatório)
     */
    private static void gvg_110t() {
        Utils.dano(partida, Utils.getAlvoAleatorio(partida, card.getOponente()), 1 + (Utils.random(99) % 4));
    }

    /**
     * Velho Retalhador do Sneed (Último Suspiro: Evoque um lacaio lendário
     * aleatório)
     */
    private static void gvg_114() {
        card.getDono().evocar(partida.criarCard(Random.byRarity(Values.LENDARIO).getId(), System.nanoTime()));
    }

    /**
     * Tocha (Grito de Guerra e Último Suspiro: Adicione um card Peça
     * Sobressalente à sua mão)
     */
    private static void gvg_115() {
        card.getDono().addMao(partida.criarCard(Random.pecaSobressalente(), System.nanoTime()));
    }

    /**
     * Saparrão (Último Suspiro: Cause 1 de dano a um inimigo aleatório)
     */
    private static void loe_046() {
        Utils.dano(partida, Utils.getAlvoAleatorio(partida, card.getOponente()), 1);
    }

    /**
     * Raptor Montado (Último Suspiro: Evoque um lacaio aleatório com 1 de
     * custo)
     */
    private static void loe_050() {
        String random = Random.getCard(Values.LACAIO, Values.GERAL, 1, Values.GERAL, Values.GERAL, Values.GERAL).getId();
        card.getDono().evocar(partida.criarCard(random, System.nanoTime()));
    }

    /**
     * Sentinela Anubisath (Último Suspiro: Conceda +3/+3 a um lacaio aliado
     * aleatório)
     */
    private static void loe_061() {
        if (!card.getDono().getMesa().isEmpty()) {
            Card aleatorio = Utils.getLacaioAleatorio(card.getDono().getMesa());
            aleatorio.addVidaMaxima(3);
            aleatorio.addAtaque(3);
        }
    }

    /**
     * Nanicos Vacilantes (Último Suspiro: Evoque três Nanicos 2/2)
     */
    private static void loe_089() {
        card.getDono().evocar(partida.criarCard(Values.NANICO2_2T, System.nanoTime()));
        card.getDono().evocar(partida.criarCard(Values.NANICO2_2T2, System.nanoTime()));
        card.getDono().evocar(partida.criarCard(Values.NANICO2_2T3, System.nanoTime()));
    }

}
