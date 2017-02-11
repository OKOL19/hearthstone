package com.limagiran.hearthstone.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Vinicius
 */
public class Images {

    private static int cardsSize = 0;
    private static final List<CardImagem> CARDS = new ArrayList<>();
    public static final ImageIcon[] CARDBACK = new ImageIcon[40];

    public static final ImageIcon BLANK_SMALL = getBlank(DimensionValues.CARD_SMALL);
    public static final ImageIcon BLANK_MEDIUM = getBlank(DimensionValues.CARD_MEDIUM);
    public static final ImageIcon BLANK_ORIGINAL = getBlank(DimensionValues.CARD_ORIGINAL);

    public static final ImageIcon MOUSE_CURSOR_DEFAULT = getImg("partida/img/cursorPadrao.png");
    public static final ImageIcon MOUSE_CURSOR_ALVO = getImg("partida/img/cursorAlvo.png");

    public static final ImageIcon ICON_SETTINGS = new ImageIcon(Images.class.getResource("/com/limagiran/hearthstone/icons/config.png"));
    public static final ImageIcon ICON_WAITING = new ImageIcon(Images.class.getResource("/com/limagiran/hearthstone/partida/img/waiting.gif"));
    public static final ImageIcon ICON_HEALTH = getImg("card/img/vida.png");
    public static final ImageIcon ICON_ATAQUE = getImg("card/img/ataque.png");
    public static final ImageIcon ICON_DANO_HEROI = Img.redimensionaImg(getImg("card/img/dano.png").getImage(), 0.8f);
    public static final ImageIcon ICON_DANO_LACAIO = Img.redimensionaImg(getImg("card/img/dano.png").getImage(), 0.6f);
    public static final ImageIcon CARD_MODEL_MESA_DEFAULT = getImg("card/img/modelDefault.png");
    public static final ImageIcon CARD_MODEL_MESA_PROVOCAR = getImg("card/img/modelProvocar.png");
    public static final ImageIcon CARD_INVESTIDA = new ImageIcon(Images.class.getResource("/com/limagiran/hearthstone/card/img/investida.gif"));
    public static final ImageIcon CARD_FURIA_DOS_VENTOS = new ImageIcon(Images.class.getResource("/com/limagiran/hearthstone/card/img/windfury.gif"));
    public static final ImageIcon CARD_CONGELADO = getImg("card/img/congelado.png");
    public static final ImageIcon CARD_SILENCIADO = getImg("card/img/silenciado.png");
    public static final ImageIcon CARD_NEVOA = getImg("partida/img/nevoa.png");
    public static final ImageIcon CARD_CUSTO_ATAQUE_DURABILIDADE_P = getImg("card/img/atribArmaP.png");
    public static final ImageIcon CARD_CUSTO_ATAQUE_VIDA_P = getImg("card/img/atribLacaioP.png");
    public static final ImageIcon CARD_CUSTO_P = getImg("card/img/atribFeiticoP.png");
    public static final ImageIcon CARD_ICON_INSPIRAR = getImg("card/img/iconInspirar.png");
    public static final ImageIcon CARD_ICON_ULTIMO_SUSPIRO = getImg("card/img/iconUltimoSuspiro.png");
    public static final ImageIcon CARD_ICON_VENENO = getImg("card/img/iconVeneno.png");
    public static final ImageIcon CARD_ICON_GATILHO = getImg("card/img/iconGatilho.png");
    public static final ImageIcon DECK_CUSTO = Img.redimensionaImg(getImg("deck/img/custo.png").getImage(), 30.0 / 34.0);
    public static final ImageIcon DECK_HERO_GUERREIRO = getImg("deck/img/guerreiro.png");
    public static final ImageIcon DECK_HERO_XAMA = getImg("deck/img/xama.png");
    public static final ImageIcon DECK_HERO_LADINO = getImg("deck/img/ladino.png");
    public static final ImageIcon DECK_HERO_PALADINO = getImg("deck/img/paladino.png");
    public static final ImageIcon DECK_HERO_CACADOR = getImg("deck/img/cacador.png");
    public static final ImageIcon DECK_HERO_DRUIDA = getImg("deck/img/druida.png");
    public static final ImageIcon DECK_HERO_BRUXO = getImg("deck/img/bruxo.png");
    public static final ImageIcon DECK_HERO_MAGO = getImg("deck/img/mago.png");
    public static final ImageIcon DECK_HERO_SACERDOTE = getImg("deck/img/sacerdote.png");
    public static final ImageIcon HEROI_ATAQUE = getImg("partida/img/heroiAtaque.png");
    public static final ImageIcon HEROI_INVESTIDA = new ImageIcon(Images.class.getClass().getResource("/com/limagiran/hearthstone/heroi/img/investida.gif"));
    public static final ImageIcon HEROI_ESCUDO = getImg("partida/img/heroiEscudo.png");
    public static final ImageIcon HEROI_SEGREDO = getImg("heroi/img/segredo.png");
    public static final ImageIcon HEROI_CONGELADO = getImg("heroi/img/congelado.png");
    public static final ImageIcon MANA_ON = getImg("partida/img/manaOn.png");
    public static final ImageIcon MANA_OFF = getImg("partida/img/manaOff.png");
    public static final ImageIcon COLECAO_MANA_ON = getImg("colecao/img/manaOn.png");
    public static final ImageIcon COLECAO_MANA_OFF = getImg("colecao/img/manaOff.png");
    public static final ImageIcon COLECAO_BUTTON_LEFT = getImg("colecao/img/left.png");
    public static final ImageIcon COLECAO_BUTTON_RIGHT = getImg("colecao/img/right.png");
    public static final ImageIcon COLECAO_FOLHA = getImg("colecao/img/folha.png");
    public static final ImageIcon COLECAO_TEXTURA = getImg("colecao/img/textura.png");
    public static final ImageIcon MULIGAR_X = getImg("partida/img/muligarX.png");
    public static final ImageIcon ARENA_TEXTURA = getImg("partida/img/arenaTextura.png");
    public static final ImageIcon ARENA_OPONENTE = getImg("partida/img/painelOponente.png");
    public static final ImageIcon ARENA_HEROI = getImg("partida/img/painelHeroi.png");
    public static final ImageIcon ARENA_BARRA_MANA = getImg("partida/img/barraManaHeroi.png");
    public static final ImageIcon ARENA_BARRA_MANA_OPONENTE = getImg("partida/img/barraManaOponente.png");

