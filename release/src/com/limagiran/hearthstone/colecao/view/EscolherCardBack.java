package com.limagiran.hearthstone.colecao.view;

import com.limagiran.hearthstone.util.Img;
import static com.limagiran.hearthstone.HearthStone.CARDBACK;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

/**
 *
 * @author Vinicius Silva
 */
public class EscolherCardBack extends javax.swing.JDialog {

    private static EscolherCardBack instance;
    private int selected = -1;
    private final ButtonGroup buttonGroup = new ButtonGroup();

    /**
     * Creates new form CardBackSelect
     *
     * @param selected botão pré-selecionado.
     */
    private EscolherCardBack(int selected) {
        initComponents();
        preencher(selected);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelCards = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Selecione o verso do card");
        setModal(true);
        setResizable(false);

        jPanelCards.setPreferredSize(new java.awt.Dimension(1250, 600));
        jPanelCards.setLayout(new java.awt.GridLayout(4, 10));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelCards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelCards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Escolher verso de card
     *
     * @param selected id do verso pré-selecionado
     * @return id do verso selecionado
     */
    public static int main(int selected) {
        instance = null;
        instance = new EscolherCardBack(selected);
        instance.setVisible(true);
        return instance.getSelected();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelCards;
    // End of variables declaration//GEN-END:variables

    /**
     * Preencher a janela para escolher o verso do card
     *
     * @param selected verso pré-selecionado. -1 para nenhum verso
     * pré-selecionado.
     */
    private void preencher(int selected) {
        for (int i = 0; i < CARDBACK.size(); i++) {
            ImageIcon cardBack = Img.recortar(CARDBACK.get(i).getCardBackImage(), 20, 45, 164, 240);
            cardBack = Img.redimensionaImg(cardBack.getImage(), 0.55);
            JToggleButton button = new JToggleButton(cardBack);
            button.addActionListener(getAction(i));
            button.setBorder(null);
            button.setBackground(new Color(0, 0, 0, 0));
            button.setOpaque(false);
            button.setFocusPainted(false);
            buttonGroup.add(button);
            if (i == selected) {
                button.setBorder(new LineBorder(Color.RED, 2));
                button.setSelected(true);
            }
            jPanelCards.add(button);
        }
    }

    /**
     * Configurar o evento ao clicar no verso de card
     *
     * @param i index do verso do card
     * @return ActionListener configurado
     */
    private ActionListener getAction(int i) {
        return (ActionEvent e) -> {
            sair(i);
        };
    }

    /**
     * Ocultar a janela atual
     *
     * @param selected verso selecionado
     */
    private void sair(int selected) {
        this.selected = selected;
        dispose();
    }

    /**
     * Verso de card selecionado
     *
     * @return id do verso selecionado
     */
    public int getSelected() {
        return selected;
    }
}