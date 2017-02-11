package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.Utils;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstone.util.Filtro;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.util.Values;


/**
 *
 * @author Vinicius Silva
 */
public class FimDoTurno {

    private static Partida partida;

    /**
     * Verifica se há algum evento para fim do turno
     *
     * @param partida partida verificada
     */
    public static void processar(Partida partida) {
        FimDoTurno.partida = partida;
        if (partida.isVezHeroi()) {
            for (Card c : partida.getAllCardsIncludeSecrets()) {
                if (!c.isSilenciado()) {
                    switch (c.getId()) {
                        //Escamedo (No final do seu turno cause 1 de dano a todos os outros lacaios)
                        case "AT_063t":
                            at_063t(c);
                            break;
                        //Imperador Thaurissan (No fim do seu turno, reduza em 1 o custo dos cards na sua mão)
                        case "BRM_028":
                            brm_028(c);
                            break;
                        //Diabrete de Sangue (Furtividade. No fim do seu turno, conceda +1 de Vida a outro lacaio aliado aleatório)
                        case "CS2_059":
                            cs2_059(c);
                            break;
                        //Ysera (No fim do seu turno, adicione um Card de Sonho à sua mão)
                        case "EX1_572":
                            ex1_572(c);
                            break;
                        //Sacerdotisa Jovem (No fim do seu turno, conceda +1 de Vida a outro lacaio aliado aleatório)
                        case "EX1_004":
                            ex1_004(c);
                            break;
                        //Robô de Reparos (No fim do seu turno, restaure 6 de Vida de um personagem ferido)
                        case "Mekka2":
                            mekka2(c);
                            break;
                        //Encorajador 3000 (No final do seu turno, conceda +1/+1 a um lacaio aleatório)
                        case "Mekka3":
                            mekka3(c);
                            break;
                        //Barão Geddon (No final do seu turno, cause 2 de dano a TODOS os outros personagens)
                        case "EX1_249":
                            ex1_249(c);
                            break;
                        //Arcanista Etéreo (Se você controlar um Segredo no final do seu turno, receba +2/+2)
                        case "EX1_274":
                            ex1_274(c);
                            break;
                        //Ragnaros, o Senhor do Fogo (Não pode atacar. No final do seu turno, cause 8 de dano a um inimigo aleatório)
                        case "EX1_298":
                            ex1_298(c);
                            break;
                        //Arvoroso (Investida. No final do turno, destrua este lacaio)
                        case "EX1_tk9":
                            ex1_tk9(c);
                            break;
                        //Totem de Vagalhão de Mana (No final do seu turno, compre um card)
                        case "EX1_575":
                            ex1_575(c);
                            break;
                        //Diabrete Mestre (No final do seu turno, cause 1 de dano a este lacaio e evoque um Diabrete 1/1)
                        case "EX1_597":
                            ex1_597(c);
                            break;
                        //Kel'Thuzad (No final de cada turno, evoque todos os lacaios aliados que morreram neste turno)
                        case "FP1_013":
                            fp1_013(c);
                            break;
                        //Canhão Vil (No final do seu turno, cause 2 de dano a um lacaio não-Mecanoide)
                        case "GVG_020":
                            gvg_020(c);
                            break;
                        //Sensei de Ferro (No fim do seu turno, conceda +2/+2 a outro Mecanoide aliado)
                        case "GVG_027":
                            gvg_027(c);
                            break;
                        //Totem de Vitalidade (No final do seu turno, recupere 4 de Vida do seu herói)
                        case "GVG_039":
                            gvg_039(c);
                            break;
                        //Golem Ânima (No final do Turno, destrua este lacaio se ele for o seu único)
                        case "GVG_077":
                            gvg_077(c);
                            break;
                        //Iluminador (Se você controlar um Segredo no final do seu turno, restaure 4 de Vida do seu herói)
                        case "GVG_089":
                            gvg_089(c);
                            break;
                        //Jarbas (Ao final dos respectivos turnos, cada jogador compra até ter 3 cards)
                        case "GVG_094":
                            gvg_094(c);
                            break;
                        //Destruidor Obsidiano (No final do seu turno, evoque um Escaravelho 1/1 com Provocar)
                        case "LOE_009":
                            loe_009(c);
                            break;
                        //Mestre Armeiro (No fim do seu turno, conceda +1 de Ataque a outro lacaio aliado aleatório)
                        case "NEW1_037":
                            new1_037(c);
                            break;
                        //Gruul (Ao fim de cada turno, receba +1/+1)
                        case "NEW1_038":
                            new1_038(c);
                            break;
                        //Hogger (No final do seu turno, evoque um Gnoll 2/2 com Provocar)
                        case "NEW1_040":
                            new1_040(c);
                            break;
                        //Totem de Cura (No fim do seu turno, restaure 1 de Vida de todos os lacaios aliados)
                        case "NEW1_009":
                            new1_009(c);
                            break;

                    }
                }
            }
        }
    }

