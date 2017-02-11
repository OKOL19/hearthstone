package com.limagiran.hearthstone.colecao.view;

import static com.limagiran.hearthstone.HearthStone.*;
import static com.limagiran.hearthstone.settings.Settings.EnumSettings.*;
import com.limagiran.hearthstone.util.*;
import com.limagiran.hearthstone.server.Cliente;
import com.limagiran.hearthstone.deck.CardView;
import com.limagiran.hearthstone.deck.Deck;
import com.limagiran.hearthstone.deck.DataBase;
import com.limagiran.hearthstone.colecao.util.*;
import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.settings.SettingsView;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import com.limagiran.hearthstone.Update;
import com.limagiran.hearthstone.deck.util.Util;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import com.limagiran.hearthstone.partida.view.PopUp;
import com.limagiran.hearthstone.server.ServerPanel;
import com.limagiran.hearthstoneia.HearthStoneIA;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Vinicius Silva
 */
public class Colecao extends JFrame {

    //private static Colecao instance;
    //Imagem de uma mana com brilho
    private final ImageIcon manaOn = Images.COLECAO_MANA_ON;
    //Imagem de uma mana sem brilho
    private final ImageIcon manaOff = Images.COLECAO_MANA_OFF;
    //label com a imagem de uma mana
    private final JLabel[] labelMana = new JLabel[8];
    //valor do custo em cima da mana
    private final JLabel[] labelCusto = new JLabel[8];
    //painel de exibição do card na página de coleção
    private final JPanel[] collectionPanel = new JPanel[8];
    //modelo da JList dos decks da coleção
    private final DefaultListModel deckListModel = new DefaultListModel();
    //modelo da JList dos cards do deck selecionado
    private final DefaultListModel cardsListModel = new DefaultListModel();
    //deck selecionado
    private Deck deck;
    //lista de CardView dos cards do deck selecionado
    private List<CardView> cardsDeck = new ArrayList<>();
    //lista de todos os decks salvos
    private final List<Deck> decks = new ArrayList<>();
    //lista espelho dos decks com o objeto DataBase responsável por salvar os decks
    private final List<DataBase> decksDB = new ArrayList<>();
    //posição do último card exibido ao passar o mouse em cima da lista de cards de um deck
    private int lastIndex = -1;
    //objeto CardView temporário para exibir um popUp de um card
    private CardView popUp;
    //nome do servidor para conexão
    private String server = "";
    //porta para conexão
    private int port = 0;
    //Files para salvar e ler as informações necessárias
    private final File PATH_DECKS = new File("decks.dat");
    //lista de cards para exibição nas páginas
    private List<Card> collection = new ArrayList<>();
    //página sendo exibida atualmente
    private int pagina = 1;

