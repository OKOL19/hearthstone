package com.limagiran.hearthstone.partida.view;

import com.limagiran.hearthstone.util.*;
import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Img;
import com.limagiran.hearthstone.util.Utils;
import com.limagiran.hearthstone.settings.SettingsView;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.io.Serializable;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;
import static com.limagiran.hearthstone.HearthStone.CARTAS;
import static com.limagiran.hearthstone.util.AbsolutesConstraints.*;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import com.limagiran.hearthstone.partida.control.Pacote;
import com.limagiran.hearthstone.partida.control.Partida;
import com.limagiran.hearthstone.poder.control.PoderHeroico;
import com.limagiran.hearthstone.server.GameCliente;
import static com.limagiran.hearthstone.settings.Settings.EnumSettings.TIME_ANIMATION;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Vinicius Silva
 */
public final class PartidaView extends JFrame implements Serializable, Param {

    public static final Color TRANSPARENTE = new Color(0, 0, 0, 0);
    public static final int PLAYER_ONE = 0;
    public static final int PLAYER_TWO = 1;
    public boolean ESPERANDO_ALVO = false;

    public boolean pintarLinhaEvento = false;

    private transient Graphics2D g2;
    private transient BufferStrategy bs;

    private final transient JPanel glassPane;

    public final Partida PARTIDA;
    private final Heroi hero;
    private final Heroi oponente;
    private transient final Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
    private final boolean inicioTurno;
    private boolean muligado = false;
    private final transient Point lastPointMouseMoved = new Point(0, 0);
    private transient final PopUpSegredos popUpSegredos = new PopUpSegredos();
    private transient Card popUpCard;
    private transient PoderHeroico popUpPoderHeroico;
    private transient final StringBuilder historico = new StringBuilder();
    private transient Component pressed;
    private transient Component released;
    private boolean mouseIsDown;
    private long lado = 0;
    public boolean bloqueado = false;
    public boolean partidaEncerrada = false;
    private Point CLICOU = new Point(0, 0);
    private Point SOLTOU = new Point(0, 0);
    private final Point ORIGEM_EVENTO = new Point(0, 0);
    private final Point DESTINO_EVENTO = new Point(0, 0);
    private final DragAndDrop DRAG_AND_DROP = new DragAndDrop(CLICOU, SOLTOU);
    private final Animacao ANIMACAO = new Animacao(ORIGEM_EVENTO, DESTINO_EVENTO);
    private JLabel exibirCard;
    private JLabel justasHeroi;
    private JLabel justasOponente;
    private long alvoSelecionado;
    public final GameCliente gameCliente;

    private PartidaView(Partida partida, boolean inicioTurno, GameCliente gameCliente) {
        PARTIDA = partida;
        this.gameCliente = gameCliente;
        initComponents();
        renderGraphics();
        this.inicioTurno = inicioTurno;
        DefaultCaret caret = (DefaultCaret) jTextAreaHistorico.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        hero = PARTIDA.getHero();
        oponente = PARTIDA.getOponente();
        adicionarPaineis();
        //configura os atalhos da janela atual
        inserirAtalhos();
        PARTIDA.setPartidaView(getInstance());
        PARTIDA.iniciarThreadExecutarJogadas();
        glassPane = (JPanel) getInstance().getGlassPane();
        glassPane.setLayout(new GridBagLayout());
        Arrays.asList(hero, oponente).forEach(Heroi::refreshDanoMagico);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelJanela = new JPanel(){
            @Override
            public String toString(){
                return getName();
            }
        };
        jPanelMana = new JPanel();
        jLabelManaBackGround = new JLabel();
        jPanelManaOponente = new JPanel();
        jLabelManaBackGroundOponente = new JLabel();
        jPanelDeck = new JPanel() {
            @Override
            public String toString() {
                return getName();
            }
        };
        jButtonPassar = new JButton();
        jPanelMesa = new JPanel();
        jScrollPaneHistorico = new JScrollPane();
        jTextAreaHistorico = new JTextArea(){
            @Override
            public String toString(){
                return getName();
            }
        };
        jButtonSettings = new JButton();
        jLabelBoardImage = new JLabel();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);

