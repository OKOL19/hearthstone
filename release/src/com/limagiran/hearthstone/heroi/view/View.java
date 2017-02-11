package com.limagiran.hearthstone.heroi.view;

import com.limagiran.hearthstone.util.Images;
import com.limagiran.hearthstone.util.Param;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import com.limagiran.hearthstone.server.GameCliente;

/**
 *
 * @author Vinicius Silva
 */
public class View implements Serializable {

    private Heroi hero;
    private ImageIcon imagemHeroi;
    private PanelHeroi panel;
    private Mesa mesa;
    private Ataque ataque;
    private Escudo escudo;
    private Vida vida;
    private Segredo segredo;
    private DanoMagico danoMagico;
    private Arma arma;
    private Mana mana;
    private MaoBack maoBack;
    private Mao mao;
    private DeckBack deckBack;

    public final void loadView(Heroi hero) {
        this.hero = hero;
        initComponents();
        atualizarAtributos();
        setInvestidaVisible(true);
        getPanelDeckBack().atualizar();
        getPanelMao().atualizar();
        getPanelMaoBack().atualizar();
    }

    private void initComponents() {
        imagemHeroi = Images.getImageHeroi(hero.getType());
        panel = new PanelHeroi(hero, imagemHeroi);
        mesa = new Mesa(hero);
        ataque = new Ataque(hero);
        escudo = new Escudo(hero);
        vida = new Vida(hero);
        segredo = new Segredo(hero);
        danoMagico = new DanoMagico();
        arma = new Arma();
        mana = new Mana(hero);
        maoBack = new MaoBack(hero);
        mao = new Mao(hero);
        deckBack = new DeckBack(hero);
    }

    public ImageIcon getHeroiImageIcon() {
        return imagemHeroi;
    }

    public void atualizarAtributos() {
        getPanelVida().atualizar();
        getPanelAtaque().atualizar();
        getPanelEscudo().atualizar();
    }

    public PanelHeroi getPanelHeroi() {
        return panel;
    }

    public Mesa getPanelMesa() {
        return mesa;
    }

    public Vida getPanelVida() {
        return vida;
    }

    public Ataque getPanelAtaque() {
        return ataque;
    }

    public Escudo getPanelEscudo() {
        return escudo;
    }

    public Mana getPanelMana() {
        return mana;
    }

    public Mao getPanelMao() {
        return mao;
    }

    public MaoBack getPanelMaoBack() {
        return maoBack;
    }

    public Segredo getPanelSegredo() {
        return segredo;
    }

    public DanoMagico getPanelDanoMagico() {
        return danoMagico;
    }

    public JLabel getLabelMana() {
        return mana.getQuantidade();
    }

    public DeckBack getPanelDeckBack() {
        return deckBack;
    }

    public Arma getPanelArma() {
        return arma;
    }

    public void setAtaqueVisible(boolean flag) {
        getPanelAtaque().setVisible(flag);
        GameCliente.send(hero.getHeroi() != Param.HEROI
                ? Param.HEROI_SET_ATAQUE_VISIBLE
                : Param.OPONENTE_SET_ATAQUE_VISIBLE, flag);
    }

    public void setInvestidaVisible(boolean flag) {
        flag = hero.getAttack() > 0 && flag;
        ataque.setInvestida(flag);
        GameCliente.send(hero.getHeroi() != Param.HEROI
                ? Param.HEROI_SET_INVESTIDA_VISIBLE
                : Param.OPONENTE_SET_INVESTIDA_VISIBLE, flag);
    }

    public boolean isInvestidaView() {
        return ataque.isInvestida();
    }
}