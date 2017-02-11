package  com.limagiran.hearthstone.util;

import static com.limagiran.hearthstone.poder.control.PoderHeroico.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Vinicius Silva
 */
public interface Values {

    //hash para verificar a integridade do arquivo JSON dos cards
    public static final String CARD_JSON_HASH = "85A57E418F60DC9A3E00731CD2836DB3C7221208AA8BACDE76BAEF2EE1FBE080";
    //hash para verificar a integridade da lista de cards
    public static final String CARD_ID_HASH = "3CB169E795017E5B7AB27EAF42DD2B11A7267D40CF93EAA84B0E26A3DFBFE822";
    //Lista de ids disponíveis no jogo
    public static final List<String> CARD_ID = loadCardsId();

    //Lista de ids disponíveis no jogo
    public static final List<String> AUDIOS_LIST = loadAudiosList();

    //Título a ser exibido na barra de títulos das janelas
    public static final String TITLE = "by Vinicius Lima - vini_lima@hotmail.com.br - ©2015 Blizzard Entertainment. Todos os direitos reservados.";
    //key utilizada nas criptografias
    public static final String KEY = "#H34|2t5t0n3#123";

    public static final String[] NOME_HEROIS_PT = new String[]{"Guerreiro", "Xamã", "Ladino", "Paladino", "Caçador", "Druida", "Bruxo", "Mago", "Sacerdote"};
    public static final String[] NOME_HEROIS_EN = new String[]{Values.GUERREIRO, Values.XAMA, Values.LADINO,
        Values.PALADINO, Values.CACADOR, Values.DRUIDA, Values.BRUXO, Values.MAGO, Values.SACERDOTE};

    public static final String[] PODER_HEROICO_BASICO = new String[]{PALADIN_DEFAULT, ROGUE_DEFAULT, PRIEST_DEFAULT, WARRIOR_DEFAULT,
        WARLOCK_DEFAULT, MAGE_DEFAULT, DRUID_DEFAULT, HUNTER_DEFAULT, SHAMAN_DEFAULT};

    public static final String[] CONJUNTOS_CARTAS = new String[]{Values.TODOS, Values.BASIC, Values.BLACKROCK_MOUNTAIN,
        Values.CLASSIC, Values.CREDITS, Values.CURSE_OF_NAXXRAMAS, Values.DEBUG, Values.GOBLINS_VS_GNOMES, Values.HERO_SKINS,
        Values.LEAGUE_OF_EXPLORERS, Values.MISSIONS, Values.PROMOTION, Values.REWARD, Values.SYSTEM, Values.TAVERN_BRAWL,
        Values.THE_GRAND_TOURNAMENT};
    public static final String TODOS = "Todos";
    public static final String BASIC = "Basic";
    public static final String BLACKROCK_MOUNTAIN = "Blackrock Mountain";
    public static final String CLASSIC = "Classic";
    public static final String CREDITS = "Credits";
    public static final String CURSE_OF_NAXXRAMAS = "Curse Of Naxxramas";
    public static final String DEBUG = "Debug";
    public static final String GOBLINS_VS_GNOMES = "Goblins Vs Gnomes";
    public static final String HERO_SKINS = "Hero Skins";
    public static final String LEAGUE_OF_EXPLORERS = "League Of Explorers";
    public static final String MISSIONS = "Missions";
    public static final String PROMOTION = "Promotion";
    public static final String REWARD = "Reward";
    public static final String SYSTEM = "System";
    public static final String TAVERN_BRAWL = "Tavern Brawl";
    public static final String THE_GRAND_TOURNAMENT = "The Grand Tournament";

