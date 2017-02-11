package com.limagiran.hearthstone.server;

import com.limagiran.hearthstone.util.GamePlay;
import com.limagiran.hearthstone.util.Utils;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import org.netbeans.lib.awtextra.*;

/**
 *
 * @author Vinicius
 */
public class ServerPanel extends JFrame {

    private static final ServerPanel INSTANCE = new ServerPanel();

    /**
     * Creates new form ServerPanel
     */
    private ServerPanel() {
        initComponents();
        init();
    }

    private void init() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                jLabelIP.setText("IP: " + Server.getIP());
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelPanel = new JPanel();
        jButtonClose = new JButton();
        jButtonRestart = new JButton();
        jButtonOpen = new JButton();
        jPanel1 = new JPanel();
        jLabelConfiguracoes = new JLabel();
        jLabelVidaInicialHeroi = new JLabel();
        jSpinnerVidaInicialHeroi = new JSpinner();
        jLabelCartasNaMao = new JLabel();
        jSpinnerCartasNaMao = new JSpinner();
        jLabelShieldInicial = new JLabel();
        jSpinnerShieldIinicial = new JSpinner();
        jLabelPorta = new JLabel();
        jTextFieldPorta = new JTextField();
        jLabelIP = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Servidor");
        getContentPane().setLayout(new AbsoluteLayout());

        jPanelPanel.setLayout(new AbsoluteLayout());

