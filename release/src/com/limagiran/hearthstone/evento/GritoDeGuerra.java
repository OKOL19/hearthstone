package com.limagiran.hearthstone.evento;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.heroi.control.*;
import com.limagiran.hearthstone.util.*;
import com.limagiran.hearthstone.partida.control.Partida;
import com.limagiran.hearthstone.partida.util.*;
import com.limagiran.hearthstone.partida.view.EscolherCard;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinicius Silva
 */
public class GritoDeGuerra implements Values {

    private static Partida partida;
    private static Card card;
    private static long escolhido;
    private static final long EXECUTAR_GRITO_DE_GUERRA = 1L;
    private static boolean REPETIR_GRITO_DE_GUERRA = false;

    /**
     * Verifica se há algum evento para grito de guerra
     *
     * @param card card que gerou o grito de guerra
     * @throws JogouCardException
     */
    public static void processar(Card card) throws JogouCardException {
        if ((GritoDeGuerra.card = card) != null) {
            escolhido = EXECUTAR_GRITO_DE_GUERRA;
            GritoDeGuerra.partida = card.getPartida();
            if (partida.isVezHeroi()) {
                //Brann Barbabronze (Seus Gritos de Guerra são ativados duas vezes)
                REPETIR_GRITO_DE_GUERRA = card.getDono().temNaMesa(BRANN_BARBABRONZE);
                switchGritoDeGuerra();
                if (REPETIR_GRITO_DE_GUERRA) {
                    escolhido = escolhido == EXECUTAR_GRITO_DE_GUERRA ? 0L : escolhido;
                    switchGritoDeGuerra();
                }
            }
        }
    }