    public static final String[] TOTENS_SHAMAN_PODER = new String[]{"NEW1_009", "CS2_050", "CS2_051", "CS2_052"};
    public static final String[] TOTENS_SHAMAN_PODER_MELHORADO = new String[]{"AT_132_SHAMANa", "AT_132_SHAMANb", "AT_132_SHAMANc", "AT_132_SHAMANd"};
    public static final String[] CARDS_DOS_SONHOS = new String[]{"DREAM_01", "DREAM_02", "DREAM_03", "DREAM_04", "DREAM_05"};
    public static final String[] INVENCAO_FABULOSA = new String[]{"Mekka1", "Mekka2", "Mekka3", "Mekka4"};
    public static final String[] PECA_SOBRESSALENTE = new String[]{"PART_001", "PART_002", "PART_003", "PART_004", "PART_005", "PART_006", "PART_007"};
    public static final String[] ARTEFATO_PODEROSO = new String[]{"LOEA16_3", "LOEA16_4", "LOEA16_5"};
    public static final String[] COMPANHEIRO_ANIMAL = new String[]{"NEW1_032", "NEW1_033", "NEW1_034"};
    public static final String[] ACORDE_PODEROSO = new String[]{"PRO_001a", "PRO_001b", "PRO_001c"};
    public static final String[] GUERREIRO_DA_HORDA = new String[]{"EX1_110", "CS2_179", "CS2_121", "EX1_390", "EX1_023", "EX1_021"};

