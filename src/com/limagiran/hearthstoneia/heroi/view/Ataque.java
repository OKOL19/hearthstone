package com.limagiran.hearthstoneia.heroi.view;

import java.io.Serializable;

/**
 *
 * @author Vinicius
 */
public class Ataque implements Serializable {

    private boolean investida;

    public Ataque() {
    }

    public void setInvestida(boolean flag) {
        investida = flag;
    }

    public boolean isInvestaida() {
        return investida;
    }
}