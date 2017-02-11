package com.limagiran.hearthstone.card.view;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.card.util.Util;
import com.limagiran.hearthstone.util.Img;
import com.limagiran.hearthstone.util.Utils;
import com.limagiran.hearthstone.util.AbsolutesConstraints;
import com.limagiran.hearthstone.util.DimensionValues;
import com.limagiran.hearthstone.util.Images;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import com.limagiran.hearthstone.util.Audios;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius
 */
public class Mesa extends JPanel {

    private final Card card;

    private JLabelAtributos ataque;
    private JLabelAtributos vida;
    private JLabelAtributos durabilidade;

    private Animacao animacao;

    private JLabel modelMesa;
    private JLabel provocar;
    private JLabel mecanica;
    private Furtividade furtividade;
    private EscudoDivino escudoDivino;
    private JLabel investida;
    private JLabel furiaDosVentos;
    private JLabel congelado;
    private Enfurecer enfurecer;
    private JLabel silenciado;

    private JLabel armaModel;

    public Mesa(Card card) {
        super(new AbsoluteLayout());
        this.card = card;
        init();
    }

    private void init() {
        switch (card.getType()) {
            case Values.LACAIO:
                initMinion();
                break;
            case Values.ARMA:
                initWeapon();
                break;
        }
    }

    private void initMinion() {
        ataque = new JLabelAtributos(card, JLabelAtributos.ATAQUE);
        vida = new JLabelAtributos(card, JLabelAtributos.VIDA);
        animacao = new Animacao(card);
        mecanica = Util.jLabelDefault(card, getImagemMecanica());
        modelMesa = Util.jLabelDefault(card, Images.CARD_MODEL_MESA_DEFAULT);
        escudoDivino = new EscudoDivino(card);
        escudoDivino.setVisible(false);
        furtividade = new Furtividade(card);
        furtividade.setVisible(false);
        furiaDosVentos = Util.jLabelDefault(card, Images.CARD_FURIA_DOS_VENTOS);
        furiaDosVentos.setVisible(false);
        investida = Util.jLabelDefault(card, Images.CARD_INVESTIDA);
        investida.setVisible(false);
        provocar = Util.jLabelDefault(card, Images.CARD_MODEL_MESA_PROVOCAR);
        provocar.setVisible(false);
        congelado = Util.jLabelDefault(card, Images.CARD_CONGELADO);
        congelado.setVisible(false);
        enfurecer = new Enfurecer(card);
        enfurecer.setVisible(false);
        silenciado = Util.jLabelDefault(card, Img.redimensionaImg(Images.CARD_SILENCIADO.getImage(), 100, 100));
        silenciado.setVisible(false);
        setSize(DimensionValues.MESA);
        setPreferredSize(DimensionValues.MESA);
        setBackground(new Color(0, 0, 0, 0));
        add(animacao, AbsolutesConstraints.MESA_LACAIO_LABEL_ANIMACAO);
        add(mecanica, AbsolutesConstraints.MESA_LACAIO_LABEL_MECANICA);
        add(ataque, AbsolutesConstraints.MESA_LACAIO_LABEL_ATAQUE);
        add(vida, AbsolutesConstraints.MESA_LACAIO_LABEL_VIDA);
        add(escudoDivino, AbsolutesConstraints.ZERO);
        add(provocar, AbsolutesConstraints.ZERO);
        add(modelMesa, AbsolutesConstraints.ZERO);
        add(silenciado, AbsolutesConstraints.ZERO);
        add(congelado, AbsolutesConstraints.CONGELADO_LABEL);
        add(furtividade, AbsolutesConstraints.ZERO);
        add(furiaDosVentos, AbsolutesConstraints.ZERO);
        add(enfurecer, AbsolutesConstraints.ZERO);
        add(getImagemLacaio(), new AbsoluteConstraints(15, 7));
        add(investida, AbsolutesConstraints.ZERO);
        initMecanicas();
    }

    private ImageIcon getImagemMecanica() {
        if (card.getMechanics().contains(Values.INSPIRAR)) {
            return Images.CARD_ICON_INSPIRAR;
        } else if (card.getMechanics().contains(Values.ULTIMO_SUSPIRO)) {
            return Images.CARD_ICON_ULTIMO_SUSPIRO;
        } else if (card.getMechanics().contains(Values.VENENO)) {
            return Images.CARD_ICON_VENENO;
        } else if (card.getText() != null && !card.getId().equals("AT_041")
                && (card.getText().contains("No início") || card.getText().contains("No final")
                || card.getText().contains("Sempre que") || card.getText().contains("Depois de"))) {
            return Images.CARD_ICON_GATILHO;
        }
        return new ImageIcon();
    }
    
