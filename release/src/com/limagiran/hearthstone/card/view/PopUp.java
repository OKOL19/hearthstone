package com.limagiran.hearthstone.card.view;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Vinicius
 */
public class PopUp extends JFrame implements Serializable {

    private final ImageIcon imagem;

    /**
     * Cria um popUp para exibição de um card
     *
     * @param imagem imagem exibida
     */
    public PopUp(ImageIcon imagem) {
        this.imagem = imagem;
        init();
    }

    /**
     * Configurar os componentes da janela
     */
    public final void init() {
        setLayout(new AbsoluteLayout());
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        add(new JLabel(imagem), new AbsoluteConstraints(0, 0));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                dispose();
            }
        });
    }

    /**
     * Alterar o local onde o popUp será exibido
     *
     * @param point ponto na tela. null para centralizar.
     */
    private void location(Point point) {
        if (point == null) {
            setLocationRelativeTo(null);
        } else {
            setLocation(point);
        }
    }

    /**
     * Exibir uma imagem na janela popUp
     *
     * @param image imagem exibida
     * @param point ponto na tela onde o popup será exibido. null para
     * centralizar.
     */
    public void show(ImageIcon image, Point point) {
        imagem.setImage(image.getImage());
        setSize(image.getIconWidth(), image.getIconHeight());
        location(point);
        setVisible(true);
    }
};