    public static final String MOEDA = "GAME_005";
    public static final String MOEDA_GALLYWIX = "GVG_028t";
    public static final String RECRUTA_PUNHO_DE_PRATA = "CS2_101t";
    public static final String FACA_PERVERSA = "CS2_082";
    public static final String ADAGA_ENVENENADA = "AT_132_ROGUEt";
    public static final String EXCESSO_DE_MANA = "CS2_013t";
    public static final String MISSEL_ARCANO = "EX1_277";
    public static final String EMBOSCADA = "AT_035t";
    public static final String NERUBIANO4_4 = "AT_036t";
    public static final String BROTO1_1 = "AT_037t";
    public static final String URSO3_3PROVOCAR = "CS2_125";
    public static final String TECETEIA1_1 = "FP1_011";
    public static final String KODO_DE_GUERRA3_5 = "AT_099t";
    public static final String ESCUDEIRO2_2 = "CS2_152";
    public static final String DIABRETE1_1 = "BRM_006t";
    public static final String DRAGONETE2_1 = "BRM_022t";
    public static final String OVELHA1_1 = "CS2_tk1";
    public static final String IMAGEM_ESPELHADA0_2 = "CS2_mirror";
    public static final String JAVALI1_1 = "CS2_boar";
    public static final String YSERA = "EX1_572";
    public static final String BANANAS = "EX1_014t";
    public static final String DRAGONETE_MECANICO2_1 = "EX1_025t";
    public static final String DEMOSSAURO5_5 = "EX1_tk29";
    public static final String ESQUILO1_1 = "EX1_tk28";
    public static final String BAINE_CASCO_SANGRENTO4_5 = "EX1_110t";
    public static final String GALINHA1_1 = "Mekka4t";
    public static final String DRAGONETE1_1 = "EX1_116t";
    public static final String DEFENSOR2_1 = "EX1_130a";
    public static final String BANDIDO_DEFIAS2_1 = "EX1_131t";
    public static final String ARVOROSO2_2 = "EX1_158t";
    public static final String PANTERA3_2 = "EX1_160t";
    public static final String SAPO0_1PROVOCAR = "hexfrog";
    public static final String LOBO_ESPIRITUAL2_3PROVOCAR = "EX1_tk11";
    public static final String DIABRETE_INUTIL = "EX1_317t";
    public static final String CREMATORIA5_3 = "EX1_383t";
    public static final String ARMA2_2 = "EX1_398t";
    public static final String ARMA1_3 = "EX1_409t";
    public static final String BATEDOR_MURLOC1_1 = "EX1_506a";
    public static final String HIENA2_2 = "EX1_534t";
    public static final String CAO1_1 = "EX1_538t";
    public static final String COBRA1_1 = "EX1_554t";
    public static final String GOLEM_DANIFICADO2_1 = "skele21";
    public static final String BOLA_DE_FOGO = "CS2_029";
    public static final String ARVOROSO2_2INVESTIDAMORREFIMTURNO = "EX1_tk9";
    public static final String ARVOROSO2_2PROVOCAR = "EX1_573t";
    public static final String JONAS_TIRAGOSTO3_3 = "EX1_finkle";
    public static final String DIABRETE1_1T = "EX1_598";
    public static final String LABAREDA_DE_AZZINOTH2_1 = "EX1_614t";
    public static final String ARANHA_ESPECTRAL1_1 = "FP1_002t";
    public static final String NERUBIANO4_4T2 = "FP1_007t";
    public static final String VISGO2_1 = "FP1_012t";
    public static final String STALAGG = "FP1_014";
    public static final String THADDIUS = "FP1_014t";
    public static final String FEUGEN = "FP1_015";
    public static final String ARVOROSO2_2T2 = "FP1_019t";
    public static final String BARAO_RIVENDARE = "FP1_031";
    public static final String GUARDIA_DA_LUZ = "EX1_001";
    public static final String FOGO_FATUO = "CS2_231";
    public static final String DIABRETE1_1T2 = "GVG_045t";
    public static final String MINA = "GVG_056t";
    public static final String ARMA1_4 = "CS2_091";
    public static final String GALINHA1_1T2 = "GVG_092t";
    public static final String ROBOMBA1_1 = "GVG_110t";
    public static final String V07TR0N = "GVG_111t";
    public static final String GNOMO_LEPROSO = "EX1_029";
    public static final String TROGG_PEDRAQUEIXO_PARRUDO = "GVG_068";
    public static final String TOCHA_VORAZ = "LOE_002t";
    public static final String ESCARAVELHO1_1 = "LOE_009t";
    public static final String MAPA_DO_MACACO_DOURADO = "LOE_019t";
    public static final String MACACO_DOURADO = "LOE_019t2";
    public static final String NANICO2_2T = "LOE_089t";
    public static final String NANICO2_2T2 = "LOE_089t2";
    public static final String NANICO2_2T3 = "LOE_089t3";
    public static final String MUMIA_ZUMBI3_3 = "LOEA16_5t";
    public static final String MALDICAO_ANTIGA = "LOE_110t";
    public static final String APRENDIZ_VIOLETA = "NEW1_026t";
    public static final String GNOLL2_2PROVOCAR = "NEW1_040t";
    public static final String DOBRADORA_DE_FEITICOS = "tt_010a";
    public static final String MURLOC1_1 = "PRO_001at";
    public static final String BRANN_BARBABRONZE = "LOE_077";
    public static final String ARMADURA_ANIMADA = "LOE_119";

    public static final String GERAL = "geral";

    public static final String DRUIDA = "Druid";
    public static final String CACADOR = "Hunter";
    public static final String MAGO = "Mage";
    public static final String PALADINO = "Paladin";
    public static final String SACERDOTE = "Priest";
    public static final String LADINO = "Rogue";
    public static final String XAMA = "Shaman";
    public static final String BRUXO = "Warlock";
    public static final String GUERREIRO = "Warrior";

    public static final String FERA = "Beast";
    public static final String DEMONIO = "Demon";
    public static final String DRAGAO = "Dragon";
    public static final String MECANOIDE = "Mech";
    public static final String MURLOC = "Murloc";
    public static final String PIRATA = "Pirate";
    public static final String TOTEM = "Totem";

    public static final String PODER_HEROICO = "poderHeroico";

    public static final String TO_STRING_PODER_HEROICO = "poderHeroico;";
    public static final String TO_STRING_HEROI = "heroi;";
    public static final String TO_STRING_LACAIO = "lacaio;";
    public static final String TO_STRING_MAO = "mao;";
    public static final String TO_STRING_MESA = "mesa;";
    public static final String TO_STRING_ARMA = "arma;";
    public static final String TO_STRING_FEITICO = "feitiço;";
    public static final String TO_STRING_SEGREDO = "segredo;";

