package  com.limagiran.hearthstone.util;

import java.io.Serializable;

/**
 *
 * @author Vinicius Silva
 */
public class GamePlay implements Serializable {

    public static final GamePlay INSTANCE = loadGamePlayConfig();
    private int vidaTotalHeroi = 30;
    private int cartasNaMao = 10;
    private int shieldInicial = 0;

    private static GamePlay loadGamePlayConfig() {
        return new GamePlay();
    }

    public int getVidaTotalHeroi() {
        return vidaTotalHeroi;
    }

    public void setVidaTotalHeroi(int vidaTotalHeroi) {
        this.vidaTotalHeroi = vidaTotalHeroi;
    }

    public int getCartasNaMao() {
        return cartasNaMao;
    }

    public void setCartasNaMao(int cartasNaMao) {
        this.cartasNaMao = cartasNaMao;
    }

    public int getShieldInicial() {
        return shieldInicial;
    }

    public void setShieldInicial(int shieldInicial) {
        this.shieldInicial = shieldInicial;
    }
}