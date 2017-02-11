package com.limagiran.hearthstone.deck;

import com.limagiran.hearthstone.util.Img;
import com.limagiran.hearthstone.util.DimensionValues;
import com.limagiran.hearthstone.util.Images;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.border.LineBorder;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Vinicius Silva
 */
public class CardView extends JPanel {

    private final JLabel backgroundImage;
    private final JLabel nome;
    private final JLabel imagem;
    private final JLabel custoImage;
    private final JLabel custo;
    private final JLabel duplo;
    private final String nome_card;
    private final String id_card;
    private final int custo_card;
    private final JFrame popUp = new JFrame();

    public CardView(String nome_card, String id_card, int custo_card) {
        this.nome_card = nome_card;
        this.id_card = id_card;
        this.custo_card = custo_card;
        this.nome = new JLabel(this.nome_card);
        ImageIcon icon = Images.getCardIcon(Images.CARD_ORIGINAL, this.id_card);
        backgroundImage = new JLabel(Img.recortar(icon, 50, 138, 197, 30));
        icon = null;
        imagem = new JLabel("", SwingConstants.CENTER);
        custoImage = new JLabel(Images.DECK_CUSTO);
        custo = new JLabel(Integer.toString(this.custo_card), JLabel.CENTER);
        duplo = new JLabel("2", JLabel.CENTER);
        init();
    }

    /**
     * Configura os componentes do view
     */
    private void init() {
        popUp.setLayout(new AbsoluteLayout());
        popUp.setUndecorated(true);
        popUp.setBackground(new Color(0, 0, 0, 0));
        popUp.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        popUp.add(imagem, new AbsoluteConstraints(0, 0));
        popUp.setSize(DimensionValues.CARD_ORIGINAL);
        popUp.setLocationRelativeTo(null);
        popUp.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                dispose();
            }
        });
        nome.setBackground(new Color(0, 0, 0, 150));
        nome.setOpaque(true);
        nome.setFont(new Font("Arial Narrow", Font.PLAIN, 14));
        nome.setForeground(Color.WHITE);
        custo.setFont(new Font("Tahoma", Font.PLAIN, 18));
        custo.setForeground(Color.WHITE);
        backgroundImage.setBorder(new LineBorder(Color.WHITE, 1));
        duplo.setBackground(new Color(0, 0, 0, 150));
        duplo.setOpaque(true);
        duplo.setFont(new Font("Tahoma", Font.BOLD, 18));
        duplo.setForeground(new Color(128, 128, 0));
        duplo.setVisible(false);
        setLayout(new AbsoluteLayout());
        add(duplo, new AbsoluteConstraints(177, 1, 20, 28));
        add(custo, new AbsoluteConstraints(0, 0, 32, 30));
        add(custoImage, new AbsoluteConstraints(5, 0));
        add(nome, new AbsoluteConstraints(32, 5));
        add(backgroundImage, new AbsoluteConstraints(0, 0, 197, 30));
    }

    /**
     * Exibe/Oculta o número 2 (dois) no view para indicar 2 cards iguais no
     * deck
     *
     * @param flag {@code true} para exibir ou {@code false} para ocultar
     */
    protected void setDuploVisible(boolean flag) {
        duplo.setVisible(flag);
    }

    public String getIdCard() {
        return id_card;
    }

    public String getNome() {
        return nome_card;
    }

    public int getCost() {
        return custo_card;
    }

    /**
     * Exibe um popUp do card centralizado na tela
     */
    public void showPopUp() {
        imagem.setIcon(Images.getCardIcon(Images.CARD_ORIGINAL, id_card));
        popUp.setVisible(true);
    }

    /**
     * Oculta o popUp do card
     */
    public void dispose() {
        imagem.setIcon(null);
        popUp.dispose();
    }
}