    /**
     * Executar o método referente ao grito de guerra
     *
     * @throws JogouCardException grito de guerra cancelado
     */
    public static void switchGritoDeGuerra() throws JogouCardException {
        switch (card.getId()) {
            //Millhouse Manavento (Grito de Guerra: Feitiços inimigos custam (0) no próximo turno)
            case "NEW1_029":
                new1_029();
                break;
            //Lança-feitiços (Grito de Guerra: Adicione um feitiço aleatório na mão de cada jogador)
            case "AT_007":
                at_007();
                break;
            //Tratador de Carneiros (Grito de Guerra: Se você tiver uma Fera, evoque uma Fera aleatória)
            case "AT_010":
                at_010();
                break;
            //Guardião do Crepúsculo (Grito de Guerra: Se você tiver um Dragão na mão, receba +1 de Ataque e Provocar)
            case "AT_017":
                at_017();
                break;
            //Negociante Escuso (Grito de Guerra: Se você tiver um Pirata, receba +1/+1)
            case "AT_032":
                at_032();
                break;
            //Aspirante de Darnassus (Grito de Guerra: Receba um Cristal de Mana vazio. Último Suspiro: Perca um Cristal de Mana)
            case "AT_038":
                at_038();
                break;
            //Andarilha da Selva (Grito de Guerra: Conceda +3 de vida a uma Fera aliada)
            case "AT_040":
                at_040();
                break;
            //Morsano Totêmico (Grito de Guerra: Evoca QUALQUER totem aleatório)
            case "AT_046":
                at_046();
                break;
            //Entalhadora de Totens (Grito de Guerra: Receba +1/+1 para cada Totem aliado)
            case "AT_047":
                at_047();
                break;
            //O Chamabruma (Grito de Guerra: conceda +1/+1 a todos os lacaios na sua mão e no seu deck)
            case "AT_054":
                at_054();
                break;
            //Mestre de Estábulo (Grito de Guerra: Conceda Imunidade a uma Fera aliada neste turno)
            case "AT_057":
                at_057();
                break;
            //Elekk do Rei (Grito de Guerra: Revele um lacaio em cada deck. Se o seu custar mais, compre-o)
            case "AT_058":
                at_058();
                break;
            //Defensora do Rei (Grito de Guerra: Se você tiver um lacaio com Provocar, receba +1 de Durabilidade)
            case "AT_065":
                at_065();
                break;
            //Parceiro de Pugilato (Provocar Grito de Guerra: Conceda Provocar a um lacaio)
            case "AT_069":
                at_069();
                break;
            //Campeã de Alexstrasza (Grito de Guerra: Se você tiver um Dragão na mão, receba +1 de Ataque e Investida)
            case "AT_071":
                at_071();
                break;
            //Varian Wrynn (Grito de Guerra: Compre 3 cards. Coloque os lacaios que você comprou diretamente no campo de batalha)
            case "AT_072":
                at_072();
                break;
            //Lança Argêntea (Grito de Guerra: Revele um lacaio em cada deck. Se o seu custar mais, +1 de Durabilidade)
            case "AT_077":
                at_077();
                break;
            //Desafiante Misterioso (Grito de Guerra: Coloque um Segredo de cada do seu deck no campo de batalha)
            case "AT_079":
                at_079();
                break;
            //Eadric, o Puro (Grito de Guerra: Mude o Ataque de todos os lacaios inimigos para 1)
            case "AT_081":
                at_081();
                break;
            //Carregador de Lança (Grito de Guerra: Conceda +2 de Ataque a um lacaio aliado)
            case "AT_084":
                at_084();
                break;
            //Sabotador (Grito de Guerra: O poder Heroico do seu oponente custa (5) a mais no próximo turno)
            case "AT_086":
                at_086();
                break;
            //Malabarista de Chamas (Grito de Guerra: Cause 1 de dano a um inimigo aleatório)
            case "AT_094":
                at_094();
                break;
            //Cavaleiro de Corda (Grito de Guerra: Conceda +1/+1 a um Mecaoide aliado)
            case "AT_096":
                at_096();
                break;
            //Engolidora de Feitiços (Grito de Guerra: Copie o Poder Heroico do seu oponente)
            case "AT_098":
                at_098();
                break;
            //Kraken do Mar do Norte (Grito de Guerra: Cause 4 de dano)
            case "AT_103":
                at_103();
                break;
            //Morsano Justador (Grito de Guerra: Revele um lacaio em cada deck. Se o seu custar mais, restaure 7 de Vida do seu herói)
            case "AT_104":
                at_104();
                break;
            //Kvaldir Ferido (Grito de Guerra: Cause 3 de dano a este lacaio)
            case "AT_105":
                at_105();
                break;
            //Campeão da Luz (Grito de Guerra: Silencie um Demônio)
            case "AT_106":
                at_106();
                break;
            //Cavalo de Guerra (Grito de Guerra: Revele um lacaio em cada deck. Se o seu custar mais, ganhe Investida)
            case "AT_108":
                at_108();
                break;
            //Vendedor de Salgadinho (Grito de Guerra: Restaure 4 de vida de todos os heróis)
            case "AT_111":
                at_111();
                break;
            //Mestre Justador (Grito de Guerra: Revele um lacaio em cada deck. Se o seu custar mais, receba Provocar e Escudo Divino)
            case "AT_112":
                at_112();
                break;
            //Treinador de Esgrima (Grito de Guerra: Na próxima vez que você usar seu Poder Heroico, ele custará 2 a menos)
            case "AT_115":
                at_115();
                break;
            //Agente do Repouso das Serpes (Grito de Guerra: Se você tiver um Dragão na mão, receba +1 de Ataque e Provocar)
            case "AT_116":
                at_116();
                break;
            //Mestre de Cerimônias (Grito de Guerra: Se você tiver um lacaio com Dano Mágico, ganhe +2/+2)
            case "AT_117":
                at_117();
                break;
            //Grã-Cruzada (Grito de Guerra: Adicione um card de Paladino aleatório à sua mão)
            case "AT_118":
                at_118();
                break;
            //Gormok, o Empalador (Grito de Guerra: se você possuir pelo menos outros 4 lacaios, cause 4 de dano)
            case "AT_122":
                at_122();
                break;
            //Justicar Veras (Grito de Guerra: Substitui seu Poder Heroico inicial por um melhor)
            case "AT_132":
                at_132();
                break;
            //Justadora de Gerintontzan (Grito de Guerra: Revele um lacaio em cada deck. Se o seu custar mais, ganhe +1/+1)
            case "AT_133":
                at_133();
                break;
            //Dragonete Crepuscular (Grito de Guerra: Se você tiver um Dragão na mão, receba +2 pontos de vida)
            case "BRM_004":
                brm_004();
                break;
            //Ferro Negro Bisbilhoteiro (Grito de Guerra: Cause 2 de dano a todos os lacaios inimigos ilesos)
            case "BRM_008":
                brm_008();
                break;
            //Flamiguarda Destruidor (Grito de Guerra: Receba 1-4 de Ataque. Sobrecarga: 1)
            case "BRM_012":
                brm_012();
                break;
            //Furiante do Núcleo (Grito de Guerra: Se sua mão estiver vazia, receba +3/+3)
            case "BRM_014":
                brm_014();
                break;
            //Consorte Dragônica (Grito de Guerra: O próximo Dragão que você jogar custa 2 a menos)
            case "BRM_018":
                brm_018();
                break;
            //Esmagador Draconídeo (Grito de Guerra: Se seu adversário tiver 15 ou menos pontos de vida, receba +3/+3)
            case "BRM_024":
                brm_024();
                break;
            //Dragão Faminto (Grito de Guerra: Evoque um lacaio aleatório com 1 de custo para o seu oponente)
            case "BRM_026":
                brm_026();
                break;
            //Laceral Mão Negra (Grito de Guerra: Se tiver um Dragão na mão, destrua um lacaio Lendário)
            case "BRM_029":
                brm_029();
                break;
            //Nefarian (Grito de Guerra: Adicione 2 feitiços aleatórios à sua mão (da classe do seu adversário)
            case "BRM_030":
                brm_030();
                break;
            //Técnica Asa Negra (Grito de Guerra: Se tiver um Dragão na mão, receba +1/+1)
            case "BRM_033":
                brm_033();
                break;
            //Desvirtuador Asa Negra (Grito de Guerra: Se tiver um Dragão na mão, cause 3 de dano)
            case "BRM_034":
                brm_034();
                break;
            //Elemental do Fogo (Grito de Guerra: Cause 3 de dano)
            case "CS2_042":
                cs2_042();
                break;
            //Infernal Medonho (Grito de Guerra: Cause 1 de dano a todos os personagens)
            case "CS2_064":
                cs2_064();
                break;
            //Guardião dos Reis (Grito de Guerra: Restaure 6 de Vida do seu herói)
            case "CS2_088":
                cs2_088();
                break;
            //Clarividente Telúrico (Grito de Guerra: Restaure 3 de Vida)
            case "CS2_117":
                cs2_117();
                break;
            //Carabineiro de Altaforja (Grito de Guerra: Cause 1 de dano)
            case "CS2_141":
                cs2_141();
                break;
            //Inventora Gnômica (Grito de Guerra: Compre um card)
            case "CS2_147":
                cs2_147();
                break;
            //Comando Lançatroz (Grito de Guerra: Cause 2 de dano)
            case "CS2_150":
                cs2_150();
                break;
            //Cavaleiro do Punho de Prata (Grito de Guerra: Evoque um escudeiro 2/2)
            case "CS2_151":
                cs2_151();
                break;
            //Mestre de Espadas Ferido (Grito de Guerra: Cause 4 de dano a ELE MESMO)
            case "CS2_181":
                cs2_181();
                break;
            //Sargento Abusivo (Grito de Guerra: Conceda +2 de Ataque a um lacaio neste turno)
            case "CS2_188":
                cs2_188();
                break;
            //Arqueira Élfica (Grito de Guerra: Cause 1 de dano)
            case "CS2_189":
                cs2_189();
                break;
            //Caçadora do Urzal (Grito de Guerra: Evoque um Javali 1/1)
            case "CS2_196":
                cs2_196();
                break;
            //Coruja Bico-de-ferro (Grito de Guerra: Silencie um lacaio)
            case "CS2_203":
                cs2_203();
                break;
            //Senhor da Guerra Lobo do Gelo (Grito de Guerra: Receba +1/+1 para cada outro lacaio aliado no campo de batalha)
            case "CS2_226":
                cs2_226();
                break;
            //Curadora Escamanegra (Grito de Guerra: Restaure 2 de Vida de todos os personagens aliados)
            case "DS1_055":
                ds1_055();
                break;
            //Mestre de Matilha (Grito de Guerra: Conceda +2/+2 e Provocar a uma Fera aliada)
            case "DS1_070":
                ds1_070();
                break;
            //O Cavaleiro Negro (Grito de Guerra: Destrua um lacaio inimigo com Provocar)
            case "EX1_002":
                ex1_002();
                break;
            //Caçador Profissional (Grito de Guerra: Destrua um lacaio com 7 ou mais de Ataque)
            case "EX1_005":
                ex1_005();
                break;
            //Mandingueiro Vodu (Grito de Guerra: Restaure 2 de Vida)
            case "EX1_011":
                ex1_011();
                break;
            //Rei Mukla (Grito de Guerra: Conceda 2 Bananas ao seu oponente)
            case "EX1_014":
                ex1_014();
                break;
            //Engenheira Novata (Grito de Guerra: Compre um card)
            case "EX1_015":
                ex1_015();
                break;
            //Clériga do Sol Partido (Grito de Guerra: Conceda +1/+1 a um lacaio aliado)
            case "EX1_019":
                ex1_019();
                break;
            //Mecânica de Dragonete (Grito de Guerra: Evoque um Dragonete Mecânico 2/1)
            case "EX1_025":
                ex1_025();
                break;
            //Draco do Crepúsculo (Grito de Guerra: Receba +1 de Vida para cada card na sua mão)
            case "EX1_043":
                ex1_043();
                break;
            //Anão Ferro Negro (Grito de Guerra: Conceda +2 de Ataque a um lacaio neste turno)
            case "EX1_046":
                ex1_046();
                break;
            //Quebra-Feitiço (Grito de Guerra: Silencie um lacaio)
            case "EX1_048":
                ex1_048();
                break;
            //Mestre Cervejeito Jovem (Grito de Guerra: Devolva um lacaio aliado do campo de batalha à sua mão)
            case "EX1_049":
                ex1_049();
                break;
            //Oráculo da Luz Fria (Grito de Guerra: Cada jogador compra 2 cards)
            case "EX1_050":
                ex1_050();
                break;
            //Mestre Cervejeiro Ancião (Grito de Guerra: Devolva um lacaio aliado do campo de batalha à sua mão)
            case "EX1_057":
                ex1_057();
                break;
            //Protetora Solfúria (Grito de Guerra: Conceda Provocar aos lacaios adjacentes)
            case "EX1_058":
                ex1_058();
                break;
            //Alquimista Enlouquecido (Grito de Guerra: Troque o Ataque pela Vida de um lacaio)
            case "EX1_059":
                ex1_059();
                break;
            //Gosma Ácida do Pântano (Grito de Guerra: Destrua a arma do seu oponente)
            case "EX1_066":
                ex1_066();
                break;
            //Bombardeiro Louco (Grito de Guerra: Cause 3 de dano divididos aleatoriamente entre todos os outros personagens)
            case "EX1_082":
                ex1_082();
                break;
            //Mestre-faz-tudo Superchispa (Grito de Guerra: Transforme outro lacaio aleatório em um Demossauro 5/5 ou em um esquilo 1/1)
            case "EX1_083":
                ex1_083();
                break;
            //Técnico de Controle Mental (Grito de Guerra: Se seu oponente tem 4 ou mais lacaios, assuma o controle de um aleatoriamente)
            case "EX1_085":
                ex1_085();
                break;
            //Golem Arcano (Investida. Grito de Guerra: Conceda um Cristal de Mana ao seu oponente)
            case "EX1_089":
                ex1_089();
                break;
            //Sacerdotisa do Conluio (Grito de Guerra: Assuma o controle de um lacaio inimigo que tenha 2 ou menos de Ataque)
            case "EX1_091":
                ex1_091();
                break;
            //Defensor de Argus (Grito de Guerra: Conceda +1/+1 e Provocar aos lacaios adjacentes)
            case "EX1_093":
                ex1_093();
                break;
            //Vidente da Luz Fria (Grito de Guerra: Conceda +2 de Vida a TODOS os outros Murlocs)
            case "EX1_103":
                ex1_103();
                break;
            //Gelbin Mekkatorque (Grito de Guerra: Evoque uma invenção FABULOSA)
            case "EX1_112":
                ex1_112();
                break;
            //Leeroy Jenkins (Investida. Grito de Guerra: Evoque dois Dragonetes 1/1 para o seu oponente)
            case "EX1_116":
                ex1_116();
                break;
            //Lâmina da Perdição (Grito de Guerra: Cause 1 de dano. Combo: em vez de 1, cause 2 de dano)
            case "EX1_133":
                ex1_133();
                break;
            //Elemental de Gelo (Grito de Guerra: Congele um personagem)
            case "EX1_283":
                ex1_283();
                break;
            //Draco Lazúli (+1 de Dano Mágico. Grito de Guerra: Compre um card)
            case "EX1_284":
                ex1_284();
                break;
            //Guarda Vil (Provocar. Grito de Guerra: Destrua um de seus Cristais de Mana)
            case "EX1_301":
                ex1_301();
                break;
            //Terror do Caos (Grito de Guerra: Destrua os lacaios dos dois lados deste e receba o Ataque e a Vida deles)
            case "EX1_304":
                ex1_304();
                break;
            //Súcubo (Grito de Guerra: Descarte um card aleatório)
            case "EX1_306":
                ex1_306();
                break;
            //Demonarca (Investida. Grito de Guerra: Descarte dois cards aleatórios)
            case "EX1_310":
                ex1_310();
                break;
            //Lorde Abissal (Grito de Guera: Cause 5 de dano ao seu herói)
            case "EX1_313":
                ex1_313();
                break;
            //Diabrete das Chamas (Grito de Guerra: Cause 3 de dano ao seu herói)
            case "EX1_319":
                ex1_319();
                break;
            //Protetor Argênteo (Grito de Guerra: Conceda Escudo Divino a um lacaio aliado)
            case "EX1_362":
                ex1_362();
                break;
            //Pacificador Aldor (Grito de Guerra: Mude o Ataque de um lacaio inimigo para 1)
            case "EX1_382":
                ex1_382();
                break;
            //Armeira de Arathi (Grito de Guerra: Equipe uma arma 2/2)
            case "EX1_398":
                ex1_398();
                break;
            //Murloc Caçamaré (Grito de Guerra: Evoque um Batedor Murloc 1/1)
            case "EX1_506":
                ex1_506();
                break;
            //Harrison Jones (Grito de Guerra: Destrua a arma de seu oponente e compre o mesmo número de cards que a durabilidade da arma)
            case "EX1_558":
                ex1_558();
                break;
            //Alexstrasza (Grito de Guerra: Defina a vida remanescente do herói para 15)
            case "EX1_561":
                ex1_561();
                break;
            //Onyxia (Grito de Guerra: Evoque Dragonetes 1/1 até seu lado do campo de batalha estar cheio)
            case "EX1_562":
                ex1_562();
                break;
            //Manipulador Sem-rosto (Grito de Guerra: Escolha um lacaio e torne-se uma cópia dele)
            case "EX1_564":
                ex1_564();
                break;
            //Sacerdotisa de Eluna (Grito de Guerra: Restaure 4 de Vida do seu herói)
            case "EX1_583":
                ex1_583();
                break;
            //Mago Ancião (Grito de Guerra: Conceda +1 de Dano Mágico aos lacaios adjacentes)
            case "EX1_584":
                ex1_584();
                break;
            //Voz dos Ventos (Grito de Guerra: Conceda Fúria dos Ventos a um lacaio aliado)
            case "EX1_587":
                ex1_587();
                break;
            //Cavaleiro Sangrento (Grito de Guerra: Todos os lacaios perdem Escudo Divino. Receba +3/+3 para cada Escudo perdido)
            case "EX1_590":
                ex1_590();
                break;
            //Lâmina Soturna (Grito de Guerra: Cause 3 de dano ao herói inimigo)
            case "EX1_593":
                ex1_593();
                break;
            //Capataz Cruel (Grito de Guerra: Cause 1 de dano a um lacaio e conceda-lhe +2 de Ataque)
            case "EX1_603":
                ex1_603();
                break;
            //Maga do Kirin Tor (Grito de Guerra: O próximo Segredo que você lançar neste turno custará 0)
            case "EX1_612":
                ex1_612();
                break;
            //Impositor do Templo (Grito de Guerra: Conceda +3 de Vida a um lacaio aliado)
            case "EX1_623":
                ex1_623();
                break;
            //Gosma Ecoante (Grito de Guerra: No final do turno, evoque uma cópia exata deste lacaio)
            case "FP1_003":
                fp1_003();
                break;
            //Alma Ululante (Grito de Guerra: Silencie seus outros lacaios)
            case "FP1_016":
                fp1_016();
                break;
            //Repugnaz (Grito de Guerra: Feitiços inimigos custam 5 a mais no próximo turno)
            case "FP1_030":
                fp1_030();
                break;
            //Mago Explosivo Goblin (Grito de Guerra: Se você tiver um Mecanoide, cause 4 de dano dividido aleatoriamente entre todos os inimigos)
            case "GVG_004":
                gvg_004();
                break;
            //Bombardeiro das Sombras (Grito de Guerra: Cause 3 de dano a cada herói)
            case "GVG_009":
                gvg_009();
                break;
            //Encolhista (Grito de Guerra: Conceda -2 de Ataque a um lacaio neste turno)                    
            case "GVG_011":
                gvg_011();
                break;
            //Vol'jin (Grito de Guerra: Troque a Vida deste lacaio pela de outro)
            case "GVG_014":
                gvg_014();
                break;
            //Autobarbeiro Goblínico (Grito de Guerra: Conceda +1 de Ataque à sua arma)
            case "GVG_023":
                gvg_023();
                break;
            //Neptulon (Grito de Guerra: Adicione 4 Murlocs aleatórios à sua mão. Sobrecarga: 3)
            case "GVG_042":
                gvg_042();
                break;
            //Glaivezuca (Grito de Guerra: Conceda +1 de Ataque a um lacaio aliado aleatório)
            case "GVG_043":
                gvg_043();
                break;
            //Rei das Feras (Provocar. Grito de Guerra: Ganhe +1 de Ataque para cada outra Fera que você tiver)
            case "GVG_046":
                gvg_046();
                break;
            //Saltador Metalodonte (Grito de Guerra: Conceda +2 de Ataque aos seus outros Mecanoides)
            case "GVG_048":
                gvg_048();
                break;
            //Dama Escudeira (Grito de Guerra: Receba 5 de Armadura)
            case "GVG_053":
                gvg_053();
                break;
            //Calhambeque Vai-no-tranco (Grito de Guerra: Conceda +2/+2 a um Mecanoide aliado)
            case "GVG_055":
                gvg_055();
                break;
            //Jaganata de Ferro (Grito de Guerra: Coloque uma Mina no deck do seu oponente. Ao ser comprada, ela explode causando 10 de dano)
            case "GVG_056":
                gvg_056();
                break;
            //Engrenomalho (Grito de Guerra: Conceda Escudo Divino e Provocar a um lacaio aliado aleatório)
            case "GVG_059":
                gvg_059();
                break;
            //Intendente (Grito de Guerra: Conceda +2/+2 aos seus Recrutas do Punho de Prata)
            case "GVG_060":
                gvg_060();
                break;
            //Robô de Cura Vintage (Grito de Guerra: Recupere 8 de Vida do seu herói)
            case "GVG_069":
                gvg_069();
                break;
            //Mística de Kezan (Grito de Guerra: Assuma o controle de um Segredo aleatório do inimigo)
            case "GVG_074":
                gvg_074();
                break;
            //Druida da Presa (Grito de Guerra: Se você tiver uma Fera, este lacaio torna-se 7/7)
            case "GVG_080":
                gvg_080();
                break;
            //Robô de Reparos Aprimorado (Grito de Guerra: Conceda +4 de Vida a um Mecanoide aliado)
            case "GVG_083":
                gvg_083();
                break;
            //Bombardeiro Muito Louco (Grito de Guerra: Cause 6 de dano divididos aleatoriamente entre todos os outros personagens)
            case "GVG_090":
                gvg_090();
                break;
            //Experimentador Gnômico (Grito de Guerra: Compre um card. Se for um lacaio, transforme-o em uma Galinha)
            case "GVG_092":
                gvg_092();
                break;
            //Exorcistinha (Provocar. Grito de Guerra: Receba +1/+1 para cada lacaio inimigo com Último Suspiro)
            case "GVG_097":
                gvg_097();
                break;
            //Taca-bomba (Grito de Guerra: Cause 4 de dano a um lacaio inimigo aleatório)
            case "GVG_099":
                gvg_099();
                break;
            //Purificador Escarlate (Grito de Guerra: Cause 2 de dano a todos os lacaios com Grito de Guerra)
            case "GVG_101":
                gvg_101();
                break;
            //Técnico da Vila da Gambiarra (Grito de Guerra: Se você tiver um Mecanoide, receba +1/+1 e adicione uma Peça Sobressalente à sua mão)
            case "GVG_102":
                gvg_102();
                break;
            //Mecanoide Aprimorador (Grito de Guerra: Conceda Fúria dos Ventos, Provocar ou Escudo Divino aos seus outros lacaios (aleatoriamente))
            case "GVG_107":
                gvg_107();
                break;
            //Recombobulador (Grito de Guerra: Transforme um lacaio aliado em um lacaio aleatório com o mesmo Custo)
            case "GVG_108":
                gvg_108();
                break;
            //Dr. Cabum (Grito de Guerra: Evoque duas 1/1 Robombas. ATENÇÃO: Os Robôs podem explodir)
            case "GVG_110":
                gvg_110();
                break;
            //Tocha (Grito de Guerra e Último Suspiro: Adicione um card Peça Sobressalente à sua mão)
            case "GVG_115":
                gvg_115();
                break;
            //Blingtron 3000 (Grito de Guerra: Equipe uma arma aleatória para cada jogador)
            case "GVG_119":
                gvg_119();
                break;
            //Rosarães Guima (Grito de Guerra: Destrua uma Fera)
            case "GVG_120":
                gvg_120();
                break;
            //Conjurador Etéreo (Grito de Guerra: Descobrir um feitiço)
            case "LOE_003":
                loe_003();
                break;
            //Curador de Museu (Grito de Guerra: Descobrir um card com Último Suspiro)
            case "LOE_006":
                loe_006();
                break;
            //Reno Jackson (Grito de Guerra: Se não tiver nenhum card repetido no seu deck, cure a vida toda do seu herói)
            case "LOE_011":
                loe_011();
                break;
            //Guardiã de Uldaman (Grito de Guerra: Mude o Ataque e a Vida de um lacaio para 3)
            case "LOE_017":
                loe_017();
                break;
            //Camelo do Deserto (Grito de Guerra: Coloque um lacaio com 1 de custo de cada deck no campo de batalha)
            case "LOE_020":
                loe_020();
                break;
            //Ambulante das Trevas (Grito de Guerra: Descobrir um card com 1 de custo)
            case "LOE_023":
                loe_023();
                break;
            //Escaravelho Adornado (Grito de Guerra: Descobrir um card com 3 de custo)
            case "LOE_029":
                loe_029();
                break;
            //Gorila-robô A-3 (Grito de Guerra: Se você controlar outro Mecanoide, Descubra um Mecanoide)
            case "LOE_039":
                loe_039();
                break;
            //Aranha da Tumba (Grito de Guerra: Descubra uma Fera)
            case "LOE_047":
                loe_047();
                break;
            //Demossauro Fossilizado (Grito de Guerra: Se você controlar uma Fera, receba Provocar)
            case "LOE_073":
                loe_073();
                break;
            //Dom Finlei Mrrggltone (Grito de Guerra: Descubra um Poder Heroico novo)
            case "LOE_076":
                loe_076();
                break;
            //Elise Mirestela (Grito de Guerra: Embaralhe o "Mapa do Macaco Dourado" no seu deck)
            case "LOE_079":
                loe_079();
                break;
            //Macaco Dourado (Provocar. Grito de Guerra: Substitua sua mão e seu deck por lacaios Lendários)
            case "LOE_019t2":
                loe_019t2();
                break;
            //Arquiladrão Rafaam (Grito de Guerra: Descubra um Artefato poderoso)
            case "LOE_092":
                loe_092();
                break;
            //Vulto Antigo (Grito de Guerra: Embaralhe no seu deck uma "Maldição Antiga" que lhe causa 7 de dano ao ser comprada)
            case "LOE_110":
                loe_110();
                break;
            //Caçadora do Relicário (Grito de Guerra: Se você possuir outros 6 lacaios, receba +4/+4)
            case "LOE_116":
                loe_116();
                break;
            //Mestre dos Disfarces (Grito de Guerra: Conceda Furtividade a um lacaio aliado)
            case "NEW1_014":
                new1_014();
                break;
            //Papagaio do Capitão (Grito de Guerra: Coloque um Pirata aleatório do seu deck na sua mão)
            case "NEW1_016":
                new1_016();
                break;
            //Caranguejo Faminto (Grito de Guerra: Destrua um murloc e receba +2/+2)
            case "NEW1_017":
                new1_017();
                break;
            //Saqueadora da Vela Sangrenta (Grito de Guerra: Ela recebe Ataque equivalente ao Ataque da sua arma)
            case "NEW1_018":
                new1_018();
                break;
            //Capitão Peleverde (Grito de Guerra: Conceda +1/+1 à sua arma)
            case "NEW1_024":
                new1_024();
                break;
            //Corsário da Vela Sangrenta (Grito de Guerra: Remova 1 de Durabilidade da arma do seu oponente)
            case "NEW1_025":
                new1_025();
                break;
            //Asa da Morte (Grito de Guerra: Destrua todos os outros lacaios e descarte a sua mão)
            case "NEW1_030":
                new1_030();
                break;
            //Kodo em Disparada (Grito de Guerra: Destrua um lacaio inimigo aleatório com 2 ou menos de Ataque)
            case "NEW1_041":
                new1_041();
                break;
            //Chefe Tauren Elite (Grito de Guerra: Dê aos dois jogadores o poder do ROCK! (com um card de Acorde Poderoso)
            case "PRO_001":
                pro_001();
                break;
        }
    }

