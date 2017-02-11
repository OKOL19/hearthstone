package  com.limagiran.hearthstone.deck;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Vinicius
 */
public class DataBase implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final String classe;
    private String nome;
    private int cardback;
    private final List<String> ids;

    public DataBase(String classe, String nome, int cardback, List<String> ids) {
        this.classe = classe;
        this.nome = nome;
        this.cardback = cardback;
        this.ids = ids;
    }

    public String getClasse() {
        return classe;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCardback() {
        return cardback;
    }

    public void setCardback(int cardback) {
        this.cardback = cardback;
    }

    public List<String> getIds() {
        return ids;
    }
}