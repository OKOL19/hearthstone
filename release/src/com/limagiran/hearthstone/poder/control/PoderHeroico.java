package com.limagiran.hearthstone.poder.control;

import com.limagiran.hearthstone.util.Images;
import com.limagiran.hearthstone.heroi.control.Heroi;
import java.io.Serializable;
import com.limagiran.hearthstone.util.Param;
import javax.swing.JPanel;
import com.limagiran.hearthstone.server.GameCliente;
import com.limagiran.hearthstone.poder.view.View;
import javax.swing.ImageIcon;
import com.limagiran.hearthstone.util.Values;

/**
 *
 * @author Vinicius
 */
public final class PoderHeroico extends View implements Serializable {

    public static final String PALADIN_DEFAULT = "CS2_101";
    public static final String ROGUE_DEFAULT = "CS2_083b";
    public static final String PRIEST_DEFAULT = "CS1h_001";
    public static final String WARRIOR_DEFAULT = "CS2_102";
    public static final String WARLOCK_DEFAULT = "CS2_056";
    public static final String MAGE_DEFAULT = "CS2_034";
    public static final String DRUID_DEFAULT = "CS2_017";
    public static final String HUNTER_DEFAULT = "DS1h_292";
    public static final String SHAMAN_DEFAULT = "CS2_049";
    public static final String PALADIN_MELHORADO = "AT_132_PALADIN";
    public static final String ROGUE_MELHORADO = "AT_132_ROGUE";
    public static final String PRIEST_MELHORADO = "AT_132_PRIEST";
    public static final String WARRIOR_MELHORADO = "AT_132_WARRIOR";
    public static final String WARLOCK_MELHORADO = "AT_132_WARLOCK";
    public static final String MAGE_MELHORADO = "AT_132_MAGE";
    public static final String DRUID_MELHORADO = "AT_132_DRUID";
    public static final String HUNTER_MELHORADO = "AT_132_HUNTER";
    public static final String SHAMAN_MELHORADO = "AT_132_SHAMAN";
    public static final String PRIEST_EX1_625 = "EX1_625t";
    public static final String PRIEST_EX1_625B = "EX1_625t2";
    public static final String SHAMAN_AT_050T = "AT_050t";

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

    private PoderHeroico(String type, Heroi heroi) {
        this.heroi = heroi;
        this.tipo = type;
        init();
    }

    public void init() {
        loadView(getInstance());
        setTipo(tipo);
    }

    public JPanel getPanelPoderHeroico() {
        return getPanelPoderHeroicoView();
    }

    public int getCusto(boolean flag) {
        return heroi.getPartida().getAlterarCusto().processarPoderHeroico(getCusto(), flag);
    }

    public int getCusto() {
        return custo + getCustoAura();
    }

    public void setCusto(int custo) {
        this.custo = custo;
        GameCliente.send(heroi.getHeroi() == Param.OPONENTE
                ? Param.HEROI_SET_CUSTO_PODER
                : Param.OPONENTE_SET_CUSTO_PODER, this.custo);
    }

    public int getCustoAura() {
        return custoAura;
    }

    public void setCustoAura(int custoAura) {
        this.custoAura = custoAura;
        GameCliente.send(heroi.getHeroi() == Param.OPONENTE
                ? Param.AURA_HEROI_SET_CUSTO_PODER
                : Param.AURA_OPONENTE_SET_CUSTO_PODER, this.custoAura);
    }

    public void addCustoAura(int value) {
        setCustoAura(getCustoAura() + value);
    }

    public final void setTipo(String type) {
        this.tipo = type;
        setIcon(getImagem(this.tipo));
    }
    
    private ImageIcon getImagem(String tipo) {
        switch (tipo) {
            case PALADIN_DEFAULT:
                return Images.PODER_PALADIN_DEFAULT;
            case ROGUE_DEFAULT:
                return Images.PODER_ROGUE_DEFAULT;
            case PRIEST_DEFAULT:
                return Images.PODER_PRIEST_DEFAULT;
            case WARRIOR_DEFAULT:
                return Images.PODER_WARRIOR_DEFAULT;
            case WARLOCK_DEFAULT:
                return Images.PODER_WARLOCK_DEFAULT;
            case MAGE_DEFAULT:
                return Images.PODER_MAGE_DEFAULT;
            case DRUID_DEFAULT:
                return Images.PODER_DRUID_DEFAULT;
            case HUNTER_DEFAULT:
                return Images.PODER_HUNTER_DEFAULT;
            case SHAMAN_DEFAULT:
                return Images.PODER_SHAMAN_DEFAULT;
            case PALADIN_MELHORADO:
                return Images.PODER_PALADIN_MELHORADO;
            case ROGUE_MELHORADO:
                return Images.PODER_ROGUE_MELHORADO;
            case PRIEST_MELHORADO:
                return Images.PODER_PRIEST_MELHORADO;
            case WARRIOR_MELHORADO:
                return Images.PODER_WARRIOR_MELHORADO;
            case WARLOCK_MELHORADO:
                return Images.PODER_WARLOCK_MELHORADO;
            case MAGE_MELHORADO:
                return Images.PODER_MAGE_MELHORADO;
            case DRUID_MELHORADO:
                return Images.PODER_DRUID_MELHORADO;
            case HUNTER_MELHORADO:
                return Images.PODER_HUNTER_MELHORADO;
            case SHAMAN_MELHORADO:
                return Images.PODER_SHAMAN_MELHORADO;
            case PRIEST_EX1_625:
                return Images.PODER_PRIEST_EX1_625T;
            case PRIEST_EX1_625B:
                return Images.PODER_PRIEST_EX1_625T2;
            case SHAMAN_AT_050T:
                return Images.PODER_SHAMAN_AT_050T;
            default:
                return Images.BLANK_SMALL;
        }
    }

    public String getTipo() {
        return tipo;
    }

    private PoderHeroico getInstance() {
        return this;
    }

    public Heroi getHeroi() {
        return heroi;
    }

}