    /**
     * Creates new form Principal
     */
    public Colecao() {
        initComponents();
        init();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelBackground = new JPanel();
        jPanelJanela = new JPanel();
        jPanelCabecalho = new JPanel();
        jTextFieldNomeHeroi = new JTextField();
        jButtonPlayerVsPlayer = new JButton();
        jButtonSettings = new JButton();
        jTextFieldServer = new JTextField();
        jLabelServidor = new JLabel();
        jLabelAbout = new JLabel();
        jButtonPlayerVsPC = new JButton();
        jButtonCriarServidor = new JButton();
        jPanelDeck = new JPanel();
        jLabelDecks = new JLabel();
        jScrollPaneMeusDecks = new JScrollPane();
        jListMeusDecks = new JList<>();
        jButtonNewDeck = new JButton();
        jButtonImportarDeck = new JButton();
        jPanelCard = new JPanel();
        jLabelQuantidadeCards = new JLabel();
        jScrollPaneCardsDeck = new JScrollPane();
        jListCardsDeck = new JList<>();
        jPanelFolha = new JPanel();
        jButtonLeft = new JButton();
        jButtonRight = new JButton();
        jPanelMensagemPopUp = new JPanel();
        jPanelCards = new JPanel();
        jPanelCard1 = new JPanel(){
            @Override
            public String toString() {
                return getName();
            }
        };
        jPanelCard2 = new JPanel(){
            @Override
            public String toString() {
                return getName();
            }
        };
        jPanelCard3 = new JPanel(){
            @Override
            public String toString() {
                return getName();
            }
        };
        jPanelCard4 = new JPanel(){
            @Override
            public String toString() {
                return getName();
            }
        };
        jPanelCard5 = new JPanel(){
            @Override
            public String toString() {
                return getName();
            }
        };
        jPanelCard6 = new JPanel(){
            @Override
            public String toString() {
                return getName();
            }
        };
        jPanelCard7 = new JPanel(){
            @Override
            public String toString() {
                return getName();
            }
        };
        jPanelCard8 = new JPanel(){
            @Override
            public String toString() {
                return getName();
            }
        };
        jPanelFiltros = new JPanel();
        jPanelManas = new JPanel();
        jLabelCusto0 = new JLabel();
        jLabelMana0 = new JLabel();
        jLabelCusto1 = new JLabel();
        jLabelMana1 = new JLabel();
        jLabelCusto2 = new JLabel();
        jLabelMana2 = new JLabel();
        jLabelCusto3 = new JLabel();
        jLabelMana3 = new JLabel();
        jLabelCusto4 = new JLabel();
        jLabelMana4 = new JLabel();
        jLabelCusto5 = new JLabel();
        jLabelMana5 = new JLabel();
        jLabelCusto6 = new JLabel();
        jLabelMana6 = new JLabel();
        jLabelCusto7 = new JLabel();
        jLabelMana7 = new JLabel();
        jComboBoxConjunto = new JComboBox<>();
        jTextFieldPesquisa = new JTextField();
        jLabelFolha = new JLabel();
        jLabelTextura = new JLabel();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanelBackground.setBackground(new Color(205, 180, 116));
        jPanelBackground.setPreferredSize(new Dimension(1360, 768));

        jPanelJanela.setOpaque(false);
        jPanelJanela.setPreferredSize(new Dimension(1350, 759));
        jPanelJanela.setLayout(new AbsoluteLayout());

        jPanelCabecalho.setOpaque(false);
        jPanelCabecalho.setLayout(new AbsoluteLayout());

        jTextFieldNomeHeroi.setFont(new Font("Tahoma", 0, 36)); // NOI18N
        jTextFieldNomeHeroi.setHorizontalAlignment(JTextField.CENTER);
        jTextFieldNomeHeroi.setText("Herói");
        jTextFieldNomeHeroi.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                jTextFieldNomeHeroiFocusGained(evt);
            }
            public void focusLost(FocusEvent evt) {
                jTextFieldNomeHeroiFocusLost(evt);
            }
        });
        jTextFieldNomeHeroi.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                jTextFieldNomeHeroiKeyPressed(evt);
            }
        });
        jPanelCabecalho.add(jTextFieldNomeHeroi, new AbsoluteConstraints(560, 0, 430, 45));

        jButtonPlayerVsPlayer.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        jButtonPlayerVsPlayer.setText("vs Player");
        jButtonPlayerVsPlayer.setMargin(new Insets(2, 2, 2, 2));
        jButtonPlayerVsPlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonPlayerVsPlayerActionPerformed(evt);
            }
        });
        jPanelCabecalho.add(jButtonPlayerVsPlayer, new AbsoluteConstraints(75, 7, 100, 30));

        jButtonSettings.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        jButtonSettings.setIcon(Images.ICON_SETTINGS);
        jButtonSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonSettingsActionPerformed(evt);
            }
        });
        jPanelCabecalho.add(jButtonSettings, new AbsoluteConstraints(1285, 5, 50, -1));

        jTextFieldServer.setFont(new Font("Tahoma", 0, 18)); // NOI18N
        jTextFieldServer.setHorizontalAlignment(JTextField.CENTER);
        jTextFieldServer.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                jTextFieldServerFocusLost(evt);
            }
        });
        jPanelCabecalho.add(jTextFieldServer, new AbsoluteConstraints(250, 7, 210, 30));

        jLabelServidor.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        jLabelServidor.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelServidor.setText("Servidor:");
        jPanelCabecalho.add(jLabelServidor, new AbsoluteConstraints(180, 12, 60, 20));

        jLabelAbout.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        jLabelAbout.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelAbout.setText("by Lima Giran");
        jPanelCabecalho.add(jLabelAbout, new AbsoluteConstraints(990, 0, 280, 45));

        jButtonPlayerVsPC.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        jButtonPlayerVsPC.setText("vs PC");
        jButtonPlayerVsPC.setMargin(new Insets(2, 2, 2, 2));
        jButtonPlayerVsPC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonPlayerVsPCActionPerformed(evt);
            }
        });
        jPanelCabecalho.add(jButtonPlayerVsPC, new AbsoluteConstraints(8, 7, 62, 30));

        jButtonCriarServidor.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        jButtonCriarServidor.setText("Criar");
        jButtonCriarServidor.setMargin(new Insets(2, 2, 2, 2));
        jButtonCriarServidor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonCriarServidorActionPerformed(evt);
            }
        });
        jPanelCabecalho.add(jButtonCriarServidor, new AbsoluteConstraints(470, 10, 80, -1));

        jPanelJanela.add(jPanelCabecalho, new AbsoluteConstraints(0, 0, 1340, 45));

        jPanelDeck.setOpaque(false);
        jPanelDeck.setLayout(new AbsoluteLayout());

        jLabelDecks.setFont(new Font("Tahoma", 0, 24)); // NOI18N
        jLabelDecks.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelDecks.setText("Decks");
        jPanelDeck.add(jLabelDecks, new AbsoluteConstraints(0, 0, 130, 40));

        jScrollPaneMeusDecks.setBackground(new Color(0, 0, 0, 0));
        jScrollPaneMeusDecks.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneMeusDecks.setPreferredSize(new Dimension(133, 482));

        jListMeusDecks.setBackground(new Color(0, 0, 0, 0));
        jListMeusDecks.setFont(new Font("Tahoma", 0, 36)); // NOI18N
        jListMeusDecks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListMeusDecks.setMaximumSize(new Dimension(133, 60));
        jListMeusDecks.setMinimumSize(new Dimension(133, 60));
        jListMeusDecks.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                jListMeusDecksMouseReleased(evt);
            }
        });
        jScrollPaneMeusDecks.setViewportView(jListMeusDecks);

        jPanelDeck.add(jScrollPaneMeusDecks, new AbsoluteConstraints(0, 40, 133, 590));

        jButtonNewDeck.setFont(new Font("Tahoma", 0, 18)); // NOI18N
        jButtonNewDeck.setText("New Deck");
        jButtonNewDeck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonNewDeckActionPerformed(evt);
            }
        });
        jPanelDeck.add(jButtonNewDeck, new AbsoluteConstraints(0, 630, 133, 30));

        jButtonImportarDeck.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        jButtonImportarDeck.setText("Importar");
        jButtonImportarDeck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonImportarDeckActionPerformed(evt);
            }
        });
        jPanelDeck.add(jButtonImportarDeck, new AbsoluteConstraints(0, 660, 133, 20));

        jPanelJanela.add(jPanelDeck, new AbsoluteConstraints(20, 50, 133, 680));

        jPanelCard.setOpaque(false);
        jPanelCard.setLayout(new AbsoluteLayout());

        jLabelQuantidadeCards.setFont(new Font("Tahoma", 0, 24)); // NOI18N
        jLabelQuantidadeCards.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelQuantidadeCards.setText("Vazio");
        jPanelCard.add(jLabelQuantidadeCards, new AbsoluteConstraints(0, 0, 197, 40));

        jScrollPaneCardsDeck.setBackground(new Color(0, 0, 0, 0));
        jScrollPaneCardsDeck.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneCardsDeck.setPreferredSize(new Dimension(200, 482));

        jListCardsDeck.setBackground(new Color(0, 0, 0, 0));
        jListCardsDeck.setFont(new Font("Tahoma", 0, 36)); // NOI18N
        jListCardsDeck.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListCardsDeck.setMaximumSize(new Dimension(197, 60));
        jListCardsDeck.setMinimumSize(new Dimension(197, 60));
        jListCardsDeck.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent evt) {
                jListCardsDeckMouseMoved(evt);
            }
        });
        jListCardsDeck.addMouseListener(new MouseAdapter() {
            public void mouseExited(MouseEvent evt) {
                jListCardsDeckMouseExited(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                jListCardsDeckMouseReleased(evt);
            }
        });
        jScrollPaneCardsDeck.setViewportView(jListCardsDeck);

        jPanelCard.add(jScrollPaneCardsDeck, new AbsoluteConstraints(0, 40, 197, 640));

        jPanelJanela.add(jPanelCard, new AbsoluteConstraints(1130, 50, 197, 680));

        jPanelFolha.setOpaque(false);
        jPanelFolha.setLayout(new AbsoluteLayout());

        jButtonLeft.setIcon(Images.COLECAO_BUTTON_LEFT);
        jButtonLeft.setBorder(null);
        jButtonLeft.setBorderPainted(false);
        jButtonLeft.setContentAreaFilled(false);
        jButtonLeft.setFocusPainted(false);
        jButtonLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonLeftActionPerformed(evt);
            }
        });
        jPanelFolha.add(jButtonLeft, new AbsoluteConstraints(0, 350, 50, 50));

        jButtonRight.setIcon(Images.COLECAO_BUTTON_RIGHT);
        jButtonRight.setBorderPainted(false);
        jButtonRight.setContentAreaFilled(false);
        jButtonRight.setFocusPainted(false);
        jButtonRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonRightActionPerformed(evt);
            }
        });
        jPanelFolha.add(jButtonRight, new AbsoluteConstraints(850, 350, 50, 50));

        jPanelMensagemPopUp.setOpaque(false);
        jPanelMensagemPopUp.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jPanelFolha.add(jPanelMensagemPopUp, new AbsoluteConstraints(0, 35, 900, 90));

        jPanelCards.setOpaque(false);
        jPanelCards.setLayout(new AbsoluteLayout());

        jPanelCard1.setOpaque(false);
        jPanelCard1.setLayout(new AbsoluteLayout());
        jPanelCards.add(jPanelCard1, new AbsoluteConstraints(0, 0, 200, 303));

        jPanelCard2.setOpaque(false);
        jPanelCard2.setLayout(new AbsoluteLayout());
        jPanelCards.add(jPanelCard2, new AbsoluteConstraints(200, 0, 200, 303));

        jPanelCard3.setOpaque(false);
        jPanelCard3.setLayout(new AbsoluteLayout());
        jPanelCards.add(jPanelCard3, new AbsoluteConstraints(400, 0, 200, 303));

        jPanelCard4.setOpaque(false);
        jPanelCard4.setLayout(new AbsoluteLayout());
        jPanelCards.add(jPanelCard4, new AbsoluteConstraints(600, 0, 200, 303));

        jPanelCard5.setOpaque(false);
        jPanelCard5.setLayout(new AbsoluteLayout());
        jPanelCards.add(jPanelCard5, new AbsoluteConstraints(0, 303, 200, 303));

        jPanelCard6.setOpaque(false);
        jPanelCard6.setLayout(new AbsoluteLayout());
        jPanelCards.add(jPanelCard6, new AbsoluteConstraints(200, 303, 200, 303));

        jPanelCard7.setOpaque(false);
        jPanelCard7.setLayout(new AbsoluteLayout());
        jPanelCards.add(jPanelCard7, new AbsoluteConstraints(400, 303, 200, 303));

        jPanelCard8.setOpaque(false);
        jPanelCard8.setLayout(new AbsoluteLayout());
        jPanelCards.add(jPanelCard8, new AbsoluteConstraints(600, 303, 200, 303));

        jPanelFolha.add(jPanelCards, new AbsoluteConstraints(50, 80, 800, 606));

        jPanelFiltros.setOpaque(false);
        jPanelFiltros.setLayout(new AbsoluteLayout());

        jPanelManas.setOpaque(false);
        jPanelManas.setLayout(new AbsoluteLayout());

        jLabelCusto0.setFont(new Font("Tahoma", 1, 16)); // NOI18N
        jLabelCusto0.setForeground(new Color(255, 255, 255));
        jLabelCusto0.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelCusto0.setText("0");
        jPanelManas.add(jLabelCusto0, new AbsoluteConstraints(0, 0, 30, 30));

        jLabelMana0.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelMana0.setHorizontalTextPosition(SwingConstants.CENTER);
        jPanelManas.add(jLabelMana0, new AbsoluteConstraints(0, 0, 30, 30));

        jLabelCusto1.setFont(new Font("Tahoma", 1, 16)); // NOI18N
        jLabelCusto1.setForeground(new Color(255, 255, 255));
        jLabelCusto1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelCusto1.setText("1");
        jPanelManas.add(jLabelCusto1, new AbsoluteConstraints(30, 0, 30, 30));

        jLabelMana1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelMana1.setHorizontalTextPosition(SwingConstants.CENTER);
        jPanelManas.add(jLabelMana1, new AbsoluteConstraints(30, 0, 30, 30));

        jLabelCusto2.setFont(new Font("Tahoma", 1, 16)); // NOI18N
        jLabelCusto2.setForeground(new Color(255, 255, 255));
        jLabelCusto2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelCusto2.setText("2");
        jPanelManas.add(jLabelCusto2, new AbsoluteConstraints(60, 0, 30, 30));

        jLabelMana2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelMana2.setHorizontalTextPosition(SwingConstants.CENTER);
        jPanelManas.add(jLabelMana2, new AbsoluteConstraints(60, 0, 30, 30));

        jLabelCusto3.setFont(new Font("Tahoma", 1, 16)); // NOI18N
        jLabelCusto3.setForeground(new Color(255, 255, 255));
        jLabelCusto3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelCusto3.setText("3");
        jPanelManas.add(jLabelCusto3, new AbsoluteConstraints(90, 0, 30, 30));

        jLabelMana3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelMana3.setHorizontalTextPosition(SwingConstants.CENTER);
        jPanelManas.add(jLabelMana3, new AbsoluteConstraints(90, 0, 30, 30));

        jLabelCusto4.setFont(new Font("Tahoma", 1, 16)); // NOI18N
        jLabelCusto4.setForeground(new Color(255, 255, 255));
        jLabelCusto4.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelCusto4.setText("4");
        jPanelManas.add(jLabelCusto4, new AbsoluteConstraints(120, 0, 30, 30));

        jLabelMana4.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelMana4.setHorizontalTextPosition(SwingConstants.CENTER);
        jPanelManas.add(jLabelMana4, new AbsoluteConstraints(120, 0, 30, 30));

        jLabelCusto5.setFont(new Font("Tahoma", 1, 16)); // NOI18N
        jLabelCusto5.setForeground(new Color(255, 255, 255));
        jLabelCusto5.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelCusto5.setText("5");
        jPanelManas.add(jLabelCusto5, new AbsoluteConstraints(150, 0, 30, 30));

        jLabelMana5.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelMana5.setHorizontalTextPosition(SwingConstants.CENTER);
        jPanelManas.add(jLabelMana5, new AbsoluteConstraints(150, 0, 30, 30));

        jLabelCusto6.setFont(new Font("Tahoma", 1, 16)); // NOI18N
        jLabelCusto6.setForeground(new Color(255, 255, 255));
        jLabelCusto6.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelCusto6.setText("6");
        jPanelManas.add(jLabelCusto6, new AbsoluteConstraints(180, 0, 30, 30));

        jLabelMana6.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelMana6.setHorizontalTextPosition(SwingConstants.CENTER);
        jPanelManas.add(jLabelMana6, new AbsoluteConstraints(180, 0, 30, 30));

        jLabelCusto7.setFont(new Font("Tahoma", 1, 16)); // NOI18N
        jLabelCusto7.setForeground(new Color(255, 255, 255));
        jLabelCusto7.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelCusto7.setText("7+");
        jPanelManas.add(jLabelCusto7, new AbsoluteConstraints(210, 0, 30, 30));

        jLabelMana7.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelMana7.setHorizontalTextPosition(SwingConstants.CENTER);
        jPanelManas.add(jLabelMana7, new AbsoluteConstraints(210, 0, 30, 30));

        jPanelFiltros.add(jPanelManas, new AbsoluteConstraints(15, 40, 240, 30));

        jComboBoxConjunto.setFont(new Font("Tahoma", 0, 12)); // NOI18N
        jComboBoxConjunto.setModel(new DefaultComboBoxModel<String>(Values.CONJUNTOS_CARTAS));
        jComboBoxConjunto.setFocusable(false);
        jComboBoxConjunto.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                jComboBoxConjuntoItemStateChanged(evt);
            }
        });
        jPanelFiltros.add(jComboBoxConjunto, new AbsoluteConstraints(340, 40, 190, 30));

        jTextFieldPesquisa.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldPesquisa.setForeground(new Color(150, 150, 150));
        jTextFieldPesquisa.setText("Pesquisar");
        jTextFieldPesquisa.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                jTextFieldPesquisaFocusGained(evt);
            }
            public void focusLost(FocusEvent evt) {
                jTextFieldPesquisaFocusLost(evt);
            }
        });
        jTextFieldPesquisa.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                jTextFieldPesquisaKeyPressed(evt);
            }
        });
        jPanelFiltros.add(jTextFieldPesquisa, new AbsoluteConstraints(600, 40, 250, 30));

        jPanelFolha.add(jPanelFiltros, new AbsoluteConstraints(0, 0, 900, 70));

        jLabelFolha.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelFolha.setIcon(Images.COLECAO_FOLHA);
        jPanelFolha.add(jLabelFolha, new AbsoluteConstraints(0, 0, 900, 700));

        jPanelJanela.add(jPanelFolha, new AbsoluteConstraints(190, 50, 900, 700));

        jLabelTextura.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTextura.setIcon(Images.COLECAO_FOLHA);
        jLabelTextura.setOpaque(true);
        jPanelJanela.add(jLabelTextura, new AbsoluteConstraints(0, 0, 1340, 748));

        GroupLayout jPanelBackgroundLayout = new GroupLayout(jPanelBackground);
        jPanelBackground.setLayout(jPanelBackgroundLayout);
        jPanelBackgroundLayout.setHorizontalGroup(jPanelBackgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanelBackgroundLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelJanela, GroupLayout.PREFERRED_SIZE, 1340, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelBackgroundLayout.setVerticalGroup(jPanelBackgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanelBackgroundLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelJanela, GroupLayout.PREFERRED_SIZE, 748, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jListCardsDeckMouseMoved(MouseEvent evt) {//GEN-FIRST:event_jListCardsDeckMouseMoved
        if (!SettingsView.staticIsVisible()) {
            int index = (evt.getY() - 1) / 30;
            if (index != lastIndex) {
                lastIndex = index;
                if (lastIndex < cardsListModel.size()) {
                    CardView c = (CardView) cardsListModel.elementAt(lastIndex);
                    popUpDispose();
                    popUp = c;
                    popUp.showPopUp();
                } else {
                    popUpDispose();
                }
            }
        }
    }//GEN-LAST:event_jListCardsDeckMouseMoved

    private void jListCardsDeckMouseExited(MouseEvent evt) {//GEN-FIRST:event_jListCardsDeckMouseExited
        popUpDispose();
    }//GEN-LAST:event_jListCardsDeckMouseExited

    private void jTextFieldNomeHeroiFocusLost(FocusEvent evt) {//GEN-FIRST:event_jTextFieldNomeHeroiFocusLost
        HERO_NAME.set(jTextFieldNomeHeroi.getText());
    }//GEN-LAST:event_jTextFieldNomeHeroiFocusLost

    private void jTextFieldNomeHeroiFocusGained(FocusEvent evt) {//GEN-FIRST:event_jTextFieldNomeHeroiFocusGained
        jTextFieldNomeHeroi.selectAll();
    }//GEN-LAST:event_jTextFieldNomeHeroiFocusGained

    private void jButtonNewDeckActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonNewDeckActionPerformed
        criarDeck();
    }//GEN-LAST:event_jButtonNewDeckActionPerformed

    private void jTextFieldPesquisaFocusGained(FocusEvent evt) {//GEN-FIRST:event_jTextFieldPesquisaFocusGained
        if (jTextFieldPesquisa.getText().equals("Pesquisar")) {
            jTextFieldPesquisa.setForeground(Color.BLACK);
            jTextFieldPesquisa.setText("");
        }
    }//GEN-LAST:event_jTextFieldPesquisaFocusGained

    private void jTextFieldPesquisaFocusLost(FocusEvent evt) {//GEN-FIRST:event_jTextFieldPesquisaFocusLost
        if (jTextFieldPesquisa.getText().replace(" ", "").isEmpty()) {
            jTextFieldPesquisa.setForeground(new Color(150, 150, 150));
            jTextFieldPesquisa.setText("Pesquisar");
        }
    }//GEN-LAST:event_jTextFieldPesquisaFocusLost

    private void jTextFieldPesquisaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            filtrar();
        }
    }//GEN-LAST:event_jTextFieldPesquisaKeyPressed

    private void jComboBoxConjuntoItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_jComboBoxConjuntoItemStateChanged
        filtrar();
    }//GEN-LAST:event_jComboBoxConjuntoItemStateChanged

    private void jButtonLeftActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonLeftActionPerformed
        Audios.playEfeitos(Audios.COLECAO_VOLTAR_PAGINA);
        pagina--;
        preencherPagina();
    }//GEN-LAST:event_jButtonLeftActionPerformed

    private void jButtonRightActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonRightActionPerformed
        Audios.playEfeitos(Audios.COLECAO_AVANCAR_PAGINA);
        pagina++;
        preencherPagina();
    }//GEN-LAST:event_jButtonRightActionPerformed

    private void jTextFieldNomeHeroiKeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTextFieldNomeHeroiKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jPanelJanela.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldNomeHeroiKeyPressed

    private void jButtonSettingsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonSettingsActionPerformed
        salvarColecao();
        SettingsView.main("Fechar Programa", () -> System.exit(0));
    }//GEN-LAST:event_jButtonSettingsActionPerformed

    private void jButtonPlayerVsPlayerActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonPlayerVsPlayerActionPerformed
        if (validarDeck()) {
            playerVsPlayer();
        }
    }//GEN-LAST:event_jButtonPlayerVsPlayerActionPerformed

    private void jTextFieldServerFocusLost(FocusEvent evt) {//GEN-FIRST:event_jTextFieldServerFocusLost
        SERVER.set(jTextFieldServer.getText());
    }//GEN-LAST:event_jTextFieldServerFocusLost

    private void jListCardsDeckMouseReleased(MouseEvent evt) {//GEN-FIRST:event_jListCardsDeckMouseReleased
        popUpDispose();
        int index = (evt.getY() - 1) / 30;
        if (index < cardsListModel.size()) {
            CardView c = (CardView) cardsListModel.elementAt(index);
            removerCard(c);
        }
    }//GEN-LAST:event_jListCardsDeckMouseReleased

    private void jListMeusDecksMouseReleased(MouseEvent evt) {//GEN-FIRST:event_jListMeusDecksMouseReleased
        int index = (evt.getY() - 1) / 45;
        Deck d = null;
        if (index < deckListModel.size()) {
            d = (Deck) deckListModel.elementAt(index);
        }
        if (d != null) {
            if (evt.getButton() == MouseEvent.BUTTON3) {
                menuPopUp(d);
            } else if (evt.getButton() == MouseEvent.BUTTON1 && (deck == null || !deck.equals(d))) {
                deck = d;
                cardsDeck = deck.getCardLabel();
                atualizarListaCards();
                limparFiltros();
            }
        }
    }//GEN-LAST:event_jListMeusDecksMouseReleased

    /**
     * Parar de tocar a música de fundo quando a janela for fechada
     *
     * @param evt
     */
    private void formWindowClosing(WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Audios.stopMusicaFundo();
    }//GEN-LAST:event_formWindowClosing

    private void jButtonImportarDeckActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonImportarDeckActionPerformed
        if (decks.size() >= 20) {
            showPopUp("Quantidade máxima de decks = 20!");
            return;
        }
        File f = Dir.janelaFile("Importar deck", ".hsd", "Hearthstone Deck");
        if (f == null) {
            return;
        }
        try {
            criarDeck(RW.readS(f).split("\n"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_jButtonImportarDeckActionPerformed

    private void jButtonPlayerVsPCActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonPlayerVsPCActionPerformed
        if (validarDeck()) {
            playerVsComputador();
        }
    }//GEN-LAST:event_jButtonPlayerVsPCActionPerformed

    private void jButtonCriarServidorActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonCriarServidorActionPerformed
        ServerPanel.main();
    }//GEN-LAST:event_jButtonCriarServidorActionPerformed

    /**
     * Instancia e abre a janela de coleção
     */
    public static void main() {
        Colecao[] hack = new Colecao[1];
        PopUpWorker.exe(new PopUpWorker.MySwingWorker(null, "Abrindo a janela de coleção") {
            @Override
            protected String doInBackground() throws Exception {
                hack[0] = new Colecao();
                return null;
            }

            @Override
            public void finish() {
                hack[0].setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButtonCriarServidor;
    private JButton jButtonImportarDeck;
    private JButton jButtonLeft;
    private JButton jButtonNewDeck;
    private JButton jButtonPlayerVsPC;
    private JButton jButtonPlayerVsPlayer;
    private JButton jButtonRight;
    private JButton jButtonSettings;
    private JComboBox<String> jComboBoxConjunto;
    private JLabel jLabelAbout;
    private JLabel jLabelCusto0;
    private JLabel jLabelCusto1;
    private JLabel jLabelCusto2;
    private JLabel jLabelCusto3;
    private JLabel jLabelCusto4;
    private JLabel jLabelCusto5;
    private JLabel jLabelCusto6;
    private JLabel jLabelCusto7;
    private JLabel jLabelDecks;
    private JLabel jLabelFolha;
    private JLabel jLabelMana0;
    private JLabel jLabelMana1;
    private JLabel jLabelMana2;
    private JLabel jLabelMana3;
    private JLabel jLabelMana4;
    private JLabel jLabelMana5;
    private JLabel jLabelMana6;
    private JLabel jLabelMana7;
    private JLabel jLabelQuantidadeCards;
    private JLabel jLabelServidor;
    private JLabel jLabelTextura;
    private JList<String> jListCardsDeck;
    private JList<Deck> jListMeusDecks;
    private JPanel jPanelBackground;
    private JPanel jPanelCabecalho;
    private JPanel jPanelCard;
    private JPanel jPanelCard1;
    private JPanel jPanelCard2;
    private JPanel jPanelCard3;
    private JPanel jPanelCard4;
    private JPanel jPanelCard5;
    private JPanel jPanelCard6;
    private JPanel jPanelCard7;
    private JPanel jPanelCard8;
    private JPanel jPanelCards;
    private JPanel jPanelDeck;
    private JPanel jPanelFiltros;
    private JPanel jPanelFolha;
    private JPanel jPanelJanela;
    private JPanel jPanelManas;
    private JPanel jPanelMensagemPopUp;
    private JScrollPane jScrollPaneCardsDeck;
    private JScrollPane jScrollPaneMeusDecks;
    private JTextField jTextFieldNomeHeroi;
    private JTextField jTextFieldPesquisa;
    private JTextField jTextFieldServer;
    // End of variables declaration//GEN-END:variables

    /**
     * Inicia os componentes, configura os painéis, preenche com os valores
     * salvos ou padrões, etc...
     */
    private void init() {
        getGlassPane().setCursor(MouseCursor.DEFAULT);
        getGlassPane().setVisible(true);
        Img.aplicarTextura(Images.COLECAO_TEXTURA, jLabelTextura);
        jScrollPaneMeusDecks.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        jScrollPaneCardsDeck.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        cloneLabels();
        jTextFieldNomeHeroi.setText(loadHeroName());
        server = SERVER.getString();
        jTextFieldServer.setText(server);
        initDeck();
        jListMeusDecks.setCellRenderer(new DeckListCellRenderer());
        jListMeusDecks.setModel(deckListModel);
        jListCardsDeck.setCellRenderer(new CardListCellRenderer());
        jListCardsDeck.setModel(cardsListModel);
        atualizarListaDecks();
        jPanelJanela.requestFocus();
        if (!decks.isEmpty()) {
            deck = decks.get(0);
            cardsDeck = deck.getCardLabel();
            jListMeusDecks.setSelectedIndex(0);
            atualizarListaCards();
            limparFiltros();
        } else {
            filtrar();
        }
        Audios.playMusicaFundo(Audios.MUSICA_FUNDO_COLECAO);
    }

    /**
     * Adiciona um deck à lista de decks criados
     *
     * @param deck deck adicionado
     */
    private void adicionarDeck(Deck deck) {
        decks.add(deck);
        decksDB.add(deck.getDb());
        deckListModel.addElement(deck);
        salvarColecao();
        jListMeusDecks.setSelectedIndex(deckListModel.getSize() - 1);
        repaint();
        this.deck = deck;
        cardsDeck = this.deck.getCardLabel();
        filtrar();
        atualizarListaCards();
    }

    /**
     * Remove um deck da lista de decks criados
     *
     * @param deck deck removido
     */
    private void removeDeck(Deck deck) {
        Audios.playEfeitos(Audios.COLECAO_REMOVER_DECK);
        decksDB.remove(deck.getDb());
        decks.remove(deck);
        deckListModel.removeElement(deck);
        this.deck = decks.isEmpty() ? null : decks.get(0);
        jListMeusDecks.setSelectedIndex(deckListModel.isEmpty() ? -1 : 0);
        repaint();
        cardsDeck = this.deck == null ? new ArrayList<>() : this.deck.getCardLabel();
        salvarColecao();
        atualizarListaCards();
    }

    /**
     * Adiciona um card no deck
     *
     * @param id id do card adicionado
     */
    private void adicionarCard(String id) {
        if (deck != null) {
            try {
                deck.addCard(id);
                Audios.playEfeitos(Audios.COLECAO_ADD_CARD);
                sortCards();
                atualizarListaCards();
            } catch (Exception ex) {
                showPopUp(ex.getMessage());
            }
        } else {
            showPopUp("Selecione um deck!");
        }
    }

    /**
     * Remove um card do deck
     *
     * @param card card removido
     */
    private void removerCard(CardView card) {
        if (jListMeusDecks.getSelectedValue() == null || !jListMeusDecks.getSelectedValue().getCardLabel().contains(card)) {
            deck = jListMeusDecks.getSelectedValue();
            cardsDeck = deck == null ? new ArrayList<>() : deck.getCardLabel();
            limparFiltros();
            atualizarListaCards();
        } else {
            Audios.playEfeitos(Audios.COLECAO_REMOVER_CARD);
            deck.removeCard(card.getIdCard());
            atualizarListaCards();
        }
    }

    /**
     * Ordena a lista de cards do deck pelo custo e nome
     */
    private void sortCards() {
        cardsDeck.sort(Sort.custoCardLabel());
    }

    /**
     * Atualiza a lista de cards da JList
     */
    private void atualizarListaCards() {
        sortCards();
        cardsListModel.removeAllElements();
        cardsDeck.forEach(cardsListModel::addElement);
        int size = deck.getList().size();
        jLabelQuantidadeCards.setText(((size == 0) ? "Vazio" : (size == 1) ? " 1 Card" : size + " Cards"));
        repaint();
    }

    /**
     * Atualiza a lista de decks da JList
     */
    private void atualizarListaDecks() {
        deckListModel.removeAllElements();
        decks.forEach(deckListModel::addElement);
    }

    /**
     * Exibe um menu popUp ao clicar com o botão direito do mouse em cima de um
     * deck
     *
     * @param deck deck que recebeu o clique do mouse
     */
    private void menuPopUp(Deck deck) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem renomear = new JMenuItem("Renomear");
        JMenuItem alterarVerso = new JMenuItem("Alterar Verso");
        JMenuItem exportar = new JMenuItem("Exportar");
        JMenuItem excluir = new JMenuItem("Excluir");
        renomear.addActionListener((ActionEvent ae) -> {
            String nome1 = JOptionPane.showInputDialog("Digite o novo nome do deck:");
            if (nome1 != null) {
                deck.setNome(nome1);
                salvarColecao();
                repaint();
                revalidate();
            }
        });
        alterarVerso.addActionListener((ActionEvent ae) -> {
            int selected = EscolherCardBack.main(deck.getCardBack());
            if (selected != -1) {
                deck.setCardBack(selected);
                salvarColecao();
            }
        });
        exportar.addActionListener(ae -> {
            File file = Dir.janelaFileSalvar("Salvar deck", deck.getNome(), ".hsd", "Hearthstone Deck");
            if (file == null) {
                return;
            }
            StringBuilder sb = new StringBuilder(deck.getClasse());
            sb.append(("\n" + deck.getNome()));
            deck.getList().forEach(id -> sb.append(("\n" + CARTAS.findCardById(id).getName())));
            try {
                RW.writeS(file, sb.toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ERRO! - " + ex);
            }
        });
        excluir.addActionListener((ActionEvent ae) -> {
            int flag = JOptionPane.showConfirmDialog(this,
                    "Confirmar exclusão do deck " + deck.getNome() + "?",
                    "Excluir Deck", JOptionPane.YES_NO_OPTION);
            if (flag == 0) {
                removeDeck(deck);
            }
        });
        menu.add(renomear);
        menu.add(alterarVerso);
        menu.add(exportar);
        menu.add(excluir);
        menu.show(this, getMousePosition().x, getMousePosition().y);
    }

    /**
     * Salva a coleção atual
     */
    public void salvarColecao() {
        RWObj.write(decksDB, PATH_DECKS);
    }

    /**
     * Fecha a janela popUp de exibição de um card
     */
    private void popUpDispose() {
        if (popUp != null) {
            popUp.dispose();
        }
    }

    /**
     * Carrega o nome do herói salvo. Caso não exista o arquivo salvo, pede ao
     * usuário para digitar um nome.
     *
     * @return último nome salvo ou o nome digitado pelo usuário.
     */
    private String loadHeroName() {
        String name = HERO_NAME.getString();
        while ((name == null) || name.trim().isEmpty()) {
            name = JOptionPane.showInputDialog("Digite um nome para seu Herói:");
        }
        return HERO_NAME.set(name);
    }

    /**
     * Criar um novo deck. Pede ao usuário para que digite um nome para o deck,
     * uma classe e escolher o verso de card.
     */
    private void criarDeck() {
        if (decks.size() < 20) {
            String nome = JOptionPane.showInputDialog("Digite um nome para o deck:");
            if (nome != null) {
                JList jList = new JList(Values.NOME_HEROIS_PT);
                jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                jList.setSelectedIndex(0);
                int op = JOptionPane.showConfirmDialog(null, jList, "Selecione um Herói:", JOptionPane.OK_CANCEL_OPTION);
                int classe = jList.getSelectedIndex();
                if (op == 0 && classe != -1) {
                    int cardBack = EscolherCardBack.main(-1);
                    if (cardBack != -1) {
                        adicionarDeck(new Deck(Values.NOME_HEROIS_EN[classe], nome, cardBack));
                    }
                }
            }
        } else {
            showPopUp("Quantidade máxima de decks = 20!");
        }
    }

    /**
     * Clona os objetos da janela para melhor uso das implementações
     */
    private void cloneLabels() {
        labelMana[0] = jLabelMana0;
        labelMana[1] = jLabelMana1;
        labelMana[2] = jLabelMana2;
        labelMana[3] = jLabelMana3;
        labelMana[4] = jLabelMana4;
        labelMana[5] = jLabelMana5;
        labelMana[6] = jLabelMana6;
        labelMana[7] = jLabelMana7;
        labelCusto[0] = jLabelCusto0;
        labelCusto[1] = jLabelCusto1;
        labelCusto[2] = jLabelCusto2;
        labelCusto[3] = jLabelCusto3;
        labelCusto[4] = jLabelCusto4;
        labelCusto[5] = jLabelCusto5;
        labelCusto[6] = jLabelCusto6;
        labelCusto[7] = jLabelCusto7;
        collectionPanel[0] = jPanelCard1;
        collectionPanel[1] = jPanelCard2;
        collectionPanel[2] = jPanelCard3;
        collectionPanel[3] = jPanelCard4;
        collectionPanel[4] = jPanelCard5;
        collectionPanel[5] = jPanelCard6;
        collectionPanel[6] = jPanelCard7;
        collectionPanel[7] = jPanelCard8;
        for (int i = 0; i < labelCusto.length; i++) {
            labelMana[i].setIcon(manaOff);
            labelMana[i].setName(Integer.toString(i));
            labelCusto[i].addMouseListener(filtroManaMouseEvent(i));
        }
        for (JPanel p : collectionPanel) {
            p.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent evt) {
                    adicionarCard(p.getName());
                }
            });
        }
    }

    /**
     * Limpa o filtro das manas
     */
    public void limparFiltroManas() {
        for (JLabel l : labelMana) {
            l.setIcon(manaOff);
        }
    }

    /**
     * Realiza a filtragem dos cards de acordo com as configurações de filtro
     * atuais
     */
    private void filtrar() {
        int custo = -1;
        for (JLabel l : labelMana) {
            if (l.getIcon().equals(manaOn)) {
                custo = Integer.parseInt(l.getName());
            }
        }
        String colecao = jComboBoxConjunto.getSelectedItem().toString();
        String pesquisar = jTextFieldPesquisa.getText();
        pesquisar = pesquisar.equals("Pesquisar") || pesquisar.replace(" ", "").isEmpty() ? "" : pesquisar.toLowerCase();
        collection = getCollection(colecao);
        filtrarColecionaveis();
        filtrarCusto(custo);
        filtrarPesquisa(pesquisar);
        sortCollection();
        pagina = 1;
        preencherPagina();
    }

    /**
     * Filtrar cards por conjunto
     *
     * @param conjunto nome do conjunto de cards. Conjunto = "Todos" para
     * retornar todos os conjuntos.
     * @return lista de cards filtrados
     */
    private List<Card> getCollection(String conjunto) {
        switch (conjunto) {
            case Values.TODOS:
                return CARTAS.getAllCards();
            case Values.BASIC:
                return CARTAS.getBasic();
            case Values.BLACKROCK_MOUNTAIN:
                return CARTAS.getBlackrockMountain();
            case Values.CLASSIC:
                return CARTAS.getClassic();
            case Values.CREDITS:
                return CARTAS.getCredits();
            case Values.CURSE_OF_NAXXRAMAS:
                return CARTAS.getCurseOfNaxxramas();
            case Values.DEBUG:
                return CARTAS.getDebug();
            case Values.GOBLINS_VS_GNOMES:
                return CARTAS.getGoblinsVsGnomes();
            case Values.HERO_SKINS:
                return CARTAS.getHeroSkins();
            case Values.LEAGUE_OF_EXPLORERS:
                return CARTAS.getLeagueOfExplorers();
            case Values.MISSIONS:
                return CARTAS.getMissions();
            case Values.PROMOTION:
                return CARTAS.getPromotion();
            case Values.REWARD:
                return CARTAS.getReward();
            case Values.SYSTEM:
                return CARTAS.getSystem();
            case Values.TAVERN_BRAWL:
                return CARTAS.getTavernBrawls();
            case Values.THE_GRAND_TOURNAMENT:
                return CARTAS.getTheGrandTournaments();
            default:
                return new ArrayList<>();
        }
    }

    /**
     * Retira da lista todos os cards que não são colecionáveis (não podem ser
     * adicionados a um deck)
     */
    private void filtrarColecionaveis() {
        for (int i = 0; i < collection.size(); i++) {
            if (!collection.get(i).isCollectible()) {
                collection.remove(i--);
            }
        }
    }

    /**
     * Filtra os cards pelo custo
     *
     * @param custo custo a ser filtrado. Custo == 7 filtra todos os cards com
     * custo MAIOR ou IGUAL a 7
     */
    private void filtrarCusto(int custo) {
        if (custo != -1) {
            for (int i = 0; i < collection.size(); i++) {
                if ((custo == 7 && collection.get(i).getCost() < custo)
                        || collection.get(i).getCost() != custo) {
                    collection.remove(i--);
                }
            }
        }
    }

    /**
     * Filtrar os cards pelo texto digitado na caixa de pesquisa. Pesquisa o
     * texto nos atributos: nome, texto, raça e raridade.
     *
     * @param pesquisar texto digitado na caixa de pesquisa
     */
    private void filtrarPesquisa(String pesquisar) {
        if (!pesquisar.isEmpty()) {
            for (int i = 0; i < collection.size(); i++) {
                String name = collection.get(i).getName();
                String text = collection.get(i).getText();
                String race = collection.get(i).getRace();
                String rarity = collection.get(i).getRarity();
                if ((name == null || !name.toLowerCase().contains(pesquisar))
                        && (text == null || !text.toLowerCase().contains(pesquisar))
                        && (race == null || !race.toLowerCase().contains(pesquisar))
                        && (rarity == null || !Utils.traduzirRaridade(rarity).toLowerCase().contains(pesquisar))) {
                    collection.remove(i--);
                }
            }
        }
    }

    /**
     * Exibe os cards na página
     */
    private void preencherPagina() {
        jButtonLeft.setEnabled(pagina != 1);
        jButtonLeft.setVisible(pagina != 1);
        jButtonRight.setEnabled(pagina * 8 < collection.size());
        jButtonRight.setVisible(pagina * 8 < collection.size());
        int ini = (pagina - 1) * 8;
        int end = pagina * 8;
        for (int i = ini; i < end; i++) {
            collectionPanel[i % 8].removeAll();
            collectionPanel[i % 8].add((i < collection.size()
                    ? new JLabel(Images.getCardIcon(Images.CARD_MEDIUM, collection.get(i).getId()))
                    : new JLabel()), new AbsoluteConstraints(0, 0));
            if (i < collection.size()) {
                collectionPanel[i % 8].setName(collection.get(i).getId());
            }
        }
        repaint();
        revalidate();
    }

    /**
     * Ordena os cards de acordo com o custo e nome, exibindo primeiro os cards
     * da classe e adiciona-os à lista collection.
     */
    private void sortCollection() {
        String classe = deck != null ? deck.getClasse() : "";
        List<Card> cardGeral = new ArrayList<>();
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getPlayerClass() == null) {
                cardGeral.add(collection.remove(i--));
            } else if (!collection.get(i).getPlayerClass().equals(classe)) {
                collection.remove(i--);
            }
        }
        collection.sort(Sort.custo());
        cardGeral.sort(Sort.custo());
        collection.addAll(cardGeral);
    }

    /**
     * Limpa todos os filtros e atualiza a coleção sem os filtros
     */
    private void limparFiltros() {
        limparFiltroManas();
        jTextFieldPesquisa.setForeground(new Color(150, 150, 150));
        jTextFieldPesquisa.setText("Pesquisar");
        jComboBoxConjunto.setSelectedIndex(0);
        filtrar();
    }

    /**
     * Exibe uma mensagem popUp na tela
     *
     * @param text texto a ser exibido
     */
    public void showPopUp(String text) {
        jPanelMensagemPopUp.removeAll();
        final PopUp showPopUp = new PopUp(text, 36);
        jPanelMensagemPopUp.add(showPopUp);
        jPanelMensagemPopUp.revalidate();
        jPanelJanela.requestFocus();
        new Thread(() -> {
            Utils.sleep(3000);
            for (float j = 19; j >= 0; j--) {
                final float jFinal = j;
                SwingUtilities.invokeLater(() -> showPopUp.setIcon(Img.transparencia(showPopUp.getImageIcon(), jFinal / 20f)));
                Utils.sleep(50);
            }
            SwingUtilities.invokeLater(() -> jPanelMensagemPopUp.remove(showPopUp));
        }).start();
    }

    /**
     * Verifica se há algum deck selecionado e se é válido.
     */
    private boolean validarDeck() {
        if (deck == null) {
            showPopUp("Selecione um deck!");
            return false;
        } else if (deck.getList().size() < 30) {
            showPopUp("O deck está incompleto!");
            return false;
        }
        return true;
    }

    /**
     * MouseEvent para um clique no labelMana para filtrar cards pelo custo
     *
     * @param i índice do labelMana
     * @return MouseEvent padrão para os labels
     */
    private MouseAdapter filtroManaMouseEvent(int i) {
        return new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent evt) {
                boolean flag = labelMana[i].getIcon().equals(manaOff);
                limparFiltroManas();
                labelMana[i].setIcon(flag ? manaOn : manaOff);
                filtrar();
            }
        };
    }

    /**
     * Verifica se o texto digitado atende ao padrão requerido (ip + porta de 4
     * ou 5 dígitos)
     *
     * @return {@code true} ou {@code false}
     */
    private boolean validateServer() {
        server = jTextFieldServer.getText();
        if (!Utils.checkRegex("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}.\\d{1,3}[\\:]\\d{4,5}$", server)) {
            Utils.error("Servidor inválido");
            return false;
        }
        port = Integer.parseInt(server.substring(server.indexOf(":") + 1));
        server = server.replace(":" + port, "");
        return true;
    }

    /**
     * Carregar a coleção de decks
     */
    private void initDeck() {
        //verifica se há algum arquivo salvo para abrir
        //se não tiver, carrega os decks padrões do jogo
        if (PATH_DECKS.exists()) {
            loadDecks();
        } else {
            loadDecksDefault();
        }
    }

    /**
     * Carrega os decks salvos
     */
    private void loadDecks() {
        List<DataBase> dataBase = RWObj.read(PATH_DECKS, List.class);
        if (dataBase != null) {
            dataBase.stream().forEach(db -> {
                Deck d = new Deck(db.getClasse(), db.getNome(), db.getCardback());
                db.getIds().forEach(id -> {
                    try {
                        d.addCard(id);
                    } catch (Exception ex) {
                        //ignorar
                    }
                });

                decks.add(d);
                decksDB.add(d.getDb());
            });
        } else {
            loadDecksDefault();
        }
    }

    /**
     * Cria a coleção com os decks padrões do jogo
     */
    private void loadDecksDefault() {
        Util.getDecks().forEach(ids -> {
            try {
                criarDeck(ids);
            } catch (Exception ex) {
                //ignorar
            }
        });
    }

    /**
     * Cria um novo deck a partir de um array e adicioná-lo à coleção
     *
     * @param ids array de string com a classe no índice zero, nome do deck no
     * índice 1 e as cartas nos demais índices
     *
     * @exception Exception falha ao adicionar uma carta no deck
     */
    public void criarDeck(String[] ids) throws Exception {
        Deck d = new Deck(ids[0], ids[1], 19);
        for (int i = 2; i < ids.length; i++) {
            d.addCard(CARTAS.findIdByName(ids[i]));
        }
        adicionarDeck(d);
    }

    /**
     * Conectar ao servidor e aguardar um adversário para main uma partida
     *
     * @return objeto runnable para executar em uma nova thread
     */
    private void connect(String servidor, int porta) {
        SwingUtils.runOnUIThread(() -> setVisible(false));
        Audios.stopMusicaFundo();
        Heroi heroi = new Heroi(HERO_NAME.getString(), Param.HEROI, deck.getClasse(),
                deck.getDeckEmbaralhado(), CARDBACK.get(deck.getCardBack()));
        Runnable callback = () -> {
            SwingUtils.runOnUIThread(() -> setVisible(true));
            Audios.playMusicaFundo(Audios.MUSICA_FUNDO_COLECAO);
        };
        Cliente.connect(servidor, porta, heroi, callback);
    }

    /**
     * Inicia uma partida player contra a inteligência artificial
     */
    private void playerVsComputador() {
        DefaultListModel dlm = new DefaultListModel();
        dlm.addElement("Aleatório");
        decks.forEach(dlm::addElement);
        JList jlist = new JList(dlm);
        jlist.setSelectedIndex(0);
        int op = JOptionPane.showConfirmDialog(this, jlist, "Escolha o deck do oponente", JOptionPane.OK_CANCEL_OPTION);
        if ((op == JOptionPane.OK_OPTION) && (jlist.getSelectedIndex() != -1)) {
            PopUpWorker.exe(new PopUpWorker.MySwingWorker(this, "Aguarde um momento...") {
                @Override
                protected String doInBackground() throws Exception {
                    HearthStoneIA.main(getDeckOponente(jlist.getSelectedValue()));
                    Utils.sleep(2000);
                    return null;
                }

                @Override
                public void finish() {
                    connect("localhost", 12491);
                }
            });
        }
    }

    /**
     * Retorna um array com:
     * <br> [0] = classe
     * <br> [1] = nome do deck
     * <br> [2...31] ids das cartas do deck
     *
     * @param object objeto Deck escolhido na JList
     * @return deck em formato array ou null para objeto não é deck
     */
    private String[] getDeckOponente(Object object) {
        try {
            Deck d = (Deck) object;
            List<String> ids = new ArrayList<>();
            ids.add(d.getClasse());
            ids.add(d.getNome());
            ids.addAll(d.getList());
            return ids.toArray(new String[0]);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Inicia uma partida player vs player via web
     */
    private void playerVsPlayer() {
        if (!validateServer()) {
            return;
        }
        Update.checkVersion(new Update.CheckVersionListener() {
            @Override
            public void check(boolean check) {
                if (check) {
                    connect(server, port);
                } else {
                    Utils.error("É necessário utilizar a versão mais recente para jogar Player vs Player");
                    Update.askUpdate(""::toString);
                }
            }

            @Override
            public void offline() {
                if (Utils.confirm("Não foi possível identificar a versão do programa. Deseja continuar?")) {
                    connect(server, port);
                }
            }
        });
    }
}
