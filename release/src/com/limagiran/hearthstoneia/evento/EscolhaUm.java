package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.Utils;
import com.limagiran.hearthstone.heroi.control.JogouCardException;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstoneia.partida.util.EscolherMesa;
import com.limagiran.hearthstoneia.partida.view.EscolherCard;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class EscolhaUm {

    private static Partida partida;
    private static Card card;
    private static String escolhido;

    /**
     * Verifica se há algum evento para grito de guerra
     *
     * @param card card que gerou o grito de guerra
     * @throws com.limagiran.hearthstone.heroi.control.JogouCardException
     */
    public static void processar(Card card) throws JogouCardException {
        EscolhaUm.card = card;
        escolhido = null;
        EscolhaUm.partida = card.getPartida();
        if (partida.isVezHeroi()) {
            switch (card.getId()) {
                //Druida do Sabre (Escolha um - Transformar-se para receber Investida ou +1/+1 e Furtividade)
                case "AT_042":
                    at_042();
                    break;
                //Druida da Chama (Escolha Um - Transforme num lacaio 5/2; ou num lacaio 2/5)
                case "BRM_010":
                    brm_010();
                    break;
                //Druida da Garra (Escolha Um  - Investida; ou +2 de Vida e Provocar)
                case "EX1_165":
                    ex1_165();
                    break;
                //Guardião do Bosque (Escolha Um - Cause 2 de dano; ou Silencie um lacaio)
                case "EX1_166":
                    ex1_166();
                    break;
                //Anciente da Guerra (Escolha Um - +5 de Ataque; ou +5 de Vida e Provocar)
                case "EX1_178":
                    ex1_178();
                    break;
                //Cenarius (Escolha Um - Conceda +2/+2 aos seus outros lacaios ou evoque dois Arvorosos 2/2 com Provocar)
                case "EX1_573":
                    ex1_573();
                    break;
                //Robozinho Anodizado (Provocar. Escolha Um - +1 de Ataque; ou +1 de Vida)
                case "GVG_030":
                    gvg_030();
                    break;
                //Mantenedora do Bosque (Escolha Um - Conceda um Cristal de Mana a cada jogador; ou Cada jogador compra um card)
                case "GVG_032":
                    gvg_032();
                    break;
                //Anciente da Tradição (Escolha Um - Compre 2 cards; ou Restaure 5 de Vida)
                case "NEW1_008":
                    new1_008();
                    break;
            }
        }
    }

    /**
     * Gera o evento padrão para causar dano em um personagem
     *
     * @param dano valor do dano
     */
    private static void causarDano(int dano) throws JogouCardException {
        long escolhido = EscolherMesa.main(1L, card, "Cause " + dano + " de dano", partida, false);
        if (escolhido != EscolherMesa.CANCEL) {
            Utils.dano(partida, escolhido, dano);
        } else {
            throw new JogouCardException("Escolha Um - cancelado.");
        }
    }

    /**
     * Gera o grito de guerra padrão para restaurar vida de um personagem
     *
     * @param vida vida restaurada
     */
    private static void restaurarVida(int vida) throws JogouCardException {
        long escolhido = EscolherMesa.main(1L, card, "Restaure " + vida + " de vida", partida, false);
        if (escolhido != EscolherMesa.CANCEL) {
            Utils.cura(partida, escolhido, vida);
        } else {
            throw new JogouCardException("Escolha Um - cancelado.");
        }
    }

    /**
     * Gera o evento padrão para silenciar um lacaio
     */
    private static void silencieUmLacaio() throws JogouCardException {
        if (partida.getMesa().size() > 1) {
            long escolhido = EscolherMesa.main(1L, partida.getPartidaView(), card, "Silencie um lacaio",
                    null, null, card.getDono().getMesa(), card.getOponente().getMesa(), false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.findCardByIDLong(escolhido).setSilenciado(true);
            } else {
                throw new JogouCardException("Escolha Um - cancelado.");
            }
        }
    }

    /**
     * Gera o evento padrão para <b>Escolha Um</b>
     *
     * @param ids id's dos cards exibidos na janela de seleção
     * @return true para card selecionado. false para card não selecionado.
     */
    private static boolean escolherCard(String[] ids) throws JogouCardException {
        escolhido = EscolherCard.main("Escolha um", ids);
        if (escolhido != null) {
            return true;
        } else {
            throw new JogouCardException("Escolha Um - cancelado.");
        }
    }

    /**
     * Druida do Sabre (Escolha um - Transformar-se para receber Investida ou
     * +1/+1 e Furtividade)
     */
    private static void at_042() throws JogouCardException {
        if (escolherCard(new String[]{"AT_042a", "AT_042b"})) {
            partida.polimorfia(card.id_long, escolhido.equals("AT_042a") ? "AT_042t" : "AT_042t2", true);
        }
    }

    /**
     * Druida da Chama (Escolha Um - Transforme num lacaio 5/2; ou num lacaio
     * 2/5)
     */
    private static void brm_010() throws JogouCardException {
        if (escolherCard(new String[]{"BRM_010t", "BRM_010t2"})) {
            partida.polimorfia(card.id_long, escolhido, true);
        }
    }

    /**
     * Druida da Garra (Escolha Um - Investida; ou +2 de Vida e Provocar)
     */
    private static void ex1_165() throws JogouCardException {
        if (escolherCard(new String[]{"EX1_165a", "EX1_165b"})) {
            if (escolhido.equals("EX1_165a")) {
                //Forma de Felino (Investida)
                partida.polimorfia(card.id_long, "EX1_165t1", true);
            } else if (escolhido.equals("EX1_165b")) {
                //Forma de Urso (+2 de Vida e Provocar)     
                partida.polimorfia(card.id_long, "EX1_165t2", true);
            }
        }
    }

    /**
     * Guardião do Bosque (Escolha Um - Cause 2 de dano; ou Silencie um lacaio)
     */
    private static void ex1_166() throws JogouCardException {
        if (escolherCard(new String[]{"EX1_166a", "EX1_166b"})) {
            if (escolhido.equals("EX1_166a")) {
                //Fogo Lunar (Cause 2 de dano)     
                causarDano(2);
            } else if (escolhido.equals("EX1_166b")) {
                //Dissipar (Silencie um lacaio)     
                silencieUmLacaio();
            }
        }
    }

    /**
     * Anciente da Guerra (Escolha Um - +5 de Ataque; ou +5 de Vida e Provocar)
     */
    private static void ex1_178() throws JogouCardException {
        if (escolherCard(new String[]{"EX1_178a", "EX1_178b"})) {
            if (escolhido.equals("EX1_178a")) {
                //Enraizado (+5 de Vida e Provocar)
                card.addVidaMaxima(5);
                card.addMechanics(Values.PROVOCAR);
            } else if (escolhido.equals("EX1_178b")) {
                //Desarraigar (+5 de Ataque)
                card.addAtaque(5);
            }
        }
    }

    /**
     * Cenarius (Escolha Um - Conceda +2/+2 aos seus outros lacaios ou evoque
     * dois Arvorosos 2/2 com Provocar)
     */
    private static void ex1_573() throws JogouCardException {
        if (escolherCard(new String[]{"EX1_573a", "EX1_573b"})) {
            if (escolhido.equals("EX1_573a")) {
                //Cenarius (Conceda +2/+2 aos seus outros lacaios)
                card.getDono().getMesa().stream()
                        .filter((lacaio) -> (!lacaio.equals(card)))
                        .forEach((lacaio) -> {
                            lacaio.addVidaMaxima(2);
                            lacaio.addAtaque(2);
                        });
            } else if (escolhido.equals("EX1_573b")) {
                //Cenarius Evoque dois Arvorosos 2/2 com Provocar)
                card.getDono().evocar(card.getDono().getPosicaoNaMesa(card.id_long) + 1, 
                        partida.criarCard(Values.ARVOROSO2_2PROVOCAR, System.nanoTime()));
                card.getDono().evocar(card.getDono().getPosicaoNaMesa(card.id_long) + 1,
                        partida.criarCard(Values.ARVOROSO2_2PROVOCAR, System.nanoTime()));
            }
        }
    }

    /**
     * Robozinho Anodizado (Provocar. Escolha Um - +1 de Ataque; ou +1 de Vida)
     */
    private static void gvg_030() throws JogouCardException {
        if (escolherCard(new String[]{"GVG_030a", "GVG_030b"})) {
            if (escolhido.equals("GVG_030a")) {
                //Modo de Ataque (+1 de Ataque)
                card.addAtaque(1);
            } else if (escolhido.equals("GVG_030b")) {
                // Modo Tanque (+1 de Vida)
                card.addVida(1);
            }
        }
    }

    /**
     * Mantenedora do Bosque (Escolha Um - Conceda um Cristal de Mana a cada
     * jogador; ou Cada jogador compra um card)
     */
    private static void gvg_032() throws JogouCardException {
        if (escolherCard(new String[]{"GVG_032a", "GVG_032b"})) {
            if (escolhido.equals("GVG_032a")) {
                // Dádiva de Mana (Conceda um Cristal de Mana a cada jogador)
                card.getDono().addMana(1);
                card.getOponente().addMana(1);
            } else if (escolhido.equals("GVG_032b")) {
                // Dádiva de Cards (Cada jogador compra um card)     
                card.getDono().comprarCarta(Card.COMPRA_EVENTO);
                card.getOponente().comprarCarta(Card.COMPRA_EVENTO);
            }
        }
    }

    /**
     * Anciente da Tradição (Escolha Um - Compre 2 cards; ou Restaure 5 de Vida)
     */
    private static void new1_008() throws JogouCardException {
        if (escolherCard(new String[]{"NEW1_008a", "NEW1_008b"})) {
            if (escolhido.equals("NEW1_008a")) {
                //Ensinamentos Ancestrais (Compre 2 cards)     
                card.getDono().comprarCarta(Card.COMPRA_EVENTO);
                card.getDono().comprarCarta(Card.COMPRA_EVENTO);
            } else if (escolhido.equals("NEW1_008b")) {
                //Segredos Ancestrais (Restaure 5 de Vida)
                restaurarVida(5);
            }
        }
    }
}