        jPanelJanela.setName("janela"); // NOI18N
        jPanelJanela.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                jPanelJanelaMouseDragged(evt);
            }
            public void mouseMoved(MouseEvent evt) {
                jPanelJanelaMouseMoved(evt);
            }
        });
        jPanelJanela.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jPanelJanelaMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                jPanelJanelaMouseReleased(evt);
            }
        });
        jPanelJanela.setLayout(new AbsoluteLayout());

        jPanelMana.setLayout(new AbsoluteLayout());

        jLabelManaBackGround.setIcon(Images.ARENA_BARRA_MANA);
        jPanelMana.add(jLabelManaBackGround, new AbsoluteConstraints(0, 0, -1, -1));

        jPanelJanela.add(jPanelMana, new AbsoluteConstraints(977, 708, 382, 51));

        jPanelManaOponente.setLayout(new AbsoluteLayout());

        jLabelManaBackGroundOponente.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        jLabelManaBackGroundOponente.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelManaBackGroundOponente.setIcon(Images.ARENA_BARRA_MANA_OPONENTE);
        jPanelManaOponente.add(jLabelManaBackGroundOponente, new AbsoluteConstraints(0, 0, 85, 35));

        jPanelJanela.add(jPanelManaOponente, new AbsoluteConstraints(976, 0, 85, 35));

        jPanelDeck.setName("panelDeck"); // NOI18N
        jPanelDeck.setOpaque(false);
        jPanelDeck.setLayout(new AbsoluteLayout());

        jButtonPassar.setText("passar");
        jButtonPassar.setEnabled(false);
        jButtonPassar.setFocusPainted(false);
        jButtonPassar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonPassarActionPerformed(evt);
            }
        });
        jPanelDeck.add(jButtonPassar, new AbsoluteConstraints(0, 160, 190, 40));

        jPanelJanela.add(jPanelDeck, new AbsoluteConstraints(1170, 200, 190, 360));

        jPanelMesa.setOpaque(false);
        jPanelMesa.setLayout(new AbsoluteLayout());
        jPanelJanela.add(jPanelMesa, new AbsoluteConstraints(0, 200, 1160, 360));

        jTextAreaHistorico.setEditable(false);
        jTextAreaHistorico.setColumns(20);
        jTextAreaHistorico.setFont(new Font("Monospaced", 0, 12)); // NOI18N
        jTextAreaHistorico.setLineWrap(true);
        jTextAreaHistorico.setRows(5);
        jTextAreaHistorico.setWrapStyleWord(true);
        jTextAreaHistorico.setName("Historico"); // NOI18N
        jScrollPaneHistorico.setViewportView(jTextAreaHistorico);

        jPanelJanela.add(jScrollPaneHistorico, new AbsoluteConstraints(1066, 0, 290, 190));

        jButtonSettings.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        jButtonSettings.setIcon(Images.ICON_SETTINGS);
        jButtonSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonSettingsActionPerformed(evt);
            }
        });
        jPanelJanela.add(jButtonSettings, new AbsoluteConstraints(1303, 673, 50, -1));

        jLabelBoardImage.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelBoardImage.setIcon(Images.ARENA_TEXTURA);
        jPanelJanela.add(jLabelBoardImage, new AbsoluteConstraints(0, 0, -1, -1));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelJanela, GroupLayout.PREFERRED_SIZE, 1360, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelJanela, GroupLayout.PREFERRED_SIZE, 760, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Botão Passar Turno
     *
     * @param evt ActionEvent
     */
    private void jButtonPassarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonPassarActionPerformed
        passarTurno();
    }//GEN-LAST:event_jButtonPassarActionPerformed

    /**
     * Captura o nome do componente onde o mouse foi clicado
     *
     * @param evt MouseEvent
     */
    private void jPanelJanelaMousePressed(MouseEvent evt) {//GEN-FIRST:event_jPanelJanelaMousePressed
        CLICOU = evt.getLocationOnScreen();
        setPressed(jPanelJanela.findComponentAt(evt.getPoint()));
        revalidate();
    }//GEN-LAST:event_jPanelJanelaMousePressed

    /**
     * Captura o nome do componente onde o clique do mouse foi solto
     *
     * @param evt MouseEvent
     */
    private void jPanelJanelaMouseReleased(MouseEvent evt) {//GEN-FIRST:event_jPanelJanelaMouseReleased
        mouseIsDown = false;
        DRAG_AND_DROP.setVisible(false);
        Component c = jPanelJanela.findComponentAt(evt.getPoint());
        if (ESPERANDO_ALVO) {
            capturarAlvoGritoDeGuerra(c, evt);
        } else if (PARTIDA.isVezHeroi()) {
            lado = evt.getXOnScreen() < (tela.width / 2) ? Values.LEFT : Values.RIGHT;
            setReleased(c);
        }
        revalidate();
    }//GEN-LAST:event_jPanelJanelaMouseReleased

    /**
     * Evento do mouse gerado ao mover o mouse em cima de um componente.
     * Verifica se é um componente válido para exibição de um popUp (Card, Poder
     * Heroico, etc...)
     *
     * @param evt MouseEvent
     */
    private void jPanelJanelaMouseMoved(MouseEvent evt) {//GEN-FIRST:event_jPanelJanelaMouseMoved
        disposeAllPopUps();
        lastPointMouseMoved.setLocation(evt.getPoint());
        String componente = jPanelJanela.findComponentAt(evt.getPoint()).toString();
        if (!ESPERANDO_ALVO && !SettingsView.staticIsVisible()) {
            exibirPopUpMouseParado(evt, componente);
        }
    }//GEN-LAST:event_jPanelJanelaMouseMoved

    /**
     * Botão desistir. Pergunta se o jogador quer desistir da partida
     *
     * @param evt ActionEvent
     */
    private void jButtonSettingsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonSettingsActionPerformed
        desistir();
    }//GEN-LAST:event_jButtonSettingsActionPerformed

    /**
     * Evento gerado ao arrastar o mouse. Atualiza a variável soltou (Point)
     * para desenhar a seta com o arrasto do mouse
     *
     * @param evt MouseEvent
     */
    private void jPanelJanelaMouseDragged(MouseEvent evt) {//GEN-FIRST:event_jPanelJanelaMouseDragged
        disposeAllPopUps();
        SOLTOU = evt.getLocationOnScreen();
        mouseIsDown = true;
    }//GEN-LAST:event_jPanelJanelaMouseDragged

    public static PartidaView main(Partida partida, boolean inicioTurno, GameCliente gameCliente) {
        return new PartidaView(partida, inicioTurno, gameCliente);
    }

    public PartidaView getInstance() {
        return this;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButtonPassar;
    private JButton jButtonSettings;
    private JLabel jLabelBoardImage;
    private JLabel jLabelManaBackGround;
    private JLabel jLabelManaBackGroundOponente;
    private JPanel jPanelDeck;
    private JPanel jPanelJanela;
    private JPanel jPanelMana;
    private JPanel jPanelManaOponente;
    private JPanel jPanelMesa;
    private JScrollPane jScrollPaneHistorico;
    private JTextArea jTextAreaHistorico;
    // End of variables declaration//GEN-END:variables

    /**
     * Iniciar o turno
     */
    public void iniciarTurno() {
        Audios.playEfeitos(Audios.PARTIDA_SUA_VEZ);
        showPopUp("Sua vez!");
        jButtonPassar.setEnabled(true);
    }

    /**
     * Passar o turno Desativa o botão Passar Turno
     */
    private void passarTurno() {
        jButtonPassar.setEnabled(false);
        Audios.playEfeitos(Audios.PARTIDA_ENCERRAR_TURNO);
        if (PARTIDA.isVezHeroi()) {
            PARTIDA.addJogada(() -> {
                bloqueado = true;
                PARTIDA.passarTurno();
                bloqueado = false;
            });
        }
    }

    /**
     * Abre a janela para muligar as cartas iniciais
     */
    private void muligar() {
        Audios.playMusicaFundo(Audios.MUSICA_FUNDO_PARTIDA);
        SwingUtils.runOnUIThread(() -> Muligar.main(PARTIDA).setVisible(true));
        if (!PARTIDA.isVezHeroi()) {
            Card moeda = CARTAS.createCard(Values.MOEDA);
            hero.getDeck().add(4, moeda);
        }
        PARTIDA.addAllCards(hero.getDeck());
        GameCliente.sendDeck(hero.getDeck());
    }

    /**
     * Variável que armazena o componente onde o mouse foi clicado
     *
     * @param pressed componente
     */
    public void setPressed(Component pressed) {
        this.pressed = pressed;
    }

    /**
     * Variável que armazena o componente onde o clique do mouse foi solto
     *
     * @param released nome do componente
     */
    public void setReleased(Component released) {
        this.released = released;
        mouseReleased();
    }

    public void start() {
        setVisible(true);
        muligar();
        glassPane.removeAll();
        final PopUp showPopUp = new PopUp("Aguardando Adversário...", 50);
        glassPane.add(showPopUp, new GridBagConstraints());
        glassPane.setVisible(true);
        new Thread(() -> {
            while (!muligado) {
                Utils.sleep(50);
            }
            Utils.sleep(100);
            SwingUtils.runOnUIThread(() -> glassPane.remove(showPopUp));
            if (inicioTurno) {
                PARTIDA.iniciarPartida();
            }
            addHistorico(PARTIDA.getHero().getNome() + " vs " + PARTIDA.getOponente().getNome());
            addHistorico("Partida iniciada!");
        }).start();
    }

    /**
     * Verifica se é a vez do player e gera a jogada se o mouse foi solto em um
     * local válido
     */
    public void mouseReleased() {
        if (PARTIDA.isVezHeroi() && jButtonPassar.isEnabled()) {
            Component clicado = pressed;
            Component solto = released;
            PARTIDA.addJogada(() -> {
                bloqueado = true;
                if (validarComponentesClicados(clicado, solto)) {
                    PARTIDA.gerarJogada(lado, clicado.toString(), solto.toString());
                }
                bloqueado = false;
                revalidate();
            });
        }
    }

    /**
     * Exibe uma janela popUp com o card onde o mouse se encontra
     *
     * @param point ponto na tela onde a janela será exibida
     * @param name nome do componente onde o mouse se encontra (com o ID do
     * card)
     */
    private void showPopUpCard(Point point, String name) {
        Card card = PARTIDA.findCardByIDLong(Partida.getIdLong(name));
        if (card != null) {
            popUpCard = card;
            popUpCard.showPopUpMedium(point);
        }
    }

    /**
     * Exibe uma janela popUp com o poder heroico onde o mouse se encontra
     *
     * @param evt evento do mouse
     */
    private void showPopUpPoderHeroico(MouseEvent evt) {
        popUpPoderHeroico = evt.getYOnScreen() < 350 ? oponente.getPoderHeroico() : hero.getPoderHeroico();
        popUpPoderHeroico.showPopUpMedium(null);
    }

    /**
     * Variável muligado, verifica se a muligação já foi feita
     *
     * @return true or false
     */
    public boolean isMuligado() {
        return muligado;
    }

    /**
     * Variável muligado, verifica se a muligação já foi feita
     *
     * @param muligado true or false
     */
    public void setMuligado(boolean muligado) {
        this.muligado = muligado;
    }

    /**
     * Método que atualiza a interface da janela principal
     */
    private void renderGraphics() {
        getContentPane().setBackground(new Color(238, 199, 122));
        setIgnoreRepaint(true);
        createBufferStrategy(2);
        bs = getBufferStrategy();
        new Thread(() -> {
            while (!getPartidaEncerrada()) {
                SwingUtilities.invokeLater(paintScreen());
                Utils.sleep(75);
            }
            if (PARTIDA != null) {
                PARTIDA.finalizar();
            }
        }).start();
    }

    /**
     * Atualiza a tela
     *
     * @return runnable
     */
    private Runnable paintScreen() {
        return () -> {
            try {
                g2 = (Graphics2D) bs.getDrawGraphics();
                paintAll(g2);
                paintDragAndDrop(g2);
            } catch (Exception ex) {
            } finally {
                if (g2 != null) {
                    g2.dispose();
                }
            }
            if (!bs.contentsLost()) {
                bs.show();
            }
        };
    }

    /**
     * Adiciona os painéis da janela principal do jogo
     */
    private void adicionarPaineis() {
        jPanelJanela.add(new HeroiView(hero), HEROI_PANEL, 0);
        jPanelJanela.add(hero.getLabelMana(), HEROI_LABEL_MANA, 0);
        jPanelJanela.add(hero.getPanelMana(), HEROI_PANEL_MANA, 0);
        jPanelJanela.add(hero.getPanelMao(), HEROI_PANEL_MAO, 0);
        jPanelDeck.add(hero.getPanelDeckBack(), HEROI_PANEL_DECK, 0);
        jPanelMesa.add(hero.getPanelMesa(), HEROI_PANEL_MESA);

        jPanelJanela.add(new OponenteView(oponente), OPONENTE_PANEL, 0);
        jPanelManaOponente.add(oponente.getLabelMana(), OPONENTE_LABEL_MANA, 0);
        jPanelJanela.add(oponente.getPanelMaoBack(), OPONENTE_PANEL_MAO_BACK, 0);
        jPanelDeck.add(oponente.getPanelDeckBack(), OPONENTE_PANEL_DECK, 0);
        jPanelMesa.add(oponente.getPanelMesa(), OPONENTE_PANEL_MESA);

        jPanelJanela.add(ANIMACAO, PARTIDA_ANIMACAO, 0);
        jPanelJanela.add(DRAG_AND_DROP, PARTIDA_DRAG_AND_DROP, 0);
        getGlassPane().setCursor(MouseCursor.DEFAULT);
        getGlassPane().setVisible(true);
    }

    @Override
    public void setCursor(Cursor cursor) {
        getGlassPane().setCursor(cursor);
    }

    /**
     * Adiciona uma mensagem no histórico do jogo
     *
     * @param mensagem mensagem a ser adicionada
     */
    public void addHistorico(String mensagem) {
        SwingUtils.runOnUIThread(() -> {
            historico.append(((historico.length() > 0 ? "\n --- " : " --- ") + mensagem));
            jTextAreaHistorico.setText(historico.toString());
        });
    }

    /**
     * Exibe uma mensagem na tela
     *
     * @param text mensagem a ser exibida
     * @param time tempo em exibição na tela
     */
    public void showPopUp(String text, long time) {
        new Thread(() -> {
            final PopUp showPopUp = new PopUp(text, 50);
            SwingUtils.runOnUIThread(() -> {
                glassPane.removeAll();
                glassPane.add(showPopUp, new GridBagConstraints());
                glassPane.revalidate();
            });
            Utils.sleep(time);
            for (float i = 9; i >= 0; i--) {
                float iFinal = i;
                SwingUtilities.invokeLater(() -> showPopUp.setIcon(Img.transparencia(showPopUp.getImageIcon(), iFinal / 10f)));
                Utils.sleep(100);
            }
            SwingUtilities.invokeLater(() -> glassPane.remove(showPopUp));
        }).start();
    }

    /**
     * Exibe uma mensagem na tela
     *
     * @param text mensagem a ser exibida
     */
    public void showPopUp(String text) {
        showPopUp(text, 3000);
    }

    /**
     * Pergunta se o jogador deseja abandonar a partida. Se sim, encerra a
     * partida.
     */
    private void desistir() {
        SettingsView.main("Desistir", () -> {
            if (Utils.confirm(this, "Deseja abandonar a partida?", "Desistir")) {
                Pacote pack = new Pacote(PARTIDA_SET_VENCEDOR);
                pack.set(VALUE, (PARTIDA.getPlayer() + 1) % 2);
                GameCliente.sendNow(pack);
                SwingUtilities.invokeLater(this::jogoEncerrado);
            }
        });
    }

    /**
     * Jogo encerrado
     */
    public void jogoEncerrado() {
        setPartidaEncerrada(true);
        Utils.sleep(3000);
        JOptionPane.showMessageDialog(null, PARTIDA.getVencedor() == PARTIDA.getPlayer()
                ? "PARABÉNS! VOCÊ VENCEU A PARTIDA!"
                : "VOCÊ PERDEU!");
        gameCliente.exit();
        dispose();
    }

    /**
     * Oponente desistiu
     */
    public void oponenteDesistiu() {
        gameCliente.exit();
        JOptionPane.showMessageDialog(null, "Seu oponente saiu da partida!");
        disposeAllPopUps();
        dispose();
    }

    /**
     * Exibe um popup com os segredos do herói
     */
    private void showPopUpSegredos() {
        if (!hero.getSegredo().isEmpty()) {
            popUpSegredos.showPopUp(hero.getSegredo());
        }
    }

    /**
     * Verifica se tem alguma animação ocorrendo atualmente
     *
     * @return true or false
     */
    public boolean isBloqueado() {
        return bloqueado;
    }

    /**
     * Altera o valor da variável animação que verifica se tem alguma animação
     * acontecendo atualmente
     *
     * @param bloqueado
     */
    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    /**
     * Exibe na tela o card jogado pelo oponente
     *
     * @param id código do card jogado
     */
    public void exibirCardJogado(String id) {
        Audios.playEfeitos(Audios.PARTIDA_CARD_JOGAR);
        exibirCard(null, Images.getCardIcon(Images.CARD_MEDIUM, id), PARTIDA_EXIBIR_CARD_JOGADO, TIME_ANIMATION.getLong());
    }

    /**
     * Exibe um verso de card e uma mensagem de SEGREDO!
     *
     */
    public void exibirSegredoAtivado() {
        Audios.playEfeitos(Audios.PARTIDA_SEGREDO_ATIVAR);
        exibirCard("SEGREDO!", oponente.getCardback().getCardBackImage(), PARTIDA_ATIVAR_SEGREDO_OPONENTE, TIME_ANIMATION.getLong());
    }

    /**
     * Exibe na tela o segredo revelado
     *
     * @param id código do segredo
     * @param heroi
     */
    public void exibirSegredoRevelado(String id, long heroi) {
        Pacote pacote = new Pacote(ANIMACAO_CARD_SEGREDO_REVELADO);
        pacote.set(CARD_ID, id);
        pacote.set(VALUE, ((heroi == HEROI) ? OPONENTE : HEROI));
        GameCliente.send(pacote);
        Audios.playEfeitos(Audios.PARTIDA_SEGREDO_REVELAR);
        exibirCard(null, Images.getCardIcon(Images.CARD_MEDIUM, id),
                ((heroi == HEROI) ? PARTIDA_EXIBIR_SEGREDO_HEROI : PARTIDA_EXIBIR_SEGREDO_OPONENTE),
                TIME_ANIMATION.getLong());
    }

    /**
     * Exibe na tela os cards selecionados para as Justas
     *
     * @param idHeroi código do card do herói
     * @param idOponente código do card do oponente
     *
     */
    public void exibirJustas(String idHeroi, String idOponente) {
        Pacote pacote = new Pacote(ANIMACAO_CARD_JUSTAS);
        pacote.set(CARD_JUSTAS_HEROI, idOponente);
        pacote.set(CARD_JUSTAS_OPONENTE, idHeroi);
        GameCliente.send(pacote);
        try {
            SwingUtils.runOnUIThread(() -> {
                justasHeroi = null;
                justasOponente = null;
                justasHeroi = new JLabel(Images.getCardIcon(Images.CARD_MEDIUM, idHeroi), javax.swing.SwingConstants.CENTER);
                justasOponente = new JLabel(Images.getCardIcon(Images.CARD_MEDIUM, idOponente), javax.swing.SwingConstants.CENTER);
                jPanelJanela.add(justasHeroi, PARTIDA_EXIBIR_JUSTAS_HEROI, 0);
                jPanelJanela.add(justasOponente, PARTIDA_EXIBIR_JUSTAS_OPONENTE, 0);
                revalidate();
            });
            Utils.sleep(3000);
            SwingUtils.runOnUIThread(() -> {
                jPanelJanela.remove(justasHeroi);
                jPanelJanela.remove(justasOponente);
            });
        } catch (Exception ex) {
        }
    }

    /**
     * Exibe o card queimado
     *
     * @param id código do card
     * @param heroi dono do card. Heroi ou OPONENTE
     *
     */
    public void exibirCardQueimado(String id, long heroi) {
        Pacote pacote = new Pacote(ANIMACAO_CARD_QUEIMADO);
        pacote.set(CARD_ID, id);
        pacote.set(VALUE, ((heroi != HEROI) ? HEROI : OPONENTE));
        GameCliente.send(pacote);
        AbsoluteConstraints position = heroi == HEROI
                ? PARTIDA_EXIBIR_JUSTAS_HEROI
                : PARTIDA_EXIBIR_JUSTAS_OPONENTE;
        Audios.playEfeitos(Audios.PARTIDA_CARD_JOGAR);
        exibirCard("Card Queimado!", Images.getCardIcon(Images.CARD_MEDIUM, id), position, TIME_ANIMATION.getLong() * 2);
    }

    /**
     * Exibe o dano de fadiga causado
     *
     * @param fadiga quantidade de dano causado pela fadiga
     * @param heroi dono do card. Heroi ou OPONENTE
     *
     */
    public void exibirDanoFadiga(int fadiga, long heroi) {
        Pacote pacote = new Pacote(ANIMACAO_FADIGA);
        pacote.set(ALVO, heroi != HEROI ? HEROI : OPONENTE);
        pacote.set(VALUE, fadiga);
        GameCliente.send(pacote);
        AbsoluteConstraints position = heroi == HEROI
                ? PARTIDA_EXIBIR_JUSTAS_HEROI
                : PARTIDA_EXIBIR_JUSTAS_OPONENTE;
        ImageIcon imageIcon = heroi == HEROI
                ? hero.getCardback().getCardBackImage()
                : oponente.getCardback().getCardBackImage();
        exibirCard("FADIGA! = " + fadiga, imageIcon, position, (long) (TIME_ANIMATION.getLong() * 1.5));
    }

    /**
     * Exibe o card comprado
     *
     * @param id id do card
     * @param heroi dono do card. Heroi ou OPONENTE
     *
     */
    public void exibirCardComprado(String id, long heroi) {
        Pacote pacote = new Pacote(ANIMACAO_CARD_COMPRADO);
        pacote.set(CARD_ID, id);
        pacote.set(VALUE, heroi != HEROI ? HEROI : OPONENTE);
        GameCliente.send(pacote);
        ImageIcon imageIcon = heroi == HEROI
                ? Images.getCardIcon(Images.CARD_MEDIUM, id)
                : oponente.getCardback().getCardBackImage();
        AbsoluteConstraints position = heroi == HEROI
                ? PARTIDA_EXIBIR_JUSTAS_HEROI
                : PARTIDA_EXIBIR_JUSTAS_OPONENTE;
        Audios.playEfeitos(Audios.PARTIDA_CARD_JOGAR);
        exibirCard(null, imageIcon, position, TIME_ANIMATION.getLong() + 300);
    }

    /**
     * Exibe um card (ou verso) na tela
     *
     * @param popUpText texto a ser exibido
     * @param card card exibido
     * @param position posição na tela
     * @param time milissegundos em exibição
     */
    private void exibirCard(String popUpText, ImageIcon card, AbsoluteConstraints position, long time) {
        try {
            SwingUtils.runOnUIThread(() -> {
                exibirCard = null;
                exibirCard = new JLabel(card, javax.swing.SwingConstants.CENTER);
                jPanelJanela.add(exibirCard, position, 0);
                revalidate();
            });
            if (popUpText != null) {
                showPopUp(popUpText, time);
            }
            Utils.sleep(time);
            SwingUtils.runOnUIThread(() -> jPanelJanela.remove(exibirCard));
        } catch (Exception ex) {
        }
    }

    public void exibirLinhaEvento(long origem, long destino) {
        try {
            setEventLocationById(ORIGEM_EVENTO, origem);
            setEventLocationById(DESTINO_EVENTO, destino);
            pintarLinhaEvento = true;
            Utils.sleep(TIME_ANIMATION.getLong());
            pintarLinhaEvento = false;
        } catch (Exception ex) {
        }
    }

    /**
     * Exibe uma mensagem na tela avisando que houve o alvo foi alterado pelo
     * evento "50% de chance"
     */
    public void chanceDeAtacarInimigoErradoAnimacao() {
        showPopUp("50% chance = Alvo alterado!");
        GameCliente.send(ANIMACAO_CHANCE_DE_ERRAR_ALVO, "value");
        Utils.sleep(TIME_ANIMATION.getLong());
    }

    /**
     * Altera as variáveis Point de evento para exibir a linha de origem e
     * destino do evento gerado pelo oponente
     *
     * @param point
     * @param id
     */
    private void setEventLocationById(Point point, long id) {
        if (id == HEROI) {
            setLocationByComponent(point, oponente.getPanelHeroi());
        } else if (id == OPONENTE) {
            setLocationByComponent(point, hero.getPanelHeroi());
        } else {
            Card c = PARTIDA.findCardByIDLong(id);
            setLocationByComponent(point, c.viewCardMesa);
        }
    }

    /**
     * Altera as variáveis Point de evento para exibir a linha de origem e
     * destino do evento gerado pelo oponente
     *
     * @param point variável a ser modificada
     * @param c componente de origem ou destino
     */
    private void setLocationByComponent(Point point, Component c) {
        Point p = c.getLocationOnScreen();
        point.x = p.x + c.getWidth() / 2;
        point.y = p.y + c.getHeight() / 2;
    }

    private void inserirAtalhos() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "testeDesenvolvimento");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0), "testeDesenvolvimentoComprarCarta");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelarAcao");

        getRootPane().getActionMap().put("testeDesenvolvimento", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
            }
        });

        getRootPane().getActionMap().put("testeDesenvolvimentoComprarCarta", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                /*hero.addMao(PARTIDA.criarCard("EX1_076", System.nanoTime()));
                hero.addMao(PARTIDA.criarCard("EX1_145", System.nanoTime()));
                hero.addMao(PARTIDA.criarCard("EX1_612", System.nanoTime()));
                hero.addMao(PARTIDA.criarCard("BRM_018", System.nanoTime()));
                com.limagiran.hearthstone.util.GamePlay.CONFIG.setCartasNaMao(20);
                hero.setMana(10);
                hero.addMana(10);
                oponente.setMana(1);*/
            }
        });

        getRootPane().getActionMap().put("cancelarAcao", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            /**
             * Cancelar o grito de guerra
             *
             * @param arg0 evento do teclado
             */
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (ESPERANDO_ALVO) {
                    setAlvoSelecionado(ALVO_CANCEL);
                }
            }
        });
    }

    public long getAlvoSelecionado() {
        return alvoSelecionado;
    }

    /**
     * Alvo selecionado no grito de guerra
     *
     * @param alvoSelecionado alvo selecionado
     */
    public void setAlvoSelecionado(long alvoSelecionado) {
        this.alvoSelecionado = alvoSelecionado;
    }

    public void disposeAllPopUps() {
        if (popUpCard != null) {
            popUpCard.disposePopUp();
        }
        if (popUpPoderHeroico != null) {
            popUpPoderHeroico.getPoder().disposePopUp();
        }
        popUpSegredos.dispose();
        popUpCard = null;
        popUpPoderHeroico = null;
    }

    /**
     * Verifica se o card, lacaio, herói, etc. clicado ainda está disponível na
     * mesa para gerar a jogada desejada
     *
     * @param clicado componente clicado
     * @param solto componente solto
     * @return true or false
     */
    private boolean validarComponentesClicados(Component clicado, Component solto) {
        try {
            return estaNaMesa(clicado.toString()) && estaNaMesa(solto.toString());
        } catch (Exception e) {
        }
        return false;
    }

    private boolean estaNaMesa(String id) {
        if (id.contains(Values.TO_STRING_LACAIO) && !PARTIDA.getMesa().contains(PARTIDA.findCardByIDLong(Partida.getIdLong(id)))) {
            return false;
        } else if (id.contains(Values.TO_STRING_MAO) && !hero.getMao().contains(PARTIDA.findCardByIDLong(Partida.getIdLong(id)))) {
            return false;
        }
        return true;
    }

    /**
     * Verifica se a partida foi encerrada
     *
     * @return true or false
     */
    public boolean isPartidaEncerrada() {
        return partidaEncerrada;
    }

    /**
     * Verifica se a partida foi encerrada
     *
     * @param partidaEncerrada
     */
    public void setPartidaEncerrada(boolean partidaEncerrada) {
        this.partidaEncerrada = partidaEncerrada;
        if (partidaEncerrada) {
            Audios.stopMusicaFundo();
            Audios.playEfeitos(((PARTIDA.getVencedor() == PARTIDA.getPlayer())
                    ? Audios.PARTIDA_ENCERRADA_VENCEU
                    : Audios.PARTIDA_ENCERRADA_PERDEU));
        }
    }

    public boolean getPartidaEncerrada() {
        return partidaEncerrada;
    }

    private void paintDragAndDrop(Graphics2D g2) {
        if (mouseIsDown && PARTIDA.isVezHeroi()) {
            pintarLinhaEvento(g2, CLICOU, SOLTOU);
        } else if (pintarLinhaEvento) {
            pintarLinhaEvento(g2, ORIGEM_EVENTO, DESTINO_EVENTO);
        }
    }

    public void pintarLinhaEvento(Graphics2D g2, Point origem, Point destino) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(5, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND));
        g2.setColor(Color.red);
        g2.drawLine(origem.x, origem.y, destino.x, destino.y);
        g2.setStroke(new BasicStroke(5, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND));
        g2.drawOval(destino.x - 25, destino.y - 25, 50, 50);
        g2.fillOval(destino.x - 10, destino.y - 10, 20, 20);
    }

    /**
     * Captura o alvo clicado logo após um evento grito de guerra ter sido
     * gerado
     *
     * @param component alvo clicado
     * @param evt evento do mouse
     */
    private void capturarAlvoGritoDeGuerra(Component component, MouseEvent evt) {
        if (evt.getButton() == MouseEvent.BUTTON1) {
            String name = component != null ? component.toString() : "";
            if (name.contains(Values.TO_STRING_HEROI) || name.contains(Values.TO_STRING_LACAIO)) {
                setAlvoSelecionado(Partida.getIdLong(name));
            }
        } else {
            setAlvoSelecionado(ALVO_CANCEL);
        }
    }

    /**
     * Exibe uma janela PopUp com a imagem do objeto onde o mouse está parado
     *
     * @param evt evento do mouse
     * @param componente toString do componente onde o mouse está parado
     */
    private void exibirPopUpMouseParado(MouseEvent evt, String componente) {
        new Thread(() -> {
            try {
                Utils.sleep(400);
                if (!ESPERANDO_ALVO && !SettingsView.staticIsVisible() && !mouseIsDown
                        && lastPointMouseMoved.equals(evt.getPoint())) {
                    Point p = jPanelJanela.findComponentAt(evt.getPoint()).getLocationOnScreen();
                    if (componente.contains(Values.TO_STRING_LACAIO)) {
                        p.x += 200;
                        p.y -= 70;
                        showPopUpCard(p, componente);
                    } else if (componente.contains(Values.TO_STRING_ARMA)) {
                        showPopUpCard(null, componente);
                    } else if (componente.contains(Values.TO_STRING_MAO)) {
                        p.y -= 300;
                        showPopUpCard(p, componente);
                    } else if (componente.contains(Values.TO_STRING_PODER_HEROICO)) {
                        showPopUpPoderHeroico(evt);
                    } else if (componente.contains(Values.TO_STRING_SEGREDO + HEROI)) {
                        showPopUpSegredos();
                    }
                }
            } catch (Exception e) {

            }
        }).start();
    }
}
