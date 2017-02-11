package com.limagiran.hearthstone.deck;

import com.limagiran.hearthstone.card.control.Card;
import com.limagiran.hearthstone.util.Utils;
import com.limagiran.hearthstone.util.Images;
import static com.limagiran.hearthstone.HearthStone.CARTAS;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import com.limagiran.hearthstone.util.Audios;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius Silva
 */
public class Deck extends JPanel {

    private static final Exception EX_DECK_CHEIO = new Exception("O deck está cheio!");
    private static final Exception EX_DUAS_COPIAS = new Exception("O deck já contém 2 cópias do card selecionado!");
    private static final Exception EX_LENDARIO_JA_EXISTE = new Exception("O deck já contém este card lendário!");
    private static final Exception EX_CLASSE_INCORRETA = new Exception("Card inválido para esta classe!");
    private static final Exception EX_CARD_NULL = new Exception("Card não encontrado!");
    private static final Exception EX_CARD_INVALIDO = new Exception("Card inválido!");

    public long id = System.nanoTime();
    private final String classe;
    private final List<String> cards = new ArrayList<>();
    private final JLabel backgroundImage;
    private final JLabel nome;
    private int cardBack;
    private final List<CardView> cardLabel = new ArrayList<>();
    private final DataBase db;

    /**
     * Cria um novo deck
     *
     * @param classe classe do deck
     * @param nome nome do deck
     * @param cardBack id do verso do card
     */
    public Deck(String classe, String nome, int cardBack) {
        this.cardBack = cardBack;
        this.classe = classe;
        this.nome = new JLabel(nome, JLabel.CENTER);
        db = new DataBase(classe, nome, cardBack, cards);
        backgroundImage = new JLabel(getImageIcon());
        init();
    }

    /**
     * Adiciona um card ao deck
     *
     * @param id id do card adicionado
     * @throws Exception retorna uma exceção com a mensagem do motivo da
     * exceção. (card não encontrado, deck cheio, limite de duas cópias, etc...)
     */
    public void addCard(String id) throws Exception {
        Card card = CARTAS.findCardById(id);
        if (card == null) {
            throw EX_CARD_NULL;
        } else if (cards.size() >= 30) {
            Audios.playEfeitos(Audios.COLECAO_ERRO_LIMITE);
            throw EX_DECK_CHEIO;
        } else if (isDuplo(id)) {
            Audios.playEfeitos(Audios.COLECAO_ERRO_LIMITE);
            throw EX_DUAS_COPIAS;
        } else if (cards.contains(id) && card.isLendario()) {
            Audios.playEfeitos(Audios.COLECAO_ERRO_LIMITE);
            throw EX_LENDARIO_JA_EXISTE;
        } else if (card.getPlayerClass() != null && !card.getPlayerClass().equals(classe)) {
            throw EX_CLASSE_INCORRETA;
        } else if (card.getType().equals(Values.LACAIO)
                && card.getType().equals(Values.FEITICO)
                && card.getType().equals(Values.ARMA)) {
            throw EX_CARD_INVALIDO;
        }
        if (cards.contains(id)) {
            setDuplo(id, true);
        } else {
            cardLabel.add(new CardView(card.getName(), card.getId(), card.getCost()));
        }
        cards.add(id);
    }

    /**
     * Remove um card do deck
     *
     * @param id id do card removido
     */
    public void removeCard(String id) {
        if (isDuplo(id)) {
            setDuplo(id, false);
            cards.remove(cards.lastIndexOf(id));
        } else {
            removeCardLabel(id);
            cards.remove(id);
        }
    }

    public List<String> getList() {
        return cards;
    }

    public String getNome() {
        return nome.getText();
    }

    public void setNome(String nome) {
        this.nome.setText(nome);
        db.setNome(nome);
    }

    public String getClasse() {
        return classe;
    }

