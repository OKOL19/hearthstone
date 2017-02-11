package com.limagiran.hearthstoneia.poder.control;

import static com.limagiran.hearthstone.poder.control.PoderHeroico.*;
import com.limagiran.hearthstoneia.heroi.control.Heroi;
import com.limagiran.hearthstoneia.poder.view.View;
import com.limagiran.hearthstoneia.server.GameCliente;
import com.limagiran.hearthstone.util.Param;
import java.io.Serializable;
import javax.swing.JPanel;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius
 */
public final class PoderHeroico extends View implements Serializable {

    public static PoderHeroico getDefault(Heroi heroi) {
        switch (heroi.getType()) {
            case Values.BRUXO:
                return new PoderHeroico(WARLOCK_DEFAULT, heroi);
            case Values.CACADOR:
                return new PoderHeroico(HUNTER_DEFAULT, heroi);
            case Values.DRUIDA:
                return new PoderHeroico(DRUID_DEFAULT, heroi);
            case Values.GUERREIRO:
                return new PoderHeroico(WARRIOR_DEFAULT, heroi);
            case Values.LADINO:
                return new PoderHeroico(ROGUE_DEFAULT, heroi);
            case Values.MAGO:
                return new PoderHeroico(MAGE_DEFAULT, heroi);
            case Values.PALADINO:
                return new PoderHeroico(PALADIN_DEFAULT, heroi);
            case Values.SACERDOTE:
                return new PoderHeroico(PRIEST_DEFAULT, heroi);
            case Values.XAMA:
                return new PoderHeroico(SHAMAN_DEFAULT, heroi);
        }
        return null;
    }

    private final Heroi heroi;
    private String tipo;
    private int custo = 2;
    private int custoAura = 0;

    private PoderHeroico(String tipo, Heroi heroi) {
        this.heroi = heroi;
        this.tipo = tipo;
        init();
    }

    public void init() {
        loadView(getInstance());
        setTipo(this.tipo);
    }

    public JPanel getPanelPoderHeroico() {
        return getPanelPoderHeroicoView();
    }

    public String getTipo() {
        return tipo;
    }

    public int getCusto(boolean flag) {
        return heroi.getPartida().getAlterarCusto().processarPoderHeroico(getCusto(), flag);
    }

    public int getCusto() {
        return custo + getCustoAura();
    }

    public void setCusto(int custo) {
        this.custo = custo;
        GameCliente.enviar(heroi.getHeroi() == Param.OPONENTE
                ? Param.HEROI_SET_CUSTO_PODER
                : Param.OPONENTE_SET_CUSTO_PODER, this.custo);
    }

    public int getCustoAura() {
        return custoAura;
    }

    public void setCustoAura(int custoAura) {
        this.custoAura = custoAura;
        GameCliente.enviar(heroi.getHeroi() == Param.OPONENTE
                ? Param.AURA_HEROI_SET_CUSTO_PODER
                : Param.AURA_OPONENTE_SET_CUSTO_PODER, this.custoAura);
    }

    public void addCustoAura(int value) {
        setCustoAura(getCustoAura() + value);
    }

    public final void setTipo(String tipo) {
        this.tipo = tipo;        
    }

    private PoderHeroico getInstance() {
        return this;
    }

    public Heroi getHeroi() {
        return heroi;
    }

}