    /**
     * Verifica se existe algum alvo válido na lista (que não esteja furtivo nem
     * seja o card que gerou o evento
     *
     * @param list lista de lacaios
     * @return {@code true} ou {@code false}
     */
    private static boolean contemAlvosValidos(List<Card>... list) {
        for (List<Card> l : list) {
            if (l.stream().filter((lacaio)
                    -> (!lacaio.isFurtivo() && !lacaio.equals(card))).count() > 0L) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gera o grito de guerra padrão para causar dano em um personagem
     *
     * @param dano valor do dano
     */
    private static void causarDano(int dano) throws JogouCardException {
        escolhido = EscolherMesa.main(escolhido, card, "Cause " + dano + " de dano", partida, false);
        if (escolhido != EscolherMesa.CANCEL) {
            Utils.dano(partida, escolhido, dano);
        } else {
            throw new JogouCardException("Grito de guerra cancelado.");
        }
    }

    /**
     * Gera o grito de guerra padrão para restaurar vida de um personagem
     *
     * @param vida vida restaurada
     */
    private static void restaurarVida(int vida) throws JogouCardException {
        escolhido = EscolherMesa.main(escolhido, card, "Restaure " + vida + " de vida", partida, false);
        if (escolhido != EscolherMesa.CANCEL) {
            Utils.cura(partida, escolhido, vida);
        } else {
            throw new JogouCardException("Grito de guerra cancelado.");
        }
    }

    /**
     * Gera o grito de guerra padrão para silenciar um lacaio
     */
    private static void silencieUmLacaio() throws JogouCardException {
        if (contemAlvosValidos(partida.getMesa())) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(), card, "Silencie um lacaio",
                    null, null, card.getDono().getMesa(), card.getOponente().getMesa(), false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.findCardByIDLong(escolhido).setSilenciado(true);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Millhouse Manavento (Grito de Guerra: Feitiços inimigos custam (0) no
     * próximo turno)
     */
    private static void new1_029() {
        partida.getAuraEstatica().feiticosMaoCustamXDaquiAXTurnos(0, Param.EXATO, Param.OPONENTE, 1);
    }

    /**
     * Lança-feitiços (Grito de Guerra: Adicione um feitiço aleatório na mão de
     * cada jogador.
     */
    private static void at_007() {
        for (Heroi h : new Heroi[]{partida.getHero(), partida.getOponente()}) {
            h.addMao(partida.criarCard(Random.byType(FEITICO).getId(), System.nanoTime()));
        }
    }

    /**
     * Tratador de Carneiros (Grito de Guerra: Se você tiver uma Fera, evoque
     * uma Fera aleatória.
     */
    private static void at_010() {
        if (card.getDono().temFeraNaMesa(card)) {
            card.getDono().evocar(card.getDono().getPosicaoNaMesa(card.id_long) + 1,
                    partida.criarCard(Random.byRace(FERA).getId(), System.nanoTime()));
        }
    }

    /**
     * Guardião do Crepúsculo (Grito de Guerra: Se você tiver um Dragão na mão,
     * receba +1 de Ataque e Provocar)
     */
    private static void at_017() {
        if (partida.getHero().getMao().stream()
                .filter((mao) -> (mao.isDragao())).count() > 0) {
            card.addAtaque(1);
            card.addMechanics(PROVOCAR);
        }
    }

    /**
     * Negociante Escuso (Grito de Guerra: Se você tiver um Pirata, receba
     * +1/+1)
     */
    private static void at_032() {
        if (card.getDono().temPirataNaMesa(card)) {
            card.addAtaque(1);
            card.addVidaMaxima(1);
        }
    }

    /**
     * Aspirante de Darnassus (Grito de Guerra: Receba um Cristal de Mana vazio.
     * Último Suspiro: Perca um Cristal de Mana)
     */
    private static void at_038() {
        card.getDono().addMana(1);
        card.getDono().addManaUtilizada(1);
    }

    /**
     * Andarilha da Selva (Grito de Guerra: Conceda +3 de vida a uma Fera
     * aliada)
     */
    private static void at_040() throws JogouCardException {
        List<Card> feras = Filtro.raca(card.getDono().getMesa(), FERA);
        if (contemAlvosValidos(feras)) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Conceda +3 de vida a uma Fera aliada", null, null, feras, null, false);
            if (escolhido != -1) {
                partida.findCardByIDLong(escolhido).addAtaque(3);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Morsano Totêmico (Grito de Guerra: Evoca QUALQUER totem aleatório)
     */
    private static void at_046() {
        card.getDono().evocar(card.getDono().getPosicaoNaMesa(card.id_long) + 1,
                partida.criarCard(Random.byRace(TOTEM).getId(), System.nanoTime()));
    }

    /**
     * Entalhadora de Totens (Grito de Guerra: Receba +1/+1 para cada Totem
     * aliado)
     */
    private static void at_047() {
        int quantidade = (int) card.getDono().getMesa().stream()
                .filter((aliado) -> (aliado.isTotem())).count();
        if (quantidade > 0) {
            card.addVidaMaxima(quantidade);
            card.addAtaque(quantidade);
        }
    }

    /**
     * O Chamabruma (Grito de Guerra: conceda +1/+1 a todos os lacaios na sua
     * mão e no seu deck)
     */
    private static void at_054() {
        List<Card> lacaios = new ArrayList<>(card.getDono().getMao());
        lacaios.addAll(card.getDono().getDeck());
        lacaios.stream().filter((lacaio) -> (lacaio.isLacaio()))
                .forEach((lacaio) -> {
                    lacaio.addVidaMaxima(1);
                    lacaio.addAtaque(1);
                });
    }

    /**
     * Mestre de Estábulo (Grito de Guerra: Conceda Imunidade a uma Fera aliada
     * neste turno)
     */
    private static void at_057() throws JogouCardException {
        List<Card> feras = Filtro.raca(card.getDono().getMesa(), FERA);
        if (contemAlvosValidos(feras)) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Conceda Imunidade a uma Fera aliada", null, null, feras, null, false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.getEfeitoProgramado().concederImunidadeAUmLacaioUmTurno(escolhido);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Elekk do Rei (Grito de Guerra: Revele um lacaio em cada deck. Se o seu
     * custar mais, compre-o)
     */
    private static void at_058() {
        if (Justas.processar(card.getDono(), card.getOponente()) == Param.HEROI) {
            card.getDono().comprarCarta(card.getDono().getDeck().indexOf(Justas.justaHeroi), Card.COMPRA_EVENTO);
        }
    }

    /**
     * Defensora do Rei (Grito de Guerra: Se você tiver um lacaio com Provocar,
     * receba +1 de Durabilidade)
     */
    private static void at_065() {
        for (Card c : card.getDono().getMesa()) {
            if (!c.equals(card) && c.isProvocar()) {
                card.addDurabilidade(1);
                break;
            }
        }
    }

    /**
     * Parceiro de Pugilato (Provocar Grito de Guerra: Conceda Provocar a um
     * lacaio)
     */
    private static void at_069() throws JogouCardException {
        if (contemAlvosValidos(partida.getMesa())) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Conceda Provocar a um lacaio",
                    null, null, card.getDono().getMesa(), card.getOponente().getMesa(), false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.findCardByIDLong(escolhido).addMechanics(PROVOCAR);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Campeã de Alexstrasza (Grito de Guerra: Se você tiver um Dragão na mão,
     * receba +1 de Ataque e Investida)
     */
    private static void at_071() {
        for (Card mao : card.getDono().getMao()) {
            if (mao.isDragao() && !mao.equals(card)) {
                card.addAtaque(1);
                card.addMechanics(INVESTIDA);
                break;
            }
        }
    }

    /**
     * Varian Wrynn (Grito de Guerra: Compre 3 cards. Coloque os lacaios que
     * você comprou diretamente no campo de batalha)
     */
    private static void at_072() {
        List<Card> comprados = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            comprados.add(card.getDono().comprarCarta(Card.COMPRA_EVENTO));
        }
        for (int i = 0; i < comprados.size(); i++) {
            if (comprados.get(i) != null && comprados.get(i).isLacaio()) {
                card.getDono().delMao(comprados.get(i));
                card.getDono().evocar(card.getDono().getPosicaoNaMesa(card.id_long) + 1, comprados.get(i));
            }
        }
    }

    /**
     * Lança Argêntea (Grito de Guerra: Revele um lacaio em cada deck. Se o seu
     * custar mais, +1 de Durabilidade)
     */
    private static void at_077() {
        if (Justas.processar(card.getDono(), card.getOponente()) == Param.HEROI) {
            card.addDurabilidade(1);
        }
    }

    /**
     * Desafiante Misterioso (Grito de Guerra: Coloque um Segredo de cada do seu
     * deck no campo de batalha)
     */
    private static void at_079() {
        List<Card> segredos = Utils.getSegredosSemRepetir(card.getDono().getDeck());
        segredos.stream().filter((segredo) -> (!card.getDono().temSegredoAtivado(segredo.getId())))
                .forEach((segredo) -> {
                    card.getDono().delDeck(segredo);
                    card.getDono().addSegredo(segredo);
                });
    }

    /**
     * Eadric, o Puro (Grito de Guerra: Mude o Ataque de todos os lacaios
     * inimigos para 1)
     */
    private static void at_081() {
        card.getOponente().getMesa().forEach(lacaio -> lacaio.setAtaque(1, true));
    }

    /**
     * Carregador de Lança (Grito de Guerra: Conceda +2 de Ataque a um lacaio
     * aliado)
     */
    private static void at_084() throws JogouCardException {
        if (contemAlvosValidos(partida.getHero().getMesa())) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Conceda +2 de Ataque", null, null, card.getDono().getMesa(), null, false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.findCardByIDLong(escolhido).addAtaque(2);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Sabotador (Grito de Guerra: O poder Heroico do seu oponente custa (5) a
     * mais no próximo turno)
     */
    private static void at_086() {
        partida.getAuraEstatica().poderHeroicoCustaXDaquiAXTurnos(5, Param.ADD, Param.OPONENTE, 1);
    }

    /**
     * Malabarista de Chamas (Grito de Guerra: Cause 1 de dano a um inimigo
     * aleatório)
     */
    private static void at_094() {
        Utils.dano(partida, Utils.getAleatorioOponente(partida), 1);
    }

    /**
     * Cavaleiro de Corda (Grito de Guerra: Conceda +1/+1 a um Mecanoide aliado)
     */
    private static void at_096() throws JogouCardException {
        List<Card> mecanoides = Filtro.raca(card.getDono().getMesa(), MECANOIDE);
        if (contemAlvosValidos(mecanoides)) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Conceda +1/+1 a um Mecanoide", null, null, mecanoides, null, false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.findCardByIDLong(escolhido).addVidaMaxima(1);
                partida.findCardByIDLong(escolhido).addAtaque(1);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Engolidora de Feitiços (Grito de Guerra: Copie o Poder Heroico do seu
     * oponente)
     */
    private static void at_098() {
        card.getDono().setPoderHeroico(card.getOponente().getPoderHeroico().getTipo());
    }

    /**
     * Kraken do Mar do Norte (Grito de Guerra: Cause 4 de dano)
     */
    private static void at_103() throws JogouCardException {
        causarDano(4);
    }

    /**
     * Morsano Justador (Grito de Guerra: Revele um lacaio em cada deck. Se o
     * seu custar mais, restaure 7 de Vida do seu herói)
     */
    private static void at_104() {
        if (Justas.processar(card.getDono(), card.getOponente()) == Param.HEROI) {
            card.getDono().addHealth(7);
        }
    }

    /**
     * Kvaldir Ferido (Grito de Guerra: Cause 3 de dano a este lacaio)
     */
    private static void at_105() {
        card.delVida(3);
    }

    /**
     * Campeão da Luz (Grito de Guerra: Silencie um Demônio)
     */
    private static void at_106() throws JogouCardException {
        List<Card> lacaiosHeroi = Filtro.raca(card.getDono().getMesa(), DEMONIO);
        List<Card> lacaiosOponente = Filtro.raca(card.getOponente().getMesa(), DEMONIO);
        if (contemAlvosValidos(lacaiosHeroi, lacaiosOponente)) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Silencie um Demônio", null, null, lacaiosHeroi, lacaiosOponente, false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.findCardByIDLong(escolhido).setSilenciado(true);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Cavalo de Guerra (Grito de Guerra: Revele um lacaio em cada deck. Se o
     * seu custar mais, ganhe Investida)
     */
    private static void at_108() {
        if (Justas.processar(card.getDono(), card.getOponente()) == Param.HEROI) {
            card.addMechanics(INVESTIDA);
        }
    }

    /**
     * Vendedor de Salgadinho (Grito de Guerra: Restaure 4 de vida de todos os
     * heróis)
     */
    private static void at_111() {
        card.getDono().addHealth(4);
        card.getOponente().addHealth(4);
    }

    /**
     * Mestre Justador (Grito de Guerra: Revele um lacaio em cada deck. Se o seu
     * custar mais, receba Provocar e Escudo Divino)
     */
    private static void at_112() {
        if (Justas.processar(card.getDono(), card.getOponente()) == Param.HEROI) {
            card.addMechanics(PROVOCAR);
            card.addMechanics(ESCUDO_DIVINO);
        }
    }

    /**
     * Treinador de Esgrima (Grito de Guerra: Na próxima vez que você usar seu
     * Poder Heroico, ele custará 2 a menos)
     */
    private static void at_115() {
        partida.getAlterarCusto().add(partida.getPlayer(), PODER_HEROICO, null, -2);
    }

    /**
     * Agente do Repouso das Serpes (Grito de Guerra: Se você tiver um Dragão na
     * mão, receba +1 de Ataque e Provocar)
     */
    private static void at_116() {
        if (card.getDono().getMao().stream()
                .filter((mao) -> (mao.isDragao())).count() > 0L) {
            card.addAtaque(1);
            card.addMechanics(PROVOCAR);
        }
    }

    /**
     * Mestre de Cerimônias (Grito de Guerra: Se você tiver um lacaio com Dano
     * Mágico, ganhe +2/+2)
     */
    private static void at_117() {
        if (card.getDono().getMao().stream()
                .filter((lac) -> (lac.getDanoMagico() > 0 && !lac.equals(card))).count() > 0L) {
            card.addVidaMaxima(2);
            card.addAtaque(2);
        }
    }

    /**
     * Grã-Cruzada (Grito de Guerra: Adicione um card de Paladino aleatório à
     * sua mão)
     */
    private static void at_118() {
        card.getDono().addMao(partida.criarCard(Random.byPlayerClass(LADINO).getId(), System.nanoTime()));
    }

    /**
     * Gormok, o Empalador (Grito de Guerra: se você possuir pelo menos outros 4
     * lacaios, cause 4 de dano)
     */
    private static void at_122() throws JogouCardException {
        if (card.getDono().getMesa().size() > 4) {
            causarDano(4);
        }
    }

    /**
     * Justicar Veras (Grito de Guerra: Substitui seu Poder Heroico inicial por
     * um melhor)
     */
    private static void at_132() {
        card.getDono().melhorarPoderHeroico();
    }

    /**
     * Justadora de Gerintontzan (Grito de Guerra: Revele um lacaio em cada
     * deck. Se o seu custar mais, ganhe +1/+1)
     */
    private static void at_133() {
        if (Justas.processar(card.getDono(), card.getOponente()) == Param.HEROI) {
            card.addVidaMaxima(1);
            card.addAtaque(1);
        }
    }

    /**
     * Dragonete Crepuscular (Grito de Guerra: Se você tiver um Dragão na mão,
     * receba +2 pontos de vida)
     */
    private static void brm_004() {
        if (card.getDono().getMao().stream()
                .filter((mao) -> (mao.isDragao())).count() > 0L) {
            card.addVidaMaxima(2);
        }
    }

    /**
     * Ferro Negro BIsbilhoteiro (Grito de Guerra: Cause 2 de dano a todos os
     * lacaios inimigos ilesos)
     */
    private static void brm_008() {
        List<Card> ilesos = new ArrayList<>();
        card.getOponente().getMesa().stream()
                .filter((lacaio) -> (lacaio.isIleso()))
                .forEach((lacaio) -> {
                    ilesos.add(lacaio);
                });
        EmArea.dano(partida, ilesos, 2, 0);
    }

    /**
     * Flamiguarda Destruidor (Grito de Guerra: Receba 1-4 de Ataque.
     * Sobrecarga: 1)
     */
    private static void brm_012() {
        card.addAtaque(Utils.random(4));
    }

    /**
     * Furiante do Núcleo (Grito de Guerra: Se sua mão estiver vazia, receba
     * +3/+3)
     */
    private static void brm_014() {
        if (card.getDono().getMao().isEmpty()) {
            card.addVidaMaxima(3);
            card.addAtaque(3);
        }
    }

    /**
     * Consorte Dragônica (Grito de Guerra: O próximo Dragão que você jogar
     * custa 2 a menos)
     */
    private static void brm_018() {
        partida.getAlterarCusto().add(partida.getPlayer(), GERAL, DRAGAO, -2);
    }

    /**
     * Esmagador Draconídeo (Grito de Guerra: Se seu adversário tiver 15 ou
     * menos pontos de vida, receba +3/+3)
     */
    private static void brm_024() {
        if (card.getOponente().getHealth() <= 15) {
            card.addVidaMaxima(3);
            card.addAtaque(3);
        }
    }

    /**
     * Dragão Faminto (Grito de Guerra: Evoque um lacaio aleatório com 1 de
     * custo para o seu oponente)
     */
    private static void brm_026() {
        card.getOponente().evocar(partida.criarCard(Random.getCard(LACAIO, GERAL,
                1, GERAL, GERAL, GERAL).getId(),
                System.nanoTime()));
    }

    /**
     * Laceral Mão Negra (Grito de Guerra: Se tiver um Dragão na mão, destrua um
     * lacaio Lendário)
     */
    private static void brm_029() throws JogouCardException {
        if (card.getDono().getMao().stream().filter((mao) -> (mao.isDragao())).count() > 0) {
            List<Card> cardsHeroi = Utils.getLendarios(card.getDono().getMesa());
            List<Card> cardsOponente = Utils.getLendarios(card.getOponente().getMesa());
            if (contemAlvosValidos(cardsHeroi, cardsOponente)) {
                escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                        card, "Destrua um lacaio Lendário", null, null, cardsHeroi, cardsOponente, false);
                if (escolhido != EscolherMesa.CANCEL) {
                    partida.findCardByIDLong(escolhido).morreu();
                } else {
                    throw new JogouCardException("Grito de guerra cancelado.");
                }
            }
        }
    }

    /**
     * Nefarian (Grito de Guerra: Adicione 2 feitiços aleatórios à sua mão (da
     * classe do seu adversário)
     */
    private static void brm_030() {
        for (int i = 0; i < 2; i++) {
            card.getDono().addMao(partida.criarCard(Random.getCard(FEITICO,
                    card.getOponente().getType(), Random.QUALQUER_CUSTO,
                    GERAL, GERAL, GERAL).getId(), System.nanoTime()));
        }
    }

    /**
     * Técnica Asa Negra (Grito de Guerra: Se tiver um Dragão na mão, receba
     * +1/+1)
     */
    private static void brm_033() {
        if (card.getDono().getMao().stream().filter((mao) -> (mao.isDragao())).count() > 0) {
            card.addAtaque(1);
            card.addVidaMaxima(1);
        }
    }

    /**
     * Desvirtuador Asa Negra (Grito de Guerra: Se tiver um Dragão na mão, cause
     * 3 de dano)
     */
    private static void brm_034() throws JogouCardException {
        if (card.getDono().getMao().stream().filter((mao) -> (mao.isDragao())).count() > 0) {
            causarDano(3);
        }
    }

    /**
     * Elemental do Fogo (Grito de Guerra: Cause 3 de dano)
     */
    private static void cs2_042() throws JogouCardException {
        causarDano(3);
    }

    /**
     * Infernal Medonho (Grito de Guerra: Cause 1 de dano a todos os
     * personagens)
     */
    private static void cs2_064() {
        card.getOponente().delHealth(1);
        card.getDono().delHealth(1);
        EmArea.dano(partida, partida.getMesa(), 1, 0);
    }

    /**
     * Guardião dos Reis (Grito de Guerra: Restaure 6 de Vida do seu herói)
     */
    private static void cs2_088() {
        card.getDono().addHealth(6);
    }

    /**
     * Clarividente Telúrico (Grito de Guerra: Restaure 3 de Vida)
     */
    private static void cs2_117() throws JogouCardException {
        restaurarVida(3);
    }

    /**
     * Carabineiro de Altaforja (Grito de Guerra: Cause 1 de dano)
     */
    private static void cs2_141() throws JogouCardException {
        causarDano(1);
    }

    /**
     * Inventora Gnômica (Grito de Guerra: Compre um card)
     */
    private static void cs2_147() {
        card.getDono().comprarCarta(Card.COMPRA_EVENTO);
    }

    /**
     * Comando Lançatroz (Grito de Guerra: Cause 2 de dano)
     */
    private static void cs2_150() throws JogouCardException {
        causarDano(2);
    }

    /**
     * Cavaleiro do Punho de Prata (Grito de Guerra: Evoque um escudeiro 2/2)
     */
    private static void cs2_151() {
        card.getDono().evocar(card.getDono().getPosicaoNaMesa(card.id_long) + 1,
                partida.criarCard(ESCUDEIRO2_2, System.nanoTime()));
    }

    /**
     * Mestre de Espadas Ferido (Grito de Guerra: Cause 4 de dano a ELE MESMO)
     */
    private static void cs2_181() {
        card.delVida(4);
    }

    /**
     * Sargento Abusivo (Grito de Guerra: Conceda +2 de Ataque a um lacaio neste
     * turno)
     */
    private static void cs2_188() throws JogouCardException {
        if (contemAlvosValidos(partida.getMesa())) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Conceda +2 de Ataque a um lacaio neste turno",
                    null, null, card.getDono().getMesa(), card.getOponente().getMesa(), false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.getEfeitoProgramado().concederAtaqueAUmLacaioUmTurno(escolhido, 2);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Arqueira Élfica (Grito de Guerra: Cause 1 de dano)
     */
    private static void cs2_189() throws JogouCardException {
        causarDano(1);
    }

    /**
     * Caçadora do Urzal (Grito de Guerra: Evoque um Javali 1/1)
     */
    private static void cs2_196() {
        card.getDono().evocar(card.getDono().getPosicaoNaMesa(card.id_long) + 1,
                partida.criarCard(JAVALI1_1, System.nanoTime()));
    }

    /**
     * Coruja Bico-de-ferro (Grito de Guerra: Silencie um lacaio)
     */
    private static void cs2_203() throws JogouCardException {
        silencieUmLacaio();
    }

    /**
     * Senhor da Guerra Lobo do Gelo (Grito de Guerra: Receba +1/+1 para cada
     * outro lacaio aliado no campo de batalha)
     */
    private static void cs2_226() {
        int qtd = card.getDono().getMesa().size();
        if (qtd > 1) {
            card.addVidaMaxima(qtd - 1);
            card.addAtaque(qtd - 1);
        }
    }

    /**
     * Curadora Escamanegra (Grito de Guerra: Restaure 2 de Vida de todos os
     * personagens aliados)
     */
    private static void ds1_055() {
        card.getDono().addHealth(2);
        EmArea.cura(partida, card.getDono().getMesa(), 2);
    }

    /**
     * Mestre de Matilha (Grito de Guerra: Conceda +2/+2 e Provocar a uma Fera
     * aliada)
     */
    private static void ds1_070() throws JogouCardException {
        List<Card> feras = Filtro.raca(card.getDono().getMesa(), FERA);
        if (contemAlvosValidos(feras)) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Conceda +2/+2 e Provocar a uma Fera aliada",
                    null, null, feras, null, false);
            if (escolhido != EscolherMesa.CANCEL) {
                Card c = partida.findCardByIDLong(escolhido);
                c.addVidaMaxima(2);
                c.addAtaque(2);
                c.addMechanics(PROVOCAR);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * O Cavaleiro Negro (Grito de Guerra: Destrua um lacaio inimigo com
     * Provocar)
     */
    private static void ex1_002() throws JogouCardException {
        List<Card> lacaios = new ArrayList<>();
        card.getOponente().getMesa().stream()
                .filter((lacaio) -> (lacaio.isProvocar() && !lacaio.isFurtivo() && !lacaio.equals(card)))
                .forEach((lacaio) -> {
                    lacaios.add(lacaio);
                });
        if (!lacaios.isEmpty()) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Destrua um lacaio inimigo com Provocar", null, null, null, lacaios, false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.findCardByIDLong(escolhido).morreu();
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Caçador Profissional (Grito de Guerra: Destrua um lacaio com 7 ou mais de
     * Ataque)
     */
    private static void ex1_005() throws JogouCardException {
        List<Card> lacaiosHeroi = Filtro.lacaiosComXOuMaisDeAtaque(card.getDono().getMesa(), 7);
        List<Card> lacaiosOponente = Filtro.lacaiosComXOuMaisDeAtaque(card.getOponente().getMesa(), 7);
        if (contemAlvosValidos(lacaiosHeroi, lacaiosOponente)) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Destrua um lacaio com 7 ou mais de ataque",
                    null, null, lacaiosHeroi, lacaiosOponente, false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.findCardByIDLong(escolhido).morreu();
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Mandingueiro Vodu (Grito de Guerra: Restaure 2 de Vida)
     */
    private static void ex1_011() throws JogouCardException {
        restaurarVida(2);
    }

    /**
     * Rei Mukla (Grito de Guerra: Conceda 2 Bananas ao seu oponente)
     */
    private static void ex1_014() {
        card.getOponente().addMao(partida.criarCard(BANANAS, System.nanoTime()));
        card.getOponente().addMao(partida.criarCard(BANANAS, System.nanoTime()));
    }

    /**
     * Engenheira Novata (Grito de Guerra: Compre um card)
     */
    private static void ex1_015() {
        card.getDono().comprarCarta(Card.COMPRA_EVENTO);
    }

    /**
     * Clériga do Sol Partido (Grito de Guerra: Conceda +1/+1 a um lacaio
     * aliado)
     */
    private static void ex1_019() throws JogouCardException {
        if (contemAlvosValidos(card.getDono().getMesa())) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Conceda +1/+1 a um lacaio aliado",
                    null, null, card.getDono().getMesa(), null, false);
            if (escolhido != EscolherMesa.CANCEL) {
                Card lacaio = partida.findCardByIDLong(escolhido);
                lacaio.addVidaMaxima(1);
                lacaio.addAtaque(1);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Mecânica de Dragonete (Grito de Guerra: Evoque um Dragonete Mecânico 2/1)
     */
    private static void ex1_025() {
        card.getDono().evocar(card.getDono().getPosicaoNaMesa(card.id_long) + 1,
                partida.criarCard(DRAGONETE_MECANICO2_1, System.nanoTime()));
    }

    /**
     * Draco do Crepúsculo (Grito de Guerra: Receba +1 de Vida para cada card na
     * sua mão)
     */
    private static void ex1_043() {
        if (card.getDono().getMao().size() > 1) {
            card.addVidaMaxima(card.getDono().getMao().size());
        }
    }

    /**
     * Anão Ferro Negro (Grito de Guerra: Conceda +2 de Ataque a um lacaio neste
     * turno)
     */
    private static void ex1_046() throws JogouCardException {
        if (contemAlvosValidos(partida.getMesa())) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Conceda +2 de Ataque a um lacaio neste turno",
                    null, null, card.getDono().getMesa(), card.getOponente().getMesa(), false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.getEfeitoProgramado().concederAtaqueAUmLacaioUmTurno(escolhido, 2);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Quebra-Feitiço (Grito de Guerra: Silencie um lacaio)
     */
    private static void ex1_048() throws JogouCardException {
        silencieUmLacaio();
    }

    /**
     * Mestre Cervejeito Jovem (Grito de Guerra: Devolva um lacaio aliado do
     * campo de batalha à sua mão)
     */
    private static void ex1_049() throws JogouCardException {
        if (contemAlvosValidos(card.getDono().getMesa()) && escolhido == EXECUTAR_GRITO_DE_GUERRA) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Devolva um lacaio aliado à sua mão",
                    null, null, card.getDono().getMesa(), null, false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.voltarPraMaoDoDono(escolhido);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Oráculo da Luz Fria (Grito de Guerra: Cada jogador compra 2 cards)
     */
    private static void ex1_050() {
        card.getDono().comprarCarta(Card.COMPRA_EVENTO);
        card.getDono().comprarCarta(Card.COMPRA_EVENTO);
        card.getOponente().comprarCarta(Card.COMPRA_EVENTO);
        card.getOponente().comprarCarta(Card.COMPRA_EVENTO);
    }

    /**
     * Mestre Cervejeiro Ancião (Grito de Guerra: Devolva um lacaio aliado do
     * campo de batalha à sua mão)
     */
    private static void ex1_057() throws JogouCardException {
        if (contemAlvosValidos(card.getDono().getMesa()) && escolhido == EXECUTAR_GRITO_DE_GUERRA) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Devolva um lacaio aliado à sua mão",
                    null, null, card.getDono().getMesa(), null, false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.voltarPraMaoDoDono(escolhido);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Protetora Solfúria (Grito de Guerra: Conceda Provocar aos lacaios
     * adjacentes)
     */
    private static void ex1_058() {
        Utils.getAdjacentes(card).forEach(adjacente -> adjacente.addMechanics(PROVOCAR));
    }

    /**
     * Alquimista Enlouquecido (Grito de Guerra: Troque o Ataque pela Vida de um
     * lacaio)
     */
    private static void ex1_059() throws JogouCardException {
        if (contemAlvosValidos(partida.getMesa())) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Troque o Ataque pela Vida de um lacaio",
                    null, null, card.getDono().getMesa(), card.getOponente().getMesa(), false);
            if (escolhido != EscolherMesa.CANCEL) {
                Card lacaio = partida.findCardByIDLong(escolhido);
                if (!lacaio.isMorto()) {
                    int vida = lacaio.getVida();
                    lacaio.setVidaOriginal(lacaio.getAtaque(), true);
                    lacaio.setAtaque(vida, true);
                }
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Gosma Ácida do Pântano (Grito de Guerra: Destrua a arma do seu oponente)
     */
    private static void ex1_066() {
        card.getOponente().destruirArma();
    }

    /**
     * Bombardeiro Louco (Grito de Guerra: Cause 3 de dano divididos
     * aleatoriamente entre todos os outros personagens)
     */
    private static void ex1_082() {
        for (int i = 0; i < 3; i++) {
            Utils.dano(partida, Utils.getPersonagemAleatorio(partida, card.id_long), 1);
        }
    }

    /**
     * Mestre-faz-tudo Superchispa (Grito de Guerra: Transforme outro lacaio
     * aleatório em um Demossauro 5/5 ou em um esquilo 1/1)
     */
    private static void ex1_083() {
        if (partida.getMesa().size() > 1) {
            Card random = Utils.getLacaioAleatorio(partida.getMesa(), card);
            partida.polimorfia(random.id_long, Utils.random(9) % 2 == 0
                    ? ESQUILO1_1 : DEMOSSAURO5_5, true);
        }
    }

    /**
     * Técnico de Controle Mental (Grito de Guerra: Se seu oponente tem 4 ou
     * mais lacaios, assuma o controle de um aleatoriamente)
     */
    private static void ex1_085() {
        List<Card> mesaOponente = card.getOponente().getMesa();
        if (mesaOponente.size() >= 4) {
            Card aleatorio = mesaOponente.get(Utils.random(mesaOponente.size()) - 1);
            partida.roubar(aleatorio.id_long);
        }
    }

    /**
     * Golem Arcano (Investida. Grito de Guerra: Conceda um Cristal de Mana ao
     * seu oponente)
     */
    private static void ex1_089() {
        card.getOponente().addMana(1);
    }

    /**
     * Sacerdotisa do Conluio (Grito de Guerra: Assuma o controle de um lacaio
     * inimigo que tenha 2 ou menos de Ataque)
     */
    private static void ex1_091() throws JogouCardException {
        List<Card> lacaios = Filtro.lacaiosComXOuMenosDeAtaque(card.getOponente().getMesa(), 2);
        if (contemAlvosValidos(lacaios)) {
            if (card.getDono().temEspacoNaMesa()) {
                if (escolhido == EXECUTAR_GRITO_DE_GUERRA) {
                    escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(), null,
                            "Assuma o controle", null, null, null, lacaios, false);
                    if (escolhido != EscolherMesa.CANCEL) {
                        partida.roubar(escolhido);
                    } else {
                        throw new JogouCardException("Grito de guerra cancelado.");
                    }
                } else if (!card.getDono().temEspacoNaMesa()) {
                    partida.findCardByIDLong(escolhido).morreu();
                }
            } else {
                UtilizarFeitico.alerta("Você não pode ter 8 lacaios na mesa!");
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Defensor de Argus (Grito de Guerra: Conceda +1/+1 e Provocar aos lacaios
     * adjacentes)
     */
    private static void ex1_093() {
        Utils.getAdjacentes(card).forEach(adjacente -> {
            adjacente.addVidaMaxima(1);
            adjacente.addAtaque(1);
            adjacente.addMechanics(PROVOCAR);
        });
    }

    /**
     * Vidente da Luz Fria (Grito de Guerra: Conceda +2 de Vida a TODOS os
     * outros Murlocs)
     */
    private static void ex1_103() {
        partida.getMesa().stream()
                .filter((lacaio) -> (!lacaio.equals(card) && lacaio.isMurloc()))
                .forEach((lacaio) -> {
                    lacaio.addVidaMaxima(2);
                });
    }

    /**
     * Gelbin Mekkatorque (Grito de Guerra: Evoque uma invenção FABULOSA)
     */
    private static void ex1_112() {
        int random = Utils.random(INVENCAO_FABULOSA.length) - 1;
        card.getDono().evocar(card.getDono().getPosicaoNaMesa(card.id_long) + 1,
                partida.criarCard(INVENCAO_FABULOSA[random], System.nanoTime()));
    }

    /**
     * Leeroy Jenkins (Investida. Grito de Guerra: Evoque dois Dragonetes 1/1
     * para o seu oponente)
     */
    private static void ex1_116() {
        card.getOponente().evocar(partida.criarCard(DRAGONETE1_1, System.nanoTime()));
        card.getOponente().evocar(partida.criarCard(DRAGONETE1_1, System.nanoTime()));
    }

    /**
     * Lâmina da Perdição (Grito de Guerra: Cause 1 de dano. Combo: em vez de 1,
     * cause 2 de dano)
     */
    private static void ex1_133() throws JogouCardException {
        causarDano(Utils.isCombo(card) ? 2 : 1);
    }

    /**
     * Elemental de Gelo (Grito de Guerra: Congele um personagem)
     */
    private static void ex1_283() throws JogouCardException {
        escolhido = EscolherMesa.main(escolhido, card, "Congele um personagem", partida, false);
        if (escolhido != EscolherMesa.CANCEL) {
            if (escolhido == Param.HEROI || escolhido == Param.OPONENTE) {
                (escolhido == Param.HEROI ? partida.getHero() : partida.getOponente()).setFreeze(true);
            } else {
                partida.findCardByIDLong(escolhido).setFreeze(true);
            }
        } else {
            throw new JogouCardException("Grito de guerra cancelado.");
        }
    }

    /**
     * Draco Lazúli (+1 de Dano Mágico. Grito de Guerra: Compre um card)
     */
    private static void ex1_284() {
        card.getDono().comprarCarta(Card.COMPRA_EVENTO);
    }

    /**
     * Guarda Vil (Provocar. Grito de Guerra: Destrua um de seus Cristais de
     * Mana)
     */
    private static void ex1_301() {
        card.getDono().delMana(1);
    }

    /**
     * Terror do Caos (Grito de Guerra: Destrua os lacaios dos dois lados deste
     * e receba o Ataque e a Vida deles)
     */
    private static void ex1_304() {
        if (escolhido == EXECUTAR_GRITO_DE_GUERRA) {
            List<Card> lacaios = Utils.getAdjacentes(card);
            if (!lacaios.isEmpty()) {
                int vida = 0, ataque = 0;
                for (Card lacaio : lacaios) {
                    vida += (lacaio.getVidaSemAura() * (REPETIR_GRITO_DE_GUERRA ? 2 : 1));
                    ataque += (lacaio.getAtaqueSemAura() * (REPETIR_GRITO_DE_GUERRA ? 2 : 1));
                }
                EmArea.destruir(lacaios);
                card.addVidaMaxima(vida);
                card.addAtaque(ataque);
            }
        }
    }

    /**
     * Súcubo (Grito de Guerra: Descarte um card aleatório)
     */
    private static void ex1_306() {
        card.getDono().descartar();
    }

    /**
     * Demonarca (Investida. Grito de Guerra: Descarte dois cards aleatórios)
     */
    private static void ex1_310() {
        card.getDono().descartar();
        card.getDono().descartar();
    }

    /**
     * Lorde Abissal (Grito de Guera: Cause 5 de dano ao seu herói)
     */
    private static void ex1_313() {
        card.getDono().delHealth(5);
    }

    /**
     * Diabrete das Chamas (Grito de Guerra: Cause 3 de dano ao seu herói)
     */
    private static void ex1_319() {
        card.getDono().delHealth(3);
    }

    /**
     * Protetor Argênteo (Grito de Guerra: Conceda Escudo Divino a um lacaio
     * aliado)
     */
    private static void ex1_362() throws JogouCardException {
        if (contemAlvosValidos(card.getDono().getMesa())) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Conceda Escudo Divino", null, null,
                    card.getDono().getMesa(), null, false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.findCardByIDLong(escolhido).addMechanics(ESCUDO_DIVINO);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Pacificador Aldor (Grito de Guerra: Mude o Ataque de um lacaio inimigo
     * para 1)
     */
    private static void ex1_382() throws JogouCardException {
        if (contemAlvosValidos(partida.getOponente().getMesa())) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    null, "Mude o Ataque de um lacaio inimigo para 1",
                    null, null, null, card.getOponente().getMesa(), false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.findCardByIDLong(escolhido).setAtaque(1, true);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Armeira de Arathi (Grito de Guerra: Equipe uma arma 2/2)
     */
    private static void ex1_398() throws JogouCardException {
        card.getDono().setWeapon(partida.criarCard(ARMA2_2, System.nanoTime()));
    }

    /**
     * Murloc Caçamaré (Grito de Guerra: Evoque um Batedor Murloc 1/1)
     */
    private static void ex1_506() {
        card.getDono().evocar(card.getDono().getPosicaoNaMesa(card.id_long) + 1,
                partida.criarCard(BATEDOR_MURLOC1_1, System.nanoTime()));
    }

    /**
     * Harrison Jones (Grito de Guerra: Destrua a arma de seu oponente e compre
     * o mesmo número de cards que a durabilidade da arma)
     */
    private static void ex1_558() throws JogouCardException {
        if (card.getOponente().getArma() != null) {
            int quantidade = card.getOponente().getArma().getDurability();
            card.getOponente().setWeapon(null);
            for (int i = 0; i < quantidade; i++) {
                card.getDono().comprarCarta(Card.COMPRA_EVENTO);
            }
        }
    }

    /**
     * Alexstrasza (Grito de Guerra: Defina a vida remanescente do herói para
     * 15)
     */
    private static void ex1_561() throws JogouCardException {
        escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                null, "Defina a vida remanescente do herói para 15",
                card.getDono(), card.getOponente(), null, null, false);
        if (escolhido != EscolherMesa.CANCEL) {
            (escolhido == Param.HEROI ? card.getDono() : card.getOponente()).setVidaTotal(15, true);
        } else {
            throw new JogouCardException("Grito de guerra cancelado.");
        }
    }

    /**
     * Onyxia (Grito de Guerra: Evoque Dragonetes 1/1 até seu lado do campo de
     * batalha estar cheio)
     *
     * @param h Heroi
     */
    private static void ex1_562() {
        while (card.getDono().temEspacoNaMesa()) {
            card.getDono().evocar(card.getDono().getPosicaoNaMesa(card.id_long) + 1,
                    partida.criarCard(DRAGONETE1_1, System.nanoTime()));
        }
    }

    /**
     * Manipulador Sem-rosto (Grito de Guerra: Escolha um lacaio e torne-se uma
     * cópia dele)
     */
    private static void ex1_564() throws JogouCardException {
        if (contemAlvosValidos(partida.getMesa()) && escolhido == EXECUTAR_GRITO_DE_GUERRA) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Escolha um lacaio e torne-se uma cópia dele",
                    null, null, card.getDono().getMesa(), card.getOponente().getMesa(), false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.tornarSeCopia(card.id_long, escolhido);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Sacerdotisa de Eluna (Grito de Guerra: Restaure 4 de Vida do seu herói)
     */
    private static void ex1_583() {
        card.getDono().addHealth(4);
    }

    /**
     * Mago Ancião (Grito de Guerra: Conceda +1 de Dano Mágico aos lacaios
     * adjacentes)
     */
    private static void ex1_584() {
        Utils.getAdjacentes(card).forEach(adjacente -> adjacente.addDanoMagico(1));
    }

    /**
     * Voz dos Ventos (Grito de Guerra: Conceda Fúria dos Ventos a um lacaio
     * aliado)
     */
    private static void ex1_587() throws JogouCardException {
        if (contemAlvosValidos(card.getDono().getMesa())) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Conceda Fúria dos Ventos a um lacaio aliado",
                    null, null, card.getDono().getMesa(), null, false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.findCardByIDLong(escolhido).addMechanics(FURIA_DOS_VENTOS);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Cavaleiro Sangrento (Grito de Guerra: Todos os lacaios perdem Escudo
     * Divino. Receba +3/+3 para cada Escudo perdido)
     */
    private static void ex1_590() {
        int quantidade = (int) partida.getMesa().stream()
                .filter((lacaio) -> (lacaio.isEscudoDivino())).count();
        if (quantidade > 0) {
            partida.getMesa().stream()
                    .filter((lacaio) -> (lacaio.isEscudoDivino()))
                    .forEach((lacaio) -> {
                        lacaio.delMechanics(ESCUDO_DIVINO);
                    });
            card.addVidaMaxima(quantidade * 3);
            card.addAtaque(quantidade * 3);
        }
    }

    /**
     * Lâmina Soturna (Grito de Guerra: Cause 3 de dano ao herói inimigo)
     */
    private static void ex1_593() {
        card.getOponente().delHealth(3);
    }

    /**
     * Capataz Cruel (Grito de Guerra: Cause 1 de dano a um lacaio e conceda-lhe
     * +2 de Ataque)
     */
    private static void ex1_603() throws JogouCardException {
        if (contemAlvosValidos(partida.getMesa())) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Cause 1 de dano e conceda-lhe +2 de Ataque",
                    null, null, card.getDono().getMesa(), card.getOponente().getMesa(), false);
            if (escolhido != EscolherMesa.CANCEL) {
                Card cardEscolhido = partida.findCardByIDLong(escolhido);
                cardEscolhido.delVida(1);
                cardEscolhido.addAtaque(2);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Maga do Kirin Tor (Grito de Guerra: O próximo Segredo que você lançar
     * neste turno custará 0)
     */
    private static void ex1_612() {
        partida.getFeiticosAtivos().ativarEX1_612();
    }

    /**
     * Impositor do Templo (Grito de Guerra: Conceda +3 de Vida a um lacaio
     * aliado)
     */
    private static void ex1_623() throws JogouCardException {
        if (contemAlvosValidos(card.getDono().getMesa())) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Conceda +3 de Vida a um lacaio aliado",
                    null, null, card.getDono().getMesa(), null, false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.findCardByIDLong(escolhido).addVidaMaxima(3);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Gosma Ecoante (Grito de Guerra: No final do turno, evoque uma cópia exata
     * deste lacaio)
     */
    private static void fp1_003() {
        if (escolhido == EXECUTAR_GRITO_DE_GUERRA) {
            partida.getEfeitoProgramado().clonarLacaioFimTurno(card.id_long);
        }
    }

    /**
     * Alma Ululante (Grito de Guerra: Silencie seus outros lacaios)
     */
    private static void fp1_016() {
        card.getDono().getMesa().stream()
                .filter((lacaio) -> (!lacaio.equals(card)))
                .forEach((lacaio) -> {
                    lacaio.setSilenciado(true);
                });
    }

    /**
     * Repugnaz (Grito de Guerra: Feitiços inimigos custam 5 a mais no próximo
     * turno)
     */
    private static void fp1_030() {
        partida.getAuraEstatica().feiticosMaoCustamXDaquiAXTurnos(5, Param.ADD, Param.OPONENTE, 1);
    }

    /**
     * Mago Explosivo Goblin (Grito de Guerra: Se você tiver um Mecanoide, cause
     * 4 de dano dividido aleatoriamente entre todos os inimigos)
     */
    private static void gvg_004() {
        if (card.getDono().temMecanoideNaMesa(card)) {
            for (int i = 0; i < 4; i++) {
                Utils.dano(partida, Utils.getAleatorioOponente(partida), 1);
            }
        }
    }

    /**
     * Bombardeiro das Sombras (Grito de Guerra: Cause 3 de dano a cada herói)
     */
    private static void gvg_009() {
        card.getOponente().delHealth(3);
        card.getDono().delHealth(3);
    }

    /**
     * Encolhista (Grito de Guerra: Conceda -2 de Ataque a um lacaio neste
     * turno)
     */
    private static void gvg_011() throws JogouCardException {
        List<Card> mesaHeroi = Filtro.lacaiosComXOuMaisDeAtaqueSemAura(partida.getHero().getMesa(), 1);
        List<Card> mesaOponente = Filtro.lacaiosComXOuMaisDeAtaqueSemAura(partida.getOponente().getMesa(), 1);
        if (contemAlvosValidos(mesaHeroi, mesaOponente)) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Conceda -2 de Ataque a um lacaio neste turno",
                    null, null, mesaHeroi, mesaOponente, false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.getEfeitoProgramado().concederAtaqueAUmLacaioUmTurno(escolhido,
                        partida.findCardByIDLong(escolhido).getAtaqueSemAura() == 1 ? -1 : -2);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Vol'jin (Grito de Guerra: Troque a Vida deste lacaio pela de outro)
     */
    private static void gvg_014() throws JogouCardException {
        if (contemAlvosValidos(partida.getMesa())) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Troque a Vida deste lacaio pela de outro",
                    null, null, card.getDono().getMesa(), card.getOponente().getMesa(), false);
            if (escolhido != EscolherMesa.CANCEL) {
                Card cardEscolhido = partida.findCardByIDLong(escolhido);
                card.setVidaOriginal(cardEscolhido.getVida(), true);
                cardEscolhido.setVidaOriginal(card.getVidaOriginal(), true);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Autobarbeiro Goblínico (Grito de Guerra: Conceda +1 de Ataque à sua arma)
     */
    private static void gvg_023() {
        if (card.getDono().getArma() != null) {
            card.getDono().getArma().addAtaque(1);
        }
    }

    /**
     * Neptulon (Grito de Guerra: Adicione 4 Murlocs aleatórios à sua mão.
     * Sobrecarga: 3)
     */
    private static void gvg_042() {
        for (int i = 0; i < 4; i++) {
            card.getDono().addMao(partida.criarCard(Random.byRace(MURLOC).getId(), System.nanoTime()));
        }
    }

    /**
     * Glaivezuca (Grito de Guerra: Conceda +1 de Ataque a um lacaio aliado
     * aleatório)
     */
    private static void gvg_043() {
        if (!card.getDono().getMesa().isEmpty()) {
            Utils.getLacaioAleatorio(card.getDono().getMesa()).addAtaque(1);
        }
    }

    /**
     * Rei das Feras (Provocar. Grito de Guerra: Ganhe +1 de Ataque para cada
     * outra Fera que você tiver)
     */
    private static void gvg_046() {
        int i = Filtro.raca(card.getDono().getMesa(), FERA).size();
        if (i > 1) {
            card.addAtaque(i - 1);
        }
    }

    /**
     * Saltador Metalodonte (Grito de Guerra: Conceda +2 de Ataque aos seus
     * outros Mecanoides)
     */
    private static void gvg_048() {
        card.getDono().getMesa().stream()
                .filter((lacaio) -> (lacaio.isMecanoide() && !lacaio.equals(card)))
                .forEach((lacaio) -> {
                    lacaio.addAtaque(2);
                });
    }

    /**
     * Dama Escudeira (Grito de Guerra: Receba 5 de Armadura)
     */
    private static void gvg_053() {
        card.getDono().addShield(5);
    }

    /**
     * Calhambeque Vai-no-tranco (Grito de Guerra: Conceda +2/+2 a um Mecanoide
     * aliado)
     */
    private static void gvg_055() throws JogouCardException {
        List<Card> mecanoides = Filtro.raca(card.getDono().getMesa(), MECANOIDE);
        if (contemAlvosValidos(mecanoides)) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Conceda +2/+2 a um Mecanoide aliado",
                    null, null, mecanoides, null, false);
            if (escolhido != EscolherMesa.CANCEL) {
                Card aliado = partida.findCardByIDLong(escolhido);
                aliado.addVidaMaxima(2);
                aliado.addAtaque(2);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Jaganata de Ferro (Grito de Guerra: Coloque uma Mina no deck do seu
     * oponente. Ao ser comprada, ela explode causando 10 de dano)
     */
    private static void gvg_056() {
        card.getOponente().addCardDeckAleatoriamente(partida.criarCard(MINA, System.nanoTime()));
    }

    /**
     * Engrenomalho (Grito de Guerra: Conceda Escudo Divino e Provocar a um
     * lacaio aliado aleatório)
     */
    private static void gvg_059() {
        if (!card.getDono().getMesa().isEmpty()) {
            Card aleatorio = Utils.getLacaioAleatorio(card.getDono().getMesa());
            aleatorio.addMechanics(ESCUDO_DIVINO);
            aleatorio.addMechanics(PROVOCAR);
        }
    }

    /**
     * Intendente (Grito de Guerra: Conceda +2/+2 aos seus Recrutas do Punho de
     * Prata)
     */
    private static void gvg_060() {
        card.getDono().getMesa().stream()
                .filter((lacaio) -> (lacaio.getId().equals(RECRUTA_PUNHO_DE_PRATA)))
                .forEach((lacaio) -> {
                    lacaio.addVidaMaxima(2);
                    lacaio.addAtaque(2);
                });
    }

    /**
     * Robô de Cura Vintage (Grito de Guerra: Recupere 8 de Vida do seu herói)
     */
    private static void gvg_069() {
        card.getDono().addHealth(8);
    }

    /**
     * Mística de Kezan (Grito de Guerra: Assuma o controle de um Segredo
     * aleatório do inimigo)
     */
    private static void gvg_074() {
        List<Card> segredos = card.getOponente().getSegredo();
        if (!segredos.isEmpty()) {
            Card segredo = segredos.get(Utils.random(segredos.size()) - 1);
            card.getDono().addSegredo(segredo);
            card.getOponente().delSegredo(segredo);
        }
    }

    /**
     * Druida da Presa (Grito de Guerra: Se você tiver uma Fera, este lacaio
     * torna-se 7/7)
     */
    private static void gvg_080() {
        if (card.getDono().temFeraNaMesa(card)) {
            partida.polimorfia(card.id_long, "GVG_080t", true);
        }
    }

    /**
     * Robô de Reparos Aprimorado (Grito de Guerra: Conceda +4 de Vida a um
     * Mecanoide aliado)
     */
    private static void gvg_083() throws JogouCardException {
        List<Card> aliados = Filtro.raca(card.getDono().getMesa(), MECANOIDE);
        if (contemAlvosValidos(aliados)) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Conceda +4 de Vida a um Mecanoide aliado",
                    null, null, aliados, null, false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.findCardByIDLong(escolhido).addVidaMaxima(4);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Bombardeiro Muito Louco (Grito de Guerra: Cause 6 de dano divididos
     * aleatoriamente entre todos os outros personagens)
     */
    private static void gvg_090() {
        for (int i = 0; i < 6; i++) {
            Utils.dano(partida, Utils.getPersonagemAleatorio(partida, card.id_long), 1);
        }
    }

    /**
     * Experimentador Gnômico (Grito de Guerra: Compre um card. Se for um
     * lacaio, transforme-o em uma Galinha)
     */
    private static void gvg_092() {
        Card comprado = card.getDono().comprarCarta(Card.COMPRA_EVENTO);
        if (comprado.isLacaio()) {
            partida.polimorfia(comprado.id_long, GALINHA1_1T2, false);
        }
    }

    /**
     * Exorcistinha (Provocar. Grito de Guerra: Receba +1/+1 para cada lacaio
     * inimigo com Último Suspiro)
     */
    private static void gvg_097() {
        int quantidade = (int) card.getOponente().getMesa().stream()
                .filter((lacaio) -> (lacaio.isUltimoSuspiro())).count();
        if (quantidade > 0) {
            card.addVidaMaxima(quantidade);
            card.addAtaque(quantidade);
        }
    }

    /**
     * Taca-bomba (Grito de Guerra: Cause 4 de dano a um lacaio inimigo
     * aleatório)
     */
    private static void gvg_099() {
        Utils.dano(partida, Utils.getAleatorioOponente(partida), 4);
    }

    /**
     * Purificador Escarlate (Grito de Guerra: Cause 2 de dano a todos os
     * lacaios com Grito de Guerra)
     */
    private static void gvg_101() {
        partida.getMesa().stream()
                .filter((lacaio) -> (lacaio.isGritoDeGuerra() && !lacaio.equals(card)))
                .forEach((lacaio) -> {
                    lacaio.delVida(2);
                });
    }

    /**
     * Técnico da Vila da Gambiarra (Grito de Guerra: Se você tiver um
     * Mecanoide, receba +1/+1 e adicione uma Peça Sobressalente à sua mão)
     */
    private static void gvg_102() {
        if (card.getDono().temMecanoideNaMesa(card)) {
            card.addVidaMaxima(1);
            card.addAtaque(1);
            card.getDono().addMao(partida.criarCard(Random.pecaSobressalente(), System.nanoTime()));
        }
    }

    /**
     * Mecanoide Aprimorador (Grito de Guerra: Conceda Fúria dos Ventos,
     * Provocar ou Escudo Divino aos seus outros lacaios (aleatoriamente))
     */
    private static void gvg_107() {
        String[] mecanicas = new String[]{FURIA_DOS_VENTOS, PROVOCAR, ESCUDO_DIVINO};
        card.getDono().getMesa().stream()
                .filter((lacaio) -> (!lacaio.equals(card)))
                .forEach((lacaio) -> {
                    lacaio.addMechanics(mecanicas[Utils.random(mecanicas.length) - 1]);
                });
    }

    /**
     * Recombobulador (Grito de Guerra: Transforme um lacaio aliado em um lacaio
     * aleatório com o mesmo Custo)
     */
    private static void gvg_108() throws JogouCardException {
        if (escolhido == EXECUTAR_GRITO_DE_GUERRA
                && contemAlvosValidos(card.getDono().getMesa())) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Transformar", null, null, card.getDono().getMesa(), null, false);
            if (escolhido != EscolherMesa.CANCEL) {
                int custo = partida.findCardByIDLong(escolhido).getCost();
                Card random = Random.getCard(LACAIO, GERAL, custo, GERAL, GERAL, GERAL);
                partida.polimorfia(escolhido, random.getId(), true);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Dr. Cabum (Grito de Guerra: Evoque duas 1/1 Robombas. ATENÇÃO: Os Robôs
     * podem explodir)
     */
    private static void gvg_110() {
        int indice = card.getDono().getPosicaoNaMesa(card.id_long);
        card.getDono().evocar(indice, partida.criarCard(ROBOMBA1_1, System.nanoTime()));
        indice = card.getDono().getPosicaoNaMesa(card.id_long);
        card.getDono().evocar(indice + 1, partida.criarCard(ROBOMBA1_1, System.nanoTime()));
    }

    /**
     * Tocha (Grito de Guerra e Último Suspiro: Adicione um card Peça
     * Sobressalente à sua mão)
     */
    private static void gvg_115() {
        card.getDono().addMao(partida.criarCard(Random.pecaSobressalente(), System.nanoTime()));
    }

    /**
     * Blingtron 3000 (Grito de Guerra: Equipe uma arma aleatória para cada
     * jogador)
     */
    private static void gvg_119() throws JogouCardException {
        if (escolhido == EXECUTAR_GRITO_DE_GUERRA) {
            card.getDono().setWeapon(partida.criarCard(Random.arma().getId(), System.nanoTime()));
            card.getOponente().setWeapon(partida.criarCard(Random.arma().getId(), System.nanoTime()));
        }
    }

    /**
     * Rosarães Guima (Grito de Guerra: Destrua uma Fera)
     */
    private static void gvg_120() throws JogouCardException {
        List<Card> ferasHeroi = Filtro.raca(card.getDono().getMesa(), FERA);
        List<Card> ferasOponente = Filtro.raca(card.getOponente().getMesa(), FERA);
        if (contemAlvosValidos(ferasHeroi, ferasOponente)) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Destrua uma Fera", null, null, ferasHeroi, ferasOponente, false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.findCardByIDLong(escolhido).morreu();
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Conjurador Etéreo (Grito de Guerra: Descobrir um feitiço)
     */
    private static void loe_003() {
        card.getDono().addMao(partida.criarCard(Utils.descobrir("Descubra um feitiço",
                Utils.getIds(Random.listOnlyFeitico(3))), System.nanoTime()));
    }

    /**
     * Curador de Museu (Grito de Guerra: Descobrir um card com Último Suspiro)
     */
    private static void loe_006() {
        Descobrir.mecanica(card, ULTIMO_SUSPIRO);
    }

    /**
     * Reno Jackson (Grito de Guerra: Se não tiver nenhum card repetido no seu
     * deck, cure a vida toda do seu herói)
     */
    private static void loe_011() {
        List<String> list = new ArrayList<>();
        card.getDono().getDeck().stream()
                .map(Card::getId)
                .forEach(list::add);
        if (list.size() == list.stream().distinct().count()) {
            card.getDono().addHealth(card.getDono().getVidaTotal() - card.getDono().getHealth());
        }
    }

    /**
     * Guardiã de Uldaman (Grito de Guerra: Mude o Ataque e a Vida de um lacaio
     * para 3)
     */
    private static void loe_017() throws JogouCardException {
        if (contemAlvosValidos(card.getDono().getMesa())) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(), card, "Destrua uma Fera",
                    null, null, card.getDono().getMesa(), card.getOponente().getMesa(), false);
            if (escolhido != EscolherMesa.CANCEL) {
                Card cardEscolhido = partida.findCardByIDLong(escolhido);
                cardEscolhido.setVidaOriginal(3, true);
                cardEscolhido.setAtaque(3, true);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Camelo do Deserto (Grito de Guerra: Coloque um lacaio com 1 de custo de
     * cada deck no campo de batalha)
     */
    private static void loe_020() {
        for (Heroi h : new Heroi[]{card.getDono(), card.getOponente()}) {
            Card lacaio = Utils.random(Filtro.custo(Filtro.lacaio(h.getDeck()), 1));
            if (lacaio != null) {
                lacaio.getDono().evocar(lacaio);
                lacaio.getDono().delDeck(lacaio);
            }
        }
    }

    /**
     * Ambulante das Trevas (Grito de Guerra: Descobrir um card com 1 de custo)
     */
    private static void loe_023() {
        Descobrir.custo(card, 1);
    }

    /**
     * Escaravelho Adornado (Grito de Guerra: Descobrir um card com 3 de custo)
     */
    private static void loe_029() {
        Descobrir.custo(card, 3);
    }

    /**
     * Gorila-robô A-3 (Grito de Guerra: Se você controlar outro Mecanoide,
     * Descubra um Mecanoide)
     */
    private static void loe_039() {
        if (card.getDono().temMecanoideNaMesa(card)) {
            Descobrir.raca(card, MECANOIDE);
        }
    }

    /**
     * Aranha da Tumba (Grito de Guerra: Descubra uma Fera)
     */
    private static void loe_047() {
        Descobrir.raca(card, FERA);
    }

    /**
     * Demossauro Fossilizado (Grito de Guerra: Se você controlar uma Fera,
     * receba Provocar)
     */
    private static void loe_073() {
        if (card.getDono().temFeraNaMesa(card)) {
            card.addMechanics(PROVOCAR);
        }
    }

    /**
     * Dom Finlei Mrrggltone (Grito de Guerra: Descubra um Poder Heroico novo)
     */
    private static void loe_076() {
        Descobrir.poderHeroico(card);
    }

    /**
     * Elise Mirestela (Grito de Guerra: Embaralhe o "Mapa do Macaco Dourado" no
     * seu deck)
     */
    private static void loe_079() {
        card.getDono().addCardDeckAleatoriamente(
                partida.criarCard(MAPA_DO_MACACO_DOURADO, System.nanoTime()));
    }

    /**
     * Macaco Dourado (Provocar. Grito de Guerra: Substitua sua mão e seu deck
     * por lacaios Lendários)
     */
    private static void loe_019t2() {
        List<Card> list = new ArrayList<>(card.getDono().getMao());
        list.addAll(card.getDono().getDeck());
        List<String> lendarios = new ArrayList<>();
        while (lendarios.size() < list.size()) {
            String random = Random.getCard(LACAIO, GERAL, Random.QUALQUER_CUSTO,
                    GERAL, LENDARIO, GERAL).getId();
            if (!lendarios.contains(random)) {
                lendarios.add(random);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            try {
                partida.polimorfia(list.get(i).id_long, lendarios.get(i), false);
            } catch (Exception e) {
            }
        }
    }

    /**
     * Arquiladrão Rafaam (Grito de Guerra: Descubra um Artefato poderoso)
     */
    private static void loe_092() throws JogouCardException {
        String id = EscolherCard.main("Escolha um", ARTEFATO_PODEROSO);
        if (id != null) {
            card.getDono().addMao(partida.criarCard(id, System.nanoTime()));
        } else {
            throw new JogouCardException("Grito de guerra cancelado.");
        }
    }

    /**
     * Vulto Antigo (Grito de Guerra: Embaralhe no seu deck uma "Maldição
     * Antiga" que lhe causa 7 de dano ao ser comprada)
     */
    private static void loe_110() {
        card.getDono().addCardDeckAleatoriamente(partida.criarCard(MALDICAO_ANTIGA, System.nanoTime()));
    }

    /**
     * Caçadora do Relicário (Grito de Guerra: Se você possuir outros 6 lacaios,
     * receba +4/+4)
     */
    private static void loe_116() {
        if (card.getDono().getMesa().size() == 7) {
            card.addVidaMaxima(4);
            card.addAtaque(4);
        }
    }

    /**
     * Mestre dos Disfarces (Grito de Guerra: Conceda Furtividade a um lacaio
     * aliado)
     */
    private static void new1_014() throws JogouCardException {
        if (contemAlvosValidos(card.getDono().getMesa())) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Conceda Furtividade a um lacaio aliado",
                    null, null, card.getDono().getMesa(), null, false);
            if (escolhido != EscolherMesa.CANCEL) {
                if (!partida.findCardByIDLong(escolhido).getMechanics().contains(FURTIVIDADE)) {
                    partida.findCardByIDLong(escolhido).addMechanics(FURTIVIDADE);
                }
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Papagaio do Capitão (Grito de Guerra: Coloque um Pirata aleatório do seu
     * deck na sua mão)
     */
    private static void new1_016() {
        Card pirata = Utils.random(Filtro.raca(card.getDono().getDeck(), PIRATA));
        if (pirata != null) {
            card.getDono().addMao(pirata);
            card.getDono().delDeck(pirata);
        }
    }

    /**
     * Caranguejo Faminto (Grito de Guerra: Destrua um murloc e receba +2/+2)
     */
    private static void new1_017() throws JogouCardException {
        List<Card> murlocs = Filtro.raca(card.getDono().getMesa(), MURLOC);
        if (contemAlvosValidos(murlocs)) {
            escolhido = EscolherMesa.main(escolhido, partida.getPartidaView(),
                    card, "Destrua um murloc e receba +2/+2", null, null, murlocs, null, false);
            if (escolhido != EscolherMesa.CANCEL) {
                partida.findCardByIDLong(escolhido).morreu();
                card.addVidaMaxima(2);
                card.addAtaque(2);
            } else {
                throw new JogouCardException("Grito de guerra cancelado.");
            }
        }
    }

    /**
     * Saqueadora da Vela Sangrenta (Grito de Guerra: Ela recebe Ataque
     * equivalente ao Ataque da sua arma)
     */
    private static void new1_018() {
        Card arma = card.getDono().getArma();
        if (arma != null && arma.getAtaque() > 0) {
            card.addAtaque(arma.getAtaque());
        }
    }

    /**
     * Capitão Peleverde (Grito de Guerra: Conceda +1/+1 à sua arma)
     */
    private static void new1_024() {
        Card arma = card.getDono().getArma();
        if (arma != null) {
            arma.addAtaque(1);
            arma.addDurabilidade(1);
        }
    }

    /**
     * Corsário da Vela Sangrenta (Grito de Guerra: Remova 1 de Durabilidade da
     * arma do seu oponente)
     */
    private static void new1_025() {
        if (card.getOponente().getArma() != null) {
            card.getOponente().getArma().delDurabilidade(1);
        }
    }

    /**
     * Asa da Morte (Grito de Guerra: Destrua todos os outros lacaios e descarte
     * a sua mão)
     */
    private static void new1_030() {
        List<Card> destruir = new ArrayList<>();
        card.getDono().getMesa().stream()
                .filter(c -> (!c.equals(card)))
                .forEach(destruir::add);
        List<Card> descartar = new ArrayList<>();
        card.getDono().getMao().forEach(descartar::add);
        EmArea.destruir(destruir);
        descartar.forEach(card.getDono()::descartar);
    }

    /**
     * Kodo em Disparada (Grito de Guerra: Destrua um lacaio inimigo aleatório
     * com 2 ou menos de Ataque)
     */
    private static void new1_041() {
        Card aleatorio = Utils.random(Filtro.lacaiosComXOuMenosDeAtaque(card.getOponente().getMesa(), 2));
        if (aleatorio != null) {
            aleatorio.morreu();
        }
    }

    /**
     * Chefe Tauren Elite (Grito de Guerra: Dê aos dois jogadores o poder do
     * ROCK! (com um card de Acorde Poderoso)
     */
    private static void pro_001() {
        card.getDono().addMao(partida.criarCard(Random.acordePoderoso(), System.nanoTime()));
        card.getOponente().addMao(partida.criarCard(Random.acordePoderoso(), System.nanoTime()));
    }

}