    public static final ImageIcon ARMA_MODEL_ON = getImg("partida/img/heroiArmaAberta.png");
    public static final ImageIcon ARMA_MODEL_OFF = getImg("partida/img/heroiArmaFechada.png");
    public static final ImageIcon PODER_MODEL_ON = getImg("partida/img/poderHeroicoAberto.png");
    public static final ImageIcon PODER_MODEL_OFF = getImg("partida/img/poderHeroicoFechado.png");
    public static final ImageIcon PODER_PALADIN_DEFAULT = getImg("poder/img/paladin.png");
    public static final ImageIcon PODER_ROGUE_DEFAULT = getImg("poder/img/rogue.png");
    public static final ImageIcon PODER_PRIEST_DEFAULT = getImg("poder/img/priest.png");
    public static final ImageIcon PODER_WARRIOR_DEFAULT = getImg("poder/img/warrior.png");
    public static final ImageIcon PODER_WARLOCK_DEFAULT = getImg("poder/img/warlock.png");
    public static final ImageIcon PODER_MAGE_DEFAULT = getImg("poder/img/mage.png");
    public static final ImageIcon PODER_DRUID_DEFAULT = getImg("poder/img/druid.png");
    public static final ImageIcon PODER_HUNTER_DEFAULT = getImg("poder/img/hunter.png");
    public static final ImageIcon PODER_SHAMAN_DEFAULT = getImg("poder/img/xama.png");
    public static final ImageIcon PODER_PALADIN_MELHORADO = getImg("poder/img/paladinMelhorado.png");
    public static final ImageIcon PODER_ROGUE_MELHORADO = getImg("poder/img/rogueMelhorado.png");
    public static final ImageIcon PODER_PRIEST_MELHORADO = getImg("poder/img/priestMelhorado.png");
    public static final ImageIcon PODER_WARRIOR_MELHORADO = getImg("poder/img/warriorMelhorado.png");
    public static final ImageIcon PODER_WARLOCK_MELHORADO = getImg("poder/img/warlockMelhorado.png");
    public static final ImageIcon PODER_MAGE_MELHORADO = getImg("poder/img/mageMelhorado.png");
    public static final ImageIcon PODER_DRUID_MELHORADO = getImg("poder/img/druidMelhorado.png");
    public static final ImageIcon PODER_HUNTER_MELHORADO = getImg("poder/img/hunterMelhorado.png");
    public static final ImageIcon PODER_SHAMAN_MELHORADO = getImg("poder/img/xamaMelhorado.png");
    public static final ImageIcon PODER_SHAMAN_AT_050T = getImg("poder/img/at_050t.png");
    public static final ImageIcon PODER_PRIEST_EX1_625T = getImg("poder/img/ex1_625t.png");
    public static final ImageIcon PODER_PRIEST_EX1_625T2 = getImg("poder/img/ex1_625t2.png");
    public static final ImageIcon PALADIN = getImg("heroi/img/paladino.png");
    public static final ImageIcon ROGUE = getImg("heroi/img/ladino.png");
    public static final ImageIcon PRIEST = getImg("heroi/img/sacerdote.png");
    public static final ImageIcon WARRIOR = getImg("heroi/img/guerreiro.png");
    public static final ImageIcon WARLOCK = getImg("heroi/img/bruxo.png");
    public static final ImageIcon MAGE = getImg("heroi/img/mago.png");
    public static final ImageIcon DRUID = getImg("heroi/img/druida.png");
    public static final ImageIcon HUNTER = getImg("heroi/img/cacador.png");
    public static final ImageIcon SHAMAN = getImg("heroi/img/xama.png");

