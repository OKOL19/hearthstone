package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.Utils;
import com.limagiran.hearthstone.partida.control.Pacote;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstone.partida.util.Random;
import com.limagiran.hearthstoneia.server.GameCliente;
import com.limagiran.hearthstone.util.Param;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class PoderHeroicoUtilizado {

    private static Partida partida;

    /**
     * Verifica se há algum evento para poder heroico utilizado (inspirar)
     *
     * @param partida partida verificada
     * @param pacote pacote configurado
     */
    public static void processar(Partida partida, Pacote pacote) {
        PoderHeroicoUtilizado.partida = partida;
        List<Card> lista = new ArrayList<>();
        lista.addAll(partida.getHero().getMesa());
        lista.addAll(partida.getOponente().getSegredo());
        if (partida.isVezHeroi()) {
            for (Card c : lista) {
                if (!c.isSilenciado()) {
                    switch (c.getId()) {
                        //Aspirante de Dalaran (Inspirar: Receba +1 de Dano Mágico)
                        case "AT_006":
                            at_006(c);
                            break;
                        //Cria das Sombras (Inspirar: Cause 4 de dano a cada herói)
                        case "AT_012":
                            at_012(c);
                            break;
                        //Confessora Albamecha (Inspirar: Evoque um lacaio Lendário aleatório)
                        case "AT_018":
                            at_018(c);
                            break;
                        //Esmagador do Caos (Inspirar: Destrua um lacaio aleatório para cada jogador)
                        case "AT_023":
                            at_023(c);
                            break;
                        //Combatente Selvagem (Inspirar: Conceda +2 de Ataque ao seu herói neste turno)
                        case "AT_039":
                            at_039(c);
                            break;
                        //Valente do Penhasco do Trovão (Inspirar: Conceda +2 de Ataque aos seus Totens)
                        case "AT_049":
                            at_049(c);
                            break;
                        //Arqueira Valente (Inspirar: Se a sua mão estiver vazia, cause 2 de dano ao herói inimigo)
                        case "AT_059":
                            at_059(c);
                            break;
                        //Aspirante de Orgrimmar (Inspirar: Conceda +1 de Ataque à sua arma)
                        case "AT_066":
                            at_066(c);
                            break;
                        //Cavaleiro Murloc (Inspirar: Evoque um Murloc aleatório)
                        case "AT_076":
                            at_076(c);
                            break;
                        //Escudeiro Humilde (Inspirar: Receba +1 de Ataque)
                        case "AT_082":
                            at_082(c);
                            break;
                        //Cavalgante de Falcodrago (Inspirar: Receba Fúria dos Ventos neste turno)
                        case "AT_083":
                            at_083(c);
                            break;
                        //Tenente da Ossaguarda (Inspirar: Receba +1 de Vida)
                        case "AT_089":
                            at_089(c);
                            break;
                        //Campeão de Mukla (Inspirar: Conceda +1/+1 aos seus outros lacaios)
                        case "AT_090":
                            at_090(c);
                            break;
                        //Socorrista de Torneio (Inspirar: Restaure 2 de Vida do seu herói)
                        case "AT_091":
                            at_091(c);
                            break;
                        //Cavalga-kodo (Inspirar: Evoque um Kodo de Guerra 3/5)
                        case "AT_099":
                            at_099(c);
                            break;
                        //Regente do Punho de Prata (Inspirar: Evoque um Recruta do Punho de Prata 1/1)
                        case "AT_100":
                            at_100(c);
                            break;
                        //Vigia Argênteo (Não pode atacar. Inspirar: Pode atacar normalmente neste turno)
                        case "AT_109":
                            at_109(c);
                            break;
                        //Gerente do Coliseu (Inspirar: Devolva este lacaio à sua mão)
                        case "AT_110":
                            at_110(c);
                            break;
                        //Recrutador (Inspirar: Adicione um Escudeiro 2/2 à sua mão)
                        case "AT_113":
                            at_113(c);
                            break;
                        //Saqueador Kvaldir (Inspirar: Receba +2/+2)
                        case "AT_119":
                            at_119(c);
                            break;
                        //Campeão do Nexus Saraad (Inspirar: Adicione um feitiço aleatório à sua mão)
                        case "AT_127":
                            at_127(c);
                            break;
                        //Armadilha de Dardos (Segredo: Quando o inimigo usar o Poder Heroico, cause 5 de dano a um inimigo aleatório)
                        case "LOE_021":
                            loe_021(c);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Aspirante de Dalaran (Inspirar: Receba +1 de Dano Mágico)
     */
    private static void at_006(Card c) {
        c.getAnimacao().disparoDeEvento();
        c.addDanoMagico(1);
    }

    /**
     * Cria das Sombras (Inspirar: Cause 4 de dano a cada herói)
     */
    private static void at_012(Card c) {
        c.getAnimacao().disparoDeEvento();
        partida.getHero().delHealth(4);
        partida.getOponente().delHealth(4);
        c.delMechanics(Values.FURTIVIDADE);
    }

    /**
     * Confessora Albamecha (Inspirar: Evoque um lacaio Lendário aleatório)
     */
    private static void at_018(Card c) {
        c.getAnimacao().disparoDeEvento();
        partida.getHero().evocar(partida.criarCard(Random.byRarity(Values.LENDARIO).getId(), System.nanoTime()));
    }

    /**
     * Esmagador do Caos (Inspirar: Destrua um lacaio aleatório para cada
     * jogador)
     */
    private static void at_023(Card c) {
        int random = Utils.random(partida.getHero().getMesa().size()) - 1;
        c.getAnimacao().disparoDeEvento();
        if (random != -1) {
            partida.getHero().getMesa().get(random).morreu();
        }
        random = Utils.random(partida.getOponente().getMesa().size()) - 1;
        if (random != -1) {
            partida.getHero().getMesa().get(random).morreu();
        }
    }

    /**
     * Combatente Selvagem (Inspirar: Conceda +2 de Ataque ao seu herói neste
     * turno)
     */
    private static void at_039(Card c) {
        c.getAnimacao().disparoDeEvento();
        partida.getEfeitoProgramado().concederAtaqueAoHeroiUmTurno(2);
    }

    /**
     * Valente do Penhasco do Trovão (Inspirar: Conceda +2 de Ataque aos seus
     * Totens)
     */
    private static void at_049(Card c) {
        c.getAnimacao().disparoDeEvento();
        for (Card totem : partida.getHero().getMesa()) {
            if (totem.isTotem()) {
                totem.addAtaque(2);
            }
        }
    }

    /**
     * Arqueira Valente (Inspirar: Se a sua mão estiver vazia, cause 2 de dano
     * ao herói inimigo)
     */
    private static void at_059(Card c) {
        if (partida.getHero().getMao().isEmpty()) {
            c.getAnimacao().disparoDeEvento();
            partida.getOponente().delHealth(2);
            c.delMechanics(Values.FURTIVIDADE);
        }
    }

    /**
     * Aspirante de Orgrimmar (Inspirar: Conceda +1 de Ataque à sua arma)
     */
    private static void at_066(Card c) {
        Card arma = partida.getHero().getArma();
        if (arma != null) {
            c.getAnimacao().disparoDeEvento();
            arma.addAtaque(1);
        }
    }

    /**
     * Cavaleiro Murloc (Inspirar: Evoque um Murloc aleatório)
     */
    private static void at_076(Card c) {
        c.getAnimacao().disparoDeEvento();
        partida.getHero().evocar(partida.criarCard(Random.byRace(Values.MURLOC).getId(), System.nanoTime()));
    }

    /**
     * Escudeiro Humilde (Inspirar: Receba +1 de Ataque)
     */
    private static void at_082(Card c) {
        c.getAnimacao().disparoDeEvento();
        c.addAtaque(1);
    }

    /**
     * Cavalgante de Falcodrago (Inspirar: Receba Fúria dos Ventos neste turno)
     */
    private static void at_083(Card c) {
        c.getAnimacao().disparoDeEvento();
        partida.getEfeitoProgramado().concederMecanicaLacaioUmTurno(Values.FURIA_DOS_VENTOS, c.id_long);
    }

    /**
     * Tenente da Ossaguarda (Inspirar: Receba +1 de Vida)
     */
    private static void at_089(Card c) {
        c.getAnimacao().disparoDeEvento();
        c.addVidaMaxima(1);
    }

    /**
     * Campeão de Mukla (Inspirar: Conceda +1/+1 aos seus outros lacaios)
     */
    private static void at_090(Card c) {
        c.getAnimacao().disparoDeEvento();
        for (Card lacaio : c.getDono().getMesa()) {
            if (!lacaio.equals(c)) {
                lacaio.addVidaMaxima(1);
                lacaio.addAtaque(1);
            }
        }
    }

    /**
     * Socorrista de Torneio (Inspirar: Restaure 2 de Vida do seu herói)
     */
    private static void at_091(Card c) {
        c.getAnimacao().disparoDeEvento();
        partida.getHero().addHealth(2);
    }

    /**
     * Cavalga-kodo (Inspirar: Evoque um Kodo de Guerra 3/5)
     */
    private static void at_099(Card c) {
        c.getAnimacao().disparoDeEvento();
        partida.getHero().evocar(partida.criarCard(Values.KODO_DE_GUERRA3_5, System.nanoTime()));
    }

    /**
     * Regente do Punho de Prata (Inspirar: Evoque um Recruta do Punho de Prata
     * 1/1)
     */
    private static void at_100(Card c) {
        c.getAnimacao().disparoDeEvento();
        partida.getHero().evocar(partida.criarCard(Values.RECRUTA_PUNHO_DE_PRATA, System.nanoTime()));
    }

    /**
     * Vigia Argênteo (Não pode atacar. Inspirar: Pode atacar normalmente neste
     * turno)
     */
    private static void at_109(Card c) {
        c.getAnimacao().disparoDeEvento();
        partida.getPodeAtacar().getPodemAtacarNesteTurno().add(c.id_long);
    }

    /**
     * Gerente do Coliseu (Inspirar: Devolva este lacaio à sua mão)
     */
    private static void at_110(Card c) {
        c.getAnimacao().disparoDeEvento();
        partida.voltarPraMaoDoDono(c.id_long);
    }

    /**
     * Recrutador (Inspirar: Adicione um Escudeiro 2/2 à sua mão)
     */
    private static void at_113(Card c) {
        c.getAnimacao().disparoDeEvento();
        partida.getHero().addMao(partida.criarCard(Values.ESCUDEIRO2_2, System.nanoTime()));
    }

    /**
     * Saqueador Kvaldir (Inspirar: Receba +2/+2)
     */
    private static void at_119(Card c) {
        c.getAnimacao().disparoDeEvento();
        c.addVidaMaxima(2);
        c.addAtaque(2);
    }

    /**
     * Campeão do Nexus Saraad (Inspirar: Adicione um feitiço aleatório à sua
     * mão)
     */
    private static void at_127(Card c) {
        c.getAnimacao().disparoDeEvento();
        partida.getHero().addMao(partida.criarCard(Random.feitico().getId(), System.nanoTime()));
    }

    /**
     * Armadilha de Dardos (Segredo: Quando o inimigo usar o Poder Heroico,
     * cause 5 de dano a um inimigo aleatório)
     */
    private static void loe_021(Card segredo) {
        GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
        Utils.dano(partida, Utils.getAlvoAleatorio(partida, segredo.getOponente()), 5);
        SegredoRevelado.processar(segredo);
    }
}