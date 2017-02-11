package com.limagiran.hearthstone.card.view;

import com.limagiran.hearthstone.card.control.Card;
import com.google.gson.annotations.Expose;
import com.limagiran.hearthstone.util.Images;
import com.limagiran.hearthstone.util.Param;
import java.awt.Point;
import java.io.Serializable;
import javax.swing.ImageIcon;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class View implements Serializable {

    private Card card;

    @Expose(serialize = false, deserialize = false)
    private PopUp popUp;
    @Expose(serialize = false, deserialize = false)
    public Mao viewCardMao;
    @Expose(serialize = false, deserialize = false)
    public Mesa viewCardMesa;

    @Expose(serialize = false, deserialize = false)
    private ImageIcon ImagemSmall;
    @Expose(serialize = false, deserialize = false)
    private ImageIcon ImagemMedium;

    /**
     * Configura os componentes do View do card
     *
     * @param card card que herdou View
     */
    public final void init(Card card) {
        this.card = card;
        carregarImagens();
        popUp = new PopUp(ImagemMedium);
        viewCardMao = new Mao(this.card);
        viewCardMesa = new Mesa(this.card);
        atualizarAtributos();
    }

    /**
     * ALtera a imagem modelo da arma para "aberto" ou "fechado"
     *
     * @param flag {@code true} para abrir ou {@code false} para fechar
     */
    public void setArmaAtivada(boolean flag) {
        viewCardMesa.setArmaAtivada(flag);
    }
    
    /**
     * Exibir o card numa janela popUp
     *
     * @param point ponto na tela onde o popup será exibido. null para
     * centralizar.
     */
    public void showPopUpSmall(Point p) {
        popUp.show(ImagemSmall, p);
    }

    /**
     * Exibir o card numa janela popUp
     *
     * @param point ponto na tela onde o popup será exibido. null para
     * centralizar.
     */
    public void showPopUpMedium(Point p) {
        popUp.show(ImagemMedium, p);
    }

    /**
     * Ocultar a janela popUp
     */
    public void disposePopUp() {
        if (popUp != null) {
            popUp.dispose();
        }
    }

    /**
     * Exibir o view responsável pela mecânica adicionada
     *
     * @param mechanic mecânica adicionada
     */
    public void addMecanicaLabel(String mechanic) {
        if (!card.isSilenciado()) {
            if (mechanic.equals(Values.ESCUDO_DIVINO)) {
                viewCardMesa.setEscudoDivino(true);
            }
            if (mechanic.equals(Values.FURTIVIDADE)) {
                viewCardMesa.setFurtividade(true);
            }
            if (mechanic.equals(Values.FURIA_DOS_VENTOS)) {
                viewCardMesa.setFuriaDosVentos(true);
            }
            if (mechanic.equals(Values.INVESTIDA)) {
                card.setAtaquesRealizados(card.getAtaquesRealizados());
            }
            if (mechanic.equals(Values.PROVOCAR)) {
                viewCardMesa.setProvocar(true);
            }
            atualizarMecanicas();
        }
    }

    /**
     * Ocultar o view responsável pela mecânica removida
     *
     * @param mechanic mecânica removida
     */
    protected void delMecanicaLabel(String mechanic) {
        if (mechanic.equals(Values.ESCUDO_DIVINO) && !card.getMechanics().contains(Values.ESCUDO_DIVINO)) {
            viewCardMesa.setEscudoDivino(false);
        }
        if (mechanic.equals(Values.FURTIVIDADE) && !card.getMechanics().contains(Values.FURTIVIDADE)) {
            viewCardMesa.setFurtividade(false);
        }
        if (mechanic.equals(Values.FURIA_DOS_VENTOS) && !card.getMechanics().contains(Values.FURIA_DOS_VENTOS)) {
            viewCardMesa.setFuriaDosVentos(false);
        }
        if (mechanic.equals(Values.INVESTIDA) && !card.getMechanics().contains(Values.INVESTIDA)) {
            card.setAtaquesRealizados(card.getAtaquesRealizados());
        }
        if (mechanic.equals(Values.PROVOCAR) && !card.getMechanics().contains(Values.PROVOCAR)) {
            viewCardMesa.setProvocar(false);
        }
        if (mechanic.equals(Values.ENFURECER) && !card.getMechanics().contains(Values.ENFURECER)) {
            //enfurecer.setVisible(false);
        }
        atualizarMecanicas();
    }

    /**
     * Exibir/Ocultar a view de fúria dos ventos
     *
     * @param flag {@code true} ou {@code false}
     */
    public void setFuriaDosVentosVisible(boolean flag) {
        if (flag || !card.getMechanics().contains(Values.FURIA_DOS_VENTOS)) {
            viewCardMesa.setFuriaDosVentos(flag);
            card.enviar(Param.CARD_SET_FURIA_DOS_VENTOS_VISIBLE, flag);
        }
    }

    /**
     * Atualizar as views do card
     */
    public void atualizarAtributos() {
        viewCardMao.atualizar();
        viewCardMesa.atualizar();
    }

    /**
     * Exibir/Ocultar a view da investida
     *
     * @param flag {@code true} ou {@code false}
     */
    public void setInvestidaVisible(boolean flag) {
        viewCardMesa.setInvestida(flag);
    }

    /**
     * Atualizar as views das mecânicas do card
     */
    public void atualizarMecanicas() {
        viewCardMesa.repaint();
        viewCardMesa.revalidate();
    }

    /**
     * Carrega as imagens do card
     */
    private void carregarImagens() {
        ImagemSmall = Images.BLANK_SMALL;
        ImagemMedium = Images.BLANK_MEDIUM;
        ImagemSmall = Images.getCardIcon(Images.CARD_SMALL, card.getId());
        ImagemMedium = Images.getCardIcon(Images.CARD_MEDIUM, card.getId());
    }

    /**
     * Retorna a imagem pequena do card
     *
     * @return ImageIcon do card
     */
    public ImageIcon getImagemSmall() {
        return new ImageIcon(ImagemSmall.getImage());
    }

    /**
     * Retorna a imagem média do card
     *
     * @return ImageIcon do card
     */
    public ImageIcon getImagemMedium() {
        return new ImageIcon(ImagemMedium.getImage());
    }

    /**
     * Remover todos os ponteiros para liberar memória
     */
    public void finalizar() {
        try {
            popUp.removeAll();
            viewCardMao.removeAll();
            viewCardMesa.removeAll();
        } catch (Exception e) {
        }
    }
}