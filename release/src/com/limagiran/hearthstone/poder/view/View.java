package com.limagiran.hearthstone.poder.view;

import com.limagiran.hearthstone.util.Img;
import com.limagiran.hearthstone.util.AbsolutesConstraints;
import com.limagiran.hearthstone.util.DimensionValues;
import com.limagiran.hearthstone.util.Images;
import static com.limagiran.hearthstone.util.Images.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import com.limagiran.hearthstone.poder.control.PoderHeroico;
import com.limagiran.hearthstone.util.Audios;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius
 */
public class View implements Serializable {

    private final JPanel panel = new JPanel(new AbsoluteLayout());
    private PoderHeroico poder;
    private JLabel areaClicavel;
    private Custo custo;
    private JLabel model;
    private JLabel imagem;
    private PopUp popUp;

    public void loadView(PoderHeroico poder) {
        this.poder = poder;
        init();
    }

    private void init() {
        panel.setOpaque(false);
        popUp = new PopUp();
        areaClicavel = new JLabel() {
            @Override
            public String toString() {
                return Values.TO_STRING_PODER_HEROICO + poder.getHeroi().getHeroi();
            }
        };
        custo = new Custo(poder);
        imagem = new JLabel();
        model = new Model();
        panel.add(areaClicavel, AbsolutesConstraints.PODER_HEROICO_AREA_CLICAVEL);
        panel.add(custo, new AbsoluteConstraints(42, 61, 36, 40));
        panel.add(model, AbsolutesConstraints.ZERO);
        panel.add(imagem, new AbsoluteConstraints(25, 85, 87, 88));
        setAtivado(false);
    }

    public JPanel getPanelPoderHeroicoView() {
        return panel;
    }

    public void setAtivado(boolean flag) {
        if (!flag && panel.isShowing()) {
            Audios.playEfeitos(Audios.PARTIDA_PODER_HEROICO_FECHADO);
        }
        model.setIcon(flag ? Images.PODER_MODEL_ON : Images.PODER_MODEL_OFF);
        custo.setVisible(flag);
    }

    public boolean isAtivado() {
        return custo.isVisible();
    }

    public void setIcon(ImageIcon icon) {
        imagem.setIcon(icon);
    }

    public PoderHeroico getPoder() {
        return poder;
    }

    public void showPopUpSmall(Point p) {
        popUp.show(getCardIcon(CARD_SMALL, poder.getTipo()), p);
    }

    public void showPopUpMedium(Point p) {
        popUp.show(getCardIcon(CARD_MEDIUM, poder.getTipo()), p);
    }

    public void showPopUp(Dimension size, Point p) {
        popUp.show(Img.redimensionaImg(getCardIcon(CARD_MEDIUM, poder.getTipo()).getImage(), size.width, size.height), p);
    }

    public void disposePopUp() {
        popUp.dispose();
    }

    @Override
    public String toString() {
        return "null";
    }
}

class Model extends JLabel {

    public Model() {
        init();
    }

    private void init() {
        setBackground(new Color(0, 0, 0, 0));
        setLayout(new AbsoluteLayout());
        setPreferredSize(DimensionValues.ARMA_PODER);
        setSize(DimensionValues.ARMA_PODER);
    }
}