package com.limagiran.hearthstoneia.heroi.view;

import com.limagiran.hearthstoneia.heroi.control.Heroi;
import com.limagiran.hearthstoneia.server.GameCliente;
import com.limagiran.hearthstone.util.Param;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author Vinicius Silva
 */
public class View implements Serializable {

    private Heroi hero;
    private ImageIcon imagemHeroi;
    private PanelHeroi panel;
    private boolean ataque;
    private DanoMagico danoMagico;

    public final void loadView(Heroi hero) {
        this.hero = hero;
        initComponents();
        setInvestidaVisible(true);
    }

    private void initComponents() {
        panel = new PanelHeroi(hero);
        danoMagico = new DanoMagico();
    }

    public ImageIcon getHeroiImageIcon() {
        return imagemHeroi;
    }

    public PanelHeroi getPanelHeroi() {
        return panel;
    }

    public DanoMagico getPanelDanoMagico() {
        return danoMagico;
    }

    public void setInvestidaVisible(boolean flag) {
        flag = hero.getAttack() > 0 && flag;
        ataque = flag;
        GameCliente.enviar(hero.getHeroi() != Param.HEROI
                ? Param.HEROI_SET_INVESTIDA_VISIBLE
                : Param.OPONENTE_SET_INVESTIDA_VISIBLE, flag);
    }

    public boolean isInvestidaView() {
        return ataque;
    }
}