        jButtonClose.setText("Close");
        jButtonClose.setEnabled(false);
        jButtonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });
        jPanelPanel.add(jButtonClose, new AbsoluteConstraints(280, 300, 98, -1));

        jButtonRestart.setText("Restart");
        jButtonRestart.setEnabled(false);
        jButtonRestart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonRestartActionPerformed(evt);
            }
        });
        jPanelPanel.add(jButtonRestart, new AbsoluteConstraints(150, 300, 100, -1));

        jButtonOpen.setText("Open");
        jButtonOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonOpenActionPerformed(evt);
            }
        });
        jPanelPanel.add(jButtonOpen, new AbsoluteConstraints(20, 300, 100, -1));

        jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        jPanel1.setLayout(new AbsoluteLayout());

        jLabelConfiguracoes.setFont(new Font("Tahoma", 0, 24)); // NOI18N
        jLabelConfiguracoes.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelConfiguracoes.setText("Configurações");
        jPanel1.add(jLabelConfiguracoes, new AbsoluteConstraints(10, 0, 340, 40));

        jLabelVidaInicialHeroi.setFont(new Font("Tahoma", 0, 18)); // NOI18N
        jLabelVidaInicialHeroi.setText("Vida inicial do herói:");
        jPanel1.add(jLabelVidaInicialHeroi, new AbsoluteConstraints(10, 50, 270, 30));

        jSpinnerVidaInicialHeroi.setFont(new Font("Tahoma", 0, 18)); // NOI18N
        jSpinnerVidaInicialHeroi.setModel(new SpinnerNumberModel(30, 10, 100, 5));
        jPanel1.add(jSpinnerVidaInicialHeroi, new AbsoluteConstraints(290, 50, 60, 30));

        jLabelCartasNaMao.setFont(new Font("Tahoma", 0, 18)); // NOI18N
        jLabelCartasNaMao.setText("Quantidade de cartas na mão:");
        jPanel1.add(jLabelCartasNaMao, new AbsoluteConstraints(10, 80, 270, 30));

        jSpinnerCartasNaMao.setFont(new Font("Tahoma", 0, 18)); // NOI18N
        jSpinnerCartasNaMao.setModel(new SpinnerNumberModel(10, 1, 30, 1));
        jPanel1.add(jSpinnerCartasNaMao, new AbsoluteConstraints(290, 80, 60, 30));

        jLabelShieldInicial.setFont(new Font("Tahoma", 0, 18)); // NOI18N
        jLabelShieldInicial.setText("Quantidade de escudo inicial:");
        jPanel1.add(jLabelShieldInicial, new AbsoluteConstraints(10, 110, 270, 30));

        jSpinnerShieldIinicial.setFont(new Font("Tahoma", 0, 18)); // NOI18N
        jSpinnerShieldIinicial.setModel(new SpinnerNumberModel(0, 0, 100, 5));
        jPanel1.add(jSpinnerShieldIinicial, new AbsoluteConstraints(290, 110, 60, 30));

        jPanelPanel.add(jPanel1, new AbsoluteConstraints(20, 30, 360, 160));

        jLabelPorta.setFont(new Font("Tahoma", 0, 18)); // NOI18N
        jLabelPorta.setText("Porta:");
        jPanelPanel.add(jLabelPorta, new AbsoluteConstraints(140, 250, 50, 20));

        jTextFieldPorta.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldPorta.setHorizontalAlignment(JTextField.CENTER);
        jTextFieldPorta.setText("12491");
        jPanelPanel.add(jTextFieldPorta, new AbsoluteConstraints(190, 250, 70, -1));

        jLabelIP.setFont(new Font("Tahoma", 0, 18)); // NOI18N
        jLabelIP.setHorizontalAlignment(SwingConstants.CENTER);
        jPanelPanel.add(jLabelIP, new AbsoluteConstraints(20, 200, 360, 30));

        getContentPane().add(jPanelPanel, new AbsoluteConstraints(0, 0, 400, 330));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRestartActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonRestartActionPerformed
        restart();
    }//GEN-LAST:event_jButtonRestartActionPerformed

    private void jButtonCloseActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        close();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonOpenActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonOpenActionPerformed
        open();
    }//GEN-LAST:event_jButtonOpenActionPerformed

    public static void main() {
        SwingUtilities.invokeLater(() -> INSTANCE.setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButtonClose;
    private JButton jButtonOpen;
    private JButton jButtonRestart;
    private JLabel jLabelCartasNaMao;
    private JLabel jLabelConfiguracoes;
    private JLabel jLabelIP;
    private JLabel jLabelPorta;
    private JLabel jLabelShieldInicial;
    private JLabel jLabelVidaInicialHeroi;
    private JPanel jPanel1;
    private JPanel jPanelPanel;
    private JSpinner jSpinnerCartasNaMao;
    private JSpinner jSpinnerShieldIinicial;
    private JSpinner jSpinnerVidaInicialHeroi;
    private JTextField jTextFieldPorta;
    // End of variables declaration//GEN-END:variables

    private void open() {
        try {
            loadConfiguracoes();
            int port = Integer.parseInt(jTextFieldPorta.getText());
            if ((port > 1024) && port <= 65536) {
                if (!Server.isOpen()) {
                    jButtonOpen.setEnabled(false);
                    jButtonRestart.setEnabled(true);
                    jButtonClose.setEnabled(true);
                    setEnabledConfiguracoes(false);
                    new Thread(() -> Server.open(port)).start();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Número da porta inválido!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número da porta inválido!");
        }

    }

    private void close() {
        if (Server.isOpen()) {
            jButtonOpen.setEnabled(true);
            jButtonRestart.setEnabled(false);
            jButtonClose.setEnabled(false);
            setEnabledConfiguracoes(true);
            Server.close();
        }
    }

    private void restart() {
        try {
            close();
            Thread.sleep(100);
            open();
        } catch (InterruptedException ex) {
        }
    }

    private void setEnabledConfiguracoes(boolean flag) {
        jTextFieldPorta.setEnabled(flag);
        jSpinnerCartasNaMao.setEnabled(flag);
        jSpinnerShieldIinicial.setEnabled(flag);
        jSpinnerVidaInicialHeroi.setEnabled(flag);
    }

    private void loadConfiguracoes() {
        GamePlay.INSTANCE.setVidaTotalHeroi(Integer.parseInt(jSpinnerVidaInicialHeroi.getValue().toString()));
        GamePlay.INSTANCE.setCartasNaMao(Integer.parseInt(jSpinnerCartasNaMao.getValue().toString()));
        GamePlay.INSTANCE.setShieldInicial(Integer.parseInt(jSpinnerShieldIinicial.getValue().toString()));
    }
}