    public static final String LACAIO = "Minion";
    public static final String FEITICO = "Spell";
    public static final String ARMA = "Weapon";

    public static final String COMUM = "Common";
    public static final String RARO = "Rare";
    public static final String EPICO = "Epic";
    public static final String LENDARIO = "Legendary";

    public static final String PROVOCAR = "Taunt";
    public static final String ESCUDO_DIVINO = "Divine Shield";
    public static final String INVESTIDA = "Charge";
    public static final String FURIA_DOS_VENTOS = "Windfury";
    public static final String FURTIVIDADE = "Stealth";
    public static final String ULTIMO_SUSPIRO = "Deathrattle";
    public static final String GRITO_DE_GUERRA = "Battlecry";
    public static final String ENFURECER = "Enrage";
    public static final String VENENO = "Poisonous";
    public static final String SOBRECARGA = "Overload";
    public static final String SEGREDO = "Secret";
    public static final String INSPIRAR = "Inspire";
    public static final String EFEITO_POR_UM_TURNO = "OneTurnEffect";
    public static final String PODER_MAGICO = "Spellpower";
    public static final String POLIMORFIA = "Morph";
    public static final String AURA = "Aura";
    public static final String CONGELAR = "Freeze";
    public static final String IMUNE_PODER_MAGICO = "ImmuneToSpellpower";
    public static final String IMUNE_PODER_MAGICO_TEXT = "Não pode ser alvo de feitiços nem de Poderes Heroicos";
    public static final String BUFF_PARA_ADJACENTES = "AdjacentBuff";
    public static final String AFETADO_PELO_PODER_MAGICO = "AffectedBySpellPower";
    public static final String COMBO = "Combo";
    public static final String SILENCIAR = "Silence";

    public static final String CLONAR_ULTIMO_SUSPIRO = "#cópia#";

    public static final long LEFT = -1;
    public static final long RIGHT = 1;
    
    public static final int PLAYER_VS_COMPUTADOR = 0;
    public static final int PLAYER_VS_PLAYER = 1;

    /*public static final String MSG_AGUARDANDO_ADVERSARIO = "Aguardando Adversário...";
    public static final String MSG_INICIANDO_PROGRAMA = "Iniciando o Programa...";
    public static final String MENSAGEM_CONECTANDO = "Conectando...";
    public static final String MENSAGEM_ATUALIZANDO = "Atualizando...";
    public static final String MENSAGEM_ATUALIZANDO_BD = "Atualizando Banco de Dados... ";
    public static final String MENSAGEM_BAIXANDO_DADOS = "Baixando Dados... ";
    public static final String MENSAGEM_EXTRAINDO = "Extraindo Arquivos... ";
    public static final String MENSAGEM_AGUARDANDO_IA = "Aguarde um instante...";*/

    public static List<String> loadCardsId() {
        try {
            String listaDeCards = RW.readJarS("/com/limagiran/hearthstone/util/listaDeCards.txt");
            if (CARD_ID_HASH.equals(Security.getSHA256(listaDeCards))) {
                return new ArrayList<>(Arrays.asList(listaDeCards.split("\n")));
            } else {
                JOptionPane.showMessageDialog(null, "ARQUIVO CORROMPIDO!\nO Programa será encerrado.");
                System.exit(0);
            }
        } catch (Exception ex) {
        }
        return new ArrayList<>();
    }

    public static List<String> loadAudiosList() {
        try {
            String listaDeCards = RW.readJarS("/com/limagiran/hearthstone/util/listaDeAudios.txt");
            return new ArrayList<>(Arrays.asList(listaDeCards.split("\n")));
        } catch (Exception ex) {
        }
        return new ArrayList<>();
    }
}