    /**
     * Imagem do herói da classe selecionada
     *
     * @return ImageIcon do herói
     */
    private ImageIcon getImageIcon() {
        switch (classe) {
            case Values.GUERREIRO:
                return Images.DECK_HERO_GUERREIRO;
            case Values.XAMA:
                return Images.DECK_HERO_XAMA;
            case Values.LADINO:
                return Images.DECK_HERO_LADINO;
            case Values.PALADINO:
                return Images.DECK_HERO_PALADINO;
            case Values.CACADOR:
                return Images.DECK_HERO_CACADOR;
            case Values.DRUIDA:
                return Images.DECK_HERO_DRUIDA;
            case Values.BRUXO:
                return Images.DECK_HERO_BRUXO;
            case Values.MAGO:
                return Images.DECK_HERO_MAGO;
            case Values.SACERDOTE:
                return Images.DECK_HERO_SACERDOTE;
        }
        return new ImageIcon();
    }

    /**
     * Configura os componentes da view
     */
    private void init() {
        nome.setBackground(new Color(0, 0, 0, 110));
        nome.setOpaque(true);
        nome.setFont(new Font("Arial Narrow", Font.BOLD, 16));
        nome.setForeground(Color.WHITE);
        backgroundImage.setBorder(new LineBorder(Color.WHITE, 1));
        setLayout(new AbsoluteLayout());
        add(nome, new AbsoluteConstraints(1, 27, 128, 16));
        add(backgroundImage, new AbsoluteConstraints(0, 0, 130, 45));
    }

    /**
     * Se o deck está selecionado ou não
     *
     * @param flag {@code true} ou {@code false}
     */
    public void setSelected(boolean flag) {
        backgroundImage.setBorder(flag
                ? new LineBorder(Color.RED, 2)
                : new LineBorder(Color.WHITE, 1));
    }

    public int getCardBack() {
        return cardBack;
    }

    public void setCardBack(int cardBack) {
        this.cardBack = cardBack;
        db.setCardback(cardBack);
    }

    public List<CardView> getCardLabel() {
        return cardLabel;
    }

    /**
     * Verifica se já existem duas cópias do card no deck.
     *
     * @param id id do card
     * @return {@code true} ou {@code false}
     */
    private boolean isDuplo(String id) {
        return cards.contains(id) && cards.indexOf(id) != cards.lastIndexOf(id);
    }

    /**
     * Exibe/Oculta o número 2 (dois) no view para indicar 2 cards iguais no
     * deck
     *
     * @param id id do card
     * @param flag {@code true} para exibir ou {@code false} para ocultar
     */
    private void setDuplo(String id, boolean flag) {
        cardLabel.stream().filter((card) -> (card.getIdCard().equals(id))).forEach((cl) -> {
            cl.setDuploVisible(flag);
        });
    }

    /**
     * Remove o view de um card removido do deck
     *
     * @param id do card removido
     */
    private void removeCardLabel(String id) {
        for (int i = 0; i < cardLabel.size(); i++) {
            if (cardLabel.get(i).getIdCard().equals(id)) {
                cardLabel.remove(i);
                break;
            }
        }
    }

    /**
     * Cria um List Card com os cards do deck embaralhados
     *
     * @return List Card
     */
    public List<Card> getDeckEmbaralhado() {
        List<Card> retorno = new ArrayList<>();
        List<Integer> embaralhado = new ArrayList<>();
        while (embaralhado.size() != getList().size()) {
            Integer index = Utils.random(getList().size()) - 1;
            if (!embaralhado.contains(index)) {
                embaralhado.add(index);
            }
        }
        for (Integer index : embaralhado) {
            retorno.add(CARTAS.createCard(getList().get(index)));
        }
        return retorno;
    }

    /**
     * Cria um List String com os ids dos cards do deck embaralhados
     *
     * @return List id dos cards
     */
    public List<String> getDeckEmbaralhadoIA() {
        List<String> retorno = new ArrayList<>();
        List<Integer> embaralhado = new ArrayList<>();
        while (embaralhado.size() != getList().size()) {
            Integer index = Utils.random(getList().size()) - 1;
            if (!embaralhado.contains(index)) {
                embaralhado.add(index);
            }
        }
        for (Integer index : embaralhado) {
            retorno.add(getList().get(index));
        }
        return retorno;
    }

    public DataBase getDb() {
        return db;
    }

    @Override
    public String toString() {
        return getNome();
    }
}