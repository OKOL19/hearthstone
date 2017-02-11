package com.limagiran.hearthstone.cardback;

import com.limagiran.hearthstone.util.Img;
import com.limagiran.hearthstone.util.Utils;
import com.limagiran.hearthstone.util.Images;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Vinicius Silva
 */
public class CardBack extends JFrame {

    private final int id;
    private final ImageIcon cardback = new ImageIcon();
    private final ImageIcon icon = new ImageIcon();

    /**
     * Cria um novo objeto de verso de card
     *
     * @param id id do verso de card
     */
    public CardBack(int id) {
        this.id = id;
        cardback.setImage(Images.CARDBACK[Utils.range(id, 1, Images.CARDBACK.length) - 1].getImage());
        config();
    }

    /**
     * Imagem original do verso do card
     *
     * @return ImageIcon do verso do card
     */
    public ImageIcon getCardBackImage() {
        return new ImageIcon(cardback.getImage());
    }

    /**
     * Imagem redimensionada do verso do card
     *
     * @param size nova dimensão da imagem
     * @return ImageIcon do verso do card redimensionado
     */
    public ImageIcon getImageIcon(Dimension size) {
        try {
            return Img.redimensionaImg(cardback.getImage(), size.width, size.height);
        } catch (Exception ex) {
            return new ImageIcon();
        }
    }

    /**
     * Imagem redimensionada e rotacionada do verso do card
     *
     * @param size nova dimensão da imagem
     * @param rotacao graus de rotação da imagem
     * @return ImageIcon do verso do card redimensionado e rotacionado
     */
    public ImageIcon getImageIcon(Dimension size, double rotacao) {
        try {
            return Img.girar(Img.redimensionaImg(cardback.getImage(), size.width, size.height), rotacao);
        } catch (Exception ex) {
            return new ImageIcon();
        }
    }

    /**
     * Configura os componentes do view
     */
    private void config() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new AbsoluteLayout());
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        JLabel l = new JLabel(icon);
        add(l, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0));
    }

    /**
     * Retorna o id do verso do card
     *
     * @return id
     */
    public int getId() {
        return id;
    }
}