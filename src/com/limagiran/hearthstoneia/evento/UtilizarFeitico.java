package com.limagiran.hearthstoneia.evento;

import com.limagiran.hearthstoneia.card.control.Card;
import com.limagiran.hearthstoneia.Utils;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import com.limagiran.hearthstone.heroi.control.JogouCardException;
import com.limagiran.hearthstone.partida.control.Pacote;
import com.limagiran.hearthstoneia.partida.control.Partida;
import com.limagiran.hearthstone.partida.util.Random;
import static com.limagiran.hearthstone.poder.control.PoderHeroico.*;
import com.limagiran.hearthstoneia.partida.view.EscolherCard;
import com.limagiran.hearthstoneia.server.GameCliente;
import com.limagiran.hearthstone.util.Filtro;
import com.limagiran.hearthstone.util.Param;
import com.limagiran.hearthstone.util.Sort;
import java.util.ArrayList;
import java.util.List;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class UtilizarFeitico {

    private static Partida partida;
    private static Heroi hero;
    private static Heroi oponente;
    private static Card feitico;
    private static Card alvoCard;
    private static Heroi alvoHeroi;
    private static String MENSAGEM;
    public static boolean jogouCardProcessado = false;
    private static String escolhido = null;
    private static Pacote pack;

    /**
     * Gera o evento para o feitiço utilizado
     *
     * @param card feitiço utilizado
     * @return {@code true} para feitiço utilizado. {@code false} o contrário.
     */
    public static void processar(Card card) throws JogouCardException {
        UtilizarFeitico.partida = card.getPartida();
        pack = partida.getJogada().pack;
        UtilizarFeitico.feitico = card;
        hero = partida.getHero();
        oponente = partida.getOponente();
        if (partida.isVezHeroi()) {
            jogouCardProcessado = false;
            //JogouCard.processar(card); // <<<<<<<<<<<<<<<<<<<<<<<<<<<<< logo após confirmar alvo
            alvoCard = null;
            alvoHeroi = null;
            MENSAGEM = hero.getNome() + " utilizou " + UtilizarFeitico.feitico.getName() + " - " + UtilizarFeitico.feitico.getDescricao();
            switchFeitico(UtilizarFeitico.feitico.getId());
            if (!MENSAGEM.isEmpty()) {
                GameCliente.addHistorico(MENSAGEM, true);
            }
            alvoCard = partida.findCardByIDLong(partida.getJogada().pack.getParamLong(Param.ALVO));
            if (alvoCard != null) {
                LacaioEnfeiticado.processar(alvoCard, UtilizarFeitico.feitico);
            }
            if (!jogouCardProcessado) {
                JogouCard.processar(UtilizarFeitico.feitico);
            }
        }
        alvoCard = null;
        alvoHeroi = null;
    }

    private static void switchFeitico(String id) throws JogouCardException {
        switch (id) {
            //moeda
            case "GAME_005":
                game_005();
                break;
            //Excesso de Mana (Compre um card. (Você só pode ter 10 de Mana na sua mesa.))
            case "CS2_013t":
                cs2_013t();
                break;
            //Lança de Fogo
            case "AT_001":
                at_001();
                break;
            //Impacto Arcano
            case "AT_004":
                at_004();
                break;
            //Polimorfia: Javali (Transforme um lacaio em um Javali 4/2 com Investida).
            case "AT_005":
                at_005();
                break;
            //Palavra de Poder: Glória (Escolha um lacaio. Sempre que ele atacar, restaure 4 de Vida do seu herói.
            case "AT_013":
                at_013();
                break;
            //Converter (Coloque uma cópia de um lacaio inimigo na sua mão).
            case "AT_015":
                at_015();
                break;
            //Confundir (Troque o Ataque pela Vida de todos os lacaios).
            case "AT_016":
                at_016();
                break;
            //Punho de Jaraxxus (Ao jogar ou descartar este card, cause 4 de dano a um inimigo aleatório)
            case "AT_022":
                at_022();
                break;
            //Fusão Demoníaca (Conceda +3/+3 a um Demônio. Conceda um Cristal de Mana ao seu oponente.
            case "AT_024":
                at_024();
                break;
            //Barganha Negra (Destrua 2 lacaios inimigos aleatórios. Descarte 2 cards aleatórios)
            case "AT_025":
                at_025();
                break;
            //Roubar (Adicione 2 cards de classe aleatórios à sua mão (da classe do seu oponente))
            case "AT_033":
                at_033();
                break;
            //Debaixo da Terra (Embaralhe 3 Emboscadas no deck do seu oponente. Quando ele comprar, você evoca um Nerubiano 4/4)
            case "AT_035":
                at_035();
                break;
            //Emboscada! (Ao comprar este card, evoque um Nerubiano 4/4 para o seu oponente. Compre um card)
            case "AT_035t":
                at_035t();
                break;
            //Raízes Vivas (Escolha um - Causar 2 de dano ou evocar dois Brotos 1/1)
            case "AT_037":
                at_037();
                break;
            //Comunhão Astral (Receba 10 cristais de Mana. Descarte a sua mão)
            case "AT_043":
                at_043();
                break;
            //Húmus (Destrua um lacaio. Adicione um lacaio aleatório à mão do seu oponente)
            case "AT_044":
                at_044();
                break;
            //Onda Curativa (Restaure 7 de Vida. Revele um lacaio em cada deck. Se o seu custar mais, em vez disso, restaure 14)
            case "AT_048":
                at_048();
                break;
            //Destruição Elemental (Cause 4-5 de dano a todos os lacaios. Sobrecarga: 5)
            case "AT_051":
                at_051();
                break;
            //Conhecimento Ancestral (Compre 2 cards. Sobrecarga 2)
            case "AT_053":
                at_053();
                break;
            //Cura Célere (Restaude 5 de vida)
            case "AT_055":
                at_055();
                break;
            //Tirambaço (Cause 2 de dano a um lacaio e aos lacaios adjacentes)
            case "AT_056":
                at_056();
                break;
            //Largar o Dedo (Cada vez que você lançar um feitiço neste turno, adicione um card de Caçador aleatório à sua mão)
            case "AT_061":
                at_061();
                break;
            //Bola de Aranhas (Evoque três Teceteias 1/1)
            case "AT_062":
                at_062();
                break;
            //Trombar (Cause 3 de dano. Receba 3 de Armadura)
            case "AT_064":
                at_064();
                break;
            //Fortalecer (Concede +2/+2 aos seus lacaios com Provocar)
            case "AT_068":
                at_068();
                break;
            //Selo dos Campeões (Conceda +3 de Ataque e Escudo Divino a um lacaio)
            case "AT_074":
                at_074();
                break;
            //Entrada no Coliseu (Destrua todos os lacaios, menos o lacaio com maior Ataque de cada jogador)
            case "AT_078":
                at_078();
                break;
            //Vigília Solene (Compre 2 cards. Custa 1 a menos para cada lacaio morto neste turno)
            case "BRM_001":
                brm_001();
                break;
            //Sopro do Dragão (Causa 4 de dano. Custa 1 a menos para cada lacaio morto neste turno)
            case "BRM_003":
                brm_003();
                break;
            //Ira Demoníaca (Cause 2 de dano a todos os lacaios, menos aos Demônios)
            case "BRM_005":
                brm_005();
                break;
            //Formação de Quadrilha (Escolha um lacaio. Embaralhe 3 cópias dele no seu deck)
            case "BRM_007":
                brm_007();
                break;
            //Choque de Lava (Cause 2 de dano. Desbloqueie Cristais de Mana Sobrecarregados)
            case "BRM_011":
                brm_011();
                break;
            //Disparo Veloz (Cause 3 de dano. Se a sua mão estiver vazia, compre um card)
            case "BRM_013":
                brm_013();
                break;
            //Revanche (Cause 1 de dano a todos os lacaios. Se você tiver 12 pontos de vida ou menos, em vez disso, cause 3 de dano)
            case "BRM_015":
                brm_015();
                break;
            //Ressuscitar (Evoque um lacaio aliado aleatório morto neste jogo)
            case "BRM_017":
                brm_017();
                break;
            //Nova Sagrada (Cause 2 de dano a todos os inimigos. Restaude 2 de Vida de todos os personagens aliados)
            case "CS1_112":
                cs1_112();
                break;
            //Controle Mental (Assuma o controle de um lacaio inimigo)
            case "CS1_113":
                cs1_113();
                break;
            //Fogo Interior (Mude o Ataque de um lacaio para ser igual à Vida dele)
            case "CS1_129":
                cs1_129();
                break;
            //Punição Sagrada (Cause 2 de dano)
            case "CS1_130":
                cs1_130();
                break;
            //Visão da Mente (Coloque uma cópia de um card aleatório da mão do seu oponente na sua mão)
            case "CS2_003":
                cs2_003();
                break;
            //Palavra de Poder: Escudo (Conceda +2 de Vida a um lacaio. Compre um card)
            case "CS2_004":
                cs2_004();
                break;
            //Garra (Conceda +2 de Ataque ao seu herói neste turno e 2 de Armadura
            case "CS2_005":
                cs2_005();
                break;
            //Toque de Cura (Restaure 8 de Vida)
            case "CS2_007":
                cs2_007();
                break;
            //Fogo Lunar (Cause 1 de dano)
            case "CS2_008":
                cs2_008();
                break;
            //Marca do Indomado (Conceda Provodar e +2/+2 a um lacaio)
            case "CS2_009":
                cs2_009();
                break;
            //Rugido Selvagem (Conceda +2 de Ataque aos seus personagens neste turno)
            case "CS2_011":
                cs2_011();
                break;
            //Patada (Cause 4 de dano a um inimigo e 1 de dano a todos os outros inimigos)
            case "CS2_012":
                cs2_012();
                break;
            //Crescimento Silvestre (Receba um Cristal de Mana vazio)
            case "CS2_013":
                cs2_013();
                break;
            //Polimorfia (Transforme um lacaio em uma Ovelha 1/1)
            case "CS2_022":
                cs2_022();
                break;
            //Intelecto Arcano (Compre 2 cards)
            case "CS2_023":
                cs2_023();
                break;
            //Seta de Gelo (Cause 3 de dano a um personage e Congele-o)
            case "CS2_024":
                cs2_024();
                break;
            //Explosão Arcana (Cause 1 de dano a todos os lacaios inimigos)
            case "CS2_025":
                cs2_025();
                break;
            //Nova Congelante (Congele todos os lacaios inimigos)
            case "CS2_026":
                cs2_026();
                break;
            //Imagem Espelhada (Evoque dois lacaios 0/2 com Provocar)
            case "CS2_027":
                cs2_027();
                break;
            //Nevasca (Cause 2 de dano a todos os lacaios inimigos e Congele-os)
            case "CS2_028":
                cs2_028();
                break;
            //Bola de Fogo (Cause 6 de dano)
            case "CS2_029":
                cs2_029();
                break;
            //Lança de Gelo (Congele um personagem. Se ele já estiver Congelado, cause 4 de dano)
            case "CS2_031":
                cs2_031();
                break;
            //Golpe Flamejante (Cause 4 de dano a todos os lacaios inimigos)
            case "CS2_032":
                cs2_032();
                break;
            //Choque Gélido (Cause 1 de dano a um personagem inimigo e Congele-o)
            case "CS2_037":
                cs2_037();
                break;
            //Espírito Ancestral (Conceda a um lacaio: "Último Suspiro: Evoque este lacaio novamente")
            case "CS2_038":
                cse_038();
                break;
            //Fúria dos Ventos (Conceda Fúria dos Ventos a um lacaio)
            case "CS2_039":
                cs2_039();
                break;
            //Cura Ancestral (Restaure toda a Vida de um lacaio e conceda Provocar a ele)
            case "CS2_041":
                cs2_041();
                break;
            //Arma Trinca-pedra (Conceda +3 de Ataque a um aliado neste turno)
            case "CS2_045":
                cs2_045();
                break;
            //Sede de Sangue (Conceda +3 de Ataque aos seus lacaios neste turno)
            case "CS2_046":
                cs2_046();
                break;
            //Visão Distante (Compre um card. Este card custa 3 a menos)
            case "CS2_053":
                cs2_053();
                break;
            //Seta Sombria (Cause 4 de dano a um lacaio)
            case "CS2_057":
                cs2_057();
                break;
            //Drenar Vida (Cause 2 de dano. Restaure 2 de Vida do seu herói)
            case "CS2_061":
                cs2_061();
                break;
            //Fogo do Inferno (Cause 3 de dano a TODOS os personagens)
            case "CS2_062":
                cs2_062();
                break;
            //Corrupção (Escolha um lacaio inimigo. No início do seu turno, destrua-o;
            case "CS2_063":
                cs2_063();
                break;
            //Punhalada pelas Costas (Cause 2 de dano a um lacaio sem ferimentos)
            case "CS2_072":
                cs2_072();
                break;
            //Sangue Frio (Conceda +2 de Ataque a um lacaio. Combo: +4 de Ataque em vez de +2)
            case "CS2_073":
                cs2_073();
                break;
            //Veneno Mortal (Conceda +2 de Ataque à sua arma)
            case "CS2_074":
                cs2_074();
                break;
            //Golpe Sinistro (Cause 3 de dano ao herói inimigo)
            case "CS2_075":
                cs2_075();
                break;
            //Assassinar (Destrua um lacaio inimigo)
            case "CS2_076":
                cs2_076();
                break;
            //Disparada (Compre 4 cards)
            case "CS2_077":
                cs2_077();
                break;
            //Marca do Caçador (muda a Vida de um lacaio para 1)
            case "CS2_084":
                cs2_084();
                break;
            //Bênção do Poder (Conceda +3 de Ataque a um lacaio)
            case "CS2_087":
                cs2_087();
                break;
            //Luz Sagrada (Restaure 6 de Vida)
            case "CS2_089":
                cs2_089();
                break;
            //Bênção dos Reis (Conceda +4/+4 a um lacaio)
            case "CS2_092":
                cs2_092();
                break;
            //Consagração (Cause 2 de danoa  todos os inimigos)
            case "CS2_093":
                cs2_093();
                break;
            //Martelo da Ira (Cause 3 de dano. Compre um card)
            case "CS2_094":
                cs2_094();
                break;
            //Investida (Conceda +2 de Ataque e Investida a um lacaio aliado)
            case "CS2_103":
                cs2_103();
                break;
            //Alvoroço (Conceda +3/+3 a um lacaio ferido)
            case "CS2_104":
                cs2_104();
                break;
            //Golpe Heroico (Conceda +4 de Ataque ao seu herói neste turno)
            case "CS2_105":
                cs2_105();
                break;
            //Executar (Destrua um lacaio inimigo que tenha recebido dano)
            case "CS2_108":
                cs2_108();
                break;
            //Cutilada (Cause 2 de dano a dois lacaios inimigos aleatórios)
            case "CS2_114":
                cs2_114();
                break;
            //Rajada de Lâminas (Destrua sua arma e cause o dano dela a todos os inimigos)
            case "CS2_233":
                cs2_233();
                break;
            //Palavra Sombria: Dor (Destrua um lacaio com 3 ou menos de Ataque)
            case "CS2_234":
                cs2_234();
                break;
            //Espírito Divino (Dobre a Vida de um lacaio)
            case "CS2_236":
                cs2_236();
                break;
            //Despertar de Ysera (Cause 5 de dano a todos os personagens, exceto Ysera)
            case "DREAM_02":
                dream_02();
                break;
            //Sonho (Devolva um lacaio à mão do dono)
            case "DREAM_04":
                dream_04();
                break;
            //Pesadelo (Conceda +5/+5 a um lacaio. No início do próximo turno, destrua-o)
            case "DREAM_05":
                dream_05();
                break;
            //Tiro Múltiplo (Cause 3 de dano a dois lacaios inimigos aleatórios)
            case "DS1_183":
                ds1_183();
                break;
            //Rastreamento (Olhe os três cards do topo do seu deck. Compre um e descarte os outros)
            case "DS1_184":
                ds1_184();
                break;
            //Tiro Arcano (Cause 2 de dano)
            case "DS1_185":
                ds1_185();
                break;
            //Impacto Mental (Cause 5 de dano ao herói inimigo)
            case "DS1_233":
                ds1_233();
                break;
            //Bananas (Conceda +1/+1 a um lacaio)
            case "EX1_014t":
                ex1_014t();
                break;
            //Eviscerar (Cause 2 de dano. Combo: Em vez de 2 cause 4 de dano)
            case "EX1_124":
                ex1_124();
                break;
            //Traição (Force um lacaio inimigo a causar dano aos lacaios perto dele)
            case "EX1_126":
                ex1_126();
                break;
            //Ocultar (Conceda Furtividade aos seus lacaios até o próximo turno)
            case "EX1_128":
                ex1_128();
                break;
            //Leque de Facas (Cause 1 de dano a todos os lacaios inimigos. Compre um card)
            case "EX1_129":
                ex1_129();
                break;
            //Quebra-crânio (Cause 2 de dano ao herói inimigo. Combo: Devolva este card à sua mão no próximo turno)
            case "EX1_136":
                ex1_136();
                break;
            //Passo Furtivo (Devolva um lacaio aliado à sua mão. Ele custa 2 a menos)
            case "EX1_144":
                ex1_144();
                break;
            //Preparação (O próximo feitiço que você lançar neste turno custará 3 a menos)
            case "EX1_145":
                ex1_145();
                break;
            //Ira (Escolha Um - Cause 3 de dano a um lacaio; ou cause 1 de dano e compre um card)
            case "EX1_154":
                ex1_154();
                break;
            //Marca da Natureza (Escolha Um - Conceda +4 de Ataque a um lacaio; ou +4 de Vida e Provocar)
            case "EX1_155":
                ex1_155();
                break;
            //Alma da Floresta (Conceda aos seus lacaios: Último Suspiro: Evoque um Arvoroso 2/2)
            case "EX1_158":
                ex1_158();
                break;
            //Poder da Selva (Escolha Um - Conceda +1/+1 aos seus lacaios ou evoque uma Pantera 3/2)
            case "EX1_160":
                ex1_160();
                break;
            //Naturalizar (Destrua um lacaio. Seu oponente compra dois cards)
            case "EX1_161":
                ex1_161();
                break;
            //Nutrir (Escolha Um  - Receba 2 Cristais de Mana; ou Compre 3 cards)
            case "EX1_164":
                ex1_164();
                break;
            //Avivar (Receba 2 Cristais de Mana somente neste turno)
            case "EX1_169":
                ex1_169();
                break;
            //Fogo Estelar (Cause 5 de dano. Compre um card)
            case "EX1_173":
                ex1_173();
                break;
            //Raio (Cause 3 de dano. Sobrecarga: 1)
            case "EX1_238":
                ex1_238();
                break;
            //Estouro de Lava (Cause 5 de dano. Sobrecarga 2)
            case "EX1_241":
                ex1_241();
                break;
            //Poder Totêmico (Conceda +2 de Vida aos seus Totens)
            case "EX1_244":
                ex1_244();
                break;
            //Choque Terreno (Silencie um lacaio, então cause 1 de dano a ele)
            case "EX1_245":
                ex1_245();
                break;
            //Bagata (Transforme um lacaio num Sapo 0/1 com Provocar)
            case "EX1_246":
                ex1_246();
                break;
            //Espírito Feral (Evoque dois Lobos Espirituais 2/3 com Provocar. Sobrecarga 2)
            case "EX1_248":
                ex1_248();
                break;
            //Raio Bifurcado (Cause 2 de dano a 2 lacaios inimigos aleatórios. Sobrecarga 2)
            case "EX1_251":
                ex1_251();
                break;
            //Tempestade de Raios (Cause 2-3 de dano a todos os lacaios inimigos. Sobrecarga 2)
            case "EX1_259":
                ex1_259();
                break;
            //Cone de Frio (Congele um lacaio e os lacaios vizinhos, causando-lhes 1 de dano)
            case "EX1_275":
                ex1_275();
                break;
            //Mísseis Arcanos (Cause 3 de dano dividido aleatoriamente entre todos os inimigos)
            case "EX1_277":
                ex1_277();
                break;
            //Estocada (Cause 1 de dano. Compre um card)
            case "EX1_278":
                ex1_278();
                break;
            //Ignimpacto (Cause 10 de dano)
            case "EX1_279":
                ex1_279();
                break;
            //Espiral da Morte (Cause 1 de dano a um lacaio. Se ele morrer, compre um card)
            case "EX1_302":
                ex1_302();
                break;
            //Chama Sombria (Destrua um lacaio aliado e cause o dano de ataque dele a todos os lacaios inimigos)
            case "EX1_303":
                ex1_303();
                break;
            //Fogo 'Alma (Cause 4 de dano. Descarte um card aleatório)
            case "EX1_308":
                ex1_308();
                break;
            //Sifão da Alma (Destrua um lacaio. Restaure 3 de Vida do seu herói)
            case "EX1_309":
                ex1_309();
                break;
            //Espiral Éterea (Destrua todos os lacaios)
            case "EX1_312":
                ex1_312();
                break;
            //Poder Esmagador (Conceda +4/+4 a um lacaio aliado até o fim do turno. Depois, ele morre. Uma morte horrível)
            case "EX1_316":
                ex1_316();
                break;
            //Detectar Demônios (Coloque 2 Demônios aleatórios do seu deck na sua mão)
            case "EX1_317":
                ex1_317();
                break;
            //Ruína do Destino (Cause 2 de dano a um personagem. Se ele morrer, evoque um Demônio aleatório)
            case "EX1_320":
                ex1_320();
                break;
            //Silêncio (Silencie um lacaio)
            case "EX1_332":
                ex1_332();
                break;
            //Insanidade Sombria (Assuma o controle de um lacaio inimigo com 3 ou menos de Ataque até o final do turno)
            case "EX1_334":
                ex1_334();
                break;
            //Roubo de Pensamentos (Copie 2 cards do deck do oponente e ponha-os na sua mão)
            case "EX1_339":
                ex11_339();
                break;
            //Jogos Mentais (Coloque uma cópia de um lacaio aleatório do deck do oponente no campo de batalha)
            case "EX1_345":
                ex1_345();
                break;
            //Favorecimento Divino (Compre cards até sua mão ficar tão cheia quanto a do oponente)
            case "EX1_349":
                ex1_349();
                break;
            //Impor as Mãos (Restaure 8 de Vida. Compre 3 cards)
            case "EX1_354":
                ex1_354();
                break;
            //Campeão Abençoado (Dobre o Ataque de um lacaio)
            case "EX1_355":
                ex1_355();
                break;
            //Humildade (Mude o Ataque de um lacaio para 1)
            case "EX1_360":
                ex1_360();
                break;
            //Bênção de Sabedoria (Escolha um lacaio. Sempre que ele atacar, compre um card)
            case "EX1_363":
                ex1_363();
                break;
            //Ira Sagrada (Compre um card e cause dano equivalente ao custo dele)
            case "EX1_365":
                ex1_365();
                break;
            //Mão da Proteção (Conceda Escudo Divino a um lacaio)
            case "EX1_371":
                ex1_371();
                break;
            //Ira Vingativa (Cause 8 de dano dividido aleatoriamente entre todos os inimigos)
            case "EX1_384":
                ex1_384();
                break;
            //Batida (Cause 2 de dano a um lacaio. Se ele sobreviver, compre um card)
            case "EX1_391":
                ex1_391();
                break;
            //Ira de Batalha (Compre um card para cada personagem aliado ferido)
            case "EX1_392":
                ex1_392();
                break;
            //Redemoinho (Cause 1 de dano a todos os lacaios)
            case "EX1_400":
                ex1_400();
                break;
            //Briga (Destrua todos os lacaios, menos um. (escolhido aleatoriamente))
            case "EX1_407":
                ex1_407();
                break;
            //Golpe Mortal (Cause 4 de dano. Se você tiver 12 pontos de vica ou menos, cause 6 de dano em vez de 4)
            case "EX1_408":
                ex1_408();
                break;
            //Aprimoramento (Se você tiver uma arma, conceda-lhe +1/+1. Se não tiver, equipe uma arma 1/3)
            case "EX1_409":
                ex1_409();
                break;
            //Escudada (Cause 1 de dano a um lacaio para cada ponto de Armadura que você tiver)
            case "EX1_410":
                ex1_410();
                break;
            //Tiro Explosivo (Cause 5 de dano a um lacaio e 2 aos adjacentes)
            case "EX1_537":
                ex1_537();
                break;
            //Soltem os Cães (Para cada lacaio inimigo, evoque um Cão 1/1 com Investida)
            case "EX1_538":
                ex1_538();
                break;
            //Comando para Matar (Cause 3 de dano. Se você tiver uma Fera, cause 5 de dano em vez de 3)
            case "EX1_539":
                ex1_539();
                break;
            //Sinalizador (Todos os lacaios perdem Furtividade. Destrua todos os Segredos do inimigo. Compre um card)
            case "EX1_544":
                ex1_544();
                break;
            //Ira Bestial (Conceda +2 de Ataque e Imunidade a uma Fera aliada neste turno)
            case "EX1_549":
                ex1_549();
                break;
            //Morder (Conceda +4 de Ataque ao seu herói neste turno e 4 de Armadura)
            case "EX1_570":
                ex1_570();
                break;
            //Força da Natureza (Evoque três Arvorosos 2/2 com Investida, que Morrerão no fim do turno)
            case "EX1_571":
                ex1_571();
                break;
            //Selvageria (Cause dano equivalente ao Ataque do seu herói a um lacaio)
            case "EX1_578":
                ex1_578();
                break;
            //Aturdir (Devolva um lacaio inimigo à mão do seu oponente)
            case "EX1_581":
                ex1_581();
                break;
            //Fogo Demoníaco (Cause 2 de dano a um lacaio. Se for um Demônio aliado, em vez disso, conceda-lhe +2/+2)
            case "EX1_596":
                ex1_596();
                break;
            //Levantar Escudo (Receba 5 de Armadura. Compre um card)
            case "EX1_606":
                ex1_606();
                break;
            //Raiva Interior (Cause 1 de dano a um lacaio e conceda-lhe + 2 de Ataque)
            case "EX1_607":
                ex1_607();
                break;
            //Disparo Mortal (Destrua um lacaio inimigo aleatório)
            case "EX1_617":
                ex1_617();
                break;
            //Igualdade (Mude a Vida de Todos os lacaios para 1)
            case "EX1_619":
                ex1_619();
                break;
            //Círculo de Cura (Restaure 4 de Vida de TODOS os lacaios)
            case "EX1_621":
                ex1_621();
                break;
            //Palavra Sombria: Morte (Destrua um lacaio com 5 ou mais de Ataque)
            case "EX1_622":
                ex1_622();
                break;
            //Fogo Sagrado (Cause 5 de dano. Restaure 5 de Vida do seu herói)
            case "EX1_624":
                ex1_624();
                break;
            //Forma de Sombra (Seu Poder Heroico se torna "Cause 2 de dano". Se você já estiver em Forma de Sombra: 3 de dano)
            case "EX1_625":
                ex1_625();
                break;
            //Dissipação em Massa (Silencie todos os lacaios inimigos. Compre um card)
            case "EX1_626":
                ex1_626();
                break;
            //Sementes de Veneno (Destrua todos os lacaios e evoque Arvorosos 2/2 para substituí-los)
            case "FP1_019":
                fp1_019();
                break;
            //Reencarnar (Destrua um lacaio, depois ressuscite-o com a Vida completa)
            case "FP1_025":
                fp1_025();
                break;
            //Canhão de Chamas (Cause 4 de dano a um lacaio inimigo aleatório)
            case "GVG_001":
                gvg_001();
                break;
            //Portal Instável (Adicione um lacaio aleatório à sua mão. Ele custa 3 a menos)
            case "GVG_003":
                gvg_003();
                break;
            //Eco de Medivh (Coloque uma cópia de cada lacaio aliado na sua mão)
            case "GVG_005":
                gvg_005();
                break;
            //Bomba Luminosa (Cause dano a todos os lacaios igual ao Ataque de cada um)
            case "GVG_008":
                gvg_008();
                break;
            //Escolhido de Velen (Conceda +2/+4 e +1 de Dano Mágico a um lacaio)
            case "GVG_010":
                gvg_010();
                break;
            //Luz dos Naarus (Restaure 3 de Vida. Se o alvo ainda estiver ferido, evoque uma Guardiã da Luz)
            case "GVG_012":
                gvg_012();
                break;
            //Bomba Negra (Cause 3 de dano)
            case "GVG_015":
                gvg_015();
                break;
            //Chamar Ajudante (Compre um card. Se for uma Fera, ela custará 4 a menos)
            case "GVG_017":
                gvg_017();
                break;
            //Óleo de Arma do Faz-tudo (Conceda +3 de Ataque à sua arma. Combo: Conceda +3 de Ataque a um lacaio aliado aleatório)
            case "GVG_022":
                gvg_022();
                break;
            //Fingir de Morto (Ative todos os Últimos Suspiros dos seus lacaios)
            case "GVG_026":
                gvg_026();
                break;
            //Moeda de Gallywix (Receba 1 Cristal de Mana somente neste turno (Não ativa Gallywix)
            case "GVG_028t":
                gvg_028t();
                break;
            //Chamado do Ancestral (Coloque um lacaio aleatório da mão de cada jogador no campo de batalha)
            case "GVG_029":
                gvg_029();
                break;
            //Reciclar (Coloque um lacaio inimigo no deck do seu oponente)
            case "GVG_031":
                gvg_031();
                break;
            //Árvore da Vida (Restaure toda a Vida de todos os personagens)
            case "GVG_033":
                gvg_033();
                break;
            //Blindagem (Conceda +1 de Vida a um lacaio)
            case "PART_001":
                part_001();
                break;
            //Retroceder Tempo (Devolva um lacaio aliado à sua mão)
            case "PART_002":
                part_002();
                break;
            //Chifre Enferrujado (Conceda Provocar a um lacaio)
            case "PART_003":
                part_003();
                break;
            //Camuflagem Instável (Conceda Furtividade a um lacaio aliado até o seu próximo turno)
            case "PART_004":
                part_004();
                break;
            //Resfriador de Emergência (Congele um lacaio)
            case "PART_005":
                part_005();
                break;
            //Chave Reversora (Troque o Ataque pela Vida de um lacaio)
            case "PART_006":
                part_006();
                break;
            //Lâminas Rodopiantes (Conceda +1 de Ataque a um lacaio)
            case "PART_007":
                part_007();
                break;
            //Estalar (Cause de 3 a 6 de dano. Sobrecarga 1)
            case "GVG_038":
                gvg_038();
                break;
            //Fogo Enfático (Escolha Um - Evoque 5 Fogos-fátuos ou Conceda +5/+5 e Provocar a um lacaio)
            case "GVG_041":
                gvg_041();
                break;
            //Implosão de Diabrete (Cause 2-4 de dano a um lacaio. Evoque um Diabrete 1/1 para cada ponto de dano causado)            
            case "GVG_045":
                gvg_045();
                break;
            //Sabotagem (Destrua um lacaio inimigo aleatório. Combo: E a arma do seu oponente)
            case "GVG_047":
                gvg_047();
                break;
            //Lâmina Quicante (Cause 1 de dano a um lacaio aleatório. Repita até um lacaio morrer)
            case "GVG_050":
                gvg_050();
                break;
            //Esmagar (Destrua um lacaio. Se você tiver um lacaio ferido, este feitiço custa 4 a menos)
            case "GVG_052":
                gvg_052();
                break;
            //Selo da Luz (Restaure 4 de Vida do seu herói e ganhe +2 de Ataque neste turno)
            case "GVG_057":
                gvg_057();
                break;
            //Preparação de Batalha (Evoque três Recrutas do Punho de Prata 1/1. Equipe uma Arma 1/4)
            case "GVG_061":
                gvg_061();
                break;
            //Disparo da Naja (cause 3 de dano a um lacaio e ao herói inimigo)
            case "GVG_073":
                gvg_073();
                break;
            //Tocha Esquecida (Causa 3 de dano. Embaralhe no seu deck uma "Tocha Voraz", que causa 6 de dano)
            case "LOE_002":
                loe_002();
                break;
            //Tocha Voraz (Cause 6 de dano)
            case "LOE_002t":
                loe_002t();
                break;
            //Murloucura (Evoque 7 Murlocs mortos nesta partidaView)
            case "LOE_026":
                loe_026();
                break;
            //Mapa do Macaco Dourado (Embaralhe o Macaco Dourado no seu deck. Compre um card)
            case "LOE_019t":
                loe_019t();
                break;
            //Lanterna do Poder (Conceda +10/+10 a um lacaio)
            case "LOEA16_3":
                loea16_3();
                break;
            //Medidor de Tempo do Terror (Cause 10 de dano dividido aleatoriamente entre todos os inimigos)
            case "LOEA16_4":
                loea16_4();
                break;
            //Espelho da Ruína (Encha seu tabuleiro com Múmias Zumbis 3/3)
            case "LOEA16_5":
                loea16_5();
                break;
            //Sepultar (Escolha um lacaio inimigo. Embaralhe-o no seu deck)
            case "LOE_104":
                loe_104();
                break;
            //Chapéu de Explorador (Conceda +1/+1 a um lacaio e "Último Suspiro: Adicione um Chapéu de Explorador à sua mão")
            case "LOE_105":
                loe_105();
                break;
            //Mal Desencavado (Cause 3 de dano a todos os lacaios. Embaralhe este card no deck do seu oponente)
            case "LOE_111":
                loe_111();
                break;
            //Retoque de Murloc (Conceda aos seus lacaios +2/+2. Custa 1 a menos por cada Murloc sob seu controle)
            case "LOE_113":
                loe_113();
                break;
            //Pacto Sacrificial (Destrua um Demônio. Restaure 5 de Vida do seu herói)
            case "NEW1_003":
                new1_003();
                break;
            //Sumir (Devolva todos os lacaios à mão do dono)
            case "NEW1_004":
                new1_004();
                break;
            //Chuva Estelar (Escolha Um - Cause 5 de dano a um lacaio ou 2 de dano a todos os lacaios inimigos)
            case "NEW1_007":
                new1_007();
                break;
            //Companheiro Animal (Evoque um Fera Companheira aleatória)
            case "NEW1_031":
                new1_031();
                break;
            //Brado de Comando (A vida de seus lacaios não pode ser reduzida a menos de 1 neste turno. Compre um card)
            case "NEW1_036":
                new1_036();
                break;
            //Eu sou Murloc (Evoca três, quatro ou cinco Murlocs 1/1)
            case "PRO_001a":
                pro_001a();
                break;
            //Coisas de Ladino (Cause 4 de dano. Compre um card)
            case "PRO_001b":
                pro_001b();
                break;
            //Poder da Horda (Evoque um Guerreiro da Horda aleatório)
            case "PRO_001c":
                pro_001c();
                break;
        }
    }

    /**
     * Ativa o feitiço para o alvo passado por parâmetro
     *
     * @param partida
     * @param id id do feitiço
     * @param alvo código do alvo (id_long do card ou Param.HEROI ou
     * Param.OPONENTE)
     */
    public static void switchFeitico(Partida partida, String id, long alvo) throws JogouCardException {
        UtilizarFeitico.partida = partida;
        pack = partida.getJogada().pack;
        pack.set(Param.ALVO, alvo);
        switchFeitico(id);
    }

    /**
     * Verifica se o feitiço foi anulado ou alterado. Altera o alvo da jogada
     * atual
     *
     * @return true para feitiço anulado e false para feitiço não anulado
     */
    private static boolean anularAlterarFeitico() throws JogouCardException {
        GameCliente.setPodeEnviar(true);
        List<Card> list = new ArrayList<>();
        for (Card segredo : oponente.getSegredo()) {
            list.add(segredo);
        }
        for (int i = 0; i < list.size(); i++) {
            switch (list.get(i).getId()) {
                //Contrafeitiço (Segredo: Quando seu oponente lançar um feitiço, Anule-o)
                case "EX1_287":
                    return ex1_287(list.get(i));
                //Dobradora de Feitiços (Segredo: Quando um inimigo lançar um feitiço em um lacaio, evoque um 1/3 para servir de alvo
                case "tt_010":
                    if (list.get(i).getDono().temEspacoNaMesa()) {
                        tt_010(list.get(i));
                        i = list.size();
                    }
                    break;
            }
        }
        long alvo = pack.getParamLong(Param.ALVO);
        if (alvo == Param.HEROI) {
            alvoHeroi = hero;
            alvoCard = null;
        } else if (alvo == Param.OPONENTE) {
            alvoHeroi = oponente;
            alvoCard = null;
        } else {
            alvoCard = partida.findCardByIDLong(alvo);
            alvoHeroi = null;
        }
        if (!jogouCardProcessado) {
            JogouCard.processar(feitico);
        }
        return false;
    }

    /**
     * Gera o evento padrão para escolher um card
     *
     * @param ids id's dos cards para seleção
     * @return true para card selecionado ou false para card não selecionado
     */
    private static boolean escolherCard(String[] ids) throws JogouCardException {
        escolhido = EscolherCard.main("Escolha um", ids);
        if (escolhido != null) {
            return true;
        } else {
            throw new JogouCardException("Feitiço cancelado.");
        }
    }

    /**
     * Verifica se o alvo é um lacaio e ele não está furtivo e não é imune a
     * feitiços
     *
     * @return true or false
     */
    private static boolean validarAlvoLacaios() throws JogouCardException {
        alvoCard = partida.findCardByIDLong(pack.getParamLong(Param.ALVO));
        if (alvoCard != null && partida.getMesa().contains(alvoCard)
                && !alvoCard.isFurtivo() && !alvoCard.isImuneAlvo()) {
            MENSAGEM += " em " + alvoCard.getName();
            JogouCard.processar(UtilizarFeitico.feitico);
        } else {
            throw new JogouCardException("Feitiço cancelado.");
        }
        return true;
    }

    /**
     * Verifica se o alvo é um lacaio e ele não está furtivo e não é imune a
     * feitiços ou um personagem
     *
     * @return true or false
     */
    private static boolean validarAlvoPersonagem() throws JogouCardException {
        long alvoLong = pack.getParamLong(Param.ALVO);
        if (alvoLong == Param.HEROI || alvoLong == Param.OPONENTE) {
            MENSAGEM += " em " + (alvoLong == Param.HEROI ? hero.getNome() : oponente.getNome());
        } else {
            alvoCard = partida.findCardByIDLong(alvoLong);
            if (alvoCard != null && partida.getMesa().contains(alvoCard)
                    && !alvoCard.isFurtivo() && !alvoCard.isImuneAlvo()) {
                MENSAGEM += " em " + alvoCard.getName();
                JogouCard.processar(UtilizarFeitico.feitico);
            } else {
                throw new JogouCardException("Feitiço cancelado.");
            }
        }
        return true;
    }

    /**
     * Verifica se o alvo é inimigo e é um lacaio e ele não está furtivo e não é
     * imune a feitiços
     *
     * @return true or false
     */
    private static boolean validarAlvoPersonagemInimigo() throws JogouCardException {
        return validarAlvoPersonagem(oponente, Param.OPONENTE);
    }

    /**
     * Verifica se o alvo é aliado e é um lacaio e ele não está furtivo e não é
     * imune a feitiços
     *
     * @return true or false
     */
    private static boolean validarAlvoPersonagemAliado() throws JogouCardException {
        return validarAlvoPersonagem(hero, Param.HEROI);
    }

    /**
     * Verifica se o alvo é um personagem do herói passado por parâmetro e não
     * está furtivo nem é imune a feitiços
     *
     * @param h herói
     * @param hLong código do herói Param.HEROI ou Param.OPONENTE
     * @return true or false
     */
    private static boolean validarAlvoPersonagem(Heroi h, long hLong) throws JogouCardException {
        long alvoLong = pack.getParamLong(Param.ALVO);
        if (alvoLong == hLong) {
            MENSAGEM += " em " + h.getNome();
        } else {
            alvoCard = partida.findCardByIDLong(alvoLong);
            if (alvoCard != null && h.getMesa().contains(alvoCard)
                    && !alvoCard.isFurtivo() && !alvoCard.isImuneAlvo()) {
                MENSAGEM += " em " + alvoCard.getName();
                JogouCard.processar(UtilizarFeitico.feitico);
            } else {
                throw new JogouCardException("Feitiço cancelado.");
            }
        }
        return true;
    }

    /**
     * Verifica se o alvo é de uma determinada raça e não está furtivo nem é
     * imune a feitiços
     *
     * @param race raça do alvo
     * @return true or false
     */
    private static boolean validarAlvoByRace(String race) throws JogouCardException {
        alvoCard = partida.findCardByIDLong(pack.getParamLong(Param.ALVO));
        if (alvoCard != null && alvoCard.getRace() != null && alvoCard.getRace().equals(race)
                && partida.getMesa().contains(alvoCard) && !alvoCard.isFurtivo() && !alvoCard.isImuneAlvo()) {
            MENSAGEM += " em " + alvoCard.getName();
            JogouCard.processar(UtilizarFeitico.feitico);
        } else {
            throw new JogouCardException("Feitiço cancelado.");
        }
        return true;
    }

    /**
     * Verifica se o alvo é aliado e é de uma determinada raça e não está
     * furtivo nem é imune a feitiços
     *
     * @param race raça do alvo
     * @return true or false
     */
    private static boolean validarAlvoAliadoByRace(String race) throws JogouCardException {
        alvoCard = partida.findCardByIDLong(pack.getParamLong(Param.ALVO));
        if (alvoCard != null && alvoCard.getRace() != null && alvoCard.getRace().equals(race)
                && hero.getMesa().contains(alvoCard) && !alvoCard.isFurtivo() && !alvoCard.isImuneAlvo()) {
            MENSAGEM += " em " + alvoCard.getName();
            JogouCard.processar(UtilizarFeitico.feitico);
        } else {
            throw new JogouCardException("Feitiço cancelado.");
        }
        return true;
    }

    /**
     * Verifica se o alvo é um lacaio aliado e não está furtivo nem é imune a
     * feitiços
     *
     * @return true or false
     */
    private static boolean validarAlvoLacaiosHeroi() throws JogouCardException {
        return validarAlvoLacaios(hero);
    }

    /**
     * Verifica se o alvo é um lacaio inimigo e não está furtivo nem é imune a
     * feitiços
     *
     * @return true or false
     */
    private static boolean validarAlvoLacaiosOponente() throws JogouCardException {
        return validarAlvoLacaios(oponente);
    }

    /**
     * Verifica se o alvo é um lacaio do herói passado por parâmetro e não está
     * furtivo nem é imune a feitiços
     *
     * @return true or false
     */
    private static boolean validarAlvoLacaios(Heroi hero) throws JogouCardException {
        alvoCard = partida.findCardByIDLong(pack.getParamLong(Param.ALVO));
        if (alvoCard != null && hero.getMesa().contains(alvoCard)
                && !alvoCard.isFurtivo() && !alvoCard.isImuneAlvo()) {
            MENSAGEM += " em " + alvoCard.getName();
            JogouCard.processar(UtilizarFeitico.feitico);
        } else {
            throw new JogouCardException("Feitiço cancelado.");
        }
        return true;
    }

    /**
     * Verifica se o herói tem arma equipada
     *
     * @param h herói
     * @return true or false
     */
    private static boolean validarArma(Heroi h) throws JogouCardException {
        if (h.getArma() != null) {
            return true;
        } else {
            throw new JogouCardException("Feitiço cancelado.");
        }
    }

    /**
     * Verifica se o herói tem arma equipada
     *
     * @return true or false
     */
    private static boolean validarArmaHeroi() throws JogouCardException {
        return validarArma(hero);
    }

    /**
     * Verifica se o herói tem, no mínimo, a quantidade de lacaios passada por
     * parâmetro
     *
     * @param hero herói
     * @param quantidade quantidade de lacaios
     * @return true or false
     */
    private static boolean validarQuantidade(Heroi hero, int quantidade) throws JogouCardException {
        if (hero.getMesa().size() >= quantidade) {
            JogouCard.processar(UtilizarFeitico.feitico);
        } else {
            throw new JogouCardException("Feitiço cancelado.");
        }
        return true;
    }

    /**
     * Gera o evento padrão para curar um personagem
     *
     * @param cura vida restaurada
     * @return true para feitiço utilizado ou false para feitiço não utilizado
     * ou cancelado
     */
    private static boolean curarPersonagem(int cura) throws JogouCardException {
        if (validarAlvoPersonagem() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.addVida(hero.getDobrarDanoECura(cura));
            } else if (alvoHeroi != null) {
                alvoHeroi.addHealth(hero.getDobrarDanoECura(cura));
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gera o evento padrão para causar dano a um personagem
     *
     * @param dano dano
     * @return true para feitiço utilizado ou false para feitiço não utilizado
     * ou cancelado
     */
    private static boolean danoPersonagem(int dano) throws JogouCardException {
        if (validarAlvoPersonagem() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.delVida(Utils.getDanoFeitico(dano, hero));
            } else if (alvoHeroi != null) {
                alvoHeroi.delHealth(Utils.getDanoFeitico(dano, hero));
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gera o evento padrão para causar dano a um lacaio
     *
     * @param dano dano
     * @return true para feitiço utilizado ou false para feitiço não utilizado
     * ou cancelado
     */
    private static boolean danoLacaio(int dano) throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.delVida(Utils.getDanoFeitico(dano, hero));
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * moeda
     */
    private static void game_005() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            partida.getEfeitoProgramado().concederManaAoHeroiUmTurno(1);
        }
    }

    /**
     * Moeda de Gallywix (Receba 1 Cristal de Mana somente neste turno (Não
     * ativa Gallywix)
     */
    private static void gvg_028t() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            partida.getEfeitoProgramado().concederManaAoHeroiUmTurno(1);
        }
    }

    /**
     * Lança de Fogo Cause 8 de dano a um lacaio.
     */
    private static void at_001() throws JogouCardException {
        danoLacaio(8);
    }

    /**
     * Impacto Arcano (Cause 2 de dano a um lacaio. Este feiriço recebe bônus
     * duplo de Dano Mágico
     */
    private static void at_004() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.delVida(hero.getDobrarDanoECura(2 + (hero.getDanoMagico() * 2)));
            }
        }
    }

    /**
     * Polimorfia: Javali (Transforme um lacaio em um Javali 4/2 com Investida)
     */
    private static void at_005() throws JogouCardException {
        polimorfia("AT_005t");
    }

    private static void polimorfia(String polimorfia) throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                partida.polimorfia(alvoCard.id_long, polimorfia, true);
            }
        }
    }

    /**
     * Palavra de Poder: Glória (Escolha um lacaio. Sempre que ele atacar,
     * restaure 4 de Vida do seu herói.
     */
    private static void at_013() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.addFazerAoAtacar(FazerAoAtacar.curarHeroi(partida, 4));
            }
        }
    }

    /**
     * Converter (Coloque uma cópia de um lacaio inimigo na sua mão).
     */
    private static void at_015() throws JogouCardException {
        if (validarAlvoLacaiosOponente() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                Card copia = partida.criarCard(alvoCard.getId(), System.nanoTime());
                hero.addMao(copia);
            }
        }
    }

    /**
     * Confundir (Troque o Ataque pela Vida de todos os lacaios).
     */
    private static void at_016() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            List<Card> todosOsLacaios = partida.getMesa();
            for (Card lacaio : todosOsLacaios) {
                int vida = lacaio.getVidaSemAura();
                int ataque = lacaio.getAtaqueSemAura();
                lacaio.setVidaOriginal(ataque, false);
                lacaio.setAtaque(vida, false);
            }
        }
    }

    /**
     * Punho de Jaraxxus (Ao jogar ou descartar este card, cause 4 de dano a um
     * inimigo aleatório)
     */
    private static void at_022() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            int random = Utils.random(oponente.getMesa().size() + 1) - 1;
            if (random == oponente.getMesa().size()) {
                oponente.delHealth(Utils.getDanoFeitico(4, hero));
            } else {
                oponente.getMesa().get(random).delVida(Utils.getDanoFeitico(4, hero));
            }
        }
    }

    /**
     * Fusão Demoníaca (Conceda +3/+3 a um Demônio. Conceda um Cristal de Mana
     * ao seu oponente.
     */
    private static void at_024() throws JogouCardException {
        if (validarAlvoByRace(Values.DEMONIO) && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.addVidaMaxima(3);
                alvoCard.addAtaque(3);
            }
            hero.addMana(1);
        }
    }

    /**
     * Barganha Negra (Destrua 2 lacaios inimigos aleatórios. Descarte 2 cards
     * aleatórios)
     */
    private static void at_025() throws JogouCardException {
        if (validarQuantidade(oponente, 2) && !anularAlterarFeitico()) {
            List<Card> lacaios = Utils.random(2, oponente.getMesa());
            EmArea.destruir(lacaios);
            hero.descartar();
            hero.descartar();
        }
    }

    /**
     * Roubar (Adicione 2 cards de classe aleatórios à sua mão (da classe do seu
     * oponente))
     */
    private static void at_033() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (int i = 0; i < 2; i++) {
                hero.addMao(partida.criarCard(Random.getCard(Values.FEITICO, oponente.getType(),
                                Random.QUALQUER_CUSTO, Values.GERAL, Values.GERAL,
                                Values.GERAL).getId(), System.nanoTime()));
            }
        }
    }

    /**
     * Debaixo da Terra (Embaralhe 3 Emboscadas no deck do seu oponente. Quando
     * ele comprar, você evoca um Nerubiano 4/4)
     */
    private static void at_035() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (int i = 0; i < 3; i++) {
                oponente.addCardDeckAleatoriamente(partida.criarCard(Values.EMBOSCADA, System.nanoTime()));
            }
        }
    }

    /**
     * Emboscada! (Ao comprar este card, evoque um Nerubiano 4/4 para o seu
     * oponente. Compre um card)
     */
    private static void at_035t() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            hero.comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Raízes Vivas (Escolha um - Causar 2 de dano ou evocar dois Brotos 1/1)
     */
    private static void at_037() throws JogouCardException {
        String escolhido = EscolherCard.main(
                "Escolha um - Causar " + Utils.getDanoFeitico(2, hero) + " de dano ou evocar dois Brotos 1/1",
                new String[]{"AT_037a", "AT_037b"});
        if (escolhido != null) {
            if (escolhido.equals("AT_037a")) {
                at_037a();
            } else {
                at_037b();
            }
        } else {
            throw new JogouCardException("Feitiço cancelado.");
        }
    }

    /**
     * Raízes Vivas (Cause 2 de dano)
     */
    private static void at_037a() throws JogouCardException {
        danoPersonagem(2);
    }

    /**
     * Raízes Vivas (Evoque dois Brotos 1/1.)
     */
    private static void at_037b() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            hero.evocar(partida.criarCard(Values.BROTO1_1, System.nanoTime()));
            hero.evocar(partida.criarCard(Values.BROTO1_1, System.nanoTime()));
        }
    }

    /**
     * Comunhão Astral (Receba 10 cristais de Mana. Descarte a sua mão)
     */
    private static void at_043() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            if (hero.getMana() < 10) {
                hero.setMana(10);
                hero.setManaUtilizada(0);
                List<Card> mao = hero.getMao().subList(0, hero.getMao().size());
                for (Card c : mao) {
                    hero.descartar(c);
                }
            } else {
                hero.addMao(partida.criarCard(Values.EXCESSO_DE_MANA, System.nanoTime()));
            }
        }
    }

    /**
     * Excesso de Mana (Compre um card. (Você só pode ter 10 de Mana na sua
     * mesa.))
     */
    private static void cs2_013t() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            hero.comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Húmus (Destrua um lacaio. Adicione um lacaio aleatório à mão do seu
     * oponente)
     */
    private static void at_044() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.morreu();
            oponente.addMao(partida.criarCard(Random.lacaio().getId(), System.nanoTime()));
        }
    }

    /**
     * Onda Curativa (Restaure 7 de Vida. Revele um lacaio em cada deck. Se o
     * seu custar mais, em vez disso, restaure 14)
     */
    private static void at_048() throws JogouCardException {
        if (validarAlvoPersonagem() && !anularAlterarFeitico()) {
            long vencedor = Justas.processar(hero, oponente);
            if (alvoCard != null) {
                alvoCard.addVida(hero.getDobrarDanoECura(vencedor == Param.HEROI ? 14 : 7));
            } else if (alvoHeroi != null) {
                alvoHeroi.addHealth(hero.getDobrarDanoECura(vencedor == Param.HEROI ? 14 : 7));
            }
        }
    }

    /**
     * Destruição Elemental (Cause 4-5 de dano a todos os lacaios. Sobrecarga:
     * 5)
     */
    private static void at_051() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            List<Card> lacaios = partida.getMesa().subList(0, partida.getMesa().size());
            EmArea.dano(partida, lacaios, Utils.getDanoFeitico(4, hero), 1);
        }
    }

    /**
     * Conhecimento Ancestral (Compre 2 cards. Sobrecarga 2)
     */
    private static void at_053() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            hero.comprarCarta(Card.COMPRA_FEITICO);
            hero.comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Cura Célere (Restaude 5 de vida)
     */
    private static void at_055() throws JogouCardException {
        curarPersonagem(5);
    }

    /**
     * Tirambaço (Cause 2 de dano a um lacaio e aos lacaios adjacentes)
     */
    private static void at_056() throws JogouCardException {
        if (validarAlvoLacaiosOponente() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                List<Card> alvos = Utils.getAdjacentes(alvoCard);
                alvos.add(0, alvoCard);
                EmArea.dano(partida, alvos, Utils.getDanoFeitico(2, hero), 0);
            }
        }
    }

    /**
     * Largar o Dedo (Cada vez que você lançar um feitiço neste turno, adicione
     * um card de Caçador aleatório à sua mão)
     */
    private static void at_061() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            partida.getFeiticosAtivos().ativarAT_061(feitico.id_long);
        }
    }

    /**
     * Bola de Aranhas (Evoque três Teceteias 1/1)
     */
    private static void at_062() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (int i = 0; i < 3; i++) {
                hero.evocar(partida.criarCard(Values.TECETEIA1_1, System.nanoTime()));
            }
        }
    }

    /**
     * Trombar (Cause 3 de dano. Receba 3 de Armadura)
     */
    private static void at_064() throws JogouCardException {
        if (danoPersonagem(3)) {
            hero.addShield(3);
        }
    }

    /**
     * Fortalecer (Concede +2/+2 aos seus lacaios com Provocar)
     */
    private static void at_068() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (Card c : hero.getMesa()) {
                if (c.isProvocar()) {
                    c.addVidaMaxima(2);
                    c.addAtaque(2);
                }
            }
        }
    }

    /**
     * Selo dos Campeões (Conceda +3 de Ataque e Escudo Divino a um lacaio)
     */
    private static void at_074() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.addAtaque(3);
                alvoCard.addMechanics(Values.ESCUDO_DIVINO);
            }
        }
    }

    /**
     * Entrada no Coliseu (Destrua todos os lacaios, menos o lacaio com maior
     * Ataque de cada jogador)
     */
    private static void at_078() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            Card maiorAtaqueHero = Utils.getMaiorAtaque(hero.getMesa());
            Card maiorAtaqueOponente = Utils.getMaiorAtaque(oponente.getMesa());
            List<Card> lacaios = partida.getMesa();
            lacaios.remove(maiorAtaqueHero);
            lacaios.remove(maiorAtaqueOponente);
            EmArea.destruir(lacaios);
        }
    }

    /**
     * Vigília Solene (Compre 2 cards. Custa 1 a menos para cada lacaio morto
     * neste turno)
     */
    private static void brm_001() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (int i = 0; i < 2; i++) {
                partida.getHero().comprarCarta(Card.COMPRA_FEITICO);
            }
        }
    }

    /**
     * Sopro do Dragão (Causa 4 de dano. Custa 1 a menos para cada lacaio morto
     * neste turno)
     */
    private static void brm_003() throws JogouCardException {
        danoPersonagem(4);
    }

    /**
     * Ira Demoníaca (Cause 2 de dano a todos os lacaios, menos aos Demônios)
     */
    private static void brm_005() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            List<Card> lacaios = new ArrayList<>();
            for (Card lacaio : partida.getMesa()) {
                if (!lacaio.isDemonio()) {
                    lacaios.add(lacaio);
                }
            }
            EmArea.dano(partida, lacaios, Utils.getDanoFeitico(2, hero), 0);
        }
    }

    /**
     * Formação de Quadrilha (Escolha um lacaio. Embaralhe 3 cópias dele no seu
     * deck)
     */
    private static void brm_007() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                for (int i = 0; i < 3; i++) {
                    partida.getHero().addCardDeckAleatoriamente(partida.criarCard(alvoCard.getId(), System.nanoTime()));
                }
            }
        }
    }

    /**
     * Choque de Lava (Cause 2 de dano. Desbloqueie Cristais de Mana
     * Sobrecarregados)
     */
    private static void brm_011() throws JogouCardException {
        if (danoPersonagem(2)) {
            hero.setManaBloqueadaEsteTurno(0);
            hero.setManaBloqueadaTurnoAnterior(0);
        }
    }

    /**
     * Disparo Veloz (Cause 3 de dano. Se a sua mão estiver vazia, compre um
     * card)
     */
    private static void brm_013() throws JogouCardException {
        if (danoPersonagem(3) && hero.getMao().isEmpty()) {
            hero.comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Revanche (Cause 1 de dano a todos os lacaios. Se você tiver 12 pontos de
     * vida ou menos, em vez disso, cause 3 de dano)
     */
    private static void brm_015() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            EmArea.dano(partida, partida.getMesa(), hero.getHealth() <= 12 ? 3 : 1, 0);
        }
    }

    /**
     * Ressuscitar (Evoque um lacaio aliado aleatório morto neste jogo)
     */
    private static void brm_017() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            List<Card> mortos = hero.getMorto();
            if (!mortos.isEmpty()) {
                int random = Utils.random(mortos.size()) - 1;
                hero.evocar(partida.criarCard(mortos.get(random).getId(), System.nanoTime()));
            }
        }
    }

    /**
     * Nova Sagrada (Cause 2 de dano a todos os inimigos. Restaude 2 de Vida de
     * todos os personagens aliados)
     */
    private static void cs1_112() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            int cura = hero.getDobrarDanoECura(2);
            int dano = Utils.getDanoFeitico(2, hero);
            hero.addHealth(cura);
            oponente.delHealth(dano);
            List<Card> aliados = partida.getHero().getMesa();
            List<Card> inimigos = partida.getOponente().getMesa();
            aliados.sort(Sort.time());
            inimigos.sort(Sort.time());
            String packIds = "";
            String packValor = "";
            List<Integer> valores = new ArrayList<>();
            for (Card lacaio : inimigos) {
                int vidaAnterior = lacaio.getVida();
                lacaio.delVidaEmArea(dano);
                valores.add(lacaio.getVida() - vidaAnterior);
                lacaio.getAnimacao().vidaAtual(valores.get(valores.size() - 1), 2000, false, false);
                packValor += (!packValor.isEmpty() ? ";" : "") + (valores.get(valores.size() - 1));
                packIds += (!packIds.isEmpty() ? ";" : "") + lacaio.id_long;
            }
            if (!packValor.isEmpty()) {
                Pacote pacote = new Pacote(Param.ANIMACAO_CARD_VIDA_EM_AREA);
                pacote.set(Param.ANIMACAO_CARD_VIDA_EM_AREA_IDS, packIds);
                pacote.set(Param.ANIMACAO_CARD_VIDA_EM_AREA_VALOR, packValor);
                GameCliente.enviar(pacote);
            }
            for (Card lacaio : aliados) {
                int vidaAnterior = lacaio.getVida();
                lacaio.addVidaEmArea(cura);
                valores.add(lacaio.getVida() - vidaAnterior);
                lacaio.getAnimacao().vidaAtual(valores.get(valores.size() - 1), 2000, false, false);
                packValor += (!packValor.isEmpty() ? ";" : "") + (valores.get(valores.size() - 1));
                packIds += (!packIds.isEmpty() ? ";" : "") + lacaio.id_long;
            }
            if (!packValor.isEmpty()) {
                Pacote pacote = new Pacote(Param.ANIMACAO_CARD_VIDA_EM_AREA);
                pacote.set(packIds, Param.ANIMACAO_CARD_VIDA_EM_AREA_IDS);
                pacote.set(packValor, Param.ANIMACAO_CARD_VIDA_EM_AREA_VALOR);
                GameCliente.enviar(pacote);
            }
            List<Card> todos = new ArrayList<>();
            todos.addAll(inimigos);
            todos.addAll(aliados);
            for (Card lacaio : todos) {
                if (lacaio.getVida() <= 0) {
                    GameCliente.enviar(Param.ANIMACAO_CARD_MORREU, lacaio.id_long);
                    lacaio.getDono().addMorto(lacaio);
                    lacaio.getDono().delMesa(lacaio);
                }
            }
            for (int i = 0; i < valores.size(); i++) {
                try {
                    if (valores.get(i) < 0) {
                        GameCliente.addHistorico(todos.get(i).getName() + " recebeu " + valores.get(i) + " de dano.", true);
                        PersonagemRecebeuDano.processar(null, todos.get(i), valores.get(i), todos);
                    } else if (valores.get(i) > 0) {
                        GameCliente.addHistorico(todos.get(i).getName() + " teve " + valores.get(i) + " de vida restaurada.", true);
                        PersonagemFoiCurado.processar(null, todos.get(i), todos);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            todos.sort(Sort.time());
            todos.stream().filter((lacaio) -> (lacaio.getVida() <= 0)).forEach((lacaio) -> {
                lacaio.morreu();
            });
        }
    }

    /**
     * Controle Mental (Assuma o controle de um lacaio inimigo)
     */
    private static void cs1_113() throws JogouCardException {
        if (validarAlvoLacaiosOponente() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                partida.roubar(alvoCard.id_long);
            }
        }
    }

    /**
     * Fogo Interior (Mude o Ataque de um lacaio para ser igual à Vida dele)
     */
    private static void cs1_129() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.setAtaque(alvoCard.getVida(), true);
            }
        }
    }

    /**
     * Punição Sagrada (Cause 2 de dano)
     */
    private static void cs1_130() throws JogouCardException {
        danoPersonagem(2);
    }

    /**
     * Visão da Mente (Coloque uma cópia de um card aleatório da mão do seu
     * oponente na sua mão)
     */
    private static void cs2_003() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            List<Card> mao = oponente.getMao();
            if (!mao.isEmpty() && !anularAlterarFeitico()) {
                int random = Utils.random(mao.size()) - 1;
                hero.addMao(partida.criarCard(mao.get(random).getId(), System.nanoTime()));
            }
        }
    }

    /**
     * Palavra de Poder: Escudo (Conceda +2 de Vida a um lacaio. Compre um card)
     */
    private static void cs2_004() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.addVidaMaxima(2);
            }
            hero.comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Garra (Conceda +2 de Ataque ao seu herói neste turno e 2 de Armadura
     */
    private static void cs2_005() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            partida.getEfeitoProgramado().concederAtaqueAoHeroiUmTurno(2);
            hero.addShield(2);
        }
    }

    /**
     * Toque de Cura (Restaure 8 de Vida)
     */
    private static void cs2_007() throws JogouCardException {
        curarPersonagem(8);
    }

    /**
     * Fogo Lunar (Cause 1 de dano)
     */
    private static void cs2_008() throws JogouCardException {
        danoPersonagem(1);
    }

    /**
     * Marca do Indomado (Conceda Provodar e +2/+2 a um lacaio)
     */
    private static void cs2_009() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.addMechanics(Values.PROVOCAR);
                alvoCard.addVidaMaxima(2);
                alvoCard.addAtaque(2);
            }
        }
    }

    /**
     * Rugido Selvagem (Conceda +2 de Ataque aos seus personagens neste turno)
     */
    private static void cs2_011() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            partida.getEfeitoProgramado().concederAtaqueAoHeroiUmTurno(2);
            for (Card lacaio : hero.getMesa()) {
                partida.getEfeitoProgramado().concederAtaqueAUmLacaioUmTurno(lacaio.id_long, 2);
            }
        }
    }

    /**
     * Patada (Cause 4 de dano a um inimigo e 1 de dano a todos os outros
     * inimigos)
     */
    private static void cs2_012() throws JogouCardException {
        if (validarAlvoPersonagemInimigo() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.delVida(Utils.getDanoFeitico(4, hero));
                List<Card> lacaios = new ArrayList<>();
                oponente.getMesa().stream().filter((lacaio) -> (!lacaio.equals(alvoCard))).forEach((lacaio) -> {
                    lacaios.add(lacaio);
                });
                oponente.delHealth(Utils.getDanoFeitico(1, hero));
                EmArea.dano(partida, lacaios, Utils.getDanoFeitico(1, hero), 0);
            } else if (alvoHeroi != null) {
                oponente.delHealth(Utils.getDanoFeitico(4, hero));
                EmArea.dano(partida, oponente.getMesa(), Utils.getDanoFeitico(1, hero), 0);
            }
        }
    }

    /**
     * Crescimento Silvestre (Receba um Cristal de Mana vazio)
     */
    private static void cs2_013() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            if (hero.getMana() < 10) {
                hero.addMana(1);
                hero.addManaUtilizada(1);
            } else {
                hero.addMao(partida.criarCard(Values.EXCESSO_DE_MANA, System.nanoTime()));
            }
        }
    }

    /**
     * Polimorfia (Transforme um lacaio em uma Ovelha 1/1)
     */
    private static void cs2_022() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                partida.polimorfia(alvoCard.id_long, Values.OVELHA1_1, true);
            }
        }
    }

    /**
     * Intelecto Arcano (Compre 2 cards)
     */
    private static void cs2_023() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            hero.comprarCarta(Card.COMPRA_FEITICO);
            hero.comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Seta de Gelo (Cause 3 de dano a um personage e Congele-o)
     */
    private static void cs2_024() throws JogouCardException {
        if (validarAlvoPersonagem() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.delVida(Utils.getDanoFeitico(3, hero));
                alvoCard.setFreeze(true);
            } else if (alvoHeroi != null) {
                alvoHeroi.delHealth(Utils.getDanoFeitico(3, hero));
                alvoHeroi.setFreeze(true);
            }
        }
    }

    /**
     * Explosão Arcana (Cause 1 de dano a todos os lacaios inimigos)
     */
    private static void cs2_025() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            EmArea.dano(partida, oponente.getMesa(), Utils.getDanoFeitico(1, hero), 0);
        }
    }

    /**
     * Nova Congelante (Congele todos os lacaios inimigos)
     */
    private static void cs2_026() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (Card lacaio : oponente.getMesa()) {
                lacaio.setFreeze(true);
            }
        }
    }

    /**
     * Imagem Espelhada (Evoque dois lacaios 0/2 com Provocar)
     */
    private static void cs2_027() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            hero.evocar(partida.criarCard(Values.IMAGEM_ESPELHADA0_2, System.nanoTime()));
            hero.evocar(partida.criarCard(Values.IMAGEM_ESPELHADA0_2, System.nanoTime()));
        }
    }

    /**
     * Nevasca (Cause 2 de dano a todos os lacaios inimigos e Congele-os)
     */
    private static void cs2_028() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (Card lacaio : oponente.getMesa()) {
                lacaio.setFreeze(true);
            }
            EmArea.dano(partida, oponente.getMesa(), Utils.getDanoFeitico(2, hero), 0);
        }
    }

    /**
     * Bola de Fogo (Cause 6 de dano)
     */
    private static void cs2_029() throws JogouCardException {
        if (validarAlvoPersonagem() && !anularAlterarFeitico()) {
            danoPersonagem(6);
        }
    }

    /**
     * Lança de Gelo (Congele um personagem. Se ele já estiver Congelado, cause
     * 4 de dano)
     */
    private static void cs2_031() throws JogouCardException {
        if (validarAlvoPersonagem() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                if (alvoCard.isFreeze()) {
                    alvoCard.delVida(Utils.getDanoFeitico(4, hero));
                } else {
                    alvoCard.setFreeze(true);
                }
            } else if (alvoHeroi != null) {
                if (alvoHeroi.isFreeze()) {
                    alvoHeroi.delHealth(Utils.getDanoFeitico(4, hero));
                } else {
                    alvoHeroi.setFreeze(true);
                }
            }
        }
    }

    /**
     * Golpe Flamejante (Cause 4 de dano a todos os lacaios inimigos)
     */
    private static void cs2_032() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            EmArea.dano(partida, oponente.getMesa(), Utils.getDanoFeitico(4, hero), 0);
        }
    }

    /**
     * Choque Gélido (Cause 1 de dano a um personagem inimigo e Congele-o)
     */
    private static void cs2_037() throws JogouCardException {
        if (validarAlvoPersonagemInimigo() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.setFreeze(true);
                alvoCard.delVida(Utils.getDanoFeitico(1, hero));
            } else if (alvoHeroi != null) {
                alvoHeroi.setFreeze(true);
                alvoHeroi.delHealth(Utils.getDanoFeitico(1, hero));
            }
        }
    }

    /**
     * Espírito Ancestral (Conceda a um lacaio: "Último Suspiro: Evoque este
     * lacaio novamente")
     */
    private static void cse_038() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.addEvocarUltimoSuspiro(alvoCard.getId());
            }
        }
    }

    /**
     * Fúria dos Ventos (Conceda Fúria dos Ventos a um lacaio)
     */
    private static void cs2_039() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.addMechanics(Values.FURIA_DOS_VENTOS);
            }
        }
    }

    /**
     * Cura Ancestral (Restaure toda a Vida de um lacaio e conceda Provocar a
     * ele)
     */
    private static void cs2_041() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.restaurarVida();
                alvoCard.addMechanics(Values.PROVOCAR);
            }
        }
    }

    /**
     * Arma Trinca-pedra (Conceda +3 de Ataque a um aliado neste turno)
     */
    private static void cs2_045() throws JogouCardException {
        if (validarAlvoPersonagemAliado() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                partida.getEfeitoProgramado().concederAtaqueAUmLacaioUmTurno(alvoCard.id_long, 3);
            } else if (alvoHeroi != null) {
                partida.getEfeitoProgramado().concederAtaqueAoHeroiUmTurno(3);
            }
        }
    }

    /**
     * Sede de Sangue (Conceda +3 de Ataque aos seus lacaios neste turno)
     */
    private static void cs2_046() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (Card lacaio : hero.getMesa()) {
                partida.getEfeitoProgramado().concederAtaqueAUmLacaioUmTurno(lacaio.id_long, 3);
            }
        }
    }

    /**
     * Visão Distante (Compre um card. Este card custa 3 a menos)
     */
    private static void cs2_053() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            Card comprado = hero.comprarCarta(Card.COMPRA_FEITICO);
            if (comprado != null) {
                comprado.delCusto(3);
            }
        }
    }

    /**
     * Seta Sombria (Cause 4 de dano a um lacaio)
     */
    private static void cs2_057() throws JogouCardException {
        danoLacaio(4);
    }

    /**
     * Drenar Vida (Cause 2 de dano. Restaure 2 de Vida do seu herói)
     */
    private static void cs2_061() throws JogouCardException {
        if (danoPersonagem(2)) {
            hero.addHealth(hero.getDobrarDanoECura(2));
        }
    }

    /**
     * Fogo do Inferno (Cause 3 de dano a TODOS os personagens)
     */
    private static void cs2_062() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            int dano = Utils.getDanoFeitico(3, hero);
            oponente.delHealth(dano);
            hero.delHealth(dano);
            EmArea.dano(partida, partida.getMesa(), dano, 0);
        }
    }

    /**
     * Corrupção (Escolha um lacaio inimigo. No início do seu turno, destrua-o;
     */
    private static void cs2_063() throws JogouCardException {
        if (validarAlvoLacaiosOponente()) {
            if (alvoCard != null) {
                partida.getEfeitoProgramado().lacaioMorrerInicioTurno(alvoCard.id_long, 1);
            }
        }
    }

    /**
     * Punhalada pelas Costas (Cause 2 de dano a um lacaio sem ferimentos)
     */
    private static void cs2_072() throws JogouCardException {
        if (validarAlvoLacaios()) {
            if (alvoCard != null && alvoCard.isIleso()) {
                if (!anularAlterarFeitico() && alvoCard != null) {
                    alvoCard.delVida(Utils.getDanoFeitico(2, hero));
                }
            } else {
                throw new JogouCardException("Feitiço cancelado.");
            }
        }
    }

    /**
     * Sangue Frio (Conceda +2 de Ataque a um lacaio. Combo: +4 de Ataque em vez
     * de +2)
     */
    private static void cs2_073() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addAtaque(Utils.isCombo(feitico) ? 4 : 2);
        }
    }

    /**
     * Veneno Mortal (Conceda +2 de Ataque à sua arma)
     */
    private static void cs2_074() throws JogouCardException {
        if (validarArmaHeroi() && !anularAlterarFeitico() && hero.getArma() != null) {
            hero.getArma().addAtaque(2);
        }
    }

    /**
     * Golpe Sinistro (Cause 3 de dano ao herói inimigo)
     */
    private static void cs2_075() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            if (alvoHeroi != null) {
                alvoHeroi.delHealth(Utils.getDanoFeitico(3, hero));
            } else if (alvoCard != null) {
                alvoCard.delVida(Utils.getDanoFeitico(3, hero));
            }
        }
    }

    /**
     * Assassinar (Destrua um lacaio inimigo)
     */
    private static void cs2_076() throws JogouCardException {
        if (validarAlvoLacaiosOponente() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.morreu();
        }
    }

    /**
     * Disparada (Compre 4 cards)
     */
    private static void cs2_077() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (int i = 0; i < 4; i++) {
                hero.comprarCarta(Card.COMPRA_FEITICO);
            }
        }
    }

    /**
     * Marca do Caçador (muda a Vida de um lacaio para 1)
     */
    private static void cs2_084() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.setVidaOriginal(1, true);
        }
    }

    /**
     * Bênção do Poder (Conceda +3 de Ataque a um lacaio)
     */
    private static void cs2_087() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addAtaque(3);
        }
    }

    /**
     * Luz Sagrada (Restaure 6 de Vida)
     */
    private static void cs2_089() throws JogouCardException {
        curarPersonagem(6);
    }

    /**
     * Bênção dos Reis (Conceda +4/+4 a um lacaio)
     */
    private static void cs2_092() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addVidaMaxima(4);
            alvoCard.addAtaque(4);
        }
    }

    /**
     * Consagração (Cause 2 de danoa todos os inimigos)
     */
    private static void cs2_093() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            int dano = Utils.getDanoFeitico(2, hero);
            oponente.delHealth(dano);
            EmArea.dano(partida, oponente.getMesa(), dano, 0);
        }
    }

    /**
     * Martelo da Ira (Cause 3 de dano. Compre um card)
     */
    private static void cs2_094() throws JogouCardException {
        if (danoPersonagem(3)) {
            hero.comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Investida (Conceda +2 de Ataque e Investida a um lacaio aliado)
     */
    private static void cs2_103() throws JogouCardException {
        if (validarAlvoLacaiosHeroi() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addAtaque(2);
            alvoCard.addMechanics(Values.INVESTIDA);
        }
    }

    /**
     * Alvoroço (Conceda +3/+3 a um lacaio ferido)
     */
    private static void cs2_104() throws JogouCardException {
        if (validarAlvoLacaios()) {
            if (alvoCard != null && !alvoCard.isIleso()) {
                if (!anularAlterarFeitico() && alvoCard != null) {
                    alvoCard.addVidaMaxima(3);
                    alvoCard.addAtaque(3);
                }
            } else {
                throw new JogouCardException("Feitiço cancelado.");
            }
        }
    }

    /**
     * Golpe Heroico (Conceda +4 de Ataque ao seu herói neste turno)
     */
    private static void cs2_105() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            partida.getEfeitoProgramado().concederAtaqueAoHeroiUmTurno(4);
        }
    }

    /**
     * Executar (Destrua um lacaio inimigo que tenha recebido dano)
     */
    private static void cs2_108() throws JogouCardException {
        if (validarAlvoLacaiosOponente()) {
            if (alvoCard != null && !alvoCard.isIleso()) {
                if (!anularAlterarFeitico() && alvoCard != null) {
                    alvoCard.morreu();
                }
            } else {
                throw new JogouCardException("Feitiço cancelado.");
            }
        }
    }

    /**
     * Cutilada (Cause 2 de dano a dois lacaios inimigos aleatórios)
     */
    private static void cs2_114() throws JogouCardException {
        if (validarQuantidade(oponente, 2) && !anularAlterarFeitico()) {
            List<Card> lacaios = Utils.random(2, oponente.getMesa());
            EmArea.dano(partida, lacaios, Utils.getDanoFeitico(2, hero), 0);
        }
    }

    /**
     * Rajada de Lâminas (Destrua sua arma e cause o dano dela a todos os
     * inimigos)
     */
    private static void cs2_233() throws JogouCardException {
        if (validarArmaHeroi() && !anularAlterarFeitico() && hero.getArma() != null) {
            int ataque = hero.getArma().getAtaque();
            hero.destruirArma();
            oponente.delHealth(ataque);
            EmArea.dano(partida, oponente.getMesa(), ataque, 0);
        }
    }

    /**
     * Palavra Sombria: Dor (Destrua um lacaio com 3 ou menos de Ataque)
     */
    private static void cs2_234() throws JogouCardException {
        if (validarAlvoLacaios()) {
            if (alvoCard != null && alvoCard.getAtaque() <= 3) {
                if (!anularAlterarFeitico() && alvoCard != null) {
                    alvoCard.morreu();
                }
            } else {
                throw new JogouCardException("Feitiço cancelado.");
            }
        }
    }

    /**
     * Espírito Divino (Dobre a Vida de um lacaio)
     */
    private static void cs2_236() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            int vida = alvoCard.getVida();
            alvoCard.addVidaMaxima(vida);
            alvoCard.setVida(vida);
        }
    }

    /**
     * Despertar de Ysera (Cause 5 de dano a todos os personagens, exceto Ysera)
     */
    private static void dream_02() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            int dano = Utils.getDanoFeitico(5, hero);
            List<Card> lacaios = new ArrayList<>();
            for (Card lacaio : partida.getMesa()) {
                if (!lacaio.getId().equals(Values.YSERA)) {
                    lacaios.add(lacaio);
                }
            }
            oponente.delHealth(dano);
            hero.delHealth(dano);
            EmArea.dano(partida, lacaios, dano, 0);
        }
    }

    /**
     * Sonho (Devolva um lacaio à mão do dono)
     */
    private static void dream_04() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            partida.voltarPraMaoDoDono(alvoCard.id_long);
        }
    }

    /**
     * Pesadelo (Conceda +5/+5 a um lacaio. No início do próximo turno,
     * destrua-o)
     */
    private static void dream_05() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addVidaMaxima(5);
            alvoCard.addAtaque(5);
            partida.getEfeitoProgramado().lacaioMorrerInicioTurno(alvoCard.id_long, 0);
        }
    }

    /**
     * Tiro Múltiplo (Cause 3 de dano a dois lacaios inimigos aleatórios)
     */
    private static void ds1_183() throws JogouCardException {
        if (validarQuantidade(oponente, 2) && !anularAlterarFeitico()) {
            EmArea.dano(partida, Utils.random(2, oponente.getMesa()), Utils.getDanoFeitico(3, hero), 0);
        }
    }

    /**
     * Rastreamento (Olhe os três cards do topo do seu deck. Compre um e
     * descarte os outros)
     */
    private static void ds1_184() throws JogouCardException {
        if (!hero.getDeck().isEmpty()) {
            if (!anularAlterarFeitico()) {
                int tamanhoDeck = hero.getDeck().size();
                String[] ids = new String[tamanhoDeck >= 3 ? 3 : tamanhoDeck];
                List<Card> cards = new ArrayList<>();
                List<String> ids_string = new ArrayList<>();
                for (int i = 0; i < ids.length; i++) {
                    cards.add(hero.getDeck().get(i));
                    ids[i] = hero.getDeck().get(i).getId();
                    ids_string.add(ids[i]);
                }
                String escolhido = EscolherCard.main("Escolha um", ids);
                while (escolhido == null) {
                    escolhido = EscolherCard.main("Escolha um", ids);
                }
                Card cardEscolhido = cards.get(ids_string.indexOf(escolhido));
                hero.comprarCarta(hero.getDeck().indexOf(cardEscolhido), Card.COMPRA_FEITICO);
                cards.remove(cardEscolhido);
                for (Card c : cards) {
                    hero.descartar(c);
                }
            }
        } else {
            throw new JogouCardException("Feitiço cancelado.");
        }
    }

    /**
     * Tiro Arcano (Cause 2 de dano)
     */
    private static void ds1_185() throws JogouCardException {
        danoPersonagem(2);
    }

    /**
     * Impacto Mental (Cause 5 de dano ao herói inimigo)
     */
    private static void ds1_233() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            oponente.delHealth(Utils.getDanoFeitico(5, hero));
        }
    }

    /**
     * Bananas (Conceda +1/+1 a um lacaio)
     */
    private static void ex1_014t() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addVidaMaxima(1);
            alvoCard.addAtaque(1);
        }
    }

    /**
     * Eviscerar (Cause 2 de dano. Combo: Em vez de 2 cause 4 de dano)
     */
    private static void ex1_124() throws JogouCardException {
        danoPersonagem(Utils.isCombo(feitico) ? 4 : 2);
    }

    /**
     * Traição (Force um lacaio inimigo a causar dano aos lacaios perto dele)
     */
    private static void ex1_126() throws JogouCardException {
        if (validarAlvoLacaiosOponente() && !anularAlterarFeitico() && alvoCard != null) {
            List<Card> adjacentes = Utils.getAdjacentes(alvoCard);
            int dano = alvoCard.getAtaque();
            EmArea.dano(partida, adjacentes, dano, 0);
        }
    }

    /**
     * Ocultar (Conceda Furtividade aos seus lacaios até o próximo turno)
     */
    private static void ex1_128() throws JogouCardException {
        if (validarQuantidade(hero, 1) && !anularAlterarFeitico()) {
            for (Card lacaio : hero.getMesa()) {
                partida.getEfeitoProgramado().concederFurtividadeAUmLacaioAteProximoTurno(lacaio.id_long);
            }
        }
    }

    /**
     * Leque de Facas (Cause 1 de dano a todos os lacaios inimigos. Compre um
     * card)
     */
    private static void ex1_129() throws JogouCardException {
        if (validarQuantidade(oponente, 1) && !anularAlterarFeitico()) {
            EmArea.dano(partida, oponente.getMesa(), Utils.getDanoFeitico(1, hero), 0);
            hero.comprarCarta(Card.COMPRA_EVENTO);
        }
    }

    /**
     * Quebra-crânio (Cause 2 de dano ao herói inimigo. Combo: Devolva este card
     * à sua mão no próximo turno)
     */
    private static void ex1_136() throws JogouCardException {
        if (!anularAlterarFeitico() && Utils.isCombo(feitico)) {
            partida.getEfeitoProgramado().adicionarCardMaoHeroiFinalDoTurno("EX1_136");
        }
    }

    /**
     * Passo Furtivo (Devolva um lacaio aliado à sua mão. Ele custa 2 a menos)
     */
    private static void ex1_144() throws JogouCardException {
        if (validarAlvoLacaiosHeroi() && !anularAlterarFeitico() && alvoCard != null) {
            partida.voltarPraMaoDoDono(alvoCard.id_long);
            alvoCard.setCusto(alvoCard.getCost() - 2);
        }
    }

    /**
     * Preparação (O próximo feitiço que você lançar neste turno custará 3 a
     * menos)
     */
    private static void ex1_145() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            partida.getFeiticosAtivos().ativarEX1_145();
        }
    }

    /**
     * Ira (Escolha Um - Cause 3 de dano a um lacaio; ou cause 1 de dano e
     * compre um card)
     */
    private static void ex1_154() throws JogouCardException {
        if (escolherCard(new String[]{"EX1_154a", "EX1_154b"})) {
            if (escolhido.equals("EX1_154a")) {
                ex1_154a();
            } else if (escolhido.equals("EX1_154b")) {
                ex1_154b();
            }
        }
    }

    /**
     * Ira Cause 3 de dano a um lacaio
     */
    private static void ex1_154a() throws JogouCardException {
        danoLacaio(3);
    }

    /**
     * Ira Cause 1 de dano e compre um card
     */
    private static void ex1_154b() throws JogouCardException {
        if (danoPersonagem(1)) {
            feitico.getDono().comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Marca da Natureza (Escolha Um - Conceda +4 de Ataque a um lacaio; ou +4
     * de Vida e Provocar)
     */
    private static void ex1_155() throws JogouCardException {
        if (escolherCard(new String[]{"EX1_155a", "EX1_155b"})) {
            if (escolhido.equals("EX1_155a")) {
                ex1_155a();
            } else if (escolhido.equals("EX1_155b")) {
                ex1_155b();
            }
        }
    }

    /**
     * Marca da Natureza (Conceda +4 de Ataque a um lacaio)
     */
    private static void ex1_155a() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addAtaque(4);
        }
    }

    /**
     * Marca da Natureza (Conceda +4 de Vida e Provocar a um lacaio)
     */
    private static void ex1_155b() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addVidaMaxima(4);
            alvoCard.addMechanics(Values.PROVOCAR);
        }
    }

    /**
     * Alma da Floresta (Conceda aos seus lacaios: Último Suspiro: Evoque um
     * Arvoroso 2/2)
     */
    private static void ex1_158() throws JogouCardException {
        if (validarQuantidade(hero, 1) && !anularAlterarFeitico()) {
            for (Card lacaio : hero.getMesa()) {
                lacaio.addEvocarUltimoSuspiro(Values.ARVOROSO2_2);
            }
        }
    }

    /**
     * Poder da Selva (Escolha Um - Conceda +1/+1 aos seus lacaios ou evoque uma
     * Pantera 3/2)
     */
    private static void ex1_160() throws JogouCardException {
        if (!hero.getMesa().isEmpty()) {
            if (escolherCard(new String[]{"EX1_160a", "EX1_160b"})) {
                if (escolhido.equals("EX1_160a")) {
                    ex1_160a();
                } else if (escolhido.equals("EX1_160b")) {
                    ex1_160b();
                }
            }
        } else {
            ex1_160a();
        }
    }

    /**
     * Evoque uma Pantera (Evoque uma Pantera 3/2)
     */
    private static void ex1_160a() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            hero.evocar(partida.criarCard(Values.PANTERA3_2, System.nanoTime()));
        }
    }

    /**
     * Líder do Bando (Conceda +1/+1 aos seus lacaios)
     */
    private static void ex1_160b() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (Card lacaio : hero.getMesa()) {
                lacaio.addVidaMaxima(1);
                lacaio.addAtaque(1);
            }
        }
    }

    /**
     * Naturalizar (Destrua um lacaio. Seu oponente compra dois cards)
     */
    private static void ex1_161() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.morreu();
            oponente.comprarCarta(Card.COMPRA_FEITICO);
            oponente.comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Nutrir (Escolha Um - Receba 2 Cristais de Mana; ou Compre 3 cards)
     */
    private static void ex1_164() throws JogouCardException {
        if (hero.getMana() < 10) {
            if (escolherCard(new String[]{"EX1_164a", "EX1_164a"})) {
                if (escolhido.equals("EX1_164a")) {
                    ex1_164a();
                } else if (escolhido.equals("EX1_164b")) {
                    ex1_164b();
                }
            }
        } else {
            ex1_164b();
        }
    }

    /**
     * Nutrir (Receba 2 Cristais de Mana)
     */
    private static void ex1_164a() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            hero.addMana(2);
        }
    }

    /**
     * Nutrir (Compre 3 cards)
     */
    private static void ex1_164b() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (int i = 0; i < 3; i++) {
                hero.comprarCarta(Card.COMPRA_FEITICO);
            }
        }
    }

    /**
     * Avivar (Receba 2 Cristais de Mana somente neste turno)
     */
    private static void ex1_169() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            partida.getEfeitoProgramado().concederManaAoHeroiUmTurno(2);
        }
    }

    /**
     * Fogo Estelar (Cause 5 de dano. Compre um card)
     */
    private static void ex1_173() throws JogouCardException {
        if (danoPersonagem(5)) {
            hero.comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Raio (Cause 3 de dano. Sobrecarga: 1)
     */
    private static void ex1_238() throws JogouCardException {
        danoPersonagem(3);
    }

    /**
     * Estouro de Lava (Cause 5 de dano. Sobrecarga 2)
     */
    private static void ex1_241() throws JogouCardException {
        danoPersonagem(5);
    }

    /**
     * Poder Totêmico (Conceda +2 de Vida aos seus Totens)
     */
    private static void ex1_244() throws JogouCardException {
        int quantidade = 0;
        for (Card lacaio : hero.getMesa()) {
            if (lacaio.isTotem()) {
                quantidade++;
            }
        }
        if (quantidade > 0) {
            if (!anularAlterarFeitico()) {
                for (Card lacaio : hero.getMesa()) {
                    if (lacaio.isTotem()) {
                        lacaio.addVidaMaxima(2);
                    }
                }
            }
        } else {
            throw new JogouCardException("Feitiço cancelado.");
        }
    }

    /**
     * Choque Terreno (Silencie um lacaio, então cause 1 de dano a ele)
     */
    private static void ex1_245() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.setSilenciado(true);
            alvoCard.delVida(Utils.getDanoFeitico(1, hero));
        }
    }

    /**
     * Bagata (Transforme um lacaio num Sapo 0/1 com Provocar)
     */
    private static void ex1_246() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            partida.polimorfia(alvoCard.id_long, Values.SAPO0_1PROVOCAR, true);
        }
    }

    /**
     * Espírito Feral (Evoque dois Lobos Espirituais 2/3 com Provocar.
     * Sobrecarga 2)
     */
    private static void ex1_248() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            hero.evocar(partida.criarCard(Values.LOBO_ESPIRITUAL2_3PROVOCAR, System.nanoTime()));
            hero.evocar(partida.criarCard(Values.LOBO_ESPIRITUAL2_3PROVOCAR, System.nanoTime()));
        }
    }

    /**
     * Raio Bifurcado (Cause 2 de dano a 2 lacaios inimigos aleatórios.
     * Sobrecarga 2)
     */
    private static void ex1_251() throws JogouCardException {
        if (validarQuantidade(oponente, 2) && !anularAlterarFeitico()) {
            List<Card> lacaios = Utils.random(2, oponente.getMesa());
            EmArea.dano(partida, lacaios, Utils.getDanoFeitico(2, hero), 0);
        }
    }

    /**
     * Tempestade de Raios (Cause 2-3 de dano a todos os lacaios inimigos.
     * Sobrecarga 2)
     */
    private static void ex1_259() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            EmArea.dano(partida, oponente.getMesa(), 2, 1);
        }
    }

    /**
     * Cone de Frio (Congele um lacaio e os lacaios vizinhos, causando-lhes 1 de
     * dano)
     */
    private static void ex1_275() throws JogouCardException {
        if (validarAlvoLacaiosOponente() && !anularAlterarFeitico() && alvoCard != null) {
            List<Card> lacaios = Utils.getAdjacentes(alvoCard);
            lacaios.add(0, alvoCard);
            for (Card lacaio : lacaios) {
                lacaio.setFreeze(true);
            }
            EmArea.dano(partida, lacaios, Utils.getDanoFeitico(1, hero), 0);
        }
    }

    /**
     * Mísseis Arcanos (Cause 3 de dano dividido aleatoriamente entre todos os
     * inimigos)
     */
    private static void ex1_277() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            int quantidade = Utils.getDanoFeitico(3, hero);
            for (int i = 0; i < quantidade; i++) {
                long alvo = Utils.getAleatorioOponente(partida);
                Utils.dano(partida, alvo, 1);
            }
        }
    }

    /**
     * Estocada (Cause 1 de dano. Compre um card)
     */
    private static void ex1_278() throws JogouCardException {
        if (danoPersonagem(1)) {
            hero.comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Ignimpacto (Cause 10 de dano)
     */
    private static void ex1_279() throws JogouCardException {
        danoPersonagem(10);
    }

    /**
     * Contrafeitiço (Segredo: Quando seu oponente lançar um feitiço, Anule-o)
     */
    private static boolean ex1_287(Card segredo) {
        GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
        SegredoRevelado.processar(segredo);
        MENSAGEM += " o feitiço " + feitico.getName() + " foi anulado pelo "
                + segredo.getName() + " - " + segredo.getDescricao();
        return true;
    }

    /**
     * Contrafeitiço (Segredo: Quando seu oponente lançar um feitiço, Anule-o)
     */
    private static boolean ex1_287ParaSegredo(Card segredo, Card segredoAnulado) {
        GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
        SegredoRevelado.processar(segredo);
        partida.addHistorico("O feitiço " + segredoAnulado.getName() + " foi anulado pelo "
                + segredo.getName() + " - " + segredo.getDescricao());
        return true;
    }

    /**
     * Espiral da Morte (Cause 1 de dano a um lacaio. Se ele morrer, compre um
     * card)
     */
    private static void ex1_302() throws JogouCardException {
        if (danoLacaio(1)) {
            if (alvoCard != null && alvoCard.getDono().getMorto().contains(alvoCard)) {
                hero.comprarCarta(Card.COMPRA_FEITICO);
            }
        }
    }

    /**
     * Chama Sombria (Destrua um lacaio aliado e cause o dano de ataque dele a
     * todos os lacaios inimigos)
     */
    private static void ex1_303() throws JogouCardException {
        if (validarAlvoLacaiosHeroi() && !anularAlterarFeitico() && alvoCard != null) {
            int ataque = alvoCard.getAtaque();
            alvoCard.morreu();
            EmArea.dano(partida, oponente.getMesa(), ataque, 0);
        }
    }

    /**
     * Fogo 'Alma (Cause 4 de dano. Descarte um card aleatório)
     */
    private static void ex1_308() throws JogouCardException {
        if (danoPersonagem(4)) {
            hero.descartar();
        }
    }

    /**
     * Sifão da Alma (Destrua um lacaio. Restaure 3 de Vida do seu herói)
     */
    private static void ex1_309() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico()) {
            if (alvoCard != null) {
                alvoCard.morreu();
            }
            hero.addHealth(hero.getDobrarDanoECura(3));
        }
    }

    /**
     * Espiral Éterea (Destrua todos os lacaios)
     */
    private static void ex1_312() {
        EmArea.destruir(partida.getMesa());
    }

    /**
     * Poder Esmagador (Conceda +4/+4 a um lacaio aliado até o fim do turno.
     * Depois, ele morre. Uma morte horrível)
     */
    private static void ex1_316() throws JogouCardException {
        if (validarAlvoLacaiosHeroi() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addVidaMaxima(4);
            alvoCard.addAtaque(4);
            partida.getEfeitoProgramado().lacaioMorrerFimTurno(alvoCard.id_long, 0);
        }
    }

    /**
     * Detectar Demônios (Coloque 2 Demônios aleatórios do seu deck na sua mão)
     */
    private static void ex1_317() {
        List<Card> demonios = Filtro.raca(hero.getDeck(), Values.DEMONIO);
        if (demonios.isEmpty()) {
            hero.addMao(partida.criarCard(Values.DIABRETE_INUTIL, System.nanoTime()));
            hero.addMao(partida.criarCard(Values.DIABRETE_INUTIL, System.nanoTime()));
        } else if (demonios.size() == 1) {
            hero.addMao(demonios.get(0));
            hero.delDeck(demonios.get(0));
            hero.addMao(partida.criarCard(Values.DIABRETE_INUTIL, System.nanoTime()));
        } else {
            List<Card> aleatorios = Utils.random(2, demonios);
            hero.addMao(aleatorios.get(0));
            hero.delDeck(demonios.get(0));
            hero.addMao(aleatorios.get(1));
            hero.delDeck(demonios.get(1));
        }
    }

    /**
     * Ruína do Destino (Cause 2 de dano a um personagem. Se ele morrer, evoque
     * um Demônio aleatório)
     */
    private static void ex1_320() throws JogouCardException {
        if (danoPersonagem(2)) {
            if (alvoCard != null && alvoCard.getDono().getMorto().contains(alvoCard)) {
                hero.evocar(partida.criarCard(Random.byRace(Values.DEMONIO).getId(), System.nanoTime()));
            }
        }
    }

    /**
     * Silêncio (Silencie um lacaio)
     */
    private static void ex1_332() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.setSilenciado(true);
        }
    }

    /**
     * Insanidade Sombria (Assuma o controle de um lacaio inimigo com 3 ou menos
     * de Ataque até o final do turno)
     */
    private static void ex1_334() throws JogouCardException {
        if (validarAlvoLacaios()) {
            if (alvoCard != null && alvoCard.getAtaque() <= 3) {
                if (!anularAlterarFeitico()) {
                    partida.getEfeitoProgramado().assumirControleAteFimDoTurno(alvoCard.id_long);
                }
            } else {
                throw new JogouCardException("Feitiço cancelado.");
            }
        }
    }

    /**
     * Roubo de Pensamentos (Copie 2 cards do deck do oponente e ponha-os na sua
     * mão)
     */
    private static void ex11_339() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            if (oponente.getDeck().size() == 1) {
                hero.addMao(partida.criarCard(oponente.getDeck().get(0).getId(), System.nanoTime()));
            } else if (oponente.getDeck().size() > 1) {
                List<Card> random = Utils.random(2, oponente.getDeck());
                hero.addMao(partida.criarCard(random.get(0).getId(), System.nanoTime()));
                hero.addMao(partida.criarCard(random.get(1).getId(), System.nanoTime()));
            }
        }
    }

    /**
     * Jogos Mentais (Coloque uma cópia de um lacaio aleatório do deck do
     * oponente no campo de batalha)
     */
    private static void ex1_345() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            Card aleatorio = Utils.getLacaioAleatorio(oponente.getDeck());
            if (aleatorio != null) {
                hero.evocar(partida.criarCard(aleatorio.getId(), System.nanoTime()));
            } else {
                hero.evocar(partida.criarCard("EX1_345t", System.nanoTime()));
            }
        }
    }

    /**
     * Favorecimento Divino (Compre cards até sua mão ficar tão cheia quanto a
     * do oponente)
     */
    private static void ex1_349() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            while (!hero.getDeck().isEmpty() && hero.getMao().size() < oponente.getMao().size()) {
                hero.comprarCarta(Card.COMPRA_FEITICO);
            }
        }
    }

    /**
     * Impor as Mãos (Restaure 8 de Vida. Compre 3 cards)
     */
    private static void ex1_354() throws JogouCardException {
        if (curarPersonagem(8)) {
            for (int i = 0; i < 3; i++) {
                hero.comprarCarta(Card.COMPRA_FEITICO);
            }
        }
    }

    /**
     * Campeão Abençoado (Dobre o Ataque de um lacaio)
     */
    private static void ex1_355() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addAtaque(alvoCard.getAtaque());
        }
    }

    /**
     * Humildade (Mude o Ataque de um lacaio para 1)
     */
    private static void ex1_360() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.setAtaque(1, true);
        }
    }

    /**
     * Bênção de Sabedoria (Escolha um lacaio. Sempre que ele atacar, compre um
     * card)
     */
    private static void ex1_363() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addFazerAoAtacar(FazerAoAtacar.comprarCardHeroi(partida));
        }
    }

    /**
     * Ira Sagrada (Compre um card e cause dano equivalente ao custo dele)
     */
    private static void ex1_365() throws JogouCardException {
        if (validarAlvoPersonagem() && !anularAlterarFeitico()) {
            Card comprado = hero.comprarCarta(Card.COMPRA_FEITICO);
            if (comprado != null) {
                if (alvoCard != null) {
                    alvoCard.delVida(comprado.getCost());
                } else if (alvoHeroi != null) {
                    alvoHeroi.delHealth(comprado.getCost());
                }
            }
        }
    }

    /**
     * Mão da Proteção (Conceda Escudo Divino a um lacaio)
     */
    private static void ex1_371() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addMechanics(Values.ESCUDO_DIVINO);
        }
    }

    /**
     * Ira Vingativa (Cause 8 de dano dividido aleatoriamente entre todos os
     * inimigos)
     */
    private static void ex1_384() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            int dano = Utils.getDanoFeitico(8, hero);
            for (int i = 0; i < dano; i++) {
                Utils.dano(partida, Utils.getAleatorioOponente(partida), 1);
            }
        }
    }

    /**
     * Batida (Cause 2 de dano a um lacaio. Se ele sobreviver, compre um card)
     */
    private static void ex1_391() throws JogouCardException {
        if (danoLacaio(2)) {
            if (alvoCard != null && !alvoCard.getDono().getMorto().contains(alvoCard)) {
                hero.comprarCarta(Card.COMPRA_FEITICO);
            }
        }
    }

    /**
     * Ira de Batalha (Compre um card para cada personagem aliado ferido)
     */
    private static void ex1_392() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            int feridos = Utils.getFeridos(hero.getMesa()).size();
            feridos += hero.isIleso() ? 0 : 1;
            for (int i = 0; i < feridos; i++) {
                hero.comprarCarta(Card.COMPRA_FEITICO);
            }
        }
    }

    /**
     * Redemoinho (Cause 1 de dano a todos os lacaios)
     */
    private static void ex1_400() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            EmArea.dano(partida, partida.getMesa(), Utils.getDanoFeitico(1, hero), 0);
        }
    }

    /**
     * Briga (Destrua todos os lacaios, menos um. (escolhido aleatoriamente))
     */
    private static void ex1_407() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            Card aleatorio = Utils.getLacaioAleatorio(partida.getMesa());
            partida.addHistorico(aleatorio.getName() + " ganhou a briga!");
            List<Card> destruir = new ArrayList<>();
            for (Card lacaio : partida.getMesa()) {
                if (!lacaio.equals(aleatorio)) {
                    destruir.add(lacaio);
                }
            }
            EmArea.destruir(destruir);
        }
    }

    /**
     * Golpe Mortal (Cause 4 de dano. Se você tiver 12 pontos de vica ou menos,
     * cause 6 de dano em vez de 4)
     */
    private static void ex1_408() throws JogouCardException {
        danoPersonagem(hero.getHealth() <= 12 ? 6 : 4);
    }

    /**
     * Aprimoramento (Se você tiver uma arma, conceda-lhe +1/+1. Se não tiver,
     * equipe uma arma 1/3)
     */
    private static void ex1_409() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            if (hero.getArma() != null) {
                hero.getArma().addDurabilidade(1);
                hero.getArma().addAtaque(1);
            } else {
                hero.setWeapon(partida.criarCard(Values.ARMA1_3, System.nanoTime()));
            }
        }
    }

    /**
     * Escudada (Cause 1 de dano a um lacaio para cada ponto de Armadura que
     * você tiver)
     */
    private static void ex1_410() throws JogouCardException {
        if (hero.getShield() > 0) {
            danoLacaio(hero.getShield());
        } else {
            throw new JogouCardException("Feitiço cancelado.");
        }
    }

    /**
     * Tiro Explosivo (Cause 5 de dano a um lacaio e 2 aos adjacentes)
     */
    private static void ex1_537() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            List<Card> adjacentes = Utils.getAdjacentes(alvoCard);
            alvoCard.delVida(Utils.getDanoFeitico(5, hero));
            EmArea.dano(partida, adjacentes, Utils.getDanoFeitico(2, hero), 0);
        }
    }

    /**
     * Soltem os Cães (Para cada lacaio inimigo, evoque um Cão 1/1 com
     * Investida)
     */
    private static void ex1_538() throws JogouCardException {
        if (validarQuantidade(oponente, 1) && !anularAlterarFeitico()) {
            int quantidade = oponente.getMesa().size();
            for (int i = 0; i < quantidade; i++) {
                hero.evocar(partida.criarCard(Values.CAO1_1, System.nanoTime()));
            }
        }
    }

    /**
     * Comando para Matar (Cause 3 de dano. Se você tiver uma Fera, cause 5 de
     * dano em vez de 3)
     */
    private static void ex1_539() throws JogouCardException {
        danoPersonagem(hero.temFeraNaMesa() ? 5 : 3);
    }

    /**
     * Sinalizador (Todos os lacaios perdem Furtividade. Destrua todos os
     * Segredos do inimigo. Compre um card)
     */
    private static void ex1_544() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (Card lacaio : partida.getMesa()) {
                if (lacaio.isFurtivo()) {
                    lacaio.delMechanics(Values.FURTIVIDADE);
                }
            }
            while (!oponente.getSegredo().isEmpty()) {
                oponente.delSegredo(oponente.getSegredo().get(0));
            }
            hero.comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Ira Bestial (Conceda +2 de Ataque e Imunidade a uma Fera aliada neste
     * turno)
     */
    private static void ex1_549() throws JogouCardException {
        if (validarAlvoAliadoByRace(Values.FERA) && !anularAlterarFeitico() && alvoCard != null) {
            partida.getEfeitoProgramado().concederAtaqueAUmLacaioUmTurno(alvoCard.id_long, 2);
            partida.getEfeitoProgramado().concederImunidadeAUmLacaioUmTurno(alvoCard.id_long);
        }
    }

    /**
     * Morder (Conceda +4 de Ataque ao seu herói neste turno e 4 de Armadura)
     */
    private static void ex1_570() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            partida.getEfeitoProgramado().concederAtaqueAoHeroiUmTurno(4);
            hero.addShield(4);
        }
    }

    /**
     * Força da Natureza (Evoque três Arvorosos 2/2 com Investida, que Morrerão
     * no fim do turno)
     */
    private static void ex1_571() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (int i = 0; i < 3; i++) {
                hero.evocar(partida.criarCard(Values.ARVOROSO2_2INVESTIDAMORREFIMTURNO, System.nanoTime()));
            }
        }
    }

    /**
     * Selvageria (Cause dano equivalente ao Ataque do seu herói a um lacaio)
     */
    private static void ex1_578() throws JogouCardException {
        if (hero.getAttack() > 1) {
            danoLacaio(hero.getAttack());
        } else {
            throw new JogouCardException("Feitiço cancelado.");
        }
    }

    /**
     * Aturdir (Devolva um lacaio inimigo à mão do seu oponente)
     */
    private static void ex1_581() throws JogouCardException {
        if (validarAlvoLacaiosOponente() && !anularAlterarFeitico() && alvoCard != null) {
            partida.voltarPraMaoDoDono(alvoCard.id_long);
        }
    }

    /**
     * Fogo Demoníaco (Cause 2 de dano a um lacaio. Se for um Demônio aliado, em
     * vez disso, conceda-lhe +2/+2)
     */
    private static void ex1_596() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            if (alvoCard.isDemonio() && hero.isCard(alvoCard.id_long)) {
                alvoCard.addVidaMaxima(2);
                alvoCard.addAtaque(2);
            } else {
                alvoCard.delVida(Utils.getDanoFeitico(2, hero));
            }
        }
    }

    /**
     * Levantar Escudo (Receba 5 de Armadura. Compre um card)
     */
    private static void ex1_606() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            hero.addShield(5);
            hero.comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Raiva Interior (Cause 1 de dano a um lacaio e conceda-lhe + 2 de Ataque)
     */
    private static void ex1_607() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.delVida(1);
            alvoCard.addAtaque(2);
        }
    }

    /**
     * Disparo Mortal (Destrua um lacaio inimigo aleatório)
     */
    private static void ex1_617() throws JogouCardException {
        if (validarQuantidade(oponente, 1) && !anularAlterarFeitico()) {
            Utils.getLacaioAleatorio(oponente.getMesa()).morreu();
        }
    }

    /**
     * Igualdade (Mude a Vida de Todos os lacaios para 1)
     */
    private static void ex1_619() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (Card lacaio : partida.getMesa()) {
                lacaio.setVidaOriginal(1, false);
            }
        }
    }

    /**
     * Círculo de Cura (Restaure 4 de Vida de TODOS os lacaios)
     */
    private static void ex1_621() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            EmArea.cura(partida, partida.getMesa(), hero.getDobrarDanoECura(4));
        }
    }

    /**
     * Palavra Sombria: Morte (Destrua um lacaio com 5 ou mais de Ataque)
     */
    private static void ex1_622() throws JogouCardException {
        if (validarAlvoLacaios()) {
            if (alvoCard != null && alvoCard.getAtaque() >= 5) {
                if (!anularAlterarFeitico() && alvoCard != null) {
                    alvoCard.morreu();
                }
            } else {
                throw new JogouCardException("Feitiço cancelado.");
            }
        }
    }

    /**
     * Fogo Sagrado (Cause 5 de dano. Restaure 5 de Vida do seu herói)
     */
    private static void ex1_624() throws JogouCardException {
        if (danoPersonagem(5)) {
            hero.addHealth(5);
        }
    }

    /**
     * Forma de Sombra (Seu Poder Heroico se torna "Cause 2 de dano". Se você já
     * estiver em Forma de Sombra: 3 de dano)
     */
    private static void ex1_625() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            if (!hero.getPoderHeroico().getTipo().equals(PRIEST_EX1_625B)
                    && hero.getPoderHeroico().getTipo().equals(PRIEST_EX1_625)) {
                hero.setPoderHeroico(PRIEST_EX1_625B);
            } else {
                hero.setPoderHeroico(PRIEST_EX1_625);
            }
        }
    }

    /**
     * Dissipação em Massa (Silencie todos os lacaios inimigos. Compre um card)
     */
    private static void ex1_626() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (Card lacaio : oponente.getMesa()) {
                lacaio.setSilenciado(true);
            }
            hero.comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Sementes de Veneno (Destrua todos os lacaios e evoque Arvorosos 2/2 para
     * substituí-los)
     */
    private static void fp1_019() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            int quantidadeHeroi = hero.getMesa().size();
            int quantidadeOponente = oponente.getMesa().size();
            EmArea.destruir(partida.getMesa());
            for (int i = 0; i < quantidadeHeroi; i++) {
                hero.evocar(partida.criarCard(Values.ARVOROSO2_2T2, System.nanoTime()));
            }
            for (int i = 0; i < quantidadeOponente; i++) {
                oponente.evocar(partida.criarCard(Values.ARVOROSO2_2T2, System.nanoTime()));
            }
        }
    }

    /**
     * Reencarnar (Destrua um lacaio, depois ressuscite-o com a Vida completa)
     */
    private static void fp1_025() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.morreu();
            alvoCard.getDono().evocar(partida.criarCard(alvoCard.getId(), System.nanoTime()));
        }
    }

    /**
     * Canhão de Chamas (Cause 4 de dano a um lacaio inimigo aleatório)
     */
    private static void gvg_001() throws JogouCardException {
        if (validarQuantidade(oponente, 1) && !anularAlterarFeitico()) {
            Utils.getLacaioAleatorio(oponente.getMesa()).delVida(Utils.getDanoFeitico(4, hero));
        }
    }

    /**
     * Portal Instável (Adicione um lacaio aleatório à sua mão. Ele custa 3 a
     * menos)
     */
    private static void gvg_003() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            Card aleatorio = partida.criarCard(Random.lacaio().getId(), System.nanoTime());
            aleatorio.delCusto(3);
            hero.addMao(aleatorio);
        }
    }

    /**
     * Eco de Medivh (Coloque uma cópia de cada lacaio aliado na sua mão)
     */
    private static void gvg_005() throws JogouCardException {
        if (validarQuantidade(hero, 1) && !anularAlterarFeitico()) {
            for (Card lacaio : hero.getMesa()) {
                hero.addMao(partida.criarCard(lacaio.getId(), System.nanoTime()));
            }
        }
    }

    /**
     * Bomba Luminosa (Cause dano a todos os lacaios igual ao Ataque de cada um)
     */
    private static void gvg_008() throws JogouCardException {
        if (!partida.getMesa().isEmpty()) {
            if (!anularAlterarFeitico()) {
                List<Card> lacaios = partida.getMesa();
                lacaios.sort(Sort.time());
                List<Integer> vidaAnterior = new ArrayList<>();
                for (Card lacaio : lacaios) {
                    vidaAnterior.add(lacaio.getVida());
                    lacaio.delVidaEmArea(lacaio.getAtaque());
                }
                for (int i = 0; i < vidaAnterior.size(); i++) {
                    try {
                        if (lacaios.get(i).getVida() < vidaAnterior.get(i)) {
                            int damage = vidaAnterior.get(i) - lacaios.get(i).getVida();
                            GameCliente.addHistorico(lacaios.get(i).getName() + " recebeu " + damage + " de dano.", true);
                            PersonagemRecebeuDano.processar(null, lacaios.get(i), damage, lacaios);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                for (Card lacaio : lacaios) {
                    if (lacaio.getVida() <= 0) {
                        lacaio.morreu();
                    }
                }
            }
        } else {
            throw new JogouCardException("Feitiço cancelado.");
        }
    }

    /**
     * Escolhido de Velen (Conceda +2/+4 e +1 de Dano Mágico a um lacaio)
     */
    private static void gvg_010() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addVidaMaxima(4);
            alvoCard.addAtaque(2);
            alvoCard.addDanoMagico(1);
        }
    }

    /**
     * Luz dos Naarus (Restaure 3 de Vida. Se o alvo ainda estiver ferido,
     * evoque uma Guardiã da Luz)
     */
    private static void gvg_012() throws JogouCardException {
        if (curarPersonagem(3)) {
            if ((alvoCard != null && !alvoCard.isIleso()) || (alvoHeroi != null && !alvoHeroi.isIleso())) {
                hero.evocar(partida.criarCard(Values.GUARDIA_DA_LUZ, System.nanoTime()));
            }
        }
    }

    /**
     * Bomba Negra (Cause 3 de dano)
     */
    private static void gvg_015() throws JogouCardException {
        danoPersonagem(3);
    }

    /**
     * Chamar Ajudante (Compre um card. Se for uma Fera, ela custará 4 a menos)
     */
    private static void gvg_017() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            Card card = hero.comprarCarta(Card.COMPRA_FEITICO);
            if (card != null && card.isFera()) {
                card.delCusto(4);
            }
        }
    }

    /**
     * Óleo de Arma do Faz-tudo (Conceda +3 de Ataque à sua arma. Combo: Conceda
     * +3 de Ataque a um lacaio aliado aleatório)
     */
    private static void gvg_022() throws JogouCardException {
        if (validarArmaHeroi() && !anularAlterarFeitico()) {
            hero.getArma().addAtaque(3);
            if (Utils.isCombo(feitico) && !hero.getMesa().isEmpty()) {
                Utils.getLacaioAleatorio(hero.getMesa()).addAtaque(3);
            }
        }
    }

    /**
     * Fingir de Morto (Ative todos os Últimos Suspiros dos seus lacaios)
     */
    private static void gvg_026() throws JogouCardException {
        if (validarQuantidade(hero, 1) && !anularAlterarFeitico()) {
            for (Card lacaio : hero.getMesa()) {
                UltimoSuspiro.processar(lacaio, lacaio.getDono().getPosicaoNaMesa(lacaio.id_long));
            }
        }
    }

    /**
     * Chamado do Ancestral (Coloque um lacaio aleatório da mão de cada jogador
     * no campo de batalha)
     */
    private static void gvg_029() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            Card aleatorio = Utils.getLacaioAleatorio(hero.getMao());
            if (aleatorio != null && aleatorio.getDono().temEspacoNaMesa()) {
                hero.evocar(aleatorio);
                hero.delMao(aleatorio);
            }
            aleatorio = Utils.getLacaioAleatorio(oponente.getMao());
            if (aleatorio != null && aleatorio.getDono().temEspacoNaMesa()) {
                oponente.evocar(aleatorio);
                oponente.delMao(aleatorio);
            }
        }
    }

    /**
     * Reciclar (Coloque um lacaio inimigo no deck do seu oponente)
     */
    private static void gvg_031() throws JogouCardException {
        if (validarAlvoLacaiosOponente() && !anularAlterarFeitico() && alvoCard != null) {
            partida.cardDefault(alvoCard.id_long, alvoCard.getId());
            partida.addHistorico(alvoCard.getName() + " foi reembaralhado no deck do dono.");
            alvoCard.getDono().addCardDeckAleatoriamente(alvoCard);
            alvoCard.getDono().delMesa(alvoCard);
        }
    }

    /**
     * Árvore da Vida (Restaure toda a Vida de todos os personagens)
     */
    private static void gvg_033() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            hero.addHealth(hero.getVidaTotal() - hero.getHealth());
            oponente.addHealth(oponente.getVidaTotal() - oponente.getHealth());
            EmArea.cura(partida, partida.getMesa(), 999999999);
        }
    }

    /**
     * Blindagem (Conceda +1 de Vida a um lacaio)
     */
    private static void part_001() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addVidaMaxima(1);
        }
    }

    /**
     * Retroceder Tempo (Devolva um lacaio aliado à sua mão)
     */
    private static void part_002() throws JogouCardException {
        if (validarAlvoLacaiosHeroi() && !anularAlterarFeitico() && alvoCard != null) {
            partida.voltarPraMaoDoDono(alvoCard.id_long);
        }
    }

    /**
     * Chifre Enferrujado (Conceda Provocar a um lacaio)
     */
    private static void part_003() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addMechanics(Values.PROVOCAR);
        }
    }

    /**
     * Camuflagem Instável (Conceda Furtividade a um lacaio aliado até o seu
     * próximo turno)
     */
    private static void part_004() throws JogouCardException {
        if (validarAlvoLacaiosHeroi() && !anularAlterarFeitico() && alvoCard != null) {
            partida.getEfeitoProgramado().concederFurtividadeAUmLacaioAteProximoTurno(alvoCard.id_long);
        }
    }

    /**
     * Resfriador de Emergência (Congele um lacaio)
     */
    private static void part_005() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.setFreeze(true);
        }
    }

    /**
     * Chave Reversora (Troque o Ataque pela Vida de um lacaio)
     */
    private static void part_006() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            int vida = alvoCard.getVida();
            alvoCard.setVidaOriginal(alvoCard.getAtaque(), true);
            alvoCard.setAtaque(vida, true);
        }
    }

    /**
     * Lâminas Rodopiantes (Conceda +1 de Ataque a um lacaio)
     */
    private static void part_007() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addAtaque(1);
        }
    }

    /**
     * Estalar (Cause de 3 a 6 de dano. Sobrecarga 1)
     */
    private static void gvg_038() throws JogouCardException {
        danoPersonagem(3 + (Utils.random(99) % 4));
    }

    /**
     * Fogo Enfático (Escolha Um - Evoque 5 Fogos-fátuos ou Conceda +5/+5 e
     * Provocar a um lacaio)
     */
    private static void gvg_041() throws JogouCardException {
        if (escolherCard(new String[]{"GVG_041a", "GVG_041b"})) {
            if (escolhido.equals("GVG_041a")) {
                gvg_041a();
            } else if (escolhido.equals("GVG_041b")) {
                gvg_041b();
            }
        }
    }

    /**
     * Fogo Enfático (Conceda +5/+5 e Provocar a um lacaio)
     */
    private static void gvg_041a() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addVidaMaxima(5);
            alvoCard.addAtaque(5);
            if (!alvoCard.isProvocar()) {
                alvoCard.addMechanics(Values.PROVOCAR);
            }
        }
    }

    /**
     * Fogo Enfático (Evoque 5 Fogos-fátuos)
     */
    private static void gvg_041b() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (int i = 0; i < 5; i++) {
                hero.evocar(partida.criarCard(Values.FOGO_FATUO, System.nanoTime()));
            }
        }
    }

    /**
     * Implosão de Diabrete (Cause 2-4 de dano a um lacaio. Evoque um Diabrete
     * 1/1 para cada ponto de dano causado)
     */
    private static void gvg_045() throws JogouCardException {
        int dano = 2 + (Utils.random(99) % 3);
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            int vidaAnterior = alvoCard.getVida();
            alvoCard.delVida(Utils.getDanoFeitico(dano, hero));
            int evocar = vidaAnterior - alvoCard.getVida();
            for (int i = 0; i < evocar; i++) {
                hero.evocar(partida.criarCard(Values.DIABRETE1_1T2, System.nanoTime()));
            }
        }
    }

    /**
     * Sabotagem (Destrua um lacaio inimigo aleatório. Combo: E a arma do seu
     * oponente)
     */
    private static void gvg_047() throws JogouCardException {
        if (validarQuantidade(oponente, 1) && !anularAlterarFeitico()) {
            Utils.getLacaioAleatorio(oponente.getMesa()).morreu();
            if (Utils.isCombo(feitico) && oponente.getArma() != null) {
                oponente.destruirArma();
            }
        }
    }

    /**
     * Lâmina Quicante (Cause 1 de dano a um lacaio aleatório. Repita até um
     * lacaio morrer)
     */
    private static void gvg_050() throws JogouCardException {
        if (partida.getFeiticosAtivos().isNEW1_036() && oponente.getMesa().isEmpty()) {
            throw new JogouCardException("Feitiço cancelado.");
        } else if (!partida.getMesa().isEmpty()) {
            if (!anularAlterarFeitico()) {
                int dano = Utils.getDanoFeitico(1, hero);
                while (true) {
                    Card aleatorio = Utils.getLacaioAleatorio(partida.getMesa());
                    aleatorio.delVida(dano);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                    }
                    if (aleatorio.getVida() <= 0) {
                        break;
                    }
                }
            }
        } else {
            throw new JogouCardException("Feitiço cancelado.");
        }

    }

    /**
     * Esmagar (Destrua um lacaio. Se você tiver um lacaio ferido, este feitiço
     * custa 4 a menos)
     */
    private static void gvg_052() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.morreu();
        }
    }

    /**
     * Selo da Luz (Restaure 4 de Vida do seu herói e ganhe +2 de Ataque neste
     * turno)
     */
    private static void gvg_057() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            feitico.getDono().addHealth(hero.getDobrarDanoECura(4));
            partida.getEfeitoProgramado().concederAtaqueAoHeroiUmTurno(2);
        }
    }

    /**
     * Preparação de Batalha (Evoque três Recrutas do Punho de Prata 1/1. Equipe
     * uma Arma 1/4)
     */
    private static void gvg_061() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (int i = 0; i < 3; i++) {
                hero.evocar(partida.criarCard(Values.RECRUTA_PUNHO_DE_PRATA, System.nanoTime()));
            }
            hero.setWeapon(partida.criarCard(Values.ARMA1_4, System.nanoTime()));
        }
    }

    /**
     * Disparo da Naja (cause 3 de dano a um lacaio e ao herói inimigo)
     */
    private static void gvg_073() throws JogouCardException {
        int dano = Utils.getDanoFeitico(3, hero);
        if (danoLacaio(3)) {
            oponente.delHealth(dano);
        }
    }

    /**
     * Tocha Esquecida (Causa 3 de dano. Embaralhe no seu deck uma "Tocha
     * Voraz", que causa 6 de dano)
     */
    private static void loe_002() throws JogouCardException {
        if (danoPersonagem(3)) {
            hero.addCardDeckAleatoriamente(partida.criarCard(Values.TOCHA_VORAZ, System.nanoTime()));
        }
    }

    /**
     * Tocha Voraz (Cause 6 de dano)
     */
    private static void loe_002t() throws JogouCardException {
        danoPersonagem(6);
    }

    /**
     * Murloucura (Evoque 7 Murlocs mortos nesta partidaView)
     */
    private static void loe_026() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            List<Card> mortos = new ArrayList<>();
            mortos.addAll(hero.getMorto());
            mortos.addAll(oponente.getMorto());
            mortos = Filtro.raca(mortos, Values.MURLOC);
            if (mortos.size() < 7) {
                mortos = Utils.random(mortos.size(), mortos);
            } else {
                mortos = Utils.random(7, mortos);
            }
            for (Card evocar : mortos) {
                hero.evocar(partida.criarCard(evocar.getId(), System.nanoTime()));
            }
        }
    }

    /**
     * Mapa do Macaco Dourado (Embaralhe o Macaco Dourado no seu deck. Compre um
     * card)
     */
    private static void loe_019t() {
        feitico.getDono().addCardDeckAleatoriamente(partida.criarCard(Values.MACACO_DOURADO, System.nanoTime()));
        feitico.getDono().comprarCarta(Card.COMPRA_EVENTO);
    }

    /**
     * Lanterna do Poder (Conceda +10/+10 a um lacaio)
     */
    private static void loea16_3() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addVidaMaxima(10);
            alvoCard.addAtaque(10);
        }
    }

    /**
     * Medidor de Tempo do Terror (Cause 10 de dano dividido aleatoriamente
     * entre todos os inimigos)
     */
    private static void loea16_4() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            int dano = Utils.getDanoFeitico(10, hero);
            for (int i = 0; i < dano; i++) {
                Utils.dano(partida, Utils.getAleatorioOponente(partida), 1);
            }
        }
    }

    /**
     * Espelho da Ruína (Encha seu tabuleiro com Múmias Zumbis 3/3)
     */
    private static void loea16_5() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            while (hero.temEspacoNaMesa()) {
                hero.evocar(partida.criarCard(Values.MUMIA_ZUMBI3_3, System.nanoTime()));
            }
        }
    }

    /**
     * Sepultar (Escolha um lacaio inimigo. Embaralhe-o no seu deck)
     */
    private static void loe_104() throws JogouCardException {
        if (validarAlvoLacaiosOponente() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.getDono().delMesa(alvoCard);
            partida.cardDefault(alvoCard.id_long, alvoCard.getId());
            hero.addCardDeckAleatoriamente(alvoCard);
        }
    }

    /**
     * Chapéu de Explorador (Conceda +1/+1 a um lacaio e "Último Suspiro:
     * Adicione um Chapéu de Explorador à sua mão")
     */
    private static void loe_105() throws JogouCardException {
        if (validarAlvoLacaios() && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.addVidaMaxima(1);
            alvoCard.addAtaque(1);
            alvoCard.addAddMaoUltimoSuspiro("LOE_105");
        }
    }

    /**
     * Mal Desencavado (Cause 3 de dano a todos os lacaios. Embaralhe este card
     * no deck do seu oponente)
     */
    private static void loe_111() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            EmArea.dano(partida, partida.getMesa(), Utils.getDanoFeitico(3, hero), 0);
            oponente.addCardDeckAleatoriamente(partida.criarCard("LOE_111", System.nanoTime()));
        }
    }

    /**
     * Retoque de Murloc (Conceda aos seus lacaios +2/+2. Custa 1 a menos por
     * cada Murloc sob seu controle)
     */
    private static void loe_113() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            for (Card lacaio : hero.getMesa()) {
                lacaio.addVidaMaxima(2);
                lacaio.addAtaque(2);
            }
        }
    }

    /**
     * Pacto Sacrificial (Destrua um Demônio. Restaure 5 de Vida do seu herói)
     */
    private static void new1_003() throws JogouCardException {
        if (validarAlvoByRace(Values.DEMONIO) && !anularAlterarFeitico() && alvoCard != null) {
            alvoCard.morreu();
            hero.addHealth(hero.getDobrarDanoECura(5));
        }
    }

    /**
     * NEW1_004;Sumir (Devolva todos os lacaios à mão do dono)
     */
    private static void new1_004() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            List<Card> mesa = partida.getMesa();
            for (Card lacaio : mesa) {
                partida.voltarPraMaoDoDono(lacaio.id_long);
            }
        }
    }

    /**
     * Chuva Estelar (Escolha Um - Cause 5 de dano a um lacaio ou 2 de dano a
     * todos os lacaios inimigos)
     */
    private static void new1_007() throws JogouCardException {
        if (escolherCard(new String[]{"NEW1_007a", "NEW1_007b"})) {
            if (escolhido.equals("NEW1_007a")) {
                new1_007a();
            } else if (escolhido.equals("NEW1_007b")) {
                new1_007b();
            }
        }
    }

    /**
     * Chuva Estelar (Cause 2 de dano a todos os lacaios inimigos)
     */
    private static void new1_007a() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            EmArea.dano(partida, oponente.getMesa(),
                    Utils.getDanoFeitico(2, hero), 0);
        }
    }

    /**
     * Chuva Estelar (Cause 5 de dano a um lacaio)
     */
    private static void new1_007b() throws JogouCardException {
        danoLacaio(5);
    }

    /**
     * Companheiro Animal (Evoque um Fera Companheira aleatória)
     */
    private static void new1_031() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            hero.evocar(partida.criarCard(Random.companheiroAnimal(), System.nanoTime()));
        }
    }

    /**
     * Brado de Comando (A vida de seus lacaios não pode ser reduzida a menos de
     * 1 neste turno. Compre um card)
     */
    private static void new1_036() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            partida.getFeiticosAtivos().ativarNEW1_036();
            hero.comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Dobradora de Feitiços (Segredo: Quando um inimigo lançar um feitiço em um
     * lacaio, evoque um 1/3 para servir de alvo
     *
     * @param segredo Card
     */
    private static void tt_010(Card segredo) throws JogouCardException {
        if (alvoCard != null) {
            GameCliente.exibirSegredoRevelado(segredo.getId(), segredo.getDono().getHeroi());
            Card alvo = partida.criarCard(Values.DOBRADORA_DE_FEITICOS, System.nanoTime());
            segredo.getDono().evocar(alvo);
            MENSAGEM += " o alvo do feitiço " + feitico.getName() + " foi alterado para " + alvo.getName() + " pelo " + segredo.getName();
            pack.set(Param.ALVO, alvo.id_long);
            SegredoRevelado.processar(segredo);
        }
    }

    /**
     * Eu sou Murloc (Evoca três, quatro ou cinco Murlocs 1/1)
     */
    private static void pro_001a() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            int quantidade = 3 + (Utils.random(99) % 3);
            for (int i = 0; i < quantidade; i++) {
                hero.evocar(partida.criarCard(Values.MURLOC1_1, System.nanoTime()));
            }
        }
    }

    /**
     * Coisas de Ladino (Cause 4 de dano. Compre um card)
     */
    private static void pro_001b() throws JogouCardException {
        if (danoPersonagem(4)) {
            hero.comprarCarta(Card.COMPRA_FEITICO);
        }
    }

    /**
     * Poder da Horda (Evoque um Guerreiro da Horda aleatório)
     */
    private static void pro_001c() throws JogouCardException {
        if (!anularAlterarFeitico()) {
            hero.evocar(partida.criarCard(Random.guerreiroDaHorda(), System.nanoTime()));
        }
    }

    public static boolean anularSegredo(Partida partida, Card segredoAnulado) {
        UtilizarFeitico.partida = partida;
        List<Card> list = new ArrayList<>();
        for (Card segredo : segredoAnulado.getOponente().getSegredo()) {
            list.add(segredo);
        }
        for (int i = 0; i < list.size(); i++) {
            switch (list.get(i).getId()) {
                //Contrafeitiço (Segredo: Quando seu oponente lançar um feitiço, Anule-o)
                case "EX1_287":
                    return ex1_287ParaSegredo(list.get(i), segredoAnulado);
            }
        }
        return false;
    }

}