    /**
     * Escamedo (No final do seu turno cause 1 de dano a todos os outros
     * lacaios)
     *
     * @param c Card
     */
    private static void at_063t(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            List<Card> lacaios = partida.getMesa();
            lacaios.remove(c);
            EmArea.dano(partida, lacaios, 1, 0);
            c.delMechanics(Values.FURTIVIDADE);
        }
    }

    /**
     * Imperador Thaurissan (No fim do seu turno, reduza em 1 o custo dos cards
     * na sua mão)
     */
    private static void brm_028(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            for (Card mao : partida.getHero().getMao()) {
                mao.delCusto(1);
            }
        }
    }

    /**
     * Diabrete de Sangue (Furtividade. No fim do seu turno, conceda +1 de Vida
     * a outro lacaio aliado aleatório)
     *
     * @param c Card
     */
    private static void cs2_059(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            Card aleatorio = Utils.getLacaioAleatorio(c.getDono().getMesa(), c);
            if (aleatorio != null) {
                aleatorio.addVidaMaxima(1);
            }
        }
    }

    /**
     * Ysera (No fim do seu turno, adicione um Card de Sonho à sua mão)
     *
     * @param c Card
     */
    private static void ex1_572(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            int random = Utils.random(Values.CARDS_DOS_SONHOS.length) - 1;
            c.getDono().addMao(partida.criarCard(Values.CARDS_DOS_SONHOS[random], System.nanoTime()));
        }
    }

    /**
     * Sacerdotisa Jovem (No fim do seu turno, conceda +1 de Vida a outro lacaio
     * aliado aleatório)
     *
     * @param c Card
     */
    private static void ex1_004(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            Card aleatorio = Utils.getLacaioAleatorio(partida.getHero().getMesa(), c);
            if (aleatorio != null) {
                c.getAnimacao().disparoDeEvento();
                aleatorio.addVidaMaxima(1);
            }
        }
    }

    /**
     * Robô de Reparos (No fim do seu turno, restaure 6 de Vida de um personagem
     * ferido)
     *
     * @param c Card
     */
    private static void mekka2(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            long personagem = Utils.getPersonagemAleatorioFerido(partida);
            if (personagem != Utils.ALVO_INVALIDO) {
                c.getAnimacao().disparoDeEvento();
                Utils.cura(partida, personagem, 6);
            }
        }
    }

    /**
     * Encorajador 3000 (No final do seu turno, conceda +1/+1 a um lacaio
     * aleatório)
     *
     * @param c Card
     */
    private static void mekka3(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            Card aleatorio = partida.getMesa().get(Utils.random(partida.getMesa().size()) - 1);
            aleatorio.addVidaMaxima(1);
            aleatorio.addAtaque(1);
        }
    }

    /**
     * Barão Geddon (No final do seu turno, cause 2 de dano a TODOS os outros
     * personagens)
     *
     * @param c Card
     */
    private static void ex1_249(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            List<Card> lacaios = new ArrayList<>();
            partida.getMesa().stream().filter((lacaio) -> (!lacaio.equals(c))).forEach((lacaio) -> {
                lacaios.add(lacaio);
            });
            c.getAnimacao().disparoDeEvento();
            partida.getOponente().delHealth(2);
            partida.getHero().delHealth(2);
            EmArea.dano(partida, lacaios, 2, 0);
            c.delMechanics(Values.FURTIVIDADE);
        }
    }

    /**
     * Arcanista Etéreo (Se você controlar um Segredo no final do seu turno,
     * receba +2/+2)
     *
     * @param c Card
     */
    private static void ex1_274(Card c) {
        if (partida.getHero().isCard(c.id_long) && partida.getHero().getSegredo().size() > 0) {
            c.getAnimacao().disparoDeEvento();
            c.addVidaMaxima(2);
            c.addAtaque(2);
        }
    }

    /**
     * Ragnaros, o Senhor do Fogo (Não pode atacar. No final do seu turno, cause
     * 8 de dano a um inimigo aleatório)
     *
     * @param c Card
     */
    private static void ex1_298(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            Utils.dano(partida, Utils.getAleatorioOponente(partida), 8);
            c.delMechanics(Values.FURTIVIDADE);
        }
    }

    /**
     * Arvoroso (Investida. No final do turno, destrua este lacaio)
     *
     * @param c Card
     */
    private static void ex1_tk9(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            c.morreu();
        }
    }

    /**
     * Totem de Vagalhão de Mana (No final do seu turno, compre um card)
     *
     * @param c Card
     */
    private static void ex1_575(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().comprarCarta(Card.COMPRA_EVENTO);
        }
    }

    /**
     * Diabrete Mestre (No final do seu turno, cause 1 de dano a este lacaio e
     * evoque um Diabrete 1/1)
     *
     * @param c Card
     */
    private static void ex1_597(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            c.delVida(1);
            c.getDono().evocar(partida.criarCard(Values.DIABRETE1_1T, System.nanoTime()));
            c.delMechanics(Values.FURTIVIDADE);
        }
    }

    /**
     * Kel'Thuzad (No final de cada turno, evoque todos os lacaios aliados que
     * morreram neste turno)
     */
    private static void fp1_013(Card c) {
        c.getAnimacao().disparoDeEvento();
        for (Card lacaio : c.getDono().getLacaiosMortosNaRodada()) {
            c.getDono().evocar(partida.criarCard(lacaio.getId(), System.nanoTime()));
        }
    }

    /**
     * Canhão Vil (No final do seu turno, cause 2 de dano a um lacaio
     * não-Mecanoide)
     *
     * @param c Card
     */
    private static void gvg_020(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            List<Card> lacaios = new ArrayList<>();
            partida.getMesa().stream().filter((mesa) -> (!mesa.isMecanoide())).forEach((mesa) -> {
                lacaios.add(mesa);
            });
            Card aleatorio = Utils.getLacaioAleatorio(lacaios);
            if (aleatorio != null) {
                c.getAnimacao().disparoDeEvento();
                aleatorio.delVida(2);
                c.delMechanics(Values.FURTIVIDADE);
            }
        }
    }

    /**
     * Sensei de Ferro (No fim do seu turno, conceda +2/+2 a outro Mecanoide
     * aliado)
     *
     * @param c Card
     */
    private static void gvg_027(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            Card aleatorio = Utils.getLacaioAleatorio(Filtro.raca(c.getDono().getMesa(), Values.MECANOIDE), c);
            if (aleatorio != null) {
                c.getAnimacao().disparoDeEvento();
                aleatorio.addVidaMaxima(2);
                aleatorio.addAtaque(2);
            }
        }
    }

    /**
     * Totem de Vitalidade (No final do seu turno, recupere 4 de Vida do seu
     * herói)
     *
     * @param c Card
     */
    private static void gvg_039(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().addHealth(4);
        }
    }

    /**
     * Golem Ânima (No final do Turno, destrua este lacaio se ele for o seu
     * único)
     *
     * @param c Card
     */
    private static void gvg_077(Card c) {
        if (c.getDono().getMesa().size() == 1 && c.getDono().getMesa().get(0).equals(c)) {
            c.getAnimacao().disparoDeEvento();
            c.morreu();
        }
    }

    /**
     * Iluminador (Se você controlar um Segredo no final do seu turno, restaure
     * 4 de Vida do seu herói)
     *
     * @param c Card
     */
    private static void gvg_089(Card c) {
        if (partida.getHero().isCard(c.id_long) && c.getDono().getSegredo().size() > 0) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().addHealth(4);
        }
    }

    /**
     * Jarbas (Ao final dos respectivos turnos, cada jogador compra até ter 3
     * cards)
     *
     * @param c Card
     */
    private static void gvg_094(Card c) {
        while (partida.getHero().getMao().size() < 3) {
            c.getAnimacao().disparoDeEvento();
            partida.getHero().comprarCarta(Card.COMPRA_EVENTO);
        }
    }

    /**
     * Destruidor Obsidiano (No final do seu turno, evoque um Escaravelho 1/1
     * com Provocar)
     *
     * @param c Card
     */
    private static void loe_009(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().evocar(partida.criarCard(Values.ESCARAVELHO1_1, System.nanoTime()));
        }
    }

    /**
     * Mestre Armeiro (No fim do seu turno, conceda +1 de Ataque a outro lacaio
     * aliado aleatório)
     *
     * @param c Card
     */
    private static void new1_037(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            Card aleatorio = Utils.getLacaioAleatorio(c.getDono().getMesa(), c);
            if (aleatorio != null) {
                c.getAnimacao().disparoDeEvento();
                aleatorio.addAtaque(1);
            }
        }
    }

    /**
     * Gruul (Ao fim de cada turno, receba +1/+1)
     *
     * @param c Card
     */
    private static void new1_038(Card c) {
        c.getAnimacao().disparoDeEvento();
        c.addVidaMaxima(1);
        c.addAtaque(1);
    }

    /**
     * Hogger (No final do seu turno, evoque um Gnoll 2/2 com Provocar)
     *
     * @param c Card
     */
    private static void new1_040(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            c.getDono().evocar(partida.criarCard(Values.GNOLL2_2PROVOCAR, System.nanoTime()));
        }
    }

    /**
     * Totem de Cura (No fim do seu turno, restaure 1 de Vida de todos os
     * lacaios aliados)
     *
     * @param c Card
     */
    private static void new1_009(Card c) {
        if (partida.getHero().isCard(c.id_long)) {
            c.getAnimacao().disparoDeEvento();
            for (Card mesa : c.getDono().getMesa()) {
                mesa.addVida(1);
            }
        }
    }

}