    public static final int CARD_SMALL = 0;
    public static final int CARD_MEDIUM = 1;
    public static final int CARD_ORIGINAL = 2;

    
    /**
     * Retorna a imagem do card
     *
     * @param size tamanho da imagem
     * @param id id do card
     * @return ImagemIcon do card
     */
    public static final ImageIcon getCardIcon(int size, String id) {
        if ((id == null) || id.equals("null")) {
            return ((size == CARD_SMALL) ? BLANK_SMALL : BLANK_MEDIUM);
        }
        switch (size) {
            case CARD_SMALL:
                return findCardImage(id).SMALL;
            case CARD_ORIGINAL:
                return new ImageIcon("bin/o/" + id + ".hsi");
            default:
                return findCardImage(id).MEDIUM;
        }
    }

    /**
     * Retorna a imagem do herói
     *
     * @param type classe do herói
     * @return ImageIcon com a imagem do herói
     */
    public static final ImageIcon getImageHeroi(String type) {
        switch (type) {
            case Values.BRUXO:
                return WARLOCK;
            case Values.CACADOR:
                return HUNTER;
            case Values.DRUIDA:
                return DRUID;
            case Values.GUERREIRO:
                return WARRIOR;
            case Values.LADINO:
                return ROGUE;
            case Values.MAGO:
                return MAGE;
            case Values.PALADINO:
                return PALADIN;
            case Values.SACERDOTE:
                return PRIEST;
            case Values.XAMA:
                return SHAMAN;
        }
        return getBlank(new Dimension(MAGE.getIconWidth(), MAGE.getIconHeight()));
    }

    public static ImageIcon getImg(String file) {
        try {
            return new ImageIcon(Images.class.getResource("/com/limagiran/hearthstone/" + file));
        } catch (Exception ex) {
            return BLANK_SMALL;
        }
    }

    /**
     * Retorna a imagem do verso do card
     *
     * @param file caminho do arquivo na base de dados
     * @return ImageIcon com a imagem
     */
    private static ImageIcon getCardBack(int id) {
        try {
            return new ImageIcon(ImageIO.read(new File("bin/b/cardback_" + id + ".hsi")));
        } catch (Exception ex) {
            return BLANK_MEDIUM;
        }
    }

    /**
     * Carrega todas as imagens dos versos de card disponíveis
     */
    public static void loadCardBack() {
        for (int id = 1; id <= 40; id++) {
            CARDBACK[id - 1] = getCardBack(id);
        }
    }

    /**
     * Procura um CardImagem pelo id do card, caso ele não tenha sido carregado
     * ainda, realiza o carregamento da imagem
     *
     * @param id id da imagem
     * @return CardImagem com as imagens Small, Medium e Original do Card
     */
    private static CardImagem findCardImage(String id) {
        for (CardImagem c : CARDS) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        CardImagem cardImagem = new CardImagem(id);
        if (CARDS.size() <= 100) {
            CARDS.add(cardImagem);
        } else {
            CARDS.set(cardsSize % 100, cardImagem);
            cardsSize++;
        }
        return cardImagem;
    }

    private static ImageIcon getBlank(Dimension d) {
        BufferedImage bi = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setBackground(new Color(0, 0, 0, 0));
        g2.dispose();
        return new ImageIcon(bi);
    }

}

final class CardImagem {

    public String id;
    public ImageIcon SMALL;
    public ImageIcon MEDIUM;

    public CardImagem(String id) {
        this.id = id;
        SMALL = getImageIcon('s');
        MEDIUM = getImageIcon('m');
    }

    public ImageIcon getImageIcon(char size) {
        try {
            return new ImageIcon("bin/" + size + "/" + id + ".hsi");
        } catch (Exception e) {
            return new ImageIcon();
        }
    }

    public String getId() {
        return id;
    }
}