    /**
     * Exibe/Oculta o ícone da mecânica do lacaio
     * @param flag {@code true} ou {@code false} 
     */
    public void setMecanicaIcon(boolean flag) {
        mecanica.setVisible(flag);
    }

    private void initWeapon() {
        armaModel = new JLabel(Images.ARMA_MODEL_ON);
        ataque = new JLabelAtributos(card, JLabelAtributos.ATAQUE);
        durabilidade = new JLabelAtributos(card, JLabelAtributos.DURABILIDADE);
        JLabel armaLabel = new JLabel(Img.getCirculo(card.getImagemMedium(), 55, 55, 90, 90)) {
            @Override
            public String toString() {
                return card.getToString();
            }
        };
        setBackground(new Color(0, 0, 0, 0));
        add(ataque, AbsolutesConstraints.MESA_WEAPON_LABEL_ATAQUE);
        add(durabilidade, AbsolutesConstraints.MESA_WEAPON_LABEL_DURABILIDADE);
        add(armaModel, AbsolutesConstraints.ZERO);
        add(armaLabel, AbsolutesConstraints.MESA_WEAPON_LABEL_ARMA);
        setSize(DimensionValues.ARMA_PODER);
        setPreferredSize(DimensionValues.ARMA_PODER);
        initMecanicas();
    }

    public void atualizar() {
        if (ataque != null) {
            ataque.repaint();
        }
        if (vida != null) {
            vida.repaint();
        }
        if (durabilidade != null) {
            durabilidade.repaint();
        }
    }

    private void initMecanicas() {
        if (card.isLacaio()) {
            investida.setVisible(card.getMechanics().contains(Values.INVESTIDA));
            escudoDivino.setVisible(card.isEscudoDivino());
            furtividade.setVisible(card.isFurtivo());
            furiaDosVentos.setVisible(card.isFuriaDosVentos());
            provocar.setVisible(card.isProvocar());
        }
    }

    private JLabel getImagemLacaio() {
        ImageIcon edit;
        try {
            edit = Images.getCardIcon(Images.CARD_ORIGINAL, card.getId());
            if (edit != null) {
                edit = Img.redimensionaImg(edit.getImage(), (0.78));
                edit = Img.getCirculo(edit, 55, 50, 112, 148);
                return new JLabel(edit) {
                    @Override
                    public String toString() {
                        return card.getToString();
                    }
                };
            }
        } catch (Exception e) {
            edit = null;
        }
        return new JLabel();
    }

    public Animacao getAnimacao() {
        return animacao;
    }

    /**
     * ALtera a imagem modelo da arma para "aberto" ou "fechado"
     *
     * @param flag {@code true} para abrir ou {@code false} para fechar
     */
    public void setArmaAtivada(boolean flag) {
        if((armaModel.getIcon().equals(Images.ARMA_MODEL_ON) && !flag)
                || (armaModel.getIcon().equals(Images.ARMA_MODEL_OFF) && flag)) {
            Audios.playEfeitos(flag ? Audios.PARTIDA_ARMA_ATIVAR : Audios.PARTIDA_ARMA_DESATIVAR);
            Utils.sleep(300);
        }
        armaModel.setIcon(flag ? Images.ARMA_MODEL_ON : Images.ARMA_MODEL_OFF);
    }
    
    public void setEscudoDivino(boolean flag) {
        escudoDivino.setVisible(flag);
    }

    public void setFurtividade(boolean flag) {
        furtividade.setVisible(flag);
    }

    public void setFuriaDosVentos(boolean flag) {
        furiaDosVentos.setVisible(flag);
    }

    public void setProvocar(boolean flag) {
        provocar.setVisible(flag);
    }

    public void setInvestida(boolean flag) {
        investida.setVisible(flag);
    }

    public boolean isInvestida() {
        return investida.isVisible();
    }

    public void setEnfurecer(boolean flag) {
        enfurecer.setVisible(flag);
    }

    public void setCongelado(boolean flag) {
        congelado.setVisible(flag);
    }

    public void setSilenciado(boolean flag) {
        silenciado.setVisible(flag);
    }

    @Override
    public String toString() {
        return card.getToString();
    }
}